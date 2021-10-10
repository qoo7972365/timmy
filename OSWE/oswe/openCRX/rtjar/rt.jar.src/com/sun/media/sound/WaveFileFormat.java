/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import javax.sound.sampled.AudioFileFormat;
/*     */ import javax.sound.sampled.AudioFormat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class WaveFileFormat
/*     */   extends AudioFileFormat
/*     */ {
/*     */   private final int waveType;
/*     */   private static final int STANDARD_HEADER_SIZE = 28;
/*     */   private static final int STANDARD_FMT_CHUNK_SIZE = 16;
/*     */   static final int RIFF_MAGIC = 1380533830;
/*     */   static final int WAVE_MAGIC = 1463899717;
/*     */   static final int FMT_MAGIC = 1718449184;
/*     */   static final int DATA_MAGIC = 1684108385;
/*     */   static final int WAVE_FORMAT_UNKNOWN = 0;
/*     */   static final int WAVE_FORMAT_PCM = 1;
/*     */   static final int WAVE_FORMAT_ADPCM = 2;
/*     */   static final int WAVE_FORMAT_ALAW = 6;
/*     */   static final int WAVE_FORMAT_MULAW = 7;
/*     */   static final int WAVE_FORMAT_OKI_ADPCM = 16;
/*     */   static final int WAVE_FORMAT_DIGISTD = 21;
/*     */   static final int WAVE_FORMAT_DIGIFIX = 22;
/*     */   static final int WAVE_IBM_FORMAT_MULAW = 257;
/*     */   static final int WAVE_IBM_FORMAT_ALAW = 258;
/*     */   static final int WAVE_IBM_FORMAT_ADPCM = 259;
/*     */   static final int WAVE_FORMAT_DVI_ADPCM = 17;
/*     */   static final int WAVE_FORMAT_SX7383 = 7175;
/*     */   
/*     */   WaveFileFormat(AudioFileFormat paramAudioFileFormat) {
/*  79 */     this(paramAudioFileFormat.getType(), paramAudioFileFormat.getByteLength(), paramAudioFileFormat.getFormat(), paramAudioFileFormat.getFrameLength());
/*     */   }
/*     */ 
/*     */   
/*     */   WaveFileFormat(AudioFileFormat.Type paramType, int paramInt1, AudioFormat paramAudioFormat, int paramInt2) {
/*  84 */     super(paramType, paramInt1, paramAudioFormat, paramInt2);
/*     */     
/*  86 */     AudioFormat.Encoding encoding = paramAudioFormat.getEncoding();
/*     */     
/*  88 */     if (encoding.equals(AudioFormat.Encoding.ALAW)) {
/*  89 */       this.waveType = 6;
/*  90 */     } else if (encoding.equals(AudioFormat.Encoding.ULAW)) {
/*  91 */       this.waveType = 7;
/*  92 */     } else if (encoding.equals(AudioFormat.Encoding.PCM_SIGNED) || encoding
/*  93 */       .equals(AudioFormat.Encoding.PCM_UNSIGNED)) {
/*  94 */       this.waveType = 1;
/*     */     } else {
/*  96 */       this.waveType = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   int getWaveType() {
/* 102 */     return this.waveType;
/*     */   }
/*     */   
/*     */   int getHeaderSize() {
/* 106 */     return getHeaderSize(getWaveType());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static int getHeaderSize(int paramInt) {
/* 112 */     return 28 + getFmtChunkSize(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static int getFmtChunkSize(int paramInt) {
/* 118 */     byte b = 16;
/* 119 */     if (paramInt != 1) {
/* 120 */       b += 2;
/*     */     }
/* 122 */     return b;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/WaveFileFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */