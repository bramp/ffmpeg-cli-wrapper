package net.bramp.ffmpeg;

import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.fixtures.Samples;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.junit.Test;

import java.io.IOException;

/**
 * Ensures the example in the readme continues to work
 */
public class ReadmeTest {

  @Test
  public void test() throws IOException {
    FFmpeg ffmpeg = new FFmpeg(FFmpeg.DEFAULT_PATH);
    FFprobe ffprobe = new FFprobe(FFprobe.DEFAULT_PATH);

    // String in = Samples.big_buck_bunny_720p_1mb;
    FFmpegProbeResult in = ffprobe.probe(Samples.big_buck_bunny_720p_1mb);

    // @formatter:off
    FFmpegBuilder builder = new FFmpegBuilder()

      .setInput(in)              // Filename, or a FFmpegProbeResult
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
}
