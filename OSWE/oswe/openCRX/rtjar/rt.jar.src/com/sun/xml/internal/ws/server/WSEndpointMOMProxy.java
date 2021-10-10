/*     */ package com.sun.xml.internal.ws.server;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.org.glassfish.gmbal.AMXClient;
/*     */ import com.sun.org.glassfish.gmbal.GmbalMBean;
/*     */ import com.sun.org.glassfish.gmbal.ManagedObjectManager;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.api.pipe.Codec;
/*     */ import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptor;
/*     */ import com.sun.xml.internal.ws.api.pipe.ServerTubeAssemblerContext;
/*     */ import com.sun.xml.internal.ws.api.pipe.ThrowableContainerPropertySet;
/*     */ import com.sun.xml.internal.ws.api.server.Container;
/*     */ import com.sun.xml.internal.ws.api.server.ServiceDefinition;
/*     */ import com.sun.xml.internal.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.internal.ws.policy.PolicyMap;
/*     */ import com.sun.xml.internal.ws.wsdl.OperationDispatcher;
/*     */ import java.io.IOException;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.AnnotatedElement;
/*     */ import java.util.List;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Executor;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.ObjectName;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSEndpointMOMProxy
/*     */   extends WSEndpoint
/*     */   implements ManagedObjectManager
/*     */ {
/*     */   @NotNull
/*     */   private final WSEndpointImpl wsEndpoint;
/*     */   private ManagedObjectManager managedObjectManager;
/*     */   
/*     */   WSEndpointMOMProxy(@NotNull WSEndpointImpl wsEndpoint) {
/*  73 */     this.wsEndpoint = wsEndpoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ManagedObjectManager getManagedObjectManager() {
/*  83 */     if (this.managedObjectManager == null) {
/*  84 */       this.managedObjectManager = this.wsEndpoint.obtainManagedObjectManager();
/*     */     }
/*  86 */     return this.managedObjectManager;
/*     */   }
/*     */   
/*     */   void setManagedObjectManager(ManagedObjectManager managedObjectManager) {
/*  90 */     this.managedObjectManager = managedObjectManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInitialized() {
/* 100 */     return (this.managedObjectManager != null);
/*     */   }
/*     */   
/*     */   public WSEndpointImpl getWsEndpoint() {
/* 104 */     return this.wsEndpoint;
/*     */   }
/*     */ 
/*     */   
/*     */   public void suspendJMXRegistration() {
/* 109 */     getManagedObjectManager().suspendJMXRegistration();
/*     */   }
/*     */ 
/*     */   
/*     */   public void resumeJMXRegistration() {
/* 114 */     getManagedObjectManager().resumeJMXRegistration();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isManagedObject(Object obj) {
/* 119 */     return getManagedObjectManager().isManagedObject(obj);
/*     */   }
/*     */ 
/*     */   
/*     */   public GmbalMBean createRoot() {
/* 124 */     return getManagedObjectManager().createRoot();
/*     */   }
/*     */ 
/*     */   
/*     */   public GmbalMBean createRoot(Object root) {
/* 129 */     return getManagedObjectManager().createRoot(root);
/*     */   }
/*     */ 
/*     */   
/*     */   public GmbalMBean createRoot(Object root, String name) {
/* 134 */     return getManagedObjectManager().createRoot(root, name);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getRoot() {
/* 139 */     return getManagedObjectManager().getRoot();
/*     */   }
/*     */ 
/*     */   
/*     */   public GmbalMBean register(Object parent, Object obj, String name) {
/* 144 */     return getManagedObjectManager().register(parent, obj, name);
/*     */   }
/*     */ 
/*     */   
/*     */   public GmbalMBean register(Object parent, Object obj) {
/* 149 */     return getManagedObjectManager().register(parent, obj);
/*     */   }
/*     */ 
/*     */   
/*     */   public GmbalMBean registerAtRoot(Object obj, String name) {
/* 154 */     return getManagedObjectManager().registerAtRoot(obj, name);
/*     */   }
/*     */ 
/*     */   
/*     */   public GmbalMBean registerAtRoot(Object obj) {
/* 159 */     return getManagedObjectManager().registerAtRoot(obj);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unregister(Object obj) {
/* 164 */     getManagedObjectManager().unregister(obj);
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectName getObjectName(Object obj) {
/* 169 */     return getManagedObjectManager().getObjectName(obj);
/*     */   }
/*     */ 
/*     */   
/*     */   public AMXClient getAMXClient(Object obj) {
/* 174 */     return getManagedObjectManager().getAMXClient(obj);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getObject(ObjectName oname) {
/* 179 */     return getManagedObjectManager().getObject(oname);
/*     */   }
/*     */ 
/*     */   
/*     */   public void stripPrefix(String... str) {
/* 184 */     getManagedObjectManager().stripPrefix(str);
/*     */   }
/*     */ 
/*     */   
/*     */   public void stripPackagePrefix() {
/* 189 */     getManagedObjectManager().stripPackagePrefix();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDomain() {
/* 194 */     return getManagedObjectManager().getDomain();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMBeanServer(MBeanServer server) {
/* 199 */     getManagedObjectManager().setMBeanServer(server);
/*     */   }
/*     */ 
/*     */   
/*     */   public MBeanServer getMBeanServer() {
/* 204 */     return getManagedObjectManager().getMBeanServer();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setResourceBundle(ResourceBundle rb) {
/* 209 */     getManagedObjectManager().setResourceBundle(rb);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceBundle getResourceBundle() {
/* 214 */     return getManagedObjectManager().getResourceBundle();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAnnotation(AnnotatedElement element, Annotation annotation) {
/* 219 */     getManagedObjectManager().addAnnotation(element, annotation);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRegistrationDebug(ManagedObjectManager.RegistrationDebugLevel level) {
/* 224 */     getManagedObjectManager().setRegistrationDebug(level);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRuntimeDebug(boolean flag) {
/* 229 */     getManagedObjectManager().setRuntimeDebug(flag);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTypelibDebug(int level) {
/* 234 */     getManagedObjectManager().setTypelibDebug(level);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setJMXRegistrationDebug(boolean flag) {
/* 239 */     getManagedObjectManager().setJMXRegistrationDebug(flag);
/*     */   }
/*     */ 
/*     */   
/*     */   public String dumpSkeleton(Object obj) {
/* 244 */     return getManagedObjectManager().dumpSkeleton(obj);
/*     */   }
/*     */ 
/*     */   
/*     */   public void suppressDuplicateRootReport(boolean suppressReport) {
/* 249 */     getManagedObjectManager().suppressDuplicateRootReport(suppressReport);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 254 */     getManagedObjectManager().close();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsProxiedInstance(WSEndpoint endpoint) {
/* 259 */     if (this.wsEndpoint == null) {
/* 260 */       return (endpoint == null);
/*     */     }
/* 262 */     return this.wsEndpoint.equals(endpoint);
/*     */   }
/*     */ 
/*     */   
/*     */   public Codec createCodec() {
/* 267 */     return this.wsEndpoint.createCodec();
/*     */   }
/*     */ 
/*     */   
/*     */   public QName getServiceName() {
/* 272 */     return this.wsEndpoint.getServiceName();
/*     */   }
/*     */ 
/*     */   
/*     */   public QName getPortName() {
/* 277 */     return this.wsEndpoint.getPortName();
/*     */   }
/*     */ 
/*     */   
/*     */   public Class getImplementationClass() {
/* 282 */     return this.wsEndpoint.getImplementationClass();
/*     */   }
/*     */ 
/*     */   
/*     */   public WSBinding getBinding() {
/* 287 */     return this.wsEndpoint.getBinding();
/*     */   }
/*     */ 
/*     */   
/*     */   public Container getContainer() {
/* 292 */     return this.wsEndpoint.getContainer();
/*     */   }
/*     */ 
/*     */   
/*     */   public WSDLPort getPort() {
/* 297 */     return this.wsEndpoint.getPort();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setExecutor(Executor exec) {
/* 302 */     this.wsEndpoint.setExecutor(exec);
/*     */   }
/*     */ 
/*     */   
/*     */   public void schedule(Packet request, WSEndpoint.CompletionCallback callback, FiberContextSwitchInterceptor interceptor) {
/* 307 */     this.wsEndpoint.schedule(request, callback, interceptor);
/*     */   }
/*     */ 
/*     */   
/*     */   public WSEndpoint.PipeHead createPipeHead() {
/* 312 */     return this.wsEndpoint.createPipeHead();
/*     */   }
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 317 */     if (this.wsEndpoint != null) {
/* 318 */       this.wsEndpoint.dispose();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ServiceDefinition getServiceDefinition() {
/* 324 */     return this.wsEndpoint.getServiceDefinition();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set getComponentRegistry() {
/* 329 */     return this.wsEndpoint.getComponentRegistry();
/*     */   }
/*     */ 
/*     */   
/*     */   public SEIModel getSEIModel() {
/* 334 */     return this.wsEndpoint.getSEIModel();
/*     */   }
/*     */ 
/*     */   
/*     */   public PolicyMap getPolicyMap() {
/* 339 */     return this.wsEndpoint.getPolicyMap();
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeManagedObjectManager() {
/* 344 */     this.wsEndpoint.closeManagedObjectManager();
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerTubeAssemblerContext getAssemblerContext() {
/* 349 */     return this.wsEndpoint.getAssemblerContext();
/*     */   }
/*     */ 
/*     */   
/*     */   public EndpointReference getEndpointReference(Class<EndpointReference> clazz, String address, String wsdlAddress, Element... referenceParameters) {
/* 354 */     return this.wsEndpoint.getEndpointReference(clazz, address, wsdlAddress, referenceParameters);
/*     */   }
/*     */ 
/*     */   
/*     */   public EndpointReference getEndpointReference(Class<EndpointReference> clazz, String address, String wsdlAddress, List<Element> metadata, List<Element> referenceParameters) {
/* 359 */     return this.wsEndpoint.getEndpointReference(clazz, address, wsdlAddress, metadata, referenceParameters);
/*     */   }
/*     */ 
/*     */   
/*     */   public OperationDispatcher getOperationDispatcher() {
/* 364 */     return this.wsEndpoint.getOperationDispatcher();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet createServiceResponseForException(ThrowableContainerPropertySet tc, Packet responsePacket, SOAPVersion soapVersion, WSDLPort wsdlPort, SEIModel seiModel, WSBinding binding) {
/* 375 */     return this.wsEndpoint.createServiceResponseForException(tc, responsePacket, soapVersion, wsdlPort, seiModel, binding);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/server/WSEndpointMOMProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */