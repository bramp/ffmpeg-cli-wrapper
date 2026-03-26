package net.bramp.ffmpeg.builder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.junit.Test;

public abstract class AbstractFFmpegInputBuilderTest extends AbstractFFmpegStreamBuilderTest {
  @Override
  protected abstract AbstractFFmpegInputBuilder<?> getBuilder();

  @Test
  public void testReadAtNativeFrameRate() {
    List<String> command = getBuilder().readAtNativeFrameRate().build(0);

    assertThat(removeCommon(command), is(ImmutableList.of("-re")));
  }

  @Test
  public void testSetStreamLoopInfinit() {
    List<String> command = getBuilder().setStreamLoop(-1).build(0);

    assertThat(removeCommon(command), is(ImmutableList.of("-stream_loop", "-1")));
  }

  @Test
  public void testSetStreamLoopCounter() {
    List<String> command = getBuilder().setStreamLoop(2).build(0);

    assertThat(removeCommon(command), is(ImmutableList.of("-stream_loop", "2")));
  }
}
