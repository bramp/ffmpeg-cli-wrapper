package net.bramp.ffmpeg.nut;

import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.math.Fraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

/**
 * A video or audio frame
 */
public class Frame {
  final static Logger LOG = LoggerFactory.getLogger(Frame.class);

  Stream stream;
  long pts;
  byte[] data;

  Map<String, Object> side_data;
  Map<String, Object> meta_data;

  protected Map<String, Object> readMetaData(NutDataInputStream in) throws IOException {
    Map<String, Object> data = new TreeMap<String, Object>();
    long count = in.readVarLong();
    for (int i = 0; i < count; i++) {
      byte[] name = in.readVarArray();
      long type = in.readSignedVarInt();
      Object value;

      if (type == -1) {
        value = new String(in.readVarArray(), StandardCharsets.UTF_8);

      } else if (type == -2) {
        String k = new String(in.readVarArray());
        String v = new String(in.readVarArray());
        value = k + "=" + v; // TODO Change this some how

      } else if (type == -3) {
        value = in.readSignedVarInt();

      } else if (type == -4) {
        /*
         * t (v coded universal timestamp) tmp v id= tmp % time_base_count value= (tmp /
         * time_base_count) * time_base[id]
         */
        value = in.readVarLong(); // TODO Convert to timestamp

      } else if (type < -4) {
        long denominator = -type - 4;
        long numerator = in.readSignedVarInt();
        value = Fraction.getFraction((int) numerator, (int) denominator);

      } else {
        value = type;
      }

      data.put(new String(name), value);
    }
    return data;
  }


  public void read(NutReader nut, NutDataInputStream in, int code) throws IOException {
    if (code == 'N') { // TODO should 0/255 be illegal?
      throw new IOException("Illegal frame code: " + code);
    }

    FrameCode fc = nut.header.frameCodes.get(code);
    long frame_flags = fc.flags;
    if ((frame_flags & FrameCode.FLAG_INVALID) == FrameCode.FLAG_INVALID) {
      throw new IOException("Using invalid framecode: " + code);
    }

    if ((frame_flags & FrameCode.FLAG_CODED) == FrameCode.FLAG_CODED) {
      long coded_flags = in.readVarLong();
      frame_flags ^= coded_flags;
    }

    int size = fc.data_size_lsb;

    int stream_id;
    long coded_pts;
    long match_time_delta;
    int header_idx = fc.header_idx;
    int frame_res = fc.reserved_count;

    if ((frame_flags & FrameCode.FLAG_STREAM_ID) == FrameCode.FLAG_STREAM_ID) {
      stream_id = in.readVarInt();
      if (stream_id >= nut.streams.size()) {
        throw new IOException("Illegal stream id value " + stream_id + " must be < "
            + nut.streams.size());
      }
    } else {
      stream_id = fc.stream_id;
    }

    stream = nut.streams.get(stream_id);


    if ((frame_flags & FrameCode.FLAG_CODED_PTS) == FrameCode.FLAG_CODED_PTS) {
      coded_pts = in.readVarLong();
      if (coded_pts < (1 << stream.header.msb_pts_shift)) {
        long mask = (1L << stream.header.msb_pts_shift) - 1;
        long delta = stream.last_pts - mask / 2;
        pts = ((coded_pts - delta) & mask) + delta;
      } else
        pts = coded_pts - (1L << stream.header.msb_pts_shift);
    } else {
      // TODO Test this code path
      pts = stream.last_pts + fc.pts_delta;
    }
    stream.last_pts = pts;

    if ((frame_flags & FrameCode.FLAG_SIZE_MSB) == FrameCode.FLAG_SIZE_MSB) {
      int data_size_msb = in.readVarInt();
      size += fc.data_size_mul * data_size_msb;
    }
    if ((frame_flags & FrameCode.FLAG_MATCH_TIME) == FrameCode.FLAG_MATCH_TIME) {
      match_time_delta = in.readSignedVarInt();
    }
    if ((frame_flags & FrameCode.FLAG_HEADER_IDX) == FrameCode.FLAG_HEADER_IDX) {
      header_idx = in.readVarInt();
      if (header_idx >= nut.header.elision.size()) {
        throw new IOException("Illegal header index " + header_idx + " must be < "
            + nut.header.elision.size());
      }
    }
    if ((frame_flags & FrameCode.FLAG_RESERVED) == FrameCode.FLAG_RESERVED) {
      frame_res = in.readVarInt();
    }

    for (int i = 0; i < frame_res; i++) {
      in.readVarLong(); // Discard
    }

    if ((frame_flags & FrameCode.FLAG_CHECKSUM) == FrameCode.FLAG_CHECKSUM) {
      long checksum = in.readInt();
      // TODO Test checksum
    }

    if (size > 4096) {
      header_idx = 0;
    }

    // Now data
    if ((frame_flags & FrameCode.FLAG_SM_DATA) == FrameCode.FLAG_SM_DATA) {
      // TODO Test this path.

      if (nut.header.version < 4) {
        throw new IOException("Frame SM Data not allowed in version 4 or less");
      }
      long pos = in.offset();
      side_data = readMetaData(in);
      meta_data = readMetaData(in);
      size -= (in.offset() - pos);

    } else {
      side_data = null;
      meta_data = null;
    }

    // TODO Use some kind of byte pool
    data = new byte[size];

    byte[] elision = nut.header.elision.get(header_idx);
    System.arraycopy(elision, 0, data, 0, elision.length);
    in.readFully(data, elision.length, size - elision.length);

    LOG.debug("{}", this);
  }


  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("stream_id", stream.header.stream_id)
        .add("pts", pts).add("data", String.format("(%d bytes)", data.length)).toString();
  }
}
