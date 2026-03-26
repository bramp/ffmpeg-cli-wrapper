package net.bramp.ffmpeg.adapter;

import com.google.gson.*;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import net.bramp.ffmpeg.probe.FFmpegStream;

public class FFmpegStreamSideDataAdapter implements JsonDeserializer<FFmpegStream.SideData> {
  @Override
  public FFmpegStream.SideData deserialize(
      JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {
    if (!(jsonElement instanceof JsonObject)) return null;
    try {
      Object sideData =
          Class.forName(FFmpegStream.SideData.class.getName()).getConstructor().newInstance();
      Field[] fields = Class.forName(FFmpegStream.SideData.class.getName()).getFields();
      for (Field field : fields) {
        String fieldName = field.getName();
        AnnotatedType annotatedType = field.getAnnotatedType();
        Object deserialize =
            jsonDeserializationContext.deserialize(
                ((JsonObject) jsonElement).get(fieldName), annotatedType.getType());
        field.set(sideData, deserialize);
      }
      return (FFmpegStream.SideData) sideData;
    } catch (Exception exception) {
      return null;
    }
  }
}
