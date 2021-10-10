/*    */ package com.sun.istack.internal;
/*    */ 
/*    */ import org.xml.sax.SAXException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SAXException2
/*    */   extends SAXException
/*    */ {
/*    */   public SAXException2(String message) {
/* 38 */     super(message);
/*    */   }
/*    */   
/*    */   public SAXException2(Exception e) {
/* 42 */     super(e);
/*    */   }
/*    */   
/*    */   public SAXException2(String message, Exception e) {
/* 46 */     super(message, e);
/*    */   }
/*    */   
/*    */   public Throwable getCause() {
/* 50 */     return getException();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/istack/internal/SAXException2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */