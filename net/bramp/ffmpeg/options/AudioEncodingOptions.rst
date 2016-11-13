.. java:import:: java.beans ConstructorProperties

AudioEncodingOptions
====================

.. java:package:: net.bramp.ffmpeg.options
   :noindex:

.. java:type:: public class AudioEncodingOptions

   Encoding options for audio

   :author: bramp

Fields
------
bit_rate
^^^^^^^^

.. java:field:: public final long bit_rate
   :outertype: AudioEncodingOptions

channels
^^^^^^^^

.. java:field:: public final int channels
   :outertype: AudioEncodingOptions

codec
^^^^^

.. java:field:: public final String codec
   :outertype: AudioEncodingOptions

enabled
^^^^^^^

.. java:field:: public final boolean enabled
   :outertype: AudioEncodingOptions

quality
^^^^^^^

.. java:field:: public final Integer quality
   :outertype: AudioEncodingOptions

sample_format
^^^^^^^^^^^^^

.. java:field:: public final String sample_format
   :outertype: AudioEncodingOptions

sample_rate
^^^^^^^^^^^

.. java:field:: public final int sample_rate
   :outertype: AudioEncodingOptions

Constructors
------------
AudioEncodingOptions
^^^^^^^^^^^^^^^^^^^^

.. java:constructor:: @ConstructorProperties public AudioEncodingOptions(boolean enabled, String codec, int channels, int sample_rate, String sample_format, long bit_rate, Integer quality)
   :outertype: AudioEncodingOptions

