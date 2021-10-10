/*     */ package java.security.cert;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.Provider;
/*     */ import java.security.Security;
/*     */ import java.util.Collection;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CertStore
/*     */ {
/*     */   private static final String CERTSTORE_TYPE = "certstore.type";
/*     */   private CertStoreSpi storeSpi;
/*     */   private Provider provider;
/*     */   private String type;
/*     */   private CertStoreParameters params;
/*     */   
/*     */   protected CertStore(CertStoreSpi paramCertStoreSpi, Provider paramProvider, String paramString, CertStoreParameters paramCertStoreParameters) {
/* 117 */     this.storeSpi = paramCertStoreSpi;
/* 118 */     this.provider = paramProvider;
/* 119 */     this.type = paramString;
/* 120 */     if (paramCertStoreParameters != null) {
/* 121 */       this.params = (CertStoreParameters)paramCertStoreParameters.clone();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Collection<? extends Certificate> getCertificates(CertSelector paramCertSelector) throws CertStoreException {
/* 151 */     return this.storeSpi.engineGetCertificates(paramCertSelector);
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
/*     */   public final Collection<? extends CRL> getCRLs(CRLSelector paramCRLSelector) throws CertStoreException {
/* 181 */     return this.storeSpi.engineGetCRLs(paramCRLSelector);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CertStore getInstance(String paramString, CertStoreParameters paramCertStoreParameters) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
/*     */     try {
/* 228 */       GetInstance.Instance instance = GetInstance.getInstance("CertStore", CertStoreSpi.class, paramString, paramCertStoreParameters);
/*     */       
/* 230 */       return new CertStore((CertStoreSpi)instance.impl, instance.provider, paramString, paramCertStoreParameters);
/*     */     }
/* 232 */     catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 233 */       return handleException(noSuchAlgorithmException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static CertStore handleException(NoSuchAlgorithmException paramNoSuchAlgorithmException) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
/* 239 */     Throwable throwable = paramNoSuchAlgorithmException.getCause();
/* 240 */     if (throwable instanceof InvalidAlgorithmParameterException) {
/* 241 */       throw (InvalidAlgorithmParameterException)throwable;
/*     */     }
/* 243 */     throw paramNoSuchAlgorithmException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CertStore getInstance(String paramString1, CertStoreParameters paramCertStoreParameters, String paramString2) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
/*     */     try {
/* 298 */       GetInstance.Instance instance = GetInstance.getInstance("CertStore", CertStoreSpi.class, paramString1, paramCertStoreParameters, paramString2);
/*     */       
/* 300 */       return new CertStore((CertStoreSpi)instance.impl, instance.provider, paramString1, paramCertStoreParameters);
/*     */     }
/* 302 */     catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 303 */       return handleException(noSuchAlgorithmException);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CertStore getInstance(String paramString, CertStoreParameters paramCertStoreParameters, Provider paramProvider) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
/*     */     try {
/* 352 */       GetInstance.Instance instance = GetInstance.getInstance("CertStore", CertStoreSpi.class, paramString, paramCertStoreParameters, paramProvider);
/*     */       
/* 354 */       return new CertStore((CertStoreSpi)instance.impl, instance.provider, paramString, paramCertStoreParameters);
/*     */     }
/* 356 */     catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 357 */       return handleException(noSuchAlgorithmException);
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
/*     */   public final CertStoreParameters getCertStoreParameters() {
/* 370 */     return (this.params == null) ? null : (CertStoreParameters)this.params.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getType() {
/* 379 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Provider getProvider() {
/* 388 */     return this.provider;
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
/*     */   public static final String getDefaultType() {
/* 412 */     String str = AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */           public String run() {
/* 414 */             return Security.getProperty("certstore.type");
/*     */           }
/*     */         });
/* 417 */     if (str == null) {
/* 418 */       str = "LDAP";
/*     */     }
/* 420 */     return str;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/cert/CertStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */