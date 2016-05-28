package net.bramp.ffmpeg;

import net.bramp.ffmpeg.builder.FFmpegBuilder;
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
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * -psnr
 *
 * @author bramp
 *
 */
public class FFmpegBuilderTest {

  public FFmpegBuilderTest() throws IOException {}

  @Test
  public void testNormal() {

    FFmpegBuilder builder =
            new FFmpegBuilder().setFormat("customFormat").setInput("input")
                    .setStartOffset(1500, TimeUnit.MILLISECONDS).overrideOutputFiles(true)
                    .addOutput("output").setFormat("mp4").setStartOffset(500, TimeUnit.MILLISECONDS)
                    .setAudioCodec("aac").setAudioChannels(1).setAudioSampleRate(48000)
                    .setAudioBitStreamFilter("bar").setVideoCodec("libx264")
                    .setVideoFrameRate(FFmpeg.FPS_30).setVideoResolution(320, 240)
                    .setVideoBitStreamFilter("foo").done();

    List<String> args = builder.build();
    assertThat(args, is(Arrays.asList("-y", "-v", "error", "-ss", "00:00:01.500", "-f",
            "customFormat", "-i", "input", "-f", "mp4", "-ss", "00:00:00.500", "-vcodec", "libx264",
            "-s", "320x240", "-r", "30/1", "-bsf:v", "foo", "-acodec", "aac", "-ac", "1", "-ar",
            "48000", "-bsf:a", "bar", "output")));
  }

  @Test
  public void testDisabled() {

    FFmpegBuilder builder =
            new FFmpegBuilder().setInput("input").addOutput("output").disableAudio().disableSubtitle()
                    .disableVideo().done();

    List<String> args = builder.build();
    assertThat(args,
            is(Arrays.asList("-y", "-v", "error", "-i", "input", "-vn", "-an", "-sn", "output")));
  }

  @Test
  public void testFilter() {

    FFmpegBuilder builder =
            new FFmpegBuilder().setInput("input").addOutput("output").disableAudio().disableSubtitle()
                    .setVideoFilter("scale='trunc(ow/a/2)*2:320'").done();

    List<String> args = builder.build();
    assertThat(args, is(Arrays.asList("-y", "-v", "error", "-i", "input", "-vf",
            "scale='trunc(ow/a/2)*2:320'", "-an", "-sn", "output")));
  }

  @Test
  public void testFilter_and_scale() {

    FFmpegBuilder builder =
            new FFmpegBuilder().setInput("input").addOutput("output").setVideoResolution(320, 240)
                    .setVideoFilter("scale='trunc(ow/a/2)*2:320'").done();

    List<String> args = builder.build();
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

    EncodingOptions options =
            new FFmpegBuilder().setInput("input").addOutput("output").useOptions(main)
                    .useOptions(audio).useOptions(video).buildOptions();

    assertThat(main, reflectEquals(options.getMain()));
    assertThat(audio, reflectEquals(options.getAudio()));
    assertThat(video, reflectEquals(options.getVideo()));
  }

  @Test
  public void testMultipleOutputs() {
    List<String> args =
            new FFmpegBuilder().setInput("input").addOutput("output1").setVideoResolution(320, 240)
                    .done().addOutput("output2").setVideoResolution(640, 480).done().build();

    assertThat(args, is(Arrays.asList("-y", "-v", "error", "-i", "input", "-s", "320x240",
            "output1", "-s", "640x480", "output2")));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNothing() {
    FFmpegBuilder builder = new FFmpegBuilder();
    builder.build();
  }
}
