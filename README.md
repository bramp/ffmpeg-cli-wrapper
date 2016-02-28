FFmpeg Java
===========
by Andrew Brampton ([bramp.net](http://bramp.net)) (c) 2013-2014,2016

A fluent interface to running FFmpeg from Java.

![Java](https://img.shields.io/badge/Java-6+-brightgreen.svg)
[![Build Status](https://img.shields.io/travis/bramp/ffmpeg-cli-wrapper/gh-pages.svg)](https://travis-ci.org/bramp/ffmpeg-cli-wrapper)

[GitHub](https://github.com/bramp/ffmpeg-cli-wrapper) | [API docs](https://bramp.github.io/ffmpeg-cli-wrapper/apidocs/index.html)

Usage
-----

```java
    FFmpeg ffmpeg = new FFmpeg("/path/to/ffmpeg");
	FFprobe ffprobe = new FFprobe("/path/to/ffprobe");

    FFmpegBuilder builder = new FFmpegBuilder()
    	.setInput(in)
    	.overrideOutputFiles(true)
    	.addOutput("output.mp4")
	        .setFormat("mp4")
	        .setTargetSize(250000)
	        
	        .disableSubtitle()
	        
	        .setAudioChannels(1)
	        .setAudioCodec("libfdk_aac")
	        .setAudioRate(48000)
	        .setAudioBitrate(32768)
	        
	        .setVideoCodec("libx264")
	        .setVideoFramerate(Fraction.getFraction(24, 1))
	        .setVideoResolution(640, 480)
	        
	        .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
	        .done();

	FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
	executor.createTwoPassJob(builder).run();
```

Install FFmpeg on Ubuntu
-----------------

We only the support the original FFmpeg, not the libav version. Before Ubuntu 12.04, and in 15.04
and later original FFmpeg is shipped. If you have to run on a version with libav, you can install
FFmpeg from a PPA, or using the static build. More information [here](http://askubuntu.com/q/373322/34845)
