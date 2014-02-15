package net.bramp.ffmpeg;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
			.overrideOutputFiles(true)
			.addOutput("output")
				.setFormat("mp4")
				.setAudioCodec("aac")
				.setAudioChannels(1)
				.setAudioRate(48000)
				.setVideoCodec("libx264")
				.setVideoFramerate(FFmpeg.FPS_30)
				.setVideoResolution(320, 240)
				.done();

		List<String> args = builder.build();
		assertThat(args, is(Arrays.asList("-y", "-v", "error", "-i", "input", "-f", "mp4", "-vcodec", "libx264", "-s", "320x240", "-r", "30/1", "-acodec", "aac", "-ac", "1", "-ar", "48000", "output")));
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

	@Test(expected=IllegalArgumentException.class)
	public void testNothing() {
		FFmpegBuilder builder = new FFmpegBuilder();
		builder.build();
	}
}
