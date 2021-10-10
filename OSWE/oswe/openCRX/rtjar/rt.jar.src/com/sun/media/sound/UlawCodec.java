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
/*     */ 
/*     */ public final class UlawCodec
/*     */   extends SunCodec
/*     */ {
/*  46 */   private static final byte[] ULAW_TABH = new byte[256];
/*  47 */   private static final byte[] ULAW_TABL = new byte[256];
/*     */   
/*  49 */   private static final AudioFormat.Encoding[] ulawEncodings = new AudioFormat.Encoding[] { AudioFormat.Encoding.ULAW, AudioFormat.Encoding.PCM_SIGNED };
/*     */ 
/*     */   
/*  52 */   private static final short[] seg_end = new short[] { 255, 511, 1023, 2047, 4095, 8191, 16383, Short.MAX_VALUE };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  59 */     for (byte b = 0; b < 'Ā'; b++) {
/*  60 */       int i = b ^ 0xFFFFFFFF;
/*     */ 
/*     */       
/*  63 */       i &= 0xFF;
/*  64 */       int j = ((i & 0xF) << 3) + 132;
/*  65 */       j <<= (i & 0x70) >> 4;
/*  66 */       j = ((i & 0x80) != 0) ? (132 - j) : (j - 132);
/*     */       
/*  68 */       ULAW_TABL[b] = (byte)(j & 0xFF);
/*  69 */       ULAW_TABH[b] = (byte)(j >> 8 & 0xFF);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UlawCodec() {
/*  78 */     super(ulawEncodings, ulawEncodings);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioFormat.Encoding[] getTargetEncodings(AudioFormat paramAudioFormat) {
/*  84 */     if (AudioFormat.Encoding.PCM_SIGNED.equals(paramAudioFormat.getEncoding())) {
/*  85 */       if (paramAudioFormat.getSampleSizeInBits() == 16) {
/*  86 */         AudioFormat.Encoding[] arrayOfEncoding = new AudioFormat.Encoding[1];
/*  87 */         arrayOfEncoding[0] = AudioFormat.Encoding.ULAW;
/*  88 */         return arrayOfEncoding;
/*     */       } 
/*  90 */       return new AudioFormat.Encoding[0];
/*     */     } 
/*  92 */     if (AudioFormat.Encoding.ULAW.equals(paramAudioFormat.getEncoding())) {
/*  93 */       if (paramAudioFormat.getSampleSizeInBits() == 8) {
/*  94 */         AudioFormat.Encoding[] arrayOfEncoding = new AudioFormat.Encoding[1];
/*  95 */         arrayOfEncoding[0] = AudioFormat.Encoding.PCM_SIGNED;
/*  96 */         return arrayOfEncoding;
/*     */       } 
/*  98 */       return new AudioFormat.Encoding[0];
/*     */     } 
/*     */     
/* 101 */     return new AudioFormat.Encoding[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioFormat[] getTargetFormats(AudioFormat.Encoding paramEncoding, AudioFormat paramAudioFormat) {
/* 109 */     if ((AudioFormat.Encoding.PCM_SIGNED.equals(paramEncoding) && AudioFormat.Encoding.ULAW
/* 110 */       .equals(paramAudioFormat.getEncoding())) || (AudioFormat.Encoding.ULAW
/*     */       
/* 112 */       .equals(paramEncoding) && AudioFormat.Encoding.PCM_SIGNED
/* 113 */       .equals(paramAudioFormat.getEncoding()))) {
/* 114 */       return getOutputFormats(paramAudioFormat);
/*     */     }
/* 116 */     return new AudioFormat[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioInputStream getAudioInputStream(AudioFormat.Encoding paramEncoding, AudioInputStream paramAudioInputStream) {
/* 123 */     AudioFormat audioFormat1 = paramAudioInputStream.getFormat();
/* 124 */     AudioFormat.Encoding encoding = audioFormat1.getEncoding();
/*     */     
/* 126 */     if (encoding.equals(paramEncoding)) {
/* 127 */       return paramAudioInputStream;
/*     */     }
/* 129 */     AudioFormat audioFormat2 = null;
/* 130 */     if (!isConversionSupported(paramEncoding, paramAudioInputStream.getFormat())) {
/* 131 */       throw new IllegalArgumentException("Unsupported conversion: " + paramAudioInputStream.getFormat().toString() + " to " + paramEncoding.toString());
/*     */     }
/* 133 */     if (AudioFormat.Encoding.ULAW.equals(encoding) && AudioFormat.Encoding.PCM_SIGNED
/* 134 */       .equals(paramEncoding)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 141 */       audioFormat2 = new AudioFormat(paramEncoding, audioFormat1.getSampleRate(), 16, audioFormat1.getChannels(), 2 * audioFormat1.getChannels(), audioFormat1.getSampleRate(), audioFormat1.isBigEndian());
/* 142 */     } else if (AudioFormat.Encoding.PCM_SIGNED.equals(encoding) && AudioFormat.Encoding.ULAW
/* 143 */       .equals(paramEncoding)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 149 */       audioFormat2 = new AudioFormat(paramEncoding, audioFormat1.getSampleRate(), 8, audioFormat1.getChannels(), audioFormat1.getChannels(), audioFormat1.getSampleRate(), false);
/*     */     } else {
/*     */       
/* 152 */       throw new IllegalArgumentException("Unsupported conversion: " + paramAudioInputStream.getFormat().toString() + " to " + paramEncoding.toString());
/*     */     } 
/*     */     
/* 155 */     return getAudioInputStream(audioFormat2, paramAudioInputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioInputStream getAudioInputStream(AudioFormat paramAudioFormat, AudioInputStream paramAudioInputStream) {
/* 163 */     return getConvertedStream(paramAudioFormat, paramAudioInputStream);
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
/*     */   private AudioInputStream getConvertedStream(AudioFormat paramAudioFormat, AudioInputStream paramAudioInputStream) {
/* 179 */     AudioInputStream audioInputStream = null;
/*     */     
/* 181 */     AudioFormat audioFormat = paramAudioInputStream.getFormat();
/*     */     
/* 183 */     if (audioFormat.matches(paramAudioFormat)) {
/* 184 */       audioInputStream = paramAudioInputStream;
/*     */     } else {
/* 186 */       audioInputStream = new UlawCodecStream(paramAudioInputStream, paramAudioFormat);
/*     */     } 
/* 188 */     return audioInputStream;
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
/*     */   private AudioFormat[] getOutputFormats(AudioFormat paramAudioFormat) {
/* 201 */     Vector<AudioFormat> vector = new Vector();
/*     */ 
/*     */     
/* 204 */     if (paramAudioFormat.getSampleSizeInBits() == 16 && AudioFormat.Encoding.PCM_SIGNED
/* 205 */       .equals(paramAudioFormat.getEncoding())) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 211 */       AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.ULAW, paramAudioFormat.getSampleRate(), 8, paramAudioFormat.getChannels(), paramAudioFormat.getChannels(), paramAudioFormat.getSampleRate(), false);
/*     */       
/* 213 */       vector.addElement(audioFormat);
/*     */     } 
/*     */     
/* 216 */     if (AudioFormat.Encoding.ULAW.equals(paramAudioFormat.getEncoding())) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 222 */       AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, paramAudioFormat.getSampleRate(), 16, paramAudioFormat.getChannels(), paramAudioFormat.getChannels() * 2, paramAudioFormat.getSampleRate(), false);
/*     */       
/* 224 */       vector.addElement(audioFormat);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 231 */       audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, paramAudioFormat.getSampleRate(), 16, paramAudioFormat.getChannels(), paramAudioFormat.getChannels() * 2, paramAudioFormat.getSampleRate(), true);
/*     */       
/* 233 */       vector.addElement(audioFormat);
/*     */     } 
/*     */     
/* 236 */     AudioFormat[] arrayOfAudioFormat = new AudioFormat[vector.size()];
/* 237 */     for (byte b = 0; b < arrayOfAudioFormat.length; b++) {
/* 238 */       arrayOfAudioFormat[b] = vector.elementAt(b);
/*     */     }
/* 240 */     return arrayOfAudioFormat;
/*     */   }
/*     */ 
/*     */   
/*     */   class UlawCodecStream
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
/*     */     byte[] tabByte2;
/*     */     int highByte;
/*     */     int lowByte;
/*     */     
/*     */     UlawCodecStream(AudioInputStream param1AudioInputStream, AudioFormat param1AudioFormat) {
/* 263 */       super(param1AudioInputStream, param1AudioFormat, -1L); boolean bool; this.tempBuffer = null; this.encode = false; this.tabByte1 = null; this.tabByte2 = null; this.highByte = 0;
/*     */       this.lowByte = 1;
/* 265 */       AudioFormat audioFormat = param1AudioInputStream.getFormat();
/*     */ 
/*     */       
/* 268 */       if (!UlawCodec.this.isConversionSupported(param1AudioFormat, audioFormat)) {
/* 269 */         throw new IllegalArgumentException("Unsupported conversion: " + audioFormat.toString() + " to " + param1AudioFormat.toString());
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 276 */       if (AudioFormat.Encoding.ULAW.equals(audioFormat.getEncoding())) {
/* 277 */         this.encode = false;
/* 278 */         this.encodeFormat = audioFormat;
/* 279 */         this.decodeFormat = param1AudioFormat;
/* 280 */         bool = param1AudioFormat.isBigEndian();
/*     */       } else {
/* 282 */         this.encode = true;
/* 283 */         this.encodeFormat = param1AudioFormat;
/* 284 */         this.decodeFormat = audioFormat;
/* 285 */         bool = audioFormat.isBigEndian();
/* 286 */         this.tempBuffer = new byte[64];
/*     */       } 
/*     */ 
/*     */       
/* 290 */       if (bool) {
/* 291 */         this.tabByte1 = UlawCodec.ULAW_TABH;
/* 292 */         this.tabByte2 = UlawCodec.ULAW_TABL;
/* 293 */         this.highByte = 0;
/* 294 */         this.lowByte = 1;
/*     */       } else {
/* 296 */         this.tabByte1 = UlawCodec.ULAW_TABL;
/* 297 */         this.tabByte2 = UlawCodec.ULAW_TABH;
/* 298 */         this.highByte = 1;
/* 299 */         this.lowByte = 0;
/*     */       } 
/*     */ 
/*     */       
/* 303 */       if (param1AudioInputStream instanceof AudioInputStream) {
/* 304 */         this.frameLength = param1AudioInputStream.getFrameLength();
/*     */       }
/*     */       
/* 307 */       this.framePos = 0L;
/* 308 */       this.frameSize = audioFormat.getFrameSize();
/* 309 */       if (this.frameSize == -1) {
/* 310 */         this.frameSize = 1;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private short search(short param1Short1, short[] param1ArrayOfshort, short param1Short2) {
/*     */       short s;
/* 320 */       for (s = 0; s < param1Short2; s = (short)(s + 1)) {
/* 321 */         if (param1Short1 <= param1ArrayOfshort[s]) return s; 
/*     */       } 
/* 323 */       return param1Short2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int read() throws IOException {
/* 331 */       byte[] arrayOfByte = new byte[1];
/* 332 */       if (read(arrayOfByte, 0, arrayOfByte.length) == 1) {
/* 333 */         return arrayOfByte[1] & 0xFF;
/*     */       }
/* 335 */       return -1;
/*     */     }
/*     */     
/*     */     public int read(byte[] param1ArrayOfbyte) throws IOException {
/* 339 */       return read(param1ArrayOfbyte, 0, param1ArrayOfbyte.length);
/*     */     }
/*     */ 
/*     */     
/*     */     public int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/* 344 */       if (param1Int2 % this.frameSize != 0) {
/* 345 */         param1Int2 -= param1Int2 % this.frameSize;
/*     */       }
/* 347 */       if (this.encode) {
/* 348 */         char c = '';
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 356 */         int n = 0;
/* 357 */         int i1 = param1Int1;
/* 358 */         int i2 = param1Int2 * 2;
/* 359 */         boolean bool = (i2 > 64) ? true : i2;
/*     */         
/* 361 */         while ((n = super.read(this.tempBuffer, 0, bool)) > 0) {
/* 362 */           for (byte b = 0; b < n; b += 2) {
/*     */             char c1; byte b1;
/* 364 */             short s2 = (short)(this.tempBuffer[b + this.highByte] << 8 & 0xFF00);
/* 365 */             s2 = (short)(s2 | (short)((short)this.tempBuffer[b + this.lowByte] & 0xFF));
/*     */ 
/*     */             
/* 368 */             if (s2 < 0) {
/* 369 */               s2 = (short)(c - s2);
/* 370 */               c1 = '';
/*     */             } else {
/* 372 */               s2 = (short)(s2 + c);
/* 373 */               c1 = 'ÿ';
/*     */             } 
/*     */             
/* 376 */             short s1 = search(s2, UlawCodec.seg_end, (short)8);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 381 */             if (s1 >= 8) {
/* 382 */               b1 = (byte)(0x7F ^ c1);
/*     */             } else {
/* 384 */               b1 = (byte)(s1 << 4 | s2 >> s1 + 3 & 0xF);
/* 385 */               b1 = (byte)(b1 ^ c1);
/*     */             } 
/*     */             
/* 388 */             param1ArrayOfbyte[i1] = b1;
/* 389 */             i1++;
/*     */           } 
/*     */           
/* 392 */           i2 -= n;
/* 393 */           bool = (i2 > 64) ? true : i2;
/*     */         } 
/* 395 */         if (i1 == param1Int1 && n < 0) {
/* 396 */           return n;
/*     */         }
/* 398 */         return i1 - param1Int1;
/*     */       } 
/*     */       
/* 401 */       int j = param1Int2 / 2;
/* 402 */       int k = param1Int1 + param1Int2 / 2;
/* 403 */       int m = super.read(param1ArrayOfbyte, k, j);
/*     */       
/* 405 */       if (m < 0)
/* 406 */         return m; 
/*     */       int i;
/* 408 */       for (i = param1Int1; i < param1Int1 + m * 2; i += 2) {
/* 409 */         param1ArrayOfbyte[i] = this.tabByte1[param1ArrayOfbyte[k] & 0xFF];
/* 410 */         param1ArrayOfbyte[i + 1] = this.tabByte2[param1ArrayOfbyte[k] & 0xFF];
/* 411 */         k++;
/*     */       } 
/* 413 */       return i - param1Int1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/UlawCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */