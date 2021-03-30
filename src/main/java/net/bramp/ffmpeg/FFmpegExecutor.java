package net.bramp.ffmpeg;

import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import net.bramp.ffmpeg.job.SinglePassFFmpegJob;
import net.bramp.ffmpeg.job.TwoPassFFmpegJob;
import net.bramp.ffmpeg.progress.ProgressListener;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

public class FFmpegExecutor {

  final FFmpeg ffmpeg;
  final FFprobe ffprobe;

  public FFmpegExecutor() throws IOException {
    this(new FFmpeg(), new FFprobe());
  }

  public FFmpegExecutor(FFmpeg ffmpeg) throws IOException {
    this(ffmpeg, new FFprobe());
  }

  public FFmpegExecutor(FFmpeg ffmpeg, FFprobe ffprobe) {
    this.ffmpeg = checkNotNull(ffmpeg);
    this.ffprobe = checkNotNull(ffprobe);
  }

  public FFmpegJob createJob(FFmpegBuilder builder) {
    return new SinglePassFFmpegJob(ffmpeg, builder);
  }

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
}
