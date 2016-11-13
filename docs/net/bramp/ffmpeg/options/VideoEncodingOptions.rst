.. java:import:: org.apache.commons.lang3.math Fraction

.. java:import:: java.beans ConstructorProperties

VideoEncodingOptions
====================

.. java:package:: net.bramp.ffmpeg.options
   :noindex:

.. java:type:: public class VideoEncodingOptions

   Encoding options for video

   :author: bramp

Fields
------
bit_rate
^^^^^^^^

.. java:field:: public final long bit_rate
   :outertype: VideoEncodingOptions

codec
^^^^^

.. java:field:: public final String codec
   :outertype: VideoEncodingOptions

enabled
^^^^^^^

.. java:field:: public final boolean enabled
   :outertype: VideoEncodingOptions

filter
^^^^^^

.. java:field:: public final String filter
   :outertype: VideoEncodingOptions

frame_rate
^^^^^^^^^^

.. java:field:: public final Fraction frame_rate
   :outertype: VideoEncodingOptions

frames
^^^^^^

.. java:field:: public final Integer frames
   :outertype: VideoEncodingOptions

height
^^^^^^

.. java:field:: public final int height
   :outertype: VideoEncodingOptions

preset
^^^^^^

.. java:field:: public final String preset
   :outertype: VideoEncodingOptions

width
^^^^^

.. java:field:: public final int width
   :outertype: VideoEncodingOptions

Constructors
------------
VideoEncodingOptions
^^^^^^^^^^^^^^^^^^^^

.. java:constructor:: @ConstructorProperties public VideoEncodingOptions(boolean enabled, String codec, Fraction frame_rate, int width, int height, long bit_rate, Integer frames, String filter, String preset)
   :outertype: VideoEncodingOptions

