.. java:import:: org.apache.commons.lang3.math Fraction

.. java:import:: java.io IOException

Stream
======

.. java:package:: net.bramp.ffmpeg.nut
   :noindex:

.. java:type:: public class Stream

Fields
------
header
^^^^^^

.. java:field:: final StreamHeaderPacket header
   :outertype: Stream

last_pts
^^^^^^^^

.. java:field::  long last_pts
   :outertype: Stream

timeBase
^^^^^^^^

.. java:field:: final Fraction timeBase
   :outertype: Stream

Constructors
------------
Stream
^^^^^^

.. java:constructor:: public Stream(MainHeaderPacket header, StreamHeaderPacket streamHeader) throws IOException
   :outertype: Stream

