package net.bramp.ffmpeg.builder;

import com.google.common.base.Ascii;

public enum Strict {
    VERY, // strictly conform to an older more strict version of the specifications or reference
    // software
    STRICT, // strictly conform to all the things in the specificiations no matter what consequences
    NORMAL, // normal
    UNOFFICIAL, // allow unofficial extensions
    EXPERIMENTAL;

    @Override
    public String toString() {
        // ffmpeg command line requires these options in lower case
        return Ascii.toLowerCase(name());
    }
}
