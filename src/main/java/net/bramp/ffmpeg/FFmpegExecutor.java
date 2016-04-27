package net.bramp.ffmpeg;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.UUID;

import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.callback.FfmpegCallback;
import net.bramp.ffmpeg.job.FFmpegJob;
import net.bramp.ffmpeg.job.SinglePassFFmpegJob;
import net.bramp.ffmpeg.job.TwoPassFFmpegJob;

public class FFmpegExecutor {

	final FFmpeg ffmpeg;
	final FFprobe ffprobe;

	public FFmpegExecutor(FFmpeg ffmpeg, FFprobe ffprobe) {
		this.ffmpeg = checkNotNull(ffmpeg);
		this.ffprobe = checkNotNull(ffprobe);
	}

	public FFmpegJob createJob(FFmpegBuilder builder) {
		final List<String> args = builder.build();
		return new SinglePassFFmpegJob(ffmpeg, args);
	}

	public FFmpegJob createJob(FFmpegBuilder builder, FfmpegCallback callback) {
		final List<String> args = builder.build();
		return new SinglePassFFmpegJob(ffmpeg, args, callback);
	}

	/**
	 * Info: https://trac.ffmpeg.org/wiki/x264EncodingGuide#twopass
	 * 
	 * @param builder
	 * @return A new two-pass FFmpegJob
	 */
	public FFmpegJob createTwoPassJob(FFmpegBuilder builder) {

		// Random prefix so multiple runs don't clash
		String passlogPrefix = UUID.randomUUID().toString();

		// Two pass
		final boolean override = builder.getOverrideOutputFiles();

		final List<String> args1 = builder.setPass(1).setPassPrefix(passlogPrefix).overrideOutputFiles(true).build();

		final List<String> args2 = builder.setPass(2).setPassPrefix(passlogPrefix).overrideOutputFiles(override)
				.build();

		return new TwoPassFFmpegJob(ffmpeg, passlogPrefix, args1, args2);
	}

	/**
	 * Info: https://trac.ffmpeg.org/wiki/x264EncodingGuide#twopass
	 * 
	 * @param builder
	 * @param callback
	 *            Callback function to be called after execution
	 * @return A new two-pass FFmpegJob
	 */
	public FFmpegJob createTwoPassJob(FFmpegBuilder builder, FfmpegCallback callback) {

		// Random prefix so multiple runs don't clash
		String passlogPrefix = UUID.randomUUID().toString();

		// Two pass
		final boolean override = builder.getOverrideOutputFiles();

		final List<String> args1 = builder.setPass(1).setPassPrefix(passlogPrefix).overrideOutputFiles(true).build();

		final List<String> args2 = builder.setPass(2).setPassPrefix(passlogPrefix).overrideOutputFiles(override)
				.build();

		return new TwoPassFFmpegJob(ffmpeg, passlogPrefix, args1, args2);
	}

}
