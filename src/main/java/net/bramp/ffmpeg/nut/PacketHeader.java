package net.bramp.ffmpeg.nut;

import com.google.common.base.MoreObjects;

import java.io.IOException;

public class PacketHeader {

  long startcode;
  long forwardPtr;
  int checksum; // header checksum

  long end; // End byte of packet

  public void read(NutDataInputStream in, long startcode) throws IOException {
    this.startcode = startcode;
    forwardPtr = in.readVarLong();
    if (forwardPtr > 4096) {
      long expected = in.getCRC();
      checksum = in.readInt();
      if (checksum != expected) {
        // TODO This code path has never been tested.
        throw new IOException(
            String.format("invalid header checksum %X want %X", expected, checksum));
      }
    }

    in.resetCRC();
    end = in.offset() + forwardPtr - 4; // 4 bytes for footer CRC
  }

  @Override
  public String toString() {
    MoreObjects.ToStringHelper helper =
        MoreObjects.toStringHelper(this)
            .add("startcode", Packet.Startcode.toString(startcode))
            .add("forwardPtr", forwardPtr);

    if (forwardPtr > 4096) {
      helper = helper.add("checksum", checksum);
    }
    return helper.toString();
  }
}
