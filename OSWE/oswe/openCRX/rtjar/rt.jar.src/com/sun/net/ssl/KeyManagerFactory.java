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
/*     */ import java.security.UnrecoverableKeyException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class KeyManagerFactory
/*     */ {
/*     */   private Provider provider;
/*     */   private KeyManagerFactorySpi factorySpi;
/*     */   private String algorithm;
/*     */   
/*     */   public static final String getDefaultAlgorithm() {
/*  66 */     String str = AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */           public String run() {
/*  68 */             return Security.getProperty("sun.ssl.keymanager.type");
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
/*     */   protected KeyManagerFactory(KeyManagerFactorySpi paramKeyManagerFactorySpi, Provider paramProvider, String paramString) {
/*  87 */     this.factorySpi = paramKeyManagerFactorySpi;
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
/*     */   public final String getAlgorithm() {
/* 102 */     return this.algorithm;
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
/*     */   public static final KeyManagerFactory getInstance(String paramString) throws NoSuchAlgorithmException {
/*     */     try {
/* 127 */       Object[] arrayOfObject = SSLSecurity.getImpl(paramString, "KeyManagerFactory", (String)null);
/*     */       
/* 129 */       return new KeyManagerFactory((KeyManagerFactorySpi)arrayOfObject[0], (Provider)arrayOfObject[1], paramString);
/*     */     
/*     */     }
/* 132 */     catch (NoSuchProviderException noSuchProviderException) {
/* 133 */       throw new NoSuchAlgorithmException(paramString + " not found");
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
/*     */   public static final KeyManagerFactory getInstance(String paramString1, String paramString2) throws NoSuchAlgorithmException, NoSuchProviderException {
/* 156 */     if (paramString2 == null || paramString2.length() == 0)
/* 157 */       throw new IllegalArgumentException("missing provider"); 
/* 158 */     Object[] arrayOfObject = SSLSecurity.getImpl(paramString1, "KeyManagerFactory", paramString2);
/*     */     
/* 160 */     return new KeyManagerFactory((KeyManagerFactorySpi)arrayOfObject[0], (Provider)arrayOfObject[1], paramString1);
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
/*     */   public static final KeyManagerFactory getInstance(String paramString, Provider paramProvider) throws NoSuchAlgorithmException {
/* 181 */     if (paramProvider == null)
/* 182 */       throw new IllegalArgumentException("missing provider"); 
/* 183 */     Object[] arrayOfObject = SSLSecurity.getImpl(paramString, "KeyManagerFactory", paramProvider);
/*     */     
/* 185 */     return new KeyManagerFactory((KeyManagerFactorySpi)arrayOfObject[0], (Provider)arrayOfObject[1], paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Provider getProvider() {
/* 195 */     return this.provider;
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
/*     */   public void init(KeyStore paramKeyStore, char[] paramArrayOfchar) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
/* 210 */     this.factorySpi.engineInit(paramKeyStore, paramArrayOfchar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyManager[] getKeyManagers() {
/* 218 */     return this.factorySpi.engineGetKeyManagers();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/net/ssl/KeyManagerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */