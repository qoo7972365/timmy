/*     */ package com.sun.xml.internal.bind.v2.runtime;
/*     */ 
/*     */ import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;
/*     */ import com.sun.xml.internal.bind.marshaller.DataWriter;
/*     */ import com.sun.xml.internal.bind.marshaller.DumbEscapeHandler;
/*     */ import com.sun.xml.internal.bind.marshaller.MinimumEscapeHandler;
/*     */ import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;
/*     */ import com.sun.xml.internal.bind.marshaller.NioEscapeHandler;
/*     */ import com.sun.xml.internal.bind.marshaller.SAX2DOMEx;
/*     */ import com.sun.xml.internal.bind.marshaller.XMLWriter;
/*     */ import com.sun.xml.internal.bind.v2.runtime.output.C14nXmlOutput;
/*     */ import com.sun.xml.internal.bind.v2.runtime.output.Encoded;
/*     */ import com.sun.xml.internal.bind.v2.runtime.output.ForkXmlOutput;
/*     */ import com.sun.xml.internal.bind.v2.runtime.output.IndentingUTF8XmlOutput;
/*     */ import com.sun.xml.internal.bind.v2.runtime.output.NamespaceContextImpl;
/*     */ import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;
/*     */ import com.sun.xml.internal.bind.v2.runtime.output.UTF8XmlOutput;
/*     */ import com.sun.xml.internal.bind.v2.runtime.output.XMLEventWriterOutput;
/*     */ import com.sun.xml.internal.bind.v2.runtime.output.XMLStreamWriterOutput;
/*     */ import com.sun.xml.internal.bind.v2.runtime.output.XmlOutput;
/*     */ import com.sun.xml.internal.bind.v2.util.FatalAdapter;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.Closeable;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.Flushable;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.io.Writer;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.MarshalException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.PropertyException;
/*     */ import javax.xml.bind.ValidationEvent;
/*     */ import javax.xml.bind.ValidationEventHandler;
/*     */ import javax.xml.bind.annotation.adapters.XmlAdapter;
/*     */ import javax.xml.bind.attachment.AttachmentMarshaller;
/*     */ import javax.xml.bind.helpers.AbstractMarshallerImpl;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.stream.XMLEventWriter;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import javax.xml.transform.sax.SAXResult;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import javax.xml.validation.Schema;
/*     */ import javax.xml.validation.ValidatorHandler;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.SAXException;
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
/*     */ public final class MarshallerImpl
/*     */   extends AbstractMarshallerImpl
/*     */   implements ValidationEventHandler
/*     */ {
/*  99 */   private String indent = "    ";
/*     */ 
/*     */   
/* 102 */   private NamespacePrefixMapper prefixMapper = null;
/*     */ 
/*     */   
/* 105 */   private CharacterEscapeHandler escapeHandler = null;
/*     */ 
/*     */   
/* 108 */   private String header = null;
/*     */ 
/*     */ 
/*     */   
/*     */   final JAXBContextImpl context;
/*     */ 
/*     */   
/*     */   protected final XMLSerializer serializer;
/*     */ 
/*     */   
/*     */   private Schema schema;
/*     */ 
/*     */   
/* 121 */   private Marshaller.Listener externalListener = null;
/*     */   
/*     */   private boolean c14nSupport;
/*     */   private Flushable toBeFlushed;
/*     */   private Closeable toBeClosed;
/*     */   protected static final String INDENT_STRING = "com.sun.xml.internal.bind.indentString";
/*     */   protected static final String PREFIX_MAPPER = "com.sun.xml.internal.bind.namespacePrefixMapper";
/*     */   protected static final String ENCODING_HANDLER = "com.sun.xml.internal.bind.characterEscapeHandler";
/*     */   protected static final String ENCODING_HANDLER2 = "com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler";
/*     */   protected static final String XMLDECLARATION = "com.sun.xml.internal.bind.xmlDeclaration";
/*     */   protected static final String XML_HEADERS = "com.sun.xml.internal.bind.xmlHeaders";
/*     */   protected static final String C14N = "com.sun.xml.internal.bind.c14n";
/*     */   protected static final String OBJECT_IDENTITY_CYCLE_DETECTION = "com.sun.xml.internal.bind.objectIdentitityCycleDetection";
/*     */   
/*     */   public MarshallerImpl(JAXBContextImpl c, AssociationMap assoc) {
/* 136 */     this.context = c;
/* 137 */     this.serializer = new XMLSerializer(this);
/* 138 */     this.c14nSupport = this.context.c14nSupport;
/*     */     
/*     */     try {
/* 141 */       setEventHandler(this);
/* 142 */     } catch (JAXBException e) {
/* 143 */       throw new AssertionError(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public JAXBContextImpl getContext() {
/* 148 */     return this.context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(Object obj, OutputStream out, NamespaceContext inscopeNamespace) throws JAXBException {
/* 158 */     write(obj, createWriter(out), new StAXPostInitAction(inscopeNamespace, this.serializer));
/*     */   }
/*     */ 
/*     */   
/*     */   public void marshal(Object obj, XMLStreamWriter writer) throws JAXBException {
/* 163 */     write(obj, XMLStreamWriterOutput.create(writer, this.context, this.escapeHandler), new StAXPostInitAction(writer, this.serializer));
/*     */   }
/*     */ 
/*     */   
/*     */   public void marshal(Object obj, XMLEventWriter writer) throws JAXBException {
/* 168 */     write(obj, (XmlOutput)new XMLEventWriterOutput(writer), new StAXPostInitAction(writer, this.serializer));
/*     */   }
/*     */   
/*     */   public void marshal(Object obj, XmlOutput output) throws JAXBException {
/* 172 */     write(obj, output, (Runnable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final XmlOutput createXmlOutput(Result result) throws JAXBException {
/* 179 */     if (result instanceof SAXResult) {
/* 180 */       return (XmlOutput)new SAXOutput(((SAXResult)result).getHandler());
/*     */     }
/* 182 */     if (result instanceof DOMResult) {
/* 183 */       Node node = ((DOMResult)result).getNode();
/*     */       
/* 185 */       if (node == null) {
/* 186 */         Document doc = JAXBContextImpl.createDom((getContext()).disableSecurityProcessing);
/* 187 */         ((DOMResult)result).setNode(doc);
/* 188 */         return (XmlOutput)new SAXOutput((ContentHandler)new SAX2DOMEx(doc));
/*     */       } 
/* 190 */       return (XmlOutput)new SAXOutput((ContentHandler)new SAX2DOMEx(node));
/*     */     } 
/*     */     
/* 193 */     if (result instanceof StreamResult) {
/* 194 */       StreamResult sr = (StreamResult)result;
/*     */       
/* 196 */       if (sr.getWriter() != null)
/* 197 */         return createWriter(sr.getWriter()); 
/* 198 */       if (sr.getOutputStream() != null)
/* 199 */         return createWriter(sr.getOutputStream()); 
/* 200 */       if (sr.getSystemId() != null) {
/* 201 */         String fileURL = sr.getSystemId();
/*     */         
/*     */         try {
/* 204 */           fileURL = (new URI(fileURL)).getPath();
/* 205 */         } catch (URISyntaxException uRISyntaxException) {}
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 210 */           FileOutputStream fos = new FileOutputStream(fileURL);
/* 211 */           assert this.toBeClosed == null;
/* 212 */           this.toBeClosed = fos;
/* 213 */           return createWriter(fos);
/* 214 */         } catch (IOException e) {
/* 215 */           throw new MarshalException(e);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 221 */     throw new MarshalException(Messages.UNSUPPORTED_RESULT.format(new Object[0]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final Runnable createPostInitAction(Result result) {
/* 228 */     if (result instanceof DOMResult) {
/* 229 */       Node node = ((DOMResult)result).getNode();
/* 230 */       return new DomPostInitAction(node, this.serializer);
/*     */     } 
/* 232 */     return null;
/*     */   }
/*     */   
/*     */   public void marshal(Object target, Result result) throws JAXBException {
/* 236 */     write(target, createXmlOutput(result), createPostInitAction(result));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final <T> void write(Name rootTagName, JaxBeanInfo<T> bi, T obj, XmlOutput out, Runnable postInitAction) throws JAXBException {
/*     */     try {
/*     */       try {
/* 246 */         prewrite(out, true, postInitAction);
/* 247 */         this.serializer.startElement(rootTagName, (Object)null);
/* 248 */         if (bi.jaxbType == Void.class || bi.jaxbType == void.class) {
/*     */           
/* 250 */           this.serializer.endNamespaceDecls((Object)null);
/* 251 */           this.serializer.endAttributes();
/*     */         }
/* 253 */         else if (obj == null) {
/* 254 */           this.serializer.writeXsiNilTrue();
/*     */         } else {
/* 256 */           this.serializer.childAsXsiType(obj, "root", bi, false);
/*     */         } 
/* 258 */         this.serializer.endElement();
/* 259 */         postwrite();
/* 260 */       } catch (SAXException e) {
/* 261 */         throw new MarshalException(e);
/* 262 */       } catch (IOException e) {
/* 263 */         throw new MarshalException(e);
/* 264 */       } catch (XMLStreamException e) {
/* 265 */         throw new MarshalException(e);
/*     */       } finally {
/* 267 */         this.serializer.close();
/*     */       } 
/*     */     } finally {
/* 270 */       cleanUp();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void write(Object obj, XmlOutput out, Runnable postInitAction) throws JAXBException {
/*     */     try {
/*     */       ForkXmlOutput forkXmlOutput;
/* 279 */       if (obj == null) {
/* 280 */         throw new IllegalArgumentException(Messages.NOT_MARSHALLABLE.format(new Object[0]));
/*     */       }
/* 282 */       if (this.schema != null) {
/*     */         
/* 284 */         ValidatorHandler validator = this.schema.newValidatorHandler();
/* 285 */         validator.setErrorHandler((ErrorHandler)new FatalAdapter(this.serializer));
/*     */         
/* 287 */         XMLFilterImpl f = new XMLFilterImpl()
/*     */           {
/*     */             public void startPrefixMapping(String prefix, String uri) throws SAXException {
/* 290 */               super.startPrefixMapping(prefix.intern(), uri.intern());
/*     */             }
/*     */           };
/* 293 */         f.setContentHandler(validator);
/* 294 */         forkXmlOutput = new ForkXmlOutput((XmlOutput)new SAXOutput(f)
/*     */             {
/*     */               public void startDocument(XMLSerializer serializer, boolean fragment, int[] nsUriIndex2prefixIndex, NamespaceContextImpl nsContext) throws SAXException, IOException, XMLStreamException {
/* 297 */                 super.startDocument(serializer, false, nsUriIndex2prefixIndex, nsContext);
/*     */               }
/*     */               
/*     */               public void endDocument(boolean fragment) throws SAXException, IOException, XMLStreamException {
/* 301 */                 super.endDocument(false);
/*     */               }
/*     */             }out);
/*     */       } 
/*     */       
/*     */       try {
/* 307 */         prewrite((XmlOutput)forkXmlOutput, isFragment(), postInitAction);
/* 308 */         this.serializer.childAsRoot(obj);
/* 309 */         postwrite();
/* 310 */       } catch (SAXException e) {
/* 311 */         throw new MarshalException(e);
/* 312 */       } catch (IOException e) {
/* 313 */         throw new MarshalException(e);
/* 314 */       } catch (XMLStreamException e) {
/* 315 */         throw new MarshalException(e);
/*     */       } finally {
/* 317 */         this.serializer.close();
/*     */       } 
/*     */     } finally {
/* 320 */       cleanUp();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void cleanUp() {
/* 325 */     if (this.toBeFlushed != null) {
/*     */       try {
/* 327 */         this.toBeFlushed.flush();
/* 328 */       } catch (IOException iOException) {}
/*     */     }
/*     */     
/* 331 */     if (this.toBeClosed != null) {
/*     */       try {
/* 333 */         this.toBeClosed.close();
/* 334 */       } catch (IOException iOException) {}
/*     */     }
/*     */     
/* 337 */     this.toBeFlushed = null;
/* 338 */     this.toBeClosed = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void prewrite(XmlOutput out, boolean fragment, Runnable postInitAction) throws IOException, SAXException, XMLStreamException {
/* 344 */     this.serializer.startDocument(out, fragment, getSchemaLocation(), getNoNSSchemaLocation());
/* 345 */     if (postInitAction != null) postInitAction.run(); 
/* 346 */     if (this.prefixMapper != null) {
/*     */       
/* 348 */       String[] decls = this.prefixMapper.getContextualNamespaceDecls();
/* 349 */       if (decls != null)
/* 350 */         for (int i = 0; i < decls.length; i += 2) {
/* 351 */           String prefix = decls[i];
/* 352 */           String nsUri = decls[i + 1];
/* 353 */           if (nsUri != null && prefix != null) {
/* 354 */             this.serializer.addInscopeBinding(nsUri, prefix);
/*     */           }
/*     */         }  
/*     */     } 
/* 358 */     this.serializer.setPrefixMapper(this.prefixMapper);
/*     */   }
/*     */   
/*     */   private void postwrite() throws IOException, SAXException, XMLStreamException {
/* 362 */     this.serializer.endDocument();
/* 363 */     this.serializer.reconcileID();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CharacterEscapeHandler getEscapeHandler() {
/* 373 */     return this.escapeHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CharacterEscapeHandler createEscapeHandler(String encoding) {
/* 383 */     if (this.escapeHandler != null)
/*     */     {
/* 385 */       return this.escapeHandler;
/*     */     }
/* 387 */     if (encoding.startsWith("UTF"))
/*     */     {
/*     */       
/* 390 */       return MinimumEscapeHandler.theInstance;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 395 */       return (CharacterEscapeHandler)new NioEscapeHandler(getJavaEncoding(encoding));
/* 396 */     } catch (Throwable e) {
/*     */       
/* 398 */       return DumbEscapeHandler.theInstance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public XmlOutput createWriter(Writer w, String encoding) {
/*     */     XMLWriter xw;
/* 404 */     if (!(w instanceof BufferedWriter)) {
/* 405 */       w = new BufferedWriter(w);
/*     */     }
/* 407 */     assert this.toBeFlushed == null;
/* 408 */     this.toBeFlushed = w;
/*     */     
/* 410 */     CharacterEscapeHandler ceh = createEscapeHandler(encoding);
/*     */ 
/*     */     
/* 413 */     if (isFormattedOutput()) {
/* 414 */       DataWriter d = new DataWriter(w, encoding, ceh);
/* 415 */       d.setIndentStep(this.indent);
/* 416 */       DataWriter dataWriter1 = d;
/*     */     } else {
/* 418 */       xw = new XMLWriter(w, encoding, ceh);
/*     */     } 
/* 420 */     xw.setXmlDecl(!isFragment());
/* 421 */     xw.setHeader(this.header);
/* 422 */     return (XmlOutput)new SAXOutput((ContentHandler)xw);
/*     */   }
/*     */   
/*     */   public XmlOutput createWriter(Writer w) {
/* 426 */     return createWriter(w, getEncoding());
/*     */   }
/*     */   
/*     */   public XmlOutput createWriter(OutputStream os) throws JAXBException {
/* 430 */     return createWriter(os, getEncoding());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XmlOutput createWriter(OutputStream os, String encoding) throws JAXBException {
/* 438 */     if (encoding.equals("UTF-8")) {
/* 439 */       UTF8XmlOutput out; Encoded[] table = this.context.getUTF8NameTable();
/*     */       
/* 441 */       if (isFormattedOutput()) {
/* 442 */         IndentingUTF8XmlOutput indentingUTF8XmlOutput = new IndentingUTF8XmlOutput(os, this.indent, table, this.escapeHandler);
/*     */       }
/* 444 */       else if (this.c14nSupport) {
/* 445 */         C14nXmlOutput c14nXmlOutput = new C14nXmlOutput(os, table, this.context.c14nSupport, this.escapeHandler);
/*     */       } else {
/* 447 */         out = new UTF8XmlOutput(os, table, this.escapeHandler);
/*     */       } 
/* 449 */       if (this.header != null)
/* 450 */         out.setHeader(this.header); 
/* 451 */       return (XmlOutput)out;
/*     */     } 
/*     */     
/*     */     try {
/* 455 */       return createWriter(new OutputStreamWriter(os, 
/* 456 */             getJavaEncoding(encoding)), encoding);
/*     */     }
/* 458 */     catch (UnsupportedEncodingException e) {
/* 459 */       throw new MarshalException(Messages.UNSUPPORTED_ENCODING
/* 460 */           .format(new Object[] { encoding }, ), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getProperty(String name) throws PropertyException {
/* 468 */     if ("com.sun.xml.internal.bind.indentString".equals(name))
/* 469 */       return this.indent; 
/* 470 */     if ("com.sun.xml.internal.bind.characterEscapeHandler".equals(name) || "com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler".equals(name))
/* 471 */       return this.escapeHandler; 
/* 472 */     if ("com.sun.xml.internal.bind.namespacePrefixMapper".equals(name))
/* 473 */       return this.prefixMapper; 
/* 474 */     if ("com.sun.xml.internal.bind.xmlDeclaration".equals(name))
/* 475 */       return Boolean.valueOf(!isFragment()); 
/* 476 */     if ("com.sun.xml.internal.bind.xmlHeaders".equals(name))
/* 477 */       return this.header; 
/* 478 */     if ("com.sun.xml.internal.bind.c14n".equals(name))
/* 479 */       return Boolean.valueOf(this.c14nSupport); 
/* 480 */     if ("com.sun.xml.internal.bind.objectIdentitityCycleDetection".equals(name)) {
/* 481 */       return Boolean.valueOf(this.serializer.getObjectIdentityCycleDetection());
/*     */     }
/* 483 */     return super.getProperty(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setProperty(String name, Object value) throws PropertyException {
/* 488 */     if ("com.sun.xml.internal.bind.indentString".equals(name)) {
/* 489 */       checkString(name, value);
/* 490 */       this.indent = (String)value;
/*     */       return;
/*     */     } 
/* 493 */     if ("com.sun.xml.internal.bind.characterEscapeHandler".equals(name) || "com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler".equals(name)) {
/* 494 */       if (!(value instanceof CharacterEscapeHandler))
/* 495 */         throw new PropertyException(Messages.MUST_BE_X
/* 496 */             .format(new Object[] {
/*     */                 
/* 498 */                 name, CharacterEscapeHandler.class.getName(), value
/* 499 */                 .getClass().getName() })); 
/* 500 */       this.escapeHandler = (CharacterEscapeHandler)value;
/*     */       return;
/*     */     } 
/* 503 */     if ("com.sun.xml.internal.bind.namespacePrefixMapper".equals(name)) {
/* 504 */       if (!(value instanceof NamespacePrefixMapper))
/* 505 */         throw new PropertyException(Messages.MUST_BE_X
/* 506 */             .format(new Object[] {
/*     */                 
/* 508 */                 name, NamespacePrefixMapper.class.getName(), value
/* 509 */                 .getClass().getName() })); 
/* 510 */       this.prefixMapper = (NamespacePrefixMapper)value;
/*     */       return;
/*     */     } 
/* 513 */     if ("com.sun.xml.internal.bind.xmlDeclaration".equals(name)) {
/* 514 */       checkBoolean(name, value);
/*     */ 
/*     */       
/* 517 */       super.setProperty("jaxb.fragment", Boolean.valueOf(!((Boolean)value).booleanValue()));
/*     */       return;
/*     */     } 
/* 520 */     if ("com.sun.xml.internal.bind.xmlHeaders".equals(name)) {
/* 521 */       checkString(name, value);
/* 522 */       this.header = (String)value;
/*     */       return;
/*     */     } 
/* 525 */     if ("com.sun.xml.internal.bind.c14n".equals(name)) {
/* 526 */       checkBoolean(name, value);
/* 527 */       this.c14nSupport = ((Boolean)value).booleanValue();
/*     */       return;
/*     */     } 
/* 530 */     if ("com.sun.xml.internal.bind.objectIdentitityCycleDetection".equals(name)) {
/* 531 */       checkBoolean(name, value);
/* 532 */       this.serializer.setObjectIdentityCycleDetection(((Boolean)value).booleanValue());
/*     */       
/*     */       return;
/*     */     } 
/* 536 */     super.setProperty(name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkBoolean(String name, Object value) throws PropertyException {
/* 543 */     if (!(value instanceof Boolean)) {
/* 544 */       throw new PropertyException(Messages.MUST_BE_X
/* 545 */           .format(new Object[] {
/*     */               
/* 547 */               name, Boolean.class.getName(), value
/* 548 */               .getClass().getName()
/*     */             }));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkString(String name, Object value) throws PropertyException {
/* 555 */     if (!(value instanceof String))
/* 556 */       throw new PropertyException(Messages.MUST_BE_X
/* 557 */           .format(new Object[] {
/*     */               
/* 559 */               name, String.class.getName(), value
/* 560 */               .getClass().getName()
/*     */             })); 
/*     */   }
/*     */   
/*     */   public <A extends XmlAdapter> void setAdapter(Class<A> type, A adapter) {
/* 565 */     if (type == null)
/* 566 */       throw new IllegalArgumentException(); 
/* 567 */     this.serializer.putAdapter(type, (XmlAdapter)adapter);
/*     */   }
/*     */ 
/*     */   
/*     */   public <A extends XmlAdapter> A getAdapter(Class<A> type) {
/* 572 */     if (type == null)
/* 573 */       throw new IllegalArgumentException(); 
/* 574 */     if (this.serializer.containsAdapter(type))
/*     */     {
/* 576 */       return this.serializer.getAdapter(type);
/*     */     }
/* 578 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAttachmentMarshaller(AttachmentMarshaller am) {
/* 583 */     this.serializer.attachmentMarshaller = am;
/*     */   }
/*     */ 
/*     */   
/*     */   public AttachmentMarshaller getAttachmentMarshaller() {
/* 588 */     return this.serializer.attachmentMarshaller;
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema getSchema() {
/* 593 */     return this.schema;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSchema(Schema s) {
/* 598 */     this.schema = s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleEvent(ValidationEvent event) {
/* 606 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Marshaller.Listener getListener() {
/* 611 */     return this.externalListener;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setListener(Marshaller.Listener listener) {
/* 616 */     this.externalListener = listener;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/MarshallerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */