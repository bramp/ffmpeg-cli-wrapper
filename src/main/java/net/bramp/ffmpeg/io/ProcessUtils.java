package net.bramp.ffmpeg.io;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * A collection of utility methods for dealing with processes.
 * 
 * @author bramp
 */
public final class ProcessUtils {
  private ProcessUtils() {
    throw new AssertionError("No instances for you!");
  }

  /**
   * Waits until a process finishes or a timeout occurs
   *
   * @param p process
   * @param timeout timeout in given unit
   * @param unit time unit
   * @return the process exit value
   * @throws TimeoutException if a timeout occurs
   */
  public static int waitForWithTimeout(final Process p, long timeout, TimeUnit unit)
      throws TimeoutException {

    try {
      p.waitFor(timeout, unit);

    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    if (p.isAlive()) {
      throw new TimeoutException("Process did not finish within timeout");
    }

    return p.exitValue();
  }
}
