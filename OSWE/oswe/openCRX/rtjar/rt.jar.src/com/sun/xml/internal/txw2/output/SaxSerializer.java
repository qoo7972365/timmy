/*     */ package com.sun.xml.internal.txw2.output;
/*     */ 
/*     */ import com.sun.xml.internal.txw2.TxwException;
/*     */ import java.util.Stack;
/*     */ import javax.xml.transform.sax.SAXResult;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.ext.LexicalHandler;
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
/*     */ public class SaxSerializer
/*     */   implements XmlSerializer
/*     */ {
/*     */   private final ContentHandler writer;
/*     */   private final LexicalHandler lexical;
/*     */   
/*     */   public SaxSerializer(ContentHandler handler) {
/*  48 */     this(handler, null, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SaxSerializer(ContentHandler handler, LexicalHandler lex) {
/*  59 */     this(handler, lex, true);
/*     */   }
/*     */   
/*     */   public SaxSerializer(ContentHandler handler, LexicalHandler lex, boolean indenting) {
/*  63 */     if (!indenting) {
/*  64 */       this.writer = handler;
/*  65 */       this.lexical = lex;
/*     */     } else {
/*  67 */       IndentingXMLFilter indenter = new IndentingXMLFilter(handler, lex);
/*  68 */       this.writer = indenter;
/*  69 */       this.lexical = indenter;
/*     */     } 
/*     */   }
/*     */   
/*     */   public SaxSerializer(SAXResult result) {
/*  74 */     this(result.getHandler(), result.getLexicalHandler());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDocument() {
/*     */     try {
/*  82 */       this.writer.startDocument();
/*  83 */     } catch (SAXException e) {
/*  84 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  90 */   private final Stack<String> prefixBindings = new Stack<>();
/*     */ 
/*     */   
/*     */   public void writeXmlns(String prefix, String uri) {
/*  94 */     if (prefix == null) {
/*  95 */       prefix = "";
/*     */     }
/*     */     
/*  98 */     if (prefix.equals("xml")) {
/*     */       return;
/*     */     }
/*     */     
/* 102 */     this.prefixBindings.add(uri);
/* 103 */     this.prefixBindings.add(prefix);
/*     */   }
/*     */ 
/*     */   
/* 107 */   private final Stack<String> elementBindings = new Stack<>();
/*     */ 
/*     */   
/*     */   public void beginStartTag(String uri, String localName, String prefix) {
/* 111 */     this.elementBindings.add(getQName(prefix, localName));
/* 112 */     this.elementBindings.add(localName);
/* 113 */     this.elementBindings.add(uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 119 */   private final AttributesImpl attrs = new AttributesImpl();
/*     */   
/*     */   public void writeAttribute(String uri, String localName, String prefix, StringBuilder value) {
/* 122 */     this.attrs.addAttribute(uri, localName, 
/*     */         
/* 124 */         getQName(prefix, localName), "CDATA", value
/*     */         
/* 126 */         .toString());
/*     */   }
/*     */   
/*     */   public void endStartTag(String uri, String localName, String prefix) {
/*     */     try {
/* 131 */       while (this.prefixBindings.size() != 0) {
/* 132 */         this.writer.startPrefixMapping(this.prefixBindings.pop(), this.prefixBindings
/* 133 */             .pop());
/*     */       }
/*     */ 
/*     */       
/* 137 */       this.writer.startElement(uri, localName, 
/*     */           
/* 139 */           getQName(prefix, localName), this.attrs);
/*     */ 
/*     */       
/* 142 */       this.attrs.clear();
/* 143 */     } catch (SAXException e) {
/* 144 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void endTag() {
/*     */     try {
/* 150 */       this.writer.endElement(this.elementBindings.pop(), this.elementBindings
/* 151 */           .pop(), this.elementBindings
/* 152 */           .pop());
/*     */     }
/* 154 */     catch (SAXException e) {
/* 155 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void text(StringBuilder text) {
/*     */     try {
/* 161 */       this.writer.characters(text.toString().toCharArray(), 0, text.length());
/* 162 */     } catch (SAXException e) {
/* 163 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void cdata(StringBuilder text) {
/* 168 */     if (this.lexical == null) {
/* 169 */       throw new UnsupportedOperationException("LexicalHandler is needed to write PCDATA");
/*     */     }
/*     */     try {
/* 172 */       this.lexical.startCDATA();
/* 173 */       text(text);
/* 174 */       this.lexical.endCDATA();
/* 175 */     } catch (SAXException e) {
/* 176 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void comment(StringBuilder comment) {
/*     */     try {
/* 182 */       if (this.lexical == null) {
/* 183 */         throw new UnsupportedOperationException("LexicalHandler is needed to write comments");
/*     */       }
/* 185 */       this.lexical.comment(comment.toString().toCharArray(), 0, comment.length());
/* 186 */     } catch (SAXException e) {
/* 187 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void endDocument() {
/*     */     try {
/* 193 */       this.writer.endDocument();
/* 194 */     } catch (SAXException e) {
/* 195 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {}
/*     */ 
/*     */   
/*     */   private static String getQName(String prefix, String localName) {
/*     */     String qName;
/* 206 */     if (prefix == null || prefix.length() == 0) {
/* 207 */       qName = localName;
/*     */     } else {
/* 209 */       qName = prefix + ':' + localName;
/*     */     } 
/* 211 */     return qName;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/txw2/output/SaxSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */