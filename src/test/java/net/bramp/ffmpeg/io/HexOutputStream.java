package net.bramp.ffmpeg.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * Converts bytes into hex output
 */
public class HexOutputStream extends OutputStream {

  final Writer writer;
  int count = 0;

  /**
   * Creates an output stream filter built on top of the specified underlying output stream.
   *
   * @param out the underlying output stream to be assigned to the field <tt>this.out</tt> for later
   *        use, or <code>null</code> if this instance is to be created without an underlying
   *        stream.
   */
  public HexOutputStream(OutputStream out) {
    writer = new OutputStreamWriter(out);
  }

  public void write(int b) throws IOException {
    writer.write(String.format("%02X ", b & 0xFF));
    count++;
    if (count > 16) {
      count = 0;
      writer.write("\n");
    }
  }

  public void write(byte[] b, int off, int len) throws IOException {
    for (int i = 0; i < len; i++) {
      write(b[off++]);
    }
  }

  public void write(byte[] b) throws IOException {
    write(b, 0, b.length);
  }

  @Override
  public void flush() throws IOException {
    writer.flush();
  }

  @Override
  public void close() throws IOException {
    writer.close();
  }
}
