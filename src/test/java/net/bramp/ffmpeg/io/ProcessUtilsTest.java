package net.bramp.ffmpeg.io;

import net.bramp.ffmpeg.FFmpeg;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class ProcessUtilsTest {
    @Test
    public void testProcessFinishesBeforeTimeout() throws Exception {
        String ffmpeg = new FFmpeg().getPath();
        ProcessBuilder processBuilder = new ProcessBuilder(ffmpeg, "-y", "-v", "quiet", "-f", "lavfi", "-i", "testsrc=duration=1:size=1280x720:rate=10", "-c:v", "libx264", "-t", "1", "output.mp4");
        Process process = processBuilder.start();

        int exitValue = ProcessUtils.waitForWithTimeout(process, 5, TimeUnit.SECONDS);

        assertEquals(0, exitValue);
    }

    @Test
    public void testProcessDoesNotFinishBeforeTimeout() throws IOException {
        String ffmpeg = new FFmpeg().getPath();
        ProcessBuilder processBuilder = new ProcessBuilder(ffmpeg, "-y", "-v", "quiet", "-f", "lavfi", "-i", "testsrc=duration=10:size=1280x720:rate=30", "-c:v", "libx264", "-t", "10", "output.mp4");
        Process process = processBuilder.start();

        assertThrows(TimeoutException.class, () -> {
            ProcessUtils.waitForWithTimeout(process, 1, TimeUnit.MILLISECONDS);
        });

        process.destroy();
    }
}
