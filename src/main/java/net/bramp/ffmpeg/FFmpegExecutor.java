package net.bramp.ffmpeg;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import net.bramp.ffmpeg.job.SinglePassFFmpegJob;
import net.bramp.ffmpeg.job.TwoPassFFmpegJob;
import net.bramp.ffmpeg.progress.ProgressListener;

/** Executor that creates and runs FFmpeg jobs. */
public class FFmpegExecutor {

  final FFmpeg ffmpeg;
  final FFprobe ffprobe;

  /** Constructs an FFmpegExecutor using default FFmpeg and FFprobe instances. */
  public FFmpegExecutor() throws IOException {
    this(new FFmpeg(), new FFprobe());
  }

  /** Constructs an FFmpegExecutor with the specified FFmpeg instance. */
  public FFmpegExecutor(FFmpeg ffmpeg) throws IOException {
    this(ffmpeg, new FFprobe());
  }

  /** Constructs an FFmpegExecutor with the specified FFmpeg and FFprobe instances. */
  public FFmpegExecutor(FFmpeg ffmpeg, FFprobe ffprobe) {
    this.ffmpeg = checkNotNull(ffmpeg);
    this.ffprobe = checkNotNull(ffprobe);
  }

  /** Creates a single-pass FFmpeg job from the given builder. */
  public FFmpegJob createJob(FFmpegBuilder builder) {
    return new SinglePassFFmpegJob(ffmpeg, builder);
  }

  /** Creates a single-pass FFmpeg job with a progress listener. */
  public FFmpegJob createJob(FFmpegBuilder builder, ProgressListener listener) {
    return new SinglePassFFmpegJob(ffmpeg, builder, listener);
  }

  /**
   * Creates a two pass job, which will execute FFmpeg twice to produce a better quality output.
   * More info: https://trac.ffmpeg.org/wiki/x264EncodingGuide#twopass
   *
   * @param builder The FFmpegBuilder
   * @return A new two-pass FFmpegJob
   */
  public FFmpegJob createTwoPassJob(FFmpegBuilder builder) {
    return new TwoPassFFmpegJob(ffmpeg, builder);
  }

  /** Creates a two-pass FFmpeg job with a progress listener. */
  public FFmpegJob createTwoPassJob(FFmpegBuilder builder, ProgressListener listener) {
    return new TwoPassFFmpegJob(ffmpeg, builder, listener);
  }
}
