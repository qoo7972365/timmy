/*    */ package com.sun.org.apache.xerces.internal.impl.dv.dtd;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.impl.dv.InvalidDatatypeValueException;
/*    */ import com.sun.org.apache.xerces.internal.impl.dv.ValidationContext;
/*    */ import com.sun.org.apache.xerces.internal.util.XML11Char;
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
/*    */ public class XML11IDREFDatatypeValidator
/*    */   extends IDREFDatatypeValidator
/*    */ {
/*    */   public void validate(String content, ValidationContext context) throws InvalidDatatypeValueException {
/* 65 */     if (context.useNamespaces()) {
/* 66 */       if (!XML11Char.isXML11ValidNCName(content)) {
/* 67 */         throw new InvalidDatatypeValueException("IDREFInvalidWithNamespaces", new Object[] { content });
/*    */       
/*    */       }
/*    */     }
/* 71 */     else if (!XML11Char.isXML11ValidName(content)) {
/* 72 */       throw new InvalidDatatypeValueException("IDREFInvalid", new Object[] { content });
/*    */     } 
/*    */ 
/*    */     
/* 76 */     context.addIdRef(content);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/dv/dtd/XML11IDREFDatatypeValidator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */