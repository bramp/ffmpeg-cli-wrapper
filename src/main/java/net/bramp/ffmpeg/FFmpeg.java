package net.bramp.ffmpeg;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.info.Codec;
import net.bramp.ffmpeg.info.Format;
import net.bramp.ffmpeg.progress.ProgressListener;
import net.bramp.ffmpeg.progress.ProgressParser;
import net.bramp.ffmpeg.progress.TcpProgressParser;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.NullOutputStream;
import org.apache.commons.lang3.math.Fraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.io.output.NullOutputStream.NULL_OUTPUT_STREAM;

/**
 * Wrapper around FFmpeg
 *
 * @author bramp
 *
 */
public class FFmpeg extends FFcommon {

  final static String FFMPEG = "ffmpeg";
  final static String DEFAULT_PATH = MoreObjects.firstNonNull(System.getenv("FFMPEG"), FFMPEG);

  public final static Fraction FPS_30 = Fraction.getFraction(30, 1);
  public final static Fraction FPS_29_97 = Fraction.getFraction(30000, 1001);
  public final static Fraction FPS_24 = Fraction.getFraction(24, 1);
  public final static Fraction FPS_23_976 = Fraction.getFraction(24000, 1001);

  public final static int AUDIO_MONO = 1;
  public final static int AUDIO_STEREO = 2;

  public final static String AUDIO_DEPTH_U8 = "u8"; // 8
  public final static String AUDIO_DEPTH_S16 = "s16"; // 16
  public final static String AUDIO_DEPTH_S32 = "s32"; // 32
  public final static String AUDIO_DEPTH_FLT = "flt"; // 32
  public final static String AUDIO_DEPTH_DBL = "dbl"; // 64

  public final static int AUDIO_SAMPLE_8000 = 8000;
  public final static int AUDIO_SAMPLE_11025 = 11025;
  public final static int AUDIO_SAMPLE_12000 = 12000;
  public final static int AUDIO_SAMPLE_16000 = 16000;
  public final static int AUDIO_SAMPLE_22050 = 22050;
  public final static int AUDIO_SAMPLE_32000 = 32000;
  public final static int AUDIO_SAMPLE_44100 = 44100;
  public final static int AUDIO_SAMPLE_48000 = 48000;
  public final static int AUDIO_SAMPLE_96000 = 96000;

  final static Pattern CODECS_REGEX = Pattern
      .compile("^ ([ D][ E][VAS][ S][ D][ T]) (\\S+)\\s+(.*)$");
  final static Pattern FORMATS_REGEX = Pattern.compile("^ ([ D][ E]) (\\S+)\\s+(.*)$");

  /**
   * Supported codecs
   */
  List<Codec> codecs = null;

  /**
   * Supported formats
   */
  List<Format> formats = null;

  public FFmpeg() throws IOException {
    this(DEFAULT_PATH, new RunProcessFunction());
  }

  public FFmpeg(@Nonnull ProcessFunction runFunction) throws IOException {
    this(DEFAULT_PATH, runFunction);
  }

  public FFmpeg(@Nonnull String path, @Nonnull ProcessFunction runFunction) throws IOException {
    super(path, runFunction);
    version();
  }

  /**
   * Returns true if the binary we are using is the true ffmpeg. This is to avoid conflict with
   * avconv (from the libav project), that some symlink to ffmpeg.
   * 
   * @return
   */
  public boolean isFFmpeg() throws IOException {
    return version().startsWith("ffmpeg");
  }

  /**
   * Throws an exception if this is an unsupported version of ffmpeg.
   * 
   * @throws IllegalArgumentException
   * @throws IOException
   */
  private void checkIfFFmpeg() throws IllegalArgumentException, IOException {
    if (!isFFmpeg()) {
      throw new IllegalArgumentException("This binary '" + path
          + "' is not a supported version of ffmpeg");
    }
  }

  public synchronized @Nonnull List<Codec> codecs() throws IOException {
    checkIfFFmpeg();

    if (this.codecs == null) {
      codecs = new ArrayList<>();

      Process p = runFunc.run(ImmutableList.of(path, "-codecs"));
      try {
        BufferedReader r = wrapInReader(p);
        String line;
        while ((line = r.readLine()) != null) {
          Matcher m = CODECS_REGEX.matcher(line);
          if (!m.matches())
            continue;

          codecs.add(new Codec(m.group(2), m.group(3), m.group(1)));
        }

        throwOnError(p);
        this.codecs = ImmutableList.copyOf(codecs);
      } finally {
        p.destroy();
      }
    }

    return codecs;
  }


  public synchronized @Nonnull List<Format> formats() throws IOException {
    checkIfFFmpeg();

    if (this.formats == null) {
      formats = new ArrayList<>();

      Process p = runFunc.run(ImmutableList.of(path, "-formats"));
      try {
        BufferedReader r = wrapInReader(p);
        String line;
        while ((line = r.readLine()) != null) {
          Matcher m = FORMATS_REGEX.matcher(line);
          if (!m.matches())
            continue;

          formats.add(new Format(m.group(2), m.group(3), m.group(1)));
        }

        throwOnError(p);
        this.formats = ImmutableList.copyOf(formats);
      } finally {
        p.destroy();
      }
    }
    return formats;
  }

  protected ProgressParser createProgressParser(ProgressListener listener) throws IOException {
    // TODO In future create the best kind for this OS, unix socket, named pipe, or TCP.
    try {
      // Default to TCP because it is supported across all OSes, and is better than UDP because it
      // provides good properties such as in-order packets, reliability, error checking, etc.
      return new TcpProgressParser(checkNotNull(listener));
    } catch (URISyntaxException e) {
      throw new IOException(e);
    }
  }

  public void run(List<String> args) throws IOException {
    checkIfFFmpeg();
    super.run(args);
  }

  public void run(FFmpegBuilder builder, @Nullable ProgressListener listener) throws IOException {
    checkNotNull(builder);
    checkIfFFmpeg();

    if (listener != null) {
      try (ProgressParser progressParser = createProgressParser(listener)) {
        progressParser.start();
        builder = builder.addProgress(progressParser.getUri());

        run(builder.build());
      }
    } else {
      run(builder.build());
    }
  }

  public FFmpegBuilder builder() {
    return new FFmpegBuilder();
  }

  public String getPath() {
    return path;
  }
}
