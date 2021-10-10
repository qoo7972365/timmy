/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.sound.sampled.AudioFormat;
/*     */ import javax.sound.sampled.AudioInputStream;
/*     */ import javax.sound.sampled.AudioSystem;
/*     */ import javax.sound.sampled.Clip;
/*     */ import javax.sound.sampled.Control;
/*     */ import javax.sound.sampled.DataLine;
/*     */ import javax.sound.sampled.Line;
/*     */ import javax.sound.sampled.LineEvent;
/*     */ import javax.sound.sampled.LineListener;
/*     */ import javax.sound.sampled.LineUnavailableException;
/*     */ import javax.sound.sampled.Mixer;
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
/*     */ public final class SoftMixingMixer
/*     */   implements Mixer
/*     */ {
/*     */   static final String INFO_NAME = "Gervill Sound Mixer";
/*     */   static final String INFO_VENDOR = "OpenJDK Proposal";
/*     */   static final String INFO_DESCRIPTION = "Software Sound Mixer";
/*     */   static final String INFO_VERSION = "1.0";
/*     */   
/*     */   private static class Info
/*     */     extends Mixer.Info
/*     */   {
/*     */     Info() {
/*  55 */       super("Gervill Sound Mixer", "OpenJDK Proposal", "Software Sound Mixer", "1.0");
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
/*     */   
/*  67 */   static final Mixer.Info info = new Info();
/*     */   
/*  69 */   final Object control_mutex = this;
/*     */   
/*     */   boolean implicitOpen = false;
/*     */   
/*     */   private boolean open = false;
/*     */   
/*  75 */   private SoftMixingMainMixer mainmixer = null;
/*     */   
/*  77 */   private AudioFormat format = new AudioFormat(44100.0F, 16, 2, true, false);
/*     */   
/*  79 */   private SourceDataLine sourceDataLine = null;
/*     */   
/*  81 */   private SoftAudioPusher pusher = null;
/*     */   
/*  83 */   private AudioInputStream pusher_stream = null;
/*     */   
/*  85 */   private final float controlrate = 147.0F;
/*     */   
/*  87 */   private final long latency = 100000L;
/*     */   
/*     */   private final boolean jitter_correction = false;
/*     */   
/*  91 */   private final List<LineListener> listeners = new ArrayList<>();
/*     */   
/*     */   private final Line.Info[] sourceLineInfo;
/*     */ 
/*     */   
/*     */   public SoftMixingMixer() {
/*  97 */     this.sourceLineInfo = new Line.Info[2];
/*     */     
/*  99 */     ArrayList<AudioFormat> arrayList = new ArrayList();
/* 100 */     for (byte b = 1; b <= 2; b++) {
/* 101 */       arrayList.add(new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, -1.0F, 8, b, b, -1.0F, false));
/*     */ 
/*     */       
/* 104 */       arrayList.add(new AudioFormat(AudioFormat.Encoding.PCM_UNSIGNED, -1.0F, 8, b, b, -1.0F, false));
/*     */ 
/*     */       
/* 107 */       for (byte b1 = 16; b1 < 32; b1 += 8) {
/* 108 */         arrayList.add(new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, -1.0F, b1, b, b * b1 / 8, -1.0F, false));
/*     */ 
/*     */         
/* 111 */         arrayList.add(new AudioFormat(AudioFormat.Encoding.PCM_UNSIGNED, -1.0F, b1, b, b * b1 / 8, -1.0F, false));
/*     */ 
/*     */         
/* 114 */         arrayList.add(new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, -1.0F, b1, b, b * b1 / 8, -1.0F, true));
/*     */ 
/*     */         
/* 117 */         arrayList.add(new AudioFormat(AudioFormat.Encoding.PCM_UNSIGNED, -1.0F, b1, b, b * b1 / 8, -1.0F, true));
/*     */       } 
/*     */ 
/*     */       
/* 121 */       arrayList.add(new AudioFormat(AudioFormat.Encoding.PCM_FLOAT, -1.0F, 32, b, b * 4, -1.0F, false));
/*     */ 
/*     */       
/* 124 */       arrayList.add(new AudioFormat(AudioFormat.Encoding.PCM_FLOAT, -1.0F, 32, b, b * 4, -1.0F, true));
/*     */ 
/*     */       
/* 127 */       arrayList.add(new AudioFormat(AudioFormat.Encoding.PCM_FLOAT, -1.0F, 64, b, b * 8, -1.0F, false));
/*     */ 
/*     */       
/* 130 */       arrayList.add(new AudioFormat(AudioFormat.Encoding.PCM_FLOAT, -1.0F, 64, b, b * 8, -1.0F, true));
/*     */     } 
/*     */ 
/*     */     
/* 134 */     AudioFormat[] arrayOfAudioFormat = arrayList.<AudioFormat>toArray(
/* 135 */         new AudioFormat[arrayList.size()]);
/* 136 */     this.sourceLineInfo[0] = new DataLine.Info(SourceDataLine.class, arrayOfAudioFormat, -1, -1);
/*     */ 
/*     */     
/* 139 */     this.sourceLineInfo[1] = new DataLine.Info(Clip.class, arrayOfAudioFormat, -1, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Line getLine(Line.Info paramInfo) throws LineUnavailableException {
/* 145 */     if (!isLineSupported(paramInfo)) {
/* 146 */       throw new IllegalArgumentException("Line unsupported: " + paramInfo);
/*     */     }
/* 148 */     if (paramInfo.getLineClass() == SourceDataLine.class) {
/* 149 */       return new SoftMixingSourceDataLine(this, (DataLine.Info)paramInfo);
/*     */     }
/* 151 */     if (paramInfo.getLineClass() == Clip.class) {
/* 152 */       return new SoftMixingClip(this, (DataLine.Info)paramInfo);
/*     */     }
/*     */     
/* 155 */     throw new IllegalArgumentException("Line unsupported: " + paramInfo);
/*     */   }
/*     */   
/*     */   public int getMaxLines(Line.Info paramInfo) {
/* 159 */     if (paramInfo.getLineClass() == SourceDataLine.class)
/* 160 */       return -1; 
/* 161 */     if (paramInfo.getLineClass() == Clip.class)
/* 162 */       return -1; 
/* 163 */     return 0;
/*     */   }
/*     */   
/*     */   public Mixer.Info getMixerInfo() {
/* 167 */     return info;
/*     */   }
/*     */   
/*     */   public Line.Info[] getSourceLineInfo() {
/* 171 */     Line.Info[] arrayOfInfo = new Line.Info[this.sourceLineInfo.length];
/* 172 */     System.arraycopy(this.sourceLineInfo, 0, arrayOfInfo, 0, this.sourceLineInfo.length);
/*     */     
/* 174 */     return arrayOfInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Line.Info[] getSourceLineInfo(Line.Info paramInfo) {
/* 180 */     ArrayList<Line.Info> arrayList = new ArrayList();
/*     */     
/* 182 */     for (byte b = 0; b < this.sourceLineInfo.length; b++) {
/* 183 */       if (paramInfo.matches(this.sourceLineInfo[b])) {
/* 184 */         arrayList.add(this.sourceLineInfo[b]);
/*     */       }
/*     */     } 
/* 187 */     return arrayList.<Line.Info>toArray(new Line.Info[arrayList.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Line[] getSourceLines() {
/*     */     Line[] arrayOfLine;
/* 194 */     synchronized (this.control_mutex) {
/*     */       
/* 196 */       if (this.mainmixer == null)
/* 197 */         return new Line[0]; 
/* 198 */       SoftMixingDataLine[] arrayOfSoftMixingDataLine = this.mainmixer.getOpenLines();
/*     */       
/* 200 */       arrayOfLine = new Line[arrayOfSoftMixingDataLine.length];
/*     */       
/* 202 */       for (byte b = 0; b < arrayOfLine.length; b++) {
/* 203 */         arrayOfLine[b] = arrayOfSoftMixingDataLine[b];
/*     */       }
/*     */     } 
/*     */     
/* 207 */     return arrayOfLine;
/*     */   }
/*     */   
/*     */   public Line.Info[] getTargetLineInfo() {
/* 211 */     return new Line.Info[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public Line.Info[] getTargetLineInfo(Line.Info paramInfo) {
/* 216 */     return new Line.Info[0];
/*     */   }
/*     */   
/*     */   public Line[] getTargetLines() {
/* 220 */     return new Line[0];
/*     */   }
/*     */   
/*     */   public boolean isLineSupported(Line.Info paramInfo) {
/* 224 */     if (paramInfo != null) {
/* 225 */       for (byte b = 0; b < this.sourceLineInfo.length; b++) {
/* 226 */         if (paramInfo.matches(this.sourceLineInfo[b])) {
/* 227 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/* 231 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isSynchronizationSupported(Line[] paramArrayOfLine, boolean paramBoolean) {
/* 235 */     return false;
/*     */   }
/*     */   
/*     */   public void synchronize(Line[] paramArrayOfLine, boolean paramBoolean) {
/* 239 */     throw new IllegalArgumentException("Synchronization not supported by this mixer.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void unsynchronize(Line[] paramArrayOfLine) {
/* 244 */     throw new IllegalArgumentException("Synchronization not supported by this mixer.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void addLineListener(LineListener paramLineListener) {
/* 249 */     synchronized (this.control_mutex) {
/* 250 */       this.listeners.add(paramLineListener);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void sendEvent(LineEvent paramLineEvent) {
/* 255 */     if (this.listeners.size() == 0) {
/*     */       return;
/*     */     }
/* 258 */     LineListener[] arrayOfLineListener = this.listeners.<LineListener>toArray(new LineListener[this.listeners.size()]);
/* 259 */     for (LineListener lineListener : arrayOfLineListener) {
/* 260 */       lineListener.update(paramLineEvent);
/*     */     }
/*     */   }
/*     */   
/*     */   public void close() {
/* 265 */     if (!isOpen()) {
/*     */       return;
/*     */     }
/* 268 */     sendEvent(new LineEvent(this, LineEvent.Type.CLOSE, -1L));
/*     */ 
/*     */     
/* 271 */     SoftAudioPusher softAudioPusher = null;
/* 272 */     AudioInputStream audioInputStream = null;
/* 273 */     synchronized (this.control_mutex) {
/* 274 */       if (this.pusher != null) {
/* 275 */         softAudioPusher = this.pusher;
/* 276 */         audioInputStream = this.pusher_stream;
/* 277 */         this.pusher = null;
/* 278 */         this.pusher_stream = null;
/*     */       } 
/*     */     } 
/*     */     
/* 282 */     if (softAudioPusher != null) {
/*     */ 
/*     */ 
/*     */       
/* 286 */       softAudioPusher.stop();
/*     */       
/*     */       try {
/* 289 */         audioInputStream.close();
/* 290 */       } catch (IOException iOException) {
/* 291 */         iOException.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 295 */     synchronized (this.control_mutex) {
/*     */       
/* 297 */       if (this.mainmixer != null)
/* 298 */         this.mainmixer.close(); 
/* 299 */       this.open = false;
/*     */       
/* 301 */       if (this.sourceDataLine != null) {
/* 302 */         this.sourceDataLine.drain();
/* 303 */         this.sourceDataLine.close();
/* 304 */         this.sourceDataLine = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Control getControl(Control.Type paramType) {
/* 312 */     throw new IllegalArgumentException("Unsupported control type : " + paramType);
/*     */   }
/*     */ 
/*     */   
/*     */   public Control[] getControls() {
/* 317 */     return new Control[0];
/*     */   }
/*     */   
/*     */   public Line.Info getLineInfo() {
/* 321 */     return new Line.Info(Mixer.class);
/*     */   }
/*     */   
/*     */   public boolean isControlSupported(Control.Type paramType) {
/* 325 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isOpen() {
/* 329 */     synchronized (this.control_mutex) {
/* 330 */       return this.open;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void open() throws LineUnavailableException {
/* 335 */     if (isOpen()) {
/* 336 */       this.implicitOpen = false;
/*     */       return;
/*     */     } 
/* 339 */     open(null);
/*     */   }
/*     */   
/*     */   public void open(SourceDataLine paramSourceDataLine) throws LineUnavailableException {
/* 343 */     if (isOpen()) {
/* 344 */       this.implicitOpen = false;
/*     */       return;
/*     */     } 
/* 347 */     synchronized (this.control_mutex) {
/*     */ 
/*     */       
/*     */       try {
/* 351 */         if (paramSourceDataLine != null) {
/* 352 */           this.format = paramSourceDataLine.getFormat();
/*     */         }
/* 354 */         AudioInputStream audioInputStream = openStream(getFormat());
/*     */         
/* 356 */         if (paramSourceDataLine == null) {
/* 357 */           synchronized (SoftMixingMixerProvider.mutex) {
/*     */             
/* 359 */             SoftMixingMixerProvider.lockthread = Thread.currentThread();
/*     */           } 
/*     */           
/*     */           try {
/* 363 */             Mixer mixer = AudioSystem.getMixer(null);
/* 364 */             if (mixer != null) {
/*     */ 
/*     */ 
/*     */               
/* 368 */               DataLine.Info info = null;
/* 369 */               AudioFormat audioFormat = null;
/*     */               
/* 371 */               Line.Info[] arrayOfInfo = mixer.getSourceLineInfo();
/*     */               byte b;
/* 373 */               label89: for (b = 0; b < arrayOfInfo.length; b++) {
/* 374 */                 if (arrayOfInfo[b].getLineClass() == SourceDataLine.class) {
/*     */                   
/* 376 */                   DataLine.Info info1 = (DataLine.Info)arrayOfInfo[b];
/* 377 */                   AudioFormat[] arrayOfAudioFormat = info1.getFormats();
/* 378 */                   for (byte b1 = 0; b1 < arrayOfAudioFormat.length; b1++) {
/* 379 */                     AudioFormat audioFormat1 = arrayOfAudioFormat[b1];
/* 380 */                     if ((audioFormat1.getChannels() == 2 || audioFormat1
/* 381 */                       .getChannels() == -1) && (
/* 382 */                       audioFormat1.getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED) || audioFormat1
/* 383 */                       .getEncoding().equals(AudioFormat.Encoding.PCM_UNSIGNED)) && (
/* 384 */                       audioFormat1.getSampleRate() == -1.0F || audioFormat1
/* 385 */                       .getSampleRate() == 48000.0D) && (
/* 386 */                       audioFormat1.getSampleSizeInBits() == -1 || audioFormat1
/* 387 */                       .getSampleSizeInBits() == 16)) {
/*     */                       
/* 389 */                       info = info1;
/* 390 */                       int k = audioFormat1.getChannels();
/* 391 */                       boolean bool1 = audioFormat1.getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED);
/* 392 */                       float f = audioFormat1.getSampleRate();
/* 393 */                       boolean bool2 = audioFormat1.isBigEndian();
/* 394 */                       int m = audioFormat1.getSampleSizeInBits();
/* 395 */                       if (m == -1) m = 16; 
/* 396 */                       if (k == -1) k = 2; 
/* 397 */                       if (f == -1.0F) f = 48000.0F; 
/* 398 */                       audioFormat = new AudioFormat(f, m, k, bool1, bool2);
/*     */                       
/*     */                       break label89;
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */               
/* 406 */               if (audioFormat != null) {
/*     */                 
/* 408 */                 this.format = audioFormat;
/* 409 */                 paramSourceDataLine = (SourceDataLine)mixer.getLine(info);
/*     */               } 
/*     */             } 
/*     */             
/* 413 */             if (paramSourceDataLine == null)
/* 414 */               paramSourceDataLine = AudioSystem.getSourceDataLine(this.format); 
/*     */           } finally {
/* 416 */             synchronized (SoftMixingMixerProvider.mutex) {
/* 417 */               SoftMixingMixerProvider.lockthread = null;
/*     */             } 
/*     */           } 
/*     */           
/* 421 */           if (paramSourceDataLine == null) {
/* 422 */             throw new IllegalArgumentException("No line matching " + info
/* 423 */                 .toString() + " is supported.");
/*     */           }
/*     */         } 
/* 426 */         getClass(); double d = 100000.0D;
/*     */         
/* 428 */         if (!paramSourceDataLine.isOpen()) {
/*     */           
/* 430 */           int k = getFormat().getFrameSize() * (int)(getFormat().getFrameRate() * d / 1000000.0D);
/* 431 */           paramSourceDataLine.open(getFormat(), k);
/*     */ 
/*     */ 
/*     */           
/* 435 */           this.sourceDataLine = paramSourceDataLine;
/*     */         } 
/* 437 */         if (!paramSourceDataLine.isActive()) {
/* 438 */           paramSourceDataLine.start();
/*     */         }
/* 440 */         int i = 512;
/*     */         try {
/* 442 */           i = audioInputStream.available();
/* 443 */         } catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 452 */         int j = paramSourceDataLine.getBufferSize();
/* 453 */         j -= j % i;
/*     */         
/* 455 */         if (j < 3 * i) {
/* 456 */           j = 3 * i;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 462 */         this.pusher = new SoftAudioPusher(paramSourceDataLine, audioInputStream, i);
/* 463 */         this.pusher_stream = audioInputStream;
/* 464 */         this.pusher.start();
/*     */       }
/* 466 */       catch (LineUnavailableException lineUnavailableException) {
/* 467 */         if (isOpen())
/* 468 */           close(); 
/* 469 */         throw new LineUnavailableException(lineUnavailableException.toString());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioInputStream openStream(AudioFormat paramAudioFormat) throws LineUnavailableException {
/* 478 */     if (isOpen()) {
/* 479 */       throw new LineUnavailableException("Mixer is already open");
/*     */     }
/* 481 */     synchronized (this.control_mutex) {
/*     */       
/* 483 */       this.open = true;
/*     */       
/* 485 */       this.implicitOpen = false;
/*     */       
/* 487 */       if (paramAudioFormat != null) {
/* 488 */         this.format = paramAudioFormat;
/*     */       }
/* 490 */       this.mainmixer = new SoftMixingMainMixer(this);
/*     */       
/* 492 */       sendEvent(new LineEvent(this, LineEvent.Type.OPEN, -1L));
/*     */ 
/*     */       
/* 495 */       return this.mainmixer.getInputStream();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeLineListener(LineListener paramLineListener) {
/* 502 */     synchronized (this.control_mutex) {
/* 503 */       this.listeners.remove(paramLineListener);
/*     */     } 
/*     */   }
/*     */   
/*     */   public long getLatency() {
/* 508 */     synchronized (this.control_mutex) {
/* 509 */       return 100000L;
/*     */     } 
/*     */   }
/*     */   
/*     */   public AudioFormat getFormat() {
/* 514 */     synchronized (this.control_mutex) {
/* 515 */       return this.format;
/*     */     } 
/*     */   }
/*     */   
/*     */   float getControlRate() {
/* 520 */     return 147.0F;
/*     */   }
/*     */   
/*     */   SoftMixingMainMixer getMainMixer() {
/* 524 */     if (!isOpen())
/* 525 */       return null; 
/* 526 */     return this.mainmixer;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SoftMixingMixer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */