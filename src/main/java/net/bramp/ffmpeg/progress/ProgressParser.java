package net.bramp.ffmpeg.progress;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;

/** Parses the FFmpeg progress fields */
public interface ProgressParser extends Closeable {

  void start() throws IOException;

  void stop() throws IOException;

  /**
   * The URL to parse to FFmpeg to communicate with this parser
   *
   * @return The URI to communicate with FFmpeg.
   */
  URI getUri();
}
