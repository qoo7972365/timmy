/*     */ package com.sun.org.apache.xalan.internal.xsltc.trax;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.xsltc.dom.SAXImpl;
/*     */ import java.io.IOException;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.DTDHandler;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXNotRecognizedException;
/*     */ import org.xml.sax.SAXNotSupportedException;
/*     */ import org.xml.sax.XMLReader;
/*     */ import org.xml.sax.ext.LexicalHandler;
/*     */ import org.xml.sax.ext.Locator2;
/*     */ import org.xml.sax.helpers.AttributesImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StAXStream2SAX
/*     */   implements XMLReader, Locator
/*     */ {
/*     */   private final XMLStreamReader staxStreamReader;
/*  64 */   private ContentHandler _sax = null;
/*  65 */   private LexicalHandler _lex = null;
/*  66 */   private SAXImpl _saxImpl = null;
/*     */   
/*     */   public StAXStream2SAX(XMLStreamReader staxSrc) {
/*  69 */     this.staxStreamReader = staxSrc;
/*     */   }
/*     */   
/*     */   public ContentHandler getContentHandler() {
/*  73 */     return this._sax;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContentHandler(ContentHandler handler) throws NullPointerException {
/*  79 */     this._sax = handler;
/*  80 */     if (handler instanceof LexicalHandler) {
/*  81 */       this._lex = (LexicalHandler)handler;
/*     */     }
/*     */     
/*  84 */     if (handler instanceof SAXImpl) {
/*  85 */       this._saxImpl = (SAXImpl)handler;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void parse(InputSource unused) throws IOException, SAXException {
/*     */     try {
/*  92 */       bridge();
/*  93 */     } catch (XMLStreamException e) {
/*  94 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void parse() throws IOException, SAXException, XMLStreamException {
/* 101 */     bridge();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parse(String sysId) throws IOException, SAXException {
/* 110 */     throw new IOException("This method is not yet implemented.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bridge() throws XMLStreamException {
/*     */     try {
/* 118 */       int depth = 0;
/*     */ 
/*     */       
/* 121 */       int event = this.staxStreamReader.getEventType();
/* 122 */       if (event == 7) {
/* 123 */         event = this.staxStreamReader.next();
/*     */       }
/*     */ 
/*     */       
/* 127 */       if (event != 1) {
/* 128 */         event = this.staxStreamReader.nextTag();
/*     */         
/* 130 */         if (event != 1) {
/* 131 */           throw new IllegalStateException("The current event is not START_ELEMENT\n but" + event);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 136 */       handleStartDocument();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       do {
/* 142 */         switch (event) {
/*     */           case 1:
/* 144 */             depth++;
/* 145 */             handleStartElement();
/*     */             break;
/*     */           case 2:
/* 148 */             handleEndElement();
/* 149 */             depth--;
/*     */             break;
/*     */           case 4:
/* 152 */             handleCharacters();
/*     */             break;
/*     */           case 9:
/* 155 */             handleEntityReference();
/*     */             break;
/*     */           case 3:
/* 158 */             handlePI();
/*     */             break;
/*     */           case 5:
/* 161 */             handleComment();
/*     */             break;
/*     */           case 11:
/* 164 */             handleDTD();
/*     */             break;
/*     */           case 10:
/* 167 */             handleAttribute();
/*     */             break;
/*     */           case 13:
/* 170 */             handleNamespace();
/*     */             break;
/*     */           case 12:
/* 173 */             handleCDATA();
/*     */             break;
/*     */           case 15:
/* 176 */             handleEntityDecl();
/*     */             break;
/*     */           case 14:
/* 179 */             handleNotationDecl();
/*     */             break;
/*     */           case 6:
/* 182 */             handleSpace();
/*     */             break;
/*     */           default:
/* 185 */             throw new InternalError("processing event: " + event);
/*     */         } 
/*     */         
/* 188 */         event = this.staxStreamReader.next();
/* 189 */       } while (depth != 0);
/*     */       
/* 191 */       handleEndDocument();
/* 192 */     } catch (SAXException e) {
/* 193 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleEndDocument() throws SAXException {
/* 198 */     this._sax.endDocument();
/*     */   }
/*     */   
/*     */   private void handleStartDocument() throws SAXException {
/* 202 */     this._sax.setDocumentLocator(new Locator2() {
/*     */           public int getColumnNumber() {
/* 204 */             return StAXStream2SAX.this.staxStreamReader.getLocation().getColumnNumber();
/*     */           }
/*     */           public int getLineNumber() {
/* 207 */             return StAXStream2SAX.this.staxStreamReader.getLocation().getLineNumber();
/*     */           }
/*     */           public String getPublicId() {
/* 210 */             return StAXStream2SAX.this.staxStreamReader.getLocation().getPublicId();
/*     */           }
/*     */           public String getSystemId() {
/* 213 */             return StAXStream2SAX.this.staxStreamReader.getLocation().getSystemId();
/*     */           }
/*     */           public String getXMLVersion() {
/* 216 */             return StAXStream2SAX.this.staxStreamReader.getVersion();
/*     */           }
/*     */           public String getEncoding() {
/* 219 */             return StAXStream2SAX.this.staxStreamReader.getEncoding();
/*     */           }
/*     */         });
/* 222 */     this._sax.startDocument();
/*     */   }
/*     */   
/*     */   private void handlePI() throws XMLStreamException {
/*     */     try {
/* 227 */       this._sax.processingInstruction(this.staxStreamReader
/* 228 */           .getPITarget(), this.staxStreamReader
/* 229 */           .getPIData());
/* 230 */     } catch (SAXException e) {
/* 231 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleCharacters() throws XMLStreamException {
/* 239 */     int textLength = this.staxStreamReader.getTextLength();
/* 240 */     char[] chars = new char[textLength];
/*     */     
/* 242 */     this.staxStreamReader.getTextCharacters(0, chars, 0, textLength);
/*     */     
/*     */     try {
/* 245 */       this._sax.characters(chars, 0, chars.length);
/* 246 */     } catch (SAXException e) {
/* 247 */       throw new XMLStreamException(e);
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
/*     */   private void handleEndElement() throws XMLStreamException {
/* 265 */     QName qName = this.staxStreamReader.getName();
/*     */ 
/*     */     
/*     */     try {
/* 269 */       String qname = "";
/* 270 */       if (qName.getPrefix() != null && qName.getPrefix().trim().length() != 0) {
/* 271 */         qname = qName.getPrefix() + ":";
/*     */       }
/* 273 */       qname = qname + qName.getLocalPart();
/*     */ 
/*     */       
/* 276 */       this._sax.endElement(qName
/* 277 */           .getNamespaceURI(), qName
/* 278 */           .getLocalPart(), qname);
/*     */ 
/*     */ 
/*     */       
/* 282 */       int nsCount = this.staxStreamReader.getNamespaceCount();
/* 283 */       for (int i = nsCount - 1; i >= 0; i--) {
/* 284 */         String prefix = this.staxStreamReader.getNamespacePrefix(i);
/* 285 */         if (prefix == null) {
/* 286 */           prefix = "";
/*     */         }
/* 288 */         this._sax.endPrefixMapping(prefix);
/*     */       } 
/* 290 */     } catch (SAXException e) {
/* 291 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleStartElement() throws XMLStreamException {
/*     */     try {
/*     */       String rawname;
/* 299 */       int nsCount = this.staxStreamReader.getNamespaceCount();
/* 300 */       for (int i = 0; i < nsCount; i++) {
/* 301 */         String str = this.staxStreamReader.getNamespacePrefix(i);
/* 302 */         if (str == null) {
/* 303 */           str = "";
/*     */         }
/* 305 */         this._sax.startPrefixMapping(str, this.staxStreamReader
/*     */             
/* 307 */             .getNamespaceURI(i));
/*     */       } 
/*     */ 
/*     */       
/* 311 */       QName qName = this.staxStreamReader.getName();
/* 312 */       String prefix = qName.getPrefix();
/*     */       
/* 314 */       if (prefix == null || prefix.length() == 0) {
/* 315 */         rawname = qName.getLocalPart();
/*     */       } else {
/* 317 */         rawname = prefix + ':' + qName.getLocalPart();
/* 318 */       }  Attributes attrs = getAttributes();
/* 319 */       this._sax.startElement(qName
/* 320 */           .getNamespaceURI(), qName
/* 321 */           .getLocalPart(), rawname, attrs);
/*     */     
/*     */     }
/* 324 */     catch (SAXException e) {
/* 325 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Attributes getAttributes() {
/* 336 */     AttributesImpl attrs = new AttributesImpl();
/*     */     
/* 338 */     int eventType = this.staxStreamReader.getEventType();
/* 339 */     if (eventType != 10 && eventType != 1)
/*     */     {
/* 341 */       throw new InternalError("getAttributes() attempting to process: " + eventType);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 350 */     for (int i = 0; i < this.staxStreamReader.getAttributeCount(); i++) {
/* 351 */       String qName, uri = this.staxStreamReader.getAttributeNamespace(i);
/* 352 */       if (uri == null) uri = ""; 
/* 353 */       String localName = this.staxStreamReader.getAttributeLocalName(i);
/* 354 */       String prefix = this.staxStreamReader.getAttributePrefix(i);
/*     */       
/* 356 */       if (prefix == null || prefix.length() == 0) {
/* 357 */         qName = localName;
/*     */       } else {
/* 359 */         qName = prefix + ':' + localName;
/* 360 */       }  String type = this.staxStreamReader.getAttributeType(i);
/* 361 */       String value = this.staxStreamReader.getAttributeValue(i);
/*     */       
/* 363 */       attrs.addAttribute(uri, localName, qName, type, value);
/*     */     } 
/*     */     
/* 366 */     return attrs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleNamespace() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleAttribute() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleDTD() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleComment() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleEntityReference() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleSpace() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleNotationDecl() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleEntityDecl() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleCDATA() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DTDHandler getDTDHandler() {
/* 420 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ErrorHandler getErrorHandler() {
/* 428 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getFeature(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 438 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFeature(String name, boolean value) throws SAXNotRecognizedException, SAXNotSupportedException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDTDHandler(DTDHandler handler) throws NullPointerException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEntityResolver(EntityResolver resolver) throws NullPointerException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityResolver getEntityResolver() {
/* 471 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setErrorHandler(ErrorHandler handler) throws NullPointerException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 498 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnNumber() {
/* 506 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLineNumber() {
/* 514 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPublicId() {
/* 522 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSystemId() {
/* 530 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/trax/StAXStream2SAX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */