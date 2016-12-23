package net.bramp.ffmpeg.progress;

/** Captures output from the ffmpeg command line as status occurs. */
// TODO Consider adding other stats. Start, end, stream, error
public interface ProgressListener {
  // Called every time there is status, typically once a second, and at the end.
  // The Progress.status will normally be CONTINUE, until after the last frame, when it will be END.
  void progress(Progress progress);
}
