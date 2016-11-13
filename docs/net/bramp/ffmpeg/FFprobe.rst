.. java:import:: com.google.common.base MoreObjects

.. java:import:: com.google.common.collect ImmutableList

.. java:import:: com.google.gson Gson

.. java:import:: net.bramp.ffmpeg.io LoggingFilterReader

.. java:import:: net.bramp.ffmpeg.probe FFmpegProbeResult

.. java:import:: org.slf4j Logger

.. java:import:: org.slf4j LoggerFactory

.. java:import:: javax.annotation Nonnull

.. java:import:: javax.annotation Nullable

.. java:import:: java.io IOException

.. java:import:: java.io Reader

.. java:import:: java.util List

FFprobe
=======

.. java:package:: net.bramp.ffmpeg
   :noindex:

.. java:type:: public class FFprobe extends FFcommon

   Wrapper around FFprobe

   :author: bramp

Fields
------
DEFAULT_PATH
^^^^^^^^^^^^

.. java:field:: static final String DEFAULT_PATH
   :outertype: FFprobe

FFPROBE
^^^^^^^

.. java:field:: static final String FFPROBE
   :outertype: FFprobe

LOG
^^^

.. java:field:: static final Logger LOG
   :outertype: FFprobe

gson
^^^^

.. java:field:: static final Gson gson
   :outertype: FFprobe

Constructors
------------
FFprobe
^^^^^^^

.. java:constructor:: public FFprobe() throws IOException
   :outertype: FFprobe

FFprobe
^^^^^^^

.. java:constructor:: public FFprobe(ProcessFunction runFunction) throws IOException
   :outertype: FFprobe

FFprobe
^^^^^^^

.. java:constructor:: public FFprobe(String path, ProcessFunction runFunction)
   :outertype: FFprobe

Methods
-------
isFFprobe
^^^^^^^^^

.. java:method:: public boolean isFFprobe() throws IOException
   :outertype: FFprobe

   Returns true if the binary we are using is the true ffprobe. This is to avoid conflict with avprobe (from the libav project), that some symlink to ffprobe.

   :throws IOException: If a I/O error occurs while executing ffprobe.
   :return: true iff this is the official ffprobe binary.

probe
^^^^^

.. java:method:: public FFmpegProbeResult probe(String mediaPath) throws IOException
   :outertype: FFprobe

probe
^^^^^

.. java:method:: public FFmpegProbeResult probe(String mediaPath, String userAgent) throws IOException
   :outertype: FFprobe

run
^^^

.. java:method:: public void run(List<String> args) throws IOException
   :outertype: FFprobe

