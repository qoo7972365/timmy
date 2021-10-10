/*    */ package com.sun.org.apache.xerces.internal.dom;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
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
/*    */ class LCount
/*    */ {
/* 40 */   static final Map<String, LCount> lCounts = new ConcurrentHashMap<>();
/* 41 */   public int captures = 0, bubbles = 0, defaults, total = 0;
/*    */ 
/*    */   
/*    */   static LCount lookup(String evtName) {
/* 45 */     LCount lc = lCounts.get(evtName);
/* 46 */     if (lc == null)
/* 47 */       lCounts.put(evtName, lc = new LCount()); 
/* 48 */     return lc;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/dom/LCount.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */