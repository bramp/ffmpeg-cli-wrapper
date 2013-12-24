package net.bramp.ffmpeg.info;

import java.util.List;

public class FFmpegProbeResult {
	public FFmpegError error;
	public FFmpegFormat format;
	public List<FFmpegStream> streams;
}
