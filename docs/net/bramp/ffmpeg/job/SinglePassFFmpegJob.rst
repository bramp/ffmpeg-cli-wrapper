.. java:import:: com.google.common.base Throwables

.. java:import:: net.bramp.ffmpeg FFmpeg

.. java:import:: net.bramp.ffmpeg.builder FFmpegBuilder

.. java:import:: net.bramp.ffmpeg.progress ProgressListener

.. java:import:: javax.annotation Nullable

SinglePassFFmpegJob
===================

.. java:package:: net.bramp.ffmpeg.job
   :noindex:

.. java:type:: public class SinglePassFFmpegJob extends FFmpegJob

Fields
------
builder
^^^^^^^

.. java:field:: public final FFmpegBuilder builder
   :outertype: SinglePassFFmpegJob

Constructors
------------
SinglePassFFmpegJob
^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public SinglePassFFmpegJob(FFmpeg ffmpeg, FFmpegBuilder builder)
   :outertype: SinglePassFFmpegJob

SinglePassFFmpegJob
^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public SinglePassFFmpegJob(FFmpeg ffmpeg, FFmpegBuilder builder, ProgressListener listener)
   :outertype: SinglePassFFmpegJob

Methods
-------
run
^^^

.. java:method:: public void run()
   :outertype: SinglePassFFmpegJob

