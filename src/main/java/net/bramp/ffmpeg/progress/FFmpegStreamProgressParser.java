package net.bramp.ffmpeg.progress;

import java.net.URI;

/**
 * TODO I'm not sure I like this class hierarchy.
 */
public class FFmpegStreamProgressParser extends FFmpegProgressParser {

  final FFmpegProgressListener listener;

  public FFmpegStreamProgressParser(FFmpegProgressListener listener) {
    super(listener);
    this.listener = listener;
  }

  @Override
  public void start() throws Exception {}

  @Override
  public void stop() {}

  @Override
  public URI getUri() {
    return null;
  }
}
