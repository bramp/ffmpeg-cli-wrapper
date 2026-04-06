package net.bramp.ffmpeg.builder;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static net.bramp.ffmpeg.FFmpegUtils.millisToSeconds;
import static net.bramp.ffmpeg.Preconditions.checkNotEmpty;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.CheckReturnValue;

/** Builder for FFmpeg HLS (HTTP Live Streaming) output arguments. */
public class FFmpegHlsOutputBuilder extends AbstractFFmpegOutputBuilder<FFmpegHlsOutputBuilder> {

  public Long hls_time;
  public String hls_segment_filename;
  public Long hls_init_time;
  public Integer hls_list_size;
  public String hls_base_url;
  public String hls_playlist_type;
  public String master_pl_name;
  public String var_stream_map;

  private final List<HlsVariant> variants = new ArrayList<>();

  protected FFmpegHlsOutputBuilder(FFmpegBuilder parent, String filename) {
    super(parent, filename);
    this.format = "hls";
  }

  @Override
  public FFmpegHlsOutputBuilder setFormat(String format) {
    if (format == null || !format.equals("hls")) {
      throw new IllegalArgumentException(
          "Format cannot be set to anything else except 'hls' for FFmpegHlsOutputBuilder");
    }
    super.setFormat(format);

    return this;
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
   * "file%03d.ts" segment files: file000.ts, file001.ts, file002.ts, etc.
   *
   * @param filename hls_segment_file_name to set
   * @return {@link FFmpegHlsOutputBuilder}
   */
  public FFmpegHlsOutputBuilder setHlsSegmentFileName(String filename) {
    this.hls_segment_filename = checkNotEmpty(filename, "filename must not be empty");

    return this;
  }

  /**
   * <strong>Segment will be cut on the next key frame after this time has passed on the first m3u8
   * list.</strong> <br>
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
   * <strong>Set the maximum number of playlist entries. If set to 0 the list file will contain all
   * the segments .</strong> <br>
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
   * <strong>Append baseurl to every entry in the playlist. Useful to generate playlists with
   * absolute paths. <br>
   * Note that the playlist sequence number must be unique for each segment and it is not to be
   * confused with the segment filename sequence number which can be cyclic, for example if the wrap
   * option is specified.</strong> <br>
   *
   * @param baseurl hls_base_url to set
   * @return {@link FFmpegHlsOutputBuilder}
   */
  public FFmpegHlsOutputBuilder setHlsBaseUrl(String baseurl) {
    this.hls_base_url = checkNotEmpty(baseurl, "baseurl must not be empty");

    return this;
  }

  /**
   * Set the playlist type.
   *
   * @param type The playlist type (e.g. "event", "vod")
   * @return {@link FFmpegHlsOutputBuilder}
   */
  public FFmpegHlsOutputBuilder setHlsPlaylistType(String type) {
    this.hls_playlist_type = checkNotEmpty(type, "type must not be empty");
    return this;
  }

  /**
   * Set the master playlist name.
   *
   * @param name The master playlist name
   * @return {@link FFmpegHlsOutputBuilder}
   */
  public FFmpegHlsOutputBuilder setMasterPlName(String name) {
    this.master_pl_name = checkNotEmpty(name, "name must not be empty");
    return this;
  }

  /**
   * Set the variant stream map string manually.
   *
   * <p>Prefer using {@link #addVariant(HlsVariant)} for a cleaner API.
   *
   * @param map The variant stream map (e.g. "v:0,a:0 v:1,a:1")
   * @return {@link FFmpegHlsOutputBuilder}
   */
  public FFmpegHlsOutputBuilder setVarStreamMap(String map) {
    this.var_stream_map = checkNotEmpty(map, "map must not be empty");
    return this;
  }

  /**
   * Adds an HLS variant to this output.
   *
   * @param variant The variant configuration.
   * @return {@link FFmpegHlsOutputBuilder}
   */
  public FFmpegHlsOutputBuilder addVariant(HlsVariant variant) {
    this.variants.add(checkNotNull(variant));
    return this;
  }

  @Override
  protected void addFormatArgs(ImmutableList.Builder<String> args) {
    super.addFormatArgs(args);
    if (hls_time != null) {
      args.add("-hls_time", millisToSeconds(hls_time));
    }

    if (!Strings.isNullOrEmpty(hls_segment_filename)) {
      args.add("-hls_segment_filename", hls_segment_filename);
    }

    if (hls_init_time != null) {
      args.add("-hls_init_time", millisToSeconds(hls_init_time));
    }

    if (hls_list_size != null) {
      args.add("-hls_list_size", hls_list_size.toString());
    }

    if (!Strings.isNullOrEmpty(hls_base_url)) {
      args.add("-hls_base_url", hls_base_url);
    }

    if (!Strings.isNullOrEmpty(hls_playlist_type)) {
      args.add("-hls_playlist_type", hls_playlist_type);
    }

    if (!Strings.isNullOrEmpty(master_pl_name)) {
      args.add("-master_pl_name", master_pl_name);
    }

    if (!Strings.isNullOrEmpty(var_stream_map)) {
      args.add("-var_stream_map", var_stream_map);
    } else if (!variants.isEmpty()) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < variants.size(); i++) {
        if (i > 0) {
          sb.append(" ");
        }
        sb.append(variants.get(i).toString());
      }
      args.add("-var_stream_map", sb.toString());
    }
  }

  @CheckReturnValue
  @Override
  protected FFmpegHlsOutputBuilder getThis() {
    return this;
  }
}
