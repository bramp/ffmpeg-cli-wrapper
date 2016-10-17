package net.bramp.ffmpeg.nut;

import org.apache.commons.lang3.math.Fraction;

import java.io.IOException;

public class Stream {
  final StreamHeaderPacket header;

  final Fraction time_base;
  long last_pts = 0;

  public Stream(MainHeaderPacket header, StreamHeaderPacket streamHeader) throws IOException {
    this.header = streamHeader;
    if (streamHeader.time_base_id >= header.time_base.length) {
      throw new IOException("Invalid time_base_id " + streamHeader.time_base_id + " must be < "
          + header.time_base.length);
    }
    this.time_base = header.time_base[streamHeader.time_base_id];
  }
}
