package net.bramp.ffmpeg.adapter;

import com.google.gson.*;
import net.bramp.ffmpeg.probe.FFmpegFrame;
import net.bramp.ffmpeg.probe.FFmpegFrameOrPacket;
import net.bramp.ffmpeg.probe.FFmpegPacket;

import java.lang.reflect.Type;

public class FFmpegPacketsAndFramesAdapter implements JsonDeserializer<FFmpegFrameOrPacket> {
  @Override
  public FFmpegFrameOrPacket deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
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
