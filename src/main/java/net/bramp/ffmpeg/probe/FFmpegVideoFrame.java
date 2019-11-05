package net.bramp.ffmpeg.probe;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings(
  value = {"UUF_UNUSED_PUBLIC_OR_PROTECTED_FIELD", "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"},
  justification = "POJO objects where the fields are populated by gson"
)
public class FFmpegVideoFrame extends FFmpegFrame {
  public int width;
  public int height;
  public String pix_fmt;
  /** I, B or P. Question: Change this to enum? */
  public String pict_type;

  public int coded_picture_number;
  public int display_picture_number;
  public int interlaced_frame;
  public int top_field_first;
  public int repeat_pict;
  public String chroma_location;
}
