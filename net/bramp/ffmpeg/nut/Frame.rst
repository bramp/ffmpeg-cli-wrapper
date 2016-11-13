.. java:import:: com.google.common.base MoreObjects

.. java:import:: org.apache.commons.lang3.math Fraction

.. java:import:: java.io IOException

.. java:import:: java.nio.charset StandardCharsets

.. java:import:: java.util Map

.. java:import:: java.util TreeMap

Frame
=====

.. java:package:: net.bramp.ffmpeg.nut
   :noindex:

.. java:type:: public class Frame

   A video or audio frame

Fields
------
FLAG_CHECKSUM
^^^^^^^^^^^^^

.. java:field:: static final long FLAG_CHECKSUM
   :outertype: Frame

FLAG_CODED
^^^^^^^^^^

.. java:field:: static final long FLAG_CODED
   :outertype: Frame

FLAG_CODED_PTS
^^^^^^^^^^^^^^

.. java:field:: static final long FLAG_CODED_PTS
   :outertype: Frame

FLAG_EOR
^^^^^^^^

.. java:field:: static final long FLAG_EOR
   :outertype: Frame

FLAG_HEADER_IDX
^^^^^^^^^^^^^^^

.. java:field:: static final long FLAG_HEADER_IDX
   :outertype: Frame

FLAG_INVALID
^^^^^^^^^^^^

.. java:field:: static final long FLAG_INVALID
   :outertype: Frame

FLAG_KEY
^^^^^^^^

.. java:field:: static final long FLAG_KEY
   :outertype: Frame

FLAG_MATCH_TIME
^^^^^^^^^^^^^^^

.. java:field:: static final long FLAG_MATCH_TIME
   :outertype: Frame

FLAG_RESERVED
^^^^^^^^^^^^^

.. java:field:: static final long FLAG_RESERVED
   :outertype: Frame

FLAG_SIZE_MSB
^^^^^^^^^^^^^

.. java:field:: static final long FLAG_SIZE_MSB
   :outertype: Frame

FLAG_SM_DATA
^^^^^^^^^^^^

.. java:field:: static final long FLAG_SM_DATA
   :outertype: Frame

FLAG_STREAM_ID
^^^^^^^^^^^^^^

.. java:field:: static final long FLAG_STREAM_ID
   :outertype: Frame

data
^^^^

.. java:field::  byte[] data
   :outertype: Frame

flags
^^^^^

.. java:field::  long flags
   :outertype: Frame

metaData
^^^^^^^^

.. java:field::  Map<String, Object> metaData
   :outertype: Frame

pts
^^^

.. java:field::  long pts
   :outertype: Frame

sideData
^^^^^^^^

.. java:field::  Map<String, Object> sideData
   :outertype: Frame

stream
^^^^^^

.. java:field::  Stream stream
   :outertype: Frame

Methods
-------
read
^^^^

.. java:method:: public void read(NutReader nut, NutDataInputStream in, int code) throws IOException
   :outertype: Frame

readMetaData
^^^^^^^^^^^^

.. java:method:: protected Map<String, Object> readMetaData(NutDataInputStream in) throws IOException
   :outertype: Frame

toString
^^^^^^^^

.. java:method:: @Override public String toString()
   :outertype: Frame

