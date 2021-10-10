/*     */ package com.sun.org.apache.xalan.internal.xsltc.dom;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.xsltc.NodeIterator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;
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
/*     */ public abstract class NodeIteratorBase
/*     */   implements NodeIterator
/*     */ {
/*  39 */   protected int _last = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   protected int _position = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int _markedNode;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   protected int _startNode = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean _includeSelf = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean _isRestartable = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRestartable(boolean isRestartable) {
/*  71 */     this._isRestartable = isRestartable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract NodeIterator setStartNode(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NodeIterator reset() {
/*  86 */     boolean temp = this._isRestartable;
/*  87 */     this._isRestartable = true;
/*     */     
/*  89 */     setStartNode(this._includeSelf ? (this._startNode + 1) : this._startNode);
/*  90 */     this._isRestartable = temp;
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NodeIterator includeSelf() {
/*  98 */     this._includeSelf = true;
/*  99 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLast() {
/* 108 */     if (this._last == -1) {
/* 109 */       int temp = this._position;
/* 110 */       setMark();
/* 111 */       reset();
/*     */       while (true) {
/* 113 */         this._last++;
/* 114 */         if (next() == -1)
/* 115 */         { gotoMark();
/* 116 */           this._position = temp; break; } 
/*     */       } 
/* 118 */     }  return this._last;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPosition() {
/* 125 */     return (this._position == 0) ? 1 : this._position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReverse() {
/* 134 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NodeIterator cloneIterator() {
/*     */     try {
/* 145 */       NodeIteratorBase clone = (NodeIteratorBase)clone();
/* 146 */       clone._isRestartable = false;
/* 147 */       return clone.reset();
/*     */     }
/* 149 */     catch (CloneNotSupportedException e) {
/* 150 */       BasisLibrary.runTimeError("ITERATOR_CLONE_ERR", e
/* 151 */           .toString());
/* 152 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int returnNode(int node) {
/* 161 */     this._position++;
/* 162 */     return node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final NodeIterator resetPosition() {
/* 169 */     this._position = 0;
/* 170 */     return this;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/dom/NodeIteratorBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */