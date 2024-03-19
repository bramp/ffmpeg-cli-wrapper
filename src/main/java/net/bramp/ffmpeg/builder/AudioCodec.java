package net.bramp.ffmpeg.builder;
/**The available codecs may vary depending on the version of FFmpeg.
 * <br>
 *  you can get a list of available codecs through use {@link net.bramp.ffmpeg.FFmpeg#codecs()}.
 *
 *  @see net.bramp.ffmpeg.FFmpeg#codecs()
 *  @author van1164
 *  */
public enum AudioCodec {
    /**4GV (Fourth Generation Vocoder)*/
    GV("4gv"),
    /**8SVX exponential*/
    SVX_EXP("8svx_exp"),
    /**8SVX fibonacci*/
    SVX_FIB("8svx_fib"),
    /**AAC (Advanced Audio Coding) (decoders: aac aac_fixed) (encoders: aac aac_mf)*/
    AAC("aac"),
    /**AAC LATM (Advanced Audio Coding LATM syntax)*/
    AAC_LATM("aac_latm"),
    /**ATSC A/52A (AC-3) (decoders: ac3 ac3_fixed) (encoders: ac3 ac3_fixed ac3_mf)*/
    AC3("ac3"),
    /**AC-4*/
    AC4("ac4"),
    /**Sipro ACELP.KELVIN*/
    ACELP_KELVIN("acelp.kelvin"),
    /**ADPCM 4X Movie*/
    ADPCM_4XM("adpcm_4xm"),
    /**SEGA CRI ADX ADPCM*/
    ADPCM_ADX("adpcm_adx"),
    /**ADPCM Nintendo Gamecube AFC*/
    ADPCM_AFC("adpcm_afc"),
    /**ADPCM AmuseGraphics Movie AGM*/
    ADPCM_AGM("adpcm_agm"),
    /**ADPCM Yamaha AICA*/
    ADPCM_AICA("adpcm_aica"),
    /**ADPCM Argonaut Games*/
    ADPCM_ARGO("adpcm_argo"),
    /**ADPCM Creative Technology*/
    ADPCM_CT("adpcm_ct"),
    /**ADPCM Nintendo Gamecube DTK*/
    ADPCM_DTK("adpcm_dtk"),
    /**ADPCM Electronic Arts*/
    ADPCM_EA("adpcm_ea"),
    /**ADPCM Electronic Arts Maxis CDROM XA*/
    ADPCM_EA_MAXIS_XA("adpcm_ea_maxis_xa"),
    /**ADPCM Electronic Arts R1*/
    ADPCM_EA_R1("adpcm_ea_r1"),
    /**ADPCM Electronic Arts R2*/
    ADPCM_EA_R2("adpcm_ea_r2"),
    /**ADPCM Electronic Arts R3*/
    ADPCM_EA_R3("adpcm_ea_r3"),
    /**ADPCM Electronic Arts XAS*/
    ADPCM_EA_XAS("adpcm_ea_xas"),
    /**G.722 ADPCM (decoders: g722) (encoders: g722)*/
    ADPCM_G722("adpcm_g722"),
    /**G.726 ADPCM (decoders: g726) (encoders: g726)*/
    ADPCM_G726("adpcm_g726"),
    /**G.726 ADPCM little-endian (decoders: g726le) (encoders: g726le)*/
    ADPCM_G726LE("adpcm_g726le"),
    /**ADPCM IMA Acorn Replay*/
    ADPCM_IMA_ACORN("adpcm_ima_acorn"),
    /**ADPCM IMA High Voltage Software ALP*/
    ADPCM_IMA_ALP("adpcm_ima_alp"),
    /**ADPCM IMA AMV*/
    ADPCM_IMA_AMV("adpcm_ima_amv"),
    /**ADPCM IMA CRYO APC*/
    ADPCM_IMA_APC("adpcm_ima_apc"),
    /**ADPCM IMA Ubisoft APM*/
    ADPCM_IMA_APM("adpcm_ima_apm"),
    /**ADPCM IMA Cunning Developments*/
    ADPCM_IMA_CUNNING("adpcm_ima_cunning"),
    /**ADPCM IMA Eurocom DAT4*/
    ADPCM_IMA_DAT4("adpcm_ima_dat4"),
    /**ADPCM IMA Duck DK3*/
    ADPCM_IMA_DK3("adpcm_ima_dk3"),
    /**ADPCM IMA Duck DK4*/
    ADPCM_IMA_DK4("adpcm_ima_dk4"),
    /**ADPCM IMA Electronic Arts EACS*/
    ADPCM_IMA_EA_EACS("adpcm_ima_ea_eacs"),
    /**ADPCM IMA Electronic Arts SEAD*/
    ADPCM_IMA_EA_SEAD("adpcm_ima_ea_sead"),
    /**ADPCM IMA Funcom ISS*/
    ADPCM_IMA_ISS("adpcm_ima_iss"),
    /**ADPCM IMA MobiClip MOFLEX*/
    ADPCM_IMA_MOFLEX("adpcm_ima_moflex"),
    /**ADPCM IMA Capcom's MT Framework*/
    ADPCM_IMA_MTF("adpcm_ima_mtf"),
    /**ADPCM IMA Dialogic OKI*/
    ADPCM_IMA_OKI("adpcm_ima_oki"),
    /**ADPCM IMA QuickTime*/
    ADPCM_IMA_QT("adpcm_ima_qt"),
    /**ADPCM IMA Radical*/
    ADPCM_IMA_RAD("adpcm_ima_rad"),
    /**ADPCM IMA Loki SDL MJPEG*/
    ADPCM_IMA_SMJPEG("adpcm_ima_smjpeg"),
    /**ADPCM IMA Simon & Schuster Interactive*/
    ADPCM_IMA_SSI("adpcm_ima_ssi"),
    /**ADPCM IMA WAV*/
    ADPCM_IMA_WAV("adpcm_ima_wav"),
    /**ADPCM IMA Westwood*/
    ADPCM_IMA_WS("adpcm_ima_ws"),
    /**ADPCM Microsoft*/
    ADPCM_MS("adpcm_ms"),
    /**ADPCM MTAF*/
    ADPCM_MTAF("adpcm_mtaf"),
    /**ADPCM Playstation*/
    ADPCM_PSX("adpcm_psx"),
    /**ADPCM Sound Blaster Pro 2-bit*/
    ADPCM_SBPRO_2("adpcm_sbpro_2"),
    /**ADPCM Sound Blaster Pro 2.6-bit*/
    ADPCM_SBPRO_3("adpcm_sbpro_3"),
    /**ADPCM Sound Blaster Pro 4-bit*/
    ADPCM_SBPRO_4("adpcm_sbpro_4"),
    /**ADPCM Shockwave Flash*/
    ADPCM_SWF("adpcm_swf"),
    /**ADPCM Nintendo THP*/
    ADPCM_THP("adpcm_thp"),
    /**ADPCM Nintendo THP (Little-Endian)*/
    ADPCM_THP_LE("adpcm_thp_le"),
    /**LucasArts VIMA audio*/
    ADPCM_VIMA("adpcm_vima"),
    /**ADPCM CDROM XA*/
    ADPCM_XA("adpcm_xa"),
    /**ADPCM Konami XMD*/
    ADPCM_XMD("adpcm_xmd"),
    /**ADPCM Yamaha*/
    ADPCM_YAMAHA("adpcm_yamaha"),
    /**ADPCM Zork*/
    ADPCM_ZORK("adpcm_zork"),
    /**ALAC (Apple Lossless Audio Codec)*/
    ALAC("alac"),
    /**AMR-NB (Adaptive Multi-Rate NarrowBand) (decoders: amrnb libopencore_amrnb) (encoders: libopencore_amrnb)*/
    AMR_NB("amr_nb"),
    /**AMR-WB (Adaptive Multi-Rate WideBand) (decoders: amrwb libopencore_amrwb) (encoders: libvo_amrwbenc)*/
    AMR_WB("amr_wb"),
    /**Null audio codec*/
    ANULL("anull"),
    /**Marian's A-pac audio*/
    APAC("apac"),
    /**Monkey's Audio*/
    APE("ape"),
    /**aptX (Audio Processing Technology for Bluetooth)*/
    APTX("aptx"),
    /**aptX HD (Audio Processing Technology for Bluetooth)*/
    APTX_HD("aptx_hd"),
    /**ATRAC1 (Adaptive TRansform Acoustic Coding)*/
    ATRAC1("atrac1"),
    /**ATRAC3 (Adaptive TRansform Acoustic Coding 3)*/
    ATRAC3("atrac3"),
    /**ATRAC3 AL (Adaptive TRansform Acoustic Coding 3 Advanced Lossless)*/
    ATRAC3AL("atrac3al"),
    /**ATRAC3+ (Adaptive TRansform Acoustic Coding 3+) (decoders: atrac3plus)*/
    ATRAC3P("atrac3p"),
    /**ATRAC3+ AL (Adaptive TRansform Acoustic Coding 3+ Advanced Lossless) (decoders: atrac3plusal)*/
    ATRAC3PAL("atrac3pal"),
    /**ATRAC9 (Adaptive TRansform Acoustic Coding 9)*/
    ATRAC9("atrac9"),
    /**On2 Audio for Video Codec (decoders: on2avc)*/
    AVC("avc"),
    /**Bink Audio (DCT)*/
    BINKAUDIO_DCT("binkaudio_dct"),
    /**Bink Audio (RDFT)*/
    BINKAUDIO_RDFT("binkaudio_rdft"),
    /**Discworld II BMV audio*/
    BMV_AUDIO("bmv_audio"),
    /**Bonk audio*/
    BONK("bonk"),
    /**DPCM Cuberoot-Delta-Exact*/
    CBD2_DPCM("cbd2_dpcm"),
    /**Constrained Energy Lapped Transform (CELT)*/
    CELT("celt"),
    /**codec2 (very low bitrate speech codec)*/
    CODEC2("codec2"),
    /**RFC 3389 Comfort Noise*/
    COMFORTNOISE("comfortnoise"),
    /**Cook / Cooker / Gecko (RealAudio G2)*/
    COOK("cook"),
    /**DPCM Xilam DERF*/
    DERF_DPCM("derf_dpcm"),
    /**DFPWM (Dynamic Filter Pulse Width Modulation)*/
    DFPWM("dfpwm"),
    /**Dolby E*/
    DOLBY_E("dolby_e"),
    /**DSD (Direct Stream Digital), least significant bit first*/
    DSD_LSBF("dsd_lsbf"),
    /**DSD (Direct Stream Digital), least significant bit first, planar*/
    DSD_LSBF_PLANAR("dsd_lsbf_planar"),
    /**DSD (Direct Stream Digital), most significant bit first*/
    DSD_MSBF("dsd_msbf"),
    /**DSD (Direct Stream Digital), most significant bit first, planar*/
    DSD_MSBF_PLANAR("dsd_msbf_planar"),
    /**Delphine Software International CIN audio*/
    DSICINAUDIO("dsicinaudio"),
    /**Digital Speech Standard - Standard Play mode (DSS SP)*/
    DSS_SP("dss_sp"),
    /**DST (Direct Stream Transfer)*/
    DST("dst"),
    /**DCA (DTS Coherent Acoustics) (decoders: dca) (encoders: dca)*/
    DTS("dts"),
    /**DV audio*/
    DVAUDIO("dvaudio"),
    /**ATSC A/52B (AC-3, E-AC-3)*/
    EAC3("eac3"),
    /**EVRC (Enhanced Variable Rate Codec)*/
    EVRC("evrc"),
    /**MobiClip FastAudio*/
    FASTAUDIO("fastaudio"),
    /**FLAC (Free Lossless Audio Codec)*/
    FLAC("flac"),
    /**FTR Voice*/
    FTR("ftr"),
    /**G.723.1*/
    G723_1("g723_1"),
    /**G.729*/
    G729("g729"),
    /**DPCM Gremlin*/
    GREMLIN_DPCM("gremlin_dpcm"),
    /**GSM (decoders: gsm libgsm) (encoders: libgsm)*/
    GSM("gsm"),
    /**GSM Microsoft variant (decoders: gsm_ms libgsm_ms) (encoders: libgsm_ms)*/
    GSM_MS("gsm_ms"),
    /**CRI HCA*/
    HCA("hca"),
    /**HCOM Audio*/
    HCOM("hcom"),
    /**IAC (Indeo Audio Coder)*/
    IAC("iac"),
    /**iLBC (Internet Low Bitrate Codec)*/
    ILBC("ilbc"),
    /**IMC (Intel Music Coder)*/
    IMC("imc"),
    /**DPCM Interplay*/
    INTERPLAY_DPCM("interplay_dpcm"),
    /**Interplay ACM*/
    INTERPLAYACM("interplayacm"),
    /**MACE (Macintosh Audio Compression/Expansion) 3:1*/
    MACE3("mace3"),
    /**MACE (Macintosh Audio Compression/Expansion) 6:1*/
    MACE6("mace6"),
    /**Voxware MetaSound*/
    METASOUND("metasound"),
    /**Micronas SC-4 Audio*/
    MISC4("misc4"),
    /**MLP (Meridian Lossless Packing)*/
    MLP("mlp"),
    /**MP1 (MPEG audio layer 1) (decoders: mp1 mp1float)*/
    MP1("mp1"),
    /**MP2 (MPEG audio layer 2) (decoders: mp2 mp2float) (encoders: mp2 mp2fixed)*/
    MP2("mp2"),
    /**MP3 (MPEG audio layer 3) (decoders: mp3float mp3) (encoders: libmp3lame mp3_mf)*/
    MP3("mp3"),
    /**ADU (Application Data Unit) MP3 (MPEG audio layer 3) (decoders: mp3adufloat mp3adu)*/
    MP3ADU("mp3adu"),
    /**MP3onMP4 (decoders: mp3on4float mp3on4)*/
    MP3ON4("mp3on4"),
    /**MPEG-4 Audio Lossless Coding (ALS) (decoders: als)*/
    MP4ALS("mp4als"),
    /**MPEG-H 3D Audio*/
    MPEGH_3D_AUDIO("mpegh_3d_audio"),
    /**MSN Siren*/
    MSNSIREN("msnsiren"),
    /**Musepack SV7 (decoders: mpc7)*/
    MUSEPACK7("musepack7"),
    /**Musepack SV8 (decoders: mpc8)*/
    MUSEPACK8("musepack8"),
    /**Nellymoser Asao*/
    NELLYMOSER("nellymoser"),
    /**Opus (Opus Interactive Audio Codec) (decoders: opus libopus) (encoders: opus libopus)*/
    OPUS("opus"),
    /**OSQ (Original Sound Quality)*/
    OSQ("osq"),
    /**Amazing Studio Packed Animation File Audio*/
    PAF_AUDIO("paf_audio"),
    /**PCM A-law / G.711 A-law*/
    PCM_ALAW("pcm_alaw"),
    /**PCM signed 16|20|24-bit big-endian for Blu-ray media*/
    PCM_BLURAY("pcm_bluray"),
    /**PCM signed 20|24-bit big-endian*/
    PCM_DVD("pcm_dvd"),
    /**PCM 16.8 floating point little-endian*/
    PCM_F16LE("pcm_f16le"),
    /**PCM 24.0 floating point little-endian*/
    PCM_F24LE("pcm_f24le"),
    /**PCM 32-bit floating point big-endian*/
    PCM_F32BE("pcm_f32be"),
    /**PCM 32-bit floating point little-endian*/
    PCM_F32LE("pcm_f32le"),
    /**PCM 64-bit floating point big-endian*/
    PCM_F64BE("pcm_f64be"),
    /**PCM 64-bit floating point little-endian*/
    PCM_F64LE("pcm_f64le"),
    /**PCM signed 20-bit little-endian planar*/
    PCM_LXF("pcm_lxf"),
    /**PCM mu-law / G.711 mu-law*/
    PCM_MULAW("pcm_mulaw"),
    /**PCM signed 16-bit big-endian*/
    PCM_S16BE("pcm_s16be"),
    /**PCM signed 16-bit big-endian planar*/
    PCM_S16BE_PLANAR("pcm_s16be_planar"),
    /**PCM signed 16-bit little-endian*/
    PCM_S16LE("pcm_s16le"),
    /**PCM signed 16-bit little-endian planar*/
    PCM_S16LE_PLANAR("pcm_s16le_planar"),
    /**PCM signed 24-bit big-endian*/
    PCM_S24BE("pcm_s24be"),
    /**PCM D-Cinema audio signed 24-bit*/
    PCM_S24DAUD("pcm_s24daud"),
    /**PCM signed 24-bit little-endian*/
    PCM_S24LE("pcm_s24le"),
    /**PCM signed 24-bit little-endian planar*/
    PCM_S24LE_PLANAR("pcm_s24le_planar"),
    /**PCM signed 32-bit big-endian*/
    PCM_S32BE("pcm_s32be"),
    /**PCM signed 32-bit little-endian*/
    PCM_S32LE("pcm_s32le"),
    /**PCM signed 32-bit little-endian planar*/
    PCM_S32LE_PLANAR("pcm_s32le_planar"),
    /**PCM signed 64-bit big-endian*/
    PCM_S64BE("pcm_s64be"),
    /**PCM signed 64-bit little-endian*/
    PCM_S64LE("pcm_s64le"),
    /**PCM signed 8-bit*/
    PCM_S8("pcm_s8"),
    /**PCM signed 8-bit planar*/
    PCM_S8_PLANAR("pcm_s8_planar"),
    /**PCM SGA*/
    PCM_SGA("pcm_sga"),
    /**PCM unsigned 16-bit big-endian*/
    PCM_U16BE("pcm_u16be"),
    /**PCM unsigned 16-bit little-endian*/
    PCM_U16LE("pcm_u16le"),
    /**PCM unsigned 24-bit big-endian*/
    PCM_U24BE("pcm_u24be"),
    /**PCM unsigned 24-bit little-endian*/
    PCM_U24LE("pcm_u24le"),
    /**PCM unsigned 32-bit big-endian*/
    PCM_U32BE("pcm_u32be"),
    /**PCM unsigned 32-bit little-endian*/
    PCM_U32LE("pcm_u32le"),
    /**PCM unsigned 8-bit*/
    PCM_U8("pcm_u8"),
    /**PCM Archimedes VIDC*/
    PCM_VIDC("pcm_vidc"),
    /**QCELP / PureVoice*/
    QCELP("qcelp"),
    /**QDesign Music Codec 2*/
    QDM2("qdm2"),
    /**QDesign Music*/
    QDMC("qdmc"),
    /**RealAudio 1.0 (14.4K) (decoders: real_144) (encoders: real_144)*/
    RA_144("ra_144"),
    /**RealAudio 2.0 (28.8K) (decoders: real_288)*/
    RA_288("ra_288"),
    /**RealAudio Lossless*/
    RALF("ralf"),
    /**RKA (RK Audio)*/
    RKA("rka"),
    /**DPCM id RoQ*/
    ROQ_DPCM("roq_dpcm"),
    /**SMPTE 302M*/
    S302M("s302m"),
    /**SBC (low-complexity subband codec)*/
    SBC("sbc"),
    /**DPCM Squareroot-Delta-Exact*/
    SDX2_DPCM("sdx2_dpcm"),
    /**Shorten*/
    SHORTEN("shorten"),
    /**RealAudio SIPR / ACELP.NET*/
    SIPR("sipr"),
    /**Siren*/
    SIREN("siren"),
    /**Smacker audio (decoders: smackaud)*/
    SMACKAUDIO("smackaudio"),
    /**SMV (Selectable Mode Vocoder)*/
    SMV("smv"),
    /**DPCM Sol*/
    SOL_DPCM("sol_dpcm"),
    /**Sonic*/
    SONIC("sonic"),
    /**Sonic lossless*/
    SONICLS("sonicls"),
    /**Speex (decoders: speex libspeex) (encoders: libspeex)*/
    SPEEX("speex"),
    /**TAK (Tom's lossless Audio Kompressor)*/
    TAK("tak"),
    /**TrueHD*/
    TRUEHD("truehd"),
    /**DSP Group TrueSpeech*/
    TRUESPEECH("truespeech"),
    /**TTA (True Audio)*/
    TTA("tta"),
    /**VQF TwinVQ*/
    TWINVQ("twinvq"),
    /**Sierra VMD audio*/
    VMDAUDIO("vmdaudio"),
    /**Vorbis (decoders: vorbis libvorbis) (encoders: vorbis libvorbis)*/
    VORBIS("vorbis"),
    /**DPCM Marble WADY*/
    WADY_DPCM("wady_dpcm"),
    /**Waveform Archiver*/
    WAVARC("wavarc"),
    /**Wave synthesis pseudo-codec*/
    WAVESYNTH("wavesynth"),
    /**WavPack*/
    WAVPACK("wavpack"),
    /**Westwood Audio (SND1) (decoders: ws_snd1)*/
    WESTWOOD_SND1("westwood_snd1"),
    /**Windows Media Audio Lossless*/
    WMALOSSLESS("wmalossless"),
    /**Windows Media Audio 9 Professional*/
    WMAPRO("wmapro"),
    /**Windows Media Audio 1*/
    WMAV1("wmav1"),
    /**Windows Media Audio 2*/
    WMAV2("wmav2"),
    /**Windows Media Audio Voice*/
    WMAVOICE("wmavoice"),
    /**DPCM Xan*/
    XAN_DPCM("xan_dpcm"),
    /**Xbox Media Audio 1*/
    XMA1("xma1"),
    /**Xbox Media Audio 2*/
    XMA2("xma2");
    final String codec;

    AudioCodec(String codec) {
        this.codec = codec;
    }

    @Override
    public String toString() {
        return this.codec;
    }
}

