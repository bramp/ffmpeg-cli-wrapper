package net.bramp.ffmpeg.builder;

public enum StreamSpecifierType {
  /** Video */
  Video("v"),

  /** Video streams which are not attached pictures, video thumbnails or cover arts. */
  PureVideo("V"),

  /** Audio */
  Audio("a"),

  /** Subtitles */
  Subtitle("s"),

  /** Data */
  Data("d"),

  /** Attachment */
  Attachment("t");

  final String prefix;

  StreamSpecifierType(String prefix) {
    this.prefix = prefix;
  }

  @Override
  public String toString() {
    return prefix;
  }
}
