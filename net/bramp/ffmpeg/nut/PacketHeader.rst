.. java:import:: com.google.common.base MoreObjects

.. java:import:: java.io IOException

PacketHeader
============

.. java:package:: net.bramp.ffmpeg.nut
   :noindex:

.. java:type::  class PacketHeader

Fields
------
checksum
^^^^^^^^

.. java:field::  int checksum
   :outertype: PacketHeader

end
^^^

.. java:field::  long end
   :outertype: PacketHeader

forwardPtr
^^^^^^^^^^

.. java:field::  long forwardPtr
   :outertype: PacketHeader

startcode
^^^^^^^^^

.. java:field::  long startcode
   :outertype: PacketHeader

Methods
-------
read
^^^^

.. java:method:: public void read(NutDataInputStream in, long startcode) throws IOException
   :outertype: PacketHeader

toString
^^^^^^^^

.. java:method:: @Override public String toString()
   :outertype: PacketHeader

