package net.bramp.ffmpeg.job;

import java.util.List;

import net.bramp.ffmpeg.FFmpeg;

import com.google.common.base.Throwables;

public class SinglePassFFmpegJob extends FFmpegJob {

	final List<String> args;

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
			Throwables.propagate(t);
		}
	}
}
