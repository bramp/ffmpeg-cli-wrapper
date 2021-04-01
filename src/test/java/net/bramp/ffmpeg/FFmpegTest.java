package net.bramp.ffmpeg;

import net.bramp.ffmpeg.fixtures.Codecs;
import net.bramp.ffmpeg.fixtures.Formats;
import net.bramp.ffmpeg.lang.NewProcessAnswer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

@RunWith(MockitoJUnitRunner.class)
public class FFmpegTest {

  @Mock ProcessFunction runFunc;

  FFmpeg ffmpeg;

  @Before
  public void before() throws IOException {
    when(runFunc.run(argThatHasItem("-formats")))
        .thenAnswer(new NewProcessAnswer("ffmpeg-formats"));
  }

  private void mockVersion0_10_9() throws IOException {
    when(runFunc.run(argThatHasItem("-version")))
        .thenAnswer(new NewProcessAnswer("ffmpeg-version_0.10.9"));
    when(runFunc.run(argThatHasItem("-codecs")))
        .thenAnswer(new NewProcessAnswer("ffmpeg-codecs_0.10.9"));
    ffmpeg = new FFmpeg(runFunc);
  }

  private void mockVersion4_1_1() throws IOException {
    when(runFunc.run(argThatHasItem("-version")))
        .thenAnswer(new NewProcessAnswer("ffmpeg-version_4.1.1"));
    when(runFunc.run(argThatHasItem("-codecs")))
        .thenAnswer(new NewProcessAnswer("ffmpeg-codecs_4.1.1"));
    ffmpeg = new FFmpeg(runFunc);
  }

  @SuppressWarnings("unchecked")
  public static <T> List<T> argThatHasItem(T s) {
    return (List<T>) argThat(hasItem(s));
  }

  @Test
  public void testVersion() throws Exception {
    mockVersion0_10_9();

    assertEquals("ffmpeg version 0.10.9-7:0.10.9-1~raring1", ffmpeg.version());
    assertEquals("ffmpeg version 0.10.9-7:0.10.9-1~raring1", ffmpeg.version());

    verify(runFunc, times(1)).run(argThatHasItem("-version"));
  }

  @Test
  public void testCodecsInLegacyVersions() throws IOException {
    mockVersion0_10_9();

    // Run twice, the second should be cached
    assertEquals(Codecs.LEGACY_CODECS, ffmpeg.codecs());
    assertEquals(Codecs.LEGACY_CODECS, ffmpeg.codecs());

    verify(runFunc, times(1)).run(argThatHasItem("-codecs"));
  }

  @Test
  public void testCodecsInModernVersions() throws IOException {
    mockVersion4_1_1();

    // Run twice, the second should be cached
    assertEquals(Codecs.CODECS, ffmpeg.codecs());
    assertEquals(Codecs.CODECS, ffmpeg.codecs());

    verify(runFunc, times(1)).run(argThatHasItem("-codecs"));
  }

  @Test
  public void testFormats() throws IOException {
    mockVersion0_10_9();

    // Run twice, the second should be cached
    assertEquals(Formats.FORMATS, ffmpeg.formats());
    assertEquals(Formats.FORMATS, ffmpeg.formats());

    verify(runFunc, times(1)).run(argThatHasItem("-formats"));
  }
}
