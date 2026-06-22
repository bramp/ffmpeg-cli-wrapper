package net.bramp.ffmpeg.progress;

import static com.google.common.base.Preconditions.checkNotNull;
import static net.bramp.ffmpeg.FFmpegUtils.fromTimecode;

import com.google.common.base.MoreObjects;
import java.util.Objects;
import javax.annotation.CheckReturnValue;
import net.bramp.ffmpeg.FFmpegUtils;
import org.apache.commons.lang3.math.Fraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Change to be immutable
/** Represents progress data reported by FFmpeg during encoding. */
public class Progress {
  static final Logger logger = LoggerFactory.getLogger(Progress.class);

  /** Enum representing the status of FFmpeg progress updates. */
  public enum Status {
    CONTINUE("continue"),
    END("end");

    private final String status;

    Status(String status) {
      this.status = status;
    }

    @Override
    public String toString() {
      return status;
    }

    /**
     * Returns the canonical status for this String or throws a IllegalArgumentException.
     *
     * @param status the status to convert to a Status enum.
     * @return the Status enum.
     * @throws IllegalArgumentException if the status is unknown.
     */
    public static Status of(String status) {
      for (Status s : Status.values()) {
        if (status.equalsIgnoreCase(s.status)) {
          return s;
        }
      }

      throw new IllegalArgumentException("invalid progress status '" + status + "'");
    }
  }

  /** The frame number being processed. */
  public long frame = 0;

  /** The current frames per second. */
  public Fraction fps = Fraction.ZERO;

  /** Current bitrate. */
  public long bitrate = 0;

  /** Output file size (in bytes). */
  public long total_size = 0;

  // TODO: Change this to a java.time.Duration
  /** Output time (in nanoseconds). */
  public long out_time_ns = 0;

  public long dup_frames = 0;

  /** Number of frames dropped. */
  public long drop_frames = 0;

  /** Speed of transcoding. 1 means realtime, 2 means twice realtime. */
  public float speed = 0;

  /** Current status, can be one of "continue", or "end". */
  public Status status = null;

  /** Constructs a default empty progress instance. */
  public Progress() {
    // Nothing
  }

  /** Constructs a progress instance with the specified values. */
  public Progress(
      long frame,
      float fps,
      long bitrate,
      long total_size,
      long out_time_ns,
      long dup_frames,
      long drop_frames,
      float speed,
      Status status) {
    this.frame = frame;
    this.fps = Fraction.getFraction(fps);
    this.bitrate = bitrate;
    this.total_size = total_size;
    this.out_time_ns = out_time_ns;
    this.dup_frames = dup_frames;
    this.drop_frames = drop_frames;
    this.speed = speed;
    this.status = status;
  }

  /**
   * Parses values from the line, into this object.
   *
   * <p>The value options are defined in ffmpeg.c's print_report function
   * https://github.com/FFmpeg/FFmpeg/blob/master/fftools/ffmpeg.c
   *
   * @param line A single line of output from ffmpeg
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
        try {
          frame = Long.parseLong(value);
        } catch (NumberFormatException e) {
          logger.warn("Failed to parse frame: {}", value);
          frame = -1;
        }
        return false;

      case "fps":
        try {
          fps = Fraction.getFraction(value);
        } catch (NumberFormatException e) {
          logger.warn("Failed to parse fps: {}", value);
          fps = null;
        }
        return false;

      case "bitrate":
        try {
          bitrate = FFmpegUtils.parseBitrate(value);
        } catch (IllegalArgumentException e) {
          logger.warn("Failed to parse bitrate: {}", value);
          bitrate = -1;
        }
        return false;

      case "total_size":
        try {
          total_size = Long.parseLong(value);
        } catch (NumberFormatException e) {
          logger.warn("Failed to parse total_size: {}", value);
          total_size = -1;
        }
        return false;

      case "out_time_ms":
        // This is a duplicate of the "out_time" field, but expressed as a int instead of string.
        // Note this value is in microseconds, not milliseconds, and is based on AV_TIME_BASE which
        // could change.
        // out_time_ns = Long.parseLong(value) * 1000;
        return false;

      case "out_time_us":
        return false;

      case "out_time":
        try {
          out_time_ns = fromTimecode(value);
        } catch (IllegalArgumentException e) {
          logger.warn("Failed to parse out_time: {}", value);
          out_time_ns = -1;
        }
        return false;

      case "dup_frames":
        try {
          dup_frames = Long.parseLong(value);
        } catch (NumberFormatException e) {
          logger.warn("Failed to parse dup_frames: {}", value);
          dup_frames = -1;
        }
        return false;

      case "drop_frames":
        try {
          drop_frames = Long.parseLong(value);
        } catch (NumberFormatException e) {
          logger.warn("Failed to parse drop_frames: {}", value);
          drop_frames = -1;
        }
        return false;

      case "speed":
        try {
          speed = Float.parseFloat(value.replace("x", ""));
        } catch (NumberFormatException e) {
          logger.warn("Failed to parse speed: {}", value);
          speed = -1;
      }
        return false;

      case "progress":
        // TODO: After "end" stream is closed
        try {
          status = Status.of(value);
        } catch (IllegalArgumentException e) {
          logger.warn("Failed to parse progress status: {}", value);
        }
        return true; // The status field is always last in the record

      default:
        if (key.startsWith("stream_")) {
          // TODO: handle stream_0_0_q=0.0:
          // stream_%d_%d_q= file_index, index, quality
          // stream_%d_%d_psnr_%c=%2.2f, file_index, index, type{Y, U, V}, quality // Enable with
          // AV_CODEC_FLAG_PSNR
          // stream_%d_%d_psnr_all
        } else {
          logger.warn("Skipping unhandled key: {} = {}", key, value);
        }

        return false; // Either way, not supported
    }
  }

  @CheckReturnValue
  public boolean isEnd() {
    return status == Status.END;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Progress)) {
      return false;
    }

    Progress progress1 = (Progress) o;
    return frame == progress1.frame
        && bitrate == progress1.bitrate
        && total_size == progress1.total_size
        && out_time_ns == progress1.out_time_ns
        && dup_frames == progress1.dup_frames
        && drop_frames == progress1.drop_frames
        && Float.compare(progress1.speed, speed) == 0
        && Objects.equals(fps, progress1.fps)
        && Objects.equals(status, progress1.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        frame, fps, bitrate, total_size, out_time_ns, dup_frames, drop_frames, speed, status);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("frame", frame)
        .add("fps", fps)
        .add("bitrate", bitrate)
        .add("total_size", total_size)
        .add("out_time_ns", out_time_ns)
        .add("dup_frames", dup_frames)
        .add("drop_frames", drop_frames)
        .add("speed", speed)
        .add("status", status)
        .toString();
  }

  public long getFrame() {
    return frame;
  }

  public Fraction getFps() {
    return fps;
  }

  public long getBitrate() {
    return bitrate;
  }

  public long getTotalSize() {
    return total_size;
  }

  public long getOutTimeNs() {
    return out_time_ns;
  }

  public long getDupFrames() {
    return dup_frames;
  }

  public long getDropFrames() {
    return drop_frames;
  }

  public float getSpeed() {
    return speed;
  }

  public Status getStatus() {
    return status;
  }
}
