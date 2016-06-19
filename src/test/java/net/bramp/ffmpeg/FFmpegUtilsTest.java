package net.bramp.ffmpeg;

import org.junit.Test;

import static net.bramp.ffmpeg.FFmpegUtils.millisecondsToString;
import static org.junit.Assert.assertEquals;

public class FFmpegUtilsTest {

  @Test
  public void testMillisecondsToString() throws Exception {
    assertEquals("00:01:03.123", millisecondsToString(63123));
    assertEquals("00:01:03", millisecondsToString(63000));
    assertEquals("01:23:45.678", millisecondsToString(5025678));
    assertEquals("00:00:00", millisecondsToString(0));
  }
}
