package net.bramp.ffmpeg.options;

import org.apache.commons.lang3.math.Fraction;

import java.beans.ConstructorProperties;

/**
 * Encoding options for video
 *
 * @author bramp
 */
public class VideoEncodingOptions {
  public final boolean enabled;
  public final String codec;
  public final Fraction frame_rate;
  public final int width;
  public final int height;
  public final long bit_rate;
  public final Integer frames;
  public final String filter;
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
}
