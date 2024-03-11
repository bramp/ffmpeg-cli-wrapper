package net.bramp.ffmpeg.progress;

import static net.bramp.ffmpeg.Helper.loadResource;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import com.google.common.io.ByteStreams;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.util.List;
import net.bramp.ffmpeg.fixtures.Progresses;
import org.junit.Test;

public class UdpProgressParserTest extends AbstractProgressParserTest {

  @Override
  public ProgressParser newParser(ProgressListener listener)
      throws IOException, URISyntaxException {
    return new UdpProgressParser(listener);
  }

  @Test
  public void testNormal() throws IOException, InterruptedException {
    parser.start();

    final InetAddress addr = InetAddress.getByName(uri.getHost());
    final int port = uri.getPort();

    try (DatagramSocket socket = new DatagramSocket()) {
      // Load each Progress Fixture, and send in a single datagram packet
      for (String progressFixture : Progresses.allFiles) {
        InputStream inputStream = loadResource(progressFixture);
        byte[] bytes = ByteStreams.toByteArray(inputStream);

        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, addr, port);
        socket.send(packet);
      }
    }

    Thread.sleep(1000); // HACK: Wait a short while to avoid closing the receiving socket

    parser.stop();

    assertThat(progesses, equalTo(Progresses.allProgresses));
  }

  @Test
  public void testNaProgressPackets() throws IOException, InterruptedException, URISyntaxException {
    parser.start();

    final InetAddress addr = InetAddress.getByName(uri.getHost());
    final int port = uri.getPort();

    try (DatagramSocket socket = new DatagramSocket()) {
      // Load each Progress Fixture, and send in a single datagram packet
      for (String progressFixture : Progresses.naProgressFile) {
        InputStream inputStream = loadResource(progressFixture);
        byte[] bytes = ByteStreams.toByteArray(inputStream);

        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, addr, port);
        socket.send(packet);
      }
    }

    Thread.sleep(100); // HACK: Wait a short while to avoid closing the receiving socket

    parser.stop();

    assertThat(progesses, equalTo(Progresses.naProgresses));
  }
}
