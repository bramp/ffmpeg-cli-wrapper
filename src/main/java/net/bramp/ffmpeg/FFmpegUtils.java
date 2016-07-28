package net.bramp.ffmpeg;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.bramp.commons.lang3.math.gson.FractionAdapter;
import net.bramp.ffmpeg.gson.LowercaseEnumTypeAdapterFactory;
import net.bramp.ffmpeg.gson.NamedBitsetAdapter;
import net.bramp.ffmpeg.io.ProcessUtils;
import net.bramp.ffmpeg.probe.FFmpegDisposition;
import org.apache.commons.lang3.math.Fraction;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Helper class with commonly used methods
 */
public final class FFmpegUtils {

  final static Gson gson = FFmpegUtils.setupGson();

  private FFmpegUtils() {
    throw new AssertionError("No instances for you!");
  }

  /**
   * Convert milliseconds to "{@code [-]S+[.ms]}"" String representation.
   * 
   * <p>
   * "{@code S+}" expresses the number of seconds, with the optional decimal part "{@code ms}".
   * The optional "{@code -}" indicates negative duration.
   * 
   * @param milliseconds time duration in milliseconds
   * @return time duration in seconds 
   * @see <a href="https://www.ffmpeg.org/ffmpeg-utils.html#Time-duration">FFmpeg - Syntax - Time duration</a>
   */
  public static String millisecondsToString(final long milliseconds) {
    // FIXME Negative durations are also supported.
    // https://www.ffmpeg.org/ffmpeg-utils.html#Time-duration
    checkArgument(milliseconds >= 0, "milliseconds must be positive");

    final long seconds = milliseconds / 1000;
    final long decimalPart = milliseconds - (seconds * 1000);
   
    if (decimalPart == 0) {
      return String.format(Locale.US, "%s", seconds);
    }

    return String.format(Locale.US, "%s.%s", seconds, decimalPart);
  }

  /**
   * Converts a string representation of bitrate to a long of bits per second
   *
   * @param bitrate in the form of 12.3kbits/s
   * @return
   */
  public static long parseBitrate(String bitrate) {
    Pattern p = Pattern.compile("(\\d+(?:\\.\\d+)?)kbits/s");
    Matcher m = p.matcher(bitrate);
    if (!m.find()) {
      throw new IllegalArgumentException("Invalid bitrate '" + bitrate + "'");
    }

    return (long) (Float.parseFloat(m.group(1)) * 1000);
  }

  public static void throwOnError(String cmd, Process p) throws IOException {
    try {
      // TODO In java 8 use waitFor(long timeout, TimeUnit unit)
      if (ProcessUtils.waitForWithTimeout(p, 1, TimeUnit.SECONDS) != 0) {
        // TODO Parse the error
        throw new IOException(cmd + " returned non-zero exit status. Check stdout.");
      }
    } catch (TimeoutException e) {
      throw new IOException("Timed out waiting for " + cmd + " to finish.");
    }
  }

  static Gson getGson() {
    return gson;
  }

  private static Gson setupGson() {
    GsonBuilder builder = new GsonBuilder();

    builder.registerTypeAdapterFactory(new LowercaseEnumTypeAdapterFactory());
    builder.registerTypeAdapter(Fraction.class, new FractionAdapter());
    builder.registerTypeAdapter(FFmpegDisposition.class, new NamedBitsetAdapter<>(
        FFmpegDisposition.class));

    return builder.create();
  }
}
