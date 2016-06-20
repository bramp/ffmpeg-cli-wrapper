package net.bramp.ffmpeg.progress;

import com.google.common.base.Charsets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Parses the FFmpeg progress fields
 */
public abstract class FFmpegProgressParser {

  final FFmpegProgressListener listener;

  public FFmpegProgressParser(FFmpegProgressListener listener) {
    this.listener = checkNotNull(listener);
  }

  protected void dispatch(Progress p) {
    listener.progress(checkNotNull(p));
  }

  private static BufferedReader wrapInBufferedReader(Reader reader) {
    checkNotNull(reader);

    if (reader instanceof BufferedReader)
      return (BufferedReader) reader;

    return new BufferedReader(reader);
  }

  public void processStream(InputStream stream) throws IOException {
    checkNotNull(stream);
    processReader(new InputStreamReader(stream, Charsets.UTF_8));
  }

  public void processReader(Reader reader) throws IOException {
    final BufferedReader in = wrapInBufferedReader(reader);

    String line;
    Progress p = new Progress();
    while ((line = in.readLine()) != null) {
      if (p.parseLine(line)) {
        dispatch(p);
        p = new Progress();
      }
    }
  }

  public abstract void start() throws Exception;

  public abstract void stop();

  public abstract URI getUri();

  /*
   * TODO implement this, with something like https://github.com/jnr/jnr-unixsocket public void
   * startUnixSocket() {
   * 
   * }
   */
}
