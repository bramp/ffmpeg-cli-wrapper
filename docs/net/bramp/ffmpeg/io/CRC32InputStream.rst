.. java:import:: java.io FilterInputStream

.. java:import:: java.io IOException

.. java:import:: java.io InputStream

.. java:import:: java.util.zip CRC32

CRC32InputStream
================

.. java:package:: net.bramp.ffmpeg.io
   :noindex:

.. java:type:: public class CRC32InputStream extends FilterInputStream

   Calculates the CRC32 for all bytes read through the input stream. Using the java.util.zip.CRC32 class to calculate the checksum.

Fields
------
crc
^^^

.. java:field:: final CRC32 crc
   :outertype: CRC32InputStream

Constructors
------------
CRC32InputStream
^^^^^^^^^^^^^^^^

.. java:constructor:: public CRC32InputStream(InputStream in)
   :outertype: CRC32InputStream

Methods
-------
getValue
^^^^^^^^

.. java:method:: public long getValue()
   :outertype: CRC32InputStream

mark
^^^^

.. java:method:: @Override public synchronized void mark(int readlimit)
   :outertype: CRC32InputStream

markSupported
^^^^^^^^^^^^^

.. java:method:: @Override public boolean markSupported()
   :outertype: CRC32InputStream

read
^^^^

.. java:method:: @Override public int read() throws IOException
   :outertype: CRC32InputStream

read
^^^^

.. java:method:: @Override public int read(byte[] b) throws IOException
   :outertype: CRC32InputStream

read
^^^^

.. java:method:: @Override public int read(byte[] b, int off, int len) throws IOException
   :outertype: CRC32InputStream

reset
^^^^^

.. java:method:: @Override public synchronized void reset() throws IOException
   :outertype: CRC32InputStream

resetCrc
^^^^^^^^

.. java:method:: public void resetCrc()
   :outertype: CRC32InputStream

skip
^^^^

.. java:method:: @Override public long skip(long n) throws IOException
   :outertype: CRC32InputStream

