package net.bramp.ffmpeg.nut;

import com.google.common.base.MoreObjects;

/** Represents a frame code table entry in the NUT multimedia container format. */
public class FrameCode {

  long flags;
  int streamId;
  int dataSizeMul;
  int dataSizeLsb;
  long ptsDelta;
  int reservedCount;
  long matchTimeDelta;
  int headerIdx;

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("flags", flags)
        .add("id", streamId)
        .add("dataSizeMul", dataSizeMul)
        .add("dataSizeLsb", dataSizeLsb)
        .add("ptsDelta", ptsDelta)
        .add("reservedCount", reservedCount)
        .add("matchTimeDelta", matchTimeDelta)
        .add("headerIdx", headerIdx)
        .toString();
  }
}
