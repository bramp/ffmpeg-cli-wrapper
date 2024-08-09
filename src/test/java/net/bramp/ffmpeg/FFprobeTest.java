package net.bramp.ffmpeg;

import com.google.common.collect.ImmutableList;
import net.bramp.ffmpeg.builder.FFprobeBuilder;
import net.bramp.ffmpeg.fixtures.Samples;
import net.bramp.ffmpeg.lang.NewProcessAnswer;
import net.bramp.ffmpeg.probe.*;
import net.bramp.ffmpeg.shared.CodecType;
import org.apache.commons.lang3.math.Fraction;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;

import static net.bramp.ffmpeg.FFmpegTest.argThatHasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FFprobeTest {

  @Mock
  ProcessFunction runFunc;

  @Captor
  ArgumentCaptor<List<String>> argsCaptor;

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

    when(runFunc.run(argThatHasItem(Samples.big_buck_bunny_720p_1mb_with_packets)))
        .thenAnswer(new NewProcessAnswer("ffprobe-big_buck_bunny_720p_1mb_packets.mp4"));

    when(runFunc.run(argThatHasItem(Samples.big_buck_bunny_720p_1mb_with_frames)))
        .thenAnswer(new NewProcessAnswer("ffprobe-big_buck_bunny_720p_1mb_frames.mp4"));

    when(runFunc.run(argThatHasItem(Samples.big_buck_bunny_720p_1mb_with_packets_and_frames)))
        .thenAnswer(new NewProcessAnswer("ffprobe-big_buck_bunny_720p_1mb_packets_and_frames.mp4"));

    when(runFunc.run(argThatHasItem(Samples.side_data_list)))
        .thenAnswer(new NewProcessAnswer("ffprobe-side_data_list"));

    when(runFunc.run(argThatHasItem(Samples.disposition_all_true)))
            .thenAnswer(new NewProcessAnswer("ffprobe-disposition_all_true"));

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
    assertThat(info.getStreams().get(0).codec_type, is(CodecType.VIDEO));
    assertThat(info.getStreams().get(1).codec_type, is(CodecType.AUDIO));

    assertThat(info.getStreams().get(1).channels, is(6));
    assertThat(info.getStreams().get(1).sample_rate, is(48_000));

    assertThat(info.getChapters().isEmpty(), is(true));
    // System.out.println(FFmpegUtils.getGson().toJson(info));
  }

  @Test
  public void testProbeBookWithChapters() throws IOException {
    FFmpegProbeResult info = ffprobe.probe(Samples.book_with_chapters);
    assertThat(info.hasError(), is(false));
    assertThat(info.getChapters().size(), is(24));

    FFmpegChapter firstChapter = info.getChapters().get(0);
    assertThat(firstChapter.time_base, is("1/44100"));
    assertThat(firstChapter.start, is(0L));
    assertThat(firstChapter.start_time, is("0.000000"));
    assertThat(firstChapter.end, is(11951309L));
    assertThat(firstChapter.end_time, is("271.004739"));
    assertThat(firstChapter.getTags().title, is("01 - Sammy Jay Makes a Fuss"));

    FFmpegChapter lastChapter = info.getChapters().get(info.getChapters().size() - 1);
    assertThat(lastChapter.time_base, is("1/44100"));
    assertThat(lastChapter.start, is(237875790L));
    assertThat(lastChapter.start_time, is("5394.008844"));
    assertThat(lastChapter.end, is(248628224L));
    assertThat(lastChapter.end_time, is("5637.828209"));
    assertThat(lastChapter.getTags().title, is("24 - Chatterer Has His Turn to Laugh"));
  }

  @Test
  public void testProbeWithPackets() throws IOException {
    FFmpegProbeResult info = ffprobe.probe(
        ffprobe
            .builder()
            .setInput(Samples.big_buck_bunny_720p_1mb_with_packets)
            .setShowPackets(true)
            .build());
    assertThat(info.hasError(), is(false));
    assertThat(info.getPackets().size(), is(381));

    FFmpegPacket firstPacket = info.getPackets().get(0);
    assertThat(firstPacket.codec_type, is(CodecType.AUDIO));
    assertThat(firstPacket.stream_index, is(1));
    assertThat(firstPacket.pts, is(0L));
    assertThat(firstPacket.pts_time, is(0.0));
    assertThat(firstPacket.dts, is(0L));
    assertThat(firstPacket.dts_time, is(0.0));
    assertThat(firstPacket.duration, is(1024L));
    assertThat(firstPacket.duration_time, is(0.021333F));
    assertThat(firstPacket.size, is("967"));
    assertThat(firstPacket.pos, is("4261"));
    assertThat(firstPacket.flags, is("K_"));

    FFmpegPacket secondPacket = info.getPackets().get(1);
    assertThat(secondPacket.codec_type, is(CodecType.VIDEO));
    assertThat(secondPacket.stream_index, is(0));
    assertThat(secondPacket.pts, is(0L));
    assertThat(secondPacket.pts_time, is(0.0));
    assertThat(secondPacket.dts, is(0L));
    assertThat(secondPacket.dts_time, is(0.0));
    assertThat(secondPacket.duration, is(512L));
    assertThat(secondPacket.duration_time, is(0.04F));
    assertThat(secondPacket.size, is("105222"));
    assertThat(secondPacket.pos, is("5228"));
    assertThat(secondPacket.flags, is("K_"));

    FFmpegPacket lastPacket = info.getPackets().get(info.getPackets().size() - 1);
    assertThat(lastPacket.codec_type, is(CodecType.AUDIO));
    assertThat(lastPacket.stream_index, is(1));
    assertThat(lastPacket.pts, is(253952L));
    assertThat(lastPacket.pts_time, is(5.290667));
    assertThat(lastPacket.dts, is(253952L));
    assertThat(lastPacket.dts_time, is(5.290667));
    assertThat(lastPacket.duration, is(1024L));
    assertThat(lastPacket.duration_time, is(0.021333F));
    assertThat(lastPacket.size, is("1111"));
    assertThat(lastPacket.pos, is("1054609"));
    assertThat(lastPacket.flags, is("K_"));
  }

  @Test
  public void testProbeWithFrames() throws IOException {
    FFmpegProbeResult info = ffprobe.probe(
        ffprobe
            .builder()
            .setInput(Samples.big_buck_bunny_720p_1mb_with_frames)
            .setShowFrames(true)
            .build());
    assertThat(info.hasError(), is(false));
    assertThat(info.getFrames().size(), is(381));

    FFmpegFrame firstFrame = info.getFrames().get(0);
    assertThat(firstFrame.stream_index, is(1));
    assertThat(firstFrame.key_frame, is(1));
    assertThat(firstFrame.pkt_pts, is(0L));
    assertThat(firstFrame.pkt_pts_time, is(0.0));
    assertThat(firstFrame.pkt_dts, is(0L));
    assertThat(firstFrame.pkt_dts_time, is(0.0));
    assertThat(firstFrame.best_effort_timestamp, is(0L));
    assertThat(firstFrame.best_effort_timestamp_time, is(0.0F));
    assertThat(firstFrame.pkt_duration, is(1024L));
    assertThat(firstFrame.pkt_duration_time, is(0.021333F));
    assertThat(firstFrame.pkt_pos, is(4261L));
    assertThat(firstFrame.pkt_size, is(967L));
    assertThat(firstFrame.sample_fmt, is("fltp"));
    assertThat(firstFrame.nb_samples, is(1024));
    assertThat(firstFrame.channels, is(6));
    assertThat(firstFrame.channel_layout, is("5.1"));

    FFmpegFrame secondFrame = info.getFrames().get(1);
    assertThat(secondFrame.media_type, is(CodecType.VIDEO));
    assertThat(secondFrame.stream_index, is(0));
    assertThat(secondFrame.key_frame, is(1));
    assertThat(secondFrame.pkt_pts, is(0L));
    assertThat(secondFrame.pkt_pts_time, is(0.0));
    assertThat(secondFrame.pkt_dts, is(0L));
    assertThat(secondFrame.pkt_dts_time, is(0.0));
    assertThat(secondFrame.best_effort_timestamp, is(0L));
    assertThat(secondFrame.best_effort_timestamp_time, is(0.0F));
    assertThat(secondFrame.pkt_duration, is(512L));
    assertThat(secondFrame.pkt_duration_time, is(0.04F));
    assertThat(secondFrame.pkt_pos, is(5228L));
    assertThat(secondFrame.pkt_size, is(105222L));
    assertThat(secondFrame.sample_fmt, new IsNull<>());
    assertThat(secondFrame.nb_samples, is(0));
    assertThat(secondFrame.channels, is(0));
    assertThat(secondFrame.channel_layout, new IsNull<>());

    FFmpegFrame lastFrame = info.getFrames().get(info.getFrames().size() - 1);
    assertLastFrame(lastFrame);
  }

  @Test
  public void testProbeWithPacketsAndFrames() throws IOException {
    FFmpegProbeResult info = ffprobe.probe(
        ffprobe
            .builder()
            .setInput(Samples.big_buck_bunny_720p_1mb_with_packets_and_frames)
            .setShowPackets(true)
            .setShowFrames(true)
            .build());
    assertThat(info.hasError(), is(false));
    assertThat(info.getPackets().size(), is(381));
    assertThat(info.getFrames().size(), is(381));

    FFmpegPacket firstPacket = info.getPackets().get(0);
    assertThat(firstPacket.codec_type, is(CodecType.AUDIO));
    assertThat(firstPacket.stream_index, is(1));
    assertThat(firstPacket.pts, is(0L));
    assertThat(firstPacket.pts_time, is(0.0));
    assertThat(firstPacket.dts, is(0L));
    assertThat(firstPacket.dts_time, is(0.0));
    assertThat(firstPacket.duration, is(1024L));
    assertThat(firstPacket.duration_time, is(0.021333F));
    assertThat(firstPacket.size, is("967"));
    assertThat(firstPacket.pos, is("4261"));
    assertThat(firstPacket.flags, is("K_"));

    FFmpegPacket secondPacket = info.getPackets().get(1);
    assertThat(secondPacket.codec_type, is(CodecType.VIDEO));
    assertThat(secondPacket.stream_index, is(0));
    assertThat(secondPacket.pts, is(0L));
    assertThat(secondPacket.pts_time, is(0.0));
    assertThat(secondPacket.dts, is(0L));
    assertThat(secondPacket.dts_time, is(0.0));
    assertThat(secondPacket.duration, is(512L));
    assertThat(secondPacket.duration_time, is(0.04F));
    assertThat(secondPacket.size, is("105222"));
    assertThat(secondPacket.pos, is("5228"));
    assertThat(secondPacket.flags, is("K_"));

    FFmpegPacket lastPacket = info.getPackets().get(info.getPackets().size() - 1);
    assertThat(lastPacket.codec_type, is(CodecType.AUDIO));
    assertThat(lastPacket.stream_index, is(1));
    assertThat(lastPacket.pts, is(253952L));
    assertThat(lastPacket.pts_time, is(5.290667));
    assertThat(lastPacket.dts, is(253952L));
    assertThat(lastPacket.dts_time, is(5.290667));
    assertThat(lastPacket.duration, is(1024L));
    assertThat(lastPacket.duration_time, is(0.021333F));
    assertThat(lastPacket.size, is("1111"));
    assertThat(lastPacket.pos, is("1054609"));
    assertThat(lastPacket.flags, is("K_"));

    FFmpegFrame firstFrame = info.getFrames().get(0);
    assertThat(firstFrame.stream_index, is(1));
    assertThat(firstFrame.key_frame, is(1));
    assertThat(firstFrame.pkt_pts, is(0L));
    assertThat(firstFrame.pkt_pts_time, is(0.0));
    assertThat(firstFrame.pkt_dts, is(0L));
    assertThat(firstFrame.pkt_dts_time, is(0.0));
    assertThat(firstFrame.best_effort_timestamp, is(0L));
    assertThat(firstFrame.best_effort_timestamp_time, is(0.0F));
    assertThat(firstFrame.pkt_duration, is(1024L));
    assertThat(firstFrame.pkt_duration_time, is(0.021333F));
    assertThat(firstFrame.pkt_pos, is(4261L));
    assertThat(firstFrame.pkt_size, is(967L));
    assertThat(firstFrame.sample_fmt, is("fltp"));
    assertThat(firstFrame.nb_samples, is(1024));
    assertThat(firstFrame.channels, is(6));
    assertThat(firstFrame.channel_layout, is("5.1"));

    FFmpegFrame secondFrame = info.getFrames().get(1);
    assertThat(secondFrame.media_type, is(CodecType.VIDEO));
    assertThat(secondFrame.stream_index, is(0));
    assertThat(secondFrame.key_frame, is(1));
    assertThat(secondFrame.pkt_pts, is(0L));
    assertThat(secondFrame.pkt_pts_time, is(0.0));
    assertThat(secondFrame.pkt_dts, is(0L));
    assertThat(secondFrame.pkt_dts_time, is(0.0));
    assertThat(secondFrame.best_effort_timestamp, is(0L));
    assertThat(secondFrame.best_effort_timestamp_time, is(0.0F));
    assertThat(secondFrame.pkt_duration, is(512L));
    assertThat(secondFrame.pkt_duration_time, is(0.04F));
    assertThat(secondFrame.pkt_pos, is(5228L));
    assertThat(secondFrame.pkt_size, is(105222L));
    assertThat(secondFrame.sample_fmt, new IsNull<>());
    assertThat(secondFrame.nb_samples, is(0));
    assertThat(secondFrame.channels, is(0));
    assertThat(secondFrame.channel_layout, new IsNull<>());

    FFmpegFrame lastFrame = info.getFrames().get(info.getFrames().size() - 1);
    assertLastFrame(lastFrame);
  }

  private void assertLastFrame(FFmpegFrame actual) {
    assertThat(actual.media_type, is(CodecType.AUDIO));
    assertThat(actual.stream_index, is(1));
    assertThat(actual.key_frame, is(1));
    assertThat(actual.pkt_pts, is(253952L));
    assertThat(actual.pkt_pts_time, is(5.290667));
    assertThat(actual.pkt_dts, is(253952L));
    assertThat(actual.pkt_dts_time, is(5.290667));
    assertThat(actual.best_effort_timestamp, is(253952L));
    assertThat(actual.best_effort_timestamp_time, is(5.290667F));
    assertThat(actual.pkt_duration, is(1024L));
    assertThat(actual.pkt_duration_time, is(0.021333F));
    assertThat(actual.pkt_pos, is(1054609L));
    assertThat(actual.pkt_size, is(1111L));
    assertThat(actual.sample_fmt, is("fltp"));
    assertThat(actual.nb_samples, is(1024));
    assertThat(actual.channels, is(6));
    assertThat(actual.channel_layout, is("5.1"));
  }

  @Test
  public void testProbeVideo2() throws IOException {
    FFmpegProbeResult info = ffprobe.probe(Samples.always_on_my_mind);
    assertFalse(info.hasError());

    // Only a quick sanity check until we do something better
    assertThat(info.getStreams(), hasSize(2));
    assertThat(info.getStreams().get(0).codec_type, is(CodecType.VIDEO));
    assertThat(info.getStreams().get(1).codec_type, is(CodecType.AUDIO));

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

  @Test
  public void testProbeSideDataList() throws IOException {
    FFmpegProbeResult info = ffprobe.probe(Samples.side_data_list);

    // Check edge case with a time larger than an integer
    assertThat(info.getStreams().get(0).getSideDataList().size(), is(1));
    assertThat(info.getStreams().get(0).getSideDataList().get(0).side_data_type, is("Display Matrix"));
    assertThat(
            info.getStreams().get(0).getSideDataList().get(0).displaymatrix,
        is(
            "\n00000000:            0      -65536           0\n00000001:        65536           0           0\n00000002:            0           0  1073741824\n"));
    assertThat(info.getStreams().get(0).getSideDataList().get(0).rotation, is(90));
  }

  @Test
  public void testChaptersWithLongIds() throws IOException {
    FFmpegProbeResult info = ffprobe.probe(Samples.chapters_with_long_id);

    assertThat(info.getChapters().get(0).id, is(6613449456311024506L));
    assertThat(info.getChapters().get(1).id, is(-4433436293284298339L));
  }

  @Test
  public void testProbeDefaultArguments() throws IOException {
    ffprobe.probe(Samples.always_on_my_mind);

    verify(runFunc, times(2)).run(argsCaptor.capture());

    List<String> value = Helper.subList(argsCaptor.getValue(), 1);

    assertThat(
      value,
      is(ImmutableList.of("-v", "quiet", "-print_format", "json", "-show_error", "-show_format", "-show_streams", "-show_chapters", Samples.always_on_my_mind))
    );
  }

  @Test
  public void testProbeProbeBuilder() throws IOException {
    ffprobe.probe(new FFprobeBuilder().setInput(Samples.always_on_my_mind));

    verify(runFunc, times(2)).run(argsCaptor.capture());

    List<String> value = Helper.subList(argsCaptor.getValue(), 1);

    assertThat(
            value,
            is(ImmutableList.of("-v", "quiet", "-print_format", "json", "-show_error", "-show_format", "-show_streams", "-show_chapters", Samples.always_on_my_mind))
    );
  }

  @Test
  public void testProbeProbeBuilderBuilt() throws IOException {
    ffprobe.probe(new FFprobeBuilder().setInput(Samples.always_on_my_mind).build());

    verify(runFunc, times(2)).run(argsCaptor.capture());

    List<String> value = Helper.subList(argsCaptor.getValue(), 1);

    assertThat(
            value,
            is(ImmutableList.of("-v", "quiet", "-print_format", "json", "-show_error", "-show_format", "-show_streams", "-show_chapters", Samples.always_on_my_mind))
    );
  }

  @Test
  public void testProbeProbeExtraArgs() throws IOException {
    ffprobe.probe(Samples.always_on_my_mind, null, "-rw_timeout", "0");

    verify(runFunc, times(2)).run(argsCaptor.capture());

    List<String> value = Helper.subList(argsCaptor.getValue(), 1);

    assertThat(
            value,
            is(ImmutableList.of("-v", "quiet", "-print_format", "json", "-show_error", "-rw_timeout", "0", "-show_format", "-show_streams", "-show_chapters", Samples.always_on_my_mind))
    );
  }

  @Test
  public void testProbeProbeUserAgent() throws IOException {
    ffprobe.probe(Samples.always_on_my_mind, "ffmpeg-cli-wrapper");

    verify(runFunc, times(2)).run(argsCaptor.capture());

    List<String> value = Helper.subList(argsCaptor.getValue(), 1);

    assertThat(
            value,
            is(ImmutableList.of("-v", "quiet", "-print_format", "json", "-show_error", "-user_agent", "ffmpeg-cli-wrapper", "-show_format", "-show_streams", "-show_chapters", Samples.always_on_my_mind))
    );
  }

  @Test
  public void testFullFormatDeserialization() throws IOException {
    FFmpegFormat format = ffprobe.probe(Samples.always_on_my_mind).getFormat();

    assertThat(format.getFilename(), endsWith("Always On My Mind [Program Only] - Adelén.mp4"));
    assertEquals(2, format.getNbStreams());
    assertEquals(0, format.getNbPrograms());
    assertEquals("mov,mp4,m4a,3gp,3g2,mj2", format.getFormatName());
    assertEquals("QuickTime / MOV", format.getFormatLongName());
    assertEquals(0d, format.getStartTime(), 0.01);
    assertEquals(181.632d, format.getDuration(), 0.01);
    assertEquals(417127573, format.getSize());
    assertEquals(18372426, format.getBitRate());
    assertEquals(100, format.getProbeScore());
    assertEquals(4, format.getTags().size());

    assertEquals("mp42", format.getTags().get("major_brand"));
  }

  @Test
  public void testFullChaptersDeserialization() throws IOException {
    List<FFmpegChapter> chapters = ffprobe.probe(Samples.book_with_chapters).getChapters();
    FFmpegChapter chapter = chapters.get(chapters.size() - 1);

    assertEquals(24, chapters.size());

    assertEquals(23, chapter.getId());
    assertEquals("1/44100", chapter.getTimeBase());
    assertEquals(237875790, chapter.getStart());
    assertEquals("5394.008844", chapter.getStartTime());
    assertEquals(248628224, chapter.getEnd());
    assertEquals("5637.828209", chapter.getEndTime());
    assertEquals("24 - Chatterer Has His Turn to Laugh", chapter.getTags().getTitle());
  }

  @Test
  public void testFullVideoStreamDeserialization() throws IOException {
    List<FFmpegStream> streams = ffprobe.probe(Samples.big_buck_bunny_720p_1mb).getStreams();
    FFmpegStream stream = streams.get(0);

    assertEquals(0, stream.getIndex());
    assertEquals("h264", stream.getCodecName());
    assertEquals("H.264 / AVC / MPEG-4 AVC / MPEG-4 part 10", stream.getCodecLongName());
    assertEquals("Main", stream.getProfile());
    assertEquals(CodecType.VIDEO, stream.getCodecType());
    assertEquals(Fraction.getFraction(1, 50), stream.getCodecTimeBase());
    assertEquals("avc1", stream.getCodecTagString());
    assertEquals("0x31637661", stream.getCodecTag());
    assertEquals(1280, stream.getWidth());
    assertEquals(720, stream.getHeight());
    assertEquals(0, stream.getHasBFrames());
    assertEquals("1:1", stream.getSampleAspectRatio());
    assertEquals("16:9", stream.getDisplayAspectRatio());
    assertEquals("yuv420p", stream.getPixFmt());
    assertEquals(31, stream.getLevel());
    assertEquals("left", stream.getChromaLocation());
    assertEquals(1, stream.getRefs());
    assertEquals("1", stream.getIsAvc());
    assertEquals("4", stream.getNalLengthSize());
    assertEquals(Fraction.getFraction(25, 1), stream.getRFrameRate());
    assertEquals(Fraction.getFraction(25, 1), stream.getAvgFrameRate());
    assertEquals(Fraction.getFraction(1, 12800), stream.getTimeBase());
    assertEquals(0, stream.getStartPts());
    assertEquals(0.0d, stream.getStartTime(), 0.01);
    assertEquals(67584, stream.getDurationTs());
    assertEquals(5.28d, stream.getDuration(), 0.01);
    assertEquals(1205959, stream.getBitRate());
    assertEquals(0, stream.getMaxBitRate());
    assertEquals(8, stream.getBitsPerRawSample());
    assertEquals(0, stream.getBitsPerSample());
    assertEquals(132, stream.getNbFrames());
    assertNull(stream.getSampleFmt());
    assertEquals(0, stream.getSampleRate());
    assertEquals(0, stream.getChannels());
    assertNull(stream.getChannelLayout());
    assertEquals(3, stream.getTags().size());
    assertEquals("und", stream.getTags().get("language"));
    assertEquals(0, stream.getSideDataList().size());
  }

  @Test
  public void testFullAudioStreamDeserialization() throws IOException {
    List<FFmpegStream> streams = ffprobe.probe(Samples.big_buck_bunny_720p_1mb).getStreams();
    FFmpegStream stream = streams.get(1);

    assertEquals(1, stream.getIndex());
    assertEquals("aac", stream.getCodecName());
    assertEquals("AAC (Advanced Audio Coding)", stream.getCodecLongName());
    assertEquals("LC", stream.getProfile());
    assertEquals(CodecType.AUDIO, stream.getCodecType());
    assertEquals(Fraction.getFraction(1, 48_000), stream.getCodecTimeBase());
    assertEquals("mp4a", stream.getCodecTagString());
    assertEquals("0x6134706d", stream.getCodecTag());
    assertEquals(0, stream.getWidth());
    assertEquals(0, stream.getHeight());
    assertEquals(0, stream.getHasBFrames());
    assertNull(stream.getSampleAspectRatio());
    assertNull(stream.getDisplayAspectRatio());
    assertNull(stream.getPixFmt());
    assertEquals(0, stream.getLevel());
    assertNull(stream.getChromaLocation());
    assertEquals(0, stream.getRefs());
    assertNull(stream.getIsAvc());
    assertNull(stream.getNalLengthSize());
    assertEquals(Fraction.getFraction(0, 1), stream.getRFrameRate());
    assertEquals(Fraction.getFraction(0, 1), stream.getAvgFrameRate());
    assertEquals(Fraction.getFraction(1, 48_000), stream.getTimeBase());
    assertEquals(0, stream.getStartPts());
    assertEquals(0.0d, stream.getStartTime(), 0.01);
    assertEquals(254976, stream.getDurationTs());
    assertEquals(5.312d, stream.getDuration(), 0.01);
    assertEquals(384828, stream.getBitRate());
    assertEquals(400392, stream.getMaxBitRate());
    assertEquals(0, stream.getBitsPerRawSample());
    assertEquals(0, stream.getBitsPerSample());
    assertEquals(249, stream.getNbFrames());
    assertEquals("fltp", stream.getSampleFmt());
    assertEquals(48000, stream.getSampleRate());
    assertEquals(6, stream.getChannels());
    assertEquals("5.1", stream.getChannelLayout());
    assertEquals(3, stream.getTags().size());
    assertEquals("und", stream.getTags().get("language"));
    assertEquals(0, stream.getSideDataList().size());
  }

  @Test
  public void testSideDataListDeserialization() throws IOException {
    List<FFmpegStream> streams = ffprobe.probe(Samples.side_data_list).getStreams();
    List<FFmpegStream.SideData> sideDataList = streams.get(0).getSideDataList();

    assertEquals(1, sideDataList.size());
    assertEquals("Display Matrix", sideDataList.get(0).getSideDataType());
    assertEquals(90, sideDataList.get(0).getRotation());
    assertThat(sideDataList.get(0).getDisplaymatrix(), startsWith("\n00000000:"));
  }

  @Test
  public void testDispositionDeserialization() throws IOException {
    List<FFmpegStream> streams = ffprobe.probe(Samples.side_data_list).getStreams();
    FFmpegDisposition disposition = streams.get(0).getDisposition();

    assertTrue(disposition.isDefault());
    assertFalse(disposition.isDub());
    assertFalse(disposition.isOriginal());
    assertFalse(disposition.isComment());
    assertFalse(disposition.isLyrics());
    assertFalse(disposition.isKaraoke());
    assertFalse(disposition.isForced());
    assertFalse(disposition.isHearingImpaired());
    assertFalse(disposition.isVisualImpaired());
    assertFalse(disposition.isCleanEffects());
    assertFalse(disposition.isAttachedPic());
    assertFalse(disposition.isCaptions());
    assertFalse(disposition.isDescriptions());
    assertFalse(disposition.isMetadata());
  }

  @Test
  public void testDispositionWithAllFieldsTrueDeserialization() throws IOException {
    List<FFmpegStream> streams = ffprobe.probe(Samples.disposition_all_true).getStreams();
    FFmpegDisposition disposition = streams.get(0).getDisposition();

    assertTrue(disposition.isDefault());
    assertTrue(disposition.isDub());
    assertTrue(disposition.isOriginal());
    assertTrue(disposition.isComment());
    assertTrue(disposition.isLyrics());
    assertTrue(disposition.isKaraoke());
    assertTrue(disposition.isForced());
    assertTrue(disposition.isHearingImpaired());
    assertTrue(disposition.isVisualImpaired());
    assertTrue(disposition.isCleanEffects());
    assertTrue(disposition.isAttachedPic());
    assertTrue(disposition.isCaptions());
    assertTrue(disposition.isDescriptions());
    assertTrue(disposition.isMetadata());
  }

  @Test
  public void testFullPacketDeserialization() throws IOException {
    FFprobeBuilder probeBuilder = ffprobe.builder().setShowPackets(true).setInput(Samples.big_buck_bunny_720p_1mb_with_packets);
    List<FFmpegPacket> packets = ffprobe.probe(probeBuilder).getPackets();

    FFmpegPacket packet = packets.get(packets.size() - 1);

    assertEquals(CodecType.AUDIO, packet.getCodecType());
    assertEquals(1, packet.getStreamIndex());
    assertEquals(253952, packet.getPts());
    assertEquals(5.290667, packet.getPtsTime(), 0.0001);
    assertEquals(253952, packet.getDts());
    assertEquals(5.290667, packet.getDtsTime(), 0.0001);
    assertEquals(1024, packet.getDuration());
    assertEquals(0.021333, packet.getDurationTime(), 0.0001);
    assertEquals("1111", packet.getSize());
    assertEquals("1054609", packet.getPos());
    assertEquals("K_", packet.getFlags());
  }

  @Test
  public void testFullFrameDeserialization() throws IOException {
    FFprobeBuilder probeBuilder = ffprobe.builder().setShowFrames(true).setInput(Samples.big_buck_bunny_720p_1mb_with_frames);
    List<FFmpegFrame> frames = ffprobe.probe(probeBuilder).getFrames();

    FFmpegFrame frame = frames.get(frames.size() - 1);

    assertEquals(CodecType.AUDIO, frame.getMediaType());
    assertEquals(1, frame.getStreamIndex());
    assertEquals(1, frame.getKeyFrame());
    assertEquals(253952, frame.getPktPts());
    assertEquals(5.290667, frame.getPktPtsTime(), 0.0001);
    assertEquals(253952, frame.getPktDts());
    assertEquals(5.290667, frame.getPktDtsTime(), 0.0001);
    assertEquals(253952, frame.getBestEffortTimestamp());
    assertEquals(5.290667, frame.getBestEffortTimestampTime(), 0.0001);
    assertEquals(1024, frame.getPktDuration());
    assertEquals(0.021333, frame.getPktDurationTime(), 0.0001);
    assertEquals(1054609, frame.getPktPos());
    assertEquals(1111, frame.getPktSize());
    assertEquals("fltp", frame.getSampleFmt());
    assertEquals(1024, frame.getNbSamples());
    assertEquals(6, frame.getChannels());
    assertEquals("5.1", frame.getChannelLayout());
  }
}
