package net.bramp.ffmpeg.builder;

import com.google.common.collect.ImmutableList;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.ProcessFunction;
import net.bramp.ffmpeg.builder.FFmpegBuilder.Verbosity;
import net.bramp.ffmpeg.fixtures.Samples;
import net.bramp.ffmpeg.lang.NewProcessAnswer;
import net.bramp.ffmpeg.options.AudioEncodingOptions;
import net.bramp.ffmpeg.options.EncodingOptions;
import net.bramp.ffmpeg.options.MainEncodingOptions;
import net.bramp.ffmpeg.options.VideoEncodingOptions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.nitorcreations.Matchers.reflectEquals;
import static net.bramp.ffmpeg.FFmpeg.*;
import static net.bramp.ffmpeg.FFmpegTest.argThatHasItem;
import static net.bramp.ffmpeg.builder.MetadataSpecifier.*;
import static net.bramp.ffmpeg.builder.StreamSpecifier.tag;
import static net.bramp.ffmpeg.builder.StreamSpecifier.usable;
import static net.bramp.ffmpeg.builder.StreamSpecifierType.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SuppressWarnings("unused")
@RunWith(MockitoJUnitRunner.class)
public class FFmpegBuilderTest {
  @Mock
  protected ProcessFunction runFunc;
  protected FFprobe ffprobe;

  @Before
  public void before() throws IOException {
    when(runFunc.run(argThatHasItem("-version")))
            .thenAnswer(new NewProcessAnswer("ffprobe-version"));
    when(runFunc.run(argThatHasItem(Samples.big_buck_bunny_720p_1mb)))
            .thenAnswer(new NewProcessAnswer("ffprobe-big_buck_bunny_720p_1mb.mp4"));

    ffprobe = new FFprobe(runFunc);
  }

  @Test
  public void testNormal() {

    List<String> args =
        new FFmpegBuilder()
            .setVerbosity(Verbosity.DEBUG)
            .setUserAgent(
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36")
            .setInput("input")
            .setStartOffset(1500, TimeUnit.MILLISECONDS)
            .overrideOutputFiles(true)
            .addOutput("output")
            .setFormat("mp4")
            .setStartOffset(500, TimeUnit.MILLISECONDS)
            .setAudioCodec("aac")
            .setAudioChannels(1)
            .setAudioSampleRate(48000)
            .setAudioBitStreamFilter("bar")
            .setAudioQuality(1)
            .setVideoCodec("libx264")
            .setVideoFrameRate(FPS_30)
            .setVideoResolution(320, 240)
            .setVideoBitStreamFilter("foo")
            .setVideoQuality(2)
            .done()
            .build();

    assertEquals(
        args,
        ImmutableList.of(
            "-y",
            "-v",
            "debug",
            "-user_agent",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36",
            "-ss",
            "00:00:01.5",
            "-i",
            "input",
            "-f",
            "mp4",
            "-ss",
            "00:00:00.5",
            "-vcodec",
            "libx264",
            "-s",
            "320x240",
            "-r",
            "30/1",
            "-qscale:v",
            "2",
            "-bsf:v",
            "foo",
            "-acodec",
            "aac",
            "-ac",
            "1",
            "-ar",
            "48000",
            "-qscale:a",
            "1",
            "-bsf:a",
            "bar",
            "output"));
  }

  @Test
  public void testDisabled() {

    List<String> args =
        new FFmpegBuilder()
            .setInput("input")
            .addOutput("output")
            .disableAudio()
            .disableSubtitle()
            .disableVideo()
            .done()
            .build();

    assertEquals(
        args, ImmutableList.of("-y", "-v", "error", "-i", "input", "-vn", "-an", "-sn", "output"));
  }

  @Test
  public void testFilter() {

    List<String> args =
        new FFmpegBuilder()
            .setInput("input")
            .addOutput("output")
            .disableAudio()
            .disableSubtitle()
            .setVideoFilter("scale='trunc(ow/a/2)*2:320'")
            .done()
            .build();

    assertEquals(
        args,
        ImmutableList.of(
            "-y",
            "-v",
            "error",
            "-i",
            "input",
            "-vf",
            "scale='trunc(ow/a/2)*2:320'",
            "-an",
            "-sn",
            "output"));
  }

  @Test
  public void testFilterAndScale() {

    List<String> args =
        new FFmpegBuilder()
            .setInput("input")
            .addOutput("output")
            .setVideoResolution(320, 240)
            .setVideoFilter("scale='trunc(ow/a/2)*2:320'")
            .done()
            .build();

    assertEquals(
        args,
        ImmutableList.of(
            "-y",
            "-v",
            "error",
            "-i",
            "input",
            "-s",
            "320x240",
            "-vf",
            "scale='trunc(ow/a/2)*2:320'",
            "output"));
  }

  /** Tests if all the various encoding options actually get stored and used correctly */
  @Test
  public void testSetOptions() {
    MainEncodingOptions main = new MainEncodingOptions("mp4", 1500L, 2L);
    AudioEncodingOptions audio =
        new AudioEncodingOptions(true, "aac", 1, AUDIO_SAMPLE_48000, AUDIO_FORMAT_S16, 1, 2.0);
    VideoEncodingOptions video =
        new VideoEncodingOptions(true, "libx264", FPS_30, 320, 240, 1, null, null, null);

    EncodingOptions options =
        new FFmpegBuilder()
            .setInput("input")
            .addOutput("output")
            .useOptions(main)
            .useOptions(audio)
            .useOptions(video)
            .buildOptions();

    assertThat(main, reflectEquals(options.getMain()));
    assertThat(audio, reflectEquals(options.getAudio()));
    assertThat(video, reflectEquals(options.getVideo()));
  }

  @Test
  public void testMultipleOutputs() {

    List<String> args =
        new FFmpegBuilder()
            .setInput("input")
            .addOutput("output1")
            .setVideoResolution(320, 240)
            .done()
            .addOutput("output2")
            .setVideoResolution(640, 480)
            .done()
            .addOutput("output3")
            .setVideoResolution("ntsc")
            .done()
            .build();

    assertEquals(
        args,
        ImmutableList.of(
            "-y", "-v", "error", "-i", "input", "-s", "320x240", "output1", "-s", "640x480",
            "output2", "-s", "ntsc", "output3"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConflictingVideoSize() {
    List<String> unused =
        new FFmpegBuilder()
            .setInput("input")
            .addOutput("output")
            .setVideoResolution(320, 240)
            .setVideoResolution("ntsc")
            .done()
            .build();
  }

  @Test
  public void testURIOutput() {
    List<String> args =
        new FFmpegBuilder()
            .setInput("input")
            .addOutput(URI.create("udp://10.1.0.102:1234"))
            .setVideoResolution(320, 240)
            .done()
            .build();

    assertEquals(
        args,
        ImmutableList.of(
            "-y", "-v", "error", "-i", "input", "-s", "320x240", "udp://10.1.0.102:1234"));
  }

  @Test(expected = IllegalStateException.class)
  public void testURIAndFilenameOutput() {
    List<String> unused =
        new FFmpegBuilder()
            .setInput("input")
            .addOutput(URI.create("udp://10.1.0.102:1234"))
            .setFilename("filename")
            .done()
            .build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddEmptyFilename() {
    List<String> unused = new FFmpegBuilder().setInput("input").addOutput("").done().build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetEmptyFilename() {
    List<String> unused =
        new FFmpegBuilder().setInput("input").addOutput("output").setFilename("").done().build();
  }

  @Test
  public void testMetaTags() {

    List<String> args =
        new FFmpegBuilder()
            .setInput("input")
            .addOutput("output")
            .addMetaTag("comment", "My Comment")
            .addMetaTag("title", "\"Video\"")
            .addMetaTag("author", "a=b:c")
            .done()
            .build();

    assertEquals(
        args,
        ImmutableList.of(
            "-y",
            "-v",
            "error",
            "-i",
            "input",
            "-metadata",
            "comment=My Comment",
            "-metadata",
            "title=\"Video\"",
            "-metadata",
            "author=a=b:c",
            "output"));
  }

  @Test
  public void testMetaTagsWithSpecifier() {

    List<String> args =
        new FFmpegBuilder()
            .setInput("input")
            .addOutput("output")
            .addMetaTag("title", "Movie Title")
            .addMetaTag(chapter(0), "author", "Bob")
            .addMetaTag(program(0), "comment", "Awesome")
            .addMetaTag(stream(0), "copyright", "Megacorp")
            .addMetaTag(stream(Video), "framerate", "24fps")
            .addMetaTag(stream(Video, 0), "artist", "Joe")
            .addMetaTag(stream(Audio, 0), "language", "eng")
            .addMetaTag(stream(Subtitle, 0), "language", "fre")
            .addMetaTag(stream(usable()), "year", "2010")
            .addMetaTag(stream(tag("key")), "a", "b")
            .addMetaTag(stream(tag("key", "value")), "a", "b")
            .done()
            .build();

    assertEquals(
        args,
        ImmutableList.of(
            "-y",
            "-v",
            "error",
            "-i",
            "input",
            "-metadata",
            "title=Movie Title",
            "-metadata:c:0",
            "author=Bob",
            "-metadata:p:0",
            "comment=Awesome",
            "-metadata:s:0",
            "copyright=Megacorp",
            "-metadata:s:v",
            "framerate=24fps",
            "-metadata:s:v:0",
            "artist=Joe",
            "-metadata:s:a:0",
            "language=eng",
            "-metadata:s:s:0",
            "language=fre",
            "-metadata:s:u",
            "year=2010",
            "-metadata:s:m:key",
            "a=b",
            "-metadata:s:m:key:value",
            "a=b",
            "output"));
  }

  @Test
  public void testExtraArgs() {
    List<String> args =
        new FFmpegBuilder()
            .addExtraArgs("-a", "b")
            .setInput("input")
            .addOutput("output")
            .addExtraArgs("-c", "d")
            .disableAudio()
            .disableSubtitle()
            .done()
            .build();

    assertEquals(
        args,
        ImmutableList.of(
            "-y", "-v", "error", "-a", "b", "-i", "input", "-an", "-sn", "-c", "d", "output"));
  }

  @Test
  public void testVbr() {
    List<String> args =
        new FFmpegBuilder()
            .setInput("input")
            .setVBR(2)
            .addOutput("output")
            .done()
            .build();

    assertEquals(
            args,
            ImmutableList.of(
                    "-y", "-v", "error", "-i", "input", "-qscale:a", "2", "output"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testVbrNegativeParam() {
    List<String> args =
        new FFmpegBuilder()
            .setInput("input")
            .setVBR(-3)
            .addOutput("output")
            .done()
            .build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testVbrQualityExceedsRange() {
    List<String> args =
        new FFmpegBuilder()
            .setInput("input")
            .setVBR(10)
            .addOutput("output")
            .done()
            .build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNothing() {
    List<String> unused = new FFmpegBuilder().build();
  }

  @Test
  public void testMultipleInput() {
    List<String> args =
        new FFmpegBuilder()
            .addInput("input1")
            .addInput("input2")
            .addOutput("output")
            .done()
            .build();

    assertEquals(
        args, ImmutableList.of("-y", "-v", "error", "-i", "input1", "-i", "input2", "output"));
  }

  @Test
  public void testAlternativeBuilderPattern() {
    List<String> args =
        new FFmpegBuilder()
            .addInput("input")
            .addOutput(new FFmpegOutputBuilder().setFilename("output.mp4").setVideoCodec("libx264"))
            .addOutput(new FFmpegOutputBuilder().setFilename("output.flv").setVideoCodec("flv"))
            .build();

    assertEquals(
        args,
        ImmutableList.of(
            "-y",
            "-v",
            "error",
            "-i",
            "input",
            "-vcodec",
            "libx264",
            "output.mp4",
            "-vcodec",
            "flv",
            "output.flv"));
  }

  @Test
  public void testPresets() {
    List<String> args =
        new FFmpegBuilder()
            .addInput("input")
            .addOutput("output")
            .setPreset("a")
            .setPresetFilename("b")
            .setVideoPreset("c")
            .setAudioPreset("d")
            .setSubtitlePreset("e")
            .done()
            .build();

    assertEquals(
        args,
        ImmutableList.of(
            "-y", "-v", "error", "-i", "input", "-preset", "a", "-fpre", "b", "-vpre", "c", "-apre",
            "d", "-spre", "e", "output"));
    }

  @Test
  public void testThreads() {
      List<String> args =
          new FFmpegBuilder()
              .setThreads(2)
              .addInput("input")
              .addOutput("output")
              .done()
              .build();

      assertEquals(
          args,
          ImmutableList.of("-y", "-v", "error", "-threads", "2", "-i", "input", "output"));
    }

  @Test(expected = IllegalArgumentException.class)
  public void testZeroThreads() {
    new FFmpegBuilder().setThreads(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeNumberOfThreads() {
    new FFmpegBuilder().setThreads(-1);
  }

  @Test
  public void testOverrideFlagPresent() {
    assertEquals(args("-y", "-v", "error", "-i", "input.mp4", "output.mp4"), simpleBuilder().overrideOutputFiles(true).build());
  }

  @Test
  public void testOverrideFlagAbsent() {
    assertEquals(args("-n", "-v", "error", "-i", "input.mp4", "output.mp4"), simpleBuilder().overrideOutputFiles(false).build());
  }

  @Test
  public void testSetPass() throws IOException {
    assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-pass", "1", "-f", "mp4", "-an", AbstractFFmpegStreamBuilder.DEVNULL), passBuilder().setPass(1).build());
  }

  @Test
  public void testSetPassDirectoryIgnoredIfPassNotSet() throws IOException {
    assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-f", "mp4", "output.mp4"), passBuilder().setPassDirectory("./tmp").build());
  }

  @Test
  public void testSetPassPrefixIgnoredIfPassNotSet() throws IOException {
    assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-f", "mp4", "output.mp4"), passBuilder().setPassPrefix("pre").build());
  }

  @Test
  public void testPass1() throws IOException {
    assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-pass", "1", "-passlogfile", "./tmp/pre", "-f", "mp4", "-an", AbstractFFmpegStreamBuilder.DEVNULL), passBuilder().setPass(1).setPassPrefix("pre").setPassDirectory("./tmp/").build());
  }

  @Test
  public void testVerbosityDefault() {
    assertEquals(args("-y", "-v", "error", "-i", "input.mp4", "output.mp4"), simpleBuilder().build());
  }

  @Test
  public void testSetVerbosity() {
    assertEquals(args("-y", "-v", "info", "-i", "input.mp4", "output.mp4"), simpleBuilder().setVerbosity(Verbosity.INFO).build());
  }

  @Test
  public void testSetProgress() throws URISyntaxException {
    assertEquals(args("-y", "-v", "error", "-progress", "tcp://127.0.0.1:1234", "-i", "input.mp4", "output.mp4"), simpleBuilder().addProgress(new URI("tcp://127.0.0.1:1234")).build());
  }

  @Test
  public void testSetUserAgent() {
    assertEquals(args("-y", "-v", "error", "-user_agent", "ffmpeg-cli-wrapper", "-i", "input.mp4", "output.mp4"), simpleBuilder().setUserAgent("ffmpeg-cli-wrapper").build());
  }








  @Test
  public void testSetQScale() {
    assertEquals(args("-y", "-v", "error", "-i", "input.mp4", "-qscale:a", "1", "output.mp4"), simpleBuilder().setVBR(1).build());
  }

  @Test
  public void testAddExtraArgs() {
    assertEquals(args("-y", "-v", "error", "-key", "value", "-i", "input.mp4", "output.mp4"), simpleBuilder().addExtraArgs("-key", "value").build());
  }

  @Test
  public void testSetAudioFilter() {
    assertEquals(args("-y", "-v", "error", "-i", "input.mp4", "-af", "fast", "output.mp4"), simpleBuilder().setAudioFilter("fast").build());
  }

  @Test
  public void testSetVideoFilter() {
    assertEquals(args("-y", "-v", "error", "-i", "input.mp4", "-vf", "fast", "output.mp4"), simpleBuilder().setVideoFilter("fast").build());
  }

  @Test
  public void testSetComplexFilter() {
    assertEquals(args("-y", "-v", "error", "-i", "input.mp4", "-filter_complex", "fast", "output.mp4"), simpleBuilder().setComplexFilter("fast").build());
  }

  @Test
  public void testSetFormat() {
    assertEquals(args("-y", "-v", "error", "-f", "mp4", "-i", "input.mp4", "output.mp4"), simpleBuilder().setFormat("mp4").build());
  }

  @Test
  public void testSetStartOffset() {
    assertEquals(args("-y", "-v", "error", "-ss", "00:00:10", "-i", "input.mp4", "output.mp4"), simpleBuilder().setStartOffset(10, TimeUnit.SECONDS).build());
  }

  @Test
  public void testReadAtNativeFrameRate() {
    assertEquals(args("-y", "-v", "error", "-re", "-i", "input.mp4", "output.mp4"), simpleBuilder().readAtNativeFrameRate().build());
  }

  protected List<String> args(String... args) {
    return ImmutableList.copyOf(args);
  }

  protected FFmpegBuilder simpleBuilder() {
    return new FFmpegBuilder()
            .addInput("input.mp4").addOutput("output.mp4").done();
  }

  protected FFmpegBuilder passBuilder() throws IOException {
    return new FFmpegBuilder()
            .addInput(ffprobe.probe(Samples.big_buck_bunny_720p_1mb))
            .addOutput("output.mp4")
            .setTargetSize(1)
            .setFormat("mp4")
            .done();
  }
}
