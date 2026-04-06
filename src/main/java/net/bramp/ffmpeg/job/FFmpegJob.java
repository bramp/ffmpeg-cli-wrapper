package net.bramp.ffmpeg.job;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nullable;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.progress.ProgressListener;

/**
 * A FFmpegJob is a single job that can be run by FFmpeg. It can be a single pass, or a two pass
 * job.
 *
 * @author bramp
 */
public abstract class FFmpegJob implements Runnable {

  /** Enum representing the execution state of an FFmpeg job. */
  public enum State {
    WAITING,
    RUNNING,
    FINISHED,
    FAILED,
  }

  final FFmpeg ffmpeg;
  final ProgressListener listener;

  State state = State.WAITING;

  /** Constructs a new FFmpeg job with the given FFmpeg instance. */
  public FFmpegJob(FFmpeg ffmpeg) {
    this(ffmpeg, null);
  }

  /** Constructs a new FFmpeg job with the given FFmpeg instance and progress listener. */
  public FFmpegJob(FFmpeg ffmpeg, @Nullable ProgressListener listener) {
    this.ffmpeg = checkNotNull(ffmpeg);
    this.listener = listener;
  }

  public State getState() {
    return state;
  }
}
