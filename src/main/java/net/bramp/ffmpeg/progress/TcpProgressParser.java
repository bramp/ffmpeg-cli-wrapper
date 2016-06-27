package net.bramp.ffmpeg.progress;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.URI;
import java.net.URISyntaxException;

public class TcpProgressParser extends AbstractSocketProgressParser {

  final ServerSocket server;
  final URI address;

  public TcpProgressParser(ProgressListener listener) throws IOException, URISyntaxException {
    this(listener, 0, InetAddress.getLoopbackAddress());
  }

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

  protected String getThreadName() {
    return "TcpProgressParser";
  }

  @Override
  protected Runnable getRunnable() {
    return new TcpProgressParserRunnable(parser, server);
  }

  public URI getUri() {
    return address;
  }

}
