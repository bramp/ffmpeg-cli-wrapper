package net.bramp.ffmpeg.lang;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * A Mock Process, which exits with zero, and returns the provided streams.
 *
 * @author bramp
 */
class MockProcess extends Process {
  final OutputStream stdin;
  final InputStream stdout;
  final InputStream stderr;

  public MockProcess(InputStream stdout) {
    this.stdin = null; // TODO make this something
    this.stdout = stdout;
    this.stderr = null; // TODO make this return nothing.
  }

  public MockProcess(OutputStream stdin, InputStream stdout, InputStream stderr) {
    this.stdin = stdin;
    this.stdout = stdout;
    this.stderr = stderr;
  }

  @Override
  public OutputStream getOutputStream() {
    return stdin;
  }

  @Override
  public InputStream getInputStream() {
    return stdout;
  }

  @Override
  public InputStream getErrorStream() {
    return stderr;
  }

  @Override
  public int waitFor() throws InterruptedException {
    return 0;
  }

  @Override
  public int exitValue() {
    return 0;
  }

  @Override
  public void destroy() {}
}
