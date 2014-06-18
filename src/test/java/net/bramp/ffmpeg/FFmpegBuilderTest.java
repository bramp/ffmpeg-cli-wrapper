package net.bramp.ffmpeg;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.bramp.ffmpeg.builder.FFmpegBuilder;

import org.junit.Test;

/**
 * -psnr
 * @author bramp
 *
 */
public class FFmpegBuilderTest {

	public FFmpegBuilderTest() throws IOException {}

	@Test
	public void testNormal() {

		FFmpegBuilder builder = new FFmpegBuilder()
			.setInput("input")
            .setStartOffset(1500, TimeUnit.MILLISECONDS)
			.overrideOutputFiles(true)
			.addOutput("output")
				.setFormat("mp4")
                .setStartOffset(500, TimeUnit.MILLISECONDS)
				.setAudioCodec("aac")
				.setAudioChannels(1)
				.setAudioRate(48000)
				.setVideoCodec("libx264")
				.setVideoFramerate(FFmpeg.FPS_30)
				.setVideoResolution(320, 240)
				.done();

		List<String> args = builder.build();
		assertThat(args, is(Arrays.asList(
            "-y", "-v", "error", "-ss", "1.500", "-i", "input",
                "-f", "mp4", "-ss", "0.500",
                "-vcodec", "libx264", "-s", "320x240", "-r", "30/1",
                "-acodec", "aac", "-ac", "1", "-ar", "48000",
                "output"
        )));
	}

	@Test
	public void testDisabled() {

		FFmpegBuilder builder = new FFmpegBuilder()
			.setInput("input")
			.addOutput("output")
				.disableAudio()
				.disableSubtitle()
				.disableVideo()
				.done();

		List<String> args = builder.build();
		assertThat(args, is(Arrays.asList("-y", "-v", "error", "-i", "input", "-vn", "-an", "-sn", "output")));
	}

	@Test
	public void testFilter() {

		FFmpegBuilder builder = new FFmpegBuilder()
				.setInput("input")
				.addOutput("output")
				.disableAudio()
				.disableSubtitle()
				.disableVideo()
				.setFilter("scale='trunc(ow/a/2)*2:320'")
				.done();

		List<String> args = builder.build();
		assertThat(args, is(Arrays.asList("-y", "-v", "error", "-i", "input", "-vn", "-an", "-sn", "-vf", "scale='trunc(ow/a/2)*2:320'", "output")));
	}
	
	@Test
	public void testFilter_and_scale() {

		FFmpegBuilder builder = new FFmpegBuilder()
				.setInput("input")
				.addOutput("output")
				.setVideoResolution(320, 240)
				.setFilter("scale='trunc(ow/a/2)*2:320'")
				.done();

		List<String> args = builder.build();
		assertThat(args, is(Arrays.asList("-y", "-v", "error", "-i", "input", "-s", "320x240", "-vf", "scale='trunc(ow/a/2)*2:320'", "output")));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testNothing() {
		FFmpegBuilder builder = new FFmpegBuilder();
		builder.build();
	}
}
