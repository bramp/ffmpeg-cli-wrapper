package net.bramp.ffmpeg.progress;

import com.google.common.net.InetAddresses;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class AbstractSocketProgressParser implements ProgressParser {

  final StreamProgressParser parser;

  Thread thread; // Thread for handling incoming connections

  public AbstractSocketProgressParser(ProgressListener listener) {
    this.parser = new StreamProgressParser(listener);
  }

  /**
   * Creates a URL to parse to FFmpeg based on the scheme, address and port.
   *
   * TODO Move this method to somewhere better.
   *
   * @param scheme
   * @param address
   * @param port
   * @return
   * @throws URISyntaxException
   */
  static URI createUri(String scheme, InetAddress address, int port) throws URISyntaxException {
    checkNotNull(address);
    return new URI(scheme, null /* userInfo */, InetAddresses.toUriString(address), port,
        null /* path */, null /* query */, null /* fragment */);
  }

  protected abstract String getThreadName();

  protected abstract Runnable getRunnable(CountDownLatch startSignal);

  /**
   *
   * @exception IllegalThreadStateException if the parser was already started.
   */
  public synchronized void start() {
    if (thread != null) {
      throw new IllegalThreadStateException("Parser already started");
    }

    String name = getThreadName() + "(" + getUri().toString() + ")";

    CountDownLatch startSignal = new CountDownLatch(1);
    Runnable runnable = getRunnable(startSignal);

    thread = new Thread(runnable, name);
    thread.start();

    // Block until the thread has started
    try {
      startSignal.await();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  @Override
  public void stop() throws IOException {
    if (thread != null) {
      thread.interrupt(); // This unblocks processStream();

      try {
        thread.join();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  @Override
  public void close() throws IOException {
    stop();
  }
}
