package net.bramp.ffmpeg.builder;

import static com.google.common.base.Preconditions.checkArgument;
import static net.bramp.ffmpeg.Preconditions.checkNotEmpty;

public class FFmpegHlsOutputBuilder extends FFmpegOutputBuilder{

    protected FFmpegHlsOutputBuilder(FFmpegBuilder parent, String filename) {
        super(parent, filename);
    }



    /**
     * Duration Examples <br>
     * Default :  "2" <br>
     * The following examples are all valid time duration:
     * <br>
     * "55" -> 55 seconds
     * <br>
     * "0.2" -> 0.2 seconds
     * <br>
     * "200ms" -> 200 milliseconds, that’s 0.2s
     * <br>
     * "200000us" -> 200000 microseconds, that’s 0.2s
     * <br>
     * "12:03:45" -> 12 hours, 03 minutes and 45 seconds
     * <br>
     * "23.189" -> 23.189 seconds
     *
     * @param duration hls_time to set
     * @return {@link FFmpegHlsOutputBuilder}
     */
    public FFmpegHlsOutputBuilder setHlsTime(String duration){
        duration = checkNotEmpty(duration, "duration must not be empty");
        extra_args.add("-hls_time");
        extra_args.add(duration);
        return this;
    }

    /**
     * hls_segment_filename Examples <br>
     * <br>
     * "file%03d.ts" ->  segment files: file000.ts, file001.ts, file002.ts, etc.
     *
     * @param filename hls_segment_file_name to set
     * @return {@link FFmpegHlsOutputBuilder}
     */
    public FFmpegHlsOutputBuilder setHlsSegmentFileName(String filename){
        filename = checkNotEmpty(filename, "filename must not be empty");
        extra_args.add("-hls_segment_filename");
        extra_args.add(filename);
        return this;
    }

    /**
     * <strong>Segment will be cut on the next key frame after this time has passed on the first m3u8 list.</strong> <br>
     * Duration Examples <br>
     * Default :  "0" <br>
     * The following examples are all valid time duration:
     * <br>
     * "55" -> 55 seconds
     * <br>
     * "0.2" -> 0.2 seconds
     * <br>
     * "200ms" -> 200 milliseconds, that’s 0.2s
     * <br>
     * "200000us" -> 200000 microseconds, that’s 0.2s
     * <br>
     * "12:03:45" -> 12 hours, 03 minutes and 45 seconds
     * <br>
     * "23.189" -> 23.189 seconds
     *
     * @param duration hls_init_time to set
     * @return {@link FFmpegHlsOutputBuilder}
     */
    public FFmpegHlsOutputBuilder setHlsInitTime(String duration){
        duration = checkNotEmpty(duration, "duration must not be empty");
        extra_args.add("-hls_init_time");
        extra_args.add(duration);
        return this;
    }

    /**
     * <strong>Set the maximum number of playlist entries. If set to 0 the list file will contain all the segments .</strong> <br>
     * Default value is 5 <br>
     * @param size hls_time to set
     * @return {@link FFmpegHlsOutputBuilder}
     */
    public FFmpegHlsOutputBuilder setHlsListSize(int size){
        checkArgument(size>0, "Size cannot be less than 0.");
        extra_args.add("-hls_list_size");
        extra_args.add(Integer.toString(size));
        return this;
    }

    /**
     * <strong>Append baseurl to every entry in the playlist.
     * Useful to generate playlists with absolute paths. <br>
     * Note that the playlist sequence number must be unique for each segment and it is not to be confused with the segment filename sequence number which can be cyclic, for example if the wrap option is specified.</strong> <br>
     * @param baseurl hls_base_url to set
     * @return {@link FFmpegHlsOutputBuilder}
     */
    public FFmpegHlsOutputBuilder setHlsBaseUrl(String baseurl){
        baseurl = checkNotEmpty(baseurl, "baseurl must not be empty");
        extra_args.add("-hls_base_url");
        extra_args.add(baseurl);
        return this;
    }



}
