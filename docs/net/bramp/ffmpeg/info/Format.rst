.. java:import:: com.google.common.base Preconditions

.. java:import:: org.apache.commons.lang3.builder EqualsBuilder

.. java:import:: org.apache.commons.lang3.builder HashCodeBuilder

Format
======

.. java:package:: net.bramp.ffmpeg.info
   :noindex:

.. java:type:: public class Format

   Information about supported Format

   :author: bramp

Fields
------
canDemux
^^^^^^^^

.. java:field:: final boolean canDemux
   :outertype: Format

canMux
^^^^^^

.. java:field:: final boolean canMux
   :outertype: Format

longName
^^^^^^^^

.. java:field:: final String longName
   :outertype: Format

name
^^^^

.. java:field:: final String name
   :outertype: Format

Constructors
------------
Format
^^^^^^

.. java:constructor:: public Format(String name, String longName, String flags)
   :outertype: Format

Methods
-------
equals
^^^^^^

.. java:method:: @Override public boolean equals(Object obj)
   :outertype: Format

getCanDemux
^^^^^^^^^^^

.. java:method:: public boolean getCanDemux()
   :outertype: Format

getCanMux
^^^^^^^^^

.. java:method:: public boolean getCanMux()
   :outertype: Format

getLongName
^^^^^^^^^^^

.. java:method:: public String getLongName()
   :outertype: Format

getName
^^^^^^^

.. java:method:: public String getName()
   :outertype: Format

hashCode
^^^^^^^^

.. java:method:: @Override public int hashCode()
   :outertype: Format

toString
^^^^^^^^

.. java:method:: @Override public String toString()
   :outertype: Format

