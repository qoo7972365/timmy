/*     */ package com.sun.org.apache.xerces.internal.util;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLLocator;
/*     */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLErrorHandler;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLParseException;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXParseException;
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
/*     */ public class ErrorHandlerWrapper
/*     */   implements XMLErrorHandler
/*     */ {
/*     */   protected ErrorHandler fErrorHandler;
/*     */   
/*     */   public ErrorHandlerWrapper() {}
/*     */   
/*     */   public ErrorHandlerWrapper(ErrorHandler errorHandler) {
/*  58 */     setErrorHandler(errorHandler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setErrorHandler(ErrorHandler errorHandler) {
/*  67 */     this.fErrorHandler = errorHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   public ErrorHandler getErrorHandler() {
/*  72 */     return this.fErrorHandler;
/*     */   }
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
/*     */   public void warning(String domain, String key, XMLParseException exception) throws XNIException {
/*  98 */     if (this.fErrorHandler != null) {
/*  99 */       SAXParseException saxException = createSAXParseException(exception);
/*     */       
/*     */       try {
/* 102 */         this.fErrorHandler.warning(saxException);
/*     */       }
/* 104 */       catch (SAXParseException e) {
/* 105 */         throw createXMLParseException(e);
/*     */       }
/* 107 */       catch (SAXException e) {
/* 108 */         throw createXNIException(e);
/*     */       } 
/*     */     } 
/*     */   }
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
/*     */   public void error(String domain, String key, XMLParseException exception) throws XNIException {
/* 133 */     if (this.fErrorHandler != null) {
/* 134 */       SAXParseException saxException = createSAXParseException(exception);
/*     */       
/*     */       try {
/* 137 */         this.fErrorHandler.error(saxException);
/*     */       }
/* 139 */       catch (SAXParseException e) {
/* 140 */         throw createXMLParseException(e);
/*     */       }
/* 142 */       catch (SAXException e) {
/* 143 */         throw createXNIException(e);
/*     */       } 
/*     */     } 
/*     */   }
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
/*     */   public void fatalError(String domain, String key, XMLParseException exception) throws XNIException {
/* 176 */     if (this.fErrorHandler != null) {
/* 177 */       SAXParseException saxException = createSAXParseException(exception);
/*     */       
/*     */       try {
/* 180 */         this.fErrorHandler.fatalError(saxException);
/*     */       }
/* 182 */       catch (SAXParseException e) {
/* 183 */         throw createXMLParseException(e);
/*     */       }
/* 185 */       catch (SAXException e) {
/* 186 */         throw createXNIException(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static SAXParseException createSAXParseException(XMLParseException exception) {
/* 198 */     return new SAXParseException(exception.getMessage(), exception
/* 199 */         .getPublicId(), exception
/* 200 */         .getExpandedSystemId(), exception
/* 201 */         .getLineNumber(), exception
/* 202 */         .getColumnNumber(), exception
/* 203 */         .getException());
/*     */   }
/*     */ 
/*     */   
/*     */   protected static XMLParseException createXMLParseException(SAXParseException exception) {
/* 208 */     final String fPublicId = exception.getPublicId();
/* 209 */     final String fExpandedSystemId = exception.getSystemId();
/* 210 */     final int fLineNumber = exception.getLineNumber();
/* 211 */     final int fColumnNumber = exception.getColumnNumber();
/* 212 */     XMLLocator location = new XMLLocator() {
/* 213 */         public String getPublicId() { return fPublicId; }
/* 214 */         public String getExpandedSystemId() { return fExpandedSystemId; }
/* 215 */         public String getBaseSystemId() { return null; }
/* 216 */         public String getLiteralSystemId() { return null; }
/* 217 */         public int getColumnNumber() { return fColumnNumber; }
/* 218 */         public int getLineNumber() { return fLineNumber; }
/* 219 */         public int getCharacterOffset() { return -1; }
/* 220 */         public String getEncoding() { return null; }
/* 221 */         public String getXMLVersion() { return null; }
/*     */       };
/* 223 */     return new XMLParseException(location, exception.getMessage(), exception);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static XNIException createXNIException(SAXException exception) {
/* 230 */     return new XNIException(exception.getMessage(), exception);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/util/ErrorHandlerWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */