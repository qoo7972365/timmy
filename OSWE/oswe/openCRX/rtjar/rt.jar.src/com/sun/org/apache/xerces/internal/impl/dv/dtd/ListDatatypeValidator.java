/*    */ package com.sun.org.apache.xerces.internal.impl.dv.dtd;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.impl.dv.DatatypeValidator;
/*    */ import com.sun.org.apache.xerces.internal.impl.dv.InvalidDatatypeValueException;
/*    */ import com.sun.org.apache.xerces.internal.impl.dv.ValidationContext;
/*    */ import java.util.StringTokenizer;
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
/*    */ public class ListDatatypeValidator
/*    */   implements DatatypeValidator
/*    */ {
/*    */   DatatypeValidator fItemValidator;
/*    */   
/*    */   public ListDatatypeValidator(DatatypeValidator itemDV) {
/* 42 */     this.fItemValidator = itemDV;
/*    */   }
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
/*    */   public void validate(String content, ValidationContext context) throws InvalidDatatypeValueException {
/* 57 */     StringTokenizer parsedList = new StringTokenizer(content, " ");
/* 58 */     int numberOfTokens = parsedList.countTokens();
/* 59 */     if (numberOfTokens == 0) {
/* 60 */       throw new InvalidDatatypeValueException("EmptyList", null);
/*    */     }
/*    */     
/* 63 */     while (parsedList.hasMoreTokens())
/* 64 */       this.fItemValidator.validate(parsedList.nextToken(), context); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/dv/dtd/ListDatatypeValidator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */