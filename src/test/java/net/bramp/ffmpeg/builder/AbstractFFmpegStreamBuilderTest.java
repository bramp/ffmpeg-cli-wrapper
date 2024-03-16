package net.bramp.ffmpeg.builder;

import com.google.common.collect.ImmutableList;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.ProcessFunction;
import net.bramp.ffmpeg.fixtures.Samples;
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
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractFFmpegStreamBuilderTest<T extends AbstractFFmpegStreamBuilderTest<T>> {
    @Mock
    protected ProcessFunction runFunc;
    protected FFprobe ffprobe;

    @Before
    public void before() throws IOException {
        when(runFunc.run(argThatHasItem("-version")))
                .thenAnswer(new NewProcessAnswer("ffprobe-version"));
        when(runFunc.run(argThatHasItem(Samples.big_buck_bunny_720p_1mb)))
                .thenAnswer(new NewProcessAnswer("ffprobe-big_buck_bunny_720p_1mb.mp4"));

        ffprobe = new FFprobe(runFunc);
    }

    @Test
    public void testSetFormat() throws IOException {
        List<String> build = passBuilder().setFormat("mp4").done().build();

        assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-f", "mp4", "output.mp4"), build);
    }

    @Test
    public void testSetStartOffset() throws IOException {
        List<String> build = passBuilder().setStartOffset(10, TimeUnit.SECONDS).done().build();

        assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-f", "mp4", "-ss", "00:00:10", "output.mp4"), build);
    }

    @Test
    public void testSetDuration() throws IOException {
        List<String> build = passBuilder().setDuration(10, TimeUnit.SECONDS).done().build();

        assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-f", "mp4", "-t", "00:00:10", "output.mp4"), build);
    }

    @Test
    public void testAddMetaTag() throws IOException {
        List<String> build = passBuilder().addMetaTag("tag_key", "tag_value").done().build();

        assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-f", "mp4", "-metadata", "tag_key=tag_value", "output.mp4"), build);
    }

    @Test
    public void testDisableAudio() throws IOException {
        List<String> build = passBuilder().disableAudio().done().build();

        assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-f", "mp4", "-an", "output.mp4"), build);
    }

    @Test
    public void testSetAudioCodec() throws IOException {
        List<String> build = passBuilder().setAudioCodec("aac").done().build();

        assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-f", "mp4", "-acodec", "aac", "output.mp4"), build);
    }

    @Test
    public void testSetAudioChannels() throws IOException {
        List<String> build = passBuilder().setAudioChannels(7).done().build();

        assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-f", "mp4", "-ac", "7", "output.mp4"), build);
    }

    @Test
    public void testSetAudioSampleRate() throws IOException {
        List<String> build = passBuilder().setAudioSampleRate(10000).done().build();

        assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-f", "mp4", "-ar", "10000", "output.mp4"), build);
    }

    @Test
    public void testSetAudioPreset() throws IOException {
        List<String> build = passBuilder().setAudioPreset("fast").done().build();

        assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-f", "mp4", "-apre", "fast", "output.mp4"), build);
    }

    @Test
    public void testVideoDisabled() throws IOException {
        List<String> build = passBuilder().disableVideo().done().build();

        assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-f", "mp4", "-vn", "output.mp4"), build);
    }

    @Test
    public void testSetVideoCodec() throws IOException {
        List<String> build = passBuilder().setVideoCodec("vp9").done().build();

        assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-f", "mp4", "-vcodec", "vp9", "output.mp4"), build);
    }

    @Test
    public void testSetVideoCopyinkf() throws IOException {
        List<String> build = passBuilder().setVideoCopyInkf(true).done().build();

        assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-f", "mp4", "-copyinkf", "output.mp4"), build);
    }

    @Test
    public void testSetFrames() throws IOException {
        List<String> build = passBuilder().setFrames(1000).done().build();

        assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-f", "mp4", "-vframes", "1000", "output.mp4"), build);
    }

    @Test
    public void testSetVideoWidth() throws IOException {
        List<String> build = passBuilder().setVideoWidth(1920).done().build();
        
        assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-f", "mp4", "output.mp4"), build);
    }

    @Test
    public void testSetVideoHeight() throws IOException {
        List<String> build = passBuilder().setVideoHeight(1080).done().build();

        assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-f", "mp4", "output.mp4"), build);
    }

    @Test
    public void testSetVideoSize() throws IOException {
        List<String> build = passBuilder().setVideoResolution("1920x1080").done().build();

        assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-f", "mp4", "-s", "1920x1080", "output.mp4"), build);
    }

    @Test
    public void testSetVideoMovFlags() throws IOException {
        List<String> build = passBuilder().setVideoMovFlags("flag").done().build();
        
        assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-f", "mp4", "-movflags", "flag", "output.mp4"), build);
    }

    @Test
    public void testSetVideoFrameRate() throws IOException {
        List<String> build = passBuilder().setVideoFrameRate(30).done().build();
        
        assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-f", "mp4", "-r", "30/1", "output.mp4"), build);
    }

    @Test
    public void testSetVideoPixelFormat() throws IOException {
        List<String> build = passBuilder().setVideoPixelFormat("yuv422p").done().build();

        assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-f", "mp4", "-pix_fmt", "yuv422p", "output.mp4"), build);
    }

    @Test
    public void testDisableSubtitle() throws IOException {
        List<String> build = passBuilder().disableSubtitle().done().build();

        assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-f", "mp4", "-sn", "output.mp4"), build);
    }

    @Test
    public void testSetSubtitlePreset() throws IOException {
        List<String> build = passBuilder().setSubtitlePreset("fast").done().build();

        assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-f", "mp4", "-spre", "fast", "output.mp4"), build);
    }

    @Test
    public void testSetSubtitleCodec() throws IOException {
        List<String> build = passBuilder().setSubtitleCodec("libx264").done().build();

        assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-f", "mp4", "-scodec", "libx264", "output.mp4"), build);
    }

    @Test
    public void testSetPreset() throws IOException {
        List<String> build = passBuilder().setPreset("fast").done().build();

        assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-f", "mp4", "-preset", "fast", "output.mp4"), build);
    }

    @Test
    public void testSetPresetFilename() throws IOException {
        List<String> build = passBuilder().setPresetFilename("fast").done().build();

        assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-f", "mp4", "-fpre", "fast", "output.mp4"), build);
    }

    @Test
    public void testSetTargetSize() throws IOException {
        List<String> build = passBuilder().setTargetSize(10000).done().build();

        assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-f", "mp4", "-b:v", "14036", "output.mp4"), build);
    }

    @Test
    public void testSetStrict() throws IOException {
        List<String> build = passBuilder().setStrict(FFmpegBuilder.Strict.STRICT).done().build();

        assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-strict", "strict", "-f", "mp4", "output.mp4"), build);
    }

    @Test
    public void testAddExtraArgs() throws IOException {
        List<String> build = passBuilder().addExtraArgs("-map", "0:1").done().build();

        assertEquals(args("-y", "-v", "error", "-i", Samples.big_buck_bunny_720p_1mb, "-f", "mp4", "-map", "0:1", "output.mp4"), build);
    }

    protected List<String> args(String... args) {
        return ImmutableList.copyOf(args);
    }

    protected FFmpegOutputBuilder passBuilder() throws IOException {
        return new FFmpegBuilder()
                .addInput(ffprobe.probe(Samples.big_buck_bunny_720p_1mb))
                .addOutput("output.mp4")
                .setTargetSize(1)
                .setFormat("mp4");
    }
}
