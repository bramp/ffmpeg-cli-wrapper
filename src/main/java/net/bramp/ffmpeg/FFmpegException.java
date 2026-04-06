package net.bramp.ffmpeg;

import java.io.IOException;
import net.bramp.ffmpeg.probe.FFmpegError;

/** Exception thrown when an FFmpeg command fails. */
public class FFmpegException extends IOException {

  private static final long serialVersionUID = 3048288225568984942L;
  private final FFmpegError error;

  /** Constructs an FFmpegException with the specified message and error details. */
  public FFmpegException(String message, FFmpegError error) {
    super(message);
    this.error = error;
  }

  public FFmpegError getError() {
    return error;
  }
}
