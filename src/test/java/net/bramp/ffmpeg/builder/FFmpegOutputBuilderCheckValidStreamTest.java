package net.bramp.ffmpeg.builder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class FFmpegOutputBuilderCheckValidStreamTest {

  @Parameters(name = "{0}")
  public static List<String> data() {
    return Arrays.asList(
        // @formatter:off
        "udp://10.1.0.102:1234",
        "tcp://127.0.0.1:2000",
        "udp://236.0.0.1:2000",
        "rtmp://live.twitch.tv/app/live_",
        "rtmp:///live/myStream.sdp",
        "rtp://127.0.0.1:1234",
        "rtsp://localhost:8888/live.sdp",
        "rtsp://localhost:8888/live.sdp?tcp",

        // Some others
        "UDP://10.1.0.102:1234"
        // @formatter:on
        );
  }

  private final URI uri;

  public FFmpegOutputBuilderCheckValidStreamTest(String url) {
    this.uri = URI.create(url);
  }

  @Test
  public void testUri() {
    FFmpegOutputBuilder.checkValidStream(uri);
  }
}
