package net.bramp.ffmpeg.nut;

public interface NutReaderListener {
  // Returns when a new frame is found.
  // References to the frame must not be kept, and any required fields must be copied out.
  void frame(Frame frame);
}
