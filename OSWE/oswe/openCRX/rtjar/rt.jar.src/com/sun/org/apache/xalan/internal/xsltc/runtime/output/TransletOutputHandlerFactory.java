/*     */ package com.sun.org.apache.xalan.internal.xsltc.runtime.output;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.xsltc.trax.SAX2DOM;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.trax.SAX2StAXEventWriter;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.trax.SAX2StAXStreamWriter;
/*     */ import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
/*     */ import com.sun.org.apache.xml.internal.serializer.ToHTMLSAXHandler;
/*     */ import com.sun.org.apache.xml.internal.serializer.ToHTMLStream;
/*     */ import com.sun.org.apache.xml.internal.serializer.ToTextSAXHandler;
/*     */ import com.sun.org.apache.xml.internal.serializer.ToTextStream;
/*     */ import com.sun.org.apache.xml.internal.serializer.ToUnknownStream;
/*     */ import com.sun.org.apache.xml.internal.serializer.ToXMLSAXHandler;
/*     */ import com.sun.org.apache.xml.internal.serializer.ToXMLStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Writer;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.stream.XMLEventWriter;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.ext.LexicalHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TransletOutputHandlerFactory
/*     */ {
/*     */   public static final int STREAM = 0;
/*     */   public static final int SAX = 1;
/*     */   public static final int DOM = 2;
/*     */   public static final int STAX = 3;
/*  61 */   private String _encoding = "utf-8";
/*  62 */   private String _method = null;
/*  63 */   private int _outputType = 0;
/*  64 */   private OutputStream _ostream = System.out;
/*  65 */   private Writer _writer = null;
/*  66 */   private Node _node = null;
/*  67 */   private Node _nextSibling = null;
/*  68 */   private XMLEventWriter _xmlStAXEventWriter = null;
/*  69 */   private XMLStreamWriter _xmlStAXStreamWriter = null;
/*  70 */   private int _indentNumber = -1;
/*  71 */   private ContentHandler _handler = null;
/*  72 */   private LexicalHandler _lexHandler = null;
/*     */   
/*     */   private boolean _overrideDefaultParser;
/*     */   
/*     */   public static TransletOutputHandlerFactory newInstance() {
/*  77 */     return new TransletOutputHandlerFactory(true);
/*     */   }
/*     */   public static TransletOutputHandlerFactory newInstance(boolean overrideDefaultParser) {
/*  80 */     return new TransletOutputHandlerFactory(overrideDefaultParser);
/*     */   }
/*     */   
/*     */   public TransletOutputHandlerFactory(boolean overrideDefaultParser) {
/*  84 */     this._overrideDefaultParser = overrideDefaultParser;
/*     */   }
/*     */   public void setOutputType(int outputType) {
/*  87 */     this._outputType = outputType;
/*     */   }
/*     */   
/*     */   public void setEncoding(String encoding) {
/*  91 */     if (encoding != null) {
/*  92 */       this._encoding = encoding;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setOutputMethod(String method) {
/*  97 */     this._method = method;
/*     */   }
/*     */   
/*     */   public void setOutputStream(OutputStream ostream) {
/* 101 */     this._ostream = ostream;
/*     */   }
/*     */   
/*     */   public void setWriter(Writer writer) {
/* 105 */     this._writer = writer;
/*     */   }
/*     */   
/*     */   public void setHandler(ContentHandler handler) {
/* 109 */     this._handler = handler;
/*     */   }
/*     */   
/*     */   public void setLexicalHandler(LexicalHandler lex) {
/* 113 */     this._lexHandler = lex;
/*     */   }
/*     */   
/*     */   public void setNode(Node node) {
/* 117 */     this._node = node;
/*     */   }
/*     */   
/*     */   public Node getNode() {
/* 121 */     return (this._handler instanceof SAX2DOM) ? ((SAX2DOM)this._handler).getDOM() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNextSibling(Node nextSibling) {
/* 126 */     this._nextSibling = nextSibling;
/*     */   }
/*     */   
/*     */   public XMLEventWriter getXMLEventWriter() {
/* 130 */     return (this._handler instanceof SAX2StAXEventWriter) ? ((SAX2StAXEventWriter)this._handler).getEventWriter() : null;
/*     */   }
/*     */   
/*     */   public void setXMLEventWriter(XMLEventWriter eventWriter) {
/* 134 */     this._xmlStAXEventWriter = eventWriter;
/*     */   }
/*     */   
/*     */   public XMLStreamWriter getXMLStreamWriter() {
/* 138 */     return (this._handler instanceof SAX2StAXStreamWriter) ? ((SAX2StAXStreamWriter)this._handler).getStreamWriter() : null;
/*     */   }
/*     */   
/*     */   public void setXMLStreamWriter(XMLStreamWriter streamWriter) {
/* 142 */     this._xmlStAXStreamWriter = streamWriter;
/*     */   }
/*     */   
/*     */   public void setIndentNumber(int value) {
/* 146 */     this._indentNumber = value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SerializationHandler getSerializationHandler() throws IOException, ParserConfigurationException {
/* 152 */     SerializationHandler result = null;
/* 153 */     switch (this._outputType) {
/*     */ 
/*     */       
/*     */       case 0:
/* 157 */         if (this._method == null) {
/*     */           
/* 159 */           result = new ToUnknownStream();
/*     */         }
/* 161 */         else if (this._method.equalsIgnoreCase("xml")) {
/*     */ 
/*     */           
/* 164 */           result = new ToXMLStream();
/*     */         
/*     */         }
/* 167 */         else if (this._method.equalsIgnoreCase("html")) {
/*     */ 
/*     */           
/* 170 */           result = new ToHTMLStream();
/*     */         
/*     */         }
/* 173 */         else if (this._method.equalsIgnoreCase("text")) {
/*     */ 
/*     */           
/* 176 */           result = new ToTextStream();
/*     */         } 
/*     */ 
/*     */         
/* 180 */         if (result != null && this._indentNumber >= 0)
/*     */         {
/* 182 */           result.setIndentAmount(this._indentNumber);
/*     */         }
/*     */         
/* 185 */         result.setEncoding(this._encoding);
/*     */         
/* 187 */         if (this._writer != null) {
/*     */           
/* 189 */           result.setWriter(this._writer);
/*     */         }
/*     */         else {
/*     */           
/* 193 */           result.setOutputStream(this._ostream);
/*     */         } 
/* 195 */         return result;
/*     */       
/*     */       case 2:
/* 198 */         this._handler = (this._node != null) ? new SAX2DOM(this._node, this._nextSibling, this._overrideDefaultParser) : new SAX2DOM(this._overrideDefaultParser);
/*     */ 
/*     */         
/* 201 */         this._lexHandler = (LexicalHandler)this._handler;
/*     */       
/*     */       case 3:
/* 204 */         if (this._xmlStAXEventWriter != null) {
/* 205 */           this._handler = new SAX2StAXEventWriter(this._xmlStAXEventWriter);
/* 206 */         } else if (this._xmlStAXStreamWriter != null) {
/* 207 */           this._handler = new SAX2StAXStreamWriter(this._xmlStAXStreamWriter);
/*     */         } 
/* 209 */         this._lexHandler = (LexicalHandler)this._handler;
/*     */       
/*     */       case 1:
/* 212 */         if (this._method == null)
/*     */         {
/* 214 */           this._method = "xml";
/*     */         }
/*     */         
/* 217 */         if (this._method.equalsIgnoreCase("xml")) {
/*     */ 
/*     */           
/* 220 */           if (this._lexHandler == null)
/*     */           {
/* 222 */             result = new ToXMLSAXHandler(this._handler, this._encoding);
/*     */           }
/*     */           else
/*     */           {
/* 226 */             result = new ToXMLSAXHandler(this._handler, this._lexHandler, this._encoding);
/*     */ 
/*     */           
/*     */           }
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 234 */         else if (this._method.equalsIgnoreCase("html")) {
/*     */ 
/*     */           
/* 237 */           if (this._lexHandler == null)
/*     */           {
/* 239 */             result = new ToHTMLSAXHandler(this._handler, this._encoding);
/*     */           }
/*     */           else
/*     */           {
/* 243 */             result = new ToHTMLSAXHandler(this._handler, this._lexHandler, this._encoding);
/*     */ 
/*     */           
/*     */           }
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 251 */         else if (this._method.equalsIgnoreCase("text")) {
/*     */ 
/*     */           
/* 254 */           if (this._lexHandler == null) {
/*     */             
/* 256 */             result = new ToTextSAXHandler(this._handler, this._encoding);
/*     */           }
/*     */           else {
/*     */             
/* 260 */             result = new ToTextSAXHandler(this._handler, this._lexHandler, this._encoding);
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 268 */         return result;
/*     */     } 
/* 270 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/runtime/output/TransletOutputHandlerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */