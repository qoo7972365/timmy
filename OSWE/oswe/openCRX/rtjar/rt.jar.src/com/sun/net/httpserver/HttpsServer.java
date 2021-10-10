/*    */ package com.sun.net.httpserver;
/*    */ 
/*    */ import com.sun.net.httpserver.spi.HttpServerProvider;
/*    */ import java.io.IOException;
/*    */ import java.net.InetSocketAddress;
/*    */ import jdk.Exported;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Exported
/*    */ public abstract class HttpsServer
/*    */   extends HttpServer
/*    */ {
/*    */   public static HttpsServer create() throws IOException {
/* 64 */     return create(null, 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static HttpsServer create(InetSocketAddress paramInetSocketAddress, int paramInt) throws IOException {
/* 89 */     HttpServerProvider httpServerProvider = HttpServerProvider.provider();
/* 90 */     return httpServerProvider.createHttpsServer(paramInetSocketAddress, paramInt);
/*    */   }
/*    */   
/*    */   public abstract void setHttpsConfigurator(HttpsConfigurator paramHttpsConfigurator);
/*    */   
/*    */   public abstract HttpsConfigurator getHttpsConfigurator();
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/net/httpserver/HttpsServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */