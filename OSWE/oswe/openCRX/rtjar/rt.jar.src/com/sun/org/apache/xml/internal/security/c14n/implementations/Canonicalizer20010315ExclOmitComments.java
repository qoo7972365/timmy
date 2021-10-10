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
/*    */ public class Canonicalizer20010315ExclOmitComments
/*    */   extends Canonicalizer20010315Excl
/*    */ {
/*    */   public Canonicalizer20010315ExclOmitComments() {
/* 33 */     super(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public final String engineGetURI() {
/* 38 */     return "http://www.w3.org/2001/10/xml-exc-c14n#";
/*    */   }
/*    */ 
/*    */   
/*    */   public final boolean engineGetIncludeComments() {
/* 43 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/c14n/implementations/Canonicalizer20010315ExclOmitComments.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */