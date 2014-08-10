package net.bramp.ffmpeg.modelmapper;

import net.bramp.ffmpeg.builder.FFmpegOutputBuilder;
import net.bramp.ffmpeg.options.AudioEncodingOptions;
import net.bramp.ffmpeg.options.EncodingOptions;
import net.bramp.ffmpeg.options.MainEncodingOptions;
import net.bramp.ffmpeg.options.VideoEncodingOptions;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NameTokenizers;

/**
 * Copies values from one type of object to another
 *
 * @author bramp
 */
public class Mapper {

	final private static ModelMapper mapper = newModelMapper();

	private static ModelMapper newModelMapper() {
		ModelMapper mapper = new ModelMapper();

		Configuration config = mapper.getConfiguration().copy()
				.setFieldMatchingEnabled(true)
				.setSourceNameTokenizer(NameTokenizers.UNDERSCORE);

		mapper.createTypeMap(MainEncodingOptions.class,  FFmpegOutputBuilder.class, config);
		mapper.createTypeMap(AudioWrapper.class,  FFmpegOutputBuilder.class, config);
		mapper.createTypeMap(VideoWrapper.class, FFmpegOutputBuilder.class, config);

		return mapper;
	}

	/**
	 * Simple wrapper object, to inject the word "audio" in the property name
	 */
	static class AudioWrapper {
		public final AudioEncodingOptions audio;

		AudioWrapper(AudioEncodingOptions audio) {
			this.audio = audio;
		}
	}

	/**
	 * Simple wrapper object, to inject the word "video" in the property name
	 */
	static class VideoWrapper {
		public final VideoEncodingOptions video;

		VideoWrapper(VideoEncodingOptions video) {
			this.video = video;
		}
	}

	public static void map(MainEncodingOptions opts, FFmpegOutputBuilder dest) {
		mapper.map(opts, dest);
	}

	public static void map(AudioEncodingOptions opts, FFmpegOutputBuilder dest) {
		mapper.map(new AudioWrapper(opts), dest);
	}

	public static void map(VideoEncodingOptions opts, FFmpegOutputBuilder dest) {
		mapper.map(new VideoWrapper(opts), dest);
	}

	public static void map(EncodingOptions opts, FFmpegOutputBuilder dest) {
		map(opts.getMain(), dest);
		map(opts.getAudio(), dest);
		map(opts.getVideo(), dest);
	}

}
