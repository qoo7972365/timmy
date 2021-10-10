/*     */ package com.sun.xml.internal.ws.api.addressing;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.WSService;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.pipe.ClientTubeAssemblerContext;
/*     */ import com.sun.xml.internal.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.internal.ws.api.pipe.TransportTubeFactory;
/*     */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*     */ import com.sun.xml.internal.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.internal.ws.binding.BindingImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NonAnonymousResponseProcessor
/*     */ {
/*  45 */   private static final NonAnonymousResponseProcessor DEFAULT = new NonAnonymousResponseProcessor();
/*     */   
/*     */   public static NonAnonymousResponseProcessor getDefault() {
/*  48 */     return DEFAULT;
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
/*     */   public Packet process(Packet packet) {
/*  62 */     Fiber.CompletionCallback fiberCallback = null;
/*  63 */     Fiber currentFiber = Fiber.getCurrentIfSet();
/*  64 */     if (currentFiber != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  71 */       final Fiber.CompletionCallback currentFiberCallback = currentFiber.getCompletionCallback();
/*     */       
/*  73 */       if (currentFiberCallback != null) {
/*  74 */         fiberCallback = new Fiber.CompletionCallback() {
/*     */             public void onCompletion(@NotNull Packet response) {
/*  76 */               currentFiberCallback.onCompletion(response);
/*     */             }
/*     */             
/*     */             public void onCompletion(@NotNull Throwable error) {
/*  80 */               currentFiberCallback.onCompletion(error);
/*     */             }
/*     */           };
/*  83 */         currentFiber.setCompletionCallback(null);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  88 */     WSEndpoint<?> endpoint = packet.endpoint;
/*  89 */     WSBinding binding = endpoint.getBinding();
/*  90 */     Tube transport = TransportTubeFactory.create(Thread.currentThread().getContextClassLoader(), new ClientTubeAssemblerContext(packet.endpointAddress, endpoint
/*     */           
/*  92 */           .getPort(), (WSService)null, binding, endpoint
/*  93 */           .getContainer(), ((BindingImpl)binding)
/*  94 */           .createCodec(), null, null));
/*  95 */     Fiber fiber = endpoint.getEngine().createFiber();
/*  96 */     fiber.start(transport, packet, fiberCallback);
/*     */ 
/*     */     
/*  99 */     Packet copy = packet.copy(false);
/* 100 */     copy.endpointAddress = null;
/*     */     
/* 102 */     return copy;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/addressing/NonAnonymousResponseProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */