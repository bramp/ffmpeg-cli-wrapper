package net.bramp.ffmpeg.options;

/**
 * 
 * @author bramp
 *
 */
public class EncodingOptions {

    // TODO Batch these into a new "MainOptions" class
	public final String format;
    public final Long startOffset;

    public final AudioEncodingOptions audio;
	public final VideoEncodingOptions video;

	public EncodingOptions(String format, Long startOffset, AudioEncodingOptions audio, VideoEncodingOptions video) {
		this.format = format;
        this.startOffset = startOffset;
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
