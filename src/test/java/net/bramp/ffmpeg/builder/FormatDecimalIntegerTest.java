package net.bramp.ffmpeg.builder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class FormatDecimalIntegerTest {

  @Parameterized.Parameters(name = "{0}")
  public static List<Object[]> data() {
    return Arrays.asList(
        new Object[][] {
          {0.0, "0"},
          {1.0, "1"},
          {-1.0, "-1"},
          {0.1, "0.1"},
          {1.1, "1.1"},
          {1.10, "1.1"},
          {1.001, "1.001"},
          {100, "100"},
          {100.01, "100.01"},
        });
  }

  final double input;
  final String expected;

  public FormatDecimalIntegerTest(double input, String expected) {
    this.input = input;
    this.expected = expected;
  }

  @Test
  public void formatDecimalInteger() throws Exception {
    String got = FFmpegOutputBuilder.formatDecimalInteger(input);

    assertThat(got, equalTo(expected));
  }
}
