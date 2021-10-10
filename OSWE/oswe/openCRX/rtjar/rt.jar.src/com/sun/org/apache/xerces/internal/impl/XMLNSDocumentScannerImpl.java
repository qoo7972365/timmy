/*     */ package com.sun.org.apache.xerces.internal.impl;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.dtd.XMLDTDValidatorFilter;
/*     */ import com.sun.org.apache.xerces.internal.util.NamespaceSupport;
/*     */ import com.sun.org.apache.xerces.internal.util.XMLAttributesImpl;
/*     */ import com.sun.org.apache.xerces.internal.util.XMLSymbols;
/*     */ import com.sun.org.apache.xerces.internal.utils.XMLSecurityManager;
/*     */ import com.sun.org.apache.xerces.internal.xni.Augmentations;
/*     */ import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
/*     */ import com.sun.org.apache.xerces.internal.xni.QName;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLDocumentHandler;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLString;
/*     */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponentManager;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLDocumentSource;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLNSDocumentScannerImpl
/*     */   extends XMLDocumentScannerImpl
/*     */ {
/*     */   protected boolean fBindNamespaces;
/*     */   protected boolean fPerformValidation;
/*     */   protected boolean fNotAddNSDeclAsAttribute = false;
/*     */   private XMLDTDValidatorFilter fDTDValidator;
/*     */   private boolean fXmlnsDeclared = false;
/*     */   
/*     */   public void reset(PropertyManager propertyManager) {
/*  84 */     setPropertyManager(propertyManager);
/*  85 */     super.reset(propertyManager);
/*  86 */     this.fBindNamespaces = false;
/*  87 */     this.fNotAddNSDeclAsAttribute = !((Boolean)propertyManager.getProperty("add-namespacedecl-as-attrbiute")).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset(XMLComponentManager componentManager) throws XMLConfigurationException {
/*  92 */     super.reset(componentManager);
/*  93 */     this.fNotAddNSDeclAsAttribute = false;
/*  94 */     this.fPerformValidation = false;
/*  95 */     this.fBindNamespaces = false;
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
/*     */   public int next() throws IOException, XNIException {
/* 107 */     if (this.fScannerLastState == 2 && this.fBindNamespaces) {
/* 108 */       this.fScannerLastState = -1;
/* 109 */       this.fNamespaceContext.popContext();
/*     */     } 
/*     */     
/* 112 */     return this.fScannerLastState = super.next();
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
/*     */   public void setDTDValidator(XMLDTDValidatorFilter dtd) {
/* 125 */     this.fDTDValidator = dtd;
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
/*     */   protected boolean scanStartElement() throws IOException, XNIException {
/* 156 */     if (this.fSkip && !this.fAdd) {
/*     */ 
/*     */ 
/*     */       
/* 160 */       QName name = this.fElementStack.getNext();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 167 */       this.fSkip = this.fEntityScanner.skipString(name.rawname);
/*     */       
/* 169 */       if (this.fSkip) {
/*     */ 
/*     */ 
/*     */         
/* 173 */         this.fElementStack.push();
/* 174 */         this.fElementQName = name;
/*     */       } else {
/*     */         
/* 177 */         this.fElementStack.reposition();
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 187 */     if (!this.fSkip || this.fAdd) {
/*     */       
/* 189 */       this.fElementQName = this.fElementStack.nextElement();
/*     */ 
/*     */       
/* 192 */       if (this.fNamespaces) {
/* 193 */         this.fEntityScanner.scanQName(this.fElementQName, XMLScanner.NameType.ELEMENTSTART);
/*     */       } else {
/* 195 */         String name = this.fEntityScanner.scanName(XMLScanner.NameType.ELEMENTSTART);
/* 196 */         this.fElementQName.setValues(null, name, name, null);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 209 */     if (this.fAdd)
/*     */     {
/* 211 */       this.fElementStack.matchElement(this.fElementQName);
/*     */     }
/*     */ 
/*     */     
/* 215 */     this.fCurrentElement = this.fElementQName;
/*     */     
/* 217 */     String rawname = this.fElementQName.rawname;
/* 218 */     checkDepth(rawname);
/* 219 */     if (this.fBindNamespaces) {
/* 220 */       this.fNamespaceContext.pushContext();
/* 221 */       if (this.fScannerState == 26 && 
/* 222 */         this.fPerformValidation) {
/* 223 */         this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_GRAMMAR_NOT_FOUND", new Object[] { rawname }, (short)1);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 228 */         if (this.fDoctypeName == null || !this.fDoctypeName.equals(rawname)) {
/* 229 */           this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "RootElementTypeMustMatchDoctypedecl", new Object[] { this.fDoctypeName, rawname }, (short)1);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 239 */     this.fEmptyElement = false;
/* 240 */     this.fAttributes.removeAllAttributes();
/*     */     
/* 242 */     if (!seekCloseOfStartTag()) {
/* 243 */       this.fReadingAttributes = true;
/* 244 */       this.fAttributeCacheUsedCount = 0;
/* 245 */       this.fStringBufferIndex = 0;
/* 246 */       this.fAddDefaultAttr = true;
/* 247 */       this.fXmlnsDeclared = false;
/*     */       
/*     */       while (true) {
/* 250 */         scanAttribute(this.fAttributes);
/* 251 */         if (this.fSecurityManager != null && !this.fSecurityManager.isNoLimit(this.fElementAttributeLimit) && this.fAttributes
/* 252 */           .getLength() > this.fElementAttributeLimit) {
/* 253 */           this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "ElementAttributeLimit", new Object[] { rawname, 
/*     */                 
/* 255 */                 Integer.valueOf(this.fElementAttributeLimit) }, (short)2);
/*     */         }
/*     */ 
/*     */         
/* 259 */         if (seekCloseOfStartTag()) {
/* 260 */           this.fReadingAttributes = false; break;
/*     */         } 
/*     */       } 
/* 263 */     }  if (this.fBindNamespaces) {
/*     */       
/* 265 */       if (this.fElementQName.prefix == XMLSymbols.PREFIX_XMLNS) {
/* 266 */         this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "ElementXMLNSPrefix", new Object[] { this.fElementQName.rawname }, (short)2);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 273 */       String prefix = (this.fElementQName.prefix != null) ? this.fElementQName.prefix : XMLSymbols.EMPTY_STRING;
/*     */ 
/*     */       
/* 276 */       this.fElementQName.uri = this.fNamespaceContext.getURI(prefix);
/*     */       
/* 278 */       this.fCurrentElement.uri = this.fElementQName.uri;
/*     */       
/* 280 */       if (this.fElementQName.prefix == null && this.fElementQName.uri != null) {
/* 281 */         this.fElementQName.prefix = XMLSymbols.EMPTY_STRING;
/*     */       }
/* 283 */       if (this.fElementQName.prefix != null && this.fElementQName.uri == null) {
/* 284 */         this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "ElementPrefixUnbound", new Object[] { this.fElementQName.prefix, this.fElementQName.rawname }, (short)2);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 291 */       int length = this.fAttributes.getLength();
/*     */       
/* 293 */       for (int i = 0; i < length; i++) {
/* 294 */         this.fAttributes.getName(i, this.fAttributeQName);
/*     */         
/* 296 */         String aprefix = (this.fAttributeQName.prefix != null) ? this.fAttributeQName.prefix : XMLSymbols.EMPTY_STRING;
/*     */         
/* 298 */         String uri = this.fNamespaceContext.getURI(aprefix);
/*     */ 
/*     */         
/* 301 */         if (this.fAttributeQName.uri == null || this.fAttributeQName.uri != uri)
/*     */         {
/*     */ 
/*     */           
/* 305 */           if (aprefix != XMLSymbols.EMPTY_STRING) {
/* 306 */             this.fAttributeQName.uri = uri;
/* 307 */             if (uri == null) {
/* 308 */               this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "AttributePrefixUnbound", new Object[] { this.fElementQName.rawname, this.fAttributeQName.rawname, aprefix }, (short)2);
/*     */             }
/*     */ 
/*     */ 
/*     */             
/* 313 */             this.fAttributes.setURI(i, uri);
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 318 */       if (length > 1) {
/* 319 */         QName name = this.fAttributes.checkDuplicatesNS();
/* 320 */         if (name != null) {
/* 321 */           if (name.uri != null) {
/* 322 */             this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "AttributeNSNotUnique", new Object[] { this.fElementQName.rawname, name.localpart, name.uri }, (short)2);
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 327 */             this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "AttributeNotUnique", new Object[] { this.fElementQName.rawname, name.rawname }, (short)2);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 337 */     if (this.fEmptyElement) {
/*     */       
/* 339 */       this.fMarkupDepth--;
/*     */ 
/*     */       
/* 342 */       if (this.fMarkupDepth < this.fEntityStack[this.fEntityDepth - 1]) {
/* 343 */         reportFatalError("ElementEntityMismatch", new Object[] { this.fCurrentElement.rawname });
/*     */       }
/*     */ 
/*     */       
/* 347 */       if (this.fDocumentHandler != null)
/*     */       {
/*     */ 
/*     */         
/* 351 */         this.fDocumentHandler.emptyElement(this.fElementQName, this.fAttributes, (Augmentations)null);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 356 */       this.fScanEndElement = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 362 */       this.fElementStack.popElement();
/*     */     }
/*     */     else {
/*     */       
/* 366 */       if (this.dtdGrammarUtil != null)
/* 367 */         this.dtdGrammarUtil.startElement(this.fElementQName, this.fAttributes); 
/* 368 */       if (this.fDocumentHandler != null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 374 */         this.fDocumentHandler.startElement(this.fElementQName, this.fAttributes, (Augmentations)null);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 380 */     return this.fEmptyElement;
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
/*     */   protected void scanAttribute(XMLAttributesImpl attributes) throws IOException, XNIException {
/* 408 */     this.fEntityScanner.scanQName(this.fAttributeQName, XMLScanner.NameType.ATTRIBUTENAME);
/*     */ 
/*     */     
/* 411 */     this.fEntityScanner.skipSpaces();
/* 412 */     if (!this.fEntityScanner.skipChar(61, XMLScanner.NameType.ATTRIBUTE)) {
/* 413 */       reportFatalError("EqRequiredInAttribute", new Object[] { this.fCurrentElement.rawname, this.fAttributeQName.rawname });
/*     */     }
/*     */     
/* 416 */     this.fEntityScanner.skipSpaces();
/*     */ 
/*     */     
/* 419 */     int attrIndex = 0;
/*     */ 
/*     */ 
/*     */     
/* 423 */     boolean isVC = (this.fHasExternalDTD && !this.fStandalone);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 433 */     XMLString tmpStr = getString();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 441 */     String localpart = this.fAttributeQName.localpart;
/* 442 */     String prefix = (this.fAttributeQName.prefix != null) ? this.fAttributeQName.prefix : XMLSymbols.EMPTY_STRING;
/*     */     
/* 444 */     int i = this.fBindNamespaces & ((prefix == XMLSymbols.PREFIX_XMLNS || (prefix == XMLSymbols.EMPTY_STRING && localpart == XMLSymbols.PREFIX_XMLNS)) ? 1 : 0);
/*     */ 
/*     */     
/* 447 */     scanAttributeValue(tmpStr, this.fTempString2, this.fAttributeQName.rawname, attributes, attrIndex, isVC, this.fCurrentElement.rawname, i);
/*     */ 
/*     */     
/* 450 */     String value = null;
/*     */ 
/*     */ 
/*     */     
/* 454 */     if (this.fBindNamespaces && 
/* 455 */       i != 0) {
/*     */       
/* 457 */       if (tmpStr.length > this.fXMLNameLimit) {
/* 458 */         this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MaxXMLNameLimit", new Object[] { new String(tmpStr.ch, tmpStr.offset, tmpStr.length), 
/*     */ 
/*     */               
/* 461 */               Integer.valueOf(tmpStr.length), Integer.valueOf(this.fXMLNameLimit), this.fSecurityManager
/* 462 */               .getStateLiteral(XMLSecurityManager.Limit.MAX_NAME_LIMIT) }(short)2);
/*     */       }
/*     */ 
/*     */       
/* 466 */       String uri = this.fSymbolTable.addSymbol(tmpStr.ch, tmpStr.offset, tmpStr.length);
/* 467 */       value = uri;
/*     */       
/* 469 */       if (prefix == XMLSymbols.PREFIX_XMLNS && localpart == XMLSymbols.PREFIX_XMLNS) {
/* 470 */         this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "CantBindXMLNS", new Object[] { this.fAttributeQName }, (short)2);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 477 */       if (uri == NamespaceContext.XMLNS_URI) {
/* 478 */         this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "CantBindXMLNS", new Object[] { this.fAttributeQName }, (short)2);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 485 */       if (localpart == XMLSymbols.PREFIX_XML) {
/* 486 */         if (uri != NamespaceContext.XML_URI) {
/* 487 */           this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "CantBindXML", new Object[] { this.fAttributeQName }, (short)2);
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 495 */       else if (uri == NamespaceContext.XML_URI) {
/* 496 */         this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "CantBindXML", new Object[] { this.fAttributeQName }, (short)2);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 502 */       prefix = (localpart != XMLSymbols.PREFIX_XMLNS) ? localpart : XMLSymbols.EMPTY_STRING;
/*     */ 
/*     */ 
/*     */       
/* 506 */       if (prefix == XMLSymbols.EMPTY_STRING && localpart == XMLSymbols.PREFIX_XMLNS) {
/* 507 */         this.fAttributeQName.prefix = XMLSymbols.PREFIX_XMLNS;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 512 */       if (uri == XMLSymbols.EMPTY_STRING && localpart != XMLSymbols.PREFIX_XMLNS) {
/* 513 */         this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "EmptyPrefixedAttName", new Object[] { this.fAttributeQName }, (short)2);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 520 */       if (((NamespaceSupport)this.fNamespaceContext).containsPrefixInCurrentContext(prefix)) {
/* 521 */         reportFatalError("AttributeNotUnique", new Object[] { this.fCurrentElement.rawname, this.fAttributeQName.rawname });
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 527 */       boolean declared = this.fNamespaceContext.declarePrefix(prefix, (uri.length() != 0) ? uri : null);
/*     */ 
/*     */       
/* 530 */       if (!declared) {
/*     */         
/* 532 */         if (this.fXmlnsDeclared) {
/* 533 */           reportFatalError("AttributeNotUnique", new Object[] { this.fCurrentElement.rawname, this.fAttributeQName.rawname });
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 539 */         this.fXmlnsDeclared = true;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 547 */       if (this.fNotAddNSDeclAsAttribute) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 554 */     if (this.fBindNamespaces) {
/* 555 */       attrIndex = attributes.getLength();
/* 556 */       attributes.addAttributeNS(this.fAttributeQName, XMLSymbols.fCDATASymbol, null);
/*     */     } else {
/* 558 */       int oldLen = attributes.getLength();
/* 559 */       attrIndex = attributes.addAttribute(this.fAttributeQName, XMLSymbols.fCDATASymbol, null);
/*     */ 
/*     */       
/* 562 */       if (oldLen == attributes.getLength()) {
/* 563 */         reportFatalError("AttributeNotUnique", new Object[] { this.fCurrentElement.rawname, this.fAttributeQName.rawname });
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 569 */     attributes.setValue(attrIndex, value, tmpStr);
/*     */ 
/*     */     
/* 572 */     attributes.setSpecified(attrIndex, true);
/*     */ 
/*     */     
/* 575 */     if (this.fAttributeQName.prefix != null) {
/* 576 */       attributes.setURI(attrIndex, this.fNamespaceContext.getURI(this.fAttributeQName.prefix));
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
/*     */   protected XMLDocumentFragmentScannerImpl.Driver createContentDriver() {
/* 588 */     return new NSContentDriver();
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
/*     */   protected final class NSContentDriver
/*     */     extends XMLDocumentScannerImpl.ContentDriver
/*     */   {
/*     */     protected boolean scanRootElementHook() throws IOException, XNIException {
/* 612 */       reconfigurePipeline();
/* 613 */       if (XMLNSDocumentScannerImpl.this.scanStartElement()) {
/* 614 */         XMLNSDocumentScannerImpl.this.setScannerState(44);
/* 615 */         XMLNSDocumentScannerImpl.this.setDriver(XMLNSDocumentScannerImpl.this.fTrailingMiscDriver);
/* 616 */         return true;
/*     */       } 
/* 618 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void reconfigurePipeline() {
/* 630 */       if (XMLNSDocumentScannerImpl.this.fNamespaces && XMLNSDocumentScannerImpl.this.fDTDValidator == null) {
/* 631 */         XMLNSDocumentScannerImpl.this.fBindNamespaces = true;
/*     */       }
/* 633 */       else if (XMLNSDocumentScannerImpl.this.fNamespaces && !XMLNSDocumentScannerImpl.this.fDTDValidator.hasGrammar()) {
/* 634 */         XMLNSDocumentScannerImpl.this.fBindNamespaces = true;
/* 635 */         XMLNSDocumentScannerImpl.this.fPerformValidation = XMLNSDocumentScannerImpl.this.fDTDValidator.validate();
/*     */         
/* 637 */         XMLDocumentSource source = XMLNSDocumentScannerImpl.this.fDTDValidator.getDocumentSource();
/* 638 */         XMLDocumentHandler handler = XMLNSDocumentScannerImpl.this.fDTDValidator.getDocumentHandler();
/* 639 */         source.setDocumentHandler(handler);
/* 640 */         if (handler != null)
/* 641 */           handler.setDocumentSource(source); 
/* 642 */         XMLNSDocumentScannerImpl.this.fDTDValidator.setDocumentSource((XMLDocumentSource)null);
/* 643 */         XMLNSDocumentScannerImpl.this.fDTDValidator.setDocumentHandler(null);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/XMLNSDocumentScannerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */