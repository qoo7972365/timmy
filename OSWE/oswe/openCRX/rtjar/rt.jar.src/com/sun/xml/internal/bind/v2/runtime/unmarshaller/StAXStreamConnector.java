/*     */ package com.sun.xml.internal.bind.v2.runtime.unmarshaller;
/*     */ 
/*     */ import com.sun.xml.internal.bind.WhiteSpaceProcessor;
/*     */ import java.lang.reflect.Constructor;
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
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
/*     */ class StAXStreamConnector
/*     */   extends StAXConnector
/*     */ {
/*     */   private final XMLStreamReader staxStreamReader;
/*     */   
/*     */   public static StAXConnector create(XMLStreamReader reader, XmlVisitor visitor) {
/*  62 */     Class<?> readerClass = reader.getClass();
/*  63 */     if (FI_STAX_READER_CLASS != null && FI_STAX_READER_CLASS.isAssignableFrom(readerClass) && FI_CONNECTOR_CTOR != null) {
/*     */       try {
/*  65 */         return FI_CONNECTOR_CTOR.newInstance(new Object[] { reader, visitor });
/*  66 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  71 */     boolean isZephyr = readerClass.getName().equals("com.sun.xml.internal.stream.XMLReaderImpl");
/*  72 */     if (!getBoolProp(reader, "org.codehaus.stax2.internNames") || 
/*  73 */       !getBoolProp(reader, "org.codehaus.stax2.internNsUris"))
/*     */     {
/*     */       
/*  76 */       if (!isZephyr)
/*     */       {
/*     */         
/*  79 */         if (!checkImplementaionNameOfSjsxp(reader))
/*     */         {
/*     */           
/*  82 */           visitor = new InterningXmlVisitor(visitor); }  } 
/*     */     }
/*  84 */     if (STAX_EX_READER_CLASS != null && STAX_EX_READER_CLASS.isAssignableFrom(readerClass)) {
/*     */       try {
/*  86 */         return STAX_EX_CONNECTOR_CTOR.newInstance(new Object[] { reader, visitor });
/*  87 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */     
/*  91 */     return new StAXStreamConnector(reader, visitor);
/*     */   }
/*     */   
/*     */   private static boolean checkImplementaionNameOfSjsxp(XMLStreamReader reader) {
/*     */     try {
/*  96 */       Object name = reader.getProperty("http://java.sun.com/xml/stream/properties/implementation-name");
/*  97 */       return (name != null && name.equals("sjsxp"));
/*  98 */     } catch (Exception e) {
/*     */ 
/*     */       
/* 101 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean getBoolProp(XMLStreamReader r, String n) {
/*     */     try {
/* 107 */       Object o = r.getProperty(n);
/* 108 */       if (o instanceof Boolean) return ((Boolean)o).booleanValue(); 
/* 109 */       return false;
/* 110 */     } catch (Exception e) {
/*     */ 
/*     */       
/* 113 */       return false;
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
/* 125 */   protected final StringBuilder buffer = new StringBuilder();
/*     */ 
/*     */   
/*     */   protected boolean textReported = false;
/*     */   
/*     */   private final Attributes attributes;
/*     */ 
/*     */   
/*     */   protected StAXStreamConnector(XMLStreamReader staxStreamReader, XmlVisitor visitor) {
/* 134 */     super(visitor);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 237 */     this.attributes = new Attributes() {
/*     */         public int getLength() {
/* 239 */           return StAXStreamConnector.this.staxStreamReader.getAttributeCount();
/*     */         }
/*     */         
/*     */         public String getURI(int index) {
/* 243 */           String uri = StAXStreamConnector.this.staxStreamReader.getAttributeNamespace(index);
/* 244 */           if (uri == null) return ""; 
/* 245 */           return uri;
/*     */         }
/*     */         
/*     */         public String getLocalName(int index) {
/* 249 */           return StAXStreamConnector.this.staxStreamReader.getAttributeLocalName(index);
/*     */         }
/*     */         
/*     */         public String getQName(int index) {
/* 253 */           String prefix = StAXStreamConnector.this.staxStreamReader.getAttributePrefix(index);
/* 254 */           if (prefix == null || prefix.length() == 0) {
/* 255 */             return getLocalName(index);
/*     */           }
/* 257 */           return prefix + ':' + getLocalName(index);
/*     */         }
/*     */         
/*     */         public String getType(int index) {
/* 261 */           return StAXStreamConnector.this.staxStreamReader.getAttributeType(index);
/*     */         }
/*     */         
/*     */         public String getValue(int index) {
/* 265 */           return StAXStreamConnector.this.staxStreamReader.getAttributeValue(index);
/*     */         }
/*     */         
/*     */         public int getIndex(String uri, String localName) {
/* 269 */           for (int i = getLength() - 1; i >= 0; i--) {
/* 270 */             if (localName.equals(getLocalName(i)) && uri.equals(getURI(i)))
/* 271 */               return i; 
/* 272 */           }  return -1;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public int getIndex(String qName) {
/* 278 */           for (int i = getLength() - 1; i >= 0; i--) {
/* 279 */             if (qName.equals(getQName(i)))
/* 280 */               return i; 
/*     */           } 
/* 282 */           return -1;
/*     */         }
/*     */         
/*     */         public String getType(String uri, String localName) {
/* 286 */           int index = getIndex(uri, localName);
/* 287 */           if (index < 0) return null; 
/* 288 */           return getType(index);
/*     */         }
/*     */         
/*     */         public String getType(String qName) {
/* 292 */           int index = getIndex(qName);
/* 293 */           if (index < 0) return null; 
/* 294 */           return getType(index);
/*     */         }
/*     */         
/*     */         public String getValue(String uri, String localName) {
/* 298 */           int index = getIndex(uri, localName);
/* 299 */           if (index < 0) return null; 
/* 300 */           return getValue(index);
/*     */         }
/*     */         
/*     */         public String getValue(String qName) {
/* 304 */           int index = getIndex(qName);
/* 305 */           if (index < 0) return null; 
/* 306 */           return getValue(index); }
/*     */       }; this.staxStreamReader = staxStreamReader;
/*     */   } public void bridge() throws XMLStreamException { try { int depth = 0; int event = this.staxStreamReader.getEventType(); if (event == 7) while (!this.staxStreamReader.isStartElement())
/*     */           event = this.staxStreamReader.next();   if (event != 1)
/*     */         throw new IllegalStateException("The current event is not START_ELEMENT\n but " + event);  handleStartDocument(this.staxStreamReader.getNamespaceContext()); while (true) { switch (event) { case 1: handleStartElement(); depth++; break;case 2: depth--; handleEndElement(); if (depth == 0)
/* 311 */               break;  break;case 4: case 6: case 12: handleCharacters(); break; }  event = this.staxStreamReader.next(); }  this.staxStreamReader.next(); handleEndDocument(); } catch (SAXException e) { throw new XMLStreamException(e); }  } protected Location getCurrentLocation() { return this.staxStreamReader.getLocation(); } protected void handleCharacters() throws XMLStreamException, SAXException { if (this.predictor.expectText())
/* 312 */       this.buffer.append(this.staxStreamReader
/* 313 */           .getTextCharacters(), this.staxStreamReader
/* 314 */           .getTextStart(), this.staxStreamReader
/* 315 */           .getTextLength());  }
/*     */   protected String getCurrentQName() { return getQName(this.staxStreamReader.getPrefix(), this.staxStreamReader.getLocalName()); }
/*     */   private void handleEndElement() throws SAXException { processText(false); this.tagName.uri = fixNull(this.staxStreamReader.getNamespaceURI()); this.tagName.local = this.staxStreamReader.getLocalName(); this.visitor.endElement(this.tagName); int nsCount = this.staxStreamReader.getNamespaceCount(); for (int i = nsCount - 1; i >= 0; i--) this.visitor.endPrefixMapping(fixNull(this.staxStreamReader.getNamespacePrefix(i)));  }
/*     */   private void handleStartElement() throws SAXException { processText(true); int nsCount = this.staxStreamReader.getNamespaceCount(); for (int i = 0; i < nsCount; i++)
/* 319 */       this.visitor.startPrefixMapping(fixNull(this.staxStreamReader.getNamespacePrefix(i)), fixNull(this.staxStreamReader.getNamespaceURI(i)));  this.tagName.uri = fixNull(this.staxStreamReader.getNamespaceURI()); this.tagName.local = this.staxStreamReader.getLocalName(); this.tagName.atts = this.attributes; this.visitor.startElement(this.tagName); } private void processText(boolean ignorable) throws SAXException { if (this.predictor.expectText() && (!ignorable || !WhiteSpaceProcessor.isWhiteSpace(this.buffer) || this.context.getCurrentState().isMixed())) {
/* 320 */       if (this.textReported) {
/* 321 */         this.textReported = false;
/*     */       } else {
/* 323 */         this.visitor.text(this.buffer);
/*     */       } 
/*     */     }
/* 326 */     this.buffer.setLength(0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 334 */   private static final Class FI_STAX_READER_CLASS = initFIStAXReaderClass();
/* 335 */   private static final Constructor<? extends StAXConnector> FI_CONNECTOR_CTOR = initFastInfosetConnectorClass();
/*     */   
/*     */   private static Class initFIStAXReaderClass() {
/*     */     try {
/* 339 */       Class<?> fisr = Class.forName("com.sun.xml.internal.org.jvnet.fastinfoset.stax.FastInfosetStreamReader");
/* 340 */       Class<?> sdp = Class.forName("com.sun.xml.internal.fastinfoset.stax.StAXDocumentParser");
/*     */       
/* 342 */       if (fisr.isAssignableFrom(sdp)) {
/* 343 */         return sdp;
/*     */       }
/* 345 */       return null;
/* 346 */     } catch (Throwable e) {
/* 347 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Constructor<? extends StAXConnector> initFastInfosetConnectorClass() {
/*     */     try {
/* 353 */       if (FI_STAX_READER_CLASS == null) {
/* 354 */         return null;
/*     */       }
/* 356 */       Class<?> c = Class.forName("com.sun.xml.internal.bind.v2.runtime.unmarshaller.FastInfosetConnector");
/*     */       
/* 358 */       return (Constructor)c.getConstructor(new Class[] { FI_STAX_READER_CLASS, XmlVisitor.class });
/* 359 */     } catch (Throwable e) {
/* 360 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 367 */   private static final Class STAX_EX_READER_CLASS = initStAXExReader();
/* 368 */   private static final Constructor<? extends StAXConnector> STAX_EX_CONNECTOR_CTOR = initStAXExConnector();
/*     */   
/*     */   private static Class initStAXExReader() {
/*     */     try {
/* 372 */       return Class.forName("com.sun.xml.internal.org.jvnet.staxex.XMLStreamReaderEx");
/* 373 */     } catch (Throwable e) {
/* 374 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Constructor<? extends StAXConnector> initStAXExConnector() {
/*     */     try {
/* 380 */       Class<?> c = Class.forName("com.sun.xml.internal.bind.v2.runtime.unmarshaller.StAXExConnector");
/* 381 */       return (Constructor)c.getConstructor(new Class[] { STAX_EX_READER_CLASS, XmlVisitor.class });
/* 382 */     } catch (Throwable e) {
/* 383 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/unmarshaller/StAXStreamConnector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */