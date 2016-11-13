.. java:import:: java.io IOException

.. java:import:: java.net DatagramSocket

.. java:import:: java.net InetAddress

.. java:import:: java.net SocketException

.. java:import:: java.net URI

.. java:import:: java.net URISyntaxException

.. java:import:: java.util.concurrent CountDownLatch

UdpProgressParser
=================

.. java:package:: net.bramp.ffmpeg.progress
   :noindex:

.. java:type:: public class UdpProgressParser extends AbstractSocketProgressParser

Fields
------
address
^^^^^^^

.. java:field:: final URI address
   :outertype: UdpProgressParser

socket
^^^^^^

.. java:field:: final DatagramSocket socket
   :outertype: UdpProgressParser

Constructors
------------
UdpProgressParser
^^^^^^^^^^^^^^^^^

.. java:constructor:: public UdpProgressParser(ProgressListener listener) throws SocketException, URISyntaxException
   :outertype: UdpProgressParser

UdpProgressParser
^^^^^^^^^^^^^^^^^

.. java:constructor:: public UdpProgressParser(ProgressListener listener, int port, InetAddress addr) throws SocketException, URISyntaxException
   :outertype: UdpProgressParser

Methods
-------
getRunnable
^^^^^^^^^^^

.. java:method:: @Override protected Runnable getRunnable(CountDownLatch startSignal)
   :outertype: UdpProgressParser

getThreadName
^^^^^^^^^^^^^

.. java:method:: protected String getThreadName()
   :outertype: UdpProgressParser

getUri
^^^^^^

.. java:method:: public URI getUri()
   :outertype: UdpProgressParser

stop
^^^^

.. java:method:: @Override public synchronized void stop() throws IOException
   :outertype: UdpProgressParser

