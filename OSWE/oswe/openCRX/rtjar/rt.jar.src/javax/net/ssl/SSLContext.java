/*     */ package javax.net.ssl;
/*     */ 
/*     */ import java.security.KeyManagementException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.Provider;
/*     */ import java.security.SecureRandom;
/*     */ import sun.security.jca.GetInstance;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SSLContext
/*     */ {
/*     */   private final Provider provider;
/*     */   private final SSLContextSpi contextSpi;
/*     */   private final String protocol;
/*     */   private static SSLContext defaultContext;
/*     */   
/*     */   protected SSLContext(SSLContextSpi paramSSLContextSpi, Provider paramProvider, String paramString) {
/*  69 */     this.contextSpi = paramSSLContextSpi;
/*  70 */     this.provider = paramProvider;
/*  71 */     this.protocol = paramString;
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
/*     */   public static synchronized SSLContext getDefault() throws NoSuchAlgorithmException {
/*  95 */     if (defaultContext == null) {
/*  96 */       defaultContext = getInstance("Default");
/*     */     }
/*  98 */     return defaultContext;
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
/*     */   public static synchronized void setDefault(SSLContext paramSSLContext) {
/* 114 */     if (paramSSLContext == null) {
/* 115 */       throw new NullPointerException();
/*     */     }
/* 117 */     SecurityManager securityManager = System.getSecurityManager();
/* 118 */     if (securityManager != null) {
/* 119 */       securityManager.checkPermission(new SSLPermission("setDefaultSSLContext"));
/*     */     }
/* 121 */     defaultContext = paramSSLContext;
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
/*     */   public static SSLContext getInstance(String paramString) throws NoSuchAlgorithmException {
/* 156 */     GetInstance.Instance instance = GetInstance.getInstance("SSLContext", SSLContextSpi.class, paramString);
/* 157 */     return new SSLContext((SSLContextSpi)instance.impl, instance.provider, paramString);
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
/*     */   public static SSLContext getInstance(String paramString1, String paramString2) throws NoSuchAlgorithmException, NoSuchProviderException {
/* 199 */     GetInstance.Instance instance = GetInstance.getInstance("SSLContext", SSLContextSpi.class, paramString1, paramString2);
/* 200 */     return new SSLContext((SSLContextSpi)instance.impl, instance.provider, paramString1);
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
/*     */   public static SSLContext getInstance(String paramString, Provider paramProvider) throws NoSuchAlgorithmException {
/* 236 */     GetInstance.Instance instance = GetInstance.getInstance("SSLContext", SSLContextSpi.class, paramString, paramProvider);
/* 237 */     return new SSLContext((SSLContextSpi)instance.impl, instance.provider, paramString);
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
/*     */   public final String getProtocol() {
/* 251 */     return this.protocol;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Provider getProvider() {
/* 260 */     return this.provider;
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
/*     */   public final void init(KeyManager[] paramArrayOfKeyManager, TrustManager[] paramArrayOfTrustManager, SecureRandom paramSecureRandom) throws KeyManagementException {
/* 282 */     this.contextSpi.engineInit(paramArrayOfKeyManager, paramArrayOfTrustManager, paramSecureRandom);
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
/*     */   public final SSLSocketFactory getSocketFactory() {
/* 294 */     return this.contextSpi.engineGetSocketFactory();
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
/*     */   public final SSLServerSocketFactory getServerSocketFactory() {
/* 306 */     return this.contextSpi.engineGetServerSocketFactory();
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
/*     */   public final SSLEngine createSSLEngine() {
/*     */     try {
/* 329 */       return this.contextSpi.engineCreateSSLEngine();
/* 330 */     } catch (AbstractMethodError abstractMethodError) {
/*     */ 
/*     */       
/* 333 */       UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException("Provider: " + getProvider() + " doesn't support this operation");
/*     */       
/* 335 */       unsupportedOperationException.initCause(abstractMethodError);
/* 336 */       throw unsupportedOperationException;
/*     */     } 
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
/*     */   public final SSLEngine createSSLEngine(String paramString, int paramInt) {
/*     */     try {
/* 361 */       return this.contextSpi.engineCreateSSLEngine(paramString, paramInt);
/* 362 */     } catch (AbstractMethodError abstractMethodError) {
/*     */ 
/*     */       
/* 365 */       UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException("Provider: " + getProvider() + " does not support this operation");
/*     */       
/* 367 */       unsupportedOperationException.initCause(abstractMethodError);
/* 368 */       throw unsupportedOperationException;
/*     */     } 
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
/*     */   public final SSLSessionContext getServerSessionContext() {
/* 386 */     return this.contextSpi.engineGetServerSessionContext();
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
/*     */   public final SSLSessionContext getClientSessionContext() {
/* 403 */     return this.contextSpi.engineGetClientSessionContext();
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
/*     */   public final SSLParameters getDefaultSSLParameters() {
/* 419 */     return this.contextSpi.engineGetDefaultSSLParameters();
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
/*     */   public final SSLParameters getSupportedSSLParameters() {
/* 436 */     return this.contextSpi.engineGetSupportedSSLParameters();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/net/ssl/SSLContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */