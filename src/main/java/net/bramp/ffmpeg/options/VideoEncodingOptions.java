package net.bramp.ffmpeg.options;

import java.beans.ConstructorProperties;
import org.apache.commons.lang3.math.Fraction;

/**
 * Encoding options for video
 *
 * @author bramp
 */
public class VideoEncodingOptions {
  /** @deprecated Use {@link #isEnabled()} instead */
  @Deprecated
  public final boolean enabled;
  /** @deprecated Use {@link #getCodec()} instead */
  @Deprecated
  public final String codec;
  /** @deprecated Use {@link #getFrameRate()} instead */
  @Deprecated
  public final Fraction frame_rate;
  /** @deprecated Use {@link #getWidth()} instead */
  @Deprecated
  public final int width;
  /** @deprecated Use {@link #getHeight()} instead */
  @Deprecated
  public final int height;
  /** @deprecated Use {@link #getBitRate()} instead */
  @Deprecated
  public final long bit_rate;
  /** @deprecated Use {@link #getFrames()} instead */
  @Deprecated
  public final Integer frames;
  /** @deprecated Use {@link #getFilter()} instead */
  @Deprecated
  public final String filter;
  /** @deprecated Use {@link #getPreset()} instead */
  @Deprecated
  public final String preset;

  @ConstructorProperties({
    "enabled",
    "codec",
    "frame_rate",
    "width",
    "height",
    "bit_rate",
    "frames",
    "video_filter",
    "preset"
  })
  public VideoEncodingOptions(
      boolean enabled,
      String codec,
      Fraction frame_rate,
      int width,
      int height,
      long bit_rate,
      Integer frames,
      String filter,
      String preset) {
    this.enabled = enabled;
    this.codec = codec;
    this.frame_rate = frame_rate;
    this.width = width;
    this.height = height;
    this.bit_rate = bit_rate;
    this.frames = frames;
    this.filter = filter;
    this.preset = preset;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public String getCodec() {
    return codec;
  }

  public Fraction getFrameRate() {
    return frame_rate;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public long getBitRate() {
    return bit_rate;
  }

  public Integer getFrames() {
    return frames;
  }

  public String getFilter() {
    return filter;
  }

  public String getPreset() {
    return preset;
  }
}
