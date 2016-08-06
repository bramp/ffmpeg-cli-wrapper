package net.bramp.ffmpeg;

import com.google.gson.Gson;
import net.bramp.ffmpeg.lang.NewProcessAnswer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Collections;

import static net.bramp.ffmpeg.FFmpegTest.argThatHasItem;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Tests what happens when using avconv
 */
@RunWith(MockitoJUnitRunner.class)
public class FFmpegAvTest {

  @Mock
  ProcessFunction runFunc;

  FFmpeg ffmpeg;

  final static Gson gson = FFmpegUtils.getGson();

  @Before
  public void before() throws IOException {
    when(runFunc.run(argThatHasItem("-version")))
        .thenAnswer(new NewProcessAnswer("avconv-version"));

    ffmpeg = new FFmpeg(runFunc);
  }

  @Test
  public void testVersion() throws Exception {
    assertEquals("avconv version 11.4, Copyright (c) 2000-2014 the Libav developers",
        ffmpeg.version());
    assertEquals("avconv version 11.4, Copyright (c) 2000-2014 the Libav developers",
        ffmpeg.version());
  }

  /**
   * We don't support avconv, so all methods should throw an exception.
   * 
   * @throws IOException
   */
  @Test(expected = IllegalArgumentException.class)
  public void testProbeVideo() throws IOException {
    ffmpeg.run(Collections.<String>emptyList());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCodecs() throws IOException {
    ffmpeg.codecs();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFormats() throws IOException {
    ffmpeg.formats();
  }
}
