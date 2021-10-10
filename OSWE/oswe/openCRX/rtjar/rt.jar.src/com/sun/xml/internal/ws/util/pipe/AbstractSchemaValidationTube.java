/*     */ package com.sun.xml.internal.ws.util.pipe;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.internal.stream.buffer.XMLStreamBufferResult;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*     */ import com.sun.xml.internal.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.internal.ws.api.pipe.helper.AbstractFilterTubeImpl;
/*     */ import com.sun.xml.internal.ws.api.server.DocumentAddressResolver;
/*     */ import com.sun.xml.internal.ws.api.server.SDDocument;
/*     */ import com.sun.xml.internal.ws.api.server.SDDocumentSource;
/*     */ import com.sun.xml.internal.ws.developer.SchemaValidationFeature;
/*     */ import com.sun.xml.internal.ws.developer.ValidationErrorHandler;
/*     */ import com.sun.xml.internal.ws.server.SDDocumentImpl;
/*     */ import com.sun.xml.internal.ws.util.ByteArrayBuffer;
/*     */ import com.sun.xml.internal.ws.util.xml.XmlUtil;
/*     */ import com.sun.xml.internal.ws.wsdl.SDDocumentResolver;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import javax.xml.validation.SchemaFactory;
/*     */ import javax.xml.validation.Validator;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.ls.LSInput;
/*     */ import org.w3c.dom.ls.LSResourceResolver;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.NamespaceSupport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractSchemaValidationTube
/*     */   extends AbstractFilterTubeImpl
/*     */ {
/*  84 */   private static final Logger LOGGER = Logger.getLogger(AbstractSchemaValidationTube.class.getName());
/*     */   
/*     */   protected final WSBinding binding;
/*     */   protected final SchemaValidationFeature feature;
/*  88 */   protected final DocumentAddressResolver resolver = new ValidationDocumentAddressResolver();
/*     */   protected final SchemaFactory sf;
/*     */   
/*     */   public AbstractSchemaValidationTube(WSBinding binding, Tube next) {
/*  92 */     super(next);
/*  93 */     this.binding = binding;
/*  94 */     this.feature = (SchemaValidationFeature)binding.getFeature(SchemaValidationFeature.class);
/*  95 */     this.sf = XmlUtil.allowExternalAccess(SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema"), "file", false);
/*     */   }
/*     */   
/*     */   protected AbstractSchemaValidationTube(AbstractSchemaValidationTube that, TubeCloner cloner) {
/*  99 */     super(that, cloner);
/* 100 */     this.binding = that.binding;
/* 101 */     this.feature = that.feature;
/* 102 */     this.sf = that.sf;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ValidationDocumentAddressResolver
/*     */     implements DocumentAddressResolver
/*     */   {
/*     */     private ValidationDocumentAddressResolver() {}
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public String getRelativeAddressFor(@NotNull SDDocument current, @NotNull SDDocument referenced) {
/* 114 */       AbstractSchemaValidationTube.LOGGER.log(Level.FINE, "Current = {0} resolved relative={1}", new Object[] { current.getURL(), referenced.getURL() });
/* 115 */       return referenced.getURL().toExternalForm();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private Document createDOM(SDDocument doc) {
/* 121 */     ByteArrayBuffer bab = new ByteArrayBuffer();
/*     */     try {
/* 123 */       doc.writeTo(null, this.resolver, (OutputStream)bab);
/* 124 */     } catch (IOException ioe) {
/* 125 */       throw new WebServiceException(ioe);
/*     */     } 
/*     */ 
/*     */     
/* 129 */     Transformer trans = XmlUtil.newTransformer();
/* 130 */     Source source = new StreamSource(bab.newInputStream(), null);
/* 131 */     DOMResult result = new DOMResult();
/*     */     try {
/* 133 */       trans.transform(source, result);
/* 134 */     } catch (TransformerException te) {
/* 135 */       throw new WebServiceException(te);
/*     */     } 
/* 137 */     return (Document)result.getNode();
/*     */   }
/*     */   
/*     */   protected class MetadataResolverImpl
/*     */     implements SDDocumentResolver, LSResourceResolver
/*     */   {
/* 143 */     final Map<String, SDDocument> docs = new HashMap<>();
/*     */ 
/*     */     
/* 146 */     final Map<String, SDDocument> nsMapping = new HashMap<>();
/*     */ 
/*     */     
/*     */     public MetadataResolverImpl() {}
/*     */     
/*     */     public MetadataResolverImpl(Iterable<SDDocument> it) {
/* 152 */       for (SDDocument doc : it) {
/* 153 */         if (doc.isSchema()) {
/* 154 */           this.docs.put(doc.getURL().toExternalForm(), doc);
/* 155 */           this.nsMapping.put(((SDDocument.Schema)doc).getTargetNamespace(), doc);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     void addSchema(Source schema) {
/* 161 */       assert schema.getSystemId() != null;
/*     */       
/* 163 */       String systemId = schema.getSystemId();
/*     */       try {
/* 165 */         XMLStreamBufferResult xsbr = (XMLStreamBufferResult)XmlUtil.identityTransform(schema, (Result)new XMLStreamBufferResult());
/* 166 */         SDDocumentSource sds = SDDocumentSource.create(new URL(systemId), (XMLStreamBuffer)xsbr.getXMLStreamBuffer());
/* 167 */         SDDocumentImpl sDDocumentImpl = SDDocumentImpl.create(sds, new QName(""), new QName(""));
/* 168 */         this.docs.put(systemId, sDDocumentImpl);
/* 169 */         this.nsMapping.put(((SDDocument.Schema)sDDocumentImpl).getTargetNamespace(), sDDocumentImpl);
/* 170 */       } catch (Exception ex) {
/* 171 */         AbstractSchemaValidationTube.LOGGER.log(Level.WARNING, "Exception in adding schemas to resolver", ex);
/*     */       } 
/*     */     }
/*     */     
/*     */     void addSchemas(Collection<? extends Source> schemas) {
/* 176 */       for (Source src : schemas) {
/* 177 */         addSchema(src);
/*     */       }
/*     */     }
/*     */     
/*     */     public SDDocument resolve(String systemId) {
/*     */       SDDocumentImpl sDDocumentImpl;
/* 183 */       SDDocument sdi = this.docs.get(systemId);
/* 184 */       if (sdi == null) {
/*     */         SDDocumentSource sds;
/*     */         try {
/* 187 */           sds = SDDocumentSource.create(new URL(systemId));
/* 188 */         } catch (MalformedURLException e) {
/* 189 */           throw new WebServiceException(e);
/*     */         } 
/* 191 */         sDDocumentImpl = SDDocumentImpl.create(sds, new QName(""), new QName(""));
/* 192 */         this.docs.put(systemId, sDDocumentImpl);
/*     */       } 
/* 194 */       return (SDDocument)sDDocumentImpl;
/*     */     }
/*     */ 
/*     */     
/*     */     public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {
/* 199 */       if (AbstractSchemaValidationTube.LOGGER.isLoggable(Level.FINE)) {
/* 200 */         AbstractSchemaValidationTube.LOGGER.log(Level.FINE, "type={0} namespaceURI={1} publicId={2} systemId={3} baseURI={4}", new Object[] { type, namespaceURI, publicId, systemId, baseURI });
/*     */       }
/*     */       try {
/*     */         final SDDocument doc;
/* 204 */         if (systemId == null) {
/* 205 */           doc = this.nsMapping.get(namespaceURI);
/*     */         } else {
/*     */           
/* 208 */           URI rel = (baseURI != null) ? (new URI(baseURI)).resolve(systemId) : new URI(systemId);
/*     */           
/* 210 */           doc = this.docs.get(rel.toString());
/*     */         } 
/* 212 */         if (doc != null) {
/* 213 */           return new LSInput()
/*     */             {
/*     */               public Reader getCharacterStream()
/*     */               {
/* 217 */                 return null;
/*     */               }
/*     */ 
/*     */               
/*     */               public void setCharacterStream(Reader characterStream) {
/* 222 */                 throw new UnsupportedOperationException();
/*     */               }
/*     */ 
/*     */               
/*     */               public InputStream getByteStream() {
/* 227 */                 ByteArrayBuffer bab = new ByteArrayBuffer();
/*     */                 try {
/* 229 */                   doc.writeTo(null, AbstractSchemaValidationTube.this.resolver, (OutputStream)bab);
/* 230 */                 } catch (IOException ioe) {
/* 231 */                   throw new WebServiceException(ioe);
/*     */                 } 
/* 233 */                 return bab.newInputStream();
/*     */               }
/*     */ 
/*     */               
/*     */               public void setByteStream(InputStream byteStream) {
/* 238 */                 throw new UnsupportedOperationException();
/*     */               }
/*     */ 
/*     */               
/*     */               public String getStringData() {
/* 243 */                 return null;
/*     */               }
/*     */ 
/*     */               
/*     */               public void setStringData(String stringData) {
/* 248 */                 throw new UnsupportedOperationException();
/*     */               }
/*     */ 
/*     */               
/*     */               public String getSystemId() {
/* 253 */                 return doc.getURL().toExternalForm();
/*     */               }
/*     */ 
/*     */               
/*     */               public void setSystemId(String systemId) {
/* 258 */                 throw new UnsupportedOperationException();
/*     */               }
/*     */ 
/*     */               
/*     */               public String getPublicId() {
/* 263 */                 return null;
/*     */               }
/*     */ 
/*     */               
/*     */               public void setPublicId(String publicId) {
/* 268 */                 throw new UnsupportedOperationException();
/*     */               }
/*     */ 
/*     */               
/*     */               public String getBaseURI() {
/* 273 */                 return doc.getURL().toExternalForm();
/*     */               }
/*     */ 
/*     */               
/*     */               public void setBaseURI(String baseURI) {
/* 278 */                 throw new UnsupportedOperationException();
/*     */               }
/*     */ 
/*     */               
/*     */               public String getEncoding() {
/* 283 */                 return null;
/*     */               }
/*     */ 
/*     */               
/*     */               public void setEncoding(String encoding) {
/* 288 */                 throw new UnsupportedOperationException();
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean getCertifiedText() {
/* 293 */                 return false;
/*     */               }
/*     */ 
/*     */               
/*     */               public void setCertifiedText(boolean certifiedText) {
/* 298 */                 throw new UnsupportedOperationException();
/*     */               }
/*     */             };
/*     */         }
/* 302 */       } catch (Exception e) {
/* 303 */         AbstractSchemaValidationTube.LOGGER.log(Level.WARNING, "Exception in LSResourceResolver impl", e);
/*     */       } 
/* 305 */       if (AbstractSchemaValidationTube.LOGGER.isLoggable(Level.FINE)) {
/* 306 */         AbstractSchemaValidationTube.LOGGER.log(Level.FINE, "Don''t know about systemId={0} baseURI={1}", new Object[] { systemId, baseURI });
/*     */       }
/* 308 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateMultiSchemaForTns(String tns, String systemId, Map<String, List<String>> schemas) {
/* 314 */     List<String> docIdList = schemas.get(tns);
/* 315 */     if (docIdList == null) {
/* 316 */       docIdList = new ArrayList<>();
/* 317 */       schemas.put(tns, docIdList);
/*     */     } 
/* 319 */     docIdList.add(systemId);
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
/*     */   protected Source[] getSchemaSources(Iterable<SDDocument> docs, MetadataResolverImpl mdresolver) {
/* 338 */     Map<String, DOMSource> inlinedSchemas = new HashMap<>();
/*     */ 
/*     */ 
/*     */     
/* 342 */     Map<String, List<String>> multiSchemaForTns = new HashMap<>();
/*     */     
/* 344 */     for (SDDocument sdoc : docs) {
/* 345 */       if (sdoc.isWSDL()) {
/* 346 */         Document dom = createDOM(sdoc);
/*     */         
/* 348 */         addSchemaFragmentSource(dom, sdoc.getURL().toExternalForm(), inlinedSchemas); continue;
/* 349 */       }  if (sdoc.isSchema()) {
/* 350 */         updateMultiSchemaForTns(((SDDocument.Schema)sdoc).getTargetNamespace(), sdoc.getURL().toExternalForm(), multiSchemaForTns);
/*     */       }
/*     */     } 
/* 353 */     if (LOGGER.isLoggable(Level.FINE)) {
/* 354 */       LOGGER.log(Level.FINE, "WSDL inlined schema fragment documents(these are used to create a pseudo schema) = {0}", inlinedSchemas.keySet());
/*     */     }
/* 356 */     for (DOMSource src : inlinedSchemas.values()) {
/* 357 */       String tns = getTargetNamespace(src);
/* 358 */       updateMultiSchemaForTns(tns, src.getSystemId(), multiSchemaForTns);
/*     */     } 
/*     */     
/* 361 */     if (multiSchemaForTns.isEmpty())
/* 362 */       return new Source[0]; 
/* 363 */     if (multiSchemaForTns.size() == 1 && ((List)multiSchemaForTns.values().iterator().next()).size() == 1) {
/*     */       
/* 365 */       String systemId = ((List<String>)multiSchemaForTns.values().iterator().next()).get(0);
/* 366 */       return new Source[] { inlinedSchemas.get(systemId) };
/*     */     } 
/*     */ 
/*     */     
/* 370 */     mdresolver.addSchemas(inlinedSchemas.values());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 375 */     Map<String, String> oneSchemaForTns = new HashMap<>();
/* 376 */     int i = 0;
/* 377 */     for (Map.Entry<String, List<String>> e : multiSchemaForTns.entrySet()) {
/*     */       String systemId;
/* 379 */       List<String> sameTnsSchemas = e.getValue();
/* 380 */       if (sameTnsSchemas.size() > 1) {
/*     */ 
/*     */         
/* 383 */         systemId = "file:x-jax-ws-include-" + i++;
/* 384 */         Source src = createSameTnsPseudoSchema(e.getKey(), sameTnsSchemas, systemId);
/* 385 */         mdresolver.addSchema(src);
/*     */       } else {
/* 387 */         systemId = sameTnsSchemas.get(0);
/*     */       } 
/* 389 */       oneSchemaForTns.put(e.getKey(), systemId);
/*     */     } 
/*     */ 
/*     */     
/* 393 */     Source pseudoSchema = createMasterPseudoSchema(oneSchemaForTns);
/* 394 */     return new Source[] { pseudoSchema };
/*     */   }
/*     */   @Nullable
/*     */   private void addSchemaFragmentSource(Document doc, String systemId, Map<String, DOMSource> map) {
/* 398 */     Element e = doc.getDocumentElement();
/* 399 */     assert e.getNamespaceURI().equals("http://schemas.xmlsoap.org/wsdl/");
/* 400 */     assert e.getLocalName().equals("definitions");
/*     */     
/* 402 */     NodeList typesList = e.getElementsByTagNameNS("http://schemas.xmlsoap.org/wsdl/", "types");
/* 403 */     for (int i = 0; i < typesList.getLength(); i++) {
/* 404 */       NodeList schemaList = ((Element)typesList.item(i)).getElementsByTagNameNS("http://www.w3.org/2001/XMLSchema", "schema");
/* 405 */       for (int j = 0; j < schemaList.getLength(); j++) {
/* 406 */         Element elem = (Element)schemaList.item(j);
/* 407 */         NamespaceSupport nss = new NamespaceSupport();
/*     */ 
/*     */         
/* 410 */         buildNamespaceSupport(nss, elem);
/* 411 */         patchDOMFragment(nss, elem);
/* 412 */         String docId = systemId + "#schema" + j;
/* 413 */         map.put(docId, new DOMSource(elem, docId));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildNamespaceSupport(NamespaceSupport nss, Node node) {
/* 423 */     if (node == null || node.getNodeType() != 1) {
/*     */       return;
/*     */     }
/*     */     
/* 427 */     buildNamespaceSupport(nss, node.getParentNode());
/*     */     
/* 429 */     nss.pushContext();
/* 430 */     NamedNodeMap atts = node.getAttributes();
/* 431 */     for (int i = 0; i < atts.getLength(); i++) {
/* 432 */       Attr a = (Attr)atts.item(i);
/* 433 */       if ("xmlns".equals(a.getPrefix())) {
/* 434 */         nss.declarePrefix(a.getLocalName(), a.getValue());
/*     */       
/*     */       }
/* 437 */       else if ("xmlns".equals(a.getName())) {
/* 438 */         nss.declarePrefix("", a.getValue());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private void patchDOMFragment(NamespaceSupport nss, Element elem) {
/* 451 */     NamedNodeMap atts = elem.getAttributes();
/* 452 */     for (Enumeration<String> en = nss.getPrefixes(); en.hasMoreElements(); ) {
/* 453 */       String prefix = en.nextElement();
/*     */       
/* 455 */       for (int i = 0; i < atts.getLength(); i++) {
/* 456 */         Attr a = (Attr)atts.item(i);
/* 457 */         if (!"xmlns".equals(a.getPrefix()) || !a.getLocalName().equals(prefix)) {
/* 458 */           if (LOGGER.isLoggable(Level.FINE)) {
/* 459 */             LOGGER.log(Level.FINE, "Patching with xmlns:{0}={1}", new Object[] { prefix, nss.getURI(prefix) });
/*     */           }
/* 461 */           elem.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + prefix, nss.getURI(prefix));
/*     */         } 
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Source createSameTnsPseudoSchema(String tns, Collection<String> docs, String pseudoSystemId) {
/* 483 */     assert docs.size() > 1;
/*     */     
/* 485 */     final StringBuilder sb = new StringBuilder("<xsd:schema xmlns:xsd='http://www.w3.org/2001/XMLSchema'");
/* 486 */     if (!tns.equals("")) {
/* 487 */       sb.append(" targetNamespace='").append(tns).append("'");
/*     */     }
/* 489 */     sb.append(">\n");
/* 490 */     for (String systemId : docs) {
/* 491 */       sb.append("<xsd:include schemaLocation='").append(systemId).append("'/>\n");
/*     */     }
/* 493 */     sb.append("</xsd:schema>\n");
/* 494 */     if (LOGGER.isLoggable(Level.FINE)) {
/* 495 */       LOGGER.log(Level.FINE, "Pseudo Schema for the same tns={0}is {1}", new Object[] { tns, sb });
/*     */     }
/*     */ 
/*     */     
/* 499 */     return new StreamSource(pseudoSystemId)
/*     */       {
/*     */         public Reader getReader() {
/* 502 */           return new StringReader(sb.toString());
/*     */         }
/*     */       };
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
/*     */   private Source createMasterPseudoSchema(Map<String, String> docs) {
/* 519 */     final StringBuilder sb = new StringBuilder("<xsd:schema xmlns:xsd='http://www.w3.org/2001/XMLSchema' targetNamespace='urn:x-jax-ws-master'>\n");
/* 520 */     for (Map.Entry<String, String> e : docs.entrySet()) {
/* 521 */       String systemId = e.getValue();
/* 522 */       String ns = e.getKey();
/* 523 */       sb.append("<xsd:import schemaLocation='").append(systemId).append("'");
/* 524 */       if (!ns.equals("")) {
/* 525 */         sb.append(" namespace='").append(ns).append("'");
/*     */       }
/* 527 */       sb.append("/>\n");
/*     */     } 
/* 529 */     sb.append("</xsd:schema>");
/* 530 */     if (LOGGER.isLoggable(Level.FINE)) {
/* 531 */       LOGGER.log(Level.FINE, "Master Pseudo Schema = {0}", sb);
/*     */     }
/*     */ 
/*     */     
/* 535 */     return new StreamSource("file:x-jax-ws-master-doc")
/*     */       {
/*     */         public Reader getReader() {
/* 538 */           return new StringReader(sb.toString());
/*     */         }
/*     */       };
/*     */   }
/*     */   protected void doProcess(Packet packet) throws SAXException {
/*     */     ValidationErrorHandler handler;
/* 544 */     getValidator().reset();
/* 545 */     Class<? extends ValidationErrorHandler> handlerClass = this.feature.getErrorHandler();
/*     */     
/*     */     try {
/* 548 */       handler = handlerClass.newInstance();
/* 549 */     } catch (Exception e) {
/* 550 */       throw new WebServiceException(e);
/*     */     } 
/* 552 */     handler.setPacket(packet);
/* 553 */     getValidator().setErrorHandler((ErrorHandler)handler);
/* 554 */     Message msg = packet.getMessage().copy();
/* 555 */     Source source = msg.readPayloadAsSource();
/*     */ 
/*     */     
/*     */     try {
/* 559 */       getValidator().validate(source);
/* 560 */     } catch (IOException e) {
/* 561 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getTargetNamespace(DOMSource src) {
/* 566 */     Element elem = (Element)src.getNode();
/* 567 */     return elem.getAttribute("targetNamespace");
/*     */   }
/*     */   
/*     */   protected abstract Validator getValidator();
/*     */   
/*     */   protected abstract boolean isNoValidation();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/util/pipe/AbstractSchemaValidationTube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */