/*    */ package com.sun.org.apache.xerces.internal.impl.dv.xs;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.impl.dv.InvalidDatatypeValueException;
/*    */ import com.sun.org.apache.xerces.internal.impl.dv.ValidationContext;
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
/*    */ public class BooleanDV
/*    */   extends TypeValidator
/*    */ {
/* 37 */   private static final String[] fValueSpace = new String[] { "false", "true", "0", "1" };
/*    */   
/*    */   public short getAllowedFacets() {
/* 40 */     return 24;
/*    */   }
/*    */   
/*    */   public Object getActualValue(String content, ValidationContext context) throws InvalidDatatypeValueException {
/* 44 */     Boolean ret = null;
/*    */     
/* 46 */     if (content.equals(fValueSpace[0]) || content.equals(fValueSpace[2])) {
/* 47 */       ret = Boolean.FALSE;
/* 48 */     } else if (content.equals(fValueSpace[1]) || content.equals(fValueSpace[3])) {
/* 49 */       ret = Boolean.TRUE;
/*    */     } else {
/* 51 */       throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { content, "boolean" });
/* 52 */     }  return ret;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/dv/xs/BooleanDV.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */