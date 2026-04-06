package net.bramp.ffmpeg.options;

import java.beans.ConstructorProperties;

/**
 * Audio, Video and Main encoding options for ffmpeg.
 *
 * @author bramp
 */
public class EncodingOptions {
  /**
   * The main encoding options.
   *
   * @deprecated Use {@link #getMain()} instead.
   */
  @Deprecated public final MainEncodingOptions main;

  /**
   * The audio encoding options.
   *
   * @deprecated Use {@link #getAudio()} instead.
   */
  @Deprecated public final AudioEncodingOptions audio;

  /**
   * The video encoding options.
   *
   * @deprecated Use {@link #getVideo()} instead.
   */
  @Deprecated public final VideoEncodingOptions video;

  /** Constructs encoding options with main, audio, and video settings. */
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
