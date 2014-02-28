package net.bramp.ffmpeg.builder;

import java.util.List;

import net.bramp.ffmpeg.options.AudioEncodingOptions;
import net.bramp.ffmpeg.options.EncodingOptions;
import net.bramp.ffmpeg.options.VideoEncodingOptions;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.math.Fraction;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

/**
 * Builds a representation of a single output/encoding setting
 */
public class FFmpegOutputBuilder implements Cloneable {

	final private static String DEVNULL = SystemUtils.IS_OS_WINDOWS ? "NUL" : "/dev/null";

	final FFmpegBuilder parent;

	/**
	 * Output filename
	 */
	public String filename;

	public String format;

	public boolean audio_enabled = true;
	public String audio_codec;
	public int audio_channels;
	public int audio_sample_rate;
	public int audio_bit_rate;
	
	public boolean video_enabled = true;
	public String video_codec;
	public Fraction video_frame_rate;
	public int video_width;
	public int video_height;
	public int video_bit_rate;

	public boolean subtitle_enabled = true;

	public long targetSize = 0; // in bytes

	protected FFmpegOutputBuilder(FFmpegBuilder parent, String filename) {
		this.parent = parent;
		this.filename = filename;
	}

    public FFmpegOutputBuilder useOptions(EncodingOptions opts) {
        this.format = opts.format;
        useOptions(opts.getAudio());
        useOptions(opts.getVideo());
        return this;
    }

    public FFmpegOutputBuilder useOptions(AudioEncodingOptions opts) {
        audio_enabled     = opts.enabled;
        audio_codec       = opts.codec;
        audio_channels    = opts.channels;
        audio_sample_rate = opts.sample_rate;
        audio_bit_rate    = opts.bit_rate;
        return this;
    }

    public FFmpegOutputBuilder useOptions(VideoEncodingOptions opts) {
        video_enabled    = opts.enabled;
        video_codec      = opts.codec;
        video_frame_rate = opts.frame_rate;
        video_width      = opts.width;
        video_height     = opts.height;
        video_bit_rate   = opts.bit_rate;
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
		this.filename = Preconditions.checkNotNull(filename);
		return this;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public FFmpegOutputBuilder setFormat(String format) {
		this.format = Preconditions.checkNotNull(format);
		return this;
	}

	public FFmpegOutputBuilder setVideoCodec(String codec) {
		this.video_enabled = true;
		this.video_codec = Preconditions.checkNotNull(codec);
		return this;
	}
	
	public FFmpegOutputBuilder setVideoFramerate(Fraction frame_rate) {
		this.video_enabled = true;
		this.video_frame_rate = Preconditions.checkNotNull(frame_rate);
		return this;
	}
	
	public FFmpegOutputBuilder setVideoFramerate(double frame_rate) {
		return setVideoFramerate(Fraction.getFraction(frame_rate));
	}

	public FFmpegOutputBuilder setVideoResolution(int width, int height) {
		Preconditions.checkArgument(width > 0 && height > 0, "Both width and height must be greater than zero");

		this.video_enabled = true;
		this.video_width = width;
		this.video_height = height;
		return this;
	}

	public FFmpegOutputBuilder setAudioCodec(String codec) {
		this.audio_enabled  = true;
		this.audio_codec    = Preconditions.checkNotNull(codec);
		return this;
	}

	public FFmpegOutputBuilder setAudioChannels(int channels) {
		Preconditions.checkArgument(channels > 0);
		this.audio_enabled   = true;
		this.audio_channels  = channels;
		return this;
	}

	public FFmpegOutputBuilder setAudioRate(int sample_rate) {
		Preconditions.checkArgument(sample_rate > 0);
		this.audio_enabled   = true;
		this.audio_sample_rate = sample_rate;
		return this;
	}

	/**
	 * Target output file size (in bytes)
	 * @param targetSize
	 * @return
	 */
	public FFmpegOutputBuilder setTargetSize(long targetSize) {
		Preconditions.checkArgument(targetSize > 0);
		this.targetSize = targetSize;
		return this;
	}
	
	public FFmpegOutputBuilder setVideoBitrate(int bit_rate) {
		Preconditions.checkArgument(bit_rate > 0);
		this.video_bit_rate = bit_rate;
		return this;
	}

	public FFmpegBuilder done() {
		return parent;
	}

	protected EncodingOptions buildOptions() {
		return new EncodingOptions(
			format, 
			new AudioEncodingOptions(audio_enabled, audio_codec, audio_channels, audio_sample_rate, audio_bit_rate),
			new VideoEncodingOptions(video_enabled, video_codec, video_frame_rate, video_width, video_height, video_bit_rate)
		);
	}

	protected List<String> build(int pass) {
		ImmutableList.Builder<String> args = new ImmutableList.Builder<String>();

		if (targetSize > 0) {
			FFmpegProbeResult input = parent.inputProbe;
			if (input == null) {
				throw new IllegalStateException("Can not set target size, without using setInput(FFmpegProbeResult)");
			}

			double durationInSeconds = input.format.duration;
			int totalBitRate = (int) Math.floor((targetSize * 8) / durationInSeconds);

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

		if (format != null) {
			args.add("-f").add(format);
		}

		if (video_enabled) {

			if (video_codec != null) {
				args.add("-vcodec").add(video_codec);
			}

			if (video_width > 0 && video_height > 0) {
				args.add("-s").add(String.format("%dx%d", video_width, video_height));
			}

			if (video_frame_rate != null) {
				//args.add("-r").add(String.format("%2f", video_frame_rate));
				args.add("-r").add(video_frame_rate.toString());
			}

			if (video_bit_rate > 0) {
				args.add("-b:v").add(String.format("%d", video_bit_rate));
			}
		} else {
			args.add("-vn");
		}

		if (audio_enabled && pass != 1) {
			if(audio_codec != null) {
				args.add("-acodec").add(audio_codec);
			}

			if(audio_channels > 0) {
				args.add("-ac").add(String.format("%d", audio_channels));
			}

			if (audio_sample_rate > 0) {
				args.add("-ar").add(String.format("%d", audio_sample_rate));
			}
			
			if (audio_bit_rate > 0) {
				args.add("-b:a").add(String.format("%d", audio_bit_rate));
			}

		} else {
			args.add("-an");
		}

		if (!subtitle_enabled)
			args.add("-sn");

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