/*     */ package com.sun.xml.internal.ws.server;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*     */ import com.sun.xml.internal.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.internal.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.internal.ws.api.server.AsyncProviderCallback;
/*     */ import com.sun.xml.internal.ws.api.server.Invoker;
/*     */ import com.sun.xml.internal.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.internal.ws.api.server.WSWebServiceContext;
/*     */ import com.sun.xml.internal.ws.resources.ServerMessages;
/*     */ import com.sun.xml.internal.ws.server.sei.Invoker;
/*     */ import com.sun.xml.internal.ws.server.sei.InvokerTube;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import javax.xml.ws.WebServiceContext;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class InvokerTube<T>
/*     */   extends InvokerTube<Invoker>
/*     */   implements EndpointAwareTube
/*     */ {
/*     */   private WSEndpoint endpoint;
/*     */   
/*     */   protected InvokerTube(Invoker invoker) {
/*  57 */     super((Invoker)invoker);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 129 */     this.wrapper = new Invoker()
/*     */       {
/*     */         public Object invoke(Packet p, Method m, Object... args) throws InvocationTargetException, IllegalAccessException {
/* 132 */           Packet old = set(p);
/*     */           try {
/* 134 */             return ((Invoker)InvokerTube.this.invoker).invoke(p, m, args);
/*     */           } finally {
/* 136 */             set(old);
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public <T> T invokeProvider(Packet p, T arg) throws IllegalAccessException, InvocationTargetException {
/* 142 */           Packet old = set(p);
/*     */           try {
/* 144 */             return (T)((Invoker)InvokerTube.this.invoker).invokeProvider(p, arg);
/*     */           } finally {
/* 146 */             set(old);
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public <T> void invokeAsyncProvider(Packet p, T arg, AsyncProviderCallback cbak, WebServiceContext ctxt) throws IllegalAccessException, InvocationTargetException {
/* 152 */           Packet old = set(p);
/*     */           try {
/* 154 */             ((Invoker)InvokerTube.this.invoker).invokeAsyncProvider(p, arg, cbak, ctxt);
/*     */           } finally {
/* 156 */             set(old);
/*     */           } 
/*     */         }
/*     */         
/*     */         private Packet set(Packet p) {
/* 161 */           Packet old = InvokerTube.packets.get();
/* 162 */           InvokerTube.packets.set(p);
/* 163 */           return old;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public void setEndpoint(WSEndpoint endpoint) {
/*     */     this.endpoint = endpoint;
/*     */     WSWebServiceContext webServiceContext = new AbstractWebServiceContext(endpoint) {
/*     */         @Nullable
/*     */         public Packet getRequestPacket() {
/*     */           Packet p = InvokerTube.packets.get();
/*     */           return p;
/*     */         }
/*     */       };
/*     */     ((Invoker)this.invoker).start(webServiceContext, endpoint);
/*     */   }
/*     */   
/*     */   protected WSEndpoint getEndpoint() {
/*     */     return this.endpoint;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public final Invoker getInvoker(Packet request) {
/*     */     return this.wrapper;
/*     */   }
/*     */   
/*     */   public final AbstractTubeImpl copy(TubeCloner cloner) {
/*     */     cloner.add(this, this);
/*     */     return (AbstractTubeImpl)this;
/*     */   }
/*     */   
/*     */   public void preDestroy() {
/*     */     ((Invoker)this.invoker).dispose();
/*     */   }
/*     */   
/*     */   private static final ThreadLocal<Packet> packets = new ThreadLocal<>();
/*     */   private final Invoker wrapper;
/*     */   
/*     */   @NotNull
/*     */   public static Packet getCurrentPacket() {
/*     */     Packet packet = packets.get();
/*     */     if (packet == null)
/*     */       throw new WebServiceException(ServerMessages.NO_CURRENT_PACKET()); 
/*     */     return packet;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/server/InvokerTube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */