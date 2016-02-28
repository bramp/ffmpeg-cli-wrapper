package net.bramp.ffmpeg;

import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.io.IOException;
import java.util.concurrent.*;

import static org.junit.Assert.assertFalse;

public class FFmpegExecutorTest {

	@Rule
	public Timeout timeout = new Timeout(30000);

	final FFmpeg ffmpeg = new FFmpeg();
	final FFprobe ffprobe = new FFprobe();

	final ExecutorService executor = Executors.newSingleThreadExecutor();

	public FFmpegExecutorTest() throws IOException {
	}

	@Test
	public void testTwoPass() throws InterruptedException, ExecutionException,
			IOException {
		FFmpegProbeResult in = ffprobe.probe(Samples.big_buck_bunny_720p_1mb);

		assertFalse(in.hasError());

		FFmpegBuilder builder = new FFmpegBuilder().setInput(in)
				.overrideOutputFiles(true).addOutput(Samples.output_mp4)
				.setFormat("mp4").disableAudio().setVideoCodec("mpeg4")
				.setVideoFrameRate(FFmpeg.FPS_30).setVideoResolution(320, 240)
				.setTargetSize(1024 * 1024).done();

		FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

		FFmpegJob job = executor.createTwoPassJob(builder);
		runAndWait(job);
	}

	@Test
	public void testFilter() throws InterruptedException, ExecutionException,
			IOException {

		FFmpegBuilder builder = new FFmpegBuilder()
				.setInput(Samples.big_buck_bunny_720p_1mb)
				.overrideOutputFiles(true).addOutput(Samples.output_mp4)
				.setFormat("mp4").disableAudio().setVideoCodec("mpeg4")
				.setVideoFilter("scale=320:trunc(ow/a/2)*2").done();

		FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

		FFmpegJob job = executor.createJob(builder);
		runAndWait(job);
	}

	protected void runAndWait(FFmpegJob job) throws ExecutionException,
			InterruptedException {
		Future<?> future = executor.submit(job);

		while (!future.isDone()) {
			try {
				future.get(100, TimeUnit.MILLISECONDS);
				break;
			} catch (TimeoutException e) {
			}
		}
	}
}
