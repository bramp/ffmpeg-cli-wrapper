package net.bramp.ffmpeg.options;

import java.beans.ConstructorProperties;

/**
 * 
 * @author bramp
 *
 */
public class EncodingOptions {

	public final MainEncodingOptions main;
    public final AudioEncodingOptions audio;
	public final VideoEncodingOptions video;

	@ConstructorProperties({"main", "audio", "video"})
	public EncodingOptions(MainEncodingOptions main, AudioEncodingOptions audio, VideoEncodingOptions video) {
		this.main = main;
		this.audio  = audio;
		this.video  = video;
	}

	public MainEncodingOptions getMain() {
		return main;
	}

	public AudioEncodingOptions getAudio() {
		return audio;
	}

	public VideoEncodingOptions getVideo() {
		return video;
	}
}
