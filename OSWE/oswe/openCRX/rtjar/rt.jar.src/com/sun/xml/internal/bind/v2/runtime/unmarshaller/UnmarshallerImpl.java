/*     */ package com.sun.xml.internal.bind.v2.runtime.unmarshaller;
/*     */ 
/*     */ import com.sun.xml.internal.bind.IDResolver;
/*     */ import com.sun.xml.internal.bind.api.ClassResolver;
/*     */ import com.sun.xml.internal.bind.unmarshaller.DOMScanner;
/*     */ import com.sun.xml.internal.bind.unmarshaller.InfosetScanner;
/*     */ import com.sun.xml.internal.bind.unmarshaller.Messages;
/*     */ import com.sun.xml.internal.bind.v2.ClassFactory;
/*     */ import com.sun.xml.internal.bind.v2.runtime.AssociationMap;
/*     */ import com.sun.xml.internal.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.internal.bind.v2.runtime.JaxBeanInfo;
/*     */ import com.sun.xml.internal.bind.v2.util.XmlFactory;
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.PropertyException;
/*     */ import javax.xml.bind.UnmarshalException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.UnmarshallerHandler;
/*     */ import javax.xml.bind.ValidationEvent;
/*     */ import javax.xml.bind.ValidationEventHandler;
/*     */ import javax.xml.bind.annotation.adapters.XmlAdapter;
/*     */ import javax.xml.bind.attachment.AttachmentUnmarshaller;
/*     */ import javax.xml.bind.helpers.AbstractUnmarshallerImpl;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import javax.xml.stream.XMLEventReader;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.events.XMLEvent;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.sax.SAXSource;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import javax.xml.validation.Schema;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.XMLReader;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class UnmarshallerImpl
/*     */   extends AbstractUnmarshallerImpl
/*     */   implements ValidationEventHandler, Closeable
/*     */ {
/*     */   protected final JAXBContextImpl context;
/*     */   private Schema schema;
/*     */   public final UnmarshallingContext coordinator;
/*     */   private Unmarshaller.Listener externalListener;
/*     */   private AttachmentUnmarshaller attachmentUnmarshaller;
/* 105 */   private IDResolver idResolver = new DefaultIDResolver();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private XMLReader reader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnmarshallerHandler getUnmarshallerHandler() {
/* 119 */     return getUnmarshallerHandler(true, null);
/*     */   }
/*     */   public UnmarshallerImpl(JAXBContextImpl context, AssociationMap assoc) {
/* 122 */     this.reader = null;
/*     */     this.context = context;
/*     */     this.coordinator = new UnmarshallingContext(this, assoc);
/*     */     try {
/*     */       setEventHandler(this);
/*     */     } catch (JAXBException e) {
/*     */       throw new AssertionError(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected XMLReader getXMLReader() throws JAXBException {
/* 137 */     if (this.reader == null) {
/*     */       try {
/* 139 */         SAXParserFactory parserFactory = XmlFactory.createParserFactory(this.context.disableSecurityProcessing);
/*     */ 
/*     */ 
/*     */         
/* 143 */         parserFactory.setValidating(false);
/* 144 */         this.reader = parserFactory.newSAXParser().getXMLReader();
/* 145 */       } catch (ParserConfigurationException e) {
/* 146 */         throw new JAXBException(e);
/* 147 */       } catch (SAXException e) {
/* 148 */         throw new JAXBException(e);
/*     */       } 
/*     */     }
/* 151 */     return this.reader;
/*     */   }
/*     */   
/*     */   private SAXConnector getUnmarshallerHandler(boolean intern, JaxBeanInfo expectedType) {
/* 155 */     XmlVisitor h = createUnmarshallerHandler((InfosetScanner)null, false, expectedType);
/* 156 */     if (intern) {
/* 157 */       h = new InterningXmlVisitor(h);
/*     */     }
/* 159 */     return new SAXConnector(h, null);
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
/*     */   public final XmlVisitor createUnmarshallerHandler(InfosetScanner scanner, boolean inplace, JaxBeanInfo expectedType) {
/* 177 */     this.coordinator.reset(scanner, inplace, expectedType, this.idResolver);
/* 178 */     XmlVisitor unmarshaller = this.coordinator;
/*     */ 
/*     */     
/* 181 */     if (this.schema != null) {
/* 182 */       unmarshaller = new ValidatingUnmarshaller(this.schema, unmarshaller);
/*     */     }
/*     */     
/* 185 */     if (this.attachmentUnmarshaller != null && this.attachmentUnmarshaller.isXOPPackage()) {
/* 186 */       unmarshaller = new MTOMDecorator(this, unmarshaller, this.attachmentUnmarshaller);
/*     */     }
/*     */     
/* 189 */     return unmarshaller;
/*     */   }
/*     */   
/* 192 */   private static final DefaultHandler dummyHandler = new DefaultHandler();
/*     */   public static final String FACTORY = "com.sun.xml.internal.bind.ObjectFactory";
/*     */   
/*     */   public static boolean needsInterning(XMLReader reader) {
/*     */     try {
/* 197 */       reader.setFeature("http://xml.org/sax/features/string-interning", true);
/* 198 */     } catch (SAXException sAXException) {}
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 203 */       if (reader.getFeature("http://xml.org/sax/features/string-interning")) {
/* 204 */         return false;
/*     */       }
/* 206 */     } catch (SAXException sAXException) {}
/*     */ 
/*     */ 
/*     */     
/* 210 */     return true;
/*     */   }
/*     */   
/*     */   protected Object unmarshal(XMLReader reader, InputSource source) throws JAXBException {
/* 214 */     return unmarshal0(reader, source, (JaxBeanInfo)null);
/*     */   }
/*     */   
/*     */   protected <T> JAXBElement<T> unmarshal(XMLReader reader, InputSource source, Class<T> expectedType) throws JAXBException {
/* 218 */     if (expectedType == null) {
/* 219 */       throw new IllegalArgumentException();
/*     */     }
/* 221 */     return (JAXBElement<T>)unmarshal0(reader, source, getBeanInfo(expectedType));
/*     */   }
/*     */ 
/*     */   
/*     */   private Object unmarshal0(XMLReader reader, InputSource source, JaxBeanInfo expectedType) throws JAXBException {
/* 226 */     SAXConnector connector = getUnmarshallerHandler(needsInterning(reader), expectedType);
/*     */     
/* 228 */     reader.setContentHandler((ContentHandler)connector);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 240 */     reader.setErrorHandler(this.coordinator);
/*     */     
/*     */     try {
/* 243 */       reader.parse(source);
/* 244 */     } catch (IOException e) {
/* 245 */       this.coordinator.clearStates();
/* 246 */       throw new UnmarshalException(e);
/* 247 */     } catch (SAXException e) {
/* 248 */       this.coordinator.clearStates();
/* 249 */       throw createUnmarshalException(e);
/*     */     } 
/*     */     
/* 252 */     Object result = connector.getResult();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 257 */     reader.setContentHandler(dummyHandler);
/* 258 */     reader.setErrorHandler(dummyHandler);
/*     */     
/* 260 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> JAXBElement<T> unmarshal(Source source, Class<T> expectedType) throws JAXBException {
/* 265 */     if (source instanceof SAXSource) {
/* 266 */       SAXSource ss = (SAXSource)source;
/*     */       
/* 268 */       XMLReader locReader = ss.getXMLReader();
/* 269 */       if (locReader == null) {
/* 270 */         locReader = getXMLReader();
/*     */       }
/*     */       
/* 273 */       return unmarshal(locReader, ss.getInputSource(), expectedType);
/*     */     } 
/* 275 */     if (source instanceof StreamSource) {
/* 276 */       return unmarshal(getXMLReader(), streamSourceToInputSource((StreamSource)source), expectedType);
/*     */     }
/* 278 */     if (source instanceof DOMSource) {
/* 279 */       return unmarshal(((DOMSource)source).getNode(), expectedType);
/*     */     }
/*     */ 
/*     */     
/* 283 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   public Object unmarshal0(Source source, JaxBeanInfo expectedType) throws JAXBException {
/* 287 */     if (source instanceof SAXSource) {
/* 288 */       SAXSource ss = (SAXSource)source;
/*     */       
/* 290 */       XMLReader locReader = ss.getXMLReader();
/* 291 */       if (locReader == null) {
/* 292 */         locReader = getXMLReader();
/*     */       }
/*     */       
/* 295 */       return unmarshal0(locReader, ss.getInputSource(), expectedType);
/*     */     } 
/* 297 */     if (source instanceof StreamSource) {
/* 298 */       return unmarshal0(getXMLReader(), streamSourceToInputSource((StreamSource)source), expectedType);
/*     */     }
/* 300 */     if (source instanceof DOMSource) {
/* 301 */       return unmarshal0(((DOMSource)source).getNode(), expectedType);
/*     */     }
/*     */ 
/*     */     
/* 305 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final ValidationEventHandler getEventHandler() {
/*     */     try {
/* 312 */       return super.getEventHandler();
/* 313 */     } catch (JAXBException e) {
/*     */       
/* 315 */       throw new AssertionError();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean hasEventHandler() {
/* 325 */     return (getEventHandler() != this);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> JAXBElement<T> unmarshal(Node node, Class<T> expectedType) throws JAXBException {
/* 330 */     if (expectedType == null) {
/* 331 */       throw new IllegalArgumentException();
/*     */     }
/* 333 */     return (JAXBElement<T>)unmarshal0(node, getBeanInfo(expectedType));
/*     */   }
/*     */   
/*     */   public final Object unmarshal(Node node) throws JAXBException {
/* 337 */     return unmarshal0(node, (JaxBeanInfo)null);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public final Object unmarshal(SAXSource source) throws JAXBException {
/* 343 */     return unmarshal(source);
/*     */   }
/*     */   
/*     */   public final Object unmarshal0(Node node, JaxBeanInfo expectedType) throws JAXBException {
/*     */     try {
/* 348 */       DOMScanner scanner = new DOMScanner();
/*     */       
/* 350 */       InterningXmlVisitor handler = new InterningXmlVisitor(createUnmarshallerHandler((InfosetScanner)null, false, expectedType));
/* 351 */       scanner.setContentHandler((ContentHandler)new SAXConnector(handler, (LocatorEx)scanner));
/*     */       
/* 353 */       if (node.getNodeType() == 1) {
/* 354 */         scanner.scan((Element)node);
/* 355 */       } else if (node.getNodeType() == 9) {
/* 356 */         scanner.scan((Document)node);
/*     */       } else {
/* 358 */         throw new IllegalArgumentException("Unexpected node type: " + node);
/*     */       } 
/*     */       
/* 361 */       Object retVal = handler.getContext().getResult();
/* 362 */       handler.getContext().clearResult();
/* 363 */       return retVal;
/* 364 */     } catch (SAXException e) {
/* 365 */       throw createUnmarshalException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object unmarshal(XMLStreamReader reader) throws JAXBException {
/* 371 */     return unmarshal0(reader, (JaxBeanInfo)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> JAXBElement<T> unmarshal(XMLStreamReader reader, Class<T> expectedType) throws JAXBException {
/* 376 */     if (expectedType == null) {
/* 377 */       throw new IllegalArgumentException();
/*     */     }
/* 379 */     return (JAXBElement<T>)unmarshal0(reader, getBeanInfo(expectedType));
/*     */   }
/*     */   
/*     */   public Object unmarshal0(XMLStreamReader reader, JaxBeanInfo expectedType) throws JAXBException {
/* 383 */     if (reader == null) {
/* 384 */       throw new IllegalArgumentException(
/* 385 */           Messages.format("Unmarshaller.NullReader"));
/*     */     }
/*     */     
/* 388 */     int eventType = reader.getEventType();
/* 389 */     if (eventType != 1 && eventType != 7)
/*     */     {
/*     */       
/* 392 */       throw new IllegalStateException(
/* 393 */           Messages.format("Unmarshaller.IllegalReaderState", Integer.valueOf(eventType)));
/*     */     }
/*     */     
/* 396 */     XmlVisitor h = createUnmarshallerHandler((InfosetScanner)null, false, expectedType);
/* 397 */     StAXConnector connector = StAXStreamConnector.create(reader, h);
/*     */     
/*     */     try {
/* 400 */       connector.bridge();
/* 401 */     } catch (XMLStreamException e) {
/* 402 */       throw handleStreamException(e);
/*     */     } 
/*     */     
/* 405 */     Object retVal = h.getContext().getResult();
/* 406 */     h.getContext().clearResult();
/* 407 */     return retVal;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> JAXBElement<T> unmarshal(XMLEventReader reader, Class<T> expectedType) throws JAXBException {
/* 412 */     if (expectedType == null) {
/* 413 */       throw new IllegalArgumentException();
/*     */     }
/* 415 */     return (JAXBElement<T>)unmarshal0(reader, getBeanInfo(expectedType));
/*     */   }
/*     */ 
/*     */   
/*     */   public Object unmarshal(XMLEventReader reader) throws JAXBException {
/* 420 */     return unmarshal0(reader, (JaxBeanInfo)null);
/*     */   }
/*     */   
/*     */   private Object unmarshal0(XMLEventReader reader, JaxBeanInfo expectedType) throws JAXBException {
/* 424 */     if (reader == null) {
/* 425 */       throw new IllegalArgumentException(
/* 426 */           Messages.format("Unmarshaller.NullReader"));
/*     */     }
/*     */     
/*     */     try {
/* 430 */       XMLEvent event = reader.peek();
/*     */       
/* 432 */       if (!event.isStartElement() && !event.isStartDocument())
/*     */       {
/* 434 */         throw new IllegalStateException(
/* 435 */             Messages.format("Unmarshaller.IllegalReaderState", 
/* 436 */               Integer.valueOf(event.getEventType())));
/*     */       }
/*     */ 
/*     */       
/* 440 */       boolean isZephyr = reader.getClass().getName().equals("com.sun.xml.internal.stream.XMLReaderImpl");
/* 441 */       XmlVisitor h = createUnmarshallerHandler((InfosetScanner)null, false, expectedType);
/* 442 */       if (!isZephyr) {
/* 443 */         h = new InterningXmlVisitor(h);
/*     */       }
/* 445 */       (new StAXEventConnector(reader, h)).bridge();
/* 446 */       return h.getContext().getResult();
/* 447 */     } catch (XMLStreamException e) {
/* 448 */       throw handleStreamException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object unmarshal0(InputStream input, JaxBeanInfo expectedType) throws JAXBException {
/* 453 */     return unmarshal0(getXMLReader(), new InputSource(input), expectedType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static JAXBException handleStreamException(XMLStreamException e) {
/* 463 */     Throwable ne = e.getNestedException();
/* 464 */     if (ne instanceof JAXBException) {
/* 465 */       return (JAXBException)ne;
/*     */     }
/* 467 */     if (ne instanceof SAXException) {
/* 468 */       return (JAXBException)new UnmarshalException(ne);
/*     */     }
/* 470 */     return (JAXBException)new UnmarshalException(e);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getProperty(String name) throws PropertyException {
/* 475 */     if (name.equals(IDResolver.class.getName())) {
/* 476 */       return this.idResolver;
/*     */     }
/* 478 */     return super.getProperty(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setProperty(String name, Object value) throws PropertyException {
/* 483 */     if (name.equals("com.sun.xml.internal.bind.ObjectFactory")) {
/* 484 */       this.coordinator.setFactories(value);
/*     */       return;
/*     */     } 
/* 487 */     if (name.equals(IDResolver.class.getName())) {
/* 488 */       this.idResolver = (IDResolver)value;
/*     */       return;
/*     */     } 
/* 491 */     if (name.equals(ClassResolver.class.getName())) {
/* 492 */       this.coordinator.classResolver = (ClassResolver)value;
/*     */       return;
/*     */     } 
/* 495 */     if (name.equals(ClassLoader.class.getName())) {
/* 496 */       this.coordinator.classLoader = (ClassLoader)value;
/*     */       return;
/*     */     } 
/* 499 */     super.setProperty(name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSchema(Schema schema) {
/* 506 */     this.schema = schema;
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema getSchema() {
/* 511 */     return this.schema;
/*     */   }
/*     */ 
/*     */   
/*     */   public AttachmentUnmarshaller getAttachmentUnmarshaller() {
/* 516 */     return this.attachmentUnmarshaller;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAttachmentUnmarshaller(AttachmentUnmarshaller au) {
/* 521 */     this.attachmentUnmarshaller = au;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValidating() {
/* 529 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValidating(boolean validating) {
/* 537 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public <A extends XmlAdapter> void setAdapter(Class<A> type, A adapter) {
/* 542 */     if (type == null) {
/* 543 */       throw new IllegalArgumentException();
/*     */     }
/* 545 */     this.coordinator.putAdapter(type, (XmlAdapter)adapter);
/*     */   }
/*     */ 
/*     */   
/*     */   public <A extends XmlAdapter> A getAdapter(Class<A> type) {
/* 550 */     if (type == null) {
/* 551 */       throw new IllegalArgumentException();
/*     */     }
/* 553 */     if (this.coordinator.containsAdapter(type)) {
/* 554 */       return (A)this.coordinator.getAdapter(type);
/*     */     }
/* 556 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnmarshalException createUnmarshalException(SAXException e) {
/* 563 */     return super.createUnmarshalException(e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleEvent(ValidationEvent event) {
/* 571 */     return (event.getSeverity() != 2);
/*     */   }
/*     */   
/*     */   private static InputSource streamSourceToInputSource(StreamSource ss) {
/* 575 */     InputSource is = new InputSource();
/* 576 */     is.setSystemId(ss.getSystemId());
/* 577 */     is.setByteStream(ss.getInputStream());
/* 578 */     is.setCharacterStream(ss.getReader());
/*     */     
/* 580 */     return is;
/*     */   }
/*     */   
/*     */   public <T> JaxBeanInfo<T> getBeanInfo(Class<T> clazz) throws JAXBException {
/* 584 */     return this.context.getBeanInfo(clazz, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Unmarshaller.Listener getListener() {
/* 589 */     return this.externalListener;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setListener(Unmarshaller.Listener listener) {
/* 594 */     this.externalListener = listener;
/*     */   }
/*     */   
/*     */   public UnmarshallingContext getContext() {
/* 598 */     return this.coordinator;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*     */     try {
/* 605 */       ClassFactory.cleanCache();
/*     */     } finally {
/* 607 */       super.finalize();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 616 */     ClassFactory.cleanCache();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/unmarshaller/UnmarshallerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */