FFmpeg Java
===========

A fluent interface to running FFmpeg from Java

Install on Ubuntu
-----------------

```
FFmpeg is in Ubuntu's default repositories, however, it is a bit dated. Instead use a PPA of it:
    sudo add-apt-repository ppa:jon-severinsson/ffmpeg
    sudo apt-get update
    sudo apt-get install ffmpeg
    ffmpeg -version
        ffmpeg version 0.10.11-7:0.10.11-1~saucy1
        built on Feb  6 2014 16:55:15 with gcc 4.8.1
```

Usage
-----

```java
    FFmpeg ffmpeg = new FFmpeg("/home/bramp/vendor/ffmpeg/ffmpeg");
	FFprobe ffprobe = new FFprobe();

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
