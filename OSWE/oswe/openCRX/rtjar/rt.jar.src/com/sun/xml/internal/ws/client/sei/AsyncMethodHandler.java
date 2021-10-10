/*     */ package com.sun.xml.internal.ws.client.sei;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.databinding.JavaCallInfo;
/*     */ import com.oracle.webservices.internal.api.message.MessageContext;
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.internal.ws.client.AsyncInvoker;
/*     */ import com.sun.xml.internal.ws.client.AsyncResponseImpl;
/*     */ import com.sun.xml.internal.ws.client.RequestContext;
/*     */ import com.sun.xml.internal.ws.client.ResponseContext;
/*     */ import java.lang.reflect.Method;
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
/*     */ abstract class AsyncMethodHandler
/*     */   extends MethodHandler
/*     */ {
/*     */   AsyncMethodHandler(SEIStub owner, Method m) {
/*  55 */     super(owner, m);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Response<Object> doInvoke(Object proxy, Object[] args, AsyncHandler handler) {
/* 128 */     AsyncInvoker invoker = new SEIAsyncInvoker(proxy, args);
/* 129 */     invoker.setNonNullAsyncHandlerGiven((handler != null));
/* 130 */     AsyncResponseImpl<Object> ft = new AsyncResponseImpl((Runnable)invoker, handler);
/* 131 */     invoker.setReceiver(ft);
/* 132 */     ft.run();
/* 133 */     return (Response<Object>)ft;
/*     */   }
/*     */   
/*     */   private class SEIAsyncInvoker
/*     */     extends AsyncInvoker
/*     */   {
/* 139 */     private final RequestContext rc = AsyncMethodHandler.this.owner.requestContext.copy();
/*     */     private final Object[] args;
/*     */     
/*     */     SEIAsyncInvoker(Object proxy, Object[] args) {
/* 143 */       this.args = args;
/*     */     }
/*     */     
/*     */     public void do_run() {
/* 147 */       JavaCallInfo call = AsyncMethodHandler.this.owner.databinding.createJavaCallInfo(AsyncMethodHandler.this.method, this.args);
/* 148 */       Packet req = (Packet)AsyncMethodHandler.this.owner.databinding.serializeRequest(call);
/*     */       
/* 150 */       Fiber.CompletionCallback callback = new Fiber.CompletionCallback()
/*     */         {
/*     */           public void onCompletion(@NotNull Packet response) {
/* 153 */             AsyncMethodHandler.SEIAsyncInvoker.this.responseImpl.setResponseContext(new ResponseContext(response));
/* 154 */             Message msg = response.getMessage();
/* 155 */             if (msg == null) {
/*     */               return;
/*     */             }
/*     */             try {
/* 159 */               Object[] rargs = new Object[1];
/* 160 */               JavaCallInfo call = AsyncMethodHandler.this.owner.databinding.createJavaCallInfo(AsyncMethodHandler.this.method, rargs);
/* 161 */               call = AsyncMethodHandler.this.owner.databinding.deserializeResponse((MessageContext)response, call);
/* 162 */               if (call.getException() != null) {
/* 163 */                 throw call.getException();
/*     */               }
/* 165 */               AsyncMethodHandler.SEIAsyncInvoker.this.responseImpl.set(rargs[0], null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             }
/* 180 */             catch (Throwable t) {
/* 181 */               if (t instanceof RuntimeException) {
/* 182 */                 if (t instanceof WebServiceException) {
/* 183 */                   AsyncMethodHandler.SEIAsyncInvoker.this.responseImpl.set(null, t);
/*     */                   return;
/*     */                 } 
/* 186 */               } else if (t instanceof Exception) {
/* 187 */                 AsyncMethodHandler.SEIAsyncInvoker.this.responseImpl.set(null, t);
/*     */                 
/*     */                 return;
/*     */               } 
/*     */               
/* 192 */               AsyncMethodHandler.SEIAsyncInvoker.this.responseImpl.set(null, (Throwable)new WebServiceException(t));
/*     */             } 
/*     */           }
/*     */ 
/*     */           
/*     */           public void onCompletion(@NotNull Throwable error) {
/* 198 */             if (error instanceof WebServiceException) {
/* 199 */               AsyncMethodHandler.SEIAsyncInvoker.this.responseImpl.set(null, error);
/*     */             }
/*     */             else {
/*     */               
/* 203 */               AsyncMethodHandler.SEIAsyncInvoker.this.responseImpl.set(null, (Throwable)new WebServiceException(error));
/*     */             } 
/*     */           }
/*     */         };
/* 207 */       AsyncMethodHandler.this.owner.doProcessAsync(this.responseImpl, req, this.rc, callback);
/*     */     }
/*     */   }
/*     */   
/*     */   ValueGetterFactory getValueGetterFactory() {
/* 212 */     return ValueGetterFactory.ASYNC;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/sei/AsyncMethodHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */