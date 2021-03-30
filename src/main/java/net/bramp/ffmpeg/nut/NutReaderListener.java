package net.bramp.ffmpeg.nut;

public interface NutReaderListener {

  /**
   * Executes when a new stream is found.
   *
   * @param stream The stream
   */
  void stream(Stream stream);

  /**
   * Executes when a new frame is found.
   *
   * @param frame A single Frame
   */
  void frame(Frame frame);
}
