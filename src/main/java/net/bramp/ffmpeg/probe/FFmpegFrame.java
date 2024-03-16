package net.bramp.ffmpeg.probe;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.bramp.ffmpeg.shared.CodecType;

@SuppressFBWarnings(
    value = {"UUF_UNUSED_PUBLIC_OR_PROTECTED_FIELD"},
    justification = "POJO objects where the fields are populated by gson")
public class FFmpegFrame implements FFmpegFrameOrPacket {
  public CodecType media_type;
  public int stream_index;
  public int key_frame;
  public long pkt_pts;
  public double pkt_pts_time;
  public long pkt_dts;
  public double pkt_dts_time;
  public long best_effort_timestamp;
  public float best_effort_timestamp_time;
  public long pkt_duration;
  public float pkt_duration_time;
  public long pkt_pos;
  public long pkt_size;
  public String sample_fmt;
  public int nb_samples;
  public int channels;
  public String channel_layout;
}
