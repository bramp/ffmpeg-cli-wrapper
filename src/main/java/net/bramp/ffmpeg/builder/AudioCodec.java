package net.bramp.ffmpeg.builder;
/**The available codecs may vary depending on the version of FFmpeg.
 * <br>
 *  you can get a list of available codecs through use {@link net.bramp.ffmpeg.FFmpeg#codecs()}.
 *
 *  @see net.bramp.ffmpeg.FFmpeg#codecs()
 *  @author van1164
 *  */
public class AudioCodec {

    /**4GV (Fourth Generation Vocoder)*/
    public static final String GV = "4gv";
    /**8SVX exponential*/
    public static final String SVX_EXP = "8svx_exp";
    /**8SVX fibonacci*/
    public static final String SVX_FIB = "8svx_fib";
    /**AAC (Advanced Audio Coding) (decoders: aac aac_fixed) (encoders: aac aac_mf)*/
    public static final String AAC = "aac";
    /**AAC LATM (Advanced Audio Coding LATM syntax)*/
    public static final String AAC_LATM = "aac_latm";
    /**ATSC A/52A (AC-3) (decoders: ac3 ac3_fixed) (encoders: ac3 ac3_fixed ac3_mf)*/
    public static final String AC3 = "ac3";
    /**AC-4*/
    public static final String AC4 = "ac4";
    /**Sipro ACELP.KELVIN*/
    public static final String ACELP_KELVIN = "acelp.kelvin";
    /**ADPCM 4X Movie*/
    public static final String ADPCM_4XM = "adpcm_4xm";
    /**SEGA CRI ADX ADPCM*/
    public static final String ADPCM_ADX = "adpcm_adx";
    /**ADPCM Nintendo Gamecube AFC*/
    public static final String ADPCM_AFC = "adpcm_afc";
    /**ADPCM AmuseGraphics Movie AGM*/
    public static final String ADPCM_AGM = "adpcm_agm";
    /**ADPCM Yamaha AICA*/
    public static final String ADPCM_AICA = "adpcm_aica";
    /**ADPCM Argonaut Games*/
    public static final String ADPCM_ARGO = "adpcm_argo";
    /**ADPCM Creative Technology*/
    public static final String ADPCM_CT = "adpcm_ct";
    /**ADPCM Nintendo Gamecube DTK*/
    public static final String ADPCM_DTK = "adpcm_dtk";
    /**ADPCM Electronic Arts*/
    public static final String ADPCM_EA = "adpcm_ea";
    /**ADPCM Electronic Arts Maxis CDROM XA*/
    public static final String ADPCM_EA_MAXIS_XA = "adpcm_ea_maxis_xa";
    /**ADPCM Electronic Arts R1*/
    public static final String ADPCM_EA_R1 = "adpcm_ea_r1";
    /**ADPCM Electronic Arts R2*/
    public static final String ADPCM_EA_R2 = "adpcm_ea_r2";
    /**ADPCM Electronic Arts R3*/
    public static final String ADPCM_EA_R3 = "adpcm_ea_r3";
    /**ADPCM Electronic Arts XAS*/
    public static final String ADPCM_EA_XAS = "adpcm_ea_xas";
    /**G.722 ADPCM (decoders: g722) (encoders: g722)*/
    public static final String ADPCM_G722 = "adpcm_g722";
    /**G.726 ADPCM (decoders: g726) (encoders: g726)*/
    public static final String ADPCM_G726 = "adpcm_g726";
    /**G.726 ADPCM little-endian (decoders: g726le) (encoders: g726le)*/
    public static final String ADPCM_G726LE = "adpcm_g726le";
    /**ADPCM IMA Acorn Replay*/
    public static final String ADPCM_IMA_ACORN = "adpcm_ima_acorn";
    /**ADPCM IMA High Voltage Software ALP*/
    public static final String ADPCM_IMA_ALP = "adpcm_ima_alp";
    /**ADPCM IMA AMV*/
    public static final String ADPCM_IMA_AMV = "adpcm_ima_amv";
    /**ADPCM IMA CRYO APC*/
    public static final String ADPCM_IMA_APC = "adpcm_ima_apc";
    /**ADPCM IMA Ubisoft APM*/
    public static final String ADPCM_IMA_APM = "adpcm_ima_apm";
    /**ADPCM IMA Cunning Developments*/
    public static final String ADPCM_IMA_CUNNING = "adpcm_ima_cunning";
    /**ADPCM IMA Eurocom DAT4*/
    public static final String ADPCM_IMA_DAT4 = "adpcm_ima_dat4";
    /**ADPCM IMA Duck DK3*/
    public static final String ADPCM_IMA_DK3 = "adpcm_ima_dk3";
    /**ADPCM IMA Duck DK4*/
    public static final String ADPCM_IMA_DK4 = "adpcm_ima_dk4";
    /**ADPCM IMA Electronic Arts EACS*/
    public static final String ADPCM_IMA_EA_EACS = "adpcm_ima_ea_eacs";
    /**ADPCM IMA Electronic Arts SEAD*/
    public static final String ADPCM_IMA_EA_SEAD = "adpcm_ima_ea_sead";
    /**ADPCM IMA Funcom ISS*/
    public static final String ADPCM_IMA_ISS = "adpcm_ima_iss";
    /**ADPCM IMA MobiClip MOFLEX*/
    public static final String ADPCM_IMA_MOFLEX = "adpcm_ima_moflex";
    /**ADPCM IMA Capcom's MT Framework*/
    public static final String ADPCM_IMA_MTF = "adpcm_ima_mtf";
    /**ADPCM IMA Dialogic OKI*/
    public static final String ADPCM_IMA_OKI = "adpcm_ima_oki";
    /**ADPCM IMA QuickTime*/
    public static final String ADPCM_IMA_QT = "adpcm_ima_qt";
    /**ADPCM IMA Radical*/
    public static final String ADPCM_IMA_RAD = "adpcm_ima_rad";
    /**ADPCM IMA Loki SDL MJPEG*/
    public static final String ADPCM_IMA_SMJPEG = "adpcm_ima_smjpeg";
    /**ADPCM IMA Simon & Schuster Interactive*/
    public static final String ADPCM_IMA_SSI = "adpcm_ima_ssi";
    /**ADPCM IMA WAV*/
    public static final String ADPCM_IMA_WAV = "adpcm_ima_wav";
    /**ADPCM IMA Westwood*/
    public static final String ADPCM_IMA_WS = "adpcm_ima_ws";
    /**ADPCM Microsoft*/
    public static final String ADPCM_MS = "adpcm_ms";
    /**ADPCM MTAF*/
    public static final String ADPCM_MTAF = "adpcm_mtaf";
    /**ADPCM Playstation*/
    public static final String ADPCM_PSX = "adpcm_psx";
    /**ADPCM Sound Blaster Pro 2-bit*/
    public static final String ADPCM_SBPRO_2 = "adpcm_sbpro_2";
    /**ADPCM Sound Blaster Pro 2.6-bit*/
    public static final String ADPCM_SBPRO_3 = "adpcm_sbpro_3";
    /**ADPCM Sound Blaster Pro 4-bit*/
    public static final String ADPCM_SBPRO_4 = "adpcm_sbpro_4";
    /**ADPCM Shockwave Flash*/
    public static final String ADPCM_SWF = "adpcm_swf";
    /**ADPCM Nintendo THP*/
    public static final String ADPCM_THP = "adpcm_thp";
    /**ADPCM Nintendo THP (Little-Endian)*/
    public static final String ADPCM_THP_LE = "adpcm_thp_le";
    /**LucasArts VIMA audio*/
    public static final String ADPCM_VIMA = "adpcm_vima";
    /**ADPCM CDROM XA*/
    public static final String ADPCM_XA = "adpcm_xa";
    /**ADPCM Konami XMD*/
    public static final String ADPCM_XMD = "adpcm_xmd";
    /**ADPCM Yamaha*/
    public static final String ADPCM_YAMAHA = "adpcm_yamaha";
    /**ADPCM Zork*/
    public static final String ADPCM_ZORK = "adpcm_zork";
    /**ALAC (Apple Lossless Audio Codec)*/
    public static final String ALAC = "alac";
    /**AMR-NB (Adaptive Multi-Rate NarrowBand) (decoders: amrnb libopencore_amrnb) (encoders: libopencore_amrnb)*/
    public static final String AMR_NB = "amr_nb";
    /**AMR-WB (Adaptive Multi-Rate WideBand) (decoders: amrwb libopencore_amrwb) (encoders: libvo_amrwbenc)*/
    public static final String AMR_WB = "amr_wb";
    /**Null audio codec*/
    public static final String ANULL = "anull";
    /**Marian's A-pac audio*/
    public static final String APAC = "apac";
    /**Monkey's Audio*/
    public static final String APE = "ape";
    /**aptX (Audio Processing Technology for Bluetooth)*/
    public static final String APTX = "aptx";
    /**aptX HD (Audio Processing Technology for Bluetooth)*/
    public static final String APTX_HD = "aptx_hd";
    /**ATRAC1 (Adaptive TRansform Acoustic Coding)*/
    public static final String ATRAC1 = "atrac1";
    /**ATRAC3 (Adaptive TRansform Acoustic Coding 3)*/
    public static final String ATRAC3 = "atrac3";
    /**ATRAC3 AL (Adaptive TRansform Acoustic Coding 3 Advanced Lossless)*/
    public static final String ATRAC3AL = "atrac3al";
    /**ATRAC3+ (Adaptive TRansform Acoustic Coding 3+) (decoders: atrac3plus)*/
    public static final String ATRAC3P = "atrac3p";
    /**ATRAC3+ AL (Adaptive TRansform Acoustic Coding 3+ Advanced Lossless) (decoders: atrac3plusal)*/
    public static final String ATRAC3PAL = "atrac3pal";
    /**ATRAC9 (Adaptive TRansform Acoustic Coding 9)*/
    public static final String ATRAC9 = "atrac9";
    /**On2 Audio for Video Codec (decoders: on2avc)*/
    public static final String AVC = "avc";
    /**Bink Audio (DCT)*/
    public static final String BINKAUDIO_DCT = "binkaudio_dct";
    /**Bink Audio (RDFT)*/
    public static final String BINKAUDIO_RDFT = "binkaudio_rdft";
    /**Discworld II BMV audio*/
    public static final String BMV_AUDIO = "bmv_audio";
    /**Bonk audio*/
    public static final String BONK = "bonk";
    /**DPCM Cuberoot-Delta-Exact*/
    public static final String CBD2_DPCM = "cbd2_dpcm";
    /**Constrained Energy Lapped Transform (CELT)*/
    public static final String CELT = "celt";
    /**codec2 (very low bitrate speech codec)*/
    public static final String CODEC2 = "codec2";
    /**RFC 3389 Comfort Noise*/
    public static final String COMFORTNOISE = "comfortnoise";
    /**Cook / Cooker / Gecko (RealAudio G2)*/
    public static final String COOK = "cook";
    /**DPCM Xilam DERF*/
    public static final String DERF_DPCM = "derf_dpcm";
    /**DFPWM (Dynamic Filter Pulse Width Modulation)*/
    public static final String DFPWM = "dfpwm";
    /**Dolby E*/
    public static final String DOLBY_E = "dolby_e";
    /**DSD (Direct Stream Digital), least significant bit first*/
    public static final String DSD_LSBF = "dsd_lsbf";
    /**DSD (Direct Stream Digital), least significant bit first, planar*/
    public static final String DSD_LSBF_PLANAR = "dsd_lsbf_planar";
    /**DSD (Direct Stream Digital), most significant bit first*/
    public static final String DSD_MSBF = "dsd_msbf";
    /**DSD (Direct Stream Digital), most significant bit first, planar*/
    public static final String DSD_MSBF_PLANAR = "dsd_msbf_planar";
    /**Delphine Software International CIN audio*/
    public static final String DSICINAUDIO = "dsicinaudio";
    /**Digital Speech Standard - Standard Play mode (DSS SP)*/
    public static final String DSS_SP = "dss_sp";
    /**DST (Direct Stream Transfer)*/
    public static final String DST = "dst";
    /**DCA (DTS Coherent Acoustics) (decoders: dca) (encoders: dca)*/
    public static final String DTS = "dts";
    /**DV audio*/
    public static final String DVAUDIO = "dvaudio";
    /**ATSC A/52B (AC-3, E-AC-3)*/
    public static final String EAC3 = "eac3";
    /**EVRC (Enhanced Variable Rate Codec)*/
    public static final String EVRC = "evrc";
    /**MobiClip FastAudio*/
    public static final String FASTAUDIO = "fastaudio";
    /**FLAC (Free Lossless Audio Codec)*/
    public static final String FLAC = "flac";
    /**FTR Voice*/
    public static final String FTR = "ftr";
    /**G.723.1*/
    public static final String G723_1 = "g723_1";
    /**G.729*/
    public static final String G729 = "g729";
    /**DPCM Gremlin*/
    public static final String GREMLIN_DPCM = "gremlin_dpcm";
    /**GSM (decoders: gsm libgsm) (encoders: libgsm)*/
    public static final String GSM = "gsm";
    /**GSM Microsoft variant (decoders: gsm_ms libgsm_ms) (encoders: libgsm_ms)*/
    public static final String GSM_MS = "gsm_ms";
    /**CRI HCA*/
    public static final String HCA = "hca";
    /**HCOM Audio*/
    public static final String HCOM = "hcom";
    /**IAC (Indeo Audio Coder)*/
    public static final String IAC = "iac";
    /**iLBC (Internet Low Bitrate Codec)*/
    public static final String ILBC = "ilbc";
    /**IMC (Intel Music Coder)*/
    public static final String IMC = "imc";
    /**DPCM Interplay*/
    public static final String INTERPLAY_DPCM = "interplay_dpcm";
    /**Interplay ACM*/
    public static final String INTERPLAYACM = "interplayacm";
    /**MACE (Macintosh Audio Compression/Expansion) 3:1*/
    public static final String MACE3 = "mace3";
    /**MACE (Macintosh Audio Compression/Expansion) 6:1*/
    public static final String MACE6 = "mace6";
    /**Voxware MetaSound*/
    public static final String METASOUND = "metasound";
    /**Micronas SC-4 Audio*/
    public static final String MISC4 = "misc4";
    /**MLP (Meridian Lossless Packing)*/
    public static final String MLP = "mlp";
    /**MP1 (MPEG audio layer 1) (decoders: mp1 mp1float)*/
    public static final String MP1 = "mp1";
    /**MP2 (MPEG audio layer 2) (decoders: mp2 mp2float) (encoders: mp2 mp2fixed)*/
    public static final String MP2 = "mp2";
    /**MP3 (MPEG audio layer 3) (decoders: mp3float mp3) (encoders: libmp3lame mp3_mf)*/
    public static final String MP3 = "mp3";
    /**ADU (Application Data Unit) MP3 (MPEG audio layer 3) (decoders: mp3adufloat mp3adu)*/
    public static final String MP3ADU = "mp3adu";
    /**MP3onMP4 (decoders: mp3on4float mp3on4)*/
    public static final String MP3ON4 = "mp3on4";
    /**MPEG-4 Audio Lossless Coding (ALS) (decoders: als)*/
    public static final String MP4ALS = "mp4als";
    /**MPEG-H 3D Audio*/
    public static final String MPEGH_3D_AUDIO = "mpegh_3d_audio";
    /**MSN Siren*/
    public static final String MSNSIREN = "msnsiren";
    /**Musepack SV7 (decoders: mpc7)*/
    public static final String MUSEPACK7 = "musepack7";
    /**Musepack SV8 (decoders: mpc8)*/
    public static final String MUSEPACK8 = "musepack8";
    /**Nellymoser Asao*/
    public static final String NELLYMOSER = "nellymoser";
    /**Opus (Opus Interactive Audio Codec) (decoders: opus libopus) (encoders: opus libopus)*/
    public static final String OPUS = "opus";
    /**OSQ (Original Sound Quality)*/
    public static final String OSQ = "osq";
    /**Amazing Studio Packed Animation File Audio*/
    public static final String PAF_AUDIO = "paf_audio";
    /**PCM A-law / G.711 A-law*/
    public static final String PCM_ALAW = "pcm_alaw";
    /**PCM signed 16|20|24-bit big-endian for Blu-ray media*/
    public static final String PCM_BLURAY = "pcm_bluray";
    /**PCM signed 20|24-bit big-endian*/
    public static final String PCM_DVD = "pcm_dvd";
    /**PCM 16.8 floating point little-endian*/
    public static final String PCM_F16LE = "pcm_f16le";
    /**PCM 24.0 floating point little-endian*/
    public static final String PCM_F24LE = "pcm_f24le";
    /**PCM 32-bit floating point big-endian*/
    public static final String PCM_F32BE = "pcm_f32be";
    /**PCM 32-bit floating point little-endian*/
    public static final String PCM_F32LE = "pcm_f32le";
    /**PCM 64-bit floating point big-endian*/
    public static final String PCM_F64BE = "pcm_f64be";
    /**PCM 64-bit floating point little-endian*/
    public static final String PCM_F64LE = "pcm_f64le";
    /**PCM signed 20-bit little-endian planar*/
    public static final String PCM_LXF = "pcm_lxf";
    /**PCM mu-law / G.711 mu-law*/
    public static final String PCM_MULAW = "pcm_mulaw";
    /**PCM signed 16-bit big-endian*/
    public static final String PCM_S16BE = "pcm_s16be";
    /**PCM signed 16-bit big-endian planar*/
    public static final String PCM_S16BE_PLANAR = "pcm_s16be_planar";
    /**PCM signed 16-bit little-endian*/
    public static final String PCM_S16LE = "pcm_s16le";
    /**PCM signed 16-bit little-endian planar*/
    public static final String PCM_S16LE_PLANAR = "pcm_s16le_planar";
    /**PCM signed 24-bit big-endian*/
    public static final String PCM_S24BE = "pcm_s24be";
    /**PCM D-Cinema audio signed 24-bit*/
    public static final String PCM_S24DAUD = "pcm_s24daud";
    /**PCM signed 24-bit little-endian*/
    public static final String PCM_S24LE = "pcm_s24le";
    /**PCM signed 24-bit little-endian planar*/
    public static final String PCM_S24LE_PLANAR = "pcm_s24le_planar";
    /**PCM signed 32-bit big-endian*/
    public static final String PCM_S32BE = "pcm_s32be";
    /**PCM signed 32-bit little-endian*/
    public static final String PCM_S32LE = "pcm_s32le";
    /**PCM signed 32-bit little-endian planar*/
    public static final String PCM_S32LE_PLANAR = "pcm_s32le_planar";
    /**PCM signed 64-bit big-endian*/
    public static final String PCM_S64BE = "pcm_s64be";
    /**PCM signed 64-bit little-endian*/
    public static final String PCM_S64LE = "pcm_s64le";
    /**PCM signed 8-bit*/
    public static final String PCM_S8 = "pcm_s8";
    /**PCM signed 8-bit planar*/
    public static final String PCM_S8_PLANAR = "pcm_s8_planar";
    /**PCM SGA*/
    public static final String PCM_SGA = "pcm_sga";
    /**PCM unsigned 16-bit big-endian*/
    public static final String PCM_U16BE = "pcm_u16be";
    /**PCM unsigned 16-bit little-endian*/
    public static final String PCM_U16LE = "pcm_u16le";
    /**PCM unsigned 24-bit big-endian*/
    public static final String PCM_U24BE = "pcm_u24be";
    /**PCM unsigned 24-bit little-endian*/
    public static final String PCM_U24LE = "pcm_u24le";
    /**PCM unsigned 32-bit big-endian*/
    public static final String PCM_U32BE = "pcm_u32be";
    /**PCM unsigned 32-bit little-endian*/
    public static final String PCM_U32LE = "pcm_u32le";
    /**PCM unsigned 8-bit*/
    public static final String PCM_U8 = "pcm_u8";
    /**PCM Archimedes VIDC*/
    public static final String PCM_VIDC = "pcm_vidc";
    /**QCELP / PureVoice*/
    public static final String QCELP = "qcelp";
    /**QDesign Music Codec 2*/
    public static final String QDM2 = "qdm2";
    /**QDesign Music*/
    public static final String QDMC = "qdmc";
    /**RealAudio 1.0 (14.4K) (decoders: real_144) (encoders: real_144)*/
    public static final String RA_144 = "ra_144";
    /**RealAudio 2.0 (28.8K) (decoders: real_288)*/
    public static final String RA_288 = "ra_288";
    /**RealAudio Lossless*/
    public static final String RALF = "ralf";
    /**RKA (RK Audio)*/
    public static final String RKA = "rka";
    /**DPCM id RoQ*/
    public static final String ROQ_DPCM = "roq_dpcm";
    /**SMPTE 302M*/
    public static final String S302M = "s302m";
    /**SBC (low-complexity subband codec)*/
    public static final String SBC = "sbc";
    /**DPCM Squareroot-Delta-Exact*/
    public static final String SDX2_DPCM = "sdx2_dpcm";
    /**Shorten*/
    public static final String SHORTEN = "shorten";
    /**RealAudio SIPR / ACELP.NET*/
    public static final String SIPR = "sipr";
    /**Siren*/
    public static final String SIREN = "siren";
    /**Smacker audio (decoders: smackaud)*/
    public static final String SMACKAUDIO = "smackaudio";
    /**SMV (Selectable Mode Vocoder)*/
    public static final String SMV = "smv";
    /**DPCM Sol*/
    public static final String SOL_DPCM = "sol_dpcm";
    /**Sonic*/
    public static final String SONIC = "sonic";
    /**Sonic lossless*/
    public static final String SONICLS = "sonicls";
    /**Speex (decoders: speex libspeex) (encoders: libspeex)*/
    public static final String SPEEX = "speex";
    /**TAK (Tom's lossless Audio Kompressor)*/
    public static final String TAK = "tak";
    /**TrueHD*/
    public static final String TRUEHD = "truehd";
    /**DSP Group TrueSpeech*/
    public static final String TRUESPEECH = "truespeech";
    /**TTA (True Audio)*/
    public static final String TTA = "tta";
    /**VQF TwinVQ*/
    public static final String TWINVQ = "twinvq";
    /**Sierra VMD audio*/
    public static final String VMDAUDIO = "vmdaudio";
    /**Vorbis (decoders: vorbis libvorbis) (encoders: vorbis libvorbis)*/
    public static final String VORBIS = "vorbis";
    /**DPCM Marble WADY*/
    public static final String WADY_DPCM = "wady_dpcm";
    /**Waveform Archiver*/
    public static final String WAVARC = "wavarc";
    /**Wave synthesis pseudo-codec*/
    public static final String WAVESYNTH = "wavesynth";
    /**WavPack*/
    public static final String WAVPACK = "wavpack";
    /**Westwood Audio (SND1) (decoders: ws_snd1)*/
    public static final String WESTWOOD_SND1 = "westwood_snd1";
    /**Windows Media Audio Lossless*/
    public static final String WMALOSSLESS = "wmalossless";
    /**Windows Media Audio 9 Professional*/
    public static final String WMAPRO = "wmapro";
    /**Windows Media Audio 1*/
    public static final String WMAV1 = "wmav1";
    /**Windows Media Audio 2*/
    public static final String WMAV2 = "wmav2";
    /**Windows Media Audio Voice*/
    public static final String WMAVOICE = "wmavoice";
    /**DPCM Xan*/
    public static final String XAN_DPCM = "xan_dpcm";
    /**Xbox Media Audio 1*/
    public static final String XMA1 = "xma1";
    /**Xbox Media Audio 2*/
    public static final String XMA2 = "xma2";
}