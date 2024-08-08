package net.bramp.ffmpeg.builder;

import com.google.common.collect.ImmutableList;
import net.bramp.ffmpeg.options.EncodingOptions;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

import javax.annotation.CheckReturnValue;
import java.util.List;

public abstract class AbstractFFmpegInputBuilder<T extends AbstractFFmpegInputBuilder<T>> extends AbstractFFmpegStreamBuilder<T> {
    private final FFmpegProbeResult probeResult;

    public AbstractFFmpegInputBuilder() {
        this(null, null);
    }

    public AbstractFFmpegInputBuilder(FFmpegBuilder parent) {
        this(parent, null);
    }

    public AbstractFFmpegInputBuilder(FFmpegBuilder parent, FFmpegProbeResult probeResult) {
        // TODO

        super(parent, "ignored");
        this.probeResult = probeResult;
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
    protected List<String> build(FFmpegBuilder parent, int pass) {
        ImmutableList.Builder<String> args = new ImmutableList.Builder<>();

        addGlobalFlags(parent, args);

        // TODO: Handle input options

        args.addAll(buildInputString());

        return args.build();
    }

    protected abstract List<String> buildInputString();
}
