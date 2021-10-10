/*    */ package com.sun.org.apache.xerces.internal.impl.dv.dtd;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.impl.dv.DatatypeValidator;
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
/*    */ public class IDREFDatatypeValidator
/*    */   implements DatatypeValidator
/*    */ {
/*    */   public void validate(String content, ValidationContext context) throws InvalidDatatypeValueException {
/* 63 */     if (context.useNamespaces()) {
/* 64 */       if (!XMLChar.isValidNCName(content)) {
/* 65 */         throw new InvalidDatatypeValueException("IDREFInvalidWithNamespaces", new Object[] { content });
/*    */       
/*    */       }
/*    */     }
/* 69 */     else if (!XMLChar.isValidName(content)) {
/* 70 */       throw new InvalidDatatypeValueException("IDREFInvalid", new Object[] { content });
/*    */     } 
/*    */ 
/*    */     
/* 74 */     context.addIdRef(content);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/dv/dtd/IDREFDatatypeValidator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */