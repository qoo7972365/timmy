/*     */ package com.sun.org.apache.xerces.internal.impl.xs.traversers;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.xs.opti.SchemaDOMParser;
/*     */ import com.sun.org.apache.xerces.internal.util.JAXPNamespaceContextWrapper;
/*     */ import com.sun.org.apache.xerces.internal.util.StAXLocationWrapper;
/*     */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*     */ import com.sun.org.apache.xerces.internal.util.XMLAttributesImpl;
/*     */ import com.sun.org.apache.xerces.internal.util.XMLStringBuffer;
/*     */ import com.sun.org.apache.xerces.internal.util.XMLSymbols;
/*     */ import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
/*     */ import com.sun.org.apache.xerces.internal.xni.QName;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLString;
/*     */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLEventReader;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.events.Attribute;
/*     */ import javax.xml.stream.events.EndElement;
/*     */ import javax.xml.stream.events.Namespace;
/*     */ import javax.xml.stream.events.ProcessingInstruction;
/*     */ import javax.xml.stream.events.StartElement;
/*     */ import javax.xml.stream.events.XMLEvent;
/*     */ import org.w3c.dom.Document;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class StAXSchemaParser
/*     */ {
/*     */   private static final int CHUNK_SIZE = 1024;
/*     */   private static final int CHUNK_MASK = 1023;
/*  68 */   private final char[] fCharBuffer = new char[1024];
/*     */ 
/*     */   
/*     */   private SymbolTable fSymbolTable;
/*     */ 
/*     */   
/*     */   private SchemaDOMParser fSchemaDOMParser;
/*     */ 
/*     */   
/*  77 */   private final StAXLocationWrapper fLocationWrapper = new StAXLocationWrapper();
/*     */ 
/*     */   
/*  80 */   private final JAXPNamespaceContextWrapper fNamespaceContext = new JAXPNamespaceContextWrapper(this.fSymbolTable);
/*     */ 
/*     */   
/*  83 */   private final QName fElementQName = new QName();
/*  84 */   private final QName fAttributeQName = new QName();
/*  85 */   private final XMLAttributesImpl fAttributes = new XMLAttributesImpl();
/*  86 */   private final XMLString fTempString = new XMLString();
/*  87 */   private final ArrayList fDeclaredPrefixes = new ArrayList();
/*  88 */   private final XMLStringBuffer fStringBuffer = new XMLStringBuffer();
/*     */   private int fDepth;
/*     */   
/*     */   public StAXSchemaParser() {
/*  92 */     this.fNamespaceContext.setDeclaredPrefixes(this.fDeclaredPrefixes);
/*     */   }
/*     */   
/*     */   public void reset(SchemaDOMParser schemaDOMParser, SymbolTable symbolTable) {
/*  96 */     this.fSchemaDOMParser = schemaDOMParser;
/*  97 */     this.fSymbolTable = symbolTable;
/*  98 */     this.fNamespaceContext.setSymbolTable(this.fSymbolTable);
/*  99 */     this.fNamespaceContext.reset();
/*     */   }
/*     */   
/*     */   public Document getDocument() {
/* 103 */     return this.fSchemaDOMParser.getDocument();
/*     */   }
/*     */   
/*     */   public void parse(XMLEventReader input) throws XMLStreamException, XNIException {
/* 107 */     XMLEvent currentEvent = input.peek();
/* 108 */     if (currentEvent != null) {
/* 109 */       int eventType = currentEvent.getEventType();
/* 110 */       if (eventType != 7 && eventType != 1)
/*     */       {
/* 112 */         throw new XMLStreamException();
/*     */       }
/* 114 */       this.fLocationWrapper.setLocation(currentEvent.getLocation());
/* 115 */       this.fSchemaDOMParser.startDocument(this.fLocationWrapper, null, this.fNamespaceContext, null);
/* 116 */       while (input.hasNext()) {
/* 117 */         StartElement start; EndElement end; ProcessingInstruction pi; currentEvent = input.nextEvent();
/* 118 */         eventType = currentEvent.getEventType();
/* 119 */         switch (eventType) {
/*     */           case 1:
/* 121 */             this.fDepth++;
/* 122 */             start = currentEvent.asStartElement();
/* 123 */             fillQName(this.fElementQName, start.getName());
/* 124 */             this.fLocationWrapper.setLocation(start.getLocation());
/* 125 */             this.fNamespaceContext.setNamespaceContext(start.getNamespaceContext());
/* 126 */             fillXMLAttributes(start);
/* 127 */             fillDeclaredPrefixes(start);
/* 128 */             addNamespaceDeclarations();
/* 129 */             this.fNamespaceContext.pushContext();
/* 130 */             this.fSchemaDOMParser.startElement(this.fElementQName, this.fAttributes, null);
/*     */           
/*     */           case 2:
/* 133 */             end = currentEvent.asEndElement();
/* 134 */             fillQName(this.fElementQName, end.getName());
/* 135 */             fillDeclaredPrefixes(end);
/* 136 */             this.fLocationWrapper.setLocation(end.getLocation());
/* 137 */             this.fSchemaDOMParser.endElement(this.fElementQName, null);
/* 138 */             this.fNamespaceContext.popContext();
/* 139 */             this.fDepth--;
/* 140 */             if (this.fDepth <= 0) {
/*     */               break;
/*     */             }
/*     */           
/*     */           case 4:
/* 145 */             sendCharactersToSchemaParser(currentEvent.asCharacters().getData(), false);
/*     */           
/*     */           case 6:
/* 148 */             sendCharactersToSchemaParser(currentEvent.asCharacters().getData(), true);
/*     */           
/*     */           case 12:
/* 151 */             this.fSchemaDOMParser.startCDATA(null);
/* 152 */             sendCharactersToSchemaParser(currentEvent.asCharacters().getData(), false);
/* 153 */             this.fSchemaDOMParser.endCDATA(null);
/*     */           
/*     */           case 3:
/* 156 */             pi = (ProcessingInstruction)currentEvent;
/* 157 */             fillProcessingInstruction(pi.getData());
/* 158 */             this.fSchemaDOMParser.processingInstruction(pi.getTarget(), this.fTempString, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case 7:
/* 170 */             this.fDepth++;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       } 
/* 178 */       this.fLocationWrapper.setLocation(null);
/* 179 */       this.fNamespaceContext.setNamespaceContext(null);
/* 180 */       this.fSchemaDOMParser.endDocument(null);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void parse(XMLStreamReader input) throws XMLStreamException, XNIException {
/* 185 */     if (input.hasNext()) {
/* 186 */       int eventType = input.getEventType();
/* 187 */       if (eventType != 7 && eventType != 1)
/*     */       {
/* 189 */         throw new XMLStreamException();
/*     */       }
/* 191 */       this.fLocationWrapper.setLocation(input.getLocation());
/* 192 */       this.fSchemaDOMParser.startDocument(this.fLocationWrapper, null, this.fNamespaceContext, null);
/* 193 */       boolean first = true;
/* 194 */       while (input.hasNext()) {
/* 195 */         if (!first) {
/* 196 */           eventType = input.next();
/*     */         } else {
/*     */           
/* 199 */           first = false;
/*     */         } 
/* 201 */         switch (eventType) {
/*     */           case 1:
/* 203 */             this.fDepth++;
/* 204 */             this.fLocationWrapper.setLocation(input.getLocation());
/* 205 */             this.fNamespaceContext.setNamespaceContext(input.getNamespaceContext());
/* 206 */             fillQName(this.fElementQName, input.getNamespaceURI(), input
/* 207 */                 .getLocalName(), input.getPrefix());
/* 208 */             fillXMLAttributes(input);
/* 209 */             fillDeclaredPrefixes(input);
/* 210 */             addNamespaceDeclarations();
/* 211 */             this.fNamespaceContext.pushContext();
/* 212 */             this.fSchemaDOMParser.startElement(this.fElementQName, this.fAttributes, null);
/*     */           
/*     */           case 2:
/* 215 */             this.fLocationWrapper.setLocation(input.getLocation());
/* 216 */             this.fNamespaceContext.setNamespaceContext(input.getNamespaceContext());
/* 217 */             fillQName(this.fElementQName, input.getNamespaceURI(), input
/* 218 */                 .getLocalName(), input.getPrefix());
/* 219 */             fillDeclaredPrefixes(input);
/* 220 */             this.fSchemaDOMParser.endElement(this.fElementQName, null);
/* 221 */             this.fNamespaceContext.popContext();
/* 222 */             this.fDepth--;
/* 223 */             if (this.fDepth <= 0) {
/*     */               break;
/*     */             }
/*     */           
/*     */           case 4:
/* 228 */             this.fTempString.setValues(input.getTextCharacters(), input
/* 229 */                 .getTextStart(), input.getTextLength());
/* 230 */             this.fSchemaDOMParser.characters(this.fTempString, null);
/*     */           
/*     */           case 6:
/* 233 */             this.fTempString.setValues(input.getTextCharacters(), input
/* 234 */                 .getTextStart(), input.getTextLength());
/* 235 */             this.fSchemaDOMParser.ignorableWhitespace(this.fTempString, null);
/*     */           
/*     */           case 12:
/* 238 */             this.fSchemaDOMParser.startCDATA(null);
/* 239 */             this.fTempString.setValues(input.getTextCharacters(), input
/* 240 */                 .getTextStart(), input.getTextLength());
/* 241 */             this.fSchemaDOMParser.characters(this.fTempString, null);
/* 242 */             this.fSchemaDOMParser.endCDATA(null);
/*     */           
/*     */           case 3:
/* 245 */             fillProcessingInstruction(input.getPIData());
/* 246 */             this.fSchemaDOMParser.processingInstruction(input.getPITarget(), this.fTempString, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case 7:
/* 258 */             this.fDepth++;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       } 
/* 266 */       this.fLocationWrapper.setLocation(null);
/* 267 */       this.fNamespaceContext.setNamespaceContext(null);
/* 268 */       this.fSchemaDOMParser.endDocument(null);
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
/*     */   private void sendCharactersToSchemaParser(String str, boolean whitespace) {
/*     */     // Byte code:
/*     */     //   0: aload_1
/*     */     //   1: ifnull -> 156
/*     */     //   4: aload_1
/*     */     //   5: invokevirtual length : ()I
/*     */     //   8: istore_3
/*     */     //   9: iload_3
/*     */     //   10: sipush #1023
/*     */     //   13: iand
/*     */     //   14: istore #4
/*     */     //   16: iload #4
/*     */     //   18: ifle -> 78
/*     */     //   21: aload_1
/*     */     //   22: iconst_0
/*     */     //   23: iload #4
/*     */     //   25: aload_0
/*     */     //   26: getfield fCharBuffer : [C
/*     */     //   29: iconst_0
/*     */     //   30: invokevirtual getChars : (II[CI)V
/*     */     //   33: aload_0
/*     */     //   34: getfield fTempString : Lcom/sun/org/apache/xerces/internal/xni/XMLString;
/*     */     //   37: aload_0
/*     */     //   38: getfield fCharBuffer : [C
/*     */     //   41: iconst_0
/*     */     //   42: iload #4
/*     */     //   44: invokevirtual setValues : ([CII)V
/*     */     //   47: iload_2
/*     */     //   48: ifeq -> 66
/*     */     //   51: aload_0
/*     */     //   52: getfield fSchemaDOMParser : Lcom/sun/org/apache/xerces/internal/impl/xs/opti/SchemaDOMParser;
/*     */     //   55: aload_0
/*     */     //   56: getfield fTempString : Lcom/sun/org/apache/xerces/internal/xni/XMLString;
/*     */     //   59: aconst_null
/*     */     //   60: invokevirtual ignorableWhitespace : (Lcom/sun/org/apache/xerces/internal/xni/XMLString;Lcom/sun/org/apache/xerces/internal/xni/Augmentations;)V
/*     */     //   63: goto -> 78
/*     */     //   66: aload_0
/*     */     //   67: getfield fSchemaDOMParser : Lcom/sun/org/apache/xerces/internal/impl/xs/opti/SchemaDOMParser;
/*     */     //   70: aload_0
/*     */     //   71: getfield fTempString : Lcom/sun/org/apache/xerces/internal/xni/XMLString;
/*     */     //   74: aconst_null
/*     */     //   75: invokevirtual characters : (Lcom/sun/org/apache/xerces/internal/xni/XMLString;Lcom/sun/org/apache/xerces/internal/xni/Augmentations;)V
/*     */     //   78: iload #4
/*     */     //   80: istore #5
/*     */     //   82: iload #5
/*     */     //   84: iload_3
/*     */     //   85: if_icmpge -> 156
/*     */     //   88: aload_1
/*     */     //   89: iload #5
/*     */     //   91: wide iinc #5 1024
/*     */     //   97: iload #5
/*     */     //   99: aload_0
/*     */     //   100: getfield fCharBuffer : [C
/*     */     //   103: iconst_0
/*     */     //   104: invokevirtual getChars : (II[CI)V
/*     */     //   107: aload_0
/*     */     //   108: getfield fTempString : Lcom/sun/org/apache/xerces/internal/xni/XMLString;
/*     */     //   111: aload_0
/*     */     //   112: getfield fCharBuffer : [C
/*     */     //   115: iconst_0
/*     */     //   116: sipush #1024
/*     */     //   119: invokevirtual setValues : ([CII)V
/*     */     //   122: iload_2
/*     */     //   123: ifeq -> 141
/*     */     //   126: aload_0
/*     */     //   127: getfield fSchemaDOMParser : Lcom/sun/org/apache/xerces/internal/impl/xs/opti/SchemaDOMParser;
/*     */     //   130: aload_0
/*     */     //   131: getfield fTempString : Lcom/sun/org/apache/xerces/internal/xni/XMLString;
/*     */     //   134: aconst_null
/*     */     //   135: invokevirtual ignorableWhitespace : (Lcom/sun/org/apache/xerces/internal/xni/XMLString;Lcom/sun/org/apache/xerces/internal/xni/Augmentations;)V
/*     */     //   138: goto -> 82
/*     */     //   141: aload_0
/*     */     //   142: getfield fSchemaDOMParser : Lcom/sun/org/apache/xerces/internal/impl/xs/opti/SchemaDOMParser;
/*     */     //   145: aload_0
/*     */     //   146: getfield fTempString : Lcom/sun/org/apache/xerces/internal/xni/XMLString;
/*     */     //   149: aconst_null
/*     */     //   150: invokevirtual characters : (Lcom/sun/org/apache/xerces/internal/xni/XMLString;Lcom/sun/org/apache/xerces/internal/xni/Augmentations;)V
/*     */     //   153: goto -> 82
/*     */     //   156: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #274	-> 0
/*     */     //   #275	-> 4
/*     */     //   #276	-> 9
/*     */     //   #277	-> 16
/*     */     //   #278	-> 21
/*     */     //   #279	-> 33
/*     */     //   #280	-> 47
/*     */     //   #281	-> 51
/*     */     //   #284	-> 66
/*     */     //   #287	-> 78
/*     */     //   #288	-> 82
/*     */     //   #289	-> 88
/*     */     //   #290	-> 107
/*     */     //   #291	-> 122
/*     */     //   #292	-> 126
/*     */     //   #295	-> 141
/*     */     //   #299	-> 156
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   9	147	3	length	I
/*     */     //   16	140	4	remainder	I
/*     */     //   82	74	5	i	I
/*     */     //   0	157	0	this	Lcom/sun/org/apache/xerces/internal/impl/xs/traversers/StAXSchemaParser;
/*     */     //   0	157	1	str	Ljava/lang/String;
/*     */     //   0	157	2	whitespace	Z
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
/*     */   private void fillProcessingInstruction(String data) {
/* 303 */     int dataLength = data.length();
/* 304 */     char[] charBuffer = this.fCharBuffer;
/* 305 */     if (charBuffer.length < dataLength) {
/*     */ 
/*     */       
/* 308 */       charBuffer = data.toCharArray();
/*     */     } else {
/*     */       
/* 311 */       data.getChars(0, dataLength, charBuffer, 0);
/*     */     } 
/* 313 */     this.fTempString.setValues(charBuffer, 0, dataLength);
/*     */   }
/*     */   
/*     */   private void fillXMLAttributes(StartElement event) {
/* 317 */     this.fAttributes.removeAllAttributes();
/* 318 */     Iterator<Attribute> attrs = event.getAttributes();
/* 319 */     while (attrs.hasNext()) {
/* 320 */       Attribute attr = attrs.next();
/* 321 */       fillQName(this.fAttributeQName, attr.getName());
/* 322 */       String type = attr.getDTDType();
/* 323 */       int idx = this.fAttributes.getLength();
/* 324 */       this.fAttributes.addAttributeNS(this.fAttributeQName, (type != null) ? type : XMLSymbols.fCDATASymbol, attr
/* 325 */           .getValue());
/* 326 */       this.fAttributes.setSpecified(idx, attr.isSpecified());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void fillXMLAttributes(XMLStreamReader input) {
/* 331 */     this.fAttributes.removeAllAttributes();
/* 332 */     int len = input.getAttributeCount();
/* 333 */     for (int i = 0; i < len; i++) {
/* 334 */       fillQName(this.fAttributeQName, input.getAttributeNamespace(i), input
/* 335 */           .getAttributeLocalName(i), input.getAttributePrefix(i));
/* 336 */       String type = input.getAttributeType(i);
/* 337 */       this.fAttributes.addAttributeNS(this.fAttributeQName, (type != null) ? type : XMLSymbols.fCDATASymbol, input
/* 338 */           .getAttributeValue(i));
/* 339 */       this.fAttributes.setSpecified(i, input.isAttributeSpecified(i));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addNamespaceDeclarations() {
/* 344 */     String prefix = null;
/* 345 */     String localpart = null;
/* 346 */     String rawname = null;
/* 347 */     String nsPrefix = null;
/* 348 */     String nsURI = null;
/*     */     
/* 350 */     Iterator<String> iter = this.fDeclaredPrefixes.iterator();
/* 351 */     while (iter.hasNext()) {
/* 352 */       nsPrefix = iter.next();
/* 353 */       nsURI = this.fNamespaceContext.getURI(nsPrefix);
/* 354 */       if (nsPrefix.length() > 0) {
/* 355 */         prefix = XMLSymbols.PREFIX_XMLNS;
/* 356 */         localpart = nsPrefix;
/* 357 */         this.fStringBuffer.clear();
/* 358 */         this.fStringBuffer.append(prefix);
/* 359 */         this.fStringBuffer.append(':');
/* 360 */         this.fStringBuffer.append(localpart);
/* 361 */         rawname = this.fSymbolTable.addSymbol(this.fStringBuffer.ch, this.fStringBuffer.offset, this.fStringBuffer.length);
/*     */       } else {
/*     */         
/* 364 */         prefix = XMLSymbols.EMPTY_STRING;
/* 365 */         localpart = XMLSymbols.PREFIX_XMLNS;
/* 366 */         rawname = XMLSymbols.PREFIX_XMLNS;
/*     */       } 
/* 368 */       this.fAttributeQName.setValues(prefix, localpart, rawname, NamespaceContext.XMLNS_URI);
/* 369 */       this.fAttributes.addAttribute(this.fAttributeQName, XMLSymbols.fCDATASymbol, (nsURI != null) ? nsURI : XMLSymbols.EMPTY_STRING);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void fillDeclaredPrefixes(StartElement event) {
/* 376 */     fillDeclaredPrefixes(event.getNamespaces());
/*     */   }
/*     */ 
/*     */   
/*     */   private void fillDeclaredPrefixes(EndElement event) {
/* 381 */     fillDeclaredPrefixes(event.getNamespaces());
/*     */   }
/*     */ 
/*     */   
/*     */   private void fillDeclaredPrefixes(Iterator<Namespace> namespaces) {
/* 386 */     this.fDeclaredPrefixes.clear();
/* 387 */     while (namespaces.hasNext()) {
/* 388 */       Namespace ns = namespaces.next();
/* 389 */       String prefix = ns.getPrefix();
/* 390 */       this.fDeclaredPrefixes.add((prefix != null) ? prefix : "");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void fillDeclaredPrefixes(XMLStreamReader reader) {
/* 396 */     this.fDeclaredPrefixes.clear();
/* 397 */     int len = reader.getNamespaceCount();
/* 398 */     for (int i = 0; i < len; i++) {
/* 399 */       String prefix = reader.getNamespacePrefix(i);
/* 400 */       this.fDeclaredPrefixes.add((prefix != null) ? prefix : "");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void fillQName(QName toFill, QName toCopy) {
/* 406 */     fillQName(toFill, toCopy.getNamespaceURI(), toCopy.getLocalPart(), toCopy.getPrefix());
/*     */   }
/*     */ 
/*     */   
/*     */   final void fillQName(QName toFill, String uri, String localpart, String prefix) {
/* 411 */     uri = (uri != null && uri.length() > 0) ? this.fSymbolTable.addSymbol(uri) : null;
/* 412 */     localpart = (localpart != null) ? this.fSymbolTable.addSymbol(localpart) : XMLSymbols.EMPTY_STRING;
/* 413 */     prefix = (prefix != null && prefix.length() > 0) ? this.fSymbolTable.addSymbol(prefix) : XMLSymbols.EMPTY_STRING;
/* 414 */     String raw = localpart;
/* 415 */     if (prefix != XMLSymbols.EMPTY_STRING) {
/* 416 */       this.fStringBuffer.clear();
/* 417 */       this.fStringBuffer.append(prefix);
/* 418 */       this.fStringBuffer.append(':');
/* 419 */       this.fStringBuffer.append(localpart);
/* 420 */       raw = this.fSymbolTable.addSymbol(this.fStringBuffer.ch, this.fStringBuffer.offset, this.fStringBuffer.length);
/*     */     } 
/* 422 */     toFill.setValues(prefix, localpart, raw, uri);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/xs/traversers/StAXSchemaParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */