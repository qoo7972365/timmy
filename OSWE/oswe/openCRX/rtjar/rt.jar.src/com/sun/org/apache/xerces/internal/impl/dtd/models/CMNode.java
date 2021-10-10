/*     */ package com.sun.org.apache.xerces.internal.impl.dtd.models;
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
/*     */ public abstract class CMNode
/*     */ {
/*     */   private int fType;
/*     */   private CMStateSet fFirstPos;
/*     */   private CMStateSet fFollowPos;
/*     */   private CMStateSet fLastPos;
/*     */   private int fMaxStates;
/*     */   private Object fUserData;
/*     */   
/*     */   public CMNode(int type) {
/* 185 */     this.fFirstPos = null;
/* 186 */     this.fFollowPos = null;
/* 187 */     this.fLastPos = null;
/* 188 */     this.fMaxStates = -1;
/* 189 */     this.fUserData = null;
/*     */     this.fType = type;
/*     */   }
/*     */   
/*     */   public abstract boolean isNullable();
/*     */   
/*     */   public final int type() {
/*     */     return this.fType;
/*     */   }
/*     */   
/*     */   public final CMStateSet firstPos() {
/*     */     if (this.fFirstPos == null) {
/*     */       this.fFirstPos = new CMStateSet(this.fMaxStates);
/*     */       calcFirstPos(this.fFirstPos);
/*     */     } 
/*     */     return this.fFirstPos;
/*     */   }
/*     */   
/*     */   public final CMStateSet lastPos() {
/*     */     if (this.fLastPos == null) {
/*     */       this.fLastPos = new CMStateSet(this.fMaxStates);
/*     */       calcLastPos(this.fLastPos);
/*     */     } 
/*     */     return this.fLastPos;
/*     */   }
/*     */   
/*     */   final void setFollowPos(CMStateSet setToAdopt) {
/*     */     this.fFollowPos = setToAdopt;
/*     */   }
/*     */   
/*     */   public final void setMaxStates(int maxStates) {
/*     */     this.fMaxStates = maxStates;
/*     */   }
/*     */   
/*     */   public void setUserData(Object userData) {
/*     */     this.fUserData = userData;
/*     */   }
/*     */   
/*     */   public Object getUserData() {
/*     */     return this.fUserData;
/*     */   }
/*     */   
/*     */   protected abstract void calcFirstPos(CMStateSet paramCMStateSet);
/*     */   
/*     */   protected abstract void calcLastPos(CMStateSet paramCMStateSet);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/dtd/models/CMNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */