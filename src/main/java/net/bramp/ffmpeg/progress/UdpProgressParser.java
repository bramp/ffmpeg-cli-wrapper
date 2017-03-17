package net.bramp.ffmpeg.progress;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;

import static com.google.common.base.Preconditions.checkNotNull;

public class UdpProgressParser extends AbstractSocketProgressParser {

  final DatagramSocket socket;
  final URI address;

  public UdpProgressParser(ProgressListener listener) throws SocketException, URISyntaxException {
    this(listener, 0, InetAddress.getLoopbackAddress());
  }

  public UdpProgressParser(ProgressListener listener, int port, InetAddress addr)
      throws SocketException, URISyntaxException {

    super(listener);

    this.socket = new DatagramSocket(port, checkNotNull(addr));
    this.address = createUri("udp", socket.getLocalAddress(), socket.getLocalPort());

    this.socket.setBroadcast(false);
    // this.socket.setSoTimeout(); // TODO Setup timeouts
  }

  @Override
  public synchronized void stop() throws IOException {
    if (socket.isClosed()) {
      // Allow double stop, and ignore
      return;
    }

    socket.close();
    super.stop();
  }

  @Override
  protected String getThreadName() {
    return "UdpProgressParser";
  }

  @Override
  protected Runnable getRunnable(CountDownLatch startSignal) {
    return new UdpProgressParserRunnable(parser, socket, startSignal);
  }

  @Override
  public URI getUri() {
    return address;
  }
}
