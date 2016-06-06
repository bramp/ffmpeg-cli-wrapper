package net.bramp.commons.lang3.math.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.apache.commons.lang3.math.Fraction;

import java.io.IOException;

/**
 * GSON TypeAdapter for Apache Commons Math Fraction Object
 *
 * @author bramp
 *
 */
public class FractionAdapter extends TypeAdapter<Fraction> {

  /**
   * If set, 0/0 returns this value, instead of throwing a ArithmeticException
   */
  private final Fraction zeroByZero;

  /**
   * If set, N/0 returns this value, instead of throwing a ArithmeticException
   */
  private final Fraction divideByZero;

  public FractionAdapter() {
    this(Fraction.ZERO, Fraction.ZERO);
  }

  private FractionAdapter(Fraction zeroByZero, Fraction divideByZero) {
    this.zeroByZero = zeroByZero;
    this.divideByZero = divideByZero;
  }

  public Fraction read(JsonReader reader) throws IOException {
    JsonToken next = reader.peek();

    if (next == JsonToken.NULL) {
      reader.nextNull();
      return null;
    }

    if (next == JsonToken.NUMBER) {
      return Fraction.getFraction(reader.nextDouble());
    }

    String fraction = reader.nextString().trim();

    // Ambiguous as to what 0/0 is, but FFmpeg seems to think it's zero
    if (zeroByZero != null && fraction.equals("0/0"))
      return zeroByZero;

    // Another edge cases is invalid files sometimes output 1/0.
    if (divideByZero != null && fraction.endsWith("/0"))
      return divideByZero;

    return Fraction.getFraction(fraction);
  }

  public void write(JsonWriter writer, Fraction value) throws IOException {
    if (value == null) {
      writer.nullValue();
      return;
    }
    writer.value(value.toProperString());
  }
}
