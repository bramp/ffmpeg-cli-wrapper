package net.bramp.ffmpeg.builder;

import com.google.common.collect.ImmutableList;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FFmpegHlsOutputBuilderTest {
    public FFmpegHlsOutputBuilderTest() throws IOException {
    }

    @Test
    public void testAddHlsOutput() {
        List<String> args =
                new FFmpegBuilder()
                        .setInput("input")
                        .addHlsOutput("output.m3u8")
                        .setHlsTime(5, TimeUnit.MILLISECONDS)
                        .setHlsBaseUrl("test1234/")
                        .setHlsListSize(3)
                        .setHlsInitTime(3, TimeUnit.MILLISECONDS)
                        .setHlsSegmentFileName("file%03d.ts")
                        .done()
                        .build();
        assertEquals(
                args, ImmutableList.of("-y", "-v", "error", "-f", "hls", "-i", "input", "-hls_time", "00:00:00.005",
                        "-hls_segment_filename", "file%03d.ts", "-hls_init_time", "00:00:00.003",
                        "-hls_list_size", "3", "-hls_base_url", "test1234/", "output.m3u8"));
    }

    @Test
    public void mixedHlsAndDefault() {
        List<String> args =
                new FFmpegBuilder()
                        .setInput("input")
                        .addHlsOutput("output.m3u8")
                        .setHlsTime(5, TimeUnit.MILLISECONDS)
                        .setHlsBaseUrl("test1234/")
                        .setVideoBitRate(3)
                        .setVideoFilter("SDF")
                        .setHlsListSize(3)
                        .setHlsInitTime(3, TimeUnit.MILLISECONDS)
                        .setHlsSegmentFileName("file%03d.ts")
                        .done()
                        .build();
        assertEquals(
                args, ImmutableList.of("-y", "-v", "error", "-f", "hls", "-i", "input", "-hls_time", "00:00:00.005",
                        "-hls_segment_filename", "file%03d.ts", "-hls_init_time", "00:00:00.003",
                        "-hls_list_size", "3", "-hls_base_url", "test1234/", "output.m3u8"));
    }


}