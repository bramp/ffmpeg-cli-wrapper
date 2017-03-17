package net.bramp.ffmpeg.info;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Information about supported Codecs
 *
 * @author bramp
 */
@Immutable
public class Codec {

  enum Type {
    VIDEO,
    AUDIO,
    SUBTITLE
  }

  final String name;
  final String longName;

  /** Can I decode with this codec */
  final boolean canDecode;

  /** Can I encode with this codec */
  final boolean canEncode;

  /** What type of codec is this */
  final Type type;

  /**
   * @param name short codec name
   * @param longName long codec name
   * @param flags is expected to be in the following format:
   *     <pre>
   * D..... = Decoding supported
   * .E.... = Encoding supported
   * ..V... = Video codec
   * ..A... = Audio codec
   * ..S... = Subtitle codec
   * ...S.. = Supports draw_horiz_band
   * ....D. = Supports direct rendering method 1
   * .....T = Supports weird frame truncation
   * </pre>
   */
  public Codec(String name, String longName, String flags) {
    this.name = Preconditions.checkNotNull(name).trim();
    this.longName = Preconditions.checkNotNull(longName).trim();

    Preconditions.checkNotNull(flags);
    Preconditions.checkArgument(flags.length() == 6, "Format flags is invalid '{}'", flags);
    this.canDecode = flags.charAt(0) == 'D';
    this.canEncode = flags.charAt(1) == 'E';

    switch (flags.charAt(2)) {
      case 'V':
        this.type = Type.VIDEO;
        break;
      case 'A':
        this.type = Type.AUDIO;
        break;
      case 'S':
        this.type = Type.SUBTITLE;
        break;
      default:
        throw new IllegalArgumentException("Invalid codec type '" + flags.charAt(3) + "'");
    }

    // TODO There are more flags to parse
  }

  @Override
  public String toString() {
    return name + " " + longName;
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  public String getName() {
    return name;
  }

  public String getLongName() {
    return longName;
  }

  public boolean getCanDecode() {
    return canDecode;
  }

  public boolean getCanEncode() {
    return canEncode;
  }

  public Type getType() {
    return type;
  }
}
