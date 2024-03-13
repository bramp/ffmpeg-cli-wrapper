package net.bramp.ffmpeg.builder;

import net.bramp.ffmpeg.FFprobe;

import java.io.IOException;

public abstract class AbstractFFmpegStreamBuilderTest<T extends AbstractFFmpegStreamBuilderTest<T>> {
    protected final FFprobe ffprobe;

    protected AbstractFFmpegStreamBuilderTest() throws IOException {
        this.ffprobe = new FFprobe();
    }
}
