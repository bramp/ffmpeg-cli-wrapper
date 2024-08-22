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

    @Test
    public void testSetLoop() {
    List<String> args =
            new FFmpegBuilder()
                    .addInput("input")
                    .setStreamLoop(2)
                    .done()
                    .addOutput("output")
                    .done()
                    .build();

      assertEquals(
              args,
              ImmutableList.of("-y", "-v", "error", "-stream_loop", "2", "-i", "input", "output"));
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
  public void testQuestion156(){
    List<String> args =
            new FFmpegBuilder()
                    .overrideOutputFiles(true)
                    .setVerbosity(FFmpegBuilder.Verbosity.INFO)
                    // X11 screen input
                    .addInput(":0.0+0,0")
                    .setFormat("x11grab")
                    .setVideoResolution("1280x720")
                    .setVideoFrameRate(30)
                    .addExtraArgs("-draw_mouse", "0")
                    .addExtraArgs("-thread_queue_size", "4096")
                    .done()
                    // alsa audio input
                    .addInput("hw:0,1,0")
                    .setFormat("alsa")
                    .addExtraArgs("-thread_queue_size", "4096")
                    .done()
                    // Youtube output
                    .addOutput("rtmp://a.rtmp.youtube.com/live2/XXX")
                    .setAudioCodec("aac")
                    .setFormat("flv")
                    .done()
                    .build();

    assertEquals(
            ImmutableList.of("-y", "-v", "info",
                    "-f", "x11grab", "-s", "1280x720", "-r", "30/1", "-draw_mouse", "0", "-thread_queue_size", "4096", "-i", ":0.0+0,0",
                    "-f", "alsa", "-thread_queue_size", "4096", "-i", "hw:0,1,0",
                    "-f", "flv", "-acodec", "aac", "rtmp://a.rtmp.youtube.com/live2/XXX"),
            args
    );
  }

  @Test
  public void testSetStrict() {
    List<String> args = new FFmpegBuilder()
            .addInput("input.mp4")
            .done()
            .addOutput("output.mp4")
            .done()
            .setStrict(Strict.EXPERIMENTAL)
            .build();

    assertEquals(ImmutableList.of("-strict", "experimental", "-y", "-v", "error", "-i", "input.mp4", "output.mp4"), args);
  }

  @Test
  public void testQuestion65() {
    List<String> args = new FFmpegBuilder()
            .addInput("aevalsrc=0")
            .setFormat("lavfi")
            .done()
            .addInput("1.mp4")
            .done()
            .addOutput("output.mp4")
            .setVideoCodec("copy")
            .setAudioCodec("aac")
            .addExtraArgs("-map", "0:0")
            .addExtraArgs("-map", "1:0")
            .addExtraArgs("-shortest")
            .done()
            .build();

    assertEquals(ImmutableList.of("-y", "-v", "error", "-f", "lavfi", "-i", "aevalsrc=0", "-i", "1.mp4", "-vcodec", "copy", "-acodec", "aac", "-map", "0:0", "-map", "1:0", "-shortest", "output.mp4"), args);
  }

  @Test
  public void testQuestion295() {
    List<String> args = new FFmpegBuilder()
            .addInput("audio=<device>")
            .setFormat("dshow")
            .done()
            .addInput("desktop")
            .setFormat("gdigrab")
            .setFrames(30)
            .done()
            .addOutput("video_file_name.mp4")
            .setVideoCodec("libx264")
            .done()
            .build();

    assertEquals(ImmutableList.of(
            "-y", "-v", "error", "-f", "dshow", "-i", "audio=<device>", "-f", "gdigrab", "-vframes", "30", "-i", "desktop", "-vcodec", "libx264", "video_file_name.mp4"
    ), args);
  }

  @Test
  public void testQuestion252() {
    List<String> args = new FFmpegBuilder()
            .addInput("video_160x90_250k.webm").setFormat("webm_dash_manifest").done()
            .addInput("video_320x180_500k.webm").setFormat("webm_dash_manifest").done()
            .addInput("video_640x360_750k.webm").setFormat("webm_dash_manifest").done()
            .addInput("video_640x360_1000k.webm").setFormat("webm_dash_manifest").done()
            .addInput("video_1280x720_600k.webm").setFormat("webm_dash_manifest").done()
            .addInput("audio_128k.webm").setFormat("webm_dash_manifest").done()
            .addOutput("manifest.mp4")
            .setVideoCodec("copy")
            .setAudioCodec("copy")
            .addExtraArgs("-map", "0")
            .addExtraArgs("-map", "1")
            .addExtraArgs("-map", "2")
            .addExtraArgs("-map", "3")
            .addExtraArgs("-map", "4")
            .addExtraArgs("-map", "5")
            .setFormat("webm_dash_manifest")
            .addExtraArgs("-adaptation_sets", "id=0,streams=0,1,2,3,4 id=1,streams=5")
            .done()
            .build();

    assertEquals(ImmutableList.of(
            "-y",  "-v", "error", "-f", "webm_dash_manifest", "-i", "video_160x90_250k.webm", "-f", "webm_dash_manifest", "-i", "video_320x180_500k.webm", "-f", "webm_dash_manifest", "-i", "video_640x360_750k.webm", "-f", "webm_dash_manifest", "-i", "video_640x360_1000k.webm", "-f", "webm_dash_manifest", "-i", "video_1280x720_600k.webm", "-f", "webm_dash_manifest", "-i", "audio_128k.webm", "-f", "webm_dash_manifest", "-vcodec", "copy", "-acodec", "copy", "-map", "0", "-map", "1", "-map", "2", "-map", "3", "-map", "4", "-map", "5", "-adaptation_sets", "id=0,streams=0,1,2,3,4 id=1,streams=5", "manifest.mp4"
    ), args);
  }
}
