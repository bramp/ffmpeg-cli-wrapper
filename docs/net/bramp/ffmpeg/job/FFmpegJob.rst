.. java:import:: net.bramp.ffmpeg FFmpeg

.. java:import:: net.bramp.ffmpeg.progress ProgressListener

.. java:import:: javax.annotation Nullable

FFmpegJob
=========

.. java:package:: net.bramp.ffmpeg.job
   :noindex:

.. java:type:: public abstract class FFmpegJob implements Runnable

   :author: bramp

Fields
------
ffmpeg
^^^^^^

.. java:field:: final FFmpeg ffmpeg
   :outertype: FFmpegJob

listener
^^^^^^^^

.. java:field:: final ProgressListener listener
   :outertype: FFmpegJob

state
^^^^^

.. java:field::  State state
   :outertype: FFmpegJob

Constructors
------------
FFmpegJob
^^^^^^^^^

.. java:constructor:: public FFmpegJob(FFmpeg ffmpeg)
   :outertype: FFmpegJob

FFmpegJob
^^^^^^^^^

.. java:constructor:: public FFmpegJob(FFmpeg ffmpeg, ProgressListener listener)
   :outertype: FFmpegJob

Methods
-------
getState
^^^^^^^^

.. java:method:: public State getState()
   :outertype: FFmpegJob

