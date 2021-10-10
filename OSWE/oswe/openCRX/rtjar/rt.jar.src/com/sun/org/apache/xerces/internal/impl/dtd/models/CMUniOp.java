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
/*     */ public class CMUniOp
/*     */   extends CMNode
/*     */ {
/*     */   private CMNode fChild;
/*     */   
/*     */   public CMUniOp(int type, CMNode childNode) {
/*  79 */     super(type);
/*     */ 
/*     */     
/*  82 */     if (type() != 1 && 
/*  83 */       type() != 2 && 
/*  84 */       type() != 3)
/*     */     {
/*  86 */       throw new RuntimeException("ImplementationMessages.VAL_UST");
/*     */     }
/*     */ 
/*     */     
/*  90 */     this.fChild = childNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final CMNode getChild() {
/*  99 */     return this.fChild;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNullable() {
/* 112 */     if (type() == 3) {
/* 113 */       return this.fChild.isNullable();
/*     */     }
/* 115 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void calcFirstPos(CMStateSet toSet) {
/* 125 */     toSet.setTo(this.fChild.firstPos());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void calcLastPos(CMStateSet toSet) {
/* 131 */     toSet.setTo(this.fChild.lastPos());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/dtd/models/CMUniOp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */