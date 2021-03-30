package net.bramp.ffmpeg.progress;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.CountDownLatch;

import static com.google.common.base.Preconditions.checkNotNull;

class TcpProgressParserRunnable implements Runnable {

  final StreamProgressParser parser;
  final ServerSocket server;
  final CountDownLatch startSignal;

  public TcpProgressParserRunnable(
      StreamProgressParser parser, ServerSocket server, CountDownLatch startSignal) {
    this.parser = checkNotNull(parser);
    this.server = checkNotNull(server);
    this.startSignal = checkNotNull(startSignal);
  }

  @Override
  public void run() {
    while (!server.isClosed() && !Thread.currentThread().isInterrupted()) {
      try {
        // There is a subtle race condition, where ffmpeg can start up, and close before this thread
        // is scheduled. This happens more often on Travis than a unloaded system.
        startSignal.countDown();

        try (Socket socket = server.accept()) {
          try (InputStream stream = socket.getInputStream()) {
            parser.processStream(stream);
          }
        }

      } catch (SocketException e) {
        // Most likley a Socket closed exception, which we can safely ignore

      } catch (IOException e) {
        // We have no good way to report this back to the user... yet
        // TODO Report to the user that this failed in some way
      }
    }
  }
}
