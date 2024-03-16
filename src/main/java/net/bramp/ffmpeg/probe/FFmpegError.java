package net.bramp.ffmpeg.probe;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings(
    value = {"UUF_UNUSED_PUBLIC_OR_PROTECTED_FIELD"},
    justification = "POJO objects where the fields are populated by gson")
public class FFmpegError {
  /** @deprecated Use {@link #getCode()} instead */
  @Deprecated
  public int code;
  /** @deprecated Use {@link #getString()} instead */
  @Deprecated
  public String string;

  public int getCode() {
    return code;
  }

  public String getString() {
    return string;
  }
}
