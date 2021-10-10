/*    */ package com.sun.org.apache.xerces.internal.impl.xs.models;
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
/*    */ public final class XSCMRepeatingLeaf
/*    */   extends XSCMLeaf
/*    */ {
/*    */   private final int fMinOccurs;
/*    */   private final int fMaxOccurs;
/*    */   
/*    */   public XSCMRepeatingLeaf(int type, Object leaf, int minOccurs, int maxOccurs, int id, int position) {
/* 39 */     super(type, leaf, id, position);
/* 40 */     this.fMinOccurs = minOccurs;
/* 41 */     this.fMaxOccurs = maxOccurs;
/*    */   }
/*    */   
/*    */   final int getMinOccurs() {
/* 45 */     return this.fMinOccurs;
/*    */   }
/*    */   
/*    */   final int getMaxOccurs() {
/* 49 */     return this.fMaxOccurs;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/xs/models/XSCMRepeatingLeaf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */