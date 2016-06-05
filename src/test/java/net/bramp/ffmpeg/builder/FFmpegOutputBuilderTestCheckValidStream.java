package net.bramp.ffmpeg.builder;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;

public class FFmpegOutputBuilderTestCheckValidStream {

  // @formatter:off
  static final List<String> goodURIs = ImmutableList.of(
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
  );

  static final List<String> badSchemes = ImmutableList.of(
      "http://www.example.com/",
      "https://live.twitch.tv/app/live_",
      "ftp://236.0.0.1:2000"
  );

  static final List<String> missingPort = ImmutableList.of(
      "udp://10.1.0.102/",
      "tcp://127.0.0.1/"
  );
  // @formatter:on

  protected void testForNoException(final List<String> list) throws URISyntaxException {
    for (String url : list) {
      URI u = new URI(url);
      try {
        FFmpegOutputBuilder.checkValidStream(u);

      } catch (IllegalArgumentException e) {
        fail("Unexpected error while handling '" + url + "': " + e.toString());
      }
    }
  }

  protected void testForException(final List<String> list) throws URISyntaxException {
    for (String url : badSchemes) {
      URI u = new URI(url);
      try {
        FFmpegOutputBuilder.checkValidStream(u);

      } catch (IllegalArgumentException e) {
        continue;
      }

      fail("Expected error but none thrown for '" + url + "'");
    }
  }

  @Test
  public void testGoodURLs() throws URISyntaxException {
    testForNoException(goodURIs);
  }

  @Test
  public void testBadSchemes() throws URISyntaxException {
    testForException(badSchemes);
  }

  @Test
  public void testMissingPort() throws URISyntaxException {
    testForException(missingPort);
  }
}
