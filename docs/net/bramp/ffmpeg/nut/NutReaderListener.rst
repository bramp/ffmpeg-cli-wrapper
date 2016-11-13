NutReaderListener
=================

.. java:package:: net.bramp.ffmpeg.nut
   :noindex:

.. java:type:: public interface NutReaderListener

Methods
-------
frame
^^^^^

.. java:method::  void frame(Frame frame)
   :outertype: NutReaderListener

   Executes when a new frame is found.

   :param frame: A single Frame

stream
^^^^^^

.. java:method::  void stream(Stream stream)
   :outertype: NutReaderListener

   Executes when a new stream is found.

   :param stream: The stream

