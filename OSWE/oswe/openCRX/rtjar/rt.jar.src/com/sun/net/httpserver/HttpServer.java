/*     */ package com.sun.net.httpserver;
/*     */ 
/*     */ import com.sun.net.httpserver.spi.HttpServerProvider;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.util.concurrent.Executor;
/*     */ import jdk.Exported;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Exported
/*     */ public abstract class HttpServer
/*     */ {
/*     */   public static HttpServer create() throws IOException {
/* 105 */     return create(null, 0);
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
/*     */   public static HttpServer create(InetSocketAddress paramInetSocketAddress, int paramInt) throws IOException {
/* 129 */     HttpServerProvider httpServerProvider = HttpServerProvider.provider();
/* 130 */     return httpServerProvider.createHttpServer(paramInetSocketAddress, paramInt);
/*     */   }
/*     */   
/*     */   public abstract void bind(InetSocketAddress paramInetSocketAddress, int paramInt) throws IOException;
/*     */   
/*     */   public abstract void start();
/*     */   
/*     */   public abstract void setExecutor(Executor paramExecutor);
/*     */   
/*     */   public abstract Executor getExecutor();
/*     */   
/*     */   public abstract void stop(int paramInt);
/*     */   
/*     */   public abstract HttpContext createContext(String paramString, HttpHandler paramHttpHandler);
/*     */   
/*     */   public abstract HttpContext createContext(String paramString);
/*     */   
/*     */   public abstract void removeContext(String paramString) throws IllegalArgumentException;
/*     */   
/*     */   public abstract void removeContext(HttpContext paramHttpContext);
/*     */   
/*     */   public abstract InetSocketAddress getAddress();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/net/httpserver/HttpServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */