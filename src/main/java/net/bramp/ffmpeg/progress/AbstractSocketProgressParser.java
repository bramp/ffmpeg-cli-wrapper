package net.bramp.ffmpeg.progress;

import com.google.common.net.InetAddresses;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;

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

  protected abstract Runnable getRunnable();

  /**
   *
   * @exception IllegalThreadStateException if the parser was already started.
   */
  public synchronized void start() {
    if (thread != null) {
      throw new IllegalThreadStateException("Parser already started");
    }

    String name = getThreadName() + "(" + getUri().toString() + ")";
    thread = new Thread(getRunnable(), name);
    thread.start();
  }

  @Override
  public void stop() throws IOException {
    if (thread != null) {
      thread.interrupt(); // This unblocks processStream();

      try {
        thread.join();
        thread = null;
      } catch (InterruptedException e) {
        // Ignore and return
      }
    }
  }

  @Override
  public void close() throws IOException {
    stop();
  }
}
