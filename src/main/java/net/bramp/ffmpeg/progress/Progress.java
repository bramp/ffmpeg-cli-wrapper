package net.bramp.ffmpeg.progress;

import com.google.common.base.MoreObjects;
import net.bramp.ffmpeg.FFmpegUtils;
import org.apache.commons.lang3.math.Fraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 */
// TODO Change to be immutable
public class Progress {

  final static Logger LOG = LoggerFactory.getLogger(Progress.class);

  public enum Status {
    CONTINUE("continue"), END("end");

    private final String status;

    Status(String status) {
      this.status = status;
    }

    public String toString() {
      return status;
    }

    public static Status of(String status) {
      for (Status s : Status.values()) {
        if (status.equalsIgnoreCase(s.status)) {
          return s;
        }
      }

      throw new IllegalArgumentException("invalid progress status '" + status + "'");
    }
  }

  /**
   * The frame number being processed
   */
  public long frame = 0;

  /**
   * The current frames per second
   */
  public Fraction fps = Fraction.ZERO;

  /**
   * Current bitrate
   */
  public long bitrate = 0;

  /**
   * Output file size
   */
  public long total_size = 0;

  /**
   * Output time (in microseconds)
   */
  // TODO Change this to a JodaTime
  public long out_time_us = 0;

  public long dup_frames = 0;

  /**
   * Number of frames dropped
   */
  public long drop_frames = 0;

  /**
   * Speed of transcoding. 1 means realtime, 2 means twice realtime.
   */
  public float speed = 0;

  /**
   * Current status, can be one of "continue", or "end"
   */
  public Status status = null;

  public Progress() {
    // Nothing
  }

  public Progress(long frame, float fps, long bitrate, long total_size, long out_time_us,
      long dup_frames, long drop_frames, float speed, Status status) {
    this.frame = frame;
    this.fps = Fraction.getFraction(fps);
    this.bitrate = bitrate;
    this.total_size = total_size;
    this.out_time_us = out_time_us;
    this.dup_frames = dup_frames;
    this.drop_frames = drop_frames;
    this.speed = speed;
    this.status = status;
  }

  /**
   * Parses values from the line, into this object.
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

      case "out_time_ms": // This is microseconds, not milliseconds
        // TODO Stop using out_time_ms, because it is based on AV_TIME_BASE which could change.
        // instead use "out_time", which is normalised, and outputted in standard units.
        out_time_us = Long.parseLong(value);
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
        // TODO After "end" stream is closed
        status = Status.of(value);
        return true; // The status field is always last in the record

      default:
        if (key.startsWith("stream_")) {
          // TODO handle stream_0_0_q=0.0:
          // stream_%d_%d_q= file_index, index, quality
          // stream_%d_%d_psnr_%c=%2.2f, file_index, index, type{Y, U, V}, quality // Enable with
          // AV_CODEC_FLAG_PSNR
          // stream_%d_%d_psnr_all
        } else {
          LOG.warn("skipping unhandled key: {} = {}", key, value);
        }

        return false; // Either way, not supported
    }
  }

  public boolean isEnd() {
    return status == Status.END;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Progress progress1 = (Progress) o;
    return frame == progress1.frame && bitrate == progress1.bitrate
        && total_size == progress1.total_size && out_time_us == progress1.out_time_us
        && dup_frames == progress1.dup_frames && drop_frames == progress1.drop_frames
        && Float.compare(progress1.speed, speed) == 0 && Objects.equals(fps, progress1.fps)
        && Objects.equals(status, progress1.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(frame, fps, bitrate, total_size, out_time_us, dup_frames, drop_frames,
        speed, status);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("frame", frame)
        .add("fps", fps)
        .add("bitrate", bitrate)
        .add("total_size", total_size)
        .add("out_time_us", out_time_us)
        .add("dup_frames", dup_frames)
        .add("drop_frames", drop_frames)
        .add("speed", speed)
        .add("status", status)
        .toString();
  }
}
