package net.bramp.ffmpeg;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Locale;

import org.junit.Test;

import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.fixtures.Samples;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;

/**
 * Ensures the examples in the README continue to work.
 */
public class ReadmeTest {

  final Locale locale = Locale.US;
  final FFmpeg ffmpeg = new FFmpeg();
  final FFprobe ffprobe = new FFprobe();
 
  public ReadmeTest() throws IOException {}
  
  @Test
  public void testVideoEncoding() throws IOException {

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

  @Test
  public void testGetMediaInformation() throws IOException {
    FFmpegProbeResult probeResult = ffprobe.probe(Samples.big_buck_bunny_720p_1mb);

    FFmpegFormat format = probeResult.getFormat();
    String line1 =
        String.format(locale, "File: '%s' ; Format: '%s' ; Duration: %.3fs", format.filename,
            format.format_long_name, format.duration);

    FFmpegStream stream = probeResult.getStreams().get(0);
    String line2 =
        String.format(locale, "Codec: '%s' ; Width: %dpx ; Height: %dpx", stream.codec_long_name,
            stream.width, stream.height);

    assertThat(
        line1,
        is("File: 'src/test/resources/net/bramp/ffmpeg/samples/big_buck_bunny_720p_1mb.mp4' ; Format: 'QuickTime / MOV' ; Duration: 5.312s"));
    assertThat(line2,
        is("Codec: 'H.264 / AVC / MPEG-4 AVC / MPEG-4 part 10' ; Width: 1280px ; Height: 720px"));
  }
}
