/*     */ package com.sun.net.ssl;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.Provider;
/*     */ import java.security.Security;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class TrustManagerFactory
/*     */ {
/*     */   private Provider provider;
/*     */   private TrustManagerFactorySpi factorySpi;
/*     */   private String algorithm;
/*     */   
/*     */   public static final String getDefaultAlgorithm() {
/*  66 */     String str = AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */           public String run() {
/*  68 */             return Security.getProperty("sun.ssl.trustmanager.type");
/*     */           }
/*     */         });
/*  71 */     if (str == null) {
/*  72 */       str = "SunX509";
/*     */     }
/*  74 */     return str;
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
/*     */   protected TrustManagerFactory(TrustManagerFactorySpi paramTrustManagerFactorySpi, Provider paramProvider, String paramString) {
/*  87 */     this.factorySpi = paramTrustManagerFactorySpi;
/*  88 */     this.provider = paramProvider;
/*  89 */     this.algorithm = paramString;
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
/*     */   public final String getAlgorithm() {
/* 104 */     return this.algorithm;
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
/*     */   public static final TrustManagerFactory getInstance(String paramString) throws NoSuchAlgorithmException {
/*     */     try {
/* 129 */       Object[] arrayOfObject = SSLSecurity.getImpl(paramString, "TrustManagerFactory", (String)null);
/*     */       
/* 131 */       return new TrustManagerFactory((TrustManagerFactorySpi)arrayOfObject[0], (Provider)arrayOfObject[1], paramString);
/*     */     
/*     */     }
/* 134 */     catch (NoSuchProviderException noSuchProviderException) {
/* 135 */       throw new NoSuchAlgorithmException(paramString + " not found");
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
/*     */   public static final TrustManagerFactory getInstance(String paramString1, String paramString2) throws NoSuchAlgorithmException, NoSuchProviderException {
/* 158 */     if (paramString2 == null || paramString2.length() == 0)
/* 159 */       throw new IllegalArgumentException("missing provider"); 
/* 160 */     Object[] arrayOfObject = SSLSecurity.getImpl(paramString1, "TrustManagerFactory", paramString2);
/*     */     
/* 162 */     return new TrustManagerFactory((TrustManagerFactorySpi)arrayOfObject[0], (Provider)arrayOfObject[1], paramString1);
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
/*     */   public static final TrustManagerFactory getInstance(String paramString, Provider paramProvider) throws NoSuchAlgorithmException {
/* 183 */     if (paramProvider == null)
/* 184 */       throw new IllegalArgumentException("missing provider"); 
/* 185 */     Object[] arrayOfObject = SSLSecurity.getImpl(paramString, "TrustManagerFactory", paramProvider);
/*     */     
/* 187 */     return new TrustManagerFactory((TrustManagerFactorySpi)arrayOfObject[0], (Provider)arrayOfObject[1], paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Provider getProvider() {
/* 197 */     return this.provider;
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
/*     */   public void init(KeyStore paramKeyStore) throws KeyStoreException {
/* 210 */     this.factorySpi.engineInit(paramKeyStore);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TrustManager[] getTrustManagers() {
/* 218 */     return this.factorySpi.engineGetTrustManagers();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/net/ssl/TrustManagerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */