package net.bramp.ffmpeg.info;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Information about supported Format
 *
 * @author bramp
 */
public class Format {
  final String name;
  final String longName;

  final boolean canDemux;
  final boolean canMux;

  /**
   * @param name short format name
   * @param longName long format name
   * @param flags is expected to be in the following format:
   *     <pre>
   * D. = Demuxing supported
   * .E = Muxing supported
   * </pre>
   */
  public Format(String name, String longName, String flags) {
    this.name = Preconditions.checkNotNull(name).trim();
    this.longName = Preconditions.checkNotNull(longName).trim();

    Preconditions.checkNotNull(flags);
    Preconditions.checkArgument(flags.length() == 2, "Format flags is invalid '{}'", flags);
    canDemux = flags.charAt(0) == 'D';
    canMux = flags.charAt(1) == 'E';
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

  public boolean getCanDemux() {
    return canDemux;
  }

  public boolean getCanMux() {
    return canMux;
  }
}
