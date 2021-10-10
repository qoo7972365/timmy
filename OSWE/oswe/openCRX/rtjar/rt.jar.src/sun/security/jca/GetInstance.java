/*     */ package sun.security.jca;
/*     */ 
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.Provider;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GetInstance
/*     */ {
/*     */   public static final class Instance
/*     */   {
/*     */     public final Provider provider;
/*     */     public final Object impl;
/*     */     
/*     */     private Instance(Provider param1Provider, Object param1Object) {
/*  54 */       this.provider = param1Provider;
/*  55 */       this.impl = param1Object;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object[] toArray() {
/*  60 */       return new Object[] { this.impl, this.provider };
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static Provider.Service getService(String paramString1, String paramString2) throws NoSuchAlgorithmException {
/*  66 */     ProviderList providerList = Providers.getProviderList();
/*  67 */     Provider.Service service = providerList.getService(paramString1, paramString2);
/*  68 */     if (service == null) {
/*  69 */       throw new NoSuchAlgorithmException(paramString2 + " " + paramString1 + " not available");
/*     */     }
/*     */     
/*  72 */     return service;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Provider.Service getService(String paramString1, String paramString2, String paramString3) throws NoSuchAlgorithmException, NoSuchProviderException {
/*  78 */     if (paramString3 == null || paramString3.length() == 0) {
/*  79 */       throw new IllegalArgumentException("missing provider");
/*     */     }
/*  81 */     Provider provider = Providers.getProviderList().getProvider(paramString3);
/*  82 */     if (provider == null) {
/*  83 */       throw new NoSuchProviderException("no such provider: " + paramString3);
/*     */     }
/*  85 */     Provider.Service service = provider.getService(paramString1, paramString2);
/*  86 */     if (service == null) {
/*  87 */       throw new NoSuchAlgorithmException("no such algorithm: " + paramString2 + " for provider " + paramString3);
/*     */     }
/*     */     
/*  90 */     return service;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Provider.Service getService(String paramString1, String paramString2, Provider paramProvider) throws NoSuchAlgorithmException {
/*  95 */     if (paramProvider == null) {
/*  96 */       throw new IllegalArgumentException("missing provider");
/*     */     }
/*  98 */     Provider.Service service = paramProvider.getService(paramString1, paramString2);
/*  99 */     if (service == null) {
/* 100 */       throw new NoSuchAlgorithmException("no such algorithm: " + paramString2 + " for provider " + paramProvider
/* 101 */           .getName());
/*     */     }
/* 103 */     return service;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<Provider.Service> getServices(String paramString1, String paramString2) {
/* 113 */     ProviderList providerList = Providers.getProviderList();
/* 114 */     return providerList.getServices(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static List<Provider.Service> getServices(String paramString, List<String> paramList) {
/* 125 */     ProviderList providerList = Providers.getProviderList();
/* 126 */     return providerList.getServices(paramString, paramList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<Provider.Service> getServices(List<ServiceId> paramList) {
/* 134 */     ProviderList providerList = Providers.getProviderList();
/* 135 */     return providerList.getServices(paramList);
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
/*     */   public static Instance getInstance(String paramString1, Class<?> paramClass, String paramString2) throws NoSuchAlgorithmException {
/* 156 */     ProviderList providerList = Providers.getProviderList();
/* 157 */     Provider.Service service = providerList.getService(paramString1, paramString2);
/* 158 */     if (service == null) {
/* 159 */       throw new NoSuchAlgorithmException(paramString2 + " " + paramString1 + " not available");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 164 */       return getInstance(service, paramClass);
/* 165 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException2) {
/* 166 */       NoSuchAlgorithmException noSuchAlgorithmException1 = noSuchAlgorithmException2;
/*     */ 
/*     */ 
/*     */       
/* 170 */       for (Provider.Service service1 : providerList.getServices(paramString1, paramString2)) {
/* 171 */         if (service1 == service) {
/*     */           continue;
/*     */         }
/*     */         
/*     */         try {
/* 176 */           return getInstance(service1, paramClass);
/* 177 */         } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 178 */           noSuchAlgorithmException1 = noSuchAlgorithmException;
/*     */         } 
/*     */       } 
/* 181 */       throw noSuchAlgorithmException1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Instance getInstance(String paramString1, Class<?> paramClass, String paramString2, Object paramObject) throws NoSuchAlgorithmException {
/* 186 */     List<Provider.Service> list = getServices(paramString1, paramString2);
/* 187 */     NoSuchAlgorithmException noSuchAlgorithmException = null;
/* 188 */     for (Provider.Service service : list) {
/*     */       try {
/* 190 */         return getInstance(service, paramClass, paramObject);
/* 191 */       } catch (NoSuchAlgorithmException noSuchAlgorithmException1) {
/* 192 */         noSuchAlgorithmException = noSuchAlgorithmException1;
/*     */       } 
/*     */     } 
/* 195 */     if (noSuchAlgorithmException != null) {
/* 196 */       throw noSuchAlgorithmException;
/*     */     }
/* 198 */     throw new NoSuchAlgorithmException(paramString2 + " " + paramString1 + " not available");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Instance getInstance(String paramString1, Class<?> paramClass, String paramString2, String paramString3) throws NoSuchAlgorithmException, NoSuchProviderException {
/* 206 */     return getInstance(getService(paramString1, paramString2, paramString3), paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Instance getInstance(String paramString1, Class<?> paramClass, String paramString2, Object paramObject, String paramString3) throws NoSuchAlgorithmException, NoSuchProviderException {
/* 212 */     return getInstance(getService(paramString1, paramString2, paramString3), paramClass, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Instance getInstance(String paramString1, Class<?> paramClass, String paramString2, Provider paramProvider) throws NoSuchAlgorithmException {
/* 218 */     return getInstance(getService(paramString1, paramString2, paramProvider), paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Instance getInstance(String paramString1, Class<?> paramClass, String paramString2, Object paramObject, Provider paramProvider) throws NoSuchAlgorithmException {
/* 224 */     return getInstance(getService(paramString1, paramString2, paramProvider), paramClass, paramObject);
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
/*     */   public static Instance getInstance(Provider.Service paramService, Class<?> paramClass) throws NoSuchAlgorithmException {
/* 236 */     Object object = paramService.newInstance(null);
/* 237 */     checkSuperClass(paramService, object.getClass(), paramClass);
/* 238 */     return new Instance(paramService.getProvider(), object);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Instance getInstance(Provider.Service paramService, Class<?> paramClass, Object paramObject) throws NoSuchAlgorithmException {
/* 243 */     Object object = paramService.newInstance(paramObject);
/* 244 */     checkSuperClass(paramService, object.getClass(), paramClass);
/* 245 */     return new Instance(paramService.getProvider(), object);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void checkSuperClass(Provider.Service paramService, Class<?> paramClass1, Class<?> paramClass2) throws NoSuchAlgorithmException {
/* 254 */     if (paramClass2 == null) {
/*     */       return;
/*     */     }
/* 257 */     if (!paramClass2.isAssignableFrom(paramClass1))
/* 258 */       throw new NoSuchAlgorithmException("class configured for " + paramService
/* 259 */           .getType() + ": " + paramService
/* 260 */           .getClassName() + " not a " + paramService.getType()); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jca/GetInstance.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */