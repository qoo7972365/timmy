/*    */ package com.sun.media.sound;
/*    */ 
/*    */ import javax.sound.midi.InvalidMidiDataException;
/*    */ import javax.sound.midi.SysexMessage;
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
/*    */ final class FastSysexMessage
/*    */   extends SysexMessage
/*    */ {
/*    */   FastSysexMessage(byte[] paramArrayOfbyte) throws InvalidMidiDataException {
/* 38 */     super(paramArrayOfbyte);
/* 39 */     if (paramArrayOfbyte.length == 0 || ((paramArrayOfbyte[0] & 0xFF) != 240 && (paramArrayOfbyte[0] & 0xFF) != 247)) {
/* 40 */       super.setMessage(paramArrayOfbyte, paramArrayOfbyte.length);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   byte[] getReadOnlyMessage() {
/* 49 */     return this.data;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setMessage(byte[] paramArrayOfbyte, int paramInt) throws InvalidMidiDataException {
/* 55 */     if (paramArrayOfbyte.length == 0 || ((paramArrayOfbyte[0] & 0xFF) != 240 && (paramArrayOfbyte[0] & 0xFF) != 247)) {
/* 56 */       super.setMessage(paramArrayOfbyte, paramArrayOfbyte.length);
/*    */     }
/* 58 */     this.length = paramInt;
/* 59 */     this.data = new byte[this.length];
/* 60 */     System.arraycopy(paramArrayOfbyte, 0, this.data, 0, paramInt);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/FastSysexMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */