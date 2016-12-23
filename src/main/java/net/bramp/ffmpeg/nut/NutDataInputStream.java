package net.bramp.ffmpeg.nut;

import com.google.common.io.CountingInputStream;
import net.bramp.ffmpeg.io.CRC32InputStream;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.google.common.base.Preconditions.checkNotNull;

/** A DataInputStream that implements a couple of custom FFmpeg Nut datatypes. */
public class NutDataInputStream implements DataInput {

  final DataInputStream in;
  final CRC32InputStream crc;
  final CountingInputStream count;

  // These are for debugging, remove later
  long startCrcRange;
  long endCrcRange;

  public NutDataInputStream(InputStream in) {
    checkNotNull(in);
    this.count = new CountingInputStream(in);
    this.crc = new CRC32InputStream(count);
    this.in = new DataInputStream(crc);
  }

  public void resetCRC() {
    startCrcRange = count.getCount();
    crc.resetCrc();
  }

  public long getCRC() {
    endCrcRange = count.getCount();
    return crc.getValue();
  }

  // Read a simple var int up to 32 bits
  public int readVarInt() throws IOException {
    boolean more;
    int result = 0;
    do {
      int b = in.readUnsignedByte();
      more = (b & 0x80) == 0x80;
      result = 128 * result + (b & 0x7F);

      // TODO Check for int overflow
    } while (more);

    return result;
  }

  // Read a simple var int up to 64 bits
  public long readVarLong() throws IOException {
    boolean more;
    long result = 0;
    do {
      int b = in.readUnsignedByte();
      more = (b & 0x80) == 0x80;
      result = 128 * result + (b & 0x7F);

      // TODO Check for long overflow
    } while (more);

    return result;
  }

  // Read a signed var int
  public long readSignedVarInt() throws IOException {
    long temp = readVarLong() + 1;
    if ((temp & 1) == 1) {
      return -(temp >> 1);
    }
    return temp >> 1;
  }

  // Read a array with a varint prefixed length
  public byte[] readVarArray() throws IOException {
    int len = (int) readVarLong();
    byte[] result = new byte[len];
    in.read(result);
    return result;
  }

  // Returns the start code, OR frame_code if the code doesn't start with 'N'
  public long readStartCode() throws IOException {
    byte frameCode = in.readByte();
    if (frameCode != 'N') {
      return (long) (frameCode & 0xff);
    }

    // Otherwise read the remaining 64bit startCode
    byte[] buffer = new byte[8];
    buffer[0] = frameCode;
    readFully(buffer, 1, 7);
    return (((long) buffer[0] << 56)
        + ((long) (buffer[1] & 255) << 48)
        + ((long) (buffer[2] & 255) << 40)
        + ((long) (buffer[3] & 255) << 32)
        + ((long) (buffer[4] & 255) << 24)
        + ((buffer[5] & 255) << 16)
        + ((buffer[6] & 255) << 8)
        + ((buffer[7] & 255) << 0));
  }

  public long offset() {
    return count.getCount();
  }

  @Override
  public void readFully(byte[] b) throws IOException {
    in.readFully(b);
  }

  @Override
  public void readFully(byte[] b, int off, int len) throws IOException {
    in.readFully(b, off, len);
  }

  @Override
  public int skipBytes(int n) throws IOException {
    return in.skipBytes(n);
  }

  @Override
  public boolean readBoolean() throws IOException {
    return in.readBoolean();
  }

  @Override
  public byte readByte() throws IOException {
    return in.readByte();
  }

  @Override
  public int readUnsignedByte() throws IOException {
    return in.readUnsignedByte();
  }

  @Override
  public short readShort() throws IOException {
    return in.readShort();
  }

  @Override
  public int readUnsignedShort() throws IOException {
    return in.readUnsignedShort();
  }

  @Override
  public char readChar() throws IOException {
    return in.readChar();
  }

  @Override
  public int readInt() throws IOException {
    return in.readInt();
  }

  @Override
  public long readLong() throws IOException {
    return in.readLong();
  }

  @Override
  public float readFloat() throws IOException {
    return in.readFloat();
  }

  @Override
  public double readDouble() throws IOException {
    return in.readDouble();
  }

  @Override
  @Deprecated
  public String readLine() throws IOException {
    return in.readLine();
  }

  @Override
  public String readUTF() throws IOException {
    return in.readUTF();
  }
}
