/*    */ package com.sun.org.apache.xml.internal.dtm.ref;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
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
/*    */ public final class EmptyIterator
/*    */   implements DTMAxisIterator
/*    */ {
/* 35 */   private static final EmptyIterator INSTANCE = new EmptyIterator();
/*    */   public static DTMAxisIterator getInstance() {
/* 37 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   public final int next() {
/* 41 */     return -1;
/*    */   } public final DTMAxisIterator reset() {
/* 43 */     return this;
/*    */   } public final int getLast() {
/* 45 */     return 0;
/*    */   } public final int getPosition() {
/* 47 */     return 1;
/*    */   }
/*    */   public final void setMark() {}
/*    */   public final void gotoMark() {}
/*    */   
/*    */   public final DTMAxisIterator setStartNode(int node) {
/* 53 */     return this;
/*    */   } public final int getStartNode() {
/* 55 */     return -1;
/*    */   } public final boolean isReverse() {
/* 57 */     return false;
/*    */   } public final DTMAxisIterator cloneIterator() {
/* 59 */     return this;
/*    */   }
/*    */   public final void setRestartable(boolean isRestartable) {}
/*    */   public final int getNodeByPosition(int position) {
/* 63 */     return -1;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/dtm/ref/EmptyIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */