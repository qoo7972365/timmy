/*     */ package com.sun.xml.internal.ws.client;
/*     */ 
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.Cancelable;
/*     */ import com.sun.xml.internal.ws.util.CompletedFuture;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.FutureTask;
/*     */ import javax.xml.ws.AsyncHandler;
/*     */ import javax.xml.ws.Response;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AsyncResponseImpl<T>
/*     */   extends FutureTask<T>
/*     */   implements Response<T>, ResponseContextReceiver
/*     */ {
/*     */   private final AsyncHandler<T> handler;
/*     */   private ResponseContext responseContext;
/*     */   private final Runnable callable;
/*     */   private Cancelable cancelable;
/*     */   
/*     */   public AsyncResponseImpl(Runnable runnable, @Nullable AsyncHandler<T> handler) {
/*  65 */     super(runnable, null);
/*  66 */     this.callable = runnable;
/*  67 */     this.handler = handler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/*  75 */       this.callable.run();
/*  76 */     } catch (WebServiceException e) {
/*     */ 
/*     */       
/*  79 */       set(null, (Throwable)e);
/*  80 */     } catch (Throwable e) {
/*     */ 
/*     */       
/*  83 */       set(null, (Throwable)new WebServiceException(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ResponseContext getContext() {
/*  89 */     return this.responseContext;
/*     */   }
/*     */   
/*     */   public void setResponseContext(ResponseContext rc) {
/*  93 */     this.responseContext = rc;
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(T v, Throwable t) {
/*  98 */     if (this.handler != null)
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 116 */         this.handler.handleResponse(new CallbackFuture<>(v, t));
/* 117 */       } catch (Throwable e) {
/* 118 */         setException(e); return;
/*     */       }   class CallbackFuture<T> extends CompletedFuture<T> implements Response<T> {
/*     */       public CallbackFuture(T v, Throwable t) { super(v, t); }
/*     */       
/* 122 */       public Map<String, Object> getContext() { return AsyncResponseImpl.this.getContext(); } }; if (t != null) {
/* 123 */       setException(t);
/*     */     } else {
/* 125 */       set((V)v);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setCancelable(Cancelable cancelable) {
/* 130 */     this.cancelable = cancelable;
/*     */   }
/*     */   
/*     */   public boolean cancel(boolean mayInterruptIfRunning) {
/* 134 */     if (this.cancelable != null)
/* 135 */       this.cancelable.cancel(mayInterruptIfRunning); 
/* 136 */     return super.cancel(mayInterruptIfRunning);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/AsyncResponseImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */