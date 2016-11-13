.. java:import:: net.bramp.ffmpeg.builder FFmpegBuilder

.. java:import:: net.bramp.ffmpeg.job FFmpegJob

.. java:import:: net.bramp.ffmpeg.job SinglePassFFmpegJob

.. java:import:: net.bramp.ffmpeg.job TwoPassFFmpegJob

.. java:import:: net.bramp.ffmpeg.progress ProgressListener

.. java:import:: java.io IOException

FFmpegExecutor
==============

.. java:package:: net.bramp.ffmpeg
   :noindex:

.. java:type:: public class FFmpegExecutor

Fields
------
ffmpeg
^^^^^^

.. java:field:: final FFmpeg ffmpeg
   :outertype: FFmpegExecutor

ffprobe
^^^^^^^

.. java:field:: final FFprobe ffprobe
   :outertype: FFmpegExecutor

Constructors
------------
FFmpegExecutor
^^^^^^^^^^^^^^

.. java:constructor:: public FFmpegExecutor() throws IOException
   :outertype: FFmpegExecutor

FFmpegExecutor
^^^^^^^^^^^^^^

.. java:constructor:: public FFmpegExecutor(FFmpeg ffmpeg) throws IOException
   :outertype: FFmpegExecutor

FFmpegExecutor
^^^^^^^^^^^^^^

.. java:constructor:: public FFmpegExecutor(FFmpeg ffmpeg, FFprobe ffprobe)
   :outertype: FFmpegExecutor

Methods
-------
createJob
^^^^^^^^^

.. java:method:: public FFmpegJob createJob(FFmpegBuilder builder)
   :outertype: FFmpegExecutor

createJob
^^^^^^^^^

.. java:method:: public FFmpegJob createJob(FFmpegBuilder builder, ProgressListener listener)
   :outertype: FFmpegExecutor

createTwoPassJob
^^^^^^^^^^^^^^^^

.. java:method:: public FFmpegJob createTwoPassJob(FFmpegBuilder builder)
   :outertype: FFmpegExecutor

   Creates a two pass job, which will execute FFmpeg twice to produce a better quality output. More info: https://trac.ffmpeg.org/wiki/x264EncodingGuide#twopass

   :param builder: The FFmpegBuilder
   :return: A new two-pass FFmpegJob

