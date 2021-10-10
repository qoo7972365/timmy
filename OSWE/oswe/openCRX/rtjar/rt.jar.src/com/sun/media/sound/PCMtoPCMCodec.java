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
/*     */ public final class PCMtoPCMCodec
/*     */   extends SunCodec
/*     */ {
/*  44 */   private static final AudioFormat.Encoding[] inputEncodings = new AudioFormat.Encoding[] { AudioFormat.Encoding.PCM_SIGNED, AudioFormat.Encoding.PCM_UNSIGNED };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  49 */   private static final AudioFormat.Encoding[] outputEncodings = new AudioFormat.Encoding[] { AudioFormat.Encoding.PCM_SIGNED, AudioFormat.Encoding.PCM_UNSIGNED };
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int tempBufferSize = 64;
/*     */ 
/*     */ 
/*     */   
/*  57 */   private byte[] tempBuffer = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PCMtoPCMCodec() {
/*  64 */     super(inputEncodings, outputEncodings);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioFormat.Encoding[] getTargetEncodings(AudioFormat paramAudioFormat) {
/*  74 */     if (paramAudioFormat.getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED) || paramAudioFormat
/*  75 */       .getEncoding().equals(AudioFormat.Encoding.PCM_UNSIGNED)) {
/*     */       
/*  77 */       AudioFormat.Encoding[] arrayOfEncoding = new AudioFormat.Encoding[2];
/*  78 */       arrayOfEncoding[0] = AudioFormat.Encoding.PCM_SIGNED;
/*  79 */       arrayOfEncoding[1] = AudioFormat.Encoding.PCM_UNSIGNED;
/*  80 */       return arrayOfEncoding;
/*     */     } 
/*  82 */     return new AudioFormat.Encoding[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioFormat[] getTargetFormats(AudioFormat.Encoding paramEncoding, AudioFormat paramAudioFormat) {
/*  93 */     AudioFormat[] arrayOfAudioFormat1 = getOutputFormats(paramAudioFormat);
/*  94 */     Vector<AudioFormat> vector = new Vector();
/*  95 */     for (byte b1 = 0; b1 < arrayOfAudioFormat1.length; b1++) {
/*  96 */       if (arrayOfAudioFormat1[b1].getEncoding().equals(paramEncoding)) {
/*  97 */         vector.addElement(arrayOfAudioFormat1[b1]);
/*     */       }
/*     */     } 
/*     */     
/* 101 */     AudioFormat[] arrayOfAudioFormat2 = new AudioFormat[vector.size()];
/*     */     
/* 103 */     for (byte b2 = 0; b2 < arrayOfAudioFormat2.length; b2++) {
/* 104 */       arrayOfAudioFormat2[b2] = vector.elementAt(b2);
/*     */     }
/*     */     
/* 107 */     return arrayOfAudioFormat2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioInputStream getAudioInputStream(AudioFormat.Encoding paramEncoding, AudioInputStream paramAudioInputStream) {
/* 115 */     if (isConversionSupported(paramEncoding, paramAudioInputStream.getFormat())) {
/*     */       
/* 117 */       AudioFormat audioFormat1 = paramAudioInputStream.getFormat();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 124 */       AudioFormat audioFormat2 = new AudioFormat(paramEncoding, audioFormat1.getSampleRate(), audioFormat1.getSampleSizeInBits(), audioFormat1.getChannels(), audioFormat1.getFrameSize(), audioFormat1.getFrameRate(), audioFormat1.isBigEndian());
/*     */       
/* 126 */       return getAudioInputStream(audioFormat2, paramAudioInputStream);
/*     */     } 
/*     */     
/* 129 */     throw new IllegalArgumentException("Unsupported conversion: " + paramAudioInputStream.getFormat().toString() + " to " + paramEncoding.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioInputStream getAudioInputStream(AudioFormat paramAudioFormat, AudioInputStream paramAudioInputStream) {
/* 138 */     return getConvertedStream(paramAudioFormat, paramAudioInputStream);
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
/*     */   
/*     */   private AudioInputStream getConvertedStream(AudioFormat paramAudioFormat, AudioInputStream paramAudioInputStream) {
/* 157 */     AudioInputStream audioInputStream = null;
/*     */     
/* 159 */     AudioFormat audioFormat = paramAudioInputStream.getFormat();
/*     */     
/* 161 */     if (audioFormat.matches(paramAudioFormat)) {
/*     */       
/* 163 */       audioInputStream = paramAudioInputStream;
/*     */     } else {
/*     */       
/* 166 */       audioInputStream = new PCMtoPCMCodecStream(paramAudioInputStream, paramAudioFormat);
/* 167 */       this.tempBuffer = new byte[64];
/*     */     } 
/* 169 */     return audioInputStream;
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
/*     */     AudioFormat[] arrayOfAudioFormat;
/* 184 */     Vector<AudioFormat> vector = new Vector();
/*     */ 
/*     */     
/* 187 */     int i = paramAudioFormat.getSampleSizeInBits();
/* 188 */     boolean bool = paramAudioFormat.isBigEndian();
/*     */ 
/*     */     
/* 191 */     if (i == 8) {
/* 192 */       if (AudioFormat.Encoding.PCM_SIGNED.equals(paramAudioFormat.getEncoding())) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 199 */         AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_UNSIGNED, paramAudioFormat.getSampleRate(), paramAudioFormat.getSampleSizeInBits(), paramAudioFormat.getChannels(), paramAudioFormat.getFrameSize(), paramAudioFormat.getFrameRate(), false);
/*     */         
/* 201 */         vector.addElement(audioFormat);
/*     */       } 
/*     */       
/* 204 */       if (AudioFormat.Encoding.PCM_UNSIGNED.equals(paramAudioFormat.getEncoding()))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 211 */         AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, paramAudioFormat.getSampleRate(), paramAudioFormat.getSampleSizeInBits(), paramAudioFormat.getChannels(), paramAudioFormat.getFrameSize(), paramAudioFormat.getFrameRate(), false);
/*     */         
/* 213 */         vector.addElement(audioFormat);
/*     */       }
/*     */     
/* 216 */     } else if (i == 16) {
/*     */       
/* 218 */       if (AudioFormat.Encoding.PCM_SIGNED.equals(paramAudioFormat.getEncoding()) && bool) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 225 */         AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_UNSIGNED, paramAudioFormat.getSampleRate(), paramAudioFormat.getSampleSizeInBits(), paramAudioFormat.getChannels(), paramAudioFormat.getFrameSize(), paramAudioFormat.getFrameRate(), true);
/*     */         
/* 227 */         vector.addElement(audioFormat);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 233 */         audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, paramAudioFormat.getSampleRate(), paramAudioFormat.getSampleSizeInBits(), paramAudioFormat.getChannels(), paramAudioFormat.getFrameSize(), paramAudioFormat.getFrameRate(), false);
/*     */         
/* 235 */         vector.addElement(audioFormat);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 241 */         audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_UNSIGNED, paramAudioFormat.getSampleRate(), paramAudioFormat.getSampleSizeInBits(), paramAudioFormat.getChannels(), paramAudioFormat.getFrameSize(), paramAudioFormat.getFrameRate(), false);
/*     */         
/* 243 */         vector.addElement(audioFormat);
/*     */       } 
/*     */       
/* 246 */       if (AudioFormat.Encoding.PCM_UNSIGNED.equals(paramAudioFormat.getEncoding()) && bool) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 253 */         AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, paramAudioFormat.getSampleRate(), paramAudioFormat.getSampleSizeInBits(), paramAudioFormat.getChannels(), paramAudioFormat.getFrameSize(), paramAudioFormat.getFrameRate(), true);
/*     */         
/* 255 */         vector.addElement(audioFormat);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 261 */         audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_UNSIGNED, paramAudioFormat.getSampleRate(), paramAudioFormat.getSampleSizeInBits(), paramAudioFormat.getChannels(), paramAudioFormat.getFrameSize(), paramAudioFormat.getFrameRate(), false);
/*     */         
/* 263 */         vector.addElement(audioFormat);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 269 */         audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, paramAudioFormat.getSampleRate(), paramAudioFormat.getSampleSizeInBits(), paramAudioFormat.getChannels(), paramAudioFormat.getFrameSize(), paramAudioFormat.getFrameRate(), false);
/*     */         
/* 271 */         vector.addElement(audioFormat);
/*     */       } 
/*     */       
/* 274 */       if (AudioFormat.Encoding.PCM_SIGNED.equals(paramAudioFormat.getEncoding()) && !bool) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 281 */         AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_UNSIGNED, paramAudioFormat.getSampleRate(), paramAudioFormat.getSampleSizeInBits(), paramAudioFormat.getChannels(), paramAudioFormat.getFrameSize(), paramAudioFormat.getFrameRate(), false);
/*     */         
/* 283 */         vector.addElement(audioFormat);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 289 */         audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, paramAudioFormat.getSampleRate(), paramAudioFormat.getSampleSizeInBits(), paramAudioFormat.getChannels(), paramAudioFormat.getFrameSize(), paramAudioFormat.getFrameRate(), true);
/*     */         
/* 291 */         vector.addElement(audioFormat);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 297 */         audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_UNSIGNED, paramAudioFormat.getSampleRate(), paramAudioFormat.getSampleSizeInBits(), paramAudioFormat.getChannels(), paramAudioFormat.getFrameSize(), paramAudioFormat.getFrameRate(), true);
/*     */         
/* 299 */         vector.addElement(audioFormat);
/*     */       } 
/*     */       
/* 302 */       if (AudioFormat.Encoding.PCM_UNSIGNED.equals(paramAudioFormat.getEncoding()) && !bool) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 309 */         AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, paramAudioFormat.getSampleRate(), paramAudioFormat.getSampleSizeInBits(), paramAudioFormat.getChannels(), paramAudioFormat.getFrameSize(), paramAudioFormat.getFrameRate(), false);
/*     */         
/* 311 */         vector.addElement(audioFormat);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 317 */         audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_UNSIGNED, paramAudioFormat.getSampleRate(), paramAudioFormat.getSampleSizeInBits(), paramAudioFormat.getChannels(), paramAudioFormat.getFrameSize(), paramAudioFormat.getFrameRate(), true);
/*     */         
/* 319 */         vector.addElement(audioFormat);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 325 */         audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, paramAudioFormat.getSampleRate(), paramAudioFormat.getSampleSizeInBits(), paramAudioFormat.getChannels(), paramAudioFormat.getFrameSize(), paramAudioFormat.getFrameRate(), true);
/*     */         
/* 327 */         vector.addElement(audioFormat);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 332 */     synchronized (vector) {
/*     */       
/* 334 */       arrayOfAudioFormat = new AudioFormat[vector.size()];
/*     */       
/* 336 */       for (byte b = 0; b < arrayOfAudioFormat.length; b++)
/*     */       {
/* 338 */         arrayOfAudioFormat[b] = vector.elementAt(b);
/*     */       }
/*     */     } 
/*     */     
/* 342 */     return arrayOfAudioFormat;
/*     */   }
/*     */   
/*     */   class PCMtoPCMCodecStream
/*     */     extends AudioInputStream
/*     */   {
/* 348 */     private final int PCM_SWITCH_SIGNED_8BIT = 1;
/* 349 */     private final int PCM_SWITCH_ENDIAN = 2;
/* 350 */     private final int PCM_SWITCH_SIGNED_LE = 3;
/* 351 */     private final int PCM_SWITCH_SIGNED_BE = 4;
/* 352 */     private final int PCM_UNSIGNED_LE2SIGNED_BE = 5;
/* 353 */     private final int PCM_SIGNED_LE2UNSIGNED_BE = 6;
/* 354 */     private final int PCM_UNSIGNED_BE2SIGNED_LE = 7;
/* 355 */     private final int PCM_SIGNED_BE2UNSIGNED_LE = 8;
/*     */     
/*     */     private final int sampleSizeInBytes;
/* 358 */     private int conversionType = 0;
/*     */ 
/*     */ 
/*     */     
/*     */     PCMtoPCMCodecStream(AudioInputStream param1AudioInputStream, AudioFormat param1AudioFormat) {
/* 363 */       super(param1AudioInputStream, param1AudioFormat, -1L);
/*     */       
/* 365 */       int i = 0;
/* 366 */       AudioFormat.Encoding encoding1 = null;
/* 367 */       AudioFormat.Encoding encoding2 = null;
/*     */ 
/*     */ 
/*     */       
/* 371 */       AudioFormat audioFormat = param1AudioInputStream.getFormat();
/*     */ 
/*     */       
/* 374 */       if (!PCMtoPCMCodec.this.isConversionSupported(audioFormat, param1AudioFormat))
/*     */       {
/* 376 */         throw new IllegalArgumentException("Unsupported conversion: " + audioFormat.toString() + " to " + param1AudioFormat.toString());
/*     */       }
/*     */       
/* 379 */       encoding1 = audioFormat.getEncoding();
/* 380 */       encoding2 = param1AudioFormat.getEncoding();
/* 381 */       boolean bool1 = audioFormat.isBigEndian();
/* 382 */       boolean bool2 = param1AudioFormat.isBigEndian();
/* 383 */       i = audioFormat.getSampleSizeInBits();
/* 384 */       this.sampleSizeInBytes = i / 8;
/*     */ 
/*     */ 
/*     */       
/* 388 */       if (i == 8) {
/* 389 */         if (AudioFormat.Encoding.PCM_UNSIGNED.equals(encoding1) && AudioFormat.Encoding.PCM_SIGNED
/* 390 */           .equals(encoding2)) {
/* 391 */           this.conversionType = 1;
/*     */         
/*     */         }
/* 394 */         else if (AudioFormat.Encoding.PCM_SIGNED.equals(encoding1) && AudioFormat.Encoding.PCM_UNSIGNED
/* 395 */           .equals(encoding2)) {
/* 396 */           this.conversionType = 1;
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 401 */       else if (encoding1.equals(encoding2) && bool1 != bool2) {
/*     */         
/* 403 */         this.conversionType = 2;
/*     */ 
/*     */       
/*     */       }
/* 407 */       else if (AudioFormat.Encoding.PCM_UNSIGNED.equals(encoding1) && !bool1 && AudioFormat.Encoding.PCM_SIGNED
/* 408 */         .equals(encoding2) && bool2) {
/*     */         
/* 410 */         this.conversionType = 5;
/*     */       
/*     */       }
/* 413 */       else if (AudioFormat.Encoding.PCM_SIGNED.equals(encoding1) && !bool1 && AudioFormat.Encoding.PCM_UNSIGNED
/* 414 */         .equals(encoding2) && bool2) {
/*     */         
/* 416 */         this.conversionType = 6;
/*     */       
/*     */       }
/* 419 */       else if (AudioFormat.Encoding.PCM_UNSIGNED.equals(encoding1) && bool1 && AudioFormat.Encoding.PCM_SIGNED
/* 420 */         .equals(encoding2) && !bool2) {
/*     */         
/* 422 */         this.conversionType = 7;
/*     */       
/*     */       }
/* 425 */       else if (AudioFormat.Encoding.PCM_SIGNED.equals(encoding1) && bool1 && AudioFormat.Encoding.PCM_UNSIGNED
/* 426 */         .equals(encoding2) && !bool2) {
/*     */         
/* 428 */         this.conversionType = 8;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 436 */       this.frameSize = audioFormat.getFrameSize();
/* 437 */       if (this.frameSize == -1) {
/* 438 */         this.frameSize = 1;
/*     */       }
/* 440 */       if (param1AudioInputStream instanceof AudioInputStream) {
/* 441 */         this.frameLength = param1AudioInputStream.getFrameLength();
/*     */       } else {
/* 443 */         this.frameLength = -1L;
/*     */       } 
/*     */ 
/*     */       
/* 447 */       this.framePos = 0L;
/*     */     }
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
/*     */     public int read() throws IOException {
/* 463 */       if (this.frameSize == 1) {
/* 464 */         if (this.conversionType == 1) {
/* 465 */           int i = super.read();
/*     */           
/* 467 */           if (i < 0) return i;
/*     */           
/* 469 */           byte b = (byte)(i & 0xF);
/* 470 */           b = (b >= 0) ? (byte)(0x80 | b) : (byte)(Byte.MAX_VALUE & b);
/* 471 */           i = b & 0xF;
/*     */           
/* 473 */           return i;
/*     */         } 
/*     */ 
/*     */         
/* 477 */         throw new IOException("cannot read a single byte if frame size > 1");
/*     */       } 
/*     */       
/* 480 */       throw new IOException("cannot read a single byte if frame size > 1");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int read(byte[] param1ArrayOfbyte) throws IOException {
/* 487 */       return read(param1ArrayOfbyte, 0, param1ArrayOfbyte.length);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/* 496 */       if (param1Int2 % this.frameSize != 0) {
/* 497 */         param1Int2 -= param1Int2 % this.frameSize;
/*     */       }
/*     */       
/* 500 */       if (this.frameLength != -1L && (param1Int2 / this.frameSize) > this.frameLength - this.framePos) {
/* 501 */         param1Int2 = (int)(this.frameLength - this.framePos) * this.frameSize;
/*     */       }
/*     */       
/* 504 */       int i = super.read(param1ArrayOfbyte, param1Int1, param1Int2);
/*     */ 
/*     */       
/* 507 */       if (i < 0) {
/* 508 */         return i;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 513 */       switch (this.conversionType) {
/*     */         
/*     */         case 1:
/* 516 */           switchSigned8bit(param1ArrayOfbyte, param1Int1, param1Int2, i);
/*     */           break;
/*     */         
/*     */         case 2:
/* 520 */           switchEndian(param1ArrayOfbyte, param1Int1, param1Int2, i);
/*     */           break;
/*     */         
/*     */         case 3:
/* 524 */           switchSignedLE(param1ArrayOfbyte, param1Int1, param1Int2, i);
/*     */           break;
/*     */         
/*     */         case 4:
/* 528 */           switchSignedBE(param1ArrayOfbyte, param1Int1, param1Int2, i);
/*     */           break;
/*     */         
/*     */         case 5:
/*     */         case 6:
/* 533 */           switchSignedLE(param1ArrayOfbyte, param1Int1, param1Int2, i);
/* 534 */           switchEndian(param1ArrayOfbyte, param1Int1, param1Int2, i);
/*     */           break;
/*     */         
/*     */         case 7:
/*     */         case 8:
/* 539 */           switchSignedBE(param1ArrayOfbyte, param1Int1, param1Int2, i);
/* 540 */           switchEndian(param1ArrayOfbyte, param1Int1, param1Int2, i);
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 548 */       return i;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void switchSigned8bit(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2, int param1Int3) {
/* 554 */       for (int i = param1Int1; i < param1Int1 + param1Int3; i++) {
/* 555 */         param1ArrayOfbyte[i] = (param1ArrayOfbyte[i] >= 0) ? (byte)(0x80 | param1ArrayOfbyte[i]) : (byte)(Byte.MAX_VALUE & param1ArrayOfbyte[i]);
/*     */       }
/*     */     }
/*     */     
/*     */     private void switchSignedBE(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2, int param1Int3) {
/*     */       int i;
/* 561 */       for (i = param1Int1; i < param1Int1 + param1Int3; i += this.sampleSizeInBytes) {
/* 562 */         param1ArrayOfbyte[i] = (param1ArrayOfbyte[i] >= 0) ? (byte)(0x80 | param1ArrayOfbyte[i]) : (byte)(Byte.MAX_VALUE & param1ArrayOfbyte[i]);
/*     */       }
/*     */     }
/*     */     
/*     */     private void switchSignedLE(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2, int param1Int3) {
/*     */       int i;
/* 568 */       for (i = param1Int1 + this.sampleSizeInBytes - 1; i < param1Int1 + param1Int3; i += this.sampleSizeInBytes) {
/* 569 */         param1ArrayOfbyte[i] = (param1ArrayOfbyte[i] >= 0) ? (byte)(0x80 | param1ArrayOfbyte[i]) : (byte)(Byte.MAX_VALUE & param1ArrayOfbyte[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private void switchEndian(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2, int param1Int3) {
/* 575 */       if (this.sampleSizeInBytes == 2) {
/* 576 */         int i; for (i = param1Int1; i < param1Int1 + param1Int3; i += this.sampleSizeInBytes) {
/*     */           
/* 578 */           byte b = param1ArrayOfbyte[i];
/* 579 */           param1ArrayOfbyte[i] = param1ArrayOfbyte[i + 1];
/* 580 */           param1ArrayOfbyte[i + 1] = b;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/PCMtoPCMCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */