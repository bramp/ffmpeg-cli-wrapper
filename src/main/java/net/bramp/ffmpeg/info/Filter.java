package net.bramp.ffmpeg.info;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Filter {
    /** Is timeline editing supported */
    private final boolean timelineSupported;

    /** Is slice based multi-threading supported */
    private final boolean sliceThreading;

    /** Are there command line options */
    private final boolean commandSupport;

    /** The filters name */
    private final String name;

    /** The input filter pattern */
    private final FilterPattern inputPattern;

    /** The output filter pattern */
    private final FilterPattern outputPattern;

    /** A short description of the filter */
    private final String description;

    public Filter(boolean timelineSupported, boolean sliceThreading, boolean commandSupport, String name, FilterPattern inputPattern, FilterPattern outputPattern, String description) {
        this.timelineSupported = timelineSupported;
        this.sliceThreading = sliceThreading;
        this.commandSupport = commandSupport;
        this.name = name;
        this.inputPattern = inputPattern;
        this.outputPattern = outputPattern;
        this.description = description;
    }

    public boolean isTimelineSupported() {
        return timelineSupported;
    }

    public boolean isSliceThreading() {
        return sliceThreading;
    }

    public boolean isCommandSupport() {
        return commandSupport;
    }

    public String getName() {
        return name;
    }

    public FilterPattern getInputPattern() {
        return inputPattern;
    }

    public FilterPattern getOutputPattern() {
        return outputPattern;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
