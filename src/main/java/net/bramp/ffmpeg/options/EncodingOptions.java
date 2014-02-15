package net.bramp.ffmpeg.options;

/**
 * 
 * @author bramp
 *
 */
public class EncodingOptions {

	public final String format;

	public final AudioEncodingOptions audio;
	public final VideoEncodingOptions video;
	
	public EncodingOptions(String format, AudioEncodingOptions audio, VideoEncodingOptions video) {
		this.format = format;
		this.audio  = audio;
		this.video  = video;
	}

	public String getFormat() {
		return format;
	}

	public AudioEncodingOptions getAudio() {
		return audio;
	}

	public VideoEncodingOptions getVideo() {
		return video;
	}
}
