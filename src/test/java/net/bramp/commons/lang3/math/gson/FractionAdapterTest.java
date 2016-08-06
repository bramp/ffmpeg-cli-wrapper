package net.bramp.commons.lang3.math.gson;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.math.Fraction;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;


public class FractionAdapterTest {
  static Gson gson;

  @BeforeClass
  public static void setupGson() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(Fraction.class, new FractionAdapter());
    gson = builder.create();
  }

  private static class TestData {
    final String s;
    final Fraction f;

    public TestData(String s, Fraction f) {
      this.s = s;
      this.f = f;
    }
  }

  final static List<TestData> readTests = ImmutableList.of(new TestData("null", null),

  new TestData("1", Fraction.getFraction(1, 1)), new TestData("1.0", Fraction.getFraction(1, 1)),
      new TestData("2", Fraction.getFraction(2, 1)),
      new TestData("0.5", Fraction.getFraction(1, 2)),

      new TestData("\"1\"", Fraction.getFraction(1, 1)),
      new TestData("\"1.0\"", Fraction.getFraction(1, 1)),
      new TestData("\"2\"", Fraction.getFraction(2, 1)),
      new TestData("\"0.5\"", Fraction.getFraction(1, 2)),
      new TestData("\"1/2\"", Fraction.getFraction(1, 2)),
      new TestData("\"1 1/2\"", Fraction.getFraction(1, 1, 2)));

  // Divide by zero
  final static List<TestData> zerosTests = ImmutableList.of(new TestData("\"0/0\"", Fraction.ZERO),
      new TestData("\"1/0\"", Fraction.ZERO));


  final static List<TestData> writeTests = ImmutableList.of(new TestData("0", Fraction.ZERO),
      new TestData("1", Fraction.getFraction(1, 1)), new TestData("2", Fraction.getFraction(2, 1)),
      new TestData("1/2", Fraction.getFraction(1, 2)),
      new TestData("1 1/2", Fraction.getFraction(1, 1, 2)));

  @Test
  public void testRead() {
    for (TestData test : readTests) {
      Fraction f = gson.fromJson(test.s, Fraction.class);
      assertThat(f, equalTo(test.f));
    }
  }

  @Test
  public void testZerosRead() {
    for (TestData test : zerosTests) {
      Fraction f = gson.fromJson(test.s, Fraction.class);
      assertThat(f, equalTo(test.f));
    }
  }

  @Test
  public void testWrites() {
    for (TestData test : writeTests) {
      String json = gson.toJson(test.f);
      assertThat(json, equalTo('"' + test.s + '"'));
    }
  }

  @Test
  public void testWriteNull() {
    String json = gson.toJson(null);
    assertThat(json, equalTo("null"));
  }
}
