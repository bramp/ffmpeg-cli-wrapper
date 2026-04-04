package net.bramp.ffmpeg.builder;

import static org.junit.Assert.assertEquals;

import java.util.List;

public class FFmpegOutputBuilderTest extends AbstractFFmpegOutputBuilderTest {

  @Override
  protected AbstractFFmpegOutputBuilder<?> getBuilder() {
    return new FFmpegBuilder().addInput("input.mp4").done().addOutput("output.mp4");
  }

  @Override
  protected List<String> removeCommon(List<String> command) {
    assertEquals("output.mp4", command.get(command.size() - 1));

    return command.subList(0, command.size() - 1);
  }
}
