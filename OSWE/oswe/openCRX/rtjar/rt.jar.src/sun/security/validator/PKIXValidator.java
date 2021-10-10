/*     */ package sun.security.validator;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.AlgorithmConstraints;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.PublicKey;
/*     */ import java.security.Timestamp;
/*     */ import java.security.cert.CertPath;
/*     */ import java.security.cert.CertPathBuilder;
/*     */ import java.security.cert.CertPathValidator;
/*     */ import java.security.cert.CertStore;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.CollectionCertStoreParameters;
/*     */ import java.security.cert.PKIXBuilderParameters;
/*     */ import java.security.cert.PKIXCertPathBuilderResult;
/*     */ import java.security.cert.PKIXCertPathValidatorResult;
/*     */ import java.security.cert.TrustAnchor;
/*     */ import java.security.cert.X509CertSelector;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import sun.security.action.GetBooleanAction;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ import sun.security.provider.certpath.AlgorithmChecker;
/*     */ import sun.security.provider.certpath.PKIXExtendedParameters;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PKIXValidator
/*     */   extends Validator
/*     */ {
/*  63 */   private static final boolean checkTLSRevocation = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("com.sun.net.ssl.checkRevocation"))).booleanValue();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final boolean TRY_VALIDATOR = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   private static final boolean ALLOW_NON_CA_ANCHOR = allowNonCaAnchor();
/*     */   
/*     */   private static boolean allowNonCaAnchor() {
/*  76 */     String str = GetPropertyAction.privilegedGetProperty("jdk.security.allowNonCaAnchor");
/*  77 */     return (str != null && (str.isEmpty() || str.equalsIgnoreCase("true")));
/*     */   }
/*     */   
/*     */   private final Set<X509Certificate> trustedCerts;
/*     */   private final PKIXBuilderParameters parameterTemplate;
/*  82 */   private int certPathLength = -1;
/*     */   
/*     */   private final Map<X500Principal, List<PublicKey>> trustedSubjects;
/*     */   
/*     */   private final CertificateFactory factory;
/*     */   
/*     */   private final boolean plugin;
/*     */   
/*     */   PKIXValidator(String paramString, Collection<X509Certificate> paramCollection) {
/*  91 */     super("PKIX", paramString);
/*  92 */     if (paramCollection instanceof Set) {
/*  93 */       this.trustedCerts = (Set<X509Certificate>)paramCollection;
/*     */     } else {
/*  95 */       this.trustedCerts = new HashSet<>(paramCollection);
/*     */     } 
/*  97 */     HashSet<TrustAnchor> hashSet = new HashSet();
/*  98 */     for (X509Certificate x509Certificate : paramCollection) {
/*  99 */       hashSet.add(new TrustAnchor(x509Certificate, null));
/*     */     }
/*     */     try {
/* 102 */       this.parameterTemplate = new PKIXBuilderParameters(hashSet, null);
/* 103 */     } catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
/* 104 */       throw new RuntimeException("Unexpected error: " + invalidAlgorithmParameterException.toString(), invalidAlgorithmParameterException);
/*     */     } 
/* 106 */     setDefaultParameters(paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     this.trustedSubjects = new HashMap<>();
/* 114 */     for (X509Certificate x509Certificate : paramCollection) {
/* 115 */       List<PublicKey> list; X500Principal x500Principal = x509Certificate.getSubjectX500Principal();
/*     */       
/* 117 */       if (this.trustedSubjects.containsKey(x500Principal)) {
/* 118 */         list = this.trustedSubjects.get(x500Principal);
/*     */       } else {
/* 120 */         list = new ArrayList();
/* 121 */         this.trustedSubjects.put(x500Principal, list);
/*     */       } 
/* 123 */       list.add(x509Certificate.getPublicKey());
/*     */     } 
/*     */     try {
/* 126 */       this.factory = CertificateFactory.getInstance("X.509");
/* 127 */     } catch (CertificateException certificateException) {
/* 128 */       throw new RuntimeException("Internal error", certificateException);
/*     */     } 
/* 130 */     this.plugin = paramString.equals("plugin code signing");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PKIXValidator(String paramString, PKIXBuilderParameters paramPKIXBuilderParameters) {
/* 137 */     super("PKIX", paramString);
/* 138 */     this.trustedCerts = new HashSet<>();
/* 139 */     for (TrustAnchor trustAnchor : paramPKIXBuilderParameters.getTrustAnchors()) {
/* 140 */       X509Certificate x509Certificate = trustAnchor.getTrustedCert();
/* 141 */       if (x509Certificate != null) {
/* 142 */         this.trustedCerts.add(x509Certificate);
/*     */       }
/*     */     } 
/* 145 */     this.parameterTemplate = paramPKIXBuilderParameters;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     this.trustedSubjects = new HashMap<>();
/* 153 */     for (X509Certificate x509Certificate : this.trustedCerts) {
/* 154 */       List<PublicKey> list; X500Principal x500Principal = x509Certificate.getSubjectX500Principal();
/*     */       
/* 156 */       if (this.trustedSubjects.containsKey(x500Principal)) {
/* 157 */         list = this.trustedSubjects.get(x500Principal);
/*     */       } else {
/* 159 */         list = new ArrayList();
/* 160 */         this.trustedSubjects.put(x500Principal, list);
/*     */       } 
/* 162 */       list.add(x509Certificate.getPublicKey());
/*     */     } 
/*     */     try {
/* 165 */       this.factory = CertificateFactory.getInstance("X.509");
/* 166 */     } catch (CertificateException certificateException) {
/* 167 */       throw new RuntimeException("Internal error", certificateException);
/*     */     } 
/* 169 */     this.plugin = paramString.equals("plugin code signing");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<X509Certificate> getTrustedCertificates() {
/* 176 */     return this.trustedCerts;
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
/*     */   public int getCertPathLength() {
/* 190 */     return this.certPathLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setDefaultParameters(String paramString) {
/* 198 */     if (paramString == "tls server" || paramString == "tls client") {
/*     */       
/* 200 */       this.parameterTemplate.setRevocationEnabled(checkTLSRevocation);
/*     */     } else {
/* 202 */       this.parameterTemplate.setRevocationEnabled(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PKIXBuilderParameters getParameters() {
/* 212 */     return this.parameterTemplate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   X509Certificate[] engineValidate(X509Certificate[] paramArrayOfX509Certificate, Collection<X509Certificate> paramCollection, AlgorithmConstraints paramAlgorithmConstraints, Object paramObject) throws CertificateException {
/* 220 */     if (paramArrayOfX509Certificate == null || paramArrayOfX509Certificate.length == 0) {
/* 221 */       throw new CertificateException("null or zero-length certificate chain");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 227 */     PKIXExtendedParameters pKIXExtendedParameters = null;
/*     */     
/*     */     try {
/* 230 */       pKIXExtendedParameters = new PKIXExtendedParameters((PKIXBuilderParameters)this.parameterTemplate.clone(), (paramObject instanceof Timestamp) ? (Timestamp)paramObject : null, this.variant);
/*     */ 
/*     */     
/*     */     }
/* 234 */     catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 239 */     if (paramAlgorithmConstraints != null) {
/* 240 */       pKIXExtendedParameters.addCertPathChecker(new AlgorithmChecker(paramAlgorithmConstraints, null, this.variant));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 247 */     X500Principal x500Principal1 = null;
/* 248 */     for (byte b = 0; b < paramArrayOfX509Certificate.length; b++) {
/* 249 */       X509Certificate x509Certificate1 = paramArrayOfX509Certificate[b];
/* 250 */       X500Principal x500Principal = x509Certificate1.getSubjectX500Principal();
/*     */       
/* 252 */       if (b == 0) {
/* 253 */         if (this.trustedCerts.contains(x509Certificate1)) {
/* 254 */           return new X509Certificate[] { paramArrayOfX509Certificate[0] };
/*     */         }
/*     */       } else {
/* 257 */         if (!x500Principal.equals(x500Principal1))
/*     */         {
/* 259 */           return doBuild(paramArrayOfX509Certificate, paramCollection, pKIXExtendedParameters);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 266 */         if (this.trustedCerts.contains(x509Certificate1) || (this.trustedSubjects
/* 267 */           .containsKey(x500Principal) && ((List)this.trustedSubjects
/* 268 */           .get(x500Principal)).contains(x509Certificate1
/* 269 */             .getPublicKey()))) {
/*     */           
/* 271 */           X509Certificate[] arrayOfX509Certificate = new X509Certificate[b];
/* 272 */           System.arraycopy(paramArrayOfX509Certificate, 0, arrayOfX509Certificate, 0, b);
/* 273 */           return doValidate(arrayOfX509Certificate, pKIXExtendedParameters);
/*     */         } 
/*     */       } 
/* 276 */       x500Principal1 = x509Certificate1.getIssuerX500Principal();
/*     */     } 
/*     */ 
/*     */     
/* 280 */     X509Certificate x509Certificate = paramArrayOfX509Certificate[paramArrayOfX509Certificate.length - 1];
/* 281 */     X500Principal x500Principal2 = x509Certificate.getIssuerX500Principal();
/* 282 */     X500Principal x500Principal3 = x509Certificate.getSubjectX500Principal();
/* 283 */     if (this.trustedSubjects.containsKey(x500Principal2) && 
/* 284 */       isSignatureValid(this.trustedSubjects.get(x500Principal2), x509Certificate)) {
/* 285 */       return doValidate(paramArrayOfX509Certificate, pKIXExtendedParameters);
/*     */     }
/*     */ 
/*     */     
/* 289 */     if (this.plugin) {
/*     */ 
/*     */ 
/*     */       
/* 293 */       if (paramArrayOfX509Certificate.length > 1) {
/* 294 */         X509Certificate[] arrayOfX509Certificate = new X509Certificate[paramArrayOfX509Certificate.length - 1];
/*     */         
/* 296 */         System.arraycopy(paramArrayOfX509Certificate, 0, arrayOfX509Certificate, 0, arrayOfX509Certificate.length);
/*     */ 
/*     */         
/*     */         try {
/* 300 */           pKIXExtendedParameters
/* 301 */             .setTrustAnchors(Collections.singleton(new TrustAnchor(paramArrayOfX509Certificate[paramArrayOfX509Certificate.length - 1], null)));
/*     */         }
/* 303 */         catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
/*     */           
/* 305 */           throw new CertificateException(invalidAlgorithmParameterException);
/*     */         } 
/* 307 */         doValidate(arrayOfX509Certificate, pKIXExtendedParameters);
/*     */       } 
/*     */ 
/*     */       
/* 311 */       throw new ValidatorException(ValidatorException.T_NO_TRUST_ANCHOR);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 317 */     return doBuild(paramArrayOfX509Certificate, paramCollection, pKIXExtendedParameters);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isSignatureValid(List<PublicKey> paramList, X509Certificate paramX509Certificate) {
/* 322 */     if (this.plugin) {
/* 323 */       for (PublicKey publicKey : paramList) {
/*     */         try {
/* 325 */           paramX509Certificate.verify(publicKey);
/* 326 */           return true;
/* 327 */         } catch (Exception exception) {}
/*     */       } 
/*     */ 
/*     */       
/* 331 */       return false;
/*     */     } 
/* 333 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private static X509Certificate[] toArray(CertPath paramCertPath, TrustAnchor paramTrustAnchor) throws CertificateException {
/* 338 */     X509Certificate x509Certificate = paramTrustAnchor.getTrustedCert();
/* 339 */     if (x509Certificate == null) {
/* 340 */       throw new ValidatorException("TrustAnchor must be specified as certificate");
/*     */     }
/*     */ 
/*     */     
/* 344 */     verifyTrustAnchor(x509Certificate);
/*     */ 
/*     */     
/* 347 */     List<? extends Certificate> list = paramCertPath.getCertificates();
/* 348 */     X509Certificate[] arrayOfX509Certificate = new X509Certificate[list.size() + 1];
/* 349 */     list.toArray(arrayOfX509Certificate);
/* 350 */     arrayOfX509Certificate[arrayOfX509Certificate.length - 1] = x509Certificate;
/* 351 */     return arrayOfX509Certificate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setDate(PKIXBuilderParameters paramPKIXBuilderParameters) {
/* 359 */     Date date = this.validationDate;
/* 360 */     if (date != null) {
/* 361 */       paramPKIXBuilderParameters.setDate(date);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private X509Certificate[] doValidate(X509Certificate[] paramArrayOfX509Certificate, PKIXBuilderParameters paramPKIXBuilderParameters) throws CertificateException {
/*     */     try {
/* 368 */       setDate(paramPKIXBuilderParameters);
/*     */ 
/*     */       
/* 371 */       CertPathValidator certPathValidator = CertPathValidator.getInstance("PKIX");
/* 372 */       CertPath certPath = this.factory.generateCertPath(Arrays.asList((Certificate[])paramArrayOfX509Certificate));
/* 373 */       this.certPathLength = paramArrayOfX509Certificate.length;
/*     */       
/* 375 */       PKIXCertPathValidatorResult pKIXCertPathValidatorResult = (PKIXCertPathValidatorResult)certPathValidator.validate(certPath, paramPKIXBuilderParameters);
/*     */       
/* 377 */       return toArray(certPath, pKIXCertPathValidatorResult.getTrustAnchor());
/* 378 */     } catch (GeneralSecurityException generalSecurityException) {
/* 379 */       throw new ValidatorException("PKIX path validation failed: " + generalSecurityException
/* 380 */           .toString(), generalSecurityException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void verifyTrustAnchor(X509Certificate paramX509Certificate) throws ValidatorException {
/* 391 */     if (ALLOW_NON_CA_ANCHOR) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 396 */     if (paramX509Certificate.getVersion() < 3) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 401 */     if (paramX509Certificate.getBasicConstraints() == -1) {
/* 402 */       throw new ValidatorException("TrustAnchor with subject \"" + paramX509Certificate
/*     */           
/* 404 */           .getSubjectX500Principal() + "\" is not a CA certificate");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 410 */     boolean[] arrayOfBoolean = paramX509Certificate.getKeyUsage();
/* 411 */     if (arrayOfBoolean != null && !arrayOfBoolean[5]) {
/* 412 */       throw new ValidatorException("TrustAnchor with subject \"" + paramX509Certificate
/*     */           
/* 414 */           .getSubjectX500Principal() + "\" does not have keyCertSign bit set in KeyUsage extension");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private X509Certificate[] doBuild(X509Certificate[] paramArrayOfX509Certificate, Collection<X509Certificate> paramCollection, PKIXBuilderParameters paramPKIXBuilderParameters) throws CertificateException {
/*     */     try {
/* 424 */       setDate(paramPKIXBuilderParameters);
/*     */ 
/*     */       
/* 427 */       X509CertSelector x509CertSelector = new X509CertSelector();
/* 428 */       x509CertSelector.setCertificate(paramArrayOfX509Certificate[0]);
/* 429 */       paramPKIXBuilderParameters.setTargetCertConstraints(x509CertSelector);
/*     */ 
/*     */       
/* 432 */       ArrayList<X509Certificate> arrayList = new ArrayList();
/*     */       
/* 434 */       arrayList.addAll(Arrays.asList(paramArrayOfX509Certificate));
/* 435 */       if (paramCollection != null) {
/* 436 */         arrayList.addAll(paramCollection);
/*     */       }
/* 438 */       CertStore certStore = CertStore.getInstance("Collection", new CollectionCertStoreParameters(arrayList));
/*     */       
/* 440 */       paramPKIXBuilderParameters.addCertStore(certStore);
/*     */ 
/*     */       
/* 443 */       CertPathBuilder certPathBuilder = CertPathBuilder.getInstance("PKIX");
/*     */       
/* 445 */       PKIXCertPathBuilderResult pKIXCertPathBuilderResult = (PKIXCertPathBuilderResult)certPathBuilder.build(paramPKIXBuilderParameters);
/*     */       
/* 447 */       return toArray(pKIXCertPathBuilderResult.getCertPath(), pKIXCertPathBuilderResult.getTrustAnchor());
/* 448 */     } catch (GeneralSecurityException generalSecurityException) {
/* 449 */       throw new ValidatorException("PKIX path building failed: " + generalSecurityException
/* 450 */           .toString(), generalSecurityException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/validator/PKIXValidator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */