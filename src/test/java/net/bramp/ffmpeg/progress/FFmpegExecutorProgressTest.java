package net.bramp.ffmpeg.progress;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.fixtures.Samples;
import net.bramp.ffmpeg.job.FFmpegJob;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class FFmpegExecutorProgressTest {

  @Rule
  public Timeout timeout = new Timeout(30, TimeUnit.SECONDS);

  final FFmpeg ffmpeg = new FFmpeg();
  final FFprobe ffprobe = new FFprobe();

  final ExecutorService executor = Executors.newSingleThreadExecutor();

  public FFmpegExecutorProgressTest() throws IOException {}

  @Test
  public void testProgress() throws InterruptedException, ExecutionException, IOException {
    FFmpegProbeResult in = ffprobe.probe(Samples.big_buck_bunny_720p_1mb);

    assertFalse(in.hasError());

    FFmpegBuilder builder =
        new FFmpegBuilder().readAtNativeFrameRate()
            // Slows the test down
            .setVerbosity(FFmpegBuilder.Verbosity.DEBUG).setInput(in).overrideOutputFiles(true)
            .addOutput(Samples.output_mp4).done();

    FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

    FFmpegJob job = executor.createJob(builder);
    runAndWait(job);

    assertEquals(FFmpegJob.State.FINISHED, job.getState());
  }

  protected void runAndWait(FFmpegJob job) throws ExecutionException, InterruptedException {
    executor.submit(job).get();
  }
}
