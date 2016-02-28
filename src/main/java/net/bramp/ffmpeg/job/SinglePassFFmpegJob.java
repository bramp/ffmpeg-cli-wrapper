package net.bramp.ffmpeg.job;

import com.google.common.base.Throwables;
import net.bramp.ffmpeg.FFmpeg;

import java.util.List;

public class SinglePassFFmpegJob extends FFmpegJob {

  final List<String> args;

  public SinglePassFFmpegJob(FFmpeg ffmpeg, List<String> args) {
    super(ffmpeg);
    this.args = args;
  }

  public void run() {
    state = State.RUNNING;

    try {
      ffmpeg.run(args);
      state = State.FINISHED;

    } catch (Throwable t) {
      state = State.FAILED;
      Throwables.propagate(t);
    }
  }
}
