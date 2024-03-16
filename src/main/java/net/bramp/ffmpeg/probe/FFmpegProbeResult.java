package net.bramp.ffmpeg.probe;

import com.google.common.collect.ImmutableList;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Collections;
import java.util.List;

/** TODO Make this immutable */
@SuppressFBWarnings(
    value = {"UUF_UNUSED_PUBLIC_OR_PROTECTED_FIELD"},
    justification = "POJO objects where the fields are populated by gson")
public class FFmpegProbeResult {
  /** @deprecated Use {@link #getError()} instead */
  @Deprecated
  public FFmpegError error;
  /** @deprecated Use {@link #getFormat()} instead */
  @Deprecated
  public FFmpegFormat format;
  /** @deprecated Use {@link #getStreams()} instead */
  @Deprecated
  public List<FFmpegStream> streams;
  /** @deprecated Use {@link #getChapters()} instead */
  @Deprecated
  public List<FFmpegChapter> chapters;

  public FFmpegError getError() {
    return error;
  }

  public boolean hasError() {
    return error != null;
  }

  public FFmpegFormat getFormat() {
    return format;
  }

  public List<FFmpegStream> getStreams() {
    if (streams == null) return Collections.emptyList();
    return ImmutableList.copyOf(streams);
  }

  public List<FFmpegChapter> getChapters() {
    if (chapters == null) return Collections.emptyList();
    return ImmutableList.copyOf(chapters);
  }
}
