package net.bramp.ffmpeg.modelmapper;

import org.modelmapper.Condition;
import org.modelmapper.spi.MappingContext;

import static com.google.common.base.Preconditions.checkNotNull;

/**
* @author bramp
*/
public class PrefixCondition implements Condition {
	final String prefix;

	public PrefixCondition(String prefix) {
		this.prefix = checkNotNull(prefix);
	}

	@Override
	public boolean applies(MappingContext context) {
		String path = org.modelmapper.internal.util.Strings.join(context.getMapping().getDestinationProperties());
		return path.startsWith(prefix);
	}
}
