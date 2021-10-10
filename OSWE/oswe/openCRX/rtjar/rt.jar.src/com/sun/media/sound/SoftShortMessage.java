/*    */ package com.sun.media.sound;
/*    */ 
/*    */ import javax.sound.midi.InvalidMidiDataException;
/*    */ import javax.sound.midi.ShortMessage;
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
/*    */ public final class SoftShortMessage
/*    */   extends ShortMessage
/*    */ {
/* 37 */   int channel = 0;
/*    */   
/*    */   public int getChannel() {
/* 40 */     return this.channel;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setMessage(int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws InvalidMidiDataException {
/* 45 */     this.channel = paramInt2;
/* 46 */     super.setMessage(paramInt1, paramInt2 & 0xF, paramInt3, paramInt4);
/*    */   }
/*    */   
/*    */   public Object clone() {
/* 50 */     SoftShortMessage softShortMessage = new SoftShortMessage();
/*    */     try {
/* 52 */       softShortMessage.setMessage(getCommand(), getChannel(), getData1(), getData2());
/* 53 */     } catch (InvalidMidiDataException invalidMidiDataException) {
/* 54 */       throw new IllegalArgumentException(invalidMidiDataException);
/*    */     } 
/* 56 */     return softShortMessage;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SoftShortMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */