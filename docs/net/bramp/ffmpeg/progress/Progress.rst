.. java:import:: com.google.common.base MoreObjects

.. java:import:: net.bramp.ffmpeg FFmpegUtils

.. java:import:: org.apache.commons.lang3.math Fraction

.. java:import:: java.util Objects

Progress
========

.. java:package:: net.bramp.ffmpeg.progress
   :noindex:

.. java:type:: public class Progress

   TODO Change to be immutable

Fields
------
bitrate
^^^^^^^

.. java:field:: public long bitrate
   :outertype: Progress

drop_frames
^^^^^^^^^^^

.. java:field:: public long drop_frames
   :outertype: Progress

dup_frames
^^^^^^^^^^

.. java:field:: public long dup_frames
   :outertype: Progress

fps
^^^

.. java:field:: public Fraction fps
   :outertype: Progress

frame
^^^^^

.. java:field:: public long frame
   :outertype: Progress

out_time_ms
^^^^^^^^^^^

.. java:field:: public long out_time_ms
   :outertype: Progress

progress
^^^^^^^^

.. java:field:: public String progress
   :outertype: Progress

speed
^^^^^

.. java:field:: public float speed
   :outertype: Progress

total_size
^^^^^^^^^^

.. java:field:: public long total_size
   :outertype: Progress

Constructors
------------
Progress
^^^^^^^^

.. java:constructor:: public Progress()
   :outertype: Progress

Progress
^^^^^^^^

.. java:constructor:: public Progress(long frame, float fps, long bitrate, long total_size, long out_time_ms, long dup_frames, long drop_frames, float speed, String progress)
   :outertype: Progress

Methods
-------
equals
^^^^^^

.. java:method:: @Override public boolean equals(Object o)
   :outertype: Progress

hashCode
^^^^^^^^

.. java:method:: @Override public int hashCode()
   :outertype: Progress

isEnd
^^^^^

.. java:method:: public boolean isEnd()
   :outertype: Progress

parseLine
^^^^^^^^^

.. java:method:: protected boolean parseLine(String line)
   :outertype: Progress

   Parses values from the line, into this object.

   :param line: A single line of output from ffmpeg
   :return: true if the record is finished

toString
^^^^^^^^

.. java:method:: @Override public String toString()
   :outertype: Progress

