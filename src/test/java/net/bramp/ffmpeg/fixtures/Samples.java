package net.bramp.ffmpeg.fixtures;

public final class Samples {
  private Samples() {
    throw new AssertionError("No instances for you!");
  }

  public static final String TEST_PREFIX = "src/test/resources/net/bramp/ffmpeg/samples/";
  public static final String big_buck_bunny_720p_1mb = TEST_PREFIX + "big_buck_bunny_720p_1mb.mp4";

  // We don't have the following files
  public static final String FAKE_PREFIX = "fake/";

  public static final String always_on_my_mind = FAKE_PREFIX
      + "Always On My Mind [Program Only] - Adelén.mp4";

  public static final String divide_by_zero = FAKE_PREFIX + "Divide By Zero.mp4";

  public static final String output_mp4 = "output.mp4"; // TODO Change to a temp directory
}
