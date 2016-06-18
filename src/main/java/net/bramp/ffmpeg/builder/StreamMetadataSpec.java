package net.bramp.ffmpeg.builder;

import static com.google.common.base.Preconditions.checkNotNull;

public class StreamMetadataSpec extends MetadataSpec {

  public final StreamSpec spec;

  protected StreamMetadataSpec(StreamSpec spec) {
    super(Type.Stream);
    this.spec = checkNotNull(spec);
  }

  @Override
  public String toString() {
    return "s:" + spec.toString();
  }
}
