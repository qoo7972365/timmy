/*     */ package com.sun.org.apache.xerces.internal.impl.xs.models;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.dtd.models.CMNode;
/*     */ import com.sun.org.apache.xerces.internal.impl.dtd.models.CMStateSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XSCMUniOp
/*     */   extends CMNode
/*     */ {
/*     */   private CMNode fChild;
/*     */   
/*     */   public XSCMUniOp(int type, CMNode childNode) {
/*  41 */     super(type);
/*     */ 
/*     */     
/*  44 */     if (type() != 5 && 
/*  45 */       type() != 4 && 
/*  46 */       type() != 6) {
/*  47 */       throw new RuntimeException("ImplementationMessages.VAL_UST");
/*     */     }
/*     */ 
/*     */     
/*  51 */     this.fChild = childNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final CMNode getChild() {
/*  59 */     return this.fChild;
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
/*     */   public boolean isNullable() {
/*  71 */     if (type() == 6) {
/*  72 */       return this.fChild.isNullable();
/*     */     }
/*  74 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void calcFirstPos(CMStateSet toSet) {
/*  83 */     toSet.setTo(this.fChild.firstPos());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void calcLastPos(CMStateSet toSet) {
/*  88 */     toSet.setTo(this.fChild.lastPos());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUserData(Object userData) {
/*  99 */     super.setUserData(userData);
/* 100 */     this.fChild.setUserData(userData);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/xs/models/XSCMUniOp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */