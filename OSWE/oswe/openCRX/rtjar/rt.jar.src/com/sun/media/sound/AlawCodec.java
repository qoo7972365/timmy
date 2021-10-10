/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Vector;
/*     */ import javax.sound.sampled.AudioFormat;
/*     */ import javax.sound.sampled.AudioInputStream;
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
/*     */ public final class AlawCodec
/*     */   extends SunCodec
/*     */ {
/*  45 */   private static final byte[] ALAW_TABH = new byte[256];
/*  46 */   private static final byte[] ALAW_TABL = new byte[256];
/*     */   
/*  48 */   private static final AudioFormat.Encoding[] alawEncodings = new AudioFormat.Encoding[] { AudioFormat.Encoding.ALAW, AudioFormat.Encoding.PCM_SIGNED };
/*     */   
/*  50 */   private static final short[] seg_end = new short[] { 255, 511, 1023, 2047, 4095, 8191, 16383, Short.MAX_VALUE };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  57 */     for (byte b = 0; b < 'Ā'; b++) {
/*  58 */       int i = b ^ 0x55;
/*  59 */       int j = (i & 0xF) << 4;
/*  60 */       int k = (i & 0x70) >> 4;
/*  61 */       int m = j + 8;
/*     */       
/*  63 */       if (k >= 1)
/*  64 */         m += 256; 
/*  65 */       if (k > 1) {
/*  66 */         m <<= k - 1;
/*     */       }
/*  68 */       if ((i & 0x80) == 0) {
/*  69 */         m = -m;
/*     */       }
/*  71 */       ALAW_TABL[b] = (byte)m;
/*  72 */       ALAW_TABH[b] = (byte)(m >> 8);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AlawCodec() {
/*  82 */     super(alawEncodings, alawEncodings);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioFormat.Encoding[] getTargetEncodings(AudioFormat paramAudioFormat) {
/*  91 */     if (paramAudioFormat.getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED)) {
/*     */       
/*  93 */       if (paramAudioFormat.getSampleSizeInBits() == 16) {
/*     */         
/*  95 */         AudioFormat.Encoding[] arrayOfEncoding = new AudioFormat.Encoding[1];
/*  96 */         arrayOfEncoding[0] = AudioFormat.Encoding.ALAW;
/*  97 */         return arrayOfEncoding;
/*     */       } 
/*     */       
/* 100 */       return new AudioFormat.Encoding[0];
/*     */     } 
/* 102 */     if (paramAudioFormat.getEncoding().equals(AudioFormat.Encoding.ALAW)) {
/*     */       
/* 104 */       if (paramAudioFormat.getSampleSizeInBits() == 8) {
/*     */         
/* 106 */         AudioFormat.Encoding[] arrayOfEncoding = new AudioFormat.Encoding[1];
/* 107 */         arrayOfEncoding[0] = AudioFormat.Encoding.PCM_SIGNED;
/* 108 */         return arrayOfEncoding;
/*     */       } 
/*     */       
/* 111 */       return new AudioFormat.Encoding[0];
/*     */     } 
/*     */ 
/*     */     
/* 115 */     return new AudioFormat.Encoding[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioFormat[] getTargetFormats(AudioFormat.Encoding paramEncoding, AudioFormat paramAudioFormat) {
/* 122 */     if ((paramEncoding.equals(AudioFormat.Encoding.PCM_SIGNED) && paramAudioFormat.getEncoding().equals(AudioFormat.Encoding.ALAW)) || (paramEncoding
/* 123 */       .equals(AudioFormat.Encoding.ALAW) && paramAudioFormat.getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED))) {
/* 124 */       return getOutputFormats(paramAudioFormat);
/*     */     }
/* 126 */     return new AudioFormat[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioInputStream getAudioInputStream(AudioFormat.Encoding paramEncoding, AudioInputStream paramAudioInputStream) {
/* 133 */     AudioFormat audioFormat1 = paramAudioInputStream.getFormat();
/* 134 */     AudioFormat.Encoding encoding = audioFormat1.getEncoding();
/*     */     
/* 136 */     if (encoding.equals(paramEncoding)) {
/* 137 */       return paramAudioInputStream;
/*     */     }
/* 139 */     AudioFormat audioFormat2 = null;
/* 140 */     if (!isConversionSupported(paramEncoding, paramAudioInputStream.getFormat())) {
/* 141 */       throw new IllegalArgumentException("Unsupported conversion: " + paramAudioInputStream.getFormat().toString() + " to " + paramEncoding.toString());
/*     */     }
/* 143 */     if (encoding.equals(AudioFormat.Encoding.ALAW) && paramEncoding
/* 144 */       .equals(AudioFormat.Encoding.PCM_SIGNED)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 152 */       audioFormat2 = new AudioFormat(paramEncoding, audioFormat1.getSampleRate(), 16, audioFormat1.getChannels(), 2 * audioFormat1.getChannels(), audioFormat1.getSampleRate(), audioFormat1.isBigEndian());
/*     */     }
/* 154 */     else if (encoding.equals(AudioFormat.Encoding.PCM_SIGNED) && paramEncoding
/* 155 */       .equals(AudioFormat.Encoding.ALAW)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 162 */       audioFormat2 = new AudioFormat(paramEncoding, audioFormat1.getSampleRate(), 8, audioFormat1.getChannels(), audioFormat1.getChannels(), audioFormat1.getSampleRate(), false);
/*     */     } else {
/*     */       
/* 165 */       throw new IllegalArgumentException("Unsupported conversion: " + paramAudioInputStream.getFormat().toString() + " to " + paramEncoding.toString());
/*     */     } 
/* 167 */     return getAudioInputStream(audioFormat2, paramAudioInputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioInputStream getAudioInputStream(AudioFormat paramAudioFormat, AudioInputStream paramAudioInputStream) {
/* 175 */     return getConvertedStream(paramAudioFormat, paramAudioInputStream);
/*     */   }
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
/*     */   private AudioInputStream getConvertedStream(AudioFormat paramAudioFormat, AudioInputStream paramAudioInputStream) {
/* 193 */     AudioInputStream audioInputStream = null;
/* 194 */     AudioFormat audioFormat = paramAudioInputStream.getFormat();
/*     */     
/* 196 */     if (audioFormat.matches(paramAudioFormat)) {
/* 197 */       audioInputStream = paramAudioInputStream;
/*     */     } else {
/* 199 */       audioInputStream = new AlawCodecStream(paramAudioInputStream, paramAudioFormat);
/*     */     } 
/*     */     
/* 202 */     return audioInputStream;
/*     */   }
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
/*     */   private AudioFormat[] getOutputFormats(AudioFormat paramAudioFormat) {
/* 216 */     Vector<AudioFormat> vector = new Vector();
/*     */ 
/*     */     
/* 219 */     if (AudioFormat.Encoding.PCM_SIGNED.equals(paramAudioFormat.getEncoding())) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 225 */       AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.ALAW, paramAudioFormat.getSampleRate(), 8, paramAudioFormat.getChannels(), paramAudioFormat.getChannels(), paramAudioFormat.getSampleRate(), false);
/*     */       
/* 227 */       vector.addElement(audioFormat);
/*     */     } 
/*     */     
/* 230 */     if (AudioFormat.Encoding.ALAW.equals(paramAudioFormat.getEncoding())) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 236 */       AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, paramAudioFormat.getSampleRate(), 16, paramAudioFormat.getChannels(), paramAudioFormat.getChannels() * 2, paramAudioFormat.getSampleRate(), false);
/*     */       
/* 238 */       vector.addElement(audioFormat);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 244 */       audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, paramAudioFormat.getSampleRate(), 16, paramAudioFormat.getChannels(), paramAudioFormat.getChannels() * 2, paramAudioFormat.getSampleRate(), true);
/*     */       
/* 246 */       vector.addElement(audioFormat);
/*     */     } 
/*     */     
/* 249 */     AudioFormat[] arrayOfAudioFormat = new AudioFormat[vector.size()];
/* 250 */     for (byte b = 0; b < arrayOfAudioFormat.length; b++) {
/* 251 */       arrayOfAudioFormat[b] = vector.elementAt(b);
/*     */     }
/* 253 */     return arrayOfAudioFormat;
/*     */   }
/*     */ 
/*     */   
/*     */   final class AlawCodecStream
/*     */     extends AudioInputStream
/*     */   {
/*     */     private static final int tempBufferSize = 64;
/*     */     
/*     */     private byte[] tempBuffer;
/*     */     
/*     */     boolean encode;
/*     */     
/*     */     AudioFormat encodeFormat;
/*     */     
/*     */     AudioFormat decodeFormat;
/*     */     
/*     */     byte[] tabByte1;
/*     */     
/*     */     byte[] tabByte2;
/*     */     
/*     */     int highByte;
/*     */     int lowByte;
/*     */     
/*     */     AlawCodecStream(AudioInputStream param1AudioInputStream, AudioFormat param1AudioFormat) {
/* 278 */       super(param1AudioInputStream, param1AudioFormat, -1L); boolean bool; this.tempBuffer = null; this.encode = false; this.tabByte1 = null; this.tabByte2 = null; this.highByte = 0;
/*     */       this.lowByte = 1;
/* 280 */       AudioFormat audioFormat = param1AudioInputStream.getFormat();
/*     */ 
/*     */       
/* 283 */       if (!AlawCodec.this.isConversionSupported(param1AudioFormat, audioFormat))
/*     */       {
/* 285 */         throw new IllegalArgumentException("Unsupported conversion: " + audioFormat.toString() + " to " + param1AudioFormat.toString());
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 292 */       if (AudioFormat.Encoding.ALAW.equals(audioFormat.getEncoding())) {
/* 293 */         this.encode = false;
/* 294 */         this.encodeFormat = audioFormat;
/* 295 */         this.decodeFormat = param1AudioFormat;
/* 296 */         bool = param1AudioFormat.isBigEndian();
/*     */       } else {
/* 298 */         this.encode = true;
/* 299 */         this.encodeFormat = param1AudioFormat;
/* 300 */         this.decodeFormat = audioFormat;
/* 301 */         bool = audioFormat.isBigEndian();
/* 302 */         this.tempBuffer = new byte[64];
/*     */       } 
/*     */       
/* 305 */       if (bool) {
/* 306 */         this.tabByte1 = AlawCodec.ALAW_TABH;
/* 307 */         this.tabByte2 = AlawCodec.ALAW_TABL;
/* 308 */         this.highByte = 0;
/* 309 */         this.lowByte = 1;
/*     */       } else {
/* 311 */         this.tabByte1 = AlawCodec.ALAW_TABL;
/* 312 */         this.tabByte2 = AlawCodec.ALAW_TABH;
/* 313 */         this.highByte = 1;
/* 314 */         this.lowByte = 0;
/*     */       } 
/*     */ 
/*     */       
/* 318 */       if (param1AudioInputStream instanceof AudioInputStream) {
/* 319 */         this.frameLength = param1AudioInputStream.getFrameLength();
/*     */       }
/*     */ 
/*     */       
/* 323 */       this.framePos = 0L;
/* 324 */       this.frameSize = audioFormat.getFrameSize();
/* 325 */       if (this.frameSize == -1) {
/* 326 */         this.frameSize = 1;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private short search(short param1Short1, short[] param1ArrayOfshort, short param1Short2) {
/*     */       short s;
/* 336 */       for (s = 0; s < param1Short2; s = (short)(s + 1)) {
/* 337 */         if (param1Short1 <= param1ArrayOfshort[s]) return s; 
/*     */       } 
/* 339 */       return param1Short2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int read() throws IOException {
/* 348 */       byte[] arrayOfByte = new byte[1];
/* 349 */       return read(arrayOfByte, 0, arrayOfByte.length);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int read(byte[] param1ArrayOfbyte) throws IOException {
/* 355 */       return read(param1ArrayOfbyte, 0, param1ArrayOfbyte.length);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/* 361 */       if (param1Int2 % this.frameSize != 0) {
/* 362 */         param1Int2 -= param1Int2 % this.frameSize;
/*     */       }
/*     */       
/* 365 */       if (this.encode) {
/*     */         
/* 367 */         byte b1 = 15;
/* 368 */         byte b2 = 4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 377 */         int n = 0;
/* 378 */         int i1 = param1Int1;
/* 379 */         int i2 = param1Int2 * 2;
/* 380 */         boolean bool = (i2 > 64) ? true : i2;
/*     */         
/* 382 */         while ((n = super.read(this.tempBuffer, 0, bool)) > 0) {
/*     */           
/* 384 */           for (byte b = 0; b < n; b += 2) {
/*     */             byte b3;
/*     */             byte b4;
/* 387 */             short s2 = (short)(this.tempBuffer[b + this.highByte] << 8 & 0xFF00);
/* 388 */             s2 = (short)(s2 | (short)(this.tempBuffer[b + this.lowByte] & 0xFF));
/*     */             
/* 390 */             if (s2 >= 0) {
/* 391 */               b3 = 213;
/*     */             } else {
/* 393 */               b3 = 85;
/* 394 */               s2 = (short)(-s2 - 8);
/*     */             } 
/*     */             
/* 397 */             short s1 = search(s2, AlawCodec.seg_end, (short)8);
/*     */ 
/*     */ 
/*     */             
/* 401 */             if (s1 >= 8) {
/* 402 */               b4 = (byte)(0x7F ^ b3);
/*     */             } else {
/* 404 */               b4 = (byte)(s1 << b2);
/* 405 */               if (s1 < 2) {
/* 406 */                 b4 = (byte)(b4 | (byte)(s2 >> 4 & b1));
/*     */               } else {
/* 408 */                 b4 = (byte)(b4 | (byte)(s2 >> s1 + 3 & b1));
/*     */               } 
/* 410 */               b4 = (byte)(b4 ^ b3);
/*     */             } 
/*     */             
/* 413 */             param1ArrayOfbyte[i1] = b4;
/* 414 */             i1++;
/*     */           } 
/*     */           
/* 417 */           i2 -= n;
/* 418 */           bool = (i2 > 64) ? true : i2;
/*     */         } 
/*     */         
/* 421 */         if (i1 == param1Int1 && n < 0) {
/* 422 */           return n;
/*     */         }
/*     */         
/* 425 */         return i1 - param1Int1;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 430 */       int j = param1Int2 / 2;
/* 431 */       int k = param1Int1 + param1Int2 / 2;
/* 432 */       int m = super.read(param1ArrayOfbyte, k, j);
/*     */       int i;
/* 434 */       for (i = param1Int1; i < param1Int1 + m * 2; i += 2) {
/* 435 */         param1ArrayOfbyte[i] = this.tabByte1[param1ArrayOfbyte[k] & 0xFF];
/* 436 */         param1ArrayOfbyte[i + 1] = this.tabByte2[param1ArrayOfbyte[k] & 0xFF];
/* 437 */         k++;
/*     */       } 
/*     */       
/* 440 */       if (m < 0) {
/* 441 */         return m;
/*     */       }
/*     */       
/* 444 */       return i - param1Int1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/AlawCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */