package net.bramp.ffmpeg.progress;

import com.google.common.base.MoreObjects;
import net.bramp.ffmpeg.FFmpegUtils;
import org.apache.commons.lang3.math.Fraction;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * TODO Change to be immutable
 */
public class Progress {
  long frame = 0;
  Fraction fps = Fraction.ZERO;

  // TODO stream_0_0_q=0.0

  long bitrate = 0;
  long total_size = 0;
  long out_time_ms = 0;

  long dup_frames = 0;
  long drop_frames = 0;
  float speed = 0;
  String progress = "";

  public Progress() {
    // Nothing
  }

  public Progress(long frame, float fps, long bitrate, long total_size, long out_time_ms,
      long dup_frames, long drop_frames, float speed, String progress) {
    this.frame = frame;
    this.fps = Fraction.getFraction(fps);
    this.bitrate = bitrate;
    this.total_size = total_size;
    this.out_time_ms = out_time_ms;
    this.dup_frames = dup_frames;
    this.drop_frames = drop_frames;
    this.speed = speed;
    this.progress = progress;
  }

  /**
   * Parses values from the line, into this object.
   *
   * @param line
   * @return true if the record is finished
   */
  protected boolean parseLine(String line) {
    line = checkNotNull(line).trim();
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
        // TODO bitrate could be "N/A"
        bitrate = FFmpegUtils.parseBitrate(value);
        return false;
      case "total_size":
        // TODO could be "N/A"
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
        // TODO Could be "N/A"
        speed = Float.parseFloat(value.replace("x", ""));
        return false;
      case "progress":
        // TODO Is only "continue" or "end". Change to enum
        // TODO After "end" stream is closed
        progress = value;
        return true; // The progress field is always last

      default:
        // TODO
        // stream_%d_%d_q= file_index, index, quality
        // stream_%d_%d_psnr_%c=%2.2f, file_index, index, type{Y, U, V}, quality // Enable with
        // AV_CODEC_FLAG_PSNR
        // stream_%d_%d_psnr_all
        return false; // Ignore for the moment
    }
  }

  public boolean isEnd() {
    return Objects.equals(progress, "end");
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

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Progress progress1 = (Progress) o;
    return frame == progress1.frame && bitrate == progress1.bitrate
        && total_size == progress1.total_size && out_time_ms == progress1.out_time_ms
        && dup_frames == progress1.dup_frames && drop_frames == progress1.drop_frames
        && Float.compare(progress1.speed, speed) == 0 && Objects.equals(fps, progress1.fps)
        && Objects.equals(progress, progress1.progress);
  }

  @Override
  public int hashCode() {
    return Objects.hash(frame, fps, bitrate, total_size, out_time_ms, dup_frames, drop_frames,
        speed, progress);

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
