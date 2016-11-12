package net.bramp.ffmpeg.nut;


import com.google.common.base.MoreObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Packet {

  final static Logger LOG = LoggerFactory.getLogger(Packet.class);

  public enum Startcode {
    MAIN(0x7A561F5F04ADL + (((long) ('N' << 8) + 'M') << 48)), STREAM(
        0x11405BF2F9DBL + (((long) ('N' << 8) + 'S') << 48)), SYNCPOINT(
        0xE4ADEECA4569L + (((long) ('N' << 8) + 'K') << 48)), INDEX(
        0xDD672F23E64EL + (((long) ('N' << 8) + 'X') << 48)), INFO(
        0xAB68B596BA78L + (((long) ('N' << 8) + 'I') << 48));

    private final long startcode;

    Startcode(long startcode) {
      this.startcode = startcode;
    }

    public long value() {
      return startcode;
    }

    public boolean equals(long startcode) {
      return this.startcode == startcode;
    }

    /**
     * Returns the Startcode enum for this code.
     * 
     * @param startcode The numeric code for this Startcode.
     * @return The Startcode
     */
    public static Startcode of(long startcode) {
      for (Startcode c : Startcode.values()) {
        if (c.equals(startcode)) {
          return c;
        }
      }
      return null;
    }

    public static boolean isPossibleStartcode(long startcode) {
      return (startcode & 0xFFL) == 'N';
    }

    public static String toString(long startcode) {
      Startcode c = of(startcode);
      if (c != null) {
        return c.name();
      }
      return String.format("%X", startcode);
    }
  }

  public final PacketHeader header = new PacketHeader();
  public final PacketFooter footer = new PacketFooter();


  protected void readBody(NutDataInputStream in) throws IOException {
    // Default implementation does nothing
  }

  public void read(NutDataInputStream in, long startcode) throws IOException {
    header.read(in, startcode);
    readBody(in);
    seekToPacketFooter(in);
    footer.read(in);
  }

  public void seekToPacketFooter(NutDataInputStream in) throws IOException {
    long current = in.offset();
    if (current > header.end) {
      throw new IOException("Can not seek backwards at:" + current + " end:" + header.end);
    }
    // TODO Fix this to not cast longs to ints
    in.skipBytes((int) (header.end - current));
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("header", header).add("footer", footer).toString();
  }
}
