package net.bramp.ffmpeg.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CRC32;

/**
 * Calculates the CRC32 for all bytes read through the input stream. Using the java.util.zip.CRC32
 * class to calculate the checksum.
 */
public class CRC32InputStream extends FilterInputStream {

  final CRC32 crc = new CRC32();

  public CRC32InputStream(InputStream in) {
    super(in);
  }

  public void resetCrc() {
    crc.reset();
  }

  public long getValue() {
    return crc.getValue();
  }

  @Override
  public int read() throws IOException {
    int b = in.read();
    if (b >= 0) {
      crc.update(b);
    }
    return b;
  }

  @Override
  public int read(byte[] b) throws IOException {
    int len = in.read(b);
    crc.update(b, 0, len);
    return len;
  }

  @Override
  public int read(byte[] b, int off, int len) throws IOException {
    int actual = in.read(b, off, len);
    crc.update(b, off, actual);
    return actual;
  }

  @Override
  public long skip(long n) throws IOException {
    long i = 0;
    while (i < n) {
      read();
      i++;
    }
    return i;
  }

  @Override
  public synchronized void mark(int readlimit) {
    throw new UnsupportedOperationException("mark not supported");
  }

  @Override
  public synchronized void reset() throws IOException {
    throw new UnsupportedOperationException("reset not supported");
  }

  @Override
  public boolean markSupported() {
    return false;
  }
}
