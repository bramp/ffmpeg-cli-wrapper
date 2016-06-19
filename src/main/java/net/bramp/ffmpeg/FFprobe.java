package net.bramp.ffmpeg;

import com.google.common.base.Charsets;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import net.bramp.ffmpeg.io.LoggingFilterReader;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Wrapper around FFprobe
 * 
 * TODO ffprobe -v quiet -print_format json -show_format -show_streams mobileedge_1280x720.mp4
 * 
 * @author bramp
 *
 */
public class FFprobe {

  final static Logger LOG = LoggerFactory.getLogger(FFprobe.class);

  final static String FFPROBE = "ffprobe";
  final static String DEFAULT_PATH = MoreObjects.firstNonNull(System.getenv("FFPROBE"), FFPROBE);

  final static Gson gson = FFmpegUtils.getGson();

  final String path;

  /**
   * Function to run FFprobe. We define it like this so we can swap it out (during testing)
   */
  final ProcessFunction runFunc;

  public FFprobe() {
    this(DEFAULT_PATH, new RunProcessFunction());
  }

  public FFprobe(@Nonnull String path) {
    this(path, new RunProcessFunction());
  }

  public FFprobe(@Nonnull ProcessFunction runFunction) {
    this(DEFAULT_PATH, runFunction);
  }

  public FFprobe(@Nonnull String path, @Nonnull ProcessFunction runFunction) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(path));
    this.runFunc = checkNotNull(runFunction);
    this.path = path;
  }

  public String getPath() {
    return path;
  }

  private static BufferedReader wrapInReader(Process p) {
    return new BufferedReader(new InputStreamReader(p.getInputStream(), Charsets.UTF_8));
  }

  public FFmpegProbeResult probe(String mediaPath) throws IOException {
    ImmutableList.Builder<String> args = new ImmutableList.Builder<String>();

    // TODO Add:
    // .add("--show_packets")
    // .add("--show_frames")

    // @formatter:off
    args.add(path)
        .add("-v", "quiet")
        .add("-print_format", "json")
        .add("-show_error")
        .add("-show_format")
        .add("-show_streams")
        .add(mediaPath);
    // @formatter:on

    Process p = runFunc.run(args.build());
    try {
      Reader reader = wrapInReader(p);
      if (LOG.isDebugEnabled()) {
        reader = new LoggingFilterReader(reader, LOG);
      }

      FFmpegProbeResult result = gson.fromJson(reader, FFmpegProbeResult.class);

      FFmpegUtils.throwOnError(FFPROBE, p);

      if (result == null) {
        throw new IllegalStateException("Gson returned null, which shouldn't happen :(");
      }

      return result;

    } finally {
      p.destroy();
    }
  }
}
