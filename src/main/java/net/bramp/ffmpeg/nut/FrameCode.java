package net.bramp.ffmpeg.nut;

import com.google.common.base.MoreObjects;

public class FrameCode {

  long flags;
  int stream_id;
  int data_size_mul;
  int data_size_lsb;
  long pts_delta;
  int reserved_count;
  long match_time_delta;
  int header_idx;

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("flags", flags).add("stream_id", stream_id)
        .add("data_size_mul", data_size_mul).add("data_size_lsb", data_size_lsb)
        .add("pts_delta", pts_delta).add("reserved_count", reserved_count)
        .add("match_time_delta", match_time_delta).add("header_idx", header_idx).toString();
  }
}
