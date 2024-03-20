package net.bramp.ffmpeg.info;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import net.bramp.ffmpeg.shared.CodecType;

public class CodecTest {
    @Test
    public void testCodecConstructor() {
        Codec c1 = new Codec("012v", "Uncompressed 4:2:2 10-bit", "D.VI.S");
        assertThat(c1.name, is("012v"));
        assertThat(c1.longName, is("Uncompressed 4:2:2 10-bit"));
        assertThat(c1.canDecode, is(true));
        assertThat(c1.canEncode, is(false));
        assertThat(c1.type, is(CodecType.VIDEO));
        assertThat(c1.intraFrameOnly, is(true));
        assertThat(c1.lossyCompression, is(false));
        assertThat(c1.losslessCompression, is(true));
        
        Codec c2 = new Codec("4xm", "4X Movie", "D.V.L.");
        assertThat(c2.name, is("4xm"));
        assertThat(c2.longName, is("4X Movie"));
        assertThat(c2.canDecode, is(true));
        assertThat(c2.canEncode, is(false));
        assertThat(c2.type, is(CodecType.VIDEO));
        assertThat(c2.intraFrameOnly, is(false));
        assertThat(c2.lossyCompression, is(true));
        assertThat(c2.losslessCompression, is(false));

        Codec c3 = new Codec("alias_pix", "Alias/Wavefront PIX image", "DEVI.S");
        assertThat(c3.name, is("alias_pix"));
        assertThat(c3.longName, is("Alias/Wavefront PIX image"));
        assertThat(c3.canDecode, is(true));
        assertThat(c3.canEncode, is(true));
        assertThat(c3.type, is(CodecType.VIDEO));
        assertThat(c3.intraFrameOnly, is(true));
        assertThat(c3.lossyCompression, is(false));
        assertThat(c3.losslessCompression, is(true));

        Codec c4 = new Codec("binkaudio_rdft", "Bink Audio (RDFT)", "D.AIL.");
        assertThat(c4.type, is(CodecType.AUDIO));

        Codec c6 = new Codec("mov_text", "MOV text", "DES...");
        assertThat(c6.type, is(CodecType.SUBTITLE));

        Codec c7 = new Codec("bin_data", "binary data", "..D...");
        assertThat(c7.type, is(CodecType.DATA));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadDecodeValue() {
        new Codec("test", "test", "X.V...");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadEncodeValue() {
        new Codec("test", "test", ".XV...");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadCodecValue() {
        new Codec("test", "test", "..X...");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadIntraFrameOnlyValue() {
        new Codec("test", "test", "..VX..");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadLossyValue() {
        new Codec("test", "test", "..V.X.");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadLosslessValue() {
        new Codec("test", "test", "..V..X");
    }
}
