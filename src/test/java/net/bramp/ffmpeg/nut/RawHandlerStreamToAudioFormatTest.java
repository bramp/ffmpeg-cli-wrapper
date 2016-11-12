package net.bramp.ffmpeg.nut;

import org.apache.commons.lang3.math.Fraction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.sound.sampled.AudioFormat;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static javax.sound.sampled.AudioFormat.Encoding.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static javax.sound.sampled.AudioFormat.Encoding;

@RunWith(Parameterized.class)
public class RawHandlerStreamToAudioFormatTest {

  @Parameterized.Parameters(name = "{4}")
  public static List<Object[]> data() {
    return Arrays.asList(new Object[][] {
        {"ALAW", 48000, 1, 2, new AudioFormat(ALAW, 48000, 8, 2, 2, 48000, false)},
        {"ULAW", 48000, 1, 3, new AudioFormat(ULAW, 48000, 8, 3, 3, 48000, false)},

        {"PSD\u0008", 48000, 1, 4, new AudioFormat(PCM_SIGNED, 48000, 8, 4, 4, 48000, false)},
        {"\u0010DUP", 48000, 1, 6, new AudioFormat(PCM_UNSIGNED, 48000, 16, 6, 12, 48000, true)},
        {"PFD\u0020", 48000, 1, 8, new AudioFormat(PCM_FLOAT, 48000, 32, 8, 32, 48000, false)},
    });
  }

  final StreamHeaderPacket stream;
  final AudioFormat expected;

  public RawHandlerStreamToAudioFormatTest(String fourcc, int sampleRateNum, int sampleRateDenom,
      int channels, AudioFormat expected) {
    stream = new StreamHeaderPacket();
    stream.type = StreamHeaderPacket.AUDIO;
    stream.fourcc = fourcc.getBytes(StandardCharsets.ISO_8859_1);
    stream.sampleRate = Fraction.getFraction(sampleRateNum, sampleRateDenom);
    stream.channels = channels;

    this.expected = expected;
  }

  @Test
  public void testStreamToAudioFormat() {
    AudioFormat format = RawHandler.streamToAudioFormat(stream);

    // Compare strings since AudioFormat does not have a good equals(..) method.
    assertThat(format.toString(), equalTo(expected.toString()));
  }
}
