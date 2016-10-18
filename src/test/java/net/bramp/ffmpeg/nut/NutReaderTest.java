package net.bramp.ffmpeg.nut;

import com.google.common.collect.ImmutableList;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.fixtures.Samples;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class NutReaderTest {

  final static Logger LOG = LoggerFactory.getLogger(NutReaderTest.class);

  @Rule
  public Timeout timeout = new Timeout(30, TimeUnit.SECONDS);

  public NutReaderTest() throws IOException {}

  @Test
  public void testNutReader() throws InterruptedException, ExecutionException, IOException {

    // @formatter:off
    List<String> args = new FFmpegBuilder()
      .setInput(Samples.big_buck_bunny_720p_1mb)
      .addStdoutOutput()
        .setFormat("nut")
        .setVideoCodec("rawvideo")
        //.setVideoPixelFormat("rgb24")
        .setVideoPixelFormat("argb") // 8 bits per channel
        .setAudioCodec("pcm_s32le")
        .done()
      .build();

    List<String> newArgs = ImmutableList.<String>builder()
      .add(FFmpeg.DEFAULT_PATH)
      .addAll(args)
      .build();
    // @formatter:on

    ProcessBuilder builder = new ProcessBuilder(newArgs);
    Process p = builder.start();

    new NutReader(p.getInputStream(), new NutReaderListener() {
      @Override
      public void frame(Frame frame) {

        if (frame.stream.header.stream_class != StreamHeaderPacket.VIDEO) {
          return;
        }

        BufferedImage img = RawImageHandler.toBufferedImage(frame);

        /*
         * try { ImageIO.write(img, "png", new File("test.png")); } catch (IOException e) {
         * LOG.error("Failed to write png", e); } System.exit(0);
         */
      }
    }).read();

    assertEquals(0, p.waitFor());
  }
}
