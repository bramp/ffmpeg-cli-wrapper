package net.bramp.ffmpeg.progress;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public abstract class AbstractProgressParserTest {

  @Rule
  public Timeout timeout = new Timeout(10, TimeUnit.SECONDS);

  final List<Progress> progesses = Collections.<Progress>synchronizedList(new ArrayList());

  ProgressParser parser;
  URI uri;

  final ProgressListener listener = new ProgressListener() {
    @Override
    public void progress(Progress p) {
      progesses.add(p);
    }
  };

  @Before
  public void setupParser() throws IOException, URISyntaxException {
    synchronized (progesses) {
      progesses.clear();
    }

    parser = newParser(listener);
    uri = parser.getUri();
  }

  public abstract ProgressParser newParser(ProgressListener listener) throws IOException,
      URISyntaxException;

  @Test
  public void testNoConnection() throws IOException, InterruptedException {
    parser.start();
    parser.stop();
    assertTrue(progesses.isEmpty());
  }

  @Test
  public void testDoubleStop() throws IOException, InterruptedException {
    parser.start();
    parser.stop();
    parser.stop();
    assertTrue(progesses.isEmpty());
  }

  @Test(expected = IllegalThreadStateException.class)
  public void testDoubleStart() throws IOException {
    parser.start();
    parser.start();
    assertTrue(progesses.isEmpty());
  }

  @Test()
  public void testStopNoStart() throws IOException {
    parser.stop();
    assertTrue(progesses.isEmpty());
  }
}
