package net.bramp.ffmpeg.probe;

import java.util.Map;

public class FFmpegFormat {
	public String filename;
	public int nb_streams;
	public String format_name;
	public String format_long_name;
	public double start_time;

	/**
	 * Duration in seconds
	 */
	public double duration;
	
	/**
	 * File size in bytes
	 */
	public long size;
	
	/**
	 * Bitrate
	 */
	public long bit_rate;

	public Map<String, String> tags;
	
	@Override
	public String toString() {
	    StringBuilder stb = new StringBuilder();
	    stb.append("[filename: " + filename +  "], ");
	    stb.append("[nb_streams: " + filename +  "], ");
	    stb.append("[format_name: " + filename +  "], ");
	    stb.append("[format_long_name: " + filename +  "], ");
	    stb.append("[start_time: " + filename +  "], ");
	    stb.append("[duration: " + filename +  "], ");
	    stb.append("[size (bytes): " + filename +  "], ");
	    stb.append("[bit_rate: " + filename +  "], ");
	    if (null != tags) {
	        stb.append(tags.toString());
	    }
	    
	    return stb.toString();
	}
}
