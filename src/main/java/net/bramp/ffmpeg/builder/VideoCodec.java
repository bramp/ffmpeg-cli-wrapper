package net.bramp.ffmpeg.builder;
/**The available codecs may vary depending on the version of FFmpeg.
 * <br>
 *  you can get a list of available codecs through use {@link net.bramp.ffmpeg.FFmpeg#codecs()}.
 *
 *  @see net.bramp.ffmpeg.FFmpeg#codecs()
 *  @author van1164
 *  */
public enum VideoCodec {
    /**Uncompressed 4:2:2 10-bit*/
    V("012v"),
    /**4X Movie*/
    XM("4xm"),
    /**QuickTime 8BPS video*/
    BPS("8bps"),
    /**Multicolor charset for Commodore 64 (encoders: a64multi)*/
    A64_MULTI("a64_multi"),
    /**Multicolor charset for Commodore 64, extended with 5th color (colram) (encoders: a64multi5)*/
    A64_MULTI5("a64_multi5"),
    /**Autodesk RLE*/
    AASC("aasc"),
    /**Amuse Graphics Movie*/
    AGM("agm"),
    /**Apple Intermediate Codec*/
    AIC("aic"),
    /**Alias/Wavefront PIX image*/
    ALIAS_PIX("alias_pix"),
    /**AMV Video*/
    AMV("amv"),
    /**Deluxe Paint Animation*/
    ANM("anm"),
    /**ASCII/ANSI art*/
    ANSI("ansi"),
    /**APNG (Animated Portable Network Graphics) image*/
    APNG("apng"),
    /**Gryphon's Anim Compressor*/
    ARBC("arbc"),
    /**Argonaut Games Video*/
    ARGO("argo"),
    /**ASUS V1*/
    ASV1("asv1"),
    /**ASUS V2*/
    ASV2("asv2"),
    /**Auravision AURA*/
    AURA("aura"),
    /**Auravision Aura 2*/
    AURA2("aura2"),
    /**Alliance for Open Media AV1 (decoders: libaom-av1 av1 av1_cuvid av1_qsv) (encoders: libaom-av1 av1_nvenc av1_qsv av1_amf)*/
    AV1("av1"),
    /**Avid AVI Codec*/
    AVRN("avrn"),
    /**Avid 1:1 10-bit RGB Packer*/
    AVRP("avrp"),
    /**AVS (Audio Video Standard) video*/
    AVS("avs"),
    /**AVS2-P2/IEEE1857.4*/
    AVS2("avs2"),
    /**AVS3-P2/IEEE1857.10*/
    AVS3("avs3"),
    /**Avid Meridien Uncompressed*/
    AVUI("avui"),
    /**Uncompressed packed MS 4:4:4:4*/
    AYUV("ayuv"),
    /**Bethesda VID video*/
    BETHSOFTVID("bethsoftvid"),
    /**Brute Force & Ignorance*/
    BFI("bfi"),
    /**Bink video*/
    BINKVIDEO("binkvideo"),
    /**Binary text*/
    BINTEXT("bintext"),
    /**Bitpacked*/
    BITPACKED("bitpacked"),
    /**BMP (Windows and OS/2 bitmap)*/
    BMP("bmp"),
    /**Discworld II BMV video*/
    BMV_VIDEO("bmv_video"),
    /**BRender PIX image*/
    BRENDER_PIX("brender_pix"),
    /**Interplay C93*/
    C93("c93"),
    /**Chinese AVS (Audio Video Standard) (AVS1-P2, JiZhun profile)*/
    CAVS("cavs"),
    /**CD Graphics video*/
    CDGRAPHICS("cdgraphics"),
    /**CDToons video*/
    CDTOONS("cdtoons"),
    /**Commodore CDXL video*/
    CDXL("cdxl"),
    /**GoPro CineForm HD*/
    CFHD("cfhd"),
    /**Cinepak*/
    CINEPAK("cinepak"),
    /**Iterated Systems ClearVideo*/
    CLEARVIDEO("clearvideo"),
    /**Cirrus Logic AccuPak*/
    CLJR("cljr"),
    /**Canopus Lossless Codec*/
    CLLC("cllc"),
    /**Electronic Arts CMV video (decoders: eacmv)*/
    CMV("cmv"),
    /**CPiA video format*/
    CPIA("cpia"),
    /**Cintel RAW*/
    CRI("cri"),
    /**CamStudio (decoders: camstudio)*/
    CSCD("cscd"),
    /**Creative YUV (CYUV)*/
    CYUV("cyuv"),
    /**Daala*/
    DAALA("daala"),
    /**DirectDraw Surface image decoder*/
    DDS("dds"),
    /**Chronomaster DFA*/
    DFA("dfa"),
    /**Dirac (encoders: vc2)*/
    DIRAC("dirac"),
    /**VC3/DNxHD*/
    DNXHD("dnxhd"),
    /**DPX (Digital Picture Exchange) image*/
    DPX("dpx"),
    /**Delphine Software International CIN video*/
    DSICINVIDEO("dsicinvideo"),
    /**DV (Digital Video)*/
    DVVIDEO("dvvideo"),
    /**Feeble Files/ScummVM DXA*/
    DXA("dxa"),
    /**Dxtory*/
    DXTORY("dxtory"),
    /**Resolume DXV*/
    DXV("dxv"),
    /**Escape 124*/
    ESCAPE124("escape124"),
    /**Escape 130*/
    ESCAPE130("escape130"),
    /**MPEG-5 EVC (Essential Video Coding)*/
    EVC("evc"),
    /**OpenEXR image*/
    EXR("exr"),
    /**FFmpeg video codec #1*/
    FFV1("ffv1"),
    /**Huffyuv FFmpeg variant*/
    FFVHUFF("ffvhuff"),
    /**Mirillis FIC*/
    FIC("fic"),
    /**FITS (Flexible Image Transport System)*/
    FITS("fits"),
    /**Flash Screen Video v1*/
    FLASHSV("flashsv"),
    /**Flash Screen Video v2*/
    FLASHSV2("flashsv2"),
    /**Autodesk Animator Flic video*/
    FLIC("flic"),
    /**FLV / Sorenson Spark / Sorenson H.263 (Flash Video) (decoders: flv) (encoders: flv)*/
    FLV1("flv1"),
    /**FM Screen Capture Codec*/
    FMVC("fmvc"),
    /**Fraps*/
    FRAPS("fraps"),
    /**Forward Uncompressed*/
    FRWU("frwu"),
    /**Go2Meeting*/
    G2M("g2m"),
    /**Gremlin Digital Video*/
    GDV("gdv"),
    /**GEM Raster image*/
    GEM("gem"),
    /**CompuServe GIF (Graphics Interchange Format)*/
    GIF("gif"),
    /**H.261*/
    H261("h261"),
    /**H.263 / H.263-1996, H.263+ / H.263-1998 / H.263 version 2*/
    H263("h263"),
    /**Intel H.263*/
    H263I("h263i"),
    /**H.263+ / H.263-1998 / H.263 version 2*/
    H263P("h263p"),
    /**H.264 / AVC / MPEG-4 AVC / MPEG-4 part 10 (decoders: h264 h264_qsv h264_cuvid) (encoders: libx264 libx264rgb h264_amf h264_mf h264_nvenc h264_qsv)*/
    H264("h264"),
    /**Vidvox Hap*/
    HAP("hap"),
    /**HDR (Radiance RGBE format) image*/
    HDR("hdr"),
    /**H.265 / HEVC (High Efficiency Video Coding) (decoders: hevc hevc_qsv hevc_cuvid) (encoders: libx265 hevc_amf hevc_mf hevc_nvenc hevc_qsv)*/
    HEVC("hevc"),
    /**HNM 4 video*/
    HNM4VIDEO("hnm4video"),
    /**Canopus HQ/HQA*/
    HQ_HQA("hq_hqa"),
    /**Canopus HQX*/
    HQX("hqx"),
    /**HuffYUV*/
    HUFFYUV("huffyuv"),
    /**HuffYUV MT*/
    HYMT("hymt"),
    /**id Quake II CIN video (decoders: idcinvideo)*/
    IDCIN("idcin"),
    /**iCEDraw text*/
    IDF("idf"),
    /**IFF ACBM/ANIM/DEEP/ILBM/PBM/RGB8/RGBN (decoders: iff)*/
    IFF_ILBM("iff_ilbm"),
    /**Infinity IMM4*/
    IMM4("imm4"),
    /**Infinity IMM5*/
    IMM5("imm5"),
    /**Intel Indeo 2*/
    INDEO2("indeo2"),
    /**Intel Indeo 3*/
    INDEO3("indeo3"),
    /**Intel Indeo Video Interactive 4*/
    INDEO4("indeo4"),
    /**Intel Indeo Video Interactive 5*/
    INDEO5("indeo5"),
    /**Interplay MVE video*/
    INTERPLAYVIDEO("interplayvideo"),
    /**IPU Video*/
    IPU("ipu"),
    /**JPEG 2000 (encoders: jpeg2000 libopenjpeg)*/
    JPEG2000("jpeg2000"),
    /**JPEG-LS*/
    JPEGLS("jpegls"),
    /**JPEG XL*/
    JPEGXL("jpegxl"),
    /**Bitmap Brothers JV video*/
    JV("jv"),
    /**Kega Game Video*/
    KGV1("kgv1"),
    /**Karl Morton's video codec*/
    KMVC("kmvc"),
    /**Lagarith lossless*/
    LAGARITH("lagarith"),
    /**Lossless JPEG*/
    LJPEG("ljpeg"),
    /**LOCO*/
    LOCO("loco"),
    /**LEAD Screen Capture*/
    LSCR("lscr"),
    /**Matrox Uncompressed SD*/
    M101("m101"),
    /**Electronic Arts Madcow Video (decoders: eamad)*/
    MAD("mad"),
    /**MagicYUV video*/
    MAGICYUV("magicyuv"),
    /**Sony PlayStation MDEC (Motion DECoder)*/
    MDEC("mdec"),
    /**Media 100i*/
    MEDIA100("media100"),
    /**Mimic*/
    MIMIC("mimic"),
    /**Motion JPEG (decoders: mjpeg mjpeg_cuvid mjpeg_qsv) (encoders: mjpeg mjpeg_qsv)*/
    MJPEG("mjpeg"),
    /**Apple MJPEG-B*/
    MJPEGB("mjpegb"),
    /**American Laser Games MM Video*/
    MMVIDEO("mmvideo"),
    /**MobiClip Video*/
    MOBICLIP("mobiclip"),
    /**Motion Pixels video*/
    MOTIONPIXELS("motionpixels"),
    /**MPEG-1 video (decoders: mpeg1video mpeg1_cuvid)*/
    MPEG1VIDEO("mpeg1video"),
    /**MPEG-2 video (decoders: mpeg2video mpegvideo mpeg2_qsv mpeg2_cuvid) (encoders: mpeg2video mpeg2_qsv)*/
    MPEG2VIDEO("mpeg2video"),
    /**MPEG-4 part 2 (decoders: mpeg4 mpeg4_cuvid) (encoders: mpeg4 libxvid)*/
    MPEG4("mpeg4"),
    /**MS ATC Screen*/
    MSA1("msa1"),
    /**Mandsoft Screen Capture Codec*/
    MSCC("mscc"),
    /**MPEG-4 part 2 Microsoft variant version 1*/
    MSMPEG4V1("msmpeg4v1"),
    /**MPEG-4 part 2 Microsoft variant version 2*/
    MSMPEG4V2("msmpeg4v2"),
    /**MPEG-4 part 2 Microsoft variant version 3 (decoders: msmpeg4) (encoders: msmpeg4)*/
    MSMPEG4V3("msmpeg4v3"),
    /**Microsoft Paint (MSP) version 2*/
    MSP2("msp2"),
    /**Microsoft RLE*/
    MSRLE("msrle"),
    /**MS Screen 1*/
    MSS1("mss1"),
    /**MS Windows Media Video V9 Screen*/
    MSS2("mss2"),
    /**Microsoft Video 1*/
    MSVIDEO1("msvideo1"),
    /**LCL (LossLess Codec Library) MSZH*/
    MSZH("mszh"),
    /**MS Expression Encoder Screen*/
    MTS2("mts2"),
    /**MidiVid 3.0*/
    MV30("mv30"),
    /**Silicon Graphics Motion Video Compressor 1*/
    MVC1("mvc1"),
    /**Silicon Graphics Motion Video Compressor 2*/
    MVC2("mvc2"),
    /**MidiVid VQ*/
    MVDV("mvdv"),
    /**MidiVid Archive Codec*/
    MVHA("mvha"),
    /**MatchWare Screen Capture Codec*/
    MWSC("mwsc"),
    /**Mobotix MxPEG video*/
    MXPEG("mxpeg"),
    /**NotchLC*/
    NOTCHLC("notchlc"),
    /**NuppelVideo/RTJPEG*/
    NUV("nuv"),
    /**Amazing Studio Packed Animation File Video*/
    PAF_VIDEO("paf_video"),
    /**PAM (Portable AnyMap) image*/
    PAM("pam"),
    /**PBM (Portable BitMap) image*/
    PBM("pbm"),
    /**PC Paintbrush PCX image*/
    PCX("pcx"),
    /**PDV (PlayDate Video)*/
    PDV("pdv"),
    /**PFM (Portable FloatMap) image*/
    PFM("pfm"),
    /**PGM (Portable GrayMap) image*/
    PGM("pgm"),
    /**PGMYUV (Portable GrayMap YUV) image*/
    PGMYUV("pgmyuv"),
    /**PGX (JPEG2000 Test Format)*/
    PGX("pgx"),
    /**PHM (Portable HalfFloatMap) image*/
    PHM("phm"),
    /**Kodak Photo CD*/
    PHOTOCD("photocd"),
    /**Pictor/PC Paint*/
    PICTOR("pictor"),
    /**Apple Pixlet*/
    PIXLET("pixlet"),
    /**PNG (Portable Network Graphics) image*/
    PNG("png"),
    /**PPM (Portable PixelMap) image*/
    PPM("ppm"),
    /**Apple ProRes (iCodec Pro) (encoders: prores prores_aw prores_ks)*/
    PRORES("prores"),
    /**Brooktree ProSumer Video*/
    PROSUMER("prosumer"),
    /**Photoshop PSD file*/
    PSD("psd"),
    /**V.Flash PTX image*/
    PTX("ptx"),
    /**Apple QuickDraw*/
    QDRAW("qdraw"),
    /**QOI (Quite OK Image)*/
    QOI("qoi"),
    /**Q-team QPEG*/
    QPEG("qpeg"),
    /**QuickTime Animation (RLE) video*/
    QTRLE("qtrle"),
    /**AJA Kona 10-bit RGB Codec*/
    R10K("r10k"),
    /**Uncompressed RGB 10-bit*/
    R210("r210"),
    /**RemotelyAnywhere Screen Capture*/
    RASC("rasc"),
    /**raw video*/
    RAWVIDEO("rawvideo"),
    /**RL2 video*/
    RL2("rl2"),
    /**id RoQ video (decoders: roqvideo) (encoders: roqvideo)*/
    ROQ("roq"),
    /**QuickTime video (RPZA)*/
    RPZA("rpza"),
    /**innoHeim/Rsupport Screen Capture Codec*/
    RSCC("rscc"),
    /**RTV1 (RivaTuner Video)*/
    RTV1("rtv1"),
    /**RealVideo 1.0*/
    RV10("rv10"),
    /**RealVideo 2.0*/
    RV20("rv20"),
    /**RealVideo 3.0*/
    RV30("rv30"),
    /**RealVideo 4.0*/
    RV40("rv40"),
    /**LucasArts SANM/SMUSH video*/
    SANM("sanm"),
    /**ScreenPressor*/
    SCPR("scpr"),
    /**Screenpresso*/
    SCREENPRESSO("screenpresso"),
    /**Digital Pictures SGA Video*/
    SGA("sga"),
    /**SGI image*/
    SGI("sgi"),
    /**SGI RLE 8-bit*/
    SGIRLE("sgirle"),
    /**BitJazz SheerVideo*/
    SHEERVIDEO("sheervideo"),
    /**Simbiosis Interactive IMX Video*/
    SIMBIOSIS_IMX("simbiosis_imx"),
    /**Smacker video (decoders: smackvid)*/
    SMACKVIDEO("smackvideo"),
    /**QuickTime Graphics (SMC)*/
    SMC("smc"),
    /**Sigmatel Motion Video*/
    SMVJPEG("smvjpeg"),
    /**Snow*/
    SNOW("snow"),
    /**Sunplus JPEG (SP5X)*/
    SP5X("sp5x"),
    /**NewTek SpeedHQ*/
    SPEEDHQ("speedhq"),
    /**Screen Recorder Gold Codec*/
    SRGC("srgc"),
    /**Sun Rasterfile image*/
    SUNRAST("sunrast"),
    /**Scalable Vector Graphics*/
    SVG("svg"),
    /**Sorenson Vector Quantizer 1 / Sorenson Video 1 / SVQ1*/
    SVQ1("svq1"),
    /**Sorenson Vector Quantizer 3 / Sorenson Video 3 / SVQ3*/
    SVQ3("svq3"),
    /**Truevision Targa image*/
    TARGA("targa"),
    /**Pinnacle TARGA CineWave YUV16*/
    TARGA_Y216("targa_y216"),
    /**TDSC*/
    TDSC("tdsc"),
    /**Electronic Arts TGQ video (decoders: eatgq)*/
    TGQ("tgq"),
    /**Electronic Arts TGV video (decoders: eatgv)*/
    TGV("tgv"),
    /**Theora (encoders: libtheora)*/
    THEORA("theora"),
    /**Nintendo Gamecube THP video*/
    THP("thp"),
    /**Tiertex Limited SEQ video*/
    TIERTEXSEQVIDEO("tiertexseqvideo"),
    /**TIFF image*/
    TIFF("tiff"),
    /**8088flex TMV*/
    TMV("tmv"),
    /**Electronic Arts TQI video (decoders: eatqi)*/
    TQI("tqi"),
    /**Duck TrueMotion 1.0*/
    TRUEMOTION1("truemotion1"),
    /**Duck TrueMotion 2.0*/
    TRUEMOTION2("truemotion2"),
    /**Duck TrueMotion 2.0 Real Time*/
    TRUEMOTION2RT("truemotion2rt"),
    /**TechSmith Screen Capture Codec (decoders: camtasia)*/
    TSCC("tscc"),
    /**TechSmith Screen Codec 2*/
    TSCC2("tscc2"),
    /**Renderware TXD (TeXture Dictionary) image*/
    TXD("txd"),
    /**IBM UltiMotion (decoders: ultimotion)*/
    ULTI("ulti"),
    /**Ut Video*/
    UTVIDEO("utvideo"),
    /**Uncompressed 4:2:2 10-bit*/
    V210("v210"),
    /**Uncompressed 4:2:2 10-bit*/
    V210X("v210x"),
    /**Uncompressed packed 4:4:4*/
    V308("v308"),
    /**Uncompressed packed QT 4:4:4:4*/
    V408("v408"),
    /**Uncompressed 4:4:4 10-bit*/
    V410("v410"),
    /**Beam Software VB*/
    VB("vb"),
    /**VBLE Lossless Codec*/
    VBLE("vble"),
    /**Vizrt Binary Image*/
    VBN("vbn"),
    /**SMPTE VC-1 (decoders: vc1 vc1_qsv vc1_cuvid)*/
    VC1("vc1"),
    /**Windows Media Video 9 Image v2*/
    VC1IMAGE("vc1image"),
    /**ATI VCR1*/
    VCR1("vcr1"),
    /**Miro VideoXL (decoders: xl)*/
    VIXL("vixl"),
    /**Sierra VMD video*/
    VMDVIDEO("vmdvideo"),
    /**vMix Video*/
    VMIX("vmix"),
    /**VMware Screen Codec / VMware Video*/
    VMNC("vmnc"),
    /**Null video codec*/
    VNULL("vnull"),
    /**On2 VP3*/
    VP3("vp3"),
    /**On2 VP4*/
    VP4("vp4"),
    /**On2 VP5*/
    VP5("vp5"),
    /**On2 VP6*/
    VP6("vp6"),
    /**On2 VP6 (Flash version, with alpha channel)*/
    VP6A("vp6a"),
    /**On2 VP6 (Flash version)*/
    VP6F("vp6f"),
    /**On2 VP7*/
    VP7("vp7"),
    /**On2 VP8 (decoders: vp8 libvpx vp8_cuvid vp8_qsv) (encoders: libvpx)*/
    VP8("vp8"),
    /**Google VP9 (decoders: vp9 libvpx-vp9 vp9_cuvid vp9_qsv) (encoders: libvpx-vp9 vp9_qsv)*/
    VP9("vp9"),
    /**ViewQuest VQC*/
    VQC("vqc"),
    /**H.266 / VVC (Versatile Video Coding)*/
    VVC("vvc"),
    /**WBMP (Wireless Application Protocol Bitmap) image*/
    WBMP("wbmp"),
    /**WinCAM Motion Video*/
    WCMV("wcmv"),
    /**WebP (encoders: libwebp_anim libwebp)*/
    WEBP("webp"),
    /**Windows Media Video 7*/
    WMV1("wmv1"),
    /**Windows Media Video 8*/
    WMV2("wmv2"),
    /**Windows Media Video 9*/
    WMV3("wmv3"),
    /**Windows Media Video 9 Image*/
    WMV3IMAGE("wmv3image"),
    /**Winnov WNV1*/
    WNV1("wnv1"),
    /**AVFrame to AVPacket passthrough*/
    WRAPPED_AVFRAME("wrapped_avframe"),
    /**Westwood Studios VQA (Vector Quantized Animation) video (decoders: vqavideo)*/
    WS_VQA("ws_vqa"),
    /**Wing Commander III / Xan*/
    XAN_WC3("xan_wc3"),
    /**Wing Commander IV / Xxan*/
    XAN_WC4("xan_wc4"),
    /**eXtended BINary text*/
    XBIN("xbin"),
    /**XBM (X BitMap) image*/
    XBM("xbm"),
    /**X-face image*/
    XFACE("xface"),
    /**XPM (X PixMap) image*/
    XPM("xpm"),
    /**XWD (X Window Dump) image*/
    XWD("xwd"),
    /**Uncompressed YUV 4:1:1 12-bit*/
    Y41P("y41p"),
    /**YUY2 Lossless Codec*/
    YLC("ylc"),
    /**Psygnosis YOP Video*/
    YOP("yop"),
    /**Uncompressed packed 4:2:0*/
    YUV4("yuv4"),
    /**ZeroCodec Lossless Video*/
    ZEROCODEC("zerocodec"),
    /**LCL (LossLess Codec Library) ZLIB*/
    ZLIB("zlib"),
    /**Zip Motion Blocks Video*/
    ZMBV("zmbv");
    final String codec;

    VideoCodec(String codec) {
        this.codec = codec;
    }

    @Override
    public String toString() {
        return this.codec;
    }
}
