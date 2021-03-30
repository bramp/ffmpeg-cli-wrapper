package net.bramp.ffmpeg.nut;

import org.apache.commons.lang3.math.Fraction;

import java.io.IOException;

public class Stream {
  final StreamHeaderPacket header;

  final Fraction timeBase;
  long last_pts = 0;

  public Stream(MainHeaderPacket header, StreamHeaderPacket streamHeader) throws IOException {
    this.header = streamHeader;
    if (streamHeader.timeBaseId >= header.timeBase.length) {
      throw new IOException(
          "Invalid timeBaseId " + streamHeader.timeBaseId + " must be < " + header.timeBase.length);
    }
    this.timeBase = header.timeBase[streamHeader.timeBaseId];
  }
}
