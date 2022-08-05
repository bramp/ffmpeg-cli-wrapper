package net.bramp.ffmpeg.info;


import net.bramp.ffmpeg.FFmpeg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class FFmpegGetInfoTest {

    private List<Codec> videoCodecs = new ArrayList<>();
    private List<Codec> audioCodecs = new ArrayList<>();
    private List<Codec> subtitleCodecs = new ArrayList<>();

    @Test
    public void getFFmpegCodecSupportTest() throws IOException {
        FFmpeg ffmpeg = new FFmpeg("D:\\Downloads\\ffmpeg\\bin\\ffmpeg.exe");
        ffmpeg.codecs();

        for (Codec codec : ffmpeg.codecs()) {
            switch (codec.getType()){
                case VIDEO:
                    videoCodecs.add(codec);
                case AUDIO:
                    audioCodecs.add(codec);
                case SUBTITLE:
                    subtitleCodecs.add(codec);

            }
        }

        System.out.println("Video codecs: " + Arrays.toString(videoCodecs.toArray()));
        System.out.println("Audio codecs: " + Arrays.toString(audioCodecs.toArray()));
        System.out.println("Subtitle codecs: " + Arrays.toString(subtitleCodecs.toArray()));
    }
}
