package net.bramp.ffmpeg.builder;

import java.net.URI;

/** Builder for standard FFmpeg output arguments. */
public class FFmpegOutputBuilder extends AbstractFFmpegOutputBuilder<FFmpegOutputBuilder> {

  /** Constructs a default output builder. */
  public FFmpegOutputBuilder() {
    super();
  }

  /** Constructs an output builder with the given filename. */
  protected FFmpegOutputBuilder(FFmpegBuilder parent, String filename) {
    super(parent, filename);
  }

  /** Constructs an output builder with the given URI. */
  protected FFmpegOutputBuilder(FFmpegBuilder parent, URI uri) {
    super(parent, uri);
  }
}
