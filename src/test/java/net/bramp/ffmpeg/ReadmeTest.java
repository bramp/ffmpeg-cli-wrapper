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
    FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
    FFmpegProbeResult in = ffprobe.probe(Samples.big_buck_bunny_720p_1mb);

    FFmpegBuilder builder =
        new FFmpegBuilder()
            .overrideOutputFiles(true) // Override the output if it exists
            .setInput(in) // Or filename
            .done()
            .addOutput("output.mp4") // Filename for the destination
            .setFormat("mp4") // Format is inferred from filename, or can be set
            .setTargetSize(250_000) // Aim for a 250KB file
            .disableSubtitle() // No subtiles
            .setAudioCodec("aac") // using the aac codec
            .setAudioChannels(1) // mono-channel
            .setAudioSampleRate(48_000) // at 48KHz
            .setAudioBitRate(32768) // at 32 kbit/s
            .setVideoCodec("libx264") // Video using x264
            .setVideoFrameRate(24, 1) // at 24 frames per second
            .setVideoResolution(640, 480) // at 640x480 resolution
            .setStrict(Strict.EXPERIMENTAL) // Allow native AAC codec
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

    assertEquals(FFmpegJob.State.FINISHED, job.getState());
  }

  @Test
  public void testTwoPassVideoEncoding() throws IOException {
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

    assertEquals(FFmpegJob.State.FINISHED, job.getState());
  }

  @Test
  public void testHLSVideoEncoding() throws IOException {
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

    assertEquals(FFmpegJob.State.FINISHED, job.getState());
  }
}
