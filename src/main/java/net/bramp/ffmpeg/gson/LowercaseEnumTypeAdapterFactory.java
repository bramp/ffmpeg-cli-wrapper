package net.bramp.ffmpeg.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Taken from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/TypeAdapterFactory.html
 */
public class LowercaseEnumTypeAdapterFactory implements TypeAdapterFactory {
  public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
    Class<T> rawType = (Class<T>) type.getRawType();
    if (!rawType.isEnum()) {
      return null;
    }

    // Setup mapping of consts
    final Map<String, T> lowercaseToConstant = new HashMap<String, T>();
    for (T constant : rawType.getEnumConstants()) {
      lowercaseToConstant.put(toLowercase(constant), constant);
    }

    return new TypeAdapter<T>() {
      public void write(JsonWriter out, T value) throws IOException {
        if (value == null) {
          out.nullValue();
        } else {
          out.value(toLowercase(value));
        }
      }

      public T read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
          reader.nextNull();
          return null;
        }
        return lowercaseToConstant.get(reader.nextString());
      }
    };
  }

  private String toLowercase(Object o) {
    return o.toString().toLowerCase(Locale.UK);
  }
}
