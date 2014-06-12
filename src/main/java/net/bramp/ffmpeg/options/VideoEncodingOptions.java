package net.bramp.ffmpeg.options;

import org.apache.commons.lang3.math.Fraction;

/**
 * Encoding options for video
 * 
 * @author bramp
 *
 */
public class VideoEncodingOptions {
	public final boolean enabled;
	public final String codec;
	public final Fraction frame_rate;
	public final int width;
	public final int height;
	public final int bit_rate;
    public final Integer frames;
    public final String filter;

	public VideoEncodingOptions(boolean enabled, String codec, Fraction frame_rate, int width, int height, int bit_rate, Integer frames, String filter) {
		this.enabled    = enabled;
		this.codec      = codec;
		this.frame_rate = frame_rate;
		this.width      = width;
		this.height     = height;
		this.bit_rate   = bit_rate;
        this.frames     = frames;
        this.filter 	= filter;
	}
}