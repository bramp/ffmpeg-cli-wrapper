.. java:import:: java.io ByteArrayInputStream

.. java:import:: java.io IOException

.. java:import:: java.net DatagramPacket

.. java:import:: java.net DatagramSocket

.. java:import:: java.net SocketException

.. java:import:: java.util.concurrent CountDownLatch

UdpProgressParserRunnable
=========================

.. java:package:: net.bramp.ffmpeg.progress
   :noindex:

.. java:type::  class UdpProgressParserRunnable implements Runnable

Fields
------
MAX_PACKET_SIZE
^^^^^^^^^^^^^^^

.. java:field:: static final int MAX_PACKET_SIZE
   :outertype: UdpProgressParserRunnable

parser
^^^^^^

.. java:field:: final StreamProgressParser parser
   :outertype: UdpProgressParserRunnable

socket
^^^^^^

.. java:field:: final DatagramSocket socket
   :outertype: UdpProgressParserRunnable

startSignal
^^^^^^^^^^^

.. java:field:: final CountDownLatch startSignal
   :outertype: UdpProgressParserRunnable

Constructors
------------
UdpProgressParserRunnable
^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public UdpProgressParserRunnable(StreamProgressParser parser, DatagramSocket socket, CountDownLatch startSignal)
   :outertype: UdpProgressParserRunnable

Methods
-------
run
^^^

.. java:method:: @Override public void run()
   :outertype: UdpProgressParserRunnable

