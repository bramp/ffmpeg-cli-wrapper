package net.bramp.ffmpeg.job;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

public class TwoPassFFmpegJob extends FFmpegJob {

  final static Logger LOG = LoggerFactory.getLogger(TwoPassFFmpegJob.class);

  final String passlogPrefix;
  final List<String> args1;
  final List<String> args2;

  public TwoPassFFmpegJob(FFmpeg ffmpeg, FFmpegBuilder builder) {
    super(ffmpeg);

    checkNotNull(builder);

    // Two pass
    final boolean override = builder.getOverrideOutputFiles();

    // Random prefix so multiple runs don't clash
    this.passlogPrefix = UUID.randomUUID().toString();

    this.args1 = builder.setPass(1).setPassPrefix(passlogPrefix).overrideOutputFiles(true).build();

    this.args2 =
        builder.setPass(2).setPassPrefix(passlogPrefix).overrideOutputFiles(override).build();
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
        ffmpeg.run(args1);
        ffmpeg.run(args2);
      } finally {
        deletePassLog();
      }
      state = State.FINISHED;

    } catch (Throwable t) {
      state = State.FAILED;
      Throwables.propagate(t);
    }
  }
}
