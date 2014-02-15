package net.bramp.commons.lang3.math.gson;

import java.io.IOException;

import org.apache.commons.lang3.math.Fraction;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * GSON TypeAdapter for Apache Commons Math Fraction Object
 *
 * @author bramp
 *
 */
public class FractionAdapter extends TypeAdapter<Fraction> {

	public Fraction read(JsonReader reader) throws IOException {
		JsonToken next = reader.peek();

		if (next == JsonToken.NULL) {
			reader.nextNull();
			return null;
		}

		if (next == JsonToken.NUMBER) {
			return Fraction.getFraction( reader.nextDouble() );
		}

		String fraction = reader.nextString();

		// Ambiguous as to what 0/0 is, but FFmpeg seems to think it's zero
		if (fraction.equals("0/0"))
			return Fraction.ZERO;

		return Fraction.getFraction( fraction );
	}

	public void write(JsonWriter writer, Fraction value) throws IOException {
		if (value == null) {
			writer.nullValue();
			return;
		}
		writer.value( value.toProperString() );
	}
}
