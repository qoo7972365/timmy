/*    */ package com.sun.org.apache.xerces.internal.jaxp.validation;
/*    */ 
/*    */ import org.xml.sax.ErrorHandler;
/*    */ import org.xml.sax.SAXException;
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
/*    */ final class DraconianErrorHandler
/*    */   implements ErrorHandler
/*    */ {
/* 37 */   private static final DraconianErrorHandler ERROR_HANDLER_INSTANCE = new DraconianErrorHandler();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static DraconianErrorHandler getInstance() {
/* 44 */     return ERROR_HANDLER_INSTANCE;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void warning(SAXParseException e) throws SAXException {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void error(SAXParseException e) throws SAXException {
/* 54 */     throw e;
/*    */   }
/*    */ 
/*    */   
/*    */   public void fatalError(SAXParseException e) throws SAXException {
/* 59 */     throw e;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/jaxp/validation/DraconianErrorHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */