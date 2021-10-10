/*    */ package com.sun.nio.file;
/*    */ 
/*    */ import java.nio.file.WatchEvent;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum SensitivityWatchEventModifier
/*    */   implements WatchEvent.Modifier
/*    */ {
/* 41 */   HIGH(2),
/*    */ 
/*    */ 
/*    */   
/* 45 */   MEDIUM(10),
/*    */ 
/*    */ 
/*    */   
/* 49 */   LOW(30);
/*    */   
/*    */   private final int sensitivity;
/*    */ 
/*    */   
/*    */   public int sensitivityValueInSeconds() {
/* 55 */     return this.sensitivity;
/*    */   }
/*    */ 
/*    */   
/*    */   SensitivityWatchEventModifier(int paramInt1) {
/* 60 */     this.sensitivity = paramInt1;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/nio/file/SensitivityWatchEventModifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */