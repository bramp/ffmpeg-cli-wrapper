package net.bramp.ffmpeg.lang;

import net.bramp.ffmpeg.Helper;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class NewProcessAnswer implements Answer<Process> {
  final String resource;

  String errResource = null;

  public NewProcessAnswer(String resource) {
    this.resource = resource;
  }

  public NewProcessAnswer(String resource, String errResource) {
    this.resource = resource;
    this.errResource = errResource;
  }

  @Override
  public Process answer(InvocationOnMock invocationOnMock) throws Throwable {
    return errResource == null
        ? new MockProcess(Helper.loadResource(resource))
        : new MockProcess(null, Helper.loadResource(resource), Helper.loadResource(errResource));
  }
}
