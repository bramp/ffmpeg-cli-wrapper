package net.bramp.ffmpeg.progress;

import com.google.common.collect.Lists;
import java.util.List;

/** Test class to keep a record of all progresses. */
public class RecordingProgressListener implements ProgressListener {
  public final List<Progress> progesses = Lists.newArrayList();

  @Override
  public void progress(Progress p) {
    progesses.add(p);
  }

  public void reset() {
    progesses.clear();
  }
}
