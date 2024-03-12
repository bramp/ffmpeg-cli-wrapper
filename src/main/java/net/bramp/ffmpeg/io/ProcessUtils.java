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

  private static class ProcessThread extends Thread {
    final Process p;
    boolean finished = false;
    int exitValue = -1;

    private ProcessThread(Process p) {
      this.p = p;
    }

    @Override
    public void run() {
      try {
        exitValue = p.waitFor();
        finished = true;
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }

    public boolean hasFinished() {
      return finished;
    }

    public int exitValue() {
      return exitValue;
    }
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

    ProcessThread t = new ProcessThread(p);
    t.start();
    try {
      unit.timedJoin(t, timeout);

    } catch (InterruptedException e) {
      t.interrupt();
      Thread.currentThread().interrupt();
    }

    if (!t.hasFinished()) {
      throw new TimeoutException("Process did not finish within timeout");
    }

    return t.exitValue();
  }
}
