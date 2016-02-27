package net.bramp.ffmpeg.builder;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegUtils;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Builds a ffmpeg command line
 * 
 * @author bramp
 *
 */
public class FFmpegBuilder {

	final static Logger LOG = LoggerFactory.getLogger(FFmpegBuilder.class);

    public enum Strict {
        VERY,        // strictly conform to a older more strict version of the spec or reference software
        STRICT,      // strictly conform to all the things in the spec no matter what consequences
        NORMAL,
        UNOFFICAL,   // allow unofficial extensions
        EXPERIMENTAL;

        //ffmpeg command line requires these options in the lower case
        public String toString() {
            return name().toLowerCase();
        }
    }

	// Global Settings
	boolean override = true;
	int pass = 0;
	String pass_prefix;

	// Input settings
	Long startOffset; // in millis
	String input;
	FFmpegProbeResult inputProbe;

	// Output
	List<FFmpegOutputBuilder> outputs = new ArrayList<FFmpegOutputBuilder>();

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
		this.pass_prefix = prefix;
		return this;
	}

	public FFmpegBuilder setInput(String filename) {
		this.input = filename;
		return this;
	}

	public FFmpegBuilder setInput(FFmpegProbeResult result) {
		this.inputProbe = checkNotNull(result);
		this.input = checkNotNull(result.format).filename;

		return this;
	}

    public FFmpegBuilder setStartOffset(long duration, TimeUnit units) {
        checkNotNull(duration);
        checkNotNull(units);

        this.startOffset = units.toMillis(duration);

        return this;
    }
	
	/**
	 * Create new output file
	 * @param filename
	 * @return A new FFmpegOutputBuilder
	 */
	public FFmpegOutputBuilder addOutput(String filename) {
		FFmpegOutputBuilder output = new FFmpegOutputBuilder(this, filename);
		outputs.add(output);
		return output;
	}

	/**
	 * Create new output (to stdout)
	 */
	public FFmpegOutputBuilder addStreamedOutput() {
		return addOutput("-");
	}

	public List<String> build() {
		ImmutableList.Builder<String> args = new ImmutableList.Builder<String>();

		Preconditions.checkArgument(input != null, "Input must be specified");
		Preconditions.checkArgument(!outputs.isEmpty(), "At least one output must be specified");

		args.add(override ? "-y" : "-n");
		args.add("-v", "error"); // TODO make configurable

        if (startOffset != null) {
            args.add("-ss").add(FFmpegUtils.millisecondsToString(startOffset));
        }

        args.add("-i").add(input);

		if (pass > 0) {
			args.add("-pass").add(Integer.toString(pass));

			if (pass_prefix != null) {
				args.add("-passlogfile").add(pass_prefix);
			}
		}

		for (FFmpegOutputBuilder output : this.outputs) {
			args.addAll( output.build(pass) );
		}

		return args.build();
	}
}
