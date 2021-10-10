/*     */ package com.sun.xml.internal.ws.assembler;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.api.pipe.Codec;
/*     */ import com.sun.xml.internal.ws.api.pipe.ServerTubeAssemblerContext;
/*     */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*     */ import com.sun.xml.internal.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.internal.ws.assembler.dev.ServerTubelineAssemblyContext;
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
/*     */ 
/*     */ class DefaultServerTubelineAssemblyContext
/*     */   extends TubelineAssemblyContextImpl
/*     */   implements ServerTubelineAssemblyContext
/*     */ {
/*     */   @NotNull
/*     */   private final ServerTubeAssemblerContext wrappedContext;
/*     */   private final PolicyMap policyMap;
/*     */   
/*     */   public DefaultServerTubelineAssemblyContext(@NotNull ServerTubeAssemblerContext context) {
/*  56 */     this.wrappedContext = context;
/*  57 */     this.policyMap = context.getEndpoint().getPolicyMap();
/*     */   }
/*     */   
/*     */   public PolicyMap getPolicyMap() {
/*  61 */     return this.policyMap;
/*     */   }
/*     */   
/*     */   public boolean isPolicyAvailable() {
/*  65 */     return (this.policyMap != null && !this.policyMap.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SEIModel getSEIModel() {
/*  75 */     return this.wrappedContext.getSEIModel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public WSDLPort getWsdlPort() {
/*  85 */     return this.wrappedContext.getWsdlModel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public WSEndpoint getEndpoint() {
/*  96 */     return this.wrappedContext.getEndpoint();
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
/*     */   @NotNull
/*     */   public Tube getTerminalTube() {
/* 110 */     return this.wrappedContext.getTerminalTube();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSynchronous() {
/* 120 */     return this.wrappedContext.isSynchronous();
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
/*     */   public Codec getCodec() {
/* 133 */     return this.wrappedContext.getCodec();
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
/*     */   public void setCodec(@NotNull Codec codec) {
/* 155 */     this.wrappedContext.setCodec(codec);
/*     */   }
/*     */   
/*     */   public ServerTubeAssemblerContext getWrappedContext() {
/* 159 */     return this.wrappedContext;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/assembler/DefaultServerTubelineAssemblyContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */