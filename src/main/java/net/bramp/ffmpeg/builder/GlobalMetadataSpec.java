package net.bramp.ffmpeg.builder;

public class GlobalMetadataSpec extends MetadataSpec {

  protected final static GlobalMetadataSpec SINGLETON = new GlobalMetadataSpec();

  protected GlobalMetadataSpec() {
    super(Type.Global);
  }

  @Override
  public String toString() {
    return "g";
  }
}
