package net.bramp.ffmpeg.builder;

import com.google.common.collect.ImmutableList;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.fixtures.Samples;
import org.junit.Test;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FFmpegHlsOutputBuilderTest {
    public FFmpegHlsOutputBuilderTest() {
    }

    @Test
    public void testAddHlsOutput() {
        List<String> args =
                new FFmpegBuilder()
                        .setInput("input")
                        .done()
                        .addHlsOutput("output.m3u8")
                        .setHlsTime(5, TimeUnit.MILLISECONDS)
                        .setHlsBaseUrl("test1234/")
                        .setHlsListSize(3)
                        .setHlsInitTime(3, TimeUnit.MILLISECONDS)
                        .setHlsSegmentFileName("file%03d.ts")
                        .done()
                        .build();

        assertEquals(ImmutableList.of("-y", "-v", "error", "-i", "input", "-f", "hls", "-hls_time", "00:00:00.005",
                        "-hls_segment_filename", "file%03d.ts", "-hls_init_time", "00:00:00.003",
                        "-hls_list_size", "3", "-hls_base_url", "test1234/", "output.m3u8"), args);
    }

    @Test
    public void mixedHlsAndDefault() {
        List<String> args =
                new FFmpegBuilder()
                        .setInput("input")
                        .done()
                        .addHlsOutput("output.m3u8")
                        .setHlsTime(5, TimeUnit.MILLISECONDS)
                        .setHlsBaseUrl("test1234/")
                        .setVideoBitRate(3)
                        .setVideoFilter("TEST")
                        .setHlsListSize(3)
                        .setHlsInitTime(3, TimeUnit.MILLISECONDS)
                        .setHlsSegmentFileName("file%03d.ts")
                        .done()
                        .build();

        assertEquals(ImmutableList.of("-y", "-v", "error", "-i", "input","-f","hls","-b:v","3","-vf","TEST","-hls_time", "00:00:00.005",
                        "-hls_segment_filename", "file%03d.ts", "-hls_init_time", "00:00:00.003",
                        "-hls_list_size", "3", "-hls_base_url", "test1234/", "output.m3u8"), args);
    }

    @Test
    public void testConvertVideoToHls() throws IOException {
        Files.createDirectories(Paths.get("tmp/"));
        Files.deleteIfExists(Paths.get("tmp/output.m3u8"));
        Files.deleteIfExists(Paths.get("tmp/file000.m3u8"));

        List<String> command = new FFmpegBuilder()
                .setInput(Samples.TEST_PREFIX + Samples.base_big_buck_bunny_720p_1mb)
                .done()
                .addHlsOutput("tmp/output.m3u8")
                .setHlsTime(5, TimeUnit.SECONDS)
                .setHlsBaseUrl("test1234/")
                .setVideoBitRate(1000)
                .setHlsListSize(3)
                .setHlsInitTime(3, TimeUnit.MILLISECONDS)
                .setHlsSegmentFileName("tmp/file%03d.ts")
                .done()
                .build();

        new FFmpeg().run(command);

        assertTrue(Files.exists(Paths.get("tmp/output.m3u8")));
        assertTrue(Files.exists(Paths.get("tmp/file000.ts")));
    }
}
