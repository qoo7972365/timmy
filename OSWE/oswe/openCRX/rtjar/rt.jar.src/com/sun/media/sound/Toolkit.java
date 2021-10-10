/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import javax.sound.sampled.AudioFormat;
/*     */ import javax.sound.sampled.AudioInputStream;
/*     */ import javax.sound.sampled.AudioSystem;
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
/*     */ public final class Toolkit
/*     */ {
/*     */   static void getUnsigned8(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*  50 */     for (int i = paramInt1; i < paramInt1 + paramInt2; i++) {
/*  51 */       paramArrayOfbyte[i] = (byte)(paramArrayOfbyte[i] + 128);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void getByteSwapped(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*  63 */     for (int i = paramInt1; i < paramInt1 + paramInt2; i += 2) {
/*     */       
/*  65 */       byte b = paramArrayOfbyte[i];
/*  66 */       paramArrayOfbyte[i] = paramArrayOfbyte[i + 1];
/*  67 */       paramArrayOfbyte[i + 1] = b;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static float linearToDB(float paramFloat) {
/*  77 */     return (float)(Math.log((paramFloat == 0.0D) ? 1.0E-4D : paramFloat) / Math.log(10.0D) * 20.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static float dBToLinear(float paramFloat) {
/*  87 */     return (float)Math.pow(10.0D, paramFloat / 20.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long align(long paramLong, int paramInt) {
/*  97 */     if (paramInt <= 1) {
/*  98 */       return paramLong;
/*     */     }
/* 100 */     return paramLong - paramLong % paramInt;
/*     */   }
/*     */ 
/*     */   
/*     */   static int align(int paramInt1, int paramInt2) {
/* 105 */     if (paramInt2 <= 1) {
/* 106 */       return paramInt1;
/*     */     }
/* 108 */     return paramInt1 - paramInt1 % paramInt2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long millis2bytes(AudioFormat paramAudioFormat, long paramLong) {
/* 116 */     long l = (long)((float)paramLong * paramAudioFormat.getFrameRate() / 1000.0F * paramAudioFormat.getFrameSize());
/* 117 */     return align(l, paramAudioFormat.getFrameSize());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long bytes2millis(AudioFormat paramAudioFormat, long paramLong) {
/* 124 */     return (long)((float)paramLong / paramAudioFormat.getFrameRate() * 1000.0F / paramAudioFormat.getFrameSize());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long micros2bytes(AudioFormat paramAudioFormat, long paramLong) {
/* 131 */     long l = (long)((float)paramLong * paramAudioFormat.getFrameRate() / 1000000.0F * paramAudioFormat.getFrameSize());
/* 132 */     return align(l, paramAudioFormat.getFrameSize());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long bytes2micros(AudioFormat paramAudioFormat, long paramLong) {
/* 139 */     return (long)((float)paramLong / paramAudioFormat.getFrameRate() * 1000000.0F / paramAudioFormat.getFrameSize());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long micros2frames(AudioFormat paramAudioFormat, long paramLong) {
/* 146 */     return (long)((float)paramLong * paramAudioFormat.getFrameRate() / 1000000.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long frames2micros(AudioFormat paramAudioFormat, long paramLong) {
/* 153 */     return (long)(paramLong / paramAudioFormat.getFrameRate() * 1000000.0D);
/*     */   }
/*     */   
/*     */   static void isFullySpecifiedAudioFormat(AudioFormat paramAudioFormat) {
/* 157 */     if (!paramAudioFormat.getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED) && 
/* 158 */       !paramAudioFormat.getEncoding().equals(AudioFormat.Encoding.PCM_UNSIGNED) && 
/* 159 */       !paramAudioFormat.getEncoding().equals(AudioFormat.Encoding.ULAW) && 
/* 160 */       !paramAudioFormat.getEncoding().equals(AudioFormat.Encoding.ALAW)) {
/*     */       return;
/*     */     }
/*     */     
/* 164 */     if (paramAudioFormat.getFrameRate() <= 0.0F) {
/* 165 */       throw new IllegalArgumentException("invalid frame rate: " + (
/* 166 */           (paramAudioFormat.getFrameRate() == -1.0F) ? "NOT_SPECIFIED" : 
/* 167 */           String.valueOf(paramAudioFormat.getFrameRate())));
/*     */     }
/* 169 */     if (paramAudioFormat.getSampleRate() <= 0.0F) {
/* 170 */       throw new IllegalArgumentException("invalid sample rate: " + (
/* 171 */           (paramAudioFormat.getSampleRate() == -1.0F) ? "NOT_SPECIFIED" : 
/* 172 */           String.valueOf(paramAudioFormat.getSampleRate())));
/*     */     }
/* 174 */     if (paramAudioFormat.getSampleSizeInBits() <= 0) {
/* 175 */       throw new IllegalArgumentException("invalid sample size in bits: " + (
/* 176 */           (paramAudioFormat.getSampleSizeInBits() == -1) ? "NOT_SPECIFIED" : 
/* 177 */           String.valueOf(paramAudioFormat.getSampleSizeInBits())));
/*     */     }
/* 179 */     if (paramAudioFormat.getFrameSize() <= 0) {
/* 180 */       throw new IllegalArgumentException("invalid frame size: " + (
/* 181 */           (paramAudioFormat.getFrameSize() == -1) ? "NOT_SPECIFIED" : 
/* 182 */           String.valueOf(paramAudioFormat.getFrameSize())));
/*     */     }
/* 184 */     if (paramAudioFormat.getChannels() <= 0) {
/* 185 */       throw new IllegalArgumentException("invalid number of channels: " + (
/* 186 */           (paramAudioFormat.getChannels() == -1) ? "NOT_SPECIFIED" : 
/* 187 */           String.valueOf(paramAudioFormat.getChannels())));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean isFullySpecifiedPCMFormat(AudioFormat paramAudioFormat) {
/* 193 */     if (!paramAudioFormat.getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED) && 
/* 194 */       !paramAudioFormat.getEncoding().equals(AudioFormat.Encoding.PCM_UNSIGNED)) {
/* 195 */       return false;
/*     */     }
/* 197 */     if (paramAudioFormat.getFrameRate() <= 0.0F || paramAudioFormat
/* 198 */       .getSampleRate() <= 0.0F || paramAudioFormat
/* 199 */       .getSampleSizeInBits() <= 0 || paramAudioFormat
/* 200 */       .getFrameSize() <= 0 || paramAudioFormat
/* 201 */       .getChannels() <= 0) {
/* 202 */       return false;
/*     */     }
/* 204 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AudioInputStream getPCMConvertedAudioInputStream(AudioInputStream paramAudioInputStream) {
/* 211 */     AudioFormat audioFormat = paramAudioInputStream.getFormat();
/*     */     
/* 213 */     if (!audioFormat.getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED) && 
/* 214 */       !audioFormat.getEncoding().equals(AudioFormat.Encoding.PCM_UNSIGNED)) {
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 224 */         AudioFormat audioFormat1 = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, audioFormat.getSampleRate(), 16, audioFormat.getChannels(), audioFormat.getChannels() * 2, audioFormat.getSampleRate(), Platform.isBigEndian());
/* 225 */         paramAudioInputStream = AudioSystem.getAudioInputStream(audioFormat1, paramAudioInputStream);
/* 226 */       } catch (Exception exception) {
/*     */         
/* 228 */         paramAudioInputStream = null;
/*     */       } 
/*     */     }
/*     */     
/* 232 */     return paramAudioInputStream;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/Toolkit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */