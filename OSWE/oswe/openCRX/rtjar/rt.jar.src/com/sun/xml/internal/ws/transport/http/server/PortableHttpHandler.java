/*     */ package com.sun.xml.internal.ws.transport.http.server;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.resources.HttpserverMessages;
/*     */ import com.sun.xml.internal.ws.transport.http.HttpAdapter;
/*     */ import com.sun.xml.internal.ws.transport.http.WSHTTPConnection;
/*     */ import java.io.IOException;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.ws.spi.http.HttpExchange;
/*     */ import javax.xml.ws.spi.http.HttpHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class PortableHttpHandler
/*     */   extends HttpHandler
/*     */ {
/*     */   private static final String GET_METHOD = "GET";
/*     */   private static final String POST_METHOD = "POST";
/*     */   private static final String HEAD_METHOD = "HEAD";
/*     */   private static final String PUT_METHOD = "PUT";
/*     */   private static final String DELETE_METHOD = "DELETE";
/*  55 */   private static final Logger logger = Logger.getLogger("com.sun.xml.internal.ws.server.http");
/*     */   
/*     */   private final HttpAdapter adapter;
/*     */   
/*     */   private final Executor executor;
/*     */   
/*     */   public PortableHttpHandler(@NotNull HttpAdapter adapter, @Nullable Executor executor) {
/*  62 */     assert adapter != null;
/*  63 */     this.adapter = adapter;
/*  64 */     this.executor = executor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handle(HttpExchange msg) {
/*     */     try {
/*  73 */       if (logger.isLoggable(Level.FINE)) {
/*  74 */         logger.log(Level.FINE, "Received HTTP request:{0}", msg.getRequestURI());
/*     */       }
/*  76 */       if (this.executor != null) {
/*     */ 
/*     */         
/*  79 */         this.executor.execute(new HttpHandlerRunnable(msg));
/*     */       } else {
/*  81 */         handleExchange(msg);
/*     */       } 
/*  83 */     } catch (Throwable e) {
/*     */       
/*  85 */       logger.log(Level.SEVERE, (String)null, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void handleExchange(HttpExchange msg) throws IOException {
/*  90 */     WSHTTPConnection con = new PortableConnectionImpl(this.adapter, msg);
/*     */     try {
/*  92 */       if (logger.isLoggable(Level.FINE)) {
/*  93 */         logger.log(Level.FINE, "Received HTTP request:{0}", msg.getRequestURI());
/*     */       }
/*  95 */       String method = msg.getRequestMethod();
/*  96 */       if (method.equals("GET") || method.equals("POST") || method.equals("HEAD") || method
/*  97 */         .equals("PUT") || method.equals("DELETE")) {
/*  98 */         this.adapter.handle(con);
/*     */       } else {
/* 100 */         logger.warning(HttpserverMessages.UNEXPECTED_HTTP_METHOD(method));
/*     */       } 
/*     */     } finally {
/* 103 */       msg.close();
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
/* 115 */       this.msg = msg;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/*     */       try {
/* 122 */         PortableHttpHandler.this.handleExchange(this.msg);
/* 123 */       } catch (Throwable e) {
/*     */         
/* 125 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/transport/http/server/PortableHttpHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */