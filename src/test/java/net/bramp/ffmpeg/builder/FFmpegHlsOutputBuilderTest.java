package net.bramp.ffmpeg.builder;

import com.google.common.collect.ImmutableList;
import junit.framework.TestCase;
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
                        .setHlsTime("5")
                        .setHlsBaseUrl("test1234/")
                        .setHlsListSize(3)
                        .setHlsInitTime("3")
                        .setHlsSegmentFileName("file%03d.ts")
                        .done()
                        .build();
        assertEquals(
                args, ImmutableList.of("-y", "-v", "error", "-i", "input", "-hls_time", "5", "-hls_base_url",
                        "test1234/", "-hls_list_size", "3", "-hls_init_time", "3", "-hls_segment_filename", "file%03d.ts", "output.m3u8"));
    }


}