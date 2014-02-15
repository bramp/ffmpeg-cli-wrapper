package net.bramp.ffmpeg;

import java.io.BufferedReader;
import java.util.List;

import javax.annotation.Nonnull;

import net.bramp.commons.lang3.math.gson.FractionAdapter;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

import org.apache.commons.lang3.math.Fraction;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Wrapper around FFprobe
 * 
 * TODO ffprobe -v quiet -print_format json -show_format -show_streams mobileedge_1280x720.mp4
 * @author bramp
 *
 */
public class FFprobe {

	final Gson gson = setupGson();
	
	final String path;

	/**
	 * Function to run FFmpeg. We define it like this so we can swap it out (during testing)
	 */
	static Function<List<String>, BufferedReader> runFunc = new RunProcessFunction();

	public FFprobe() {
		this.path = "ffprobe";
	}

	public FFprobe(@Nonnull String path) {
		this.path = path;
	}

	private static Gson setupGson() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Fraction.class, new FractionAdapter());
		return builder.create();
	}

	public String getPath() {
		return path;
	}

	public FFmpegProbeResult probe(String mediaPath) {
		ImmutableList.Builder<String> args = new ImmutableList.Builder<String>();

		args.add(path)
			.add("-v", "quiet")
			.add("-print_format", "json")
			.add("-show_error")
			.add("-show_format")
			.add("-show_streams")

			//.add("--show_packets")
			//.add("--show_frames")

			.add(mediaPath);

		BufferedReader reader = runFunc.apply(args.build());
		return gson.fromJson(reader, FFmpegProbeResult.class);
	}
}
