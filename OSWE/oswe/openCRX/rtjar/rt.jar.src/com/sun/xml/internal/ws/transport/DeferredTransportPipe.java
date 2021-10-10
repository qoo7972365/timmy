/*     */ package com.sun.xml.internal.ws.transport;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.ws.api.EndpointAddress;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.pipe.ClientTubeAssemblerContext;
/*     */ import com.sun.xml.internal.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.internal.ws.api.pipe.TransportTubeFactory;
/*     */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*     */ import com.sun.xml.internal.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.internal.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.internal.ws.developer.HttpConfigFeature;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DeferredTransportPipe
/*     */   extends AbstractTubeImpl
/*     */ {
/*     */   private Tube transport;
/*     */   private EndpointAddress address;
/*     */   private final ClassLoader classLoader;
/*     */   private final ClientTubeAssemblerContext context;
/*     */   
/*     */   public DeferredTransportPipe(ClassLoader classLoader, ClientTubeAssemblerContext context) {
/*  64 */     this.classLoader = classLoader;
/*  65 */     this.context = context;
/*  66 */     if (context.getBinding().getFeature(HttpConfigFeature.class) == null) {
/*  67 */       context.getBinding().getFeatures().mergeFeatures(new WebServiceFeature[] { (WebServiceFeature)new HttpConfigFeature() }, false);
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*  72 */       this.transport = TransportTubeFactory.create(classLoader, context);
/*  73 */       this.address = context.getAddress();
/*  74 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DeferredTransportPipe(DeferredTransportPipe that, TubeCloner cloner) {
/*  80 */     super(that, cloner);
/*  81 */     this.classLoader = that.classLoader;
/*  82 */     this.context = that.context;
/*  83 */     if (that.transport != null) {
/*  84 */       this.transport = cloner.copy(that.transport);
/*  85 */       this.address = that.address;
/*     */     } 
/*     */   }
/*     */   public NextAction processException(@NotNull Throwable t) {
/*  89 */     return this.transport.processException(t);
/*     */   }
/*     */   
/*     */   public NextAction processRequest(@NotNull Packet request) {
/*  93 */     if (request.endpointAddress == this.address)
/*     */     {
/*  95 */       return this.transport.processRequest(request);
/*     */     }
/*     */ 
/*     */     
/*  99 */     if (this.transport != null) {
/*     */       
/* 101 */       this.transport.preDestroy();
/* 102 */       this.transport = null;
/* 103 */       this.address = null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     ClientTubeAssemblerContext newContext = new ClientTubeAssemblerContext(request.endpointAddress, this.context.getWsdlModel(), this.context.getBindingProvider(), this.context.getBinding(), this.context.getContainer(), this.context.getCodec().copy(), this.context.getSEIModel(), this.context.getSEI());
/*     */ 
/*     */     
/* 119 */     this.address = request.endpointAddress;
/* 120 */     this.transport = TransportTubeFactory.create(this.classLoader, newContext);
/*     */     
/* 122 */     assert this.transport != null;
/*     */     
/* 124 */     return this.transport.processRequest(request);
/*     */   }
/*     */   
/*     */   public NextAction processResponse(@NotNull Packet response) {
/* 128 */     if (this.transport != null)
/* 129 */       return this.transport.processResponse(response); 
/* 130 */     return doReturnWith(response);
/*     */   }
/*     */   
/*     */   public void preDestroy() {
/* 134 */     if (this.transport != null) {
/* 135 */       this.transport.preDestroy();
/* 136 */       this.transport = null;
/* 137 */       this.address = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public DeferredTransportPipe copy(TubeCloner cloner) {
/* 142 */     return new DeferredTransportPipe(this, cloner);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/transport/DeferredTransportPipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */