/*     */ package com.sun.xml.internal.ws.client;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.BindingID;
/*     */ import com.sun.xml.internal.ws.api.EndpointAddress;
/*     */ import com.sun.xml.internal.ws.api.WSFeatureList;
/*     */ import com.sun.xml.internal.ws.api.WSService;
/*     */ import com.sun.xml.internal.ws.api.client.WSPortInfo;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.api.policy.PolicyResolver;
/*     */ import com.sun.xml.internal.ws.api.policy.PolicyResolverFactory;
/*     */ import com.sun.xml.internal.ws.binding.BindingImpl;
/*     */ import com.sun.xml.internal.ws.binding.WebServiceFeatureList;
/*     */ import com.sun.xml.internal.ws.policy.PolicyMap;
/*     */ import com.sun.xml.internal.ws.policy.jaxws.PolicyUtil;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PortInfo
/*     */   implements WSPortInfo
/*     */ {
/*     */   @NotNull
/*     */   private final WSServiceDelegate owner;
/*     */   @NotNull
/*     */   public final QName portName;
/*     */   @NotNull
/*     */   public final EndpointAddress targetEndpoint;
/*     */   @NotNull
/*     */   public final BindingID bindingId;
/*     */   @NotNull
/*     */   public final PolicyMap policyMap;
/*     */   @Nullable
/*     */   public final WSDLPort portModel;
/*     */   
/*     */   public PortInfo(WSServiceDelegate owner, EndpointAddress targetEndpoint, QName name, BindingID bindingId) {
/*  72 */     this.owner = owner;
/*  73 */     this.targetEndpoint = targetEndpoint;
/*  74 */     this.portName = name;
/*  75 */     this.bindingId = bindingId;
/*  76 */     this.portModel = getPortModel(owner, name);
/*  77 */     this.policyMap = createPolicyMap();
/*     */   }
/*     */ 
/*     */   
/*     */   public PortInfo(@NotNull WSServiceDelegate owner, @NotNull WSDLPort port) {
/*  82 */     this.owner = owner;
/*  83 */     this.targetEndpoint = port.getAddress();
/*  84 */     this.portName = port.getName();
/*  85 */     this.bindingId = port.getBinding().getBindingId();
/*  86 */     this.portModel = port;
/*  87 */     this.policyMap = createPolicyMap();
/*     */   }
/*     */   
/*     */   public PolicyMap getPolicyMap() {
/*  91 */     return this.policyMap;
/*     */   }
/*     */   
/*     */   public PolicyMap createPolicyMap() {
/*     */     PolicyMap map;
/*  96 */     if (this.portModel != null) {
/*  97 */       map = this.portModel.getOwner().getParent().getPolicyMap();
/*     */     } else {
/*  99 */       map = PolicyResolverFactory.create().resolve(new PolicyResolver.ClientContext(null, this.owner.getContainer()));
/*     */     } 
/*     */     
/* 102 */     if (map == null)
/* 103 */       map = PolicyMap.createPolicyMap(null); 
/* 104 */     return map;
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
/*     */   public BindingImpl createBinding(WebServiceFeature[] webServiceFeatures, Class<?> portInterface) {
/* 117 */     return createBinding(new WebServiceFeatureList(webServiceFeatures), portInterface, null);
/*     */   }
/*     */   
/*     */   public BindingImpl createBinding(WebServiceFeatureList webServiceFeatures, Class<?> portInterface, BindingImpl existingBinding) {
/*     */     Iterable<WebServiceFeature> configFeatures;
/* 122 */     if (existingBinding != null) {
/* 123 */       webServiceFeatures.addAll((Iterable)existingBinding.getFeatures());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     if (this.portModel != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 137 */       WSFeatureList wSFeatureList = this.portModel.getFeatures();
/*     */     } else {
/* 139 */       configFeatures = PolicyUtil.getPortScopedFeatures(this.policyMap, this.owner.getServiceName(), this.portName);
/*     */     } 
/* 141 */     webServiceFeatures.mergeFeatures(configFeatures, false);
/*     */ 
/*     */     
/* 144 */     webServiceFeatures.mergeFeatures(this.owner.serviceInterceptor.preCreateBinding(this, portInterface, (WSFeatureList)webServiceFeatures), false);
/*     */     
/* 146 */     BindingImpl bindingImpl = BindingImpl.create(this.bindingId, webServiceFeatures.toArray());
/* 147 */     this.owner.getHandlerConfigurator().configureHandlers(this, bindingImpl);
/* 148 */     return bindingImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private WSDLPort getPortModel(WSServiceDelegate owner, QName portName) {
/* 154 */     if (owner.getWsdlService() != null) {
/* 155 */       Iterable<? extends WSDLPort> ports = owner.getWsdlService().getPorts();
/* 156 */       for (WSDLPort port : ports) {
/* 157 */         if (port.getName().equals(portName))
/* 158 */           return port; 
/*     */       } 
/*     */     } 
/* 161 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public WSDLPort getPort() {
/* 170 */     return this.portModel;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public WSService getOwner() {
/* 175 */     return this.owner;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BindingID getBindingId() {
/* 180 */     return this.bindingId;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public EndpointAddress getEndpointAddress() {
/* 185 */     return this.targetEndpoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getServiceName() {
/* 194 */     return this.owner.getServiceName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getPortName() {
/* 202 */     return this.portName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBindingID() {
/* 211 */     return this.bindingId.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/PortInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */