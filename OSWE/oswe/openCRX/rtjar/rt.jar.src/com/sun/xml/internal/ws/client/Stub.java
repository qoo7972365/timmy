/*     */ package com.sun.xml.internal.ws.client;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.message.PropertySet;
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.org.glassfish.gmbal.ManagedObjectManager;
/*     */ import com.sun.xml.internal.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.internal.ws.addressing.WSEPRExtension;
/*     */ import com.sun.xml.internal.ws.api.BindingID;
/*     */ import com.sun.xml.internal.ws.api.Cancelable;
/*     */ import com.sun.xml.internal.ws.api.Component;
/*     */ import com.sun.xml.internal.ws.api.ComponentFeature;
/*     */ import com.sun.xml.internal.ws.api.ComponentRegistry;
/*     */ import com.sun.xml.internal.ws.api.ComponentsFeature;
/*     */ import com.sun.xml.internal.ws.api.EndpointAddress;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.WSService;
/*     */ import com.sun.xml.internal.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.internal.ws.api.client.WSPortInfo;
/*     */ import com.sun.xml.internal.ws.api.message.AddressingUtils;
/*     */ import com.sun.xml.internal.ws.api.message.Header;
/*     */ import com.sun.xml.internal.ws.api.message.MessageHeaders;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.api.pipe.ClientTubeAssemblerContext;
/*     */ import com.sun.xml.internal.ws.api.pipe.Engine;
/*     */ import com.sun.xml.internal.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptorFactory;
/*     */ import com.sun.xml.internal.ws.api.pipe.SyncStartForAsyncFeature;
/*     */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*     */ import com.sun.xml.internal.ws.api.pipe.TubelineAssembler;
/*     */ import com.sun.xml.internal.ws.api.pipe.TubelineAssemblerFactory;
/*     */ import com.sun.xml.internal.ws.api.server.Container;
/*     */ import com.sun.xml.internal.ws.api.server.ContainerResolver;
/*     */ import com.sun.xml.internal.ws.binding.BindingImpl;
/*     */ import com.sun.xml.internal.ws.developer.WSBindingProvider;
/*     */ import com.sun.xml.internal.ws.model.SOAPSEIModel;
/*     */ import com.sun.xml.internal.ws.model.wsdl.WSDLDirectProperties;
/*     */ import com.sun.xml.internal.ws.model.wsdl.WSDLPortProperties;
/*     */ import com.sun.xml.internal.ws.model.wsdl.WSDLProperties;
/*     */ import com.sun.xml.internal.ws.resources.ClientMessages;
/*     */ import com.sun.xml.internal.ws.util.Pool;
/*     */ import com.sun.xml.internal.ws.util.RuntimeVersion;
/*     */ import com.sun.xml.internal.ws.wsdl.OperationDispatcher;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CopyOnWriteArraySet;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.management.ObjectName;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.ws.Binding;
/*     */ import javax.xml.ws.BindingProvider;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ import javax.xml.ws.RespectBindingFeature;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.wsaddressing.W3CEndpointReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Stub
/*     */   implements WSBindingProvider, ResponseContextReceiver, ComponentRegistry
/*     */ {
/*     */   public static final String PREVENT_SYNC_START_FOR_ASYNC_INVOKE = "com.sun.xml.internal.ws.client.StubRequestSyncStartForAsyncInvoke";
/*     */   private Pool<Tube> tubes;
/*     */   private final Engine engine;
/*     */   protected final WSServiceDelegate owner;
/*     */   @Nullable
/*     */   protected WSEndpointReference endpointReference;
/*     */   protected final BindingImpl binding;
/*     */   protected final WSPortInfo portInfo;
/*     */   protected AddressingVersion addrVersion;
/* 151 */   public RequestContext requestContext = new RequestContext();
/*     */ 
/*     */   
/*     */   private final RequestContext cleanRequestContext;
/*     */ 
/*     */   
/*     */   private ResponseContext responseContext;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected final WSDLPort wsdlPort;
/*     */ 
/*     */   
/*     */   protected QName portname;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private volatile Header[] userOutboundHeaders;
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private final WSDLProperties wsdlProperties;
/*     */   
/* 174 */   protected OperationDispatcher operationDispatcher = null;
/*     */   
/*     */   @NotNull
/*     */   private final ManagedObjectManager managedObjectManager;
/*     */   
/*     */   private boolean managedObjectManagerClosed = false;
/* 180 */   private final Set<Component> components = new CopyOnWriteArraySet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected Stub(WSServiceDelegate owner, Tube master, BindingImpl binding, WSDLPort wsdlPort, EndpointAddress defaultEndPointAddress, @Nullable WSEndpointReference epr) {
/* 195 */     this(owner, master, null, null, binding, wsdlPort, defaultEndPointAddress, epr);
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
/*     */   @Deprecated
/*     */   protected Stub(QName portname, WSServiceDelegate owner, Tube master, BindingImpl binding, WSDLPort wsdlPort, EndpointAddress defaultEndPointAddress, @Nullable WSEndpointReference epr) {
/* 212 */     this(owner, master, null, portname, binding, wsdlPort, defaultEndPointAddress, epr);
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
/*     */   protected Stub(WSPortInfo portInfo, BindingImpl binding, Tube master, EndpointAddress defaultEndPointAddress, @Nullable WSEndpointReference epr) {
/* 228 */     this((WSServiceDelegate)portInfo.getOwner(), master, portInfo, null, binding, portInfo.getPort(), defaultEndPointAddress, epr);
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
/*     */   protected Stub(WSPortInfo portInfo, BindingImpl binding, EndpointAddress defaultEndPointAddress, @Nullable WSEndpointReference epr) {
/* 243 */     this(portInfo, binding, null, defaultEndPointAddress, epr);
/*     */   }
/*     */ 
/*     */   
/*     */   private Stub(WSServiceDelegate owner, @Nullable Tube master, @Nullable WSPortInfo portInfo, QName portname, BindingImpl binding, @Nullable WSDLPort wsdlPort, EndpointAddress defaultEndPointAddress, @Nullable WSEndpointReference epr) {
/* 248 */     Container old = ContainerResolver.getDefault().enterContainer(owner.getContainer());
/*     */     try {
/* 250 */       this.owner = owner;
/* 251 */       this.portInfo = portInfo;
/* 252 */       this.wsdlPort = (wsdlPort != null) ? wsdlPort : ((portInfo != null) ? portInfo.getPort() : null);
/* 253 */       this.portname = portname;
/* 254 */       if (portname == null) {
/* 255 */         if (portInfo != null) {
/* 256 */           this.portname = portInfo.getPortName();
/* 257 */         } else if (wsdlPort != null) {
/* 258 */           this.portname = wsdlPort.getName();
/*     */         } 
/*     */       }
/* 261 */       this.binding = binding;
/*     */       
/* 263 */       ComponentFeature cf = (ComponentFeature)binding.getFeature(ComponentFeature.class);
/* 264 */       if (cf != null && ComponentFeature.Target.STUB.equals(cf.getTarget())) {
/* 265 */         this.components.add(cf.getComponent());
/*     */       }
/* 267 */       ComponentsFeature csf = (ComponentsFeature)binding.getFeature(ComponentsFeature.class);
/* 268 */       if (csf != null) {
/* 269 */         for (ComponentFeature cfi : csf.getComponentFeatures()) {
/* 270 */           if (ComponentFeature.Target.STUB.equals(cfi.getTarget())) {
/* 271 */             this.components.add(cfi.getComponent());
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 276 */       if (epr != null) {
/* 277 */         this.requestContext.setEndPointAddressString(epr.getAddress());
/*     */       } else {
/* 279 */         this.requestContext.setEndpointAddress(defaultEndPointAddress);
/*     */       } 
/* 281 */       this.engine = new Engine(getStringId(), owner.getContainer(), owner.getExecutor());
/* 282 */       this.endpointReference = epr;
/* 283 */       this.wsdlProperties = (wsdlPort == null) ? (WSDLProperties)new WSDLDirectProperties(owner.getServiceName(), portname) : (WSDLProperties)new WSDLPortProperties(wsdlPort);
/*     */       
/* 285 */       this.cleanRequestContext = this.requestContext.copy();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 290 */       this.managedObjectManager = (new MonitorRootClient(this)).createManagedObjectManager(this);
/*     */       
/* 292 */       if (master != null) {
/* 293 */         this.tubes = (Pool<Tube>)new Pool.TubePool(master);
/*     */       } else {
/* 295 */         this.tubes = (Pool<Tube>)new Pool.TubePool(createPipeline(portInfo, (WSBinding)binding));
/*     */       } 
/*     */       
/* 298 */       this.addrVersion = binding.getAddressingVersion();
/*     */ 
/*     */ 
/*     */       
/* 302 */       this.managedObjectManager.resumeJMXRegistration();
/*     */     } finally {
/* 304 */       ContainerResolver.getDefault().exitContainer(old);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Tube createPipeline(WSPortInfo portInfo, WSBinding binding) {
/*     */     SOAPSEIModel sOAPSEIModel;
/* 313 */     checkAllWSDLExtensionsUnderstood(portInfo, binding);
/* 314 */     SEIModel seiModel = null;
/* 315 */     Class sei = null;
/* 316 */     if (portInfo instanceof SEIPortInfo) {
/* 317 */       SEIPortInfo sp = (SEIPortInfo)portInfo;
/* 318 */       sOAPSEIModel = sp.model;
/* 319 */       sei = sp.sei;
/*     */     } 
/* 321 */     BindingID bindingId = portInfo.getBindingId();
/*     */     
/* 323 */     TubelineAssembler assembler = TubelineAssemblerFactory.create(
/* 324 */         Thread.currentThread().getContextClassLoader(), bindingId, this.owner.getContainer());
/* 325 */     if (assembler == null) {
/* 326 */       throw new WebServiceException("Unable to process bindingID=" + bindingId);
/*     */     }
/* 328 */     return assembler.createClient(new ClientTubeAssemblerContext(portInfo
/*     */           
/* 330 */           .getEndpointAddress(), portInfo
/* 331 */           .getPort(), this, binding, this.owner
/* 332 */           .getContainer(), ((BindingImpl)binding).createCodec(), (SEIModel)sOAPSEIModel, sei));
/*     */   }
/*     */   
/*     */   public WSDLPort getWSDLPort() {
/* 336 */     return this.wsdlPort;
/*     */   }
/*     */   
/*     */   public WSService getService() {
/* 340 */     return this.owner;
/*     */   }
/*     */   
/*     */   public Pool<Tube> getTubes() {
/* 344 */     return this.tubes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkAllWSDLExtensionsUnderstood(WSPortInfo port, WSBinding binding) {
/* 355 */     if (port.getPort() != null && binding.isFeatureEnabled(RespectBindingFeature.class)) {
/* 356 */       port.getPort().areRequiredExtensionsUnderstood();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public WSPortInfo getPortInfo() {
/* 362 */     return this.portInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public OperationDispatcher getOperationDispatcher() {
/* 372 */     if (this.operationDispatcher == null && this.wsdlPort != null) {
/* 373 */       this.operationDispatcher = new OperationDispatcher(this.wsdlPort, (WSBinding)this.binding, null);
/*     */     }
/* 375 */     return this.operationDispatcher;
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
/*     */   @NotNull
/*     */   protected final QName getServiceName() {
/* 400 */     return this.owner.getServiceName();
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
/*     */   public final Executor getExecutor() {
/* 412 */     return this.owner.getExecutor();
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
/*     */   protected final Packet process(Packet packet, RequestContext requestContext, ResponseContextReceiver receiver) {
/* 433 */     packet.isSynchronousMEP = Boolean.valueOf(true);
/* 434 */     packet.component = (Component)this;
/* 435 */     configureRequestPacket(packet, requestContext);
/* 436 */     Pool<Tube> pool = this.tubes;
/* 437 */     if (pool == null) {
/* 438 */       throw new WebServiceException("close method has already been invoked");
/*     */     }
/*     */     
/* 441 */     Fiber fiber = this.engine.createFiber();
/* 442 */     configureFiber(fiber);
/*     */ 
/*     */     
/* 445 */     Tube tube = (Tube)pool.take();
/*     */     
/*     */     try {
/* 448 */       return fiber.runSync(tube, packet);
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */ 
/*     */       
/* 456 */       Packet reply = (fiber.getPacket() == null) ? packet : fiber.getPacket();
/* 457 */       receiver.setResponseContext(new ResponseContext(reply));
/*     */       
/* 459 */       pool.recycle(tube);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void configureRequestPacket(Packet packet, RequestContext requestContext) {
/* 465 */     packet.proxy = (BindingProvider)this;
/* 466 */     packet.handlerConfig = this.binding.getHandlerConfig();
/*     */ 
/*     */     
/* 469 */     Header[] hl = this.userOutboundHeaders;
/* 470 */     if (hl != null) {
/* 471 */       MessageHeaders mh = packet.getMessage().getHeaders();
/* 472 */       for (Header h : hl) {
/* 473 */         mh.add(h);
/*     */       }
/*     */     } 
/*     */     
/* 477 */     requestContext.fill(packet, (this.binding.getAddressingVersion() != null));
/* 478 */     packet.addSatellite((PropertySet)this.wsdlProperties);
/*     */     
/* 480 */     if (this.addrVersion != null) {
/*     */       
/* 482 */       MessageHeaders headerList = packet.getMessage().getHeaders();
/* 483 */       AddressingUtils.fillRequestAddressingHeaders(headerList, this.wsdlPort, (WSBinding)this.binding, packet);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 489 */       if (this.endpointReference != null) {
/* 490 */         this.endpointReference.addReferenceParametersToList(packet.getMessage().getHeaders());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void processAsync(AsyncResponseImpl<?> receiver, Packet request, RequestContext requestContext, final Fiber.CompletionCallback completionCallback) {
/* 515 */     request.component = (Component)this;
/* 516 */     configureRequestPacket(request, requestContext);
/*     */     
/* 518 */     final Pool<Tube> pool = this.tubes;
/* 519 */     if (pool == null) {
/* 520 */       throw new WebServiceException("close method has already been invoked");
/*     */     }
/*     */     
/* 523 */     Fiber fiber = this.engine.createFiber();
/* 524 */     configureFiber(fiber);
/*     */     
/* 526 */     receiver.setCancelable((Cancelable)fiber);
/*     */ 
/*     */     
/* 529 */     if (receiver.isCancelled()) {
/*     */       return;
/*     */     }
/*     */     
/* 533 */     FiberContextSwitchInterceptorFactory fcsif = (FiberContextSwitchInterceptorFactory)this.owner.getSPI(FiberContextSwitchInterceptorFactory.class);
/* 534 */     if (fcsif != null) {
/* 535 */       fiber.addInterceptor(fcsif.create());
/*     */     }
/*     */ 
/*     */     
/* 539 */     final Tube tube = (Tube)pool.take();
/*     */     
/* 541 */     Fiber.CompletionCallback fiberCallback = new Fiber.CompletionCallback()
/*     */       {
/*     */         public void onCompletion(@NotNull Packet response) {
/* 544 */           pool.recycle(tube);
/* 545 */           completionCallback.onCompletion(response);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void onCompletion(@NotNull Throwable error) {
/* 552 */           completionCallback.onCompletion(error);
/*     */         }
/*     */       };
/*     */ 
/*     */ 
/*     */     
/* 558 */     fiber.start(tube, request, fiberCallback, (
/* 559 */         getBinding().isFeatureEnabled(SyncStartForAsyncFeature.class) && 
/* 560 */         !requestContext.containsKey("com.sun.xml.internal.ws.client.StubRequestSyncStartForAsyncInvoke")));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void configureFiber(Fiber fiber) {}
/*     */ 
/*     */   
/* 568 */   private static final Logger monitoringLogger = Logger.getLogger("com.sun.xml.internal.ws.monitoring");
/*     */ 
/*     */   
/*     */   public void close() {
/* 572 */     Pool.TubePool tp = (Pool.TubePool)this.tubes;
/* 573 */     if (tp != null) {
/*     */ 
/*     */ 
/*     */       
/* 577 */       Tube p = tp.takeMaster();
/* 578 */       p.preDestroy();
/* 579 */       this.tubes = null;
/*     */     } 
/* 581 */     if (!this.managedObjectManagerClosed) {
/*     */       try {
/* 583 */         ObjectName name = this.managedObjectManager.getObjectName(this.managedObjectManager.getRoot());
/*     */         
/* 585 */         if (name != null) {
/* 586 */           monitoringLogger.log(Level.INFO, "Closing Metro monitoring root: {0}", name);
/*     */         }
/* 588 */         this.managedObjectManager.close();
/* 589 */       } catch (IOException e) {
/* 590 */         monitoringLogger.log(Level.WARNING, "Ignoring error when closing Managed Object Manager", e);
/*     */       } 
/* 592 */       this.managedObjectManagerClosed = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final WSBinding getBinding() {
/* 598 */     return (WSBinding)this.binding;
/*     */   }
/*     */ 
/*     */   
/*     */   public final Map<String, Object> getRequestContext() {
/* 603 */     return this.requestContext.asMap();
/*     */   }
/*     */   
/*     */   public void resetRequestContext() {
/* 607 */     this.requestContext = this.cleanRequestContext.copy();
/*     */   }
/*     */ 
/*     */   
/*     */   public final ResponseContext getResponseContext() {
/* 612 */     return this.responseContext;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setResponseContext(ResponseContext rc) {
/* 617 */     this.responseContext = rc;
/*     */   }
/*     */   
/*     */   private String getStringId() {
/* 621 */     return RuntimeVersion.VERSION + ": Stub for " + getRequestContext().get("javax.xml.ws.service.endpoint.address");
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 626 */     return getStringId();
/*     */   }
/*     */ 
/*     */   
/*     */   public final WSEndpointReference getWSEndpointReference() {
/* 631 */     if (this.binding.getBindingID().equals("http://www.w3.org/2004/08/wsdl/http")) {
/* 632 */       throw new UnsupportedOperationException(
/* 633 */           ClientMessages.UNSUPPORTED_OPERATION("BindingProvider.getEndpointReference(Class<T> class)", "XML/HTTP Binding", "SOAP11 or SOAP12 Binding"));
/*     */     }
/*     */ 
/*     */     
/* 637 */     if (this.endpointReference != null) {
/* 638 */       return this.endpointReference;
/*     */     }
/*     */     
/* 641 */     String eprAddress = this.requestContext.getEndpointAddress().toString();
/* 642 */     QName portTypeName = null;
/* 643 */     String wsdlAddress = null;
/* 644 */     List<WSEndpointReference.EPRExtension> wsdlEPRExtensions = new ArrayList<>();
/* 645 */     if (this.wsdlPort != null) {
/* 646 */       portTypeName = this.wsdlPort.getBinding().getPortTypeName();
/* 647 */       wsdlAddress = eprAddress + "?wsdl";
/*     */ 
/*     */       
/*     */       try {
/* 651 */         WSEndpointReference wsdlEpr = this.wsdlPort.getEPR();
/* 652 */         if (wsdlEpr != null) {
/* 653 */           for (WSEndpointReference.EPRExtension extnEl : wsdlEpr.getEPRExtensions()) {
/* 654 */             wsdlEPRExtensions.add(new WSEPRExtension(
/* 655 */                   XMLStreamBuffer.createNewBufferFromXMLStreamReader(extnEl.readAsXMLStreamReader()), extnEl.getQName()));
/*     */           }
/*     */         }
/*     */       }
/* 659 */       catch (XMLStreamException ex) {
/* 660 */         throw new WebServiceException(ex);
/*     */       } 
/*     */     } 
/* 663 */     AddressingVersion av = AddressingVersion.W3C;
/* 664 */     this
/* 665 */       .endpointReference = new WSEndpointReference(av, eprAddress, getServiceName(), getPortName(), portTypeName, null, wsdlAddress, null, wsdlEPRExtensions, null);
/*     */     
/* 667 */     return this.endpointReference;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final W3CEndpointReference getEndpointReference() {
/* 673 */     if (this.binding.getBindingID().equals("http://www.w3.org/2004/08/wsdl/http")) {
/* 674 */       throw new UnsupportedOperationException(
/* 675 */           ClientMessages.UNSUPPORTED_OPERATION("BindingProvider.getEndpointReference()", "XML/HTTP Binding", "SOAP11 or SOAP12 Binding"));
/*     */     }
/* 677 */     return getEndpointReference(W3CEndpointReference.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public final <T extends EndpointReference> T getEndpointReference(Class<T> clazz) {
/* 682 */     return (T)getWSEndpointReference().toSpec(clazz);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ManagedObjectManager getManagedObjectManager() {
/* 689 */     return this.managedObjectManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setOutboundHeaders(List<Header> headers) {
/* 699 */     if (headers == null) {
/* 700 */       this.userOutboundHeaders = null;
/*     */     } else {
/* 702 */       for (Header h : headers) {
/* 703 */         if (h == null) {
/* 704 */           throw new IllegalArgumentException();
/*     */         }
/*     */       } 
/* 707 */       this.userOutboundHeaders = headers.<Header>toArray(new Header[headers.size()]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setOutboundHeaders(Header... headers) {
/* 713 */     if (headers == null) {
/* 714 */       this.userOutboundHeaders = null;
/*     */     } else {
/* 716 */       for (Header h : headers) {
/* 717 */         if (h == null) {
/* 718 */           throw new IllegalArgumentException();
/*     */         }
/*     */       } 
/* 721 */       Header[] hl = new Header[headers.length];
/* 722 */       System.arraycopy(headers, 0, hl, 0, headers.length);
/* 723 */       this.userOutboundHeaders = hl;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final List<Header> getInboundHeaders() {
/* 729 */     return Collections.unmodifiableList(((MessageHeaders)this.responseContext
/* 730 */         .get("com.sun.xml.internal.ws.api.message.HeaderList")).asList());
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setAddress(String address) {
/* 735 */     this.requestContext.put("javax.xml.ws.service.endpoint.address", address);
/*     */   }
/*     */ 
/*     */   
/*     */   public <S> S getSPI(Class<S> spiType) {
/* 740 */     for (Component c : this.components) {
/* 741 */       S s = (S)c.getSPI(spiType);
/* 742 */       if (s != null) {
/* 743 */         return s;
/*     */       }
/*     */     } 
/* 746 */     return (S)this.owner.getSPI(spiType);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<Component> getComponents() {
/* 751 */     return this.components;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   protected abstract QName getPortName();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/Stub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */