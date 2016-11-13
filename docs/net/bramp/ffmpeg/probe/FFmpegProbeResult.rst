.. java:import:: com.google.common.collect ImmutableList

.. java:import:: edu.umd.cs.findbugs.annotations SuppressFBWarnings

.. java:import:: java.util List

FFmpegProbeResult
=================

.. java:package:: net.bramp.ffmpeg.probe
   :noindex:

.. java:type:: @SuppressFBWarnings public class FFmpegProbeResult

   TODO Make this immutable

Fields
------
error
^^^^^

.. java:field:: public FFmpegError error
   :outertype: FFmpegProbeResult

format
^^^^^^

.. java:field:: public FFmpegFormat format
   :outertype: FFmpegProbeResult

streams
^^^^^^^

.. java:field:: public List<FFmpegStream> streams
   :outertype: FFmpegProbeResult

Methods
-------
getError
^^^^^^^^

.. java:method:: public FFmpegError getError()
   :outertype: FFmpegProbeResult

getFormat
^^^^^^^^^

.. java:method:: public FFmpegFormat getFormat()
   :outertype: FFmpegProbeResult

getStreams
^^^^^^^^^^

.. java:method:: public List<FFmpegStream> getStreams()
   :outertype: FFmpegProbeResult

hasError
^^^^^^^^

.. java:method:: public boolean hasError()
   :outertype: FFmpegProbeResult

