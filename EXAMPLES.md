# Examples

_This file is auto-generated from test code. Do not edit directly._  
_Run `python tools/generate_examples.py` to regenerate._

All examples below are extracted from compilable, tested Java code.
See [ReadmeTest.java](src/test/java/net/bramp/ffmpeg/ReadmeTest.java)
and [ExamplesTest.java](src/test/java/net/bramp/ffmpeg/ExamplesTest.java)
for the full source.

## Table of Contents

- [Simple Video Encoding](#simple-video-encoding)
- [Get Media Information](#get-media-information)
- [Get Progress While Encoding](#get-progress-while-encoding)
- [Two-Pass Encoding](#two-pass-encoding)
- [HLS Multi-Variant Streaming](#hls-multi-variant-streaming)
- [OGV Quality Settings](#ogv-quality-settings)
- [Extract a Thumbnail](#extract-a-thumbnail)
- [Read from RTSP Camera](#read-from-rtsp-camera)
- [Set Working Directory](#set-working-directory)
- [Create Video from Images](#create-video-from-images)
- [Complex Filter - Picture-in-Picture](#complex-filter---picture-in-picture)
- [HEVC/H.265 Transcoding](#hevch265-transcoding)
- [Split Stereo to Mono Tracks](#split-stereo-to-mono-tracks)
- [WebM DASH Manifest](#webm-dash-manifest)
- [Direct Process Control](#direct-process-control)
- [Trim/Split by Time](#trimsplit-by-time)
- [Remove Audio from Video](#remove-audio-from-video)
- [Extract Audio from Video](#extract-audio-from-video)
- [MP4 to GIF](#mp4-to-gif)
- [Add Text Overlay](#add-text-overlay)
- [Add Watermark](#add-watermark)

## Simple Video Encoding

Encode a video to MP4 using x264/aac with a target file size.

```java
FFmpegProbeResult in = ffprobe.probe(Samples.big_buck_bunny_720p_1mb);

FFmpegBuilder builder =
    new FFmpegBuilder()
        // Input
        .setInput(in) // Filename, or a FFmpegProbeResult
        .done()

        // Output
        .overrideOutputFiles(true) // Override the output if it exists
        .addOutput("output.mp4") // Filename for the destination
        .setFormat("mp4") // Format is inferred from filename, or can be set
        .setTargetSize(250_000) // Aim for a 250KB file

        // No subtiles
        .disableSubtitle()

        // Audio
        .setAudioChannels(1) // Mono audio
        .setAudioCodec("aac") // using the aac codec
        .setAudioSampleRate(48_000) // at 48KHz
        .setAudioBitRate(32768) // at 32 kbit/s

        // Video
        .setVideoCodec("libx264") // Video using x264
        .setVideoFrameRate(24, 1) // at 24 frames per second
        .setVideoResolution(640, 480) // at 640x480 resolution

        // Allow FFmpeg to use experimental specs (such as x264 / aac encoders)
        .setStrict(Strict.EXPERIMENTAL)
        .done();

FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

// Run a one-pass encode
executor.createJob(builder).run();

// Or run a two-pass encode (which is better quality at the cost of being slower)
executor.createTwoPassJob(builder).run();
```

## Get Media Information

Use FFprobe to inspect format and stream details of a media file.

```java
FFmpegProbeResult probeResult = ffprobe.probe(Samples.big_buck_bunny_720p_1mb);

FFmpegFormat format = probeResult.getFormat();
System.out.format(
    "%nFile: '%s' ; Format: '%s' ; Duration: %.3fs",
    format.filename, format.format_long_name, format.duration);

FFmpegStream stream = probeResult.getStreams().get(0);
System.out.format(
    "%nCodec: '%s' ; Width: %dpx ; Height: %dpx",
    stream.codec_long_name, stream.width, stream.height);
```

## Get Progress While Encoding

Track encoding progress using a ProgressListener.

```java
FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

FFmpegProbeResult in = ffprobe.probe(Samples.big_buck_bunny_720p_1mb);

FFmpegBuilder builder =
    new FFmpegBuilder()
        .setInput(in) // Or filename
        .done()
        .addOutput("output.mp4")
        .done();

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
```

## Two-Pass Encoding

Two-pass encoding produces higher quality at the cost of being slower. The executor handles creating and cleaning up passlog files.

```java
FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
FFmpegProbeResult in = ffprobe.probe(Samples.big_buck_bunny_720p_1mb);

FFmpegBuilder builder =
    new FFmpegBuilder()
        .overrideOutputFiles(true)
        .setInput(in)
        .done()
        .addOutput("output.mp4")
        .setFormat("mp4")
        .setTargetSize(250_000)
        .disableSubtitle()
        .setAudioCodec("aac")
        .setAudioChannels(1)
        .setAudioSampleRate(48_000)
        .setAudioBitRate(32768)
        .setVideoCodec("libx264")
        .setVideoFrameRate(24, 1)
        .setVideoResolution(640, 480)
        .setStrict(Strict.EXPERIMENTAL)
        .done();

FFmpegJob job = executor.createTwoPassJob(builder);
job.run();
```

## HLS Multi-Variant Streaming

Create an HLS adaptive bitrate stream with multiple quality variants. Generates a master playlist and per-variant segment files.

```java
FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
FFmpegProbeResult in = ffprobe.probe(Samples.big_buck_bunny_720p_1mb);

Path tempDir = Files.createTempDirectory("ffmpeg-hls");

FFmpegBuilder builder =
    new FFmpegBuilder()
        .overrideOutputFiles(true)
        .setInput(in)
        .done()
        // The %v placeholder is replaced by the variant index (0, 1, etc.)
        .addHlsOutput(tempDir.resolve("var-%v/index.m3u8").toString())
        .setHlsTime(10, TimeUnit.SECONDS)
        .setHlsPlaylistType("vod")
        .setHlsListSize(0)
        .setHlsSegmentFileName(tempDir.resolve("var-%v/segment%d.ts").toString())
        .setMasterPlName("master.m3u8")
        .addVariant(new HlsVariant().addVideo(0).addAudio(0).setName("1080p"))
        .addVariant(new HlsVariant().addVideo(1).addAudio(1).setName("720p"))
        .addVariant(new HlsVariant().addVideo(2).addAudio(2).setName("480p"))
        .addVariant(new HlsVariant().addVideo(3).addAudio(3).setName("360p"))
        .addVariant(new HlsVariant().addVideo(4).addAudio(4).setName("240p"))
        .addVariant(new HlsVariant().addVideo(5).addAudio(5).setName("144p"))
        .setPreset("slow")
        .addExtraArgs("-g", "48")
        .addExtraArgs("-sc_threshold", "0")
        // Map the same input multiple times for different variants
        .addExtraArgs("-map", "0:0", "-map", "0:1") // 1080p
        .addExtraArgs("-map", "0:0", "-map", "0:1") // 720p
        .addExtraArgs("-map", "0:0", "-map", "0:1") // 480p
        .addExtraArgs("-map", "0:0", "-map", "0:1") // 360p
        .addExtraArgs("-map", "0:0", "-map", "0:1") // 240p
        .addExtraArgs("-map", "0:0", "-map", "0:1") // 144p
        // Specific settings for each variant
        .addExtraArgs("-s:v:0", "1920x1080", "-b:v:0", "1800k")
        .addExtraArgs("-s:v:1", "1280x720", "-b:v:1", "1200k")
        .addExtraArgs("-s:v:2", "858x480", "-b:v:2", "750k")
        .addExtraArgs("-s:v:3", "630x360", "-b:v:3", "550k")
        .addExtraArgs("-s:v:4", "426x240", "-b:v:4", "400k")
        .addExtraArgs("-s:v:5", "256x144", "-b:v:5", "200k")
        .setAudioCodec("copy")
        .done();

FFmpegJob job =
    executor.createJob(
        builder,
        new ProgressListener() {
          final double duration_ns = in.getFormat().duration * TimeUnit.SECONDS.toNanos(1);

          @Override
          public void progress(Progress progress) {
            double percentage = progress.out_time_ns / duration_ns;
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
```

## OGV Quality Settings

Codec-specific quality scales via addExtraArgs.

```java
FFmpegBuilder builder =
    new FFmpegBuilder()
        .setInput("input.mkv")
        .done()
        .addOutput("output.ogv")
        .setVideoCodec("libtheora")
        .addExtraArgs("-qscale:v", "7")
        .setAudioCodec("libvorbis")
        .addExtraArgs("-qscale:a", "5")
        .done();
```

## Extract a Thumbnail

Skip the first 10 frames and scale to 200px wide. Demonstrates: setFrames, setVideoFilter.

```java
FFmpegBuilder builder =
    new FFmpegBuilder()
        .setInput("sample.avi")
        .done()
        .addOutput("thumbnail.png")
        .setFrames(1)
        .setVideoFilter("select='gte(n\\,10)',scale=200:-1")
        .done();
```

## Read from RTSP Camera

Save frames as numbered JPEG images. Demonstrates: RTSP input, image2 output format.

```java
FFmpegBuilder builder =
    new FFmpegBuilder()
        .setInput("rtsp://192.168.1.1:1234/")
        .done()
        .addOutput("img%03d.jpg")
        .setFormat("image2")
        .done();
```

## Set Working Directory

Useful when input/output paths are relative. Demonstrates: RunProcessFunction.setWorkingDirectory.

```java
RunProcessFunction func = new RunProcessFunction();
func.setWorkingDirectory("/path/to/working/dir");

FFmpeg ffmpeg = new FFmpeg("/path/to/ffmpeg", func);
FFprobe ffprobe = new FFprobe("/path/to/ffprobe", func);

FFmpegBuilder builder =
    new FFmpegBuilder().setInput("input").done().addOutput("output.mp4").done();

FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

// Run a two-pass encode
executor.createTwoPassJob(builder).run();
```

## Create Video from Images

Input pattern image%03d.png matches image000.png, image001.png, etc. Demonstrates: image sequence input, setVideoFrameRate.

```java
FFmpegBuilder builder =
    new FFmpegBuilder()
        .addInput("image%03d.png")
        .done()
        .addOutput("output.mp4")
        .setVideoFrameRate(FFmpeg.FPS_24)
        .done();
```

## Complex Filter - Picture-in-Picture

Uses a complex filter graph with stream mapping. Demonstrates: multiple inputs, setComplexFilter, -map.

```java
FFmpegBuilder builder =
    new FFmpegBuilder()
        .addInput("original.mp4")
        .done()
        .addInput("spot.mp4")
        .done()
        .addOutput("with-video.mp4")
        .setComplexFilter(
            "[1:v]scale=368:207,setpts=PTS-STARTPTS+5/TB [ov]; "
                + "[0:v][ov] overlay=x=(main_w-overlay_w)/2:y=(main_h-overlay_h)/2:enable='between(t,5,15)' [v]")
        .addExtraArgs("-map", "[v]")
        .addExtraArgs("-map", "0:a")
        .setVideoCodec("libx264")
        .setPreset("ultrafast")
        .setConstantRateFactor(20)
        .setAudioCodec("copy")
        .addExtraArgs("-shortest")
        .done();
```

## HEVC/H.265 Transcoding

Uses the hvc1 tag required by Apple devices. Demonstrates: libx265 codec, tag:v extra arg, video filter set before output.

```java
FFmpegBuilder builder =
    new FFmpegBuilder()
        .addInput("original.mp4")
        .done()
        .setVideoFilter("select='gte(n\\,10)',scale=200:-1")
        .addOutput("hevc-video.mp4")
        .addExtraArgs("-tag:v", "hvc1")
        .setVideoCodec("libx265")
        .done();
```

## Split Stereo to Mono Tracks

Demonstrates: multiple outputs, -map_channel, verbosity control.

```java
FFmpegBuilder builder =
    new FFmpegBuilder()
        .setVerbosity(FFmpegBuilder.Verbosity.DEBUG)
        .setInput("input.mp3")
        .done()
        .overrideOutputFiles(true) // Override the output if it exists
        .addOutput("left.mp3")
        .addExtraArgs("-map_channel", "0.0.0")
        .done()
        .addOutput("right.mp3")
        .addExtraArgs("-map_channel", "0.0.1")
        .done();
```

## WebM DASH Manifest

Demonstrates: adding inputs in a loop, webm_dash_manifest format, adaptation sets.

```java
ArrayList<String> streams = new ArrayList<>();
FFmpegBuilder builder = new FFmpegBuilder();

builder.addInput("audio.webm").setFormat("webm_dash_manifest");

for (int i = 1; i <= 3; i++) {
  builder.addInput(String.format("video_%d.webm", i)).setFormat("webm_dash_manifest");
  streams.add(String.format("%d", i));
}

FFmpegOutputBuilder out =
    builder
        .addOutput("output.mpd")
        .setVideoCodec("copy")
        .setAudioCodec("copy") // TODO Add a new setCodec(..) method.
        .addExtraArgs("-map", "0");

for (String stream : streams) {
  out.addExtraArgs("-map", stream);
}

out.addExtraArgs(
        "-adaptation_sets",
        String.format("\"id=0,streams=0 id=1,streams=%s\"", Joiner.on(",").join(streams)))
    .done();
```

## Direct Process Control

Useful when you need direct access to the Process (e.g., to stop it).

```java
FFmpegBuilder builder =
    new FFmpegBuilder().setInput("input").done().addOutput("output.mp4").done();

List<String> args = new ArrayList<>();
args.add("/path/to/ffmpeg");
args.addAll(builder.build());

ProcessBuilder processBuilder = new ProcessBuilder(args);
processBuilder.redirectErrorStream(true);

Process p = processBuilder.start();

Thread.sleep(1000);

p.destroy();
```

## Trim/Split by Time

Extract a 1-minute segment starting at the 1-minute mark. Demonstrates: setStartOffset (input -ss), setDuration (output -t), stream copy.

```java
FFmpegBuilder builder =
    new FFmpegBuilder()
        .setInput("input.mp4")
        .setStartOffset(1, TimeUnit.MINUTES)
        .done()
        .addOutput("output.mp4")
        .setDuration(1, TimeUnit.MINUTES)
        .setVideoCodec("copy")
        .setAudioCodec("copy")
        .done();
```

## Remove Audio from Video

Keeps video untouched via stream copy. Demonstrates: disableAudio, setVideoCodec("copy").

```java
FFmpegBuilder builder =
    new FFmpegBuilder()
        .setInput("input.mp4")
        .done()
        .addOutput("output-no-audio.mp4")
        .setVideoCodec("copy")
        .disableAudio()
        .done();
```

## Extract Audio from Video

Re-encodes audio to MP3 at 192kbps. Demonstrates: disableVideo, setAudioCodec, setAudioBitRate.

```java
FFmpegBuilder builder =
    new FFmpegBuilder()
        .setInput("input.mp4")
        .done()
        .addOutput("audio.mp3")
        .disableVideo()
        .setAudioCodec("libmp3lame")
        .setAudioBitRate(192_000)
        .done();
```

## MP4 to GIF

Reduces resolution and frame rate for a smaller file. Demonstrates: format conversion, setVideoResolution, setVideoFrameRate.

```java
FFmpegBuilder builder =
    new FFmpegBuilder()
        .setInput("input.mp4")
        .done()
        .addOutput("output.gif")
        .setVideoResolution(320, 240)
        .setVideoFrameRate(10)
        .done();
```

## Add Text Overlay

Uses the drawtext filter with font styling and positioning.

```java
FFmpegBuilder builder =
    new FFmpegBuilder()
        .setInput("input.mp4")
        .done()
        .addOutput("output-with-text.mp4")
        .setVideoCodec("libx264")
        .setVideoFilter("drawtext=text='Hello World':fontsize=24:fontcolor=white:x=10:y=10")
        .done();
```

## Add Watermark

Places a PNG logo at position (10,10) using the overlay complex filter. Demonstrates: multiple inputs, setComplexFilter.

```java
FFmpegBuilder builder =
    new FFmpegBuilder()
        .addInput("video.mp4")
        .done()
        .addInput("logo.png")
        .done()
        .addOutput("watermarked.mp4")
        .setComplexFilter("overlay=10:10")
        .setVideoCodec("libx264")
        .done();
```
