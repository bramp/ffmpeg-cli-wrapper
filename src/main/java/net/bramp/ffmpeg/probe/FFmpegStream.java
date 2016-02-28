package net.bramp.ffmpeg.probe;

import org.apache.commons.lang3.math.Fraction;

import java.util.Map;

public class FFmpegStream {

  public enum CodecType {
    VIDEO, AUDIO,
  };

  public int index;
  public String codec_name;
  public String codec_long_name;
  public CodecType codec_type;
  public Fraction codec_time_base;

  public String codec_tag_string;
  public String codec_tag;

  public int width, height;

  public int has_b_frames;

  public String sample_aspect_ratio;
  public String display_aspect_ratio;

  public String pix_fmt;
  public int level;
  public String is_avc;
  public String nal_length_size;
  public Fraction r_frame_rate;
  public Fraction avg_frame_rate;
  public Fraction time_base;
  public double duration;
  public int nb_frames;

  public Map<String, String> tags;
}
