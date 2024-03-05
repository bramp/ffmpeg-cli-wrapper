package net.bramp.ffmpeg.nut;

import static org.junit.Assert.assertEquals;

import com.google.common.collect.ImmutableList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.fixtures.Samples;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO fix "invalid packet checksum" when running test
public class NutReaderTest {

  static final Logger LOG = LoggerFactory.getLogger(NutReaderTest.class);

  final boolean OUTPUT_AUDIO = false;
  final boolean OUTPUT_IMAGES = false;

  @Rule public Timeout timeout = new Timeout(30, TimeUnit.SECONDS);

  @Test
  public void testNutReader()
      throws InterruptedException, ExecutionException, IOException, LineUnavailableException {

    List<String> args =
        new FFmpegBuilder()
            .setInput(Samples.big_buck_bunny_720p_1mb)
            .addStdoutOutput()
            .setFormat("nut")
            .setVideoCodec("rawvideo")
            // .setVideoPixelFormat("rgb24") // TODO make 24bit / channel work
            .setVideoPixelFormat("argb") // 8 bits per channel
            .setAudioCodec("pcm_s32le")
            .done()
            .build();

    List<String> newArgs =
        ImmutableList.<String>builder().add(FFmpeg.DEFAULT_PATH).addAll(args).build();

    ProcessBuilder builder = new ProcessBuilder(newArgs);
    Process p = builder.start();

    new NutReader(
            p.getInputStream(),
            new NutReaderListener() {

              SourceDataLine line;

              @Override
              public void stream(Stream stream) {

                if (stream.header.type == StreamHeaderPacket.AUDIO) {

                  if (!OUTPUT_AUDIO) {
                    return;
                  }

                  if (line != null) {
                    throw new RuntimeException("Multiple audio streams not supported");
                  }

                  // Get System Audio Line
                  try {
                    line = AudioSystem.getSourceDataLine(null);

                    AudioFormat format = RawHandler.streamToAudioFormat(stream.header);
                    line.open(format);
                    line.start();

                    LOG.debug("New audio stream: {}", format);

                  } catch (LineUnavailableException e) {
                    LOG.debug("Failed to open audio device", e);
                  }
                }
              }

              @Override
              public void frame(Frame frame) {
                LOG.debug("{}", frame);

                final StreamHeaderPacket header = frame.stream.header;

                if (header.type == StreamHeaderPacket.VIDEO) {
                  BufferedImage img = RawHandler.toBufferedImage(frame);

                  if (!OUTPUT_IMAGES) {
                    return;
                  }

                  try {
                    ImageIO.write(img, "png", new File(String.format("test-%08d.png", frame.pts)));
                  } catch (IOException e) {
                    LOG.error("Failed to write png", e);
                  }

                } else if (header.type == StreamHeaderPacket.AUDIO) {
                  if (line != null) {
                    line.write(frame.data, 0, frame.data.length);
                  }
                }
              }
            })
        .read();

    assertEquals(0, p.waitFor());
  }
}
