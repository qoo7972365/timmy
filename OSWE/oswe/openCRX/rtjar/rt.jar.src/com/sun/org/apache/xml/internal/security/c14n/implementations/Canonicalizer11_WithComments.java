/*    */ package com.sun.org.apache.xml.internal.security.c14n.implementations;
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
/*    */ public class Canonicalizer11_WithComments
/*    */   extends Canonicalizer11
/*    */ {
/*    */   public Canonicalizer11_WithComments() {
/* 33 */     super(true);
/*    */   }
/*    */   
/*    */   public final String engineGetURI() {
/* 37 */     return "http://www.w3.org/2006/12/xml-c14n11#WithComments";
/*    */   }
/*    */   
/*    */   public final boolean engineGetIncludeComments() {
/* 41 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/c14n/implementations/Canonicalizer11_WithComments.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */