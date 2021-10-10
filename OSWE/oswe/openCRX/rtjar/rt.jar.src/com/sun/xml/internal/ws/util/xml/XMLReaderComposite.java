/*     */ package com.sun.xml.internal.ws.util.xml;
/*     */ 
/*     */ import com.sun.xml.internal.org.jvnet.staxex.NamespaceContextEx;
/*     */ import com.sun.xml.internal.org.jvnet.staxex.XMLStreamReaderEx;
/*     */ import com.sun.xml.internal.ws.encoding.TagInfoset;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLReaderComposite
/*     */   implements XMLStreamReaderEx
/*     */ {
/*     */   public enum State
/*     */   {
/*  49 */     StartTag, Payload, EndTag;
/*     */   }
/*  51 */   protected State state = State.StartTag; protected ElemInfo elemInfo;
/*     */   protected TagInfoset tagInfo;
/*     */   protected XMLStreamReader[] children;
/*     */   protected XMLStreamReader payloadReader;
/*  55 */   protected int payloadIndex = -1;
/*     */   
/*     */   public static class ElemInfo implements NamespaceContext { ElemInfo ancestor;
/*     */     TagInfoset tagInfo;
/*     */     
/*     */     public ElemInfo(TagInfoset tag, ElemInfo parent) {
/*  61 */       this.tagInfo = tag; this.ancestor = parent;
/*     */     } public String getNamespaceURI(String prefix) {
/*  63 */       String n = this.tagInfo.getNamespaceURI(prefix);
/*  64 */       return (n != null) ? n : ((this.ancestor != null) ? this.ancestor.getNamespaceURI(prefix) : null);
/*     */     }
/*     */     public String getPrefix(String uri) {
/*  67 */       String p = this.tagInfo.getPrefix(uri);
/*  68 */       return (p != null) ? p : ((this.ancestor != null) ? this.ancestor.getPrefix(uri) : null);
/*     */     }
/*     */     
/*     */     public List<String> allPrefixes(String namespaceURI) {
/*  72 */       List<String> l = this.tagInfo.allPrefixes(namespaceURI);
/*  73 */       if (this.ancestor != null) {
/*  74 */         List<String> p = this.ancestor.allPrefixes(namespaceURI);
/*  75 */         p.addAll(l);
/*  76 */         return p;
/*     */       } 
/*  78 */       return l;
/*     */     }
/*     */     public Iterator<String> getPrefixes(String namespaceURI) {
/*  81 */       return allPrefixes(namespaceURI).iterator();
/*     */     } }
/*     */ 
/*     */   
/*     */   public XMLReaderComposite(ElemInfo elem, XMLStreamReader[] wrapees) {
/*  86 */     this.elemInfo = elem;
/*  87 */     this.tagInfo = elem.tagInfo;
/*  88 */     this.children = wrapees;
/*  89 */     if (this.children != null && this.children.length > 0) {
/*  90 */       this.payloadIndex = 0;
/*  91 */       this.payloadReader = this.children[this.payloadIndex];
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int next() throws XMLStreamException {
/*  98 */     switch (this.state) {
/*     */       case StartTag:
/* 100 */         if (this.payloadReader != null) {
/* 101 */           this.state = State.Payload;
/* 102 */           return this.payloadReader.getEventType();
/*     */         } 
/* 104 */         this.state = State.EndTag;
/* 105 */         return 2;
/*     */       case EndTag:
/* 107 */         return 8;
/*     */     } 
/*     */     
/* 110 */     int next = 8;
/* 111 */     if (this.payloadReader != null && this.payloadReader.hasNext()) {
/* 112 */       next = this.payloadReader.next();
/*     */     }
/* 114 */     if (next != 8) return next;
/*     */     
/* 116 */     if (this.payloadIndex + 1 < this.children.length) {
/* 117 */       this.payloadIndex++;
/* 118 */       this.payloadReader = this.children[this.payloadIndex];
/* 119 */       return this.payloadReader.getEventType();
/*     */     } 
/* 121 */     this.state = State.EndTag;
/* 122 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNext() throws XMLStreamException {
/* 130 */     switch (this.state) { case EndTag:
/* 131 */         return false; }
/*     */ 
/*     */     
/* 134 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getElementText() throws XMLStreamException {
/* 140 */     switch (this.state) {
/*     */       case StartTag:
/* 142 */         if (this.payloadReader.isCharacters()) return this.payloadReader.getText(); 
/* 143 */         return "";
/*     */     } 
/*     */     
/* 146 */     return this.payloadReader.getElementText();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int nextTag() throws XMLStreamException {
/* 152 */     int e = next();
/* 153 */     if (e == 8) return e; 
/* 154 */     while (e != 8) {
/* 155 */       if (e == 1) return e; 
/* 156 */       if (e == 2) return e; 
/* 157 */       e = next();
/*     */     } 
/* 159 */     return e;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getProperty(String name) throws IllegalArgumentException {
/* 164 */     return (this.payloadReader != null) ? this.payloadReader.getProperty(name) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void require(int type, String namespaceURI, String localName) throws XMLStreamException {
/* 169 */     if (this.payloadReader != null) this.payloadReader.require(type, namespaceURI, localName);
/*     */   
/*     */   }
/*     */   
/*     */   public void close() throws XMLStreamException {
/* 174 */     if (this.payloadReader != null) this.payloadReader.close();
/*     */   
/*     */   }
/*     */   
/*     */   public String getNamespaceURI(String prefix) {
/* 179 */     switch (this.state) {
/*     */       case StartTag:
/*     */       case EndTag:
/* 182 */         return this.elemInfo.getNamespaceURI(prefix);
/*     */     } 
/*     */     
/* 185 */     return this.payloadReader.getNamespaceURI(prefix);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStartElement() {
/* 191 */     switch (this.state) { case StartTag:
/* 192 */         return true;
/* 193 */       case EndTag: return false; }
/*     */ 
/*     */     
/* 196 */     return this.payloadReader.isStartElement();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEndElement() {
/* 202 */     switch (this.state) { case StartTag:
/* 203 */         return false;
/* 204 */       case EndTag: return true; }
/*     */ 
/*     */     
/* 207 */     return this.payloadReader.isEndElement();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCharacters() {
/* 213 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 215 */         return false; }
/*     */ 
/*     */     
/* 218 */     return this.payloadReader.isCharacters();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWhiteSpace() {
/* 224 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 226 */         return false; }
/*     */ 
/*     */     
/* 229 */     return this.payloadReader.isWhiteSpace();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAttributeValue(String uri, String localName) {
/* 235 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 237 */         return this.tagInfo.atts.getValue(uri, localName); }
/*     */ 
/*     */     
/* 240 */     return this.payloadReader.getAttributeValue(uri, localName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAttributeCount() {
/* 246 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 248 */         return this.tagInfo.atts.getLength(); }
/*     */ 
/*     */     
/* 251 */     return this.payloadReader.getAttributeCount();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getAttributeName(int i) {
/* 257 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 259 */         return new QName(this.tagInfo.atts.getURI(i), this.tagInfo.atts.getLocalName(i), getPrfix(this.tagInfo.atts.getQName(i))); }
/*     */ 
/*     */     
/* 262 */     return this.payloadReader.getAttributeName(i);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAttributeNamespace(int index) {
/* 268 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 270 */         return this.tagInfo.atts.getURI(index); }
/*     */ 
/*     */     
/* 273 */     return this.payloadReader.getAttributeNamespace(index);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAttributeLocalName(int index) {
/* 279 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 281 */         return this.tagInfo.atts.getLocalName(index); }
/*     */ 
/*     */     
/* 284 */     return this.payloadReader.getAttributeLocalName(index);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAttributePrefix(int index) {
/* 290 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 292 */         return getPrfix(this.tagInfo.atts.getQName(index)); }
/*     */ 
/*     */     
/* 295 */     return this.payloadReader.getAttributePrefix(index);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getPrfix(String qName) {
/* 300 */     if (qName == null) return null; 
/* 301 */     int i = qName.indexOf(":");
/* 302 */     return (i > 0) ? qName.substring(0, i) : "";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAttributeType(int index) {
/* 308 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 310 */         return this.tagInfo.atts.getType(index); }
/*     */ 
/*     */     
/* 313 */     return this.payloadReader.getAttributeType(index);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAttributeValue(int index) {
/* 319 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 321 */         return this.tagInfo.atts.getValue(index); }
/*     */ 
/*     */     
/* 324 */     return this.payloadReader.getAttributeValue(index);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAttributeSpecified(int index) {
/* 330 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 332 */         return (index < this.tagInfo.atts.getLength()) ? ((this.tagInfo.atts.getLocalName(index) != null)) : false; }
/*     */ 
/*     */     
/* 335 */     return this.payloadReader.isAttributeSpecified(index);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNamespaceCount() {
/* 341 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 343 */         return this.tagInfo.ns.length / 2; }
/*     */ 
/*     */     
/* 346 */     return this.payloadReader.getNamespaceCount();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNamespacePrefix(int index) {
/* 352 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 354 */         return this.tagInfo.ns[2 * index]; }
/*     */ 
/*     */     
/* 357 */     return this.payloadReader.getNamespacePrefix(index);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNamespaceURI(int index) {
/* 363 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 365 */         return this.tagInfo.ns[2 * index + 1]; }
/*     */ 
/*     */     
/* 368 */     return this.payloadReader.getNamespaceURI(index);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NamespaceContextEx getNamespaceContext() {
/* 374 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 376 */         return new NamespaceContextExAdaper(this.elemInfo); }
/*     */ 
/*     */     
/* 379 */     return isPayloadReaderEx() ? 
/* 380 */       payloadReaderEx().getNamespaceContext() : new NamespaceContextExAdaper(this.payloadReader
/* 381 */         .getNamespaceContext());
/*     */   }
/*     */   
/*     */   private boolean isPayloadReaderEx() {
/* 385 */     return this.payloadReader instanceof XMLStreamReaderEx;
/*     */   } private XMLStreamReaderEx payloadReaderEx() {
/* 387 */     return (XMLStreamReaderEx)this.payloadReader;
/*     */   }
/*     */   
/*     */   public int getEventType() {
/* 391 */     switch (this.state) { case StartTag:
/* 392 */         return 1;
/* 393 */       case EndTag: return 2; }
/*     */ 
/*     */     
/* 396 */     return this.payloadReader.getEventType();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() {
/* 402 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 404 */         return null; }
/*     */ 
/*     */     
/* 407 */     return this.payloadReader.getText();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public char[] getTextCharacters() {
/* 413 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 415 */         return null; }
/*     */ 
/*     */     
/* 418 */     return this.payloadReader.getTextCharacters();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) throws XMLStreamException {
/* 424 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 426 */         return -1; }
/*     */ 
/*     */     
/* 429 */     return this.payloadReader.getTextCharacters(sourceStart, target, targetStart, length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTextStart() {
/* 435 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 437 */         return 0; }
/*     */ 
/*     */     
/* 440 */     return this.payloadReader.getTextStart();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTextLength() {
/* 446 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 448 */         return 0; }
/*     */ 
/*     */     
/* 451 */     return this.payloadReader.getTextLength();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEncoding() {
/* 457 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 459 */         return null; }
/*     */ 
/*     */     
/* 462 */     return this.payloadReader.getEncoding();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasText() {
/* 468 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 470 */         return false; }
/*     */ 
/*     */     
/* 473 */     return this.payloadReader.hasText();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Location getLocation() {
/* 479 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 481 */         return new Location()
/*     */           {
/*     */             
/*     */             public int getLineNumber()
/*     */             {
/* 486 */               return 0;
/*     */             }
/*     */ 
/*     */ 
/*     */             
/*     */             public int getColumnNumber() {
/* 492 */               return 0;
/*     */             }
/*     */ 
/*     */ 
/*     */             
/*     */             public int getCharacterOffset() {
/* 498 */               return 0;
/*     */             }
/*     */ 
/*     */ 
/*     */             
/*     */             public String getPublicId() {
/* 504 */               return null;
/*     */             }
/*     */ 
/*     */ 
/*     */             
/*     */             public String getSystemId() {
/* 510 */               return null;
/*     */             }
/*     */           }; }
/*     */ 
/*     */ 
/*     */     
/* 516 */     return this.payloadReader.getLocation();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getName() {
/* 522 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 524 */         return new QName(this.tagInfo.nsUri, this.tagInfo.localName, this.tagInfo.prefix); }
/*     */ 
/*     */     
/* 527 */     return this.payloadReader.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalName() {
/* 533 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 535 */         return this.tagInfo.localName; }
/*     */ 
/*     */     
/* 538 */     return this.payloadReader.getLocalName();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasName() {
/* 544 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 546 */         return true; }
/*     */ 
/*     */     
/* 549 */     return this.payloadReader.hasName();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNamespaceURI() {
/* 555 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 557 */         return this.tagInfo.nsUri; }
/*     */ 
/*     */     
/* 560 */     return this.payloadReader.getNamespaceURI();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrefix() {
/* 566 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 568 */         return this.tagInfo.prefix; }
/*     */ 
/*     */     
/* 571 */     return this.payloadReader.getPrefix();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 577 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 579 */         return null; }
/*     */ 
/*     */     
/* 582 */     return this.payloadReader.getVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStandalone() {
/* 588 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 590 */         return true; }
/*     */ 
/*     */     
/* 593 */     return this.payloadReader.isStandalone();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean standaloneSet() {
/* 599 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 601 */         return true; }
/*     */ 
/*     */     
/* 604 */     return this.payloadReader.standaloneSet();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCharacterEncodingScheme() {
/* 610 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 612 */         return null; }
/*     */ 
/*     */     
/* 615 */     return this.payloadReader.getCharacterEncodingScheme();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPITarget() {
/* 621 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 623 */         return null; }
/*     */ 
/*     */     
/* 626 */     return this.payloadReader.getPITarget();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPIData() {
/* 632 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 634 */         return null; }
/*     */ 
/*     */     
/* 637 */     return this.payloadReader.getPIData();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getElementTextTrim() throws XMLStreamException {
/* 643 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 645 */         return null; }
/*     */ 
/*     */     
/* 648 */     return isPayloadReaderEx() ? payloadReaderEx().getElementTextTrim() : this.payloadReader.getElementText().trim();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CharSequence getPCDATA() throws XMLStreamException {
/* 654 */     switch (this.state) { case StartTag:
/*     */       case EndTag:
/* 656 */         return null; }
/*     */ 
/*     */     
/* 659 */     return isPayloadReaderEx() ? payloadReaderEx().getPCDATA() : this.payloadReader.getElementText();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/util/xml/XMLReaderComposite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */