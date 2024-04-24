package net.bramp.ffmpeg.builder;
/**The available codecs may vary depending on the version of FFmpeg.
 * <br>
 *  you can get a list of available codecs through use {@link net.bramp.ffmpeg.FFmpeg#codecs()}.
 *
 *  @see net.bramp.ffmpeg.FFmpeg#codecs()
 *  @author van1164
 *  */
public class VideoCodec {

    /**Uncompressed 4:2:2 10-bit*/
    public static final String V = "012v";
    /**4X Movie*/
    public static final String XM = "4xm";
    /**QuickTime 8BPS video*/
    public static final String BPS = "8bps";
    /**Multicolor charset for Commodore 64 (encoders: a64multi)*/
    public static final String A64_MULTI = "a64_multi";
    /**Multicolor charset for Commodore 64, extended with 5th color (colram) (encoders: a64multi5)*/
    public static final String A64_MULTI5 = "a64_multi5";
    /**Autodesk RLE*/
    public static final String AASC = "aasc";
    /**Amuse Graphics Movie*/
    public static final String AGM = "agm";
    /**Apple Intermediate Codec*/
    public static final String AIC = "aic";
    /**Alias/Wavefront PIX image*/
    public static final String ALIAS_PIX = "alias_pix";
    /**AMV Video*/
    public static final String AMV = "amv";
    /**Deluxe Paint Animation*/
    public static final String ANM = "anm";
    /**ASCII/ANSI art*/
    public static final String ANSI = "ansi";
    /**APNG (Animated Portable Network Graphics) image*/
    public static final String APNG = "apng";
    /**Gryphon's Anim Compressor*/
    public static final String ARBC = "arbc";
    /**Argonaut Games Video*/
    public static final String ARGO = "argo";
    /**ASUS V1*/
    public static final String ASV1 = "asv1";
    /**ASUS V2*/
    public static final String ASV2 = "asv2";
    /**Auravision AURA*/
    public static final String AURA = "aura";
    /**Auravision Aura 2*/
    public static final String AURA2 = "aura2";
    /**Alliance for Open Media AV1 (decoders: libaom-av1 av1 av1_cuvid av1_qsv) (encoders: libaom-av1 av1_nvenc av1_qsv av1_amf)*/
    public static final String AV1 = "av1";
    /**Avid AVI Codec*/
    public static final String AVRN = "avrn";
    /**Avid 1:1 10-bit RGB Packer*/
    public static final String AVRP = "avrp";
    /**AVS (Audio Video Standard) video*/
    public static final String AVS = "avs";
    /**AVS2-P2/IEEE1857.4*/
    public static final String AVS2 = "avs2";
    /**AVS3-P2/IEEE1857.10*/
    public static final String AVS3 = "avs3";
    /**Avid Meridien Uncompressed*/
    public static final String AVUI = "avui";
    /**Uncompressed packed MS 4:4:4:4*/
    public static final String AYUV = "ayuv";
    /**Bethesda VID video*/
    public static final String BETHSOFTVID = "bethsoftvid";
    /**Brute Force &amp; Ignorance*/
    public static final String BFI = "bfi";
    /**Bink video*/
    public static final String BINKVIDEO = "binkvideo";
    /**Binary text*/
    public static final String BINTEXT = "bintext";
    /**Bitpacked*/
    public static final String BITPACKED = "bitpacked";
    /**BMP (Windows and OS/2 bitmap)*/
    public static final String BMP = "bmp";
    /**Discworld II BMV video*/
    public static final String BMV_VIDEO = "bmv_video";
    /**BRender PIX image*/
    public static final String BRENDER_PIX = "brender_pix";
    /**Interplay C93*/
    public static final String C93 = "c93";
    /**Chinese AVS (Audio Video Standard) (AVS1-P2, JiZhun profile)*/
    public static final String CAVS = "cavs";
    /**CD Graphics video*/
    public static final String CDGRAPHICS = "cdgraphics";
    /**CDToons video*/
    public static final String CDTOONS = "cdtoons";
    /**Commodore CDXL video*/
    public static final String CDXL = "cdxl";
    /**GoPro CineForm HD*/
    public static final String CFHD = "cfhd";
    /**Cinepak*/
    public static final String CINEPAK = "cinepak";
    /**Iterated Systems ClearVideo*/
    public static final String CLEARVIDEO = "clearvideo";
    /**Cirrus Logic AccuPak*/
    public static final String CLJR = "cljr";
    /**Canopus Lossless Codec*/
    public static final String CLLC = "cllc";
    /**Electronic Arts CMV video (decoders: eacmv)*/
    public static final String CMV = "cmv";
    /**CPiA video format*/
    public static final String CPIA = "cpia";
    /**Cintel RAW*/
    public static final String CRI = "cri";
    /**CamStudio (decoders: camstudio)*/
    public static final String CSCD = "cscd";
    /**Creative YUV (CYUV)*/
    public static final String CYUV = "cyuv";
    /**Daala*/
    public static final String DAALA = "daala";
    /**DirectDraw Surface image decoder*/
    public static final String DDS = "dds";
    /**Chronomaster DFA*/
    public static final String DFA = "dfa";
    /**Dirac (encoders: vc2)*/
    public static final String DIRAC = "dirac";
    /**VC3/DNxHD*/
    public static final String DNXHD = "dnxhd";
    /**DPX (Digital Picture Exchange) image*/
    public static final String DPX = "dpx";
    /**Delphine Software International CIN video*/
    public static final String DSICINVIDEO = "dsicinvideo";
    /**DV (Digital Video)*/
    public static final String DVVIDEO = "dvvideo";
    /**Feeble Files/ScummVM DXA*/
    public static final String DXA = "dxa";
    /**Dxtory*/
    public static final String DXTORY = "dxtory";
    /**Resolume DXV*/
    public static final String DXV = "dxv";
    /**Escape 124*/
    public static final String ESCAPE124 = "escape124";
    /**Escape 130*/
    public static final String ESCAPE130 = "escape130";
    /**MPEG-5 EVC (Essential Video Coding)*/
    public static final String EVC = "evc";
    /**OpenEXR image*/
    public static final String EXR = "exr";
    /**FFmpeg video codec #1*/
    public static final String FFV1 = "ffv1";
    /**Huffyuv FFmpeg variant*/
    public static final String FFVHUFF = "ffvhuff";
    /**Mirillis FIC*/
    public static final String FIC = "fic";
    /**FITS (Flexible Image Transport System)*/
    public static final String FITS = "fits";
    /**Flash Screen Video v1*/
    public static final String FLASHSV = "flashsv";
    /**Flash Screen Video v2*/
    public static final String FLASHSV2 = "flashsv2";
    /**Autodesk Animator Flic video*/
    public static final String FLIC = "flic";
    /**FLV / Sorenson Spark / Sorenson H.263 (Flash Video) (decoders: flv) (encoders: flv)*/
    public static final String FLV1 = "flv1";
    /**FM Screen Capture Codec*/
    public static final String FMVC = "fmvc";
    /**Fraps*/
    public static final String FRAPS = "fraps";
    /**Forward Uncompressed*/
    public static final String FRWU = "frwu";
    /**Go2Meeting*/
    public static final String G2M = "g2m";
    /**Gremlin Digital Video*/
    public static final String GDV = "gdv";
    /**GEM Raster image*/
    public static final String GEM = "gem";
    /**CompuServe GIF (Graphics Interchange Format)*/
    public static final String GIF = "gif";
    /**H.261*/
    public static final String H261 = "h261";
    /**H.263 / H.263-1996, H.263+ / H.263-1998 / H.263 version 2*/
    public static final String H263 = "h263";
    /**Intel H.263*/
    public static final String H263I = "h263i";
    /**H.263+ / H.263-1998 / H.263 version 2*/
    public static final String H263P = "h263p";
    /**H.264 / AVC / MPEG-4 AVC / MPEG-4 part 10 (decoders: h264 h264_qsv h264_cuvid) (encoders: libx264 libx264rgb h264_amf h264_mf h264_nvenc h264_qsv)*/
    public static final String H264 = "h264";
    /**Vidvox Hap*/
    public static final String HAP = "hap";
    /**HDR (Radiance RGBE format) image*/
    public static final String HDR = "hdr";
    /**H.265 / HEVC (High Efficiency Video Coding) (decoders: hevc hevc_qsv hevc_cuvid) (encoders: libx265 hevc_amf hevc_mf hevc_nvenc hevc_qsv)*/
    public static final String HEVC = "hevc";
    /**HNM 4 video*/
    public static final String HNM4VIDEO = "hnm4video";
    /**Canopus HQ/HQA*/
    public static final String HQ_HQA = "hq_hqa";
    /**Canopus HQX*/
    public static final String HQX = "hqx";
    /**HuffYUV*/
    public static final String HUFFYUV = "huffyuv";
    /**HuffYUV MT*/
    public static final String HYMT = "hymt";
    /**id Quake II CIN video (decoders: idcinvideo)*/
    public static final String IDCIN = "idcin";
    /**iCEDraw text*/
    public static final String IDF = "idf";
    /**IFF ACBM/ANIM/DEEP/ILBM/PBM/RGB8/RGBN (decoders: iff)*/
    public static final String IFF_ILBM = "iff_ilbm";
    /**Infinity IMM4*/
    public static final String IMM4 = "imm4";
    /**Infinity IMM5*/
    public static final String IMM5 = "imm5";
    /**Intel Indeo 2*/
    public static final String INDEO2 = "indeo2";
    /**Intel Indeo 3*/
    public static final String INDEO3 = "indeo3";
    /**Intel Indeo Video Interactive 4*/
    public static final String INDEO4 = "indeo4";
    /**Intel Indeo Video Interactive 5*/
    public static final String INDEO5 = "indeo5";
    /**Interplay MVE video*/
    public static final String INTERPLAYVIDEO = "interplayvideo";
    /**IPU Video*/
    public static final String IPU = "ipu";
    /**JPEG 2000 (encoders: jpeg2000 libopenjpeg)*/
    public static final String JPEG2000 = "jpeg2000";
    /**JPEG-LS*/
    public static final String JPEGLS = "jpegls";
    /**JPEG XL*/
    public static final String JPEGXL = "jpegxl";
    /**Bitmap Brothers JV video*/
    public static final String JV = "jv";
    /**Kega Game Video*/
    public static final String KGV1 = "kgv1";
    /**Karl Morton's video codec*/
    public static final String KMVC = "kmvc";
    /**Lagarith lossless*/
    public static final String LAGARITH = "lagarith";
    /**Lossless JPEG*/
    public static final String LJPEG = "ljpeg";
    /**LOCO*/
    public static final String LOCO = "loco";
    /**LEAD Screen Capture*/
    public static final String LSCR = "lscr";
    /**Matrox Uncompressed SD*/
    public static final String M101 = "m101";
    /**Electronic Arts Madcow Video (decoders: eamad)*/
    public static final String MAD = "mad";
    /**MagicYUV video*/
    public static final String MAGICYUV = "magicyuv";
    /**Sony PlayStation MDEC (Motion DECoder)*/
    public static final String MDEC = "mdec";
    /**Media 100i*/
    public static final String MEDIA100 = "media100";
    /**Mimic*/
    public static final String MIMIC = "mimic";
    /**Motion JPEG (decoders: mjpeg mjpeg_cuvid mjpeg_qsv) (encoders: mjpeg mjpeg_qsv)*/
    public static final String MJPEG = "mjpeg";
    /**Apple MJPEG-B*/
    public static final String MJPEGB = "mjpegb";
    /**American Laser Games MM Video*/
    public static final String MMVIDEO = "mmvideo";
    /**MobiClip Video*/
    public static final String MOBICLIP = "mobiclip";
    /**Motion Pixels video*/
    public static final String MOTIONPIXELS = "motionpixels";
    /**MPEG-1 video (decoders: mpeg1video mpeg1_cuvid)*/
    public static final String MPEG1VIDEO = "mpeg1video";
    /**MPEG-2 video (decoders: mpeg2video mpegvideo mpeg2_qsv mpeg2_cuvid) (encoders: mpeg2video mpeg2_qsv)*/
    public static final String MPEG2VIDEO = "mpeg2video";
    /**MPEG-4 part 2 (decoders: mpeg4 mpeg4_cuvid) (encoders: mpeg4 libxvid)*/
    public static final String MPEG4 = "mpeg4";
    /**MS ATC Screen*/
    public static final String MSA1 = "msa1";
    /**Mandsoft Screen Capture Codec*/
    public static final String MSCC = "mscc";
    /**MPEG-4 part 2 Microsoft variant version 1*/
    public static final String MSMPEG4V1 = "msmpeg4v1";
    /**MPEG-4 part 2 Microsoft variant version 2*/
    public static final String MSMPEG4V2 = "msmpeg4v2";
    /**MPEG-4 part 2 Microsoft variant version 3 (decoders: msmpeg4) (encoders: msmpeg4)*/
    public static final String MSMPEG4V3 = "msmpeg4v3";
    /**Microsoft Paint (MSP) version 2*/
    public static final String MSP2 = "msp2";
    /**Microsoft RLE*/
    public static final String MSRLE = "msrle";
    /**MS Screen 1*/
    public static final String MSS1 = "mss1";
    /**MS Windows Media Video V9 Screen*/
    public static final String MSS2 = "mss2";
    /**Microsoft Video 1*/
    public static final String MSVIDEO1 = "msvideo1";
    /**LCL (LossLess Codec Library) MSZH*/
    public static final String MSZH = "mszh";
    /**MS Expression Encoder Screen*/
    public static final String MTS2 = "mts2";
    /**MidiVid 3.0*/
    public static final String MV30 = "mv30";
    /**Silicon Graphics Motion Video Compressor 1*/
    public static final String MVC1 = "mvc1";
    /**Silicon Graphics Motion Video Compressor 2*/
    public static final String MVC2 = "mvc2";
    /**MidiVid VQ*/
    public static final String MVDV = "mvdv";
    /**MidiVid Archive Codec*/
    public static final String MVHA = "mvha";
    /**MatchWare Screen Capture Codec*/
    public static final String MWSC = "mwsc";
    /**Mobotix MxPEG video*/
    public static final String MXPEG = "mxpeg";
    /**NotchLC*/
    public static final String NOTCHLC = "notchlc";
    /**NuppelVideo/RTJPEG*/
    public static final String NUV = "nuv";
    /**Amazing Studio Packed Animation File Video*/
    public static final String PAF_VIDEO = "paf_video";
    /**PAM (Portable AnyMap) image*/
    public static final String PAM = "pam";
    /**PBM (Portable BitMap) image*/
    public static final String PBM = "pbm";
    /**PC Paintbrush PCX image*/
    public static final String PCX = "pcx";
    /**PDV (PlayDate Video)*/
    public static final String PDV = "pdv";
    /**PFM (Portable FloatMap) image*/
    public static final String PFM = "pfm";
    /**PGM (Portable GrayMap) image*/
    public static final String PGM = "pgm";
    /**PGMYUV (Portable GrayMap YUV) image*/
    public static final String PGMYUV = "pgmyuv";
    /**PGX (JPEG2000 Test Format)*/
    public static final String PGX = "pgx";
    /**PHM (Portable HalfFloatMap) image*/
    public static final String PHM = "phm";
    /**Kodak Photo CD*/
    public static final String PHOTOCD = "photocd";
    /**Pictor/PC Paint*/
    public static final String PICTOR = "pictor";
    /**Apple Pixlet*/
    public static final String PIXLET = "pixlet";
    /**PNG (Portable Network Graphics) image*/
    public static final String PNG = "png";
    /**PPM (Portable PixelMap) image*/
    public static final String PPM = "ppm";
    /**Apple ProRes (iCodec Pro) (encoders: prores prores_aw prores_ks)*/
    public static final String PRORES = "prores";
    /**Brooktree ProSumer Video*/
    public static final String PROSUMER = "prosumer";
    /**Photoshop PSD file*/
    public static final String PSD = "psd";
    /**V.Flash PTX image*/
    public static final String PTX = "ptx";
    /**Apple QuickDraw*/
    public static final String QDRAW = "qdraw";
    /**QOI (Quite OK Image)*/
    public static final String QOI = "qoi";
    /**Q-team QPEG*/
    public static final String QPEG = "qpeg";
    /**QuickTime Animation (RLE) video*/
    public static final String QTRLE = "qtrle";
    /**AJA Kona 10-bit RGB Codec*/
    public static final String R10K = "r10k";
    /**Uncompressed RGB 10-bit*/
    public static final String R210 = "r210";
    /**RemotelyAnywhere Screen Capture*/
    public static final String RASC = "rasc";
    /**raw video*/
    public static final String RAWVIDEO = "rawvideo";
    /**RL2 video*/
    public static final String RL2 = "rl2";
    /**id RoQ video (decoders: roqvideo) (encoders: roqvideo)*/
    public static final String ROQ = "roq";
    /**QuickTime video (RPZA)*/
    public static final String RPZA = "rpza";
    /**innoHeim/Rsupport Screen Capture Codec*/
    public static final String RSCC = "rscc";
    /**RTV1 (RivaTuner Video)*/
    public static final String RTV1 = "rtv1";
    /**RealVideo 1.0*/
    public static final String RV10 = "rv10";
    /**RealVideo 2.0*/
    public static final String RV20 = "rv20";
    /**RealVideo 3.0*/
    public static final String RV30 = "rv30";
    /**RealVideo 4.0*/
    public static final String RV40 = "rv40";
    /**LucasArts SANM/SMUSH video*/
    public static final String SANM = "sanm";
    /**ScreenPressor*/
    public static final String SCPR = "scpr";
    /**Screenpresso*/
    public static final String SCREENPRESSO = "screenpresso";
    /**Digital Pictures SGA Video*/
    public static final String SGA = "sga";
    /**SGI image*/
    public static final String SGI = "sgi";
    /**SGI RLE 8-bit*/
    public static final String SGIRLE = "sgirle";
    /**BitJazz SheerVideo*/
    public static final String SHEERVIDEO = "sheervideo";
    /**Simbiosis Interactive IMX Video*/
    public static final String SIMBIOSIS_IMX = "simbiosis_imx";
    /**Smacker video (decoders: smackvid)*/
    public static final String SMACKVIDEO = "smackvideo";
    /**QuickTime Graphics (SMC)*/
    public static final String SMC = "smc";
    /**Sigmatel Motion Video*/
    public static final String SMVJPEG = "smvjpeg";
    /**Snow*/
    public static final String SNOW = "snow";
    /**Sunplus JPEG (SP5X)*/
    public static final String SP5X = "sp5x";
    /**NewTek SpeedHQ*/
    public static final String SPEEDHQ = "speedhq";
    /**Screen Recorder Gold Codec*/
    public static final String SRGC = "srgc";
    /**Sun Rasterfile image*/
    public static final String SUNRAST = "sunrast";
    /**Scalable Vector Graphics*/
    public static final String SVG = "svg";
    /**Sorenson Vector Quantizer 1 / Sorenson Video 1 / SVQ1*/
    public static final String SVQ1 = "svq1";
    /**Sorenson Vector Quantizer 3 / Sorenson Video 3 / SVQ3*/
    public static final String SVQ3 = "svq3";
    /**Truevision Targa image*/
    public static final String TARGA = "targa";
    /**Pinnacle TARGA CineWave YUV16*/
    public static final String TARGA_Y216 = "targa_y216";
    /**TDSC*/
    public static final String TDSC = "tdsc";
    /**Electronic Arts TGQ video (decoders: eatgq)*/
    public static final String TGQ = "tgq";
    /**Electronic Arts TGV video (decoders: eatgv)*/
    public static final String TGV = "tgv";
    /**Theora (encoders: libtheora)*/
    public static final String THEORA = "theora";
    /**Nintendo Gamecube THP video*/
    public static final String THP = "thp";
    /**Tiertex Limited SEQ video*/
    public static final String TIERTEXSEQVIDEO = "tiertexseqvideo";
    /**TIFF image*/
    public static final String TIFF = "tiff";
    /**8088flex TMV*/
    public static final String TMV = "tmv";
    /**Electronic Arts TQI video (decoders: eatqi)*/
    public static final String TQI = "tqi";
    /**Duck TrueMotion 1.0*/
    public static final String TRUEMOTION1 = "truemotion1";
    /**Duck TrueMotion 2.0*/
    public static final String TRUEMOTION2 = "truemotion2";
    /**Duck TrueMotion 2.0 Real Time*/
    public static final String TRUEMOTION2RT = "truemotion2rt";
    /**TechSmith Screen Capture Codec (decoders: camtasia)*/
    public static final String TSCC = "tscc";
    /**TechSmith Screen Codec 2*/
    public static final String TSCC2 = "tscc2";
    /**Renderware TXD (TeXture Dictionary) image*/
    public static final String TXD = "txd";
    /**IBM UltiMotion (decoders: ultimotion)*/
    public static final String ULTI = "ulti";
    /**Ut Video*/
    public static final String UTVIDEO = "utvideo";
    /**Uncompressed 4:2:2 10-bit*/
    public static final String V210 = "v210";
    /**Uncompressed 4:2:2 10-bit*/
    public static final String V210X = "v210x";
    /**Uncompressed packed 4:4:4*/
    public static final String V308 = "v308";
    /**Uncompressed packed QT 4:4:4:4*/
    public static final String V408 = "v408";
    /**Uncompressed 4:4:4 10-bit*/
    public static final String V410 = "v410";
    /**Beam Software VB*/
    public static final String VB = "vb";
    /**VBLE Lossless Codec*/
    public static final String VBLE = "vble";
    /**Vizrt Binary Image*/
    public static final String VBN = "vbn";
    /**SMPTE VC-1 (decoders: vc1 vc1_qsv vc1_cuvid)*/
    public static final String VC1 = "vc1";
    /**Windows Media Video 9 Image v2*/
    public static final String VC1IMAGE = "vc1image";
    /**ATI VCR1*/
    public static final String VCR1 = "vcr1";
    /**Miro VideoXL (decoders: xl)*/
    public static final String VIXL = "vixl";
    /**Sierra VMD video*/
    public static final String VMDVIDEO = "vmdvideo";
    /**vMix Video*/
    public static final String VMIX = "vmix";
    /**VMware Screen Codec / VMware Video*/
    public static final String VMNC = "vmnc";
    /**Null video codec*/
    public static final String VNULL = "vnull";
    /**On2 VP3*/
    public static final String VP3 = "vp3";
    /**On2 VP4*/
    public static final String VP4 = "vp4";
    /**On2 VP5*/
    public static final String VP5 = "vp5";
    /**On2 VP6*/
    public static final String VP6 = "vp6";
    /**On2 VP6 (Flash version, with alpha channel)*/
    public static final String VP6A = "vp6a";
    /**On2 VP6 (Flash version)*/
    public static final String VP6F = "vp6f";
    /**On2 VP7*/
    public static final String VP7 = "vp7";
    /**On2 VP8 (decoders: vp8 libvpx vp8_cuvid vp8_qsv) (encoders: libvpx)*/
    public static final String VP8 = "vp8";
    /**Google VP9 (decoders: vp9 libvpx-vp9 vp9_cuvid vp9_qsv) (encoders: libvpx-vp9 vp9_qsv)*/
    public static final String VP9 = "vp9";
    /**ViewQuest VQC*/
    public static final String VQC = "vqc";
    /**H.266 / VVC (Versatile Video Coding)*/
    public static final String VVC = "vvc";
    /**WBMP (Wireless Application Protocol Bitmap) image*/
    public static final String WBMP = "wbmp";
    /**WinCAM Motion Video*/
    public static final String WCMV = "wcmv";
    /**WebP (encoders: libwebp_anim libwebp)*/
    public static final String WEBP = "webp";
    /**Windows Media Video 7*/
    public static final String WMV1 = "wmv1";
    /**Windows Media Video 8*/
    public static final String WMV2 = "wmv2";
    /**Windows Media Video 9*/
    public static final String WMV3 = "wmv3";
    /**Windows Media Video 9 Image*/
    public static final String WMV3IMAGE = "wmv3image";
    /**Winnov WNV1*/
    public static final String WNV1 = "wnv1";
    /**AVFrame to AVPacket passthrough*/
    public static final String WRAPPED_AVFRAME = "wrapped_avframe";
    /**Westwood Studios VQA (Vector Quantized Animation) video (decoders: vqavideo)*/
    public static final String WS_VQA = "ws_vqa";
    /**Wing Commander III / Xan*/
    public static final String XAN_WC3 = "xan_wc3";
    /**Wing Commander IV / Xxan*/
    public static final String XAN_WC4 = "xan_wc4";
    /**eXtended BINary text*/
    public static final String XBIN = "xbin";
    /**XBM (X BitMap) image*/
    public static final String XBM = "xbm";
    /**X-face image*/
    public static final String XFACE = "xface";
    /**XPM (X PixMap) image*/
    public static final String XPM = "xpm";
    /**XWD (X Window Dump) image*/
    public static final String XWD = "xwd";
    /**Uncompressed YUV 4:1:1 12-bit*/
    public static final String Y41P = "y41p";
    /**YUY2 Lossless Codec*/
    public static final String YLC = "ylc";
    /**Psygnosis YOP Video*/
    public static final String YOP = "yop";
    /**Uncompressed packed 4:2:0*/
    public static final String YUV4 = "yuv4";
    /**ZeroCodec Lossless Video*/
    public static final String ZEROCODEC = "zerocodec";
    /**LCL (LossLess Codec Library) ZLIB*/
    public static final String ZLIB = "zlib";
    /**Zip Motion Blocks Video*/
    public static final String ZMBV = "zmbv";
}