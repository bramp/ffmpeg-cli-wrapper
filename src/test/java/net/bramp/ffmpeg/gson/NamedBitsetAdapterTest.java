package net.bramp.ffmpeg.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.StringReader;

import static com.nitorcreations.Matchers.reflectEquals;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;


public class NamedBitsetAdapterTest {

  public static class Set {
    public boolean a;
    public boolean b;
    public int c;
    public int d;
  }

  public static final Set testSet = testSet();
  public static final String testData = "{\"a\":true,\"b\":false,\"c\":true,\"d\":false}";

  protected static Set testSet() {
    Set s = new Set();
    {
      s.a = true;
      s.b = false;
      s.c = 1;
      s.d = 0;
      return s;
    }
  }

  static Gson gson;

  @BeforeClass
  public static void setupGson() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(Set.class, new NamedBitsetAdapter<>(Set.class));
    gson = builder.create();
  }

  @Test
  public void testRead() throws Exception {
    Set s = gson.fromJson(testData, Set.class);
    assertThat(s, reflectEquals(testSet));

  }

  @Test
  public void testWrite() throws Exception {
    String json = gson.toJson(testSet);
    assertThat(json, equalTo(testData));
  }
}
