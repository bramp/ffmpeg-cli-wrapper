package net.bramp.ffmpeg.gson;

import com.google.gson.*;
import net.bramp.ffmpeg.probe.FFmpegAudioFrame;
import net.bramp.ffmpeg.probe.FFmpegFrame;
import net.bramp.ffmpeg.probe.FFmpegVideoFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

public class FFmpegFrameDeserializer implements JsonDeserializer<FFmpegFrame> {

  static final Logger LOG = LoggerFactory.getLogger(FFmpegFrameDeserializer.class);

  private static final String MEDIA_TYPE = "media_type";
  private static final String VIDEO = "video";
  private static final String AUDIO = "audio";

  @Override
  public FFmpegFrame deserialize(JsonElement json, Type type, JsonDeserializationContext ctx)
      throws JsonParseException {

    JsonObject obj = json.getAsJsonObject();
    if (obj.isJsonNull()) {
      return null;
    }

    String mediaType = obj.get(MEDIA_TYPE).getAsString();
    switch (mediaType) {
      case VIDEO:
        return ctx.deserialize(json, FFmpegVideoFrame.class);
      case AUDIO:
        return ctx.deserialize(json, FFmpegAudioFrame.class);
      default:
        LOG.warn("Failed to deserialize frame with media_type of {}", mediaType);
    }

    return null;
  }
}
