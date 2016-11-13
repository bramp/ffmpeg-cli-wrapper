.. java:import:: java.io Closeable

.. java:import:: java.io IOException

.. java:import:: java.net URI

ProgressParser
==============

.. java:package:: net.bramp.ffmpeg.progress
   :noindex:

.. java:type:: public interface ProgressParser extends Closeable

   Parses the FFmpeg progress fields

Methods
-------
getUri
^^^^^^

.. java:method::  URI getUri()
   :outertype: ProgressParser

   The URL to parse to FFmpeg to communicate with this parser

   :return: The URI to communicate with FFmpeg.

start
^^^^^

.. java:method::  void start() throws IOException
   :outertype: ProgressParser

stop
^^^^

.. java:method::  void stop() throws IOException
   :outertype: ProgressParser

