package net.bramp.ffmpeg.info;


import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.ProcessFunction;
import net.bramp.ffmpeg.lang.NewProcessAnswer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static net.bramp.ffmpeg.FFmpegTest.argThatHasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FFmpegGetInfoTest {
  @Mock
  ProcessFunction runFunc;

  @Before
  public void before() throws IOException {
    when(runFunc.run(argThatHasItem("-version")))
        .thenAnswer(new NewProcessAnswer("ffmpeg-version"));

    when(runFunc.run(argThatHasItem("-codecs")))
        .thenAnswer(new NewProcessAnswer("ffmpeg-codecs"));
  }

  @Test
  public void getFFmpegCodecSupportTest() throws IOException {
    List<Codec> videoCodecs = new ArrayList<>();
    List<Codec> audioCodecs = new ArrayList<>();
    List<Codec> subtitleCodecs = new ArrayList<>();
    List<Codec> dataCodecs = new ArrayList<>();
    List<Codec> otherCodecs = new ArrayList<>();

    FFmpeg ffmpeg = new FFmpeg("ffmpeg", runFunc);
    ffmpeg.codecs();

    for (Codec codec : ffmpeg.codecs()) {
      switch (codec.getType()) {
        case VIDEO:
          videoCodecs.add(codec);
          break;
        case AUDIO:
          audioCodecs.add(codec);
          break;
        case SUBTITLE:
          subtitleCodecs.add(codec);
          break;
        case DATA:
          dataCodecs.add(codec);
          break;
        default:
          otherCodecs.add(codec);

      }
    }

    assertThat(videoCodecs, hasSize(245));
    assertThat(audioCodecs, hasSize(180));
    assertThat(subtitleCodecs, hasSize(26));
    assertThat(dataCodecs, hasSize(8));
    assertThat(otherCodecs, hasSize(0));

    assertThat(videoCodecs, hasItem(hasProperty("name", equalTo("h264"))));
    assertThat(audioCodecs, hasItem(hasProperty("name", equalTo("aac"))));
    assertThat(subtitleCodecs, hasItem(hasProperty("name", equalTo("ssa"))));
    assertThat(dataCodecs, hasItem(hasProperty("name", equalTo("bin_data"))));
  }
}
