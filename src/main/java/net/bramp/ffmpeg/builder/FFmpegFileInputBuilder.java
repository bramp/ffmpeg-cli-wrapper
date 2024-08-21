package net.bramp.ffmpeg.builder;

import com.google.common.collect.ImmutableList;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

public class FFmpegFileInputBuilder extends AbstractFFmpegInputBuilder<FFmpegFileInputBuilder> {
    public FFmpegFileInputBuilder(FFmpegBuilder parent, String filename) {
        super(parent, filename);
    }

    public FFmpegFileInputBuilder(FFmpegBuilder parent, String filename, FFmpegProbeResult result) {
        super(parent, result, filename);

    }

    @Override
    protected void addSourceTarget(int pass, ImmutableList.Builder<String> args) {
        if (filename != null && uri != null) {
            throw new IllegalStateException("Only one of filename and uri can be set");
        }

        // Input
        if (filename != null) {
            args.add("-i", filename);
        } else if (uri != null) {
            args.add("-i", uri.toString());
        } else {
            assert false;
        }
    }
}
