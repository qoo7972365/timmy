/*     */ package com.sun.xml.internal.fastinfoset.sax;
/*     */ 
/*     */ import com.sun.xml.internal.fastinfoset.CommonResourceBundle;
/*     */ import com.sun.xml.internal.fastinfoset.Encoder;
/*     */ import com.sun.xml.internal.fastinfoset.QualifiedName;
/*     */ import com.sun.xml.internal.fastinfoset.util.LocalNameQualifiedNamesMap;
/*     */ import com.sun.xml.internal.org.jvnet.fastinfoset.FastInfosetException;
/*     */ import com.sun.xml.internal.org.jvnet.fastinfoset.sax.EncodingAlgorithmAttributes;
/*     */ import com.sun.xml.internal.org.jvnet.fastinfoset.sax.FastInfosetWriter;
/*     */ import java.io.IOException;
/*     */ import org.xml.sax.Attributes;
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
/*     */ public class SAXDocumentSerializer
/*     */   extends Encoder
/*     */   implements FastInfosetWriter
/*     */ {
/*     */   protected boolean _elementHasNamespaces = false;
/*     */   protected boolean _charactersAsCDATA = false;
/*     */   
/*     */   protected SAXDocumentSerializer(boolean v) {
/*  66 */     super(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public SAXDocumentSerializer() {}
/*     */ 
/*     */   
/*     */   public void reset() {
/*  74 */     super.reset();
/*     */     
/*  76 */     this._elementHasNamespaces = false;
/*  77 */     this._charactersAsCDATA = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void startDocument() throws SAXException {
/*     */     try {
/*  84 */       reset();
/*  85 */       encodeHeader(false);
/*  86 */       encodeInitialVocabulary();
/*  87 */     } catch (IOException e) {
/*  88 */       throw new SAXException("startDocument", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void endDocument() throws SAXException {
/*     */     try {
/*  94 */       encodeDocumentTermination();
/*  95 */     } catch (IOException e) {
/*  96 */       throw new SAXException("endDocument", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void startPrefixMapping(String prefix, String uri) throws SAXException {
/*     */     try {
/* 102 */       if (!this._elementHasNamespaces) {
/* 103 */         encodeTermination();
/*     */ 
/*     */         
/* 106 */         mark();
/* 107 */         this._elementHasNamespaces = true;
/*     */ 
/*     */         
/* 110 */         write(56);
/*     */       } 
/*     */       
/* 113 */       encodeNamespaceAttribute(prefix, uri);
/* 114 */     } catch (IOException e) {
/* 115 */       throw new SAXException("startElement", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
/* 122 */     int attributeCount = (atts != null && atts.getLength() > 0) ? countAttributes(atts) : 0;
/*     */     try {
/* 124 */       if (this._elementHasNamespaces) {
/* 125 */         this._elementHasNamespaces = false;
/*     */         
/* 127 */         if (attributeCount > 0)
/*     */         {
/* 129 */           this._octetBuffer[this._markIndex] = (byte)(this._octetBuffer[this._markIndex] | 0x40);
/*     */         }
/* 131 */         resetMark();
/*     */         
/* 133 */         write(240);
/*     */         
/* 135 */         this._b = 0;
/*     */       } else {
/* 137 */         encodeTermination();
/*     */         
/* 139 */         this._b = 0;
/* 140 */         if (attributeCount > 0) {
/* 141 */           this._b |= 0x40;
/*     */         }
/*     */       } 
/*     */       
/* 145 */       encodeElement(namespaceURI, qName, localName);
/*     */       
/* 147 */       if (attributeCount > 0) {
/* 148 */         encodeAttributes(atts);
/*     */       }
/* 150 */     } catch (IOException e) {
/* 151 */       throw new SAXException("startElement", e);
/* 152 */     } catch (FastInfosetException e) {
/* 153 */       throw new SAXException("startElement", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void endElement(String namespaceURI, String localName, String qName) throws SAXException {
/*     */     try {
/* 159 */       encodeElementTermination();
/* 160 */     } catch (IOException e) {
/* 161 */       throw new SAXException("endElement", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void characters(char[] ch, int start, int length) throws SAXException {
/* 166 */     if (length <= 0) {
/*     */       return;
/*     */     }
/*     */     
/* 170 */     if (getIgnoreWhiteSpaceTextContent() && 
/* 171 */       isWhiteSpace(ch, start, length))
/*     */       return; 
/*     */     try {
/* 174 */       encodeTermination();
/*     */       
/* 176 */       if (!this._charactersAsCDATA) {
/* 177 */         encodeCharacters(ch, start, length);
/*     */       } else {
/* 179 */         encodeCIIBuiltInAlgorithmDataAsCDATA(ch, start, length);
/*     */       } 
/* 181 */     } catch (IOException e) {
/* 182 */       throw new SAXException(e);
/* 183 */     } catch (FastInfosetException e) {
/* 184 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
/* 189 */     if (getIgnoreWhiteSpaceTextContent())
/*     */       return; 
/* 191 */     characters(ch, start, length);
/*     */   }
/*     */   
/*     */   public final void processingInstruction(String target, String data) throws SAXException {
/*     */     try {
/* 196 */       if (getIgnoreProcesingInstructions())
/*     */         return; 
/* 198 */       if (target.length() == 0) {
/* 199 */         throw new SAXException(CommonResourceBundle.getInstance()
/* 200 */             .getString("message.processingInstructionTargetIsEmpty"));
/*     */       }
/* 202 */       encodeTermination();
/*     */       
/* 204 */       encodeProcessingInstruction(target, data);
/* 205 */     } catch (IOException e) {
/* 206 */       throw new SAXException("processingInstruction", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setDocumentLocator(Locator locator) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public final void skippedEntity(String name) throws SAXException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public final void comment(char[] ch, int start, int length) throws SAXException {
/*     */     try {
/* 222 */       if (getIgnoreComments())
/*     */         return; 
/* 224 */       encodeTermination();
/*     */       
/* 226 */       encodeComment(ch, start, length);
/* 227 */     } catch (IOException e) {
/* 228 */       throw new SAXException("startElement", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void startCDATA() throws SAXException {
/* 233 */     this._charactersAsCDATA = true;
/*     */   }
/*     */   
/*     */   public final void endCDATA() throws SAXException {
/* 237 */     this._charactersAsCDATA = false;
/*     */   }
/*     */   
/*     */   public final void startDTD(String name, String publicId, String systemId) throws SAXException {
/* 241 */     if (getIgnoreDTD())
/*     */       return; 
/*     */     try {
/* 244 */       encodeTermination();
/*     */       
/* 246 */       encodeDocumentTypeDeclaration(publicId, systemId);
/* 247 */       encodeElementTermination();
/* 248 */     } catch (IOException e) {
/* 249 */       throw new SAXException("startDTD", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void endDTD() throws SAXException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public final void startEntity(String name) throws SAXException {}
/*     */ 
/*     */   
/*     */   public final void endEntity(String name) throws SAXException {}
/*     */ 
/*     */   
/*     */   public final void octets(String URI, int id, byte[] b, int start, int length) throws SAXException {
/* 266 */     if (length <= 0) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 271 */       encodeTermination();
/*     */       
/* 273 */       encodeNonIdentifyingStringOnThirdBit(URI, id, b, start, length);
/* 274 */     } catch (IOException e) {
/* 275 */       throw new SAXException(e);
/* 276 */     } catch (FastInfosetException e) {
/* 277 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void object(String URI, int id, Object data) throws SAXException {
/*     */     try {
/* 283 */       encodeTermination();
/*     */       
/* 285 */       encodeNonIdentifyingStringOnThirdBit(URI, id, data);
/* 286 */     } catch (IOException e) {
/* 287 */       throw new SAXException(e);
/* 288 */     } catch (FastInfosetException e) {
/* 289 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void bytes(byte[] b, int start, int length) throws SAXException {
/* 297 */     if (length <= 0) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 302 */       encodeTermination();
/*     */       
/* 304 */       encodeCIIOctetAlgorithmData(1, b, start, length);
/* 305 */     } catch (IOException e) {
/* 306 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void shorts(short[] s, int start, int length) throws SAXException {
/* 311 */     if (length <= 0) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 316 */       encodeTermination();
/*     */       
/* 318 */       encodeCIIBuiltInAlgorithmData(2, s, start, length);
/* 319 */     } catch (IOException e) {
/* 320 */       throw new SAXException(e);
/* 321 */     } catch (FastInfosetException e) {
/* 322 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void ints(int[] i, int start, int length) throws SAXException {
/* 327 */     if (length <= 0) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 332 */       encodeTermination();
/*     */       
/* 334 */       encodeCIIBuiltInAlgorithmData(3, i, start, length);
/* 335 */     } catch (IOException e) {
/* 336 */       throw new SAXException(e);
/* 337 */     } catch (FastInfosetException e) {
/* 338 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void longs(long[] l, int start, int length) throws SAXException {
/* 343 */     if (length <= 0) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 348 */       encodeTermination();
/*     */       
/* 350 */       encodeCIIBuiltInAlgorithmData(4, l, start, length);
/* 351 */     } catch (IOException e) {
/* 352 */       throw new SAXException(e);
/* 353 */     } catch (FastInfosetException e) {
/* 354 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void booleans(boolean[] b, int start, int length) throws SAXException {
/* 359 */     if (length <= 0) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 364 */       encodeTermination();
/*     */       
/* 366 */       encodeCIIBuiltInAlgorithmData(5, b, start, length);
/* 367 */     } catch (IOException e) {
/* 368 */       throw new SAXException(e);
/* 369 */     } catch (FastInfosetException e) {
/* 370 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void floats(float[] f, int start, int length) throws SAXException {
/* 375 */     if (length <= 0) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 380 */       encodeTermination();
/*     */       
/* 382 */       encodeCIIBuiltInAlgorithmData(6, f, start, length);
/* 383 */     } catch (IOException e) {
/* 384 */       throw new SAXException(e);
/* 385 */     } catch (FastInfosetException e) {
/* 386 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void doubles(double[] d, int start, int length) throws SAXException {
/* 391 */     if (length <= 0) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 396 */       encodeTermination();
/*     */       
/* 398 */       encodeCIIBuiltInAlgorithmData(7, d, start, length);
/* 399 */     } catch (IOException e) {
/* 400 */       throw new SAXException(e);
/* 401 */     } catch (FastInfosetException e) {
/* 402 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void uuids(long[] msblsb, int start, int length) throws SAXException {
/* 407 */     if (length <= 0) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 412 */       encodeTermination();
/*     */       
/* 414 */       encodeCIIBuiltInAlgorithmData(8, msblsb, start, length);
/* 415 */     } catch (IOException e) {
/* 416 */       throw new SAXException(e);
/* 417 */     } catch (FastInfosetException e) {
/* 418 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void numericCharacters(char[] ch, int start, int length) throws SAXException {
/* 426 */     if (length <= 0) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 431 */       encodeTermination();
/*     */       
/* 433 */       boolean addToTable = isCharacterContentChunkLengthMatchesLimit(length);
/* 434 */       encodeNumericFourBitCharacters(ch, start, length, addToTable);
/* 435 */     } catch (IOException e) {
/* 436 */       throw new SAXException(e);
/* 437 */     } catch (FastInfosetException e) {
/* 438 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void dateTimeCharacters(char[] ch, int start, int length) throws SAXException {
/* 443 */     if (length <= 0) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 448 */       encodeTermination();
/*     */       
/* 450 */       boolean addToTable = isCharacterContentChunkLengthMatchesLimit(length);
/* 451 */       encodeDateTimeFourBitCharacters(ch, start, length, addToTable);
/* 452 */     } catch (IOException e) {
/* 453 */       throw new SAXException(e);
/* 454 */     } catch (FastInfosetException e) {
/* 455 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void alphabetCharacters(String alphabet, char[] ch, int start, int length) throws SAXException {
/* 460 */     if (length <= 0) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 465 */       encodeTermination();
/*     */       
/* 467 */       boolean addToTable = isCharacterContentChunkLengthMatchesLimit(length);
/* 468 */       encodeAlphabetCharacters(alphabet, ch, start, length, addToTable);
/* 469 */     } catch (IOException e) {
/* 470 */       throw new SAXException(e);
/* 471 */     } catch (FastInfosetException e) {
/* 472 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void characters(char[] ch, int start, int length, boolean index) throws SAXException {
/* 479 */     if (length <= 0) {
/*     */       return;
/*     */     }
/*     */     
/* 483 */     if (getIgnoreWhiteSpaceTextContent() && 
/* 484 */       isWhiteSpace(ch, start, length))
/*     */       return; 
/*     */     try {
/* 487 */       encodeTermination();
/*     */       
/* 489 */       if (!this._charactersAsCDATA) {
/* 490 */         encodeNonIdentifyingStringOnThirdBit(ch, start, length, this._v.characterContentChunk, index, true);
/*     */       } else {
/* 492 */         encodeCIIBuiltInAlgorithmDataAsCDATA(ch, start, length);
/*     */       } 
/* 494 */     } catch (IOException e) {
/* 495 */       throw new SAXException(e);
/* 496 */     } catch (FastInfosetException e) {
/* 497 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int countAttributes(Attributes atts) {
/* 506 */     int count = 0;
/* 507 */     for (int i = 0; i < atts.getLength(); i++) {
/* 508 */       String uri = atts.getURI(i);
/* 509 */       if (uri != "http://www.w3.org/2000/xmlns/" && !uri.equals("http://www.w3.org/2000/xmlns/"))
/*     */       {
/*     */         
/* 512 */         count++; } 
/*     */     } 
/* 514 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void encodeAttributes(Attributes atts) throws IOException, FastInfosetException {
/* 521 */     if (atts instanceof EncodingAlgorithmAttributes) {
/* 522 */       EncodingAlgorithmAttributes eAtts = (EncodingAlgorithmAttributes)atts;
/*     */ 
/*     */       
/* 525 */       for (int i = 0; i < eAtts.getLength(); i++) {
/* 526 */         if (encodeAttribute(atts.getURI(i), atts.getQName(i), atts.getLocalName(i))) {
/* 527 */           Object data = eAtts.getAlgorithmData(i);
/*     */           
/* 529 */           if (data == null) {
/* 530 */             String value = eAtts.getValue(i);
/* 531 */             boolean addToTable = isAttributeValueLengthMatchesLimit(value.length());
/* 532 */             boolean mustBeAddedToTable = eAtts.getToIndex(i);
/*     */             
/* 534 */             String alphabet = eAtts.getAlpababet(i);
/* 535 */             if (alphabet == null) {
/* 536 */               encodeNonIdentifyingStringOnFirstBit(value, this._v.attributeValue, addToTable, mustBeAddedToTable);
/* 537 */             } else if (alphabet == "0123456789-:TZ ") {
/* 538 */               encodeDateTimeNonIdentifyingStringOnFirstBit(value, addToTable, mustBeAddedToTable);
/*     */             }
/* 540 */             else if (alphabet == "0123456789-+.E ") {
/* 541 */               encodeNumericNonIdentifyingStringOnFirstBit(value, addToTable, mustBeAddedToTable);
/*     */             } else {
/*     */               
/* 544 */               encodeNonIdentifyingStringOnFirstBit(value, this._v.attributeValue, addToTable, mustBeAddedToTable);
/*     */             } 
/*     */           } else {
/* 547 */             encodeNonIdentifyingStringOnFirstBit(eAtts.getAlgorithmURI(i), eAtts
/* 548 */                 .getAlgorithmIndex(i), data);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/* 553 */       for (int i = 0; i < atts.getLength(); i++) {
/* 554 */         if (encodeAttribute(atts.getURI(i), atts.getQName(i), atts.getLocalName(i))) {
/* 555 */           String value = atts.getValue(i);
/* 556 */           boolean addToTable = isAttributeValueLengthMatchesLimit(value.length());
/* 557 */           encodeNonIdentifyingStringOnFirstBit(value, this._v.attributeValue, addToTable, false);
/*     */         } 
/*     */       } 
/*     */     } 
/* 561 */     this._b = 240;
/* 562 */     this._terminate = true;
/*     */   }
/*     */   
/*     */   protected void encodeElement(String namespaceURI, String qName, String localName) throws IOException {
/* 566 */     LocalNameQualifiedNamesMap.Entry entry = this._v.elementName.obtainEntry(qName);
/* 567 */     if (entry._valueIndex > 0) {
/* 568 */       QualifiedName[] names = entry._value;
/* 569 */       for (int i = 0; i < entry._valueIndex; i++) {
/* 570 */         QualifiedName n = names[i];
/* 571 */         if (namespaceURI == n.namespaceName || namespaceURI.equals(n.namespaceName)) {
/* 572 */           encodeNonZeroIntegerOnThirdBit((names[i]).index);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/* 578 */     encodeLiteralElementQualifiedNameOnThirdBit(namespaceURI, getPrefixFromQualifiedName(qName), localName, entry);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean encodeAttribute(String namespaceURI, String qName, String localName) throws IOException {
/* 583 */     LocalNameQualifiedNamesMap.Entry entry = this._v.attributeName.obtainEntry(qName);
/* 584 */     if (entry._valueIndex > 0) {
/* 585 */       QualifiedName[] names = entry._value;
/* 586 */       for (int i = 0; i < entry._valueIndex; i++) {
/* 587 */         if (namespaceURI == (names[i]).namespaceName || namespaceURI.equals((names[i]).namespaceName)) {
/* 588 */           encodeNonZeroIntegerOnSecondBitFirstBitZero((names[i]).index);
/* 589 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 594 */     return encodeLiteralAttributeQualifiedNameOnSecondBit(namespaceURI, getPrefixFromQualifiedName(qName), localName, entry);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/sax/SAXDocumentSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */