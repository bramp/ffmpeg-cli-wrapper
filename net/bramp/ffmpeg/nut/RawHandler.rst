.. java:import:: javax.sound.sampled AudioFormat

.. java:import:: javax.sound.sampled AudioInputStream

.. java:import:: java.awt.image BufferedImage

.. java:import:: java.io ByteArrayInputStream

.. java:import:: java.io InputStream

.. java:import:: java.nio ByteBuffer

.. java:import:: java.nio ByteOrder

.. java:import:: java.nio IntBuffer

.. java:import:: java.util Arrays

RawHandler
==========

.. java:package:: net.bramp.ffmpeg.nut
   :noindex:

.. java:type:: public class RawHandler

Methods
-------
streamToAudioFormat
^^^^^^^^^^^^^^^^^^^

.. java:method:: public static AudioFormat streamToAudioFormat(StreamHeaderPacket header)
   :outertype: RawHandler

   Parses a FourCC into a AudioEncoding based on the following rules: "ALAW" = A-LAW "ULAW" = MU-LAW P[type][interleaving][bits] = little-endian PCM [bits][interleaving][type]P = big-endian PCM Where:   [type] is S for signed integer, U for unsigned integer, F for IEEE float   [interleaving] is D for default, P is for planar.   [bits] is 8/16/24/32

   :param header: The stream's header.
   :return: The AudioFormat matching this header.

toAudioInputStream
^^^^^^^^^^^^^^^^^^

.. java:method:: public static AudioInputStream toAudioInputStream(Frame frame)
   :outertype: RawHandler

toBufferedImage
^^^^^^^^^^^^^^^

.. java:method:: public static BufferedImage toBufferedImage(Frame frame)
   :outertype: RawHandler

