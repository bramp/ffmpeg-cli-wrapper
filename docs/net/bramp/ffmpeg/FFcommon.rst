.. java:import:: com.google.common.base Preconditions

.. java:import:: com.google.common.base Strings

.. java:import:: com.google.common.collect ImmutableList

.. java:import:: com.google.common.io CharStreams

.. java:import:: net.bramp.ffmpeg.io ProcessUtils

.. java:import:: javax.annotation Nonnull

.. java:import:: java.io BufferedReader

.. java:import:: java.io IOException

.. java:import:: java.io InputStreamReader

.. java:import:: java.nio.charset StandardCharsets

.. java:import:: java.util List

.. java:import:: java.util.concurrent TimeUnit

.. java:import:: java.util.concurrent TimeoutException

FFcommon
========

.. java:package:: net.bramp.ffmpeg
   :noindex:

.. java:type:: abstract class FFcommon

   Private class to contain common methods for both FFmpeg and FFprobe.

Fields
------
path
^^^^

.. java:field:: final String path
   :outertype: FFcommon

   Path to the binary (e.g. /usr/bin/ffmpeg)

runFunc
^^^^^^^

.. java:field:: final ProcessFunction runFunc
   :outertype: FFcommon

   Function to run FFmpeg. We define it like this so we can swap it out (during testing)

version
^^^^^^^

.. java:field::  String version
   :outertype: FFcommon

   Version string

Constructors
------------
FFcommon
^^^^^^^^

.. java:constructor:: public FFcommon(String path)
   :outertype: FFcommon

FFcommon
^^^^^^^^

.. java:constructor:: protected FFcommon(String path, ProcessFunction runFunction)
   :outertype: FFcommon

Methods
-------
getPath
^^^^^^^

.. java:method:: public String getPath()
   :outertype: FFcommon

path
^^^^

.. java:method:: public List<String> path(List<String> args) throws IOException
   :outertype: FFcommon

run
^^^

.. java:method:: public void run(List<String> args) throws IOException
   :outertype: FFcommon

   Runs ffmpeg with the supplied args. Blocking until finished.

   :param args: The arguments to pass to the binary.
   :throws IOException: If there is a problem executing the binary.

throwOnError
^^^^^^^^^^^^

.. java:method:: protected void throwOnError(Process p) throws IOException
   :outertype: FFcommon

version
^^^^^^^

.. java:method:: @Nonnull public synchronized String version() throws IOException
   :outertype: FFcommon

wrapInReader
^^^^^^^^^^^^

.. java:method:: protected BufferedReader wrapInReader(Process p)
   :outertype: FFcommon

