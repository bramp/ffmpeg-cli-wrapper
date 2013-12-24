package net.bramp.ffmpeg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;

/**
 * Simple function that creates a Process with the arguments, and returns
 * a BufferedReader reading stdout 
 * 
 * @author bramp
 *
 */
public class RunProcessFunction implements Function<List<String>, BufferedReader> {

	final static Logger LOG = LoggerFactory.getLogger(RunProcessFunction.class);

	public @Nullable BufferedReader apply(@Nullable List<String> args) {

		Preconditions.checkNotNull(args);
		Preconditions.checkArgument(!args.isEmpty());

		if (LOG.isDebugEnabled()) {
			LOG.debug("{}", Joiner.on(" ").join(args));
		}

		try {
			ProcessBuilder builder = new ProcessBuilder(args);
			builder.redirectErrorStream(true);
			Process p = builder.start();
			return new BufferedReader( new InputStreamReader(p.getInputStream()) );

		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}

}
