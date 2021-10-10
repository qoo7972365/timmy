/*     */ package com.sun.xml.internal.fastinfoset.tools;
/*     */ 
/*     */ import com.sun.xml.internal.fastinfoset.QualifiedName;
/*     */ import java.util.ArrayList;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.ext.LexicalHandler;
/*     */ import org.xml.sax.helpers.DefaultHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SAX2StAXWriter
/*     */   extends DefaultHandler
/*     */   implements LexicalHandler
/*     */ {
/*  43 */   private static final Logger logger = Logger.getLogger(SAX2StAXWriter.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   XMLStreamWriter _writer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   ArrayList _namespaces = new ArrayList();
/*     */   
/*     */   public SAX2StAXWriter(XMLStreamWriter writer) {
/*  56 */     this._writer = writer;
/*     */   }
/*     */   
/*     */   public XMLStreamWriter getWriter() {
/*  60 */     return this._writer;
/*     */   }
/*     */   
/*     */   public void startDocument() throws SAXException {
/*     */     try {
/*  65 */       this._writer.writeStartDocument();
/*     */     }
/*  67 */     catch (XMLStreamException e) {
/*  68 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void endDocument() throws SAXException {
/*     */     try {
/*  74 */       this._writer.writeEndDocument();
/*  75 */       this._writer.flush();
/*     */     }
/*  77 */     catch (XMLStreamException e) {
/*  78 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void characters(char[] ch, int start, int length) throws SAXException {
/*     */     try {
/*  86 */       this._writer.writeCharacters(ch, start, length);
/*     */     }
/*  88 */     catch (XMLStreamException e) {
/*  89 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
/*     */     try {
/*  97 */       int k = qName.indexOf(':');
/*  98 */       String prefix = (k > 0) ? qName.substring(0, k) : "";
/*  99 */       this._writer.writeStartElement(prefix, localName, namespaceURI);
/*     */       
/* 101 */       int length = this._namespaces.size(); int i;
/* 102 */       for (i = 0; i < length; i++) {
/* 103 */         QualifiedName nsh = this._namespaces.get(i);
/* 104 */         this._writer.writeNamespace(nsh.prefix, nsh.namespaceName);
/*     */       } 
/* 106 */       this._namespaces.clear();
/*     */       
/* 108 */       length = atts.getLength();
/* 109 */       for (i = 0; i < length; i++) {
/* 110 */         this._writer.writeAttribute(atts.getURI(i), atts
/* 111 */             .getLocalName(i), atts
/* 112 */             .getValue(i));
/*     */       }
/*     */     }
/* 115 */     catch (XMLStreamException e) {
/* 116 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
/*     */     try {
/* 124 */       this._writer.writeEndElement();
/*     */     }
/* 126 */     catch (XMLStreamException e) {
/* 127 */       logger.log(Level.FINE, "Exception on endElement", e);
/* 128 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startPrefixMapping(String prefix, String uri) throws SAXException {
/* 139 */     this._namespaces.add(new QualifiedName(prefix, uri));
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
/*     */   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
/* 152 */     characters(ch, start, length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processingInstruction(String target, String data) throws SAXException {
/*     */     try {
/* 159 */       this._writer.writeProcessingInstruction(target, data);
/*     */     }
/* 161 */     catch (XMLStreamException e) {
/* 162 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDocumentLocator(Locator locator) {}
/*     */ 
/*     */   
/*     */   public void skippedEntity(String name) throws SAXException {}
/*     */ 
/*     */   
/*     */   public void comment(char[] ch, int start, int length) throws SAXException {
/*     */     try {
/* 176 */       this._writer.writeComment(new String(ch, start, length));
/*     */     }
/* 178 */     catch (XMLStreamException e) {
/* 179 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void endCDATA() throws SAXException {}
/*     */   
/*     */   public void endDTD() throws SAXException {}
/*     */   
/*     */   public void endEntity(String name) throws SAXException {}
/*     */   
/*     */   public void startCDATA() throws SAXException {}
/*     */   
/*     */   public void startDTD(String name, String publicId, String systemId) throws SAXException {}
/*     */   
/*     */   public void startEntity(String name) throws SAXException {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/tools/SAX2StAXWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */