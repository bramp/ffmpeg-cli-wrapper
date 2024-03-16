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
  /** @deprecated Use {@link #getIndex()} instead */
  @Deprecated
  public int index;
  /** @deprecated Use {@link #getCodecName()} instead */
  @Deprecated
  public String codec_name;
  /** @deprecated Use {@link #getCodecLongName()} instead */
  @Deprecated
  public String codec_long_name;
  /** @deprecated Use {@link #getProfile()} instead */
  @Deprecated
  public String profile;
  /** @deprecated Use {@link #getCodecType()} instead */
  @Deprecated
  public CodecType codec_type;
  /** @deprecated Use {@link #getCodecTimeBase()} instead */
  @Deprecated
  public Fraction codec_time_base;

  /** @deprecated Use {@link #getCodecTagString()} instead */
  @Deprecated
  public String codec_tag_string;
  /** @deprecated Use {@link #getCodecTag()} instead */
  @Deprecated
  public String codec_tag;

  /** @deprecated Use {@link #getWidth()} instead */
  @Deprecated
  public int width;
  /** @deprecated Use {@link #getHeight()} instead */
  @Deprecated
  public int height;

  /** @deprecated Use {@link #getHasBFrames()} instead */
  @Deprecated
  public int has_b_frames;

  /** @deprecated Use {@link #getSampleAspectRatio()} instead */
  @Deprecated
  public String sample_aspect_ratio; // TODO Change to a Ratio/Fraction object
  /** @deprecated Use {@link #getDisplayAspectRatio()} instead */
  @Deprecated
  public String display_aspect_ratio;

  /** @deprecated Use {@link #getPixFmt()} instead */
  @Deprecated
  public String pix_fmt;
  /** @deprecated Use {@link #getLevel()} instead */
  @Deprecated
  public int level;
  /** @deprecated Use {@link #getChromaLocation()} instead */
  @Deprecated
  public String chroma_location;
  /** @deprecated Use {@link #getRefs()} instead */
  @Deprecated
  public int refs;
  /** @deprecated Use {@link #getIsAvc()} instead */
  @Deprecated
  public String is_avc;
  /** @deprecated Use {@link #getNalLengthSize()} instead */
  @Deprecated
  public String nal_length_size;
  /** @deprecated Use {@link #getRFrameRate()} instead */
  @Deprecated
  public Fraction r_frame_rate;
  /** @deprecated Use {@link #getAvgFrameRate()} instead */
  @Deprecated
  public Fraction avg_frame_rate;
  /** @deprecated Use {@link #getTimeBase()} instead */
  @Deprecated
  public Fraction time_base;

  /** @deprecated Use {@link #getStartPts()} instead */
  @Deprecated
  public long start_pts;
  /** @deprecated Use {@link #getStartTime()} instead */
  @Deprecated
  public double start_time;

  /** @deprecated Use {@link #getDurationTs()} instead */
  @Deprecated
  public long duration_ts;
  /** @deprecated Use {@link #getDuration()} instead */
  @Deprecated
  public double duration;

  /** @deprecated Use {@link #getBitRate()} instead */
  @Deprecated
  public long bit_rate;
  /** @deprecated Use {@link #getMaxBitRate()} instead */
  @Deprecated
  public long max_bit_rate;
  /** @deprecated Use {@link #getBitsPerRawSample()} instead */
  @Deprecated
  public int bits_per_raw_sample;
  /** @deprecated Use {@link #getBitsPerSample()} instead */
  @Deprecated
  public int bits_per_sample;

  /** @deprecated Use {@link #getNbFrames()} instead */
  @Deprecated
  public long nb_frames;

  /** @deprecated Use {@link #getSampleFmt()} instead */
  @Deprecated
  public String sample_fmt;
  /** @deprecated Use {@link #getSampleRate()} instead */
  @Deprecated
  public int sample_rate;
  /** @deprecated Use {@link #getChannels()} instead */
  @Deprecated
  public int channels;
  /** @deprecated Use {@link #getChannelLayout()} instead */
  @Deprecated
  public String channel_layout;

  /** @deprecated Use {@link #getDisposition()} instead */
  @Deprecated
  public FFmpegDisposition disposition;

  /** @deprecated Use {@link #getTags()} instead */
  @Deprecated
  public Map<String, String> tags;
  /** @deprecated Use {@link #getSideDataList()} instead */
  @Deprecated
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
    /** @deprecated Use {@link #getSideDataType()} instead */
    @Deprecated
    public String side_data_type;
    /** @deprecated Use {@link #getDisplaymatrix()} instead */
    @Deprecated
    public String displaymatrix;
    /** @deprecated Use {@link #getRotation()} instead */
    @Deprecated
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
