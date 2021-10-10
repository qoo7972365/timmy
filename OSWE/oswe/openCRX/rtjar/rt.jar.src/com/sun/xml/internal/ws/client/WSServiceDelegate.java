/*     */ package com.sun.xml.internal.ws.client;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.databinding.ExternalMetadataFeature;
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.Closeable;
/*     */ import com.sun.xml.internal.ws.api.BindingID;
/*     */ import com.sun.xml.internal.ws.api.ComponentFeature;
/*     */ import com.sun.xml.internal.ws.api.ComponentsFeature;
/*     */ import com.sun.xml.internal.ws.api.EndpointAddress;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.WSService;
/*     */ import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.internal.ws.api.client.ServiceInterceptor;
/*     */ import com.sun.xml.internal.ws.api.client.ServiceInterceptorFactory;
/*     */ import com.sun.xml.internal.ws.api.databinding.DatabindingConfig;
/*     */ import com.sun.xml.internal.ws.api.databinding.DatabindingFactory;
/*     */ import com.sun.xml.internal.ws.api.databinding.MetadataReader;
/*     */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLModel;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLService;
/*     */ import com.sun.xml.internal.ws.api.pipe.Stubs;
/*     */ import com.sun.xml.internal.ws.api.server.Container;
/*     */ import com.sun.xml.internal.ws.api.server.ContainerResolver;
/*     */ import com.sun.xml.internal.ws.api.wsdl.parser.WSDLParserExtension;
/*     */ import com.sun.xml.internal.ws.binding.BindingImpl;
/*     */ import com.sun.xml.internal.ws.binding.WebServiceFeatureList;
/*     */ import com.sun.xml.internal.ws.client.sei.SEIStub;
/*     */ import com.sun.xml.internal.ws.db.DatabindingImpl;
/*     */ import com.sun.xml.internal.ws.developer.MemberSubmissionAddressingFeature;
/*     */ import com.sun.xml.internal.ws.developer.UsesJAXBContextFeature;
/*     */ import com.sun.xml.internal.ws.developer.WSBindingProvider;
/*     */ import com.sun.xml.internal.ws.model.RuntimeModeler;
/*     */ import com.sun.xml.internal.ws.model.SOAPSEIModel;
/*     */ import com.sun.xml.internal.ws.resources.ClientMessages;
/*     */ import com.sun.xml.internal.ws.resources.DispatchMessages;
/*     */ import com.sun.xml.internal.ws.resources.ProviderApiMessages;
/*     */ import com.sun.xml.internal.ws.util.JAXWSUtils;
/*     */ import com.sun.xml.internal.ws.util.ServiceConfigurationError;
/*     */ import com.sun.xml.internal.ws.util.ServiceFinder;
/*     */ import com.sun.xml.internal.ws.util.xml.XmlUtil;
/*     */ import com.sun.xml.internal.ws.wsdl.parser.RuntimeWSDLParser;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PermissionCollection;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.ProtectionDomain;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import javax.jws.HandlerChain;
/*     */ import javax.jws.WebService;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import javax.xml.ws.Dispatch;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ import javax.xml.ws.Service;
/*     */ import javax.xml.ws.WebServiceClient;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.handler.HandlerResolver;
/*     */ import javax.xml.ws.soap.AddressingFeature;
/*     */ import org.xml.sax.EntityResolver;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSServiceDelegate
/*     */   extends WSService
/*     */ {
/* 146 */   private final Map<QName, PortInfo> ports = new HashMap<>();
/*     */   protected Map<QName, PortInfo> getQNameToPortInfoMap() {
/* 148 */     return this.ports;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/* 153 */   private HandlerConfigurator handlerConfigurator = new HandlerConfigurator.HandlerResolverImpl(null);
/*     */ 
/*     */ 
/*     */   
/*     */   private final Class<? extends Service> serviceClass;
/*     */ 
/*     */ 
/*     */   
/*     */   private final WebServiceFeatureList features;
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private final QName serviceName;
/*     */ 
/*     */   
/* 168 */   private final Map<QName, SEIPortInfo> seiContext = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile Executor executor;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private WSDLService wsdlService;
/*     */ 
/*     */   
/*     */   private final Container container;
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   final ServiceInterceptor serviceInterceptor;
/*     */ 
/*     */   
/*     */   private URL wsdlURL;
/*     */ 
/*     */ 
/*     */   
/*     */   public WSServiceDelegate(URL wsdlDocumentLocation, QName serviceName, Class<? extends Service> serviceClass, WebServiceFeature... features) {
/* 192 */     this(wsdlDocumentLocation, serviceName, serviceClass, new WebServiceFeatureList(features));
/*     */   }
/*     */   
/*     */   protected WSServiceDelegate(URL wsdlDocumentLocation, QName serviceName, Class<? extends Service> serviceClass, WebServiceFeatureList features) {
/* 196 */     this((wsdlDocumentLocation == null) ? null : new StreamSource(wsdlDocumentLocation
/* 197 */           .toExternalForm()), serviceName, serviceClass, features);
/*     */     
/* 199 */     this.wsdlURL = wsdlDocumentLocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WSServiceDelegate(@Nullable Source wsdl, @NotNull QName serviceName, @NotNull Class<? extends Service> serviceClass, WebServiceFeature... features) {
/* 207 */     this(wsdl, serviceName, serviceClass, new WebServiceFeatureList(features));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WSServiceDelegate(@Nullable Source wsdl, @NotNull QName serviceName, @NotNull Class<? extends Service> serviceClass, WebServiceFeatureList features) {
/* 215 */     this(wsdl, (WSDLService)null, serviceName, serviceClass, features);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WSServiceDelegate(@Nullable Source wsdl, @Nullable WSDLService service, @NotNull QName serviceName, @NotNull Class<? extends Service> serviceClass, WebServiceFeature... features) {
/* 223 */     this(wsdl, service, serviceName, serviceClass, new WebServiceFeatureList(features));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WSServiceDelegate(@Nullable Source wsdl, @Nullable WSDLService service, @NotNull QName serviceName, @NotNull final Class<? extends Service> serviceClass, WebServiceFeatureList features) {
/* 232 */     if (serviceName == null) {
/* 233 */       throw new WebServiceException(ClientMessages.INVALID_SERVICE_NAME_NULL(null));
/*     */     }
/*     */     
/* 236 */     this.features = features;
/*     */     
/* 238 */     WSService.InitParams initParams = INIT_PARAMS.get();
/* 239 */     INIT_PARAMS.set(null);
/* 240 */     if (initParams == null) {
/* 241 */       initParams = EMPTY_PARAMS;
/*     */     }
/*     */     
/* 244 */     this.serviceName = serviceName;
/* 245 */     this.serviceClass = serviceClass;
/* 246 */     Container tContainer = (initParams.getContainer() != null) ? initParams.getContainer() : ContainerResolver.getInstance().getContainer();
/* 247 */     if (tContainer == Container.NONE) {
/* 248 */       tContainer = new ClientContainer();
/*     */     }
/* 250 */     this.container = tContainer;
/*     */     
/* 252 */     ComponentFeature cf = (ComponentFeature)this.features.get(ComponentFeature.class);
/* 253 */     if (cf != null) {
/* 254 */       switch (cf.getTarget()) {
/*     */         case SERVICE:
/* 256 */           getComponents().add(cf.getComponent());
/*     */           break;
/*     */         case CONTAINER:
/* 259 */           this.container.getComponents().add(cf.getComponent());
/*     */           break;
/*     */         default:
/* 262 */           throw new IllegalArgumentException();
/*     */       } 
/*     */     }
/* 265 */     ComponentsFeature csf = (ComponentsFeature)this.features.get(ComponentsFeature.class);
/* 266 */     if (csf != null) {
/* 267 */       for (ComponentFeature cfi : csf.getComponentFeatures()) {
/* 268 */         switch (cfi.getTarget()) {
/*     */           case SERVICE:
/* 270 */             getComponents().add(cfi.getComponent());
/*     */             continue;
/*     */           case CONTAINER:
/* 273 */             this.container.getComponents().add(cfi.getComponent());
/*     */             continue;
/*     */         } 
/* 276 */         throw new IllegalArgumentException();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 282 */     ServiceInterceptor interceptor = ServiceInterceptorFactory.load(this, Thread.currentThread().getContextClassLoader());
/* 283 */     ServiceInterceptor si = (ServiceInterceptor)this.container.getSPI(ServiceInterceptor.class);
/* 284 */     if (si != null) {
/* 285 */       interceptor = ServiceInterceptor.aggregate(new ServiceInterceptor[] { interceptor, si });
/*     */     }
/* 287 */     this.serviceInterceptor = interceptor;
/*     */     
/* 289 */     if (service == null) {
/*     */       
/* 291 */       if (wsdl == null && 
/* 292 */         serviceClass != Service.class) {
/* 293 */         WebServiceClient wsClient = AccessController.<WebServiceClient>doPrivileged(new PrivilegedAction<WebServiceClient>() {
/*     */               public WebServiceClient run() {
/* 295 */                 return (WebServiceClient)serviceClass.getAnnotation(WebServiceClient.class);
/*     */               }
/*     */             });
/* 298 */         String wsdlLocation = wsClient.wsdlLocation();
/* 299 */         wsdlLocation = JAXWSUtils.absolutize(JAXWSUtils.getFileOrURLName(wsdlLocation));
/* 300 */         wsdl = new StreamSource(wsdlLocation);
/*     */       } 
/*     */       
/* 303 */       if (wsdl != null) {
/*     */         try {
/* 305 */           URL url = (wsdl.getSystemId() == null) ? null : JAXWSUtils.getEncodedURL(wsdl.getSystemId());
/* 306 */           WSDLModel model = parseWSDL(url, wsdl, serviceClass);
/* 307 */           service = model.getService(this.serviceName);
/* 308 */           if (service == null) {
/* 309 */             throw new WebServiceException(
/* 310 */                 ClientMessages.INVALID_SERVICE_NAME(this.serviceName, 
/* 311 */                   buildNameList(model.getServices().keySet())));
/*     */           }
/* 313 */           for (WSDLPort port : service.getPorts())
/* 314 */             this.ports.put(port.getName(), new PortInfo(this, port)); 
/* 315 */         } catch (MalformedURLException e) {
/* 316 */           throw new WebServiceException(ClientMessages.INVALID_WSDL_URL(wsdl.getSystemId()));
/*     */         } 
/*     */       }
/*     */     } else {
/*     */       
/* 321 */       for (WSDLPort port : service.getPorts())
/* 322 */         this.ports.put(port.getName(), new PortInfo(this, port)); 
/*     */     } 
/* 324 */     this.wsdlService = service;
/*     */     
/* 326 */     if (serviceClass != Service.class) {
/*     */ 
/*     */       
/* 329 */       HandlerChain handlerChain = AccessController.<HandlerChain>doPrivileged(new PrivilegedAction<HandlerChain>() {
/*     */             public HandlerChain run() {
/* 331 */               return (HandlerChain)serviceClass.getAnnotation(HandlerChain.class);
/*     */             }
/*     */           });
/* 334 */       if (handlerChain != null) {
/* 335 */         this.handlerConfigurator = new HandlerConfigurator.AnnotationConfigurator(this);
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
/*     */   private WSDLModel parseWSDL(URL wsdlDocumentLocation, Source wsdlSource, Class serviceClass) {
/*     */     try {
/* 348 */       return RuntimeWSDLParser.parse(wsdlDocumentLocation, wsdlSource, createCatalogResolver(), true, 
/* 349 */           getContainer(), serviceClass, (WSDLParserExtension[])ServiceFinder.find(WSDLParserExtension.class).toArray());
/* 350 */     } catch (IOException e) {
/* 351 */       throw new WebServiceException(e);
/* 352 */     } catch (XMLStreamException e) {
/* 353 */       throw new WebServiceException(e);
/* 354 */     } catch (SAXException e) {
/* 355 */       throw new WebServiceException(e);
/* 356 */     } catch (ServiceConfigurationError e) {
/* 357 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected EntityResolver createCatalogResolver() {
/* 362 */     return XmlUtil.createDefaultCatalogResolver();
/*     */   }
/*     */   
/*     */   public Executor getExecutor() {
/* 366 */     return this.executor;
/*     */   }
/*     */   
/*     */   public void setExecutor(Executor executor) {
/* 370 */     this.executor = executor;
/*     */   }
/*     */   
/*     */   public HandlerResolver getHandlerResolver() {
/* 374 */     return this.handlerConfigurator.getResolver();
/*     */   }
/*     */   
/*     */   final HandlerConfigurator getHandlerConfigurator() {
/* 378 */     return this.handlerConfigurator;
/*     */   }
/*     */   
/*     */   public void setHandlerResolver(HandlerResolver resolver) {
/* 382 */     this.handlerConfigurator = new HandlerConfigurator.HandlerResolverImpl(resolver);
/*     */   }
/*     */   
/*     */   public <T> T getPort(QName portName, Class<T> portInterface) throws WebServiceException {
/* 386 */     return getPort(portName, portInterface, EMPTY_FEATURES);
/*     */   }
/*     */   
/*     */   public <T> T getPort(QName portName, Class<T> portInterface, WebServiceFeature... features) {
/* 390 */     if (portName == null || portInterface == null)
/* 391 */       throw new IllegalArgumentException(); 
/* 392 */     WSDLService tWsdlService = this.wsdlService;
/* 393 */     if (tWsdlService == null) {
/*     */ 
/*     */       
/* 396 */       tWsdlService = getWSDLModelfromSEI(portInterface);
/*     */       
/* 398 */       if (tWsdlService == null) {
/* 399 */         throw new WebServiceException(ProviderApiMessages.NO_WSDL_NO_PORT(portInterface.getName()));
/*     */       }
/*     */     } 
/*     */     
/* 403 */     WSDLPort portModel = getPortModel(tWsdlService, portName);
/* 404 */     return getPort(portModel.getEPR(), portName, portInterface, new WebServiceFeatureList(features));
/*     */   }
/*     */   
/*     */   public <T> T getPort(EndpointReference epr, Class<T> portInterface, WebServiceFeature... features) {
/* 408 */     return getPort(WSEndpointReference.create(epr), portInterface, features);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T getPort(WSEndpointReference wsepr, Class<T> portInterface, WebServiceFeature... features) {
/* 413 */     WebServiceFeatureList featureList = new WebServiceFeatureList(features);
/* 414 */     QName portTypeName = RuntimeModeler.getPortTypeName(portInterface, getMetadadaReader(featureList, portInterface.getClassLoader()));
/*     */     
/* 416 */     QName portName = getPortNameFromEPR(wsepr, portTypeName);
/* 417 */     return getPort(wsepr, portName, portInterface, featureList);
/*     */   }
/*     */ 
/*     */   
/*     */   protected <T> T getPort(WSEndpointReference wsepr, QName portName, Class<T> portInterface, WebServiceFeatureList features) {
/* 422 */     ComponentFeature cf = (ComponentFeature)features.get(ComponentFeature.class);
/* 423 */     if (cf != null && !ComponentFeature.Target.STUB.equals(cf.getTarget())) {
/* 424 */       throw new IllegalArgumentException();
/*     */     }
/* 426 */     ComponentsFeature csf = (ComponentsFeature)features.get(ComponentsFeature.class);
/* 427 */     if (csf != null)
/* 428 */       for (ComponentFeature cfi : csf.getComponentFeatures()) {
/* 429 */         if (!ComponentFeature.Target.STUB.equals(cfi.getTarget())) {
/* 430 */           throw new IllegalArgumentException();
/*     */         }
/*     */       }  
/* 433 */     features.addAll((Iterable)this.features);
/*     */     
/* 435 */     SEIPortInfo spi = addSEI(portName, portInterface, features);
/* 436 */     return createEndpointIFBaseProxy(wsepr, portName, portInterface, features, spi);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getPort(Class<T> portInterface, WebServiceFeature... features) {
/* 442 */     QName portTypeName = RuntimeModeler.getPortTypeName(portInterface, getMetadadaReader(new WebServiceFeatureList(features), portInterface.getClassLoader()));
/* 443 */     WSDLService tmpWsdlService = this.wsdlService;
/* 444 */     if (tmpWsdlService == null) {
/*     */ 
/*     */       
/* 447 */       tmpWsdlService = getWSDLModelfromSEI(portInterface);
/*     */       
/* 449 */       if (tmpWsdlService == null) {
/* 450 */         throw new WebServiceException(ProviderApiMessages.NO_WSDL_NO_PORT(portInterface.getName()));
/*     */       }
/*     */     } 
/*     */     
/* 454 */     WSDLPort port = tmpWsdlService.getMatchingPort(portTypeName);
/* 455 */     if (port == null) {
/* 456 */       throw new WebServiceException(ClientMessages.UNDEFINED_PORT_TYPE(portTypeName));
/*     */     }
/* 458 */     QName portName = port.getName();
/* 459 */     return getPort(portName, portInterface, features);
/*     */   }
/*     */   
/*     */   public <T> T getPort(Class<T> portInterface) throws WebServiceException {
/* 463 */     return getPort(portInterface, EMPTY_FEATURES);
/*     */   }
/*     */   
/*     */   public void addPort(QName portName, String bindingId, String endpointAddress) throws WebServiceException {
/* 467 */     if (!this.ports.containsKey(portName)) {
/* 468 */       BindingID bid = (bindingId == null) ? (BindingID)BindingID.SOAP11_HTTP : BindingID.parse(bindingId);
/* 469 */       this.ports.put(portName, new PortInfo(this, (endpointAddress == null) ? null : 
/*     */             
/* 471 */             EndpointAddress.create(endpointAddress), portName, bid));
/*     */     } else {
/* 473 */       throw new WebServiceException(DispatchMessages.DUPLICATE_PORT(portName.toString()));
/*     */     } 
/*     */   }
/*     */   
/*     */   public <T> Dispatch<T> createDispatch(QName portName, Class<T> aClass, Service.Mode mode) throws WebServiceException {
/* 478 */     return createDispatch(portName, aClass, mode, EMPTY_FEATURES);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> Dispatch<T> createDispatch(QName portName, WSEndpointReference wsepr, Class<T> aClass, Service.Mode mode, WebServiceFeature... features) {
/* 483 */     return createDispatch(portName, wsepr, aClass, mode, new WebServiceFeatureList(features));
/*     */   }
/*     */   
/*     */   public <T> Dispatch<T> createDispatch(QName portName, WSEndpointReference wsepr, Class<T> aClass, Service.Mode mode, WebServiceFeatureList features) {
/* 487 */     PortInfo port = safeGetPort(portName);
/*     */     
/* 489 */     ComponentFeature cf = (ComponentFeature)features.get(ComponentFeature.class);
/* 490 */     if (cf != null && !ComponentFeature.Target.STUB.equals(cf.getTarget())) {
/* 491 */       throw new IllegalArgumentException();
/*     */     }
/* 493 */     ComponentsFeature csf = (ComponentsFeature)features.get(ComponentsFeature.class);
/* 494 */     if (csf != null)
/* 495 */       for (ComponentFeature cfi : csf.getComponentFeatures()) {
/* 496 */         if (!ComponentFeature.Target.STUB.equals(cfi.getTarget())) {
/* 497 */           throw new IllegalArgumentException();
/*     */         }
/*     */       }  
/* 500 */     features.addAll((Iterable)this.features);
/*     */     
/* 502 */     BindingImpl binding = port.createBinding(features, null, null);
/* 503 */     binding.setMode(mode);
/* 504 */     Dispatch<T> dispatch = Stubs.createDispatch(port, this, (WSBinding)binding, aClass, mode, wsepr);
/* 505 */     this.serviceInterceptor.postCreateDispatch((WSBindingProvider)dispatch);
/* 506 */     return dispatch;
/*     */   }
/*     */   
/*     */   public <T> Dispatch<T> createDispatch(QName portName, Class<T> aClass, Service.Mode mode, WebServiceFeature... features) {
/* 510 */     return createDispatch(portName, aClass, mode, new WebServiceFeatureList(features));
/*     */   }
/*     */   
/*     */   public <T> Dispatch<T> createDispatch(QName portName, Class<T> aClass, Service.Mode mode, WebServiceFeatureList features) {
/* 514 */     WSEndpointReference wsepr = null;
/* 515 */     boolean isAddressingEnabled = false;
/* 516 */     AddressingFeature af = (AddressingFeature)features.get(AddressingFeature.class);
/* 517 */     if (af == null) {
/* 518 */       af = (AddressingFeature)this.features.get(AddressingFeature.class);
/*     */     }
/* 520 */     if (af != null && af.isEnabled())
/* 521 */       isAddressingEnabled = true; 
/* 522 */     MemberSubmissionAddressingFeature msa = (MemberSubmissionAddressingFeature)features.get(MemberSubmissionAddressingFeature.class);
/* 523 */     if (msa == null) {
/* 524 */       msa = (MemberSubmissionAddressingFeature)this.features.get(MemberSubmissionAddressingFeature.class);
/*     */     }
/* 526 */     if (msa != null && msa.isEnabled())
/* 527 */       isAddressingEnabled = true; 
/* 528 */     if (isAddressingEnabled && this.wsdlService != null && this.wsdlService.get(portName) != null) {
/* 529 */       wsepr = this.wsdlService.get(portName).getEPR();
/*     */     }
/* 531 */     return createDispatch(portName, wsepr, aClass, mode, features);
/*     */   }
/*     */   
/*     */   public <T> Dispatch<T> createDispatch(EndpointReference endpointReference, Class<T> type, Service.Mode mode, WebServiceFeature... features) {
/* 535 */     WSEndpointReference wsepr = new WSEndpointReference(endpointReference);
/* 536 */     QName portName = addPortEpr(wsepr);
/* 537 */     return createDispatch(portName, wsepr, type, mode, features);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public PortInfo safeGetPort(QName portName) {
/* 546 */     PortInfo port = this.ports.get(portName);
/* 547 */     if (port == null) {
/* 548 */       throw new WebServiceException(ClientMessages.INVALID_PORT_NAME(portName, buildNameList(this.ports.keySet())));
/*     */     }
/* 550 */     return port;
/*     */   }
/*     */   
/*     */   private StringBuilder buildNameList(Collection<QName> names) {
/* 554 */     StringBuilder sb = new StringBuilder();
/* 555 */     for (QName qn : names) {
/* 556 */       if (sb.length() > 0) sb.append(','); 
/* 557 */       sb.append(qn);
/*     */     } 
/* 559 */     return sb;
/*     */   }
/*     */   
/*     */   public EndpointAddress getEndpointAddress(QName qName) {
/* 563 */     PortInfo p = this.ports.get(qName);
/* 564 */     return (p != null) ? p.targetEndpoint : null;
/*     */   }
/*     */   
/*     */   public Dispatch<Object> createDispatch(QName portName, JAXBContext jaxbContext, Service.Mode mode) throws WebServiceException {
/* 568 */     return createDispatch(portName, jaxbContext, mode, EMPTY_FEATURES);
/*     */   }
/*     */ 
/*     */   
/*     */   public Dispatch<Object> createDispatch(QName portName, WSEndpointReference wsepr, JAXBContext jaxbContext, Service.Mode mode, WebServiceFeature... features) {
/* 573 */     return createDispatch(portName, wsepr, jaxbContext, mode, new WebServiceFeatureList(features));
/*     */   }
/*     */   
/*     */   protected Dispatch<Object> createDispatch(QName portName, WSEndpointReference wsepr, JAXBContext jaxbContext, Service.Mode mode, WebServiceFeatureList features) {
/* 577 */     PortInfo port = safeGetPort(portName);
/*     */     
/* 579 */     ComponentFeature cf = (ComponentFeature)features.get(ComponentFeature.class);
/* 580 */     if (cf != null && !ComponentFeature.Target.STUB.equals(cf.getTarget())) {
/* 581 */       throw new IllegalArgumentException();
/*     */     }
/* 583 */     ComponentsFeature csf = (ComponentsFeature)features.get(ComponentsFeature.class);
/* 584 */     if (csf != null)
/* 585 */       for (ComponentFeature cfi : csf.getComponentFeatures()) {
/* 586 */         if (!ComponentFeature.Target.STUB.equals(cfi.getTarget())) {
/* 587 */           throw new IllegalArgumentException();
/*     */         }
/*     */       }  
/* 590 */     features.addAll((Iterable)this.features);
/*     */     
/* 592 */     BindingImpl binding = port.createBinding(features, null, null);
/* 593 */     binding.setMode(mode);
/* 594 */     Dispatch<Object> dispatch = Stubs.createJAXBDispatch(port, (WSBinding)binding, jaxbContext, mode, wsepr);
/*     */     
/* 596 */     this.serviceInterceptor.postCreateDispatch((WSBindingProvider)dispatch);
/* 597 */     return dispatch;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Container getContainer() {
/* 602 */     return this.container;
/*     */   }
/*     */   
/*     */   public Dispatch<Object> createDispatch(QName portName, JAXBContext jaxbContext, Service.Mode mode, WebServiceFeature... webServiceFeatures) {
/* 606 */     return createDispatch(portName, jaxbContext, mode, new WebServiceFeatureList(webServiceFeatures));
/*     */   }
/*     */   
/*     */   protected Dispatch<Object> createDispatch(QName portName, JAXBContext jaxbContext, Service.Mode mode, WebServiceFeatureList features) {
/* 610 */     WSEndpointReference wsepr = null;
/* 611 */     boolean isAddressingEnabled = false;
/* 612 */     AddressingFeature af = (AddressingFeature)features.get(AddressingFeature.class);
/* 613 */     if (af == null) {
/* 614 */       af = (AddressingFeature)this.features.get(AddressingFeature.class);
/*     */     }
/* 616 */     if (af != null && af.isEnabled())
/* 617 */       isAddressingEnabled = true; 
/* 618 */     MemberSubmissionAddressingFeature msa = (MemberSubmissionAddressingFeature)features.get(MemberSubmissionAddressingFeature.class);
/* 619 */     if (msa == null) {
/* 620 */       msa = (MemberSubmissionAddressingFeature)this.features.get(MemberSubmissionAddressingFeature.class);
/*     */     }
/* 622 */     if (msa != null && msa.isEnabled())
/* 623 */       isAddressingEnabled = true; 
/* 624 */     if (isAddressingEnabled && this.wsdlService != null && this.wsdlService.get(portName) != null) {
/* 625 */       wsepr = this.wsdlService.get(portName).getEPR();
/*     */     }
/* 627 */     return createDispatch(portName, wsepr, jaxbContext, mode, features);
/*     */   }
/*     */   
/*     */   public Dispatch<Object> createDispatch(EndpointReference endpointReference, JAXBContext context, Service.Mode mode, WebServiceFeature... features) {
/* 631 */     WSEndpointReference wsepr = new WSEndpointReference(endpointReference);
/* 632 */     QName portName = addPortEpr(wsepr);
/* 633 */     return createDispatch(portName, wsepr, context, mode, features);
/*     */   }
/*     */   
/*     */   private QName addPortEpr(WSEndpointReference wsepr) {
/* 637 */     if (wsepr == null)
/* 638 */       throw new WebServiceException(ProviderApiMessages.NULL_EPR()); 
/* 639 */     QName eprPortName = getPortNameFromEPR(wsepr, (QName)null);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 644 */     PortInfo portInfo = new PortInfo(this, (wsepr.getAddress() == null) ? null : EndpointAddress.create(wsepr.getAddress()), eprPortName, getPortModel(this.wsdlService, eprPortName).getBinding().getBindingId());
/* 645 */     if (!this.ports.containsKey(eprPortName)) {
/* 646 */       this.ports.put(eprPortName, portInfo);
/*     */     }
/*     */     
/* 649 */     return eprPortName;
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
/*     */   private QName getPortNameFromEPR(@NotNull WSEndpointReference wsepr, @Nullable QName portTypeName) {
/* 666 */     WSEndpointReference.Metadata metadata = wsepr.getMetaData();
/* 667 */     QName eprServiceName = metadata.getServiceName();
/* 668 */     QName eprPortName = metadata.getPortName();
/* 669 */     if (eprServiceName != null && !eprServiceName.equals(this.serviceName)) {
/* 670 */       throw new WebServiceException("EndpointReference WSDL ServiceName differs from Service Instance WSDL Service QName.\n The two Service QNames must match");
/*     */     }
/*     */     
/* 673 */     if (this.wsdlService == null) {
/* 674 */       Source eprWsdlSource = metadata.getWsdlSource();
/* 675 */       if (eprWsdlSource == null) {
/* 676 */         throw new WebServiceException(ProviderApiMessages.NULL_WSDL());
/*     */       }
/*     */       try {
/* 679 */         WSDLModel eprWsdlMdl = parseWSDL(new URL(wsepr.getAddress()), eprWsdlSource, (Class)null);
/* 680 */         this.wsdlService = eprWsdlMdl.getService(this.serviceName);
/* 681 */         if (this.wsdlService == null)
/* 682 */           throw new WebServiceException(ClientMessages.INVALID_SERVICE_NAME(this.serviceName, 
/* 683 */                 buildNameList(eprWsdlMdl.getServices().keySet()))); 
/* 684 */       } catch (MalformedURLException e) {
/* 685 */         throw new WebServiceException(ClientMessages.INVALID_ADDRESS(wsepr.getAddress()));
/*     */       } 
/*     */     } 
/* 688 */     QName portName = eprPortName;
/*     */     
/* 690 */     if (portName == null && portTypeName != null) {
/*     */       
/* 692 */       WSDLPort port = this.wsdlService.getMatchingPort(portTypeName);
/* 693 */       if (port == null)
/* 694 */         throw new WebServiceException(ClientMessages.UNDEFINED_PORT_TYPE(portTypeName)); 
/* 695 */       portName = port.getName();
/*     */     } 
/* 697 */     if (portName == null)
/* 698 */       throw new WebServiceException(ProviderApiMessages.NULL_PORTNAME()); 
/* 699 */     if (this.wsdlService.get(portName) == null) {
/* 700 */       throw new WebServiceException(ClientMessages.INVALID_EPR_PORT_NAME(portName, buildWsdlPortNames()));
/*     */     }
/* 702 */     return portName;
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
/*     */   private <T> T createProxy(final Class<T> portInterface, final InvocationHandler pis) {
/* 715 */     final ClassLoader loader = getDelegatingLoader(portInterface.getClassLoader(), WSServiceDelegate.class
/* 716 */         .getClassLoader());
/*     */ 
/*     */     
/* 719 */     RuntimePermission perm = new RuntimePermission("accessClassInPackage.com.sun.xml.internal.*");
/* 720 */     PermissionCollection perms = perm.newPermissionCollection();
/* 721 */     perms.add(perm);
/*     */     
/* 723 */     return AccessController.doPrivileged(new PrivilegedAction<T>()
/*     */         {
/*     */           public T run()
/*     */           {
/* 727 */             Object proxy = Proxy.newProxyInstance(loader, new Class[] { this.val$portInterface, WSBindingProvider.class, Closeable.class }, pis);
/*     */             
/* 729 */             return portInterface.cast(proxy);
/*     */           }
/*     */         }new AccessControlContext(new ProtectionDomain[] { new ProtectionDomain(null, perms) }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private WSDLService getWSDLModelfromSEI(final Class sei) {
/* 740 */     WebService ws = AccessController.<WebService>doPrivileged(new PrivilegedAction<WebService>() {
/*     */           public WebService run() {
/* 742 */             return (WebService)sei.getAnnotation(WebService.class);
/*     */           }
/*     */         });
/* 745 */     if (ws == null || ws.wsdlLocation().equals(""))
/* 746 */       return null; 
/* 747 */     String wsdlLocation = ws.wsdlLocation();
/* 748 */     wsdlLocation = JAXWSUtils.absolutize(JAXWSUtils.getFileOrURLName(wsdlLocation));
/* 749 */     Source wsdl = new StreamSource(wsdlLocation);
/* 750 */     WSDLService service = null;
/*     */     
/*     */     try {
/* 753 */       URL url = (wsdl.getSystemId() == null) ? null : new URL(wsdl.getSystemId());
/* 754 */       WSDLModel model = parseWSDL(url, wsdl, sei);
/* 755 */       service = model.getService(this.serviceName);
/* 756 */       if (service == null)
/* 757 */         throw new WebServiceException(
/* 758 */             ClientMessages.INVALID_SERVICE_NAME(this.serviceName, 
/* 759 */               buildNameList(model.getServices().keySet()))); 
/* 760 */     } catch (MalformedURLException e) {
/* 761 */       throw new WebServiceException(ClientMessages.INVALID_WSDL_URL(wsdl.getSystemId()));
/*     */     } 
/* 763 */     return service;
/*     */   }
/*     */   
/*     */   public QName getServiceName() {
/* 767 */     return this.serviceName;
/*     */   }
/*     */   
/*     */   public Class getServiceClass() {
/* 771 */     return this.serviceClass;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<QName> getPorts() throws WebServiceException {
/* 777 */     return this.ports.keySet().iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public URL getWSDLDocumentLocation() {
/* 782 */     if (this.wsdlService == null) return null; 
/*     */     try {
/* 784 */       return new URL(this.wsdlService.getParent().getLocation().getSystemId());
/* 785 */     } catch (MalformedURLException e) {
/* 786 */       throw new AssertionError(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private <T> T createEndpointIFBaseProxy(@Nullable WSEndpointReference epr, QName portName, Class<T> portInterface, WebServiceFeatureList webServiceFeatures, SEIPortInfo eif) {
/* 793 */     if (this.wsdlService == null) {
/* 794 */       throw new WebServiceException(ClientMessages.INVALID_SERVICE_NO_WSDL(this.serviceName));
/*     */     }
/*     */     
/* 797 */     if (this.wsdlService.get(portName) == null) {
/* 798 */       throw new WebServiceException(
/* 799 */           ClientMessages.INVALID_PORT_NAME(portName, buildWsdlPortNames()));
/*     */     }
/*     */     
/* 802 */     BindingImpl binding = eif.createBinding(webServiceFeatures, portInterface);
/* 803 */     InvocationHandler pis = getStubHandler(binding, eif, epr);
/*     */     
/* 805 */     T proxy = createProxy(portInterface, pis);
/*     */     
/* 807 */     if (this.serviceInterceptor != null) {
/* 808 */       this.serviceInterceptor.postCreateProxy((WSBindingProvider)proxy, portInterface);
/*     */     }
/* 810 */     return proxy;
/*     */   }
/*     */   
/*     */   protected InvocationHandler getStubHandler(BindingImpl binding, SEIPortInfo eif, @Nullable WSEndpointReference epr) {
/* 814 */     return (InvocationHandler)new SEIStub(eif, binding, eif.model, epr);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private StringBuilder buildWsdlPortNames() {
/* 821 */     Set<QName> wsdlPortNames = new HashSet<>();
/* 822 */     for (WSDLPort port : this.wsdlService.getPorts()) {
/* 823 */       wsdlPortNames.add(port.getName());
/*     */     }
/* 825 */     return buildNameList(wsdlPortNames);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public WSDLPort getPortModel(WSDLService wsdlService, QName portName) {
/* 834 */     WSDLPort port = wsdlService.get(portName);
/* 835 */     if (port == null)
/* 836 */       throw new WebServiceException(
/* 837 */           ClientMessages.INVALID_PORT_NAME(portName, buildWsdlPortNames())); 
/* 838 */     return port;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SEIPortInfo addSEI(QName portName, Class portInterface, WebServiceFeatureList features) throws WebServiceException {
/* 847 */     boolean ownModel = useOwnSEIModel(features);
/* 848 */     if (ownModel)
/*     */     {
/* 850 */       return createSEIPortInfo(portName, portInterface, features);
/*     */     }
/*     */     
/* 853 */     SEIPortInfo spi = this.seiContext.get(portName);
/* 854 */     if (spi == null) {
/* 855 */       spi = createSEIPortInfo(portName, portInterface, features);
/* 856 */       this.seiContext.put(spi.portName, spi);
/* 857 */       this.ports.put(spi.portName, spi);
/*     */     } 
/* 859 */     return spi;
/*     */   }
/*     */   
/*     */   public SEIModel buildRuntimeModel(QName serviceName, QName portName, Class portInterface, WSDLPort wsdlPort, WebServiceFeatureList features) {
/* 863 */     DatabindingFactory fac = DatabindingFactory.newInstance();
/* 864 */     DatabindingConfig config = new DatabindingConfig();
/* 865 */     config.setContractClass(portInterface);
/* 866 */     config.getMappingInfo().setServiceName(serviceName);
/* 867 */     config.setWsdlPort(wsdlPort);
/* 868 */     config.setFeatures((Iterable)features);
/* 869 */     config.setClassLoader(portInterface.getClassLoader());
/* 870 */     config.getMappingInfo().setPortName(portName);
/* 871 */     config.setWsdlURL(this.wsdlURL);
/*     */     
/* 873 */     config.setMetadataReader(getMetadadaReader(features, portInterface.getClassLoader()));
/*     */     
/* 875 */     DatabindingImpl rt = (DatabindingImpl)fac.createRuntime(config);
/*     */     
/* 877 */     return rt.getModel();
/*     */   }
/*     */   
/*     */   private MetadataReader getMetadadaReader(WebServiceFeatureList features, ClassLoader classLoader) {
/* 881 */     if (features == null) return null;
/*     */     
/* 883 */     ExternalMetadataFeature ef = (ExternalMetadataFeature)features.get(ExternalMetadataFeature.class);
/*     */     
/* 885 */     if (ef != null)
/* 886 */       return ef.getMetadataReader(classLoader, false); 
/* 887 */     return null;
/*     */   }
/*     */   
/*     */   private SEIPortInfo createSEIPortInfo(QName portName, Class portInterface, WebServiceFeatureList features) {
/* 891 */     WSDLPort wsdlPort = getPortModel(this.wsdlService, portName);
/* 892 */     SEIModel model = buildRuntimeModel(this.serviceName, portName, portInterface, wsdlPort, features);
/*     */     
/* 894 */     return new SEIPortInfo(this, portInterface, (SOAPSEIModel)model, wsdlPort);
/*     */   }
/*     */   
/*     */   private boolean useOwnSEIModel(WebServiceFeatureList features) {
/* 898 */     return features.contains(UsesJAXBContextFeature.class);
/*     */   }
/*     */   
/*     */   public WSDLService getWsdlService() {
/* 902 */     return this.wsdlService;
/*     */   }
/*     */   
/*     */   static class DaemonThreadFactory
/*     */     implements ThreadFactory {
/*     */     public Thread newThread(Runnable r) {
/* 908 */       Thread daemonThread = new Thread(r);
/* 909 */       daemonThread.setDaemon(Boolean.TRUE.booleanValue());
/* 910 */       return daemonThread;
/*     */     }
/*     */   }
/*     */   
/* 914 */   protected static final WebServiceFeature[] EMPTY_FEATURES = new WebServiceFeature[0];
/*     */   
/*     */   private static ClassLoader getDelegatingLoader(ClassLoader loader1, ClassLoader loader2) {
/* 917 */     if (loader1 == null) return loader2; 
/* 918 */     if (loader2 == null) return loader1; 
/* 919 */     return new DelegatingLoader(loader1, loader2);
/*     */   }
/*     */   
/*     */   private static final class DelegatingLoader
/*     */     extends ClassLoader {
/*     */     private final ClassLoader loader;
/*     */     
/*     */     public int hashCode() {
/* 927 */       int prime = 31;
/* 928 */       int result = 1;
/*     */       
/* 930 */       result = 31 * result + ((this.loader == null) ? 0 : this.loader.hashCode());
/*     */       
/* 932 */       result = 31 * result + ((getParent() == null) ? 0 : getParent().hashCode());
/* 933 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 938 */       if (this == obj)
/* 939 */         return true; 
/* 940 */       if (obj == null)
/* 941 */         return false; 
/* 942 */       if (getClass() != obj.getClass())
/* 943 */         return false; 
/* 944 */       DelegatingLoader other = (DelegatingLoader)obj;
/* 945 */       if (this.loader == null) {
/* 946 */         if (other.loader != null)
/* 947 */           return false; 
/* 948 */       } else if (!this.loader.equals(other.loader)) {
/* 949 */         return false;
/* 950 */       }  if (getParent() == null) {
/* 951 */         if (other.getParent() != null)
/* 952 */           return false; 
/* 953 */       } else if (!getParent().equals(other.getParent())) {
/* 954 */         return false;
/* 955 */       }  return true;
/*     */     }
/*     */     
/*     */     DelegatingLoader(ClassLoader loader1, ClassLoader loader2) {
/* 959 */       super(loader2);
/* 960 */       this.loader = loader1;
/*     */     }
/*     */     
/*     */     protected Class findClass(String name) throws ClassNotFoundException {
/* 964 */       return this.loader.loadClass(name);
/*     */     }
/*     */     
/*     */     protected URL findResource(String name) {
/* 968 */       return this.loader.getResource(name);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/WSServiceDelegate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */