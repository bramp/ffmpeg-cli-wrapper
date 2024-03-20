package net.bramp.ffmpeg.probe;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings(
    value = {"UUF_UNUSED_PUBLIC_OR_PROTECTED_FIELD"},
    justification = "POJO objects where the fields are populated by gson")
public class FFmpegChapter {
  public long id;
  public String time_base;
  public long start;
  public String start_time;
  public long end;
  public String end_time;
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
