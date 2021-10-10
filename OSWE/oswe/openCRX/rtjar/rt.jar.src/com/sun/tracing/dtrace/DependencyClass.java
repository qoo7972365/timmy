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
/*    */ public enum DependencyClass
/*    */ {
/*    */   private int encoding;
/* 38 */   UNKNOWN(0),
/*    */ 
/*    */ 
/*    */   
/* 42 */   CPU(1),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 47 */   PLATFORM(2),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 52 */   GROUP(3),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 57 */   ISA(4),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 62 */   COMMON(5);
/*    */   
/*    */   public String toDisplayString() {
/* 65 */     return toString().substring(0, 1) + 
/* 66 */       toString().substring(1).toLowerCase();
/*    */   }
/*    */   public int getEncoding() {
/* 69 */     return this.encoding;
/*    */   }
/*    */ 
/*    */   
/*    */   DependencyClass(int paramInt1) {
/* 74 */     this.encoding = paramInt1;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/tracing/dtrace/DependencyClass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */