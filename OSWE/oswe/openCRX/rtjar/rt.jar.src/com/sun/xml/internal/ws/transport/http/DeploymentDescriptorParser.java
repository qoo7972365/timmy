/*     */ package com.sun.xml.internal.ws.transport.http;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.databinding.DatabindingModeFeature;
/*     */ import com.oracle.webservices.internal.api.databinding.ExternalMetadataFeature;
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.ws.api.BindingID;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.databinding.MetadataReader;
/*     */ import com.sun.xml.internal.ws.api.server.Container;
/*     */ import com.sun.xml.internal.ws.api.server.SDDocumentSource;
/*     */ import com.sun.xml.internal.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
/*     */ import com.sun.xml.internal.ws.binding.WebServiceFeatureList;
/*     */ import com.sun.xml.internal.ws.handler.HandlerChainsModel;
/*     */ import com.sun.xml.internal.ws.resources.ServerMessages;
/*     */ import com.sun.xml.internal.ws.resources.WsservletMessages;
/*     */ import com.sun.xml.internal.ws.server.EndpointFactory;
/*     */ import com.sun.xml.internal.ws.server.ServerRtException;
/*     */ import com.sun.xml.internal.ws.streaming.Attributes;
/*     */ import com.sun.xml.internal.ws.streaming.TidyXMLStreamReader;
/*     */ import com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil;
/*     */ import com.sun.xml.internal.ws.util.HandlerAnnotationInfo;
/*     */ import com.sun.xml.internal.ws.util.exception.LocatableWebServiceException;
/*     */ import com.sun.xml.internal.ws.util.xml.XmlUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.soap.MTOMFeature;
/*     */ import javax.xml.ws.soap.SOAPBinding;
/*     */ import org.xml.sax.EntityResolver;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeploymentDescriptorParser<A>
/*     */ {
/*     */   public static final String NS_RUNTIME = "http://java.sun.com/xml/ns/jax-ws/ri/runtime";
/*     */   public static final String JAXWS_WSDL_DD_DIR = "WEB-INF/wsdl";
/*  98 */   public static final QName QNAME_ENDPOINTS = new QName("http://java.sun.com/xml/ns/jax-ws/ri/runtime", "endpoints");
/*  99 */   public static final QName QNAME_ENDPOINT = new QName("http://java.sun.com/xml/ns/jax-ws/ri/runtime", "endpoint");
/* 100 */   public static final QName QNAME_EXT_METADA = new QName("http://java.sun.com/xml/ns/jax-ws/ri/runtime", "external-metadata");
/*     */   
/*     */   public static final String ATTR_FILE = "file";
/*     */   
/*     */   public static final String ATTR_RESOURCE = "resource";
/*     */   
/*     */   public static final String ATTR_VERSION = "version";
/*     */   public static final String ATTR_NAME = "name";
/*     */   public static final String ATTR_IMPLEMENTATION = "implementation";
/*     */   public static final String ATTR_WSDL = "wsdl";
/*     */   public static final String ATTR_SERVICE = "service";
/*     */   public static final String ATTR_PORT = "port";
/*     */   public static final String ATTR_URL_PATTERN = "url-pattern";
/*     */   public static final String ATTR_ENABLE_MTOM = "enable-mtom";
/*     */   public static final String ATTR_MTOM_THRESHOLD_VALUE = "mtom-threshold-value";
/*     */   public static final String ATTR_BINDING = "binding";
/*     */   public static final String ATTR_DATABINDING = "databinding";
/* 117 */   public static final List<String> ATTRVALUE_SUPPORTED_VERSIONS = Arrays.asList(new String[] { "2.0", "2.1" });
/*     */   
/* 119 */   private static final Logger logger = Logger.getLogger("com.sun.xml.internal.ws.server.http");
/*     */ 
/*     */   
/*     */   private final Container container;
/*     */   
/*     */   private final ClassLoader classLoader;
/*     */   
/*     */   private final ResourceLoader loader;
/*     */   
/*     */   private final AdapterFactory<A> adapterFactory;
/*     */   
/* 130 */   private final Set<String> names = new HashSet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 135 */   private final Map<String, SDDocumentSource> docs = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeploymentDescriptorParser(ClassLoader cl, ResourceLoader loader, Container container, AdapterFactory<A> adapterFactory) throws MalformedURLException {
/* 145 */     this.classLoader = cl;
/* 146 */     this.loader = loader;
/* 147 */     this.container = container;
/* 148 */     this.adapterFactory = adapterFactory;
/*     */     
/* 150 */     collectDocs("/WEB-INF/wsdl/");
/* 151 */     logger.log(Level.FINE, "war metadata={0}", this.docs);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<A> parse(String systemId, InputStream is) {
/*     */     TidyXMLStreamReader tidyXMLStreamReader;
/* 159 */     XMLStreamReader reader = null;
/*     */     
/*     */     try {
/* 162 */       tidyXMLStreamReader = new TidyXMLStreamReader(XMLStreamReaderFactory.create(systemId, is, true), is);
/* 163 */       XMLStreamReaderUtil.nextElementContent((XMLStreamReader)tidyXMLStreamReader);
/* 164 */       return parseAdapters((XMLStreamReader)tidyXMLStreamReader);
/*     */     } finally {
/* 166 */       if (tidyXMLStreamReader != null) {
/*     */         try {
/* 168 */           tidyXMLStreamReader.close();
/* 169 */         } catch (XMLStreamException e) {
/* 170 */           throw new ServerRtException("runtime.parser.xmlReader", new Object[] { e });
/*     */         } 
/*     */       }
/*     */       try {
/* 174 */         is.close();
/* 175 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<A> parse(File f) throws IOException {
/* 186 */     FileInputStream in = new FileInputStream(f);
/*     */     try {
/* 188 */       return parse(f.getPath(), in);
/*     */     } finally {
/* 190 */       in.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void collectDocs(String dirPath) throws MalformedURLException {
/* 198 */     Set<String> paths = this.loader.getResourcePaths(dirPath);
/* 199 */     if (paths != null) {
/* 200 */       for (String path : paths) {
/* 201 */         if (path.endsWith("/")) {
/* 202 */           if (path.endsWith("/CVS/") || path.endsWith("/.svn/")) {
/*     */             continue;
/*     */           }
/* 205 */           collectDocs(path); continue;
/*     */         } 
/* 207 */         URL res = this.loader.getResource(path);
/* 208 */         this.docs.put(res.toString(), SDDocumentSource.create(res));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<A> parseAdapters(XMLStreamReader reader) {
/* 216 */     if (!reader.getName().equals(QNAME_ENDPOINTS)) {
/* 217 */       failWithFullName("runtime.parser.invalidElement", reader);
/*     */     }
/*     */     
/* 220 */     List<A> adapters = new ArrayList<>();
/*     */     
/* 222 */     Attributes attrs = XMLStreamReaderUtil.getAttributes(reader);
/* 223 */     String version = getMandatoryNonEmptyAttribute(reader, attrs, "version");
/* 224 */     if (!ATTRVALUE_SUPPORTED_VERSIONS.contains(version)) {
/* 225 */       failWithLocalName("runtime.parser.invalidVersionNumber", reader, version);
/*     */     }
/*     */     
/* 228 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*     */       
/* 230 */       if (reader.getName().equals(QNAME_ENDPOINT)) {
/* 231 */         attrs = XMLStreamReaderUtil.getAttributes(reader);
/*     */         
/* 233 */         String name = getMandatoryNonEmptyAttribute(reader, attrs, "name");
/* 234 */         if (!this.names.add(name)) {
/* 235 */           logger.warning(
/* 236 */               WsservletMessages.SERVLET_WARNING_DUPLICATE_ENDPOINT_NAME());
/*     */         }
/*     */ 
/*     */         
/* 240 */         String implementationName = getMandatoryNonEmptyAttribute(reader, attrs, "implementation");
/* 241 */         Class<?> implementorClass = getImplementorClass(implementationName, reader);
/*     */         
/* 243 */         MetadataReader metadataReader = null;
/* 244 */         ExternalMetadataFeature externalMetadataFeature = null;
/*     */ 
/*     */         
/* 247 */         XMLStreamReaderUtil.nextElementContent(reader);
/* 248 */         if (reader.getEventType() != 2) {
/* 249 */           externalMetadataFeature = configureExternalMetadataReader(reader);
/* 250 */           if (externalMetadataFeature != null) {
/* 251 */             metadataReader = externalMetadataFeature.getMetadataReader(implementorClass.getClassLoader(), false);
/*     */           }
/*     */         } 
/*     */         
/* 255 */         QName serviceName = getQNameAttribute(attrs, "service");
/* 256 */         if (serviceName == null) {
/* 257 */           serviceName = EndpointFactory.getDefaultServiceName(implementorClass, metadataReader);
/*     */         }
/*     */         
/* 260 */         QName portName = getQNameAttribute(attrs, "port");
/* 261 */         if (portName == null) {
/* 262 */           portName = EndpointFactory.getDefaultPortName(serviceName, implementorClass, metadataReader);
/*     */         }
/*     */ 
/*     */         
/* 266 */         String enable_mtom = getAttribute(attrs, "enable-mtom");
/* 267 */         String mtomThreshold = getAttribute(attrs, "mtom-threshold-value");
/* 268 */         String dbMode = getAttribute(attrs, "databinding");
/* 269 */         String bindingId = getAttribute(attrs, "binding");
/* 270 */         if (bindingId != null)
/*     */         {
/* 272 */           bindingId = getBindingIdForToken(bindingId);
/*     */         }
/* 274 */         WSBinding binding = createBinding(bindingId, implementorClass, enable_mtom, mtomThreshold, dbMode);
/* 275 */         if (externalMetadataFeature != null) {
/* 276 */           binding.getFeatures().mergeFeatures(new WebServiceFeature[] { (WebServiceFeature)externalMetadataFeature }, true);
/*     */         }
/*     */ 
/*     */         
/* 280 */         String urlPattern = getMandatoryNonEmptyAttribute(reader, attrs, "url-pattern");
/*     */ 
/*     */         
/* 283 */         boolean handlersSetInDD = setHandlersAndRoles(binding, reader, serviceName, portName);
/*     */         
/* 285 */         EndpointFactory.verifyImplementorClass(implementorClass, metadataReader);
/* 286 */         SDDocumentSource primaryWSDL = getPrimaryWSDL(reader, attrs, implementorClass, metadataReader);
/*     */         
/* 288 */         WSEndpoint<?> endpoint = WSEndpoint.create(implementorClass, !handlersSetInDD, null, serviceName, portName, this.container, binding, primaryWSDL, this.docs
/*     */ 
/*     */ 
/*     */             
/* 292 */             .values(), createEntityResolver(), false);
/*     */         
/* 294 */         adapters.add(this.adapterFactory.createAdapter(name, urlPattern, endpoint)); continue;
/*     */       } 
/* 296 */       failWithLocalName("runtime.parser.invalidElement", reader);
/*     */     } 
/*     */     
/* 299 */     return adapters;
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
/*     */   private static WSBinding createBinding(String ddBindingId, Class implClass, String mtomEnabled, String mtomThreshold, String dataBindingMode) {
/*     */     WebServiceFeatureList features;
/*     */     BindingID bindingID;
/* 314 */     MTOMFeature mtomfeature = null;
/* 315 */     if (mtomEnabled != null) {
/* 316 */       if (mtomThreshold != null) {
/*     */         
/* 318 */         mtomfeature = new MTOMFeature(Boolean.valueOf(mtomEnabled).booleanValue(), Integer.valueOf(mtomThreshold).intValue());
/*     */       } else {
/* 320 */         mtomfeature = new MTOMFeature(Boolean.valueOf(mtomEnabled).booleanValue());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 325 */     if (ddBindingId != null) {
/* 326 */       bindingID = BindingID.parse(ddBindingId);
/* 327 */       features = bindingID.createBuiltinFeatureList();
/*     */       
/* 329 */       if (checkMtomConflict((MTOMFeature)features.get(MTOMFeature.class), mtomfeature)) {
/* 330 */         throw new ServerRtException(ServerMessages.DD_MTOM_CONFLICT(ddBindingId, mtomEnabled), new Object[0]);
/*     */       }
/*     */     } else {
/* 333 */       bindingID = BindingID.parse(implClass);
/*     */ 
/*     */ 
/*     */       
/* 337 */       features = new WebServiceFeatureList();
/* 338 */       if (mtomfeature != null) {
/* 339 */         features.add((WebServiceFeature)mtomfeature);
/*     */       }
/* 341 */       features.addAll((Iterable)bindingID.createBuiltinFeatureList());
/*     */     } 
/*     */     
/* 344 */     if (dataBindingMode != null) {
/* 345 */       features.add((WebServiceFeature)new DatabindingModeFeature(dataBindingMode));
/*     */     }
/*     */     
/* 348 */     return bindingID.createBinding(features.toArray());
/*     */   }
/*     */   
/*     */   private static boolean checkMtomConflict(MTOMFeature lhs, MTOMFeature rhs) {
/* 352 */     if (lhs == null || rhs == null) {
/* 353 */       return false;
/*     */     }
/* 355 */     return lhs.isEnabled() ^ rhs.isEnabled();
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
/*     */   @NotNull
/*     */   public static String getBindingIdForToken(@NotNull String lexical) {
/* 368 */     if (lexical.equals("##SOAP11_HTTP"))
/* 369 */       return "http://schemas.xmlsoap.org/wsdl/soap/http"; 
/* 370 */     if (lexical.equals("##SOAP11_HTTP_MTOM"))
/* 371 */       return "http://schemas.xmlsoap.org/wsdl/soap/http?mtom=true"; 
/* 372 */     if (lexical.equals("##SOAP12_HTTP"))
/* 373 */       return "http://www.w3.org/2003/05/soap/bindings/HTTP/"; 
/* 374 */     if (lexical.equals("##SOAP12_HTTP_MTOM"))
/* 375 */       return "http://www.w3.org/2003/05/soap/bindings/HTTP/?mtom=true"; 
/* 376 */     if (lexical.equals("##XML_HTTP")) {
/* 377 */       return "http://www.w3.org/2004/08/wsdl/http";
/*     */     }
/* 379 */     return lexical;
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
/*     */   private SDDocumentSource getPrimaryWSDL(XMLStreamReader xsr, Attributes attrs, Class<?> implementorClass, MetadataReader metadataReader) {
/* 400 */     String wsdlFile = getAttribute(attrs, "wsdl");
/* 401 */     if (wsdlFile == null) {
/* 402 */       wsdlFile = EndpointFactory.getWsdlLocation(implementorClass, metadataReader);
/*     */     }
/*     */     
/* 405 */     if (wsdlFile != null) {
/* 406 */       URL wsdl; if (!wsdlFile.startsWith("WEB-INF/wsdl")) {
/* 407 */         logger.log(Level.WARNING, "Ignoring wrong wsdl={0}. It should start with {1}. Going to generate and publish a new WSDL.", new Object[] { wsdlFile, "WEB-INF/wsdl" });
/* 408 */         return null;
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 413 */         wsdl = this.loader.getResource('/' + wsdlFile);
/* 414 */       } catch (MalformedURLException e) {
/* 415 */         throw new LocatableWebServiceException(
/* 416 */             ServerMessages.RUNTIME_PARSER_WSDL_NOT_FOUND(wsdlFile), e, xsr);
/*     */       } 
/* 418 */       if (wsdl == null) {
/* 419 */         throw new LocatableWebServiceException(
/* 420 */             ServerMessages.RUNTIME_PARSER_WSDL_NOT_FOUND(wsdlFile), xsr);
/*     */       }
/* 422 */       SDDocumentSource docInfo = this.docs.get(wsdl.toExternalForm());
/* 423 */       assert docInfo != null;
/* 424 */       return docInfo;
/*     */     } 
/*     */     
/* 427 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EntityResolver createEntityResolver() {
/*     */     try {
/* 435 */       return XmlUtil.createEntityResolver(this.loader.getCatalogFile());
/* 436 */     } catch (MalformedURLException e) {
/* 437 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String getAttribute(Attributes attrs, String name) {
/* 442 */     String value = attrs.getValue(name);
/* 443 */     if (value != null) {
/* 444 */       value = value.trim();
/*     */     }
/* 446 */     return value;
/*     */   }
/*     */   
/*     */   protected QName getQNameAttribute(Attributes attrs, String name) {
/* 450 */     String value = getAttribute(attrs, name);
/* 451 */     if (value == null || value.equals("")) {
/* 452 */       return null;
/*     */     }
/* 454 */     return QName.valueOf(value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getNonEmptyAttribute(XMLStreamReader reader, Attributes attrs, String name) {
/* 459 */     String value = getAttribute(attrs, name);
/* 460 */     if (value != null && value.equals("")) {
/* 461 */       failWithLocalName("runtime.parser.invalidAttributeValue", reader, name);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 466 */     return value;
/*     */   }
/*     */   
/*     */   protected String getMandatoryAttribute(XMLStreamReader reader, Attributes attrs, String name) {
/* 470 */     String value = getAttribute(attrs, name);
/* 471 */     if (value == null) {
/* 472 */       failWithLocalName("runtime.parser.missing.attribute", reader, name);
/*     */     }
/* 474 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getMandatoryNonEmptyAttribute(XMLStreamReader reader, Attributes attributes, String name) {
/* 479 */     String value = getAttribute(attributes, name);
/* 480 */     if (value == null) {
/* 481 */       failWithLocalName("runtime.parser.missing.attribute", reader, name);
/* 482 */     } else if (value.equals("")) {
/* 483 */       failWithLocalName("runtime.parser.invalidAttributeValue", reader, name);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 488 */     return value;
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
/*     */   protected boolean setHandlersAndRoles(WSBinding binding, XMLStreamReader reader, QName serviceName, QName portName) {
/* 500 */     if (reader.getEventType() == 2 || 
/* 501 */       !reader.getName().equals(HandlerChainsModel.QNAME_HANDLER_CHAINS)) {
/* 502 */       return false;
/*     */     }
/*     */     
/* 505 */     HandlerAnnotationInfo handlerInfo = HandlerChainsModel.parseHandlerFile(reader, this.classLoader, serviceName, portName, binding);
/*     */ 
/*     */     
/* 508 */     binding.setHandlerChain(handlerInfo.getHandlers());
/* 509 */     if (binding instanceof SOAPBinding) {
/* 510 */       ((SOAPBinding)binding).setRoles(handlerInfo.getRoles());
/*     */     }
/*     */ 
/*     */     
/* 514 */     XMLStreamReaderUtil.nextContent(reader);
/* 515 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ExternalMetadataFeature configureExternalMetadataReader(XMLStreamReader reader) {
/* 520 */     ExternalMetadataFeature.Builder featureBuilder = null;
/* 521 */     while (QNAME_EXT_METADA.equals(reader.getName())) {
/*     */       
/* 523 */       if (reader.getEventType() == 1) {
/* 524 */         Attributes attrs = XMLStreamReaderUtil.getAttributes(reader);
/* 525 */         String file = getAttribute(attrs, "file");
/* 526 */         if (file != null) {
/* 527 */           if (featureBuilder == null) {
/* 528 */             featureBuilder = ExternalMetadataFeature.builder();
/*     */           }
/* 530 */           featureBuilder.addFiles(new File[] { new File(file) });
/*     */         } 
/*     */         
/* 533 */         String res = getAttribute(attrs, "resource");
/* 534 */         if (res != null) {
/* 535 */           if (featureBuilder == null) {
/* 536 */             featureBuilder = ExternalMetadataFeature.builder();
/*     */           }
/* 538 */           featureBuilder.addResources(new String[] { res });
/*     */         } 
/*     */       } 
/*     */       
/* 542 */       XMLStreamReaderUtil.nextElementContent(reader);
/*     */     } 
/*     */     
/* 545 */     return buildFeature(featureBuilder);
/*     */   }
/*     */   
/*     */   private ExternalMetadataFeature buildFeature(ExternalMetadataFeature.Builder builder) {
/* 549 */     return (builder != null) ? builder.build() : null;
/*     */   }
/*     */   
/*     */   protected static void fail(String key, XMLStreamReader reader) {
/* 553 */     logger.log(Level.SEVERE, "{0}{1}", new Object[] { key, Integer.valueOf(reader.getLocation().getLineNumber()) });
/* 554 */     throw new ServerRtException(key, new Object[] {
/*     */           
/* 556 */           Integer.toString(reader.getLocation().getLineNumber()) });
/*     */   }
/*     */   
/*     */   protected static void failWithFullName(String key, XMLStreamReader reader) {
/* 560 */     throw new ServerRtException(key, new Object[] {
/*     */           
/* 562 */           Integer.valueOf(reader.getLocation().getLineNumber()), reader
/* 563 */           .getName() });
/*     */   }
/*     */   
/*     */   protected static void failWithLocalName(String key, XMLStreamReader reader) {
/* 567 */     throw new ServerRtException(key, new Object[] {
/*     */           
/* 569 */           Integer.valueOf(reader.getLocation().getLineNumber()), reader
/* 570 */           .getLocalName() });
/*     */   }
/*     */   
/*     */   protected static void failWithLocalName(String key, XMLStreamReader reader, String arg) {
/* 574 */     throw new ServerRtException(key, new Object[] {
/*     */           
/* 576 */           Integer.valueOf(reader.getLocation().getLineNumber()), reader
/* 577 */           .getLocalName(), arg
/*     */         });
/*     */   }
/*     */   
/*     */   protected Class loadClass(String name) {
/*     */     try {
/* 583 */       return Class.forName(name, true, this.classLoader);
/* 584 */     } catch (ClassNotFoundException e) {
/* 585 */       logger.log(Level.SEVERE, e.getMessage(), e);
/* 586 */       throw new ServerRtException("runtime.parser.classNotFound", new Object[] { name });
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
/*     */   private Class getImplementorClass(String name, XMLStreamReader xsr) {
/*     */     try {
/* 600 */       return Class.forName(name, true, this.classLoader);
/* 601 */     } catch (ClassNotFoundException e) {
/* 602 */       logger.log(Level.SEVERE, e.getMessage(), e);
/* 603 */       throw new LocatableWebServiceException(
/* 604 */           ServerMessages.RUNTIME_PARSER_CLASS_NOT_FOUND(name), e, xsr);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface AdapterFactory<A> {
/*     */     A createAdapter(String param1String1, String param1String2, WSEndpoint<?> param1WSEndpoint);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/transport/http/DeploymentDescriptorParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */