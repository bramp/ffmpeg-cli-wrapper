package net.bramp.ffmpeg.probe;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.Map;

@SuppressFBWarnings(
  value = {"UUF_UNUSED_PUBLIC_OR_PROTECTED_FIELD", "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"},
  justification = "POJO objects where the fields are populated by gson"
)
public abstract class FFmpegFrame {
  public String media_type;
  public int stream_index;
  public int key_frame;

  public int pkt_pts;
  public double pkt_pts_time;
  public int pkt_dts;
  public double pkt_dts_time;

  public int best_effort_timestamp;
  public double best_effort_timestamp_time;

  public int pkt_duration;
  // Question Change this to java.time.Duration?
  public double pkt_duration_time;

  public int pkt_pos;
  public int pkt_size;
}
