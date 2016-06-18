package net.bramp.ffmpeg.builder;

// metadata_spec can be:
// g global (If metadata specifier is omitted, it defaults to global.)
// s[:stream_spec]
// c:chapter_index
// p:program_index
// index is meant to be zero based, by negitive is allowed as dummy values


/**
 * Metadata spec, as described in the "map_metadata" section of
 * https://www.ffmpeg.org/ffmpeg-all.html#Main-options
 */
public abstract class MetadataSpec {

  enum Type {
    Global, Stream, Chapter, Program
  }

  public final Type type;

  protected MetadataSpec(Type type) {
    this.type = type;
  }

  public abstract String toString();

  public static GlobalMetadataSpec global() {
    return GlobalMetadataSpec.SINGLETON;
  }

  public static ChapterMetadataSpec chapter(int index) {
    return new ChapterMetadataSpec(index);
  }

  public static ProgramMetadataSpec program(int index) {
    return new ProgramMetadataSpec(index);
  }

  public static StreamMetadataSpec stream(StreamSpec spec) {
    return new StreamMetadataSpec(spec);
  }

  public static StreamMetadataSpec stream(int spec) {
    return new StreamMetadataSpec(spec);
  }
}
