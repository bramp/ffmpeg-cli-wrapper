package net.bramp.ffmpeg.builder;

import com.google.common.collect.ImmutableList;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.fixtures.Samples;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
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
                        .addHlsOutput("output.m3u8")
                        .setHlsTime(5, TimeUnit.MILLISECONDS)
                        .setHlsBaseUrl("test1234/")
                        .setHlsListSize(3)
                        .setHlsInitTime(3, TimeUnit.MILLISECONDS)
                        .setHlsSegmentFileName("file%03d.ts")
                        .done()
                        .build();

        assertEquals(ImmutableList.of("-y", "-v", "error", "-i", "input", "-f", "hls", "-hls_time", "0.005",
                        "-hls_segment_filename", "file%03d.ts", "-hls_init_time", "00:00:00.003",
                        "-hls_list_size", "3", "-hls_base_url", "test1234/", "output.m3u8"), args);
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
                        .setVideoFilter("TEST")
                        .setHlsListSize(3)
                        .setHlsInitTime(3, TimeUnit.MILLISECONDS)
                        .setHlsSegmentFileName("file%03d.ts")
                        .done()
                        .build();

        assertEquals(ImmutableList.of("-y", "-v", "error", "-i", "input","-f","hls","-b:v","3","-vf","TEST","-hls_time", "0.005",
                        "-hls_segment_filename", "file%03d.ts", "-hls_init_time", "00:00:00.003",
                        "-hls_list_size", "3", "-hls_base_url", "test1234/", "output.m3u8"), args);
    }

    @Test
    public void testConvertVideoToHls() throws IOException {
        cleanupTmp();

        List<String> command = new FFmpegBuilder()
                .setInput(Samples.TEST_PREFIX + Samples.base_big_buck_bunny_720p_1mb)
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
    public void testConvertVideoToHlsFileSecondDuration() throws IOException {
        cleanupTmp();

        List<String> command = new FFmpegBuilder()
                .setInput(Samples.TEST_PREFIX + Samples.base_big_buck_bunny_720p_1mb)
                .addHlsOutput("tmp/output.m3u8")
                .setHlsTime(1, TimeUnit.SECONDS)
                .setHlsListSize(0)
                .setHlsSegmentFileName("tmp/file%03d.ts")
                .addExtraArgs("-force_key_frames", "expr:gte(t,n_forced)")
                .done()
                .build();

        new FFmpeg().run(command);

        FFmpegProbeResult probe = new FFprobe().probe("tmp/file000.ts");

        assertEquals(probe.getStreams().get(0).getDuration(), 1, 0.1);
    }

    @Test
    public void testConvertVideoToHlsFileMillisDuration() throws IOException {
        cleanupTmp();

        List<String> command = new FFmpegBuilder()
                .setInput(Samples.TEST_PREFIX + Samples.base_big_buck_bunny_720p_1mb)
                .addHlsOutput("tmp/output.m3u8")
                .setHlsTime(500, TimeUnit.MILLISECONDS)
                .setHlsListSize(0)
                .setHlsSegmentFileName("tmp/file%03d.ts")
                .setFrames(30)
                .addExtraArgs("-g", "5")
                .done()
                .build();

        new FFmpeg().run(command);

        FFmpegProbeResult probe = new FFprobe().probe("tmp/file000.ts");

        assertEquals(0.5, probe.getStreams().get(0).getDuration(), 0.1);
    }

    private void cleanupTmp() throws IOException {
        Path tmpFolder = Paths.get("tmp/");
        if (!Files.exists(tmpFolder)) {
            Files.createDirectory(tmpFolder);
            return;
        }


        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(tmpFolder)) {
            for (Path sub : directoryStream) {
                if (!tmpFolder.equals(sub)) {
                    Files.deleteIfExists(sub);
                }
            }
        }
    }
}
