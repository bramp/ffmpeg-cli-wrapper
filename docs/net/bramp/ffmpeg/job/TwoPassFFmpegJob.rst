.. java:import:: com.google.common.base Throwables

.. java:import:: net.bramp.ffmpeg FFmpeg

.. java:import:: net.bramp.ffmpeg.builder FFmpegBuilder

.. java:import:: net.bramp.ffmpeg.progress ProgressListener

.. java:import:: javax.annotation Nullable

.. java:import:: java.io IOException

.. java:import:: java.nio.file DirectoryStream

.. java:import:: java.nio.file Files

.. java:import:: java.nio.file Path

.. java:import:: java.nio.file Paths

.. java:import:: java.util UUID

TwoPassFFmpegJob
================

.. java:package:: net.bramp.ffmpeg.job
   :noindex:

.. java:type:: public class TwoPassFFmpegJob extends FFmpegJob

Fields
------
builder
^^^^^^^

.. java:field:: final FFmpegBuilder builder
   :outertype: TwoPassFFmpegJob

passlogPrefix
^^^^^^^^^^^^^

.. java:field:: final String passlogPrefix
   :outertype: TwoPassFFmpegJob

Constructors
------------
TwoPassFFmpegJob
^^^^^^^^^^^^^^^^

.. java:constructor:: public TwoPassFFmpegJob(FFmpeg ffmpeg, FFmpegBuilder builder)
   :outertype: TwoPassFFmpegJob

TwoPassFFmpegJob
^^^^^^^^^^^^^^^^

.. java:constructor:: public TwoPassFFmpegJob(FFmpeg ffmpeg, FFmpegBuilder builder, ProgressListener listener)
   :outertype: TwoPassFFmpegJob

Methods
-------
deletePassLog
^^^^^^^^^^^^^

.. java:method:: protected void deletePassLog() throws IOException
   :outertype: TwoPassFFmpegJob

run
^^^

.. java:method:: public void run()
   :outertype: TwoPassFFmpegJob

