package net.bramp.ffmpeg.builder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.junit.Test;

public class FFmpegFileInputBuilderTest extends AbstractFFmpegInputBuilderTest {
  @Override
  protected AbstractFFmpegInputBuilder<?> getBuilder() {
    return new FFmpegBuilder().addInput("input.mp4");
  }

  @Override
  protected List<String> removeCommon(List<String> command) {
    assertEquals(command.get(command.size() - 1), "input.mp4");
    assertEquals(command.get(command.size() - 2), "-i");

    return command.subList(0, command.size() - 2);
  }

  @Test
  public void testFileName() {
    List<String> command = new FFmpegBuilder().addInput("input.mp4").build(0);

    assertThat(command, is(ImmutableList.of("-i", "input.mp4")));
  }
}
