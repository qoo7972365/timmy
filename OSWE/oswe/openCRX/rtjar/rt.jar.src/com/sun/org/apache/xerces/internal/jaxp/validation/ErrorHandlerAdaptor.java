/*     */ package com.sun.org.apache.xerces.internal.jaxp.validation;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLErrorHandler;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLParseException;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ErrorHandlerAdaptor
/*     */   implements XMLErrorHandler
/*     */ {
/*     */   private boolean hadError = false;
/*     */   
/*     */   public boolean hadError() {
/*  84 */     return this.hadError;
/*     */   } public void reset() {
/*  86 */     this.hadError = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract ErrorHandler getErrorHandler();
/*     */ 
/*     */ 
/*     */   
/*     */   public void fatalError(String domain, String key, XMLParseException e) {
/*     */     try {
/*  98 */       this.hadError = true;
/*  99 */       getErrorHandler().fatalError(Util.toSAXParseException(e));
/* 100 */     } catch (SAXException se) {
/* 101 */       throw new WrappedSAXException(se);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void error(String domain, String key, XMLParseException e) {
/*     */     try {
/* 107 */       this.hadError = true;
/* 108 */       getErrorHandler().error(Util.toSAXParseException(e));
/* 109 */     } catch (SAXException se) {
/* 110 */       throw new WrappedSAXException(se);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void warning(String domain, String key, XMLParseException e) {
/*     */     try {
/* 116 */       getErrorHandler().warning(Util.toSAXParseException(e));
/* 117 */     } catch (SAXException se) {
/* 118 */       throw new WrappedSAXException(se);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/jaxp/validation/ErrorHandlerAdaptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */