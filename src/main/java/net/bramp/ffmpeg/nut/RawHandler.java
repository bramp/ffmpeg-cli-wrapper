package net.bramp.ffmpeg.nut;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Arrays;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class RawHandler {

  private static int[] bytesToInts(byte[] bytes) {
    IntBuffer buf = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).asIntBuffer();

    int[] data = new int[buf.capacity()];
    buf.get(data);
    return data;
  }

  public static BufferedImage toBufferedImage(Frame frame) {

    StreamHeaderPacket streamHeader = frame.stream.header;

    // DataBufferByte buffer = new DataBufferByte(frame.data, frame.data.length);
    // SampleModel sample = new MultiPixelPackedSampleModel(DataBuffer.TYPE_BYTE,
    // streamHeader.width, streamHeader.height, 1);
    // Raster raster = new Raster(sample, buffer, new Point(0,0));

    int stride = streamHeader.width; // TODO Check this is true
    BufferedImage img =
        new BufferedImage(streamHeader.width, streamHeader.height, BufferedImage.TYPE_INT_ARGB);

    // TODO Avoid this conversion.
    int[] data = bytesToInts(frame.data);

    img.setRGB(0, 0, streamHeader.width, streamHeader.height, data, 0, stride);

    return img;
  }
}
