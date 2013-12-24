package net.bramp.ffmpeg;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.base.Charsets;
import com.google.common.base.Function;

@RunWith(MockitoJUnitRunner.class)
public class FFmpegTest {

	@Mock
	Function<List<String>, BufferedReader> runFunc;

	FFmpeg ffmpeg;
	
	@Before
	public void before() throws IOException {
		FFmpeg.runFunc = runFunc;
		when(runFunc.apply(argThatHasItem("-version"))).thenReturn(loadResource("ffmpeg-version"));
		when(runFunc.apply(argThatHasItem("-formats"))).thenReturn(loadResource("ffmpeg-formats"));
		when(runFunc.apply(argThatHasItem("-codecs"))).thenReturn(loadResource("ffmpeg-codecs"));

		ffmpeg = new FFmpeg(); // setup after the mock
	}

	protected static BufferedReader loadResource(String name) {
		return new BufferedReader(
			new InputStreamReader(FFmpegTest.class.getResourceAsStream(name), Charsets.UTF_8)
		);
	}
	
	@SuppressWarnings("unchecked")
	protected static<T> List<T> argThatHasItem(T s) {
		return (List<T>) argThat(hasItem(s));
	}

	@Test
	public void testVersion() throws Exception {

		// Run twice, the second should be cached
		assertEquals("ffmpeg version 0.10.9-7:0.10.9-1~raring1", ffmpeg.version());
		assertEquals("ffmpeg version 0.10.9-7:0.10.9-1~raring1", ffmpeg.version());

		verify(runFunc, times(1)).apply(anyListOf(String.class));
	}

	@Test
	public void testCodecs() throws IOException {

		// Run twice, the second should be cached
		assertArrayEquals(Codecs.CODECS, ffmpeg.codecs().toArray());
		assertArrayEquals(Codecs.CODECS, ffmpeg.codecs().toArray());

		verify(runFunc, times(1)).apply(argThatHasItem("-codecs"));
	}
	
	@Test
	public void testFormats() throws IOException {
		// Run twice, the second should be cached
		assertArrayEquals(Formats.FORMATS, ffmpeg.formats().toArray());
		assertArrayEquals(Formats.FORMATS, ffmpeg.formats().toArray());

		verify(runFunc, times(1)).apply(argThatHasItem("-formats"));
	}
}
