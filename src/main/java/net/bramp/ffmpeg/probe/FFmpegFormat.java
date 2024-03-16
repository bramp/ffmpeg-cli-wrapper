package net.bramp.ffmpeg.probe;

import com.google.common.collect.ImmutableMap;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Map;

@SuppressFBWarnings(
    value = {"UUF_UNUSED_PUBLIC_OR_PROTECTED_FIELD", "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"},
    justification = "POJO objects where the fields are populated by gson")
public class FFmpegFormat {
  /** @deprecated Use {@link #getFilename()} instead */
  @Deprecated
  public String filename;
  /** @deprecated Use {@link #getNbStreams()} instead */
  @Deprecated
  public int nb_streams;
  /** @deprecated Use {@link #getNbPrograms()} instead */
  @Deprecated
  public int nb_programs;

  /** @deprecated Use {@link #getFormatName()} instead */
  @Deprecated
  public String format_name;
  /** @deprecated Use {@link #getFormatLongName()} instead */
  @Deprecated
  public String format_long_name;
  /** @deprecated Use {@link #getStartTime()} instead */
  @Deprecated
  public double start_time;

  // TODO Change this to java.time.Duration
  /**
   * Duration in seconds
   * @deprecated Use {@link #getDuration()} instead
   */
  @Deprecated
  public double duration;

  /**
   * File size in bytes
   * @deprecated Use {@link #getSize()} instead
   */
  @Deprecated
  public long size;

  /**
   * Bitrate
   * @deprecated Use {@link #getBitRate()} instead
   */
  @Deprecated
  public long bit_rate;

  /** @deprecated Use {@link #getProbeScore()} instead */
  @Deprecated
  public int probe_score;

  /** @deprecated Use {@link #getTags()} instead */
  @Deprecated
  public Map<String, String> tags;

  public String getFilename() {
    return filename;
  }

  public int getNbStreams() {
    return nb_streams;
  }

  public int getNbPrograms() {
    return nb_programs;
  }

  public String getFormatName() {
    return format_name;
  }

  public String getFormatLongName() {
    return format_long_name;
  }

  public double getStartTime() {
    return start_time;
  }

  public double getDuration() {
    return duration;
  }

  public long getSize() {
    return size;
  }

  public long getBitRate() {
    return bit_rate;
  }

  public int getProbeScore() {
    return probe_score;
  }

  public Map<String, String> getTags() {
    return ImmutableMap.copyOf(tags);
  }
}
