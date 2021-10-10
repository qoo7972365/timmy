/*     */ package com.sun.xml.internal.ws.assembler;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.EndpointAddress;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.WSService;
/*     */ import com.sun.xml.internal.ws.api.client.WSPortInfo;
/*     */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.api.pipe.ClientTubeAssemblerContext;
/*     */ import com.sun.xml.internal.ws.api.pipe.Codec;
/*     */ import com.sun.xml.internal.ws.api.server.Container;
/*     */ import com.sun.xml.internal.ws.assembler.dev.ClientTubelineAssemblyContext;
/*     */ import com.sun.xml.internal.ws.policy.PolicyMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DefaultClientTubelineAssemblyContext
/*     */   extends TubelineAssemblyContextImpl
/*     */   implements ClientTubelineAssemblyContext
/*     */ {
/*     */   @NotNull
/*     */   private final ClientTubeAssemblerContext wrappedContext;
/*     */   private final PolicyMap policyMap;
/*     */   private final WSPortInfo portInfo;
/*     */   private final WSDLPort wsdlPort;
/*     */   
/*     */   public DefaultClientTubelineAssemblyContext(@NotNull ClientTubeAssemblerContext context) {
/*  60 */     this.wrappedContext = context;
/*  61 */     this.wsdlPort = context.getWsdlModel();
/*  62 */     this.portInfo = context.getPortInfo();
/*  63 */     this.policyMap = context.getPortInfo().getPolicyMap();
/*     */   }
/*     */   
/*     */   public PolicyMap getPolicyMap() {
/*  67 */     return this.policyMap;
/*     */   }
/*     */   
/*     */   public boolean isPolicyAvailable() {
/*  71 */     return (this.policyMap != null && !this.policyMap.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WSDLPort getWsdlPort() {
/*  82 */     return this.wsdlPort;
/*     */   }
/*     */   
/*     */   public WSPortInfo getPortInfo() {
/*  86 */     return this.portInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public EndpointAddress getAddress() {
/*  95 */     return this.wrappedContext.getAddress();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public WSService getService() {
/* 104 */     return this.wrappedContext.getService();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public WSBinding getBinding() {
/* 111 */     return this.wrappedContext.getBinding();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SEIModel getSEIModel() {
/* 121 */     return this.wrappedContext.getSEIModel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Container getContainer() {
/* 130 */     return this.wrappedContext.getContainer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Codec getCodec() {
/* 140 */     return this.wrappedContext.getCodec();
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
/*     */   public void setCodec(@NotNull Codec codec) {
/* 155 */     this.wrappedContext.setCodec(codec);
/*     */   }
/*     */   
/*     */   public ClientTubeAssemblerContext getWrappedContext() {
/* 159 */     return this.wrappedContext;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/assembler/DefaultClientTubelineAssemblyContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */