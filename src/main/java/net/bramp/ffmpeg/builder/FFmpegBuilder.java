package net.bramp.ffmpeg.builder;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import net.bramp.ffmpeg.FFmpegUtils;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Builds a ffmpeg command line
 *
 * @author bramp
 */
public class FFmpegBuilder {

  final static Logger LOG = LoggerFactory.getLogger(FFmpegBuilder.class);

  public enum Strict {
    VERY, // strictly conform to a older more strict version of the specifications or reference
          // software
    STRICT, // strictly conform to all the things in the specificiations no matter what consequences
    NORMAL, // normal
    UNOFFICAL, // allow unofficial extensions
    EXPERIMENTAL;

    // ffmpeg command line requires these options in lower case
    @Override
    public String toString() {
      return name().toLowerCase();
    }
  }

  /**
   * Log level options: https://ffmpeg.org/ffmpeg.html#Generic-options
   */
  public enum Verbosity {
    QUIET, PANIC, FATAL, ERROR, WARNING, INFO, VERBOSE, DEBUG;

    @Override
    public String toString() {
      return name().toLowerCase();
    }
  }

  // Global Settings
  boolean override = true;
  int pass = 0;
  String pass_prefix;
  Verbosity verbosity = Verbosity.ERROR;
  URI progress;

  // Input settings
  String format;
  Long startOffset; // in millis
  boolean read_at_native_frame_rate = false;
  final List<String> inputs = new ArrayList<>();
  final Map<String, FFmpegProbeResult> inputProbes = new TreeMap<>();

  final List<String> extra_args = new ArrayList<>();

  // Output
  final List<FFmpegOutputBuilder> outputs = new ArrayList<>();

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

  public FFmpegBuilder setPassPrefix(String prefix) {
    this.pass_prefix = checkNotNull(prefix);
    return this;
  }

  public FFmpegBuilder setVerbosity(Verbosity verbosity) {
    checkNotNull(verbosity);
    this.verbosity = verbosity;
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

  public FFmpegBuilder setFormat(String format) {
    this.format = checkNotNull(format);
    return this;
  }

  public FFmpegBuilder setStartOffset(long duration, TimeUnit units) {
    checkNotNull(duration);
    checkNotNull(units);

    this.startOffset = units.toMillis(duration);

    return this;
  }

  public FFmpegBuilder addProgress(URI uri) {
    this.progress = checkNotNull(uri);
    return this;
  }


  /**
   * Add additional ouput arguments (for flags which aren't currently supported).
   *
   * @param values
   */
  public FFmpegBuilder addExtraArgs(String... values) {
    checkArgument(values.length > 0, "One or more values must be supplied");
    for (String value : values) {
      extra_args.add(checkNotNull(value));
    }
    return this;
  }


  /**
   * Create new output file.
   * 
   * @param filename output file path
   * @return A new {@link FFmpegOutputBuilder}
   */
  public FFmpegOutputBuilder addOutput(String filename) {
    FFmpegOutputBuilder output = new FFmpegOutputBuilder(this, filename);
    outputs.add(output);
    return output;
  }

  public FFmpegOutputBuilder addOutput(URI uri) {
    FFmpegOutputBuilder output = new FFmpegOutputBuilder(this, uri);
    outputs.add(output);
    return output;
  }

  /**
   * Create new output (to stdout)
   */
  public FFmpegOutputBuilder addStdoutOutput() {
    return addOutput("-");
  }

  public List<String> build() {
    ImmutableList.Builder<String> args = new ImmutableList.Builder<String>();

    Preconditions.checkArgument(!inputs.isEmpty(), "At least one input must be specified");
    Preconditions.checkArgument(!outputs.isEmpty(), "At least one output must be specified");

    args.add(override ? "-y" : "-n");
    args.add("-v", this.verbosity.toString());

    if (startOffset != null) {
      args.add("-ss", FFmpegUtils.millisecondsToString(startOffset));
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
        args.add("-passlogfile", pass_prefix);
      }
    }

    for (FFmpegOutputBuilder output : this.outputs) {
      args.addAll(output.build(pass));
    }

    return args.build();
  }
}
