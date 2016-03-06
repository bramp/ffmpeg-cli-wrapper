package net.bramp.ffmpeg.lang;

import net.bramp.ffmpeg.FFmpegTest;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class NewProcessAnswer implements Answer<Process> {
  final String resource;

  public NewProcessAnswer(String resource) {
    this.resource = resource;
  }

  @Override
  public Process answer(InvocationOnMock invocationOnMock) throws Throwable {
    return new MockProcess(FFmpegTest.loadResource(resource));
  }
}
