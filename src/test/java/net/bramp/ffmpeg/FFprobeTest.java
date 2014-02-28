package net.bramp.ffmpeg;

import net.bramp.ffmpeg.probe.FFmpegProbeResult;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import java.io.IOException;

public class FFprobeTest {

	final static String MEDIA_PATH = "/home/bramp/personal/ffmpeg/samples/";
	final static Gson gson = new Gson();

	FFprobe ffprobe;

	@Before
	public void before() {
		ffprobe = new FFprobe();
	}

	@Test
	public void testProbe() throws IOException {
		FFmpegProbeResult info = ffprobe.probe(MEDIA_PATH + "mobileedge_1280x720.mp4");
		System.out.println(gson.toJson(info));
	}

}
