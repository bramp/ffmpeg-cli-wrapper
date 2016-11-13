.. java:import:: java.io IOException

.. java:import:: java.io InputStream

.. java:import:: java.util ArrayList

.. java:import:: java.util Arrays

.. java:import:: java.util List

NutReader
=========

.. java:package:: net.bramp.ffmpeg.nut
   :noindex:

.. java:type:: public class NutReader

   Demuxer for the FFmpeg Nut file format. Lots of things not implemented, startcode searching, crc checks, etc

   **See also:** <a
        href="https://www.ffmpeg.org/~michael/nut.txt">https://www.ffmpeg.org/~michael/nut.txt</a>, <a
        href="https://github.com/FFmpeg/FFmpeg/blob/master/libavformat/nutdec.c">https://github.com/FFmpeg/FFmpeg/blob/master/libavformat/nutdec.c</a>

Fields
------
HEADER
^^^^^^

.. java:field:: static final byte[] HEADER
   :outertype: NutReader

header
^^^^^^

.. java:field:: public MainHeaderPacket header
   :outertype: NutReader

in
^^

.. java:field:: final NutDataInputStream in
   :outertype: NutReader

listener
^^^^^^^^

.. java:field:: final NutReaderListener listener
   :outertype: NutReader

streams
^^^^^^^

.. java:field:: public final List<Stream> streams
   :outertype: NutReader

Constructors
------------
NutReader
^^^^^^^^^

.. java:constructor:: public NutReader(InputStream in, NutReaderListener listener)
   :outertype: NutReader

Methods
-------
isKnownStartcode
^^^^^^^^^^^^^^^^

.. java:method:: public static boolean isKnownStartcode(long startcode)
   :outertype: NutReader

read
^^^^

.. java:method:: public void read() throws IOException
   :outertype: NutReader

   Demux the inputstream

   :throws IOException: If a I/O error occurs

readFileId
^^^^^^^^^^

.. java:method:: protected void readFileId() throws IOException
   :outertype: NutReader

   Read the magic at the beginning of the file.

   :throws IOException: If a I/O error occurs

readReservedHeaders
^^^^^^^^^^^^^^^^^^^

.. java:method:: protected long readReservedHeaders() throws IOException
   :outertype: NutReader

   Read headers we don't know how to parse yet, returning the next startcode.

   :throws IOException: If a I/O error occurs
   :return: The next startcode

