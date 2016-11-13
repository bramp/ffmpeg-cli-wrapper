.. java:import:: net.bramp.ffmpeg.builder FFmpegOutputBuilder

.. java:import:: net.bramp.ffmpeg.options AudioEncodingOptions

.. java:import:: net.bramp.ffmpeg.options EncodingOptions

.. java:import:: net.bramp.ffmpeg.options MainEncodingOptions

.. java:import:: net.bramp.ffmpeg.options VideoEncodingOptions

.. java:import:: org.modelmapper ModelMapper

.. java:import:: org.modelmapper TypeMap

.. java:import:: org.modelmapper.config Configuration

.. java:import:: org.modelmapper.convention NameTokenizers

Mapper.AudioWrapper
===================

.. java:package:: net.bramp.ffmpeg.modelmapper
   :noindex:

.. java:type:: static class AudioWrapper
   :outertype: Mapper

   Simple wrapper object, to inject the word "audio" in the property name

Fields
------
audio
^^^^^

.. java:field:: public final AudioEncodingOptions audio
   :outertype: Mapper.AudioWrapper

Constructors
------------
AudioWrapper
^^^^^^^^^^^^

.. java:constructor::  AudioWrapper(AudioEncodingOptions audio)
   :outertype: Mapper.AudioWrapper

