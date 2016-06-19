package net.bramp.ffmpeg.builder;

import com.google.common.base.Joiner;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.options.AudioEncodingOptions;
import net.bramp.ffmpeg.options.EncodingOptions;
import net.bramp.ffmpeg.options.MainEncodingOptions;
import net.bramp.ffmpeg.options.VideoEncodingOptions;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.nitorcreations.Matchers.reflectEquals;
import static net.bramp.ffmpeg.builder.MetadataSpecifier.chapter;
import static net.bramp.ffmpeg.builder.MetadataSpecifier.program;
import static net.bramp.ffmpeg.builder.MetadataSpecifier.stream;
import static net.bramp.ffmpeg.builder.StreamSpecifier.tag;
import static net.bramp.ffmpeg.builder.StreamSpecifier.usable;
import static net.bramp.ffmpeg.builder.StreamSpecifierType.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 *
 * @author bramp
 *
 */
public class FFmpegBuilderTest {

  public FFmpegBuilderTest() throws IOException {}

  @Test
  public void testNormal() {

    // @formatter:off
    List<String> args = new FFmpegBuilder()
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
          .setVideoCodec("libx264")
          .setVideoFrameRate(FFmpeg.FPS_30)
          .setVideoResolution(320, 240)
          .setVideoBitStreamFilter("foo")
          .done()
        .build();
    // @formatter:on

    assertThat(args, is(Arrays.asList("-y", "-v", "error", "-ss", "00:00:01.500", "-i", "input",
        "-f", "mp4", "-ss", "00:00:00.500", "-vcodec", "libx264", "-s", "320x240", "-r", "30/1",
        "-bsf:v", "foo", "-acodec", "aac", "-ac", "1", "-ar", "48000", "-bsf:a", "bar", "output")));
  }

  @Test
  public void testDisabled() {

    // @formatter:off
    List<String> args = new FFmpegBuilder()
        .setInput("input")
        .addOutput("output")
          .disableAudio()
          .disableSubtitle()
          .disableVideo()
          .done()
        .build();
    // @formatter:on

    assertThat(args,
        is(Arrays.asList("-y", "-v", "error", "-i", "input", "-vn", "-an", "-sn", "output")));
  }

  @Test
  public void testFilter() {

    // @formatter:off
    List<String> args = new FFmpegBuilder()
        .setInput("input")
        .addOutput("output")
          .disableAudio()
          .disableSubtitle()
          .setVideoFilter("scale='trunc(ow/a/2)*2:320'")
          .done()
        .build();
    // @formatter:on

    assertThat(args, is(Arrays.asList("-y", "-v", "error", "-i", "input", "-vf",
        "scale='trunc(ow/a/2)*2:320'", "-an", "-sn", "output")));
  }

  @Test
  public void testFilterAndScale() {

    // @formatter:off
    List<String> args = new FFmpegBuilder()
        .setInput("input")
        .addOutput("output")
          .setVideoResolution(320, 240)
          .setVideoFilter("scale='trunc(ow/a/2)*2:320'")
          .done()
        .build();
    // @formatter:on

    assertThat(args, is(Arrays.asList("-y", "-v", "error", "-i", "input", "-s", "320x240", "-vf",
        "scale='trunc(ow/a/2)*2:320'", "output")));
  }

  /**
   * Tests if all the various encoding options actually get stored and used correctly
   */
  @Test
  public void testSetOptions() {
    MainEncodingOptions main = new MainEncodingOptions("mp4", 1500L, 2L);
    AudioEncodingOptions audio =
        new AudioEncodingOptions(true, "aac", 1, FFmpeg.AUDIO_SAMPLE_48000, FFmpeg.AUDIO_DEPTH_S16,
            1, 2);
    VideoEncodingOptions video =
        new VideoEncodingOptions(true, "libx264", FFmpeg.FPS_30, 320, 240, 1, null, "", "");

    // @formatter:off
    EncodingOptions options = new FFmpegBuilder()
        .setInput("input")
        .addOutput("output")
          .useOptions(main)
          .useOptions(audio)
          .useOptions(video)
        .buildOptions();
    // @formatter:on

    assertThat(main, reflectEquals(options.getMain()));
    assertThat(audio, reflectEquals(options.getAudio()));
    assertThat(video, reflectEquals(options.getVideo()));
  }

  @Test
  public void testMultipleOutputs() {
    // @formatter:off
    List<String> args = new FFmpegBuilder()
        .setInput("input")
        .addOutput("output1")
          .setVideoResolution(320, 240)
          .done()
        .addOutput("output2")
          .setVideoResolution(640, 480)
          .done()
        .build();
    // @formatter:on

    assertThat(args, is(Arrays.asList("-y", "-v", "error", "-i", "input", "-s", "320x240",
        "output1", "-s", "640x480", "output2")));
  }

  @Test
  public void testURLOutput() {
    // @formatter:off
    List<String> args = new FFmpegBuilder()
        .setInput("input")
        .addOutput("udp://10.1.0.102:1234")
          .setVideoResolution(320, 240)
          .done()
        .build();
    // @formatter:on

    assertThat(args, is(Arrays.asList("-y", "-v", "error", "-i", "input", "-s", "320x240",
        "udp://10.1.0.102:1234")));
  }

  @Test
  public void testMetaTags() {
    // @formatter:off
    List<String> args = new FFmpegBuilder()
        .setInput("input")
        .addOutput("output")
          .addMetaTag("comment", "My Comment")
          .addMetaTag("title", "\"Video\"")
          .addMetaTag("author", "a=b:c")
          .done()
        .build();
    // @formatter:on

    assertThat(args,
        is(Arrays.asList("-y", "-v", "error", "-i", "input", "-metadata", "comment=My Comment",
            "-metadata", "title=\"Video\"", "-metadata", "author=a=b:c", "output")));
  }

  @Test
  public void testMetaTagsWithSpecifier() {
    // @formatter:off
    List<String> args = new FFmpegBuilder()
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
    // @formatter:on

    assertThat(args, is(Arrays.asList("-y", "-v", "error", "-i", "input", "-metadata",
        "title=Movie Title", "-metadata:c:0", "author=Bob", "-metadata:p:0", "comment=Awesome",
        "-metadata:s:0", "copyright=Megacorp", "-metadata:s:v", "framerate=24fps",
        "-metadata:s:v:0", "artist=Joe", "-metadata:s:a:0", "language=eng", "-metadata:s:s:0",
        "language=fre", "-metadata:s:u", "year=2010", "-metadata:s:m:key", "a=b",
        "-metadata:s:m:key:value", "a=b", "output")));
  }

  @Test
  public void testExtraArgs() {
    // @formatter:off
    List<String> args = new FFmpegBuilder()
        .addExtraArgs("-a", "b")
        .setInput("input")
        .addOutput("output")
          .addExtraArgs("-c", "d")
          .disableAudio()
          .disableSubtitle()
          .done()
        .build();
    // @formatter:on

    assertThat(args, is(Arrays.asList("-y", "-v", "error", "-a", "b", "-i", "input", "-an", "-sn",
        "-c", "d", "output")));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNothing() {
    FFmpegBuilder builder = new FFmpegBuilder();
    builder.build();
  }

}
