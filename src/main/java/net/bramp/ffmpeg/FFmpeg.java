package net.bramp.ffmpeg;

import com.google.common.base.Charsets;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.info.Codec;
import net.bramp.ffmpeg.info.Format;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.NullOutputStream;
import org.apache.commons.lang3.math.Fraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author bramp
 *
 */
public class FFmpeg {

  final static Logger LOG = LoggerFactory.getLogger(FFmpeg.class);

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
   * Path to FFmpeg (e.g. /usr/bin/ffmpeg)
   */
  final String path;

  /**
   * Function to run FFmpeg. We define it like this so we can swap it out (during testing)
   */
  final ProcessFunction runFunc;

  /**
   * Supported codecs
   */
  List<Codec> codecs = null;

  /**
   * Supported formats
   */
  List<Format> formats = null;

  /**
   * Version string
   */
  String version = null;

  public FFmpeg() throws IOException {
    this(DEFAULT_PATH, new RunProcessFunction());
  }

  public FFmpeg(@Nonnull String path) throws IOException {
    this(path, new RunProcessFunction());
  }

  public FFmpeg(@Nonnull ProcessFunction runFunction) throws IOException {
    this(DEFAULT_PATH, runFunction);
  }

  public FFmpeg(@Nonnull String path, @Nonnull ProcessFunction runFunction) throws IOException {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(path));
    this.runFunc = checkNotNull(runFunction);
    this.path = path;
    version();
  }

  private static BufferedReader wrapInReader(Process p) {
    return new BufferedReader(new InputStreamReader(p.getInputStream(), Charsets.UTF_8));
  }

  public synchronized @Nonnull String version() throws IOException {
    if (this.version == null) {
      Process p = runFunc.run(ImmutableList.of(path, "-version"));
      try {
        BufferedReader r = wrapInReader(p);
        this.version = r.readLine();
        IOUtils.copy(r, new NullOutputStream(), Charsets.UTF_8); // Throw away rest of the output
        FFmpegUtils.throwOnError(FFMPEG, p);
      } finally {
        p.destroy();
      }
    }
    return version;
  }

  public synchronized @Nonnull List<Codec> codecs() throws IOException {
    if (this.codecs == null) {
      codecs = new ArrayList<Codec>();

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

        FFmpegUtils.throwOnError(FFMPEG, p);
        this.codecs = ImmutableList.copyOf(codecs);
      } finally {
        p.destroy();
      }
    }

    return codecs;
  }


  public synchronized @Nonnull List<Format> formats() throws IOException {
    if (this.formats == null) {
      formats = new ArrayList<Format>();

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

        FFmpegUtils.throwOnError(FFMPEG, p);
        this.formats = ImmutableList.copyOf(formats);
      } finally {
        p.destroy();
      }
    }
    return formats;
  }

  /**
   * Runs ffmpeg with the supplied args. Blocking until finished.
   * @param args
   * @throws IOException
   */
  public void run(List<String> args) throws IOException {
    List<String> newArgs = ImmutableList.<String>builder().add(path).addAll(args).build();

    Process p = runFunc.run(newArgs);
    try {
      // Now block reading ffmpeg's stdout. We are effectively throwing away the output.
      IOUtils.copy(wrapInReader(p), System.out, Charsets.UTF_8); // TODO Should I be outputting to
                                                                 // stdout?

      FFmpegUtils.throwOnError(FFMPEG, p);

    } finally {
      p.destroy();
    }
  }

  public FFmpegBuilder builder() {
    return new FFmpegBuilder();
  }

  public String getPath() {
    return path;
  }
}
