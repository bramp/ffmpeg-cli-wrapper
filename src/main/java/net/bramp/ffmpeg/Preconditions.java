package net.bramp.ffmpeg;

import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;

import javax.annotation.Nullable;
import java.net.URI;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public final class Preconditions {

  private static final List<String> rtps = ImmutableList.of("rtsp", "rtp", "rtmp");
  private static final List<String> udpTcp = ImmutableList.of("udp", "tcp");

  Preconditions() {
    throw new AssertionError("No instances for you!");
  }

  /**
   * Ensures the argument is not null, empty string, or just whitespace.
   *
   * @param arg The argument
   * @param errorMessage The exception message to use if the check fails
   * @return The passed in argument if it is not blank
   */
  public static String checkNotEmpty(String arg, @Nullable Object errorMessage) {
    boolean empty = Strings.isNullOrEmpty(arg) || CharMatcher.whitespace().matchesAllOf(arg);
    checkArgument(!empty, errorMessage);
    return arg;
  }

  /**
   * Checks if the URI is valid for streaming to.
   *
   * @param uri The URI to check
   * @return The passed in URI if it is valid
   * @throws IllegalArgumentException if the URI is not valid.
   */
  public static URI checkValidStream(URI uri) throws IllegalArgumentException {
    String scheme = checkNotNull(uri).getScheme();
    scheme = checkNotNull(scheme, "URI is missing a scheme").toLowerCase();

    if (rtps.contains(scheme)) {
      return uri;
    }

    if (udpTcp.contains(scheme)) {
      if (uri.getPort() == -1) {
        throw new IllegalArgumentException("must set port when using udp or tcp scheme");
      }
      return uri;
    }

    throw new IllegalArgumentException("not a valid output URL, must use rtp/tcp/udp scheme");
  }
}
