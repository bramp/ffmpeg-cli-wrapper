package net.bramp.ffmpeg.nut;

import com.google.common.base.MoreObjects;

import java.io.IOException;

class PacketFooter {
  int checksum;

  public void read(NutDataInputStream in) throws IOException {
    long expected = in.getCRC();
    checksum = in.readInt();
    if (checksum != expected) {
      // throw new IOException(String.format("invalid packet checksum %X want %X", expected,
      // checksum));
      Packet.LOG.debug("invalid packet checksum {} want {}", expected, checksum);
    }
    in.resetCRC();
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("checksum", checksum).toString();
  }
}
