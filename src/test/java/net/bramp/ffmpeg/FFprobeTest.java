package net.bramp.ffmpeg;

import static net.bramp.ffmpeg.FFmpegTest.argThatHasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import net.bramp.ffmpeg.fixtures.Samples;
import net.bramp.ffmpeg.lang.NewProcessAnswer;
import net.bramp.ffmpeg.probe.FFmpegChapter;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.shared.CodecType;
import org.apache.commons.lang3.math.Fraction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FFprobeTest {

  @Mock ProcessFunction runFunc;

  FFprobe ffprobe;

  @Before
  public void before() throws IOException {
    when(runFunc.run(argThatHasItem("-version")))
        .thenAnswer(new NewProcessAnswer("ffprobe-version"));

    when(runFunc.run(argThatHasItem(Samples.big_buck_bunny_720p_1mb)))
        .thenAnswer(new NewProcessAnswer("ffprobe-big_buck_bunny_720p_1mb.mp4"));

    when(runFunc.run(argThatHasItem(Samples.always_on_my_mind)))
        .thenAnswer(new NewProcessAnswer("ffprobe-Always On My Mind [Program Only] - Adelen.mp4"));

    when(runFunc.run(argThatHasItem(Samples.start_pts_test)))
        .thenAnswer(new NewProcessAnswer("ffprobe-start_pts_test"));

    when(runFunc.run(argThatHasItem(Samples.divide_by_zero)))
        .thenAnswer(new NewProcessAnswer("ffprobe-divide-by-zero"));

    when(runFunc.run(argThatHasItem(Samples.book_with_chapters)))
        .thenAnswer(new NewProcessAnswer("book_with_chapters.m4b"));

    when(runFunc.run(argThatHasItem(Samples.side_data_list)))
        .thenAnswer(new NewProcessAnswer("ffprobe-side_data_list"));

    when(runFunc.run(argThatHasItem(Samples.chapters_with_long_id)))
        .thenAnswer(new NewProcessAnswer("chapters_with_long_id.m4b"));

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
    assertThat(info.getStreams().get(0).getCodecType(), is(CodecType.VIDEO));
    assertThat(info.getStreams().get(1).getCodecType(), is(CodecType.AUDIO));

    assertThat(info.getStreams().get(1).getChannels(), is(6));
    assertThat(info.getStreams().get(1).getSampleRate(), is(48_000));

    assertThat(info.getChapters().isEmpty(), is(true));
    // System.out.println(FFmpegUtils.getGson().toJson(info));
  }

  @Test
  public void testProbeBookWithChapters() throws IOException {
    FFmpegProbeResult info = ffprobe.probe(Samples.book_with_chapters);
    assertThat(info.hasError(), is(false));
    assertThat(info.getChapters().size(), is(24));

    FFmpegChapter firstChapter = info.getChapters().get(0);
    assertThat(firstChapter.getTimeBase(), is("1/44100"));
    assertThat(firstChapter.getStart(), is(0L));
    assertThat(firstChapter.getStartTime(), is("0.000000"));
    assertThat(firstChapter.getEnd(), is(11951309L));
    assertThat(firstChapter.getEndTime(), is("271.004739"));
    assertThat(firstChapter.getTags().getTitle(), is("01 - Sammy Jay Makes a Fuss"));

    FFmpegChapter lastChapter = info.getChapters().get(info.getChapters().size() - 1);
    assertThat(lastChapter.getTimeBase(), is("1/44100"));
    assertThat(lastChapter.getStart(), is(237875790L));
    assertThat(lastChapter.getStartTime(), is("5394.008844"));
    assertThat(lastChapter.getEnd(), is(248628224L));
    assertThat(lastChapter.getEndTime(), is("5637.828209"));
    assertThat(lastChapter.getTags().getTitle(), is("24 - Chatterer Has His Turn to Laugh"));
  }

  @Test
  public void testProbeVideo2() throws IOException {
    FFmpegProbeResult info = ffprobe.probe(Samples.always_on_my_mind);
    assertFalse(info.hasError());

    // Only a quick sanity check until we do something better
    assertThat(info.getStreams(), hasSize(2));
    assertThat(info.getStreams().get(0).getCodecType(), is(CodecType.VIDEO));
    assertThat(info.getStreams().get(1).getCodecType(), is(CodecType.AUDIO));

    assertThat(info.getStreams().get(1).getChannels(), is(2));
    assertThat(info.getStreams().get(1).getSampleRate(), is(48_000));

    // Test a UTF-8 name
    assertThat(
        info.getFormat().getFilename(),
        is("c:\\Users\\Bob\\Always On My Mind [Program Only] - Adelén.mp4"));

    // System.out.println(FFmpegUtils.getGson().toJson(info));
  }

  @Test
  public void testProbeStartPts() throws IOException {
    FFmpegProbeResult info = ffprobe.probe(Samples.start_pts_test);
    assertFalse(info.hasError());

    // Check edge case with a time larger than an integer
    assertThat(info.getStreams().get(0).getStartPts(), is(8570867078L));
  }

  @Test
  public void testProbeDivideByZero() throws IOException {
    // https://github.com/bramp/ffmpeg-cli-wrapper/issues/10
    FFmpegProbeResult info = ffprobe.probe(Samples.divide_by_zero);
    assertFalse(info.hasError());

    assertThat(info.getStreams().get(1).getCodecTimeBase(), is(Fraction.ZERO));

    // System.out.println(FFmpegUtils.getGson().toJson(info));
  }

  @Test
  public void testProbeSideDataList() throws IOException {
    FFmpegProbeResult info = ffprobe.probe(Samples.side_data_list);

    // Check edge case with a time larger than an integer
    assertThat(info.getStreams().get(0).getSideDataList().size(), is(1));
    assertThat(info.getStreams().get(0).getSideDataList().get(0).getSideDataType(), is("Display Matrix"));
    assertThat(
            info.getStreams().get(0).getSideDataList().get(0).getDisplaymatrix(),
        is(
            "\n00000000:            0      -65536           0\n00000001:        65536           0           0\n00000002:            0           0  1073741824\n"));
    assertThat(info.getStreams().get(0).getSideDataList().get(0).getRotation(), is(90));
  }

  @Test
  public void testChaptersWithLongIds() throws IOException {
    FFmpegProbeResult info = ffprobe.probe(Samples.chapters_with_long_id);

    assertThat(info.getChapters().get(0).getId(), is(6613449456311024506L));
    assertThat(info.getChapters().get(1).getId(), is(-4433436293284298339L));
  }
}
