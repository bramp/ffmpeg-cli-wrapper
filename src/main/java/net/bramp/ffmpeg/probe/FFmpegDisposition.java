package net.bramp.ffmpeg.probe;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/** Represents the AV_DISPOSITION_* fields */
@SuppressFBWarnings(
    value = {"UUF_UNUSED_PUBLIC_OR_PROTECTED_FIELD"},
    justification = "POJO objects where the fields are populated by gson")
public class FFmpegDisposition {
  /** @deprecated Use {@link #isDefault()} instead */
  @Deprecated
  public boolean _default;
  /** @deprecated Use {@link #isDub()} instead */
  @Deprecated
  public boolean dub;
  /** @deprecated Use {@link #isOriginal()} instead */
  @Deprecated
  public boolean original;
  /** @deprecated Use {@link #isComment()} instead */
  @Deprecated
  public boolean comment;
  /** @deprecated Use {@link #isLyrics()} instead */
  @Deprecated
  public boolean lyrics;
  /** @deprecated Use {@link #isKaraoke()} instead */
  @Deprecated
  public boolean karaoke;
  /** @deprecated Use {@link #isForced()} instead */
  @Deprecated
  public boolean forced;
  /** @deprecated Use {@link #isHearingImpaired()} instead */
  @Deprecated
  public boolean hearing_impaired;
  /** @deprecated Use {@link #isVisualImpaired()} instead */
  @Deprecated
  public boolean visual_impaired;
  /** @deprecated Use {@link #isCleanEffects()} instead */
  @Deprecated
  public boolean clean_effects;
  /** @deprecated Use {@link #isAttachedPic()} instead */
  @Deprecated
  public boolean attached_pic;
  /** @deprecated Use {@link #isCaptions()} ·()} instead */
  @Deprecated
  public boolean captions;
  /** @deprecated Use {@link #isDescriptions()} instead */
  @Deprecated
  public boolean descriptions;
  /** @deprecated Use {@link #isMetadata()} instead */
  @Deprecated
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
