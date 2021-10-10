/*    */ package com.sun.org.apache.xerces.internal.impl.xs.util;
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
/*    */ public final class XIntPool
/*    */ {
/*    */   private static final short POOL_SIZE = 10;
/* 30 */   private static final XInt[] fXIntPool = new XInt[10];
/*    */   
/*    */   static {
/* 33 */     for (int i = 0; i < 10; i++)
/* 34 */       fXIntPool[i] = new XInt(i); 
/*    */   }
/*    */   
/*    */   public final XInt getXInt(int value) {
/* 38 */     if (value >= 0 && value < fXIntPool.length) {
/* 39 */       return fXIntPool[value];
/*    */     }
/* 41 */     return new XInt(value);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/xs/util/XIntPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */