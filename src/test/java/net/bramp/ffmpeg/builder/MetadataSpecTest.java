package net.bramp.ffmpeg.builder;

import static net.bramp.ffmpeg.builder.MetadataSpecifier.*;
import static net.bramp.ffmpeg.builder.StreamSpecifier.id;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class MetadataSpecTest {

  @Test
  public void testMetaSpec() {
    assertThat(global().spec(), is("g"));
    assertThat(chapter(1).spec(), is("c:1"));
    assertThat(program(1).spec(), is("p:1"));
    assertThat(stream(1).spec(), is("s:1"));
    assertThat(stream(id(1)).spec(), is("s:i:1"));
  }
}
