.. java:import:: com.google.common.io CountingInputStream

.. java:import:: net.bramp.ffmpeg.io CRC32InputStream

.. java:import:: java.io DataInput

.. java:import:: java.io DataInputStream

.. java:import:: java.io IOException

.. java:import:: java.io InputStream

NutDataInputStream
==================

.. java:package:: net.bramp.ffmpeg.nut
   :noindex:

.. java:type:: public class NutDataInputStream implements DataInput

   A DataInputStream that implements a couple of custom FFmpeg Nut datatypes.

Fields
------
count
^^^^^

.. java:field:: final CountingInputStream count
   :outertype: NutDataInputStream

crc
^^^

.. java:field:: final CRC32InputStream crc
   :outertype: NutDataInputStream

endCrcRange
^^^^^^^^^^^

.. java:field::  long endCrcRange
   :outertype: NutDataInputStream

in
^^

.. java:field:: final DataInputStream in
   :outertype: NutDataInputStream

startCrcRange
^^^^^^^^^^^^^

.. java:field::  long startCrcRange
   :outertype: NutDataInputStream

Constructors
------------
NutDataInputStream
^^^^^^^^^^^^^^^^^^

.. java:constructor:: public NutDataInputStream(InputStream in)
   :outertype: NutDataInputStream

Methods
-------
getCRC
^^^^^^

.. java:method:: public long getCRC()
   :outertype: NutDataInputStream

offset
^^^^^^

.. java:method:: public long offset()
   :outertype: NutDataInputStream

readBoolean
^^^^^^^^^^^

.. java:method:: @Override public boolean readBoolean() throws IOException
   :outertype: NutDataInputStream

readByte
^^^^^^^^

.. java:method:: @Override public byte readByte() throws IOException
   :outertype: NutDataInputStream

readChar
^^^^^^^^

.. java:method:: @Override public char readChar() throws IOException
   :outertype: NutDataInputStream

readDouble
^^^^^^^^^^

.. java:method:: @Override public double readDouble() throws IOException
   :outertype: NutDataInputStream

readFloat
^^^^^^^^^

.. java:method:: @Override public float readFloat() throws IOException
   :outertype: NutDataInputStream

readFully
^^^^^^^^^

.. java:method:: @Override public void readFully(byte[] b) throws IOException
   :outertype: NutDataInputStream

readFully
^^^^^^^^^

.. java:method:: @Override public void readFully(byte[] b, int off, int len) throws IOException
   :outertype: NutDataInputStream

readInt
^^^^^^^

.. java:method:: @Override public int readInt() throws IOException
   :outertype: NutDataInputStream

readLine
^^^^^^^^

.. java:method:: @Override @Deprecated public String readLine() throws IOException
   :outertype: NutDataInputStream

readLong
^^^^^^^^

.. java:method:: @Override public long readLong() throws IOException
   :outertype: NutDataInputStream

readShort
^^^^^^^^^

.. java:method:: @Override public short readShort() throws IOException
   :outertype: NutDataInputStream

readSignedVarInt
^^^^^^^^^^^^^^^^

.. java:method:: public long readSignedVarInt() throws IOException
   :outertype: NutDataInputStream

readStartCode
^^^^^^^^^^^^^

.. java:method:: public long readStartCode() throws IOException
   :outertype: NutDataInputStream

readUTF
^^^^^^^

.. java:method:: @Override public String readUTF() throws IOException
   :outertype: NutDataInputStream

readUnsignedByte
^^^^^^^^^^^^^^^^

.. java:method:: @Override public int readUnsignedByte() throws IOException
   :outertype: NutDataInputStream

readUnsignedShort
^^^^^^^^^^^^^^^^^

.. java:method:: @Override public int readUnsignedShort() throws IOException
   :outertype: NutDataInputStream

readVarArray
^^^^^^^^^^^^

.. java:method:: public byte[] readVarArray() throws IOException
   :outertype: NutDataInputStream

readVarInt
^^^^^^^^^^

.. java:method:: public int readVarInt() throws IOException
   :outertype: NutDataInputStream

readVarLong
^^^^^^^^^^^

.. java:method:: public long readVarLong() throws IOException
   :outertype: NutDataInputStream

resetCRC
^^^^^^^^

.. java:method:: public void resetCRC()
   :outertype: NutDataInputStream

skipBytes
^^^^^^^^^

.. java:method:: @Override public int skipBytes(int n) throws IOException
   :outertype: NutDataInputStream

