package net.bramp.ffmpeg.fixtures;

import com.google.common.collect.ImmutableList;
import net.bramp.ffmpeg.progress.Progress;

import java.util.List;

public final class Progresses {

  private Progresses() {
    throw new AssertionError("No instances for you!");
  }

  public static final ImmutableList<String> allFiles =
      ImmutableList.of("ffmpeg-progress-0", "ffmpeg-progress-1", "ffmpeg-progress-2");

  public static final ImmutableList<Progress> allProgresses =
      ImmutableList.of(
          new Progress(5, 0.0f, 800, 48, 512000000, 0, 0, 1.01f, Progress.Status.CONTINUE),
          new Progress(118, 23.4f, -1, -1, 5034667000L, 0, 0, -1, Progress.Status.CONTINUE),
          new Progress(
              132, 23.1f, 1935500, 1285168, 5312000000L, 0, 0, 0.929f, Progress.Status.END));
}
