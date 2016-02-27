package net.bramp.ffmpeg.probe;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * TODO Make this immutable
 */
public class FFmpegProbeResult {
	public FFmpegError error;
	public FFmpegFormat format;
	public List<FFmpegStream> streams;

    public FFmpegError getError() {
        return error;
    }

    public boolean hasError() {
    return error != null;
  }


  public FFmpegFormat getFormat() {
        return format;
    }

    public List<FFmpegStream> getStreams() {
        return ImmutableList.copyOf(streams);
    }
}
