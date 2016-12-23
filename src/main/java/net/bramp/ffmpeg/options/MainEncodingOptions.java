package net.bramp.ffmpeg.options;

import java.beans.ConstructorProperties;

/** @author bramp */
public class MainEncodingOptions {
  public final String format;
  public final Long startOffset;
  public final Long duration;

  @ConstructorProperties({"format", "startOffset", "duration"})
  public MainEncodingOptions(String format, Long startOffset, Long duration) {
    this.format = format;
    this.startOffset = startOffset;
    this.duration = duration;
  }
}
