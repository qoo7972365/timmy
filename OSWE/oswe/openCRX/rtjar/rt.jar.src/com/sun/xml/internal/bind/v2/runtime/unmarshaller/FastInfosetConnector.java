/*     */ package com.sun.xml.internal.bind.v2.runtime.unmarshaller;
/*     */ 
/*     */ import com.sun.xml.internal.bind.WhiteSpaceProcessor;
/*     */ import com.sun.xml.internal.fastinfoset.stax.StAXDocumentParser;
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import org.xml.sax.Attributes;
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
/*     */ final class FastInfosetConnector
/*     */   extends StAXConnector
/*     */ {
/*     */   private final StAXDocumentParser fastInfosetStreamReader;
/*     */   private boolean textReported;
/*  54 */   private final Base64Data base64Data = new Base64Data();
/*     */ 
/*     */   
/*  57 */   private final StringBuilder buffer = new StringBuilder();
/*     */   private final CharSequenceImpl charArray;
/*     */   
/*     */   public FastInfosetConnector(StAXDocumentParser fastInfosetStreamReader, XmlVisitor visitor) {
/*  61 */     super(visitor);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 238 */     this.charArray = new CharSequenceImpl(); fastInfosetStreamReader.setStringInterning(true); this.fastInfosetStreamReader = fastInfosetStreamReader;
/*     */   } public void bridge() throws XMLStreamException { try { int depth = 0; int event = this.fastInfosetStreamReader.getEventType(); if (event == 7) while (!this.fastInfosetStreamReader.isStartElement()) event = this.fastInfosetStreamReader.next();   if (event != 1) throw new IllegalStateException("The current event is not START_ELEMENT\n but " + event);  handleStartDocument(this.fastInfosetStreamReader.getNamespaceContext()); while (true) { switch (event) { case 1: handleStartElement(); depth++; break;case 2: depth--; handleEndElement(); if (depth == 0) break;  break;case 4: case 6: case 12: if (this.predictor.expectText()) { event = this.fastInfosetStreamReader.peekNext(); if (event == 2) { processNonIgnorableText(); break; }  if (event == 1) { processIgnorableText(); break; }  handleFragmentedCharacters(); }  break; }  event = this.fastInfosetStreamReader.next(); }  this.fastInfosetStreamReader.next(); handleEndDocument(); } catch (SAXException e) { throw new XMLStreamException(e); }  } protected Location getCurrentLocation() { return this.fastInfosetStreamReader.getLocation(); }
/*     */   protected String getCurrentQName() { return this.fastInfosetStreamReader.getNameString(); }
/* 241 */   private void processNonIgnorableText() throws SAXException { this.textReported = true;
/*     */     
/* 243 */     boolean isTextAlgorithmAplied = (this.fastInfosetStreamReader.getTextAlgorithmBytes() != null);
/*     */     
/* 245 */     if (isTextAlgorithmAplied && this.fastInfosetStreamReader
/* 246 */       .getTextAlgorithmIndex() == 1)
/* 247 */     { this.base64Data.set(this.fastInfosetStreamReader.getTextAlgorithmBytesClone(), null);
/* 248 */       this.visitor.text((CharSequence)this.base64Data); }
/*     */     else
/* 250 */     { if (isTextAlgorithmAplied) {
/* 251 */         this.fastInfosetStreamReader.getText();
/*     */       }
/*     */       
/* 254 */       this.charArray.set();
/* 255 */       this.visitor.text(this.charArray); }  }
/*     */   private void handleStartElement() throws SAXException { processUnreportedText(); for (int i = 0; i < this.fastInfosetStreamReader.accessNamespaceCount(); i++)
/*     */       this.visitor.startPrefixMapping(this.fastInfosetStreamReader.getNamespacePrefix(i), this.fastInfosetStreamReader.getNamespaceURI(i));  this.tagName.uri = this.fastInfosetStreamReader.accessNamespaceURI(); this.tagName.local = this.fastInfosetStreamReader.accessLocalName(); this.tagName.atts = (Attributes)this.fastInfosetStreamReader.getAttributesHolder(); this.visitor.startElement(this.tagName); }
/*     */   private void handleFragmentedCharacters() throws XMLStreamException, SAXException { this.buffer.setLength(0); this.buffer.append(this.fastInfosetStreamReader.getTextCharacters(), this.fastInfosetStreamReader.getTextStart(), this.fastInfosetStreamReader.getTextLength()); while (true) { switch (this.fastInfosetStreamReader.peekNext()) { case 1: processBufferedText(true); return;
/*     */         case 2: processBufferedText(false); return;
/*     */         case 4: case 6: case 12:
/* 261 */           this.fastInfosetStreamReader.next(); this.buffer.append(this.fastInfosetStreamReader.getTextCharacters(), this.fastInfosetStreamReader.getTextStart(), this.fastInfosetStreamReader.getTextLength()); continue; }  this.fastInfosetStreamReader.next(); }  } private void processIgnorableText() throws SAXException { boolean isTextAlgorithmAplied = (this.fastInfosetStreamReader.getTextAlgorithmBytes() != null);
/*     */     
/* 263 */     if (isTextAlgorithmAplied && this.fastInfosetStreamReader
/* 264 */       .getTextAlgorithmIndex() == 1)
/* 265 */     { this.base64Data.set(this.fastInfosetStreamReader.getTextAlgorithmBytesClone(), null);
/* 266 */       this.visitor.text((CharSequence)this.base64Data);
/* 267 */       this.textReported = true; }
/*     */     else
/* 269 */     { if (isTextAlgorithmAplied) {
/* 270 */         this.fastInfosetStreamReader.getText();
/*     */       }
/*     */       
/* 273 */       this.charArray.set();
/* 274 */       if (!WhiteSpaceProcessor.isWhiteSpace(this.charArray))
/* 275 */       { this.visitor.text(this.charArray);
/* 276 */         this.textReported = true; }  }  }
/*     */   private void handleEndElement() throws SAXException { processUnreportedText(); this.tagName.uri = this.fastInfosetStreamReader.accessNamespaceURI(); this.tagName.local = this.fastInfosetStreamReader.accessLocalName(); this.visitor.endElement(this.tagName); for (int i = this.fastInfosetStreamReader.accessNamespaceCount() - 1; i >= 0; i--) this.visitor.endPrefixMapping(this.fastInfosetStreamReader.getNamespacePrefix(i));  }
/*     */   private final class CharSequenceImpl implements CharSequence {
/*     */     char[] ch;
/*     */     int start;
/*     */     int length;
/* 282 */     CharSequenceImpl() {} CharSequenceImpl(char[] ch, int start, int length) { this.ch = ch; this.start = start; this.length = length; } public void set() { this.ch = FastInfosetConnector.this.fastInfosetStreamReader.getTextCharacters(); this.start = FastInfosetConnector.this.fastInfosetStreamReader.getTextStart(); this.length = FastInfosetConnector.this.fastInfosetStreamReader.getTextLength(); } public final int length() { return this.length; } public final char charAt(int index) { return this.ch[this.start + index]; } public final CharSequence subSequence(int start, int end) { return new CharSequenceImpl(this.ch, this.start + start, end - start); } public String toString() { return new String(this.ch, this.start, this.length); } } private void processBufferedText(boolean ignorable) throws SAXException { if (!ignorable || !WhiteSpaceProcessor.isWhiteSpace(this.buffer)) {
/* 283 */       this.visitor.text(this.buffer);
/* 284 */       this.textReported = true;
/*     */     }  }
/*     */ 
/*     */   
/*     */   private void processUnreportedText() throws SAXException {
/* 289 */     if (!this.textReported && this.predictor.expectText()) {
/* 290 */       this.visitor.text("");
/*     */     }
/* 292 */     this.textReported = false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/unmarshaller/FastInfosetConnector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */