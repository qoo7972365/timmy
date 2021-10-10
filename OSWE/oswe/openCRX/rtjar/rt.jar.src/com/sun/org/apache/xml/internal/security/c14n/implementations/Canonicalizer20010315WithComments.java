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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Canonicalizer20010315WithComments
/*    */   extends Canonicalizer20010315
/*    */ {
/*    */   public Canonicalizer20010315WithComments() {
/* 36 */     super(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public final String engineGetURI() {
/* 41 */     return "http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments";
/*    */   }
/*    */ 
/*    */   
/*    */   public final boolean engineGetIncludeComments() {
/* 46 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/c14n/implementations/Canonicalizer20010315WithComments.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */