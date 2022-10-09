package net.bramp.ffmpeg.builder;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class FFprobeBuilderTest {
  @Test
  public void testDefaultFFprobeConfiguration() {
    final List<String> args = new FFprobeBuilder().setInput("input").build();

    assertEquals(
        args,
        ImmutableList.of(
            "-v",
            "quiet",
            "-print_format",
            "json",
            "-show_error",
            "-show_format",
            "-show_streams",
            "-show_chapters",
            "input"));
  }

  @Test
  public void testPacketsAndFramesEnabled() {
    final List<String> args = new FFprobeBuilder().setInput("input").setShowPackets(true).setShowFrames(true).build();

    assertEquals(
        args,
        ImmutableList.of(
            "-v",
            "quiet",
            "-print_format",
            "json",
            "-show_error",
            "-show_format",
            "-show_streams",
            "-show_chapters",
            "-show_packets",
            "-show_frames",
            "input"));
  }

  @Test
  public void testDefaultOptionsDisabled() {
    final List<String> args = new FFprobeBuilder()
        .setInput("input")
        .setShowChapters(false)
        .setShowStreams(false)
        .setShowFormat(false)
        .build();

    assertEquals(
        args,
        ImmutableList.of(
            "-v",
            "quiet",
            "-print_format",
            "json",
            "-show_error",
            "input"));
  }

  @Test
  public void testSpecifyUserAgent() {
    final List<String> args = new FFprobeBuilder()
        .setInput("input")
        .setShowChapters(false)
        .setShowStreams(false)
        .setShowFormat(false)
        .setUserAgent("user agent")
        .build();

    assertEquals(
        args,
        ImmutableList.of(
            "-v",
            "quiet",
            "-print_format",
            "json",
            "-show_error",
            "-user_agent",
            "user agent",
            "input"));
  }

  @Test
  public void throwsExceptionIfInputIsNull()
  {
    final FFprobeBuilder builder = new FFprobeBuilder();
    assertThrows(NullPointerException.class, () -> builder.setInput(null));
  }

  @Test
  public void throwsExceptionIfNoInputIsGiven()
  {
    final FFprobeBuilder builder = new FFprobeBuilder();
    assertThrows(NullPointerException.class, builder::build);
  }
}
