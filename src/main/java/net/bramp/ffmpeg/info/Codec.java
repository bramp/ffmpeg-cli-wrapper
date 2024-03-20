package net.bramp.ffmpeg.info;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;
import net.bramp.ffmpeg.shared.CodecType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Information about supported Codecs
 *
 * @author bramp
 */
@Immutable
public class Codec {
  final String name;
  final String longName;

  /** Can I decode with this codec */
  final boolean canDecode;

  /** Can I encode with this codec */
  final boolean canEncode;

  /** What type of codec is this */
  final CodecType type;

  /** Intra frame only codec */
  final boolean intraFrameOnly;

  /** Codec supports lossy compression */
  final boolean lossyCompression;

  /** Codeco supports lessless compression */
  final boolean losslessCompression;

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
   * ..D... = Data codec
   * ..T... = Attachment codec
   * ...I.. = Intra frame-only codec
   * ....L. = Lossy compression
   * .....S = Lossless compression
   * </pre>
   */
  public Codec(String name, String longName, String flags) {
    this.name = Preconditions.checkNotNull(name).trim();
    this.longName = Preconditions.checkNotNull(longName).trim();

    Preconditions.checkNotNull(flags);
    Preconditions.checkArgument(flags.length() == 6, "Codec flags is invalid '%s'", flags);

    switch (flags.charAt(0)) {
      case 'D': this.canDecode = true; break;
      case '.': this.canDecode = false; break;
      default: throw new IllegalArgumentException("Invalid decoding value '" + flags.charAt(0) + "'");
    }

    switch (flags.charAt(1)) {
      case 'E': this.canEncode = true; break;
      case '.': this.canEncode = false; break;
      default: throw new IllegalArgumentException("Invalid encoding value '" + flags.charAt(1) + "'");
    }

    switch (flags.charAt(2)) {
      case 'V':
        this.type = CodecType.VIDEO;
        break;
      case 'A':
        this.type = CodecType.AUDIO;
        break;
      case 'S':
        this.type = CodecType.SUBTITLE;
        break;
      case 'D':
        this.type = CodecType.DATA;
        break;
      case 'T':
        this.type = CodecType.ATTACHMENT;
        break;
      default:
        throw new IllegalArgumentException("Invalid codec type '" + flags.charAt(2) + "'");
    }

    switch (flags.charAt(3)) {
      case 'I': this.intraFrameOnly = true; break;
      case '.': this.intraFrameOnly = false; break;
      default: throw new IllegalArgumentException("Invalid encoding value '" + flags.charAt(3) + "'");
    }

    switch (flags.charAt(4)) {
      case 'L': this.lossyCompression = true; break;
      case '.': this.lossyCompression = false; break;
      default: throw new IllegalArgumentException("Invalid lossy compression value '" + flags.charAt(4) + "'");
    }

    switch (flags.charAt(5)) {
      case 'S': this.losslessCompression = true; break;
      case '.': this.losslessCompression = false; break;
      default: throw new IllegalArgumentException("Invalid lossless compression value '" + flags.charAt(5) + "'");
    }

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

  public CodecType getType() {
    return type;
  }

  public boolean isIntraFrameOnly() {
    return intraFrameOnly;
  }

  public boolean supportsLossyCompression() {
    return lossyCompression;
  }

  public boolean supportsLosslessCompression() {
    return losslessCompression;
  }

}
