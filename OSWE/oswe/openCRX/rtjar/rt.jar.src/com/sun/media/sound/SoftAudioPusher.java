/*    */ package com.sun.media.sound;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.sound.sampled.AudioInputStream;
/*    */ import javax.sound.sampled.SourceDataLine;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SoftAudioPusher
/*    */   implements Runnable
/*    */ {
/*    */   private volatile boolean active = false;
/* 40 */   private SourceDataLine sourceDataLine = null;
/*    */   
/*    */   private Thread audiothread;
/*    */   private final AudioInputStream ais;
/*    */   private final byte[] buffer;
/*    */   
/*    */   public SoftAudioPusher(SourceDataLine paramSourceDataLine, AudioInputStream paramAudioInputStream, int paramInt) {
/* 47 */     this.ais = paramAudioInputStream;
/* 48 */     this.buffer = new byte[paramInt];
/* 49 */     this.sourceDataLine = paramSourceDataLine;
/*    */   }
/*    */   
/*    */   public synchronized void start() {
/* 53 */     if (this.active)
/*    */       return; 
/* 55 */     this.active = true;
/* 56 */     this.audiothread = new Thread(this);
/* 57 */     this.audiothread.setDaemon(true);
/* 58 */     this.audiothread.setPriority(10);
/* 59 */     this.audiothread.start();
/*    */   }
/*    */   
/*    */   public synchronized void stop() {
/* 63 */     if (!this.active)
/*    */       return; 
/* 65 */     this.active = false;
/*    */     try {
/* 67 */       this.audiothread.join();
/* 68 */     } catch (InterruptedException interruptedException) {}
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/* 74 */     byte[] arrayOfByte = this.buffer;
/* 75 */     AudioInputStream audioInputStream = this.ais;
/* 76 */     SourceDataLine sourceDataLine = this.sourceDataLine;
/*    */     
/*    */     try {
/* 79 */       while (this.active) {
/*    */         
/* 81 */         int i = audioInputStream.read(arrayOfByte);
/* 82 */         if (i < 0)
/*    */           break; 
/* 84 */         sourceDataLine.write(arrayOfByte, 0, i);
/*    */       } 
/* 86 */     } catch (IOException iOException) {
/* 87 */       this.active = false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SoftAudioPusher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */