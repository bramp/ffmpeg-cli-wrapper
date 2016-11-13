.. java:import:: java.util.concurrent TimeUnit

.. java:import:: java.util.concurrent TimeoutException

ProcessUtils
============

.. java:package:: net.bramp.ffmpeg.io
   :noindex:

.. java:type:: public final class ProcessUtils

   :author: bramp

Methods
-------
waitForWithTimeout
^^^^^^^^^^^^^^^^^^

.. java:method:: public static int waitForWithTimeout(Process p, long timeout, TimeUnit unit) throws TimeoutException
   :outertype: ProcessUtils

   Waits until a process finishes or a timeout occurs

   :param p: process
   :param timeout: timeout in given unit
   :param unit: time unit
   :throws TimeoutException: if a timeout occurs
   :return: the process exit value

