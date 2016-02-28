package net.bramp.ffmpeg;

import net.bramp.ffmpeg.io.ProcessUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Helper class with commonly used methods
 */
public final class FFmpegUtils {

  private FFmpegUtils() {}

  /**
   * Convert milliseconds to "hh:mm:ss.ms" String representation.
   *
   * @param milliseconds
   * @return
   */
  public static String millisecondsToString(long milliseconds) {

    checkArgument(milliseconds >= 0, "milliseconds must be >= 0");

    long seconds = milliseconds / 1000;
    milliseconds = milliseconds - (seconds * 1000);

    long minutes = seconds / 60;
    seconds = seconds - (minutes * 60);

    long hours = minutes / 60;
    minutes = minutes - (hours * 60);

    if (milliseconds == 0)
      return String.format("%02d:%02d:%02d", hours, minutes, seconds);

    return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds);
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
}
