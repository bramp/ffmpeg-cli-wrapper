package net.bramp.ffmpeg.nut;

import java.io.IOException;
import org.apache.commons.lang3.math.Fraction;

/** Represents a media stream within a NUT multimedia container. */
public class Stream {
  final StreamHeaderPacket header;

  final Fraction timeBase;
  long last_pts = 0;

  /** Constructs a new stream from the given main header and stream header. */
  public Stream(MainHeaderPacket header, StreamHeaderPacket streamHeader) throws IOException {
    this.header = streamHeader;
    if (streamHeader.timeBaseId >= header.timeBase.length) {
      throw new IOException(
          "Invalid timeBaseId " + streamHeader.timeBaseId + " must be < " + header.timeBase.length);
    }
    this.timeBase = header.timeBase[streamHeader.timeBaseId];
  }
}
