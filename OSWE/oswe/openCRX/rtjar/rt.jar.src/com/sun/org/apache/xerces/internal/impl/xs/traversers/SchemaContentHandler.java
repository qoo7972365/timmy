/*     */ package com.sun.org.apache.xerces.internal.impl.xs.traversers;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.xs.opti.SchemaDOMParser;
/*     */ import com.sun.org.apache.xerces.internal.util.NamespaceSupport;
/*     */ import com.sun.org.apache.xerces.internal.util.SAXLocatorWrapper;
/*     */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*     */ import com.sun.org.apache.xerces.internal.util.XMLAttributesImpl;
/*     */ import com.sun.org.apache.xerces.internal.util.XMLStringBuffer;
/*     */ import com.sun.org.apache.xerces.internal.util.XMLSymbols;
/*     */ import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
/*     */ import com.sun.org.apache.xerces.internal.xni.QName;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLString;
/*     */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLParseException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ import org.xml.sax.helpers.LocatorImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SchemaContentHandler
/*     */   implements ContentHandler
/*     */ {
/*     */   private SymbolTable fSymbolTable;
/*     */   private SchemaDOMParser fSchemaDOMParser;
/*  62 */   private final SAXLocatorWrapper fSAXLocatorWrapper = new SAXLocatorWrapper();
/*     */ 
/*     */   
/*  65 */   private NamespaceSupport fNamespaceContext = new NamespaceSupport();
/*     */ 
/*     */   
/*     */   private boolean fNeedPushNSContext;
/*     */ 
/*     */   
/*     */   private boolean fNamespacePrefixes = false;
/*     */ 
/*     */   
/*     */   private boolean fStringsInternalized = false;
/*     */ 
/*     */   
/*  77 */   private final QName fElementQName = new QName();
/*  78 */   private final QName fAttributeQName = new QName();
/*  79 */   private final XMLAttributesImpl fAttributes = new XMLAttributesImpl();
/*  80 */   private final XMLString fTempString = new XMLString();
/*  81 */   private final XMLStringBuffer fStringBuffer = new XMLStringBuffer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Document getDocument() {
/*  92 */     return this.fSchemaDOMParser.getDocument();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDocumentLocator(Locator locator) {
/*  99 */     this.fSAXLocatorWrapper.setLocator(locator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDocument() throws SAXException {
/* 106 */     this.fNeedPushNSContext = true;
/* 107 */     this.fNamespaceContext.reset();
/*     */     try {
/* 109 */       this.fSchemaDOMParser.startDocument(this.fSAXLocatorWrapper, null, this.fNamespaceContext, null);
/*     */     }
/* 111 */     catch (XMLParseException e) {
/* 112 */       convertToSAXParseException(e);
/*     */     }
/* 114 */     catch (XNIException e) {
/* 115 */       convertToSAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endDocument() throws SAXException {
/* 123 */     this.fSAXLocatorWrapper.setLocator(null);
/*     */     try {
/* 125 */       this.fSchemaDOMParser.endDocument(null);
/*     */     }
/* 127 */     catch (XMLParseException e) {
/* 128 */       convertToSAXParseException(e);
/*     */     }
/* 130 */     catch (XNIException e) {
/* 131 */       convertToSAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startPrefixMapping(String prefix, String uri) throws SAXException {
/* 139 */     if (this.fNeedPushNSContext) {
/* 140 */       this.fNeedPushNSContext = false;
/* 141 */       this.fNamespaceContext.pushContext();
/*     */     } 
/* 143 */     if (!this.fStringsInternalized) {
/* 144 */       prefix = (prefix != null) ? this.fSymbolTable.addSymbol(prefix) : XMLSymbols.EMPTY_STRING;
/* 145 */       uri = (uri != null && uri.length() > 0) ? this.fSymbolTable.addSymbol(uri) : null;
/*     */     } else {
/*     */       
/* 148 */       if (prefix == null) {
/* 149 */         prefix = XMLSymbols.EMPTY_STRING;
/*     */       }
/* 151 */       if (uri != null && uri.length() == 0) {
/* 152 */         uri = null;
/*     */       }
/*     */     } 
/* 155 */     this.fNamespaceContext.declarePrefix(prefix, uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endPrefixMapping(String prefix) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
/* 169 */     if (this.fNeedPushNSContext) {
/* 170 */       this.fNamespaceContext.pushContext();
/*     */     }
/* 172 */     this.fNeedPushNSContext = true;
/*     */ 
/*     */     
/* 175 */     fillQName(this.fElementQName, uri, localName, qName);
/* 176 */     fillXMLAttributes(atts);
/*     */ 
/*     */     
/* 179 */     if (!this.fNamespacePrefixes) {
/* 180 */       int prefixCount = this.fNamespaceContext.getDeclaredPrefixCount();
/* 181 */       if (prefixCount > 0) {
/* 182 */         addNamespaceDeclarations(prefixCount);
/*     */       }
/*     */     } 
/*     */     
/*     */     try {
/* 187 */       this.fSchemaDOMParser.startElement(this.fElementQName, this.fAttributes, null);
/*     */     }
/* 189 */     catch (XMLParseException e) {
/* 190 */       convertToSAXParseException(e);
/*     */     }
/* 192 */     catch (XNIException e) {
/* 193 */       convertToSAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endElement(String uri, String localName, String qName) throws SAXException {
/* 201 */     fillQName(this.fElementQName, uri, localName, qName);
/*     */     try {
/* 203 */       this.fSchemaDOMParser.endElement(this.fElementQName, null);
/*     */     }
/* 205 */     catch (XMLParseException e) {
/* 206 */       convertToSAXParseException(e);
/*     */     }
/* 208 */     catch (XNIException e) {
/* 209 */       convertToSAXException(e);
/*     */     } finally {
/*     */       
/* 212 */       this.fNamespaceContext.popContext();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void characters(char[] ch, int start, int length) throws SAXException {
/*     */     try {
/* 221 */       this.fTempString.setValues(ch, start, length);
/* 222 */       this.fSchemaDOMParser.characters(this.fTempString, null);
/*     */     }
/* 224 */     catch (XMLParseException e) {
/* 225 */       convertToSAXParseException(e);
/*     */     }
/* 227 */     catch (XNIException e) {
/* 228 */       convertToSAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
/*     */     try {
/* 237 */       this.fTempString.setValues(ch, start, length);
/* 238 */       this.fSchemaDOMParser.ignorableWhitespace(this.fTempString, null);
/*     */     }
/* 240 */     catch (XMLParseException e) {
/* 241 */       convertToSAXParseException(e);
/*     */     }
/* 243 */     catch (XNIException e) {
/* 244 */       convertToSAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processingInstruction(String target, String data) throws SAXException {
/*     */     try {
/* 253 */       this.fTempString.setValues(data.toCharArray(), 0, data.length());
/* 254 */       this.fSchemaDOMParser.processingInstruction(target, this.fTempString, null);
/*     */     }
/* 256 */     catch (XMLParseException e) {
/* 257 */       convertToSAXParseException(e);
/*     */     }
/* 259 */     catch (XNIException e) {
/* 260 */       convertToSAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void skippedEntity(String arg) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fillQName(QName toFill, String uri, String localpart, String rawname) {
/* 276 */     if (!this.fStringsInternalized) {
/* 277 */       uri = (uri != null && uri.length() > 0) ? this.fSymbolTable.addSymbol(uri) : null;
/* 278 */       localpart = (localpart != null) ? this.fSymbolTable.addSymbol(localpart) : XMLSymbols.EMPTY_STRING;
/* 279 */       rawname = (rawname != null) ? this.fSymbolTable.addSymbol(rawname) : XMLSymbols.EMPTY_STRING;
/*     */     } else {
/*     */       
/* 282 */       if (uri != null && uri.length() == 0) {
/* 283 */         uri = null;
/*     */       }
/* 285 */       if (localpart == null) {
/* 286 */         localpart = XMLSymbols.EMPTY_STRING;
/*     */       }
/* 288 */       if (rawname == null) {
/* 289 */         rawname = XMLSymbols.EMPTY_STRING;
/*     */       }
/*     */     } 
/* 292 */     String prefix = XMLSymbols.EMPTY_STRING;
/* 293 */     int prefixIdx = rawname.indexOf(':');
/* 294 */     if (prefixIdx != -1) {
/* 295 */       prefix = this.fSymbolTable.addSymbol(rawname.substring(0, prefixIdx));
/*     */       
/* 297 */       if (localpart == XMLSymbols.EMPTY_STRING) {
/* 298 */         localpart = this.fSymbolTable.addSymbol(rawname.substring(prefixIdx + 1));
/*     */       
/*     */       }
/*     */     }
/* 302 */     else if (localpart == XMLSymbols.EMPTY_STRING) {
/* 303 */       localpart = rawname;
/*     */     } 
/* 305 */     toFill.setValues(prefix, localpart, rawname, uri);
/*     */   }
/*     */   
/*     */   private void fillXMLAttributes(Attributes atts) {
/* 309 */     this.fAttributes.removeAllAttributes();
/* 310 */     int attrCount = atts.getLength();
/* 311 */     for (int i = 0; i < attrCount; i++) {
/* 312 */       fillQName(this.fAttributeQName, atts.getURI(i), atts.getLocalName(i), atts.getQName(i));
/* 313 */       String type = atts.getType(i);
/* 314 */       this.fAttributes.addAttributeNS(this.fAttributeQName, (type != null) ? type : XMLSymbols.fCDATASymbol, atts.getValue(i));
/* 315 */       this.fAttributes.setSpecified(i, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addNamespaceDeclarations(int prefixCount) {
/* 320 */     String prefix = null;
/* 321 */     String localpart = null;
/* 322 */     String rawname = null;
/* 323 */     String nsPrefix = null;
/* 324 */     String nsURI = null;
/* 325 */     for (int i = 0; i < prefixCount; i++) {
/* 326 */       nsPrefix = this.fNamespaceContext.getDeclaredPrefixAt(i);
/* 327 */       nsURI = this.fNamespaceContext.getURI(nsPrefix);
/* 328 */       if (nsPrefix.length() > 0) {
/* 329 */         prefix = XMLSymbols.PREFIX_XMLNS;
/* 330 */         localpart = nsPrefix;
/* 331 */         this.fStringBuffer.clear();
/* 332 */         this.fStringBuffer.append(prefix);
/* 333 */         this.fStringBuffer.append(':');
/* 334 */         this.fStringBuffer.append(localpart);
/* 335 */         rawname = this.fSymbolTable.addSymbol(this.fStringBuffer.ch, this.fStringBuffer.offset, this.fStringBuffer.length);
/*     */       } else {
/*     */         
/* 338 */         prefix = XMLSymbols.EMPTY_STRING;
/* 339 */         localpart = XMLSymbols.PREFIX_XMLNS;
/* 340 */         rawname = XMLSymbols.PREFIX_XMLNS;
/*     */       } 
/* 342 */       this.fAttributeQName.setValues(prefix, localpart, rawname, NamespaceContext.XMLNS_URI);
/* 343 */       this.fAttributes.addAttribute(this.fAttributeQName, XMLSymbols.fCDATASymbol, (nsURI != null) ? nsURI : XMLSymbols.EMPTY_STRING);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset(SchemaDOMParser schemaDOMParser, SymbolTable symbolTable, boolean namespacePrefixes, boolean stringsInternalized) {
/* 350 */     this.fSchemaDOMParser = schemaDOMParser;
/* 351 */     this.fSymbolTable = symbolTable;
/* 352 */     this.fNamespacePrefixes = namespacePrefixes;
/* 353 */     this.fStringsInternalized = stringsInternalized;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void convertToSAXParseException(XMLParseException e) throws SAXException {
/* 361 */     Exception ex = e.getException();
/* 362 */     if (ex == null) {
/*     */ 
/*     */       
/* 365 */       LocatorImpl locatorImpl = new LocatorImpl();
/* 366 */       locatorImpl.setPublicId(e.getPublicId());
/* 367 */       locatorImpl.setSystemId(e.getExpandedSystemId());
/* 368 */       locatorImpl.setLineNumber(e.getLineNumber());
/* 369 */       locatorImpl.setColumnNumber(e.getColumnNumber());
/* 370 */       throw new SAXParseException(e.getMessage(), locatorImpl);
/*     */     } 
/* 372 */     if (ex instanceof SAXException)
/*     */     {
/* 374 */       throw (SAXException)ex;
/*     */     }
/* 376 */     throw new SAXException(ex);
/*     */   }
/*     */   
/*     */   static void convertToSAXException(XNIException e) throws SAXException {
/* 380 */     Exception ex = e.getException();
/* 381 */     if (ex == null) {
/* 382 */       throw new SAXException(e.getMessage());
/*     */     }
/* 384 */     if (ex instanceof SAXException) {
/* 385 */       throw (SAXException)ex;
/*     */     }
/* 387 */     throw new SAXException(ex);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/xs/traversers/SchemaContentHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */