/*     */ package com.sun.org.apache.xerces.internal.util;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.xs.util.SimpleLocator;
/*     */ import com.sun.org.apache.xerces.internal.jaxp.validation.WrappedSAXException;
/*     */ import com.sun.org.apache.xerces.internal.xni.QName;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLAttributes;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLDocumentHandler;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLLocator;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLString;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLDocumentSource;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.Locator;
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
/*     */ public class SAX2XNI
/*     */   implements ContentHandler, XMLDocumentSource
/*     */ {
/*     */   private XMLDocumentHandler fCore;
/*     */   private final NamespaceSupport nsContext;
/*     */   
/*     */   public SAX2XNI(XMLDocumentHandler core) {
/*  90 */     this.nsContext = new NamespaceSupport();
/*  91 */     this.symbolTable = new SymbolTable();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 243 */     this.xa = new XMLAttributesImpl();
/*     */     this.fCore = core;
/*     */   } private final SymbolTable symbolTable; private Locator locator; private final XMLAttributes xa;
/*     */   private XMLAttributes createAttributes(Attributes att) {
/* 247 */     this.xa.removeAllAttributes();
/* 248 */     int len = att.getLength();
/* 249 */     for (int i = 0; i < len; i++)
/* 250 */       this.xa.addAttribute(
/* 251 */           createQName(att.getURI(i), att.getLocalName(i), att.getQName(i)), att
/* 252 */           .getType(i), att
/* 253 */           .getValue(i)); 
/* 254 */     return this.xa;
/*     */   }
/*     */   
/*     */   public void setDocumentHandler(XMLDocumentHandler handler) {
/*     */     this.fCore = handler;
/*     */   }
/*     */   
/*     */   public XMLDocumentHandler getDocumentHandler() {
/*     */     return this.fCore;
/*     */   }
/*     */   
/*     */   public void startDocument() throws SAXException {
/*     */     try {
/*     */       XMLLocator xmlLocator;
/*     */       this.nsContext.reset();
/*     */       if (this.locator == null) {
/*     */         xmlLocator = new SimpleLocator(null, null, -1, -1);
/*     */       } else {
/*     */         xmlLocator = new LocatorWrapper(this.locator);
/*     */       } 
/*     */       this.fCore.startDocument(xmlLocator, null, this.nsContext, null);
/*     */     } catch (WrappedSAXException e) {
/*     */       throw e.exception;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void endDocument() throws SAXException {
/*     */     try {
/*     */       this.fCore.endDocument(null);
/*     */     } catch (WrappedSAXException e) {
/*     */       throw e.exception;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void startElement(String uri, String local, String qname, Attributes att) throws SAXException {
/*     */     try {
/*     */       this.fCore.startElement(createQName(uri, local, qname), createAttributes(att), null);
/*     */     } catch (WrappedSAXException e) {
/*     */       throw e.exception;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void endElement(String uri, String local, String qname) throws SAXException {
/*     */     try {
/*     */       this.fCore.endElement(createQName(uri, local, qname), null);
/*     */     } catch (WrappedSAXException e) {
/*     */       throw e.exception;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void characters(char[] buf, int offset, int len) throws SAXException {
/*     */     try {
/*     */       this.fCore.characters(new XMLString(buf, offset, len), null);
/*     */     } catch (WrappedSAXException e) {
/*     */       throw e.exception;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void ignorableWhitespace(char[] buf, int offset, int len) throws SAXException {
/*     */     try {
/*     */       this.fCore.ignorableWhitespace(new XMLString(buf, offset, len), null);
/*     */     } catch (WrappedSAXException e) {
/*     */       throw e.exception;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void startPrefixMapping(String prefix, String uri) {
/*     */     this.nsContext.pushContext();
/*     */     this.nsContext.declarePrefix(prefix, uri);
/*     */   }
/*     */   
/*     */   public void endPrefixMapping(String prefix) {
/*     */     this.nsContext.popContext();
/*     */   }
/*     */   
/*     */   public void processingInstruction(String target, String data) throws SAXException {
/*     */     try {
/*     */       this.fCore.processingInstruction(symbolize(target), createXMLString(data), null);
/*     */     } catch (WrappedSAXException e) {
/*     */       throw e.exception;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void skippedEntity(String name) {}
/*     */   
/*     */   public void setDocumentLocator(Locator _loc) {
/*     */     this.locator = _loc;
/*     */   }
/*     */   
/*     */   private QName createQName(String uri, String local, String raw) {
/*     */     String prefix;
/*     */     int idx = raw.indexOf(':');
/*     */     if (local.length() == 0) {
/*     */       uri = "";
/*     */       if (idx < 0) {
/*     */         local = raw;
/*     */       } else {
/*     */         local = raw.substring(idx + 1);
/*     */       } 
/*     */     } 
/*     */     if (idx < 0) {
/*     */       prefix = null;
/*     */     } else {
/*     */       prefix = raw.substring(0, idx);
/*     */     } 
/*     */     if (uri != null && uri.length() == 0)
/*     */       uri = null; 
/*     */     return new QName(symbolize(prefix), symbolize(local), symbolize(raw), symbolize(uri));
/*     */   }
/*     */   
/*     */   private String symbolize(String s) {
/*     */     if (s == null)
/*     */       return null; 
/*     */     return this.symbolTable.addSymbol(s);
/*     */   }
/*     */   
/*     */   private XMLString createXMLString(String str) {
/*     */     return new XMLString(str.toCharArray(), 0, str.length());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/util/SAX2XNI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */