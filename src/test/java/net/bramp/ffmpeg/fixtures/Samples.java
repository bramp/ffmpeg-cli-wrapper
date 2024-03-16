package net.bramp.ffmpeg.fixtures;

public final class Samples {
  private Samples() {
    throw new AssertionError("No instances for you!");
  }

  // Test sample files (only a handful to keep the repo small)
  public static final String TEST_PREFIX = "src/test/resources/net/bramp/ffmpeg/samples/";

  public static final String base_big_buck_bunny_720p_1mb = "big_buck_bunny_720p_1mb.mp4";
  public static final String base_testscreen_jpg = "testscreen.jpg";
  public static final String base_test_mp3 = "test.mp3";

  public static final String big_buck_bunny_720p_1mb = TEST_PREFIX + base_big_buck_bunny_720p_1mb;
  public static final String testscreen_jpg = TEST_PREFIX + base_testscreen_jpg;
  public static final String test_mp3 = TEST_PREFIX + base_test_mp3;

  private static final String book_m4b = "book_with_chapters.m4b";
  public static final String book_with_chapters = TEST_PREFIX + book_m4b;
  private static final String base_side_data_list = "side_data_list";
  public static final String side_data_list = TEST_PREFIX + base_side_data_list;

  // We don't have the following files
  public static final String FAKE_PREFIX = "fake/";

  public static final String always_on_my_mind =
      FAKE_PREFIX + "Always On My Mind [Program Only] - Adelen.mp4";

  public static final String start_pts_test = FAKE_PREFIX + "start_pts_test_1mb.ts";

  public static final String divide_by_zero = FAKE_PREFIX + "Divide By Zero.mp4";
  public static final String big_buck_bunny_720p_1mb_with_packets = FAKE_PREFIX + "big_buck_bunny_720p_1mb_packets.mp4";
  public static final String big_buck_bunny_720p_1mb_with_frames = FAKE_PREFIX + "big_buck_bunny_720p_1mb_frames.mp4";
  public static final String big_buck_bunny_720p_1mb_with_packets_and_frames = FAKE_PREFIX + "big_buck_bunny_720p_1mb_packets_and_frames.mp4";

  public static final String chapters_with_long_id = FAKE_PREFIX + "chapters_with_long_id.m4b";

  // TODO Change to a temp directory
  // TODO Generate random names, so we can run tests concurrently
  public static final String output_mp4 = "output.mp4";
}
