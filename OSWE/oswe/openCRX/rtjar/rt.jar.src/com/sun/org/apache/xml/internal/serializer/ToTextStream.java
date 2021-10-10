/*     */ package com.sun.org.apache.xml.internal.serializer;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.serializer.utils.Utils;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ToTextStream
/*     */   extends ToStream
/*     */ {
/*     */   protected void startDocumentInternal() throws SAXException {
/*  67 */     super.startDocumentInternal();
/*     */     
/*  69 */     this.m_needToCallStartDocument = false;
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
/*     */   public void endDocument() throws SAXException {
/*  90 */     flushPending();
/*  91 */     flushWriter();
/*  92 */     if (this.m_tracer != null) {
/*  93 */       fireEndDoc();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(String namespaceURI, String localName, String name, Attributes atts) throws SAXException {
/* 133 */     if (this.m_tracer != null) {
/* 134 */       fireStartElem(name);
/* 135 */       firePseudoAttributes();
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
/*     */   
/*     */   public void endElement(String namespaceURI, String localName, String name) throws SAXException {
/* 168 */     if (this.m_tracer != null) {
/* 169 */       fireEndElem(name);
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
/*     */   public void characters(char[] ch, int start, int length) throws SAXException {
/* 201 */     flushPending();
/*     */ 
/*     */     
/*     */     try {
/* 205 */       if (inTemporaryOutputState()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 217 */         this.m_writer.write(ch, start, length);
/*     */       }
/*     */       else {
/*     */         
/* 221 */         writeNormalizedChars(ch, start, length, this.m_lineSepUse);
/*     */       } 
/*     */       
/* 224 */       if (this.m_tracer != null) {
/* 225 */         fireCharEvent(ch, start, length);
/*     */       }
/* 227 */     } catch (IOException ioe) {
/*     */       
/* 229 */       throw new SAXException(ioe);
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
/*     */   public void charactersRaw(char[] ch, int start, int length) throws SAXException {
/*     */     try {
/* 250 */       writeNormalizedChars(ch, start, length, this.m_lineSepUse);
/*     */     }
/* 252 */     catch (IOException ioe) {
/*     */       
/* 254 */       throw new SAXException(ioe);
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
/*     */   void writeNormalizedChars(char[] ch, int start, int length, boolean useLineSep) throws IOException, SAXException {
/* 279 */     String encoding = getEncoding();
/* 280 */     Writer writer = this.m_writer;
/* 281 */     int end = start + length;
/*     */ 
/*     */     
/* 284 */     char S_LINEFEED = '\n';
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 290 */     for (int i = start; i < end; i++) {
/* 291 */       char c = ch[i];
/*     */       
/* 293 */       if ('\n' == c && useLineSep) {
/* 294 */         writer.write(this.m_lineSep, 0, this.m_lineSepLen);
/*     */       }
/* 296 */       else if (this.m_encodingInfo.isInEncoding(c)) {
/* 297 */         writer.write(c);
/*     */       }
/* 299 */       else if (Encodings.isHighUTF16Surrogate(c) || 
/* 300 */         Encodings.isLowUTF16Surrogate(c)) {
/* 301 */         int codePoint = writeUTF16Surrogate(c, ch, i, end);
/* 302 */         if (codePoint >= 0)
/*     */         {
/*     */           
/* 305 */           if (Encodings.isHighUTF16Surrogate(c)) {
/* 306 */             i++;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 311 */           if (codePoint > 0)
/*     */           {
/*     */             
/* 314 */             String integralValue = Integer.toString(codePoint);
/* 315 */             String msg = Utils.messages.createMessage("ER_ILLEGAL_CHARACTER", new Object[] { integralValue, encoding });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 322 */             System.err.println(msg);
/*     */           
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 329 */       else if (encoding != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 335 */         writer.write(38);
/* 336 */         writer.write(35);
/* 337 */         writer.write(Integer.toString(c));
/* 338 */         writer.write(59);
/*     */ 
/*     */ 
/*     */         
/* 342 */         String integralValue = Integer.toString(c);
/* 343 */         String msg = Utils.messages.createMessage("ER_ILLEGAL_CHARACTER", new Object[] { integralValue, encoding });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 350 */         System.err.println(msg);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 355 */         writer.write(c);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cdata(char[] ch, int start, int length) throws SAXException {
/*     */     try {
/* 393 */       writeNormalizedChars(ch, start, length, this.m_lineSepUse);
/* 394 */       if (this.m_tracer != null) {
/* 395 */         fireCDATAEvent(ch, start, length);
/*     */       }
/* 397 */     } catch (IOException ioe) {
/*     */       
/* 399 */       throw new SAXException(ioe);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
/*     */     try {
/* 435 */       writeNormalizedChars(ch, start, length, this.m_lineSepUse);
/*     */     }
/* 437 */     catch (IOException ioe) {
/*     */       
/* 439 */       throw new SAXException(ioe);
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
/*     */   public void processingInstruction(String target, String data) throws SAXException {
/* 466 */     flushPending();
/*     */     
/* 468 */     if (this.m_tracer != null) {
/* 469 */       fireEscapingEvent(target, data);
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
/*     */   public void comment(String data) throws SAXException {
/* 483 */     int length = data.length();
/* 484 */     if (length > this.m_charsBuff.length)
/*     */     {
/* 486 */       this.m_charsBuff = new char[length * 2 + 1];
/*     */     }
/* 488 */     data.getChars(0, length, this.m_charsBuff, 0);
/* 489 */     comment(this.m_charsBuff, 0, length);
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
/*     */   public void comment(char[] ch, int start, int length) throws SAXException {
/* 508 */     flushPending();
/* 509 */     if (this.m_tracer != null) {
/* 510 */       fireCommentEvent(ch, start, length);
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
/*     */   public void entityReference(String name) throws SAXException {
/* 522 */     if (this.m_tracer != null) {
/* 523 */       fireEntityReference(name);
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
/*     */   public void addAttribute(String uri, String localName, String rawName, String type, String value, boolean XSLAttribute) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endCDATA() throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endElement(String elemName) throws SAXException {
/* 553 */     if (this.m_tracer != null) {
/* 554 */       fireEndElem(elemName);
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
/*     */   public void startElement(String elementNamespaceURI, String elementLocalName, String elementName) throws SAXException {
/* 566 */     if (this.m_needToCallStartDocument) {
/* 567 */       startDocumentInternal();
/*     */     }
/* 569 */     if (this.m_tracer != null) {
/* 570 */       fireStartElem(elementName);
/* 571 */       firePseudoAttributes();
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
/*     */   public void characters(String characters) throws SAXException {
/* 584 */     int length = characters.length();
/* 585 */     if (length > this.m_charsBuff.length)
/*     */     {
/* 587 */       this.m_charsBuff = new char[length * 2 + 1];
/*     */     }
/* 589 */     characters.getChars(0, length, this.m_charsBuff, 0);
/* 590 */     characters(this.m_charsBuff, 0, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAttribute(String name, String value) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addUniqueAttribute(String qName, String value, int flags) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean startPrefixMapping(String prefix, String uri, boolean shouldFlush) throws SAXException {
/* 618 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startPrefixMapping(String prefix, String uri) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void namespaceAfterStartElement(String prefix, String uri) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flushPending() throws SAXException {
/* 639 */     if (this.m_needToCallStartDocument) {
/*     */       
/* 641 */       startDocumentInternal();
/* 642 */       this.m_needToCallStartDocument = false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/serializer/ToTextStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */