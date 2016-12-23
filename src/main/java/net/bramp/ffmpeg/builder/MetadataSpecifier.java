package net.bramp.ffmpeg.builder;

// metadata_spec can be:
// g global (If metadata specifier is omitted, it defaults to global.)
// s[:stream_spec]
// c:chapter_index
// p:program_index
// index is meant to be zero based, by negitive is allowed as dummy values

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Metadata spec, as described in the "map_metadata" section of
 * https://www.ffmpeg.org/ffmpeg-all.html#Main-options
 */
public class MetadataSpecifier {

  final String spec;

  private MetadataSpecifier(String spec) {
    this.spec = checkNotNull(spec);
  }

  private MetadataSpecifier(String prefix, int index) {
    this.spec = checkNotNull(prefix) + ":" + index;
  }

  private MetadataSpecifier(String prefix, StreamSpecifier spec) {
    this.spec = checkNotNull(prefix) + ":" + checkNotNull(spec).spec();
  }

  public String spec() {
    return spec;
  }

  public static String checkValidKey(String key) {
    checkNotNull(key);
    checkArgument(!key.isEmpty(), "key must not be empty");
    checkArgument(key.matches("\\w+"), "key must only contain letters, numbers or _");
    return key;
  }

  public static MetadataSpecifier global() {
    return new MetadataSpecifier("g");
  }

  public static MetadataSpecifier chapter(int index) {
    return new MetadataSpecifier("c", index);
  }

  public static MetadataSpecifier program(int index) {
    return new MetadataSpecifier("p", index);
  }

  public static MetadataSpecifier stream(int index) {
    return new MetadataSpecifier("s", StreamSpecifier.stream(index));
  }

  public static MetadataSpecifier stream(StreamSpecifierType type) {
    return new MetadataSpecifier("s", StreamSpecifier.stream(type));
  }

  public static MetadataSpecifier stream(StreamSpecifierType stream_type, int stream_index) {
    return new MetadataSpecifier("s", StreamSpecifier.stream(stream_type, stream_index));
  }

  public static MetadataSpecifier stream(StreamSpecifier spec) {
    checkNotNull(spec);
    return new MetadataSpecifier("s", spec);
  }
}
