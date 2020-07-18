package net.bramp.ffmpeg.progress;

import net.bramp.ffmpeg.fixtures.Progresses;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static net.bramp.ffmpeg.Helper.combineResource;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class StreamProgressParserTest {

	RecordingProgressListener listener = new RecordingProgressListener();

	@Before
	public void setup() throws IOException {
		listener.reset();

		StreamProgressParser parser = new StreamProgressParser(listener);

		InputStream inputStream = combineResource(Progresses.allFiles);
		parser.processStream(inputStream);
	}

	@Test
	public void testNormal() {
		assertThat(listener.progesses, equalTo((List<Progress>) Progresses.allProgresses));
	}
}