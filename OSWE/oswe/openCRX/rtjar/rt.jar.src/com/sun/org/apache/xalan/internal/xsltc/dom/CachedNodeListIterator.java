/*     */ package com.sun.org.apache.xalan.internal.xsltc.dom;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;
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
/*     */ public final class CachedNodeListIterator
/*     */   extends DTMAxisIteratorBase
/*     */ {
/*     */   private DTMAxisIterator _source;
/*  42 */   private IntegerArray _nodes = new IntegerArray();
/*  43 */   private int _numCachedNodes = 0;
/*  44 */   private int _index = 0;
/*     */   private boolean _isEnded = false;
/*     */   
/*     */   public CachedNodeListIterator(DTMAxisIterator source) {
/*  48 */     this._source = source;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRestartable(boolean isRestartable) {}
/*     */ 
/*     */   
/*     */   public DTMAxisIterator setStartNode(int node) {
/*  57 */     if (this._isRestartable) {
/*  58 */       this._startNode = node;
/*  59 */       this._source.setStartNode(node);
/*  60 */       resetPosition();
/*     */       
/*  62 */       this._isRestartable = false;
/*     */     } 
/*  64 */     return this;
/*     */   }
/*     */   
/*     */   public int next() {
/*  68 */     return getNode(this._index++);
/*     */   }
/*     */   
/*     */   public int getPosition() {
/*  72 */     return (this._index == 0) ? 1 : this._index;
/*     */   }
/*     */   
/*     */   public int getNodeByPosition(int pos) {
/*  76 */     return getNode(pos);
/*     */   }
/*     */   
/*     */   public int getNode(int index) {
/*  80 */     if (index < this._numCachedNodes) {
/*  81 */       return this._nodes.at(index);
/*     */     }
/*  83 */     if (!this._isEnded) {
/*  84 */       int node = this._source.next();
/*  85 */       if (node != -1) {
/*  86 */         this._nodes.add(node);
/*  87 */         this._numCachedNodes++;
/*     */       } else {
/*     */         
/*  90 */         this._isEnded = true;
/*     */       } 
/*  92 */       return node;
/*     */     } 
/*     */     
/*  95 */     return -1;
/*     */   }
/*     */   
/*     */   public DTMAxisIterator cloneIterator() {
/*  99 */     ClonedNodeListIterator clone = new ClonedNodeListIterator(this);
/* 100 */     return clone;
/*     */   }
/*     */   
/*     */   public DTMAxisIterator reset() {
/* 104 */     this._index = 0;
/* 105 */     return this;
/*     */   }
/*     */   
/*     */   public void setMark() {
/* 109 */     this._source.setMark();
/*     */   }
/*     */   
/*     */   public void gotoMark() {
/* 113 */     this._source.gotoMark();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/dom/CachedNodeListIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */