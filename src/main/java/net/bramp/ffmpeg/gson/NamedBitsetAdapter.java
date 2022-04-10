package net.bramp.ffmpeg.gson;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Optional;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Converts a json object which represents a set of booleans. For example:
 *
 * <pre>
 * <code>
 * public class Set {
 *   public boolean a = true;
 *   public boolean b = false;
 *   public int c = 1;
 *   public int d = 0;
 * }
 * </code>
 * </pre>
 *
 * is turned into:
 *
 * <pre>
 * {
 *   "a": true,
 *   "b": false,
 *   "c": true,
 *   "d": false
 * }
 * </pre>
 */
public class NamedBitsetAdapter<T> extends TypeAdapter<T> {

  final Class<T> clazz;

  public NamedBitsetAdapter(Class<T> clazz) {
    this.clazz = checkNotNull(clazz);
  }

  protected Optional<Boolean> readBoolean(JsonReader reader) throws IOException {
    JsonToken next = reader.peek();
    switch (next) {
      case BOOLEAN:
        return Optional.of(reader.nextBoolean());
      case NUMBER:
        return Optional.of(reader.nextInt() != 0);
      default:
        reader.skipValue();
        return Optional.absent();
    }
  }

  protected void setField(T target, String name, boolean value) throws IllegalAccessException {
    try {
      Field f = clazz.getField(name);
      if ((boolean.class.equals(f.getType()))) {
        f.setBoolean(target, value);
      } else if (int.class.equals(f.getType())) {
        f.setInt(target, value ? 1 : 0);
      }

    } catch (NoSuchFieldException e) {
      // Just continue
    }
  }

  @Override
  public T read(JsonReader reader) throws IOException {

    JsonToken next = reader.peek();

    if (next == JsonToken.NULL) {
      reader.nextNull();
      return null;
    }

    try {
      T obj = clazz.getDeclaredConstructor().newInstance();
      reader.beginObject();

      next = reader.peek();
      while (next != JsonToken.END_OBJECT) {
        String name = reader.nextName();
        Optional<Boolean> value = readBoolean(reader);

        if (value.isPresent()) {
          setField(obj, name, value.get());
        }

        next = reader.peek();
      }

      reader.endObject();
      return obj;

    } catch (InstantiationException
        | IllegalAccessException
        | NoSuchMethodException
        | InvocationTargetException e) {
      throw new IOException("Reflection error", e);
    }
  }

  @Override
  public void write(JsonWriter writer, T value) throws IOException {

    if (value == null) {
      writer.nullValue();
      return;
    }

    assert value.getClass().equals(clazz);

    writer.beginObject();
    for (Field f : clazz.getFields()) {
      try {
        boolean b;
        if (boolean.class.equals(f.getType())) {
          b = f.getBoolean(value);
        } else if (int.class.equals(f.getType())) {
          b = f.getInt(value) != 0;
        } else {
          continue;
        }

        writer.name(f.getName());
        writer.value(b);

      } catch (IllegalAccessException e) {
        throw new IOException("Reflection error", e);
      }
    }
    writer.endObject();
  }
}
