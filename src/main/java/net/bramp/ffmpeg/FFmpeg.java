package net.bramp.ffmpeg;

import static com.google.common.base.MoreObjects.firstNonNull;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.ImmutableList;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.info.Codec;
import net.bramp.ffmpeg.info.Format;
import net.bramp.ffmpeg.info.PixelFormat;
import net.bramp.ffmpeg.progress.ProgressListener;
import net.bramp.ffmpeg.progress.ProgressParser;
import net.bramp.ffmpeg.progress.TcpProgressParser;
import org.apache.commons.lang3.math.Fraction;

/**
 * Wrapper around FFmpeg
 *
 * @author bramp
 */
public class FFmpeg extends FFcommon {

  public static final String FFMPEG = "ffmpeg";
  public static final String DEFAULT_PATH = firstNonNull(System.getenv("FFMPEG"), FFMPEG);

  public static final Fraction FPS_30 = Fraction.getFraction(30, 1);
  public static final Fraction FPS_29_97 = Fraction.getFraction(30000, 1001);
  public static final Fraction FPS_24 = Fraction.getFraction(24, 1);
  public static final Fraction FPS_23_976 = Fraction.getFraction(24000, 1001);

  public static final int AUDIO_MONO = 1;
  public static final int AUDIO_STEREO = 2;

  public static final String AUDIO_FORMAT_U8 = "u8"; // 8
  public static final String AUDIO_FORMAT_S16 = "s16"; // 16
  public static final String AUDIO_FORMAT_S32 = "s32"; // 32
  public static final String AUDIO_FORMAT_FLT = "flt"; // 32
  public static final String AUDIO_FORMAT_DBL = "dbl"; // 64

  @Deprecated public static final String AUDIO_DEPTH_U8 = AUDIO_FORMAT_U8;
  @Deprecated public static final String AUDIO_DEPTH_S16 = AUDIO_FORMAT_S16;
  @Deprecated public static final String AUDIO_DEPTH_S32 = AUDIO_FORMAT_S32;
  @Deprecated public static final String AUDIO_DEPTH_FLT = AUDIO_FORMAT_FLT;
  @Deprecated public static final String AUDIO_DEPTH_DBL = AUDIO_FORMAT_DBL;

  public static final int AUDIO_SAMPLE_8000 = 8000;
  public static final int AUDIO_SAMPLE_11025 = 11025;
  public static final int AUDIO_SAMPLE_12000 = 12000;
  public static final int AUDIO_SAMPLE_16000 = 16000;
  public static final int AUDIO_SAMPLE_22050 = 22050;
  public static final int AUDIO_SAMPLE_32000 = 32000;
  public static final int AUDIO_SAMPLE_44100 = 44100;
  public static final int AUDIO_SAMPLE_48000 = 48000;
  public static final int AUDIO_SAMPLE_96000 = 96000;

  static final Pattern CODECS_REGEX = Pattern.compile("^ ([.D][.E][VASD][.I][.L][.S]) (\\S{2,})\\s+(.*)$");
  static final Pattern FORMATS_REGEX = Pattern.compile("^ ([ D][ E]) (\\S+)\\s+(.*)$");
  static final Pattern PIXEL_FORMATS_REGEX = Pattern.compile("^([.I][.O][.H][.P][.B]) (\\S{2,})\\s+(\\d+)\\s+(\\d+)$");

  /** Supported codecs */
  List<Codec> codecs = null;

  /** Supported formats */
  List<Format> formats = null;

  /** Supported pixel formats */
  private List<PixelFormat> pixelFormats = null;

  public FFmpeg() throws IOException {
    this(DEFAULT_PATH, new RunProcessFunction());
  }

  public FFmpeg(@Nonnull ProcessFunction runFunction) throws IOException {
    this(DEFAULT_PATH, runFunction);
  }

  public FFmpeg(@Nonnull String path) throws IOException {
    this(path, new RunProcessFunction());
  }

  public FFmpeg(@Nonnull String path, @Nonnull ProcessFunction runFunction) throws IOException {
    super(path, runFunction);
    version();
  }

  /**
   * Returns true if the binary we are using is the true ffmpeg. This is to avoid conflict with
   * avconv (from the libav project), that some symlink to ffmpeg.
   *
   * @return true iff this is the official ffmpeg binary.
   * @throws IOException If a I/O error occurs while executing ffmpeg.
   */
  public boolean isFFmpeg() throws IOException {
    return version().startsWith("ffmpeg");
  }

  /**
   * Throws an exception if this is an unsupported version of ffmpeg.
   *
   * @throws IllegalArgumentException if this is not the official ffmpeg binary.
   * @throws IOException If a I/O error occurs while executing ffmpeg.
   */
  private void checkIfFFmpeg() throws IllegalArgumentException, IOException {
    if (!isFFmpeg()) {
      throw new IllegalArgumentException(
          "This binary '" + path + "' is not a supported version of ffmpeg");
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
          if (!m.matches()) continue;

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
          if (!m.matches()) continue;

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

  public synchronized List<PixelFormat> pixelFormats() throws IOException {
    checkIfFFmpeg();

    if (this.pixelFormats == null) {
      pixelFormats = new ArrayList<>();

      Process p = runFunc.run(ImmutableList.of(path, "-pix_fmts"));
      try {
        BufferedReader r = wrapInReader(p);
        String line;
        while ((line = r.readLine()) != null) {
          Matcher m = PIXEL_FORMATS_REGEX.matcher(line);
          if (!m.matches())
            continue;
          String flags = m.group(1);

          pixelFormats.add(new PixelFormat(
              m.group(2),
              Integer.parseInt(m.group(3)),
              Integer.parseInt(m.group(4)),
              flags));
        }

        throwOnError(p);
        this.pixelFormats = ImmutableList.copyOf(pixelFormats);
      } finally {
        p.destroy();
      }
    }

    return pixelFormats;
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

  @Override
  public void run(List<String> args) throws IOException {
    checkIfFFmpeg();
    super.run(args);
  }

  public void run(FFmpegBuilder builder) throws IOException {
    run(builder, null);
  }

  public void run(FFmpegBuilder builder, @Nullable ProgressListener listener) throws IOException {
    checkNotNull(builder);

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

  @CheckReturnValue
  public FFmpegBuilder builder() {
    return new FFmpegBuilder();
  }

  @Override
  public String getPath() {
    return path;
  }
}
