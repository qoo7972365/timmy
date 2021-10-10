/*     */ package com.sun.org.apache.xalan.internal.xsltc.trax;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.xsltc.dom.SAXImpl;
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLEventReader;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.events.Attribute;
/*     */ import javax.xml.stream.events.Characters;
/*     */ import javax.xml.stream.events.EndElement;
/*     */ import javax.xml.stream.events.Namespace;
/*     */ import javax.xml.stream.events.ProcessingInstruction;
/*     */ import javax.xml.stream.events.StartDocument;
/*     */ import javax.xml.stream.events.StartElement;
/*     */ import javax.xml.stream.events.XMLEvent;
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
/*     */ 
/*     */ 
/*     */ public class StAXEvent2SAX
/*     */   implements XMLReader, Locator
/*     */ {
/*     */   private final XMLEventReader staxEventReader;
/*  75 */   private ContentHandler _sax = null;
/*  76 */   private LexicalHandler _lex = null;
/*  77 */   private SAXImpl _saxImpl = null;
/*  78 */   private String version = null;
/*  79 */   private String encoding = null;
/*     */ 
/*     */   
/*     */   public StAXEvent2SAX(XMLEventReader staxCore) {
/*  83 */     this.staxEventReader = staxCore;
/*     */   }
/*     */   
/*     */   public ContentHandler getContentHandler() {
/*  87 */     return this._sax;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContentHandler(ContentHandler handler) throws NullPointerException {
/*  93 */     this._sax = handler;
/*  94 */     if (handler instanceof LexicalHandler) {
/*  95 */       this._lex = (LexicalHandler)handler;
/*     */     }
/*     */     
/*  98 */     if (handler instanceof SAXImpl) {
/*  99 */       this._saxImpl = (SAXImpl)handler;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void parse(InputSource unused) throws IOException, SAXException {
/*     */     try {
/* 106 */       bridge();
/* 107 */     } catch (XMLStreamException e) {
/* 108 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void parse() throws IOException, SAXException, XMLStreamException {
/* 115 */     bridge();
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
/*     */   private void bridge() throws XMLStreamException {
/*     */     try {
/* 143 */       int depth = 0;
/* 144 */       boolean startedAtDocument = false;
/*     */       
/* 146 */       XMLEvent event = this.staxEventReader.peek();
/*     */       
/* 148 */       if (!event.isStartDocument() && !event.isStartElement()) {
/* 149 */         throw new IllegalStateException();
/*     */       }
/*     */       
/* 152 */       if (event.getEventType() == 7) {
/* 153 */         startedAtDocument = true;
/* 154 */         this.version = ((StartDocument)event).getVersion();
/* 155 */         if (((StartDocument)event).encodingSet())
/* 156 */           this.encoding = ((StartDocument)event).getCharacterEncodingScheme(); 
/* 157 */         event = this.staxEventReader.nextEvent();
/* 158 */         event = this.staxEventReader.nextEvent();
/*     */       } 
/*     */       
/* 161 */       handleStartDocument(event);
/*     */ 
/*     */       
/* 164 */       while (event.getEventType() != 1) {
/* 165 */         switch (event.getEventType()) {
/*     */           case 4:
/* 167 */             handleCharacters(event.asCharacters());
/*     */             break;
/*     */           case 3:
/* 170 */             handlePI((ProcessingInstruction)event);
/*     */             break;
/*     */           case 5:
/* 173 */             handleComment();
/*     */             break;
/*     */           case 11:
/* 176 */             handleDTD();
/*     */             break;
/*     */           case 6:
/* 179 */             handleSpace();
/*     */             break;
/*     */           default:
/* 182 */             throw new InternalError("processing prolog event: " + event);
/*     */         } 
/* 184 */         event = this.staxEventReader.nextEvent();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       do {
/* 192 */         switch (event.getEventType()) {
/*     */           case 1:
/* 194 */             depth++;
/* 195 */             handleStartElement(event.asStartElement());
/*     */             break;
/*     */           case 2:
/* 198 */             handleEndElement(event.asEndElement());
/* 199 */             depth--;
/*     */             break;
/*     */           case 4:
/* 202 */             handleCharacters(event.asCharacters());
/*     */             break;
/*     */           case 9:
/* 205 */             handleEntityReference();
/*     */             break;
/*     */           case 3:
/* 208 */             handlePI((ProcessingInstruction)event);
/*     */             break;
/*     */           case 5:
/* 211 */             handleComment();
/*     */             break;
/*     */           case 11:
/* 214 */             handleDTD();
/*     */             break;
/*     */           case 10:
/* 217 */             handleAttribute();
/*     */             break;
/*     */           case 13:
/* 220 */             handleNamespace();
/*     */             break;
/*     */           case 12:
/* 223 */             handleCDATA();
/*     */             break;
/*     */           case 15:
/* 226 */             handleEntityDecl();
/*     */             break;
/*     */           case 14:
/* 229 */             handleNotationDecl();
/*     */             break;
/*     */           case 6:
/* 232 */             handleSpace();
/*     */             break;
/*     */           default:
/* 235 */             throw new InternalError("processing event: " + event);
/*     */         } 
/*     */         
/* 238 */         event = this.staxEventReader.nextEvent();
/* 239 */       } while (depth != 0);
/*     */       
/* 241 */       if (startedAtDocument)
/*     */       {
/* 243 */         while (event.getEventType() != 8) {
/* 244 */           switch (event.getEventType()) {
/*     */             case 4:
/* 246 */               handleCharacters(event.asCharacters());
/*     */               break;
/*     */             case 3:
/* 249 */               handlePI((ProcessingInstruction)event);
/*     */               break;
/*     */             case 5:
/* 252 */               handleComment();
/*     */               break;
/*     */             case 6:
/* 255 */               handleSpace();
/*     */               break;
/*     */             default:
/* 258 */               throw new InternalError("processing misc event after document element: " + event);
/*     */           } 
/* 260 */           event = this.staxEventReader.nextEvent();
/*     */         } 
/*     */       }
/*     */       
/* 264 */       handleEndDocument();
/* 265 */     } catch (SAXException e) {
/* 266 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleEndDocument() throws SAXException {
/* 272 */     this._sax.endDocument();
/*     */   }
/*     */   
/*     */   private void handleStartDocument(final XMLEvent event) throws SAXException {
/* 276 */     this._sax.setDocumentLocator(new Locator2() {
/*     */           public int getColumnNumber() {
/* 278 */             return event.getLocation().getColumnNumber();
/*     */           }
/*     */           public int getLineNumber() {
/* 281 */             return event.getLocation().getLineNumber();
/*     */           }
/*     */           public String getPublicId() {
/* 284 */             return event.getLocation().getPublicId();
/*     */           }
/*     */           public String getSystemId() {
/* 287 */             return event.getLocation().getSystemId();
/*     */           }
/*     */           public String getXMLVersion() {
/* 290 */             return StAXEvent2SAX.this.version;
/*     */           }
/*     */           public String getEncoding() {
/* 293 */             return StAXEvent2SAX.this.encoding;
/*     */           }
/*     */         });
/*     */     
/* 297 */     this._sax.startDocument();
/*     */   }
/*     */ 
/*     */   
/*     */   private void handlePI(ProcessingInstruction event) throws XMLStreamException {
/*     */     try {
/* 303 */       this._sax.processingInstruction(event
/* 304 */           .getTarget(), event
/* 305 */           .getData());
/* 306 */     } catch (SAXException e) {
/* 307 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleCharacters(Characters event) throws XMLStreamException {
/*     */     try {
/* 313 */       this._sax.characters(event
/* 314 */           .getData().toCharArray(), 0, event
/*     */           
/* 316 */           .getData().length());
/* 317 */     } catch (SAXException e) {
/* 318 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleEndElement(EndElement event) throws XMLStreamException {
/* 323 */     QName qName = event.getName();
/*     */ 
/*     */     
/* 326 */     String qname = "";
/* 327 */     if (qName.getPrefix() != null && qName.getPrefix().trim().length() != 0) {
/* 328 */       qname = qName.getPrefix() + ":";
/*     */     }
/* 330 */     qname = qname + qName.getLocalPart();
/*     */ 
/*     */     
/*     */     try {
/* 334 */       this._sax.endElement(qName
/* 335 */           .getNamespaceURI(), qName
/* 336 */           .getLocalPart(), qname);
/*     */ 
/*     */ 
/*     */       
/* 340 */       for (Iterator<Namespace> i = event.getNamespaces(); i.hasNext(); ) {
/* 341 */         String prefix = (String)i.next();
/* 342 */         if (prefix == null) {
/* 343 */           prefix = "";
/*     */         }
/* 345 */         this._sax.endPrefixMapping(prefix);
/*     */       } 
/* 347 */     } catch (SAXException e) {
/* 348 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleStartElement(StartElement event) throws XMLStreamException {
/*     */     try {
/*     */       String rawname;
/* 356 */       for (Iterator<Namespace> i = event.getNamespaces(); i.hasNext(); ) {
/* 357 */         String str = ((Namespace)i.next()).getPrefix();
/* 358 */         if (str == null) {
/* 359 */           str = "";
/*     */         }
/* 361 */         this._sax.startPrefixMapping(str, event
/*     */             
/* 363 */             .getNamespaceURI(str));
/*     */       } 
/*     */ 
/*     */       
/* 367 */       QName qName = event.getName();
/* 368 */       String prefix = qName.getPrefix();
/*     */       
/* 370 */       if (prefix == null || prefix.length() == 0) {
/* 371 */         rawname = qName.getLocalPart();
/*     */       } else {
/* 373 */         rawname = prefix + ':' + qName.getLocalPart();
/*     */       } 
/*     */       
/* 376 */       Attributes saxAttrs = getAttributes(event);
/* 377 */       this._sax.startElement(qName
/* 378 */           .getNamespaceURI(), qName
/* 379 */           .getLocalPart(), rawname, saxAttrs);
/*     */     
/*     */     }
/* 382 */     catch (SAXException e) {
/* 383 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Attributes getAttributes(StartElement event) {
/* 393 */     AttributesImpl attrs = new AttributesImpl();
/*     */     
/* 395 */     if (!event.isStartElement()) {
/* 396 */       throw new InternalError("getAttributes() attempting to process: " + event);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 405 */     for (Iterator<Attribute> i = event.getAttributes(); i.hasNext(); ) {
/* 406 */       String qName; Attribute staxAttr = i.next();
/*     */       
/* 408 */       String uri = staxAttr.getName().getNamespaceURI();
/* 409 */       if (uri == null) {
/* 410 */         uri = "";
/*     */       }
/* 412 */       String localName = staxAttr.getName().getLocalPart();
/* 413 */       String prefix = staxAttr.getName().getPrefix();
/*     */       
/* 415 */       if (prefix == null || prefix.length() == 0) {
/* 416 */         qName = localName;
/*     */       } else {
/* 418 */         qName = prefix + ':' + localName;
/*     */       } 
/* 420 */       String type = staxAttr.getDTDType();
/* 421 */       String value = staxAttr.getValue();
/*     */       
/* 423 */       attrs.addAttribute(uri, localName, qName, type, value);
/*     */     } 
/*     */     
/* 426 */     return attrs;
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
/* 480 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ErrorHandler getErrorHandler() {
/* 488 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getFeature(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 498 */     return false;
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
/*     */   public void parse(String sysId) throws IOException, SAXException {
/* 515 */     throw new IOException("This method is not yet implemented.");
/*     */   }
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
/*     */   public void setEntityResolver(EntityResolver resolver) throws NullPointerException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityResolver getEntityResolver() {
/* 539 */     return null;
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
/* 566 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnNumber() {
/* 574 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLineNumber() {
/* 582 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPublicId() {
/* 590 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSystemId() {
/* 598 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/trax/StAXEvent2SAX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */