package net.bramp.ffmpeg.builder;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import net.bramp.ffmpeg.options.EncodingOptions;
import org.apache.commons.lang3.SystemUtils;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static net.bramp.ffmpeg.Preconditions.checkNotEmpty;

public class FFmpegInputBuilder extends AbstractFFmpegStreamBuilder<FFmpegInputBuilder> {

    protected int thread_queue_size;
    protected int device_number;                //device number for multiple devices with the same name
    protected static final String DEVNULL = SystemUtils.IS_OS_WINDOWS ? "NUL" : "/dev/null";

    public FFmpegInputBuilder(FFmpegBuilder parent, String source) {
        super(parent);
        device_number = 0;
        this.input = source;
    }

    public FFmpegInputBuilder(FFmpegBuilder parent, String source, int device_number) {
        this(parent, source);
        this.device_number = device_number;
    }

    @Override
    protected FFmpegInputBuilder getThis() {
        return this;
    }

    //TO-DO: something here???
    @Override
    public EncodingOptions buildOptions() {
        return null;
    }

    public FFmpegInputBuilder setThreadQueueSize(int thread_queue_size){
        this.thread_queue_size = thread_queue_size;
        this.addExtraArgs("-thread_queue_size", String.valueOf(thread_queue_size));
        return this;
    }

    @Override
    protected List<String> build(FFmpegBuilder parent, int pass) {
        checkNotNull(parent);

        if (pass > 0) {
            // TODO Write a test for this:
            checkArgument(format != null, "Format must be specified when using two-pass");
        }

        ImmutableList.Builder<String> args = new ImmutableList.Builder<>();

        addGlobalFlags(parent, args);

        if (video_enabled) {
            addVideoFlags(parent, args);
        } else {
            args.add("-vn");
        }

        if (audio_enabled && pass != 1) {
            addAudioFlags(args);
        } else {
            args.add("-an");
        }

        if (subtitle_enabled) {
            if (!Strings.isNullOrEmpty(subtitle_preset)) {
                args.add("-spre", subtitle_preset);
            }
        } else {
            args.add("-sn");
        }

        args.addAll(extra_args);

        if (filename != null && uri != null) {
            throw new IllegalStateException("Only one of filename and uri can be set");
        }

        // Input
        if (pass == 1) {
            args.add(DEVNULL);
        } else if (input != null) {
            args.add("-video_device_number", "" + device_number);
            args.add("-i", input);
        } else {
            assert (false);
        }

        return args.build();
    }
}
