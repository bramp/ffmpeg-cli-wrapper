.. java:import:: com.google.common.base Optional

.. java:import:: com.google.gson TypeAdapter

.. java:import:: com.google.gson.stream JsonReader

.. java:import:: com.google.gson.stream JsonToken

.. java:import:: com.google.gson.stream JsonWriter

.. java:import:: java.io IOException

.. java:import:: java.lang.reflect Field

NamedBitsetAdapter
==================

.. java:package:: net.bramp.ffmpeg.gson
   :noindex:

.. java:type:: public class NamedBitsetAdapter<T> extends TypeAdapter<T>

   Converts a json object which represents a set of booleans. For example:

   .. parsed-literal::

      public class Set {
        public boolean a = true;
        public boolean b = false;
        public int c = 1;
        public int d = 0;
      }

   is turned into:

   .. parsed-literal::

      {
        "a": true,
        "b": false,
        "c": true,
        "d": false
      }

Fields
------
clazz
^^^^^

.. java:field:: final Class<T> clazz
   :outertype: NamedBitsetAdapter

Constructors
------------
NamedBitsetAdapter
^^^^^^^^^^^^^^^^^^

.. java:constructor:: public NamedBitsetAdapter(Class<T> clazz)
   :outertype: NamedBitsetAdapter

Methods
-------
read
^^^^

.. java:method:: public T read(JsonReader reader) throws IOException
   :outertype: NamedBitsetAdapter

readBoolean
^^^^^^^^^^^

.. java:method:: protected Optional<Boolean> readBoolean(JsonReader reader) throws IOException
   :outertype: NamedBitsetAdapter

setField
^^^^^^^^

.. java:method:: protected void setField(T target, String name, boolean value) throws IllegalAccessException
   :outertype: NamedBitsetAdapter

write
^^^^^

.. java:method:: public void write(JsonWriter writer, T value) throws IOException
   :outertype: NamedBitsetAdapter

