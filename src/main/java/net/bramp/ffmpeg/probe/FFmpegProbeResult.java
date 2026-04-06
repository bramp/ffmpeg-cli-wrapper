package net.bramp.ffmpeg.probe;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** TODO Make this immutable. */
public class FFmpegProbeResult {
  public FFmpegError error;
  public FFmpegFormat format;
  public List<FFmpegStream> streams;
  public List<FFmpegChapter> chapters;

  private List<FFmpegPacket> packets;
  private List<FFmpegFrame> frames;
  public List<FFmpegFrameOrPacket> packets_and_frames;

  public FFmpegError getError() {
    return error;
  }

  /** Returns whether the probe result contains an error. */
  public boolean hasError() {
    return error != null;
  }

  /** Returns the format information from the probe result. */
  public FFmpegFormat getFormat() {
    return format;
  }

  /** Returns the list of streams in the probe result. */
  public List<FFmpegStream> getStreams() {
    if (streams == null) {
      return Collections.emptyList();
    }
    return ImmutableList.copyOf(streams);
  }

  /** Returns the list of chapters in the probe result. */
  public List<FFmpegChapter> getChapters() {
    if (chapters == null) {
      return Collections.emptyList();
    }
    return ImmutableList.copyOf(chapters);
  }

  /** Returns the list of packets from the probe result. */
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

  /** Returns the list of frames in the probe result. */
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
