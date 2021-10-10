/*    */ package com.sun.org.apache.xml.internal.security.utils;
/*    */ 
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
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
/*    */ public class IgnoreAllErrorHandler
/*    */   implements ErrorHandler
/*    */ {
/* 39 */   private static Logger log = Logger.getLogger(IgnoreAllErrorHandler.class.getName());
/*    */ 
/*    */ 
/*    */   
/* 43 */   private static final boolean warnOnExceptions = System.getProperty("com.sun.org.apache.xml.internal.security.test.warn.on.exceptions", "false").equals("true");
/*    */ 
/*    */ 
/*    */   
/* 47 */   private static final boolean throwExceptions = System.getProperty("com.sun.org.apache.xml.internal.security.test.throw.exceptions", "false").equals("true");
/*    */ 
/*    */ 
/*    */   
/*    */   public void warning(SAXParseException paramSAXParseException) throws SAXException {
/* 52 */     if (warnOnExceptions) {
/* 53 */       log.log(Level.WARNING, "", paramSAXParseException);
/*    */     }
/* 55 */     if (throwExceptions) {
/* 56 */       throw paramSAXParseException;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void error(SAXParseException paramSAXParseException) throws SAXException {
/* 63 */     if (warnOnExceptions) {
/* 64 */       log.log(Level.SEVERE, "", paramSAXParseException);
/*    */     }
/* 66 */     if (throwExceptions) {
/* 67 */       throw paramSAXParseException;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void fatalError(SAXParseException paramSAXParseException) throws SAXException {
/* 74 */     if (warnOnExceptions) {
/* 75 */       log.log(Level.WARNING, "", paramSAXParseException);
/*    */     }
/* 77 */     if (throwExceptions)
/* 78 */       throw paramSAXParseException; 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/utils/IgnoreAllErrorHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */