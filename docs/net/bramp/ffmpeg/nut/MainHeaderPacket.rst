.. java:import:: com.google.common.base MoreObjects

.. java:import:: org.apache.commons.lang3.math Fraction

.. java:import:: java.io IOException

.. java:import:: java.util ArrayList

.. java:import:: java.util List

MainHeaderPacket
================

.. java:package:: net.bramp.ffmpeg.nut
   :noindex:

.. java:type::  class MainHeaderPacket extends Packet

Fields
------
BROADCAST_MODE
^^^^^^^^^^^^^^

.. java:field:: public static final int BROADCAST_MODE
   :outertype: MainHeaderPacket

elision
^^^^^^^

.. java:field:: final List<byte[]> elision
   :outertype: MainHeaderPacket

flags
^^^^^

.. java:field::  long flags
   :outertype: MainHeaderPacket

frameCodes
^^^^^^^^^^

.. java:field:: final List<FrameCode> frameCodes
   :outertype: MainHeaderPacket

maxDistance
^^^^^^^^^^^

.. java:field::  long maxDistance
   :outertype: MainHeaderPacket

minorVersion
^^^^^^^^^^^^

.. java:field::  long minorVersion
   :outertype: MainHeaderPacket

streamCount
^^^^^^^^^^^

.. java:field::  int streamCount
   :outertype: MainHeaderPacket

timeBase
^^^^^^^^

.. java:field::  Fraction[] timeBase
   :outertype: MainHeaderPacket

version
^^^^^^^

.. java:field::  long version
   :outertype: MainHeaderPacket

Constructors
------------
MainHeaderPacket
^^^^^^^^^^^^^^^^

.. java:constructor:: public MainHeaderPacket()
   :outertype: MainHeaderPacket

Methods
-------
readBody
^^^^^^^^

.. java:method:: protected void readBody(NutDataInputStream in) throws IOException
   :outertype: MainHeaderPacket

toString
^^^^^^^^

.. java:method:: @Override public String toString()
   :outertype: MainHeaderPacket

