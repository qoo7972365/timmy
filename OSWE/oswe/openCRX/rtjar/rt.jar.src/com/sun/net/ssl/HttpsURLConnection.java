/*     */ package com.sun.net.ssl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import javax.net.ssl.SSLSocketFactory;
/*     */ import javax.security.cert.X509Certificate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public abstract class HttpsURLConnection
/*     */   extends HttpURLConnection
/*     */ {
/*     */   private static HostnameVerifier defaultHostnameVerifier = new HostnameVerifier()
/*     */     {
/*     */       public boolean verify(String param1String1, String param1String2) {
/*     */         return false;
/*     */       }
/*     */     };
/*     */   
/*     */   protected HostnameVerifier hostnameVerifier;
/*     */   
/*     */   public abstract String getCipherSuite();
/*     */   
/*     */   public abstract X509Certificate[] getServerCertificateChain();
/*     */   
/*     */   public HttpsURLConnection(URL paramURL) throws IOException {
/*  59 */     super(paramURL);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     this.hostnameVerifier = defaultHostnameVerifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     this.sslSocketFactory = getDefaultSSLSocketFactory(); } public static void setDefaultHostnameVerifier(HostnameVerifier paramHostnameVerifier) {
/*     */     if (paramHostnameVerifier == null)
/*     */       throw new IllegalArgumentException("no default HostnameVerifier specified"); 
/*     */     SecurityManager securityManager = System.getSecurityManager();
/*     */     if (securityManager != null)
/*     */       securityManager.checkPermission(new SSLPermission("setHostnameVerifier")); 
/*     */     defaultHostnameVerifier = paramHostnameVerifier;
/*     */   } public static void setDefaultSSLSocketFactory(SSLSocketFactory paramSSLSocketFactory) {
/* 149 */     if (paramSSLSocketFactory == null) {
/* 150 */       throw new IllegalArgumentException("no default SSLSocketFactory specified");
/*     */     }
/*     */ 
/*     */     
/* 154 */     SecurityManager securityManager = System.getSecurityManager();
/* 155 */     if (securityManager != null) {
/* 156 */       securityManager.checkSetFactory();
/*     */     }
/* 158 */     defaultSSLSocketFactory = paramSSLSocketFactory;
/*     */   }
/*     */   
/*     */   public static HostnameVerifier getDefaultHostnameVerifier() {
/*     */     return defaultHostnameVerifier;
/*     */   }
/*     */   
/*     */   public static SSLSocketFactory getDefaultSSLSocketFactory() {
/* 166 */     if (defaultSSLSocketFactory == null)
/*     */     {
/* 168 */       defaultSSLSocketFactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
/*     */     }
/* 170 */     return defaultSSLSocketFactory;
/*     */   } public void setHostnameVerifier(HostnameVerifier paramHostnameVerifier) {
/*     */     if (paramHostnameVerifier == null)
/*     */       throw new IllegalArgumentException("no HostnameVerifier specified"); 
/*     */     this.hostnameVerifier = paramHostnameVerifier;
/*     */   } public HostnameVerifier getHostnameVerifier() {
/*     */     return this.hostnameVerifier;
/*     */   } private static SSLSocketFactory defaultSSLSocketFactory = null; private SSLSocketFactory sslSocketFactory; public void setSSLSocketFactory(SSLSocketFactory paramSSLSocketFactory) {
/* 178 */     if (paramSSLSocketFactory == null) {
/* 179 */       throw new IllegalArgumentException("no SSLSocketFactory specified");
/*     */     }
/*     */ 
/*     */     
/* 183 */     SecurityManager securityManager = System.getSecurityManager();
/* 184 */     if (securityManager != null) {
/* 185 */       securityManager.checkSetFactory();
/*     */     }
/*     */     
/* 188 */     this.sslSocketFactory = paramSSLSocketFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SSLSocketFactory getSSLSocketFactory() {
/* 196 */     return this.sslSocketFactory;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/net/ssl/HttpsURLConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */