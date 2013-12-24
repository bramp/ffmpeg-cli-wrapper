package net.bramp.ffmpeg.builder;

import java.util.List;

import net.bramp.ffmpeg.info.FFmpegProbeResult;

import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.math.Fraction;

import com.google.common.collect.ImmutableList;

public class FFmpegOutputBuilder implements Cloneable {

	final static String DEVNULL = SystemUtils.IS_OS_WINDOWS ? "NUL" : "/dev/null";
	
	final FFmpegBuilder parent;

	private String filename;
	private String format;

	private long targetSize = 0; // in bytes

	private boolean video_enabled   = true;
	private boolean audio_enabled   = true;
	private boolean subtitle_enabled = true;

	private String audio_codec;
	private int audio_channels;
	private int audio_sample_rate;
	private int audio_bit_rate = 0;

	private String video_codec;
	private Fraction video_frame_rate;
	private int video_width;
	private int video_height;
	private int video_bit_rate = 0;

	protected FFmpegOutputBuilder(FFmpegBuilder parent, String filename) {
		this.parent = parent;
		this.filename = filename;
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
		this.filename = filename;
		return this;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public FFmpegOutputBuilder setFormat(String format) {
		this.format = format;
		return this;
	}

	public FFmpegOutputBuilder setVideoCodec(String codec) {
		this.video_enabled = true;
		this.video_codec = codec;
		return this;
	}
	
	public FFmpegOutputBuilder setVideoFramerate(Fraction frame_rate) {
		this.video_enabled = true;
		this.video_frame_rate = frame_rate;
		return this;
	}
	
	public FFmpegOutputBuilder setVideoFramerate(double frame_rate) {
		return setVideoFramerate(Fraction.getFraction(frame_rate));
	}

	public FFmpegOutputBuilder setVideoResolution(int width, int height) {
		this.video_enabled = true;
		this.video_width = width;
		this.video_height = height;
		return this;
	}

	public FFmpegOutputBuilder setAudio(String audio_codec, int audio_channels, int audio_sample_rate) {
		this.audio_enabled   = true;
		this.audio_codec    = audio_codec;
		this.audio_channels = audio_channels;
		this.audio_sample_rate = audio_sample_rate;
		return this;
	}
	
	/**
	 * Target output file size (in bytes)
	 * @param targetSize
	 * @return
	 */
	public FFmpegOutputBuilder setTargetSize(long targetSize) {
		this.targetSize = targetSize;
		return this;
	}
	
	public FFmpegOutputBuilder setVideoBitrate(int bit_rate) {
		this.video_bit_rate = bit_rate;
		return this;
	}

	public FFmpegBuilder done() {
		return parent;
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