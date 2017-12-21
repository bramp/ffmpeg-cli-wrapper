package net.bramp.ffmpeg;

import com.google.common.base.Joiner;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.builder.FFmpegOutputBuilder;
import net.bramp.ffmpeg.lang.NewProcessAnswer;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;

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

  @Test
  public void testExample7() throws IOException {
    FFmpegBuilder builder =
        new FFmpegBuilder()
            .addInput("original.mp4")
            .addInput("spot.mp4")
            .setComplexFilter(
                "[1:v]scale=368:207,setpts=PTS-STARTPTS+5/TB [ov]; "
                    + "[0:v][ov] overlay=x=(main_w-overlay_w)/2:y=(main_h-overlay_h)/2:enable='between(t,5,15)' [v]")
            .addOutput("with-video.mp4")
            .addExtraArgs("-map", "[v]")
            .addExtraArgs("-map", "0:a")
            .setVideoCodec("libx264")
            .setPreset("ultrafast")
            .setConstantRateFactor(20)
            .setAudioCodec("copy")
            .addExtraArgs("-shortest")
            .done();

    String expected =
        "ffmpeg -y -v error"
            + " -i original.mp4"
            + " -i spot.mp4"
            + " -filter_complex [1:v]scale=368:207,setpts=PTS-STARTPTS+5/TB [ov]; [0:v][ov] overlay=x=(main_w-overlay_w)/2:y=(main_h-overlay_h)/2:enable='between(t,5,15)' [v]"
            + " -preset ultrafast"
            + " -crf 20"
            + " -vcodec libx264"
            + " -acodec copy"
            + " -map [v]"
            + " -map 0:a"
            + " -shortest"
            + " with-video.mp4";

    String actual = Joiner.on(" ").join(ffmpeg.path(builder.build()));
    assertEquals(expected, actual);
  }

  // Transcode to iOS HEVC format, with video filter set before output
  @Test
  public void testExample8() throws IOException {
    FFmpegBuilder builder =
        new FFmpegBuilder()
            .addInput("original.mp4")
            .setVideoFilter("select='gte(n\\,10)',scale=200:-1")
            .addOutput("hevc-video.mp4")
            .addExtraArgs("-tag:v", "hvc1")
            .setVideoCodec("libx265")
            .done();

    String expected =
        "ffmpeg -y -v error"
            + " -i original.mp4"
            + " -vf select='gte(n\\,10)',scale=200:-1"
            + " -vcodec libx265"
            + " -tag:v hvc1"
            + " hevc-video.mp4";

    String actual = Joiner.on(" ").join(ffmpeg.path(builder.build()));
    assertEquals(expected, actual);
  }

  // Convert a stereo mp3 into two mono tracks.
  @Test
  public void testExample9() throws IOException {
    FFmpegBuilder builder =
        new FFmpegBuilder()
            .setVerbosity(FFmpegBuilder.Verbosity.DEBUG)
            .setInput("input.mp3")
            .overrideOutputFiles(true) // Override the output if it exists
            .addOutput("left.mp3")
            .addExtraArgs("-map_channel", "0.0.0")
            .done()
            .addOutput("right.mp3")
            .addExtraArgs("-map_channel", "0.0.1")
            .done();

    String expected =
        "ffmpeg -y -v debug "
            + "-i input.mp3 "
            + "-map_channel 0.0.0 left.mp3 "
            + "-map_channel 0.0.1 right.mp3";

    String actual = Joiner.on(" ").join(ffmpeg.path(builder.build()));
    assertEquals(expected, actual);
  }

  // A test with videos added in a loop.
  @Test
  public void testExample10() throws IOException {
    String expected = "ffmpeg -y -v error"
        + " -f webm_dash_manifest"
        + " -i audio.webm"
        + " -i video_1.webm"
        + " -i video_2.webm"
        + " -i video_3.webm"
        + " -vcodec copy -acodec copy"
        + " -map 0 -map 1 -map 2 -map 3"
        + " -adaptation_sets \"id=0,streams=0 id=1,streams=1,2,3\""
        + " output.mpd";

    ArrayList<String> streams = new ArrayList<>();
    FFmpegBuilder builder = new FFmpegBuilder();

    builder.addInput("audio.webm").setFormat("webm_dash_manifest");

    for (int i = 1; i <= 3; i++) {
      builder.addInput(String.format("video_%d.webm", i)).setFormat("webm_dash_manifest");
      streams.add(String.format("%d", i));
    }

    FFmpegOutputBuilder out = builder.addOutput("output.mpd")
        .setVideoCodec("copy").setAudioCodec("copy") // TODO Add a new setCodec(..) method.
        .addExtraArgs("-map", "0");

    for (String stream : streams) {
      out.addExtraArgs("-map", stream);
    }

    out.addExtraArgs("-adaptation_sets",
        String.format("\"id=0,streams=0 id=1,streams=%s\"", Joiner.on(",").join(streams)))
        .done();

    String actual = Joiner.on(" ").join(ffmpeg.path(builder.build()));
    assertEquals(expected, actual);
  }

    // Convert a stereo mp3 into two mono tracks.
    @Test
    public void testExampleThread() throws IOException {
        FFmpegBuilder builder =
                new FFmpegBuilder()
                        .setThreads(2)
                        .setVerbosity(FFmpegBuilder.Verbosity.DEBUG)
                        .setInput("input.mp3")
                        .overrideOutputFiles(true) // Override the output if it exists
                        .addOutput("left.mp3")
                        .addExtraArgs("-map_channel", "0.0.0")
                        .done()
                        .addOutput("right.mp3")
                        .addExtraArgs("-map_channel", "0.0.1")
                        .done();

        String expected =
                "ffmpeg -y -v debug "
                        + "-threads 2 "
                        + "-i input.mp3 "
                        + "-map_channel 0.0.0 left.mp3 "
                        + "-map_channel 0.0.1 right.mp3";

        String actual = Joiner.on(" ").join(ffmpeg.path(builder.build()));
        assertEquals(expected, actual);
    }
}
