.. java:import:: com.google.common.net InetAddresses

.. java:import:: java.io IOException

.. java:import:: java.net InetAddress

.. java:import:: java.net URI

.. java:import:: java.net URISyntaxException

.. java:import:: java.util.concurrent CountDownLatch

AbstractSocketProgressParser
============================

.. java:package:: net.bramp.ffmpeg.progress
   :noindex:

.. java:type:: public abstract class AbstractSocketProgressParser implements ProgressParser

Fields
------
parser
^^^^^^

.. java:field:: final StreamProgressParser parser
   :outertype: AbstractSocketProgressParser

thread
^^^^^^

.. java:field::  Thread thread
   :outertype: AbstractSocketProgressParser

Constructors
------------
AbstractSocketProgressParser
^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public AbstractSocketProgressParser(ProgressListener listener)
   :outertype: AbstractSocketProgressParser

Methods
-------
close
^^^^^

.. java:method:: @Override public void close() throws IOException
   :outertype: AbstractSocketProgressParser

createUri
^^^^^^^^^

.. java:method:: static URI createUri(String scheme, InetAddress address, int port) throws URISyntaxException
   :outertype: AbstractSocketProgressParser

   Creates a URL to parse to FFmpeg based on the scheme, address and port. TODO Move this method to somewhere better.

   :param scheme:
   :param address:
   :param port:
   :throws URISyntaxException:

getRunnable
^^^^^^^^^^^

.. java:method:: protected abstract Runnable getRunnable(CountDownLatch startSignal)
   :outertype: AbstractSocketProgressParser

getThreadName
^^^^^^^^^^^^^

.. java:method:: protected abstract String getThreadName()
   :outertype: AbstractSocketProgressParser

start
^^^^^

.. java:method:: public synchronized void start()
   :outertype: AbstractSocketProgressParser

   :throws IllegalThreadStateException: if the parser was already started.

stop
^^^^

.. java:method:: @Override public void stop() throws IOException
   :outertype: AbstractSocketProgressParser

