package net.bramp.ffmpeg.progress;

/** Captures output from the ffmpeg command line as status occurs. */
// TODO Consider adding other stats. Start, end, stream, error
public interface ProgressListener {
  /** Called on each progress update, typically once per second, and at the end of processing. */
  void progress(Progress progress);
}
