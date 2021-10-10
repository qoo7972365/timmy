/*    */ package com.sun.media.sound;
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
/*    */ public final class DLSSampleLoop
/*    */ {
/*    */   public static final int LOOP_TYPE_FORWARD = 0;
/*    */   public static final int LOOP_TYPE_RELEASE = 1;
/*    */   long type;
/*    */   long start;
/*    */   long length;
/*    */   
/*    */   public long getLength() {
/* 41 */     return this.length;
/*    */   }
/*    */   
/*    */   public void setLength(long paramLong) {
/* 45 */     this.length = paramLong;
/*    */   }
/*    */   
/*    */   public long getStart() {
/* 49 */     return this.start;
/*    */   }
/*    */   
/*    */   public void setStart(long paramLong) {
/* 53 */     this.start = paramLong;
/*    */   }
/*    */   
/*    */   public long getType() {
/* 57 */     return this.type;
/*    */   }
/*    */   
/*    */   public void setType(long paramLong) {
/* 61 */     this.type = paramLong;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/DLSSampleLoop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */