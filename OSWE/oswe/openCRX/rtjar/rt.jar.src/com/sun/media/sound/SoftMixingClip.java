/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Arrays;
/*     */ import javax.sound.sampled.AudioFormat;
/*     */ import javax.sound.sampled.AudioInputStream;
/*     */ import javax.sound.sampled.Clip;
/*     */ import javax.sound.sampled.DataLine;
/*     */ import javax.sound.sampled.LineEvent;
/*     */ import javax.sound.sampled.LineUnavailableException;
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
/*     */ public final class SoftMixingClip
/*     */   extends SoftMixingDataLine
/*     */   implements Clip
/*     */ {
/*     */   private AudioFormat format;
/*     */   private int framesize;
/*     */   private byte[] data;
/*     */   
/*  53 */   private final InputStream datastream = new InputStream()
/*     */     {
/*     */       public int read() throws IOException {
/*  56 */         byte[] arrayOfByte = new byte[1];
/*  57 */         int i = read(arrayOfByte);
/*  58 */         if (i < 0)
/*  59 */           return i; 
/*  60 */         return arrayOfByte[0] & 0xFF;
/*     */       }
/*     */ 
/*     */       
/*     */       public int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/*  65 */         if (SoftMixingClip.this._loopcount != 0) {
/*  66 */           int k = SoftMixingClip.this._loopend * SoftMixingClip.this.framesize;
/*  67 */           int m = SoftMixingClip.this._loopstart * SoftMixingClip.this.framesize;
/*  68 */           int n = SoftMixingClip.this._frameposition * SoftMixingClip.this.framesize;
/*     */           
/*  70 */           if (n + param1Int2 >= k && 
/*  71 */             n < k) {
/*  72 */             int i1 = param1Int1 + param1Int2;
/*  73 */             int i2 = param1Int1;
/*  74 */             while (param1Int1 != i1) {
/*  75 */               if (n == k) {
/*  76 */                 if (SoftMixingClip.this._loopcount == 0)
/*     */                   break; 
/*  78 */                 n = m;
/*  79 */                 if (SoftMixingClip.this._loopcount != -1)
/*  80 */                   SoftMixingClip.this._loopcount--; 
/*     */               } 
/*  82 */               param1Int2 = i1 - param1Int1;
/*  83 */               int i3 = k - n;
/*  84 */               if (param1Int2 > i3)
/*  85 */                 param1Int2 = i3; 
/*  86 */               System.arraycopy(SoftMixingClip.this.data, n, param1ArrayOfbyte, param1Int1, param1Int2);
/*  87 */               param1Int1 += param1Int2;
/*     */             } 
/*  89 */             if (SoftMixingClip.this._loopcount == 0) {
/*  90 */               param1Int2 = i1 - param1Int1;
/*  91 */               int i3 = k - n;
/*  92 */               if (param1Int2 > i3)
/*  93 */                 param1Int2 = i3; 
/*  94 */               System.arraycopy(SoftMixingClip.this.data, n, param1ArrayOfbyte, param1Int1, param1Int2);
/*  95 */               param1Int1 += param1Int2;
/*     */             } 
/*  97 */             SoftMixingClip.this._frameposition = n / SoftMixingClip.this.framesize;
/*  98 */             return i2 - param1Int1;
/*     */           } 
/*     */         } 
/*     */         
/* 102 */         int i = SoftMixingClip.this._frameposition * SoftMixingClip.this.framesize;
/* 103 */         int j = SoftMixingClip.this.bufferSize - i;
/* 104 */         if (j == 0)
/* 105 */           return -1; 
/* 106 */         if (param1Int2 > j)
/* 107 */           param1Int2 = j; 
/* 108 */         System.arraycopy(SoftMixingClip.this.data, i, param1ArrayOfbyte, param1Int1, param1Int2);
/* 109 */         SoftMixingClip.this._frameposition = SoftMixingClip.this._frameposition + param1Int2 / SoftMixingClip.this.framesize;
/* 110 */         return param1Int2;
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private int offset;
/*     */   
/*     */   private int bufferSize;
/*     */   
/*     */   private float[] readbuffer;
/*     */   
/*     */   private boolean open = false;
/*     */   
/*     */   private AudioFormat outputformat;
/*     */   
/*     */   private int out_nrofchannels;
/*     */   
/*     */   private int in_nrofchannels;
/*     */   
/* 129 */   private int frameposition = 0;
/*     */   
/*     */   private boolean frameposition_sg = false;
/*     */   
/*     */   private boolean active_sg = false;
/*     */   
/* 135 */   private int loopstart = 0;
/*     */   
/* 137 */   private int loopend = -1;
/*     */   
/*     */   private boolean active = false;
/*     */   
/* 141 */   private int loopcount = 0;
/*     */   
/*     */   private boolean _active = false;
/*     */   
/* 145 */   private int _frameposition = 0;
/*     */   
/*     */   private boolean loop_sg = false;
/*     */   
/* 149 */   private int _loopcount = 0;
/*     */   
/* 151 */   private int _loopstart = 0;
/*     */   
/* 153 */   private int _loopend = -1;
/*     */   
/*     */   private float _rightgain;
/*     */   
/*     */   private float _leftgain;
/*     */   
/*     */   private float _eff1gain;
/*     */   
/*     */   private float _eff2gain;
/*     */   
/*     */   private AudioFloatInputStream afis;
/*     */   
/*     */   SoftMixingClip(SoftMixingMixer paramSoftMixingMixer, DataLine.Info paramInfo) {
/* 166 */     super(paramSoftMixingMixer, paramInfo);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processControlLogic() {
/* 171 */     this._rightgain = this.rightgain;
/* 172 */     this._leftgain = this.leftgain;
/* 173 */     this._eff1gain = this.eff1gain;
/* 174 */     this._eff2gain = this.eff2gain;
/*     */     
/* 176 */     if (this.active_sg) {
/* 177 */       this._active = this.active;
/* 178 */       this.active_sg = false;
/*     */     } else {
/* 180 */       this.active = this._active;
/*     */     } 
/*     */     
/* 183 */     if (this.frameposition_sg) {
/* 184 */       this._frameposition = this.frameposition;
/* 185 */       this.frameposition_sg = false;
/* 186 */       this.afis = null;
/*     */     } else {
/* 188 */       this.frameposition = this._frameposition;
/*     */     } 
/* 190 */     if (this.loop_sg) {
/* 191 */       this._loopcount = this.loopcount;
/* 192 */       this._loopstart = this.loopstart;
/* 193 */       this._loopend = this.loopend;
/*     */     } 
/*     */     
/* 196 */     if (this.afis == null) {
/* 197 */       this.afis = AudioFloatInputStream.getInputStream(new AudioInputStream(this.datastream, this.format, -1L));
/*     */ 
/*     */       
/* 200 */       if (Math.abs(this.format.getSampleRate() - this.outputformat.getSampleRate()) > 1.0E-6D) {
/* 201 */         this.afis = new SoftMixingDataLine.AudioFloatInputStreamResampler(this.afis, this.outputformat);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void processAudioLogic(SoftAudioBuffer[] paramArrayOfSoftAudioBuffer) {
/* 207 */     if (this._active) {
/* 208 */       float[] arrayOfFloat1 = paramArrayOfSoftAudioBuffer[0].array();
/* 209 */       float[] arrayOfFloat2 = paramArrayOfSoftAudioBuffer[1].array();
/* 210 */       int i = paramArrayOfSoftAudioBuffer[0].getSize();
/*     */       
/* 212 */       int j = i * this.in_nrofchannels;
/* 213 */       if (this.readbuffer == null || this.readbuffer.length < j) {
/* 214 */         this.readbuffer = new float[j];
/*     */       }
/* 216 */       int k = 0;
/*     */       try {
/* 218 */         k = this.afis.read(this.readbuffer);
/* 219 */         if (k == -1) {
/* 220 */           this._active = false;
/*     */           return;
/*     */         } 
/* 223 */         if (k != this.in_nrofchannels)
/* 224 */           Arrays.fill(this.readbuffer, k, j, 0.0F); 
/* 225 */       } catch (IOException iOException) {}
/*     */ 
/*     */       
/* 228 */       int m = this.in_nrofchannels; byte b; int n;
/* 229 */       for (b = 0, n = 0; b < i; b++, n += m) {
/* 230 */         arrayOfFloat1[b] = arrayOfFloat1[b] + this.readbuffer[n] * this._leftgain;
/*     */       }
/*     */       
/* 233 */       if (this.out_nrofchannels != 1) {
/* 234 */         if (this.in_nrofchannels == 1) {
/* 235 */           for (b = 0, n = 0; b < i; b++, n += m) {
/* 236 */             arrayOfFloat2[b] = arrayOfFloat2[b] + this.readbuffer[n] * this._rightgain;
/*     */           }
/*     */         } else {
/* 239 */           for (b = 0, n = 1; b < i; b++, n += m) {
/* 240 */             arrayOfFloat2[b] = arrayOfFloat2[b] + this.readbuffer[n] * this._rightgain;
/*     */           }
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 246 */       if (this._eff1gain > 2.0E-4D) {
/*     */ 
/*     */         
/* 249 */         float[] arrayOfFloat = paramArrayOfSoftAudioBuffer[2].array(); int i1;
/* 250 */         for (n = 0, i1 = 0; n < i; n++, i1 += m) {
/* 251 */           arrayOfFloat[n] = arrayOfFloat[n] + this.readbuffer[i1] * this._eff1gain;
/*     */         }
/* 253 */         if (this.in_nrofchannels == 2) {
/* 254 */           for (n = 0, i1 = 1; n < i; n++, i1 += m) {
/* 255 */             arrayOfFloat[n] = arrayOfFloat[n] + this.readbuffer[i1] * this._eff1gain;
/*     */           }
/*     */         }
/*     */       } 
/*     */       
/* 260 */       if (this._eff2gain > 2.0E-4D) {
/*     */         
/* 262 */         float[] arrayOfFloat = paramArrayOfSoftAudioBuffer[3].array(); int i1;
/* 263 */         for (n = 0, i1 = 0; n < i; n++, i1 += m) {
/* 264 */           arrayOfFloat[n] = arrayOfFloat[n] + this.readbuffer[i1] * this._eff2gain;
/*     */         }
/* 266 */         if (this.in_nrofchannels == 2) {
/* 267 */           for (n = 0, i1 = 1; n < i; n++, i1 += m) {
/* 268 */             arrayOfFloat[n] = arrayOfFloat[n] + this.readbuffer[i1] * this._eff2gain;
/*     */           }
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFrameLength() {
/* 277 */     return this.bufferSize / this.format.getFrameSize();
/*     */   }
/*     */   
/*     */   public long getMicrosecondLength() {
/* 281 */     return 
/* 282 */       (long)(getFrameLength() * 1000000.0D / getFormat().getSampleRate());
/*     */   }
/*     */   
/*     */   public void loop(int paramInt) {
/* 286 */     LineEvent lineEvent = null;
/*     */     
/* 288 */     synchronized (this.control_mutex) {
/* 289 */       if (isOpen()) {
/* 290 */         if (this.active)
/*     */           return; 
/* 292 */         this.active = true;
/* 293 */         this.active_sg = true;
/* 294 */         this.loopcount = paramInt;
/*     */         
/* 296 */         lineEvent = new LineEvent(this, LineEvent.Type.START, getLongFramePosition());
/*     */       } 
/*     */     } 
/*     */     
/* 300 */     if (lineEvent != null) {
/* 301 */       sendEvent(lineEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void open(AudioInputStream paramAudioInputStream) throws LineUnavailableException, IOException {
/* 307 */     if (isOpen()) {
/* 308 */       throw new IllegalStateException("Clip is already open with format " + 
/* 309 */           getFormat() + " and frame lengh of " + getFrameLength());
/*     */     }
/* 311 */     if (AudioFloatConverter.getConverter(paramAudioInputStream.getFormat()) == null) {
/* 312 */       throw new IllegalArgumentException("Invalid format : " + paramAudioInputStream
/* 313 */           .getFormat().toString());
/*     */     }
/* 315 */     if (paramAudioInputStream.getFrameLength() != -1L) {
/*     */       
/* 317 */       byte[] arrayOfByte = new byte[(int)paramAudioInputStream.getFrameLength() * paramAudioInputStream.getFormat().getFrameSize()];
/* 318 */       int i = 512 * paramAudioInputStream.getFormat().getFrameSize();
/* 319 */       int j = 0;
/* 320 */       while (j != arrayOfByte.length) {
/* 321 */         if (i > arrayOfByte.length - j)
/* 322 */           i = arrayOfByte.length - j; 
/* 323 */         int k = paramAudioInputStream.read(arrayOfByte, j, i);
/* 324 */         if (k == -1)
/*     */           break; 
/* 326 */         if (k == 0)
/* 327 */           Thread.yield(); 
/* 328 */         j += k;
/*     */       } 
/* 330 */       open(paramAudioInputStream.getFormat(), arrayOfByte, 0, j);
/*     */     } else {
/* 332 */       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 333 */       byte[] arrayOfByte = new byte[512 * paramAudioInputStream.getFormat().getFrameSize()];
/* 334 */       int i = 0;
/* 335 */       while ((i = paramAudioInputStream.read(arrayOfByte)) != -1) {
/* 336 */         if (i == 0)
/* 337 */           Thread.yield(); 
/* 338 */         byteArrayOutputStream.write(arrayOfByte, 0, i);
/*     */       } 
/* 340 */       open(paramAudioInputStream.getFormat(), byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.size());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void open(AudioFormat paramAudioFormat, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws LineUnavailableException {
/* 347 */     synchronized (this.control_mutex) {
/* 348 */       if (isOpen()) {
/* 349 */         throw new IllegalStateException("Clip is already open with format " + 
/* 350 */             getFormat() + " and frame lengh of " + 
/* 351 */             getFrameLength());
/*     */       }
/* 353 */       if (AudioFloatConverter.getConverter(paramAudioFormat) == null)
/* 354 */         throw new IllegalArgumentException("Invalid format : " + paramAudioFormat
/* 355 */             .toString()); 
/* 356 */       if (paramInt2 % paramAudioFormat.getFrameSize() != 0) {
/* 357 */         throw new IllegalArgumentException("Buffer size does not represent an integral number of sample frames!");
/*     */       }
/*     */       
/* 360 */       if (paramArrayOfbyte != null) {
/* 361 */         this.data = Arrays.copyOf(paramArrayOfbyte, paramArrayOfbyte.length);
/*     */       }
/* 363 */       this.offset = paramInt1;
/* 364 */       this.bufferSize = paramInt2;
/* 365 */       this.format = paramAudioFormat;
/* 366 */       this.framesize = paramAudioFormat.getFrameSize();
/*     */       
/* 368 */       this.loopstart = 0;
/* 369 */       this.loopend = -1;
/* 370 */       this.loop_sg = true;
/*     */       
/* 372 */       if (!this.mixer.isOpen()) {
/* 373 */         this.mixer.open();
/* 374 */         this.mixer.implicitOpen = true;
/*     */       } 
/*     */       
/* 377 */       this.outputformat = this.mixer.getFormat();
/* 378 */       this.out_nrofchannels = this.outputformat.getChannels();
/* 379 */       this.in_nrofchannels = paramAudioFormat.getChannels();
/*     */       
/* 381 */       this.open = true;
/*     */       
/* 383 */       this.mixer.getMainMixer().openLine(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFramePosition(int paramInt) {
/* 389 */     synchronized (this.control_mutex) {
/* 390 */       this.frameposition_sg = true;
/* 391 */       this.frameposition = paramInt;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setLoopPoints(int paramInt1, int paramInt2) {
/* 396 */     synchronized (this.control_mutex) {
/* 397 */       if (paramInt2 != -1) {
/* 398 */         if (paramInt2 < paramInt1) {
/* 399 */           throw new IllegalArgumentException("Invalid loop points : " + paramInt1 + " - " + paramInt2);
/*     */         }
/* 401 */         if (paramInt2 * this.framesize > this.bufferSize) {
/* 402 */           throw new IllegalArgumentException("Invalid loop points : " + paramInt1 + " - " + paramInt2);
/*     */         }
/*     */       } 
/* 405 */       if (paramInt1 * this.framesize > this.bufferSize) {
/* 406 */         throw new IllegalArgumentException("Invalid loop points : " + paramInt1 + " - " + paramInt2);
/*     */       }
/* 408 */       if (0 < paramInt1) {
/* 409 */         throw new IllegalArgumentException("Invalid loop points : " + paramInt1 + " - " + paramInt2);
/*     */       }
/* 411 */       this.loopstart = paramInt1;
/* 412 */       this.loopend = paramInt2;
/* 413 */       this.loop_sg = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setMicrosecondPosition(long paramLong) {
/* 418 */     setFramePosition(
/* 419 */         (int)(paramLong * getFormat().getSampleRate() / 1000000.0D));
/*     */   }
/*     */   
/*     */   public int available() {
/* 423 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drain() {}
/*     */ 
/*     */   
/*     */   public void flush() {}
/*     */   
/*     */   public int getBufferSize() {
/* 433 */     return this.bufferSize;
/*     */   }
/*     */   
/*     */   public AudioFormat getFormat() {
/* 437 */     return this.format;
/*     */   }
/*     */   
/*     */   public int getFramePosition() {
/* 441 */     synchronized (this.control_mutex) {
/* 442 */       return this.frameposition;
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getLevel() {
/* 447 */     return -1.0F;
/*     */   }
/*     */   
/*     */   public long getLongFramePosition() {
/* 451 */     return getFramePosition();
/*     */   }
/*     */   
/*     */   public long getMicrosecondPosition() {
/* 455 */     return 
/* 456 */       (long)(getFramePosition() * 1000000.0D / getFormat().getSampleRate());
/*     */   }
/*     */   
/*     */   public boolean isActive() {
/* 460 */     synchronized (this.control_mutex) {
/* 461 */       return this.active;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isRunning() {
/* 466 */     synchronized (this.control_mutex) {
/* 467 */       return this.active;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/* 473 */     LineEvent lineEvent = null;
/*     */     
/* 475 */     synchronized (this.control_mutex) {
/* 476 */       if (isOpen()) {
/* 477 */         if (this.active)
/*     */           return; 
/* 479 */         this.active = true;
/* 480 */         this.active_sg = true;
/* 481 */         this.loopcount = 0;
/*     */         
/* 483 */         lineEvent = new LineEvent(this, LineEvent.Type.START, getLongFramePosition());
/*     */       } 
/*     */     } 
/*     */     
/* 487 */     if (lineEvent != null)
/* 488 */       sendEvent(lineEvent); 
/*     */   }
/*     */   
/*     */   public void stop() {
/* 492 */     LineEvent lineEvent = null;
/*     */     
/* 494 */     synchronized (this.control_mutex) {
/* 495 */       if (isOpen()) {
/* 496 */         if (!this.active)
/*     */           return; 
/* 498 */         this.active = false;
/* 499 */         this.active_sg = true;
/*     */         
/* 501 */         lineEvent = new LineEvent(this, LineEvent.Type.STOP, getLongFramePosition());
/*     */       } 
/*     */     } 
/*     */     
/* 505 */     if (lineEvent != null)
/* 506 */       sendEvent(lineEvent); 
/*     */   }
/*     */   
/*     */   public void close() {
/* 510 */     LineEvent lineEvent = null;
/*     */     
/* 512 */     synchronized (this.control_mutex) {
/* 513 */       if (!isOpen())
/*     */         return; 
/* 515 */       stop();
/*     */ 
/*     */       
/* 518 */       lineEvent = new LineEvent(this, LineEvent.Type.CLOSE, getLongFramePosition());
/*     */       
/* 520 */       this.open = false;
/* 521 */       this.mixer.getMainMixer().closeLine(this);
/*     */     } 
/*     */     
/* 524 */     if (lineEvent != null) {
/* 525 */       sendEvent(lineEvent);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isOpen() {
/* 530 */     return this.open;
/*     */   }
/*     */   
/*     */   public void open() throws LineUnavailableException {
/* 534 */     if (this.data == null) {
/* 535 */       throw new IllegalArgumentException("Illegal call to open() in interface Clip");
/*     */     }
/*     */     
/* 538 */     open(this.format, this.data, this.offset, this.bufferSize);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SoftMixingClip.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */