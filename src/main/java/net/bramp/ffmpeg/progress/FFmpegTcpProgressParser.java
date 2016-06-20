package net.bramp.ffmpeg.progress;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;

public class FFmpegTcpProgressParser extends FFmpegProgressParser {

  final int port;
  final InetAddress addr;

  URI address;

  public FFmpegTcpProgressParser(FFmpegProgressListener listener) {
    this(listener, 0, InetAddress.getLoopbackAddress());
  }

  public FFmpegTcpProgressParser(FFmpegProgressListener listener, int port, InetAddress addr) {
    super(listener);
    this.port = port;
    this.addr = addr;
  }

  public void start() throws IOException {
    try (ServerSocket server = new ServerSocket(port, 0, addr)) {
      address = URI.create("tcp://" + server.getLocalSocketAddress().toString());

      while (true) {
        Socket socket = server.accept();
        try (InputStream stream = socket.getInputStream()) {
          processStream(stream);
        }
      }
    }
  }

  @Override
  public void stop() {}

  public URI getUri() {
    return address;
  }
}
