package net.bramp.ffmpeg;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.info.Codec;
import net.bramp.ffmpeg.info.Format;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.math.Fraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;

/**
 * @author bramp
 *
 */
public class FFmpeg {

	final static Logger LOG = LoggerFactory.getLogger(FFmpeg.class);

	public final static Fraction FPS_30     = Fraction.getFraction(30, 1);
	public final static Fraction FPS_29_97  = Fraction.getFraction(30000, 1001);
	public final static Fraction FPS_24     = Fraction.getFraction(24, 1);
	public final static Fraction FPS_23_976 = Fraction.getFraction(24000, 1001);

    final static Pattern CODECS_REGEX = Pattern.compile("^ ([ D][ E][VAS][ S][ D][ T]) (\\S+)\\s+(.*)$");
    final static Pattern FORMATS_REGEX = Pattern.compile("^ ([ D][ E]) (\\S+)\\s+(.*)$");

    /**
     * Path to FFmpeg (e.g. /usr/bin/ffmpeg)
     */
	final String path;

	/**
	 * Function to run FFmpeg. We define it like this so we can swap it out (during testing)
	 */
	static ProcessFunction runFunc = new RunProcessFunction();

	/**
	 * Supported codecs
	 */
	List<Codec> codecs = null;

	/**
	 * Supported formats
	 */
	List<Format> formats = null;
	
	/**
	 * Version string
	 */
	String version = null;

	public FFmpeg() throws IOException {
		this.path = "ffmpeg";
		this.version = version();
	}

	public FFmpeg(@Nonnull String path) throws IOException {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(path));
		this.path = path;
		this.version = version();
	}

	public synchronized @Nonnull String version() throws IOException {
        BufferedReader r = runFunc.run(ImmutableList.of(path, "-version"));
        return r.readLine();
	}

	public synchronized @Nonnull List<Codec> codecs() throws IOException {
		if (codecs == null) {
			codecs = new ArrayList<Codec>();

			String line;

			BufferedReader r = runFunc.run(ImmutableList.of(path, "-codecs"));
			while ((line = r.readLine()) != null) {
				Matcher m = CODECS_REGEX.matcher(line);
				if (!m.matches())
					continue;

				codecs.add( new Codec(m.group(2), m.group(3), m.group(1)) );
			}

			codecs = ImmutableList.copyOf(codecs);
		}

		return codecs;
	}

	public synchronized @Nonnull List<Format> formats() throws IOException {
		if (formats == null) {
			formats = new ArrayList<Format>();

			String line;

			BufferedReader r = runFunc.run(ImmutableList.of(path, "-formats"));
			while ((line = r.readLine()) != null) {
				Matcher m = FORMATS_REGEX.matcher(line);
				if (!m.matches())
					continue;

				formats.add( new Format(m.group(2), m.group(3), m.group(1)) );
			}

			formats = ImmutableList.copyOf(formats);
		}

		return formats;
	}

	public void run(List<String> args) throws IOException {
		List<String> newArgs = new ArrayList<String>(1 + args.size());
		newArgs.add(path);
		newArgs.addAll(args);

		BufferedReader reader = runFunc.run(newArgs);

		// Now block reading ffmpeg's stdout
		IOUtils.copy(reader, System.out);
	}

	public FFmpegBuilder buider() {
		return new FFmpegBuilder();
	}

	public String getPath() {
		return path;
	}
}
