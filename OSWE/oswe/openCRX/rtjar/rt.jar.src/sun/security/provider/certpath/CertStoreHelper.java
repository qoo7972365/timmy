/*     */ package sun.security.provider.certpath;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.security.AccessController;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.security.cert.CertStore;
/*     */ import java.security.cert.CertStoreException;
/*     */ import java.security.cert.X509CRLSelector;
/*     */ import java.security.cert.X509CertSelector;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import sun.security.util.Cache;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CertStoreHelper
/*     */ {
/*     */   private static final int NUM_TYPES = 2;
/*  54 */   private static final Map<String, String> classMap = new HashMap<>(2);
/*     */   static {
/*  56 */     classMap.put("LDAP", "sun.security.provider.certpath.ldap.LDAPCertStoreHelper");
/*     */ 
/*     */     
/*  59 */     classMap.put("SSLServer", "sun.security.provider.certpath.ssl.SSLServerCertStoreHelper");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  64 */   private static Cache<String, CertStoreHelper> cache = Cache.newSoftMemoryCache(2);
/*     */ 
/*     */ 
/*     */   
/*     */   public static CertStoreHelper getInstance(final String type) throws NoSuchAlgorithmException {
/*  69 */     CertStoreHelper certStoreHelper = cache.get(type);
/*  70 */     if (certStoreHelper != null) {
/*  71 */       return certStoreHelper;
/*     */     }
/*  73 */     final String cl = classMap.get(type);
/*  74 */     if (str == null) {
/*  75 */       throw new NoSuchAlgorithmException(type + " not available");
/*     */     }
/*     */     try {
/*  78 */       certStoreHelper = AccessController.<CertStoreHelper>doPrivileged(new PrivilegedExceptionAction<CertStoreHelper>()
/*     */           {
/*     */             public CertStoreHelper run() throws ClassNotFoundException {
/*     */               try {
/*  82 */                 Class<?> clazz = Class.forName(cl, true, null);
/*     */                 
/*  84 */                 CertStoreHelper certStoreHelper = (CertStoreHelper)clazz.newInstance();
/*  85 */                 CertStoreHelper.cache.put(type, certStoreHelper);
/*  86 */                 return certStoreHelper;
/*  87 */               } catch (InstantiationException|IllegalAccessException instantiationException) {
/*     */                 
/*  89 */                 throw new AssertionError(instantiationException);
/*     */               } 
/*     */             }
/*     */           });
/*  93 */       return certStoreHelper;
/*  94 */     } catch (PrivilegedActionException privilegedActionException) {
/*  95 */       throw new NoSuchAlgorithmException(type + " not available", privilegedActionException
/*  96 */           .getException());
/*     */     } 
/*     */   }
/*     */   static boolean isCausedByNetworkIssue(String paramString, CertStoreException paramCertStoreException) {
/*     */     Throwable throwable;
/* 101 */     switch (paramString) {
/*     */       case "LDAP":
/*     */       case "SSLServer":
/*     */         try {
/* 105 */           CertStoreHelper certStoreHelper = getInstance(paramString);
/* 106 */           return certStoreHelper.isCausedByNetworkIssue(paramCertStoreException);
/* 107 */         } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 108 */           return false;
/*     */         } 
/*     */       case "URI":
/* 111 */         throwable = paramCertStoreException.getCause();
/* 112 */         return (throwable != null && throwable instanceof IOException);
/*     */     } 
/*     */     
/* 115 */     return false;
/*     */   }
/*     */   
/*     */   public abstract CertStore getCertStore(URI paramURI) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException;
/*     */   
/*     */   public abstract X509CertSelector wrap(X509CertSelector paramX509CertSelector, X500Principal paramX500Principal, String paramString) throws IOException;
/*     */   
/*     */   public abstract X509CRLSelector wrap(X509CRLSelector paramX509CRLSelector, Collection<X500Principal> paramCollection, String paramString) throws IOException;
/*     */   
/*     */   public abstract boolean isCausedByNetworkIssue(CertStoreException paramCertStoreException);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/CertStoreHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */