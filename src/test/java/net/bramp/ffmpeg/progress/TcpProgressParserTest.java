package net.bramp.ffmpeg.progress;

import net.bramp.ffmpeg.fixtures.Progresses;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

import static net.bramp.ffmpeg.Helper.combineResource;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class TcpProgressParserTest extends AbstractProgressParserTest {

  @Override
  public ProgressParser newParser(ProgressListener listener) throws IOException, URISyntaxException {
    return new TcpProgressParser(listener);
  }

  @Test
  public void testNormal() throws IOException, InterruptedException, URISyntaxException {
    parser.start();

    Socket client = new Socket(uri.getHost(), uri.getPort());
    assertTrue("Socket is connected", client.isConnected());

    InputStream inputStream = combineResource(Progresses.allFiles);
    OutputStream outputStream = client.getOutputStream();

    int bytes = IOUtils.copy(inputStream, outputStream);

    client.close();

    parser.stop();

    assertThat(bytes, greaterThan(0));
    assertThat(progesses, equalTo(Progresses.allProgresses));
  }

  @Test
  public void testPrematureDisconnect() throws IOException, InterruptedException,
      URISyntaxException {
    parser.start();
    new Socket(uri.getHost(), uri.getPort()).close();
    parser.stop();

    assertTrue(progesses.isEmpty());
  }
}
