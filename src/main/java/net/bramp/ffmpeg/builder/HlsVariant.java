package net.bramp.ffmpeg.builder;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an HLS variant stream mapping.
 *
 * <p>Used with {@code -var_stream_map} to group audio, video, and subtitle streams.
 */
public class HlsVariant {

  private final List<String> streams = new ArrayList<>();
  private String name;

  public HlsVariant() {}

  /**
   * Adds a video stream to this variant.
   *
   * @param index The zero-based index of the video stream in the output.
   * @return this
   */
  public HlsVariant addVideo(int index) {
    checkArgument(index >= 0, "index must be >= 0");
    streams.add("v:" + index);
    return this;
  }

  /**
   * Adds an audio stream to this variant.
   *
   * @param index The zero-based index of the audio stream in the output.
   * @return this
   */
  public HlsVariant addAudio(int index) {
    checkArgument(index >= 0, "index must be >= 0");
    streams.add("a:" + index);
    return this;
  }

  /**
   * Adds a subtitle stream to this variant.
   *
   * @param index The zero-based index of the subtitle stream in the output.
   * @return this
   */
  public HlsVariant addSubtitle(int index) {
    checkArgument(index >= 0, "index must be >= 0");
    streams.add("s:" + index);
    return this;
  }

  /**
   * Sets the name for this variant.
   *
   * @param name The variant name (e.g., "1080p").
   * @return this
   */
  public HlsVariant setName(String name) {
    this.name = checkNotNull(name);
    return this;
  }

  @Override
  public String toString() {
    if (streams.isEmpty()) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < streams.size(); i++) {
      if (i > 0) {
        sb.append(",");
      }
      sb.append(streams.get(i));
    }
    if (name != null && !name.isEmpty()) {
      sb.append(",name:").append(name);
    }
    return sb.toString();
  }
}
