.. java:import:: com.google.common.base Preconditions

.. java:import:: com.google.common.collect ImmutableList

.. java:import:: net.bramp.ffmpeg FFmpegUtils

.. java:import:: net.bramp.ffmpeg.probe FFmpegProbeResult

.. java:import:: org.slf4j Logger

.. java:import:: org.slf4j LoggerFactory

.. java:import:: java.net URI

.. java:import:: java.util ArrayList

.. java:import:: java.util List

.. java:import:: java.util Map

.. java:import:: java.util TreeMap

.. java:import:: java.util.concurrent TimeUnit

FFmpegBuilder
=============

.. java:package:: net.bramp.ffmpeg.builder
   :noindex:

.. java:type:: public class FFmpegBuilder

   Builds a ffmpeg command line

   :author: bramp

Fields
------
LOG
^^^

.. java:field:: static final Logger LOG
   :outertype: FFmpegBuilder

extra_args
^^^^^^^^^^

.. java:field:: final List<String> extra_args
   :outertype: FFmpegBuilder

format
^^^^^^

.. java:field::  String format
   :outertype: FFmpegBuilder

inputProbes
^^^^^^^^^^^

.. java:field:: final Map<String, FFmpegProbeResult> inputProbes
   :outertype: FFmpegBuilder

inputs
^^^^^^

.. java:field:: final List<String> inputs
   :outertype: FFmpegBuilder

outputs
^^^^^^^

.. java:field:: final List<FFmpegOutputBuilder> outputs
   :outertype: FFmpegBuilder

override
^^^^^^^^

.. java:field::  boolean override
   :outertype: FFmpegBuilder

pass
^^^^

.. java:field::  int pass
   :outertype: FFmpegBuilder

pass_directory
^^^^^^^^^^^^^^

.. java:field::  String pass_directory
   :outertype: FFmpegBuilder

pass_prefix
^^^^^^^^^^^

.. java:field::  String pass_prefix
   :outertype: FFmpegBuilder

progress
^^^^^^^^

.. java:field::  URI progress
   :outertype: FFmpegBuilder

read_at_native_frame_rate
^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:field::  boolean read_at_native_frame_rate
   :outertype: FFmpegBuilder

startOffset
^^^^^^^^^^^

.. java:field::  Long startOffset
   :outertype: FFmpegBuilder

user_agent
^^^^^^^^^^

.. java:field::  String user_agent
   :outertype: FFmpegBuilder

verbosity
^^^^^^^^^

.. java:field::  Verbosity verbosity
   :outertype: FFmpegBuilder

Methods
-------
addExtraArgs
^^^^^^^^^^^^

.. java:method:: public FFmpegBuilder addExtraArgs(String... values)
   :outertype: FFmpegBuilder

   Add additional ouput arguments (for flags which aren't currently supported).

   :param values: The extra arguments.
   :return: this

addInput
^^^^^^^^

.. java:method:: public FFmpegBuilder addInput(FFmpegProbeResult result)
   :outertype: FFmpegBuilder

addInput
^^^^^^^^

.. java:method:: public FFmpegBuilder addInput(String filename)
   :outertype: FFmpegBuilder

addOutput
^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder addOutput(String filename)
   :outertype: FFmpegBuilder

   Adds new output file.

   :param filename: output file path
   :return: A new \ :java:ref:`FFmpegOutputBuilder`\

addOutput
^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder addOutput(URI uri)
   :outertype: FFmpegBuilder

   Adds new output file.

   :param uri: output file uri typically a stream
   :return: A new \ :java:ref:`FFmpegOutputBuilder`\

addOutput
^^^^^^^^^

.. java:method:: public FFmpegBuilder addOutput(FFmpegOutputBuilder output)
   :outertype: FFmpegBuilder

   Adds an existing FFmpegOutputBuilder. This is similar to calling the other addOuput methods but instead allows an existing FFmpegOutputBuilder to be used, and reused.

   .. parsed-literal::

      List<String> args = new FFmpegBuilder()
        .addOutput(new FFmpegOutputBuilder()
          .setFilename("output.flv")
          .setVideoCodec("flv")
        )
        .build();

   :param output: FFmpegOutputBuilder to add
   :return: this

addProgress
^^^^^^^^^^^

.. java:method:: public FFmpegBuilder addProgress(URI uri)
   :outertype: FFmpegBuilder

addStdoutOutput
^^^^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder addStdoutOutput()
   :outertype: FFmpegBuilder

   Create new output (to stdout)

   :return: A new \ :java:ref:`FFmpegOutputBuilder`\

build
^^^^^

.. java:method:: public List<String> build()
   :outertype: FFmpegBuilder

clearInputs
^^^^^^^^^^^

.. java:method:: protected void clearInputs()
   :outertype: FFmpegBuilder

getOverrideOutputFiles
^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public boolean getOverrideOutputFiles()
   :outertype: FFmpegBuilder

overrideOutputFiles
^^^^^^^^^^^^^^^^^^^

.. java:method:: public FFmpegBuilder overrideOutputFiles(boolean override)
   :outertype: FFmpegBuilder

readAtNativeFrameRate
^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public FFmpegBuilder readAtNativeFrameRate()
   :outertype: FFmpegBuilder

setFormat
^^^^^^^^^

.. java:method:: public FFmpegBuilder setFormat(String format)
   :outertype: FFmpegBuilder

setInput
^^^^^^^^

.. java:method:: public FFmpegBuilder setInput(FFmpegProbeResult result)
   :outertype: FFmpegBuilder

setInput
^^^^^^^^

.. java:method:: public FFmpegBuilder setInput(String filename)
   :outertype: FFmpegBuilder

setPass
^^^^^^^

.. java:method:: public FFmpegBuilder setPass(int pass)
   :outertype: FFmpegBuilder

setPassDirectory
^^^^^^^^^^^^^^^^

.. java:method:: public FFmpegBuilder setPassDirectory(String directory)
   :outertype: FFmpegBuilder

setPassPrefix
^^^^^^^^^^^^^

.. java:method:: public FFmpegBuilder setPassPrefix(String prefix)
   :outertype: FFmpegBuilder

setStartOffset
^^^^^^^^^^^^^^

.. java:method:: public FFmpegBuilder setStartOffset(long duration, TimeUnit units)
   :outertype: FFmpegBuilder

setUserAgent
^^^^^^^^^^^^

.. java:method:: public FFmpegBuilder setUserAgent(String userAgent)
   :outertype: FFmpegBuilder

setVerbosity
^^^^^^^^^^^^

.. java:method:: public FFmpegBuilder setVerbosity(Verbosity verbosity)
   :outertype: FFmpegBuilder

