package net.bramp.ffmpeg.builder;

// metadata_spec can be:
// g global (If metadata specifier is omitted, it defaults to global.)
// s[:stream_spec]
// c:chapter_index
// p:program_index
// index is meant to be zero based, by negitive is allowed as dummy values

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.errorprone.annotations.Immutable;

/**
 * Metadata spec, as described in the "map_metadata" section of <a
 * href="https://www.ffmpeg.org/ffmpeg-all.html#Main-options">Main options</a>.
 */
@Immutable
public class MetadataSpecifier {

  private final String spec;

  private MetadataSpecifier(String spec) {
    this.spec = checkNotNull(spec);
  }

  private MetadataSpecifier(String prefix, int index) {
    this.spec = checkNotNull(prefix) + ":" + index;
  }

  private MetadataSpecifier(String prefix, StreamSpecifier spec) {
    this.spec = checkNotNull(prefix) + ":" + checkNotNull(spec).spec();
  }

  /** Returns the metadata specifier string. */
  public String spec() {
    return spec;
  }

  /** Validates and returns the given metadata key. */
  public static String checkValidKey(String key) {
    checkNotNull(key);
    checkArgument(!key.isEmpty(), "key must not be empty");
    checkArgument(key.matches("\\w+"), "key must only contain letters, numbers or _");
    return key;
  }

  /** Creates a global metadata specifier. */
  public static MetadataSpecifier global() {
    return new MetadataSpecifier("g");
  }

  /** Creates a chapter metadata specifier for the given index. */
  public static MetadataSpecifier chapter(int index) {
    return new MetadataSpecifier("c", index);
  }

  /** Creates a program metadata specifier for the given index. */
  public static MetadataSpecifier program(int index) {
    return new MetadataSpecifier("p", index);
  }

  /** Creates a stream metadata specifier for the given stream index. */
  public static MetadataSpecifier stream(int index) {
    return new MetadataSpecifier("s", StreamSpecifier.stream(index));
  }

  /** Creates a stream metadata specifier for the given stream type. */
  public static MetadataSpecifier stream(StreamSpecifierType type) {
    return new MetadataSpecifier("s", StreamSpecifier.stream(type));
  }

  /** Creates a stream metadata specifier for the given type and index. */
  public static MetadataSpecifier stream(StreamSpecifierType stream_type, int stream_index) {
    return new MetadataSpecifier("s", StreamSpecifier.stream(stream_type, stream_index));
  }

  /** Creates a stream metadata specifier from an existing stream specifier. */
  public static MetadataSpecifier stream(StreamSpecifier spec) {
    checkNotNull(spec);
    return new MetadataSpecifier("s", spec);
  }
}
