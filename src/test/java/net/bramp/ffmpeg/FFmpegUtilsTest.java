package net.bramp.ffmpeg;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static net.bramp.ffmpeg.FFmpegUtils.*;
import static org.junit.Assert.assertEquals;

public class FFmpegUtilsTest {

  @Test(expected = AssertionError.class)
  public void testAbstractUtilsClass() {
    new FFmpegUtils();
  }

  @Test
  @SuppressWarnings("deprecation")
  public void testMillisecondsToString() {
    assertEquals("00:01:03.123", millisecondsToString(63123));
    assertEquals("00:01:03", millisecondsToString(63000));
    assertEquals("01:23:45.678", millisecondsToString(5025678));
    assertEquals("00:00:00", millisecondsToString(0));
    assertEquals("00:00:00.001", millisecondsToString(1));
  }

  @Test(expected = IllegalArgumentException.class)
  @SuppressWarnings("deprecation")
  public void testMillisecondsToStringNegative() {
    millisecondsToString(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  @SuppressWarnings("deprecation")
  public void testMillisecondsToStringNegativeMinValue() {
    millisecondsToString(Long.MIN_VALUE);
  }

  @Test
  public void testToTimecode() {
    assertEquals("00:00:00", toTimecode(0, TimeUnit.NANOSECONDS));
    assertEquals("00:00:00.000000001", toTimecode(1, TimeUnit.NANOSECONDS));
    assertEquals("00:00:00.000001", toTimecode(1, TimeUnit.MICROSECONDS));
    assertEquals("00:00:00.001", toTimecode(1, TimeUnit.MILLISECONDS));
    assertEquals("00:00:01", toTimecode(1, TimeUnit.SECONDS));
    assertEquals("00:01:00", toTimecode(1, TimeUnit.MINUTES));
    assertEquals("01:00:00", toTimecode(1, TimeUnit.HOURS));
  }

  @Test
  public void testFromTimecode() {
    assertEquals(63123000000L, fromTimecode("00:01:03.123"));
    assertEquals(63000000000L, fromTimecode("00:01:03"));
    assertEquals(5025678000000L, fromTimecode("01:23:45.678"));
    assertEquals(0, fromTimecode("00:00:00"));
  }

  @Test
  public void testParseBitrate() {
    assertEquals(12300, parseBitrate("12.3kbits/s"));
    assertEquals(1000, parseBitrate("1kbits/s"));
    assertEquals(123, parseBitrate("0.123kbits/s"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseBitrateInvalidEmpty() {
    parseBitrate("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseBitrateInvalidNumber() {
    parseBitrate("12.3");
  }

}
