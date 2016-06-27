package net.bramp.ffmpeg;

import org.junit.Test;

import static net.bramp.ffmpeg.FFmpegUtils.millisecondsToString;
import static net.bramp.ffmpeg.FFmpegUtils.parseBitrate;
import static org.junit.Assert.assertEquals;

public class FFmpegUtilsTest {

  @Test
  public void testMillisecondsToString() throws Exception {
    assertEquals("00:01:03.123", millisecondsToString(63123));
    assertEquals("00:01:03", millisecondsToString(63000));
    assertEquals("01:23:45.678", millisecondsToString(5025678));
    assertEquals("00:00:00", millisecondsToString(0));
    assertEquals("2562047788015:12:55.807", millisecondsToString(Long.MAX_VALUE));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMillisecondsToStringNegative() throws Exception {
    millisecondsToString(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMillisecondsToStringNegativeMinValue() throws Exception {
    millisecondsToString(Long.MIN_VALUE);
  }

  @Test
  public void testParseBitrate() throws Exception {
    assertEquals(12300, parseBitrate("12.3kbits/s"));
    assertEquals(1000, parseBitrate("1kbits/s"));
    assertEquals(123, parseBitrate("0.123kbits/s"));
  }
}
