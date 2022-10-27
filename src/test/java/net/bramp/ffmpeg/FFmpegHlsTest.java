package net.bramp.ffmpeg;

import com.google.common.base.Joiner;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.progress.Progress;
import net.bramp.ffmpeg.progress.ProgressListener;
import org.junit.Test;

public class FFmpegHlsTest {
    final Locale locale = Locale.US;
    final FFmpeg ffmpeg = new FFmpeg();
    final FFprobe ffprobe = new FFprobe();
    public FFmpegHlsTest() throws IOException {}

    @Test
    public void testHLSVideoEncoding() throws IOException {
        long start = System.currentTimeMillis();
        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        String inFileName = "src/test/resources/net/bramp/ffmpeg/samples/big_buck_bunny_720p_1mb.mp4";
        FFmpegProbeResult in = ffprobe.probe(inFileName);
        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(inFileName)
                .setInput(in)
                .overrideOutputFiles(true)
                .addOutput("src/test/resources/net/bramp/ffmpeg/samples/%v/index.m3u8")
                .setPreset("slow")
                .addExtraArgs("-g", "48")
                .addExtraArgs("-sc_threshold","0")
                .addExtraArgs("-map","0:0")
                .addExtraArgs("-map","0:1")
                .addExtraArgs("-map","0:0")
                .addExtraArgs("-map","0:1")
                .addExtraArgs("-map","0:0")
                .addExtraArgs("-map","0:1")
                .addExtraArgs("-map","0:0")
                .addExtraArgs("-map","0:1")
                .addExtraArgs("-map","0:0")
                .addExtraArgs("-map","0:1")
                .addExtraArgs("-map","0:0")
                .addExtraArgs("-map","0:1")
                .addExtraArgs("-s:v:0","1920*1080")
                .addExtraArgs("-b:v:0","1800k")
                .addExtraArgs("-s:v:1","1280*720")
                .addExtraArgs("-b:v:1","1200k")
                .addExtraArgs("-s:v:2","858*480")
                .addExtraArgs("-b:v:2","750k")
                .addExtraArgs("-s:v:3","630*360")
                .addExtraArgs("-b:v:3","550k")
                .addExtraArgs("-s:v:4","426*240")
                .addExtraArgs("-b:v:4","400k")
                .addExtraArgs("-s:v:5","256*144")
                .addExtraArgs("-b:v:5","200k")
                .addExtraArgs("-c:a","copy")
                .addExtraArgs("-var_stream_map","v:0,a:0,name:1080p v:1,a:1,name:720p v:2,a:2,name:480p v:3,a:3,name:360p v:4,a:4,name:240p v:5,a:5,name:144p")
                .addExtraArgs("-master_pl_name","master.m3u8")
                .addExtraArgs("-f","hls")
                .addExtraArgs("-hls_time","10")
                .addExtraArgs("-hls_playlist_type","vod")
                .addExtraArgs("-hls_list_size","0")
                .addExtraArgs("-hls_segment_filename","src/test/resources/net/bramp/ffmpeg/samples/%v/segment%d.ts")
                .done();
        String actual = Joiner.on(" ").join(ffmpeg.path(builder.build()));
        System.out.println("actual "+actual);
        FFmpegJob job =
                executor.createJob(
                        builder,
                        new ProgressListener() {
                            // Using the FFmpegProbeResult determine the duration of the input
                            final double duration_ns = in.getFormat().duration * TimeUnit.SECONDS.toNanos(1);
                            @Override
                            public void progress(Progress progress) {
                                double percentage = progress.out_time_ns / duration_ns;
                                // Print out interesting information about the progress
                                System.out.println(
                                        String.format(
                                                locale,
                                                "[%.0f%%] status:%s frame:%d time:%s fps:%.0f speed:%.2fx",
                                                percentage * 100,
                                                progress.status,
                                                progress.frame,
                                                FFmpegUtils.toTimecode(progress.out_time_ns, TimeUnit.NANOSECONDS),
                                                progress.fps.doubleValue(),
                                                progress.speed));
                            }
                        });
        job.run();
        long end = System.currentTimeMillis();
        long execution = (end - start)/1000;
        System.out.println("Execution time: " + execution + " seconds");
    }
}
