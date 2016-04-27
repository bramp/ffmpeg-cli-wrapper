package net.bramp.ffmpeg.job;

import java.util.List;

import com.google.common.base.Throwables;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.callback.FfmpegCallback;

public class SinglePassFFmpegJob extends FFmpegJob {

	final List<String> args;
	FfmpegCallback ffmpegCallback;

	public SinglePassFFmpegJob(FFmpeg ffmpeg, List<String> args, FfmpegCallback ffmpegCallback) {
		super(ffmpeg);
		this.ffmpegCallback = ffmpegCallback;
		this.args = args;
	}

	public SinglePassFFmpegJob(FFmpeg ffmpeg, List<String> args) {
		super(ffmpeg);
		this.args = args;
	}

	public void run() {
		state = State.RUNNING;
		try {
			ffmpeg.run(args);
			state = State.FINISHED;

		} catch (Throwable t) {
			state = State.FAILED;
			if (!callback())
				Throwables.propagate(t);

		} finally {
			if (state != State.FAILED)
				callback();
		}
	}

	private boolean callback() {
		boolean callbackValid = (ffmpegCallback != null);
		if (callbackValid)
			ffmpegCallback.callback(state);
		return callbackValid;
	}
}
