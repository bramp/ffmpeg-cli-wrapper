package net.bramp.ffmpeg;

import com.google.common.collect.ImmutableList;
import com.google.common.io.ByteStreams;
import com.google.common.io.CountingOutputStream;
import com.google.common.net.HostAndPort;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.fixtures.Samples;
import net.bramp.ffmpeg.job.FFmpegJob;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.progress.Progress;
import net.bramp.ffmpeg.progress.RecordingProgressListener;
import org.glassfish.grizzly.PortRange;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.http.util.MimeType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static net.bramp.ffmpeg.FFmpeg.FPS_30;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/** Tests actually shelling out ffmpeg and ffprobe. Could be flakey if ffmpeg or ffprobe change. */
public class FFmpegExecutorTest {

  static final Logger LOG = LoggerFactory.getLogger(FFmpegExecutorTest.class);

  @Rule public Timeout timeout = new Timeout(30, TimeUnit.SECONDS);

  final FFmpeg ffmpeg = new FFmpeg();
  final FFprobe ffprobe = new FFprobe();
  final FFmpegExecutor ffExecutor = new FFmpegExecutor(ffmpeg, ffprobe);
  final ExecutorService executor = Executors.newSingleThreadExecutor();

  public FFmpegExecutorTest() throws IOException {}

  // Webserver which can be used for fetching files over HTTP
  static HttpServer server;

  @BeforeClass
  public static void startWebserver() throws IOException {
    MimeType.add("mp4", "video/mp4");

    server =
        HttpServer.createSimpleServer(
            Samples.TEST_PREFIX, "127.0.0.1", new PortRange(10000, 60000));
    server.start();

    LOG.info("Started server at {}", getWebserverRoot());
  }

  @AfterClass
  public static void stopWebserver() {
    server.shutdownNow();
  }

  public static String getWebserverRoot() {
    NetworkListener net = server.getListener("grizzly");
    HostAndPort hp = HostAndPort.fromParts(net.getHost(), net.getPort());
    return "http://" + hp.toString() + "/";
  }

  @Test
  public void testNormal() throws InterruptedException, ExecutionException, IOException {

    FFmpegBuilder builder =
        new FFmpegBuilder()
            .setVerbosity(FFmpegBuilder.Verbosity.DEBUG)
            .setUserAgent(
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36")
            .setInput(getWebserverRoot() + Samples.base_big_buck_bunny_720p_1mb)
            .addExtraArgs("-probesize", "1000000")
            //.setStartOffset(1500, TimeUnit.MILLISECONDS)
            .overrideOutputFiles(true)
            .addOutput(Samples.output_mp4)
            .setFrames(100)
            .setFormat("mp4")
            .setStartOffset(500, TimeUnit.MILLISECONDS)
            .setAudioCodec("aac")
            .setAudioChannels(1)
            .setAudioSampleRate(48000)
            .setAudioBitStreamFilter("chomp")
            .setAudioFilter("aecho=0.8:0.88:6:0.4")
            .setAudioQuality(1)
            .setVideoCodec("libx264")
            .setVideoFrameRate(FPS_30)
            .setVideoResolution(320, 240)
            //.setVideoFilter("scale=320:trunc(ow/a/2)*2")
            //.setVideoPixelFormat("yuv420p")
            //.setVideoBitStreamFilter("noise")
            .setVideoQuality(2)
            .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
            .done();

    FFmpegJob job = ffExecutor.createJob(builder);
    runAndWait(job);

    assertEquals(FFmpegJob.State.FINISHED, job.getState());
  }

  @Test
  public void testTwoPass() throws InterruptedException, ExecutionException, IOException {
    FFmpegProbeResult in = ffprobe.probe(Samples.big_buck_bunny_720p_1mb);
    assertFalse(in.hasError());

    FFmpegBuilder builder =
        new FFmpegBuilder()
            .setInput(in)
            .overrideOutputFiles(true)
            .addOutput(Samples.output_mp4)
            .setFormat("mp4")
            .disableAudio()
            .setVideoCodec("mpeg4")
            .setVideoFrameRate(FFmpeg.FPS_30)
            .setVideoResolution(320, 240)
            .setTargetSize(1024 * 1024)
            .done();

    FFmpegJob job = ffExecutor.createTwoPassJob(builder);
    runAndWait(job);

    assertEquals(FFmpegJob.State.FINISHED, job.getState());
  }

  @Test
  public void testFilter() throws InterruptedException, ExecutionException, IOException {

    FFmpegBuilder builder =
        new FFmpegBuilder()
            .setInput(Samples.big_buck_bunny_720p_1mb)
            .overrideOutputFiles(true)
            .addOutput(Samples.output_mp4)
            .setFormat("mp4")
            .disableAudio()
            .setVideoCodec("mpeg4")
            .setVideoFilter("scale=320:trunc(ow/a/2)*2")
            .done();

    FFmpegJob job = ffExecutor.createJob(builder);
    runAndWait(job);

    assertEquals(FFmpegJob.State.FINISHED, job.getState());
  }

  @Test
  public void testMetaTags() throws InterruptedException, ExecutionException, IOException {

    FFmpegBuilder builder =
        new FFmpegBuilder()
            .setInput(Samples.big_buck_bunny_720p_1mb)
            .overrideOutputFiles(true)
            .addOutput(Samples.output_mp4)
            .setFormat("mp4")
            .disableAudio()
            .setVideoCodec("mpeg4")
            .addMetaTag("comment", "This=Nice!")
            .addMetaTag("title", "Big Buck Bunny")
            .done();

    FFmpegJob job = ffExecutor.createJob(builder);
    runAndWait(job);

    assertEquals(FFmpegJob.State.FINISHED, job.getState());
  }

  /**
   * Test if addStdoutOutput() actually works, and the output can be correctly captured.
   *
   * @throws InterruptedException
   * @throws ExecutionException
   * @throws IOException
   */
  @Test
  public void testStdout() throws InterruptedException, ExecutionException, IOException {

    FFmpegBuilder builder =
        new FFmpegBuilder()
            .setInput(Samples.big_buck_bunny_720p_1mb)
            .addStdoutOutput()
            .setFormat("s8")
            .setAudioChannels(1)
            .done();

    List<String> newArgs =
        ImmutableList.<String>builder().add(ffmpeg.getPath()).addAll(builder.build()).build();

    // TODO Add support to the FFmpegJob to export the stream
    Process p = new ProcessBuilder(newArgs).start();

    CountingOutputStream out = new CountingOutputStream(ByteStreams.nullOutputStream());
    ByteStreams.copy(p.getInputStream(), out);

    assertEquals(0, p.waitFor());

    // This is perhaps fragile, but one byte per audio sample
    assertEquals(254976, out.getCount());
  }

  @Test
  public void testProgress() throws InterruptedException, ExecutionException, IOException {
    FFmpegProbeResult in = ffprobe.probe(Samples.big_buck_bunny_720p_1mb);

    assertFalse(in.hasError());

    FFmpegBuilder builder =
        new FFmpegBuilder()
            .readAtNativeFrameRate() // Slows the test down
            .setInput(in)
            .overrideOutputFiles(true)
            .addOutput(Samples.output_mp4)
            .done();

    RecordingProgressListener listener = new RecordingProgressListener();

    FFmpegJob job = ffExecutor.createJob(builder, listener);
    runAndWait(job);

    assertEquals(FFmpegJob.State.FINISHED, job.getState());

    List<Progress> progesses = listener.progesses;

    // Since the results of ffmpeg are not predictable, test for the bare minimum.
    assertThat(progesses, hasSize(greaterThanOrEqualTo(2)));
    assertThat(progesses.get(0).status, is(Progress.Status.CONTINUE));
    assertThat(progesses.get(progesses.size() - 1).status, is(Progress.Status.END));
  }

  protected void runAndWait(FFmpegJob job) throws ExecutionException, InterruptedException {
    executor.submit(job).get();
  }
}
