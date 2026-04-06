package net.bramp.ffmpeg.progress;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

/** Parses FFmpeg progress output from an input stream. */
public class StreamProgressParser {

  final ProgressListener listener;

  /** Constructs a new stream progress parser with the given listener. */
  public StreamProgressParser(ProgressListener listener) {
    this.listener = checkNotNull(listener);
  }

  private static BufferedReader wrapInBufferedReader(Reader reader) {
    checkNotNull(reader);

    if (reader instanceof BufferedReader) {
      return (BufferedReader) reader;
    }

    return new BufferedReader(reader);
  }

  /** Parses FFmpeg progress output from the given input stream. */
  public void processStream(InputStream stream) throws IOException {
    checkNotNull(stream);
    processReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
  }

  /** Processes progress output from the given reader. */
  public void processReader(Reader reader) throws IOException {
    final BufferedReader in = wrapInBufferedReader(reader);

    String line;
    Progress p = new Progress();
    while ((line = in.readLine()) != null) {
      if (p.parseLine(line)) {
        listener.progress(p);
        p = new Progress();
      }
    }
  }
}
