package net.bramp.ffmpeg.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.bramp.ffmpeg.probe.FFmpegFrame;
import net.bramp.ffmpeg.probe.FFmpegFrameOrPacket;
import net.bramp.ffmpeg.probe.FFmpegPacket;

/** Gson type adapter that deserializes FFmpeg packets and frames from JSON. */
public class FFmpegPacketsAndFramesAdapter implements JsonDeserializer<FFmpegFrameOrPacket> {
  @Override
  public FFmpegFrameOrPacket deserialize(
      JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {
    if (jsonElement instanceof JsonObject) {
      final String objectType = ((JsonObject) jsonElement).get("type").getAsString();

      if (objectType.equals("packet")) {
        return jsonDeserializationContext.deserialize(jsonElement, FFmpegPacket.class);
      } else {
        return jsonDeserializationContext.deserialize(jsonElement, FFmpegFrame.class);
      }
    }

    return null;
  }
}
