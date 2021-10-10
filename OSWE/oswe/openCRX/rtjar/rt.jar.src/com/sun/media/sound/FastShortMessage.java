/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import javax.sound.midi.InvalidMidiDataException;
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
/*     */ final class FastShortMessage
/*     */   extends ShortMessage
/*     */ {
/*     */   private int packedMsg;
/*     */   
/*     */   FastShortMessage(int paramInt) throws InvalidMidiDataException {
/*  39 */     this.packedMsg = paramInt;
/*  40 */     getDataLength(paramInt & 0xFF);
/*     */   }
/*     */ 
/*     */   
/*     */   FastShortMessage(ShortMessage paramShortMessage) {
/*  45 */     this
/*     */       
/*  47 */       .packedMsg = paramShortMessage.getStatus() | paramShortMessage.getData1() << 8 | paramShortMessage.getData2() << 16;
/*     */   }
/*     */   
/*     */   int getPackedMsg() {
/*  51 */     return this.packedMsg;
/*     */   }
/*     */   
/*     */   public byte[] getMessage() {
/*  55 */     int i = 0;
/*     */ 
/*     */     
/*     */     try {
/*  59 */       i = getDataLength(this.packedMsg & 0xFF) + 1;
/*  60 */     } catch (InvalidMidiDataException invalidMidiDataException) {}
/*     */ 
/*     */     
/*  63 */     byte[] arrayOfByte = new byte[i];
/*  64 */     if (i > 0) {
/*  65 */       arrayOfByte[0] = (byte)(this.packedMsg & 0xFF);
/*  66 */       if (i > 1) {
/*  67 */         arrayOfByte[1] = (byte)((this.packedMsg & 0xFF00) >> 8);
/*  68 */         if (i > 2) {
/*  69 */           arrayOfByte[2] = (byte)((this.packedMsg & 0xFF0000) >> 16);
/*     */         }
/*     */       } 
/*     */     } 
/*  73 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   public int getLength() {
/*     */     try {
/*  78 */       return getDataLength(this.packedMsg & 0xFF) + 1;
/*  79 */     } catch (InvalidMidiDataException invalidMidiDataException) {
/*     */ 
/*     */       
/*  82 */       return 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setMessage(int paramInt) throws InvalidMidiDataException {
/*  87 */     int i = getDataLength(paramInt);
/*  88 */     if (i != 0) {
/*  89 */       super.setMessage(paramInt);
/*     */     }
/*  91 */     this.packedMsg = this.packedMsg & 0xFFFF00 | paramInt & 0xFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMessage(int paramInt1, int paramInt2, int paramInt3) throws InvalidMidiDataException {
/*  96 */     getDataLength(paramInt1);
/*  97 */     this.packedMsg = paramInt1 & 0xFF | (paramInt2 & 0xFF) << 8 | (paramInt3 & 0xFF) << 16;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMessage(int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws InvalidMidiDataException {
/* 102 */     getDataLength(paramInt1);
/* 103 */     this.packedMsg = paramInt1 & 0xF0 | paramInt2 & 0xF | (paramInt3 & 0xFF) << 8 | (paramInt4 & 0xFF) << 16;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getChannel() {
/* 108 */     return this.packedMsg & 0xF;
/*     */   }
/*     */   
/*     */   public int getCommand() {
/* 112 */     return this.packedMsg & 0xF0;
/*     */   }
/*     */   
/*     */   public int getData1() {
/* 116 */     return (this.packedMsg & 0xFF00) >> 8;
/*     */   }
/*     */   
/*     */   public int getData2() {
/* 120 */     return (this.packedMsg & 0xFF0000) >> 16;
/*     */   }
/*     */   
/*     */   public int getStatus() {
/* 124 */     return this.packedMsg & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 134 */       return new FastShortMessage(this.packedMsg);
/* 135 */     } catch (InvalidMidiDataException invalidMidiDataException) {
/*     */ 
/*     */       
/* 138 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/FastShortMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */