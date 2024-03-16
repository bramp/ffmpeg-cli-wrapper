package net.bramp.ffmpeg.options;

import java.beans.ConstructorProperties;

/**
 * Encoding options for audio
 *
 * @author bramp
 */
public class AudioEncodingOptions {
  /** @deprecated Use {@link #isEnabled()} instead */
  @Deprecated
  public final boolean enabled;
  /** @deprecated Use {@link #getCodec()} instead */
  @Deprecated
  public final String codec;
  /** @deprecated Use {@link #getChannels()} instead */
  @Deprecated
  public final int channels;
  /** @deprecated Use {@link #getSampleRate()} instead */
  @Deprecated
  public final int sample_rate;
  /** @deprecated Use {@link #getSampleFormat()} instead */
  @Deprecated
  public final String sample_format;
  /** @deprecated Use {@link #getBitRate()} instead */
  @Deprecated
  public final long bit_rate;
  /** @deprecated Use {@link #getQuality()} instead */
  @Deprecated
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

  public boolean isEnabled() {
    return enabled;
  }

  public String getCodec() {
    return codec;
  }

  public int getChannels() {
    return channels;
  }

  public int getSampleRate() {
    return sample_rate;
  }

  public String getSampleFormat() {
    return sample_format;
  }

  public long getBitRate() {
    return bit_rate;
  }

  public Double getQuality() {
    return quality;
  }
}
