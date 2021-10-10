/*     */ package com.sun.xml.internal.ws.server;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.message.PropertySet;
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.org.glassfish.gmbal.ManagedObjectManager;
/*     */ import com.sun.xml.internal.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.internal.ws.addressing.EPRSDDocumentFilter;
/*     */ import com.sun.xml.internal.ws.addressing.WSEPRExtension;
/*     */ import com.sun.xml.internal.ws.api.Component;
/*     */ import com.sun.xml.internal.ws.api.ComponentFeature;
/*     */ import com.sun.xml.internal.ws.api.ComponentsFeature;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.api.pipe.Codec;
/*     */ import com.sun.xml.internal.ws.api.pipe.Engine;
/*     */ import com.sun.xml.internal.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptor;
/*     */ import com.sun.xml.internal.ws.api.pipe.ServerPipeAssemblerContext;
/*     */ import com.sun.xml.internal.ws.api.pipe.ServerTubeAssemblerContext;
/*     */ import com.sun.xml.internal.ws.api.pipe.SyncStartForAsyncFeature;
/*     */ import com.sun.xml.internal.ws.api.pipe.ThrowableContainerPropertySet;
/*     */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*     */ import com.sun.xml.internal.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.internal.ws.api.pipe.TubelineAssembler;
/*     */ import com.sun.xml.internal.ws.api.pipe.TubelineAssemblerFactory;
/*     */ import com.sun.xml.internal.ws.api.server.Container;
/*     */ import com.sun.xml.internal.ws.api.server.ContainerResolver;
/*     */ import com.sun.xml.internal.ws.api.server.EndpointAwareCodec;
/*     */ import com.sun.xml.internal.ws.api.server.EndpointComponent;
/*     */ import com.sun.xml.internal.ws.api.server.EndpointReferenceExtensionContributor;
/*     */ import com.sun.xml.internal.ws.api.server.LazyMOMProvider;
/*     */ import com.sun.xml.internal.ws.api.server.SDDocumentFilter;
/*     */ import com.sun.xml.internal.ws.api.server.ServiceDefinition;
/*     */ import com.sun.xml.internal.ws.api.server.TransportBackChannel;
/*     */ import com.sun.xml.internal.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.internal.ws.api.server.WebServiceContextDelegate;
/*     */ import com.sun.xml.internal.ws.binding.BindingImpl;
/*     */ import com.sun.xml.internal.ws.fault.SOAPFaultBuilder;
/*     */ import com.sun.xml.internal.ws.model.wsdl.WSDLDirectProperties;
/*     */ import com.sun.xml.internal.ws.model.wsdl.WSDLPortProperties;
/*     */ import com.sun.xml.internal.ws.model.wsdl.WSDLProperties;
/*     */ import com.sun.xml.internal.ws.policy.PolicyMap;
/*     */ import com.sun.xml.internal.ws.resources.HandlerMessages;
/*     */ import com.sun.xml.internal.ws.util.Pool;
/*     */ import com.sun.xml.internal.ws.util.ServiceFinder;
/*     */ import com.sun.xml.internal.ws.wsdl.OperationDispatcher;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CopyOnWriteArraySet;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.annotation.PreDestroy;
/*     */ import javax.management.ObjectName;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.handler.Handler;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSEndpointImpl<T>
/*     */   extends WSEndpoint<T>
/*     */   implements LazyMOMProvider.WSEndpointScopeChangeListener
/*     */ {
/*  82 */   private static final Logger logger = Logger.getLogger("com.sun.xml.internal.ws.server.endpoint");
/*     */   
/*     */   @NotNull
/*     */   private final QName serviceName;
/*     */   
/*     */   @NotNull
/*     */   private final QName portName;
/*     */   
/*     */   protected final WSBinding binding;
/*     */   
/*     */   private final SEIModel seiModel;
/*     */   
/*     */   @NotNull
/*     */   private final Container container;
/*     */   
/*     */   private final WSDLPort port;
/*     */   
/*     */   protected final Tube masterTubeline;
/*     */   private final ServiceDefinitionImpl serviceDef;
/* 101 */   private final Object managedObjectManagerLock = new Object(); private final SOAPVersion soapVersion; private final Engine engine; @NotNull private final Codec masterCodec; @NotNull private final PolicyMap endpointPolicy; private final Pool<Tube> tubePool; private final OperationDispatcher operationDispatcher; @NotNull
/* 102 */   private ManagedObjectManager managedObjectManager; private boolean managedObjectManagerClosed = false; private LazyMOMProvider.Scope lazyMOMProviderScope = LazyMOMProvider.Scope.STANDALONE;
/*     */   @NotNull
/*     */   private final ServerTubeAssemblerContext context;
/* 105 */   private Map<QName, WSEndpointReference.EPRExtension> endpointReferenceExtensions = new HashMap<>();
/*     */ 
/*     */   
/*     */   private boolean disposed;
/*     */ 
/*     */   
/*     */   private final Class<T> implementationClass;
/*     */   
/*     */   @NotNull
/*     */   private final WSDLProperties wsdlProperties;
/*     */   
/* 116 */   private final Set<Component> componentRegistry = new CopyOnWriteArraySet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WSEndpointImpl(@NotNull QName serviceName, @NotNull QName portName, WSBinding binding, Container container, SEIModel seiModel, WSDLPort port, Class<T> implementationClass, @Nullable ServiceDefinitionImpl serviceDef, EndpointAwareTube terminalTube, boolean isSynchronous, PolicyMap endpointPolicy) {
/* 124 */     this.serviceName = serviceName;
/* 125 */     this.portName = portName;
/* 126 */     this.binding = binding;
/* 127 */     this.soapVersion = binding.getSOAPVersion();
/* 128 */     this.container = container;
/* 129 */     this.port = port;
/* 130 */     this.implementationClass = implementationClass;
/* 131 */     this.serviceDef = serviceDef;
/* 132 */     this.seiModel = seiModel;
/* 133 */     this.endpointPolicy = endpointPolicy;
/*     */     
/* 135 */     LazyMOMProvider.INSTANCE.registerEndpoint(this);
/* 136 */     initManagedObjectManager();
/*     */     
/* 138 */     if (serviceDef != null) {
/* 139 */       serviceDef.setOwner(this);
/*     */     }
/*     */     
/* 142 */     ComponentFeature cf = (ComponentFeature)binding.getFeature(ComponentFeature.class);
/* 143 */     if (cf != null) {
/* 144 */       switch (cf.getTarget()) {
/*     */         case GLASSFISH_NO_JMX:
/* 146 */           this.componentRegistry.add(cf.getComponent());
/*     */           break;
/*     */         case null:
/* 149 */           container.getComponents().add(cf.getComponent());
/*     */           break;
/*     */         default:
/* 152 */           throw new IllegalArgumentException();
/*     */       } 
/*     */     }
/* 155 */     ComponentsFeature csf = (ComponentsFeature)binding.getFeature(ComponentsFeature.class);
/* 156 */     if (csf != null) {
/* 157 */       for (ComponentFeature cfi : csf.getComponentFeatures()) {
/* 158 */         switch (cfi.getTarget()) {
/*     */           case GLASSFISH_NO_JMX:
/* 160 */             this.componentRegistry.add(cfi.getComponent());
/*     */             continue;
/*     */           case null:
/* 163 */             container.getComponents().add(cfi.getComponent());
/*     */             continue;
/*     */         } 
/* 166 */         throw new IllegalArgumentException();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 171 */     TubelineAssembler assembler = TubelineAssemblerFactory.create(
/* 172 */         Thread.currentThread().getContextClassLoader(), binding.getBindingId(), container);
/* 173 */     assert assembler != null;
/*     */     
/* 175 */     this.operationDispatcher = (port == null) ? null : new OperationDispatcher(port, binding, seiModel);
/*     */     
/* 177 */     this.context = createServerTubeAssemblerContext(terminalTube, isSynchronous);
/* 178 */     this.masterTubeline = assembler.createServer(this.context);
/*     */     
/* 180 */     Codec c = this.context.getCodec();
/* 181 */     if (c instanceof EndpointAwareCodec) {
/*     */       
/* 183 */       c = c.copy();
/* 184 */       ((EndpointAwareCodec)c).setEndpoint(this);
/*     */     } 
/* 186 */     this.masterCodec = c;
/*     */     
/* 188 */     this.tubePool = (Pool<Tube>)new Pool.TubePool(this.masterTubeline);
/* 189 */     terminalTube.setEndpoint(this);
/* 190 */     this.engine = new Engine(toString(), container);
/* 191 */     this.wsdlProperties = (port == null) ? (WSDLProperties)new WSDLDirectProperties(serviceName, portName, seiModel) : (WSDLProperties)new WSDLPortProperties(port, seiModel);
/*     */     
/* 193 */     Map<QName, WSEndpointReference.EPRExtension> eprExtensions = new HashMap<>();
/*     */     try {
/* 195 */       if (port != null) {
/*     */         
/* 197 */         WSEndpointReference wsdlEpr = port.getEPR();
/* 198 */         if (wsdlEpr != null) {
/* 199 */           for (WSEndpointReference.EPRExtension extnEl : wsdlEpr.getEPRExtensions()) {
/* 200 */             eprExtensions.put(extnEl.getQName(), extnEl);
/*     */           }
/*     */         }
/*     */       } 
/*     */       
/* 205 */       EndpointReferenceExtensionContributor[] eprExtnContributors = (EndpointReferenceExtensionContributor[])ServiceFinder.find(EndpointReferenceExtensionContributor.class).toArray();
/* 206 */       for (EndpointReferenceExtensionContributor eprExtnContributor : eprExtnContributors) {
/* 207 */         WSEndpointReference.EPRExtension wsdlEPRExtn = eprExtensions.remove(eprExtnContributor.getQName());
/* 208 */         WSEndpointReference.EPRExtension endpointEprExtn = eprExtnContributor.getEPRExtension(this, wsdlEPRExtn);
/* 209 */         if (endpointEprExtn != null) {
/* 210 */           eprExtensions.put(endpointEprExtn.getQName(), endpointEprExtn);
/*     */         }
/*     */       } 
/* 213 */       for (WSEndpointReference.EPRExtension extn : eprExtensions.values()) {
/* 214 */         this.endpointReferenceExtensions.put(extn.getQName(), new WSEPRExtension(
/* 215 */               XMLStreamBuffer.createNewBufferFromXMLStreamReader(extn.readAsXMLStreamReader()), extn.getQName()));
/*     */       }
/* 217 */     } catch (XMLStreamException ex) {
/* 218 */       throw new WebServiceException(ex);
/*     */     } 
/* 220 */     if (!eprExtensions.isEmpty()) {
/* 221 */       serviceDef.addFilter((SDDocumentFilter)new EPRSDDocumentFilter(this));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected ServerTubeAssemblerContext createServerTubeAssemblerContext(EndpointAwareTube terminalTube, boolean isSynchronous) {
/* 227 */     return (ServerTubeAssemblerContext)new ServerPipeAssemblerContext(this.seiModel, this.port, this, terminalTube, isSynchronous);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WSEndpointImpl(@NotNull QName serviceName, @NotNull QName portName, WSBinding binding, Container container, SEIModel seiModel, WSDLPort port, Tube masterTubeline) {
/* 235 */     this.serviceName = serviceName;
/* 236 */     this.portName = portName;
/* 237 */     this.binding = binding;
/* 238 */     this.soapVersion = binding.getSOAPVersion();
/* 239 */     this.container = container;
/* 240 */     this.endpointPolicy = null;
/* 241 */     this.port = port;
/* 242 */     this.seiModel = seiModel;
/* 243 */     this.serviceDef = null;
/* 244 */     this.implementationClass = null;
/* 245 */     this.masterTubeline = masterTubeline;
/* 246 */     this.masterCodec = ((BindingImpl)this.binding).createCodec();
/*     */     
/* 248 */     LazyMOMProvider.INSTANCE.registerEndpoint(this);
/* 249 */     initManagedObjectManager();
/*     */     
/* 251 */     this.operationDispatcher = (port == null) ? null : new OperationDispatcher(port, binding, seiModel);
/* 252 */     this.context = (ServerTubeAssemblerContext)new ServerPipeAssemblerContext(seiModel, port, this, null, false);
/*     */ 
/*     */     
/* 255 */     this.tubePool = (Pool<Tube>)new Pool.TubePool(masterTubeline);
/* 256 */     this.engine = new Engine(toString(), container);
/* 257 */     this.wsdlProperties = (port == null) ? (WSDLProperties)new WSDLDirectProperties(serviceName, portName, seiModel) : (WSDLProperties)new WSDLPortProperties(port, seiModel);
/*     */   }
/*     */   
/*     */   public Collection<WSEndpointReference.EPRExtension> getEndpointReferenceExtensions() {
/* 261 */     return this.endpointReferenceExtensions.values();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public OperationDispatcher getOperationDispatcher() {
/* 269 */     return this.operationDispatcher;
/*     */   }
/*     */   
/*     */   public PolicyMap getPolicyMap() {
/* 273 */     return this.endpointPolicy;
/*     */   }
/*     */   @NotNull
/*     */   public Class<T> getImplementationClass() {
/* 277 */     return this.implementationClass;
/*     */   }
/*     */   @NotNull
/*     */   public WSBinding getBinding() {
/* 281 */     return this.binding;
/*     */   }
/*     */   @NotNull
/*     */   public Container getContainer() {
/* 285 */     return this.container;
/*     */   }
/*     */   
/*     */   public WSDLPort getPort() {
/* 289 */     return this.port;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public SEIModel getSEIModel() {
/* 294 */     return this.seiModel;
/*     */   }
/*     */   
/*     */   public void setExecutor(Executor exec) {
/* 298 */     this.engine.setExecutor(exec);
/*     */   }
/*     */ 
/*     */   
/*     */   public Engine getEngine() {
/* 303 */     return this.engine;
/*     */   }
/*     */   
/*     */   public void schedule(Packet request, WSEndpoint.CompletionCallback callback, FiberContextSwitchInterceptor interceptor) {
/* 307 */     processAsync(request, callback, interceptor, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void processAsync(final Packet request, final WSEndpoint.CompletionCallback callback, FiberContextSwitchInterceptor interceptor, boolean schedule) {
/* 313 */     Container old = ContainerResolver.getDefault().enterContainer(this.container);
/*     */     try {
/* 315 */       request.endpoint = this;
/* 316 */       request.addSatellite((PropertySet)this.wsdlProperties);
/*     */       
/* 318 */       Fiber fiber = this.engine.createFiber();
/* 319 */       fiber.setDeliverThrowableInPacket(true);
/* 320 */       if (interceptor != null) {
/* 321 */         fiber.addInterceptor(interceptor);
/*     */       }
/* 323 */       final Tube tube = (Tube)this.tubePool.take();
/* 324 */       Fiber.CompletionCallback cbak = new Fiber.CompletionCallback() {
/*     */           public void onCompletion(@NotNull Packet response) {
/* 326 */             ThrowableContainerPropertySet tc = (ThrowableContainerPropertySet)response.getSatellite(ThrowableContainerPropertySet.class);
/* 327 */             if (tc == null)
/*     */             {
/*     */               
/* 330 */               WSEndpointImpl.this.tubePool.recycle(tube);
/*     */             }
/*     */             
/* 333 */             if (callback != null) {
/* 334 */               if (tc != null) {
/* 335 */                 response = WSEndpointImpl.this.createServiceResponseForException(tc, response, WSEndpointImpl.this
/*     */                     
/* 337 */                     .soapVersion, request.endpoint
/* 338 */                     .getPort(), null, request.endpoint
/*     */                     
/* 340 */                     .getBinding());
/*     */               }
/* 342 */               callback.onCompletion(response);
/*     */             } 
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void onCompletion(@NotNull Throwable error) {
/* 349 */             throw new IllegalStateException();
/*     */           }
/*     */         };
/*     */       
/* 353 */       fiber.start(tube, request, cbak, (this.binding
/* 354 */           .isFeatureEnabled(SyncStartForAsyncFeature.class) || !schedule));
/*     */     } finally {
/*     */       
/* 357 */       ContainerResolver.getDefault().exitContainer(old);
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
/*     */   public Packet createServiceResponseForException(ThrowableContainerPropertySet tc, Packet responsePacket, SOAPVersion soapVersion, WSDLPort wsdlPort, SEIModel seiModel, WSBinding binding) {
/* 370 */     if (tc.isFaultCreated()) return responsePacket;
/*     */     
/* 372 */     Message faultMessage = SOAPFaultBuilder.createSOAPFaultMessage(soapVersion, null, tc.getThrowable());
/* 373 */     Packet result = responsePacket.createServerResponse(faultMessage, wsdlPort, seiModel, binding);
/*     */     
/* 375 */     tc.setFaultMessage(faultMessage);
/* 376 */     tc.setResponsePacket(responsePacket);
/* 377 */     tc.setFaultCreated(true);
/* 378 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void process(Packet request, WSEndpoint.CompletionCallback callback, FiberContextSwitchInterceptor interceptor) {
/* 383 */     processAsync(request, callback, interceptor, false);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public WSEndpoint.PipeHead createPipeHead() {
/* 388 */     return new WSEndpoint.PipeHead() {
/* 389 */         private final Tube tube = TubeCloner.clone(WSEndpointImpl.this.masterTubeline);
/*     */ 
/*     */         
/*     */         @NotNull
/*     */         public Packet process(Packet request, WebServiceContextDelegate wscd, TransportBackChannel tbc) {
/* 394 */           Container old = ContainerResolver.getDefault().enterContainer(WSEndpointImpl.this.container); try {
/*     */             Packet response;
/* 396 */             request.webServiceContextDelegate = wscd;
/* 397 */             request.transportBackChannel = tbc;
/* 398 */             request.endpoint = WSEndpointImpl.this;
/* 399 */             request.addSatellite((PropertySet)WSEndpointImpl.this.wsdlProperties);
/*     */             
/* 401 */             Fiber fiber = WSEndpointImpl.this.engine.createFiber();
/*     */             
/*     */             try {
/* 404 */               response = fiber.runSync(this.tube, request);
/* 405 */             } catch (RuntimeException re) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 411 */               Message faultMsg = SOAPFaultBuilder.createSOAPFaultMessage(WSEndpointImpl.this.soapVersion, null, re);
/* 412 */               response = request.createServerResponse(faultMsg, request.endpoint
/* 413 */                   .getPort(), null, request.endpoint
/* 414 */                   .getBinding());
/*     */             } 
/* 416 */             return response;
/*     */           } finally {
/* 418 */             ContainerResolver.getDefault().exitContainer(old);
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public synchronized void dispose() {
/* 425 */     if (this.disposed) {
/*     */       return;
/*     */     }
/* 428 */     this.disposed = true;
/*     */     
/* 430 */     this.masterTubeline.preDestroy();
/*     */     
/* 432 */     for (Handler handler : this.binding.getHandlerChain()) {
/* 433 */       Method[] arrayOfMethod; int i; byte b; for (arrayOfMethod = handler.getClass().getMethods(), i = arrayOfMethod.length, b = 0; b < i; ) { Method method = arrayOfMethod[b];
/* 434 */         if (method.getAnnotation(PreDestroy.class) == null) {
/*     */           b++; continue;
/*     */         } 
/*     */         try {
/* 438 */           method.invoke(handler, new Object[0]);
/* 439 */         } catch (Exception e) {
/* 440 */           logger.log(Level.WARNING, HandlerMessages.HANDLER_PREDESTROY_IGNORE(e.getMessage()), e);
/*     */         }  }
/*     */     
/*     */     } 
/*     */     
/* 445 */     closeManagedObjectManager();
/* 446 */     LazyMOMProvider.INSTANCE.unregisterEndpoint(this);
/*     */   }
/*     */   
/*     */   public ServiceDefinitionImpl getServiceDefinition() {
/* 450 */     return this.serviceDef;
/*     */   }
/*     */   
/*     */   public Set<EndpointComponent> getComponentRegistry() {
/* 454 */     Set<EndpointComponent> sec = new EndpointComponentSet();
/* 455 */     for (Component c : this.componentRegistry) {
/* 456 */       sec.add((c instanceof EndpointComponentWrapper) ? ((EndpointComponentWrapper)c)
/* 457 */           .component : new ComponentWrapper(c));
/*     */     }
/*     */     
/* 460 */     return sec;
/*     */   }
/*     */   
/*     */   private class EndpointComponentSet extends HashSet<EndpointComponent> {
/*     */     private EndpointComponentSet() {}
/*     */     
/*     */     public Iterator<EndpointComponent> iterator() {
/* 467 */       final Iterator<EndpointComponent> it = super.iterator();
/* 468 */       return new Iterator<EndpointComponent>() {
/* 469 */           private EndpointComponent last = null;
/*     */           
/*     */           public boolean hasNext() {
/* 472 */             return it.hasNext();
/*     */           }
/*     */           
/*     */           public EndpointComponent next() {
/* 476 */             this.last = it.next();
/* 477 */             return this.last;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 481 */             it.remove();
/* 482 */             if (this.last != null) {
/* 483 */               WSEndpointImpl.this.componentRegistry.remove((this.last instanceof WSEndpointImpl.ComponentWrapper) ? ((WSEndpointImpl.ComponentWrapper)this.last)
/* 484 */                   .component : new WSEndpointImpl.EndpointComponentWrapper(this.last));
/*     */             }
/*     */             
/* 487 */             this.last = null;
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean add(EndpointComponent e) {
/* 494 */       boolean result = super.add(e);
/* 495 */       if (result) {
/* 496 */         WSEndpointImpl.this.componentRegistry.add(new WSEndpointImpl.EndpointComponentWrapper(e));
/*     */       }
/* 498 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 503 */       boolean result = super.remove(o);
/* 504 */       if (result) {
/* 505 */         WSEndpointImpl.this.componentRegistry.remove((o instanceof WSEndpointImpl.ComponentWrapper) ? ((WSEndpointImpl.ComponentWrapper)o)
/* 506 */             .component : new WSEndpointImpl.EndpointComponentWrapper((EndpointComponent)o));
/*     */       }
/*     */       
/* 509 */       return result;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ComponentWrapper
/*     */     implements EndpointComponent {
/*     */     private final Component component;
/*     */     
/*     */     public ComponentWrapper(Component component) {
/* 518 */       this.component = component;
/*     */     }
/*     */     
/*     */     public <S> S getSPI(Class<S> spiType) {
/* 522 */       return (S)this.component.getSPI(spiType);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 527 */       return this.component.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 532 */       return this.component.equals(obj);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class EndpointComponentWrapper implements Component {
/*     */     private final EndpointComponent component;
/*     */     
/*     */     public EndpointComponentWrapper(EndpointComponent component) {
/* 540 */       this.component = component;
/*     */     }
/*     */     
/*     */     public <S> S getSPI(Class<S> spiType) {
/* 544 */       return (S)this.component.getSPI(spiType);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 549 */       return this.component.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 554 */       return this.component.equals(obj);
/*     */     }
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Set<Component> getComponents() {
/* 560 */     return this.componentRegistry;
/*     */   }
/*     */   
/*     */   public <T extends javax.xml.ws.EndpointReference> T getEndpointReference(Class<T> clazz, String address, String wsdlAddress, Element... referenceParameters) {
/* 564 */     List<Element> refParams = null;
/* 565 */     if (referenceParameters != null) {
/* 566 */       refParams = Arrays.asList(referenceParameters);
/*     */     }
/* 568 */     return getEndpointReference(clazz, address, wsdlAddress, null, refParams);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends javax.xml.ws.EndpointReference> T getEndpointReference(Class<T> clazz, String address, String wsdlAddress, List<Element> metadata, List<Element> referenceParameters) {
/* 574 */     QName portType = null;
/* 575 */     if (this.port != null) {
/* 576 */       portType = this.port.getBinding().getPortTypeName();
/*     */     }
/*     */     
/* 579 */     AddressingVersion av = AddressingVersion.fromSpecClass(clazz);
/* 580 */     return (T)(new WSEndpointReference(av, address, this.serviceName, this.portName, portType, metadata, wsdlAddress, referenceParameters, this.endpointReferenceExtensions
/* 581 */         .values(), null)).toSpec(clazz);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public QName getPortName() {
/* 586 */     return this.portName;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Codec createCodec() {
/* 591 */     return this.masterCodec.copy();
/*     */   }
/*     */   @NotNull
/*     */   public QName getServiceName() {
/* 595 */     return this.serviceName;
/*     */   }
/*     */   
/*     */   private void initManagedObjectManager() {
/* 599 */     synchronized (this.managedObjectManagerLock) {
/* 600 */       if (this.managedObjectManager == null)
/* 601 */         switch (this.lazyMOMProviderScope) {
/*     */           case GLASSFISH_NO_JMX:
/* 603 */             this.managedObjectManager = new WSEndpointMOMProxy(this);
/*     */             break;
/*     */           default:
/* 606 */             this.managedObjectManager = obtainManagedObjectManager();
/*     */             break;
/*     */         }  
/*     */     } 
/*     */   }
/*     */   @NotNull
/*     */   public ManagedObjectManager getManagedObjectManager() {
/* 613 */     return this.managedObjectManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   ManagedObjectManager obtainManagedObjectManager() {
/* 624 */     MonitorRootService monitorRootService = new MonitorRootService(this);
/* 625 */     ManagedObjectManager mOM = monitorRootService.createManagedObjectManager(this);
/*     */ 
/*     */     
/* 628 */     mOM.resumeJMXRegistration();
/*     */     
/* 630 */     return mOM;
/*     */   }
/*     */   
/*     */   public void scopeChanged(LazyMOMProvider.Scope scope) {
/* 634 */     synchronized (this.managedObjectManagerLock) {
/* 635 */       if (this.managedObjectManagerClosed) {
/*     */         return;
/*     */       }
/*     */       
/* 639 */       this.lazyMOMProviderScope = scope;
/*     */ 
/*     */       
/* 642 */       if (this.managedObjectManager == null) {
/* 643 */         if (scope != LazyMOMProvider.Scope.GLASSFISH_NO_JMX) {
/* 644 */           this.managedObjectManager = obtainManagedObjectManager();
/*     */         } else {
/* 646 */           this.managedObjectManager = new WSEndpointMOMProxy(this);
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 651 */       else if (this.managedObjectManager instanceof WSEndpointMOMProxy && 
/* 652 */         !((WSEndpointMOMProxy)this.managedObjectManager).isInitialized()) {
/* 653 */         ((WSEndpointMOMProxy)this.managedObjectManager).setManagedObjectManager(obtainManagedObjectManager());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 659 */   private static final Logger monitoringLogger = Logger.getLogger("com.sun.xml.internal.ws.monitoring");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeManagedObjectManager() {
/* 665 */     synchronized (this.managedObjectManagerLock) {
/* 666 */       if (this.managedObjectManagerClosed == true) {
/*     */         return;
/*     */       }
/* 669 */       if (this.managedObjectManager != null) {
/* 670 */         boolean close = true;
/*     */ 
/*     */         
/* 673 */         if (this.managedObjectManager instanceof WSEndpointMOMProxy && 
/* 674 */           !((WSEndpointMOMProxy)this.managedObjectManager).isInitialized()) {
/* 675 */           close = false;
/*     */         }
/*     */         
/* 678 */         if (close) {
/*     */           try {
/* 680 */             ObjectName name = this.managedObjectManager.getObjectName(this.managedObjectManager.getRoot());
/*     */             
/* 682 */             if (name != null) {
/* 683 */               monitoringLogger.log(Level.INFO, "Closing Metro monitoring root: {0}", name);
/*     */             }
/* 685 */             this.managedObjectManager.close();
/* 686 */           } catch (IOException e) {
/* 687 */             monitoringLogger.log(Level.WARNING, "Ignoring error when closing Managed Object Manager", e);
/*     */           } 
/*     */         }
/*     */       } 
/* 691 */       this.managedObjectManagerClosed = true;
/*     */     } 
/*     */   }
/*     */   @NotNull
/*     */   public ServerTubeAssemblerContext getAssemblerContext() {
/* 696 */     return this.context;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/server/WSEndpointImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */