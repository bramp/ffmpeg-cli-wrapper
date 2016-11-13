StreamSpecifier
===============

.. java:package:: net.bramp.ffmpeg.builder
   :noindex:

.. java:type:: public class StreamSpecifier

   https://ffmpeg.org/ffmpeg.html#Stream-specifiers

Fields
------
spec
^^^^

.. java:field:: final String spec
   :outertype: StreamSpecifier

Methods
-------
id
^^

.. java:method:: public static StreamSpecifier id(int stream_id)
   :outertype: StreamSpecifier

   Match the stream by stream id (e.g. PID in MPEG-TS container).

   :param stream_id: The stream id
   :return: A new StreamSpecifier

program
^^^^^^^

.. java:method:: public static StreamSpecifier program(int program_id)
   :outertype: StreamSpecifier

   Matches all streams in the program.

   :param program_id: The program id
   :return: A new StreamSpecifier

program
^^^^^^^

.. java:method:: public static StreamSpecifier program(int program_id, int stream_index)
   :outertype: StreamSpecifier

   Matches the stream with number stream_index in the program with the id program_id.

   :param program_id: The program id
   :param stream_index: The stream index
   :return: A new StreamSpecifier

spec
^^^^

.. java:method:: public String spec()
   :outertype: StreamSpecifier

stream
^^^^^^

.. java:method:: public static StreamSpecifier stream(int index)
   :outertype: StreamSpecifier

   Matches the stream with this index.

   :param index: The stream index
   :return: A new StreamSpecifier

stream
^^^^^^

.. java:method:: public static StreamSpecifier stream(StreamSpecifierType type)
   :outertype: StreamSpecifier

   Matches all streams of this type.

   :param type: The stream type
   :return: A new StreamSpecifier

stream
^^^^^^

.. java:method:: public static StreamSpecifier stream(StreamSpecifierType type, int index)
   :outertype: StreamSpecifier

   Matches the stream number stream_index of this type.

   :param type: The stream type
   :param index: The stream index
   :return: A new StreamSpecifier

tag
^^^

.. java:method:: public static StreamSpecifier tag(String key)
   :outertype: StreamSpecifier

   Matches all streams with the given metadata tag.

   :param key: The metadata tag
   :return: A new StreamSpecifier

tag
^^^

.. java:method:: public static StreamSpecifier tag(String key, String value)
   :outertype: StreamSpecifier

   Matches streams with the metadata tag key having the specified value.

   :param key: The metadata tag
   :param value: The metatdata's value
   :return: A new StreamSpecifier

usable
^^^^^^

.. java:method:: public static StreamSpecifier usable()
   :outertype: StreamSpecifier

   Matches streams with usable configuration, the codec must be defined and the essential information such as video dimension or audio sample rate must be present.

   :return: A new StreamSpecifier

