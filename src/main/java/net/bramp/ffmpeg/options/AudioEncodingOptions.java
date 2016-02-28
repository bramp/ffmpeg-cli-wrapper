package net.bramp.ffmpeg.options;

import java.beans.ConstructorProperties;

/**
 * Encoding options for audio
 * 
 * @author bramp
 *
 */
public class AudioEncodingOptions {

  public final boolean enabled;
  public final String codec;
  public final int channels;
  public final int sample_rate;
  public final String bit_depth;
  public final long bit_rate;
  public final int quality;

  @ConstructorProperties({"enabled", "codec", "channels", "sample_rate", "bit_depth", "bit_rate",
      "quality"})
  public AudioEncodingOptions(boolean enabled, String codec, int channels, int sample_rate,
      String bit_depth, long bit_rate, int quality) {
    this.enabled = enabled;
    this.codec = codec;
    this.channels = channels;
    this.sample_rate = sample_rate;
    this.bit_depth = bit_depth;
    this.bit_rate = bit_rate;
    this.quality = quality;
  }

}
