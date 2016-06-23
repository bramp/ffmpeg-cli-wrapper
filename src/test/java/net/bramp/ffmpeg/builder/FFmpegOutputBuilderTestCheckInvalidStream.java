package net.bramp.ffmpeg.builder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class FFmpegOutputBuilderTestCheckInvalidStream {

	@Parameters(name="{0}")
	public static List<String> data() {
		return Arrays.asList(
			// Bad schemes
			"http://www.example.com/",
			"https://live.twitch.tv/app/live_",
			"ftp://236.0.0.1:2000",
			// Missing ports
			"udp://10.1.0.102/",
			"tcp://127.0.0.1/"
		);
	}

    private final URI uri;

    public FFmpegOutputBuilderTestCheckInvalidStream(String url) {
	this.uri = URI.create(url);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUri() {
	FFmpegOutputBuilder.checkValidStream(uri);
    }

}
