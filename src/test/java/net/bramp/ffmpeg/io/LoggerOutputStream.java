package net.bramp.ffmpeg.io;

import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class LoggerOutputStream extends OutputStream {

  final Logger logger;
  final Level level;

  final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

  public LoggerOutputStream(Logger logger, Level level) {
    this.logger = logger;
    this.level = level;
  }

  @Override
  public void write(int b) throws IOException {
    buffer.write(b);
    if (b == '\n') {
      String line = buffer.toString(StandardCharsets.UTF_8.name());
      switch (level) {
        case TRACE:
          logger.trace(line);
          break;
        case DEBUG:
          logger.debug(line);
          break;
        case INFO:
          logger.info(line);
          break;
        case WARN:
          logger.warn(line);
          break;
        case ERROR:
          logger.error(line);
          break;
      }
      buffer.reset();
    }
  }
}
