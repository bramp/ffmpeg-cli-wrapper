package net.bramp.ffmpeg.builder;

import java.net.URI;

/** Builder for standard FFmpeg output arguments. */
public class FFmpegOutputBuilder extends AbstractFFmpegOutputBuilder<FFmpegOutputBuilder> {

  public FFmpegOutputBuilder() {
    super();
  }

  protected FFmpegOutputBuilder(FFmpegBuilder parent, String filename) {
    super(parent, filename);
  }

  protected FFmpegOutputBuilder(FFmpegBuilder parent, URI uri) {
    super(parent, uri);
  }
}
