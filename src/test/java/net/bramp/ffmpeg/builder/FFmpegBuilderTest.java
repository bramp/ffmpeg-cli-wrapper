package net.bramp.ffmpeg.builder;

import com.google.common.collect.ImmutableList;
import net.bramp.ffmpeg.options.AudioEncodingOptions;
import net.bramp.ffmpeg.options.EncodingOptions;
import net.bramp.ffmpeg.options.MainEncodingOptions;
import net.bramp.ffmpeg.options.VideoEncodingOptions;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.nitorcreations.Matchers.reflectEquals;
import static net.bramp.ffmpeg.FFmpeg.AUDIO_FORMAT_S16;
import static net.bramp.ffmpeg.FFmpeg.AUDIO_SAMPLE_48000;
import static net.bramp.ffmpeg.FFmpeg.FPS_30;
import static net.bramp.ffmpeg.builder.FFmpegBuilder.Verbosity;
import static net.bramp.ffmpeg.builder.MetadataSpecifier.*;
import static net.bramp.ffmpeg.builder.StreamSpecifier.tag;
import static net.bramp.ffmpeg.builder.StreamSpecifier.usable;
import static net.bramp.ffmpeg.builder.StreamSpecifierType.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/** @author bramp */
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
  public void testFFmpegInputBuilder(){
    String cmd = "-y -v info -f x11grab -s 1280x720 -r 30/1 -draw_mouse 0 -thread_queue_size 4096 -video_device_number 0 " +
            "-i :0.0+0,0 -f alsa -thread_queue_size 4096 -video_device_number 0 -i hw:0,1,0 -f flv -acodec aac rtmp://a.rtmp.youtube.com/live2/XXX" ;
    List<String> args =
            new FFmpegBuilder()
            .overrideOutputFiles(true)
            .setVerbosity(FFmpegBuilder.Verbosity.INFO)
            .addInputStream(":0.0+0,0")
              .setFormat("x11grab")
              .addExtraArgs("-draw_mouse", "0")
              .setVideoFrameRate(30)
              .setVideoResolution("1280x720")
              .setThreadQueueSize(4096)
              .done()
            .addInputStream("hw:0,1,0")
              .setFormat("alsa")
              .setThreadQueueSize(4096)
              .done()
            .addOutput("rtmp://a.rtmp.youtube.com/live2/XXX")
              .setAudioCodec("aac")
              .setFormat("flv")
              .done()
            .build();

    assertEquals(
            ImmutableList.copyOf(parseCmd(cmd)),
            args
    );
  }

  //convenience method to parse commands into the immutable list assert form
  private List<String> parseCmd(String cmd){
    return Arrays.asList(cmd.split(" "));
  }
}
