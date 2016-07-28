package net.bramp.ffmpeg;

import org.junit.Test;

import static net.bramp.ffmpeg.FFmpegUtils.millisecondsToString;
import static net.bramp.ffmpeg.FFmpegUtils.parseBitrate;
import static org.junit.Assert.assertEquals;

public class FFmpegUtilsTest {

  @Test
  public void testMillisecondsToString() throws Exception {
    assertEquals("63.123", millisecondsToString(63123));
    assertEquals("63", millisecondsToString(63000));
    assertEquals("5025.678", millisecondsToString(5025678));
    assertEquals("0", millisecondsToString(0));
    assertEquals("9223372036854775.807", millisecondsToString(Long.MAX_VALUE));
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
