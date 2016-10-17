package net.bramp.ffmpeg.nut;

import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.math.Fraction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class MainHeaderPacket extends Packet {

  public final static int BROADCAST_MODE = 0;

  long version;
  long minor_version;
  int stream_count;
  long max_distance;
  Fraction[] time_base;
  long flags;

  final List<FrameCode> frameCodes = new ArrayList<>();
  final List<byte[]> elision = new ArrayList<>();

  public MainHeaderPacket() {}

  protected void readBody(NutDataInputStream in) throws IOException {
    frameCodes.clear();

    version = in.readVarLong();
    if (version > 3) {
      minor_version = in.readVarLong();
    }

    stream_count = in.readVarInt();
    if (stream_count >= 250) {
      throw new IOException("Illegal stream count " + stream_count + " must be < 250");
    }

    max_distance = in.readVarLong();
    if (max_distance > 65536) {
      max_distance = 65536;
    }

    int time_base_count = in.readVarInt();
    time_base = new Fraction[time_base_count];
    for (int i = 0; i < time_base_count; i++) {
      int time_base_num = (int) in.readVarLong();
      int time_base_denom = (int) in.readVarLong();
      time_base[i] = Fraction.getFraction(time_base_num, time_base_denom);
    }

    long pts = 0;
    int mul = 1;
    int stream_id = 0;
    int header_idx = 0;

    long match = 1 - (1 << 62);
    int size;
    int reserved;
    long count;

    for (int i = 0; i < 256;) {
      long flags = in.readVarLong();
      long fields = in.readVarLong();
      if (fields > 0) {
        pts = in.readSignedVarInt();
      }
      if (fields > 1) {
        mul = in.readVarInt();
        if (mul >= 16384) {
          throw new IOException("Illegal mul value " + mul + " must be < 16384");
        }
      }
      if (fields > 2) {
        stream_id = in.readVarInt();
        if (stream_id >= stream_count) {
          throw new IOException("Illegal stream id value " + stream_id + " must be < "
              + stream_count);
        }
      }
      if (fields > 3) {
        size = in.readVarInt();
      } else {
        size = 0;
      }
      if (fields > 4) {
        reserved = in.readVarInt();
        if (reserved >= 256) {
          throw new IOException("Illegal reserved frame count " + reserved + " must be < 256");
        }
      } else {
        reserved = 0;
      }
      if (fields > 5) {
        count = in.readVarLong();
      } else {
        count = mul - size;
      }
      if (fields > 6) {
        match = in.readSignedVarInt();
      }
      if (fields > 7) {
        header_idx = in.readVarInt();
      }
      for (int j = 8; j < fields; j++) {
        in.readVarLong(); // Throw away
      }

      if (stream_id >= stream_count) {
        throw new IOException(String.format("Invalid stream value %d, must be < %d", stream_id,
            stream_count));
      }

      if (count <= 0 || (count > 256 - i - (i <= 'N' ? 1 : 0))) {
        throw new IOException(String.format("Invalid count value %d, must be > 0 && < %d", count,
            256 - i - (i <= 'N' ? 1 : 0)));
      }

      for (int j = 0; j < count && i < 256; j++, i++) {
        FrameCode fc = new FrameCode();
        frameCodes.add(fc);

        // Skip 'N' because that is an illegal frame code
        if (i == 'N') {
          fc.flags = FrameCode.FLAG_INVALID;
          j--;
          continue;
        }

        fc.flags = flags;
        fc.stream_id = stream_id;
        fc.data_size_mul = mul;
        fc.data_size_lsb = size + j;
        fc.pts_delta = pts;
        fc.reserved_count = reserved;
        fc.match_time_delta = match;
        fc.header_idx = header_idx;

        if (fc.data_size_lsb >= 16384) {
          throw new IOException("Illegal data_size_lsb value " + fc.data_size_lsb
              + " must be < 16384");
        }
      }
    }

    int remain = 1024;
    if (in.offset() < (header.end - 4)) {
      int header_count = in.readVarInt();
      if (header_count >= 128) {
        throw new IOException("Invalid header_count value " + header_count + " must be < 128");
      }

      elision.clear();
      elision.add(new byte[0]); // First elision is always empty
      for (int i = 1; i < header_count; i++) {
        byte[] e = in.readVarArray();
        if (e.length == 0 || e.length >= 256) {
          throw new IOException("Invalid elision length " + e.length + " must be > 0 and < 256");
        }
        if (e.length > remain) {
          throw new IOException("Invalid elision length value " + e.length + " must be <= "
              + remain);
        }
        remain -= e.length;
        elision.add(e);
      }
    }

    if (version > 3 && (in.offset() < (header.end - 4))) {
      flags = in.readVarLong();
    }
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("header", header).add("version", version)
        .add("minor_version", minor_version).add("stream_count", stream_count)
        .add("max_distance", max_distance).add("time_base", time_base).add("flags", flags)
        .add("frameCodes", frameCodes.size()).add("elision", elision).add("footer", footer)
        .toString();

  }
}
