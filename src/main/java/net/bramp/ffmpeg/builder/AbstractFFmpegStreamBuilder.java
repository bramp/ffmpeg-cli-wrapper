package net.bramp.ffmpeg.builder;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static net.bramp.ffmpeg.FFmpegUtils.toTimecode;
import static net.bramp.ffmpeg.builder.MetadataSpecifier.checkValidKey;
import static net.bramp.ffmpeg.Preconditions.checkValidStream;
import static net.bramp.ffmpeg.Preconditions.checkNotEmpty;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import net.bramp.ffmpeg.modelmapper.Mapper;
import net.bramp.ffmpeg.options.AudioEncodingOptions;
import net.bramp.ffmpeg.options.EncodingOptions;
import net.bramp.ffmpeg.options.MainEncodingOptions;
import net.bramp.ffmpeg.options.VideoEncodingOptions;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.math.Fraction;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * This abstract class holds flags that are both applicable to input and output streams in the
 * ffmpeg command, while flags that apply to a particular direction (input/output) are located in
 * {@link FFmpegOutputBuilder}. <br>
 * <br>
 * All possible flags can be found in the <a href="https://ffmpeg.org/ffmpeg.html#Options">official
 * ffmpeg page</a> The discrimination criteria for flag location are the specifiers for each command
 *
 * <ul>
 *   <li>AbstractFFmpegStreamBuilder
 *       <ul>
 *         <li>(input/output): <code>-t duration (input/output)</code>
 *         <li>(input/output,per-stream): <code>
 *             -codec[:stream_specifier] codec (input/output,per-stream)</code>
 *         <li>(global): <code>-filter_threads nb_threads (global)</code>
 *       </ul>
 *
 *   <li>FFmpegInputBuilder
 *       <ul>
 *         <li>(input): <code>-muxdelay seconds (input)</code>
 *         <li>(input,per-stream): <code>-guess_layout_max channels (input,per-stream)</code>
 *       </ul>
 *
 *   <li>FFmpegOutputBuilder
 *       <ul>
 *         <li>(output): <code>-atag fourcc/tag (output)</code>
 *         <li>(output,per-stream): <code>
 *             -bsf[:stream_specifier] bitstream_filters (output,per-stream)</code>
 *       </ul>
 *
 * </ul>
 *
 * @param <T> A concrete class that extends from the AbstractFFmpegStreamBuilder
 */
public abstract class AbstractFFmpegStreamBuilder<T extends AbstractFFmpegStreamBuilder<T>> {

  private static final String DEVNULL = SystemUtils.IS_OS_WINDOWS ? "NUL" : "/dev/null";

  final FFmpegBuilder parent;

  /** Output filename or uri. Only one may be set */
  public String filename;

  public URI uri;

  public String format;

  public Long startOffset; // in milliseconds
  public Long duration; // in milliseconds

  public final List<String> meta_tags = new ArrayList<>();

  public boolean audio_enabled = true;
  public String audio_codec;
  public int audio_channels;
  public int audio_sample_rate;
  public String audio_preset;

  public boolean video_enabled = true;
  public String video_codec;
  public boolean video_copyinkf;
  public Fraction video_frame_rate;
  public int video_width;
  public int video_height;
  public String video_size;
  public String video_movflags;
  public String video_filter_complex;
  public Integer video_frames;
  public String video_pixel_format;

  public boolean subtitle_enabled = true;
  public String subtitle_preset;

  public String preset;
  public String presetFilename;

  public final List<String> extra_args = new ArrayList<>();

  public FFmpegBuilder.Strict strict = FFmpegBuilder.Strict.NORMAL;

  public long targetSize = 0; // in bytes
  public long pass_padding_bitrate = 1024; // in bits per second

  public boolean throwWarnings = true; // TODO Either delete this, or apply it consistently

  protected AbstractFFmpegStreamBuilder() {
    this.parent = null;
  }

  protected AbstractFFmpegStreamBuilder(FFmpegBuilder parent, String filename) {
    this.parent = checkNotNull(parent);
    this.filename = checkNotEmpty(filename, "filename must not be empty");
  }

  protected AbstractFFmpegStreamBuilder(FFmpegBuilder parent, URI uri) {
    this.parent = checkNotNull(parent);
    this.uri = checkValidStream(uri);
  }

  protected abstract T getThis();

  public T useOptions(EncodingOptions opts) {
    Mapper.map(opts, this);
    return getThis();
  }

  public T useOptions(MainEncodingOptions opts) {
    Mapper.map(opts, this);
    return getThis();
  }

  public T useOptions(AudioEncodingOptions opts) {
    Mapper.map(opts, this);
    return getThis();
  }

  public T useOptions(VideoEncodingOptions opts) {
    Mapper.map(opts, this);
    return getThis();
  }

  public T disableVideo() {
    this.video_enabled = false;
    return getThis();
  }

  public T disableAudio() {
    this.audio_enabled = false;
    return getThis();
  }

  public T disableSubtitle() {
    this.subtitle_enabled = false;
    return getThis();
  }

  /**
   * Sets a file to use containing presets.
   *
   * <p>Uses `-fpre`.
   *
   * @param presetFilename the preset by filename
   * @return this
   */
  public T setPresetFilename(String presetFilename) {
    this.presetFilename = checkNotEmpty(presetFilename, "file preset must not be empty");
    return getThis();
  }

  /**
   * Sets a preset by name (this only works with some codecs).
   *
   * <p>Uses `-preset`.
   *
   * @param preset the preset
   * @return this
   */
  public T setPreset(String preset) {
    this.preset = checkNotEmpty(preset, "preset must not be empty");
    return getThis();
  }

  public T setFilename(String filename) {
    this.filename = checkNotEmpty(filename, "filename must not be empty");
    return getThis();
  }

  public String getFilename() {
    return filename;
  }

  public T setUri(URI uri) {
    this.uri = checkValidStream(uri);
    return getThis();
  }

  public URI getUri() {
    return uri;
  }

  public T setFormat(String format) {
    this.format = checkNotEmpty(format, "format must not be empty");
    return getThis();
  }

  public T setVideoCodec(String codec) {
    this.video_enabled = true;
    this.video_codec = checkNotEmpty(codec, "codec must not be empty");
    return getThis();
  }

  public T setVideoCopyInkf(boolean copyinkf) {
    this.video_enabled = true;
    this.video_copyinkf = copyinkf;
    return getThis();
  }

  public T setVideoMovFlags(String movflags) {
    this.video_enabled = true;
    this.video_movflags = checkNotEmpty(movflags, "movflags must not be empty");
    return getThis();
  }

  public T setComplexVideoFilter(String filter) {
    this.video_enabled = true;
    this.video_filter_complex = checkNotEmpty(filter, "filter must not be empty");
    return getThis();
  }

  /**
   * Sets the video's frame rate
   *
   * @param frame_rate Frames per second
   * @return this
   * @see net.bramp.ffmpeg.FFmpeg#FPS_30
   * @see net.bramp.ffmpeg.FFmpeg#FPS_29_97
   * @see net.bramp.ffmpeg.FFmpeg#FPS_24
   * @see net.bramp.ffmpeg.FFmpeg#FPS_23_976
   */
  public T setVideoFrameRate(Fraction frame_rate) {
    this.video_enabled = true;
    this.video_frame_rate = checkNotNull(frame_rate);
    return getThis();
  }

  /**
   * Set the video frame rate in terms of frames per interval. For example 24fps would be 24/1,
   * however NTSC TV at 23.976fps would be 24000 per 1001.
   *
   * @param frames The number of frames within the given seconds
   * @param per The number of seconds
   * @return this
   */
  public T setVideoFrameRate(int frames, int per) {
    return setVideoFrameRate(Fraction.getFraction(frames, per));
  }

  public T setVideoFrameRate(double frame_rate) {
    return setVideoFrameRate(Fraction.getFraction(frame_rate));
  }

  /**
   * Set the number of video frames to record.
   *
   * @param frames The number of frames
   * @return this
   */
  public T setFrames(int frames) {
    this.video_enabled = true;
    this.video_frames = frames;
    return getThis();
  }

  protected static boolean isValidSize(int widthOrHeight) {
    return widthOrHeight > 0 || widthOrHeight == -1;
  }

  public T setVideoWidth(int width) {
    checkArgument(isValidSize(width), "Width must be -1 or greater than zero");

    this.video_enabled = true;
    this.video_width = width;
    return getThis();
  }

  public T setVideoHeight(int height) {
    checkArgument(isValidSize(height), "Height must be -1 or greater than zero");

    this.video_enabled = true;
    this.video_height = height;
    return getThis();
  }

  public T setVideoResolution(int width, int height) {
    checkArgument(
        isValidSize(width) && isValidSize(height),
        "Both width and height must be -1 or greater than zero");

    this.video_enabled = true;
    this.video_width = width;
    this.video_height = height;
    return getThis();
  }

  /**
   * Sets video resolution based on an abbreviation, e.g. "ntsc" for 720x480, or "vga" for 640x480
   *
   * @see <a href="https://www.ffmpeg.org/ffmpeg-utils.html#Video-size">ffmpeg video size</a>
   * @param abbreviation The abbreviation size. No validation is done, instead the value is passed
   *     as is to ffmpeg.
   * @return this
   */
  public T setVideoResolution(String abbreviation) {
    this.video_enabled = true;
    this.video_size = checkNotEmpty(abbreviation, "video abbreviation must not be empty");
    return getThis();
  }

  public T setVideoPixelFormat(String format) {
    this.video_enabled = true;
    this.video_pixel_format = checkNotEmpty(format, "format must not be empty");
    return getThis();
  }

  /**
   * Add metadata on output streams. Which keys are possible depends on the used codec.
   *
   * @param key Metadata key, e.g. "comment"
   * @param value Value to set for key
   * @return this
   */
  public T addMetaTag(String key, String value) {
    checkValidKey(key);
    checkNotEmpty(value, "value must not be empty");
    meta_tags.add("-metadata");
    meta_tags.add(key + "=" + value);
    return getThis();
  }

  /**
   * Add metadata on output streams. Which keys are possible depends on the used codec.
   *
   * <pre>{@code
   * import static net.bramp.ffmpeg.builder.MetadataSpecifier.*;
   * import static net.bramp.ffmpeg.builder.StreamSpecifier.*;
   * import static net.bramp.ffmpeg.builder.StreamSpecifierType.*;
   *
   * new FFmpegBuilder()
   *   .addMetaTag("title", "Movie Title") // Annotate whole file
   *   .addMetaTag(chapter(0), "author", "Bob") // Annotate first chapter
   *   .addMetaTag(program(0), "comment", "Awesome") // Annotate first program
   *   .addMetaTag(stream(0), "copyright", "Megacorp") // Annotate first stream
   *   .addMetaTag(stream(Video), "framerate", "24fps") // Annotate all video streams
   *   .addMetaTag(stream(Video, 0), "artist", "Joe") // Annotate first video stream
   *   .addMetaTag(stream(Audio, 0), "language", "eng") // Annotate first audio stream
   *   .addMetaTag(stream(Subtitle, 0), "language", "fre") // Annotate first subtitle stream
   *   .addMetaTag(usable(), "year", "2010") // Annotate all streams with a usable configuration
   * }</pre>
   *
   * @param spec Metadata specifier, e.g `MetadataSpec.stream(Audio, 0)`
   * @param key Metadata key, e.g. "comment"
   * @param value Value to set for key
   * @return this
   */
  public T addMetaTag(MetadataSpecifier spec, String key, String value) {
    checkValidKey(key);
    checkNotEmpty(value, "value must not be empty");
    meta_tags.add("-metadata:" + spec.spec());
    meta_tags.add(key + "=" + value);
    return getThis();
  }

  public T setAudioCodec(String codec) {
    this.audio_enabled = true;
    this.audio_codec = checkNotEmpty(codec, "codec must not be empty");
    return getThis();
  }

  /**
   * Sets the number of audio channels
   *
   * @param channels Number of channels
   * @return this
   * @see net.bramp.ffmpeg.FFmpeg#AUDIO_MONO
   * @see net.bramp.ffmpeg.FFmpeg#AUDIO_STEREO
   */
  public T setAudioChannels(int channels) {
    checkArgument(channels > 0, "channels must be positive");
    this.audio_enabled = true;
    this.audio_channels = channels;
    return getThis();
  }

  /**
   * Sets the Audio sample rate, for example 44_000.
   *
   * @param sample_rate Samples measured in Hz
   * @return this
   * @see net.bramp.ffmpeg.FFmpeg#AUDIO_SAMPLE_8000
   * @see net.bramp.ffmpeg.FFmpeg#AUDIO_SAMPLE_11025
   * @see net.bramp.ffmpeg.FFmpeg#AUDIO_SAMPLE_12000
   * @see net.bramp.ffmpeg.FFmpeg#AUDIO_SAMPLE_16000
   * @see net.bramp.ffmpeg.FFmpeg#AUDIO_SAMPLE_22050
   * @see net.bramp.ffmpeg.FFmpeg#AUDIO_SAMPLE_32000
   * @see net.bramp.ffmpeg.FFmpeg#AUDIO_SAMPLE_44100
   * @see net.bramp.ffmpeg.FFmpeg#AUDIO_SAMPLE_48000
   * @see net.bramp.ffmpeg.FFmpeg#AUDIO_SAMPLE_96000
   */
  public T setAudioSampleRate(int sample_rate) {
    checkArgument(sample_rate > 0, "sample rate must be positive");
    this.audio_enabled = true;
    this.audio_sample_rate = sample_rate;
    return getThis();
  }

  /**
   * Target output file size (in bytes)
   *
   * @param targetSize The target size in bytes
   * @return this
   */
  public T setTargetSize(long targetSize) {
    checkArgument(targetSize > 0, "target size must be positive");
    this.targetSize = targetSize;
    return getThis();
  }

  /**
   * Decodes but discards input until the offset.
   *
   * @param offset The offset
   * @param units The units the offset is in
   * @return this
   */
  public T setStartOffset(long offset, TimeUnit units) {
    checkNotNull(units);

    this.startOffset = units.toMillis(offset);

    return getThis();
  }

  /**
   * Stop writing the output after duration is reached.
   *
   * @param duration The duration
   * @param units The units the duration is in
   * @return this
   */
  public T setDuration(long duration, TimeUnit units) {
    checkNotNull(units);

    this.duration = units.toMillis(duration);

    return getThis();
  }

  public T setStrict(FFmpegBuilder.Strict strict) {
    this.strict = checkNotNull(strict);
    return getThis();
  }

  /**
   * When doing multi-pass we add a little extra padding, to ensure we reach our target
   *
   * @param bitrate bit rate
   * @return this
   */
  public T setPassPaddingBitrate(long bitrate) {
    checkArgument(bitrate > 0, "bitrate must be positive");
    this.pass_padding_bitrate = bitrate;
    return getThis();
  }

  /**
   * Sets a audio preset to use.
   *
   * <p>Uses `-apre`.
   *
   * @param preset the preset
   * @return this
   */
  public T setAudioPreset(String preset) {
    this.audio_enabled = true;
    this.audio_preset = checkNotEmpty(preset, "audio preset must not be empty");
    return getThis();
  }

  /**
   * Sets a subtitle preset to use.
   *
   * <p>Uses `-spre`.
   *
   * @param preset the preset
   * @return this
   */
  public T setSubtitlePreset(String preset) {
    this.subtitle_enabled = true;
    this.subtitle_preset = checkNotEmpty(preset, "subtitle preset must not be empty");
    return getThis();
  }

  /**
   * Add additional output arguments (for flags which aren't currently supported).
   *
   * @param values The extra arguments
   * @return this
   */
  public T addExtraArgs(String... values) {
    checkArgument(values.length > 0, "one or more values must be supplied");
    checkNotEmpty(values[0], "first extra arg may not be empty");

    for (String value : values) {
      extra_args.add(checkNotNull(value));
    }
    return getThis();
  }

  /**
   * Finished with this output
   *
   * @return the parent FFmpegBuilder
   */
  public FFmpegBuilder done() {
    Preconditions.checkState(parent != null, "Can not call done without parent being set");
    return parent;
  }

  /**
   * Returns a representation of this Builder that can be safely serialised.
   *
   * <p>NOTE: This method is horribly out of date, and its use should be rethought.
   *
   * @return A new EncodingOptions capturing this Builder's state
   */
  public abstract EncodingOptions buildOptions();

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
  protected List<String> build(FFmpegBuilder parent, int pass) {
    checkNotNull(parent);

    if (pass > 0) {
      // TODO Write a test for this:
      checkArgument(format != null, "Format must be specified when using two-pass");
    }

    ImmutableList.Builder<String> args = new ImmutableList.Builder<>();

    if (strict != FFmpegBuilder.Strict.NORMAL) {
      args.add("-strict", strict.toString());
    }

    if (!Strings.isNullOrEmpty(format)) {
      args.add("-f", format);
    }

    if (!Strings.isNullOrEmpty(preset)) {
      args.add("-preset", preset);
    }

    if (!Strings.isNullOrEmpty(presetFilename)) {
      args.add("-fpre", presetFilename);
    }

    if (startOffset != null) {
      args.add("-ss", toTimecode(startOffset, TimeUnit.MILLISECONDS));
    }

    if (duration != null) {
      args.add("-t", toTimecode(duration, TimeUnit.MILLISECONDS));
    }

    args.addAll(meta_tags);

    if (video_enabled) {
      addVideoFlags(parent, args);
    } else {
      args.add("-vn");
    }

    if (audio_enabled && pass != 1) {
      addAudioFlags(args);
    } else {
      args.add("-an");
    }

    if (subtitle_enabled) {
      if (!Strings.isNullOrEmpty(subtitle_preset)) {
        args.add("-spre", subtitle_preset);
      }
    } else {
      args.add("-sn");
    }

    args.addAll(extra_args);

    if (filename != null && uri != null) {
      throw new IllegalStateException("Only one of filename and uri can be set");
    }

    // Output
    if (pass == 1) {
      args.add(DEVNULL);
    } else if (filename != null) {
      args.add(filename);
    } else if (uri != null) {
      args.add(uri.toString());
    } else {
      assert (false);
    }

    return args.build();
  }

  protected void addAudioFlags(ImmutableList.Builder<String> args) {
    if (!Strings.isNullOrEmpty(audio_codec)) {
      args.add("-acodec", audio_codec);
    }

    if (audio_channels > 0) {
      args.add("-ac", String.valueOf(audio_channels));
    }

    if (audio_sample_rate > 0) {
      args.add("-ar", String.valueOf(audio_sample_rate));
    }

    if (!Strings.isNullOrEmpty(audio_preset)) {
      args.add("-apre", audio_preset);
    }
  }

  protected void addVideoFlags(FFmpegBuilder parent, ImmutableList.Builder<String> args) {
    if (video_frames != null) {
      args.add("-vframes", video_frames.toString());
    }

    if (!Strings.isNullOrEmpty(video_codec)) {
      args.add("-vcodec", video_codec);
    }

    if (!Strings.isNullOrEmpty(video_pixel_format)) {
      args.add("-pix_fmt", video_pixel_format);
    }

    if (video_copyinkf) {
      args.add("-copyinkf");
    }

    if (!Strings.isNullOrEmpty(video_movflags)) {
      args.add("-movflags", video_movflags);
    }

    if (video_size != null) {
      checkArgument(
          video_width == 0 && video_height == 0,
          "Can not specific width or height, as well as an abbreviatied video size");
      args.add("-s", video_size);

    } else if (video_width != 0 && video_height != 0) {
      args.add("-s", String.format("%dx%d", video_width, video_height));
    }

    // TODO What if width is set but heigh isn't. We don't seem to do anything

    if (video_frame_rate != null) {
      args.add("-r", video_frame_rate.toString());
    }

    if (!Strings.isNullOrEmpty(video_filter_complex)) {
      args.add("-filter_complex", video_filter_complex);
    }
  }
}
