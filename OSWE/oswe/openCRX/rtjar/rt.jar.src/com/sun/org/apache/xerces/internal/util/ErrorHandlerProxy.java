/*    */ package com.sun.org.apache.xerces.internal.util;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.xni.parser.XMLErrorHandler;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ErrorHandlerProxy
/*    */   implements ErrorHandler
/*    */ {
/*    */   public void error(SAXParseException e) throws SAXException {
/* 48 */     XMLErrorHandler eh = getErrorHandler();
/* 49 */     if (eh instanceof ErrorHandlerWrapper) {
/* 50 */       ((ErrorHandlerWrapper)eh).fErrorHandler.error(e);
/*    */     } else {
/*    */       
/* 53 */       eh.error("", "", ErrorHandlerWrapper.createXMLParseException(e));
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void fatalError(SAXParseException e) throws SAXException {
/* 60 */     XMLErrorHandler eh = getErrorHandler();
/* 61 */     if (eh instanceof ErrorHandlerWrapper) {
/* 62 */       ((ErrorHandlerWrapper)eh).fErrorHandler.fatalError(e);
/*    */     } else {
/*    */       
/* 65 */       eh.fatalError("", "", ErrorHandlerWrapper.createXMLParseException(e));
/*    */     } 
/*    */   }
/*    */   
/*    */   public void warning(SAXParseException e) throws SAXException {
/* 70 */     XMLErrorHandler eh = getErrorHandler();
/* 71 */     if (eh instanceof ErrorHandlerWrapper) {
/* 72 */       ((ErrorHandlerWrapper)eh).fErrorHandler.warning(e);
/*    */     } else {
/*    */       
/* 75 */       eh.warning("", "", ErrorHandlerWrapper.createXMLParseException(e));
/*    */     } 
/*    */   }
/*    */   
/*    */   protected abstract XMLErrorHandler getErrorHandler();
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/util/ErrorHandlerProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */