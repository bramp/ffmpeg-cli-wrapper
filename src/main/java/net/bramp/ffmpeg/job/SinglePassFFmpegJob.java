package net.bramp.ffmpeg.job;

import com.google.common.base.Optional;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.progress.FFmpegProgressListener;
import net.bramp.ffmpeg.progress.FFmpegProgressParser;
import net.bramp.ffmpeg.progress.FFmpegTcpProgressParser;
import net.bramp.ffmpeg.progress.NullFFmpegProgressParser;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class SinglePassFFmpegJob extends FFmpegJob {

  final List<String> args;
  final FFmpegProgressParser parser;

  public SinglePassFFmpegJob(FFmpeg ffmpeg, FFmpegBuilder builder) {
    this(ffmpeg, builder, Optional.<FFmpegProgressListener>absent());
  }

  public SinglePassFFmpegJob(FFmpeg ffmpeg, FFmpegBuilder builder,
      Optional<FFmpegProgressListener> listener) {
    super(ffmpeg);
    checkNotNull(builder);
    checkNotNull(listener);

    if (listener.isPresent()) {
      this.parser = new FFmpegTcpProgressParser(listener.get());
      builder.addProgress(this.parser.getUri());

    } else {
      this.parser = NullFFmpegProgressParser.instance();
    }

    this.args = builder.build();
  }

  public void run() {

    state = State.RUNNING;

    try {
      ffmpeg.run(args);
      state = State.FINISHED;

    } catch (Throwable t) {
      state = State.FAILED;
      Throwables.propagate(t);
    } finally {
      parser.stop();
    }
  }
}
