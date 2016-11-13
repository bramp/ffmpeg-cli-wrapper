.. java:import:: net.bramp.ffmpeg.builder FFmpegOutputBuilder

.. java:import:: net.bramp.ffmpeg.options AudioEncodingOptions

.. java:import:: net.bramp.ffmpeg.options EncodingOptions

.. java:import:: net.bramp.ffmpeg.options MainEncodingOptions

.. java:import:: net.bramp.ffmpeg.options VideoEncodingOptions

.. java:import:: org.modelmapper ModelMapper

.. java:import:: org.modelmapper TypeMap

.. java:import:: org.modelmapper.config Configuration

.. java:import:: org.modelmapper.convention NameTokenizers

Mapper.VideoWrapper
===================

.. java:package:: net.bramp.ffmpeg.modelmapper
   :noindex:

.. java:type:: static class VideoWrapper
   :outertype: Mapper

   Simple wrapper object, to inject the word "video" in the property name

Fields
------
video
^^^^^

.. java:field:: public final VideoEncodingOptions video
   :outertype: Mapper.VideoWrapper

Constructors
------------
VideoWrapper
^^^^^^^^^^^^

.. java:constructor::  VideoWrapper(VideoEncodingOptions video)
   :outertype: Mapper.VideoWrapper

