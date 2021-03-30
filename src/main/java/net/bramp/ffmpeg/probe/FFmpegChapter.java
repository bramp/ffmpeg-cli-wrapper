
package net.bramp.ffmpeg.probe;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings(
  value = {"UUF_UNUSED_PUBLIC_OR_PROTECTED_FIELD"},
  justification = "POJO objects where the fields are populated by gson"
)
public class FFmpegChapter {

  public int id;
  public String time_base;
  public long start;
  public String start_time;
  public long end;
  public String end_time;
  public FFmpegChapterTag tags;
}
