package net.bramp.ffmpeg.info;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public class Format {
	final String shortName;
	final String longName;

	public Format(String shortName, String longName) {
		this(shortName, longName, 0);
	}

	public Format(String shortName, String longName, int flags) {
		this.shortName = Preconditions.checkNotNull(shortName);
		this.longName  = Preconditions.checkNotNull(longName);
	}

	@Override
	public String toString() {
		return shortName + " " + longName;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Format)) {
			return false;
		}

		Format that = (Format) obj;
		return Objects.equal(this.shortName, that.shortName) && 
			Objects.equal(this.longName, that.longName);
	}
}
