package net.bramp.ffmpeg.builder;

public abstract class AbstractFFmpegInputBuilderTest extends AbstractFFmpegStreamBuilderTest {
    @Override
    protected abstract AbstractFFmpegInputBuilder<?> getBuilder();
}
