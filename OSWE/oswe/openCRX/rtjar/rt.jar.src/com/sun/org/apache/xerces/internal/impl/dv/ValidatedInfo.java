/*    */ package com.sun.org.apache.xerces.internal.impl.dv;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.xs.ShortList;
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
/*    */ 
/*    */ 
/*    */ public class ValidatedInfo
/*    */ {
/*    */   public String normalizedValue;
/*    */   public Object actualValue;
/*    */   public short actualValueType;
/*    */   public XSSimpleType memberType;
/*    */   public XSSimpleType[] memberTypes;
/*    */   public ShortList itemValueTypes;
/*    */   
/*    */   public void reset() {
/* 82 */     this.normalizedValue = null;
/* 83 */     this.actualValue = null;
/* 84 */     this.memberType = null;
/* 85 */     this.memberTypes = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String stringValue() {
/* 93 */     if (this.actualValue == null) {
/* 94 */       return this.normalizedValue;
/*    */     }
/* 96 */     return this.actualValue.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/dv/ValidatedInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */