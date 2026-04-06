package net.bramp.ffmpeg;

import java.io.IOException;
import java.util.List;

/**
 * Runs a process returning a Reader to its stdout.
 *
 * @author bramp
 */
public interface ProcessFunction {
  /** Runs a process with the given arguments and returns the process handle. */
  Process run(List<String> args) throws IOException;
}
