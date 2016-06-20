package net.bramp.ffmpeg.progress;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;

public class FFmpegUdpProgressParser extends FFmpegProgressParser {

  final int port;
  final InetAddress addr;

  URI address;

  public FFmpegUdpProgressParser(FFmpegProgressListener listener) {
    this(listener, 0, InetAddress.getLoopbackAddress());
  }

  public FFmpegUdpProgressParser(FFmpegProgressListener listener, int port, InetAddress addr) {
    super(listener);
    this.port = port;
    this.addr = addr;
  }


  public void start() throws IOException {
    try (DatagramSocket socket = new DatagramSocket(port, addr)) {
      socket.setBroadcast(false);
      // socket.setSoTimeout(); // TODO Setup timeouts

      address = URI.create("udp://" + socket.getLocalSocketAddress().toString());


      final byte[] buf = new byte[1024];
      final ByteArrayInputStream in = new ByteArrayInputStream(buf);

      while (true) {
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        processStream(in);
        in.reset();
      }
    }
  }

  @Override
  public void stop() {}

  public URI getUri() {
    return address;
  }
}
