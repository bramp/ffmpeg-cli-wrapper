package net.bramp.ffmpeg;

import com.google.common.base.Joiner;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.lang.NewProcessAnswer;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;

import static net.bramp.ffmpeg.FFmpegTest.argThatHasItem;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Ensures the examples in the Examples on github continue to work.
 * https://github.com/bramp/ffmpeg-cli-wrapper/wiki/Random-Examples
 */
@RunWith(MockitoJUnitRunner.class)
public class ExamplesTest {

  @Mock ProcessFunction runFunc;

  FFmpeg ffmpeg;

  @Before
  public void before() throws IOException {
    when(runFunc.run(argThatHasItem("-version")))
        .thenAnswer(new NewProcessAnswer("ffmpeg-version"));
    ffmpeg = new FFmpeg("ffmpeg", runFunc);
  }

  @Test
  public void testExample1() throws IOException {
    ffmpeg = new FFmpeg("ffmpeg\\win64\\bin\\ffmpeg.exe", runFunc);

    FFmpegBuilder builder =
        new FFmpegBuilder()
            .addExtraArgs("-rtbufsize", "1500M")
            .addExtraArgs("-re")
            .setFormat("dshow")
            .setInput(
                "video=\"Microsoft Camera Rear\":audio=\"Microphone Array (Realtek High Definition Audio(SST))\"")
            .addOutput("rtmp://a.rtmp.youtube.com/live2/1234-5678")
            .setFormat("flv")
            .addExtraArgs("-bufsize", "4000k")
            .addExtraArgs("-maxrate", "1000k")
            .setAudioCodec("libmp3lame")
            .setAudioSampleRate(FFmpeg.AUDIO_SAMPLE_44100)
            .setAudioBitRate(1_000_000)
            .addExtraArgs("-profile:v", "baseline")
            .setVideoCodec("libx264")
            .setVideoPixelFormat("yuv420p")
            .setVideoResolution(426, 240)
            .setVideoBitRate(2_000_000)
            .setVideoFrameRate(30)
            .addExtraArgs("-deinterlace")
            .addExtraArgs("-preset", "medium")
            .addExtraArgs("-g", "30")
            .done();

    String expected =
        "ffmpeg\\win64\\bin\\ffmpeg.exe -y -v error"
            + " -f dshow -rtbufsize 1500M -re"
            + " -i video=\"Microsoft Camera Rear\":audio=\"Microphone Array (Realtek High Definition Audio(SST))\""
            + " -f flv"
            + " -vcodec libx264 -pix_fmt yuv420p -s 426x240 -r 30/1 -b:v 2000000"
            + " -acodec libmp3lame -ar 44100 -b:a 1000000 -bufsize 4000k -maxrate 1000k"
            + " -profile:v baseline -deinterlace -preset medium -g 30"
            + " rtmp://a.rtmp.youtube.com/live2/1234-5678";

    String actual = Joiner.on(" ").join(ffmpeg.path(builder.build()));

    assertEquals(expected, actual);
  }

  @Test
  public void testExample2() throws IOException {
    FFmpegBuilder builder =
        new FFmpegBuilder()
            .setInput("input.mkv")
            .addOutput("output.ogv")
            .setVideoCodec("libtheora")
            .addExtraArgs("-qscale:v", "7")
            .setAudioCodec("libvorbis")
            .addExtraArgs("-qscale:a", "5")
            .done();

    String expected =
        "ffmpeg -y -v error"
            + " -i input.mkv"
            + " -vcodec libtheora"
            + " -acodec libvorbis"
            + " -qscale:v 7"
            + " -qscale:a 5"
            + " output.ogv";

    String actual = Joiner.on(" ").join(ffmpeg.path(builder.build()));
    assertEquals(expected, actual);
  }

  @Test
  public void testExample3() throws IOException {
    FFmpegBuilder builder =
        new FFmpegBuilder()
            .setInput("sample.avi")
            .addOutput("thumbnail.png")
            .setFrames(1)
            .setVideoFilter("select='gte(n\\,10)',scale=200:-1")
            .done();

    String expected =
        "ffmpeg -y -v error"
            + " -i sample.avi"
            + " -vframes 1 -vf select='gte(n\\,10)',scale=200:-1"
            + " thumbnail.png";

    String actual = Joiner.on(" ").join(ffmpeg.path(builder.build()));
    assertEquals(expected, actual);
  }

  // Read from RTSP (IP camera)
  @Test
  public void testExample4() throws IOException {

    FFmpegBuilder builder =
        new FFmpegBuilder()
            .setInput("rtsp://192.168.1.1:1234/")
            .addOutput("img%03d.jpg")
            .setFormat("image2")
            .done();

    String expected = "ffmpeg -y -v error -i rtsp://192.168.1.1:1234/ -f image2 img%03d.jpg";

    String actual = Joiner.on(" ").join(ffmpeg.path(builder.build()));
    assertEquals(expected, actual);
  }

  // Set the working directory of ffmpeg
  @Ignore("because this test will invoke /path/to/ffmpeg.")
  @Test
  public void testExample5() throws IOException {
    RunProcessFunction func = new RunProcessFunction();
    func.setWorkingDirectory("/path/to/working/dir");

    FFmpeg ffmpeg = new FFmpeg("/path/to/ffmpeg", func);
    FFprobe ffprobe = new FFprobe("/path/to/ffprobe", func);

    FFmpegBuilder builder = new FFmpegBuilder().setInput("input").addOutput("output.mp4").done();

    FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

    // Run a two-pass encode
    executor.createTwoPassJob(builder).run();
  }

  // Create a video from images
  @Test
  public void testExample6() throws IOException {
    FFmpegBuilder builder =
        new FFmpegBuilder()
            .addInput("image%03d.png")
            .addOutput("output.mp4")
            .setVideoFrameRate(FFmpeg.FPS_24)
            .done();

    String expected = "ffmpeg -y -v error -i image%03d.png -r 24/1 output.mp4";

    String actual = Joiner.on(" ").join(ffmpeg.path(builder.build()));
    assertEquals(expected, actual);
  }
}
