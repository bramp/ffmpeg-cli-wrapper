.. java:import:: com.google.common.base MoreObjects

.. java:import:: org.slf4j Logger

.. java:import:: org.slf4j LoggerFactory

.. java:import:: java.io IOException

Packet
======

.. java:package:: net.bramp.ffmpeg.nut
   :noindex:

.. java:type:: public class Packet

Fields
------
LOG
^^^

.. java:field:: static final Logger LOG
   :outertype: Packet

footer
^^^^^^

.. java:field:: public final PacketFooter footer
   :outertype: Packet

header
^^^^^^

.. java:field:: public final PacketHeader header
   :outertype: Packet

Methods
-------
read
^^^^

.. java:method:: public void read(NutDataInputStream in, long startcode) throws IOException
   :outertype: Packet

readBody
^^^^^^^^

.. java:method:: protected void readBody(NutDataInputStream in) throws IOException
   :outertype: Packet

seekToPacketFooter
^^^^^^^^^^^^^^^^^^

.. java:method:: public void seekToPacketFooter(NutDataInputStream in) throws IOException
   :outertype: Packet

toString
^^^^^^^^

.. java:method:: @Override public String toString()
   :outertype: Packet

