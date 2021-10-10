/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import javax.sound.sampled.AudioFormat;
/*     */ import javax.sound.sampled.AudioInputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DataPusher
/*     */   implements Runnable
/*     */ {
/*     */   private static final int AUTO_CLOSE_TIME = 5000;
/*     */   private static final boolean DEBUG = false;
/*     */   private final SourceDataLine source;
/*     */   private final AudioFormat format;
/*     */   private final AudioInputStream ais;
/*     */   private final byte[] audioData;
/*     */   private final int audioDataByteLength;
/*     */   private int pos;
/*  57 */   private int newPos = -1;
/*     */   
/*     */   private boolean looping;
/*  60 */   private Thread pushThread = null;
/*     */   
/*     */   private int wantedState;
/*     */   private int threadState;
/*  64 */   private final int STATE_NONE = 0;
/*  65 */   private final int STATE_PLAYING = 1;
/*  66 */   private final int STATE_WAITING = 2;
/*  67 */   private final int STATE_STOPPING = 3;
/*  68 */   private final int STATE_STOPPED = 4;
/*  69 */   private final int BUFFER_SIZE = 16384;
/*     */   
/*     */   public DataPusher(SourceDataLine paramSourceDataLine, AudioFormat paramAudioFormat, byte[] paramArrayOfbyte, int paramInt) {
/*  72 */     this(paramSourceDataLine, paramAudioFormat, null, paramArrayOfbyte, paramInt);
/*     */   }
/*     */   
/*     */   public DataPusher(SourceDataLine paramSourceDataLine, AudioInputStream paramAudioInputStream) {
/*  76 */     this(paramSourceDataLine, paramAudioInputStream.getFormat(), paramAudioInputStream, null, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private DataPusher(SourceDataLine paramSourceDataLine, AudioFormat paramAudioFormat, AudioInputStream paramAudioInputStream, byte[] paramArrayOfbyte, int paramInt) {
/*  82 */     this.source = paramSourceDataLine;
/*  83 */     this.format = paramAudioFormat;
/*  84 */     this.ais = paramAudioInputStream;
/*  85 */     this.audioDataByteLength = paramInt;
/*  86 */     this.audioData = (paramArrayOfbyte == null) ? null : Arrays.copyOf(paramArrayOfbyte, paramArrayOfbyte.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void start() {
/*  91 */     start(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void start(boolean paramBoolean) {
/*     */     try {
/*  97 */       if (this.threadState == 3)
/*     */       {
/*     */         
/* 100 */         stop();
/*     */       }
/* 102 */       this.looping = paramBoolean;
/* 103 */       this.newPos = 0;
/* 104 */       this.wantedState = 1;
/* 105 */       if (!this.source.isOpen())
/*     */       {
/* 107 */         this.source.open(this.format);
/*     */       }
/*     */       
/* 110 */       this.source.flush();
/*     */       
/* 112 */       this.source.start();
/* 113 */       if (this.pushThread == null)
/*     */       {
/* 115 */         this.pushThread = JSSecurityManager.createThread(this, null, false, -1, true);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 121 */       notifyAll();
/* 122 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void stop() {
/* 131 */     if (this.threadState == 3 || this.threadState == 4 || this.pushThread == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     this.wantedState = 2;
/* 140 */     if (this.source != null)
/*     */     {
/* 142 */       this.source.flush();
/*     */     }
/* 144 */     notifyAll();
/* 145 */     byte b = 50;
/* 146 */     while (b-- >= 0 && this.threadState == 1) {
/*     */       try {
/* 148 */         wait(100L);
/* 149 */       } catch (InterruptedException interruptedException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   synchronized void close() {
/* 155 */     if (this.source != null)
/*     */     {
/* 157 */       this.source.close();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/* 165 */     byte[] arrayOfByte = null;
/* 166 */     boolean bool = (this.ais != null) ? true : false;
/* 167 */     if (bool) {
/* 168 */       arrayOfByte = new byte[16384];
/*     */     } else {
/* 170 */       arrayOfByte = this.audioData;
/*     */     } 
/* 172 */     while (this.wantedState != 3) {
/*     */       
/* 174 */       if (this.wantedState == 2) {
/*     */ 
/*     */         
/*     */         try {
/* 178 */           synchronized (this) {
/* 179 */             this.threadState = 2;
/* 180 */             this.wantedState = 3;
/* 181 */             wait(5000L);
/*     */           } 
/* 183 */         } catch (InterruptedException interruptedException) {}
/*     */         
/*     */         continue;
/*     */       } 
/* 187 */       if (this.newPos >= 0) {
/* 188 */         this.pos = this.newPos;
/* 189 */         this.newPos = -1;
/*     */       } 
/* 191 */       this.threadState = 1;
/* 192 */       int i = 16384;
/* 193 */       if (bool) {
/*     */         try {
/* 195 */           this.pos = 0;
/*     */ 
/*     */           
/* 198 */           i = this.ais.read(arrayOfByte, 0, arrayOfByte.length);
/* 199 */         } catch (IOException iOException) {
/*     */           
/* 201 */           i = -1;
/*     */         } 
/*     */       } else {
/* 204 */         if (i > this.audioDataByteLength - this.pos) {
/* 205 */           i = this.audioDataByteLength - this.pos;
/*     */         }
/* 207 */         if (i == 0) {
/* 208 */           i = -1;
/*     */         }
/*     */       } 
/* 211 */       if (i < 0) {
/*     */         
/* 213 */         if (!bool && this.looping) {
/*     */           
/* 215 */           this.pos = 0;
/*     */           
/*     */           continue;
/*     */         } 
/* 219 */         this.wantedState = 2;
/* 220 */         this.source.drain();
/*     */         
/*     */         continue;
/*     */       } 
/* 224 */       int j = this.source.write(arrayOfByte, this.pos, i);
/* 225 */       this.pos += j;
/*     */     } 
/*     */     
/* 228 */     this.threadState = 3;
/*     */ 
/*     */     
/* 231 */     this.source.flush();
/*     */     
/* 233 */     this.source.stop();
/*     */     
/* 235 */     this.source.flush();
/*     */     
/* 237 */     this.source.close();
/* 238 */     this.threadState = 4;
/* 239 */     synchronized (this) {
/* 240 */       this.pushThread = null;
/* 241 */       notifyAll();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/DataPusher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */