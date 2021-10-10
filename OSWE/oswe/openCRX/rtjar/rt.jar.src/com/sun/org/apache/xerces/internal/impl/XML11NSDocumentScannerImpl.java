/*     */ package com.sun.org.apache.xerces.internal.impl;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.dtd.XMLDTDValidatorFilter;
/*     */ import com.sun.org.apache.xerces.internal.util.XMLAttributesImpl;
/*     */ import com.sun.org.apache.xerces.internal.util.XMLSymbols;
/*     */ import com.sun.org.apache.xerces.internal.utils.XMLSecurityManager;
/*     */ import com.sun.org.apache.xerces.internal.xni.Augmentations;
/*     */ import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
/*     */ import com.sun.org.apache.xerces.internal.xni.QName;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLDocumentHandler;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XML11NSDocumentScannerImpl
/*     */   extends XML11DocumentScannerImpl
/*     */ {
/*     */   protected boolean fBindNamespaces;
/*     */   protected boolean fPerformValidation;
/*     */   private XMLDTDValidatorFilter fDTDValidator;
/*     */   private boolean fSawSpace;
/*     */   
/*     */   public void setDTDValidator(XMLDTDValidatorFilter validator) {
/* 111 */     this.fDTDValidator = validator;
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
/*     */   protected boolean scanStartElement() throws IOException, XNIException {
/* 140 */     this.fEntityScanner.scanQName(this.fElementQName, XMLScanner.NameType.ELEMENTSTART);
/*     */     
/* 142 */     String rawname = this.fElementQName.rawname;
/* 143 */     if (this.fBindNamespaces) {
/* 144 */       this.fNamespaceContext.pushContext();
/* 145 */       if (this.fScannerState == 26 && 
/* 146 */         this.fPerformValidation) {
/* 147 */         this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_GRAMMAR_NOT_FOUND", new Object[] { rawname }, (short)1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 153 */         if (this.fDoctypeName == null || 
/* 154 */           !this.fDoctypeName.equals(rawname)) {
/* 155 */           this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "RootElementTypeMustMatchDoctypedecl", new Object[] { this.fDoctypeName, rawname }, (short)1);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 166 */     this.fCurrentElement = this.fElementStack.pushElement(this.fElementQName);
/*     */ 
/*     */     
/* 169 */     boolean empty = false;
/* 170 */     this.fAttributes.removeAllAttributes();
/*     */     
/*     */     while (true) {
/* 173 */       boolean sawSpace = this.fEntityScanner.skipSpaces();
/*     */ 
/*     */       
/* 176 */       int c = this.fEntityScanner.peekChar();
/* 177 */       if (c == 62) {
/* 178 */         this.fEntityScanner.scanChar(null); break;
/*     */       } 
/* 180 */       if (c == 47) {
/* 181 */         this.fEntityScanner.scanChar(null);
/* 182 */         if (!this.fEntityScanner.skipChar(62, null)) {
/* 183 */           reportFatalError("ElementUnterminated", new Object[] { rawname });
/*     */         }
/*     */ 
/*     */         
/* 187 */         empty = true; break;
/*     */       } 
/* 189 */       if (!isValidNameStartChar(c) || !sawSpace)
/*     */       {
/*     */         
/* 192 */         if (!isValidNameStartHighSurrogate(c) || !sawSpace) {
/* 193 */           reportFatalError("ElementUnterminated", new Object[] { rawname });
/*     */         }
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 200 */       scanAttribute(this.fAttributes);
/* 201 */       if (this.fSecurityManager != null && !this.fSecurityManager.isNoLimit(this.fElementAttributeLimit) && this.fAttributes
/* 202 */         .getLength() > this.fElementAttributeLimit) {
/* 203 */         this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "ElementAttributeLimit", new Object[] { rawname, new Integer(this.fElementAttributeLimit) }, (short)2);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 211 */     if (this.fBindNamespaces) {
/*     */       
/* 213 */       if (this.fElementQName.prefix == XMLSymbols.PREFIX_XMLNS) {
/* 214 */         this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "ElementXMLNSPrefix", new Object[] { this.fElementQName.rawname }, (short)2);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 222 */       String prefix = (this.fElementQName.prefix != null) ? this.fElementQName.prefix : XMLSymbols.EMPTY_STRING;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 227 */       this.fElementQName.uri = this.fNamespaceContext.getURI(prefix);
/*     */       
/* 229 */       this.fCurrentElement.uri = this.fElementQName.uri;
/*     */       
/* 231 */       if (this.fElementQName.prefix == null && this.fElementQName.uri != null) {
/* 232 */         this.fElementQName.prefix = XMLSymbols.EMPTY_STRING;
/*     */         
/* 234 */         this.fCurrentElement.prefix = XMLSymbols.EMPTY_STRING;
/*     */       } 
/* 236 */       if (this.fElementQName.prefix != null && this.fElementQName.uri == null) {
/* 237 */         this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "ElementPrefixUnbound", new Object[] { this.fElementQName.prefix, this.fElementQName.rawname }, (short)2);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 247 */       int length = this.fAttributes.getLength();
/* 248 */       for (int i = 0; i < length; i++) {
/* 249 */         this.fAttributes.getName(i, this.fAttributeQName);
/*     */         
/* 251 */         String aprefix = (this.fAttributeQName.prefix != null) ? this.fAttributeQName.prefix : XMLSymbols.EMPTY_STRING;
/*     */ 
/*     */ 
/*     */         
/* 255 */         String uri = this.fNamespaceContext.getURI(aprefix);
/*     */ 
/*     */         
/* 258 */         if (this.fAttributeQName.uri == null || this.fAttributeQName.uri != uri)
/*     */         {
/*     */ 
/*     */           
/* 262 */           if (aprefix != XMLSymbols.EMPTY_STRING) {
/* 263 */             this.fAttributeQName.uri = uri;
/* 264 */             if (uri == null) {
/* 265 */               this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "AttributePrefixUnbound", new Object[] { this.fElementQName.rawname, this.fAttributeQName.rawname, aprefix }, (short)2);
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 274 */             this.fAttributes.setURI(i, uri);
/*     */           } 
/*     */         }
/*     */       } 
/* 278 */       if (length > 1) {
/* 279 */         QName name = this.fAttributes.checkDuplicatesNS();
/* 280 */         if (name != null) {
/* 281 */           if (name.uri != null) {
/* 282 */             this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "AttributeNSNotUnique", new Object[] { this.fElementQName.rawname, name.localpart, name.uri }, (short)2);
/*     */ 
/*     */ 
/*     */           
/*     */           }
/*     */           else {
/*     */ 
/*     */ 
/*     */             
/* 291 */             this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "AttributeNotUnique", new Object[] { this.fElementQName.rawname, name.rawname }, (short)2);
/*     */           } 
/*     */         }
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
/* 304 */     if (empty) {
/*     */       
/* 306 */       this.fMarkupDepth--;
/*     */ 
/*     */       
/* 309 */       if (this.fMarkupDepth < this.fEntityStack[this.fEntityDepth - 1]) {
/* 310 */         reportFatalError("ElementEntityMismatch", new Object[] { this.fCurrentElement.rawname });
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 315 */       if (this.fDocumentHandler != null) {
/* 316 */         this.fDocumentHandler.emptyElement(this.fElementQName, this.fAttributes, (Augmentations)null);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 322 */       this.fScanEndElement = true;
/*     */ 
/*     */       
/* 325 */       this.fElementStack.popElement();
/*     */     } else {
/* 327 */       if (this.dtdGrammarUtil != null) {
/* 328 */         this.dtdGrammarUtil.startElement(this.fElementQName, this.fAttributes);
/*     */       }
/*     */       
/* 331 */       if (this.fDocumentHandler != null) {
/* 332 */         this.fDocumentHandler.startElement(this.fElementQName, this.fAttributes, (Augmentations)null);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 338 */     return empty;
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
/*     */   protected void scanStartElementName() throws IOException, XNIException {
/* 350 */     this.fEntityScanner.scanQName(this.fElementQName, XMLScanner.NameType.ELEMENTSTART);
/*     */ 
/*     */     
/* 353 */     this.fSawSpace = this.fEntityScanner.skipSpaces();
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
/*     */   protected boolean scanStartElementAfterName() throws IOException, XNIException {
/* 366 */     String rawname = this.fElementQName.rawname;
/* 367 */     if (this.fBindNamespaces) {
/* 368 */       this.fNamespaceContext.pushContext();
/* 369 */       if (this.fScannerState == 26 && 
/* 370 */         this.fPerformValidation) {
/* 371 */         this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_GRAMMAR_NOT_FOUND", new Object[] { rawname }, (short)1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 377 */         if (this.fDoctypeName == null || 
/* 378 */           !this.fDoctypeName.equals(rawname)) {
/* 379 */           this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "RootElementTypeMustMatchDoctypedecl", new Object[] { this.fDoctypeName, rawname }, (short)1);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 390 */     this.fCurrentElement = this.fElementStack.pushElement(this.fElementQName);
/*     */ 
/*     */     
/* 393 */     boolean empty = false;
/* 394 */     this.fAttributes.removeAllAttributes();
/*     */ 
/*     */     
/*     */     while (true) {
/* 398 */       int c = this.fEntityScanner.peekChar();
/* 399 */       if (c == 62) {
/* 400 */         this.fEntityScanner.scanChar(null); break;
/*     */       } 
/* 402 */       if (c == 47) {
/* 403 */         this.fEntityScanner.scanChar(null);
/* 404 */         if (!this.fEntityScanner.skipChar(62, null)) {
/* 405 */           reportFatalError("ElementUnterminated", new Object[] { rawname });
/*     */         }
/*     */ 
/*     */         
/* 409 */         empty = true; break;
/*     */       } 
/* 411 */       if (!isValidNameStartChar(c) || !this.fSawSpace)
/*     */       {
/*     */         
/* 414 */         if (!isValidNameStartHighSurrogate(c) || !this.fSawSpace) {
/* 415 */           reportFatalError("ElementUnterminated", new Object[] { rawname });
/*     */         }
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 422 */       scanAttribute(this.fAttributes);
/*     */ 
/*     */       
/* 425 */       this.fSawSpace = this.fEntityScanner.skipSpaces();
/*     */     } 
/*     */ 
/*     */     
/* 429 */     if (this.fBindNamespaces) {
/*     */       
/* 431 */       if (this.fElementQName.prefix == XMLSymbols.PREFIX_XMLNS) {
/* 432 */         this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "ElementXMLNSPrefix", new Object[] { this.fElementQName.rawname }, (short)2);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 440 */       String prefix = (this.fElementQName.prefix != null) ? this.fElementQName.prefix : XMLSymbols.EMPTY_STRING;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 445 */       this.fElementQName.uri = this.fNamespaceContext.getURI(prefix);
/*     */       
/* 447 */       this.fCurrentElement.uri = this.fElementQName.uri;
/*     */       
/* 449 */       if (this.fElementQName.prefix == null && this.fElementQName.uri != null) {
/* 450 */         this.fElementQName.prefix = XMLSymbols.EMPTY_STRING;
/*     */         
/* 452 */         this.fCurrentElement.prefix = XMLSymbols.EMPTY_STRING;
/*     */       } 
/* 454 */       if (this.fElementQName.prefix != null && this.fElementQName.uri == null) {
/* 455 */         this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "ElementPrefixUnbound", new Object[] { this.fElementQName.prefix, this.fElementQName.rawname }, (short)2);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 465 */       int length = this.fAttributes.getLength();
/* 466 */       for (int i = 0; i < length; i++) {
/* 467 */         this.fAttributes.getName(i, this.fAttributeQName);
/*     */         
/* 469 */         String aprefix = (this.fAttributeQName.prefix != null) ? this.fAttributeQName.prefix : XMLSymbols.EMPTY_STRING;
/*     */ 
/*     */ 
/*     */         
/* 473 */         String uri = this.fNamespaceContext.getURI(aprefix);
/*     */ 
/*     */         
/* 476 */         if (this.fAttributeQName.uri == null || this.fAttributeQName.uri != uri)
/*     */         {
/*     */ 
/*     */           
/* 480 */           if (aprefix != XMLSymbols.EMPTY_STRING) {
/* 481 */             this.fAttributeQName.uri = uri;
/* 482 */             if (uri == null) {
/* 483 */               this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "AttributePrefixUnbound", new Object[] { this.fElementQName.rawname, this.fAttributeQName.rawname, aprefix }, (short)2);
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 492 */             this.fAttributes.setURI(i, uri);
/*     */           } 
/*     */         }
/*     */       } 
/* 496 */       if (length > 1) {
/* 497 */         QName name = this.fAttributes.checkDuplicatesNS();
/* 498 */         if (name != null) {
/* 499 */           if (name.uri != null) {
/* 500 */             this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "AttributeNSNotUnique", new Object[] { this.fElementQName.rawname, name.localpart, name.uri }, (short)2);
/*     */ 
/*     */ 
/*     */           
/*     */           }
/*     */           else {
/*     */ 
/*     */ 
/*     */             
/* 509 */             this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "AttributeNotUnique", new Object[] { this.fElementQName.rawname, name.rawname }, (short)2);
/*     */           } 
/*     */         }
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
/* 522 */     if (this.fDocumentHandler != null) {
/* 523 */       if (empty) {
/*     */ 
/*     */         
/* 526 */         this.fMarkupDepth--;
/*     */ 
/*     */         
/* 529 */         if (this.fMarkupDepth < this.fEntityStack[this.fEntityDepth - 1]) {
/* 530 */           reportFatalError("ElementEntityMismatch", new Object[] { this.fCurrentElement.rawname });
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 535 */         this.fDocumentHandler.emptyElement(this.fElementQName, this.fAttributes, (Augmentations)null);
/*     */         
/* 537 */         if (this.fBindNamespaces) {
/* 538 */           this.fNamespaceContext.popContext();
/*     */         }
/*     */         
/* 541 */         this.fElementStack.popElement();
/*     */       } else {
/* 543 */         this.fDocumentHandler.startElement(this.fElementQName, this.fAttributes, (Augmentations)null);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 549 */     return empty;
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
/*     */   protected void scanAttribute(XMLAttributesImpl attributes) throws IOException, XNIException {
/*     */     int attrIndex;
/* 576 */     this.fEntityScanner.scanQName(this.fAttributeQName, XMLScanner.NameType.ATTRIBUTENAME);
/*     */ 
/*     */     
/* 579 */     this.fEntityScanner.skipSpaces();
/* 580 */     if (!this.fEntityScanner.skipChar(61, XMLScanner.NameType.ATTRIBUTE)) {
/* 581 */       reportFatalError("EqRequiredInAttribute", new Object[] { this.fCurrentElement.rawname, this.fAttributeQName.rawname });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 587 */     this.fEntityScanner.skipSpaces();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 592 */     if (this.fBindNamespaces) {
/* 593 */       attrIndex = attributes.getLength();
/* 594 */       attributes.addAttributeNS(this.fAttributeQName, XMLSymbols.fCDATASymbol, null);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 599 */       int oldLen = attributes.getLength();
/*     */       
/* 601 */       attrIndex = attributes.addAttribute(this.fAttributeQName, XMLSymbols.fCDATASymbol, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 607 */       if (oldLen == attributes.getLength()) {
/* 608 */         reportFatalError("AttributeNotUnique", new Object[] { this.fCurrentElement.rawname, this.fAttributeQName.rawname });
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 617 */     boolean isVC = (this.fHasExternalDTD && !this.fStandalone);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 625 */     String localpart = this.fAttributeQName.localpart;
/* 626 */     String prefix = (this.fAttributeQName.prefix != null) ? this.fAttributeQName.prefix : XMLSymbols.EMPTY_STRING;
/*     */     
/* 628 */     int i = this.fBindNamespaces & ((prefix == XMLSymbols.PREFIX_XMLNS || (prefix == XMLSymbols.EMPTY_STRING && localpart == XMLSymbols.PREFIX_XMLNS)) ? 1 : 0);
/*     */ 
/*     */     
/* 631 */     scanAttributeValue(this.fTempString, this.fTempString2, this.fAttributeQName.rawname, isVC, this.fCurrentElement.rawname, i);
/*     */     
/* 633 */     String value = this.fTempString.toString();
/* 634 */     attributes.setValue(attrIndex, value);
/* 635 */     attributes.setNonNormalizedValue(attrIndex, this.fTempString2.toString());
/* 636 */     attributes.setSpecified(attrIndex, true);
/*     */ 
/*     */     
/* 639 */     if (this.fBindNamespaces) {
/* 640 */       if (i != 0) {
/* 641 */         if (value.length() > this.fXMLNameLimit) {
/* 642 */           this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MaxXMLNameLimit", new Object[] { value, 
/*     */                 
/* 644 */                 Integer.valueOf(value.length()), Integer.valueOf(this.fXMLNameLimit), this.fSecurityManager
/* 645 */                 .getStateLiteral(XMLSecurityManager.Limit.MAX_NAME_LIMIT) }, (short)2);
/*     */         }
/*     */ 
/*     */         
/* 649 */         String uri = this.fSymbolTable.addSymbol(value);
/*     */ 
/*     */         
/* 652 */         if (prefix == XMLSymbols.PREFIX_XMLNS && localpart == XMLSymbols.PREFIX_XMLNS)
/*     */         {
/* 654 */           this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "CantBindXMLNS", new Object[] { this.fAttributeQName }, (short)2);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 662 */         if (uri == NamespaceContext.XMLNS_URI) {
/* 663 */           this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "CantBindXMLNS", new Object[] { this.fAttributeQName }, (short)2);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 671 */         if (localpart == XMLSymbols.PREFIX_XML) {
/* 672 */           if (uri != NamespaceContext.XML_URI) {
/* 673 */             this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "CantBindXML", new Object[] { this.fAttributeQName }, (short)2);
/*     */ 
/*     */ 
/*     */           
/*     */           }
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 682 */         else if (uri == NamespaceContext.XML_URI) {
/* 683 */           this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "CantBindXML", new Object[] { this.fAttributeQName }, (short)2);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 691 */         prefix = (localpart != XMLSymbols.PREFIX_XMLNS) ? localpart : XMLSymbols.EMPTY_STRING;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 699 */         this.fNamespaceContext.declarePrefix(prefix, 
/*     */             
/* 701 */             (uri.length() != 0) ? uri : null);
/*     */         
/* 703 */         attributes.setURI(attrIndex, this.fNamespaceContext
/*     */             
/* 705 */             .getURI(XMLSymbols.PREFIX_XMLNS));
/*     */ 
/*     */       
/*     */       }
/* 709 */       else if (this.fAttributeQName.prefix != null) {
/* 710 */         attributes.setURI(attrIndex, this.fNamespaceContext
/*     */             
/* 712 */             .getURI(this.fAttributeQName.prefix));
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
/*     */   protected int scanEndElement() throws IOException, XNIException {
/* 740 */     QName endElementName = this.fElementStack.popElement();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 752 */     if (!this.fEntityScanner.skipString(endElementName.rawname)) {
/* 753 */       reportFatalError("ETagRequired", new Object[] { endElementName.rawname });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 759 */     this.fEntityScanner.skipSpaces();
/* 760 */     if (!this.fEntityScanner.skipChar(62, XMLScanner.NameType.ELEMENTEND)) {
/* 761 */       reportFatalError("ETagUnterminated", new Object[] { endElementName.rawname });
/*     */     }
/*     */ 
/*     */     
/* 765 */     this.fMarkupDepth--;
/*     */ 
/*     */     
/* 768 */     this.fMarkupDepth--;
/*     */ 
/*     */     
/* 771 */     if (this.fMarkupDepth < this.fEntityStack[this.fEntityDepth - 1]) {
/* 772 */       reportFatalError("ElementEntityMismatch", new Object[] { endElementName.rawname });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 778 */     if (this.fDocumentHandler != null) {
/* 779 */       this.fDocumentHandler.endElement(endElementName, (Augmentations)null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 787 */     if (this.dtdGrammarUtil != null) {
/* 788 */       this.dtdGrammarUtil.endElement(endElementName);
/*     */     }
/* 790 */     return this.fMarkupDepth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset(XMLComponentManager componentManager) throws XMLConfigurationException {
/* 797 */     super.reset(componentManager);
/* 798 */     this.fPerformValidation = false;
/* 799 */     this.fBindNamespaces = false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected XMLDocumentFragmentScannerImpl.Driver createContentDriver() {
/* 804 */     return new NS11ContentDriver();
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
/*     */   public int next() throws IOException, XNIException {
/* 817 */     if (this.fScannerLastState == 2 && this.fBindNamespaces) {
/* 818 */       this.fScannerLastState = -1;
/* 819 */       this.fNamespaceContext.popContext();
/*     */     } 
/*     */     
/* 822 */     return this.fScannerLastState = super.next();
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
/*     */   protected final class NS11ContentDriver
/*     */     extends XMLDocumentScannerImpl.ContentDriver
/*     */   {
/*     */     protected boolean scanRootElementHook() throws IOException, XNIException {
/* 846 */       if (XML11NSDocumentScannerImpl.this.fExternalSubsetResolver != null && !XML11NSDocumentScannerImpl.this.fSeenDoctypeDecl && !XML11NSDocumentScannerImpl.this.fDisallowDoctype && (XML11NSDocumentScannerImpl.this.fValidation || XML11NSDocumentScannerImpl.this.fLoadExternalDTD)) {
/*     */         
/* 848 */         XML11NSDocumentScannerImpl.this.scanStartElementName();
/* 849 */         resolveExternalSubsetAndRead();
/* 850 */         reconfigurePipeline();
/* 851 */         if (XML11NSDocumentScannerImpl.this.scanStartElementAfterName()) {
/* 852 */           XML11NSDocumentScannerImpl.this.setScannerState(44);
/* 853 */           XML11NSDocumentScannerImpl.this.setDriver(XML11NSDocumentScannerImpl.this.fTrailingMiscDriver);
/* 854 */           return true;
/*     */         } 
/*     */       } else {
/*     */         
/* 858 */         reconfigurePipeline();
/* 859 */         if (XML11NSDocumentScannerImpl.this.scanStartElement()) {
/* 860 */           XML11NSDocumentScannerImpl.this.setScannerState(44);
/* 861 */           XML11NSDocumentScannerImpl.this.setDriver(XML11NSDocumentScannerImpl.this.fTrailingMiscDriver);
/* 862 */           return true;
/*     */         } 
/*     */       } 
/* 865 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void reconfigurePipeline() {
/* 876 */       if (XML11NSDocumentScannerImpl.this.fDTDValidator == null) {
/* 877 */         XML11NSDocumentScannerImpl.this.fBindNamespaces = true;
/*     */       }
/* 879 */       else if (!XML11NSDocumentScannerImpl.this.fDTDValidator.hasGrammar()) {
/* 880 */         XML11NSDocumentScannerImpl.this.fBindNamespaces = true;
/* 881 */         XML11NSDocumentScannerImpl.this.fPerformValidation = XML11NSDocumentScannerImpl.this.fDTDValidator.validate();
/*     */         
/* 883 */         XMLDocumentSource source = XML11NSDocumentScannerImpl.this.fDTDValidator.getDocumentSource();
/* 884 */         XMLDocumentHandler handler = XML11NSDocumentScannerImpl.this.fDTDValidator.getDocumentHandler();
/* 885 */         source.setDocumentHandler(handler);
/* 886 */         if (handler != null)
/* 887 */           handler.setDocumentSource(source); 
/* 888 */         XML11NSDocumentScannerImpl.this.fDTDValidator.setDocumentSource((XMLDocumentSource)null);
/* 889 */         XML11NSDocumentScannerImpl.this.fDTDValidator.setDocumentHandler(null);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/XML11NSDocumentScannerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */