package net.bramp.ffmpeg.options;

import java.beans.ConstructorProperties;

/**
 * Encoding options that are specific to the main output.
 * 
 * @author bramp
 */
public class MainEncodingOptions {
  /** @deprecated Use {@link #getFormat()} instead */
  @Deprecated
  public final String format;
  /** @deprecated Use {@link #getStartOffset()} instead */
  @Deprecated
  public final Long startOffset;
  /** @deprecated Use {@link #getDuration()} instead */
  @Deprecated
  public final Long duration;

  @ConstructorProperties({"format", "startOffset", "duration"})
  public MainEncodingOptions(String format, Long startOffset, Long duration) {
    this.format = format;
    this.startOffset = startOffset;
    this.duration = duration;
  }

  public String getFormat() {
    return format;
  }

  public Long getStartOffset() {
    return startOffset;
  }

  public Long getDuration() {
    return duration;
  }
}
