package net.bramp.ffmpeg.gson;

import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.Immutable;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Maps Enums to lowercase strings.
 *
 * <p>Adapted from: <a href=
 * "https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/TypeAdapterFactory.html"
 * >TypeAdapterFactory</a>
 */
@Immutable
public class LowercaseEnumTypeAdapterFactory implements TypeAdapterFactory {

  @Immutable
  private static class MyTypeAdapter<T> extends TypeAdapter<T> {

    // T is a Enum, thus immutable, however, we can't enforce that type due to the
    // TypeAdapterFactory interface
    @SuppressWarnings("Immutable")
    private final ImmutableMap<String, T> lowercaseToEnum;

    public MyTypeAdapter(Map<String, T> lowercaseToEnum) {
      this.lowercaseToEnum = ImmutableMap.copyOf(lowercaseToEnum);
    }

    @Override
    public void write(JsonWriter out, T value) throws IOException {
      checkNotNull(out);

      if (value == null) {
        out.nullValue();
      } else {
        out.value(toLowercase(value));
      }
    }

    @Override
    public T read(JsonReader reader) throws IOException {
      checkNotNull(reader);

      if (reader.peek() == JsonToken.NULL) {
        reader.nextNull();
        return null;
      }
      return lowercaseToEnum.get(reader.nextString());
    }
  }

  @CheckReturnValue
  @Override
  @SuppressWarnings("unchecked")
  public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
    checkNotNull(type);

    Class<T> rawType = (Class<T>) type.getRawType();
    if (!rawType.isEnum()) {
      return null;
    }

    // Setup mapping of consts
    final Map<String, T> lowercaseToEnum = new HashMap<>();
    for (T constant : rawType.getEnumConstants()) {
      lowercaseToEnum.put(toLowercase(constant), constant);
    }

    return new MyTypeAdapter<T>(lowercaseToEnum);
  }

  @CheckReturnValue
  private static String toLowercase(@Nonnull Object o) {
    return checkNotNull(o).toString().toLowerCase(Locale.UK);
  }
}
