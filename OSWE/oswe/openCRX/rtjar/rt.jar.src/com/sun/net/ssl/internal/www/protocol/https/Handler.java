/*    */ package com.sun.net.ssl.internal.www.protocol.https;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.Proxy;
/*    */ import java.net.URL;
/*    */ import java.net.URLConnection;
/*    */ import sun.net.www.protocol.https.Handler;
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
/*    */ public class Handler
/*    */   extends Handler
/*    */ {
/*    */   public Handler() {}
/*    */   
/*    */   public Handler(String paramString, int paramInt) {
/* 45 */     super(paramString, paramInt);
/*    */   }
/*    */   
/*    */   protected URLConnection openConnection(URL paramURL) throws IOException {
/* 49 */     return openConnection(paramURL, (Proxy)null);
/*    */   }
/*    */   
/*    */   protected URLConnection openConnection(URL paramURL, Proxy paramProxy) throws IOException {
/* 53 */     return (URLConnection)new HttpsURLConnectionOldImpl(paramURL, paramProxy, this);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/net/ssl/internal/www/protocol/https/Handler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */