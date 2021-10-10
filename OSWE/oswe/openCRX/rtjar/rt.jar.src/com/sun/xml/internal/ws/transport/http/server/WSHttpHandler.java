/*     */ package com.sun.xml.internal.ws.transport.http.server;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.net.httpserver.HttpExchange;
/*     */ import com.sun.net.httpserver.HttpHandler;
/*     */ import com.sun.xml.internal.ws.resources.HttpserverMessages;
/*     */ import com.sun.xml.internal.ws.transport.http.HttpAdapter;
/*     */ import com.sun.xml.internal.ws.transport.http.WSHTTPConnection;
/*     */ import java.io.IOException;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class WSHttpHandler
/*     */   implements HttpHandler
/*     */ {
/*     */   private static final String GET_METHOD = "GET";
/*     */   private static final String POST_METHOD = "POST";
/*     */   private static final String HEAD_METHOD = "HEAD";
/*     */   private static final String PUT_METHOD = "PUT";
/*     */   private static final String DELETE_METHOD = "DELETE";
/*  56 */   private static final Logger LOGGER = Logger.getLogger("com.sun.xml.internal.ws.server.http");
/*     */   
/*  58 */   private static final boolean fineTraceEnabled = LOGGER.isLoggable(Level.FINE);
/*     */   
/*     */   private final HttpAdapter adapter;
/*     */   private final Executor executor;
/*     */   
/*     */   public WSHttpHandler(@NotNull HttpAdapter adapter, @Nullable Executor executor) {
/*  64 */     assert adapter != null;
/*  65 */     this.adapter = adapter;
/*  66 */     this.executor = executor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handle(HttpExchange msg) {
/*     */     try {
/*  74 */       if (fineTraceEnabled) {
/*  75 */         LOGGER.log(Level.FINE, "Received HTTP request:{0}", msg.getRequestURI());
/*     */       }
/*  77 */       if (this.executor != null) {
/*     */ 
/*     */         
/*  80 */         this.executor.execute(new HttpHandlerRunnable(msg));
/*     */       } else {
/*  82 */         handleExchange(msg);
/*     */       } 
/*  84 */     } catch (Throwable throwable) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleExchange(HttpExchange msg) throws IOException {
/*  90 */     WSHTTPConnection con = new ServerConnectionImpl(this.adapter, msg);
/*     */     try {
/*  92 */       if (fineTraceEnabled) {
/*  93 */         LOGGER.log(Level.FINE, "Received HTTP request:{0}", msg.getRequestURI());
/*     */       }
/*  95 */       String method = msg.getRequestMethod();
/*  96 */       if (method.equals("GET") || method.equals("POST") || method.equals("HEAD") || method
/*  97 */         .equals("PUT") || method.equals("DELETE")) {
/*  98 */         this.adapter.handle(con);
/*     */       }
/* 100 */       else if (LOGGER.isLoggable(Level.WARNING)) {
/* 101 */         LOGGER.warning(HttpserverMessages.UNEXPECTED_HTTP_METHOD(method));
/*     */       } 
/*     */     } finally {
/*     */       
/* 105 */       msg.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   class HttpHandlerRunnable
/*     */     implements Runnable
/*     */   {
/*     */     final HttpExchange msg;
/*     */ 
/*     */     
/*     */     HttpHandlerRunnable(HttpExchange msg) {
/* 117 */       this.msg = msg;
/*     */     }
/*     */     
/*     */     public void run() {
/*     */       try {
/* 122 */         WSHttpHandler.this.handleExchange(this.msg);
/* 123 */       } catch (Throwable e) {
/*     */         
/* 125 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/transport/http/server/WSHttpHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */