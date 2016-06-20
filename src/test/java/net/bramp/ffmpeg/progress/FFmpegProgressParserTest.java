package net.bramp.ffmpeg.progress;

import com.google.common.collect.ImmutableList;
import net.bramp.ffmpeg.FFmpegTest;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


public class FFmpegProgressParserTest {

  /**
   * Simple wrapper around "new SequenceInputStream", so the user doesn't have to deal with the
   * horribly dated Enumeration type.
   * 
   * @param input
   * @return
   */
  private InputStream sequenceInputStream(List<InputStream> input) {
    checkNotNull(input);
    return new SequenceInputStream(Collections.enumeration(input));
  }

  @Test
  public void test() throws IOException {
    FFmpegProgressListener listener = new FFmpegProgressListener() {

      @Override
      public void progress(Progress p) {
        System.out.println(p);
      }
    };
    FFmpegProgressParser parser = new FFmpegStreamProgressParser(listener);

    List<InputStream> progressInput =
        ImmutableList.of(FFmpegTest.loadResource("ffmpeg-progress-0"),
            FFmpegTest.loadResource("ffmpeg-progress-1"),
            FFmpegTest.loadResource("ffmpeg-progress-2"));

    parser.processStream(sequenceInputStream(progressInput));
  }
}
