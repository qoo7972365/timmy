/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Arrays;
/*     */ import javax.sound.sampled.AudioFormat;
/*     */ import javax.sound.sampled.AudioInputStream;
/*     */ import javax.sound.sampled.DataLine;
/*     */ import javax.sound.sampled.LineEvent;
/*     */ import javax.sound.sampled.LineUnavailableException;
/*     */ import javax.sound.sampled.SourceDataLine;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SoftMixingSourceDataLine
/*     */   extends SoftMixingDataLine
/*     */   implements SourceDataLine
/*     */ {
/*     */   private boolean open = false;
/*  49 */   private AudioFormat format = new AudioFormat(44100.0F, 16, 2, true, false);
/*     */   
/*     */   private int framesize;
/*     */   
/*  53 */   private int bufferSize = -1;
/*     */   
/*     */   private float[] readbuffer;
/*     */   
/*     */   private boolean active = false;
/*     */   
/*     */   private byte[] cycling_buffer;
/*     */   
/*  61 */   private int cycling_read_pos = 0;
/*     */   
/*  63 */   private int cycling_write_pos = 0;
/*     */   
/*  65 */   private int cycling_avail = 0;
/*     */   
/*  67 */   private long cycling_framepos = 0L; private AudioFloatInputStream afis; private boolean _active; private AudioFormat outputformat; private int out_nrofchannels; private int in_nrofchannels;
/*     */   private float _rightgain;
/*     */   private float _leftgain;
/*     */   private float _eff1gain;
/*     */   private float _eff2gain;
/*     */   
/*     */   private static class NonBlockingFloatInputStream extends AudioFloatInputStream { AudioFloatInputStream ais;
/*     */     
/*     */     NonBlockingFloatInputStream(AudioFloatInputStream param1AudioFloatInputStream) {
/*  76 */       this.ais = param1AudioFloatInputStream;
/*     */     }
/*     */     
/*     */     public int available() throws IOException {
/*  80 */       return this.ais.available();
/*     */     }
/*     */     
/*     */     public void close() throws IOException {
/*  84 */       this.ais.close();
/*     */     }
/*     */     
/*     */     public AudioFormat getFormat() {
/*  88 */       return this.ais.getFormat();
/*     */     }
/*     */     
/*     */     public long getFrameLength() {
/*  92 */       return this.ais.getFrameLength();
/*     */     }
/*     */     
/*     */     public void mark(int param1Int) {
/*  96 */       this.ais.mark(param1Int);
/*     */     }
/*     */     
/*     */     public boolean markSupported() {
/* 100 */       return this.ais.markSupported();
/*     */     }
/*     */     
/*     */     public int read(float[] param1ArrayOffloat, int param1Int1, int param1Int2) throws IOException {
/* 104 */       int i = available();
/* 105 */       if (param1Int2 > i) {
/* 106 */         int j = this.ais.read(param1ArrayOffloat, param1Int1, i);
/* 107 */         Arrays.fill(param1ArrayOffloat, param1Int1 + j, param1Int1 + param1Int2, 0.0F);
/* 108 */         return param1Int2;
/*     */       } 
/* 110 */       return this.ais.read(param1ArrayOffloat, param1Int1, param1Int2);
/*     */     }
/*     */     
/*     */     public void reset() throws IOException {
/* 114 */       this.ais.reset();
/*     */     }
/*     */     
/*     */     public long skip(long param1Long) throws IOException {
/* 118 */       return this.ais.skip(param1Long);
/*     */     } }
/*     */ 
/*     */   
/*     */   SoftMixingSourceDataLine(SoftMixingMixer paramSoftMixingMixer, DataLine.Info paramInfo)
/*     */   {
/* 124 */     super(paramSoftMixingMixer, paramInfo);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 189 */     this._active = false;
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
/*     */   protected void processControlLogic() {
/* 206 */     this._active = this.active;
/* 207 */     this._rightgain = this.rightgain;
/* 208 */     this._leftgain = this.leftgain;
/* 209 */     this._eff1gain = this.eff1gain;
/* 210 */     this._eff2gain = this.eff2gain;
/*     */   } public int write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) { if (!isOpen()) return 0;  if (paramInt2 % this.framesize != 0) throw new IllegalArgumentException("Number of bytes does not represent an integral number of sample frames.");  if (paramInt1 < 0) throw new ArrayIndexOutOfBoundsException(paramInt1);  if (paramInt1 + paramInt2 > paramArrayOfbyte.length) throw new ArrayIndexOutOfBoundsException(paramArrayOfbyte.length);  byte[] arrayOfByte = this.cycling_buffer; int i = this.cycling_buffer.length; int j = 0; while (j != paramInt2) { int k; synchronized (this.cycling_buffer) { int m = this.cycling_write_pos; k = this.cycling_avail; while (j != paramInt2 && k != i) { arrayOfByte[m++] = paramArrayOfbyte[paramInt1++]; j++; k++; if (m == i)
/*     */             m = 0;  }  this.cycling_avail = k; this.cycling_write_pos = m; if (j == paramInt2)
/*     */           return j;  }  if (k == i) { try { Thread.sleep(1L); } catch (InterruptedException interruptedException) { return j; }  if (!isRunning())
/* 214 */           return j;  }  }  return j; } protected void processAudioLogic(SoftAudioBuffer[] paramArrayOfSoftAudioBuffer) { if (this._active) {
/* 215 */       float[] arrayOfFloat1 = paramArrayOfSoftAudioBuffer[0].array();
/* 216 */       float[] arrayOfFloat2 = paramArrayOfSoftAudioBuffer[1].array();
/* 217 */       int i = paramArrayOfSoftAudioBuffer[0].getSize();
/*     */       
/* 219 */       int j = i * this.in_nrofchannels;
/* 220 */       if (this.readbuffer == null || this.readbuffer.length < j) {
/* 221 */         this.readbuffer = new float[j];
/*     */       }
/* 223 */       int k = 0;
/*     */       try {
/* 225 */         k = this.afis.read(this.readbuffer);
/* 226 */         if (k != this.in_nrofchannels)
/* 227 */           Arrays.fill(this.readbuffer, k, j, 0.0F); 
/* 228 */       } catch (IOException iOException) {}
/*     */ 
/*     */       
/* 231 */       int m = this.in_nrofchannels; byte b; int n;
/* 232 */       for (b = 0, n = 0; b < i; b++, n += m) {
/* 233 */         arrayOfFloat1[b] = arrayOfFloat1[b] + this.readbuffer[n] * this._leftgain;
/*     */       }
/* 235 */       if (this.out_nrofchannels != 1) {
/* 236 */         if (this.in_nrofchannels == 1) {
/* 237 */           for (b = 0, n = 0; b < i; b++, n += m) {
/* 238 */             arrayOfFloat2[b] = arrayOfFloat2[b] + this.readbuffer[n] * this._rightgain;
/*     */           }
/*     */         } else {
/* 241 */           for (b = 0, n = 1; b < i; b++, n += m) {
/* 242 */             arrayOfFloat2[b] = arrayOfFloat2[b] + this.readbuffer[n] * this._rightgain;
/*     */           }
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 248 */       if (this._eff1gain > 1.0E-4D) {
/*     */         
/* 250 */         float[] arrayOfFloat = paramArrayOfSoftAudioBuffer[2].array(); int i1;
/* 251 */         for (n = 0, i1 = 0; n < i; n++, i1 += m) {
/* 252 */           arrayOfFloat[n] = arrayOfFloat[n] + this.readbuffer[i1] * this._eff1gain;
/*     */         }
/* 254 */         if (this.in_nrofchannels == 2) {
/* 255 */           for (n = 0, i1 = 1; n < i; n++, i1 += m) {
/* 256 */             arrayOfFloat[n] = arrayOfFloat[n] + this.readbuffer[i1] * this._eff1gain;
/*     */           }
/*     */         }
/*     */       } 
/*     */       
/* 261 */       if (this._eff2gain > 1.0E-4D) {
/*     */         
/* 263 */         float[] arrayOfFloat = paramArrayOfSoftAudioBuffer[3].array(); int i1;
/* 264 */         for (n = 0, i1 = 0; n < i; n++, i1 += m) {
/* 265 */           arrayOfFloat[n] = arrayOfFloat[n] + this.readbuffer[i1] * this._eff2gain;
/*     */         }
/* 267 */         if (this.in_nrofchannels == 2) {
/* 268 */           for (n = 0, i1 = 1; n < i; n++, i1 += m) {
/* 269 */             arrayOfFloat[n] = arrayOfFloat[n] + this.readbuffer[i1] * this._eff2gain;
/*     */           }
/*     */         }
/*     */       } 
/*     */     }  }
/*     */ 
/*     */ 
/*     */   
/*     */   public void open() throws LineUnavailableException {
/* 278 */     open(this.format);
/*     */   }
/*     */   
/*     */   public void open(AudioFormat paramAudioFormat) throws LineUnavailableException {
/* 282 */     if (this.bufferSize == -1)
/* 283 */       this
/* 284 */         .bufferSize = (int)(paramAudioFormat.getFrameRate() / 2.0F) * paramAudioFormat.getFrameSize(); 
/* 285 */     open(paramAudioFormat, this.bufferSize);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void open(AudioFormat paramAudioFormat, int paramInt) throws LineUnavailableException {
/* 291 */     LineEvent lineEvent = null;
/*     */     
/* 293 */     if (paramInt < paramAudioFormat.getFrameSize() * 32) {
/* 294 */       paramInt = paramAudioFormat.getFrameSize() * 32;
/*     */     }
/* 296 */     synchronized (this.control_mutex) {
/*     */       
/* 298 */       if (!isOpen()) {
/* 299 */         if (!this.mixer.isOpen()) {
/* 300 */           this.mixer.open();
/* 301 */           this.mixer.implicitOpen = true;
/*     */         } 
/*     */         
/* 304 */         lineEvent = new LineEvent(this, LineEvent.Type.OPEN, 0L);
/*     */         
/* 306 */         this
/* 307 */           .bufferSize = paramInt - paramInt % paramAudioFormat.getFrameSize();
/* 308 */         this.format = paramAudioFormat;
/* 309 */         this.framesize = paramAudioFormat.getFrameSize();
/* 310 */         this.outputformat = this.mixer.getFormat();
/* 311 */         this.out_nrofchannels = this.outputformat.getChannels();
/* 312 */         this.in_nrofchannels = paramAudioFormat.getChannels();
/*     */         
/* 314 */         this.open = true;
/*     */         
/* 316 */         this.mixer.getMainMixer().openLine(this);
/*     */         
/* 318 */         this.cycling_buffer = new byte[this.framesize * paramInt];
/* 319 */         this.cycling_read_pos = 0;
/* 320 */         this.cycling_write_pos = 0;
/* 321 */         this.cycling_avail = 0;
/* 322 */         this.cycling_framepos = 0L;
/*     */         
/* 324 */         InputStream inputStream = new InputStream()
/*     */           {
/*     */             public int read() throws IOException {
/* 327 */               byte[] arrayOfByte = new byte[1];
/* 328 */               int i = read(arrayOfByte);
/* 329 */               if (i < 0)
/* 330 */                 return i; 
/* 331 */               return arrayOfByte[0] & 0xFF;
/*     */             }
/*     */             
/*     */             public int available() throws IOException {
/* 335 */               synchronized (SoftMixingSourceDataLine.this.cycling_buffer) {
/* 336 */                 return SoftMixingSourceDataLine.this.cycling_avail;
/*     */               } 
/*     */             }
/*     */ 
/*     */ 
/*     */             
/*     */             public int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/* 343 */               synchronized (SoftMixingSourceDataLine.this.cycling_buffer) {
/* 344 */                 if (param1Int2 > SoftMixingSourceDataLine.this.cycling_avail)
/* 345 */                   param1Int2 = SoftMixingSourceDataLine.this.cycling_avail; 
/* 346 */                 int i = SoftMixingSourceDataLine.this.cycling_read_pos;
/* 347 */                 byte[] arrayOfByte = SoftMixingSourceDataLine.this.cycling_buffer;
/* 348 */                 int j = arrayOfByte.length;
/* 349 */                 for (byte b = 0; b < param1Int2; b++) {
/* 350 */                   param1ArrayOfbyte[param1Int1++] = arrayOfByte[i];
/* 351 */                   i++;
/* 352 */                   if (i == j)
/* 353 */                     i = 0; 
/*     */                 } 
/* 355 */                 SoftMixingSourceDataLine.this.cycling_read_pos = i;
/* 356 */                 SoftMixingSourceDataLine.this.cycling_avail = SoftMixingSourceDataLine.this.cycling_avail - param1Int2;
/* 357 */                 SoftMixingSourceDataLine.this.cycling_framepos = SoftMixingSourceDataLine.this.cycling_framepos + (param1Int2 / SoftMixingSourceDataLine.this.framesize);
/*     */               } 
/* 359 */               return param1Int2;
/*     */             }
/*     */           };
/*     */ 
/*     */         
/* 364 */         this
/* 365 */           .afis = AudioFloatInputStream.getInputStream(new AudioInputStream(inputStream, paramAudioFormat, -1L));
/*     */ 
/*     */         
/* 368 */         this.afis = new NonBlockingFloatInputStream(this.afis);
/*     */         
/* 370 */         if (Math.abs(paramAudioFormat.getSampleRate() - this.outputformat
/* 371 */             .getSampleRate()) > 1.0E-6D) {
/* 372 */           this.afis = new SoftMixingDataLine.AudioFloatInputStreamResampler(this.afis, this.outputformat);
/*     */         
/*     */         }
/*     */       }
/* 376 */       else if (!paramAudioFormat.matches(getFormat())) {
/* 377 */         throw new IllegalStateException("Line is already open with format " + 
/* 378 */             getFormat() + " and bufferSize " + 
/* 379 */             getBufferSize());
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 385 */     if (lineEvent != null) {
/* 386 */       sendEvent(lineEvent);
/*     */     }
/*     */   }
/*     */   
/*     */   public int available() {
/* 391 */     synchronized (this.cycling_buffer) {
/* 392 */       return this.cycling_buffer.length - this.cycling_avail;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void drain() {
/*     */     while (true) {
/*     */       int i;
/* 399 */       synchronized (this.cycling_buffer) {
/* 400 */         i = this.cycling_avail;
/*     */       } 
/* 402 */       if (i != 0)
/*     */         return; 
/*     */       try {
/* 405 */         Thread.sleep(1L);
/* 406 */       } catch (InterruptedException interruptedException) {
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void flush() {
/* 413 */     synchronized (this.cycling_buffer) {
/* 414 */       this.cycling_read_pos = 0;
/* 415 */       this.cycling_write_pos = 0;
/* 416 */       this.cycling_avail = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getBufferSize() {
/* 421 */     synchronized (this.control_mutex) {
/* 422 */       return this.bufferSize;
/*     */     } 
/*     */   }
/*     */   
/*     */   public AudioFormat getFormat() {
/* 427 */     synchronized (this.control_mutex) {
/* 428 */       return this.format;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getFramePosition() {
/* 433 */     return (int)getLongFramePosition();
/*     */   }
/*     */   
/*     */   public float getLevel() {
/* 437 */     return -1.0F;
/*     */   }
/*     */   
/*     */   public long getLongFramePosition() {
/* 441 */     synchronized (this.cycling_buffer) {
/* 442 */       return this.cycling_framepos;
/*     */     } 
/*     */   }
/*     */   
/*     */   public long getMicrosecondPosition() {
/* 447 */     return 
/* 448 */       (long)(getLongFramePosition() * 1000000.0D / getFormat().getSampleRate());
/*     */   }
/*     */   
/*     */   public boolean isActive() {
/* 452 */     synchronized (this.control_mutex) {
/* 453 */       return this.active;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isRunning() {
/* 458 */     synchronized (this.control_mutex) {
/* 459 */       return this.active;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/* 465 */     LineEvent lineEvent = null;
/*     */     
/* 467 */     synchronized (this.control_mutex) {
/* 468 */       if (isOpen()) {
/* 469 */         if (this.active)
/*     */           return; 
/* 471 */         this.active = true;
/*     */         
/* 473 */         lineEvent = new LineEvent(this, LineEvent.Type.START, getLongFramePosition());
/*     */       } 
/*     */     } 
/*     */     
/* 477 */     if (lineEvent != null)
/* 478 */       sendEvent(lineEvent); 
/*     */   }
/*     */   
/*     */   public void stop() {
/* 482 */     LineEvent lineEvent = null;
/*     */     
/* 484 */     synchronized (this.control_mutex) {
/* 485 */       if (isOpen()) {
/* 486 */         if (!this.active)
/*     */           return; 
/* 488 */         this.active = false;
/*     */         
/* 490 */         lineEvent = new LineEvent(this, LineEvent.Type.STOP, getLongFramePosition());
/*     */       } 
/*     */     } 
/*     */     
/* 494 */     if (lineEvent != null) {
/* 495 */       sendEvent(lineEvent);
/*     */     }
/*     */   }
/*     */   
/*     */   public void close() {
/* 500 */     LineEvent lineEvent = null;
/*     */     
/* 502 */     synchronized (this.control_mutex) {
/* 503 */       if (!isOpen())
/*     */         return; 
/* 505 */       stop();
/*     */ 
/*     */       
/* 508 */       lineEvent = new LineEvent(this, LineEvent.Type.CLOSE, getLongFramePosition());
/*     */       
/* 510 */       this.open = false;
/* 511 */       this.mixer.getMainMixer().closeLine(this);
/*     */     } 
/*     */     
/* 514 */     if (lineEvent != null) {
/* 515 */       sendEvent(lineEvent);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isOpen() {
/* 520 */     synchronized (this.control_mutex) {
/* 521 */       return this.open;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SoftMixingSourceDataLine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */