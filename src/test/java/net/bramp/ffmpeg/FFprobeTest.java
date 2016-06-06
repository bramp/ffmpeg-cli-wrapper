package net.bramp.ffmpeg;

import com.google.gson.Gson;
import net.bramp.ffmpeg.fixtures.Samples;
import net.bramp.ffmpeg.lang.NewProcessAnswer;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;
import org.apache.commons.lang3.math.Fraction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static net.bramp.ffmpeg.FFmpegTest.argThatHasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
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

    when(runFunc.run(argThatHasItem(Samples.always_on_my_mind))).thenAnswer(
        new NewProcessAnswer("ffprobe-Always On My Mind [Program Only] - Adelén.mp4"));

    when(runFunc.run(argThatHasItem(Samples.divide_by_zero))).thenAnswer(
        new NewProcessAnswer("ffprobe-divide-by-zero"));

    ffprobe = new FFprobe(runFunc);
  }

  @Test
  public void testProbeVideo() throws IOException {
    FFmpegProbeResult info = ffprobe.probe(Samples.big_buck_bunny_720p_1mb);
    assertFalse(info.hasError());

    // Only a quick sanity check until we do something better
    assertThat(info.getStreams(), hasSize(2));
    assertThat(info.getStreams().get(0).codec_type, is(FFmpegStream.CodecType.VIDEO));
    assertThat(info.getStreams().get(1).codec_type, is(FFmpegStream.CodecType.AUDIO));

    assertThat(info.getStreams().get(1).channels, is(6));
    assertThat(info.getStreams().get(1).sample_rate, is(48_000));

    // System.out.println(FFmpegUtils.getGson().toJson(info));
  }

  @Test
  public void testProbeVideo2() throws IOException {
    FFmpegProbeResult info = ffprobe.probe(Samples.always_on_my_mind);
    assertFalse(info.hasError());

    // Only a quick sanity check until we do something better
    assertThat(info.getStreams(), hasSize(2));
    assertThat(info.getStreams().get(0).codec_type, is(FFmpegStream.CodecType.VIDEO));
    assertThat(info.getStreams().get(1).codec_type, is(FFmpegStream.CodecType.AUDIO));

    assertThat(info.getStreams().get(1).channels, is(2));
    assertThat(info.getStreams().get(1).sample_rate, is(48_000));

    // Test a UTF-8 name
    assertThat(info.getFormat().filename,
        is("c:\\Users\\Bob\\Always On My Mind [Program Only] - Adelén.mp4"));

    // System.out.println(FFmpegUtils.getGson().toJson(info));
  }

  @Test
  public void testProbeDivideByZero() throws IOException {
    // https://github.com/bramp/ffmpeg-cli-wrapper/issues/10
    FFmpegProbeResult info = ffprobe.probe(Samples.divide_by_zero);
    assertFalse(info.hasError());

    assertThat(info.getStreams().get(1).codec_time_base, is(Fraction.ZERO));

    // System.out.println(FFmpegUtils.getGson().toJson(info));
  }
}
