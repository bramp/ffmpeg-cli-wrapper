package net.bramp.ffmpeg.nut;

import com.google.common.base.MoreObjects;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.math.Fraction;

public class MainHeaderPacket extends Packet {

  public static final int BROADCAST_MODE = 0;

  long version;
  long minorVersion;
  int streamCount;
  long maxDistance;
  Fraction[] timeBase;
  long flags;

  final List<FrameCode> frameCodes = new ArrayList<>();
  final List<byte[]> elision = new ArrayList<>();

  public MainHeaderPacket() {}

  @Override
  protected void readBody(NutDataInputStream in) throws IOException {
    frameCodes.clear();

    version = in.readVarLong();
    if (version > 3) {
      minorVersion = in.readVarLong();
    }

    streamCount = in.readVarInt();
    if (streamCount >= 250) {
      throw new IOException("Illegal stream count " + streamCount + " must be < 250");
    }

    maxDistance = in.readVarLong();
    if (maxDistance > 65536) {
      maxDistance = 65536;
    }

    int time_base_count = in.readVarInt();
    timeBase = new Fraction[time_base_count];
    for (int i = 0; i < time_base_count; i++) {
      int time_base_num = (int) in.readVarLong();
      int time_base_denom = (int) in.readVarLong();
      timeBase[i] = Fraction.getFraction(time_base_num, time_base_denom);
    }

    long pts = 0;
    int mul = 1;
    int stream_id = 0;
    int header_idx = 0;

    long match = 1L - (1L << 62);
    int size;
    int reserved;
    long count;

    for (int i = 0; i < 256; ) {
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
        if (stream_id >= streamCount) {
          throw new IOException(
              "Illegal stream id value " + stream_id + " must be < " + streamCount);
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

      if (stream_id >= streamCount) {
        throw new IOException(
            String.format("Invalid stream value %d, must be < %d", stream_id, streamCount));
      }

      if (count <= 0 || (count > 256 - i - (i <= 'N' ? 1 : 0))) {
        throw new IOException(
            String.format(
                "Invalid count value %d, must be > 0 && < %d",
                count, 256 - i - (i <= 'N' ? 1 : 0)));
      }

      for (int j = 0; j < count && i < 256; j++, i++) {
        FrameCode fc = new FrameCode();
        frameCodes.add(fc);

        // Skip 'N' because that is an illegal frame code
        if (i == 'N') {
          fc.flags = Frame.FLAG_INVALID;
          j--;
          continue;
        }

        fc.flags = flags;
        fc.streamId = stream_id;
        fc.dataSizeMul = mul;
        fc.dataSizeLsb = size + j;
        fc.ptsDelta = pts;
        fc.reservedCount = reserved;
        fc.matchTimeDelta = match;
        fc.headerIdx = header_idx;

        if (fc.dataSizeLsb >= 16384) {
          throw new IOException("Illegal dataSizeLsb value " + fc.dataSizeLsb + " must be < 16384");
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
          throw new IOException(
              "Invalid elision length value " + e.length + " must be <= " + remain);
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
    return MoreObjects.toStringHelper(this)
        .add("header", header)
        .add("version", version)
        .add("minorVersion", minorVersion)
        .add("streamCount", streamCount)
        .add("maxDistance", maxDistance)
        .add("timeBase", timeBase)
        .add("flags", flags)
        .add("frameCodes", frameCodes.size())
        .add("elision", elision)
        .add("footer", footer)
        .toString();
  }
}
