package net.bramp.ffmpeg.nut;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static net.bramp.ffmpeg.nut.StreamHeaderPacket.fourccToString;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Arrays;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

public class RawHandler {

  private static int[] bytesToInts(byte[] bytes) {
    IntBuffer buf = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).asIntBuffer();

    int[] data = new int[buf.capacity()];
    buf.get(data);
    return data;
  }

  public static BufferedImage toBufferedImage(Frame frame) {
    checkNotNull(frame);

    final StreamHeaderPacket header = frame.stream.header;

    checkArgument(header.type == StreamHeaderPacket.VIDEO);

    // DataBufferByte buffer = new DataBufferByte(frame.data, frame.data.length);
    // SampleModel sample = new MultiPixelPackedSampleModel(DataBuffer.TYPE_BYTE,
    // streamHeader.width, streamHeader.height, 1);
    // Raster raster = new Raster(sample, buffer, new Point(0,0));

    int type = BufferedImage.TYPE_INT_ARGB; // TODO Use the type defined in the stream header.
    BufferedImage img = new BufferedImage(header.width, header.height, type);

    // TODO Avoid this conversion.
    int[] data = bytesToInts(frame.data);
    int stride = header.width; // TODO Check this is true
    img.setRGB(0, 0, header.width, header.height, data, 0, stride);

    return img;
  }

  /**
   * Parses a FourCC into a AudioEncoding based on the following rules:<br>
   * "ALAW" = A-LAW<br>
   * "ULAW" = MU-LAW<br>
   * P[type][interleaving][bits] = little-endian PCM<br>
   * [bits][interleaving][type]P = big-endian PCM<br>
   * Where:<br>
   * &nbsp;&nbsp;[type] is S for signed integer, U for unsigned integer, F for IEEE float<br>
   * &nbsp;&nbsp;[interleaving] is D for default, P is for planar.<br>
   * &nbsp;&nbsp;[bits] is 8/16/24/32<br>
   *
   * @param header The stream's header.
   * @return The AudioFormat matching this header.
   */
  public static AudioFormat streamToAudioFormat(final StreamHeaderPacket header) {
    checkNotNull(header);
    checkArgument(header.type == StreamHeaderPacket.AUDIO);

    // Vars that go into the AudioFormat
    AudioFormat.Encoding encoding;
    float sampleRate = header.sampleRate.floatValue();
    int bits = 8;
    boolean bigEndian = false;

    final byte[] fourcc = header.fourcc;

    if (Arrays.equals(fourcc, new byte[] {'A', 'L', 'A', 'W'})) {
      encoding = AudioFormat.Encoding.ALAW;

    } else if (Arrays.equals(fourcc, new byte[] {'U', 'L', 'A', 'W'})) {
      encoding = AudioFormat.Encoding.ULAW;

    } else if (fourcc.length == 4) {
      byte type;
      byte interleaving;

      if (fourcc[0] == 'P') {
        bigEndian = false;
        type = fourcc[1];
        interleaving = fourcc[2];
        bits = fourcc[3];
      } else if (fourcc[3] == 'P') {
        bigEndian = true;
        type = fourcc[2];
        interleaving = fourcc[1];
        bits = fourcc[0];
      } else {
        throw new IllegalArgumentException(
            "unknown fourcc value: '" + fourccToString(fourcc) + "'");
      }

      if (interleaving != 'D') {
        throw new IllegalArgumentException(
            "unsupported interleaving '"
                + interleaving
                + "' in fourcc value '"
                + fourccToString(fourcc)
                + "'");
      }

      switch (type) {
        case 'S':
          encoding = AudioFormat.Encoding.PCM_SIGNED;
          break;
        case 'U':
          encoding = AudioFormat.Encoding.PCM_UNSIGNED;
          break;
        case 'F':
          encoding = AudioFormat.Encoding.PCM_FLOAT;
          break;
        default:
          throw new IllegalArgumentException(
              "unknown fourcc '" + fourccToString(fourcc) + "' type: " + type);
      }
    } else {
      throw new IllegalArgumentException("unknown fourcc value: '" + fourccToString(fourcc) + "'");
    }

    int frameSize = (bits * header.channels) / 8;
    float frameRate = sampleRate; // This may not be true for the compressed formats

    return new AudioFormat(
        encoding, sampleRate, bits, header.channels, frameSize, frameRate, bigEndian);
  }

  public static AudioInputStream toAudioInputStream(Frame frame) {
    checkNotNull(frame);
    final StreamHeaderPacket header = checkNotNull(frame.stream.header);
    checkArgument(header.type == StreamHeaderPacket.AUDIO);

    AudioFormat format = streamToAudioFormat(header);
    InputStream stream = new ByteArrayInputStream(frame.data);

    return new AudioInputStream(stream, format, frame.data.length / format.getFrameSize());
  }
}
