/*     */ package sun.security.validator;
/*     */ 
/*     */ import java.security.AlgorithmConstraints;
/*     */ import java.security.KeyStore;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.PKIXBuilderParameters;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Validator
/*     */ {
/*  89 */   static final X509Certificate[] CHAIN0 = new X509Certificate[0];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String TYPE_SIMPLE = "Simple";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String TYPE_PKIX = "PKIX";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String VAR_GENERIC = "generic";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String VAR_CODE_SIGNING = "code signing";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String VAR_JCE_SIGNING = "jce signing";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String VAR_TLS_CLIENT = "tls client";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String VAR_TLS_SERVER = "tls server";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String VAR_TSA_SERVER = "tsa server";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String VAR_PLUGIN_CODE_SIGNING = "plugin code signing";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String type;
/*     */ 
/*     */ 
/*     */   
/*     */   final EndEntityChecker endEntityChecker;
/*     */ 
/*     */ 
/*     */   
/*     */   final String variant;
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   volatile Date validationDate;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Validator(String paramString1, String paramString2) {
/* 158 */     this.type = paramString1;
/* 159 */     this.variant = paramString2;
/* 160 */     this.endEntityChecker = EndEntityChecker.getInstance(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Validator getInstance(String paramString1, String paramString2, KeyStore paramKeyStore) {
/* 169 */     return getInstance(paramString1, paramString2, TrustStoreUtil.getTrustedCerts(paramKeyStore));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Validator getInstance(String paramString1, String paramString2, Collection<X509Certificate> paramCollection) {
/* 178 */     if (paramString1.equals("Simple"))
/* 179 */       return new SimpleValidator(paramString2, paramCollection); 
/* 180 */     if (paramString1.equals("PKIX")) {
/* 181 */       return new PKIXValidator(paramString2, paramCollection);
/*     */     }
/* 183 */     throw new IllegalArgumentException("Unknown validator type: " + paramString1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Validator getInstance(String paramString1, String paramString2, PKIXBuilderParameters paramPKIXBuilderParameters) {
/* 194 */     if (!paramString1.equals("PKIX")) {
/* 195 */       throw new IllegalArgumentException("getInstance(PKIXBuilderParameters) can only be used with PKIX validator");
/*     */     }
/*     */ 
/*     */     
/* 199 */     return new PKIXValidator(paramString2, paramPKIXBuilderParameters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final X509Certificate[] validate(X509Certificate[] paramArrayOfX509Certificate) throws CertificateException {
/* 207 */     return validate(paramArrayOfX509Certificate, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final X509Certificate[] validate(X509Certificate[] paramArrayOfX509Certificate, Collection<X509Certificate> paramCollection) throws CertificateException {
/* 217 */     return validate(paramArrayOfX509Certificate, paramCollection, null);
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
/*     */   public final X509Certificate[] validate(X509Certificate[] paramArrayOfX509Certificate, Collection<X509Certificate> paramCollection, Object paramObject) throws CertificateException {
/* 238 */     return validate(paramArrayOfX509Certificate, paramCollection, null, paramObject);
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
/*     */   public final X509Certificate[] validate(X509Certificate[] paramArrayOfX509Certificate, Collection<X509Certificate> paramCollection, AlgorithmConstraints paramAlgorithmConstraints, Object paramObject) throws CertificateException {
/* 262 */     paramArrayOfX509Certificate = engineValidate(paramArrayOfX509Certificate, paramCollection, paramAlgorithmConstraints, paramObject);
/*     */ 
/*     */     
/* 265 */     if (paramArrayOfX509Certificate.length > 1) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 272 */       boolean bool = (this.type == "PKIX") ? false : true;
/*     */       
/* 274 */       this.endEntityChecker.check(paramArrayOfX509Certificate, paramObject, bool);
/*     */     } 
/*     */ 
/*     */     
/* 278 */     return paramArrayOfX509Certificate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract X509Certificate[] engineValidate(X509Certificate[] paramArrayOfX509Certificate, Collection<X509Certificate> paramCollection, AlgorithmConstraints paramAlgorithmConstraints, Object paramObject) throws CertificateException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Collection<X509Certificate> getTrustedCertificates();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setValidationDate(Date paramDate) {
/* 301 */     this.validationDate = paramDate;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/validator/Validator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */