.. java:import:: java.beans ConstructorProperties

EncodingOptions
===============

.. java:package:: net.bramp.ffmpeg.options
   :noindex:

.. java:type:: public class EncodingOptions

   :author: bramp

Fields
------
audio
^^^^^

.. java:field:: public final AudioEncodingOptions audio
   :outertype: EncodingOptions

main
^^^^

.. java:field:: public final MainEncodingOptions main
   :outertype: EncodingOptions

video
^^^^^

.. java:field:: public final VideoEncodingOptions video
   :outertype: EncodingOptions

Constructors
------------
EncodingOptions
^^^^^^^^^^^^^^^

.. java:constructor:: @ConstructorProperties public EncodingOptions(MainEncodingOptions main, AudioEncodingOptions audio, VideoEncodingOptions video)
   :outertype: EncodingOptions

Methods
-------
getAudio
^^^^^^^^

.. java:method:: public AudioEncodingOptions getAudio()
   :outertype: EncodingOptions

getMain
^^^^^^^

.. java:method:: public MainEncodingOptions getMain()
   :outertype: EncodingOptions

getVideo
^^^^^^^^

.. java:method:: public VideoEncodingOptions getVideo()
   :outertype: EncodingOptions

