/*     */ package com.sun.xml.internal.ws.api.server;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.server.sei.Invoker;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import javax.xml.ws.Provider;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Invoker
/*     */   extends Invoker
/*     */ {
/*     */   private static final Method invokeMethod;
/*     */   private static final Method asyncInvokeMethod;
/*     */   
/*     */   public void start(@NotNull WSWebServiceContext wsc, @NotNull WSEndpoint endpoint) {
/*  64 */     start(wsc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start(@NotNull WebServiceContext wsc) {
/*  72 */     throw new IllegalStateException("deprecated version called");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T invokeProvider(@NotNull Packet p, T arg) throws IllegalAccessException, InvocationTargetException {
/*  91 */     return (T)invoke(p, invokeMethod, new Object[] { arg });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> void invokeAsyncProvider(@NotNull Packet p, T arg, AsyncProviderCallback cbak, WebServiceContext ctxt) throws IllegalAccessException, InvocationTargetException {
/*  99 */     invoke(p, asyncInvokeMethod, new Object[] { arg, cbak, ctxt });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 106 */       invokeMethod = Provider.class.getMethod("invoke", new Class[] { Object.class });
/* 107 */     } catch (NoSuchMethodException e) {
/* 108 */       throw new AssertionError(e);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 116 */       asyncInvokeMethod = AsyncProvider.class.getMethod("invoke", new Class[] { Object.class, AsyncProviderCallback.class, WebServiceContext.class });
/* 117 */     } catch (NoSuchMethodException e) {
/* 118 */       throw new AssertionError(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/server/Invoker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */