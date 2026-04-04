# GitHub Issue Responses

Draft responses for open issues on bramp/ffmpeg-cli-wrapper.
Generated 2026-04-04. Review before posting.

---

## ~~Issue #365 — FFProbeStream is missing "color_space" attribute~~ DONE
**Status:** Closed 2026-04-04. PR #366 merged, comment posted, issue closed.

---

## ~~Issue #364 — Some videos fail to transcode on the first attempt but succeed on the second attempt~~ DONE
**Status:** Responded 2026-04-04. Asked for more info (stacktrace, ffprobe version, file system details). Left open.

---

## ~~Issue #360 — It would be nice if there was compatibility~~ DONE
**Status:** Responded and closed (not planned) 2026-04-04. Asked for platform details, closed as needing more info.

---

## Issue #355 — Allow user to replace all uses of STDOUT/STDERR for logging
**Labels:** enhancement  
**Opened:** 2024-09-23 by @AlexanderSchuetz97  
**URL:** https://github.com/bramp/ffmpeg-cli-wrapper/issues/355

### Summary of Discussion
- User's deployment discards stdout/stderr; wants ffmpeg errors captured via a pluggable logging interface.
- Proposed a `FFmpegLoggingManager` with a static setter and a method like `appendLog(Process, boolean isStdErr, byte[], int off, int len)`.
- @Euklios (collaborator) agrees the current stdout/stderr situation is problematic. Notes that stdout and stderr are currently merged via `redirectErrorStream(true)` in `RunProcessFunction`. Also notes slf4j-api is already a dependency.
- @AlexanderSchuetz97 doesn't need the Process reference as long as some unique identifier is provided.

### Proposed Response
> This is a good enhancement request and I agree with the direction. Today, `RunProcessFunction` merges stderr into stdout via `redirectErrorStream(true)`, and the output goes to `processOutputStream` / `processErrorStream` which default to `System.out` / `System.err`.
>
> A few thoughts on implementation:
>
> 1. **Separate stdout and stderr streams** — `RunProcessFunction` currently merges them, which loses the distinction. We should stop doing that and handle them independently.
> 2. **Use slf4j** — Since it's already a dependency, logging stderr through slf4j by default would be a natural improvement. Users who want custom handling could provide their own `ProcessFunction` implementation.
> 3. **Pluggable output handling** — The existing `setProcessOutputStream` / `setProcessErrorStream` on `FFcommon` already provides some customization. We could make this more ergonomic.
>
> I'd welcome a PR that:
> - Stops merging stderr into stdout in `RunProcessFunction`
> - Adds slf4j logging for the stderr output by default
> - Keeps the existing `setProcessOutputStream` / `setProcessErrorStream` API for custom handling
>
> Would one of you like to take this on?

---

## Issue #354 — Process stdout should not use CharStream.copy
**Labels:** bug  
**Opened:** 2024-08-22 by @Euklios  
**URL:** https://github.com/bramp/ffmpeg-cli-wrapper/issues/354

### Summary of Discussion
- @Euklios identified that stdout output is treated as text (via `CharStreams.copy`) when it should be binary — this corrupts encoded file output piped via stdout.
- Fix committed to Euklios's fork (dd797a7), to be delivered together with #334 (pipe:0 support).
- Linked to the broader stdin/stdout binary handling work.

### Proposed Response
> Thanks @Euklios. I see you have a fix for this in your fork. Is this included in an open PR, or should we create one? Let's get this merged — it's a legitimate bug for anyone piping output via stdout.

---

## Issue #347 — hls_time: Invalid chars on ffmpeg version 3
**Labels:** bug  
**Opened:** 2024-08-21 by @Euklios  
**URL:** https://github.com/bramp/ffmpeg-cli-wrapper/issues/347

### Summary of Discussion
- HLS output builder uses `hh:mm:ss` format for `hls_time`, but ffmpeg v3 expects seconds (e.g., `120` not `2:00`).
- @Euklios committed a fix (use seconds instead) but reverted due to compatibility issues. Says a proper fix requires the async work to be merged first.

### Proposed Response
> @Euklios — is this still blocked on async work, or can the seconds-based format be applied independently now? Using seconds should be universally compatible across ffmpeg versions 3+, so it seems like a safe change regardless. Let me know the status and if there's a PR ready to review.

---

## Issue #334 — Add support for -i pipe:0
**Labels:** enhancement  
**Opened:** 2024-07-26 by @AlexanderSchuetz97  
**URL:** https://github.com/bramp/ffmpeg-cli-wrapper/issues/334

### Summary of Discussion
- User needs to transcode from `InputStream` via `pipe:0` because the MP4 demuxer doesn't work with unix/tcp sockets.
- @Euklios has a working implementation on his fork using `setProcessInputStream(InputStream)`.
- Discussions about thread safety of simultaneous stdin writing and stdout reading.
- @Euklios noted this needs careful handling of OS buffers and may require moving stdout/stdin to separate threads.
- Related to #354 (stdout binary handling) and #318 (input builder).

### Proposed Response
> @Euklios — it sounds like you have a working implementation for this. Is this ready as a PR against this repo? I'd like to get pipe:0 support landed. The `setProcessInputStream(InputStream)` approach looks clean.
>
> Regarding thread safety with concurrent stdin/stdout: I agree we should handle stdin on a separate thread to avoid deadlocks from OS buffer exhaustion. Let me know what's needed to get this across the finish line.

---

## ~~Issue #332 — ProgressListener never triggered but the video is in converting~~ DONE
**Status:** Responded and closed 2026-04-04. Pointed to v0.9.0 (includes PR #315 fix). Closed as completed.

---

## Issue #316 — Plan for moving to newer java versions + modernising library
**Labels:** (none)  
**Opened:** 2024-03-11 by @bramp  
**URL:** https://github.com/bramp/ffmpeg-cli-wrapper/issues/316

### Summary of Discussion
- Long discussion about Java version requirements. Current minimum is Java 8.
- @AlexanderSchuetz97 strongly advocates keeping Java 8 support (medical environment, legacy codebases).
- @Euklios willing to maintain a Java 8 branch if needed, offered to take on maintainer role.
- Consensus: don't break Java 8 users unless absolutely necessary. API cleanup (getters, immutability) can happen independently of version bumps.
- @bramp added @Euklios as a maintainer.
- Discussion about Gradle (declined — staying with Maven).
- Separate from Java version: desire to clean up API (private fields, proper getters, immutable collections).

### Proposed Action: **Leave open as tracking issue, post update**

### Proposed Response
> Update on this: We're continuing to target Java 8 as the minimum for now. The main modernization priorities are:
>
> 1. **API cleanup** — Adding proper getters, deprecating direct field access, making probe result objects more immutable. This can happen without changing the Java version target.
> 2. **Pending release** — There are several merged fixes and features that need a release. I'll work on cutting a new version.
> 3. **Java version** — We'll bump when the ecosystem forces our hand (e.g., when key dependencies drop Java 8 support), but not before. When we do, we'll tag a final Java-8-compatible release.

---

## Issue #299 — Re-attaching to the progress TCP port
**Labels:** (none)  
**Opened:** 2023-12-14 by @curiousurick  
**URL:** https://github.com/bramp/ffmpeg-cli-wrapper/issues/299

### Summary of Discussion
- User wants to reconnect to a running ffmpeg's progress port after a server restart.
- @Euklios explained it's theoretically possible using `TcpProgressParser` with the port extracted from the running ffmpeg process's `-progress` argument.
- No follow-up from the original reporter.

### Proposed Response
> @curiousurick — @Euklios provided a solution above. To clarify the architecture: the TCP progress listener is created by *our library* (Java side), and ffmpeg connects to it as a client. So after a server restart, you would need to:
>
> 1. Find the running ffmpeg process (`ps aux | grep ffmpeg`)
> 2. Extract the `-progress tcp://...` URI from its command line
> 3. Create a new `TcpProgressParser` bound to the same port
>
> However, note that ffmpeg connects to the progress TCP port *once* at startup. If the listener goes away, ffmpeg won't reconnect — it will just stop sending progress. So re-attaching to a running ffmpeg's progress is **not possible** with the current architecture.
>
> If this is a critical use case for you, you'd need to write progress to a file (`-progress file://path`) and poll that file instead. That survives server restarts.
>
> Closing as answered — please reopen if you have further questions.

---

## Issue #298 — FFmpegJob running. how to do force stop job?
**Labels:** question  
**Opened:** 2023-12-13 by @13330863440  
**URL:** https://github.com/bramp/ffmpeg-cli-wrapper/issues/298

### Summary of Discussion
- User wants to forcibly stop a running FFmpegJob.
- @luoyh provided a working solution: implement a custom `RunProcessFunction` that captures the `Process` reference, then call `process.destroy()` / `process.toHandle().destroy()` from another thread.

### Proposed Response
> @luoyh's answer above is the recommended approach — create a custom `RunProcessFunction` that captures the `Process` reference so you can destroy it from another thread.
>
> We acknowledge the current API doesn't make this easy. Exposing the process handle or adding a `cancel()` method to `FFmpegJob` is something we'd like to address in a future API improvement (see #316). For now, the custom `RunProcessFunction` approach works well.
>
> Here's a slightly refined version:
>
> ```java
> public class CancellableProcessFunction extends RunProcessFunction {
>     private volatile Process process;
>     
>     @Override
>     public Process run(List<String> args) throws IOException {
>         process = super.run(args);
>         return process;
>     }
>     
>     public void cancel() {
>         Process p = process;
>         if (p != null) {
>             p.destroyForcibly();
>         }
>     }
> }
> ```
>
> Closing as answered.

---

## Issue #291 — CVE-2023-39018: Assumed code injection vulnerability
**Labels:** (none) — pinned  
**Opened:** 2023-07-20 by @LetianYuan  
**URL:** https://github.com/bramp/ffmpeg-cli-wrapper/issues/291

### Summary of Discussion
- CVE-2023-39018 was filed claiming the FFmpeg constructor allows arbitrary code execution because `version()` is called in the constructor, which invokes the provided path.
- @bramp acknowledged the `version()` call could be deferred but noted this is by-design behavior — the whole purpose of the library is to execute ffmpeg.
- @Euklios contacted MITRE; CVE is now marked as **disputed**.
- Most vulnerability scanners no longer flag it, but Sonatype's OSS Index still does.
- Community consensus: this is a bogus CVE filed in bad faith.

### Proposed Response
> Status update: The CVE remains disputed at MITRE and NVD, which is the strongest resolution we can achieve — MITRE doesn't typically withdraw CVEs.
>
> For anyone encountering this in vulnerability scans:
> - **NVD**: No longer flags this
> - **Sonatype OSS Index**: May still flag it — you can suppress it in dependency-check-maven with a suppression rule
> - **IntelliJ / other tools**: Check if they source from NVD (should be fine) or OSS Index
>
> As a concrete improvement, we've considered deferring the `version()` call from the constructor to first use. This would address the theoretical concern without breaking any real workflows. I'll look at this for the next release.
>
> For suppression in OWASP dependency-check-maven, add to your suppressions XML:
> ```xml
> <suppress>
>    <cve>CVE-2023-39018</cve>
> </suppress>
> ```

---

## Issue #287 — Passlog files not deleted when custom pass directory is set
**Labels:** bug, good first issue  
**Opened:** 2023-05-29 by @jakubzieba  
**URL:** https://github.com/bramp/ffmpeg-cli-wrapper/issues/287

### Summary of Discussion
- `TwoPassFFmpegJob.deletePassLog()` always searches in `Paths.get("")` (current working directory), ignoring any custom pass directory set via `FFmpegBuilder.setPassDirectory()`.
- PR #361 was opened by @gogoadl on 2025-05-02 to fix this.
- No comments on the issue itself.

### Proposed Action: **Review and merge PR #361, then close this issue**

### Proposed Response
> This is a valid bug — confirmed by looking at the code. `deletePassLog()` in `TwoPassFFmpegJob` uses `Paths.get("")` instead of the configured pass directory.
>
> PR #361 has been opened to fix this. I'll review and merge it.

---

## Issue #282 — How to stop FFMPEG process gracefully with ffmpeg-cli-wrapper?
**Labels:** question  
**Opened:** 2023-03-15 by @mertakkartal  
**URL:** https://github.com/bramp/ffmpeg-cli-wrapper/issues/282

### Summary of Discussion
- User wants to stop a recording before the configured duration elapses.
- @bramp suggested executing ffmpeg manually via `ProcessBuilder` to get a `Process` object.
- Related to #298 (same question about stopping jobs).

### Proposed Response
> This is a common request — see also #298 for a working solution.
>
> The recommended approach is to use a custom `RunProcessFunction` that captures the `Process` reference:
>
> ```java
> CancellableProcessFunction procFunc = new CancellableProcessFunction();
> FFmpeg ffmpeg = new FFmpeg("/path/to/ffmpeg", procFunc);
> 
> // Start the job in another thread
> executor.submit(() -> {
>     FFmpegJob job = executor.createJob(builder);
>     job.run();
> });
> 
> // Later, to stop gracefully:
> procFunc.cancel();
> ```
>
> See #298 for the `CancellableProcessFunction` implementation.
>
> For a truly graceful stop (letting ffmpeg finish writing headers/trailers), you'd want to send `Process.destroy()` (SIGTERM) rather than `destroyForcibly()` (SIGKILL). FFmpeg handles SIGTERM gracefully and will finalize the output file.
>
> Closing as answered — duplicate of #298.

---

## Issue #280 — What does the thread safety of this wrapper look like?
**Labels:** question  
**Opened:** 2023-01-23 by @Igrium  
**URL:** https://github.com/bramp/ffmpeg-cli-wrapper/issues/280

### Summary of Discussion
- User asks if `FFmpeg` and `FFmpegExecutor` are thread-safe.
- No responses yet. 3 thumbs-up reactions.

### Proposed Response
> Good question. Here's the thread safety picture:
>
> **`FFmpeg` / `FFprobe`** — These are **thread-safe for concurrent use** in the common case. The `version()`, `codecs()`, `formats()`, etc. methods are all `synchronized` and lazily cached. Each call to `run()` creates a new `Process`, so concurrent encoding jobs won't interfere with each other.
>
> **`FFmpegExecutor`** — **Thread-safe**. It's stateless and just delegates to `FFmpeg`/`FFprobe`.
>
> **`FFmpegBuilder`** — **NOT thread-safe**. It's a mutable builder — don't share a single builder across threads. Create one builder per job.
>
> **`FFmpegJob`** — **Run once per instance**. The `state` field is not synchronized, so if you need to check job state from another thread, you may see stale values. Best practice: create a new job for each encoding task.
>
> **`FFmpegProbeResult` / `FFmpegStream`** — **Effectively immutable** after construction (populated by Gson). Safe to read from multiple threads. Don't modify the public fields.
>
> **In summary:** Create one `FFmpeg` and `FFprobe` instance, share them across threads. Create a new `FFmpegBuilder` and `FFmpegJob` per task. You do NOT need external synchronization for the common use case of running multiple concurrent encoding jobs.

---

## Issue #257 — I want to get rotation information for the video information
**Labels:** (none)  
**Opened:** 2022-08-11 by @rjusang  
**URL:** https://github.com/bramp/ffmpeg-cli-wrapper/issues/257

### Summary of Discussion
- No description beyond the title. No comments.

### Proposed Response
> Video rotation info is typically stored in the stream's `side_data_list` or in the stream `tags` (e.g., `tags.get("rotate")`). With this wrapper, you can access it via:
>
> ```java
> FFmpegProbeResult result = ffprobe.probe("input.mp4");
> for (FFmpegStream stream : result.getStreams()) {
>     // Check tags for rotation
>     if (stream.tags != null && stream.tags.containsKey("rotate")) {
>         String rotation = stream.tags.get("rotate");
>         System.out.println("Rotation: " + rotation);
>     }
>     
>     // Check side_data_list for display matrix
>     if (stream.side_data_list != null) {
>         for (FFmpegStream.SideData sd : stream.side_data_list) {
>             if ("Display Matrix".equals(sd.side_data_type)) {
>                 System.out.println("Rotation: " + sd.rotation);
>             }
>         }
>     }
> }
> ```
>
> Note: Newer ffmpeg versions store rotation in `side_data_list` rather than `tags`. If `side_data_list` fields aren't captured by our model, you may need to run `ffprobe -show_streams -print_format json` manually to see the raw output, and we can add any missing fields.
>
> Closing due to inactivity — please reopen if you still need help.

---

## Issue #242 — How to implement this inside android apps
**Labels:** question  
**Opened:** 2022-02-24 by @hafiz013  
**URL:** https://github.com/bramp/ffmpeg-cli-wrapper/issues/242

### Summary of Discussion
- User asks how to use this library in Android.
- 1 comment (not fetched, likely a response).

### Proposed Response
> This library is a **wrapper around the ffmpeg command-line binary** — it requires `ffmpeg` and `ffprobe` to be installed and accessible as executables. It is designed for server-side / desktop Java environments.
>
> **For Android**, this library is not the right fit because:
> 1. Android doesn't ship with ffmpeg installed
> 2. Running native CLI processes on Android is restricted and unreliable
>
> Instead, consider these Android-specific alternatives:
> - [**FFmpegKit**](https://github.com/arthenica/ffmpeg-kit) — The most popular FFmpeg wrapper for Android (and iOS). Provides native bindings.
> - [**mobile-ffmpeg**](https://github.com/tanersener/mobile-ffmpeg) — Another option (predecessor to FFmpegKit).
>
> Closing as out of scope for this library.

---

## Issue #237 — Extract actual duration from a file/link
**Labels:** question  
**Opened:** 2021-09-15 by @anz000  
**URL:** https://github.com/bramp/ffmpeg-cli-wrapper/issues/237

### Summary of Discussion
- User wants to get the actual duration of a media file.

### Proposed Response
> You can get the duration using ffprobe:
>
> ```java
> FFprobe ffprobe = new FFprobe("/path/to/ffprobe");
> FFmpegProbeResult result = ffprobe.probe("input.mp4");
> double durationSeconds = result.getFormat().duration;
> System.out.println("Duration: " + durationSeconds + " seconds");
> ```
>
> For streams with potentially different durations, you can also check per-stream:
> ```java
> for (FFmpegStream stream : result.getStreams()) {
>     System.out.println(stream.codec_type + " duration: " + stream.duration + "s");
> }
> ```
>
> Closing as answered — please reopen if you need more help.

---

## Issue #235 — How to check if a file is really an image file?
**Labels:** question  
**Opened:** 2021-08-11 by @tjuxiaoyi  
**URL:** https://github.com/bramp/ffmpeg-cli-wrapper/issues/235

### Summary of Discussion
- No description beyond the title. No comments.

### Proposed Response
> You can use ffprobe to check if a file is an image:
>
> ```java
> FFprobe ffprobe = new FFprobe();
> FFmpegProbeResult result = ffprobe.probe("file.png");
> 
> boolean isImage = result.getStreams().stream()
>     .anyMatch(s -> s.codec_type == FFmpegStream.CodecType.VIDEO 
>                 && (s.nb_frames != null && "1".equals(s.nb_frames))
>                    || s.duration <= 0);
> ```
>
> However, this is really more of an ffprobe usage question than a library question. For robust file type detection, consider using Java's `Files.probeContentType()` or a library like Apache Tika instead, as they don't require shelling out to ffprobe.
>
> Closing due to inactivity.

---

## Issue #232 — How to catch ffmpeg exception?
**Labels:** question  
**Opened:** 2021-05-14 by @ChengHoHang  
**URL:** https://github.com/bramp/ffmpeg-cli-wrapper/issues/232

### Summary of Discussion
- User asks how to catch ffmpeg errors.
- 3 comments, 2 reactions.

### Proposed Response
> When ffmpeg exits with a non-zero exit code, the library throws an `IOException` (or `FFmpegException` if available in your version). You can catch it like this:
>
> ```java
> try {
>     FFmpegJob job = executor.createJob(builder);
>     job.run();
> } catch (IOException e) {
>     // ffmpeg returned an error
>     System.err.println("FFmpeg failed: " + e.getMessage());
> }
> ```
>
> Currently, stderr output is merged with stdout and written to `System.out` by default. To capture the error output programmatically, you can set a custom output stream:
>
> ```java
> StringBuilder errorOutput = new StringBuilder();
> ffmpeg.setProcessOutputStream(errorOutput);
> ```
>
> For more robust error handling, see issue #355 which discusses improving the logging/error capture architecture.
>
> Closing as answered.

---

## Issue #215 — Joining Mp3 Audio File to Video
**Labels:** question  
**Opened:** 2020-05-11 by @drveresh  
**URL:** https://github.com/bramp/ffmpeg-cli-wrapper/issues/215

### Summary of Discussion
- No comments. Almost 6 years old.

### Proposed Response
> To combine an audio file with a video file using this wrapper:
>
> ```java
> FFmpegBuilder builder = new FFmpegBuilder()
>     .addInput("video.mp4")
>     .addInput("audio.mp3")
>     .addOutput("output.mp4")
>         .setVideoCodec("copy")
>         .setAudioCodec("aac")
>         .addExtraArgs("-map", "0:v:0")
>         .addExtraArgs("-map", "1:a:0")
>         .addExtraArgs("-shortest")
>         .done();
> ```
>
> This maps the video stream from the first input and the audio stream from the second input. `-shortest` stops encoding when the shorter stream ends.
>
> Closing due to long inactivity — please open a new issue if you still need help.

---

## Issue #202 — How to close
**Labels:** question  
**Opened:** 2019-09-29 by @wyyg66666666  
**URL:** https://github.com/bramp/ffmpeg-cli-wrapper/issues/202

### Summary of Discussion
- No description or comments. Title is just "how to close".

### Proposed Response
> If you're asking how to stop a running ffmpeg process, see #298 and #282 for solutions using a custom `RunProcessFunction`.
>
> If you're asking about resource cleanup: the `FFmpeg` and `FFprobe` objects don't hold any resources that require explicit cleanup. Each `run()` call creates and cleans up its own process. There's nothing to "close".
>
> Closing due to inactivity.

---

## Issue #200 — Aspect ratio
**Labels:** (none)  
**Opened:** 2019-07-05 by @rosariod  
**URL:** https://github.com/bramp/ffmpeg-cli-wrapper/issues/200

### Summary of Discussion
- No description or comments. Title is just "aspect ratio".

### Proposed Response
> Could you provide more details about what you need? If you're looking to:
>
> **Set output aspect ratio:**
> ```java
> builder.addOutput("output.mp4")
>     .addExtraArgs("-aspect", "16:9")
>     .done();
> ```
>
> **Read input aspect ratio:**
> ```java
> FFmpegProbeResult result = ffprobe.probe("input.mp4");
> FFmpegStream video = result.getStreams().get(0);
> System.out.println("Display aspect ratio: " + video.display_aspect_ratio);
> System.out.println("Sample aspect ratio: " + video.sample_aspect_ratio);
> ```
>
> Closing due to long inactivity — please open a new issue with specifics if you still need help.

---

## Issue #196 — How can I rewrite pts?
**Labels:** question  
**Opened:** 2019-04-11 by @AalisaM  
**URL:** https://github.com/bramp/ffmpeg-cli-wrapper/issues/196

### Summary of Discussion
- User wants to rewrite PTS (presentation timestamps).
- 2 comments.

### Proposed Response
> You can add PTS filters using `addExtraArgs`:
>
> ```java
> builder.addOutput("output.mp4")
>     .setVideoFilter("setpts=PTS-STARTPTS")
>     .done();
> ```
>
> Or for more complex cases:
> ```java
> .addExtraArgs("-filter:v", "setpts=0.5*PTS")  // 2x speed
> .addExtraArgs("-filter:a", "atempo=2.0")       // Match audio speed
> ```
>
> Closing due to long inactivity.

---

## Issue #188 — Set multiple input images when creating a video
**Labels:** question  
**Opened:** 2018-11-19 by @mavrovgeorgi  
**URL:** https://github.com/bramp/ffmpeg-cli-wrapper/issues/188

### Summary of Discussion
- User wants to create a video from multiple input images.

### Proposed Response
> To create a video from a sequence of images:
>
> ```java
> FFmpegBuilder builder = new FFmpegBuilder()
>     .addExtraArgs("-framerate", "1")           // 1 image per second
>     .addInput("img%03d.png")                    // img001.png, img002.png, ...
>     .addOutput("output.mp4")
>         .setVideoCodec("libx264")
>         .setVideoFrameRate(Fraction.getFraction(25, 1))
>         .addExtraArgs("-pix_fmt", "yuv420p")
>         .done();
> ```
>
> If your images don't follow a numbered pattern, you can use a concat demuxer with a text file listing the images.
>
> Closing due to long inactivity.

---

## Issue #181 — How to use ffmpeg java api to extract key frames?
**Labels:** question  
**Opened:** 2018-09-19 by @lianshushu  
**URL:** https://github.com/bramp/ffmpeg-cli-wrapper/issues/181

### Summary of Discussion
- No comments.

### Proposed Response
> To extract key frames (I-frames) from a video:
>
> ```java
> FFmpegBuilder builder = new FFmpegBuilder()
>     .addInput("input.mp4")
>     .addOutput("keyframe_%04d.png")
>         .setVideoFilter("select='eq(pict_type\\,I)'")
>         .addExtraArgs("-vsync", "vfr")
>         .done();
> 
> FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
> executor.createJob(builder).run();
> ```
>
> This uses ffmpeg's `select` filter to output only I-frames (key frames) as individual PNG files.
>
> Closing due to long inactivity.

---

## Issue #175 — Invalid data found when processing input [mp3float] Header missing
**Labels:** (none)  
**Opened:** 2018-08-24 by @sarvesh  
**URL:** https://github.com/bramp/ffmpeg-cli-wrapper/issues/175

### Summary of Discussion
- User getting "Invalid data found when processing input" with mp3 header issues. 

### Proposed Response
> This error comes from ffmpeg itself, not from the wrapper library. "Header missing" typically means the input file is corrupted, truncated, or not actually an MP3 file.
>
> Things to try:
> 1. Verify the file plays correctly in a media player
> 2. Run `ffprobe input.mp3` directly to check for errors
> 3. Try re-encoding: `ffmpeg -i input.mp3 -c:a libmp3lame output.mp3`
>
> Closing due to long inactivity — this is an ffmpeg issue, not a wrapper issue.

---

## Issue #172 — Kill ffmpeg process?
**Labels:** question  
**Opened:** 2018-07-21 by @nolawnchairs  
**URL:** https://github.com/bramp/ffmpeg-cli-wrapper/issues/172

### Summary of Discussion
- Same question as #282 and #298. 5 thumbs-up reactions.

### Proposed Response
> Duplicate of #298 — see the solution there using a custom `RunProcessFunction` to capture and destroy the process. Closing as duplicate.

---

## Issue #167 — How to split video in picture question
**Labels:** question  
**Opened:** 2018-05-28 by @leccyril  
**URL:** https://github.com/bramp/ffmpeg-cli-wrapper/issues/167

### Summary of Discussion
- No comments.

### Proposed Response
> To extract frames from video as images:
>
> ```java
> // Extract one frame per second
> FFmpegBuilder builder = new FFmpegBuilder()
>     .addInput("input.mp4")
>     .addOutput("frame_%04d.png")
>         .setVideoFilter("fps=1")
>         .done();
> ```
>
> Or to extract a single frame at a specific time:
> ```java
> FFmpegBuilder builder = new FFmpegBuilder()
>     .addExtraArgs("-ss", "00:00:10")  // Seek to 10 seconds
>     .addInput("input.mp4")
>     .addOutput("frame.png")
>         .addExtraArgs("-frames:v", "1")
>         .done();
> ```
>
> Closing due to long inactivity.

---

## Issue #166 — How to implement ffmpeg -i input.m4a -c:a copy -movflags +faststart output.m4a
**Labels:** question  
**Opened:** 2018-05-09 by @adarshhm  
**URL:** https://github.com/bramp/ffmpeg-cli-wrapper/issues/166

### Summary of Discussion
- 1 comment.

### Proposed Response
> ```java
> FFmpegBuilder builder = new FFmpegBuilder()
>     .addInput("input.m4a")
>     .addOutput("output.m4a")
>         .setAudioCodec("copy")
>         .addExtraArgs("-movflags", "+faststart")
>         .done();
> ```
>
> Closing due to long inactivity.

---

## Summary of Recommended Actions

| Issue | Action | Priority |
|-------|--------|----------|
| #365 | Merge PR #366, close issue | High |
| #287 | Review PR #361, merge, close issue | High |
| #291 | Post CVE update, consider deferring version() | Medium |
| #316 | Post modernization update | Medium |
| #332 | Cut a new release with pending fixes | Medium |
| #334 | Get Euklios's pipe:0 PR opened | Medium |
| #354 | Get Euklios's stdout fix PR opened | Medium |
| #355 | Design and implement logging improvement | Medium |
| #347 | Check status of hls_time fix | Low |
| #364 | Ask for more info | Low |
| #360 | Ask for more info, likely close | Low |
| #299 | Close as answered | Low |
| #298 | Close as answered | Low |
| #282 | Close as answered (dup of #298) | Low |
| #280 | Answer thread safety question | Low |
| #172 | Close as dup of #298 | Low |
| #257, #242, #237, #235, #232, #215, #202, #200, #196, #188, #181, #175, #167, #166 | Answer and close (stale questions) | Low |
