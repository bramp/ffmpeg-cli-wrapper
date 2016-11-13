.. java:import:: com.google.common.base Charsets

.. java:import:: java.io BufferedReader

.. java:import:: java.io IOException

.. java:import:: java.io InputStream

.. java:import:: java.io InputStreamReader

.. java:import:: java.io Reader

StreamProgressParser
====================

.. java:package:: net.bramp.ffmpeg.progress
   :noindex:

.. java:type:: public class StreamProgressParser

Fields
------
listener
^^^^^^^^

.. java:field:: final ProgressListener listener
   :outertype: StreamProgressParser

Constructors
------------
StreamProgressParser
^^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public StreamProgressParser(ProgressListener listener)
   :outertype: StreamProgressParser

Methods
-------
processReader
^^^^^^^^^^^^^

.. java:method:: public void processReader(Reader reader) throws IOException
   :outertype: StreamProgressParser

processStream
^^^^^^^^^^^^^

.. java:method:: public void processStream(InputStream stream) throws IOException
   :outertype: StreamProgressParser

