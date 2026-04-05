package net.bramp.ffmpeg;

import static net.bramp.ffmpeg.FFmpegTest.argThatHasItem;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.google.common.base.Joiner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.builder.FFmpegOutputBuilder;
import net.bramp.ffmpeg.lang.NewProcessAnswer;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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

  // Stream from a webcam (via DirectShow on Windows) to YouTube Live via RTMP.
  // Demonstrates: input format override, RTMP output, hardware-specific input names.
  @Test
  public void testWebcamToYouTubeRtmp() throws IOException {
    ffmpeg = new FFmpeg("ffmpeg\\win64\\bin\\ffmpeg.exe", runFunc);

    FFmpegBuilder builder =
        new FFmpegBuilder()
            .addExtraArgs("-rtbufsize", "1500M")
            .addExtraArgs("-re")
            .setInput(
                "video=\"Microsoft Camera Rear\":audio=\"Microphone Array (Realtek High Definition Audio(SST))\"")
            .setFormat("dshow")
            .done()
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
            + " -rtbufsize 1500M -re -f dshow"
            + " -i video=\"Microsoft Camera Rear\":audio=\"Microphone Array (Realtek High Definition Audio(SST))\""
            + " -f flv"
            + " -vcodec libx264 -pix_fmt yuv420p -s 426x240 -r 30/1 -b:v 2000000"
            + " -acodec libmp3lame -ar 44100 -b:a 1000000 -bufsize 4000k -maxrate 1000k"
            + " -profile:v baseline -deinterlace -preset medium -g 30"
            + " rtmp://a.rtmp.youtube.com/live2/1234-5678";

    String actual = Joiner.on(" ").join(ffmpeg.path(builder.build()));

    assertEquals(expected, actual);
  }

  // Encode to OGV (Theora/Vorbis) with quality-based settings instead of bitrate.
  @Test
  public void testOgvQualitySettings() throws IOException {
    // <!-- example: OGV Quality Settings -->
    // Codec-specific quality scales via addExtraArgs.
    FFmpegBuilder builder =
        new FFmpegBuilder()
            .setInput("input.mkv")
            .done()
            .addOutput("output.ogv")
            .setVideoCodec("libtheora")
            .addExtraArgs("-qscale:v", "7")
            .setAudioCodec("libvorbis")
            .addExtraArgs("-qscale:a", "5")
            .done();
    // <!-- /example -->

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

  // Extract a single frame as a PNG thumbnail.
  @Test
  public void testExtractThumbnail() throws IOException {
    // <!-- example: Extract a Thumbnail -->
    // Skip the first 10 frames and scale to 200px wide.
    // Demonstrates: setFrames, setVideoFilter.
    FFmpegBuilder builder =
        new FFmpegBuilder()
            .setInput("sample.avi")
            .done()
            .addOutput("thumbnail.png")
            .setFrames(1)
            .setVideoFilter("select='gte(n\\,10)',scale=200:-1")
            .done();
    // <!-- /example -->

    String expected =
        "ffmpeg -y -v error"
            + " -i sample.avi"
            + " -vframes 1 -vf select='gte(n\\,10)',scale=200:-1"
            + " thumbnail.png";

    String actual = Joiner.on(" ").join(ffmpeg.path(builder.build()));
    assertEquals(expected, actual);
  }

  // Capture frames from an RTSP IP camera.
  @Test
  public void testReadFromRtspCamera() throws IOException {
    // <!-- example: Read from RTSP Camera -->
    // Save frames as numbered JPEG images.
    // Demonstrates: RTSP input, image2 output format.
    FFmpegBuilder builder =
        new FFmpegBuilder()
            .setInput("rtsp://192.168.1.1:1234/")
            .done()
            .addOutput("img%03d.jpg")
            .setFormat("image2")
            .done();
    // <!-- /example -->

    String expected = "ffmpeg -y -v error -i rtsp://192.168.1.1:1234/ -f image2 img%03d.jpg";

    String actual = Joiner.on(" ").join(ffmpeg.path(builder.build()));
    assertEquals(expected, actual);
  }

  // Run ffmpeg with a custom working directory.
  @Ignore("because this test will invoke /path/to/ffmpeg.")
  @Test
  public void testSetWorkingDirectory() throws IOException {
    // <!-- example: Set Working Directory -->
    // Useful when input/output paths are relative.
    // Demonstrates: RunProcessFunction.setWorkingDirectory.
    RunProcessFunction func = new RunProcessFunction();
    func.setWorkingDirectory("/path/to/working/dir");

    FFmpeg ffmpeg = new FFmpeg("/path/to/ffmpeg", func);
    FFprobe ffprobe = new FFprobe("/path/to/ffprobe", func);

    FFmpegBuilder builder =
        new FFmpegBuilder().setInput("input").done().addOutput("output.mp4").done();

    FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

    // Run a two-pass encode
    executor.createTwoPassJob(builder).run();
    // <!-- /example -->
  }

  // Create a video from a numbered sequence of PNG images.
  @Test
  public void testCreateVideoFromImages() throws IOException {
    // <!-- example: Create Video from Images -->
    // Input pattern image%03d.png matches image000.png, image001.png, etc.
    // Demonstrates: image sequence input, setVideoFrameRate.
    FFmpegBuilder builder =
        new FFmpegBuilder()
            .addInput("image%03d.png")
            .done()
            .addOutput("output.mp4")
            .setVideoFrameRate(FFmpeg.FPS_24)
            .done();
    // <!-- /example -->

    String expected = "ffmpeg -y -v error -i image%03d.png -r 24/1 output.mp4";

    String actual = Joiner.on(" ").join(ffmpeg.path(builder.build()));
    assertEquals(expected, actual);
  }

  // Overlay a smaller video on top of a main video (picture-in-picture).
  @Test
  public void testComplexFilterPictureInPicture() throws IOException {
    // <!-- example: Complex Filter - Picture-in-Picture -->
    // Uses a complex filter graph with stream mapping.
    // Demonstrates: multiple inputs, setComplexFilter, -map.
    FFmpegBuilder builder =
        new FFmpegBuilder()
            .addInput("original.mp4")
            .done()
            .addInput("spot.mp4")
            .done()
            .addOutput("with-video.mp4")
            .setComplexFilter(
                "[1:v]scale=368:207,setpts=PTS-STARTPTS+5/TB [ov]; "
                    + "[0:v][ov] overlay=x=(main_w-overlay_w)/2:y=(main_h-overlay_h)/2:enable='between(t,5,15)' [v]")
            .addExtraArgs("-map", "[v]")
            .addExtraArgs("-map", "0:a")
            .setVideoCodec("libx264")
            .setPreset("ultrafast")
            .setConstantRateFactor(20)
            .setAudioCodec("copy")
            .addExtraArgs("-shortest")
            .done();
    // <!-- /example -->

    String expected =
        "ffmpeg -y -v error"
            + " -i original.mp4"
            + " -i spot.mp4"
            + " -preset ultrafast"
            + " -crf 20"
            + " -filter_complex [1:v]scale=368:207,setpts=PTS-STARTPTS+5/TB [ov]; [0:v][ov] overlay=x=(main_w-overlay_w)/2:y=(main_h-overlay_h)/2:enable='between(t,5,15)' [v]"
            + " -vcodec libx264"
            + " -acodec copy"
            + " -map [v]"
            + " -map 0:a"
            + " -shortest"
            + " with-video.mp4";

    String actual = Joiner.on(" ").join(ffmpeg.path(builder.build()));
    assertEquals(expected, actual);
  }

  // Transcode to HEVC (H.265) for iOS compatibility.
  @Test
  public void testHevcTranscoding() throws IOException {
    // <!-- example: HEVC/H.265 Transcoding -->
    // Uses the hvc1 tag required by Apple devices.
    // Demonstrates: libx265 codec, tag:v extra arg, video filter set before output.
    FFmpegBuilder builder =
        new FFmpegBuilder()
            .addInput("original.mp4")
            .done()
            .setVideoFilter("select='gte(n\\,10)',scale=200:-1")
            .addOutput("hevc-video.mp4")
            .addExtraArgs("-tag:v", "hvc1")
            .setVideoCodec("libx265")
            .done();
    // <!-- /example -->

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

  // Split a stereo MP3 into separate left and right mono files.
  @Test
  public void testSplitStereoToMono() throws IOException {
    // <!-- example: Split Stereo to Mono Tracks -->
    // Demonstrates: multiple outputs, -map_channel, verbosity control.
    FFmpegBuilder builder =
        new FFmpegBuilder()
            .setVerbosity(FFmpegBuilder.Verbosity.DEBUG)
            .setInput("input.mp3")
            .done()
            .overrideOutputFiles(true) // Override the output if it exists
            .addOutput("left.mp3")
            .addExtraArgs("-map_channel", "0.0.0")
            .done()
            .addOutput("right.mp3")
            .addExtraArgs("-map_channel", "0.0.1")
            .done();
    // <!-- /example -->

    String expected =
        "ffmpeg -y -v debug "
            + "-i input.mp3 "
            + "-map_channel 0.0.0 left.mp3 "
            + "-map_channel 0.0.1 right.mp3";

    String actual = Joiner.on(" ").join(ffmpeg.path(builder.build()));
    assertEquals(expected, actual);
  }

  // Create a WebM DASH manifest (MPD) from audio and multiple video tracks.
  @Test
  public void testWebmDashManifest() throws IOException {
    String expected =
        "ffmpeg -y -v error"
            + " -f webm_dash_manifest"
            + " -i audio.webm"
            + " -f webm_dash_manifest"
            + " -i video_1.webm"
            + " -f webm_dash_manifest"
            + " -i video_2.webm"
            + " -f webm_dash_manifest"
            + " -i video_3.webm"
            + " -vcodec copy -acodec copy"
            + " -map 0 -map 1 -map 2 -map 3"
            + " -adaptation_sets \"id=0,streams=0 id=1,streams=1,2,3\""
            + " output.mpd";

    // <!-- example: WebM DASH Manifest -->
    // Demonstrates: adding inputs in a loop, webm_dash_manifest format, adaptation sets.
    ArrayList<String> streams = new ArrayList<>();
    FFmpegBuilder builder = new FFmpegBuilder();

    builder.addInput("audio.webm").setFormat("webm_dash_manifest");

    for (int i = 1; i <= 3; i++) {
      builder.addInput(String.format("video_%d.webm", i)).setFormat("webm_dash_manifest");
      streams.add(String.format("%d", i));
    }

    FFmpegOutputBuilder out =
        builder
            .addOutput("output.mpd")
            .setVideoCodec("copy")
            .setAudioCodec("copy") // TODO Add a new setCodec(..) method.
            .addExtraArgs("-map", "0");

    for (String stream : streams) {
      out.addExtraArgs("-map", stream);
    }

    out.addExtraArgs(
            "-adaptation_sets",
            String.format("\"id=0,streams=0 id=1,streams=%s\"", Joiner.on(",").join(streams)))
        .done();
    // <!-- /example -->

    String actual = Joiner.on(" ").join(ffmpeg.path(builder.build()));
    assertEquals(expected, actual);
  }

  // Use ProcessBuilder directly instead of FFmpegExecutor.
  @Test
  @Ignore("because this test will invoke /path/to/ffmpeg.")
  public void testDirectProcessControl() throws IOException, InterruptedException {
    // <!-- example: Direct Process Control -->
    // Useful when you need direct access to the Process (e.g., to stop it).
    FFmpegBuilder builder =
        new FFmpegBuilder().setInput("input").done().addOutput("output.mp4").done();

    List<String> args = new ArrayList<>();
    args.add("/path/to/ffmpeg");
    args.addAll(builder.build());

    ProcessBuilder processBuilder = new ProcessBuilder(args);
    processBuilder.redirectErrorStream(true);

    Process p = processBuilder.start();

    Thread.sleep(1000);

    p.destroy();
    // <!-- /example -->
  }

  // Extract a segment of a video without re-encoding.
  @Test
  public void testTrimSplitByTime() throws IOException {
    // <!-- example: Trim/Split by Time -->
    // Extract a 1-minute segment starting at the 1-minute mark.
    // Demonstrates: setStartOffset (input -ss), setDuration (output -t), stream copy.
    FFmpegBuilder builder =
        new FFmpegBuilder()
            .setInput("input.mp4")
            .setStartOffset(1, TimeUnit.MINUTES)
            .done()
            .addOutput("output.mp4")
            .setDuration(1, TimeUnit.MINUTES)
            .setVideoCodec("copy")
            .setAudioCodec("copy")
            .done();
    // <!-- /example -->

    String expected =
        "ffmpeg -y -v error -ss 00:01:00 -i input.mp4 -t 00:01:00 -vcodec copy -acodec copy output.mp4";
    String actual = Joiner.on(" ").join(ffmpeg.path(builder.build()));
    assertEquals(expected, actual);
  }

  // Strip the audio track from a video.
  @Test
  public void testRemoveAudio() throws IOException {
    // <!-- example: Remove Audio from Video -->
    // Keeps video untouched via stream copy.
    // Demonstrates: disableAudio, setVideoCodec("copy").
    FFmpegBuilder builder =
        new FFmpegBuilder()
            .setInput("input.mp4")
            .done()
            .addOutput("output-no-audio.mp4")
            .setVideoCodec("copy")
            .disableAudio()
            .done();
    // <!-- /example -->

    String expected = "ffmpeg -y -v error -i input.mp4 -vcodec copy -an output-no-audio.mp4";
    String actual = Joiner.on(" ").join(ffmpeg.path(builder.build()));
    assertEquals(expected, actual);
  }

  // Extract the audio track from a video as an MP3 file.
  @Test
  public void testExtractAudio() throws IOException {
    // <!-- example: Extract Audio from Video -->
    // Re-encodes audio to MP3 at 192kbps.
    // Demonstrates: disableVideo, setAudioCodec, setAudioBitRate.
    FFmpegBuilder builder =
        new FFmpegBuilder()
            .setInput("input.mp4")
            .done()
            .addOutput("audio.mp3")
            .disableVideo()
            .setAudioCodec("libmp3lame")
            .setAudioBitRate(192_000)
            .done();
    // <!-- /example -->

    String expected =
        "ffmpeg -y -v error -i input.mp4 -vn -acodec libmp3lame -b:a 192000 audio.mp3";
    String actual = Joiner.on(" ").join(ffmpeg.path(builder.build()));
    assertEquals(expected, actual);
  }

  // Convert an MP4 video to an animated GIF.
  @Test
  public void testMp4ToGif() throws IOException {
    // <!-- example: MP4 to GIF -->
    // Reduces resolution and frame rate for a smaller file.
    // Demonstrates: format conversion, setVideoResolution, setVideoFrameRate.
    FFmpegBuilder builder =
        new FFmpegBuilder()
            .setInput("input.mp4")
            .done()
            .addOutput("output.gif")
            .setVideoResolution(320, 240)
            .setVideoFrameRate(10)
            .done();
    // <!-- /example -->

    String expected = "ffmpeg -y -v error -i input.mp4 -s 320x240 -r 10/1 output.gif";
    String actual = Joiner.on(" ").join(ffmpeg.path(builder.build()));
    assertEquals(expected, actual);
  }

  // Burn a text string onto the video.
  @Test
  public void testTextOverlay() throws IOException {
    // <!-- example: Add Text Overlay -->
    // Uses the drawtext filter with font styling and positioning.
    FFmpegBuilder builder =
        new FFmpegBuilder()
            .setInput("input.mp4")
            .done()
            .addOutput("output-with-text.mp4")
            .setVideoCodec("libx264")
            .setVideoFilter("drawtext=text='Hello World':fontsize=24:fontcolor=white:x=10:y=10")
            .done();
    // <!-- /example -->

    String expected =
        "ffmpeg -y -v error -i input.mp4"
            + " -vcodec libx264"
            + " -vf drawtext=text='Hello World':fontsize=24:fontcolor=white:x=10:y=10"
            + " output-with-text.mp4";
    String actual = Joiner.on(" ").join(ffmpeg.path(builder.build()));
    assertEquals(expected, actual);
  }

  // Overlay a logo image onto a video.
  @Test
  public void testWatermark() throws IOException {
    // <!-- example: Add Watermark -->
    // Places a PNG logo at position (10,10) using the overlay complex filter.
    // Demonstrates: multiple inputs, setComplexFilter.
    FFmpegBuilder builder =
        new FFmpegBuilder()
            .addInput("video.mp4")
            .done()
            .addInput("logo.png")
            .done()
            .addOutput("watermarked.mp4")
            .setComplexFilter("overlay=10:10")
            .setVideoCodec("libx264")
            .done();
    // <!-- /example -->

    String expected =
        "ffmpeg -y -v error"
            + " -i video.mp4 -i logo.png"
            + " -filter_complex overlay=10:10"
            + " -vcodec libx264"
            + " watermarked.mp4";
    String actual = Joiner.on(" ").join(ffmpeg.path(builder.build()));
    assertEquals(expected, actual);
  }
}
