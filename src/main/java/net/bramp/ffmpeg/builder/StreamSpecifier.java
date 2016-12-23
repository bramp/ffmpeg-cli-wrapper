package net.bramp.ffmpeg.builder;

import static com.google.common.base.Preconditions.checkNotNull;
import static net.bramp.ffmpeg.builder.MetadataSpecifier.checkValidKey;

/** https://ffmpeg.org/ffmpeg.html#Stream-specifiers */
public class StreamSpecifier {

  final String spec;

  private StreamSpecifier(String spec) {
    this.spec = spec;
  }

  public String spec() {
    return spec;
  }

  /**
   * Matches the stream with this index.
   *
   * @param index The stream index
   * @return A new StreamSpecifier
   */
  public static StreamSpecifier stream(int index) {
    return new StreamSpecifier(String.valueOf(index));
  }

  /**
   * Matches all streams of this type.
   *
   * @param type The stream type
   * @return A new StreamSpecifier
   */
  public static StreamSpecifier stream(StreamSpecifierType type) {
    checkNotNull(type);
    return new StreamSpecifier(type.toString());
  }

  /**
   * Matches the stream number stream_index of this type.
   *
   * @param type The stream type
   * @param index The stream index
   * @return A new StreamSpecifier
   */
  public static StreamSpecifier stream(StreamSpecifierType type, int index) {
    checkNotNull(type);
    return new StreamSpecifier(type.toString() + ":" + index);
  }

  /**
   * Matches all streams in the program.
   *
   * @param program_id The program id
   * @return A new StreamSpecifier
   */
  public static StreamSpecifier program(int program_id) {
    return new StreamSpecifier("p:" + program_id);
  }

  /**
   * Matches the stream with number stream_index in the program with the id program_id.
   *
   * @param program_id The program id
   * @param stream_index The stream index
   * @return A new StreamSpecifier
   */
  public static StreamSpecifier program(int program_id, int stream_index) {
    return new StreamSpecifier("p:" + program_id + ":" + stream_index);
  }

  /**
   * Match the stream by stream id (e.g. PID in MPEG-TS container).
   *
   * @param stream_id The stream id
   * @return A new StreamSpecifier
   */
  public static StreamSpecifier id(int stream_id) {
    return new StreamSpecifier("i:" + stream_id);
  }

  /**
   * Matches all streams with the given metadata tag.
   *
   * @param key The metadata tag
   * @return A new StreamSpecifier
   */
  public static StreamSpecifier tag(String key) {
    return new StreamSpecifier("m:" + checkValidKey(key));
  }

  /**
   * Matches streams with the metadata tag key having the specified value.
   *
   * @param key The metadata tag
   * @param value The metatdata's value
   * @return A new StreamSpecifier
   */
  public static StreamSpecifier tag(String key, String value) {
    checkValidKey(key);
    checkNotNull(value);
    return new StreamSpecifier("m:" + key + ":" + value);
  }

  /**
   * Matches streams with usable configuration, the codec must be defined and the essential
   * information such as video dimension or audio sample rate must be present.
   *
   * @return A new StreamSpecifier
   */
  public static StreamSpecifier usable() {
    return new StreamSpecifier("u");
  }
}
