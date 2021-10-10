/*    */ package com.sun.tracing.dtrace;
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
/*    */ public enum StabilityLevel
/*    */ {
/*    */   private int encoding;
/* 39 */   INTERNAL(0),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 44 */   PRIVATE(1),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 49 */   OBSOLETE(2),
/*    */ 
/*    */ 
/*    */   
/* 53 */   EXTERNAL(3),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 60 */   UNSTABLE(4),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 65 */   EVOLVING(5),
/*    */ 
/*    */ 
/*    */   
/* 69 */   STABLE(6),
/*    */ 
/*    */ 
/*    */   
/* 73 */   STANDARD(7);
/*    */   
/*    */   String toDisplayString() {
/* 76 */     return toString().substring(0, 1) + 
/* 77 */       toString().substring(1).toLowerCase();
/*    */   }
/*    */   public int getEncoding() {
/* 80 */     return this.encoding;
/*    */   }
/*    */ 
/*    */   
/*    */   StabilityLevel(int paramInt1) {
/* 85 */     this.encoding = paramInt1;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/tracing/dtrace/StabilityLevel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */