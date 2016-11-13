.. java:import:: com.google.common.base Joiner

.. java:import:: com.google.common.base Preconditions

.. java:import:: org.slf4j Logger

.. java:import:: org.slf4j LoggerFactory

.. java:import:: java.io IOException

.. java:import:: java.util List

RunProcessFunction
==================

.. java:package:: net.bramp.ffmpeg
   :noindex:

.. java:type:: public class RunProcessFunction implements ProcessFunction

   Simple function that creates a Process with the arguments, and returns a BufferedReader reading stdout

   :author: bramp

Fields
------
LOG
^^^

.. java:field:: static final Logger LOG
   :outertype: RunProcessFunction

Methods
-------
run
^^^

.. java:method:: public Process run(List<String> args) throws IOException
   :outertype: RunProcessFunction

