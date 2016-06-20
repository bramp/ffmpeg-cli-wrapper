package net.bramp.ffmpeg.job;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.util.List;

public class SinglePassFFmpegJob extends FFmpegJob {

  final List<String> args;

  public SinglePassFFmpegJob(FFmpeg ffmpeg, FFmpegBuilder argsBuilder) {
    super(ffmpeg);
    this.args = argsBuilder.build();
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
