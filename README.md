FFmpeg Java
===========
by Andrew Brampton ([bramp.net](http://bramp.net)) (c) 2013-2014,2016

A fluent interface to running FFmpeg from Java.

![Java](https://img.shields.io/badge/Java-6+-brightgreen.svg)
[![Build Status](https://img.shields.io/travis/bramp/ffmpeg-cli-wrapper/master.svg)](https://travis-ci.org/bramp/ffmpeg-cli-wrapper)

[GitHub](https://github.com/bramp/ffmpeg-cli-wrapper) | [API docs](https://bramp.github.io/ffmpeg-cli-wrapper/)

Usage
-----

```xml
<dependency>
  <groupId>net.bramp.ffmpeg</groupId>
  <artifactId>ffmpeg</artifactId>
  <version>0.3</version>
</dependency>
```

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

Licence (Simplified BSD License)
--------------------------------
Copyright (c) 2016, Andrew Brampton
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
