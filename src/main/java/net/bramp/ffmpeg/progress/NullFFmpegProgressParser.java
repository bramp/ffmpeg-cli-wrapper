package net.bramp.ffmpeg.progress;

import java.net.URI;

/**
 * Dummy FFmpegProgressParser that does nothing.
 */
public class NullFFmpegProgressParser extends FFmpegProgressParser {

  protected NullFFmpegProgressParser() {
    super(new FFmpegProgressListener() {

      @Override
      public void progress(Progress p) {
        // Do nothing
      }
    });
  }

  private static final NullFFmpegProgressParser SINGLETON = new NullFFmpegProgressParser();

  public static NullFFmpegProgressParser instance() {
    return SINGLETON;
  }

  @Override
  public void start() throws Exception {

  }

  @Override
  public void stop() {

  }

  @Override
  public URI getUri() {
    return null;
  }
}
