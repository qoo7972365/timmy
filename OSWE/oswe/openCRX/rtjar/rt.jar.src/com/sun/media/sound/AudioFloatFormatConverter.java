/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import javax.sound.sampled.AudioFormat;
/*     */ import javax.sound.sampled.AudioInputStream;
/*     */ import javax.sound.sampled.spi.FormatConversionProvider;
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
/*     */ public final class AudioFloatFormatConverter
/*     */   extends FormatConversionProvider
/*     */ {
/*     */   private static class AudioFloatFormatConverterInputStream
/*     */     extends InputStream
/*     */   {
/*     */     private final AudioFloatConverter converter;
/*     */     private final AudioFloatInputStream stream;
/*     */     private float[] readfloatbuffer;
/*     */     private final int fsize;
/*     */     
/*     */     AudioFloatFormatConverterInputStream(AudioFormat param1AudioFormat, AudioFloatInputStream param1AudioFloatInputStream) {
/*  59 */       this.stream = param1AudioFloatInputStream;
/*  60 */       this.converter = AudioFloatConverter.getConverter(param1AudioFormat);
/*  61 */       this.fsize = (param1AudioFormat.getSampleSizeInBits() + 7) / 8;
/*     */     }
/*     */     
/*     */     public int read() throws IOException {
/*  65 */       byte[] arrayOfByte = new byte[1];
/*  66 */       int i = read(arrayOfByte);
/*  67 */       if (i < 0)
/*  68 */         return i; 
/*  69 */       return arrayOfByte[0] & 0xFF;
/*     */     }
/*     */ 
/*     */     
/*     */     public int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/*  74 */       int i = param1Int2 / this.fsize;
/*  75 */       if (this.readfloatbuffer == null || this.readfloatbuffer.length < i)
/*  76 */         this.readfloatbuffer = new float[i]; 
/*  77 */       int j = this.stream.read(this.readfloatbuffer, 0, i);
/*  78 */       if (j < 0)
/*  79 */         return j; 
/*  80 */       this.converter.toByteArray(this.readfloatbuffer, 0, j, param1ArrayOfbyte, param1Int1);
/*  81 */       return j * this.fsize;
/*     */     }
/*     */     
/*     */     public int available() throws IOException {
/*  85 */       int i = this.stream.available();
/*  86 */       if (i < 0)
/*  87 */         return i; 
/*  88 */       return i * this.fsize;
/*     */     }
/*     */     
/*     */     public void close() throws IOException {
/*  92 */       this.stream.close();
/*     */     }
/*     */     
/*     */     public synchronized void mark(int param1Int) {
/*  96 */       this.stream.mark(param1Int * this.fsize);
/*     */     }
/*     */     
/*     */     public boolean markSupported() {
/* 100 */       return this.stream.markSupported();
/*     */     }
/*     */     
/*     */     public synchronized void reset() throws IOException {
/* 104 */       this.stream.reset();
/*     */     }
/*     */     
/*     */     public long skip(long param1Long) throws IOException {
/* 108 */       long l = this.stream.skip(param1Long / this.fsize);
/* 109 */       if (l < 0L)
/* 110 */         return l; 
/* 111 */       return l * this.fsize;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class AudioFloatInputStreamChannelMixer
/*     */     extends AudioFloatInputStream
/*     */   {
/*     */     private final int targetChannels;
/*     */     
/*     */     private final int sourceChannels;
/*     */     
/*     */     private final AudioFloatInputStream ais;
/*     */     
/*     */     private final AudioFormat targetFormat;
/*     */     
/*     */     private float[] conversion_buffer;
/*     */ 
/*     */     
/*     */     AudioFloatInputStreamChannelMixer(AudioFloatInputStream param1AudioFloatInputStream, int param1Int) {
/* 131 */       this.sourceChannels = param1AudioFloatInputStream.getFormat().getChannels();
/* 132 */       this.targetChannels = param1Int;
/* 133 */       this.ais = param1AudioFloatInputStream;
/* 134 */       AudioFormat audioFormat = param1AudioFloatInputStream.getFormat();
/* 135 */       this
/*     */ 
/*     */ 
/*     */         
/* 139 */         .targetFormat = new AudioFormat(audioFormat.getEncoding(), audioFormat.getSampleRate(), audioFormat.getSampleSizeInBits(), param1Int, audioFormat.getFrameSize() / this.sourceChannels * param1Int, audioFormat.getFrameRate(), audioFormat.isBigEndian());
/*     */     }
/*     */     
/*     */     public int available() throws IOException {
/* 143 */       return this.ais.available() / this.sourceChannels * this.targetChannels;
/*     */     }
/*     */     
/*     */     public void close() throws IOException {
/* 147 */       this.ais.close();
/*     */     }
/*     */     
/*     */     public AudioFormat getFormat() {
/* 151 */       return this.targetFormat;
/*     */     }
/*     */     
/*     */     public long getFrameLength() {
/* 155 */       return this.ais.getFrameLength();
/*     */     }
/*     */     
/*     */     public void mark(int param1Int) {
/* 159 */       this.ais.mark(param1Int / this.targetChannels * this.sourceChannels);
/*     */     }
/*     */     
/*     */     public boolean markSupported() {
/* 163 */       return this.ais.markSupported();
/*     */     }
/*     */     
/*     */     public int read(float[] param1ArrayOffloat, int param1Int1, int param1Int2) throws IOException {
/* 167 */       int i = param1Int2 / this.targetChannels * this.sourceChannels;
/* 168 */       if (this.conversion_buffer == null || this.conversion_buffer.length < i)
/* 169 */         this.conversion_buffer = new float[i]; 
/* 170 */       int j = this.ais.read(this.conversion_buffer, 0, i);
/* 171 */       if (j < 0)
/* 172 */         return j; 
/* 173 */       if (this.sourceChannels == 1) {
/* 174 */         int k = this.targetChannels;
/* 175 */         for (byte b = 0; b < this.targetChannels; b++) {
/* 176 */           int m; for (byte b1 = 0; b1 < i; b1++, m += k) {
/* 177 */             param1ArrayOffloat[m] = this.conversion_buffer[b1];
/*     */           }
/*     */         } 
/* 180 */       } else if (this.targetChannels == 1) {
/* 181 */         int k = this.sourceChannels; int m, n;
/* 182 */         for (m = 0, n = param1Int1; m < i; m += k, n++) {
/* 183 */           param1ArrayOffloat[n] = this.conversion_buffer[m];
/*     */         }
/* 185 */         for (m = 1; m < this.sourceChannels; m++) {
/* 186 */           int i2; for (n = m, i2 = param1Int1; n < i; n += k, i2++) {
/* 187 */             param1ArrayOffloat[i2] = param1ArrayOffloat[i2] + this.conversion_buffer[n];
/*     */           }
/*     */         } 
/* 190 */         float f = 1.0F / this.sourceChannels; int i1;
/* 191 */         for (n = 0, i1 = param1Int1; n < i; n += k, i1++) {
/* 192 */           param1ArrayOffloat[i1] = param1ArrayOffloat[i1] * f;
/*     */         }
/*     */       } else {
/* 195 */         int k = Math.min(this.sourceChannels, this.targetChannels);
/* 196 */         int m = param1Int1 + param1Int2;
/* 197 */         int n = this.targetChannels;
/* 198 */         int i1 = this.sourceChannels; int i2;
/* 199 */         for (i2 = 0; i2 < k; i2++) {
/* 200 */           int i4; for (int i3 = param1Int1 + i2; i3 < m; i3 += n, i4 += i1) {
/* 201 */             param1ArrayOffloat[i3] = this.conversion_buffer[i4];
/*     */           }
/*     */         } 
/* 204 */         for (i2 = k; i2 < this.targetChannels; i2++) {
/* 205 */           int i3; for (i3 = param1Int1 + i2; i3 < m; i3 += n) {
/* 206 */             param1ArrayOffloat[i3] = 0.0F;
/*     */           }
/*     */         } 
/*     */       } 
/* 210 */       return j / this.sourceChannels * this.targetChannels;
/*     */     }
/*     */     
/*     */     public void reset() throws IOException {
/* 214 */       this.ais.reset();
/*     */     }
/*     */     
/*     */     public long skip(long param1Long) throws IOException {
/* 218 */       long l = this.ais.skip(param1Long / this.targetChannels * this.sourceChannels);
/* 219 */       if (l < 0L)
/* 220 */         return l; 
/* 221 */       return l / this.sourceChannels * this.targetChannels;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class AudioFloatInputStreamResampler
/*     */     extends AudioFloatInputStream
/*     */   {
/*     */     private final AudioFloatInputStream ais;
/*     */     
/*     */     private final AudioFormat targetFormat;
/*     */     
/*     */     private float[] skipbuffer;
/*     */     
/*     */     private SoftAbstractResampler resampler;
/*     */     
/* 237 */     private final float[] pitch = new float[1];
/*     */     
/*     */     private final float[] ibuffer2;
/*     */     
/*     */     private final float[][] ibuffer;
/*     */     
/* 243 */     private float ibuffer_index = 0.0F;
/*     */     
/* 245 */     private int ibuffer_len = 0;
/*     */     
/*     */     private final int nrofchannels;
/*     */     
/*     */     private float[][] cbuffer;
/*     */     
/* 251 */     private final int buffer_len = 512;
/*     */     
/*     */     private final int pad;
/*     */     
/*     */     private final int pad2;
/*     */     
/* 257 */     private final float[] ix = new float[1];
/*     */     
/* 259 */     private final int[] ox = new int[1];
/*     */     
/* 261 */     private float[][] mark_ibuffer = (float[][])null;
/*     */     
/* 263 */     private float mark_ibuffer_index = 0.0F;
/*     */     
/* 265 */     private int mark_ibuffer_len = 0;
/*     */ 
/*     */     
/*     */     AudioFloatInputStreamResampler(AudioFloatInputStream param1AudioFloatInputStream, AudioFormat param1AudioFormat) {
/* 269 */       this.ais = param1AudioFloatInputStream;
/* 270 */       AudioFormat audioFormat = param1AudioFloatInputStream.getFormat();
/* 271 */       this
/*     */ 
/*     */         
/* 274 */         .targetFormat = new AudioFormat(audioFormat.getEncoding(), param1AudioFormat.getSampleRate(), audioFormat.getSampleSizeInBits(), audioFormat.getChannels(), audioFormat.getFrameSize(), param1AudioFormat.getSampleRate(), audioFormat.isBigEndian());
/* 275 */       this.nrofchannels = this.targetFormat.getChannels();
/* 276 */       Object object = param1AudioFormat.getProperty("interpolation");
/* 277 */       if (object != null && object instanceof String) {
/* 278 */         String str = (String)object;
/* 279 */         if (str.equalsIgnoreCase("point"))
/* 280 */           this.resampler = new SoftPointResampler(); 
/* 281 */         if (str.equalsIgnoreCase("linear"))
/* 282 */           this.resampler = new SoftLinearResampler2(); 
/* 283 */         if (str.equalsIgnoreCase("linear1"))
/* 284 */           this.resampler = new SoftLinearResampler(); 
/* 285 */         if (str.equalsIgnoreCase("linear2"))
/* 286 */           this.resampler = new SoftLinearResampler2(); 
/* 287 */         if (str.equalsIgnoreCase("cubic"))
/* 288 */           this.resampler = new SoftCubicResampler(); 
/* 289 */         if (str.equalsIgnoreCase("lanczos"))
/* 290 */           this.resampler = new SoftLanczosResampler(); 
/* 291 */         if (str.equalsIgnoreCase("sinc"))
/* 292 */           this.resampler = new SoftSincResampler(); 
/*     */       } 
/* 294 */       if (this.resampler == null) {
/* 295 */         this.resampler = new SoftLinearResampler2();
/*     */       }
/* 297 */       this.pitch[0] = audioFormat.getSampleRate() / param1AudioFormat.getSampleRate();
/* 298 */       this.pad = this.resampler.getPadding();
/* 299 */       this.pad2 = this.pad * 2;
/* 300 */       this.ibuffer = new float[this.nrofchannels][512 + this.pad2];
/* 301 */       this.ibuffer2 = new float[this.nrofchannels * 512];
/* 302 */       this.ibuffer_index = (512 + this.pad);
/* 303 */       this.ibuffer_len = 512;
/*     */     }
/*     */     
/*     */     public int available() throws IOException {
/* 307 */       return 0;
/*     */     }
/*     */     
/*     */     public void close() throws IOException {
/* 311 */       this.ais.close();
/*     */     }
/*     */     
/*     */     public AudioFormat getFormat() {
/* 315 */       return this.targetFormat;
/*     */     }
/*     */     
/*     */     public long getFrameLength() {
/* 319 */       return -1L;
/*     */     }
/*     */     
/*     */     public void mark(int param1Int) {
/* 323 */       this.ais.mark((int)(param1Int * this.pitch[0]));
/* 324 */       this.mark_ibuffer_index = this.ibuffer_index;
/* 325 */       this.mark_ibuffer_len = this.ibuffer_len;
/* 326 */       if (this.mark_ibuffer == null) {
/* 327 */         this.mark_ibuffer = new float[this.ibuffer.length][(this.ibuffer[0]).length];
/*     */       }
/* 329 */       for (byte b = 0; b < this.ibuffer.length; b++) {
/* 330 */         float[] arrayOfFloat1 = this.ibuffer[b];
/* 331 */         float[] arrayOfFloat2 = this.mark_ibuffer[b];
/* 332 */         for (byte b1 = 0; b1 < arrayOfFloat2.length; b1++) {
/* 333 */           arrayOfFloat2[b1] = arrayOfFloat1[b1];
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean markSupported() {
/* 339 */       return this.ais.markSupported();
/*     */     }
/*     */ 
/*     */     
/*     */     private void readNextBuffer() throws IOException {
/* 344 */       if (this.ibuffer_len == -1)
/*     */         return; 
/*     */       int i;
/* 347 */       for (i = 0; i < this.nrofchannels; i++) {
/* 348 */         float[] arrayOfFloat = this.ibuffer[i];
/* 349 */         int j = this.ibuffer_len + this.pad2; int k; byte b1;
/* 350 */         for (k = this.ibuffer_len, b1 = 0; k < j; k++, b1++) {
/* 351 */           arrayOfFloat[b1] = arrayOfFloat[k];
/*     */         }
/*     */       } 
/*     */       
/* 355 */       this.ibuffer_index -= this.ibuffer_len;
/*     */       
/* 357 */       this.ibuffer_len = this.ais.read(this.ibuffer2);
/* 358 */       if (this.ibuffer_len >= 0) {
/* 359 */         while (this.ibuffer_len < this.ibuffer2.length) {
/* 360 */           i = this.ais.read(this.ibuffer2, this.ibuffer_len, this.ibuffer2.length - this.ibuffer_len);
/*     */           
/* 362 */           if (i == -1)
/*     */             break; 
/* 364 */           this.ibuffer_len += i;
/*     */         } 
/* 366 */         Arrays.fill(this.ibuffer2, this.ibuffer_len, this.ibuffer2.length, 0.0F);
/* 367 */         this.ibuffer_len /= this.nrofchannels;
/*     */       } else {
/* 369 */         Arrays.fill(this.ibuffer2, 0, this.ibuffer2.length, 0.0F);
/*     */       } 
/*     */       
/* 372 */       i = this.ibuffer2.length;
/* 373 */       for (byte b = 0; b < this.nrofchannels; b++) {
/* 374 */         float[] arrayOfFloat = this.ibuffer[b];
/* 375 */         for (int j = b, k = this.pad2; j < i; j += this.nrofchannels, k++) {
/* 376 */           arrayOfFloat[k] = this.ibuffer2[j];
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int read(float[] param1ArrayOffloat, int param1Int1, int param1Int2) throws IOException {
/* 384 */       if (this.cbuffer == null || (this.cbuffer[0]).length < param1Int2 / this.nrofchannels) {
/* 385 */         this.cbuffer = new float[this.nrofchannels][param1Int2 / this.nrofchannels];
/*     */       }
/* 387 */       if (this.ibuffer_len == -1)
/* 388 */         return -1; 
/* 389 */       if (param1Int2 < 0)
/* 390 */         return 0; 
/* 391 */       int i = param1Int1 + param1Int2;
/* 392 */       int j = param1Int2 / this.nrofchannels;
/* 393 */       int k = 0;
/* 394 */       int m = this.ibuffer_len;
/* 395 */       while (j > 0) {
/* 396 */         if (this.ibuffer_len >= 0) {
/* 397 */           if (this.ibuffer_index >= (this.ibuffer_len + this.pad))
/* 398 */             readNextBuffer(); 
/* 399 */           m = this.ibuffer_len + this.pad;
/*     */         } 
/*     */         
/* 402 */         if (this.ibuffer_len < 0) {
/* 403 */           m = this.pad2;
/* 404 */           if (this.ibuffer_index >= m) {
/*     */             break;
/*     */           }
/*     */         } 
/* 408 */         if (this.ibuffer_index < 0.0F)
/*     */           break; 
/* 410 */         int n = k;
/* 411 */         for (byte b1 = 0; b1 < this.nrofchannels; b1++) {
/* 412 */           this.ix[0] = this.ibuffer_index;
/* 413 */           this.ox[0] = k;
/* 414 */           float[] arrayOfFloat = this.ibuffer[b1];
/* 415 */           this.resampler.interpolate(arrayOfFloat, this.ix, m, this.pitch, 0.0F, this.cbuffer[b1], this.ox, param1Int2 / this.nrofchannels);
/*     */         } 
/*     */         
/* 418 */         this.ibuffer_index = this.ix[0];
/* 419 */         k = this.ox[0];
/* 420 */         j -= k - n;
/*     */       } 
/* 422 */       for (byte b = 0; b < this.nrofchannels; b++) {
/* 423 */         byte b1 = 0;
/* 424 */         float[] arrayOfFloat = this.cbuffer[b]; int n;
/* 425 */         for (n = b + param1Int1; n < i; n += this.nrofchannels) {
/* 426 */           param1ArrayOffloat[n] = arrayOfFloat[b1++];
/*     */         }
/*     */       } 
/* 429 */       return param1Int2 - j * this.nrofchannels;
/*     */     }
/*     */     
/*     */     public void reset() throws IOException {
/* 433 */       this.ais.reset();
/* 434 */       if (this.mark_ibuffer == null)
/*     */         return; 
/* 436 */       this.ibuffer_index = this.mark_ibuffer_index;
/* 437 */       this.ibuffer_len = this.mark_ibuffer_len;
/* 438 */       for (byte b = 0; b < this.ibuffer.length; b++) {
/* 439 */         float[] arrayOfFloat1 = this.mark_ibuffer[b];
/* 440 */         float[] arrayOfFloat2 = this.ibuffer[b];
/* 441 */         for (byte b1 = 0; b1 < arrayOfFloat2.length; b1++) {
/* 442 */           arrayOfFloat2[b1] = arrayOfFloat1[b1];
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long skip(long param1Long) throws IOException {
/* 449 */       if (param1Long < 0L)
/* 450 */         return 0L; 
/* 451 */       if (this.skipbuffer == null)
/* 452 */         this.skipbuffer = new float[1024 * this.targetFormat.getFrameSize()]; 
/* 453 */       float[] arrayOfFloat = this.skipbuffer;
/* 454 */       long l = param1Long;
/* 455 */       while (l > 0L) {
/* 456 */         int i = read(arrayOfFloat, 0, (int)Math.min(l, this.skipbuffer.length));
/*     */         
/* 458 */         if (i < 0) {
/* 459 */           if (l == param1Long)
/* 460 */             return i; 
/*     */           break;
/*     */         } 
/* 463 */         l -= i;
/*     */       } 
/* 465 */       return param1Long - l;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 471 */   private final AudioFormat.Encoding[] formats = new AudioFormat.Encoding[] { AudioFormat.Encoding.PCM_SIGNED, AudioFormat.Encoding.PCM_UNSIGNED, AudioFormat.Encoding.PCM_FLOAT };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioInputStream getAudioInputStream(AudioFormat.Encoding paramEncoding, AudioInputStream paramAudioInputStream) {
/* 477 */     if (paramAudioInputStream.getFormat().getEncoding().equals(paramEncoding))
/* 478 */       return paramAudioInputStream; 
/* 479 */     AudioFormat audioFormat1 = paramAudioInputStream.getFormat();
/* 480 */     int i = audioFormat1.getChannels();
/* 481 */     AudioFormat.Encoding encoding = paramEncoding;
/* 482 */     float f = audioFormat1.getSampleRate();
/* 483 */     int j = audioFormat1.getSampleSizeInBits();
/* 484 */     boolean bool = audioFormat1.isBigEndian();
/* 485 */     if (paramEncoding.equals(AudioFormat.Encoding.PCM_FLOAT))
/* 486 */       j = 32; 
/* 487 */     AudioFormat audioFormat2 = new AudioFormat(encoding, f, j, i, i * j / 8, f, bool);
/*     */     
/* 489 */     return getAudioInputStream(audioFormat2, paramAudioInputStream);
/*     */   }
/*     */ 
/*     */   
/*     */   public AudioInputStream getAudioInputStream(AudioFormat paramAudioFormat, AudioInputStream paramAudioInputStream) {
/* 494 */     if (!isConversionSupported(paramAudioFormat, paramAudioInputStream.getFormat()))
/* 495 */       throw new IllegalArgumentException("Unsupported conversion: " + paramAudioInputStream
/* 496 */           .getFormat().toString() + " to " + paramAudioFormat
/* 497 */           .toString()); 
/* 498 */     return getAudioInputStream(paramAudioFormat, 
/* 499 */         AudioFloatInputStream.getInputStream(paramAudioInputStream));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioInputStream getAudioInputStream(AudioFormat paramAudioFormat, AudioFloatInputStream paramAudioFloatInputStream) {
/* 505 */     if (!isConversionSupported(paramAudioFormat, paramAudioFloatInputStream.getFormat()))
/* 506 */       throw new IllegalArgumentException("Unsupported conversion: " + paramAudioFloatInputStream
/* 507 */           .getFormat().toString() + " to " + paramAudioFormat
/* 508 */           .toString()); 
/* 509 */     if (paramAudioFormat.getChannels() != paramAudioFloatInputStream.getFormat()
/* 510 */       .getChannels())
/*     */     {
/* 512 */       paramAudioFloatInputStream = new AudioFloatInputStreamChannelMixer(paramAudioFloatInputStream, paramAudioFormat.getChannels()); } 
/* 513 */     if (Math.abs(paramAudioFormat.getSampleRate() - paramAudioFloatInputStream
/* 514 */         .getFormat().getSampleRate()) > 1.0E-6D) {
/* 515 */       paramAudioFloatInputStream = new AudioFloatInputStreamResampler(paramAudioFloatInputStream, paramAudioFormat);
/*     */     }
/* 517 */     return new AudioInputStream(new AudioFloatFormatConverterInputStream(paramAudioFormat, paramAudioFloatInputStream), paramAudioFormat, paramAudioFloatInputStream
/*     */         
/* 519 */         .getFrameLength());
/*     */   }
/*     */   
/*     */   public AudioFormat.Encoding[] getSourceEncodings() {
/* 523 */     return new AudioFormat.Encoding[] { AudioFormat.Encoding.PCM_SIGNED, AudioFormat.Encoding.PCM_UNSIGNED, AudioFormat.Encoding.PCM_FLOAT };
/*     */   }
/*     */ 
/*     */   
/*     */   public AudioFormat.Encoding[] getTargetEncodings() {
/* 528 */     return new AudioFormat.Encoding[] { AudioFormat.Encoding.PCM_SIGNED, AudioFormat.Encoding.PCM_UNSIGNED, AudioFormat.Encoding.PCM_FLOAT };
/*     */   }
/*     */ 
/*     */   
/*     */   public AudioFormat.Encoding[] getTargetEncodings(AudioFormat paramAudioFormat) {
/* 533 */     if (AudioFloatConverter.getConverter(paramAudioFormat) == null)
/* 534 */       return new AudioFormat.Encoding[0]; 
/* 535 */     return new AudioFormat.Encoding[] { AudioFormat.Encoding.PCM_SIGNED, AudioFormat.Encoding.PCM_UNSIGNED, AudioFormat.Encoding.PCM_FLOAT };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioFormat[] getTargetFormats(AudioFormat.Encoding paramEncoding, AudioFormat paramAudioFormat) {
/* 541 */     if (AudioFloatConverter.getConverter(paramAudioFormat) == null)
/* 542 */       return new AudioFormat[0]; 
/* 543 */     int i = paramAudioFormat.getChannels();
/*     */     
/* 545 */     ArrayList<AudioFormat> arrayList = new ArrayList();
/*     */     
/* 547 */     if (paramEncoding.equals(AudioFormat.Encoding.PCM_SIGNED)) {
/* 548 */       arrayList.add(new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, -1.0F, 8, i, i, -1.0F, false));
/*     */     }
/*     */     
/* 551 */     if (paramEncoding.equals(AudioFormat.Encoding.PCM_UNSIGNED)) {
/* 552 */       arrayList.add(new AudioFormat(AudioFormat.Encoding.PCM_UNSIGNED, -1.0F, 8, i, i, -1.0F, false));
/*     */     }
/*     */ 
/*     */     
/* 556 */     for (byte b = 16; b < 32; b += 8) {
/* 557 */       if (paramEncoding.equals(AudioFormat.Encoding.PCM_SIGNED)) {
/* 558 */         arrayList.add(new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, -1.0F, b, i, i * b / 8, -1.0F, false));
/*     */ 
/*     */         
/* 561 */         arrayList.add(new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, -1.0F, b, i, i * b / 8, -1.0F, true));
/*     */       } 
/*     */ 
/*     */       
/* 565 */       if (paramEncoding.equals(AudioFormat.Encoding.PCM_UNSIGNED)) {
/* 566 */         arrayList.add(new AudioFormat(AudioFormat.Encoding.PCM_UNSIGNED, -1.0F, b, i, i * b / 8, -1.0F, true));
/*     */ 
/*     */         
/* 569 */         arrayList.add(new AudioFormat(AudioFormat.Encoding.PCM_UNSIGNED, -1.0F, b, i, i * b / 8, -1.0F, false));
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 575 */     if (paramEncoding.equals(AudioFormat.Encoding.PCM_FLOAT)) {
/* 576 */       arrayList.add(new AudioFormat(AudioFormat.Encoding.PCM_FLOAT, -1.0F, 32, i, i * 4, -1.0F, false));
/*     */ 
/*     */       
/* 579 */       arrayList.add(new AudioFormat(AudioFormat.Encoding.PCM_FLOAT, -1.0F, 32, i, i * 4, -1.0F, true));
/*     */ 
/*     */       
/* 582 */       arrayList.add(new AudioFormat(AudioFormat.Encoding.PCM_FLOAT, -1.0F, 64, i, i * 8, -1.0F, false));
/*     */ 
/*     */       
/* 585 */       arrayList.add(new AudioFormat(AudioFormat.Encoding.PCM_FLOAT, -1.0F, 64, i, i * 8, -1.0F, true));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 590 */     return arrayList.<AudioFormat>toArray(new AudioFormat[arrayList.size()]);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConversionSupported(AudioFormat paramAudioFormat1, AudioFormat paramAudioFormat2) {
/* 595 */     if (AudioFloatConverter.getConverter(paramAudioFormat2) == null)
/* 596 */       return false; 
/* 597 */     if (AudioFloatConverter.getConverter(paramAudioFormat1) == null)
/* 598 */       return false; 
/* 599 */     if (paramAudioFormat2.getChannels() <= 0)
/* 600 */       return false; 
/* 601 */     if (paramAudioFormat1.getChannels() <= 0)
/* 602 */       return false; 
/* 603 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConversionSupported(AudioFormat.Encoding paramEncoding, AudioFormat paramAudioFormat) {
/* 608 */     if (AudioFloatConverter.getConverter(paramAudioFormat) == null)
/* 609 */       return false; 
/* 610 */     for (byte b = 0; b < this.formats.length; b++) {
/* 611 */       if (paramEncoding.equals(this.formats[b]))
/* 612 */         return true; 
/*     */     } 
/* 614 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/AudioFloatFormatConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */