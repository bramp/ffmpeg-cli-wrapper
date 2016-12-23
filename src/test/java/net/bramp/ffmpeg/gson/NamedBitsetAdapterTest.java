package net.bramp.ffmpeg.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.nitorcreations.Matchers.reflectEquals;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class NamedBitsetAdapterTest {

  static class Set {
    public boolean a;
    public boolean b;
    public int c;
    public int d;
  }

  static class SetWithSkipField extends Set {
    public String e; // Since this is not a boolean or int, it gets skipped
  }

  private static final Set testSet = testSet();
  private static final String testData = "{\"a\":true,\"b\":false,\"c\":true,\"d\":false}";

  private static final SetWithSkipField testSetWithSkip = testSetWithSkip();
  private static final String testDataWithSkipField =
      "{\"e\":\"skip\",\"a\":true,\"b\":false,\"c\":true,\"d\":false}";

  private static Set testSet() {
    Set s = new Set();
    {
      s.a = true;
      s.b = false;
      s.c = 1;
      s.d = 0;
      return s;
    }
  }

  private static SetWithSkipField testSetWithSkip() {
    SetWithSkipField s = new SetWithSkipField();
    {
      s.a = true;
      s.b = false;
      s.c = 1;
      s.d = 0;
      s.e = "skip";
      return s;
    }
  }

  static Gson gson;

  @BeforeClass
  public static void setupGson() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(Set.class, new NamedBitsetAdapter<>(Set.class));
    builder.registerTypeAdapter(
        SetWithSkipField.class, new NamedBitsetAdapter<>(SetWithSkipField.class));
    gson = builder.create();
  }

  @Test
  public void testRead() throws Exception {
    Set s = gson.fromJson(testData, Set.class);
    assertThat(s, reflectEquals(testSet));
  }

  @Test
  public void testReadWithSkipField() throws Exception {
    Set s = gson.fromJson(testDataWithSkipField, Set.class);
    assertThat(s, reflectEquals(testSet));
  }

  @Test
  public void testWrite() throws Exception {
    // TODO This assumes that toJson will print the fields in particular order
    String json = gson.toJson(testSet);
    assertThat(json, equalTo(testData));
  }

  @Test
  public void testWriteWithSkipField() throws Exception {
    // TODO This assumes that toJson will print the fields in particular order
    String json = gson.toJson(testSetWithSkip);
    assertThat(json, equalTo(testData));
  }

  @Test
  public void testReadNull() throws Exception {
    Set s = gson.fromJson("null", Set.class);
    assertThat(s, equalTo(null));
  }

  @Test
  public void testWriteNull() throws Exception {
    String json = gson.toJson(null);
    assertThat(json, equalTo("null"));
  }
}
