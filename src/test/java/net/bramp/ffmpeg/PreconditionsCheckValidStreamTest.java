package net.bramp.ffmpeg;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class PreconditionsCheckValidStreamTest {

  @Parameters(name = "{0}")
  public static List<String> data() {
    return Arrays.asList(
        "udp://10.1.0.102:1234",
        "tcp://127.0.0.1:2000",
        "udp://236.0.0.1:2000",
        "rtmp://live.twitch.tv/app/live_",
        "rtmp:///live/myStream.sdp",
        "rtp://127.0.0.1:1234",
        "rtsp://localhost:8888/live.sdp",
        "rtsp://localhost:8888/live.sdp?tcp",

        // Some others
        "UDP://10.1.0.102:1234");
  }

  private final URI uri;

  public PreconditionsCheckValidStreamTest(String url) {
    this.uri = URI.create(url);
  }

  @Test
  public void testUri() {
    Preconditions.checkValidStream(uri);
  }
}
