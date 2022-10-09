package net.bramp.ffmpeg.probe;

import com.google.common.collect.ImmutableList;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/** TODO Make this immutable */
@SuppressFBWarnings(
    value = {"UUF_UNUSED_PUBLIC_OR_PROTECTED_FIELD"},
    justification = "POJO objects where the fields are populated by gson")
public class FFmpegProbeResult {
  public FFmpegError error;
  public FFmpegFormat format;
  public List<FFmpegStream> streams;
  public List<FFmpegChapter> chapters;
  public List<FFmpegPacket> packets;
  public List<FFmpegFrame> frames;
  public List<FFmpegFrameOrPacket> packets_and_frames;

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
    if (streams == null) return Collections.emptyList();
    return ImmutableList.copyOf(streams);
  }

  public List<FFmpegChapter> getChapters() {
    if (chapters == null) return Collections.emptyList();
    return ImmutableList.copyOf(chapters);
  }

  public List<FFmpegPacket> getPackets() {
    if (packets == null) {
      if (packets_and_frames != null) {
        packets = packets_and_frames
            .stream()
            .filter(FFmpegPacket.class::isInstance)
            .map(FFmpegPacket.class::cast)
            .collect(Collectors.toList());
      } else {
        return Collections.emptyList();
      }
    }

    return ImmutableList.copyOf(packets);
  }

  public List<FFmpegFrame> getFrames() {
    if (frames == null) {
      if (packets_and_frames != null) {
        frames = packets_and_frames
            .stream()
            .filter(FFmpegFrame.class::isInstance)
            .map(FFmpegFrame.class::cast)
            .collect(Collectors.toList());
      } else {
        return Collections.emptyList();
      }
    }
    return ImmutableList.copyOf(frames);
  }
}
