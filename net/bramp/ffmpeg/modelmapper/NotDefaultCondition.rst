.. java:import:: com.google.common.base Defaults

.. java:import:: com.google.common.base Objects

.. java:import:: org.modelmapper Condition

.. java:import:: org.modelmapper.spi MappingContext

NotDefaultCondition
===================

.. java:package:: net.bramp.ffmpeg.modelmapper
   :noindex:

.. java:type:: public class NotDefaultCondition<S, D> implements Condition<S, D>

   Only maps properties which are not their type's default value.

   :author: bramp
   :param <S>: source type
   :param <D>: destination type

Fields
------
notDefault
^^^^^^^^^^

.. java:field:: public static final NotDefaultCondition notDefault
   :outertype: NotDefaultCondition

Methods
-------
applies
^^^^^^^

.. java:method:: @Override public boolean applies(MappingContext<S, D> context)
   :outertype: NotDefaultCondition

