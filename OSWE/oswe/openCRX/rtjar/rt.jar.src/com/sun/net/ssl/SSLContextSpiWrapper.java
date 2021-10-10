/*     */ package com.sun.net.ssl;
/*     */ 
/*     */ import java.security.KeyManagementException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.Provider;
/*     */ import java.security.SecureRandom;
/*     */ import javax.net.ssl.KeyManager;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLServerSocketFactory;
/*     */ import javax.net.ssl.SSLSocketFactory;
/*     */ import javax.net.ssl.TrustManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SSLContextSpiWrapper
/*     */   extends SSLContextSpi
/*     */ {
/*     */   private SSLContext theSSLContext;
/*     */   
/*     */   SSLContextSpiWrapper(String paramString, Provider paramProvider) throws NoSuchAlgorithmException {
/* 285 */     this.theSSLContext = SSLContext.getInstance(paramString, paramProvider);
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
/*     */   protected void engineInit(KeyManager[] paramArrayOfKeyManager, TrustManager[] paramArrayOfTrustManager, SecureRandom paramSecureRandom) throws KeyManagementException {
/*     */     KeyManager[] arrayOfKeyManager;
/*     */     TrustManager[] arrayOfTrustManager;
/* 299 */     if (paramArrayOfKeyManager != null) {
/* 300 */       arrayOfKeyManager = new KeyManager[paramArrayOfKeyManager.length]; byte b1, b2;
/* 301 */       for (b2 = 0, b1 = 0; b2 < paramArrayOfKeyManager.length; ) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 307 */         if (!(paramArrayOfKeyManager[b2] instanceof KeyManager)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 315 */           if (paramArrayOfKeyManager[b2] instanceof X509KeyManager) {
/* 316 */             arrayOfKeyManager[b1] = new X509KeyManagerJavaxWrapper((X509KeyManager)paramArrayOfKeyManager[b2]);
/*     */ 
/*     */             
/* 319 */             b1++;
/*     */           } 
/*     */         } else {
/*     */           
/* 323 */           arrayOfKeyManager[b1] = (KeyManager)paramArrayOfKeyManager[b2];
/* 324 */           b1++;
/*     */         } 
/* 326 */         b2++;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 334 */       if (b1 != b2)
/*     */       {
/* 336 */         arrayOfKeyManager = (KeyManager[])SSLSecurity.truncateArray((Object[])arrayOfKeyManager, (Object[])new KeyManager[b1]);
/*     */       }
/*     */     } else {
/*     */       
/* 340 */       arrayOfKeyManager = null;
/*     */     } 
/*     */ 
/*     */     
/* 344 */     if (paramArrayOfTrustManager != null) {
/* 345 */       arrayOfTrustManager = new TrustManager[paramArrayOfTrustManager.length];
/*     */       byte b1, b2;
/* 347 */       for (b2 = 0, b1 = 0; b2 < paramArrayOfTrustManager.length; ) {
/*     */ 
/*     */ 
/*     */         
/* 351 */         if (!(paramArrayOfTrustManager[b2] instanceof TrustManager)) {
/*     */           
/* 353 */           if (paramArrayOfTrustManager[b2] instanceof X509TrustManager) {
/* 354 */             arrayOfTrustManager[b1] = new X509TrustManagerJavaxWrapper((X509TrustManager)paramArrayOfTrustManager[b2]);
/*     */ 
/*     */             
/* 357 */             b1++;
/*     */           } 
/*     */         } else {
/* 360 */           arrayOfTrustManager[b1] = (TrustManager)paramArrayOfTrustManager[b2];
/* 361 */           b1++;
/*     */         } 
/* 363 */         b2++;
/*     */       } 
/*     */       
/* 366 */       if (b1 != b2)
/*     */       {
/* 368 */         arrayOfTrustManager = (TrustManager[])SSLSecurity.truncateArray((Object[])arrayOfTrustManager, (Object[])new TrustManager[b1]);
/*     */       }
/*     */     } else {
/*     */       
/* 372 */       arrayOfTrustManager = null;
/*     */     } 
/*     */     
/* 375 */     this.theSSLContext.init(arrayOfKeyManager, arrayOfTrustManager, paramSecureRandom);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SSLSocketFactory engineGetSocketFactory() {
/* 380 */     return this.theSSLContext.getSocketFactory();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SSLServerSocketFactory engineGetServerSocketFactory() {
/* 385 */     return this.theSSLContext.getServerSocketFactory();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/net/ssl/SSLContextSpiWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */