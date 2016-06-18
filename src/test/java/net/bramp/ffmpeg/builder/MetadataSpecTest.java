package net.bramp.ffmpeg.builder;

import org.junit.Test;

import static net.bramp.ffmpeg.builder.MetadataSpec.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class MetadataSpecTest {

  @Test
  public void testMetaSpec() {
    assertThat(global().toString(), is("g"));
    assertThat(chapter(1).toString(), is("c:1"));
    assertThat(program(1).toString(), is("p:1"));
    assertThat(stream(1).toString(), is("s:1"));
  }
}
