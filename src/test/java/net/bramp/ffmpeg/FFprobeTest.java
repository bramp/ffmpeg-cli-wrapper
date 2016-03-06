package net.bramp.ffmpeg;

import com.google.gson.Gson;
import net.bramp.ffmpeg.fixtures.Samples;
import net.bramp.ffmpeg.lang.NewProcessAnswer;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static net.bramp.ffmpeg.FFmpegTest.argThatHasItem;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FFprobeTest {

  @Mock
  ProcessFunction runFunc;

  FFprobe ffprobe;

  final static Gson gson = FFmpegUtils.getGson();

  @Before
  public void before() throws IOException {
    when(runFunc.run(argThatHasItem(Samples.big_buck_bunny_720p_1mb))).thenAnswer(
        new NewProcessAnswer("ffprobe-big_buck_bunny_720p_1mb.mp4"));
    ffprobe = new FFprobe();
  }

  @Test
  public void testProbe() throws IOException {
    FFmpegProbeResult info = ffprobe.probe(Samples.big_buck_bunny_720p_1mb);
    assertFalse(info.hasError());
    System.out.println(FFmpegUtils.getGson().toJson(info));
  }

}
