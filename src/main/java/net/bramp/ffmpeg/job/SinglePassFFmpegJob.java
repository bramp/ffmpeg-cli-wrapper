package net.bramp.ffmpeg.job;

import com.google.common.base.Throwables;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.progress.ProgressListener;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

public class SinglePassFFmpegJob extends FFmpegJob {

  final public FFmpegBuilder builder;

  public SinglePassFFmpegJob(FFmpeg ffmpeg, FFmpegBuilder builder) {
    this(ffmpeg, builder, null);
  }

  public SinglePassFFmpegJob(FFmpeg ffmpeg, FFmpegBuilder builder,
      @Nullable ProgressListener listener) {
    super(ffmpeg, listener);
    this.builder = checkNotNull(builder);

    // Build the args now (but throw away the results). This allows the illegal arguments to be
    // caught early, but also allows the ffmpeg command to actually alter the arguments when
    // running.
    this.builder.build();
  }

  public void run() {

    state = State.RUNNING;

    try {
      ffmpeg.run(builder, listener);
      state = State.FINISHED;

    } catch (Throwable t) {
      state = State.FAILED;
      Throwables.propagate(t);
    }
  }
}
