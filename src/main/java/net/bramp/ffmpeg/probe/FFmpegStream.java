package net.bramp.ffmpeg.probe;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Map;
import org.apache.commons.lang3.math.Fraction;

@SuppressFBWarnings(
    value = {"UUF_UNUSED_PUBLIC_OR_PROTECTED_FIELD"},
    justification = "POJO objects where the fields are populated by gson")
public class FFmpegStream {

  public enum CodecType {
    VIDEO,
    AUDIO,
    SUBTITLE,
    DATA,
    ATTACHMENT
  }

  public int index;
  public String codec_name;
  public String codec_long_name;
  public String profile;
  public CodecType codec_type;
  public Fraction codec_time_base;

  public String codec_tag_string;
  public String codec_tag;

  public int width, height;

  public int has_b_frames;

  public String sample_aspect_ratio; // TODO Change to a Ratio/Fraction object
  public String display_aspect_ratio;

  public String pix_fmt;
  public int level;
  public String chroma_location;
  public int refs;
  public String is_avc;
  public String nal_length_size;
  public Fraction r_frame_rate;
  public Fraction avg_frame_rate;
  public Fraction time_base;

  public long start_pts;
  public double start_time;

  public long duration_ts;
  public double duration;

  public long bit_rate;
  public long max_bit_rate;
  public int bits_per_raw_sample;
  public int bits_per_sample;

  public long nb_frames;

  public String sample_fmt;
  public int sample_rate;
  public int channels;
  public String channel_layout;

  public FFmpegDisposition disposition;

  public Map<String, String> tags;
}
