package net.bramp.ffmpeg.builder;

import static net.bramp.ffmpeg.builder.StreamSpecifier.*;
import static net.bramp.ffmpeg.builder.StreamSpecifierType.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Test;

public class StreamSpecTest {

  @Test
  public void testStreamSpec() {
    assertThat(stream(1).spec(), is("1"));
    assertThat(stream(Video).spec(), is("v"));

    assertThat(stream(Video, 1).spec(), is("v:1"));
    assertThat(stream(PureVideo, 1).spec(), is("V:1"));
    assertThat(stream(Audio, 1).spec(), is("a:1"));
    assertThat(stream(Subtitle, 1).spec(), is("s:1"));
    assertThat(stream(Data, 1).spec(), is("d:1"));
    assertThat(stream(Attachment, 1).spec(), is("t:1"));

    assertThat(program(1).spec(), is("p:1"));
    assertThat(program(1, 2).spec(), is("p:1:2"));

    assertThat(id(1).spec(), is("i:1"));

    assertThat(tag("key").spec(), is("m:key"));
    assertThat(tag("key", "value").spec(), is("m:key:value"));
    assertThat(usable().spec(), is("u"));
  }
}
