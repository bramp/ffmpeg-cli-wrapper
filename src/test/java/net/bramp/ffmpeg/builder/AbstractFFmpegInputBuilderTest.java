package net.bramp.ffmpeg.builder;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public abstract class AbstractFFmpegInputBuilderTest extends AbstractFFmpegStreamBuilderTest {
    @Override
    protected abstract AbstractFFmpegInputBuilder<?> getBuilder();

    @Test
    public void testReadAtNativeFrameRate() {
        List<String> command = getBuilder()
                .readAtNativeFrameRate()
                .build(0);

        assertThat(removeCommon(command), is(ImmutableList.of("-re")));
    }
}
