package net.bramp.ffmpeg.builder;

import com.google.common.collect.ImmutableList;
import net.bramp.ffmpeg.options.EncodingOptions;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

import javax.annotation.CheckReturnValue;
import java.util.List;

public abstract class AbstractFFmpegInputBuilder<T extends AbstractFFmpegInputBuilder<T>> extends AbstractFFmpegStreamBuilder<T> {
    private final FFmpegProbeResult probeResult;

    private boolean readAtNativeFrameRate;

    protected AbstractFFmpegInputBuilder(FFmpegBuilder parent, String filename) {
        this(parent, null, filename);
    }

    protected AbstractFFmpegInputBuilder(FFmpegBuilder parent, FFmpegProbeResult probeResult, String filename) {
        super(parent, filename);
        this.probeResult = probeResult;
    }

    public T readAtNativeFrameRate() {
        this.readAtNativeFrameRate = true;
        return getThis();
    }

    public FFmpegProbeResult getProbeResult() {
        return probeResult;
    }

    @Override
    @CheckReturnValue
    @SuppressWarnings("unchecked")
    protected T getThis() {
        return (T) this;
    }

    @Override
    public EncodingOptions buildOptions() {
        return null;
    }

    @Override
    protected void addGlobalFlags(FFmpegBuilder parent, ImmutableList.Builder<String> args) {
        if (this.readAtNativeFrameRate) {
            args.add("-re");
        }

        super.addGlobalFlags(parent, args);
    }
}