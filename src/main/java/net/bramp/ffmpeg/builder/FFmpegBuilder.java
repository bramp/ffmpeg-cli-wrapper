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

/**
 * Builds a ffmpeg command line
 *
 * @author bramp
 */
public class FFmpegBuilder {

  public enum Strict {
    VERY, // strictly conform to a older more strict version of the specifications or reference
    // software
    STRICT, // strictly conform to all the things in the specificiations no matter what consequences
    NORMAL, // normal
    UNOFFICIAL, // allow unofficial extensions
    EXPERIMENTAL;

    @Override
    public String toString() {
      // ffmpeg command line requires these options in lower case
      return Ascii.toLowerCase(name());
    }
  }

  /** Log level options: https://ffmpeg.org/ffmpeg.html#Generic-options */
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
  final List<String> inputs = new ArrayList<>();
  final Map<String, FFmpegProbeResult> inputProbes = new TreeMap<>();

  final List<String> extra_args = new ArrayList<>();

  // Output
  final List<FFmpegOutputBuilder> outputs = new ArrayList<>();

  // Filters
  String audioFilter;
  String videoFilter;
  String complexFilter;

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

  public FFmpegBuilder readAtNativeFrameRate() {
    this.read_at_native_frame_rate = true;
    return this;
  }

  public FFmpegBuilder addInput(FFmpegProbeResult result) {
    checkNotNull(result);
    String filename = checkNotNull(result.format).filename;
    inputProbes.put(filename, result);
    return addInput(filename);
  }

  public FFmpegBuilder addInput(String filename) {
    checkNotNull(filename);
    inputs.add(filename);
    return this;
  }

  protected void clearInputs() {
    inputs.clear();
    inputProbes.clear();
  }

  public FFmpegBuilder setInput(FFmpegProbeResult result) {
    clearInputs();
    return addInput(result);
  }

  public FFmpegBuilder setInput(String filename) {
    clearInputs();
    return addInput(filename);
  }

  public FFmpegBuilder setThreads(int threads) {
    checkArgument(threads > 0, "threads must be greater than zero");
    this.threads = threads;
    return this;
  }

  public FFmpegBuilder setFormat(String format) {
    this.format = checkNotNull(format);
    return this;
  }

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
   */
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
   *   .build();</code>
   * </pre>
   *
   * @param filename output file path
   *
   * @return A new {@link FFmpegHlsOutputBuilder}
   */
  public FFmpegHlsOutputBuilder addHlsOutput(String filename) {
    checkArgument(format == null || format.equals("hls"),"The format is already set to a value other than hls.");
    if(format == null) setFormat("hls");
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
    ImmutableList.Builder<String> args = new ImmutableList.Builder<String>();

    Preconditions.checkArgument(!inputs.isEmpty(), "At least one input must be specified");
    Preconditions.checkArgument(!outputs.isEmpty(), "At least one output must be specified");

    args.add(override ? "-y" : "-n");
    args.add("-v", this.verbosity.toString());

    if (user_agent != null) {
      args.add("-user_agent", user_agent);
    }

    if (startOffset != null) {
      args.add("-ss", FFmpegUtils.toTimecode(startOffset, TimeUnit.MILLISECONDS));
    }

    if (threads > 0) {
      args.add("-threads", String.valueOf(threads));
    }

    if (format != null) {
      args.add("-f", format);
    }

    if (read_at_native_frame_rate) {
      args.add("-re");
    }

    if (progress != null) {
      args.add("-progress", progress.toString());
    }

    args.addAll(extra_args);

    for (String input : inputs) {
      args.add("-i", input);
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
      args.add("-filter_complex", complexFilter);
    }

    if (qscale != null) {
      args.add("-qscale:a", qscale.toString());
    }

    for (FFmpegOutputBuilder output : this.outputs) {
      args.addAll(output.build(this, pass));
    }

    return args.build();
  }
}
