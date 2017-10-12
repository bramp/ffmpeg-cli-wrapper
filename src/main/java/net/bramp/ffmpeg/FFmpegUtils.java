package net.bramp.ffmpeg;

import com.google.common.base.CharMatcher;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.bramp.commons.lang3.math.gson.FractionAdapter;
import net.bramp.ffmpeg.gson.LowercaseEnumTypeAdapterFactory;
import net.bramp.ffmpeg.gson.NamedBitsetAdapter;
import net.bramp.ffmpeg.probe.FFmpegDisposition;
import org.apache.commons.lang3.math.Fraction;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.concurrent.TimeUnit.*;
import static net.bramp.ffmpeg.Preconditions.checkNotEmpty;

/** Helper class with commonly used methods */
public final class FFmpegUtils {

  static final Gson gson = FFmpegUtils.setupGson();
  static final Pattern BITRATE_REGEX = Pattern.compile("(\\d+(?:\\.\\d+)?)kbits/s");
  static final Pattern TIME_REGEX = Pattern.compile("(\\d+):(\\d+):(\\d+(?:\\.\\d+)?)");
  static final CharMatcher ZERO = CharMatcher.is('0');

  FFmpegUtils() {
    throw new AssertionError("No instances for you!");
  }

  /**
   * Convert milliseconds to "hh:mm:ss.ms" String representation.
   *
   * @param milliseconds time duration in milliseconds
   * @return time duration in human-readable format
   * @deprecated please use #toTimecode() instead.
   */
  @Deprecated
  public static String millisecondsToString(long milliseconds) {
    return toTimecode(milliseconds, MILLISECONDS);
  }

  /**
   * Convert the duration to "hh:mm:ss" timecode representation, where ss (seconds) can be decimal.
   *
   * @param duration the duration.
   * @param units the unit the duration is in.
   * @return the timecode representation.
   */
  public static String toTimecode(long duration, TimeUnit units) {
    // FIXME Negative durations are also supported.
    // https://www.ffmpeg.org/ffmpeg-utils.html#Time-duration
    checkArgument(duration >= 0, "duration must be positive");

    long nanoseconds = units.toNanos(duration); // TODO This will clip at Long.MAX_VALUE
    long seconds = units.toSeconds(duration);
    long ns = nanoseconds - SECONDS.toNanos(seconds);

    long minutes = SECONDS.toMinutes(seconds);
    seconds -= MINUTES.toSeconds(minutes);

    long hours = MINUTES.toHours(minutes);
    minutes -= HOURS.toMinutes(hours);

    if (ns == 0) {
      return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    return ZERO.trimTrailingFrom(String.format("%02d:%02d:%02d.%09d", hours, minutes, seconds, ns));
  }

  /**
   * Returns the number of nanoseconds this timecode represents. The string is expected to be in the
   * format "hour:minute:second", where second can be a decimal number.
   *
   * @param time the timecode to parse.
   * @return the number of nanoseconds.
   */
  public static long fromTimecode(String time) {
    checkNotEmpty(time, "time must not be empty string");
    Matcher m = TIME_REGEX.matcher(time);
    if (!m.find()) {
      throw new IllegalArgumentException("invalid time '" + time + "'");
    }

    long hours = Long.parseLong(m.group(1));
    long mins = Long.parseLong(m.group(2));
    double secs = Double.parseDouble(m.group(3));

    return HOURS.toNanos(hours) + MINUTES.toNanos(mins) + (long) (SECONDS.toNanos(1) * secs);
  }

  /**
   * Converts a string representation of bitrate to a long of bits per second
   *
   * @param bitrate in the form of 12.3kbits/s
   * @return the bitrate in bits per second.
   */
  public static long parseBitrate(String bitrate) {
    if (bitrate.equals("N/A")) {
      return -1;
    }

    Matcher m = BITRATE_REGEX.matcher(bitrate);
    if (!m.find()) {
      throw new IllegalArgumentException("Invalid bitrate '" + bitrate + "'");
    }

    return (long) (Float.parseFloat(m.group(1)) * 1000);
  }

  static Gson getGson() {
    return gson;
  }

  private static Gson setupGson() {
    GsonBuilder builder = new GsonBuilder();

    builder.registerTypeAdapterFactory(new LowercaseEnumTypeAdapterFactory());
    builder.registerTypeAdapter(Fraction.class, new FractionAdapter());
    builder.registerTypeAdapter(
        FFmpegDisposition.class, new NamedBitsetAdapter<>(FFmpegDisposition.class));

    return builder.create();
  }
}
