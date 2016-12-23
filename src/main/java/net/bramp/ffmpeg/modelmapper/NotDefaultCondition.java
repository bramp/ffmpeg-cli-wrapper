package net.bramp.ffmpeg.modelmapper;

import com.google.common.base.Defaults;
import com.google.common.base.Objects;
import org.modelmapper.Condition;
import org.modelmapper.spi.MappingContext;

/**
 * Only maps properties which are not their type's default value.
 *
 * @param <S> source type
 * @param <D> destination type
 * @author bramp
 */
public class NotDefaultCondition<S, D> implements Condition<S, D> {

  public static final NotDefaultCondition notDefault = new NotDefaultCondition();

  @Override
  public boolean applies(MappingContext<S, D> context) {
    return !Objects.equal(context.getSource(), Defaults.defaultValue(context.getSourceType()));
  }
}
