package net.bramp.ffmpeg.callback;

import net.bramp.ffmpeg.job.FFmpegJob.State;

/**
 * @author mithran alandur
 *         <h1>Callback function to be called on completion</h1>
 */
public interface FfmpegCallback {
	public void callback(State state);
}
