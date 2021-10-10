/*     */ package com.sun.xml.internal.ws.util.xml;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.SAXParseException2;
/*     */ import com.sun.istack.internal.XMLStreamReaderToContentHandler;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.transform.sax.SAXSource;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.DTDHandler;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXNotRecognizedException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ import org.xml.sax.XMLReader;
/*     */ import org.xml.sax.ext.LexicalHandler;
/*     */ import org.xml.sax.helpers.XMLFilterImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StAXSource
/*     */   extends SAXSource
/*     */ {
/*     */   private final XMLStreamReaderToContentHandler reader;
/*     */   private final XMLStreamReader staxReader;
/*  90 */   private final XMLFilterImpl repeater = new XMLFilterImpl();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   private final XMLReader pseudoParser = new XMLReader() {
/*     */       private LexicalHandler lexicalHandler;
/*     */       
/*     */       public boolean getFeature(String name) throws SAXNotRecognizedException {
/*  99 */         throw new SAXNotRecognizedException(name);
/*     */       }
/*     */       private EntityResolver entityResolver; private DTDHandler dtdHandler;
/*     */       private ErrorHandler errorHandler;
/*     */       
/*     */       public void setFeature(String name, boolean value) throws SAXNotRecognizedException {
/* 105 */         if (!name.equals("http://xml.org/sax/features/namespaces") || !value)
/*     */         {
/* 107 */           if (!name.equals("http://xml.org/sax/features/namespace-prefixes") || value)
/*     */           {
/*     */             
/* 110 */             throw new SAXNotRecognizedException(name);
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */       public Object getProperty(String name) throws SAXNotRecognizedException {
/* 116 */         if ("http://xml.org/sax/properties/lexical-handler".equals(name)) {
/* 117 */           return this.lexicalHandler;
/*     */         }
/* 119 */         throw new SAXNotRecognizedException(name);
/*     */       }
/*     */ 
/*     */       
/*     */       public void setProperty(String name, Object value) throws SAXNotRecognizedException {
/* 124 */         if ("http://xml.org/sax/properties/lexical-handler".equals(name)) {
/* 125 */           this.lexicalHandler = (LexicalHandler)value;
/*     */           return;
/*     */         } 
/* 128 */         throw new SAXNotRecognizedException(name);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void setEntityResolver(EntityResolver resolver) {
/* 138 */         this.entityResolver = resolver;
/*     */       }
/*     */ 
/*     */       
/*     */       public EntityResolver getEntityResolver() {
/* 143 */         return this.entityResolver;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void setDTDHandler(DTDHandler handler) {
/* 150 */         this.dtdHandler = handler;
/*     */       }
/*     */       
/*     */       public DTDHandler getDTDHandler() {
/* 154 */         return this.dtdHandler;
/*     */       }
/*     */ 
/*     */       
/*     */       public void setContentHandler(ContentHandler handler) {
/* 159 */         StAXSource.this.repeater.setContentHandler(handler);
/*     */       }
/*     */ 
/*     */       
/*     */       public ContentHandler getContentHandler() {
/* 164 */         return StAXSource.this.repeater.getContentHandler();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void setErrorHandler(ErrorHandler handler) {
/* 171 */         this.errorHandler = handler;
/*     */       }
/*     */       
/*     */       public ErrorHandler getErrorHandler() {
/* 175 */         return this.errorHandler;
/*     */       }
/*     */ 
/*     */       
/*     */       public void parse(InputSource input) throws SAXException {
/* 180 */         parse();
/*     */       }
/*     */ 
/*     */       
/*     */       public void parse(String systemId) throws SAXException {
/* 185 */         parse();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void parse() throws SAXException {
/*     */         try {
/* 193 */           StAXSource.this.reader.bridge();
/* 194 */         } catch (XMLStreamException e) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 202 */           SAXParseException2 sAXParseException2 = new SAXParseException2(e.getMessage(), null, null, (e.getLocation() == null) ? -1 : e.getLocation().getLineNumber(), (e.getLocation() == null) ? -1 : e.getLocation().getColumnNumber(), e);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 207 */           if (this.errorHandler != null) {
/* 208 */             this.errorHandler.fatalError((SAXParseException)sAXParseException2);
/*     */           }
/*     */ 
/*     */           
/* 212 */           throw sAXParseException2;
/*     */         } finally {
/*     */           
/*     */           try {
/* 216 */             StAXSource.this.staxReader.close();
/* 217 */           } catch (XMLStreamException xMLStreamException) {}
/*     */         } 
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StAXSource(XMLStreamReader reader, boolean eagerQuit) {
/* 234 */     this(reader, eagerQuit, new String[0]);
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
/*     */   public StAXSource(XMLStreamReader reader, boolean eagerQuit, @NotNull String[] inscope) {
/* 255 */     if (reader == null)
/* 256 */       throw new IllegalArgumentException(); 
/* 257 */     this.staxReader = reader;
/*     */     
/* 259 */     int eventType = reader.getEventType();
/* 260 */     if (eventType != 7 && eventType != 1)
/*     */     {
/* 262 */       throw new IllegalStateException();
/*     */     }
/*     */     
/* 265 */     this.reader = new XMLStreamReaderToContentHandler(reader, this.repeater, eagerQuit, false, inscope);
/*     */     
/* 267 */     setXMLReader(this.pseudoParser);
/*     */     
/* 269 */     setInputSource(new InputSource());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/util/xml/StAXSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */