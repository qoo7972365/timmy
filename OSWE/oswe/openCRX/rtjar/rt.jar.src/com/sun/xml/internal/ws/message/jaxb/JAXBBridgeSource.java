/*     */ package com.sun.xml.internal.ws.message.jaxb;
/*     */ 
/*     */ import com.sun.xml.internal.ws.spi.db.XMLBridge;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.transform.sax.SAXSource;
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
/*     */ final class JAXBBridgeSource
/*     */   extends SAXSource
/*     */ {
/*     */   private final XMLBridge bridge;
/*     */   private final Object contentObject;
/*     */   private final XMLReader pseudoParser;
/*     */   
/*     */   public JAXBBridgeSource(XMLBridge bridge, Object contentObject) {
/*  59 */     this.pseudoParser = new XMLFilterImpl() {
/*     */         public boolean getFeature(String name) throws SAXNotRecognizedException {
/*  61 */           if (name.equals("http://xml.org/sax/features/namespaces"))
/*  62 */             return true; 
/*  63 */           if (name.equals("http://xml.org/sax/features/namespace-prefixes"))
/*  64 */             return false; 
/*  65 */           throw new SAXNotRecognizedException(name);
/*     */         }
/*     */         private LexicalHandler lexicalHandler;
/*     */         public void setFeature(String name, boolean value) throws SAXNotRecognizedException {
/*  69 */           if (name.equals("http://xml.org/sax/features/namespaces") && value)
/*     */             return; 
/*  71 */           if (name.equals("http://xml.org/sax/features/namespace-prefixes") && !value)
/*     */             return; 
/*  73 */           throw new SAXNotRecognizedException(name);
/*     */         }
/*     */         
/*     */         public Object getProperty(String name) throws SAXNotRecognizedException {
/*  77 */           if ("http://xml.org/sax/properties/lexical-handler".equals(name)) {
/*  78 */             return this.lexicalHandler;
/*     */           }
/*  80 */           throw new SAXNotRecognizedException(name);
/*     */         }
/*     */         
/*     */         public void setProperty(String name, Object value) throws SAXNotRecognizedException {
/*  84 */           if ("http://xml.org/sax/properties/lexical-handler".equals(name)) {
/*  85 */             this.lexicalHandler = (LexicalHandler)value;
/*     */             return;
/*     */           } 
/*  88 */           throw new SAXNotRecognizedException(name);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public void parse(InputSource input) throws SAXException {
/*  94 */           parse();
/*     */         }
/*     */         
/*     */         public void parse(String systemId) throws SAXException {
/*  98 */           parse();
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void parse() throws SAXException {
/*     */           try {
/* 106 */             startDocument();
/*     */             
/* 108 */             JAXBBridgeSource.this.bridge.marshal(JAXBBridgeSource.this.contentObject, this, null);
/* 109 */             endDocument();
/* 110 */           } catch (JAXBException e) {
/*     */ 
/*     */             
/* 113 */             SAXParseException se = new SAXParseException(e.getMessage(), null, null, -1, -1, (Exception)e);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 118 */             fatalError(se);
/*     */ 
/*     */ 
/*     */             
/* 122 */             throw se;
/*     */           } 
/*     */         }
/*     */       };
/*     */     this.bridge = bridge;
/*     */     this.contentObject = contentObject;
/*     */     setXMLReader(this.pseudoParser);
/*     */     setInputSource(new InputSource());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/message/jaxb/JAXBBridgeSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */