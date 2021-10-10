/*      */ package com.sun.xml.internal.txw2.output;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.Writer;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import org.xml.sax.Attributes;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.ext.LexicalHandler;
/*      */ import org.xml.sax.helpers.AttributesImpl;
/*      */ import org.xml.sax.helpers.XMLFilterImpl;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XMLWriter
/*      */   extends XMLFilterImpl
/*      */   implements LexicalHandler
/*      */ {
/*      */   private final HashMap locallyDeclaredPrefix;
/*      */   private final Attributes EMPTY_ATTS;
/*      */   private boolean inCDATA;
/*      */   private int elementLevel;
/*      */   private Writer output;
/*      */   private String encoding;
/*      */   private boolean writeXmlDecl;
/*      */   private String header;
/*      */   private final CharacterEscapeHandler escapeHandler;
/*      */   private boolean startTagIsClosed;
/*      */   
/*      */   public XMLWriter(Writer writer, String encoding, CharacterEscapeHandler _escapeHandler) {
/*  414 */     this.locallyDeclaredPrefix = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1050 */     this.EMPTY_ATTS = new AttributesImpl();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1058 */     this.inCDATA = false;
/* 1059 */     this.elementLevel = 0;
/*      */ 
/*      */     
/* 1062 */     this.writeXmlDecl = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1068 */     this.header = null;
/*      */ 
/*      */ 
/*      */     
/* 1072 */     this.startTagIsClosed = true;
/*      */     init(writer, encoding);
/*      */     this.escapeHandler = _escapeHandler;
/*      */   }
/*      */   
/*      */   public XMLWriter(Writer writer, String encoding) {
/*      */     this(writer, encoding, DumbEscapeHandler.theInstance);
/*      */   }
/*      */   
/*      */   private void init(Writer writer, String encoding) {
/*      */     setOutput(writer, encoding);
/*      */   }
/*      */   
/*      */   public void reset() {
/*      */     this.elementLevel = 0;
/*      */     this.startTagIsClosed = true;
/*      */   }
/*      */   
/*      */   public void flush() throws IOException {
/*      */     this.output.flush();
/*      */   }
/*      */   
/*      */   public void setOutput(Writer writer, String _encoding) {
/*      */     if (writer == null) {
/*      */       this.output = new OutputStreamWriter(System.out);
/*      */     } else {
/*      */       this.output = writer;
/*      */     } 
/*      */     this.encoding = _encoding;
/*      */   }
/*      */   
/*      */   public void setEncoding(String encoding) {
/*      */     this.encoding = encoding;
/*      */   }
/*      */   
/*      */   public void setXmlDecl(boolean _writeXmlDecl) {
/*      */     this.writeXmlDecl = _writeXmlDecl;
/*      */   }
/*      */   
/*      */   public void setHeader(String _header) {
/*      */     this.header = _header;
/*      */   }
/*      */   
/*      */   public void startPrefixMapping(String prefix, String uri) throws SAXException {
/*      */     this.locallyDeclaredPrefix.put(prefix, uri);
/*      */   }
/*      */   
/*      */   public void startDocument() throws SAXException {
/*      */     try {
/*      */       reset();
/*      */       if (this.writeXmlDecl) {
/*      */         String e = "";
/*      */         if (this.encoding != null)
/*      */           e = " encoding=\"" + this.encoding + "\""; 
/*      */         write("<?xml version=\"1.0\"" + e + " standalone=\"yes\"?>\n");
/*      */       } 
/*      */       if (this.header != null)
/*      */         write(this.header); 
/*      */       super.startDocument();
/*      */     } catch (IOException e) {
/*      */       throw new SAXException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void endDocument() throws SAXException {
/*      */     try {
/*      */       if (!this.startTagIsClosed) {
/*      */         write("/>");
/*      */         this.startTagIsClosed = true;
/*      */       } 
/*      */       write('\n');
/*      */       super.endDocument();
/*      */       try {
/*      */         flush();
/*      */       } catch (IOException e) {
/*      */         throw new SAXException(e);
/*      */       } 
/*      */     } catch (IOException e) {
/*      */       throw new SAXException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
/*      */     try {
/*      */       if (!this.startTagIsClosed)
/*      */         write(">"); 
/*      */       this.elementLevel++;
/*      */       write('<');
/*      */       writeName(uri, localName, qName, true);
/*      */       writeAttributes(atts);
/*      */       if (!this.locallyDeclaredPrefix.isEmpty()) {
/*      */         Iterator<Map.Entry> itr = this.locallyDeclaredPrefix.entrySet().iterator();
/*      */         while (itr.hasNext()) {
/*      */           Map.Entry e = itr.next();
/*      */           String p = (String)e.getKey();
/*      */           String u = (String)e.getValue();
/*      */           if (u == null)
/*      */             u = ""; 
/*      */           write(' ');
/*      */           if ("".equals(p)) {
/*      */             write("xmlns=\"");
/*      */           } else {
/*      */             write("xmlns:");
/*      */             write(p);
/*      */             write("=\"");
/*      */           } 
/*      */           char[] ch = u.toCharArray();
/*      */           writeEsc(ch, 0, ch.length, true);
/*      */           write('"');
/*      */         } 
/*      */         this.locallyDeclaredPrefix.clear();
/*      */       } 
/*      */       super.startElement(uri, localName, qName, atts);
/*      */       this.startTagIsClosed = false;
/*      */     } catch (IOException e) {
/*      */       throw new SAXException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void endElement(String uri, String localName, String qName) throws SAXException {
/*      */     try {
/*      */       if (this.startTagIsClosed) {
/*      */         write("</");
/*      */         writeName(uri, localName, qName, true);
/*      */         write('>');
/*      */       } else {
/*      */         write("/>");
/*      */         this.startTagIsClosed = true;
/*      */       } 
/*      */       if (this.elementLevel == 1)
/*      */         write('\n'); 
/*      */       super.endElement(uri, localName, qName);
/*      */       this.elementLevel--;
/*      */     } catch (IOException e) {
/*      */       throw new SAXException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void characters(char[] ch, int start, int len) throws SAXException {
/*      */     try {
/*      */       if (!this.startTagIsClosed) {
/*      */         write('>');
/*      */         this.startTagIsClosed = true;
/*      */       } 
/*      */       if (this.inCDATA) {
/*      */         this.output.write(ch, start, len);
/*      */       } else {
/*      */         writeEsc(ch, start, len, false);
/*      */       } 
/*      */       super.characters(ch, start, len);
/*      */     } catch (IOException e) {
/*      */       throw new SAXException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
/*      */     try {
/*      */       writeEsc(ch, start, length, false);
/*      */       super.ignorableWhitespace(ch, start, length);
/*      */     } catch (IOException e) {
/*      */       throw new SAXException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void processingInstruction(String target, String data) throws SAXException {
/*      */     try {
/*      */       if (!this.startTagIsClosed) {
/*      */         write('>');
/*      */         this.startTagIsClosed = true;
/*      */       } 
/*      */       write("<?");
/*      */       write(target);
/*      */       write(' ');
/*      */       write(data);
/*      */       write("?>");
/*      */       if (this.elementLevel < 1)
/*      */         write('\n'); 
/*      */       super.processingInstruction(target, data);
/*      */     } catch (IOException e) {
/*      */       throw new SAXException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void startElement(String uri, String localName) throws SAXException {
/*      */     startElement(uri, localName, "", this.EMPTY_ATTS);
/*      */   }
/*      */   
/*      */   public void startElement(String localName) throws SAXException {
/*      */     startElement("", localName, "", this.EMPTY_ATTS);
/*      */   }
/*      */   
/*      */   public void endElement(String uri, String localName) throws SAXException {
/*      */     endElement(uri, localName, "");
/*      */   }
/*      */   
/*      */   public void endElement(String localName) throws SAXException {
/*      */     endElement("", localName, "");
/*      */   }
/*      */   
/*      */   public void dataElement(String uri, String localName, String qName, Attributes atts, String content) throws SAXException {
/*      */     startElement(uri, localName, qName, atts);
/*      */     characters(content);
/*      */     endElement(uri, localName, qName);
/*      */   }
/*      */   
/*      */   public void dataElement(String uri, String localName, String content) throws SAXException {
/*      */     dataElement(uri, localName, "", this.EMPTY_ATTS, content);
/*      */   }
/*      */   
/*      */   public void dataElement(String localName, String content) throws SAXException {
/*      */     dataElement("", localName, "", this.EMPTY_ATTS, content);
/*      */   }
/*      */   
/*      */   public void characters(String data) throws SAXException {
/*      */     try {
/*      */       if (!this.startTagIsClosed) {
/*      */         write('>');
/*      */         this.startTagIsClosed = true;
/*      */       } 
/*      */       char[] ch = data.toCharArray();
/*      */       characters(ch, 0, ch.length);
/*      */     } catch (IOException e) {
/*      */       throw new SAXException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void startDTD(String name, String publicId, String systemId) throws SAXException {}
/*      */   
/*      */   public void endDTD() throws SAXException {}
/*      */   
/*      */   public void startEntity(String name) throws SAXException {}
/*      */   
/*      */   public void endEntity(String name) throws SAXException {}
/*      */   
/*      */   public void startCDATA() throws SAXException {
/*      */     try {
/*      */       if (!this.startTagIsClosed) {
/*      */         write('>');
/*      */         this.startTagIsClosed = true;
/*      */       } 
/*      */       write("<![CDATA[");
/*      */       this.inCDATA = true;
/*      */     } catch (IOException e) {
/*      */       throw new SAXException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void endCDATA() throws SAXException {
/*      */     try {
/*      */       this.inCDATA = false;
/*      */       write("]]>");
/*      */     } catch (IOException e) {
/*      */       throw new SAXException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void comment(char[] ch, int start, int length) throws SAXException {
/*      */     try {
/*      */       this.output.write("<!--");
/*      */       this.output.write(ch, start, length);
/*      */       this.output.write("-->");
/*      */     } catch (IOException e) {
/*      */       throw new SAXException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void write(char c) throws IOException {
/*      */     this.output.write(c);
/*      */   }
/*      */   
/*      */   private void write(String s) throws IOException {
/*      */     this.output.write(s);
/*      */   }
/*      */   
/*      */   private void writeAttributes(Attributes atts) throws IOException, SAXException {
/*      */     int len = atts.getLength();
/*      */     for (int i = 0; i < len; i++) {
/*      */       char[] ch = atts.getValue(i).toCharArray();
/*      */       write(' ');
/*      */       writeName(atts.getURI(i), atts.getLocalName(i), atts.getQName(i), false);
/*      */       write("=\"");
/*      */       writeEsc(ch, 0, ch.length, true);
/*      */       write('"');
/*      */     } 
/*      */   }
/*      */   
/*      */   private void writeEsc(char[] ch, int start, int length, boolean isAttVal) throws SAXException, IOException {
/*      */     this.escapeHandler.escape(ch, start, length, isAttVal, this.output);
/*      */   }
/*      */   
/*      */   private void writeName(String uri, String localName, String qName, boolean isElement) throws IOException {
/*      */     write(qName);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/txw2/output/XMLWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */