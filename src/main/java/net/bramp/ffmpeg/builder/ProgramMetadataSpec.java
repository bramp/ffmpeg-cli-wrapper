package net.bramp.ffmpeg.builder;

public class ProgramMetadataSpec extends MetadataSpec {

  public final int index;

  protected ProgramMetadataSpec(int index) {
    super(Type.Program);
    this.index = index;
  }

  @Override
  public String toString() {
    return "p:" + index;
  }
}
