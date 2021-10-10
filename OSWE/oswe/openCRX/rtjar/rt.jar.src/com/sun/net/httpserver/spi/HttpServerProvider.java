/*     */ package com.sun.net.httpserver.spi;
/*     */ 
/*     */ import com.sun.net.httpserver.HttpServer;
/*     */ import com.sun.net.httpserver.HttpsServer;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Iterator;
/*     */ import java.util.ServiceConfigurationError;
/*     */ import java.util.ServiceLoader;
/*     */ import jdk.Exported;
/*     */ import sun.net.httpserver.DefaultHttpServerProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public abstract class HttpServerProvider
/*     */ {
/*  72 */   private static final Object lock = new Object();
/*  73 */   private static HttpServerProvider provider = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected HttpServerProvider() {
/*  83 */     SecurityManager securityManager = System.getSecurityManager();
/*  84 */     if (securityManager != null)
/*  85 */       securityManager.checkPermission(new RuntimePermission("httpServerProvider")); 
/*     */   }
/*     */   
/*     */   private static boolean loadProviderFromProperty() {
/*  89 */     String str = System.getProperty("com.sun.net.httpserver.HttpServerProvider");
/*  90 */     if (str == null)
/*  91 */       return false; 
/*     */     try {
/*  93 */       Class<?> clazz = Class.forName(str, true, 
/*  94 */           ClassLoader.getSystemClassLoader());
/*  95 */       provider = (HttpServerProvider)clazz.newInstance();
/*  96 */       return true;
/*  97 */     } catch (ClassNotFoundException|IllegalAccessException|InstantiationException|SecurityException classNotFoundException) {
/*     */ 
/*     */ 
/*     */       
/* 101 */       throw new ServiceConfigurationError(null, classNotFoundException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean loadProviderAsService() {
/* 109 */     Iterator<HttpServerProvider> iterator = ServiceLoader.<HttpServerProvider>load(HttpServerProvider.class, ClassLoader.getSystemClassLoader()).iterator();
/*     */     while (true) {
/*     */       try {
/* 112 */         if (!iterator.hasNext())
/* 113 */           return false; 
/* 114 */         provider = iterator.next();
/* 115 */         return true;
/* 116 */       } catch (ServiceConfigurationError serviceConfigurationError) {
/* 117 */         if (serviceConfigurationError.getCause() instanceof SecurityException)
/*     */           continue;  break;
/*     */       } 
/*     */     } 
/* 121 */     throw serviceConfigurationError;
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
/*     */   public static HttpServerProvider provider() {
/* 162 */     synchronized (lock) {
/* 163 */       if (provider != null)
/* 164 */         return provider; 
/* 165 */       return 
/* 166 */         AccessController.<HttpServerProvider>doPrivileged(new PrivilegedAction() {
/*     */             public Object run() {
/* 168 */               if (HttpServerProvider.loadProviderFromProperty())
/* 169 */                 return HttpServerProvider.provider; 
/* 170 */               if (HttpServerProvider.loadProviderAsService())
/* 171 */                 return HttpServerProvider.provider; 
/* 172 */               HttpServerProvider.provider = new DefaultHttpServerProvider();
/* 173 */               return HttpServerProvider.provider;
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract HttpServer createHttpServer(InetSocketAddress paramInetSocketAddress, int paramInt) throws IOException;
/*     */   
/*     */   public abstract HttpsServer createHttpsServer(InetSocketAddress paramInetSocketAddress, int paramInt) throws IOException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/net/httpserver/spi/HttpServerProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */