package net.bramp.ffmpeg;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Simple function that creates a Process with the arguments, and returns a BufferedReader reading
 * stdout
 *
 * @author bramp
 */
public class RunProcessFunction implements ProcessFunction {

  static final Logger LOG = LoggerFactory.getLogger(RunProcessFunction.class);

  public Process run(List<String> args) throws IOException {

    Preconditions.checkNotNull(args, "Arguments must not be null");
    Preconditions.checkArgument(!args.isEmpty(), "No arguments specified");

    if (LOG.isInfoEnabled()) {
      LOG.info("{}", Joiner.on(" ").join(args));
    }

    ProcessBuilder builder = new ProcessBuilder(args);
    builder.redirectErrorStream(true);
    return builder.start();
  }
}
