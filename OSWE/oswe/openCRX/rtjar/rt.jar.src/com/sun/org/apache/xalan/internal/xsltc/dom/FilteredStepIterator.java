/*    */ package com.sun.org.apache.xalan.internal.xsltc.dom;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class FilteredStepIterator
/*    */   extends StepIterator
/*    */ {
/*    */   private Filter _filter;
/*    */   
/*    */   public FilteredStepIterator(DTMAxisIterator source, DTMAxisIterator iterator, Filter filter) {
/* 42 */     super(source, iterator);
/* 43 */     this._filter = filter;
/*    */   }
/*    */   
/*    */   public int next() {
/*    */     int node;
/* 48 */     while ((node = super.next()) != -1) {
/* 49 */       if (this._filter.test(node)) {
/* 50 */         return returnNode(node);
/*    */       }
/*    */     } 
/* 53 */     return node;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/dom/FilteredStepIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */