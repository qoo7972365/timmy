/*    */ package com.sun.org.apache.xerces.internal.impl.dv.xs;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.impl.dv.InvalidDatatypeValueException;
/*    */ import com.sun.org.apache.xerces.internal.impl.dv.ValidationContext;
/*    */ import com.sun.org.apache.xerces.internal.util.XMLChar;
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
/*    */ public class IDDV
/*    */   extends TypeValidator
/*    */ {
/*    */   public short getAllowedFacets() {
/* 39 */     return 2079;
/*    */   }
/*    */   
/*    */   public Object getActualValue(String content, ValidationContext context) throws InvalidDatatypeValueException {
/* 43 */     if (!XMLChar.isValidNCName(content)) {
/* 44 */       throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { content, "NCName" });
/*    */     }
/* 46 */     return content;
/*    */   }
/*    */   
/*    */   public void checkExtraRules(Object value, ValidationContext context) throws InvalidDatatypeValueException {
/* 50 */     String content = (String)value;
/* 51 */     if (context.isIdDeclared(content))
/* 52 */       throw new InvalidDatatypeValueException("cvc-id.2", new Object[] { content }); 
/* 53 */     context.addId(content);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/dv/xs/IDDV.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */