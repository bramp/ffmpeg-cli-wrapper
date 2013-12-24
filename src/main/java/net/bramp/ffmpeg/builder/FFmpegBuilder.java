package net.bramp.ffmpeg.builder;

import java.util.ArrayList;
import java.util.List;

import net.bramp.ffmpeg.info.FFmpegProbeResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

/**
 * Builds a ffmpeg command line
 * 
 * @author bramp
 *
 */
public class FFmpegBuilder {

	final static Logger LOG = LoggerFactory.getLogger(FFmpegBuilder.class);

	// Global Settings
	boolean override = true;
	int pass = 0;

	// Input and Output settings
	String input;
	FFmpegProbeResult inputProbe;
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

	public FFmpegBuilder setInput(String filename) {
		this.input = filename;
		return this;
	}

	public FFmpegBuilder setInput(FFmpegProbeResult result) {
		Preconditions.checkNotNull(result);
		Preconditions.checkNotNull(result.format);

		this.input = result.format.filename;
		this.inputProbe = result;
		return this;
	}
	
	/**
	 * Create new output file
	 * @param filename
	 * @return
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
		Preconditions.checkArgument(!outputs.isEmpty(), "Atleast one output must be specified");

		args.add(override ? "-y" : "-n");
		args.add("-v", "error"); // TODO make configurable

		args.add("-i").add(input);

		if (pass > 0) {
			args.add("-pass").add(Integer.toString(pass));
		}

		for (FFmpegOutputBuilder output : this.outputs) {
			args.addAll( output.build(pass) );
		}

		return args.build();
	}
}
