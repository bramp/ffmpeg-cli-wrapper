package net.bramp.ffmpeg.job;

import com.google.common.base.Throwables;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.progress.ProgressListener;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

public class TwoPassFFmpegJob extends FFmpegJob {

  final String passlogPrefix;
  final FFmpegBuilder builder;

  public TwoPassFFmpegJob(FFmpeg ffmpeg, FFmpegBuilder builder) {
    this(ffmpeg, builder, null);
  }

  public TwoPassFFmpegJob(FFmpeg ffmpeg, FFmpegBuilder builder, @Nullable ProgressListener listener) {
    super(ffmpeg, listener);

    // Random prefix so multiple runs don't clash
    this.passlogPrefix = UUID.randomUUID().toString();
    this.builder = checkNotNull(builder).setPassPrefix(passlogPrefix);

    // Build the args now (but throw away the results). This allows the illegal arguments to be
    // caught early, but also allows the ffmpeg command to actually alter the arguments when
    // running.
    this.builder.setPass(1).build();
  }

  protected void deletePassLog() throws IOException {
    final Path cwd = Paths.get("");
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(cwd, passlogPrefix + "*.log*")) {
      for (Path p : stream) {
        Files.deleteIfExists(p);
      }
    }
  }

  public void run() {
    state = State.RUNNING;

    try {
      try {
        // Two pass
        final boolean override = builder.getOverrideOutputFiles();

        FFmpegBuilder b1 = builder.setPass(1).overrideOutputFiles(true);
        ffmpeg.run(b1, listener);

        FFmpegBuilder b2 = builder.setPass(2).overrideOutputFiles(override);
        ffmpeg.run(b2, listener);

      } finally {
        deletePassLog();
      }
      state = State.FINISHED;

    } catch (Throwable t) {
      state = State.FAILED;

      Throwables.throwIfUnchecked(t);
      throw new RuntimeException(t);
    }
  }
}
