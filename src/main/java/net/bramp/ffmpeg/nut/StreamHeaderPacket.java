package net.bramp.ffmpeg.nut;

import com.google.common.base.MoreObjects;

import java.io.IOException;

class StreamHeaderPacket extends Packet {

  public final static int VIDEO = 0;
  public final static int AUDIO = 1;
  public final static int SUBTITLE = 2;
  public final static int USER_DATA = 3;

  int stream_id;
  long stream_class;
  byte[] fourcc;
  int time_base_id;
  int msb_pts_shift;
  int max_pts_distance;
  long decode_delay;
  long stream_flags;
  byte[] codec_specific_data;

  // If video
  int width;
  int height;
  int sample_width;
  int sample_height;
  long colorspace_type;

  // If audio
  long samplerate_num;
  long samplerate_denom;
  long channel_count;

  protected void readBody(NutDataInputStream in) throws IOException {

    stream_id = in.readVarInt();
    stream_class = in.readVarLong();
    fourcc = in.readVarArray();

    if (fourcc.length != 2 && fourcc.length != 4) {
      // TODO In future fourcc could be a different size, but for sanity checking lets leave this
      // check in.
      throw new IOException("Unexpected fourcc length: " + fourcc.length);
    }

    time_base_id = in.readVarInt();
    msb_pts_shift = in.readVarInt();
    if (msb_pts_shift >= 16) {
      throw new IOException("invalid msb_pts_shift " + msb_pts_shift + " want < 16");
    }
    max_pts_distance = in.readVarInt();
    decode_delay = in.readVarLong();
    stream_flags = in.readVarLong();
    codec_specific_data = in.readVarArray();

    if (stream_class == VIDEO) {
      width = in.readVarInt();
      height = in.readVarInt();

      if (width == 0 || height == 0) {
        throw new IOException("invalid video dimensions " + width + "x" + height);
      }

      sample_width = in.readVarInt();
      sample_height = in.readVarInt();

      // Both MUST be 0 if unknown otherwise both MUST be nonzero.
      if ((sample_width == 0 || sample_height == 0) && sample_width != sample_height) {
        throw new IOException("invalid video sample dimensions " + sample_width + "x"
            + sample_height);
      }

      colorspace_type = in.readVarLong();

    } else if (stream_class == AUDIO) {
      samplerate_num = in.readVarLong();
      samplerate_denom = in.readVarLong();
      channel_count = in.readVarLong();
    }
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("header", header).add("stream_id", stream_id)
        .add("stream_class", stream_class).add("fourcc", new String(fourcc))
        .add("time_base_id", time_base_id).add("msb_pts_shift", msb_pts_shift)
        .add("max_pts_distance", max_pts_distance).add("decode_delay", decode_delay)
        .add("stream_flags", stream_flags).add("codec_specific_data", codec_specific_data)
        .add("width", width).add("height", height).add("sample_width", sample_width)
        .add("sample_height", sample_height).add("colorspace_type", colorspace_type)
        .add("samplerate_num", samplerate_num).add("samplerate_denom", samplerate_denom)
        .add("channel_count", channel_count).add("footer", footer).toString();
  }
}
