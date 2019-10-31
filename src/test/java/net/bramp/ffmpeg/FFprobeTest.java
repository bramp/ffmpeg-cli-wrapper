package net.bramp.ffmpeg;

import com.google.gson.Gson;
import net.bramp.ffmpeg.fixtures.Samples;
import net.bramp.ffmpeg.lang.NewProcessAnswer;
import net.bramp.ffmpeg.probe.FFmpegAudioFrame;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;
import net.bramp.ffmpeg.probe.FFmpegVideoFrame;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.math.Fraction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;

import static net.bramp.ffmpeg.FFmpegTest.argThatHasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FFprobeTest {

  @Mock ProcessFunction runFunc;

  FFprobe ffprobe;

  static final Gson gson = FFmpegUtils.getGson();

  @Before
  public void before() throws IOException {
    when(runFunc.run(argThatHasItem("-version")))
        .thenAnswer(new NewProcessAnswer("ffprobe-version"));

    when(runFunc.run(argThatHasItem(Samples.big_buck_bunny_720p_1mb)))
        .thenAnswer(new NewProcessAnswer("ffprobe-big_buck_bunny_720p_1mb.mp4"));

    when(runFunc.run(argThatHasItem(Samples.always_on_my_mind)))
        .thenAnswer(new NewProcessAnswer("ffprobe-Always On My Mind [Program Only] - Adelén.mp4"));

    when(runFunc.run(argThatHasItem(Samples.start_pts_test)))
        .thenAnswer(new NewProcessAnswer("ffprobe-start_pts_test"));

    when(runFunc.run(argThatHasItem(Samples.divide_by_zero)))
        .thenAnswer(new NewProcessAnswer("ffprobe-divide-by-zero"));

    ffprobe = new FFprobe(runFunc);
  }

  @Test
  public void testVersion() throws Exception {
    assertEquals(
        "ffprobe version 3.0.2 Copyright (c) 2007-2016 the FFmpeg developers", ffprobe.version());
    assertEquals(
        "ffprobe version 3.0.2 Copyright (c) 2007-2016 the FFmpeg developers", ffprobe.version());

    verify(runFunc, times(1)).run(argThatHasItem("-version"));
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

    assertThat(info.getFrames(), hasSize(381));
    assertThat(info.getFrames().get(0), instanceOf(FFmpegAudioFrame.class));
    assertThat(info.getFrames().get(1), instanceOf(FFmpegVideoFrame.class));

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
    assertThat(
        info.getFormat().filename,
        is("c:\\Users\\Bob\\Always On My Mind [Program Only] - Adelén.mp4"));

    // System.out.println(FFmpegUtils.getGson().toJson(info));
  }

  @Test
  public void testProbeStartPts() throws IOException {
    FFmpegProbeResult info = ffprobe.probe(Samples.start_pts_test);
    assertFalse(info.hasError());

    // Check edge case with a time larger than an integer
    assertThat(info.getStreams().get(0).start_pts, is(8570867078L));
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
