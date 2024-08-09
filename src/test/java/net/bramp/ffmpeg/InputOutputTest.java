package net.bramp.ffmpeg;

import com.google.common.collect.ImmutableList;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.lang.NewProcessAnswer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static net.bramp.ffmpeg.FFmpegTest.argThatHasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InputOutputTest {
    @Mock
    ProcessFunction runFunc;

    FFmpeg ffmpeg;

    @Before
    public void before() throws IOException {
        when(runFunc.run(argThatHasItem("-version")))
                .thenAnswer(new NewProcessAnswer("avconv-version"));

        ffmpeg = new FFmpeg(runFunc);
    }

    @Test
    public void setInputFormat() {
        List<String> command = new FFmpegBuilder()
                .addInput("input.mp4")
                .setFormat("mp4")
                .done()
                .addOutput("output.mp4")
                .done()
                .build();

        assertThat(command, is(ImmutableList.of("-y", "-v", "error", "-f", "mp4", "-i", "input.mp4", "output.mp4")));
    }

    @Test
    public void setInputFormatMultiple() {
        List<String> command = new FFmpegBuilder()
                .addInput("input.mp4")
                .setFormat("mp4")
                .done()
                .addInput("input.mkv")
                .setFormat("matroschka")
                .done()
                .addOutput("output.mp4")
                .done()
                .build();

        assertThat(command, is(ImmutableList.of("-y", "-v", "error", "-f", "mp4", "-i", "input.mp4", "-f", "matroschka", "-i", "input.mkv", "output.mp4")));
    }

    @Test
    public void setStartOffsetOnInput() {
        List<String> command = new FFmpegBuilder()
                .addInput("input.mp4")
                .setStartOffset(10, TimeUnit.SECONDS)
                .done()
                .addOutput("output.mp4")
                .done()
                .build();

        assertThat(command, is(ImmutableList.of("-y", "-v", "error", "-ss", "00:00:10", "-i", "input.mp4", "output.mp4")));
    }

    @Test
    public void setStartOffsetOnOutput() {
        List<String> command = new FFmpegBuilder()
                .addInput("input.mp4")
                .done()
                .addOutput("output.mp4")
                .setStartOffset(10, TimeUnit.SECONDS)
                .done()
                .build();

        assertThat(command, is(ImmutableList.of("-y", "-v", "error", "-i", "input.mp4", "-ss", "00:00:10", "output.mp4")));
    }

    @Test
    public void setStartOffsetOnInputAndOutput() {
        List<String> command = new FFmpegBuilder()
                .addInput("input.mp4")
                .setStartOffset(1, TimeUnit.SECONDS)
                .done()
                .addOutput("output.mp4")
                .setStartOffset(10, TimeUnit.SECONDS)
                .done()
                .build();

        assertThat(command, is(ImmutableList.of("-y", "-v", "error", "-ss", "00:00:01", "-i", "input.mp4", "-ss", "00:00:10", "output.mp4")));
    }

    @Test
    public void readAtNativeFrameRate() {
        List<String> command = new FFmpegBuilder()
                .addInput("input.mp4")
                .readAtNativeFrameRate()
                .done()
                .addOutput("output.mp4")
                .done()
                .build();

        assertThat(command, is(ImmutableList.of("-y", "-v", "error", "-re", "-i", "input.mp4", "output.mp4")));
    }

    @Test
    public void readAtNativeFrameRateMultiple() {
        List<String> command = new FFmpegBuilder()
                .addInput("input.mp4")
                .readAtNativeFrameRate()
                .done()
                .addInput("input.mkv")
                .readAtNativeFrameRate()
                .done()
                .addOutput("output.mp4")
                .done()
                .build();

        assertThat(command, is(ImmutableList.of("-y", "-v", "error", "-re", "-i", "input.mp4", "-re", "-i", "input.mkv", "output.mp4")));
    }

    @Test
    public void outputCodec() {
        List<String> command = new FFmpegBuilder()
                .addInput("input.mp4")
                .done()
                .addOutput("output.mp4")
                .setVideoCodec("libx264")
                .setAudioCodec("aac")
                .setSubtitleCodec("vtt")
                .done()
                .build();

        assertThat(command, is(ImmutableList.of("-y", "-v", "error", "-i", "input.mp4", "-vcodec", "libx264", "-acodec", "aac", "-scodec", "vtt", "output.mp4")));
    }

    @Test
    public void inputCodec() {
        List<String> command = new FFmpegBuilder()
                .addInput("input.mp4")
                .setVideoCodec("libx264")
                .setAudioCodec("aac")
                .setSubtitleCodec("vtt")
                .done()
                .addOutput("output.mp4")
                .done()
                .build();

        assertThat(command, is(ImmutableList.of("-y", "-v", "error", "-vcodec", "libx264", "-acodec", "aac", "-scodec", "vtt", "-i", "input.mp4", "output.mp4")));
    }

    @Test
    public void inputVideoDisabled() {
        List<String> command = new FFmpegBuilder()
                .addInput("input.mp4")
                .disableVideo()
                .done()
                .addOutput("output.mp4")
                .done()
                .build();

        assertThat(command, is(ImmutableList.of("-y", "-v", "error", "-vn", "-i", "input.mp4", "output.mp4")));
    }

    @Test
    public void outputVideoDisabled() {
        List<String> command = new FFmpegBuilder()
                .addInput("input.mp4")
                .done()
                .addOutput("output.mp4")
                .disableVideo()
                .done()
                .build();

        assertThat(command, is(ImmutableList.of("-y", "-v", "error", "-i", "input.mp4", "-vn", "output.mp4")));
    }

    @Test
    public void inputAudioDisabled() {
        List<String> command = new FFmpegBuilder()
                .addInput("input.mp4")
                .disableAudio()
                .done()
                .addOutput("output.mp4")
                .done()
                .build();

        assertThat(command, is(ImmutableList.of("-y", "-v", "error", "-an", "-i", "input.mp4", "output.mp4")));
    }

    @Test
    public void outputAudioDisabled() {
        List<String> command = new FFmpegBuilder()
                .addInput("input.mp4")
                .done()
                .addOutput("output.mp4")
                .disableAudio()
                .done()
                .build();

        assertThat(command, is(ImmutableList.of("-y", "-v", "error", "-i", "input.mp4", "-an", "output.mp4")));
    }

    @Test
    public void inputSubtitleDisabled() {
        List<String> command = new FFmpegBuilder()
                .addInput("input.mp4")
                .disableSubtitle()
                .done()
                .addOutput("output.mp4")
                .done()
                .build();

        assertThat(command, is(ImmutableList.of("-y", "-v", "error", "-sn", "-i", "input.mp4", "output.mp4")));
    }

    @Test
    public void outputSubtitleDisabled() {
        List<String> command = new FFmpegBuilder()
                .addInput("input.mp4")
                .done()
                .addOutput("output.mp4")
                .disableSubtitle()
                .done()
                .build();

        assertThat(command, is(ImmutableList.of("-y", "-v", "error", "-i", "input.mp4", "-sn", "output.mp4")));
    }

    @Test
    public void setExtraArgsToOnMultipleInputs() {
        List<String> command = new FFmpegBuilder()
                .addInput("input.mp4")
                .addExtraArgs("-t", "10")
                .done()
                .addInput("input.mkv")
                .addExtraArgs("-t", "20")
                .done()
                .addOutput("output.mp4")
                .done()
                .build();

        assertThat(command, is(ImmutableList.of("-y", "-v", "error", "-t", "10", "-i", "input.mp4", "-t", "20", "-i", "input.mkv", "output.mp4")));
    }
}
