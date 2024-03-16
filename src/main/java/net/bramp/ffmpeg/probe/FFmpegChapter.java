package net.bramp.ffmpeg.probe;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings(
    value = {"UUF_UNUSED_PUBLIC_OR_PROTECTED_FIELD"},
    justification = "POJO objects where the fields are populated by gson")
public class FFmpegChapter {
  /** @deprecated Use {@link #getId()} instead */
  @Deprecated
  public long id;
  /** @deprecated Use {@link #getTimeBase()} instead */
  @Deprecated
  public String time_base;
  /** @deprecated Use {@link #getStart()} instead */
  @Deprecated
  public long start;
  /** @deprecated Use {@link #getStartTime()} instead */
  @Deprecated
  public String start_time;
  /** @deprecated Use {@link #getEnd()} instead */
  @Deprecated
  public long end;
  /** @deprecated Use {@link #getEndTime()} instead */
  @Deprecated
  public String end_time;
  /** @deprecated Use {@link #getTags()} instead */
  @Deprecated
  public FFmpegChapterTag tags;

  public long getId() {
    return id;
  }

  public String getTimeBase() {
    return time_base;
  }

  public long getStart() {
    return start;
  }

  public String getStartTime() {
    return start_time;
  }

  public long getEnd() {
    return end;
  }

  public String getEndTime() {
    return end_time;
  }

  public FFmpegChapterTag getTags() {
    return tags;
  }
}
