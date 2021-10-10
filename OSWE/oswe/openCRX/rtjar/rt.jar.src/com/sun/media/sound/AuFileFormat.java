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
/*     */ final class AuFileFormat
/*     */   extends AudioFileFormat
/*     */ {
/*     */   static final int AU_SUN_MAGIC = 779316836;
/*     */   static final int AU_SUN_INV_MAGIC = 1684960046;
/*     */   static final int AU_DEC_MAGIC = 779314176;
/*     */   static final int AU_DEC_INV_MAGIC = 6583086;
/*     */   static final int AU_ULAW_8 = 1;
/*     */   static final int AU_LINEAR_8 = 2;
/*     */   static final int AU_LINEAR_16 = 3;
/*     */   static final int AU_LINEAR_24 = 4;
/*     */   static final int AU_LINEAR_32 = 5;
/*     */   static final int AU_FLOAT = 6;
/*     */   static final int AU_DOUBLE = 7;
/*     */   static final int AU_ADPCM_G721 = 23;
/*     */   static final int AU_ADPCM_G722 = 24;
/*     */   static final int AU_ADPCM_G723_3 = 25;
/*     */   static final int AU_ADPCM_G723_5 = 26;
/*     */   static final int AU_ALAW_8 = 27;
/*     */   static final int AU_HEADERSIZE = 24;
/*     */   private int auType;
/*     */   
/*     */   AuFileFormat(AudioFileFormat paramAudioFileFormat) {
/*  67 */     this(paramAudioFileFormat.getType(), paramAudioFileFormat.getByteLength(), paramAudioFileFormat.getFormat(), paramAudioFileFormat.getFrameLength());
/*     */   }
/*     */ 
/*     */   
/*     */   AuFileFormat(AudioFileFormat.Type paramType, int paramInt1, AudioFormat paramAudioFormat, int paramInt2) {
/*  72 */     super(paramType, paramInt1, paramAudioFormat, paramInt2);
/*     */     
/*  74 */     AudioFormat.Encoding encoding = paramAudioFormat.getEncoding();
/*     */     
/*  76 */     this.auType = -1;
/*     */     
/*  78 */     if (AudioFormat.Encoding.ALAW.equals(encoding)) {
/*  79 */       if (paramAudioFormat.getSampleSizeInBits() == 8) {
/*  80 */         this.auType = 27;
/*     */       }
/*  82 */     } else if (AudioFormat.Encoding.ULAW.equals(encoding)) {
/*  83 */       if (paramAudioFormat.getSampleSizeInBits() == 8) {
/*  84 */         this.auType = 1;
/*     */       }
/*  86 */     } else if (AudioFormat.Encoding.PCM_SIGNED.equals(encoding)) {
/*  87 */       if (paramAudioFormat.getSampleSizeInBits() == 8) {
/*  88 */         this.auType = 2;
/*  89 */       } else if (paramAudioFormat.getSampleSizeInBits() == 16) {
/*  90 */         this.auType = 3;
/*  91 */       } else if (paramAudioFormat.getSampleSizeInBits() == 24) {
/*  92 */         this.auType = 4;
/*  93 */       } else if (paramAudioFormat.getSampleSizeInBits() == 32) {
/*  94 */         this.auType = 5;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAuType() {
/* 102 */     return this.auType;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/AuFileFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */