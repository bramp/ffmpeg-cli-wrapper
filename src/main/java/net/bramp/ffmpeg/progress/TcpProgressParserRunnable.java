package net.bramp.ffmpeg.progress;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static com.google.common.base.Preconditions.checkNotNull;

class TcpProgressParserRunnable implements Runnable {

  final StreamProgressParser parser;
  final ServerSocket server;

  public TcpProgressParserRunnable(StreamProgressParser parser, ServerSocket server) {
    this.parser = checkNotNull(parser);
    this.server = checkNotNull(server);
  }

  @Override
  public void run() {
    while (!server.isClosed() && !Thread.currentThread().isInterrupted()) {
      try {
        try (Socket socket = server.accept()) {
          try (InputStream stream = socket.getInputStream()) {
            parser.processStream(stream);
          }
        }

      } catch (IOException e) {
        // We have no good way to report this back to the user... yet
        // TODO Report to the user that this failed in some way
      }
    }
  }
}
