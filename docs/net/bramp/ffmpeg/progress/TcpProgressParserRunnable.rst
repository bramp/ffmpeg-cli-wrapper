.. java:import:: java.io IOException

.. java:import:: java.io InputStream

.. java:import:: java.net ServerSocket

.. java:import:: java.net Socket

.. java:import:: java.net SocketException

.. java:import:: java.util.concurrent CountDownLatch

TcpProgressParserRunnable
=========================

.. java:package:: net.bramp.ffmpeg.progress
   :noindex:

.. java:type::  class TcpProgressParserRunnable implements Runnable

Fields
------
parser
^^^^^^

.. java:field:: final StreamProgressParser parser
   :outertype: TcpProgressParserRunnable

server
^^^^^^

.. java:field:: final ServerSocket server
   :outertype: TcpProgressParserRunnable

startSignal
^^^^^^^^^^^

.. java:field:: final CountDownLatch startSignal
   :outertype: TcpProgressParserRunnable

Constructors
------------
TcpProgressParserRunnable
^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public TcpProgressParserRunnable(StreamProgressParser parser, ServerSocket server, CountDownLatch startSignal)
   :outertype: TcpProgressParserRunnable

Methods
-------
run
^^^

.. java:method:: @Override public void run()
   :outertype: TcpProgressParserRunnable

