package net.bramp.ffmpeg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.io.Charsets;
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
public class RunProcessFunction implements ProcessFunction {

	final static Logger LOG = LoggerFactory.getLogger(RunProcessFunction.class);

	public BufferedReader run(List<String> args) throws IOException {

		Preconditions.checkNotNull(args, "Arguments must not be null");
		Preconditions.checkArgument(!args.isEmpty(), "No arguments specified");

		if (LOG.isInfoEnabled()) {
			LOG.info("{}", Joiner.on(" ").join(args));
		}

        ProcessBuilder builder = new ProcessBuilder(args);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        return new BufferedReader( new InputStreamReader(p.getInputStream(), Charsets.UTF_8) );
	}

}
