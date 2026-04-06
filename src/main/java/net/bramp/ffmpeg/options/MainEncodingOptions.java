package net.bramp.ffmpeg.options;

import java.beans.ConstructorProperties;

/**
 * Encoding options that are specific to the main output.
 *
 * @author bramp
 */
public class MainEncodingOptions {
  /**
   * The output format.
   *
   * @deprecated Use {@link #getFormat()} instead.
   */
  @Deprecated public final String format;

  /**
   * The start offset in microseconds.
   *
   * @deprecated Use {@link #getStartOffset()} instead.
   */
  @Deprecated public final Long startOffset;

  /**
   * The duration in microseconds.
   *
   * @deprecated Use {@link #getDuration()} instead.
   */
  @Deprecated public final Long duration;

  /** Constructs main encoding options with the specified format, offset, and duration. */
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
