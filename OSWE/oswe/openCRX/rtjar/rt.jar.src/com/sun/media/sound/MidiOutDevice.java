/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import javax.sound.midi.MidiMessage;
/*     */ import javax.sound.midi.MidiUnavailableException;
/*     */ import javax.sound.midi.Receiver;
/*     */ import javax.sound.midi.ShortMessage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class MidiOutDevice
/*     */   extends AbstractMidiDevice
/*     */ {
/*     */   MidiOutDevice(AbstractMidiDeviceProvider.Info paramInfo) {
/*  44 */     super(paramInfo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized void implOpen() throws MidiUnavailableException {
/*  53 */     int i = ((AbstractMidiDeviceProvider.Info)getDeviceInfo()).getIndex();
/*  54 */     this.id = nOpen(i);
/*  55 */     if (this.id == 0L) {
/*  56 */       throw new MidiUnavailableException("Unable to open native device");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized void implClose() {
/*  65 */     long l = this.id;
/*  66 */     this.id = 0L;
/*     */     
/*  68 */     super.implClose();
/*     */ 
/*     */     
/*  71 */     nClose(l);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMicrosecondPosition() {
/*  77 */     long l = -1L;
/*  78 */     if (isOpen()) {
/*  79 */       l = nGetTimeStamp(this.id);
/*     */     }
/*  81 */     return l;
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
/*     */   protected boolean hasReceivers() {
/*  93 */     return true;
/*     */   }
/*     */   private native long nOpen(int paramInt) throws MidiUnavailableException;
/*     */   
/*     */   protected Receiver createReceiver() {
/*  98 */     return new MidiOutReceiver();
/*     */   }
/*     */   private native void nClose(long paramLong);
/*     */   private native void nSendShortMessage(long paramLong1, int paramInt, long paramLong2);
/*     */   private native void nSendLongMessage(long paramLong1, byte[] paramArrayOfbyte, int paramInt, long paramLong2);
/*     */   
/*     */   private native long nGetTimeStamp(long paramLong);
/*     */   
/*     */   final class MidiOutReceiver extends AbstractMidiDevice.AbstractReceiver { void implSend(MidiMessage param1MidiMessage, long param1Long) {
/* 107 */       int i = param1MidiMessage.getLength();
/* 108 */       int j = param1MidiMessage.getStatus();
/* 109 */       if (i <= 3 && j != 240 && j != 247) {
/*     */         int k;
/* 111 */         if (param1MidiMessage instanceof ShortMessage) {
/* 112 */           if (param1MidiMessage instanceof FastShortMessage) {
/* 113 */             k = ((FastShortMessage)param1MidiMessage).getPackedMsg();
/*     */           } else {
/* 115 */             ShortMessage shortMessage = (ShortMessage)param1MidiMessage;
/*     */ 
/*     */             
/* 118 */             k = j & 0xFF | (shortMessage.getData1() & 0xFF) << 8 | (shortMessage.getData2() & 0xFF) << 16;
/*     */           } 
/*     */         } else {
/* 121 */           k = 0;
/* 122 */           byte[] arrayOfByte = param1MidiMessage.getMessage();
/* 123 */           if (i > 0) {
/* 124 */             k = arrayOfByte[0] & 0xFF;
/* 125 */             if (i > 1) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 131 */               if (j == 255) {
/*     */                 return;
/*     */               }
/* 134 */               k |= (arrayOfByte[1] & 0xFF) << 8;
/* 135 */               if (i > 2) {
/* 136 */                 k |= (arrayOfByte[2] & 0xFF) << 16;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/* 141 */         MidiOutDevice.this.nSendShortMessage(MidiOutDevice.this.id, k, param1Long);
/*     */       } else {
/*     */         byte[] arrayOfByte;
/* 144 */         if (param1MidiMessage instanceof FastSysexMessage) {
/* 145 */           arrayOfByte = ((FastSysexMessage)param1MidiMessage).getReadOnlyMessage();
/*     */         } else {
/* 147 */           arrayOfByte = param1MidiMessage.getMessage();
/*     */         } 
/* 149 */         int k = Math.min(i, arrayOfByte.length);
/* 150 */         if (k > 0) {
/* 151 */           MidiOutDevice.this.nSendLongMessage(MidiOutDevice.this.id, arrayOfByte, k, param1Long);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     synchronized void sendPackedMidiMessage(int param1Int, long param1Long) {
/* 158 */       if (isOpen() && MidiOutDevice.this.id != 0L)
/* 159 */         MidiOutDevice.this.nSendShortMessage(MidiOutDevice.this.id, param1Int, param1Long); 
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/MidiOutDevice.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */