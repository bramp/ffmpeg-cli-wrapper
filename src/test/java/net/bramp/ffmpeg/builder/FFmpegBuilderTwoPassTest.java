package net.bramp.ffmpeg.builder;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;

import static net.bramp.ffmpeg.builder.AbstractFFmpegStreamBuilder.DEVNULL;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FFmpegBuilderTwoPassTest {

    @Test
    public void firstPass() {
        List<String> command = new FFmpegBuilder()
                .addInput("input.mp4")
                .done()
                .addOutput("output.mp4")
                .setVideoBitRate(1_000_000)
                .setFormat("mp4")
                .done()
                .setPass(1)
                .build();

        assertThat(command, is(ImmutableList.of("-y", "-v", "error", "-an", "-i", "input.mp4", "-pass", "1", "-f", "mp4", "-b:v", "1000000", "-an", DEVNULL)));
    }

    @Test
    public void secondPass() {
        List<String> command = new FFmpegBuilder()
                .addInput("input.mp4")
                .done()
                .addOutput("output.mp4")
                .setVideoBitRate(1_000_000)
                .setFormat("mp4")
                .done()
                .setPass(2)
                .build();

        assertThat(command, is(ImmutableList.of("-y", "-v", "error", "-i", "input.mp4", "-pass", "2", "-f", "mp4", "-b:v", "1000000", "output.mp4")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void firstPassNoBitrate() {
        List<String> ignored = new FFmpegBuilder()
                .addInput("input.mp4")
                .done()
                .addOutput("output.mp4")
                .setFormat("mp4")
                .done()
                .setPass(1)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void secondPassNoBitrate() {
        List<String> ignored = new FFmpegBuilder()
                .addInput("input.mp4")
                .done()
                .addOutput("output.mp4")
                .setFormat("mp4")
                .done()
                .setPass(2)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void firstPassNoFormat() {
        List<String> ignored = new FFmpegBuilder()
                .addInput("input.mp4")
                .done()
                .addOutput("output.mp4")
                .setVideoBitRate(1_000_000)
                .done()
                .setPass(1)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void secondPassNoFormat() {
        List<String> ignored = new FFmpegBuilder()
                .addInput("input.mp4")
                .done()
                .addOutput("output.mp4")
                .setVideoBitRate(1_000_000)
                .done()
                .setPass(2)
                .build();
    }
}
