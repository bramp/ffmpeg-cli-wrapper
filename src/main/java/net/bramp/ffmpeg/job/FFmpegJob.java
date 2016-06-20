package net.bramp.ffmpeg.job;

import com.google.common.base.Optional;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.progress.FFmpegProgressListener;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 * @author bramp
 *
 */
public abstract class FFmpegJob implements Runnable {

  public enum State {
    WAITING, RUNNING, FINISHED, FAILED,
  }

  final FFmpeg ffmpeg;
  final FFmpegProgressListener progressListener;

  State state = State.WAITING;

  public FFmpegJob(FFmpeg ffmpeg) {
    this(ffmpeg, Optional.<FFmpegProgressListener>absent());
  }

  public FFmpegJob(FFmpeg ffmpeg, Optional<FFmpegProgressListener> progressListener) {
    this.ffmpeg = checkNotNull(ffmpeg);
    this.progressListener = progressListener.orNull();
  }


  public State getState() {
    return state;
  }
}
