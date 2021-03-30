package net.bramp.ffmpeg.options;

import java.beans.ConstructorProperties;

/**
 * Encoding options for audio
 *
 * @author bramp
 */
public class AudioEncodingOptions {

  public final boolean enabled;
  public final String codec;
  public final int channels;
  public final int sample_rate;
  public final String sample_format;
  public final long bit_rate;
  public final Double quality;

  @ConstructorProperties({
    "enabled",
    "codec",
    "channels",
    "sample_rate",
    "sample_format",
    "bit_rate",
    "quality"
  })
  public AudioEncodingOptions(
      boolean enabled,
      String codec,
      int channels,
      int sample_rate,
      String sample_format,
      long bit_rate,
      Double quality) {
    this.enabled = enabled;
    this.codec = codec;
    this.channels = channels;
    this.sample_rate = sample_rate;
    this.sample_format = sample_format;
    this.bit_rate = bit_rate;
    this.quality = quality;
  }
}
