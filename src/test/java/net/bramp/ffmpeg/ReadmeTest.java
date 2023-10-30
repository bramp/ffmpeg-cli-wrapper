package net.bramp.ffmpeg;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Joiner;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.fixtures.Samples;
import net.bramp.ffmpeg.job.FFmpegJob;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;
import net.bramp.ffmpeg.progress.Progress;
import net.bramp.ffmpeg.progress.ProgressListener;
import org.junit.Test;

/** Ensures the examples in the README continue to work. */
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

    FFmpegBuilder builder =
        new FFmpegBuilder()
            .setInput(inFilename) // Filename, or a FFmpegProbeResult
            .setInput(in)
            .overrideOutputFiles(true) // Override the output if it exists
            .addOutput("output.mp4") // Filename for the destination
            .setFormat("mp4") // Format is inferred from filename, or can be set
            .setTargetSize(250_000) // Aim for a 250KB file
            .disableSubtitle() // No subtiles
            .setAudioChannels(1) // Mono audio
            .setAudioCodec("aac") // using the aac codec
            .setAudioSampleRate(48_000) // at 48KHz
            .setAudioBitRate(32768) // at 32 kbit/s
            .setVideoCodec("libx264") // Video using x264
            .setVideoFrameRate(24, 1) // at 24 frames per second
            .setVideoResolution(640, 480) // at 640x480 resolution
            .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL) // Allow FFmpeg to use experimental specs
            .done();

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
        String.format(
            locale,
            "File: '%s' ; Format: '%s' ; Duration: %.3fs",
            format.filename,
            format.format_long_name,
            format.duration);

    FFmpegStream stream = probeResult.getStreams().get(0);
    String line2 =
        String.format(
            locale,
            "Codec: '%s' ; Width: %dpx ; Height: %dpx",
            stream.codec_long_name,
            stream.width,
            stream.height);

    assertThat(
        line1,
        is(
            "File: 'src/test/resources/net/bramp/ffmpeg/samples/big_buck_bunny_720p_1mb.mp4' ; Format: 'QuickTime / MOV' ; Duration: 5.312s"));
    assertThat(
        line2,
        is("Codec: 'H.264 / AVC / MPEG-4 AVC / MPEG-4 part 10' ; Width: 1280px ; Height: 720px"));
  }

  @Test
  public void testProgress() throws IOException {
    FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

    final FFmpegProbeResult in = ffprobe.probe(Samples.big_buck_bunny_720p_1mb);

    FFmpegBuilder builder =
        new FFmpegBuilder()
            .setInput(in) // Or filename
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
  public void testHLSVideoEncoding() throws IOException {
    long start = System.currentTimeMillis();
    FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
    FFmpegProbeResult in = ffprobe.probe(Samples.big_buck_bunny_720p_1mb);
    FFmpegBuilder builder = new FFmpegBuilder()
            .setInput(in)
            .overrideOutputFiles(true)
            .addOutput("src/test/resources/net/bramp/ffmpeg/samples/%v/index.m3u8")
            .setPreset("slow")
            .addExtraArgs("-g", "48")
            .addExtraArgs("-sc_threshold","0")
            .addExtraArgs("-map","0:0")
            .addExtraArgs("-map","0:1")
            .addExtraArgs("-map","0:0")
            .addExtraArgs("-map","0:1")
            .addExtraArgs("-map","0:0")
            .addExtraArgs("-map","0:1")
            .addExtraArgs("-map","0:0")
            .addExtraArgs("-map","0:1")
            .addExtraArgs("-map","0:0")
            .addExtraArgs("-map","0:1")
            .addExtraArgs("-map","0:0")
            .addExtraArgs("-map","0:1")
            .addExtraArgs("-s:v:0","1920*1080")
            .addExtraArgs("-b:v:0","1800k")
            .addExtraArgs("-s:v:1","1280*720")
            .addExtraArgs("-b:v:1","1200k")
            .addExtraArgs("-s:v:2","858*480")
            .addExtraArgs("-b:v:2","750k")
            .addExtraArgs("-s:v:3","630*360")
            .addExtraArgs("-b:v:3","550k")
            .addExtraArgs("-s:v:4","426*240")
            .addExtraArgs("-b:v:4","400k")
            .addExtraArgs("-s:v:5","256*144")
            .addExtraArgs("-b:v:5","200k")
            .addExtraArgs("-c:a","copy")
            .addExtraArgs("-var_stream_map","v:0,a:0,name:1080p v:1,a:1,name:720p v:2,a:2,name:480p v:3,a:3,name:360p v:4,a:4,name:240p v:5,a:5,name:144p")
            .addExtraArgs("-master_pl_name","master.m3u8")
            .addExtraArgs("-f","hls")
            .addExtraArgs("-hls_time","10")
            .addExtraArgs("-hls_playlist_type","vod")
            .addExtraArgs("-hls_list_size","0")
            .addExtraArgs("-hls_segment_filename","src/test/resources/net/bramp/ffmpeg/samples/%v/segment%d.ts")
            .done();
    String actual = Joiner.on(" ").join(ffmpeg.path(builder.build()));
    System.out.println("actual "+actual);
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
    long end = System.currentTimeMillis();
    long execution = (end - start)/1000;
    System.out.println("Execution time: " + execution + " seconds");
  }
}
