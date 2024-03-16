package net.bramp.ffmpeg.probe;

import com.google.common.collect.ImmutableList;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** TODO Make this immutable */
@SuppressFBWarnings(
    value = {"UUF_UNUSED_PUBLIC_OR_PROTECTED_FIELD"},
    justification = "POJO objects where the fields are populated by gson")
public class FFmpegProbeResult {
  /** @deprecated Use {@link #getError()} instead */
  @Deprecated
  public FFmpegError error;
  /** @deprecated Use {@link #getFormat()} instead */
  @Deprecated
  public FFmpegFormat format;
  /** @deprecated Use {@link #getStreams()} instead */
  @Deprecated
  public List<FFmpegStream> streams;
  /** @deprecated Use {@link #getChapters()} instead */
  @Deprecated
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
        List<FFmpegPacket> tmp = new ArrayList<>();
        for (FFmpegFrameOrPacket packetsAndFrame : packets_and_frames) {
          if (packetsAndFrame instanceof FFmpegPacket) {
            tmp.add((FFmpegPacket) packetsAndFrame);
          }
        }
        packets = tmp;
      } else {
        return Collections.emptyList();
      }
    }

    return ImmutableList.copyOf(packets);
  }

  public List<FFmpegFrame> getFrames() {
    if (frames == null) {
      if (packets_and_frames != null) {
        List<FFmpegFrame> tmp = new ArrayList<>();
        for (FFmpegFrameOrPacket packetsAndFrame : packets_and_frames) {
          if (packetsAndFrame instanceof FFmpegFrame) {
            tmp.add((FFmpegFrame) packetsAndFrame);
          }
        }
        frames = tmp;
      } else {
        return Collections.emptyList();
      }
    }
    return ImmutableList.copyOf(frames);
  }
}
