package net.bramp.ffmpeg.nut;

import com.google.common.base.MoreObjects;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.lang3.math.Fraction;

/** A video or audio frame */
public class Frame {
  // TODO Change this to a enum
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

  Stream stream;
  long flags;
  long pts;
  byte[] data;

  Map<String, Object> sideData;
  Map<String, Object> metaData;

  protected Map<String, Object> readMetaData(NutDataInputStream in) throws IOException {
    Map<String, Object> data = new TreeMap<String, Object>();
    long count = in.readVarLong();
    for (int i = 0; i < count; i++) {
      String name = new String(in.readVarArray(), StandardCharsets.UTF_8);
      long type = in.readSignedVarInt();
      Object value;

      if (type == -1) {
        value = new String(in.readVarArray(), StandardCharsets.UTF_8);

      } else if (type == -2) {
        String k = new String(in.readVarArray(), StandardCharsets.UTF_8);
        String v = new String(in.readVarArray(), StandardCharsets.UTF_8);
        value = k + "=" + v; // TODO Change this some how

      } else if (type == -3) {
        value = in.readSignedVarInt();

      } else if (type == -4) {
        /*
         * t (v coded universal timestamp) tmp v id= tmp % time_base_count value= (tmp /
         * time_base_count) * timeBase[id]
         */
        value = in.readVarLong(); // TODO Convert to timestamp

      } else if (type < -4) {
        long denominator = -type - 4;
        long numerator = in.readSignedVarInt();
        value = Fraction.getFraction((int) numerator, (int) denominator);

      } else {
        value = type;
      }

      data.put(name, value);
    }
    return data;
  }

  public void read(NutReader nut, NutDataInputStream in, int code) throws IOException {
    if (code == 'N') {
      throw new IOException("Illegal frame code: " + code);
    }

    FrameCode fc = nut.header.frameCodes.get(code);
    flags = fc.flags;
    if ((flags & FLAG_INVALID) == FLAG_INVALID) {
      throw new IOException("Using invalid framecode: " + code);
    }

    if ((flags & FLAG_CODED) == FLAG_CODED) {
      long coded_flags = in.readVarLong();
      flags ^= coded_flags;
    }

    int size = fc.dataSizeLsb;

    int stream_id;
    long coded_pts;
    int header_idx = fc.headerIdx;
    int frame_res = fc.reservedCount;

    if ((flags & FLAG_STREAM_ID) == FLAG_STREAM_ID) {
      stream_id = in.readVarInt();
      if (stream_id >= nut.streams.size()) {
        throw new IOException(
            "Illegal stream id value " + stream_id + " must be < " + nut.streams.size());
      }
    } else {
      stream_id = fc.streamId;
    }

    stream = nut.streams.get(stream_id);

    if ((flags & FLAG_CODED_PTS) == FLAG_CODED_PTS) {
      coded_pts = in.readVarLong();
      if (coded_pts < (1 << stream.header.msbPtsShift)) {
        long mask = (1L << stream.header.msbPtsShift) - 1;
        long delta = stream.last_pts - mask / 2;
        pts = ((coded_pts - delta) & mask) + delta;
      } else {
        pts = coded_pts - (1L << stream.header.msbPtsShift);
      }
    } else {
      // TODO Test this code path
      pts = stream.last_pts + fc.ptsDelta;
    }
    stream.last_pts = pts;

    if ((flags & FLAG_SIZE_MSB) == FLAG_SIZE_MSB) {
      int data_size_msb = in.readVarInt();
      size += fc.dataSizeMul * data_size_msb;
    }
    if ((flags & FLAG_MATCH_TIME) == FLAG_MATCH_TIME) {
      fc.matchTimeDelta = in.readSignedVarInt();
    }
    if ((flags & FLAG_HEADER_IDX) == FLAG_HEADER_IDX) {
      header_idx = in.readVarInt();
      if (header_idx >= nut.header.elision.size()) {
        throw new IOException(
            "Illegal header index " + header_idx + " must be < " + nut.header.elision.size());
      }
    }
    if ((flags & FLAG_RESERVED) == FLAG_RESERVED) {
      frame_res = in.readVarInt();
    }

    for (int i = 0; i < frame_res; i++) {
      in.readVarLong(); // Discard
    }

    if ((flags & FLAG_CHECKSUM) == FLAG_CHECKSUM) {
      @SuppressWarnings("unused")
      long checksum = in.readInt();
      // TODO Test checksum
    }

    if (size > 4096) {
      header_idx = 0;
    }

    // Now data
    if ((flags & FLAG_SM_DATA) == FLAG_SM_DATA) {
      // TODO Test this path.

      if (nut.header.version < 4) {
        throw new IOException("Frame SM Data not allowed in version 4 or less");
      }
      long pos = in.offset();
      sideData = readMetaData(in);
      metaData = readMetaData(in);
      long metadataLen = (in.offset() - pos);
      if (metadataLen > size) {
        throw new EOFException();
      }

      size -= (int) metadataLen;

    } else {
      sideData = null;
      metaData = null;
    }

    // TODO Use some kind of byte pool
    data = new byte[size];

    byte[] elision = nut.header.elision.get(header_idx);
    System.arraycopy(elision, 0, data, 0, elision.length);
    in.readFully(data, elision.length, size - elision.length);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("id", stream.header.id)
        .add("pts", pts)
        .add("data", String.format("(%d bytes)", data.length))
        .toString();
  }
}
