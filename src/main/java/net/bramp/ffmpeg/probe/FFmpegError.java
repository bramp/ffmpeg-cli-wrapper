package net.bramp.ffmpeg.probe;

import java.io.Serializable;

/** Represents an error returned by FFprobe. */
public class FFmpegError implements Serializable {
  private static final long serialVersionUID = 1L;

  public int code;
  public String string;

  public int getCode() {
    return code;
  }

  public String getString() {
    return string;
  }
}
