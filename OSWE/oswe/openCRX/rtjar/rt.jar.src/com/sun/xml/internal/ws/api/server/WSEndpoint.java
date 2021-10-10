/*     */ package com.sun.xml.internal.ws.api.server;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.org.glassfish.gmbal.ManagedObjectManager;
/*     */ import com.sun.xml.internal.ws.api.Component;
/*     */ import com.sun.xml.internal.ws.api.ComponentRegistry;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.config.management.EndpointCreationAttributes;
/*     */ import com.sun.xml.internal.ws.api.config.management.ManagedEndpointFactory;
/*     */ import com.sun.xml.internal.ws.api.databinding.MetadataReader;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.api.pipe.Codec;
/*     */ import com.sun.xml.internal.ws.api.pipe.Engine;
/*     */ import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptor;
/*     */ import com.sun.xml.internal.ws.api.pipe.ServerTubeAssemblerContext;
/*     */ import com.sun.xml.internal.ws.api.pipe.ThrowableContainerPropertySet;
/*     */ import com.sun.xml.internal.ws.policy.PolicyMap;
/*     */ import com.sun.xml.internal.ws.server.EndpointAwareTube;
/*     */ import com.sun.xml.internal.ws.server.EndpointFactory;
/*     */ import com.sun.xml.internal.ws.util.ServiceFinder;
/*     */ import com.sun.xml.internal.ws.util.xml.XmlUtil;
/*     */ import com.sun.xml.internal.ws.wsdl.OperationDispatcher;
/*     */ import java.net.URL;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Executor;
/*     */ import javax.xml.namespace.QName;
/*     */ import org.w3c.dom.Element;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WSEndpoint<T>
/*     */   implements ComponentRegistry
/*     */ {
/*     */   @NotNull
/*     */   public abstract Codec createCodec();
/*     */   
/*     */   @NotNull
/*     */   public abstract QName getServiceName();
/*     */   
/*     */   @NotNull
/*     */   public abstract QName getPortName();
/*     */   
/*     */   @NotNull
/*     */   public abstract Class<T> getImplementationClass();
/*     */   
/*     */   @NotNull
/*     */   public abstract WSBinding getBinding();
/*     */   
/*     */   @NotNull
/*     */   public abstract Container getContainer();
/*     */   
/*     */   @Nullable
/*     */   public abstract WSDLPort getPort();
/*     */   
/*     */   public abstract void setExecutor(@NotNull Executor paramExecutor);
/*     */   
/*     */   public final void schedule(@NotNull Packet request, @NotNull CompletionCallback callback) {
/* 235 */     schedule(request, callback, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void schedule(@NotNull Packet paramPacket, @NotNull CompletionCallback paramCompletionCallback, @Nullable FiberContextSwitchInterceptor paramFiberContextSwitchInterceptor);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(@NotNull Packet request, @NotNull CompletionCallback callback, @Nullable FiberContextSwitchInterceptor interceptor) {
/* 250 */     schedule(request, callback, interceptor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Engine getEngine() {
/* 258 */     throw new UnsupportedOperationException();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public abstract PipeHead createPipeHead();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void dispose();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   public abstract ServiceDefinition getServiceDefinition();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<BoundEndpoint> getBoundEndpoints() {
/* 386 */     Module m = getContainer().<Module>getSPI(Module.class);
/* 387 */     return (m != null) ? m.getBoundEndpoints() : null;
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
/*     */   public abstract Set<EndpointComponent> getComponentRegistry();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Set<Component> getComponents() {
/* 410 */     return Collections.emptySet();
/*     */   }
/*     */   @Nullable
/*     */   public <S> S getSPI(@NotNull Class<S> spiType) {
/* 414 */     Set<Component> componentRegistry = getComponents();
/* 415 */     if (componentRegistry != null)
/* 416 */       for (Component c : componentRegistry) {
/* 417 */         S s = (S)c.getSPI(spiType);
/* 418 */         if (s != null) {
/* 419 */           return s;
/*     */         }
/*     */       }  
/* 422 */     return getContainer().getSPI(spiType);
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
/*     */   @Nullable
/*     */   public abstract SEIModel getSEIModel();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract PolicyMap getPolicyMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   public abstract ManagedObjectManager getManagedObjectManager();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void closeManagedObjectManager();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   public abstract ServerTubeAssemblerContext getAssemblerContext();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> WSEndpoint<T> create(@NotNull Class<T> implType, boolean processHandlerAnnotation, @Nullable Invoker invoker, @Nullable QName serviceName, @Nullable QName portName, @Nullable Container container, @Nullable WSBinding binding, @Nullable SDDocumentSource primaryWsdl, @Nullable Collection<? extends SDDocumentSource> metadata, @Nullable EntityResolver resolver, boolean isTransportSynchronous) {
/* 545 */     return create(implType, processHandlerAnnotation, invoker, serviceName, portName, container, binding, primaryWsdl, metadata, resolver, isTransportSynchronous, true);
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
/*     */   public static <T> WSEndpoint<T> create(@NotNull Class<T> implType, boolean processHandlerAnnotation, @Nullable Invoker invoker, @Nullable QName serviceName, @Nullable QName portName, @Nullable Container container, @Nullable WSBinding binding, @Nullable SDDocumentSource primaryWsdl, @Nullable Collection<? extends SDDocumentSource> metadata, @Nullable EntityResolver resolver, boolean isTransportSynchronous, boolean isStandard) {
/* 563 */     WSEndpoint<T> endpoint = EndpointFactory.createEndpoint(implType, processHandlerAnnotation, invoker, serviceName, portName, container, binding, primaryWsdl, metadata, resolver, isTransportSynchronous, isStandard);
/*     */ 
/*     */     
/* 566 */     Iterator<ManagedEndpointFactory> managementFactories = ServiceFinder.find(ManagedEndpointFactory.class).iterator();
/* 567 */     if (managementFactories.hasNext()) {
/* 568 */       ManagedEndpointFactory managementFactory = managementFactories.next();
/* 569 */       EndpointCreationAttributes attributes = new EndpointCreationAttributes(processHandlerAnnotation, invoker, resolver, isTransportSynchronous);
/*     */ 
/*     */       
/* 572 */       WSEndpoint<T> managedEndpoint = managementFactory.createEndpoint(endpoint, attributes);
/*     */       
/* 574 */       if (endpoint.getAssemblerContext().getTerminalTube() instanceof EndpointAwareTube) {
/* 575 */         ((EndpointAwareTube)endpoint.getAssemblerContext().getTerminalTube()).setEndpoint(managedEndpoint);
/*     */       }
/*     */       
/* 578 */       return managedEndpoint;
/*     */     } 
/*     */ 
/*     */     
/* 582 */     return endpoint;
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
/*     */   @Deprecated
/*     */   public static <T> WSEndpoint<T> create(@NotNull Class<T> implType, boolean processHandlerAnnotation, @Nullable Invoker invoker, @Nullable QName serviceName, @Nullable QName portName, @Nullable Container container, @Nullable WSBinding binding, @Nullable SDDocumentSource primaryWsdl, @Nullable Collection<? extends SDDocumentSource> metadata, @Nullable EntityResolver resolver) {
/* 600 */     return create(implType, processHandlerAnnotation, invoker, serviceName, portName, container, binding, primaryWsdl, metadata, resolver, false);
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
/*     */   public static <T> WSEndpoint<T> create(@NotNull Class<T> implType, boolean processHandlerAnnotation, @Nullable Invoker invoker, @Nullable QName serviceName, @Nullable QName portName, @Nullable Container container, @Nullable WSBinding binding, @Nullable SDDocumentSource primaryWsdl, @Nullable Collection<? extends SDDocumentSource> metadata, @Nullable URL catalogUrl) {
/* 624 */     return create(implType, processHandlerAnnotation, invoker, serviceName, portName, container, binding, primaryWsdl, metadata, 
/*     */         
/* 626 */         XmlUtil.createEntityResolver(catalogUrl), false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static QName getDefaultServiceName(Class endpointClass) {
/* 633 */     return getDefaultServiceName(endpointClass, true, null);
/*     */   } @NotNull
/*     */   public static QName getDefaultServiceName(Class endpointClass, MetadataReader metadataReader) {
/* 636 */     return getDefaultServiceName(endpointClass, true, metadataReader);
/*     */   }
/*     */   @NotNull
/*     */   public static QName getDefaultServiceName(Class endpointClass, boolean isStandard) {
/* 640 */     return getDefaultServiceName(endpointClass, isStandard, null);
/*     */   } @NotNull
/*     */   public static QName getDefaultServiceName(Class endpointClass, boolean isStandard, MetadataReader metadataReader) {
/* 643 */     return EndpointFactory.getDefaultServiceName(endpointClass, isStandard, metadataReader);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static QName getDefaultPortName(@NotNull QName serviceName, Class endpointClass) {
/* 650 */     return getDefaultPortName(serviceName, endpointClass, (MetadataReader)null);
/*     */   } @NotNull
/*     */   public static QName getDefaultPortName(@NotNull QName serviceName, Class endpointClass, MetadataReader metadataReader) {
/* 653 */     return getDefaultPortName(serviceName, endpointClass, true, metadataReader);
/*     */   }
/*     */   @NotNull
/*     */   public static QName getDefaultPortName(@NotNull QName serviceName, Class endpointClass, boolean isStandard) {
/* 657 */     return getDefaultPortName(serviceName, endpointClass, isStandard, null);
/*     */   } @NotNull
/*     */   public static QName getDefaultPortName(@NotNull QName serviceName, Class endpointClass, boolean isStandard, MetadataReader metadataReader) {
/* 660 */     return EndpointFactory.getDefaultPortName(serviceName, endpointClass, isStandard, metadataReader);
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
/*     */   public abstract <T extends javax.xml.ws.EndpointReference> T getEndpointReference(Class<T> paramClass, String paramString1, String paramString2, Element... paramVarArgs);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract <T extends javax.xml.ws.EndpointReference> T getEndpointReference(Class<T> paramClass, String paramString1, String paramString2, List<Element> paramList1, List<Element> paramList2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equalsProxiedInstance(WSEndpoint endpoint) {
/* 694 */     if (endpoint == null) return false; 
/* 695 */     return equals(endpoint);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public abstract OperationDispatcher getOperationDispatcher();
/*     */   
/*     */   public abstract Packet createServiceResponseForException(ThrowableContainerPropertySet paramThrowableContainerPropertySet, Packet paramPacket, SOAPVersion paramSOAPVersion, WSDLPort paramWSDLPort, SEIModel paramSEIModel, WSBinding paramWSBinding);
/*     */   
/*     */   public static interface PipeHead {
/*     */     @NotNull
/*     */     Packet process(@NotNull Packet param1Packet, @Nullable WebServiceContextDelegate param1WebServiceContextDelegate, @Nullable TransportBackChannel param1TransportBackChannel);
/*     */   }
/*     */   
/*     */   public static interface CompletionCallback {
/*     */     void onCompletion(@NotNull Packet param1Packet);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/server/WSEndpoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */