package net.bramp.ffmpeg.probe;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.bramp.ffmpeg.adapter.BooleanTypeAdapter;

/** Represents the AV_DISPOSITION_* fields */
@SuppressFBWarnings(
    value = {"UUF_UNUSED_PUBLIC_OR_PROTECTED_FIELD"},
    justification = "POJO objects where the fields are populated by gson")
public class FFmpegDisposition {
  @SerializedName("default")
  @JsonAdapter(BooleanTypeAdapter.class)
  public boolean _default;

  @JsonAdapter(BooleanTypeAdapter.class)
  public boolean dub;

  @JsonAdapter(BooleanTypeAdapter.class)
  public boolean original;

  @JsonAdapter(BooleanTypeAdapter.class)
  public boolean comment;

  @JsonAdapter(BooleanTypeAdapter.class)
  public boolean lyrics;

  @JsonAdapter(BooleanTypeAdapter.class)
  public boolean karaoke;

  @JsonAdapter(BooleanTypeAdapter.class)
  public boolean forced;

  @JsonAdapter(BooleanTypeAdapter.class)
  public boolean hearing_impaired;

  @JsonAdapter(BooleanTypeAdapter.class)
  public boolean visual_impaired;

  @JsonAdapter(BooleanTypeAdapter.class)
  public boolean clean_effects;

  @JsonAdapter(BooleanTypeAdapter.class)
  public boolean attached_pic;

  @JsonAdapter(BooleanTypeAdapter.class)
  public boolean timed_thumbnails;

  @JsonAdapter(BooleanTypeAdapter.class)
  public boolean non_diegetic;

  @JsonAdapter(BooleanTypeAdapter.class)
  public boolean captions;

  @JsonAdapter(BooleanTypeAdapter.class)
  public boolean descriptions;

  @JsonAdapter(BooleanTypeAdapter.class)
  public boolean metadata;

  @JsonAdapter(BooleanTypeAdapter.class)
  public boolean dependent;

  @JsonAdapter(BooleanTypeAdapter.class)
  public boolean still_image;

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

  public boolean isTimedThumbnails() {
    return timed_thumbnails;
  }

  public boolean isNonDiegetic() {
    return non_diegetic;
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

  public boolean isDependent() {
    return dependent;
  }

  public boolean isStillImage() {
    return still_image;
  }
}
