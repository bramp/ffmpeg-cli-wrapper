package net.bramp.ffmpeg.options;

/**
 * Encoding options for audio
 * 
 * @author bramp
 *
 */
public class AudioEncodingOptions {

	public final boolean enabled;
	public final String codec;
	public final int channels;
	public final int sample_rate;
	public final int bit_rate;

	public AudioEncodingOptions(boolean enabled, String codec, int channels, int sample_rate, int bit_rate) {
		this.enabled  = enabled;
		this.codec    = codec;
		this.channels = channels;
		this.sample_rate = sample_rate;
		this.bit_rate = bit_rate;
	}

}