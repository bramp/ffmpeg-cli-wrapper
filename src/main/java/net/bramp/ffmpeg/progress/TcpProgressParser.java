package net.bramp.ffmpeg.progress;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;

/** Parses FFmpeg progress output over a TCP socket connection. */
public class TcpProgressParser extends AbstractSocketProgressParser {

  final ServerSocket server;
  final URI address;

  /** Constructs a TCP progress parser listening on a random available port. */
  public TcpProgressParser(ProgressListener listener) throws IOException, URISyntaxException {
    this(listener, 0, InetAddress.getLoopbackAddress());
  }

  /** Creates a new TCP progress parser bound to the given port and address. */
  public TcpProgressParser(ProgressListener listener, int port, InetAddress addr)
      throws IOException, URISyntaxException {
    super(listener);
    this.server = new ServerSocket(port, 0, addr);
    this.address = createUri("tcp", server.getInetAddress(), server.getLocalPort());
  }

  @Override
  public synchronized void stop() throws IOException {
    if (server.isClosed()) {
      // Allow double stop, and ignore
      return;
    }

    server.close(); // This unblocks server.accept();
    super.stop();
  }

  @Override
  protected String getThreadName() {
    return "TcpProgressParser";
  }

  @Override
  protected Runnable getRunnable(CountDownLatch startSignal) {
    return new TcpProgressParserRunnable(parser, server, startSignal);
  }

  @Override
  public URI getUri() {
    return address;
  }
}
