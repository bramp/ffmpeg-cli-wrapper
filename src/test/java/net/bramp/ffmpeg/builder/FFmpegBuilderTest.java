package net.bramp.ffmpeg.builder;

import static com.nitorcreations.Matchers.reflectEquals;
import static net.bramp.ffmpeg.FFmpeg.AUDIO_FORMAT_S16;
import static net.bramp.ffmpeg.FFmpeg.AUDIO_SAMPLE_48000;
import static net.bramp.ffmpeg.FFmpeg.FPS_30;
import static net.bramp.ffmpeg.builder.MetadataSpecifier.*;
import static net.bramp.ffmpeg.builder.StreamSpecifier.tag;
import static net.bramp.ffmpeg.builder.StreamSpecifier.usable;
import static net.bramp.ffmpeg.builder.StreamSpecifierType.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.bramp.ffmpeg.builder.FFmpegBuilder.Verbosity;
import net.bramp.ffmpeg.options.AudioEncodingOptions;
import net.bramp.ffmpeg.options.EncodingOptions;
import net.bramp.ffmpeg.options.MainEncodingOptions;
import net.bramp.ffmpeg.options.VideoEncodingOptions;
import org.junit.Test;

@SuppressWarnings("unused")
public class FFmpegBuilderTest {

  public FFmpegBuilderTest() throws IOException {}

  @Test
  public void testNormal() {

    List<String> args =
        new FFmpegBuilder()
            .setVerbosity(Verbosity.DEBUG)
            .setUserAgent(
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36")
            .setInput("input")
            .setStartOffset(1500, TimeUnit.MILLISECONDS)
            .done()
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
            .done()
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
            .done()
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
            .done()
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
            .done()
            .addOutput("output")
            .useOptions(main)
            .useOptions(audio)
            .useOptions(video)
            .buildOptions();

    assertThat(main, reflectEquals(options.getMain()));
    assertThat(audio, reflectEquals(options.getAudio()));
    assertThat(video, reflectEquals(options.getVideo()));
  }

  /** Tests if all the various encoding options actually get stored and used correctly */
  @Test
  public void testVideoCodecWithEnum() {
    MainEncodingOptions main = new MainEncodingOptions("mp4", 1500L, 2L);
    AudioEncodingOptions audio =
            new AudioEncodingOptions(true, AudioCodec.AAC, 1, AUDIO_SAMPLE_48000, AUDIO_FORMAT_S16, 1, 2.0);
    VideoEncodingOptions video =
            new VideoEncodingOptions(true, VideoCodec.H264, FPS_30, 320, 240, 1, null, null, null);

    EncodingOptions options =
            new FFmpegBuilder()
                    .setInput("input")
                    .done()
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
            .done()
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
            .done()
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
            .done()
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
            .done()
            .addOutput(URI.create("udp://10.1.0.102:1234"))
            .setFilename("filename")
            .done()
            .build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddEmptyFilename() {
    List<String> unused = new FFmpegBuilder().setInput("input").done().addOutput("").done().build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetEmptyFilename() {
    List<String> unused =
        new FFmpegBuilder().setInput("input").done().addOutput("output").setFilename("").done().build();
  }

  @Test
  public void testMetaTags() {

    List<String> args =
        new FFmpegBuilder()
            .setInput("input")
            .done()
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
            .done()
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
            .done()
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
            .done()
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
            .done()
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
            .done()
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
            .done()
            .addInput("input2")
            .done()
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
            .done()
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
            .done()
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
              .done()
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
}
