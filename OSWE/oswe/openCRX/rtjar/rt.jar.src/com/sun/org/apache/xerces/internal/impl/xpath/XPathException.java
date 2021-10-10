/*    */ package com.sun.org.apache.xerces.internal.impl.xpath;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XPathException
/*    */   extends Exception
/*    */ {
/*    */   static final long serialVersionUID = -948482312169512085L;
/*    */   private String fKey;
/*    */   
/*    */   public XPathException() {
/* 48 */     this.fKey = "c-general-xpath";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public XPathException(String key) {
/* 54 */     this.fKey = key;
/*    */   }
/*    */   
/*    */   public String getKey() {
/* 58 */     return this.fKey;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/xpath/XPathException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */