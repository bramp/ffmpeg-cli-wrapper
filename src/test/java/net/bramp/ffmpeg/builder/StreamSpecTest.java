package net.bramp.ffmpeg.builder;

import static net.bramp.ffmpeg.builder.MetadataSpec.stream;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class StreamSpecTest {

  @Test
  public void testStreamSpec() {
    assertThat(stream(1).toString(), is("1"));
    assertThat(stream(VIDEO, 1).toString(), is("v:1"));

    assertThat(chapter(1).toString(), is("c:1"));
    assertThat(program(1).toString(), is("p:1"));
  }

  addMetaTag("key", "value", );
  addMetaTag("key", "value", chapter(1));
  addMetaTag("key", "value", program(1));
  addMetaTag("key", "value", stream(0));
  addMetaTag("key", "value", stream(VIDEO, 0);
  addMetaTag("key", "value", stream(AUDIO, 0);
  addMetaTag("key", "value", stream(id(0x502));
  addMetaTag("key", "value", stream(tag("key2", "value2"))); // This one is confusing, key2 refers to the output stream, and would apply key/value to any output stream already with key2/value2.
  */
}
}
