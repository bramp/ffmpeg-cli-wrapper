package net.bramp.ffmpeg.builder;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static net.bramp.ffmpeg.Preconditions.checkNotEmpty;

import com.google.common.base.Ascii;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import javax.annotation.CheckReturnValue;
import net.bramp.ffmpeg.FFmpegUtils;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Builds a ffmpeg command line
 *
 * @author bramp
 */
public class FFmpegBuilder {

  private static final Logger log = LoggerFactory.getLogger(FFmpegBuilder.class);

  /** Log level options: <a href="https://ffmpeg.org/ffmpeg.html#Generic-options">ffmpeg documentation</a> */
  public enum Verbosity {
    QUIET,
    PANIC,
    FATAL,
    ERROR,
    WARNING,
    INFO,
    VERBOSE,
    DEBUG;

    @Override
    public String toString() {
      // ffmpeg command line requires these options in lower case
      return Ascii.toLowerCase(name());
    }
  }

  // Global Settings
  boolean override = true;
  int pass = 0;
  String pass_directory = "";
  String pass_prefix;
  Verbosity verbosity = Verbosity.ERROR;
  URI progress;
  String user_agent;
  Integer qscale;

  int threads;
  // Input settings
  String format;
  Long startOffset; // in millis
  boolean read_at_native_frame_rate = false;
  final List<AbstractFFmpegInputBuilder<?>> inputs = new ArrayList<>();
  final Map<String, FFmpegProbeResult> inputProbes = new TreeMap<>();

  final List<String> extra_args = new ArrayList<>();

  // Output
  final List<AbstractFFmpegOutputBuilder<?>> outputs = new ArrayList<>();

  protected Strict strict = Strict.NORMAL;

  // Filters
  String audioFilter;
  String videoFilter;
  String complexFilter;

  public FFmpegBuilder setStrict(Strict strict) {
    this.strict = checkNotNull(strict);
    return this;
  }

  public FFmpegBuilder overrideOutputFiles(boolean override) {
    this.override = override;
    return this;
  }

  public boolean getOverrideOutputFiles() {
    return this.override;
  }

  public FFmpegBuilder setPass(int pass) {
    this.pass = pass;
    return this;
  }

  public FFmpegBuilder setPassDirectory(String directory) {
    this.pass_directory = checkNotNull(directory);
    return this;
  }

  public FFmpegBuilder setPassPrefix(String prefix) {
    this.pass_prefix = checkNotNull(prefix);
    return this;
  }

  public FFmpegBuilder setVerbosity(Verbosity verbosity) {
    checkNotNull(verbosity);
    this.verbosity = verbosity;
    return this;
  }

  public FFmpegBuilder setUserAgent(String userAgent) {
    this.user_agent = checkNotNull(userAgent);
    return this;
  }

  /**
   * Makes ffmpeg read the first input at the native frame read
   * @return this
   * @deprecated Use {@link AbstractFFmpegInputBuilder#readAtNativeFrameRate()} instead
   */
  @Deprecated
  public FFmpegBuilder readAtNativeFrameRate() {
    this.read_at_native_frame_rate = true;
    return this;
  }

  public FFmpegFileInputBuilder addInput(FFmpegProbeResult result) {
    checkNotNull(result);
    String filename = checkNotNull(result.getFormat()).getFilename();

    return this.doAddInput(new FFmpegFileInputBuilder(this, filename, result));
  }

  public FFmpegFileInputBuilder addInput(String filename) {
    checkNotNull(filename);

    return this.doAddInput(new FFmpegFileInputBuilder(this, filename));
  }

  public <T extends AbstractFFmpegInputBuilder<T>> FFmpegBuilder addInput(T input) {
    return this.doAddInput(input).done();
  }

  protected <T extends AbstractFFmpegInputBuilder<T>> T doAddInput(T input) {
    checkNotNull(input);

    inputs.add(input);
    return input;
  }

  protected void clearInputs() {
    inputs.clear();
    inputProbes.clear();
  }

  public FFmpegFileInputBuilder setInput(FFmpegProbeResult result) {
    clearInputs();
    return addInput(result);
  }

  public FFmpegFileInputBuilder setInput(String filename) {
    clearInputs();
    return addInput(filename);
  }

  public <T extends AbstractFFmpegInputBuilder<T>> FFmpegBuilder setInput(T input) {
    checkNotNull(input);

    clearInputs();
    inputs.add(input);

    return this;
  }

  public FFmpegBuilder setThreads(int threads) {
    checkArgument(threads > 0, "threads must be greater than zero");
    this.threads = threads;
    return this;
  }

  /**
   * Sets the format for the first input stream
   * @param format, the format of this input stream, not null
   * @return this
   * @deprecated Specify this option on an input stream using {@link AbstractFFmpegStreamBuilder#setFormat(String)}
   */
  @Deprecated
  public FFmpegBuilder setFormat(String format) {
    this.format = checkNotNull(format);
    return this;
  }

  /**
   * Sets the start offset for the first input stream
   * @param duration the amount of the offset, measured in terms of the unit
   * @param units the unit that the duration is measured in, not null
   * @return this
   * @deprecated Specify this option on an input or output stream using {@link AbstractFFmpegStreamBuilder#setStartOffset(long, TimeUnit)}
   */
  @Deprecated
  public FFmpegBuilder setStartOffset(long duration, TimeUnit units) {
    checkNotNull(units);

    this.startOffset = units.toMillis(duration);

    return this;
  }

  public FFmpegBuilder addProgress(URI uri) {
    this.progress = checkNotNull(uri);
    return this;
  }

  /**
   * Sets the complex filter flag.
   *
   * @param filter the complex filter string
   * @return this
   * @deprecated Use {@link AbstractFFmpegOutputBuilder#setComplexFilter(String)} instead
   */
  @Deprecated
  public FFmpegBuilder setComplexFilter(String filter) {
    this.complexFilter = checkNotEmpty(filter, "filter must not be empty");
    return this;
  }

  /**
   * Sets the audio filter flag.
   *
   * @param filter the audio filter string
   * @return this
   */
  public FFmpegBuilder setAudioFilter(String filter) {
    this.audioFilter = checkNotEmpty(filter, "filter must not be empty");
    return this;
  }

  /**
   * Sets the video filter flag.
   *
   * @param filter the video filter string
   * @return this
   */
  public FFmpegBuilder setVideoFilter(String filter) {
    this.videoFilter = checkNotEmpty(filter, "filter must not be empty");
    return this;
  }

  /**
   * Sets vbr quality when decoding mp3 output.
   *
   * @param quality the quality between 0 and 9. Where 0 is best.
   * @return FFmpegBuilder
   */
  public FFmpegBuilder setVBR(Integer quality) {
    Preconditions.checkArgument(quality > 0 && quality < 9, "vbr must be between 0 and 9");
    this.qscale = quality;
    return this;
  }

  /**
   * Add additional ouput arguments (for flags which aren't currently supported).
   *
   * @param values The extra arguments.
   * @return this
   */
  public FFmpegBuilder addExtraArgs(String... values) {
    checkArgument(values.length > 0, "one or more values must be supplied");
    checkNotEmpty(values[0], "first extra arg may not be empty");

    for (String value : values) {
      extra_args.add(checkNotNull(value));
    }
    return this;
  }

  /**
   * Adds new output file.
   *
   * @param filename output file path
   * @return A new {@link FFmpegOutputBuilder}
   */
  public FFmpegOutputBuilder addOutput(String filename) {
    FFmpegOutputBuilder output = new FFmpegOutputBuilder(this, filename);
    outputs.add(output);
    return output;
  }

  /**
   * Adds new output file.
   *
   * @param uri output file uri typically a stream
   * @return A new {@link FFmpegOutputBuilder}
   */
  public FFmpegOutputBuilder addOutput(URI uri) {
    FFmpegOutputBuilder output = new FFmpegOutputBuilder(this, uri);
    outputs.add(output);
    return output;
  }

  /**
   * Adds new HLS(Http Live Streaming) output file.
   * <br>
   * <pre>
   * <code>List&lt;String&gt; args = new FFmpegBuilder()
   *   .addHlsOutput(&quot;output.m3u8&quot;)
   *   .done().build();</code>
   * </pre>
   *
   * @param filename output file path
   *
   * @return A new {@link FFmpegHlsOutputBuilder}
   */
  public FFmpegHlsOutputBuilder addHlsOutput(String filename) {
    FFmpegHlsOutputBuilder output = new FFmpegHlsOutputBuilder(this, filename);
    outputs.add(output);
    return output;
  }

  /**
   * Adds an existing FFmpegOutputBuilder. This is similar to calling the other addOuput methods but
   * instead allows an existing FFmpegOutputBuilder to be used, and reused.
   *
   * <pre>
   * <code>List&lt;String&gt; args = new FFmpegBuilder()
   *   .addOutput(new FFmpegOutputBuilder()
   *     .setFilename(&quot;output.flv&quot;)
   *     .setVideoCodec(&quot;flv&quot;)
   *   )
   *   .build();</code>
   * </pre>
   *
   * @param output FFmpegOutputBuilder to add
   * @return this
   */
  public FFmpegBuilder addOutput(FFmpegOutputBuilder output) {
    outputs.add(output);
    return this;
  }

  /**
   * Create new output (to stdout)
   *
   * @return A new {@link FFmpegOutputBuilder}
   */
  public FFmpegOutputBuilder addStdoutOutput() {
    return addOutput("-");
  }

  @CheckReturnValue
  public List<String> build() {
    ImmutableList.Builder<String> args = new ImmutableList.Builder<>();

    Preconditions.checkArgument(!inputs.isEmpty(), "At least one input must be specified");
    Preconditions.checkArgument(!outputs.isEmpty(), "At least one output must be specified");

    if (strict != Strict.NORMAL) {
      args.add("-strict", strict.toString());
    }

    args.add(override ? "-y" : "-n");
    args.add("-v", this.verbosity.toString());

    if (user_agent != null) {
      args.add("-user_agent", user_agent);
    }

    if (startOffset != null) {
      log.warn("Using FFmpegBuilder#setStartOffset is deprecated. Specify it on the inputStream or outputStream instead");
      args.add("-ss", FFmpegUtils.toTimecode(startOffset, TimeUnit.MILLISECONDS));
    }

    if (threads > 0) {
      args.add("-threads", String.valueOf(threads));
    }

    if (format != null) {
      log.warn("Using FFmpegBuilder#setFormat is deprecated. Specify it on the inputStream or outputStream instead");
      args.add("-f", format);
    }

    if (read_at_native_frame_rate) {
      log.warn("Using FFmpegBuilder#readAtNativeFrameRate is deprecated. Specify it on the inputStream instead");
      args.add("-re");
    }

    if (progress != null) {
      args.add("-progress", progress.toString());
    }

    args.addAll(extra_args);

    for (AbstractFFmpegInputBuilder<?> input : this.inputs) {
      args.addAll(input.build(this, pass));
    }

    if (pass > 0) {
      args.add("-pass", Integer.toString(pass));

      if (pass_prefix != null) {
        args.add("-passlogfile", pass_directory + pass_prefix);
      }
    }

    if (!Strings.isNullOrEmpty(audioFilter)) {
      args.add("-af", audioFilter);
    }

    if (!Strings.isNullOrEmpty(videoFilter)) {
      args.add("-vf", videoFilter);
    }

    if (!Strings.isNullOrEmpty(complexFilter)) {
      log.warn("Using FFmpegBuilder#setComplexFilter is deprecated. Specify it on the outputStream instead");
      args.add("-filter_complex", complexFilter);
    }

    if (qscale != null) {
      args.add("-qscale:a", qscale.toString());
    }

    for (AbstractFFmpegOutputBuilder<?> output : this.outputs) {
      args.addAll(output.build(this, pass));
    }

    return args.build();
  }
}
