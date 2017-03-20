package net.bramp.ffmpeg.builder;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import net.bramp.ffmpeg.options.AudioEncodingOptions;
import net.bramp.ffmpeg.options.EncodingOptions;
import net.bramp.ffmpeg.options.MainEncodingOptions;
import net.bramp.ffmpeg.options.VideoEncodingOptions;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

import javax.annotation.CheckReturnValue;
import java.net.URI;
import java.util.List;

import static com.google.common.base.Preconditions.*;
import static net.bramp.ffmpeg.Preconditions.checkNotEmpty;

/** Builds a representation of a single output/encoding setting */
public class FFmpegOutputBuilder extends AbstractFFmpegStreamBuilder<FFmpegOutputBuilder> {

  public String audio_sample_format;
  public long audio_bit_rate;
  public Integer audio_quality;
  public String audio_bit_stream_filter;
  public String audio_filter;

  public long video_bit_rate;
  public Integer video_quality;
  public String video_preset;
  public String video_filter;
  public String video_bit_stream_filter;

  public FFmpegOutputBuilder() {
    super();
  }

  protected FFmpegOutputBuilder(FFmpegBuilder parent, String filename) {
    super(parent, filename);
  }

  protected FFmpegOutputBuilder(FFmpegBuilder parent, URI uri) {
    super(parent, uri);
  }

  public FFmpegOutputBuilder setVideoBitRate(long bit_rate) {
    checkArgument(bit_rate > 0, "bit rate must be positive");
    this.video_enabled = true;
    this.video_bit_rate = bit_rate;
    return this;
  }

  public FFmpegOutputBuilder setVideoQuality(int quality) {
    checkArgument(quality > 0, "quality must be positive");
    this.video_enabled = true;
    this.video_quality = quality;
    return this;
  }

  public FFmpegOutputBuilder setVideoBitStreamFilter(String filter) {
    this.video_bit_stream_filter = checkNotEmpty(filter, "filter must not be empty");
    return this;
  }

  /**
   * Sets a video preset to use.
   *
   * <p>Uses `-vpre`.
   *
   * @param preset the preset
   * @return this
   */
  public FFmpegOutputBuilder setVideoPreset(String preset) {
    this.video_enabled = true;
    this.video_preset = checkNotEmpty(preset, "video preset must not be empty");
    return this;
  }

  protected static boolean isValidSize(int widthOrHeight) {
    return widthOrHeight > 0 || widthOrHeight == -1;
  }

  /**
   * Sets Video Filter
   *
   * <p>TODO Build a fluent Filter builder
   *
   * @param filter The video filter.
   * @return this
   */
  public FFmpegOutputBuilder setVideoFilter(String filter) {
    this.video_enabled = true;
    this.video_filter = checkNotEmpty(filter, "filter must not be empty");
    return this;
  }

  /**
   * Sets the audio bit depth.
   *
   * @param bit_depth The sample format, one of the net.bramp.ffmpeg.FFmpeg#AUDIO_DEPTH_* constants.
   * @return this
   * @see net.bramp.ffmpeg.FFmpeg#AUDIO_DEPTH_U8
   * @see net.bramp.ffmpeg.FFmpeg#AUDIO_DEPTH_S16
   * @see net.bramp.ffmpeg.FFmpeg#AUDIO_DEPTH_S32
   * @see net.bramp.ffmpeg.FFmpeg#AUDIO_DEPTH_FLT
   * @see net.bramp.ffmpeg.FFmpeg#AUDIO_DEPTH_DBL
   * @deprecated use {@link #setAudioSampleFormat} instead.
   */
  @Deprecated
  public FFmpegOutputBuilder setAudioBitDepth(String bit_depth) {
    return setAudioSampleFormat(bit_depth);
  }

  /**
   * Sets the audio sample format.
   *
   * @param sample_format The sample format, one of the net.bramp.ffmpeg.FFmpeg#AUDIO_FORMAT_*
   *     constants.
   * @return this
   * @see net.bramp.ffmpeg.FFmpeg#AUDIO_FORMAT_U8
   * @see net.bramp.ffmpeg.FFmpeg#AUDIO_FORMAT_S16
   * @see net.bramp.ffmpeg.FFmpeg#AUDIO_FORMAT_S32
   * @see net.bramp.ffmpeg.FFmpeg#AUDIO_FORMAT_FLT
   * @see net.bramp.ffmpeg.FFmpeg#AUDIO_FORMAT_DBL
   */
  public FFmpegOutputBuilder setAudioSampleFormat(String sample_format) {
    this.audio_enabled = true;
    this.audio_sample_format = checkNotEmpty(sample_format, "sample format must not be empty");
    return this;
  }

  /**
   * Sets the Audio bit rate
   *
   * @param bit_rate Audio bitrate in bits per second.
   * @return this
   */
  public FFmpegOutputBuilder setAudioBitRate(long bit_rate) {
    checkArgument(bit_rate > 0, "bit rate must be positive");
    this.audio_enabled = true;
    this.audio_bit_rate = bit_rate;
    return this;
  }

  public FFmpegOutputBuilder setAudioQuality(int quality) {
    checkArgument(quality > 0, "quality must be positive");
    this.audio_enabled = true;
    this.audio_quality = quality;
    return this;
  }

  public FFmpegOutputBuilder setAudioBitStreamFilter(String filter) {
    this.audio_enabled = true;
    this.audio_bit_stream_filter = checkNotEmpty(filter, "filter must not be empty");
    return this;
  }

  /**
   * Sets Audio Filter
   *
   * <p>TODO Build a fluent Filter builder
   *
   * @param filter The audio filter.
   * @return this
   */
  public FFmpegOutputBuilder setAudioFilter(String filter) {
    this.audio_enabled = true;
    this.audio_filter = checkNotEmpty(filter, "filter must not be empty");
    return this;
  }

  /**
   * Returns a representation of this Builder that can be safely serialised.
   *
   * <p>NOTE: This method is horribly out of date, and its use should be rethought.
   *
   * @return A new EncodingOptions capturing this Builder's state
   */
  @CheckReturnValue
  @Override
  public EncodingOptions buildOptions() {
    // TODO When/if modelmapper supports @ConstructorProperties, we map this
    // object, instead of doing new XXX(...)
    // https://github.com/jhalterman/modelmapper/issues/44
    return new EncodingOptions(
        new MainEncodingOptions(format, startOffset, duration),
        new AudioEncodingOptions(
            audio_enabled,
            audio_codec,
            audio_channels,
            audio_sample_rate,
            audio_sample_format,
            audio_bit_rate,
            audio_quality),
        new VideoEncodingOptions(
            video_enabled,
            video_codec,
            video_frame_rate,
            video_width,
            video_height,
            video_bit_rate,
            video_frames,
            video_filter,
            video_preset));
  }

  @CheckReturnValue
  @Override
  protected List<String> build(int pass) {
    Preconditions.checkState(parent != null, "Can not build without parent being set");
    return build(parent, pass);
  }

  /**
   * Builds the arguments
   *
   * @param parent The parent FFmpegBuilder
   * @param pass The particular pass. For one-pass this value will be zero, for multi-pass, it will
   *     be 1 for the first pass, 2 for the second, and so on.
   * @return The arguments
   */
  @CheckReturnValue
  @Override
  protected List<String> build(FFmpegBuilder parent, int pass) {
    if (pass > 0) {
      checkArgument(
          targetSize != 0 || video_bit_rate != 0,
          "Target size, or video bitrate must be specified when using two-pass");
    }
    if (targetSize > 0) {
      checkState(parent.inputs.size() == 1, "Target size does not support multiple inputs");

      String firstInput = parent.inputs.iterator().next();
      FFmpegProbeResult input = parent.inputProbes.get(firstInput);

      checkState(input != null, "Target size must be used with setInput(FFmpegProbeResult)");

      // TODO factor in start time and/or number of frames

      double durationInSeconds = input.format.duration;
      long totalBitRate =
          (long) Math.floor((targetSize * 8) / durationInSeconds) - pass_padding_bitrate;

      // TODO Calculate audioBitRate

      if (video_enabled && video_bit_rate == 0) {
        // Video (and possibly audio)
        long audioBitRate = audio_enabled ? audio_bit_rate : 0;
        video_bit_rate = totalBitRate - audioBitRate;
      } else if (audio_enabled && audio_bit_rate == 0) {
        // Just Audio
        audio_bit_rate = totalBitRate;
      }
    }

    return super.build(parent, pass);
  }

  @Override
  protected void addVideoFlags(FFmpegBuilder parent, ImmutableList.Builder<String> args) {
    super.addVideoFlags(parent, args);

    if (video_bit_rate > 0 && video_quality != null) {
      // I'm not sure, but it seems video_quality overrides video_bit_rate, so don't allow both
      throw new IllegalStateException("Only one of video_bit_rate and video_quality can be set");
    }

    if (video_bit_rate > 0) {
      args.add("-b:v", String.valueOf(video_bit_rate));
    }

    if (video_quality != null) {
      args.add("-qscale:v", String.valueOf(video_quality));
    }

    if (!Strings.isNullOrEmpty(video_preset)) {
      args.add("-vpre", video_preset);
    }

    if (!Strings.isNullOrEmpty(video_filter)) {
      checkState(
          parent.inputs.size() == 1,
          "Video filter only works with one input, instead use setVideoFilterComplex(..)");
      args.add("-vf", video_filter);
    }

    if (!Strings.isNullOrEmpty(video_bit_stream_filter)) {
      args.add("-bsf:v", video_bit_stream_filter);
    }
  }

  @Override
  protected void addAudioFlags(ImmutableList.Builder<String> args) {
    super.addAudioFlags(args);

    if (!Strings.isNullOrEmpty(audio_sample_format)) {
      args.add("-sample_fmt", audio_sample_format);
    }

    if (audio_bit_rate > 0 && audio_quality != null && throwWarnings) {
      // I'm not sure, but it seems audio_quality overrides audio_bit_rate, so don't allow both
      throw new IllegalStateException("Only one of audio_bit_rate and audio_quality can be set");
    }

    if (audio_bit_rate > 0) {
      args.add("-b:a", String.valueOf(audio_bit_rate));
    }

    if (audio_quality != null) {
      args.add("-qscale:a", String.valueOf(audio_quality));
    }

    if (!Strings.isNullOrEmpty(audio_bit_stream_filter)) {
      args.add("-bsf:a", audio_bit_stream_filter);
    }

    if (!Strings.isNullOrEmpty(audio_filter)) {
      args.add("-af", audio_filter);
    }
  }

  @CheckReturnValue
  @Override
  protected FFmpegOutputBuilder getThis() {
    return this;
  }
}
