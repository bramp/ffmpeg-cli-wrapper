package net.bramp.ffmpeg.nut;

import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.math.Fraction;

import java.io.IOException;

class StreamHeaderPacket extends Packet {

  public final static int VIDEO = 0;
  public final static int AUDIO = 1;
  public final static int SUBTITLE = 2;
  public final static int USER_DATA = 3;

  int id;
  long type; // One of VIDEO/AUDIO/SUBTITLE/USER_DATA // TODO Convert to enum.
  byte[] fourcc;
  int timeBaseId;
  int msbPtsShift;
  int maxPtsDistance;
  long decodeDelay;
  long flags;
  byte[] codecSpecificData;

  // If video
  int width;
  int height;
  int sampleWidth;
  int sampleHeight;
  long colorspaceType;

  // If audio
  Fraction sampleRate = Fraction.ZERO;
  int channels;

  protected void readBody(NutDataInputStream in) throws IOException {

    id = in.readVarInt();
    type = in.readVarLong();
    fourcc = in.readVarArray();

    if (fourcc.length != 2 && fourcc.length != 4) {
      // TODO In future fourcc could be a different size, but for sanity checking lets leave this
      // check in.
      throw new IOException("Unexpected fourcc length: " + fourcc.length);
    }

    timeBaseId = in.readVarInt();
    msbPtsShift = in.readVarInt();
    if (msbPtsShift >= 16) {
      throw new IOException("invalid msbPtsShift " + msbPtsShift + " want < 16");
    }
    maxPtsDistance = in.readVarInt();
    decodeDelay = in.readVarLong();
    flags = in.readVarLong();
    codecSpecificData = in.readVarArray();

    if (type == VIDEO) {
      width = in.readVarInt();
      height = in.readVarInt();

      if (width == 0 || height == 0) {
        throw new IOException("invalid video dimensions " + width + "x" + height);
      }

      sampleWidth = in.readVarInt();
      sampleHeight = in.readVarInt();

      // Both MUST be 0 if unknown otherwise both MUST be nonzero.
      if ((sampleWidth == 0 || sampleHeight == 0) && sampleWidth != sampleHeight) {
        throw new IOException("invalid video sample dimensions " + sampleWidth + "x" + sampleHeight);
      }

      colorspaceType = in.readVarLong();

    } else if (type == AUDIO) {
      int samplerateNum = in.readVarInt();
      int samplerateDenom = in.readVarInt();
      sampleRate = Fraction.getFraction(samplerateNum, samplerateDenom);
      channels = in.readVarInt();
    }
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("header", header).add("id", id).add("type", type)
        .add("fourcc", new String(fourcc)).add("timeBaseId", timeBaseId)
        .add("msbPtsShift", msbPtsShift).add("maxPtsDistance", maxPtsDistance)
        .add("decodeDelay", decodeDelay).add("flags", flags)
        .add("codecSpecificData", codecSpecificData).add("width", width).add("height", height)
        .add("sampleWidth", sampleWidth).add("sampleHeight", sampleHeight)
        .add("colorspaceType", colorspaceType).add("sampleRate", sampleRate)
        .add("channels", channels).add("footer", footer).toString();
  }
}
