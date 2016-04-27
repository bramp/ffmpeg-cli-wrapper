package net.bramp.ffmpeg.job;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.callback.FfmpegCallback;

public class TwoPassFFmpegJob extends FFmpegJob {

	final static Logger LOG = LoggerFactory.getLogger(TwoPassFFmpegJob.class);

	final String passlogPrefix;
	final List<String> args1;
	FfmpegCallback ffmpegCallback;
	final List<String> args2;

	public TwoPassFFmpegJob(FFmpeg ffmpeg, String passlogPrefix, List<String> args1, List<String> args2,
			FfmpegCallback ffmpegCallback) {
		super(ffmpeg);
		this.ffmpegCallback = ffmpegCallback;
		this.passlogPrefix = passlogPrefix;
		this.args1 = args1;
		this.args2 = args2;
	}

	public TwoPassFFmpegJob(FFmpeg ffmpeg, String passlogPrefix, List<String> args1, List<String> args2) {
		super(ffmpeg);
		this.passlogPrefix = passlogPrefix;
		this.args1 = args1;
		this.args2 = args2;
	}

	protected void deletePassLog() throws IOException {
		Path path = FileSystems.getDefault().getPath(passlogPrefix);

		Files.deleteIfExists(path);
	}

	private boolean callback() {
		boolean callbackValid = (ffmpegCallback != null);
		if (callbackValid)
			ffmpegCallback.callback(state);
		return callbackValid;
	}

	public void run() {
		state = State.RUNNING;

		try {
			ffmpeg.run(args1);
			ffmpeg.run(args2);

			deletePassLog();

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
}
