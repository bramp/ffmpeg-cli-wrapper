package net.bramp.ffmpeg.fixtures;

public abstract class Samples {
  private Samples() {}

  public static final String TEST_PREFIX = "src/test/resources/net/bramp/ffmpeg/samples/";
  public static final String big_buck_bunny_720p_1mb = TEST_PREFIX + "big_buck_bunny_720p_1mb.mp4";

  public static final String output_mp4 = "output.mp4"; // TODO Change to a temp directory
}
