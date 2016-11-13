.. java:import:: org.slf4j Logger

.. java:import:: java.io FilterReader

.. java:import:: java.io IOException

.. java:import:: java.io Reader

LoggingFilterReader
===================

.. java:package:: net.bramp.ffmpeg.io
   :noindex:

.. java:type:: public class LoggingFilterReader extends FilterReader

   Wraps a Reader, and logs full lines of input as it is read.

   :author: bramp

Fields
------
LOG_CHAR
^^^^^^^^

.. java:field:: static final char LOG_CHAR
   :outertype: LoggingFilterReader

buffer
^^^^^^

.. java:field:: final StringBuilder buffer
   :outertype: LoggingFilterReader

logger
^^^^^^

.. java:field:: final Logger logger
   :outertype: LoggingFilterReader

Constructors
------------
LoggingFilterReader
^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public LoggingFilterReader(Reader in, Logger logger)
   :outertype: LoggingFilterReader

Methods
-------
log
^^^

.. java:method:: protected void log()
   :outertype: LoggingFilterReader

read
^^^^

.. java:method:: @Override public int read(char[] cbuf, int off, int len) throws IOException
   :outertype: LoggingFilterReader

read
^^^^

.. java:method:: @Override public int read() throws IOException
   :outertype: LoggingFilterReader

