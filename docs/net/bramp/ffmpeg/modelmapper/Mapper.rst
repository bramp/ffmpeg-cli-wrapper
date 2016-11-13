.. java:import:: net.bramp.ffmpeg.builder FFmpegOutputBuilder

.. java:import:: net.bramp.ffmpeg.options AudioEncodingOptions

.. java:import:: net.bramp.ffmpeg.options EncodingOptions

.. java:import:: net.bramp.ffmpeg.options MainEncodingOptions

.. java:import:: net.bramp.ffmpeg.options VideoEncodingOptions

.. java:import:: org.modelmapper ModelMapper

.. java:import:: org.modelmapper TypeMap

.. java:import:: org.modelmapper.config Configuration

.. java:import:: org.modelmapper.convention NameTokenizers

Mapper
======

.. java:package:: net.bramp.ffmpeg.modelmapper
   :noindex:

.. java:type:: public class Mapper

   Copies values from one type of object to another

   :author: bramp

Methods
-------
map
^^^

.. java:method:: public static void map(MainEncodingOptions opts, FFmpegOutputBuilder dest)
   :outertype: Mapper

map
^^^

.. java:method:: public static void map(AudioEncodingOptions opts, FFmpegOutputBuilder dest)
   :outertype: Mapper

map
^^^

.. java:method:: public static void map(VideoEncodingOptions opts, FFmpegOutputBuilder dest)
   :outertype: Mapper

map
^^^

.. java:method:: public static void map(EncodingOptions opts, FFmpegOutputBuilder dest)
   :outertype: Mapper

