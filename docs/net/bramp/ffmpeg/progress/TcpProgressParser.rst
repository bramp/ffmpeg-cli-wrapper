.. java:import:: java.io IOException

.. java:import:: java.net InetAddress

.. java:import:: java.net ServerSocket

.. java:import:: java.net URI

.. java:import:: java.net URISyntaxException

.. java:import:: java.util.concurrent CountDownLatch

TcpProgressParser
=================

.. java:package:: net.bramp.ffmpeg.progress
   :noindex:

.. java:type:: public class TcpProgressParser extends AbstractSocketProgressParser

Fields
------
address
^^^^^^^

.. java:field:: final URI address
   :outertype: TcpProgressParser

server
^^^^^^

.. java:field:: final ServerSocket server
   :outertype: TcpProgressParser

Constructors
------------
TcpProgressParser
^^^^^^^^^^^^^^^^^

.. java:constructor:: public TcpProgressParser(ProgressListener listener) throws IOException, URISyntaxException
   :outertype: TcpProgressParser

TcpProgressParser
^^^^^^^^^^^^^^^^^

.. java:constructor:: public TcpProgressParser(ProgressListener listener, int port, InetAddress addr) throws IOException, URISyntaxException
   :outertype: TcpProgressParser

Methods
-------
getRunnable
^^^^^^^^^^^

.. java:method:: @Override protected Runnable getRunnable(CountDownLatch startSignal)
   :outertype: TcpProgressParser

getThreadName
^^^^^^^^^^^^^

.. java:method:: protected String getThreadName()
   :outertype: TcpProgressParser

getUri
^^^^^^

.. java:method:: public URI getUri()
   :outertype: TcpProgressParser

stop
^^^^

.. java:method:: @Override public synchronized void stop() throws IOException
   :outertype: TcpProgressParser

