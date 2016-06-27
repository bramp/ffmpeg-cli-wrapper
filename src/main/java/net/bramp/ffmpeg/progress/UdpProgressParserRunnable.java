package net.bramp.ffmpeg.progress;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import static com.google.common.base.Preconditions.checkNotNull;

class UdpProgressParserRunnable implements Runnable {

  final static int MAX_PACKET_SIZE = 1500;

  final StreamProgressParser parser;
  final DatagramSocket socket;

  public UdpProgressParserRunnable(StreamProgressParser parser, DatagramSocket socket) {
    this.parser = checkNotNull(parser);
    this.socket = checkNotNull(socket);
  }

  @Override
  public void run() {
    final byte[] buf = new byte[MAX_PACKET_SIZE];
    final DatagramPacket packet = new DatagramPacket(buf, buf.length);

    while (!socket.isClosed() && !Thread.currentThread().isInterrupted()) {
      try {
        // TODO This doesn't handle the case of a progress being split across two packets
        socket.receive(packet);

        if (packet.getLength() == 0) {
          continue;
        }

        final ByteArrayInputStream in =
            new ByteArrayInputStream(packet.getData(), packet.getOffset(), packet.getLength());
        parser.processStream(in);

      } catch (SocketException e) {
        // Most likley a Socket closed exception, which we can safely ignore

      } catch (IOException e) {
        // We have no good way to report this back to the user... yet
        // TODO Report to the user that this failed in some way
      }
    }
  }
}
