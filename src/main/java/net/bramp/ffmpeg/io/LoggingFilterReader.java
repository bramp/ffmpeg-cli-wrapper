package net.bramp.ffmpeg.io;

import com.google.common.primitives.Chars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Wraps a Reader, and logs full lines of input as its read.
 *
 * @author bramp
 */
public class LoggingFilterReader extends FilterReader {

  final static char LOG_CHAR = '\n';

  final Logger logger;
  final StringBuilder buffer = new StringBuilder();

  public LoggingFilterReader(Reader in, Logger logger) {
    super(in);
    this.logger = logger;
  }

  protected void log() {
    if (buffer.length() > 0) {
      // TODO Change from debug, to a user defined level
      logger.debug(buffer.toString());
      buffer.setLength(0);
    }
  }

  private static int indexOf(char[] array, char c, int off, int len) {
    for (int i = off; i < off + len; i++) {
      if (array[i] == c) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public int read(char[] cbuf, int off, int len) throws IOException {
    int ret = super.read(cbuf, off, len);
    if (ret != -1) {
      buffer.append(cbuf, off, ret);
    }

    // If end of stream, or contains new line
    if (ret == -1 || indexOf(cbuf, LOG_CHAR, off, ret) != -1) {
      // BUG this will log a unfinished line, if a string such as
      // "line \n unfinished" is read.
      log();
    }

    return ret;
  }

  @Override
  public int read() throws IOException {
    int ret = super.read();
    if (ret != -1) {
      buffer.append((char) ret);
    }

    // If end of stream, or contains new line
    if (ret == -1 || ret == LOG_CHAR) {
      log();
    }
    return ret;
  }
}
