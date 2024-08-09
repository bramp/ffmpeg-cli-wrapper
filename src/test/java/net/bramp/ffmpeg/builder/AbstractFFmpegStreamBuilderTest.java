package net.bramp.ffmpeg.builder;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.math.Fraction;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public abstract class AbstractFFmpegStreamBuilderTest {
    protected abstract AbstractFFmpegStreamBuilder<?> getBuilder();

    protected abstract List<String> removeCommon(List<String> command);

    @Test
    public void testSetFormat() {
        List<String> command = getBuilder().setFormat("mp4").build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-f", "mp4")));
    }

    @Test
    public void testSetStartOffset() {
        List<String> command = getBuilder().setStartOffset(10, TimeUnit.SECONDS).build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-ss", "00:00:10")));
    }

    @Test
    public void testSetDuration() {
        List<String> command = getBuilder().setDuration(5, TimeUnit.SECONDS).build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-t", "00:00:05")));
    }

    @Test
    public void testAddMetaTagKeyValue() {
        List<String> command = getBuilder().addMetaTag("key", "value").build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-metadata", "key=value")));
    }

    @Test
    public void testAddMetaTagSpecKeyValue() {
        List<String> command = getBuilder().addMetaTag(MetadataSpecifier.stream(1), "key", "value").build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-metadata:s:1", "key=value")));
    }

    @Test
    public void testDisableAudio() {
        List<String> command = getBuilder().disableAudio().build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-an")));
    }

    @Test
    public void testSetAudioCodec() {
        List<String> command = getBuilder().setAudioCodec("acc").build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-acodec", "acc")));
    }

    @Test
    public void testSetAudioChannels() {
        List<String> command = getBuilder().setAudioChannels(7).build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-ac", "7")));
    }

    @Test
    public void testSetAudioSampleRate() {
        List<String> command = getBuilder().setAudioSampleRate(44100).build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-ar", "44100")));
    }

    @Test
    public void testSetAudioPreset() {
        List<String> command = getBuilder().setAudioPreset("ac").build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-apre", "ac")));
    }

    @Test
    public void testDisableVideo() {
        List<String> command = getBuilder().disableVideo().build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-vn")));
    }

    @Test
    public void testSetVideoCodec() {
        List<String> command = getBuilder().setVideoCodec("libx264").build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-vcodec", "libx264")));
    }

    @Test
    public void testSetVideoCopyInkf() {
        List<String> command = getBuilder().setVideoCopyInkf(true).build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-copyinkf")));
    }

    @Test
    public void testSetVideoFrameRateDouble() {
        List<String> command = getBuilder().setVideoFrameRate(1d / 60d).build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-r", "1/60")));
    }

    @Test
    public void testSetVideoFrameRateFraction() {
        List<String> command = getBuilder().setVideoFrameRate(Fraction.ONE_THIRD).build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-r", "1/3")));
    }

    @Test
    public void testSetVideoFrameRateFramesPer() {
        List<String> command = getBuilder().setVideoFrameRate(30, 1).build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-r", "30/1")));
    }

    @Test
    public void testSetVideoWidth() {
        List<String> command = getBuilder().setVideoWidth(1920).build(0);

        assertThat(removeCommon(command), is(ImmutableList.of()));
    }

    @Test
    public void testSetVideoHeight() {
        List<String> command = getBuilder().setVideoHeight(1080).build(0);

        assertThat(removeCommon(command), is(ImmutableList.of()));
    }

    @Test
    public void testSetVideoWidthAndHeight() {
        List<String> command = getBuilder().setVideoWidth(1920).setVideoHeight(1080).build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-s", "1920x1080")));
    }

    @Test
    public void testSetVideoSize() {
        List<String> command = getBuilder().setVideoResolution("1920x1080").build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-s", "1920x1080")));
    }

    @Test
    public void testSetVideoMovflags() {
        List<String> command = getBuilder().setVideoMovFlags("mov").build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-movflags", "mov")));
    }

    @Test
    public void testSetVideoFrames() {
        List<String> command = getBuilder().setFrames(30).build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-vframes", "30")));
    }

    @Test
    public void testSetVideoPixelFormat() {
        List<String> command = getBuilder().setVideoPixelFormat("yuv420").build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-pix_fmt", "yuv420")));
    }

    @Test
    public void testDisableSubtitle() {
        List<String> command = getBuilder().disableSubtitle().build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-sn")));
    }

    @Test
    public void testSetSubtitlePreset() {
        List<String> command = getBuilder().setSubtitlePreset("ac").build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-spre", "ac")));
    }

    @Test
    public void testSetSubtitleCodec() {
        List<String> command = getBuilder().setSubtitleCodec("vtt").build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-scodec", "vtt")));
    }

    @Test
    public void testSetPreset() {
        List<String> command = getBuilder().setPreset("pre").build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-preset", "pre")));
    }

    @Test
    public void testSetPresetFilename() {
        List<String> command = getBuilder().setPresetFilename("pre.txt").build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-fpre", "pre.txt")));
    }

    @Test
    public void testSetStrict() {
        List<String> command = getBuilder().setStrict(FFmpegBuilder.Strict.STRICT).build(0);

        assertEquals("strict", command.get(command.indexOf("-strict") + 1));
    }
}
