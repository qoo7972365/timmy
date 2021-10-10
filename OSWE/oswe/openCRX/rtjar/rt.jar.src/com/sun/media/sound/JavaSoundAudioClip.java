/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.applet.AudioClip;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import javax.sound.midi.InvalidMidiDataException;
/*     */ import javax.sound.midi.MetaEventListener;
/*     */ import javax.sound.midi.MetaMessage;
/*     */ import javax.sound.midi.MidiFileFormat;
/*     */ import javax.sound.midi.MidiSystem;
/*     */ import javax.sound.midi.MidiUnavailableException;
/*     */ import javax.sound.midi.Sequence;
/*     */ import javax.sound.midi.Sequencer;
/*     */ import javax.sound.sampled.AudioFormat;
/*     */ import javax.sound.sampled.AudioInputStream;
/*     */ import javax.sound.sampled.AudioSystem;
/*     */ import javax.sound.sampled.Clip;
/*     */ import javax.sound.sampled.DataLine;
/*     */ import javax.sound.sampled.Line;
/*     */ import javax.sound.sampled.LineEvent;
/*     */ import javax.sound.sampled.LineListener;
/*     */ import javax.sound.sampled.SourceDataLine;
/*     */ import javax.sound.sampled.UnsupportedAudioFileException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JavaSoundAudioClip
/*     */   implements AudioClip, MetaEventListener, LineListener
/*     */ {
/*     */   private static final boolean DEBUG = false;
/*     */   private static final int BUFFER_SIZE = 16384;
/*  65 */   private long lastPlayCall = 0L;
/*     */   
/*     */   private static final int MINIMUM_PLAY_DELAY = 30;
/*  68 */   private byte[] loadedAudio = null;
/*  69 */   private int loadedAudioByteLength = 0;
/*  70 */   private AudioFormat loadedAudioFormat = null;
/*     */   
/*  72 */   private AutoClosingClip clip = null;
/*     */   
/*     */   private boolean clipLooping = false;
/*  75 */   private DataPusher datapusher = null;
/*     */   
/*  77 */   private Sequencer sequencer = null;
/*  78 */   private Sequence sequence = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean sequencerloop = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long CLIP_THRESHOLD = 1048576L;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int STREAM_BUFFER_SIZE = 1024;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaSoundAudioClip(InputStream paramInputStream) throws IOException {
/*  98 */     BufferedInputStream bufferedInputStream = new BufferedInputStream(paramInputStream, 1024);
/*  99 */     bufferedInputStream.mark(1024);
/* 100 */     boolean bool = false;
/*     */     try {
/* 102 */       AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);
/*     */       
/* 104 */       bool = loadAudioData(audioInputStream);
/*     */       
/* 106 */       if (bool) {
/* 107 */         bool = false;
/* 108 */         if (this.loadedAudioByteLength < 1048576L) {
/* 109 */           bool = createClip();
/*     */         }
/* 111 */         if (!bool) {
/* 112 */           bool = createSourceDataLine();
/*     */         }
/*     */       } 
/* 115 */     } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
/*     */       
/*     */       try {
/* 118 */         MidiFileFormat midiFileFormat = MidiSystem.getMidiFileFormat(bufferedInputStream);
/* 119 */         bool = createSequencer(bufferedInputStream);
/* 120 */       } catch (InvalidMidiDataException invalidMidiDataException) {
/* 121 */         bool = false;
/*     */       } 
/*     */     } 
/* 124 */     if (!bool) {
/* 125 */       throw new IOException("Unable to create AudioClip from input stream");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void play() {
/* 131 */     startImpl(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void loop() {
/* 136 */     startImpl(true);
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized void startImpl(boolean paramBoolean) {
/* 141 */     long l1 = System.currentTimeMillis();
/* 142 */     long l2 = l1 - this.lastPlayCall;
/* 143 */     if (l2 < 30L) {
/*     */       return;
/*     */     }
/*     */     
/* 147 */     this.lastPlayCall = l1;
/*     */ 
/*     */     
/*     */     try {
/* 151 */       if (this.clip != null) {
/*     */ 
/*     */ 
/*     */         
/* 155 */         this.clip.setAutoClosing(false);
/*     */         try {
/* 157 */           if (!this.clip.isOpen()) {
/* 158 */             this.clip.open(this.loadedAudioFormat, this.loadedAudio, 0, this.loadedAudioByteLength);
/*     */           } else {
/*     */             
/* 161 */             this.clip.flush();
/* 162 */             if (paramBoolean != this.clipLooping)
/*     */             {
/* 164 */               this.clip.stop();
/*     */             }
/*     */           } 
/* 167 */           this.clip.setFramePosition(0);
/* 168 */           if (paramBoolean) {
/* 169 */             this.clip.loop(-1);
/*     */           } else {
/* 171 */             this.clip.start();
/*     */           } 
/* 173 */           this.clipLooping = paramBoolean;
/*     */         } finally {
/* 175 */           this.clip.setAutoClosing(true);
/*     */         } 
/* 177 */       } else if (this.datapusher != null) {
/* 178 */         this.datapusher.start(paramBoolean);
/*     */       
/*     */       }
/* 181 */       else if (this.sequencer != null) {
/* 182 */         this.sequencerloop = paramBoolean;
/* 183 */         if (this.sequencer.isRunning()) {
/* 184 */           this.sequencer.setMicrosecondPosition(0L);
/*     */         }
/* 186 */         if (!this.sequencer.isOpen()) {
/*     */           try {
/* 188 */             this.sequencer.open();
/* 189 */             this.sequencer.setSequence(this.sequence);
/*     */           }
/* 191 */           catch (InvalidMidiDataException invalidMidiDataException) {
/*     */           
/* 193 */           } catch (MidiUnavailableException midiUnavailableException) {}
/*     */         }
/*     */ 
/*     */         
/* 197 */         this.sequencer.addMetaEventListener(this);
/*     */         try {
/* 199 */           this.sequencer.start();
/* 200 */         } catch (Exception exception) {}
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 205 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void stop() {
/* 213 */     this.lastPlayCall = 0L;
/*     */     
/* 215 */     if (this.clip != null) {
/*     */       
/*     */       try {
/* 218 */         this.clip.flush();
/* 219 */       } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 224 */         this.clip.stop();
/* 225 */       } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 230 */     else if (this.datapusher != null) {
/* 231 */       this.datapusher.stop();
/*     */     
/*     */     }
/* 234 */     else if (this.sequencer != null) {
/*     */       try {
/* 236 */         this.sequencerloop = false;
/* 237 */         this.sequencer.addMetaEventListener(this);
/* 238 */         this.sequencer.stop();
/* 239 */       } catch (Exception exception) {}
/*     */ 
/*     */       
/*     */       try {
/* 243 */         this.sequencer.close();
/* 244 */       } catch (Exception exception) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void update(LineEvent paramLineEvent) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void meta(MetaMessage paramMetaMessage) {
/* 263 */     if (paramMetaMessage.getType() == 47) {
/* 264 */       if (this.sequencerloop) {
/*     */         
/* 266 */         this.sequencer.setMicrosecondPosition(0L);
/* 267 */         loop();
/*     */       } else {
/* 269 */         stop();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 276 */     return getClass().toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() {
/* 282 */     if (this.clip != null)
/*     */     {
/* 284 */       this.clip.close();
/*     */     }
/*     */ 
/*     */     
/* 288 */     if (this.datapusher != null) {
/* 289 */       this.datapusher.close();
/*     */     }
/*     */     
/* 292 */     if (this.sequencer != null) {
/* 293 */       this.sequencer.close();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean loadAudioData(AudioInputStream paramAudioInputStream) throws IOException, UnsupportedAudioFileException {
/* 303 */     paramAudioInputStream = Toolkit.getPCMConvertedAudioInputStream(paramAudioInputStream);
/* 304 */     if (paramAudioInputStream == null) {
/* 305 */       return false;
/*     */     }
/*     */     
/* 308 */     this.loadedAudioFormat = paramAudioInputStream.getFormat();
/* 309 */     long l1 = paramAudioInputStream.getFrameLength();
/* 310 */     int i = this.loadedAudioFormat.getFrameSize();
/* 311 */     long l2 = -1L;
/* 312 */     if (l1 != -1L && l1 > 0L && i != -1 && i > 0)
/*     */     {
/*     */ 
/*     */       
/* 316 */       l2 = l1 * i;
/*     */     }
/* 318 */     if (l2 != -1L) {
/*     */       
/* 320 */       readStream(paramAudioInputStream, l2);
/*     */     } else {
/*     */       
/* 323 */       readStream(paramAudioInputStream);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 328 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readStream(AudioInputStream paramAudioInputStream, long paramLong) throws IOException {
/*     */     int i;
/* 336 */     if (paramLong > 2147483647L) {
/* 337 */       i = Integer.MAX_VALUE;
/*     */     } else {
/* 339 */       i = (int)paramLong;
/*     */     } 
/* 341 */     this.loadedAudio = new byte[i];
/* 342 */     this.loadedAudioByteLength = 0;
/*     */ 
/*     */     
/*     */     while (true) {
/* 346 */       int j = paramAudioInputStream.read(this.loadedAudio, this.loadedAudioByteLength, i - this.loadedAudioByteLength);
/* 347 */       if (j <= 0) {
/* 348 */         paramAudioInputStream.close();
/*     */         break;
/*     */       } 
/* 351 */       this.loadedAudioByteLength += j;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void readStream(AudioInputStream paramAudioInputStream) throws IOException {
/* 357 */     DirectBAOS directBAOS = new DirectBAOS();
/* 358 */     byte[] arrayOfByte = new byte[16384];
/* 359 */     int i = 0;
/* 360 */     int j = 0;
/*     */ 
/*     */     
/*     */     while (true) {
/* 364 */       i = paramAudioInputStream.read(arrayOfByte, 0, arrayOfByte.length);
/* 365 */       if (i <= 0) {
/* 366 */         paramAudioInputStream.close();
/*     */         break;
/*     */       } 
/* 369 */       j += i;
/* 370 */       directBAOS.write(arrayOfByte, 0, i);
/*     */     } 
/* 372 */     this.loadedAudio = directBAOS.getInternalBuffer();
/* 373 */     this.loadedAudioByteLength = j;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean createClip() {
/*     */     try {
/* 384 */       DataLine.Info info = new DataLine.Info(Clip.class, this.loadedAudioFormat);
/* 385 */       if (!AudioSystem.isLineSupported(info))
/*     */       {
/*     */         
/* 388 */         return false;
/*     */       }
/* 390 */       Line line = AudioSystem.getLine(info);
/* 391 */       if (!(line instanceof AutoClosingClip))
/*     */       {
/*     */         
/* 394 */         return false;
/*     */       }
/* 396 */       this.clip = (AutoClosingClip)line;
/* 397 */       this.clip.setAutoClosing(true);
/*     */     }
/* 399 */     catch (Exception exception) {
/*     */ 
/*     */       
/* 402 */       return false;
/*     */     } 
/*     */     
/* 405 */     if (this.clip == null)
/*     */     {
/* 407 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 411 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean createSourceDataLine() {
/*     */     try {
/* 417 */       DataLine.Info info = new DataLine.Info(SourceDataLine.class, this.loadedAudioFormat);
/* 418 */       if (!AudioSystem.isLineSupported(info))
/*     */       {
/*     */         
/* 421 */         return false;
/*     */       }
/* 423 */       SourceDataLine sourceDataLine = (SourceDataLine)AudioSystem.getLine(info);
/* 424 */       this.datapusher = new DataPusher(sourceDataLine, this.loadedAudioFormat, this.loadedAudio, this.loadedAudioByteLength);
/* 425 */     } catch (Exception exception) {
/*     */ 
/*     */       
/* 428 */       return false;
/*     */     } 
/*     */     
/* 431 */     if (this.datapusher == null)
/*     */     {
/* 433 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 437 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean createSequencer(BufferedInputStream paramBufferedInputStream) throws IOException {
/*     */     try {
/* 446 */       this.sequencer = MidiSystem.getSequencer();
/* 447 */     } catch (MidiUnavailableException midiUnavailableException) {
/*     */       
/* 449 */       return false;
/*     */     } 
/* 451 */     if (this.sequencer == null) {
/* 452 */       return false;
/*     */     }
/*     */     
/*     */     try {
/* 456 */       this.sequence = MidiSystem.getSequence(paramBufferedInputStream);
/* 457 */       if (this.sequence == null) {
/* 458 */         return false;
/*     */       }
/* 460 */     } catch (InvalidMidiDataException invalidMidiDataException) {
/*     */       
/* 462 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 466 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class DirectBAOS
/*     */     extends ByteArrayOutputStream
/*     */   {
/*     */     public byte[] getInternalBuffer() {
/* 480 */       return this.buf;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/JavaSoundAudioClip.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */