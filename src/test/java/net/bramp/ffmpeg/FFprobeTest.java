package net.bramp.ffmpeg;

import com.google.gson.Gson;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;

public class FFprobeTest {

	final static Gson gson = new Gson();

	FFprobe ffprobe;

	@Before
	public void before() {
		ffprobe = new FFprobe();
	}

	@Test
	public void testProbe() throws IOException {
		FFmpegProbeResult info = ffprobe.probe(Samples.big_buck_bunny_720p_1mb);
		assertFalse(info.hasError());
		System.out.println(gson.toJson(info));
	}

}
