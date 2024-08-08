package net.bramp.ffmpeg.builder;

import net.bramp.ffmpeg.options.EncodingOptions;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FFmpegFileInputBuilder extends AbstractFFmpegInputBuilder<FFmpegFileInputBuilder> {
    private final String filename;

    public FFmpegFileInputBuilder(String filename) {
        this.filename = filename;
    }

    public FFmpegFileInputBuilder(FFmpegBuilder parent, String filename) {
        super(parent);
        this.filename = filename;
    }

    public FFmpegFileInputBuilder(FFmpegBuilder parent, String filename, FFmpegProbeResult result) {
        super(parent, result);
        this.filename = filename;

    }

    @Override
    protected List<String> buildInputString() {
        return Arrays.asList("-i", filename);
    }
}
