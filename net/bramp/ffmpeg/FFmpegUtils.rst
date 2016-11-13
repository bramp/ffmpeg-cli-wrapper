.. java:import:: com.google.gson Gson

.. java:import:: com.google.gson GsonBuilder

.. java:import:: net.bramp.commons.lang3.math.gson FractionAdapter

.. java:import:: net.bramp.ffmpeg.gson LowercaseEnumTypeAdapterFactory

.. java:import:: net.bramp.ffmpeg.gson NamedBitsetAdapter

.. java:import:: net.bramp.ffmpeg.probe FFmpegDisposition

.. java:import:: org.apache.commons.lang3.math Fraction

.. java:import:: java.util.regex Matcher

.. java:import:: java.util.regex Pattern

FFmpegUtils
===========

.. java:package:: net.bramp.ffmpeg
   :noindex:

.. java:type:: public final class FFmpegUtils

   Helper class with commonly used methods

Fields
------
BITRATE_REGEX
^^^^^^^^^^^^^

.. java:field:: static final Pattern BITRATE_REGEX
   :outertype: FFmpegUtils

gson
^^^^

.. java:field:: static final Gson gson
   :outertype: FFmpegUtils

Constructors
------------
FFmpegUtils
^^^^^^^^^^^

.. java:constructor::  FFmpegUtils()
   :outertype: FFmpegUtils

Methods
-------
getGson
^^^^^^^

.. java:method:: static Gson getGson()
   :outertype: FFmpegUtils

millisecondsToString
^^^^^^^^^^^^^^^^^^^^

.. java:method:: public static String millisecondsToString(long milliseconds)
   :outertype: FFmpegUtils

   Convert milliseconds to "hh:mm:ss.ms" String representation.

   :param milliseconds: time duration in milliseconds
   :return: time duration in human-readable format

parseBitrate
^^^^^^^^^^^^

.. java:method:: public static long parseBitrate(String bitrate)
   :outertype: FFmpegUtils

   Converts a string representation of bitrate to a long of bits per second

   :param bitrate: in the form of 12.3kbits/s
   :return: the bitrate in bits per second.

