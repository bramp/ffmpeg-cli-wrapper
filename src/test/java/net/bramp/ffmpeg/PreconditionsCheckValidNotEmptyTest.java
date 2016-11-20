package net.bramp.ffmpeg;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;


@RunWith(Parameterized.class)
public class PreconditionsCheckValidNotEmptyTest {
  @Parameterized.Parameters(name = "{0}")
  public static List<String> data() {
    return Arrays.asList(
        "bob",
        " hello "
        );
  }

  private final String input;

  public PreconditionsCheckValidNotEmptyTest(String input) {
    this.input = input;
  }

  @Test
  public void testUri() {
    Preconditions.checkNotEmpty(input, "test must not throw exception");
  }
}
