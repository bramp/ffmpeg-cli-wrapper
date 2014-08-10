package net.bramp.ffmpeg;

import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * TODO Change this test to not have hardcoded paths
 */
public class FFmpegExecutorTest {

	@Rule
	public Timeout timeout = new Timeout(30000);

	FFmpeg ffmpeg = new FFmpeg();
	FFprobe ffprobe = new FFprobe();
	
	ExecutorService executor = Executors.newSingleThreadExecutor();

	public FFmpegExecutorTest() throws IOException {}

	@Test
	public void testTwoPass() throws InterruptedException, ExecutionException, IOException {
		String input = "/home/bramp/personal/ffmpeg/samples/mobileedge_1280x720.mp4";
		FFmpegProbeResult in = ffprobe.probe(input);

		FFmpegBuilder builder = new FFmpegBuilder()
			.setInput(in)
			.overrideOutputFiles(true)
			.addOutput("/home/bramp/personal/ffmpeg/samples/output.mp4")
				.setFormat("mp4")
				.disableAudio()
				.setVideoCodec("libx264")
				.setVideoFrameRate(FFmpeg.FPS_30)
				.setVideoResolution(320, 240)
				.setTargetSize(1024 * 1024)
				.done();

		FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

		FFmpegJob job = executor.createTwoPassJob(builder);
		runAndWait(job);
	}


	@Test
	public void testFilter() throws InterruptedException, ExecutionException, IOException {
		String input = "/home/bramp/personal/ffmpeg/samples/mobileedge_1280x720.mp4";

		FFmpegBuilder builder = new FFmpegBuilder()
				.setInput(input)
				.overrideOutputFiles(true)
				.addOutput("/home/bramp/personal/ffmpeg/samples/output.mp4")
				.setFormat("mp4")
				.disableAudio()
				.setVideoCodec("libx264")
				.setVideoFilter("scale=320:trunc(ow/a/2)*2")
				.done();

		FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

		FFmpegJob job = executor.createJob(builder);
		runAndWait(job);
	}

	protected void runAndWait(FFmpegJob job) throws ExecutionException, InterruptedException {
		Future<?> future = executor.submit(job);

		while (!future.isDone()) {
			try {
				future.get(100, TimeUnit.MILLISECONDS);
				break;
			} catch (TimeoutException e) {}
		}
	}
}
