package net.bramp.ffmpeg.progress;

import com.google.common.base.MoreObjects;
import net.bramp.ffmpeg.FFmpegUtils;
import org.apache.commons.lang3.math.Fraction;

import static com.google.common.base.Preconditions.checkNotNull;

public class Progress {
  long frame;
  Fraction fps;

  // TODO stream_0_0_q=0.0

  long bitrate;
  long total_size; // =48
  long out_time_ms; // =512000 //out_time=00:00:00.512000

  long dup_frames = 0;
  long drop_frames = 0;
  float speed; // =1.01x
  String progress;

  /**
   * Parses values from the line, into this object.
   *
   * @param line
   * @return true if the record is finished
   */
  protected boolean parseLine(String line) {
    line = line.trim();
    if (line.isEmpty()) {
      return false; // Skip empty lines
    }

    final String[] args = line.split("=", 2);
    if (args.length != 2) {
      // invalid argument, so skip
      return false;
    }

    final String key = checkNotNull(args[0]);
    final String value = checkNotNull(args[1]);

    switch (key) {
      case "frame":
        frame = Long.parseLong(value);
        return false;
      case "fps":
        fps = Fraction.getFraction(value);
        return false;
      case "bitrate":
        bitrate = FFmpegUtils.parseBitrate(value);
        return false;
      case "total_size":
        total_size = Long.parseLong(value);
        return false;
      case "out_time_ms":
        out_time_ms = Long.parseLong(value);
        return false;
      case "out_time":
        // There is also out_time_ms, so we ignore out_time.
        // TODO maybe in the future actually parse out_time, if a out_time_ms wasn't provided.
        return false;

      case "dup_frames":
        dup_frames = Long.parseLong(value);
        return false;
      case "drop_frames":
        drop_frames = Long.parseLong(value);
        return false;
      case "speed":
        speed = Float.parseFloat(value.replace("x", ""));
        return false;
      case "progress":
        progress = value;
        return true;

      default:
        System.out.println("Unsupported key " + line); // TODO Fix
        return false; // Ignore for the moment
    }
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        // @formatter:off
        .add("frame", frame)
        .add("fps", fps)
        .add("bitrate", bitrate)
        .add("total_size", total_size)
        .add("out_time_ms", out_time_ms)
        .add("dup_frames", dup_frames)
        .add("drop_frames", drop_frames)
        .add("speed", speed)
        .add("progress", progress)
        // @formatter:on
        .toString();
  }
}
