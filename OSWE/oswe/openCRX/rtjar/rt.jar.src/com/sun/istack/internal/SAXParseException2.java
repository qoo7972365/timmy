/*    */ package com.sun.istack.internal;
/*    */ 
/*    */ import org.xml.sax.Locator;
/*    */ import org.xml.sax.SAXParseException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SAXParseException2
/*    */   extends SAXParseException
/*    */ {
/*    */   public SAXParseException2(String message, Locator locator) {
/* 39 */     super(message, locator);
/*    */   }
/*    */   
/*    */   public SAXParseException2(String message, Locator locator, Exception e) {
/* 43 */     super(message, locator, e);
/*    */   }
/*    */   
/*    */   public SAXParseException2(String message, String publicId, String systemId, int lineNumber, int columnNumber) {
/* 47 */     super(message, publicId, systemId, lineNumber, columnNumber);
/*    */   }
/*    */   
/*    */   public SAXParseException2(String message, String publicId, String systemId, int lineNumber, int columnNumber, Exception e) {
/* 51 */     super(message, publicId, systemId, lineNumber, columnNumber, e);
/*    */   }
/*    */   
/*    */   public Throwable getCause() {
/* 55 */     return getException();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/istack/internal/SAXParseException2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */