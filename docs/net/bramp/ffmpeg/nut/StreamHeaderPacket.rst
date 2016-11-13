.. java:import:: com.google.common.base MoreObjects

.. java:import:: org.apache.commons.lang3.math Fraction

.. java:import:: java.io IOException

StreamHeaderPacket
==================

.. java:package:: net.bramp.ffmpeg.nut
   :noindex:

.. java:type::  class StreamHeaderPacket extends Packet

Fields
------
AUDIO
^^^^^

.. java:field:: public static final int AUDIO
   :outertype: StreamHeaderPacket

SUBTITLE
^^^^^^^^

.. java:field:: public static final int SUBTITLE
   :outertype: StreamHeaderPacket

USER_DATA
^^^^^^^^^

.. java:field:: public static final int USER_DATA
   :outertype: StreamHeaderPacket

VIDEO
^^^^^

.. java:field:: public static final int VIDEO
   :outertype: StreamHeaderPacket

channels
^^^^^^^^

.. java:field::  int channels
   :outertype: StreamHeaderPacket

codecSpecificData
^^^^^^^^^^^^^^^^^

.. java:field::  byte[] codecSpecificData
   :outertype: StreamHeaderPacket

colorspaceType
^^^^^^^^^^^^^^

.. java:field::  long colorspaceType
   :outertype: StreamHeaderPacket

decodeDelay
^^^^^^^^^^^

.. java:field::  long decodeDelay
   :outertype: StreamHeaderPacket

flags
^^^^^

.. java:field::  long flags
   :outertype: StreamHeaderPacket

fourcc
^^^^^^

.. java:field::  byte[] fourcc
   :outertype: StreamHeaderPacket

height
^^^^^^

.. java:field::  int height
   :outertype: StreamHeaderPacket

id
^^

.. java:field::  int id
   :outertype: StreamHeaderPacket

maxPtsDistance
^^^^^^^^^^^^^^

.. java:field::  int maxPtsDistance
   :outertype: StreamHeaderPacket

msbPtsShift
^^^^^^^^^^^

.. java:field::  int msbPtsShift
   :outertype: StreamHeaderPacket

sampleHeight
^^^^^^^^^^^^

.. java:field::  int sampleHeight
   :outertype: StreamHeaderPacket

sampleRate
^^^^^^^^^^

.. java:field::  Fraction sampleRate
   :outertype: StreamHeaderPacket

sampleWidth
^^^^^^^^^^^

.. java:field::  int sampleWidth
   :outertype: StreamHeaderPacket

timeBaseId
^^^^^^^^^^

.. java:field::  int timeBaseId
   :outertype: StreamHeaderPacket

type
^^^^

.. java:field::  long type
   :outertype: StreamHeaderPacket

width
^^^^^

.. java:field::  int width
   :outertype: StreamHeaderPacket

Methods
-------
readBody
^^^^^^^^

.. java:method:: protected void readBody(NutDataInputStream in) throws IOException
   :outertype: StreamHeaderPacket

toString
^^^^^^^^

.. java:method:: @Override public String toString()
   :outertype: StreamHeaderPacket

