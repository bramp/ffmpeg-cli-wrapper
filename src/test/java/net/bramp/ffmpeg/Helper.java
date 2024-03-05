package net.bramp.ffmpeg;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Iterators.asEnumeration;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.List;
import javax.annotation.Nullable;

/** Random test helper methods. */
public class Helper {

  static final Function<String, InputStream> resourceLoader =
      new Function<String, InputStream>() {
        @Nullable
        @Override
        public InputStream apply(@Nullable String input) {
          return loadResource(input);
        }
      };

  /**
   * Simple wrapper around "new SequenceInputStream", so the user doesn't have to deal with the
   * horribly dated Enumeration type.
   *
   * @param input
   * @return
   */
  public static InputStream sequenceInputStream(Iterable<InputStream> input) {
    checkNotNull(input);
    return new SequenceInputStream(asEnumeration(input.iterator()));
  }

  public static InputStream loadResource(String name) {
    checkNotNull(name);
    return FFmpegTest.class.getResourceAsStream("fixtures/" + name);
  }

  /**
   * Loads all resources, and returns one stream containing them all.
   *
   * @param names
   * @return
   */
  public static InputStream combineResource(List<String> names) {
    checkNotNull(names);
    return sequenceInputStream(Iterables.transform(names, resourceLoader));
  }
}
