/*     */ package com.sun.org.apache.xalan.internal.xsltc.dom;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;
/*     */ import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
/*     */ import com.sun.org.apache.xml.internal.dtm.ref.DTMAxisIteratorBase;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ForwardPositionIterator
/*     */   extends DTMAxisIteratorBase
/*     */ {
/*     */   private DTMAxisIterator _source;
/*     */   
/*     */   public ForwardPositionIterator(DTMAxisIterator source) {
/*  70 */     this._source = source;
/*     */   }
/*     */ 
/*     */   
/*     */   public DTMAxisIterator cloneIterator() {
/*     */     try {
/*  76 */       ForwardPositionIterator clone = (ForwardPositionIterator)clone();
/*  77 */       clone._source = this._source.cloneIterator();
/*  78 */       clone._isRestartable = false;
/*  79 */       return clone.reset();
/*     */     }
/*  81 */     catch (CloneNotSupportedException e) {
/*  82 */       BasisLibrary.runTimeError("ITERATOR_CLONE_ERR", e
/*  83 */           .toString());
/*  84 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int next() {
/*  89 */     return returnNode(this._source.next());
/*     */   }
/*     */   
/*     */   public DTMAxisIterator setStartNode(int node) {
/*  93 */     this._source.setStartNode(node);
/*  94 */     return this;
/*     */   }
/*     */   
/*     */   public DTMAxisIterator reset() {
/*  98 */     this._source.reset();
/*  99 */     return resetPosition();
/*     */   }
/*     */   
/*     */   public void setMark() {
/* 103 */     this._source.setMark();
/*     */   }
/*     */   
/*     */   public void gotoMark() {
/* 107 */     this._source.gotoMark();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/dom/ForwardPositionIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */