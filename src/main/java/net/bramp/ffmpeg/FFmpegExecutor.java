package net.bramp.ffmpeg;

import java.util.List;

import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import net.bramp.ffmpeg.job.SinglePassFFmpegJob;
import net.bramp.ffmpeg.job.TwoPassFFmpegJob;

public class FFmpegExecutor {

	final FFmpeg ffmpeg;
	final FFprobe ffprobe;

	public FFmpegExecutor(FFmpeg ffmpeg, FFprobe ffprobe) {
		this.ffmpeg = ffmpeg;
		this.ffprobe = ffprobe;
	}

	public FFmpegJob createJob(FFmpegBuilder builder) {
		// Single Pass
		final List<String> args = builder.build();
		return new SinglePassFFmpegJob(ffmpeg, args);
	}

	/**
	 * Info: https://trac.ffmpeg.org/wiki/x264EncodingGuide#twopass
	 * @param builder
	 * @return
	 */
	public FFmpegJob createTwoPassJob(FFmpegBuilder builder) {

		// Two pass
		final boolean override = builder.getOverrideOutputFiles();

		final List<String> args1 = builder
			.setPass(1).overrideOutputFiles(true)
			.build();

		final List<String> args2 = builder
			.setPass(2).overrideOutputFiles(override)
			.build();

		return new TwoPassFFmpegJob(ffmpeg, args1, args2);
	}
}
