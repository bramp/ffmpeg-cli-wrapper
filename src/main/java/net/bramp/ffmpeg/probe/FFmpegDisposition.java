package net.bramp.ffmpeg.probe;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/** Represents the AV_DISPOSITION_* fields */
@SuppressFBWarnings(
    value = {"UUF_UNUSED_PUBLIC_OR_PROTECTED_FIELD"},
    justification = "POJO objects where the fields are populated by gson")
public class FFmpegDisposition {
  public boolean _default;
  public boolean dub;
  public boolean original;
  public boolean comment;
  public boolean lyrics;
  public boolean karaoke;
  public boolean forced;
  public boolean hearing_impaired;
  public boolean visual_impaired;
  public boolean clean_effects;
  public boolean attached_pic;
  public boolean captions;
  public boolean descriptions;
  public boolean metadata;

  public boolean isDefault() {
    return _default;
  }

  public boolean isDub() {
    return dub;
  }

  public boolean isOriginal() {
    return original;
  }

  public boolean isComment() {
    return comment;
  }

  public boolean isLyrics() {
    return lyrics;
  }

  public boolean isKaraoke() {
    return karaoke;
  }

  public boolean isForced() {
    return forced;
  }

  public boolean isHearingImpaired() {
    return hearing_impaired;
  }

  public boolean isVisualImpaired() {
    return visual_impaired;
  }

  public boolean isCleanEffects() {
    return clean_effects;
  }

  public boolean isAttachedPic() {
    return attached_pic;
  }

  public boolean isCaptions() {
    return captions;
  }

  public boolean isDescriptions() {
    return descriptions;
  }

  public boolean isMetadata() {
    return metadata;
  }
}
