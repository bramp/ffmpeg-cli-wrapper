.. java:import:: com.google.gson TypeAdapter

.. java:import:: com.google.gson.stream JsonReader

.. java:import:: com.google.gson.stream JsonToken

.. java:import:: com.google.gson.stream JsonWriter

.. java:import:: org.apache.commons.lang3.math Fraction

.. java:import:: java.io IOException

FractionAdapter
===============

.. java:package:: net.bramp.commons.lang3.math.gson
   :noindex:

.. java:type:: public class FractionAdapter extends TypeAdapter<Fraction>

   GSON TypeAdapter for Apache Commons Math Fraction Object

   :author: bramp

Constructors
------------
FractionAdapter
^^^^^^^^^^^^^^^

.. java:constructor:: public FractionAdapter()
   :outertype: FractionAdapter

Methods
-------
read
^^^^

.. java:method:: public Fraction read(JsonReader reader) throws IOException
   :outertype: FractionAdapter

write
^^^^^

.. java:method:: public void write(JsonWriter writer, Fraction value) throws IOException
   :outertype: FractionAdapter

