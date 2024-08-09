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

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FFmpegHlsOutputBuilderTest extends AbstractFFmpegOutputBuilderTest {
    public FFmpegHlsOutputBuilderTest() {
    }

    @Override
    protected AbstractFFmpegOutputBuilder<?> getBuilder() {
        return new FFmpegBuilder().addInput("input.mp4").done().addHlsOutput("output.m3u8");
    }

    @Override
    protected List<String> removeCommon(List<String> command) {
        assertEquals("-f", command.get(0));
        assertEquals("hls", command.get(1));
        assertEquals("output.m3u8", command.get(command.size() - 1));

        return command.subList(2, command.size() - 1);
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

    @Test
    public void testHlsTime() {
        List<String> command = new FFmpegHlsOutputBuilder(new FFmpegBuilder(), "output.m3u8")
                .setHlsTime(5, TimeUnit.SECONDS)
                .build(0);

        assertThat(command, is(ImmutableList.of("-f", "hls", "-hls_time", "00:00:05", "output.m3u8")));
    }

    @Test
    public void testHlsSegmentFileName() {
        List<String> command = new FFmpegHlsOutputBuilder(new FFmpegBuilder(), "output.m3u8")
                .setHlsSegmentFileName("segment%03d.ts")
                .build(0);

        assertThat(command, is(ImmutableList.of("-f", "hls", "-hls_segment_filename", "segment%03d.ts", "output.m3u8")));
    }

    @Test
    public void testHlsInitTime() {
        List<String> command = new FFmpegHlsOutputBuilder(new FFmpegBuilder(), "output.m3u8")
                .setHlsInitTime(10, TimeUnit.MILLISECONDS)
                .build(0);

        assertThat(command, is(ImmutableList.of("-f", "hls", "-hls_init_time", "00:00:00.01", "output.m3u8")));
    }

    @Test
    public void testHlsListSize() {
        List<String> command = new FFmpegHlsOutputBuilder(new FFmpegBuilder(), "output.m3u8")
                .setHlsListSize(3)
                .build(0);

        assertThat(command, is(ImmutableList.of("-f", "hls", "-hls_list_size", "3", "output.m3u8")));
    }

    @Test
    public void testHlsBaseUrl() {
        List<String> command = new FFmpegHlsOutputBuilder(new FFmpegBuilder(), "output.m3u8")
                .setHlsBaseUrl("/base")
                .build(0);

        assertThat(command, is(ImmutableList.of("-f", "hls", "-hls_base_url", "/base", "output.m3u8")));
    }
}
