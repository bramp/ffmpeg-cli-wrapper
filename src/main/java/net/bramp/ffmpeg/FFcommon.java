package net.bramp.ffmpeg;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.io.CharStreams;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.Nonnull;
import net.bramp.ffmpeg.io.ProcessUtils;

/** Private class to contain common methods for both FFmpeg and FFprobe. */
abstract class FFcommon {

  /** Path to the binary (e.g. /usr/bin/ffmpeg) */
  final String path;

  /** Function to run FFmpeg. We define it like this so we can swap it out (during testing) */
  final ProcessFunction runFunc;

  /** Version string */
  String version = null;

  /** Process input stream */
  Appendable processOutputStream = System.out;

  /** Process error stream */
  Appendable processErrorStream = System.err;

  public FFcommon(@Nonnull String path) {
    this(path, new RunProcessFunction());
  }

  protected FFcommon(@Nonnull String path, @Nonnull ProcessFunction runFunction) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(path));
    this.runFunc = checkNotNull(runFunction);
    this.path = path;
  }

  public void setProcessOutputStream(@Nonnull Appendable processOutputStream) {
    Preconditions.checkNotNull(processOutputStream);
    this.processOutputStream = processOutputStream;
  }

  public void setProcessErrorStream(@Nonnull Appendable processErrorStream) {
    Preconditions.checkNotNull(processErrorStream);
    this.processErrorStream = processErrorStream;
  }

  private BufferedReader _wrapInReader(final InputStream inputStream) {
    return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
  }

  protected BufferedReader wrapInReader(Process p) {
    return _wrapInReader(p.getInputStream());
  }

  protected BufferedReader wrapErrorInReader(Process p) {
    return _wrapInReader(p.getErrorStream());
  }

  protected void throwOnError(Process p) throws IOException {
    try {
      // TODO In java 8 use waitFor(long timeout, TimeUnit unit)
      if (ProcessUtils.waitForWithTimeout(p, 1, TimeUnit.SECONDS) != 0) {
        // TODO Parse the error
        throw new IOException(path + " returned non-zero exit status. Check stdout.");
      }
    } catch (TimeoutException e) {
      throw new IOException("Timed out waiting for " + path + " to finish.");
    }
  }

  /**
   * Returns the version string for this binary.
   *
   * @return the version string.
   * @throws IOException If there is an error capturing output from the binary.
   */
  public synchronized @Nonnull String version() throws IOException {
    if (this.version == null) {
      Process p = runFunc.run(ImmutableList.of(path, "-version"));
      try {
        BufferedReader r = wrapInReader(p);
        this.version = r.readLine();
        CharStreams.copy(r, CharStreams.nullWriter()); // Throw away rest of the output

        throwOnError(p);
      } finally {
        p.destroy();
      }
    }
    return version;
  }

  public String getPath() {
    return path;
  }

  /**
   * Returns the full path to the binary with arguments appended.
   *
   * @param args The arguments to pass to the binary.
   * @return The full path and arguments to execute the binary.
   * @throws IOException If there is an error capturing output from the binary
   */
  public List<String> path(List<String> args) throws IOException {
    return ImmutableList.<String>builder().add(path).addAll(args).build();
  }

  /**
   * Runs the binary (ffmpeg) with the supplied args. Blocking until finished.
   *
   * @param args The arguments to pass to the binary.
   * @throws IOException If there is a problem executing the binary.
   */
  public void run(List<String> args) throws IOException {
    checkNotNull(args);

    Process p = runFunc.run(path(args));
    assert (p != null);

    try {
      // TODO Move the copy onto a thread, so that FFmpegProgressListener can be on this thread.

      // Now block reading ffmpeg's stdout. We are effectively throwing away the output.
      CharStreams.copy(wrapInReader(p), processOutputStream);
      CharStreams.copy(wrapErrorInReader(p), processErrorStream);
      throwOnError(p);

    } finally {
      p.destroy();
    }
  }
}
