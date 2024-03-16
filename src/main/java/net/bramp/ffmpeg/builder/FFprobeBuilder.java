package net.bramp.ffmpeg.builder;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import javax.annotation.CheckReturnValue;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static net.bramp.ffmpeg.Preconditions.checkNotEmpty;

/**
 * Builds a ffprobe command line
 */
public class FFprobeBuilder {
  private boolean showFormat = true;
  private boolean showStreams = true;
  private boolean showChapters = true;
  private boolean showFrames = false;
  private boolean showPackets = false;
  private String userAgent;
  private String input;

  private final List<String> extraArgs = new ArrayList<>();

  public FFprobeBuilder setShowFormat(boolean showFormat) {
    this.showFormat = showFormat;
    return this;
  }

  public FFprobeBuilder setShowStreams(boolean showStreams) {
    this.showStreams = showStreams;
    return this;
  }

  public FFprobeBuilder setShowChapters(boolean showChapters) {
    this.showChapters = showChapters;
    return this;
  }

  public FFprobeBuilder setShowFrames(boolean showFrames) {
    this.showFrames = showFrames;
    return this;
  }

  public FFprobeBuilder setShowPackets(boolean showPackets) {
    this.showPackets = showPackets;
    return this;
  }

  public FFprobeBuilder setUserAgent(String userAgent) {
    this.userAgent = userAgent;
    return this;
  }

  public FFprobeBuilder setInput(String filename) {
    checkNotNull(filename);
    this.input = filename;
    return this;
  }

  public FFprobeBuilder addExtraArgs(String... values) {
    checkArgument(values != null, "extraArgs can not be null");
    checkArgument(values.length > 0, "one or more values must be supplied");
    checkNotEmpty(values[0], "first extra arg may not be empty");

    for (String value : values) {
      extraArgs.add(checkNotNull(value));
    }
    return this;
  }

  @CheckReturnValue
  public List<String> build() {
    ImmutableList.Builder<String> args = new ImmutableList.Builder<>();

    Preconditions.checkNotNull(input, "Input must be specified");

    args
        .add("-v", "quiet")
        .add("-print_format", "json")
        .add("-show_error");

    if (userAgent != null) {
      args.add("-user_agent", userAgent);
    }

    args.addAll(extraArgs);

    if (showFormat) args.add("-show_format");
    if (showStreams) args.add("-show_streams");
    if (showChapters) args.add("-show_chapters");
    if (showPackets) args.add("-show_packets");
    if (showFrames) args.add("-show_frames");

    args.add(input);

    return args.build();
  }
}
