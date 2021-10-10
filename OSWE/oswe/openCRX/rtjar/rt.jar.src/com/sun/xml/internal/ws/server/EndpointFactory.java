/*     */ package com.sun.xml.internal.ws.server;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.databinding.ExternalMetadataFeature;
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.stream.buffer.MutableXMLStreamBuffer;
/*     */ import com.sun.xml.internal.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.internal.ws.api.BindingID;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.WSFeatureList;
/*     */ import com.sun.xml.internal.ws.api.databinding.DatabindingConfig;
/*     */ import com.sun.xml.internal.ws.api.databinding.DatabindingFactory;
/*     */ import com.sun.xml.internal.ws.api.databinding.MetadataReader;
/*     */ import com.sun.xml.internal.ws.api.databinding.WSDLGenInfo;
/*     */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLModel;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLService;
/*     */ import com.sun.xml.internal.ws.api.policy.PolicyResolver;
/*     */ import com.sun.xml.internal.ws.api.policy.PolicyResolverFactory;
/*     */ import com.sun.xml.internal.ws.api.server.AsyncProvider;
/*     */ import com.sun.xml.internal.ws.api.server.Container;
/*     */ import com.sun.xml.internal.ws.api.server.ContainerResolver;
/*     */ import com.sun.xml.internal.ws.api.server.InstanceResolver;
/*     */ import com.sun.xml.internal.ws.api.server.Invoker;
/*     */ import com.sun.xml.internal.ws.api.server.SDDocument;
/*     */ import com.sun.xml.internal.ws.api.server.SDDocumentSource;
/*     */ import com.sun.xml.internal.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
/*     */ import com.sun.xml.internal.ws.api.wsdl.parser.WSDLParserExtension;
/*     */ import com.sun.xml.internal.ws.api.wsdl.parser.XMLEntityResolver;
/*     */ import com.sun.xml.internal.ws.api.wsdl.writer.WSDLGeneratorExtension;
/*     */ import com.sun.xml.internal.ws.binding.BindingImpl;
/*     */ import com.sun.xml.internal.ws.binding.SOAPBindingImpl;
/*     */ import com.sun.xml.internal.ws.binding.WebServiceFeatureList;
/*     */ import com.sun.xml.internal.ws.db.DatabindingImpl;
/*     */ import com.sun.xml.internal.ws.model.AbstractSEIModelImpl;
/*     */ import com.sun.xml.internal.ws.model.ReflectAnnotationReader;
/*     */ import com.sun.xml.internal.ws.model.RuntimeModeler;
/*     */ import com.sun.xml.internal.ws.model.SOAPSEIModel;
/*     */ import com.sun.xml.internal.ws.policy.PolicyMap;
/*     */ import com.sun.xml.internal.ws.policy.jaxws.PolicyUtil;
/*     */ import com.sun.xml.internal.ws.resources.ServerMessages;
/*     */ import com.sun.xml.internal.ws.server.provider.ProviderInvokerTube;
/*     */ import com.sun.xml.internal.ws.server.sei.SEIInvokerTube;
/*     */ import com.sun.xml.internal.ws.util.HandlerAnnotationInfo;
/*     */ import com.sun.xml.internal.ws.util.HandlerAnnotationProcessor;
/*     */ import com.sun.xml.internal.ws.util.ServiceConfigurationError;
/*     */ import com.sun.xml.internal.ws.util.ServiceFinder;
/*     */ import com.sun.xml.internal.ws.util.xml.XmlUtil;
/*     */ import com.sun.xml.internal.ws.wsdl.parser.RuntimeWSDLParser;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Logger;
/*     */ import javax.jws.WebService;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.ws.Provider;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.WebServiceProvider;
/*     */ import javax.xml.ws.soap.SOAPBinding;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.InputSource;
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
/*     */ public class EndpointFactory
/*     */ {
/* 106 */   private static final EndpointFactory instance = new EndpointFactory();
/*     */   
/*     */   public static EndpointFactory getInstance() {
/* 109 */     return instance;
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
/*     */   public static <T> WSEndpoint<T> createEndpoint(Class<T> implType, boolean processHandlerAnnotation, @Nullable Invoker invoker, @Nullable QName serviceName, @Nullable QName portName, @Nullable Container container, @Nullable WSBinding binding, @Nullable SDDocumentSource primaryWsdl, @Nullable Collection<? extends SDDocumentSource> metadata, EntityResolver resolver, boolean isTransportSynchronous) {
/* 129 */     return createEndpoint(implType, processHandlerAnnotation, invoker, serviceName, portName, container, binding, primaryWsdl, metadata, resolver, isTransportSynchronous, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> WSEndpoint<T> createEndpoint(Class<T> implType, boolean processHandlerAnnotation, @Nullable Invoker invoker, @Nullable QName serviceName, @Nullable QName portName, @Nullable Container container, @Nullable WSBinding binding, @Nullable SDDocumentSource primaryWsdl, @Nullable Collection<? extends SDDocumentSource> metadata, EntityResolver resolver, boolean isTransportSynchronous, boolean isStandard) {
/* 140 */     EndpointFactory factory = (container != null) ? (EndpointFactory)container.getSPI(EndpointFactory.class) : null;
/* 141 */     if (factory == null) {
/* 142 */       factory = getInstance();
/*     */     }
/* 144 */     return factory.create(implType, processHandlerAnnotation, invoker, serviceName, portName, container, binding, primaryWsdl, metadata, resolver, isTransportSynchronous, isStandard);
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
/*     */   public <T> WSEndpoint<T> create(Class<T> implType, boolean processHandlerAnnotation, @Nullable Invoker invoker, @Nullable QName serviceName, @Nullable QName portName, @Nullable Container container, @Nullable WSBinding binding, @Nullable SDDocumentSource primaryWsdl, @Nullable Collection<? extends SDDocumentSource> metadata, EntityResolver resolver, boolean isTransportSynchronous) {
/* 165 */     return create(implType, processHandlerAnnotation, invoker, serviceName, portName, container, binding, primaryWsdl, metadata, resolver, isTransportSynchronous, true);
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
/*     */   public <T> WSEndpoint<T> create(Class<T> implType, boolean processHandlerAnnotation, @Nullable Invoker invoker, @Nullable QName serviceName, @Nullable QName portName, @Nullable Container container, @Nullable WSBinding binding, @Nullable SDDocumentSource primaryWsdl, @Nullable Collection<? extends SDDocumentSource> metadata, EntityResolver resolver, boolean isTransportSynchronous, boolean isStandard) {
/*     */     BindingImpl bindingImpl;
/*     */     EndpointAwareTube terminal;
/* 179 */     if (implType == null) {
/* 180 */       throw new IllegalArgumentException();
/*     */     }
/* 182 */     MetadataReader metadataReader = getExternalMetadatReader(implType, binding);
/*     */     
/* 184 */     if (isStandard) {
/* 185 */       verifyImplementorClass(implType, metadataReader);
/*     */     }
/*     */     
/* 188 */     if (invoker == null) {
/* 189 */       invoker = InstanceResolver.createDefault(implType).createInvoker();
/*     */     }
/*     */     
/* 192 */     List<SDDocumentSource> md = new ArrayList<>();
/* 193 */     if (metadata != null) {
/* 194 */       md.addAll(metadata);
/*     */     }
/* 196 */     if (primaryWsdl != null && !md.contains(primaryWsdl)) {
/* 197 */       md.add(primaryWsdl);
/*     */     }
/* 199 */     if (container == null) {
/* 200 */       container = ContainerResolver.getInstance().getContainer();
/*     */     }
/* 202 */     if (serviceName == null) {
/* 203 */       serviceName = getDefaultServiceName(implType, metadataReader);
/*     */     }
/* 205 */     if (portName == null) {
/* 206 */       portName = getDefaultPortName(serviceName, implType, metadataReader);
/*     */     }
/*     */     
/* 209 */     String serviceNS = serviceName.getNamespaceURI();
/* 210 */     String portNS = portName.getNamespaceURI();
/* 211 */     if (!serviceNS.equals(portNS)) {
/* 212 */       throw new ServerRtException("wrong.tns.for.port", new Object[] { portNS, serviceNS });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 217 */     if (binding == null) {
/* 218 */       bindingImpl = BindingImpl.create(BindingID.parse(implType));
/*     */     }
/* 220 */     if (isStandard && primaryWsdl != null) {
/* 221 */       verifyPrimaryWSDL(primaryWsdl, serviceName);
/*     */     }
/*     */     
/* 224 */     QName portTypeName = null;
/* 225 */     if (isStandard && implType.getAnnotation(WebServiceProvider.class) == null) {
/* 226 */       portTypeName = RuntimeModeler.getPortTypeName(implType, metadataReader);
/*     */     }
/*     */ 
/*     */     
/* 230 */     List<SDDocumentImpl> docList = categoriseMetadata(md, serviceName, portTypeName);
/*     */ 
/*     */     
/* 233 */     SDDocumentImpl primaryDoc = (primaryWsdl != null) ? SDDocumentImpl.create(primaryWsdl, serviceName, portTypeName) : findPrimary(docList);
/*     */ 
/*     */     
/* 236 */     WSDLPort wsdlPort = null;
/* 237 */     AbstractSEIModelImpl seiModel = null;
/*     */     
/* 239 */     if (primaryDoc != null) {
/* 240 */       wsdlPort = getWSDLPort(primaryDoc, (List)docList, serviceName, portName, container, resolver);
/*     */     }
/*     */     
/* 243 */     WebServiceFeatureList features = bindingImpl.getFeatures();
/* 244 */     if (isStandard) {
/* 245 */       features.parseAnnotations(implType);
/*     */     }
/* 247 */     PolicyMap policyMap = null;
/*     */     
/* 249 */     if (isUseProviderTube(implType, isStandard)) {
/*     */       Iterable<WebServiceFeature> configFtrs;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 255 */       if (wsdlPort != null) {
/* 256 */         policyMap = wsdlPort.getOwner().getParent().getPolicyMap();
/*     */         
/* 258 */         WSFeatureList wSFeatureList = wsdlPort.getFeatures();
/*     */       } else {
/*     */         
/* 261 */         policyMap = PolicyResolverFactory.create().resolve(new PolicyResolver.ServerContext(null, container, implType, false, new com.sun.xml.internal.ws.policy.PolicyMapMutator[0]));
/*     */         
/* 263 */         configFtrs = PolicyUtil.getPortScopedFeatures(policyMap, serviceName, portName);
/*     */       } 
/* 265 */       features.mergeFeatures(configFtrs, true);
/* 266 */       terminal = createProviderInvokerTube(implType, (WSBinding)bindingImpl, invoker, container);
/*     */     } else {
/*     */       
/* 269 */       seiModel = createSEIModel(wsdlPort, implType, serviceName, portName, (WSBinding)bindingImpl, primaryDoc);
/* 270 */       if (bindingImpl instanceof SOAPBindingImpl)
/*     */       {
/* 272 */         ((SOAPBindingImpl)bindingImpl).setPortKnownHeaders(((SOAPSEIModel)seiModel)
/* 273 */             .getKnownHeaders());
/*     */       }
/*     */       
/* 276 */       if (primaryDoc == null) {
/* 277 */         primaryDoc = generateWSDL((WSBinding)bindingImpl, seiModel, docList, container, implType);
/*     */         
/* 279 */         wsdlPort = getWSDLPort(primaryDoc, (List)docList, serviceName, portName, container, resolver);
/* 280 */         seiModel.freeze(wsdlPort);
/*     */       } 
/* 282 */       policyMap = wsdlPort.getOwner().getParent().getPolicyMap();
/*     */ 
/*     */ 
/*     */       
/* 286 */       features.mergeFeatures((Iterable)wsdlPort.getFeatures(), true);
/* 287 */       terminal = createSEIInvokerTube(seiModel, invoker, (WSBinding)bindingImpl);
/*     */     } 
/*     */ 
/*     */     
/* 291 */     if (processHandlerAnnotation) {
/* 292 */       processHandlerAnnotation((WSBinding)bindingImpl, implType, serviceName, portName);
/*     */     }
/*     */     
/* 295 */     if (primaryDoc != null) {
/* 296 */       docList = findMetadataClosure(primaryDoc, docList, resolver);
/*     */     }
/*     */     
/* 299 */     ServiceDefinitionImpl serviceDefiniton = (primaryDoc != null) ? new ServiceDefinitionImpl(docList, primaryDoc) : null;
/*     */     
/* 301 */     return create(serviceName, portName, (WSBinding)bindingImpl, container, (SEIModel)seiModel, wsdlPort, implType, serviceDefiniton, terminal, isTransportSynchronous, policyMap);
/*     */   }
/*     */ 
/*     */   
/*     */   protected <T> WSEndpoint<T> create(QName serviceName, QName portName, WSBinding binding, Container container, SEIModel seiModel, WSDLPort wsdlPort, Class<T> implType, ServiceDefinitionImpl serviceDefinition, EndpointAwareTube terminal, boolean isTransportSynchronous, PolicyMap policyMap) {
/* 306 */     return new WSEndpointImpl<>(serviceName, portName, binding, container, seiModel, wsdlPort, implType, serviceDefinition, terminal, isTransportSynchronous, policyMap);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isUseProviderTube(Class<?> implType, boolean isStandard) {
/* 311 */     return (!isStandard || implType.getAnnotation(WebServiceProvider.class) != null);
/*     */   }
/*     */   
/*     */   protected EndpointAwareTube createSEIInvokerTube(AbstractSEIModelImpl seiModel, Invoker invoker, WSBinding binding) {
/* 315 */     return (EndpointAwareTube)new SEIInvokerTube(seiModel, invoker, binding);
/*     */   }
/*     */ 
/*     */   
/*     */   protected <T> EndpointAwareTube createProviderInvokerTube(Class<T> implType, WSBinding binding, Invoker invoker, Container container) {
/* 320 */     return (EndpointAwareTube)ProviderInvokerTube.create(implType, binding, invoker, container);
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
/*     */   private static List<SDDocumentImpl> findMetadataClosure(SDDocumentImpl primaryDoc, List<SDDocumentImpl> docList, EntityResolver resolver) {
/* 333 */     Map<String, SDDocumentImpl> oldMap = new HashMap<>();
/* 334 */     for (SDDocumentImpl doc : docList) {
/* 335 */       oldMap.put(doc.getSystemId().toString(), doc);
/*     */     }
/*     */     
/* 338 */     Map<String, SDDocumentImpl> newMap = new HashMap<>();
/* 339 */     newMap.put(primaryDoc.getSystemId().toString(), primaryDoc);
/*     */     
/* 341 */     List<String> remaining = new ArrayList<>();
/* 342 */     remaining.addAll(primaryDoc.getImports());
/* 343 */     while (!remaining.isEmpty()) {
/* 344 */       String url = remaining.remove(0);
/* 345 */       SDDocumentImpl doc = oldMap.get(url);
/* 346 */       if (doc == null)
/*     */       {
/* 348 */         if (resolver != null) {
/*     */           try {
/* 350 */             InputSource source = resolver.resolveEntity(null, url);
/* 351 */             if (source != null) {
/* 352 */               MutableXMLStreamBuffer xsb = new MutableXMLStreamBuffer();
/* 353 */               XMLStreamReader reader = XmlUtil.newXMLInputFactory(true).createXMLStreamReader(source.getByteStream());
/* 354 */               xsb.createFromXMLStreamReader(reader);
/*     */               
/* 356 */               SDDocumentSource sdocSource = SDDocumentImpl.create(new URL(url), (XMLStreamBuffer)xsb);
/* 357 */               doc = SDDocumentImpl.create(sdocSource, (QName)null, (QName)null);
/*     */             } 
/* 359 */           } catch (Exception ex) {
/* 360 */             ex.printStackTrace();
/*     */           } 
/*     */         }
/*     */       }
/*     */       
/* 365 */       if (doc != null && !newMap.containsKey(url)) {
/* 366 */         newMap.put(url, doc);
/* 367 */         remaining.addAll(doc.getImports());
/*     */       } 
/*     */     } 
/* 370 */     List<SDDocumentImpl> newMetadata = new ArrayList<>();
/* 371 */     newMetadata.addAll(newMap.values());
/* 372 */     return newMetadata;
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> void processHandlerAnnotation(WSBinding binding, Class<T> implType, QName serviceName, QName portName) {
/* 377 */     HandlerAnnotationInfo chainInfo = HandlerAnnotationProcessor.buildHandlerInfo(implType, serviceName, portName, binding);
/*     */     
/* 379 */     if (chainInfo != null) {
/* 380 */       binding.setHandlerChain(chainInfo.getHandlers());
/* 381 */       if (binding instanceof SOAPBinding) {
/* 382 */         ((SOAPBinding)binding).setRoles(chainInfo.getRoles());
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
/*     */   public static boolean verifyImplementorClass(Class<?> clz) {
/* 400 */     return verifyImplementorClass(clz, null);
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
/*     */   public static boolean verifyImplementorClass(Class<?> clz, MetadataReader metadataReader) {
/*     */     ReflectAnnotationReader reflectAnnotationReader;
/* 417 */     if (metadataReader == null) {
/* 418 */       reflectAnnotationReader = new ReflectAnnotationReader();
/*     */     }
/*     */     
/* 421 */     WebServiceProvider wsProvider = (WebServiceProvider)reflectAnnotationReader.getAnnotation(WebServiceProvider.class, clz);
/* 422 */     WebService ws = (WebService)reflectAnnotationReader.getAnnotation(WebService.class, clz);
/* 423 */     if (wsProvider == null && ws == null) {
/* 424 */       throw new IllegalArgumentException(clz + " has neither @WebService nor @WebServiceProvider annotation");
/*     */     }
/* 426 */     if (wsProvider != null && ws != null) {
/* 427 */       throw new IllegalArgumentException(clz + " has both @WebService and @WebServiceProvider annotations");
/*     */     }
/* 429 */     if (wsProvider != null) {
/* 430 */       if (Provider.class.isAssignableFrom(clz) || AsyncProvider.class.isAssignableFrom(clz)) {
/* 431 */         return true;
/*     */       }
/* 433 */       throw new IllegalArgumentException(clz + " doesn't implement Provider or AsyncProvider interface");
/*     */     } 
/* 435 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AbstractSEIModelImpl createSEIModel(WSDLPort wsdlPort, Class<?> implType, @NotNull QName serviceName, @NotNull QName portName, WSBinding binding, SDDocumentSource primaryWsdl) {
/* 442 */     DatabindingFactory fac = DatabindingFactory.newInstance();
/* 443 */     DatabindingConfig config = new DatabindingConfig();
/* 444 */     config.setEndpointClass(implType);
/* 445 */     config.getMappingInfo().setServiceName(serviceName);
/* 446 */     config.setWsdlPort(wsdlPort);
/* 447 */     config.setWSBinding(binding);
/* 448 */     config.setClassLoader(implType.getClassLoader());
/* 449 */     config.getMappingInfo().setPortName(portName);
/* 450 */     if (primaryWsdl != null) config.setWsdlURL(primaryWsdl.getSystemId()); 
/* 451 */     config.setMetadataReader(getExternalMetadatReader(implType, binding));
/*     */     
/* 453 */     DatabindingImpl rt = (DatabindingImpl)fac.createRuntime(config);
/* 454 */     return (AbstractSEIModelImpl)rt.getModel();
/*     */   }
/*     */   
/*     */   public static MetadataReader getExternalMetadatReader(Class<?> implType, WSBinding binding) {
/* 458 */     ExternalMetadataFeature ef = (ExternalMetadataFeature)binding.getFeature(ExternalMetadataFeature.class);
/*     */ 
/*     */     
/* 461 */     if (ef != null)
/* 462 */       return ef.getMetadataReader(implType.getClassLoader(), false); 
/* 463 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static QName getDefaultServiceName(Class<?> implType) {
/* 490 */     return getDefaultServiceName(implType, (MetadataReader)null);
/*     */   }
/*     */   @NotNull
/*     */   public static QName getDefaultServiceName(Class<?> implType, MetadataReader metadataReader) {
/* 494 */     return getDefaultServiceName(implType, true, metadataReader);
/*     */   }
/*     */   
/*     */   @NotNull
/* 498 */   public static QName getDefaultServiceName(Class<?> implType, boolean isStandard) { return getDefaultServiceName(implType, isStandard, null); } @NotNull
/*     */   public static QName getDefaultServiceName(Class<?> implType, boolean isStandard, MetadataReader metadataReader) {
/*     */     ReflectAnnotationReader reflectAnnotationReader;
/*     */     QName serviceName;
/* 502 */     if (metadataReader == null) {
/* 503 */       reflectAnnotationReader = new ReflectAnnotationReader();
/*     */     }
/*     */     
/* 506 */     WebServiceProvider wsProvider = (WebServiceProvider)reflectAnnotationReader.getAnnotation(WebServiceProvider.class, implType);
/* 507 */     if (wsProvider != null) {
/* 508 */       String tns = wsProvider.targetNamespace();
/* 509 */       String local = wsProvider.serviceName();
/* 510 */       serviceName = new QName(tns, local);
/*     */     } else {
/* 512 */       serviceName = RuntimeModeler.getServiceName(implType, (MetadataReader)reflectAnnotationReader, isStandard);
/*     */     } 
/* 514 */     assert serviceName != null;
/* 515 */     return serviceName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static QName getDefaultPortName(QName serviceName, Class<?> implType) {
/* 525 */     return getDefaultPortName(serviceName, implType, (MetadataReader)null);
/*     */   }
/*     */   @NotNull
/*     */   public static QName getDefaultPortName(QName serviceName, Class<?> implType, MetadataReader metadataReader) {
/* 529 */     return getDefaultPortName(serviceName, implType, true, metadataReader);
/*     */   }
/*     */   
/*     */   @NotNull
/* 533 */   public static QName getDefaultPortName(QName serviceName, Class<?> implType, boolean isStandard) { return getDefaultPortName(serviceName, implType, isStandard, null); } @NotNull
/*     */   public static QName getDefaultPortName(QName serviceName, Class<?> implType, boolean isStandard, MetadataReader metadataReader) {
/*     */     ReflectAnnotationReader reflectAnnotationReader;
/*     */     QName portName;
/* 537 */     if (metadataReader == null) {
/* 538 */       reflectAnnotationReader = new ReflectAnnotationReader();
/*     */     }
/*     */     
/* 541 */     WebServiceProvider wsProvider = (WebServiceProvider)reflectAnnotationReader.getAnnotation(WebServiceProvider.class, implType);
/* 542 */     if (wsProvider != null) {
/* 543 */       String tns = wsProvider.targetNamespace();
/* 544 */       String local = wsProvider.portName();
/* 545 */       portName = new QName(tns, local);
/*     */     } else {
/* 547 */       portName = RuntimeModeler.getPortName(implType, (MetadataReader)reflectAnnotationReader, serviceName.getNamespaceURI(), isStandard);
/*     */     } 
/* 549 */     assert portName != null;
/* 550 */     return portName;
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
/*     */   @Nullable
/*     */   public static String getWsdlLocation(Class<?> implType) {
/* 563 */     return getWsdlLocation(implType, (MetadataReader)new ReflectAnnotationReader());
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
/*     */   @Nullable
/*     */   public static String getWsdlLocation(Class<?> implType, MetadataReader metadataReader) {
/*     */     ReflectAnnotationReader reflectAnnotationReader;
/* 577 */     if (metadataReader == null) {
/* 578 */       reflectAnnotationReader = new ReflectAnnotationReader();
/*     */     }
/*     */     
/* 581 */     WebService ws = (WebService)reflectAnnotationReader.getAnnotation(WebService.class, implType);
/* 582 */     if (ws != null) {
/* 583 */       return nullIfEmpty(ws.wsdlLocation());
/*     */     }
/* 585 */     WebServiceProvider wsProvider = implType.<WebServiceProvider>getAnnotation(WebServiceProvider.class);
/* 586 */     assert wsProvider != null;
/* 587 */     return nullIfEmpty(wsProvider.wsdlLocation());
/*     */   }
/*     */ 
/*     */   
/*     */   private static String nullIfEmpty(String string) {
/* 592 */     if (string.length() < 1) {
/* 593 */       string = null;
/*     */     }
/* 595 */     return string;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static SDDocumentImpl generateWSDL(WSBinding binding, AbstractSEIModelImpl seiModel, List<SDDocumentImpl> docs, Container container, Class implType) {
/* 604 */     BindingID bindingId = binding.getBindingId();
/* 605 */     if (!bindingId.canGenerateWSDL()) {
/* 606 */       throw new ServerRtException("can.not.generate.wsdl", new Object[] { bindingId });
/*     */     }
/*     */     
/* 609 */     if (bindingId.toString().equals("http://java.sun.com/xml/ns/jaxws/2003/05/soap/bindings/HTTP/")) {
/* 610 */       String msg = ServerMessages.GENERATE_NON_STANDARD_WSDL();
/* 611 */       logger.warning(msg);
/*     */     } 
/*     */ 
/*     */     
/* 615 */     WSDLGenResolver wsdlResolver = new WSDLGenResolver(docs, seiModel.getServiceQName(), seiModel.getPortTypeName());
/* 616 */     WSDLGenInfo wsdlGenInfo = new WSDLGenInfo();
/* 617 */     wsdlGenInfo.setWsdlResolver(wsdlResolver);
/* 618 */     wsdlGenInfo.setContainer(container);
/* 619 */     wsdlGenInfo.setExtensions((WSDLGeneratorExtension[])ServiceFinder.find(WSDLGeneratorExtension.class).toArray());
/* 620 */     wsdlGenInfo.setInlineSchemas(false);
/* 621 */     wsdlGenInfo.setSecureXmlProcessingDisabled(isSecureXmlProcessingDisabled(binding.getFeatures()));
/* 622 */     seiModel.getDatabinding().generateWSDL(wsdlGenInfo);
/*     */ 
/*     */ 
/*     */     
/* 626 */     return wsdlResolver.updateDocs();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isSecureXmlProcessingDisabled(WSFeatureList featureList) {
/* 631 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<SDDocumentImpl> categoriseMetadata(List<SDDocumentSource> src, QName serviceName, QName portTypeName) {
/* 640 */     List<SDDocumentImpl> r = new ArrayList<>(src.size());
/* 641 */     for (SDDocumentSource doc : src) {
/* 642 */       r.add(SDDocumentImpl.create(doc, serviceName, portTypeName));
/*     */     }
/* 644 */     return r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void verifyPrimaryWSDL(@NotNull SDDocumentSource primaryWsdl, @NotNull QName serviceName) {
/* 652 */     SDDocumentImpl primaryDoc = SDDocumentImpl.create(primaryWsdl, serviceName, (QName)null);
/* 653 */     if (!(primaryDoc instanceof SDDocument.WSDL)) {
/* 654 */       throw new WebServiceException(primaryWsdl.getSystemId() + " is not a WSDL. But it is passed as a primary WSDL");
/*     */     }
/*     */     
/* 657 */     SDDocument.WSDL wsdlDoc = (SDDocument.WSDL)primaryDoc;
/* 658 */     if (!wsdlDoc.hasService()) {
/* 659 */       if (wsdlDoc.getAllServices().isEmpty()) {
/* 660 */         throw new WebServiceException("Not a primary WSDL=" + primaryWsdl.getSystemId() + " since it doesn't have Service " + serviceName);
/*     */       }
/*     */       
/* 663 */       throw new WebServiceException("WSDL " + primaryDoc.getSystemId() + " has the following services " + wsdlDoc
/* 664 */           .getAllServices() + " but not " + serviceName + ". Maybe you forgot to specify a serviceName and/or targetNamespace in @WebService/@WebServiceProvider?");
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
/*     */   @Nullable
/*     */   private static SDDocumentImpl findPrimary(@NotNull List<SDDocumentImpl> docList) {
/* 679 */     SDDocumentImpl primaryDoc = null;
/* 680 */     boolean foundConcrete = false;
/* 681 */     boolean foundAbstract = false;
/* 682 */     for (SDDocumentImpl doc : docList) {
/* 683 */       if (doc instanceof SDDocument.WSDL) {
/* 684 */         SDDocument.WSDL wsdlDoc = (SDDocument.WSDL)doc;
/* 685 */         if (wsdlDoc.hasService()) {
/* 686 */           primaryDoc = doc;
/* 687 */           if (foundConcrete) {
/* 688 */             throw new ServerRtException("duplicate.primary.wsdl", new Object[] { doc.getSystemId() });
/*     */           }
/* 690 */           foundConcrete = true;
/*     */         } 
/* 692 */         if (wsdlDoc.hasPortType()) {
/* 693 */           if (foundAbstract) {
/* 694 */             throw new ServerRtException("duplicate.abstract.wsdl", new Object[] { doc.getSystemId() });
/*     */           }
/* 696 */           foundAbstract = true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 700 */     return primaryDoc;
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
/*     */   @NotNull
/*     */   private static WSDLPort getWSDLPort(SDDocumentSource primaryWsdl, List<? extends SDDocumentSource> metadata, @NotNull QName serviceName, @NotNull QName portName, Container container, EntityResolver resolver) {
/* 716 */     URL wsdlUrl = primaryWsdl.getSystemId();
/*     */     
/*     */     try {
/* 719 */       WSDLModel wsdlDoc = RuntimeWSDLParser.parse(new XMLEntityResolver.Parser(primaryWsdl), new EntityResolverImpl(metadata, resolver), false, container, 
/*     */           
/* 721 */           (WSDLParserExtension[])ServiceFinder.find(WSDLParserExtension.class).toArray());
/* 722 */       if (wsdlDoc.getServices().size() == 0) {
/* 723 */         throw new ServerRtException(ServerMessages.localizableRUNTIME_PARSER_WSDL_NOSERVICE_IN_WSDLMODEL(wsdlUrl));
/*     */       }
/* 725 */       WSDLService wsdlService = wsdlDoc.getService(serviceName);
/* 726 */       if (wsdlService == null) {
/* 727 */         throw new ServerRtException(ServerMessages.localizableRUNTIME_PARSER_WSDL_INCORRECTSERVICE(serviceName, wsdlUrl));
/*     */       }
/* 729 */       WSDLPort wsdlPort = wsdlService.get(portName);
/* 730 */       if (wsdlPort == null) {
/* 731 */         throw new ServerRtException(ServerMessages.localizableRUNTIME_PARSER_WSDL_INCORRECTSERVICEPORT(serviceName, portName, wsdlUrl));
/*     */       }
/* 733 */       return wsdlPort;
/* 734 */     } catch (IOException e) {
/* 735 */       throw new ServerRtException("runtime.parser.wsdl", new Object[] { wsdlUrl, e });
/* 736 */     } catch (XMLStreamException e) {
/* 737 */       throw new ServerRtException("runtime.saxparser.exception", new Object[] { e.getMessage(), e.getLocation(), e });
/* 738 */     } catch (SAXException e) {
/* 739 */       throw new ServerRtException("runtime.parser.wsdl", new Object[] { wsdlUrl, e });
/* 740 */     } catch (ServiceConfigurationError e) {
/* 741 */       throw new ServerRtException("runtime.parser.wsdl", new Object[] { wsdlUrl, e });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class EntityResolverImpl
/*     */     implements XMLEntityResolver
/*     */   {
/* 749 */     private Map<String, SDDocumentSource> metadata = new HashMap<>();
/*     */     private EntityResolver resolver;
/*     */     
/*     */     public EntityResolverImpl(List<? extends SDDocumentSource> metadata, EntityResolver resolver) {
/* 753 */       for (SDDocumentSource doc : metadata) {
/* 754 */         this.metadata.put(doc.getSystemId().toExternalForm(), doc);
/*     */       }
/* 756 */       this.resolver = resolver;
/*     */     }
/*     */     
/*     */     public XMLEntityResolver.Parser resolveEntity(String publicId, String systemId) throws IOException, XMLStreamException {
/* 760 */       if (systemId != null) {
/* 761 */         SDDocumentSource doc = this.metadata.get(systemId);
/* 762 */         if (doc != null)
/* 763 */           return new XMLEntityResolver.Parser(doc); 
/*     */       } 
/* 765 */       if (this.resolver != null) {
/*     */         try {
/* 767 */           InputSource source = this.resolver.resolveEntity(publicId, systemId);
/* 768 */           if (source != null) {
/* 769 */             XMLEntityResolver.Parser p = new XMLEntityResolver.Parser(null, XMLStreamReaderFactory.create(source, true));
/* 770 */             return p;
/*     */           } 
/* 772 */         } catch (SAXException e) {
/* 773 */           throw new XMLStreamException(e);
/*     */         } 
/*     */       }
/* 776 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 781 */   private static final Logger logger = Logger.getLogger("com.sun.xml.internal.ws.server.endpoint");
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/server/EndpointFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */