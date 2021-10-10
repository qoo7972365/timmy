/*     */ package com.sun.xml.internal.ws.server.provider;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.internal.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.internal.ws.api.pipe.ThrowableContainerPropertySet;
/*     */ import com.sun.xml.internal.ws.api.server.AsyncProviderCallback;
/*     */ import com.sun.xml.internal.ws.api.server.Invoker;
/*     */ import com.sun.xml.internal.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.internal.ws.server.AbstractWebServiceContext;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.ws.WebServiceContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AsyncProviderInvokerTube<T>
/*     */   extends ProviderInvokerTube<T>
/*     */ {
/*  52 */   private static final Logger LOGGER = Logger.getLogger("com.sun.xml.internal.ws.server.AsyncProviderInvokerTube");
/*     */ 
/*     */   
/*     */   public AsyncProviderInvokerTube(Invoker invoker, ProviderArgumentsBuilder<T> argsBuilder) {
/*  56 */     super(invoker, argsBuilder);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public NextAction processRequest(@NotNull Packet request) {
/*  66 */     T param = this.argsBuilder.getParameter(request);
/*  67 */     NoSuspendResumer resumer = new NoSuspendResumer();
/*     */     
/*  69 */     AsyncProviderCallbackImpl callback = new AsyncProviderCallbackImpl(request, resumer);
/*  70 */     AsyncWebServiceContext ctxt = new AsyncWebServiceContext(getEndpoint(), request);
/*     */     
/*  72 */     LOGGER.fine("Invoking AsyncProvider Endpoint");
/*     */     try {
/*  74 */       getInvoker(request).invokeAsyncProvider(request, param, callback, (WebServiceContext)ctxt);
/*  75 */     } catch (Throwable e) {
/*  76 */       LOGGER.log(Level.SEVERE, e.getMessage(), e);
/*  77 */       return doThrow(e);
/*     */     } 
/*     */     
/*  80 */     synchronized (callback) {
/*  81 */       if (resumer.response != null) {
/*     */ 
/*     */         
/*  84 */         ThrowableContainerPropertySet tc = (ThrowableContainerPropertySet)resumer.response.getSatellite(ThrowableContainerPropertySet.class);
/*  85 */         Throwable t = (tc != null) ? tc.getThrowable() : null;
/*     */         
/*  87 */         return (t != null) ? doThrow(resumer.response, t) : doReturnWith(resumer.response);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  92 */       callback.resumer = new FiberResumer();
/*  93 */       return doSuspend();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static interface Resumer
/*     */   {
/*     */     void onResume(Packet param1Packet);
/*     */   }
/*     */   
/*     */   public class FiberResumer
/*     */     implements Resumer
/*     */   {
/* 105 */     private final Fiber fiber = Fiber.current();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void onResume(Packet response) {
/* 111 */       ThrowableContainerPropertySet tc = (ThrowableContainerPropertySet)response.getSatellite(ThrowableContainerPropertySet.class);
/* 112 */       Throwable t = (tc != null) ? tc.getThrowable() : null;
/* 113 */       this.fiber.resume(t, response);
/*     */     }
/*     */   }
/*     */   
/*     */   private class NoSuspendResumer implements Resumer {
/* 118 */     protected Packet response = null;
/*     */     
/*     */     public void onResume(Packet response) {
/* 121 */       this.response = response;
/*     */     }
/*     */     
/*     */     private NoSuspendResumer() {} }
/*     */   
/*     */   public class AsyncProviderCallbackImpl implements AsyncProviderCallback<T> {
/*     */     private final Packet request;
/*     */     
/*     */     public AsyncProviderCallbackImpl(Packet request, AsyncProviderInvokerTube.Resumer resumer) {
/* 130 */       this.request = request;
/* 131 */       this.resumer = resumer;
/*     */     }
/*     */     private AsyncProviderInvokerTube.Resumer resumer;
/*     */     public void send(@Nullable T param) {
/* 135 */       if (param == null && 
/* 136 */         this.request.transportBackChannel != null) {
/* 137 */         this.request.transportBackChannel.close();
/*     */       }
/*     */       
/* 140 */       Packet packet = AsyncProviderInvokerTube.this.argsBuilder.getResponse(this.request, param, AsyncProviderInvokerTube.this.getEndpoint().getPort(), AsyncProviderInvokerTube.this.getEndpoint().getBinding());
/* 141 */       synchronized (this) {
/* 142 */         this.resumer.onResume(packet);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void sendError(@NotNull Throwable t) {
/*     */       Exception e;
/* 148 */       if (t instanceof Exception) {
/* 149 */         e = (Exception)t;
/*     */       } else {
/* 151 */         e = new RuntimeException(t);
/*     */       } 
/* 153 */       Packet packet = AsyncProviderInvokerTube.this.argsBuilder.getResponse(this.request, e, AsyncProviderInvokerTube.this.getEndpoint().getPort(), AsyncProviderInvokerTube.this.getEndpoint().getBinding());
/* 154 */       synchronized (this) {
/* 155 */         this.resumer.onResume(packet);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public class AsyncWebServiceContext
/*     */     extends AbstractWebServiceContext
/*     */   {
/*     */     final Packet packet;
/*     */     
/*     */     public AsyncWebServiceContext(WSEndpoint endpoint, Packet packet) {
/* 167 */       super(endpoint);
/* 168 */       this.packet = packet;
/*     */     }
/*     */     @NotNull
/*     */     public Packet getRequestPacket() {
/* 172 */       return this.packet;
/*     */     } }
/*     */   
/*     */   @NotNull
/*     */   public NextAction processResponse(@NotNull Packet response) {
/* 177 */     return doReturnWith(response);
/*     */   }
/*     */   @NotNull
/*     */   public NextAction processException(@NotNull Throwable t) {
/* 181 */     return doThrow(t);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/server/provider/AsyncProviderInvokerTube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */