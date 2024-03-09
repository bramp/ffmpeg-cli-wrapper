package net.bramp.ffmpeg.info;

import net.bramp.ffmpeg.probe.FFmpegStream;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.*;

public class FilterPattern {
    /** Indicates weather this pattern represents a source or a sink and therefore has no other options */
    private final boolean sinkOrSource;

    /** Indicates weather this pattern accepts a variable number of streams */
    private final boolean variableStreams;

    /** Contains a pattern matching the stream types supported */
    private final List<FFmpegStream.CodecType> streams;

    public FilterPattern(String pattern) {
        this.sinkOrSource = pattern.contains("|");
        this.variableStreams = pattern.contains("N");
        List<FFmpegStream.CodecType> streams = new ArrayList<>();

        for (char c : pattern.toCharArray()) {
            if (c == '|' || c == 'N') {
                // These symbols are handled separately
                continue;
            }
            if (c == 'A') {
                streams.add(FFmpegStream.CodecType.AUDIO);
            } else if (c == 'V') {
                streams.add(FFmpegStream.CodecType.VIDEO);
            } else {
                throw new IllegalStateException("Unsupported character in filter pattern " + c);
            }
        }

        this.streams = Collections.unmodifiableList(streams);
    }

    public boolean isSinkOrSource() {
        return sinkOrSource;
    }

    public boolean isVariableStreams() {
        return variableStreams;
    }

    public List<FFmpegStream.CodecType> getStreams() {
        return streams;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        if (isSinkOrSource()) {
            return "|";
        }

        if (isVariableStreams()) {
            return "N";
        }

        return Arrays.toString(this.streams.toArray());
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
