package net.bramp.ffmpeg.builder;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public abstract class AbstractFFmpegOutputBuilderTest extends AbstractFFmpegStreamBuilderTest {

    @Override
    protected abstract AbstractFFmpegOutputBuilder<?> getBuilder();

    @Test
    public void testConstantRateFactor() {
        List<String> command = getBuilder()
                .setConstantRateFactor(5)
                .build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-crf", "5")));
    }

    @Test
    public void testAudioSampleFormat() {
        List<String> command = getBuilder()
                .setAudioSampleFormat("asf")
                .build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-sample_fmt", "asf")));
    }

    @Test
    public void testAudioBitrate() {
        List<String> command = getBuilder()
                .setAudioBitRate(1_000)
                .build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-b:a", "1000")));
    }

    @Test
    public void testAudioQuality() {
        List<String> command = getBuilder()
                .setAudioQuality(5)
                .build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-qscale:a", "5")));
    }

    @Test
    public void testSetAudioBitStreamFilter() {
        List<String> command = getBuilder()
                .setAudioBitStreamFilter("filter")
                .build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-bsf:a", "filter")));
    }

    @Test
    public void testSetVideoBitRate() {
        List<String> command = getBuilder()
                .setVideoBitRate(1_000_000)
                .build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-b:v", "1000000")));
    }

    @Test
    public void testSetVideoQuality() {
        List<String> command = getBuilder()
                .setVideoQuality(20)
                .build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-qscale:v", "20")));
    }

    @Test
    public void testSetVideoPreset() {
        List<String> command = getBuilder()
                .setVideoPreset("main")
                .build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-vpre", "main")));
    }

    @Test
    public void testSetVideoFilter() {
        List<String> command = getBuilder()
                .setVideoFilter("filter")
                .build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-vf", "filter")));
    }

    @Test
    public void testSetVideoBitStreamFilter() {
        List<String> command = getBuilder()
                .setVideoBitStreamFilter("bit-stream-filter")
                .build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-bsf:v", "bit-stream-filter")));
    }

    @Test
    public void testSetComplexFilter() {
        List<String> command = getBuilder()
                .setComplexFilter("complex-filter")
                .build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-filter_complex", "complex-filter")));
    }

    @Test
    public void testSetBFrames() {
        List<String> command = getBuilder()
                .setBFrames(2)
                .build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-bf", "2")));
    }
}