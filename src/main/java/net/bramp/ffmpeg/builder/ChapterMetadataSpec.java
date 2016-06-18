package net.bramp.ffmpeg.builder;

public class ChapterMetadataSpec extends MetadataSpec {

  public final int index;

  protected ChapterMetadataSpec(int index) {
    super(Type.Chapter);
    this.index = index;
  }

  @Override
  public String toString() {
    return "c:" + index;
  }
}
