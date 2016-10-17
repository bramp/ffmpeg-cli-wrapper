package net.bramp.ffmpeg.nut;

import com.google.common.base.MoreObjects;

public class FrameCode {

  static final long FLAG_KEY = 1 << 0;
  static final long FLAG_EOR = 1 << 1;
  static final long FLAG_CODED_PTS = 1 << 3;
  static final long FLAG_STREAM_ID = 1 << 4;
  static final long FLAG_SIZE_MSB = 1 << 5;
  static final long FLAG_CHECKSUM = 1 << 6;
  static final long FLAG_RESERVED = 1 << 7;
  static final long FLAG_SM_DATA = 1 << 8;
  static final long FLAG_HEADER_IDX = 1 << 10;
  static final long FLAG_MATCH_TIME = 1 << 11;
  static final long FLAG_CODED = 1 << 12;
  static final long FLAG_INVALID = 1 << 13;

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
