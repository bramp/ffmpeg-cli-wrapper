package net.bramp.ffmpeg.probe;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings(
  value = {"UUF_UNUSED_PUBLIC_OR_PROTECTED_FIELD", "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"},
  justification = "POJO objects where the fields are populated by gson"
)
public class FFmpegAudioFrame extends FFmpegFrame {
  public String sample_fmt;
  public int nb_samples;
  public int channels;
  public String channel_layout;
}
