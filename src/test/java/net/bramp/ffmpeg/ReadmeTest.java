package net.bramp.ffmpeg;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.builder.HlsVariant;
import net.bramp.ffmpeg.builder.Strict;
import net.bramp.ffmpeg.fixtures.Samples;
import net.bramp.ffmpeg.job.FFmpegJob;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;
import net.bramp.ffmpeg.progress.Progress;
import net.bramp.ffmpeg.progress.ProgressListener;
import org.junit.Test;

/**
 * These tests are those that appear in the README.md. They are listed here to ensure they always
 * compile and run.
 */
public class ReadmeTest {

  final FFmpeg ffmpeg = new FFmpeg();
  final FFprobe ffprobe = new FFprobe();

  final Locale locale = Locale.US;

  public ReadmeTest() throws IOException {}

  @Test
  public void testSimpleVideoEncoding() throws IOException {
    // <!-- example: Simple Video Encoding -->
    // Encode a video to MP4 using x264/aac with a target file size.

    // <!-- readme: Video Encoding -->
    FFmpegProbeResult in = ffprobe.probe(Samples.big_buck_bunny_720p_1mb);

    FFmpegBuilder builder =
        new FFmpegBuilder()
            // Input
            .setInput(in) // Filename, or a FFmpegProbeResult
            .done()

            // Output
            .overrideOutputFiles(true) // Override the output if it exists
            .addOutput("output.mp4") // Filename for the destination
            .setFormat("mp4") // Format is inferred from filename, or can be set
            .setTargetSize(250_000) // Aim for a 250KB file

            // No subtiles
            .disableSubtitle()

            // Audio
            .setAudioChannels(1) // Mono audio
            .setAudioCodec("aac") // using the aac codec
            .setAudioSampleRate(48_000) // at 48KHz
            .setAudioBitRate(32768) // at 32 kbit/s

            // Video
            .setVideoCodec("libx264") // Video using x264
            .setVideoFrameRate(24, 1) // at 24 frames per second
            .setVideoResolution(640, 480) // at 640x480 resolution

            // Allow FFmpeg to use experimental specs (such as x264 / aac encoders)
            .setStrict(Strict.EXPERIMENTAL)
            .done();

    FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

    // Run a one-pass encode
    executor.createJob(builder).run();

    // Or run a two-pass encode (which is better quality at the cost of being slower)
    executor.createTwoPassJob(builder).run();
    // <!-- /readme -->
    // <!-- /example -->
  }

  @Test
  public void testGetMediaInformation() throws IOException {
    // <!-- example: Get Media Information -->
    // Use FFprobe to inspect format and stream details of a media file.

    // <!-- readme: Get Media Information -->
    FFmpegProbeResult probeResult = ffprobe.probe(Samples.big_buck_bunny_720p_1mb);

    FFmpegFormat format = probeResult.getFormat();
    System.out.format(
        "%nFile: '%s' ; Format: '%s' ; Duration: %.3fs",
        format.filename, format.format_long_name, format.duration);

    FFmpegStream stream = probeResult.getStreams().get(0);
    System.out.format(
        "%nCodec: '%s' ; Width: %dpx ; Height: %dpx",
        stream.codec_long_name, stream.width, stream.height);
    // <!-- /readme -->
    // <!-- /example -->
  }

  @Test
  public void testGetProgressWhileEncoding() throws IOException {
    // <!-- example: Get Progress While Encoding -->
    // Track encoding progress using a ProgressListener.

    // <!-- readme: Get progress while encoding -->
    FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

    FFmpegProbeResult in = ffprobe.probe(Samples.big_buck_bunny_720p_1mb);

    FFmpegBuilder builder =
        new FFmpegBuilder()
            .setInput(in) // Or filename
            .done()
            .addOutput("output.mp4")
            .done();

    FFmpegJob job =
        executor.createJob(
            builder,
            new ProgressListener() {

              // Using the FFmpegProbeResult determine the duration of the input
              final double duration_ns = in.getFormat().duration * TimeUnit.SECONDS.toNanos(1);

              @Override
              public void progress(Progress progress) {
                double percentage = progress.out_time_ns / duration_ns;

                // Print out interesting information about the progress
                System.out.println(
                    String.format(
                        "[%.0f%%] status:%s frame:%d time:%s fps:%.0f speed:%.2fx",
                        percentage * 100,
                        progress.status,
                        progress.frame,
                        FFmpegUtils.toTimecode(progress.out_time_ns, TimeUnit.NANOSECONDS),
                        progress.fps.doubleValue(),
                        progress.speed));
              }
            });

    job.run();
    // <!-- /readme -->
    // <!-- /example -->

    assertEquals(FFmpegJob.State.FINISHED, job.getState());
  }

  @Test
  public void testTwoPassVideoEncoding() throws IOException {
    // <!-- example: Two-Pass Encoding -->
    // Two-pass encoding produces higher quality at the cost of being slower.
    // The executor handles creating and cleaning up passlog files.
    FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
    FFmpegProbeResult in = ffprobe.probe(Samples.big_buck_bunny_720p_1mb);

    FFmpegBuilder builder =
        new FFmpegBuilder()
            .overrideOutputFiles(true)
            .setInput(in)
            .done()
            .addOutput("output.mp4")
            .setFormat("mp4")
            .setTargetSize(250_000)
            .disableSubtitle()
            .setAudioCodec("aac")
            .setAudioChannels(1)
            .setAudioSampleRate(48_000)
            .setAudioBitRate(32768)
            .setVideoCodec("libx264")
            .setVideoFrameRate(24, 1)
            .setVideoResolution(640, 480)
            .setStrict(Strict.EXPERIMENTAL)
            .done();

    FFmpegJob job = executor.createTwoPassJob(builder);
    job.run();
    // <!-- /example -->

    assertEquals(FFmpegJob.State.FINISHED, job.getState());
  }

  @Test
  public void testHLSVideoEncoding() throws IOException {
    // <!-- example: HLS Multi-Variant Streaming -->
    // Create an HLS adaptive bitrate stream with multiple quality variants.
    // Generates a master playlist and per-variant segment files.
    FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
    FFmpegProbeResult in = ffprobe.probe(Samples.big_buck_bunny_720p_1mb);

    Path tempDir = Files.createTempDirectory("ffmpeg-hls");

    FFmpegBuilder builder =
        new FFmpegBuilder()
            .overrideOutputFiles(true)
            .setInput(in)
            .done()
            // The %v placeholder is replaced by the variant index (0, 1, etc.)
            .addHlsOutput(tempDir.resolve("var-%v/index.m3u8").toString())
            .setHlsTime(10, TimeUnit.SECONDS)
            .setHlsPlaylistType("vod")
            .setHlsListSize(0)
            .setHlsSegmentFileName(tempDir.resolve("var-%v/segment%d.ts").toString())
            .setMasterPlName("master.m3u8")
            .addVariant(new HlsVariant().addVideo(0).addAudio(0).setName("1080p"))
            .addVariant(new HlsVariant().addVideo(1).addAudio(1).setName("720p"))
            .addVariant(new HlsVariant().addVideo(2).addAudio(2).setName("480p"))
            .addVariant(new HlsVariant().addVideo(3).addAudio(3).setName("360p"))
            .addVariant(new HlsVariant().addVideo(4).addAudio(4).setName("240p"))
            .addVariant(new HlsVariant().addVideo(5).addAudio(5).setName("144p"))
            .setPreset("slow")
            .addExtraArgs("-g", "48")
            .addExtraArgs("-sc_threshold", "0")
            // Map the same input multiple times for different variants
            .addExtraArgs("-map", "0:0", "-map", "0:1") // 1080p
            .addExtraArgs("-map", "0:0", "-map", "0:1") // 720p
            .addExtraArgs("-map", "0:0", "-map", "0:1") // 480p
            .addExtraArgs("-map", "0:0", "-map", "0:1") // 360p
            .addExtraArgs("-map", "0:0", "-map", "0:1") // 240p
            .addExtraArgs("-map", "0:0", "-map", "0:1") // 144p
            // Specific settings for each variant
            .addExtraArgs("-s:v:0", "1920x1080", "-b:v:0", "1800k")
            .addExtraArgs("-s:v:1", "1280x720", "-b:v:1", "1200k")
            .addExtraArgs("-s:v:2", "858x480", "-b:v:2", "750k")
            .addExtraArgs("-s:v:3", "630x360", "-b:v:3", "550k")
            .addExtraArgs("-s:v:4", "426x240", "-b:v:4", "400k")
            .addExtraArgs("-s:v:5", "256x144", "-b:v:5", "200k")
            .setAudioCodec("copy")
            .done();

    FFmpegJob job =
        executor.createJob(
            builder,
            new ProgressListener() {
              final double duration_ns = in.getFormat().duration * TimeUnit.SECONDS.toNanos(1);

              @Override
              public void progress(Progress progress) {
                double percentage = progress.out_time_ns / duration_ns;
                System.out.println(
                    String.format(
                        locale,
                        "[%.0f%%] status:%s frame:%d time:%s fps:%.0f speed:%.2fx",
                        percentage * 100,
                        progress.status,
                        progress.frame,
                        FFmpegUtils.toTimecode(progress.out_time_ns, TimeUnit.NANOSECONDS),
                        progress.fps.doubleValue(),
                        progress.speed));
              }
            });

    job.run();
    // <!-- /example -->

    assertEquals(FFmpegJob.State.FINISHED, job.getState());
  }
}
