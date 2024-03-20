package net.bramp.ffmpeg.probe;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.List;
import java.util.Map;

import net.bramp.ffmpeg.shared.CodecType;
import org.apache.commons.lang3.math.Fraction;

@SuppressFBWarnings(
    value = {"UUF_UNUSED_PUBLIC_OR_PROTECTED_FIELD"},
    justification = "POJO objects where the fields are populated by gson")
public class FFmpegStream {
  public int index;
  public String codec_name;
  public String codec_long_name;
  public String profile;
  public CodecType codec_type;
  public Fraction codec_time_base;

  public String codec_tag_string;
  public String codec_tag;

  public int width;
  public int height;

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

  // TODO: Make Map immutable
  public Map<String, String> tags;

  // TODO: Convert array to immutable List
  public SideData[] side_data_list;

  public int getIndex() {
    return index;
  }

  public String getCodecName() {
    return codec_name;
  }

  public String getCodecLongName() {
    return codec_long_name;
  }

  public String getProfile() {
    return profile;
  }

  public CodecType getCodecType() {
    return codec_type;
  }

  public Fraction getCodecTimeBase() {
    return codec_time_base;
  }

  public String getCodecTagString() {
    return codec_tag_string;
  }

  public String getCodecTag() {
    return codec_tag;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public int getHasBFrames() {
    return has_b_frames;
  }

  public String getSampleAspectRatio() {
    return sample_aspect_ratio;
  }

  public String getDisplayAspectRatio() {
    return display_aspect_ratio;
  }

  public String getPixFmt() {
    return pix_fmt;
  }

  public int getLevel() {
    return level;
  }

  public String getChromaLocation() {
    return chroma_location;
  }

  public int getRefs() {
    return refs;
  }

  public String getIsAvc() {
    return is_avc;
  }

  public String getNalLengthSize() {
    return nal_length_size;
  }

  public Fraction getRFrameRate() {
    return r_frame_rate;
  }

  public Fraction getAvgFrameRate() {
    return avg_frame_rate;
  }

  public Fraction getTimeBase() {
    return time_base;
  }

  public long getStartPts() {
    return start_pts;
  }

  public double getStartTime() {
    return start_time;
  }

  public long getDurationTs() {
    return duration_ts;
  }

  public double getDuration() {
    return duration;
  }

  public long getBitRate() {
    return bit_rate;
  }

  public long getMaxBitRate() {
    return max_bit_rate;
  }

  public int getBitsPerRawSample() {
    return bits_per_raw_sample;
  }

  public int getBitsPerSample() {
    return bits_per_sample;
  }

  public long getNbFrames() {
    return nb_frames;
  }

  public String getSampleFmt() {
    return sample_fmt;
  }

  public int getSampleRate() {
    return sample_rate;
  }

  public int getChannels() {
    return channels;
  }

  public String getChannelLayout() {
    return channel_layout;
  }

  public FFmpegDisposition getDisposition() {
    return disposition;
  }

  public Map<String, String> getTags() {
    return ImmutableMap.copyOf(tags);
  }

  public List<SideData> getSideDataList() {
    return ImmutableList.copyOf(side_data_list);
  }

  public static class SideData {
    public String side_data_type;
    public String displaymatrix;
    public int rotation;

    public String getSideDataType() {
      return side_data_type;
    }

    public String getDisplaymatrix() {
      return displaymatrix;
    }

    public int getRotation() {
      return rotation;
    }
  }
}
