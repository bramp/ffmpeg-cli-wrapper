.. java:import:: com.google.common.base Preconditions

.. java:import:: org.apache.commons.lang3.builder EqualsBuilder

.. java:import:: org.apache.commons.lang3.builder HashCodeBuilder

Codec
=====

.. java:package:: net.bramp.ffmpeg.info
   :noindex:

.. java:type:: public class Codec

   Information about supported Codecs

   :author: bramp

Fields
------
canDecode
^^^^^^^^^

.. java:field:: final boolean canDecode
   :outertype: Codec

   Can I decode with this codec

canEncode
^^^^^^^^^

.. java:field:: final boolean canEncode
   :outertype: Codec

   Can I encode with this codec

longName
^^^^^^^^

.. java:field:: final String longName
   :outertype: Codec

name
^^^^

.. java:field:: final String name
   :outertype: Codec

type
^^^^

.. java:field:: final Type type
   :outertype: Codec

   What type of codec is this

Constructors
------------
Codec
^^^^^

.. java:constructor:: public Codec(String name, String longName, String flags)
   :outertype: Codec

Methods
-------
equals
^^^^^^

.. java:method:: @Override public boolean equals(Object obj)
   :outertype: Codec

getCanDecode
^^^^^^^^^^^^

.. java:method:: public boolean getCanDecode()
   :outertype: Codec

getCanEncode
^^^^^^^^^^^^

.. java:method:: public boolean getCanEncode()
   :outertype: Codec

getLongName
^^^^^^^^^^^

.. java:method:: public String getLongName()
   :outertype: Codec

getName
^^^^^^^

.. java:method:: public String getName()
   :outertype: Codec

getType
^^^^^^^

.. java:method:: public Type getType()
   :outertype: Codec

hashCode
^^^^^^^^

.. java:method:: @Override public int hashCode()
   :outertype: Codec

toString
^^^^^^^^

.. java:method:: @Override public String toString()
   :outertype: Codec

