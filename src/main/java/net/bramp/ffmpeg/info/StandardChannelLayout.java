package net.bramp.ffmpeg.info;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class StandardChannelLayout implements ChannelLayout {
    private final String name;
    private final List<IndividualChannel> decomposition;

    public StandardChannelLayout(String name, List<IndividualChannel> decomposition) {
        this.name = name;
        this.decomposition = decomposition;
    }

    @Override
    public String getName() {
        return name;
    }

    public List<IndividualChannel> getDecomposition() {
        return decomposition;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
