package net.bramp.ffmpeg.probe;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.bramp.ffmpeg.shared.CodecType;

@SuppressFBWarnings(
    value = {"UUF_UNUSED_PUBLIC_OR_PROTECTED_FIELD"},
    justification = "POJO objects where the fields are populated by gson")
public class FFmpegPacket implements FFmpegFrameOrPacket {
  public CodecType codec_type;
  public int stream_index;
  public long pts;
  public double pts_time;
  public long dts;
  public double dts_time;
  public long duration;
  public float duration_time;
  public String size;
  public String pos;
  public String flags;

  public CodecType getCodecType() {
    return codec_type;
  }

  public int getStreamIndex() {
    return stream_index;
  }

  public long getPts() {
    return pts;
  }

  public double getPtsTime() {
    return pts_time;
  }

  public long getDts() {
    return dts;
  }

  public double getDtsTime() {
    return dts_time;
  }

  public long getDuration() {
    return duration;
  }

  public float getDurationTime() {
    return duration_time;
  }

  public String getSize() {
    return size;
  }

  public String getPos() {
    return pos;
  }

  public String getFlags() {
    return flags;
  }
}
