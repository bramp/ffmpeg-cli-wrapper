package net.bramp.ffmpeg.builder;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.fixtures.Samples;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static net.bramp.ffmpeg.builder.AbstractFFmpegStreamBuilder.DEVNULL;
import static org.junit.Assert.assertEquals;

public class FFmpegOutputBuilderTest extends AbstractFFmpegStreamBuilderTest<FFmpegOutputBuilderTest> {
    @Test
    public void testSetConstantRateFactorPass0() throws IOException {
        List<String> build = new FFmpegBuilder()
                .addInput(ffprobe.probe(Samples.big_buck_bunny_720p_1mb))
                .addOutput("output.mp4")
                .setConstantRateFactor(23)
                .setFormat("mp4")
                .build(0);

        assertEquals(args("-f", "mp4", "-crf", "23", "output.mp4"), build);
    }

    @SuppressWarnings("CheckReturnValue")
    @Test(expected = IllegalArgumentException.class)
    public void testSetConstantRateFactorPass1() throws IOException {
        new FFmpegBuilder()
                .addInput(ffprobe.probe(Samples.big_buck_bunny_720p_1mb))
                .addOutput("output.mp4")
                .setConstantRateFactor(23)
                .setFormat("mp4")
                .build(1);
    }

    @SuppressWarnings("CheckReturnValue")
    @Test(expected = IllegalArgumentException.class)
    public void testSetConstantRateFactorPass2() throws IOException {
        new FFmpegBuilder()
                .addInput(ffprobe.probe(Samples.big_buck_bunny_720p_1mb))
                .addOutput("output.mp4")
                .setConstantRateFactor(23)
                .setFormat("mp4")
                .build(2);
    }

    @Test
    public void setVideoBitRatePass0() throws IOException {
        List<String> build = passBuilder().setVideoBitRate(10000).build(0);

        assertEquals(args("-f", "mp4", "-b:v", "10000", "output.mp4"), build);
    }

    @Test
    public void setVideoBitRatePass1() throws IOException {
        List<String> build = passBuilder().setVideoBitRate(10000).build(1);

        assertEquals(args("-f", "mp4", "-b:v", "10000", "-an", DEVNULL), build);
    }

    @Test
    public void setVideoBitRatePass2() throws IOException {
        List<String> build = passBuilder().setVideoBitRate(10000).build(2);

        assertEquals(args("-f", "mp4", "-b:v", "10000", "output.mp4"), build);
    }

    @Test
    public void setVideoQualityPass0() throws IOException {
        List<String> build = passBuilder().setVideoQuality(10d).build(0);

        assertEquals(args("-f", "mp4", "-qscale:v", "10", "output.mp4"), build);
    }

    @Test
    public void setVideoQualityPass1() throws IOException {
        List<String> build = passBuilder().setVideoQuality(10d).build(1);

        assertEquals(args("-f", "mp4", "-qscale:v", "10", "-an", DEVNULL), build);
    }

    @Test
    public void setVideoQualityPass2() throws IOException {
        List<String> build = passBuilder().setVideoQuality(10d).build(2);

        assertEquals(args("-f", "mp4", "-qscale:v", "10", "output.mp4"), build);
    }

    @Test
    public void setVideoBitStreamFilterPass0() throws IOException {
        List<String> build = passBuilder().setVideoBitStreamFilter("test").build(0);

        assertEquals(args("-f", "mp4", "-bsf:v", "test", "output.mp4"), build);
    }

    @Test
    public void setVideoBitStreamFilterPass1() throws IOException {
        List<String> build = passBuilder().setVideoBitStreamFilter("test").build(1);

        assertEquals(args("-f", "mp4", "-bsf:v", "test", "-an", DEVNULL), build);
    }

    @Test
    public void setVideoBitStreamFilterPass2() throws IOException {
        List<String> build = passBuilder().setVideoBitStreamFilter("test").build(2);

        assertEquals(args("-f", "mp4", "-bsf:v", "test", "output.mp4"), build);
    }

    @Test
    public void setVideoPresetPass0() throws IOException {
        List<String> build = passBuilder().setVideoPreset("fast").build(0);

        assertEquals(args("-f", "mp4", "-vpre", "fast", "output.mp4"), build);
    }

    @Test
    public void setVideoPresetPass1() throws IOException {
        List<String> build = passBuilder().setVideoPreset("fast").build(1);

        assertEquals(args("-f", "mp4", "-vpre", "fast", "-an", DEVNULL), build);
    }

    @Test
    public void setVideoPresetPass2() throws IOException {
        List<String> build = passBuilder().setVideoPreset("fast").build(2);

        assertEquals(args("-f", "mp4", "-vpre", "fast", "output.mp4"), build);
    }

    @Test
    public void setVideoFilterPass0() throws IOException {
        List<String> build = passBuilder().setVideoFilter("test").build(0);

        assertEquals(args("-f", "mp4", "-vf", "test", "output.mp4"), build);
    }

    @Test
    public void setVideoFilterPass1() throws IOException {
        List<String> build = passBuilder().setVideoFilter("test").build(1);

        assertEquals(args("-f", "mp4", "-vf", "test", "-an", DEVNULL), build);
    }

    @Test
    public void setVideoFilterPass2() throws IOException {
        List<String> build = passBuilder().setVideoFilter("test").build(2);

        assertEquals(args("-f", "mp4", "-vf", "test", "output.mp4"), build);
    }

    @Test
    public void setAudioBitDepthPass0() throws IOException {
        List<String> build = passBuilder().setAudioBitDepth(FFmpeg.AUDIO_DEPTH_S16).build(0);

        assertEquals(args("-f", "mp4", "-sample_fmt", "s16", "output.mp4"), build);
    }

    @Test
    public void setAudioBitDepthPass1() throws IOException {
        List<String> build = passBuilder().setAudioBitDepth(FFmpeg.AUDIO_DEPTH_S16).build(1);

        assertEquals(args("-f", "mp4", "-an", DEVNULL), build);
    }

    @Test
    public void setAudioBitDepthPass2() throws IOException {
        List<String> build = passBuilder().setAudioBitDepth(FFmpeg.AUDIO_DEPTH_S16).build(2);

        assertEquals(args("-f", "mp4", "-sample_fmt", "s16", "output.mp4"), build);
    }

    @Test
    public void setAudioSampleFormatPass0() throws IOException {
        List<String> build = passBuilder().setAudioSampleFormat(FFmpeg.AUDIO_FORMAT_S16).build(0);

        assertEquals(args("-f", "mp4", "-sample_fmt", "s16", "output.mp4"), build);
    }

    @Test
    public void setAudioSampleFormatPass1() throws IOException {
        List<String> build = passBuilder().setAudioSampleFormat(FFmpeg.AUDIO_FORMAT_S16).build(1);

        assertEquals(args("-f", "mp4", "-an", DEVNULL), build);
    }

    @Test
    public void setAudioSampleFormatPass2() throws IOException {
        List<String> build = passBuilder().setAudioSampleFormat(FFmpeg.AUDIO_FORMAT_S16).build(2);

        assertEquals(args("-f", "mp4", "-sample_fmt", "s16", "output.mp4"), build);
    }

    @Test
    public void setAudioBitRatePass0() throws IOException {
        List<String> build = passBuilder().setAudioBitRate(10000).build(0);

        assertEquals(args("-f", "mp4", "-b:a", "10000", "output.mp4"), build);
    }

    @Test
    public void setAudioBitRatePass1() throws IOException {
        List<String> build = passBuilder().setAudioBitRate(10000).build(1);

        assertEquals(args("-f", "mp4", "-an", DEVNULL), build);
    }

    @Test
    public void setAudioBitRatePass2() throws IOException {
        List<String> build = passBuilder().setAudioBitRate(10000).build(2);

        assertEquals(args("-f", "mp4", "-b:a", "10000", "output.mp4"), build);
    }

    @Test
    public void setAudioQualityPass0() throws IOException {
        List<String> build = passBuilder().setAudioQuality(10d).build(0);

        assertEquals(args("-f", "mp4", "-qscale:a", "10", "output.mp4"), build);
    }

    @Test
    public void setAudioQualityPass1() throws IOException {
        List<String> build = passBuilder().setAudioQuality(10d).build(1);

        assertEquals(args("-f", "mp4", "-an", DEVNULL), build);
    }

    @Test
    public void setAudioQualityPass2() throws IOException {
        List<String> build = passBuilder().setAudioQuality(10d).build(2);

        assertEquals(args("-f", "mp4", "-qscale:a", "10", "output.mp4"), build);
    }

    @Test
    public void setAudioBitStreamFilterPass0() throws IOException {
        List<String> build = passBuilder().setAudioBitStreamFilter("filter").build(0);

        assertEquals(args("-f", "mp4", "-bsf:a", "filter", "output.mp4"), build);
    }

    @Test
    public void setAudioBitStreamFilterPass1() throws IOException {
        List<String> build = passBuilder().setAudioBitStreamFilter("filter").build(1);

        assertEquals(args("-f", "mp4", "-an", DEVNULL), build);
    }

    @Test
    public void setAudioBitStreamFilterPass2() throws IOException {
        List<String> build = passBuilder().setAudioBitStreamFilter("filter").build(2);

        assertEquals(args("-f", "mp4", "-bsf:a", "filter", "output.mp4"), build);
    }

    @Test
    public void setAudioFilterPass0() throws IOException {
        List<String> build = passBuilder().setAudioFilter("filter").build(0);

        assertEquals(args("-f", "mp4", "-af", "filter", "output.mp4"), build);
    }

    @Test
    public void setAudioFilterPass1() throws IOException {
        List<String> build = passBuilder().setAudioFilter("filter").build(1);

        assertEquals(args("-f", "mp4", "-an", DEVNULL), build);
    }

    @Test
    public void setAudioFilterPass2() throws IOException {
        List<String> build = passBuilder().setAudioFilter("filter").build(2);

        assertEquals(args("-f", "mp4", "-af", "filter", "output.mp4"), build);
    }

    @Test
    public void testPass1() throws IOException {
        FFmpegBuilder ffmpegBuilder = new FFmpegBuilder().addInput(ffprobe.probe(Samples.big_buck_bunny_720p_1mb));
        List<String> build = new FFmpegOutputBuilder().setFilename("output.mp4").setTargetSize(1).setFormat("mp4").build(ffmpegBuilder, 1);

        assertEquals(args("-f", "mp4", "-an", DEVNULL), build);
    }

    @Test
    public void testPass2() throws IOException {
        FFmpegBuilder ffmpegBuilder = new FFmpegBuilder().addInput(ffprobe.probe(Samples.big_buck_bunny_720p_1mb));
        List<String> build = new FFmpegOutputBuilder().setFilename("output.mp4").setTargetSize(1).setFormat("mp4").build(ffmpegBuilder, 2);

        assertEquals(args("-f", "mp4", "output.mp4"), build);
    }

    @SuppressWarnings("CheckReturnValue")
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testPass1FailsIfNoTargetSize() throws IOException {
        FFmpegBuilder ffmpegBuilder = new FFmpegBuilder().addInput(ffprobe.probe(Samples.big_buck_bunny_720p_1mb));
        List<String> build = new FFmpegOutputBuilder().setFilename("output.mp4").setFormat("mp4").build(ffmpegBuilder, 1);

        assertEquals(args("output.mp4"), build);
    }

    @SuppressWarnings("CheckReturnValue")
    @Test(expected = java.lang.IllegalStateException.class)
    public void testPass1FailsIfMultipleInputs() throws IOException {
      FFmpegBuilder ffmpegBuilder = new FFmpegBuilder()
        .addInput(ffprobe.probe(Samples.big_buck_bunny_720p_1mb))
        .addInput(ffprobe.probe(Samples.big_buck_bunny_720p_1mb));

      List<String> build = new FFmpegOutputBuilder().setFilename("output.mp4").setTargetSize(1).setFormat("mp4").build(ffmpegBuilder, 1);

      assertEquals(args("output.mp4"), build);
    }

    @SuppressWarnings("CheckReturnValue")
    @Test(expected = java.lang.IllegalStateException.class)
    public void testPass1FailsIfInputIsNotProbed() {
        FFmpegBuilder ffmpegBuilder = new FFmpegBuilder().addInput(Samples.big_buck_bunny_720p_1mb);
        List<String> build = new FFmpegOutputBuilder().setFilename("output.mp4").setTargetSize(1).setFormat("mp4").build(ffmpegBuilder, 1);

        assertEquals(args("output.mp4"), build);
    }

    @SuppressWarnings("CheckReturnValue")
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testPass1FailsIfFormatNotSet() throws IOException {
        FFmpegBuilder ffmpegBuilder = new FFmpegBuilder().addInput(ffprobe.probe(Samples.big_buck_bunny_720p_1mb));
        List<String> build = new FFmpegOutputBuilder().setFilename("output.mp4").setTargetSize(1).build(ffmpegBuilder, 1);

        assertEquals(args("output.mp4"), build);
    }

    @SuppressWarnings("CheckReturnValue")
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testPass2FailsIfNoTargetSize() throws IOException {
        FFmpegBuilder ffmpegBuilder = new FFmpegBuilder().addInput(ffprobe.probe(Samples.big_buck_bunny_720p_1mb));
        List<String> build = new FFmpegOutputBuilder().setFilename("output.mp4").setFormat("mp4").build(ffmpegBuilder, 2);

        assertEquals(args("output.mp4"), build);
    }

    @SuppressWarnings("CheckReturnValue")
    @Test(expected = java.lang.IllegalStateException.class)
    public void testPass2FailsIfMultipleInputs() throws IOException {
        FFmpegBuilder ffmpegBuilder = new FFmpegBuilder()
          .addInput(ffprobe.probe(Samples.big_buck_bunny_720p_1mb))
          .addInput(ffprobe.probe(Samples.big_buck_bunny_720p_1mb));

        List<String> build = new FFmpegOutputBuilder().setFilename("output.mp4").setTargetSize(1).setFormat("mp4").build(ffmpegBuilder, 2);

        assertEquals(args("output.mp4"), build);
    }

    @SuppressWarnings("CheckReturnValue")
    @Test(expected = java.lang.IllegalStateException.class)
    public void testPass2FailsIfInputIsNotProbed() {
        FFmpegBuilder ffmpegBuilder = new FFmpegBuilder().addInput(Samples.big_buck_bunny_720p_1mb);
        List<String> build = new FFmpegOutputBuilder().setFilename("output.mp4").setTargetSize(1).setFormat("mp4").build(ffmpegBuilder, 2);

        assertEquals(args("output.mp4"), build);
    }

    @SuppressWarnings("CheckReturnValue")
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testPass2FailsIfFormatNotSet() throws IOException {
        FFmpegBuilder ffmpegBuilder = new FFmpegBuilder().addInput(ffprobe.probe(Samples.big_buck_bunny_720p_1mb));
        List<String> build = new FFmpegOutputBuilder().setFilename("output.mp4").setTargetSize(1).build(ffmpegBuilder, 2);

        assertEquals(args("output.mp4"), build);
    }
}
