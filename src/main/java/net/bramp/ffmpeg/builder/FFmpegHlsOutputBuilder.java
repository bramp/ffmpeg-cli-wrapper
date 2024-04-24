package net.bramp.ffmpeg.builder;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import javax.annotation.CheckReturnValue;
import static com.google.common.base.Preconditions.*;
import static net.bramp.ffmpeg.FFmpegUtils.toTimecode;
import static net.bramp.ffmpeg.Preconditions.checkNotEmpty;
import java.util.concurrent.TimeUnit;

public class FFmpegHlsOutputBuilder extends AbstractFFmpegOutputBuilder<FFmpegHlsOutputBuilder> {

    public Long hls_time;
    public String hls_segment_filename;
    public Long hls_init_time;
    public Integer hls_list_size;
    public String hls_base_url;


    protected FFmpegHlsOutputBuilder(FFmpegBuilder parent, String filename) {
        super(parent, filename);
    }


    /**
     * Set the target segment length. Default value is 2 seconds.
     *
     * @param duration hls_time to set
     * @param units The units the offset is in
     * @return {@link FFmpegHlsOutputBuilder}
     */
    public FFmpegHlsOutputBuilder setHlsTime(long duration, TimeUnit units) {
        checkNotNull(units);
        this.hls_time = units.toMillis(duration);

        return this;
    }

    /**
     * hls_segment_filename Examples <br>
     * <br>
     * "file%03d.ts"  segment files: file000.ts, file001.ts, file002.ts, etc.
     *
     * @param filename hls_segment_file_name to set
     * @return {@link FFmpegHlsOutputBuilder}
     */
    public FFmpegHlsOutputBuilder setHlsSegmentFileName(String filename) {
        this.hls_segment_filename = checkNotEmpty(filename, "filename must not be empty");

        return this;
    }

    /**
     * <strong>Segment will be cut on the next key frame after this time has passed on the first m3u8 list.</strong> <br>
     *
     * @param duration hls_init_time to set
     * @param units The units the offset is in
     * @return {@link FFmpegHlsOutputBuilder}
     */
    public FFmpegHlsOutputBuilder setHlsInitTime(long duration, TimeUnit units) {
        checkNotNull(units);
        this.hls_init_time = units.toMillis(duration);

        return this;
    }

    /**
     * <strong>Set the maximum number of playlist entries. If set to 0 the list file will contain all the segments .</strong> <br>
     * Default value is 5 <br>
     *
     * @param size hls_time to set
     * @return {@link FFmpegHlsOutputBuilder}
     */
    public FFmpegHlsOutputBuilder setHlsListSize(int size) {
        checkArgument(size >= 0, "Size cannot be less than 0.");
        this.hls_list_size = size;

        return this;
    }

    /**
     * <strong>Append baseurl to every entry in the playlist.
     * Useful to generate playlists with absolute paths. <br>
     * Note that the playlist sequence number must be unique for each segment and it is not to be confused with the segment filename sequence number which can be cyclic, for example if the wrap option is specified.</strong> <br>
     *
     * @param baseurl hls_base_url to set
     * @return {@link FFmpegHlsOutputBuilder}
     */
    public FFmpegHlsOutputBuilder setHlsBaseUrl(String baseurl) {
        this.hls_base_url = checkNotEmpty(baseurl, "baseurl must not be empty");

        return this;
    }

    @Override
    protected void addFormatArgs(ImmutableList.Builder<String> args) {
        super.addFormatArgs(args);
        if (hls_time != null) {
            args.add("-hls_time", toTimecode(hls_time, TimeUnit.MILLISECONDS));
        }

        if (!Strings.isNullOrEmpty(hls_segment_filename)) {
            args.add("-hls_segment_filename", hls_segment_filename);
        }

        if (hls_init_time != null) {
            args.add("-hls_init_time",toTimecode(hls_init_time, TimeUnit.MILLISECONDS));
        }

        if (hls_list_size != null) {
            args.add("-hls_list_size",hls_list_size.toString());
        }

        if (!Strings.isNullOrEmpty(hls_base_url)){
            args.add("-hls_base_url",hls_base_url);
        }
    }

    @CheckReturnValue
    @Override
    protected FFmpegHlsOutputBuilder getThis() {
        return this;
    }
}

