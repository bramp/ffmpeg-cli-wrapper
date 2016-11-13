MetadataSpecifier
=================

.. java:package:: net.bramp.ffmpeg.builder
   :noindex:

.. java:type:: public class MetadataSpecifier

   Metadata spec, as described in the "map_metadata" section of https://www.ffmpeg.org/ffmpeg-all.html#Main-options

Fields
------
spec
^^^^

.. java:field:: final String spec
   :outertype: MetadataSpecifier

Methods
-------
chapter
^^^^^^^

.. java:method:: public static MetadataSpecifier chapter(int index)
   :outertype: MetadataSpecifier

checkValidKey
^^^^^^^^^^^^^

.. java:method:: public static String checkValidKey(String key)
   :outertype: MetadataSpecifier

global
^^^^^^

.. java:method:: public static MetadataSpecifier global()
   :outertype: MetadataSpecifier

program
^^^^^^^

.. java:method:: public static MetadataSpecifier program(int index)
   :outertype: MetadataSpecifier

spec
^^^^

.. java:method:: public String spec()
   :outertype: MetadataSpecifier

stream
^^^^^^

.. java:method:: public static MetadataSpecifier stream(int index)
   :outertype: MetadataSpecifier

stream
^^^^^^

.. java:method:: public static MetadataSpecifier stream(StreamSpecifierType type)
   :outertype: MetadataSpecifier

stream
^^^^^^

.. java:method:: public static MetadataSpecifier stream(StreamSpecifierType stream_type, int stream_index)
   :outertype: MetadataSpecifier

stream
^^^^^^

.. java:method:: public static MetadataSpecifier stream(StreamSpecifier spec)
   :outertype: MetadataSpecifier

