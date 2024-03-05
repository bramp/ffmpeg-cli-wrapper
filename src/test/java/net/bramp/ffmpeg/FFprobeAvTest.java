package net.bramp.ffmpeg;

import static net.bramp.ffmpeg.FFmpegTest.argThatHasItem;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import java.io.IOException;
import net.bramp.ffmpeg.fixtures.Samples;
import net.bramp.ffmpeg.lang.NewProcessAnswer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/** Tests what happens when using avprobe */
@RunWith(MockitoJUnitRunner.class)
public class FFprobeAvTest {

  @Mock ProcessFunction runFunc;

  FFprobe ffprobe;

  static final Gson gson = FFmpegUtils.getGson();

  @Before
  public void before() throws IOException {
    when(runFunc.run(argThatHasItem("-version")))
        .thenAnswer(new NewProcessAnswer("avprobe-version"));

    ffprobe = new FFprobe(runFunc);
  }

  @Test
  public void testVersion() throws Exception {
    assertEquals(
        "avprobe version 11.4, Copyright (c) 2007-2014 the Libav developers", ffprobe.version());
    assertEquals(
        "avprobe version 11.4, Copyright (c) 2007-2014 the Libav developers", ffprobe.version());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testProbeVideo() throws IOException {
    ffprobe.probe(Samples.big_buck_bunny_720p_1mb);
  }
}
