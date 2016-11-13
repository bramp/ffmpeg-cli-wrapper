.. java:import:: com.google.common.base Preconditions

.. java:import:: com.google.common.base Strings

.. java:import:: com.google.common.collect ImmutableList

.. java:import:: net.bramp.ffmpeg.modelmapper Mapper

.. java:import:: net.bramp.ffmpeg.options AudioEncodingOptions

.. java:import:: net.bramp.ffmpeg.options EncodingOptions

.. java:import:: net.bramp.ffmpeg.options MainEncodingOptions

.. java:import:: net.bramp.ffmpeg.options VideoEncodingOptions

.. java:import:: net.bramp.ffmpeg.probe FFmpegProbeResult

.. java:import:: org.apache.commons.lang3 SystemUtils

.. java:import:: org.apache.commons.lang3.math Fraction

.. java:import:: javax.annotation Nullable

.. java:import:: java.net URI

.. java:import:: java.util ArrayList

.. java:import:: java.util List

.. java:import:: java.util.concurrent TimeUnit

FFmpegOutputBuilder
===================

.. java:package:: net.bramp.ffmpeg.builder
   :noindex:

.. java:type:: public class FFmpegOutputBuilder

   Builds a representation of a single output/encoding setting

Fields
------
audio_bit_rate
^^^^^^^^^^^^^^

.. java:field:: public long audio_bit_rate
   :outertype: FFmpegOutputBuilder

audio_bit_stream_filter
^^^^^^^^^^^^^^^^^^^^^^^

.. java:field:: public String audio_bit_stream_filter
   :outertype: FFmpegOutputBuilder

audio_channels
^^^^^^^^^^^^^^

.. java:field:: public int audio_channels
   :outertype: FFmpegOutputBuilder

audio_codec
^^^^^^^^^^^

.. java:field:: public String audio_codec
   :outertype: FFmpegOutputBuilder

audio_enabled
^^^^^^^^^^^^^

.. java:field:: public boolean audio_enabled
   :outertype: FFmpegOutputBuilder

audio_quality
^^^^^^^^^^^^^

.. java:field:: public Integer audio_quality
   :outertype: FFmpegOutputBuilder

audio_sample_format
^^^^^^^^^^^^^^^^^^^

.. java:field:: public String audio_sample_format
   :outertype: FFmpegOutputBuilder

audio_sample_rate
^^^^^^^^^^^^^^^^^

.. java:field:: public int audio_sample_rate
   :outertype: FFmpegOutputBuilder

duration
^^^^^^^^

.. java:field:: public Long duration
   :outertype: FFmpegOutputBuilder

extra_args
^^^^^^^^^^

.. java:field:: public final List<String> extra_args
   :outertype: FFmpegOutputBuilder

filename
^^^^^^^^

.. java:field:: public String filename
   :outertype: FFmpegOutputBuilder

   Output filename or uri. Only one may be set

format
^^^^^^

.. java:field:: public String format
   :outertype: FFmpegOutputBuilder

meta_tags
^^^^^^^^^

.. java:field:: public final List<String> meta_tags
   :outertype: FFmpegOutputBuilder

parent
^^^^^^

.. java:field:: final FFmpegBuilder parent
   :outertype: FFmpegOutputBuilder

pass_padding_bitrate
^^^^^^^^^^^^^^^^^^^^

.. java:field:: public long pass_padding_bitrate
   :outertype: FFmpegOutputBuilder

startOffset
^^^^^^^^^^^

.. java:field:: public Long startOffset
   :outertype: FFmpegOutputBuilder

strict
^^^^^^

.. java:field:: public FFmpegBuilder.Strict strict
   :outertype: FFmpegOutputBuilder

subtitle_enabled
^^^^^^^^^^^^^^^^

.. java:field:: public boolean subtitle_enabled
   :outertype: FFmpegOutputBuilder

targetSize
^^^^^^^^^^

.. java:field:: public long targetSize
   :outertype: FFmpegOutputBuilder

throwWarnings
^^^^^^^^^^^^^

.. java:field:: public boolean throwWarnings
   :outertype: FFmpegOutputBuilder

uri
^^^

.. java:field:: public URI uri
   :outertype: FFmpegOutputBuilder

video_bit_rate
^^^^^^^^^^^^^^

.. java:field:: public long video_bit_rate
   :outertype: FFmpegOutputBuilder

video_bit_stream_filter
^^^^^^^^^^^^^^^^^^^^^^^

.. java:field:: public String video_bit_stream_filter
   :outertype: FFmpegOutputBuilder

video_codec
^^^^^^^^^^^

.. java:field:: public String video_codec
   :outertype: FFmpegOutputBuilder

video_copyinkf
^^^^^^^^^^^^^^

.. java:field:: public boolean video_copyinkf
   :outertype: FFmpegOutputBuilder

video_enabled
^^^^^^^^^^^^^

.. java:field:: public boolean video_enabled
   :outertype: FFmpegOutputBuilder

video_filter
^^^^^^^^^^^^

.. java:field:: public String video_filter
   :outertype: FFmpegOutputBuilder

video_filter_complex
^^^^^^^^^^^^^^^^^^^^

.. java:field:: public String video_filter_complex
   :outertype: FFmpegOutputBuilder

video_frame_rate
^^^^^^^^^^^^^^^^

.. java:field:: public Fraction video_frame_rate
   :outertype: FFmpegOutputBuilder

video_frames
^^^^^^^^^^^^

.. java:field:: public Integer video_frames
   :outertype: FFmpegOutputBuilder

video_height
^^^^^^^^^^^^

.. java:field:: public int video_height
   :outertype: FFmpegOutputBuilder

video_movflags
^^^^^^^^^^^^^^

.. java:field:: public String video_movflags
   :outertype: FFmpegOutputBuilder

video_pixel_format
^^^^^^^^^^^^^^^^^^

.. java:field:: public String video_pixel_format
   :outertype: FFmpegOutputBuilder

video_preset
^^^^^^^^^^^^

.. java:field:: public String video_preset
   :outertype: FFmpegOutputBuilder

video_quality
^^^^^^^^^^^^^

.. java:field:: public Integer video_quality
   :outertype: FFmpegOutputBuilder

video_size
^^^^^^^^^^

.. java:field:: public String video_size
   :outertype: FFmpegOutputBuilder

video_width
^^^^^^^^^^^

.. java:field:: public int video_width
   :outertype: FFmpegOutputBuilder

Constructors
------------
FFmpegOutputBuilder
^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public FFmpegOutputBuilder()
   :outertype: FFmpegOutputBuilder

FFmpegOutputBuilder
^^^^^^^^^^^^^^^^^^^

.. java:constructor:: protected FFmpegOutputBuilder(FFmpegBuilder parent, String filename)
   :outertype: FFmpegOutputBuilder

FFmpegOutputBuilder
^^^^^^^^^^^^^^^^^^^

.. java:constructor:: protected FFmpegOutputBuilder(FFmpegBuilder parent, URI uri)
   :outertype: FFmpegOutputBuilder

Methods
-------
addExtraArgs
^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder addExtraArgs(String... values)
   :outertype: FFmpegOutputBuilder

   Add additional ouput arguments (for flags which aren't currently supported).

   :param values: The extra arguments
   :return: this

addMetaTag
^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder addMetaTag(String key, String value)
   :outertype: FFmpegOutputBuilder

   Add metadata on output streams. Which keys are possible depends on the used codec.

   :param key: Metadata key, e.g. "comment"
   :param value: Value to set for key
   :return: this

addMetaTag
^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder addMetaTag(MetadataSpecifier spec, String key, String value)
   :outertype: FFmpegOutputBuilder

   Add metadata on output streams. Which keys are possible depends on the used codec.

   .. parsed-literal::

      import static net.bramp.ffmpeg.builder.MetadataSpecifier.*;
      import static net.bramp.ffmpeg.builder.StreamSpecifier.*;
      import static net.bramp.ffmpeg.builder.StreamSpecifierType.*;

      new FFmpegBuilder()
        .addMetaTag("title", "Movie Title") // Annotate whole file
        .addMetaTag(chapter(0), "author", "Bob") // Annotate first chapter
        .addMetaTag(program(0), "comment", "Awesome") // Annotate first program
        .addMetaTag(stream(0), "copyright", "Megacorp") // Annotate first stream
        .addMetaTag(stream(Video), "framerate", "24fps") // Annotate all video streams
        .addMetaTag(stream(Video, 0), "artist", "Joe") // Annotate first video stream
        .addMetaTag(stream(Audio, 0), "language", "eng") // Annotate first audio stream
        .addMetaTag(stream(Subtitle, 0), "language", "fre") // Annotate first subtitle stream
        .addMetaTag(usable(), "year", "2010") // Annotate all streams with a usable configuration

   assertThat(global().spec(), is("g")); assertThat(chapter(1).spec(), is("c:1")); assertThat(program(1).spec(), is("p:1")); assertThat(stream(1).spec(), is("s:1")); assertThat(stream(id(1)).spec(), is("s:i:1"));

   :param spec: Metadata specifier, e.g `MetadataSpec.stream(Audio, 0)`
   :param key: Metadata key, e.g. "comment"
   :param value: Value to set for key
   :return: this

build
^^^^^

.. java:method:: protected List<String> build(int pass)
   :outertype: FFmpegOutputBuilder

build
^^^^^

.. java:method:: protected List<String> build(FFmpegBuilder parent, int pass)
   :outertype: FFmpegOutputBuilder

   Builds the arguments

   :param parent: The parent FFmpegBuilder
   :param pass: The particular pass. For one-pass this value will be zero, for multi-pass, it will be 1 for the first pass, 2 for the second, and so on.
   :return: The arguments

buildOptions
^^^^^^^^^^^^

.. java:method:: public EncodingOptions buildOptions()
   :outertype: FFmpegOutputBuilder

   Returns a representation of this Builder that can be safely serialised. NOTE: This method is horribly out of date, and its use should be rethought.

   :return: A new EncodingOptions capturing this Builder's state

checkValidStream
^^^^^^^^^^^^^^^^

.. java:method:: public static URI checkValidStream(URI uri) throws IllegalArgumentException
   :outertype: FFmpegOutputBuilder

   Checks if the URI is valid for streaming to

   :param uri: The URI to check
   :throws IllegalArgumentException: if the URI is not valid.
   :return: The passed in URI if it is valid

disableAudio
^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder disableAudio()
   :outertype: FFmpegOutputBuilder

disableSubtitle
^^^^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder disableSubtitle()
   :outertype: FFmpegOutputBuilder

disableVideo
^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder disableVideo()
   :outertype: FFmpegOutputBuilder

done
^^^^

.. java:method:: public FFmpegBuilder done()
   :outertype: FFmpegOutputBuilder

   Finished with this output

   :return: the parent FFmpegBuilder

getFilename
^^^^^^^^^^^

.. java:method:: public String getFilename()
   :outertype: FFmpegOutputBuilder

getUri
^^^^^^

.. java:method:: public URI getUri()
   :outertype: FFmpegOutputBuilder

isValidSize
^^^^^^^^^^^

.. java:method:: protected static boolean isValidSize(int widthOrHeight)
   :outertype: FFmpegOutputBuilder

setAudioBitDepth
^^^^^^^^^^^^^^^^

.. java:method:: @Deprecated public FFmpegOutputBuilder setAudioBitDepth(String bit_depth)
   :outertype: FFmpegOutputBuilder

   Sets the audio bit depth.

   :param bit_depth: The sample format, one of the net.bramp.ffmpeg.FFmpeg#AUDIO_DEPTH_* constants.
   :return: this

   **See also:** :java:ref:`net.bramp.ffmpeg.FFmpeg.AUDIO_DEPTH_U8`, :java:ref:`net.bramp.ffmpeg.FFmpeg.AUDIO_DEPTH_S16`, :java:ref:`net.bramp.ffmpeg.FFmpeg.AUDIO_DEPTH_S32`, :java:ref:`net.bramp.ffmpeg.FFmpeg.AUDIO_DEPTH_FLT`, :java:ref:`net.bramp.ffmpeg.FFmpeg.AUDIO_DEPTH_DBL`

setAudioBitRate
^^^^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setAudioBitRate(long bit_rate)
   :outertype: FFmpegOutputBuilder

   Sets the Audio bit rate

   :param bit_rate: Audio bitrate in bits per second.
   :return: this

setAudioBitStreamFilter
^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setAudioBitStreamFilter(String filter)
   :outertype: FFmpegOutputBuilder

setAudioChannels
^^^^^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setAudioChannels(int channels)
   :outertype: FFmpegOutputBuilder

   Sets the number of audio channels

   :param channels: Number of channels
   :return: this

   **See also:** :java:ref:`net.bramp.ffmpeg.FFmpeg.AUDIO_MONO`, :java:ref:`net.bramp.ffmpeg.FFmpeg.AUDIO_STEREO`

setAudioCodec
^^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setAudioCodec(String codec)
   :outertype: FFmpegOutputBuilder

setAudioQuality
^^^^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setAudioQuality(int quality)
   :outertype: FFmpegOutputBuilder

setAudioSampleFormat
^^^^^^^^^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setAudioSampleFormat(String sample_format)
   :outertype: FFmpegOutputBuilder

   Sets the audio sample format.

   :param sample_format: The sample format, one of the net.bramp.ffmpeg.FFmpeg#AUDIO_FORMAT_* constants.
   :return: this

   **See also:** :java:ref:`net.bramp.ffmpeg.FFmpeg.AUDIO_FORMAT_U8`, :java:ref:`net.bramp.ffmpeg.FFmpeg.AUDIO_FORMAT_S16`, :java:ref:`net.bramp.ffmpeg.FFmpeg.AUDIO_FORMAT_S32`, :java:ref:`net.bramp.ffmpeg.FFmpeg.AUDIO_FORMAT_FLT`, :java:ref:`net.bramp.ffmpeg.FFmpeg.AUDIO_FORMAT_DBL`

setAudioSampleRate
^^^^^^^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setAudioSampleRate(int sample_rate)
   :outertype: FFmpegOutputBuilder

   Sets the Audio sample rate, for example 44_000.

   :param sample_rate: Samples measured in Hz
   :return: this

   **See also:** :java:ref:`net.bramp.ffmpeg.FFmpeg.AUDIO_SAMPLE_8000`, :java:ref:`net.bramp.ffmpeg.FFmpeg.AUDIO_SAMPLE_11025`, :java:ref:`net.bramp.ffmpeg.FFmpeg.AUDIO_SAMPLE_12000`, :java:ref:`net.bramp.ffmpeg.FFmpeg.AUDIO_SAMPLE_16000`, :java:ref:`net.bramp.ffmpeg.FFmpeg.AUDIO_SAMPLE_22050`, :java:ref:`net.bramp.ffmpeg.FFmpeg.AUDIO_SAMPLE_32000`, :java:ref:`net.bramp.ffmpeg.FFmpeg.AUDIO_SAMPLE_44100`, :java:ref:`net.bramp.ffmpeg.FFmpeg.AUDIO_SAMPLE_48000`, :java:ref:`net.bramp.ffmpeg.FFmpeg.AUDIO_SAMPLE_96000`

setComplexVideoFilter
^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setComplexVideoFilter(String filter)
   :outertype: FFmpegOutputBuilder

setDuration
^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setDuration(long duration, TimeUnit units)
   :outertype: FFmpegOutputBuilder

   Stop writing the output after duration is reached.

   :param duration: The duration
   :param units: The units the duration is in
   :return: this

setFilename
^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setFilename(String filename)
   :outertype: FFmpegOutputBuilder

setFormat
^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setFormat(String format)
   :outertype: FFmpegOutputBuilder

setFrames
^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setFrames(int frames)
   :outertype: FFmpegOutputBuilder

   Set the number of video frames to record.

   :param frames: The number of frames
   :return: this

setPassPaddingBitrate
^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setPassPaddingBitrate(long bitrate)
   :outertype: FFmpegOutputBuilder

   When doing multi-pass we add a little extra padding, to ensure we reach our target

   :param bitrate: bit rate
   :return: this

setStartOffset
^^^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setStartOffset(long offset, TimeUnit units)
   :outertype: FFmpegOutputBuilder

   Decodes but discards input until the offset.

   :param offset: The offset
   :param units: The units the offset is in
   :return: this

setStrict
^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setStrict(FFmpegBuilder.Strict strict)
   :outertype: FFmpegOutputBuilder

setTargetSize
^^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setTargetSize(long targetSize)
   :outertype: FFmpegOutputBuilder

   Target output file size (in bytes)

   :param targetSize: The target size in bytes
   :return: this

setUri
^^^^^^

.. java:method:: public FFmpegOutputBuilder setUri(URI uri)
   :outertype: FFmpegOutputBuilder

setVideoBitRate
^^^^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setVideoBitRate(long bit_rate)
   :outertype: FFmpegOutputBuilder

setVideoBitStreamFilter
^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setVideoBitStreamFilter(String filter)
   :outertype: FFmpegOutputBuilder

setVideoCodec
^^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setVideoCodec(String codec)
   :outertype: FFmpegOutputBuilder

setVideoCopyInkf
^^^^^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setVideoCopyInkf(boolean copyinkf)
   :outertype: FFmpegOutputBuilder

setVideoFilter
^^^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setVideoFilter(String filter)
   :outertype: FFmpegOutputBuilder

   Sets Video Filter TODO Build a fluent Filter builder

   :param filter: The video filter.
   :return: this

setVideoFrameRate
^^^^^^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setVideoFrameRate(Fraction frame_rate)
   :outertype: FFmpegOutputBuilder

   Sets the video's frame rate

   :param frame_rate: Frames per second
   :return: this

   **See also:** :java:ref:`net.bramp.ffmpeg.FFmpeg.FPS_30`, :java:ref:`net.bramp.ffmpeg.FFmpeg.FPS_29_97`, :java:ref:`net.bramp.ffmpeg.FFmpeg.FPS_24`, :java:ref:`net.bramp.ffmpeg.FFmpeg.FPS_23_976`

setVideoFrameRate
^^^^^^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setVideoFrameRate(int frames, int per)
   :outertype: FFmpegOutputBuilder

   Set the video frame rate in terms of frames per interval. For example 24fps would be 24/1, however NTSC TV at 23.976fps would be 24000 per 1001.

   :param frames: The number of frames within the given seconds
   :param per: The number of seconds
   :return: this

setVideoFrameRate
^^^^^^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setVideoFrameRate(double frame_rate)
   :outertype: FFmpegOutputBuilder

setVideoHeight
^^^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setVideoHeight(int height)
   :outertype: FFmpegOutputBuilder

setVideoMovFlags
^^^^^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setVideoMovFlags(String movflags)
   :outertype: FFmpegOutputBuilder

setVideoPixelFormat
^^^^^^^^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setVideoPixelFormat(String format)
   :outertype: FFmpegOutputBuilder

setVideoPreset
^^^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setVideoPreset(String preset)
   :outertype: FFmpegOutputBuilder

setVideoQuality
^^^^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setVideoQuality(int quality)
   :outertype: FFmpegOutputBuilder

setVideoResolution
^^^^^^^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setVideoResolution(int width, int height)
   :outertype: FFmpegOutputBuilder

setVideoResolution
^^^^^^^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setVideoResolution(String abbreviation)
   :outertype: FFmpegOutputBuilder

   Sets video resolution based on an abbreviation, e.g. "ntsc" for 720x480, or "vga" for 640x480

   :param abbreviation: The abbreviation size. No validation is done, instead the value is passed as is to ffmpeg.
   :return: this

   **See also:** \ `ffmpeg video size <https://www.ffmpeg.org/ffmpeg-utils.html#Video-size>`_\

setVideoWidth
^^^^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder setVideoWidth(int width)
   :outertype: FFmpegOutputBuilder

useOptions
^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder useOptions(EncodingOptions opts)
   :outertype: FFmpegOutputBuilder

useOptions
^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder useOptions(MainEncodingOptions opts)
   :outertype: FFmpegOutputBuilder

useOptions
^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder useOptions(AudioEncodingOptions opts)
   :outertype: FFmpegOutputBuilder

useOptions
^^^^^^^^^^

.. java:method:: public FFmpegOutputBuilder useOptions(VideoEncodingOptions opts)
   :outertype: FFmpegOutputBuilder

