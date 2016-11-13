.. java:import:: com.google.common.collect ImmutableList

.. java:import:: net.bramp.ffmpeg.builder FFmpegBuilder

.. java:import:: net.bramp.ffmpeg.info Codec

.. java:import:: net.bramp.ffmpeg.info Format

.. java:import:: net.bramp.ffmpeg.progress ProgressListener

.. java:import:: net.bramp.ffmpeg.progress ProgressParser

.. java:import:: net.bramp.ffmpeg.progress TcpProgressParser

.. java:import:: org.apache.commons.lang3.math Fraction

.. java:import:: javax.annotation Nonnull

.. java:import:: javax.annotation Nullable

.. java:import:: java.io BufferedReader

.. java:import:: java.io IOException

.. java:import:: java.net URISyntaxException

.. java:import:: java.util ArrayList

.. java:import:: java.util List

.. java:import:: java.util.regex Matcher

.. java:import:: java.util.regex Pattern

FFmpeg
======

.. java:package:: net.bramp.ffmpeg
   :noindex:

.. java:type:: public class FFmpeg extends FFcommon

   Wrapper around FFmpeg

   :author: bramp

Fields
------
AUDIO_DEPTH_DBL
^^^^^^^^^^^^^^^

.. java:field:: @Deprecated public static final String AUDIO_DEPTH_DBL
   :outertype: FFmpeg

AUDIO_DEPTH_FLT
^^^^^^^^^^^^^^^

.. java:field:: @Deprecated public static final String AUDIO_DEPTH_FLT
   :outertype: FFmpeg

AUDIO_DEPTH_S16
^^^^^^^^^^^^^^^

.. java:field:: @Deprecated public static final String AUDIO_DEPTH_S16
   :outertype: FFmpeg

AUDIO_DEPTH_S32
^^^^^^^^^^^^^^^

.. java:field:: @Deprecated public static final String AUDIO_DEPTH_S32
   :outertype: FFmpeg

AUDIO_DEPTH_U8
^^^^^^^^^^^^^^

.. java:field:: @Deprecated public static final String AUDIO_DEPTH_U8
   :outertype: FFmpeg

AUDIO_FORMAT_DBL
^^^^^^^^^^^^^^^^

.. java:field:: public static final String AUDIO_FORMAT_DBL
   :outertype: FFmpeg

AUDIO_FORMAT_FLT
^^^^^^^^^^^^^^^^

.. java:field:: public static final String AUDIO_FORMAT_FLT
   :outertype: FFmpeg

AUDIO_FORMAT_S16
^^^^^^^^^^^^^^^^

.. java:field:: public static final String AUDIO_FORMAT_S16
   :outertype: FFmpeg

AUDIO_FORMAT_S32
^^^^^^^^^^^^^^^^

.. java:field:: public static final String AUDIO_FORMAT_S32
   :outertype: FFmpeg

AUDIO_FORMAT_U8
^^^^^^^^^^^^^^^

.. java:field:: public static final String AUDIO_FORMAT_U8
   :outertype: FFmpeg

AUDIO_MONO
^^^^^^^^^^

.. java:field:: public static final int AUDIO_MONO
   :outertype: FFmpeg

AUDIO_SAMPLE_11025
^^^^^^^^^^^^^^^^^^

.. java:field:: public static final int AUDIO_SAMPLE_11025
   :outertype: FFmpeg

AUDIO_SAMPLE_12000
^^^^^^^^^^^^^^^^^^

.. java:field:: public static final int AUDIO_SAMPLE_12000
   :outertype: FFmpeg

AUDIO_SAMPLE_16000
^^^^^^^^^^^^^^^^^^

.. java:field:: public static final int AUDIO_SAMPLE_16000
   :outertype: FFmpeg

AUDIO_SAMPLE_22050
^^^^^^^^^^^^^^^^^^

.. java:field:: public static final int AUDIO_SAMPLE_22050
   :outertype: FFmpeg

AUDIO_SAMPLE_32000
^^^^^^^^^^^^^^^^^^

.. java:field:: public static final int AUDIO_SAMPLE_32000
   :outertype: FFmpeg

AUDIO_SAMPLE_44100
^^^^^^^^^^^^^^^^^^

.. java:field:: public static final int AUDIO_SAMPLE_44100
   :outertype: FFmpeg

AUDIO_SAMPLE_48000
^^^^^^^^^^^^^^^^^^

.. java:field:: public static final int AUDIO_SAMPLE_48000
   :outertype: FFmpeg

AUDIO_SAMPLE_8000
^^^^^^^^^^^^^^^^^

.. java:field:: public static final int AUDIO_SAMPLE_8000
   :outertype: FFmpeg

AUDIO_SAMPLE_96000
^^^^^^^^^^^^^^^^^^

.. java:field:: public static final int AUDIO_SAMPLE_96000
   :outertype: FFmpeg

AUDIO_STEREO
^^^^^^^^^^^^

.. java:field:: public static final int AUDIO_STEREO
   :outertype: FFmpeg

CODECS_REGEX
^^^^^^^^^^^^

.. java:field:: static final Pattern CODECS_REGEX
   :outertype: FFmpeg

DEFAULT_PATH
^^^^^^^^^^^^

.. java:field:: public static final String DEFAULT_PATH
   :outertype: FFmpeg

FFMPEG
^^^^^^

.. java:field:: public static final String FFMPEG
   :outertype: FFmpeg

FORMATS_REGEX
^^^^^^^^^^^^^

.. java:field:: static final Pattern FORMATS_REGEX
   :outertype: FFmpeg

FPS_23_976
^^^^^^^^^^

.. java:field:: public static final Fraction FPS_23_976
   :outertype: FFmpeg

FPS_24
^^^^^^

.. java:field:: public static final Fraction FPS_24
   :outertype: FFmpeg

FPS_29_97
^^^^^^^^^

.. java:field:: public static final Fraction FPS_29_97
   :outertype: FFmpeg

FPS_30
^^^^^^

.. java:field:: public static final Fraction FPS_30
   :outertype: FFmpeg

codecs
^^^^^^

.. java:field::  List<Codec> codecs
   :outertype: FFmpeg

   Supported codecs

formats
^^^^^^^

.. java:field::  List<Format> formats
   :outertype: FFmpeg

   Supported formats

Constructors
------------
FFmpeg
^^^^^^

.. java:constructor:: public FFmpeg() throws IOException
   :outertype: FFmpeg

FFmpeg
^^^^^^

.. java:constructor:: public FFmpeg(ProcessFunction runFunction) throws IOException
   :outertype: FFmpeg

FFmpeg
^^^^^^

.. java:constructor:: public FFmpeg(String path) throws IOException
   :outertype: FFmpeg

FFmpeg
^^^^^^

.. java:constructor:: public FFmpeg(String path, ProcessFunction runFunction) throws IOException
   :outertype: FFmpeg

Methods
-------
builder
^^^^^^^

.. java:method:: public FFmpegBuilder builder()
   :outertype: FFmpeg

codecs
^^^^^^

.. java:method:: @Nonnull public synchronized List<Codec> codecs() throws IOException
   :outertype: FFmpeg

createProgressParser
^^^^^^^^^^^^^^^^^^^^

.. java:method:: protected ProgressParser createProgressParser(ProgressListener listener) throws IOException
   :outertype: FFmpeg

formats
^^^^^^^

.. java:method:: @Nonnull public synchronized List<Format> formats() throws IOException
   :outertype: FFmpeg

getPath
^^^^^^^

.. java:method:: public String getPath()
   :outertype: FFmpeg

isFFmpeg
^^^^^^^^

.. java:method:: public boolean isFFmpeg() throws IOException
   :outertype: FFmpeg

   Returns true if the binary we are using is the true ffmpeg. This is to avoid conflict with avconv (from the libav project), that some symlink to ffmpeg.

   :throws IOException: If a I/O error occurs while executing ffmpeg.
   :return: true iff this is the official ffmpeg binary.

run
^^^

.. java:method:: public void run(List<String> args) throws IOException
   :outertype: FFmpeg

run
^^^

.. java:method:: public void run(FFmpegBuilder builder, ProgressListener listener) throws IOException
   :outertype: FFmpeg

