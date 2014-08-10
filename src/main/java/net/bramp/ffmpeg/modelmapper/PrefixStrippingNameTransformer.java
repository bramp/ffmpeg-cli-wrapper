package net.bramp.ffmpeg.modelmapper;

import org.modelmapper.convention.NameTransformers;
import org.modelmapper.internal.util.Strings;
import org.modelmapper.spi.NameTransformer;
import org.modelmapper.spi.NameableType;

import static com.google.common.base.Preconditions.checkNotNull;

/**
* @author bramp
*/
public class PrefixStrippingNameTransformer implements NameTransformer {

	final NameTransformer pre;
	final String prefix;

	public PrefixStrippingNameTransformer(String prefix) {
		this(NameTransformers.JAVABEANS_MUTATOR, prefix);
	}

	public PrefixStrippingNameTransformer(NameTransformer pre, String prefix) {
		this.pre = checkNotNull(pre);
		this.prefix = checkNotNull(prefix);
	}

	@Override
	public String transform(String name, NameableType nameableType) {
		System.out.print(name);

		name = pre.transform(name, nameableType);
		if (name.startsWith(prefix))
			name = Strings.decapitalize(name.substring(prefix.length()));

		System.out.println("->" + name);
		return name;
	}
}
