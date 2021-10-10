/*     */ package com.sun.net.ssl;
/*     */ 
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.Provider;
/*     */ import sun.security.jca.ProviderList;
/*     */ import sun.security.jca.Providers;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SSLSecurity
/*     */ {
/*     */   private static Provider.Service getService(String paramString1, String paramString2) {
/*  62 */     ProviderList providerList = Providers.getProviderList();
/*  63 */     for (Provider provider : providerList.providers()) {
/*  64 */       Provider.Service service = provider.getService(paramString1, paramString2);
/*  65 */       if (service != null) {
/*  66 */         return service;
/*     */       }
/*     */     } 
/*  69 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object[] getImpl1(String paramString1, String paramString2, Provider.Service paramService) throws NoSuchAlgorithmException {
/*     */     Class<?> clazz;
/*  78 */     Provider provider = paramService.getProvider();
/*  79 */     String str = paramService.getClassName();
/*     */     
/*     */     try {
/*  82 */       ClassLoader classLoader = provider.getClass().getClassLoader();
/*  83 */       if (classLoader == null) {
/*     */         
/*  85 */         clazz = Class.forName(str);
/*     */       } else {
/*  87 */         clazz = classLoader.loadClass(str);
/*     */       } 
/*  89 */     } catch (ClassNotFoundException classNotFoundException) {
/*  90 */       throw new NoSuchAlgorithmException("Class " + str + " configured for " + paramString2 + " not found: " + classNotFoundException
/*     */ 
/*     */ 
/*     */           
/*  94 */           .getMessage());
/*  95 */     } catch (SecurityException securityException) {
/*  96 */       throw new NoSuchAlgorithmException("Class " + str + " configured for " + paramString2 + " cannot be accessed: " + securityException
/*     */ 
/*     */ 
/*     */           
/* 100 */           .getMessage());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 138 */       Object object = null;
/*     */ 
/*     */       
/*     */       Class<?> clazz1;
/*     */ 
/*     */       
/* 144 */       if ((clazz1 = Class.forName("javax.net.ssl." + paramString2 + "Spi")) != null && 
/*     */         
/* 146 */         checkSuperclass(clazz, clazz1)) {
/*     */         
/* 148 */         if (paramString2.equals("SSLContext")) {
/* 149 */           object = new SSLContextSpiWrapper(paramString1, provider);
/* 150 */         } else if (paramString2.equals("TrustManagerFactory")) {
/* 151 */           TrustManagerFactorySpiWrapper trustManagerFactorySpiWrapper = new TrustManagerFactorySpiWrapper(paramString1, provider);
/* 152 */         } else if (paramString2.equals("KeyManagerFactory")) {
/* 153 */           KeyManagerFactorySpiWrapper keyManagerFactorySpiWrapper = new KeyManagerFactorySpiWrapper(paramString1, provider);
/*     */ 
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 160 */           throw new IllegalStateException("Class " + clazz
/* 161 */               .getName() + " unknown engineType wrapper:" + paramString2);
/*     */         } 
/*     */       } else {
/*     */         Class<?> clazz2;
/* 165 */         if ((clazz2 = Class.forName("com.sun.net.ssl." + paramString2 + "Spi")) != null && 
/*     */           
/* 167 */           checkSuperclass(clazz, clazz2)) {
/* 168 */           object = paramService.newInstance(null);
/*     */         }
/*     */       } 
/* 171 */       if (object != null) {
/* 172 */         return new Object[] { object, provider };
/*     */       }
/* 174 */       throw new NoSuchAlgorithmException("Couldn't locate correct object or wrapper: " + paramString2 + " " + paramString1);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 179 */     catch (ClassNotFoundException classNotFoundException) {
/* 180 */       IllegalStateException illegalStateException = new IllegalStateException("Engine Class Not Found for " + paramString2);
/*     */       
/* 182 */       illegalStateException.initCause(classNotFoundException);
/* 183 */       throw illegalStateException;
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
/*     */   static Object[] getImpl(String paramString1, String paramString2, String paramString3) throws NoSuchAlgorithmException, NoSuchProviderException {
/*     */     Provider.Service service;
/* 199 */     if (paramString3 != null) {
/* 200 */       ProviderList providerList = Providers.getProviderList();
/* 201 */       Provider provider = providerList.getProvider(paramString3);
/* 202 */       if (provider == null) {
/* 203 */         throw new NoSuchProviderException("No such provider: " + paramString3);
/*     */       }
/*     */       
/* 206 */       service = provider.getService(paramString2, paramString1);
/*     */     } else {
/* 208 */       service = getService(paramString2, paramString1);
/*     */     } 
/* 210 */     if (service == null) {
/* 211 */       throw new NoSuchAlgorithmException("Algorithm " + paramString1 + " not available");
/*     */     }
/*     */     
/* 214 */     return getImpl1(paramString1, paramString2, service);
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
/*     */   static Object[] getImpl(String paramString1, String paramString2, Provider paramProvider) throws NoSuchAlgorithmException {
/* 229 */     Provider.Service service = paramProvider.getService(paramString2, paramString1);
/* 230 */     if (service == null) {
/* 231 */       throw new NoSuchAlgorithmException("No such algorithm: " + paramString1);
/*     */     }
/*     */     
/* 234 */     return getImpl1(paramString1, paramString2, service);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean checkSuperclass(Class<?> paramClass1, Class<?> paramClass2) {
/* 241 */     if (paramClass1 == null || paramClass2 == null) {
/* 242 */       return false;
/*     */     }
/* 244 */     while (!paramClass1.equals(paramClass2)) {
/* 245 */       paramClass1 = paramClass1.getSuperclass();
/* 246 */       if (paramClass1 == null) {
/* 247 */         return false;
/*     */       }
/*     */     } 
/* 250 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Object[] truncateArray(Object[] paramArrayOfObject1, Object[] paramArrayOfObject2) {
/* 260 */     for (byte b = 0; b < paramArrayOfObject2.length; b++) {
/* 261 */       paramArrayOfObject2[b] = paramArrayOfObject1[b];
/*     */     }
/*     */     
/* 264 */     return paramArrayOfObject2;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/net/ssl/SSLSecurity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */