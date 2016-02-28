package net.bramp.ffmpeg;

import com.google.common.base.Charsets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FFmpegTest {

	@Mock
	ProcessFunction runFunc;

	FFmpeg ffmpeg;

	@Before
	public void before() throws IOException {
		when(runFunc.run(argThatHasItem("-version"))).then(
				new Answer<BufferedReader>() {
					public BufferedReader answer(InvocationOnMock invocation)
							throws Throwable {
						return loadResource("ffmpeg-version");
					}
				});

		when(runFunc.run(argThatHasItem("-formats"))).thenReturn(
				loadResource("ffmpeg-formats"));
		when(runFunc.run(argThatHasItem("-codecs"))).thenReturn(
				loadResource("ffmpeg-codecs"));

		ffmpeg = new FFmpeg(runFunc);
	}

	protected static BufferedReader loadResource(String name) {
		return new BufferedReader(new InputStreamReader(
				FFmpegTest.class.getResourceAsStream(name), Charsets.UTF_8));
	}

	@SuppressWarnings("unchecked")
	protected static <T> List<T> argThatHasItem(T s) {
		return (List<T>) argThat(hasItem(s));
	}

	@Test
	public void testVersion() throws Exception {
		assertEquals("ffmpeg version 0.10.9-7:0.10.9-1~raring1",
				ffmpeg.version());
		assertEquals("ffmpeg version 0.10.9-7:0.10.9-1~raring1",
				ffmpeg.version());
	}

	@Test
	public void testCodecs() throws IOException {

		// Run twice, the second should be cached
		assertArrayEquals(Codecs.CODECS, ffmpeg.codecs().toArray());
		assertArrayEquals(Codecs.CODECS, ffmpeg.codecs().toArray());

		verify(runFunc, times(1)).run(argThatHasItem("-codecs"));
	}

	@Test
	public void testFormats() throws IOException {
		// Run twice, the second should be cached
		assertArrayEquals(Formats.FORMATS, ffmpeg.formats().toArray());
		assertArrayEquals(Formats.FORMATS, ffmpeg.formats().toArray());

		verify(runFunc, times(1)).run(argThatHasItem("-formats"));
	}
}
