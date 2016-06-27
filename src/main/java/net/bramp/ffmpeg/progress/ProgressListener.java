package net.bramp.ffmpeg.progress;

public interface ProgressListener {
  // TODO Consider adding other stats. Start, end, stream, error
  void progress(Progress progress);
}
