.. java:import:: com.google.common.base Preconditions

.. java:import:: com.google.common.collect ImmutableList

.. java:import:: net.bramp.ffmpeg FFmpegUtils

.. java:import:: net.bramp.ffmpeg.probe FFmpegProbeResult

.. java:import:: org.slf4j Logger

.. java:import:: org.slf4j LoggerFactory

.. java:import:: java.net URI

.. java:import:: java.util ArrayList

.. java:import:: java.util List

.. java:import:: java.util Map

.. java:import:: java.util TreeMap

.. java:import:: java.util.concurrent TimeUnit

FFmpegBuilder.Verbosity
=======================

.. java:package:: net.bramp.ffmpeg.builder
   :noindex:

.. java:type:: public enum Verbosity
   :outertype: FFmpegBuilder

   Log level options: https://ffmpeg.org/ffmpeg.html#Generic-options

Enum Constants
--------------
DEBUG
^^^^^

.. java:field:: public static final FFmpegBuilder.Verbosity DEBUG
   :outertype: FFmpegBuilder.Verbosity

ERROR
^^^^^

.. java:field:: public static final FFmpegBuilder.Verbosity ERROR
   :outertype: FFmpegBuilder.Verbosity

FATAL
^^^^^

.. java:field:: public static final FFmpegBuilder.Verbosity FATAL
   :outertype: FFmpegBuilder.Verbosity

INFO
^^^^

.. java:field:: public static final FFmpegBuilder.Verbosity INFO
   :outertype: FFmpegBuilder.Verbosity

PANIC
^^^^^

.. java:field:: public static final FFmpegBuilder.Verbosity PANIC
   :outertype: FFmpegBuilder.Verbosity

QUIET
^^^^^

.. java:field:: public static final FFmpegBuilder.Verbosity QUIET
   :outertype: FFmpegBuilder.Verbosity

VERBOSE
^^^^^^^

.. java:field:: public static final FFmpegBuilder.Verbosity VERBOSE
   :outertype: FFmpegBuilder.Verbosity

WARNING
^^^^^^^

.. java:field:: public static final FFmpegBuilder.Verbosity WARNING
   :outertype: FFmpegBuilder.Verbosity

