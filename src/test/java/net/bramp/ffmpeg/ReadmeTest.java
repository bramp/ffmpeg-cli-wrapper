package net.bramp.ffmpeg;

import com.google.common.base.MoreObjects;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.fixtures.Samples;
import net.bramp.ffmpeg.job.FFmpegJob;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;
import net.bramp.ffmpeg.progress.Progress;
import net.bramp.ffmpeg.progress.ProgressListener;
import org.junit.Test;

import java.io.IOException;
import java.util.Locale;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Ensures the examples in the README continue to work.
 */
public class ReadmeTest {

  final Locale locale = Locale.US;
  final FFmpeg ffmpeg = new FFmpeg();
  final FFprobe ffprobe = new FFprobe();

  public ReadmeTest() throws IOException {}

  @Test
  public void testCreateFF() throws IOException {
    FFmpeg ffmpeg = new FFmpeg(FFmpeg.DEFAULT_PATH);
    FFprobe ffprobe = new FFprobe(FFmpeg.DEFAULT_PATH);

    // Construct them, and do nothing with them
  }


  @Test
  public void testVideoEncoding() throws IOException {

    String inFilename = Samples.big_buck_bunny_720p_1mb;
    FFmpegProbeResult in = ffprobe.probe(inFilename);

    // @formatter:off
    FFmpegBuilder builder = new FFmpegBuilder()

      .setInput(inFilename)      // Filename, or a FFmpegProbeResult
      .setInput(in)
      .overrideOutputFiles(true) // Override the output if it exists

      .addOutput("output.mp4")   // Filename for the destination
        .setFormat("mp4")        // Format is inferred from filename, or can be set
        .setTargetSize(250_000)  // Aim for a 250KB file

        .disableSubtitle()       // No subtiles

        .setAudioChannels(1)         // Mono audio
        .setAudioCodec("aac")        // using the aac codec
        .setAudioSampleRate(48_000)  // at 48KHz
        .setAudioBitRate(32768)      // at 32 kbit/s

        .setVideoCodec("libx264")     // Video using x264
        .setVideoFrameRate(24, 1)     // at 24 frames per second
        .setVideoResolution(640, 480) // at 640x480 resolution

        .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL) // Allow FFmpeg to use experimental specs
        .done();
    // @formatter:on

    FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

    // Run a one-pass encode
    executor.createJob(builder).run();

    // Or run a two-pass encode (which is slower at the cost of better quality
    executor.createTwoPassJob(builder).run();
  }

  @Test
  public void testGetMediaInformation() throws IOException {
    FFmpegProbeResult probeResult = ffprobe.probe(Samples.big_buck_bunny_720p_1mb);

    FFmpegFormat format = probeResult.getFormat();
    String line1 = String.format(locale, "File: '%s' ; Format: '%s' ; Duration: %.3fs",
        format.filename, format.format_long_name, format.duration);

    FFmpegStream stream = probeResult.getStreams().get(0);
    String line2 = String.format(locale, "Codec: '%s' ; Width: %dpx ; Height: %dpx",
        stream.codec_long_name, stream.width, stream.height);

    assertThat(
        line1,
        is("File: 'src/test/resources/net/bramp/ffmpeg/samples/big_buck_bunny_720p_1mb.mp4' ; Format: 'QuickTime / MOV' ; Duration: 5.312s"));
    assertThat(line2,
        is("Codec: 'H.264 / AVC / MPEG-4 AVC / MPEG-4 part 10' ; Width: 1280px ; Height: 720px"));
  }

  @Test
  public void testProgress() throws IOException {
    FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

    final FFmpegProbeResult in = ffprobe.probe(Samples.big_buck_bunny_720p_1mb);

    FFmpegBuilder builder = new FFmpegBuilder()
        .setInput(in) // Or filename
        .addOutput("output.mp4")
        .done();

    FFmpegJob job = executor.createJob(builder, new ProgressListener() {

      // Using the FFmpegProbeResult determine the duraction of the input
      final double duration_us = in.getFormat().duration * 1000000.0;

      @Override
      public void progress(Progress progress) {
        double percentage = progress.out_time_us / duration_us;

        // Print out interesting information about the progress
        System.out.println(String.format(locale,
            "[%.0f%%] status:%s frame:%d time:%d ms fps:%.0f speed:%.2fx",
            percentage * 100,
            progress.status,
            progress.frame,
            progress.out_time_us,
            progress.fps.doubleValue(),
            progress.speed
            ));
      }
    });

    job.run();

    assertEquals(FFmpegJob.State.FINISHED, job.getState());
  }
}
