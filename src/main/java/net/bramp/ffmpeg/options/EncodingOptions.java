package net.bramp.ffmpeg.options;

import java.beans.ConstructorProperties;

/**
 * Audio, Video and Main encoding options for ffmpeg.
 * 
 * @author bramp
 */
public class EncodingOptions {
  /** @deprecated Use {@link #getMain()} instead */
  @Deprecated
  public final MainEncodingOptions main;
  /** @deprecated Use {@link #getAudio()} instead */
  @Deprecated
  public final AudioEncodingOptions audio;
  /** @deprecated Use {@link #getVideo()} instead */
  @Deprecated
  public final VideoEncodingOptions video;

  @ConstructorProperties({"main", "audio", "video"})
  public EncodingOptions(
      MainEncodingOptions main, AudioEncodingOptions audio, VideoEncodingOptions video) {
    this.main = main;
    this.audio = audio;
    this.video = video;
  }

  public MainEncodingOptions getMain() {
    return main;
  }

  public AudioEncodingOptions getAudio() {
    return audio;
  }

  public VideoEncodingOptions getVideo() {
    return video;
  }
}
