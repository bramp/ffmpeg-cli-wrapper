package net.bramp.ffmpeg.builder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * https://ffmpeg.org/ffmpeg.html#Stream-specifiers
 */
public abstract class StreamSpec {

  enum Type {
    StreamIndex, Program, StreamId, Metadata, Usable
  }

  enum StreamType {
    Any(""),
    Video("v"),
    PureVideo("V"),
    Audio("a"),
    Subtiles("s"),
    Data("d"),
    Attachments("t");

    final String prefix;

    StreamType(String prefix) {
      this.prefix = prefix;
    }

    public String toString() {
      return prefix;
    }
  }

  public final Type type;

  protected StreamSpec(Type type) {
    this.type = type;
  }

  public static class IndexStreamSpec extends StreamSpec {

  }

  public static class ProgramStreamSpec extends StreamSpec {

  }

  public static class IdStreamSpec extends StreamSpec {

  }

  public static class MetadataStreamSpec extends StreamSpec {

    public final String key, value;

    protected MetadataStreamSpec(String key, String value) {
      super(Type.Metadata);
      this.key = checkNotNull(key);
      this.value = value;
    }

    @Override
    public String toString() {
      return "m:" + key + (value != null ? ":" + value : "");
    }
  }

  public static class UsableStreamSpec extends StreamSpec {
    protected final static UsableStreamSpec SINGLETON = new UsableStreamSpec();

    protected UsableStreamSpec() {
      super(Type.Usable);
    }

    @Override
    public String toString() {
      return "u";
    }
  }

  public abstract String toString();

  public StreamSpec stream(int stream_index) {
    return null;
  }

  public StreamSpec stream(StreamType stream_type) {
    checkNotNull(stream_type);
    return null;
  }

  public StreamSpec stream(StreamType stream_type, int stream_index) {
    checkNotNull(stream_type);
    return null;
  }

  public StreamSpec program(int program_id) {
    return null;
  }

  public StreamSpec program(int program_id, int stream_index) {
    return null;
  }

  public StreamSpec id(int stream_id) {
    return null;
  }

  public StreamSpec tag(String key) {
    checkNotNull(key);
    return null;
  }

  public StreamSpec tag(String key, String value) {
    checkNotNull(key);
    checkNotNull(value);
    return null;
  }

  public StreamSpec usable() {
    return UsableStreamSpec.SINGLETON;
  }
}


// metadata_spec can be:
// g global (If metadata specifier is omitted, it defaults to global.)
// s[:stream_spec]
// c:chapter_index
// p:program_index
// index is meant to be zero based, by negitive is allowed as dummy values
/*
 * public abstract class MetadataSpec { global() stream(index(0)) stream(video(), index(0))
 * chapter(1) program(1) }
 */
