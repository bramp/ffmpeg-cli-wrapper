package net.bramp.ffmpeg.progress;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;

/** Parses FFmpeg progress output over a UDP socket connection. */
public class UdpProgressParser extends AbstractSocketProgressParser {

  final DatagramSocket socket;
  final URI address;

  /** Constructs a UDP progress parser listening on a random available port. */
  public UdpProgressParser(ProgressListener listener) throws SocketException, URISyntaxException {
    this(listener, 0, InetAddress.getLoopbackAddress());
  }

  /** Creates a new UDP progress parser bound to the given port and address. */
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
