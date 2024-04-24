package net.bramp.ffmpeg.builder;

import static com.google.common.base.Preconditions.*;
import static net.bramp.ffmpeg.Preconditions.checkNotEmpty;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.InlineMe;

import java.net.URI;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.CheckReturnValue;
import net.bramp.ffmpeg.options.AudioEncodingOptions;
import net.bramp.ffmpeg.options.EncodingOptions;
import net.bramp.ffmpeg.options.MainEncodingOptions;
import net.bramp.ffmpeg.options.VideoEncodingOptions;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

/** Builds a representation of a single output/encoding setting */
@SuppressWarnings({"DeprecatedIsStillUsed", "deprecation","unchecked"})
public abstract class AbstractFFmpegOutputBuilder<T extends AbstractFFmpegOutputBuilder<T>> extends AbstractFFmpegStreamBuilder<T> {

  static final Pattern trailingZero = Pattern.compile("\\.0*$");
  /** @deprecated Use {@link #getConstantRateFactor()} instead*/
  @Deprecated
  public Double constantRateFactor;

  /** @deprecated Use {@link #getAudioSampleFormat()} instead*/
  @Deprecated
  public String audio_sample_format;

  /** @deprecated Use {@link #getAudioBitRate()} instead*/
  @Deprecated
  public long audio_bit_rate;

  /** @deprecated Use {@link #getAudioQuality()} instead*/
  @Deprecated
  public Double audio_quality;

  /** @deprecated Use {@link #getVideoBitStreamFilter()} instead*/
  @Deprecated
  public String audio_bit_stream_filter;

  /** @deprecated Use {@link #getAudioFilter()} instead*/
  @Deprecated
  public String audio_filter;

  /** @deprecated Use {@link #getVideoBitRate()} instead*/
  @Deprecated
  public long video_bit_rate;

  /** @deprecated Use {@link #getVideoQuality()} instead*/
  @Deprecated
  public Double video_quality;

  /** @deprecated Use {@link #getVideoPreset()} instead*/
  @Deprecated
  public String video_preset;

  /** @deprecated Use {@link #getVideoFilter()} instead*/
  @Deprecated
  public String video_filter;

  /** @deprecated Use {@link #getVideoBitStreamFilter()} instead*/
  @Deprecated
  public String video_bit_stream_filter;

  public AbstractFFmpegOutputBuilder() {
    super();
  }

  protected AbstractFFmpegOutputBuilder(FFmpegBuilder parent, String filename) {
    super(parent, filename);
  }

  protected AbstractFFmpegOutputBuilder(FFmpegBuilder parent, URI uri) {
    super(parent, uri);
  }

  public T setConstantRateFactor(double factor) {
    checkArgument(factor >= 0, "constant rate factor must be greater or equal to zero");
    this.constantRateFactor = factor;
    return (T) this;
  }

  public T setVideoBitRate(long bit_rate) {
    checkArgument(bit_rate > 0, "bit rate must be positive");
    this.video_enabled = true;
    this.video_bit_rate = bit_rate;
    return (T) this;
  }

  public T setVideoQuality(double quality) {
    checkArgument(quality > 0, "quality must be positive");
    this.video_enabled = true;
    this.video_quality = quality;
    return (T) this;
  }

  public T setVideoBitStreamFilter(String filter) {
    this.video_bit_stream_filter = checkNotEmpty(filter, "filter must not be empty");
    return (T) this;
  }

  /**
   * Sets a video preset to use.
   *
   * <p>Uses `-vpre`.
   *
   * @param preset the preset
   * @return this
   */
  public T setVideoPreset(String preset) {
    this.video_enabled = true;
    this.video_preset = checkNotEmpty(preset, "video preset must not be empty");
    return (T) this;
  }

  /**
   * Sets Video Filter
   *
   * <p>TODO Build a fluent Filter builder
   *
   * @param filter The video filter.
   * @return this
   */
  public T setVideoFilter(String filter) {
    this.video_enabled = true;
    this.video_filter = checkNotEmpty(filter, "filter must not be empty");
    return (T) this;
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
  @InlineMe(replacement = "this.setAudioSampleFormat(bit_depth)")
  final public T setAudioBitDepth(String bit_depth) {
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
  public T setAudioSampleFormat(String sample_format) {
    this.audio_enabled = true;
    this.audio_sample_format = checkNotEmpty(sample_format, "sample format must not be empty");
    return (T) this;
  }

  /**
   * Sets the Audio bit rate
   *
   * @param bit_rate Audio bitrate in bits per second.
   * @return this
   */
  public T setAudioBitRate(long bit_rate) {
    checkArgument(bit_rate > 0, "bit rate must be positive");
    this.audio_enabled = true;
    this.audio_bit_rate = bit_rate;
    return (T) this;
  }

  public T setAudioQuality(double quality) {
    checkArgument(quality > 0, "quality must be positive");
    this.audio_enabled = true;
    this.audio_quality = quality;
    return (T) this;
  }

  public T setAudioBitStreamFilter(String filter) {
    this.audio_enabled = true;
    this.audio_bit_stream_filter = checkNotEmpty(filter, "filter must not be empty");
    return (T) this;
  }

  /**
   * Sets Audio Filter
   *
   * <p>TODO Build a fluent Filter builder
   *
   * @param filter The audio filter.
   * @return this
   */
  public T setAudioFilter(String filter) {
    this.audio_enabled = true;
    this.audio_filter = checkNotEmpty(filter, "filter must not be empty");
    return (T) this;
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

      checkArgument(
          constantRateFactor == null, "Target size can not be used with constantRateFactor");

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

  /**
   * Returns a double formatted as a string. If the double is an integer, then trailing zeros are
   * striped.
   *
   * @param d the double to format.
   * @return The formatted double.
   */
  protected static String formatDecimalInteger(double d) {
    return trailingZero.matcher(String.valueOf(d)).replaceAll("");
  }

  @Override
  protected void addGlobalFlags(FFmpegBuilder parent, ImmutableList.Builder<String> args) {
    super.addGlobalFlags(parent, args);

    if (constantRateFactor != null) {
      args.add("-crf", formatDecimalInteger(constantRateFactor));
    }
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
      args.add("-qscale:v", formatDecimalInteger(video_quality));
    }

    if (!Strings.isNullOrEmpty(video_preset)) {
      args.add("-vpre", video_preset);
    }

    if (!Strings.isNullOrEmpty(video_filter)) {
      checkState(
          parent.inputs.size() == 1,
          "Video filter only works with one input, instead use setComplexVideoFilter(..)");
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
      args.add("-qscale:a", formatDecimalInteger(audio_quality));
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
  protected T getThis() {
    return (T) this;
  }


  public Double getConstantRateFactor() {
    return constantRateFactor;
  }

  public String getAudioSampleFormat() {
    return audio_sample_format;
  }

  public long getAudioBitRate() {
    return audio_bit_rate;
  }

  public Double getAudioQuality() {
    return audio_quality;
  }

  public String getAudioBitStreamFilter() {
    return audio_bit_stream_filter;
  }

  public String getAudioFilter() {
    return audio_filter;
  }

  public long getVideoBitRate() {
    return video_bit_rate;
  }

  public Double getVideoQuality() {
    return video_quality;
  }

  public String getVideoPreset() {
    return video_preset;
  }

  public String getVideoFilter() {
    return video_filter;
  }

  public String getVideoBitStreamFilter() {
    return video_bit_stream_filter;
  }
}
