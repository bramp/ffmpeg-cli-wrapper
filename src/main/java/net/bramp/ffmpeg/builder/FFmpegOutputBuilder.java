package net.bramp.ffmpeg.builder;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import net.bramp.ffmpeg.modelmapper.Mapper;
import net.bramp.ffmpeg.options.AudioEncodingOptions;
import net.bramp.ffmpeg.options.EncodingOptions;
import net.bramp.ffmpeg.options.MainEncodingOptions;
import net.bramp.ffmpeg.options.VideoEncodingOptions;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.math.Fraction;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Builds a representation of a single output/encoding setting
 */
public class FFmpegOutputBuilder implements Cloneable {

	final private static String DEVNULL = SystemUtils.IS_OS_WINDOWS ? "NUL" : "/dev/null";

	FFmpegBuilder parent;

	/**
	 * Output filename
	 */
	public String filename;

	public String format;

	public Long startOffset; // in millis
    public Long duration; // in millis

	public boolean audio_enabled = true;
	public String audio_codec;
	public int audio_channels;
	public int audio_sample_rate;
	public String audio_bit_depth;
	public int audio_bit_rate;
	public int audio_quality;

	public boolean video_enabled = true;
	public String video_codec;
	public Fraction video_frame_rate;
	public int video_width;
	public int video_height;
	public int video_bit_rate;
    public Integer video_frames;
	public String video_preset;
	public String video_filter;

	public boolean subtitle_enabled = true;

    public FFmpegBuilder.Strict strict = FFmpegBuilder.Strict.NORMAL;

	public long targetSize = 0; // in bytes
	public int pass_padding_bitrate = 1024; // in bits per second

	public boolean throwWarnings = true;

	public FFmpegOutputBuilder() {}

    protected FFmpegOutputBuilder(FFmpegBuilder parent, String filename) {
		this.parent = parent;
		this.filename = filename;
	}

    public FFmpegOutputBuilder useOptions(EncodingOptions opts) {
	    Mapper.map(opts, this);
        return this;
    }

	public FFmpegOutputBuilder useOptions(MainEncodingOptions opts) {
		Mapper.map(opts, this);
		return this;
	}

    public FFmpegOutputBuilder useOptions(AudioEncodingOptions opts) {
	    Mapper.map(opts, this);
        return this;
    }

    public FFmpegOutputBuilder useOptions(VideoEncodingOptions opts) {
	    Mapper.map(opts, this);
        return this;
    }

	public FFmpegOutputBuilder disableVideo() {
		this.video_enabled = false;
		return this;
	}

	public FFmpegOutputBuilder disableAudio() {
		this.audio_enabled = false;
		return this;
	}

	public FFmpegOutputBuilder disableSubtitle() {
		this.subtitle_enabled = false;
		return this;
	}
	
	public FFmpegOutputBuilder setFilename(String filename) {
		this.filename = checkNotNull(filename);
		return this;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public FFmpegOutputBuilder setFormat(String format) {
		this.format = checkNotNull(format);
		return this;
	}

	public FFmpegOutputBuilder setVideoBitRate(int bit_rate) {
		Preconditions.checkArgument(bit_rate > 0);
		this.video_enabled  = true;
		this.video_bit_rate = bit_rate;
		return this;
	}

	public FFmpegOutputBuilder setVideoCodec(String codec) {
		this.video_enabled = true;
		this.video_codec = checkNotNull(codec);
		return this;
	}
	
	public FFmpegOutputBuilder setVideoFrameRate(Fraction frame_rate) {
		this.video_enabled = true;
		this.video_frame_rate = checkNotNull(frame_rate);
		return this;
	}

	public FFmpegOutputBuilder setVideoFrameRate(double frame_rate) {
		return setVideoFrameRate(Fraction.getFraction(frame_rate));
	}

    /**
     * Set the number of video frames to record.
     * 
     * @param frames
     * @return this
     */
    public FFmpegOutputBuilder setFrames(int frames) {
        this.video_enabled = true;
        this.video_frames = frames;
        return this;
    }

	public FFmpegOutputBuilder setVideoPreset(String preset) {
		this.video_enabled = true;
		this.video_preset = checkNotNull(preset);
		return this;
	}

	protected static boolean isValidSize(int widthOrHeight) {
		return widthOrHeight > 0 || widthOrHeight == -1;
	}

	public FFmpegOutputBuilder setVideoWidth(int width) {
		Preconditions.checkArgument(isValidSize(width), "Width must be valid greater than 0");

		this.video_enabled = true;
		this.video_width = width;
		return this;
	}

	public FFmpegOutputBuilder setVideoHeight(int height) {
		Preconditions.checkArgument(isValidSize(height), "Height must be valid greater than 0");

		this.video_enabled = true;
		this.video_height = height;
		return this;
	}

	public FFmpegOutputBuilder setVideoResolution(int width, int height) {
		Preconditions.checkArgument(isValidSize(width) && isValidSize(height),
				"Both width and height must be valid resolutions");

		this.video_enabled = true;
		this.video_width = width;
		this.video_height = height;
		return this;
	}

	/**
	 * Sets Video Filter
	 * TODO Build a fluent Filter builder
	 * @param filter
	 * @return this
	 */
	public FFmpegOutputBuilder setVideoFilter(String filter) {
		this.video_enabled = true;
		this.video_filter  = checkNotNull(filter);
		return this;
	}


	public FFmpegOutputBuilder setAudioCodec(String codec) {
		this.audio_enabled  = true;
		this.audio_codec    = checkNotNull(codec);
		return this;
	}

	public FFmpegOutputBuilder setAudioChannels(int channels) {
		Preconditions.checkArgument(channels > 0);
		this.audio_enabled   = true;
		this.audio_channels  = channels;
		return this;
	}

	/**
	 * Sets the Audio Sample Rate, for example 440000
	 * @param sample_rate
	 * @return this
	 */
	public FFmpegOutputBuilder setAudioSampleRate(int sample_rate) {
		Preconditions.checkArgument(sample_rate > 0);
		this.audio_enabled     = true;
		this.audio_sample_rate = sample_rate;
		return this;
	}

	/**
	 * Sets the Audio Bit Depth. Samples given in the FFmpeg.AUDIO_DEPTH_* constants.
	 * @param bit_depth
	 * @return this
	 */
	public FFmpegOutputBuilder setAudioBitDepth(String bit_depth) {
		Preconditions.checkNotNull(bit_depth);
		this.audio_enabled   = true;
		this.audio_bit_depth = bit_depth;
		return this;
	}


	/**
	 * Sets the Audio bitrate
	 *
	 * @param bit_rate
	 * @return this
	 */
	public FFmpegOutputBuilder setAudioBitRate(int bit_rate) {
		Preconditions.checkArgument(bit_rate > 0);
		this.audio_enabled  = true;
		this.audio_bit_rate = bit_rate;
		return this;
	}

	public FFmpegOutputBuilder setAudioQuality(int quality) {
		Preconditions.checkArgument(quality >= 1 && quality <= 5);
		this.audio_enabled = true;
		this.audio_quality = quality;
		return this;
	}

	/**
	 * Target output file size (in bytes)
	 * @param targetSize
	 * @return this
	 */
	public FFmpegOutputBuilder setTargetSize(long targetSize) {
		Preconditions.checkArgument(targetSize > 0);
		this.targetSize = targetSize;
		return this;
	}

    /**
     * Decodes but discards input until the duration
     * @param duration
     * @param units
     * @return this
     */
    public FFmpegOutputBuilder setStartOffset(long duration, TimeUnit units) {
        checkNotNull(duration);
        checkNotNull(units);

        this.startOffset = units.toMillis(duration);

        return this;
    }

	/**
	 * Stop writing the output after its duration reaches duration
	 * @param duration
	 * @param units
	 * @return this
	 */
	public FFmpegOutputBuilder setDuration(long duration, TimeUnit units) {
		checkNotNull(duration);
		checkNotNull(units);

		this.duration = units.toMillis(duration);

		return this;
	}

    public FFmpegOutputBuilder setStrict(FFmpegBuilder.Strict strict) {
        this.strict = checkNotNull(strict);
        return this;
    }

	/**
	 * When doing multi-pass we add a little extra padding, to ensure we reach our target
	 * @param bitrate
	 * @return this
	 */
	public FFmpegOutputBuilder setPassPaddingBitrate(int bitrate) {
		Preconditions.checkArgument(bitrate > 0);
		this.pass_padding_bitrate = bitrate;
		return this;
	}

	/**
	 * Finished with this output
	 * @return the parent FFmpegBuilder
	 */
	public FFmpegBuilder done() {
        Preconditions.checkState(parent != null, "Can not call done without parent being set");
		return parent;
	}

	public EncodingOptions buildOptions() {
		// TODO When/if modelmapper supports @ConstructorProperties, we map this object, instead of doing new XXX(...)
		//     https://github.com/jhalterman/modelmapper/issues/44
		return new EncodingOptions(
			new MainEncodingOptions(format, startOffset, duration),
			new AudioEncodingOptions(audio_enabled, audio_codec, audio_channels, audio_sample_rate, audio_bit_depth, audio_bit_rate, audio_quality),
			new VideoEncodingOptions(video_enabled, video_codec, video_frame_rate, video_width, video_height, video_bit_rate, video_frames, video_filter, video_preset)
		);
	}

	protected List<String> build(int pass) {
        Preconditions.checkState(parent != null, "Can not build without parent being set");

		ImmutableList.Builder<String> args = new ImmutableList.Builder<String>();

		if (targetSize > 0) {
			FFmpegProbeResult input = parent.inputProbe;
			if (input == null) {
				throw new IllegalStateException("Can not set target size, without using setInput(FFmpegProbeResult)");
			}

            // TODO factor in start time and/or number of frames

			double durationInSeconds = input.format.duration;
			int totalBitRate = (int) Math.floor((targetSize * 8) / durationInSeconds) - pass_padding_bitrate;

			// TODO Calculate audioBitRate

			if (video_enabled && video_bit_rate == 0) {
				// Video (and possibly audio)
				int audioBitRate = audio_enabled ? audio_bit_rate : 0;
				video_bit_rate = totalBitRate - audioBitRate;
			} else if (audio_enabled && audio_bit_rate == 0) {
				// Just Audio
				audio_bit_rate = totalBitRate;
			}
		}

        if (strict != FFmpegBuilder.Strict.NORMAL) {
            args.add("-strict").add(strict.toString().toLowerCase());
        }

		if (!Strings.isNullOrEmpty(format)) {
			args.add("-f").add(format);
		}

        if (startOffset != null) {
            // TODO Consider formatting into "hh:mm:ss[.xxx]"
            args.add("-ss").add(String.format("%.3f", startOffset / 1000f));
        }

		if (duration != null) {
			// TODO Consider formatting into "hh:mm:ss[.xxx]"
			args.add("-t").add(String.format("%.3f", duration / 1000f));
		}

		if (video_enabled) {

			if (!Strings.isNullOrEmpty(video_codec)) {
				args.add("-vcodec").add(video_codec);
			}

			if (video_width != 0 && video_height != 0) {
				args.add("-s").add(String.format("%dx%d", video_width, video_height));
			}

			if (video_frame_rate != null) {
				//args.add("-r").add(String.format("%2f", video_frame_rate));
				args.add("-r").add(video_frame_rate.toString());
			}

			if (video_bit_rate > 0) {
				args.add("-b:v").add(String.format("%d", video_bit_rate));
			}

			if (!Strings.isNullOrEmpty(video_preset)) {
				args.add("-vpre").add(video_preset);
			}

			if (!Strings.isNullOrEmpty(video_filter)) {
				args.add("-vf").add(video_filter);
			}

		} else {
			args.add("-vn");
		}

		if (audio_enabled && pass != 1) {
			if(!Strings.isNullOrEmpty(audio_codec)) {
				args.add("-acodec").add(audio_codec);
			}

			if(audio_channels > 0) {
				args.add("-ac").add(String.format("%d", audio_channels));
			}

			if (audio_sample_rate > 0) {
				args.add("-ar").add(String.format("%d", audio_sample_rate));
			}

			if (!Strings.isNullOrEmpty(audio_bit_depth)) {
				args.add("-sample_fmt").add(audio_bit_depth);
			}

			if (audio_bit_rate > 0 && audio_quality > 0 && throwWarnings) {
				// I'm not sure, but it seems audio_quality overrides audio_bit_rate, so don't allow both
				throw new IllegalStateException("Only one of audio_bit_rate and audio_quality can be set");
			}

			if (audio_bit_rate > 0) {
				args.add("-b:a").add(String.format("%d", audio_bit_rate));
			}

			if (audio_quality > 0) {
				args.add("-aq").add(String.format("%d", audio_quality));
			}

		} else {
			args.add("-an");
		}

		if (!subtitle_enabled)
			args.add("-sn");

		// Output
		if (pass == 1) {
			args.add(DEVNULL);
		} else {
			args.add(filename);
		}

		return args.build();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}