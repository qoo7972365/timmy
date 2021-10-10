/*     */ package sun.security.provider.certpath;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.security.AlgorithmConstraints;
/*     */ import java.security.AlgorithmParameters;
/*     */ import java.security.CryptoPrimitive;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.PublicKey;
/*     */ import java.security.Timestamp;
/*     */ import java.security.cert.CRLException;
/*     */ import java.security.cert.CertPathValidatorException;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.PKIXCertPathChecker;
/*     */ import java.security.cert.PKIXReason;
/*     */ import java.security.cert.TrustAnchor;
/*     */ import java.security.cert.X509CRL;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.security.interfaces.DSAParams;
/*     */ import java.security.interfaces.DSAPublicKey;
/*     */ import java.security.spec.DSAPublicKeySpec;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
/*     */ import sun.security.util.AnchorCertificates;
/*     */ import sun.security.util.ConstraintsParameters;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.util.DisabledAlgorithmConstraints;
/*     */ import sun.security.util.KeyUtil;
/*     */ import sun.security.x509.AlgorithmId;
/*     */ import sun.security.x509.X509CRLImpl;
/*     */ import sun.security.x509.X509CertImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AlgorithmChecker
/*     */   extends PKIXCertPathChecker
/*     */ {
/*  77 */   private static final Debug debug = Debug.getInstance("certpath");
/*     */   
/*     */   private final AlgorithmConstraints constraints;
/*     */   
/*     */   private final PublicKey trustedPubKey;
/*     */   
/*     */   private final Date pkixdate;
/*     */   private PublicKey prevPubKey;
/*     */   private final Timestamp jarTimestamp;
/*     */   private final String variant;
/*  87 */   private static final Set<CryptoPrimitive> SIGNATURE_PRIMITIVE_SET = Collections.unmodifiableSet(EnumSet.of(CryptoPrimitive.SIGNATURE));
/*     */ 
/*     */   
/*  90 */   private static final Set<CryptoPrimitive> KU_PRIMITIVE_SET = Collections.unmodifiableSet(EnumSet.of(CryptoPrimitive.SIGNATURE, CryptoPrimitive.KEY_ENCAPSULATION, CryptoPrimitive.PUBLIC_KEY_ENCRYPTION, CryptoPrimitive.KEY_AGREEMENT));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   private static final DisabledAlgorithmConstraints certPathDefaultConstraints = new DisabledAlgorithmConstraints("jdk.certpath.disabledAlgorithms");
/*     */ 
/*     */ 
/*     */   
/* 101 */   private static final boolean publicCALimits = certPathDefaultConstraints
/* 102 */     .checkProperty("jdkCA");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean trustedMatch = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AlgorithmChecker(TrustAnchor paramTrustAnchor, String paramString) {
/* 118 */     this(paramTrustAnchor, certPathDefaultConstraints, null, null, paramString);
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
/*     */   public AlgorithmChecker(AlgorithmConstraints paramAlgorithmConstraints, Timestamp paramTimestamp, String paramString) {
/* 137 */     this(null, paramAlgorithmConstraints, null, paramTimestamp, paramString);
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
/*     */   public AlgorithmChecker(TrustAnchor paramTrustAnchor, AlgorithmConstraints paramAlgorithmConstraints, Date paramDate, Timestamp paramTimestamp, String paramString) {
/* 160 */     if (paramTrustAnchor != null) {
/* 161 */       if (paramTrustAnchor.getTrustedCert() != null) {
/* 162 */         this.trustedPubKey = paramTrustAnchor.getTrustedCert().getPublicKey();
/*     */         
/* 164 */         this.trustedMatch = checkFingerprint(paramTrustAnchor.getTrustedCert());
/* 165 */         if (this.trustedMatch && debug != null) {
/* 166 */           debug.println("trustedMatch = true");
/*     */         }
/*     */       } else {
/* 169 */         this.trustedPubKey = paramTrustAnchor.getCAPublicKey();
/*     */       } 
/*     */     } else {
/* 172 */       this.trustedPubKey = null;
/* 173 */       if (debug != null) {
/* 174 */         debug.println("TrustAnchor is null, trustedMatch is false.");
/*     */       }
/*     */     } 
/*     */     
/* 178 */     this.prevPubKey = this.trustedPubKey;
/* 179 */     this.constraints = (paramAlgorithmConstraints == null) ? certPathDefaultConstraints : paramAlgorithmConstraints;
/*     */ 
/*     */ 
/*     */     
/* 183 */     this.pkixdate = (paramTimestamp != null) ? paramTimestamp.getTimestamp() : paramDate;
/*     */     
/* 185 */     this.jarTimestamp = paramTimestamp;
/* 186 */     this.variant = (paramString == null) ? "generic" : paramString;
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
/*     */   public AlgorithmChecker(TrustAnchor paramTrustAnchor, Date paramDate, String paramString) {
/* 201 */     this(paramTrustAnchor, certPathDefaultConstraints, paramDate, null, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean checkFingerprint(X509Certificate paramX509Certificate) {
/* 207 */     if (!publicCALimits) {
/* 208 */       return false;
/*     */     }
/*     */     
/* 211 */     if (debug != null) {
/* 212 */       debug.println("AlgorithmChecker.contains: " + paramX509Certificate.getSigAlgName());
/*     */     }
/* 214 */     return AnchorCertificates.contains(paramX509Certificate);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(boolean paramBoolean) throws CertPathValidatorException {
/* 220 */     if (!paramBoolean) {
/* 221 */       if (this.trustedPubKey != null) {
/* 222 */         this.prevPubKey = this.trustedPubKey;
/*     */       } else {
/* 224 */         this.prevPubKey = null;
/*     */       } 
/*     */     } else {
/* 227 */       throw new CertPathValidatorException("forward checking not supported");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isForwardCheckingSupported() {
/* 236 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getSupportedExtensions() {
/* 241 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void check(Certificate paramCertificate, Collection<String> paramCollection) throws CertPathValidatorException {
/*     */     X509CertImpl x509CertImpl;
/*     */     AlgorithmId algorithmId;
/* 249 */     if (!(paramCertificate instanceof X509Certificate) || this.constraints == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 255 */     boolean[] arrayOfBoolean = ((X509Certificate)paramCertificate).getKeyUsage();
/* 256 */     if (arrayOfBoolean != null && arrayOfBoolean.length < 9) {
/* 257 */       throw new CertPathValidatorException("incorrect KeyUsage extension", null, null, -1, PKIXReason.INVALID_KEY_USAGE);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 265 */       x509CertImpl = X509CertImpl.toImpl((X509Certificate)paramCertificate);
/* 266 */       algorithmId = (AlgorithmId)x509CertImpl.get("x509.algorithm");
/* 267 */     } catch (CertificateException certificateException) {
/* 268 */       throw new CertPathValidatorException(certificateException);
/*     */     } 
/*     */     
/* 271 */     AlgorithmParameters algorithmParameters = algorithmId.getParameters();
/* 272 */     PublicKey publicKey = paramCertificate.getPublicKey();
/* 273 */     String str = x509CertImpl.getSigAlgName();
/*     */ 
/*     */     
/* 276 */     if (!this.constraints.permits(SIGNATURE_PRIMITIVE_SET, str, algorithmParameters))
/*     */     {
/* 278 */       throw new CertPathValidatorException("Algorithm constraints check failed on signature algorithm: " + str, null, null, -1, CertPathValidatorException.BasicReason.ALGORITHM_CONSTRAINED);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 285 */     Set<CryptoPrimitive> set = KU_PRIMITIVE_SET;
/*     */     
/* 287 */     if (arrayOfBoolean != null) {
/* 288 */       set = EnumSet.noneOf(CryptoPrimitive.class);
/*     */       
/* 290 */       if (arrayOfBoolean[0] || arrayOfBoolean[1] || arrayOfBoolean[5] || arrayOfBoolean[6])
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 295 */         set.add(CryptoPrimitive.SIGNATURE);
/*     */       }
/*     */       
/* 298 */       if (arrayOfBoolean[2]) {
/* 299 */         set.add(CryptoPrimitive.KEY_ENCAPSULATION);
/*     */       }
/*     */       
/* 302 */       if (arrayOfBoolean[3]) {
/* 303 */         set.add(CryptoPrimitive.PUBLIC_KEY_ENCRYPTION);
/*     */       }
/*     */       
/* 306 */       if (arrayOfBoolean[4]) {
/* 307 */         set.add(CryptoPrimitive.KEY_AGREEMENT);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 313 */       if (set.isEmpty()) {
/* 314 */         throw new CertPathValidatorException("incorrect KeyUsage extension bits", null, null, -1, PKIXReason.INVALID_KEY_USAGE);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 320 */     ConstraintsParameters constraintsParameters = new ConstraintsParameters((X509Certificate)paramCertificate, this.trustedMatch, this.pkixdate, this.jarTimestamp, this.variant);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 325 */     if (this.constraints instanceof DisabledAlgorithmConstraints) {
/* 326 */       ((DisabledAlgorithmConstraints)this.constraints).permits(str, constraintsParameters);
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 332 */       certPathDefaultConstraints.permits(str, constraintsParameters);
/*     */       
/* 334 */       if (!this.constraints.permits(set, publicKey)) {
/* 335 */         throw new CertPathValidatorException("Algorithm constraints check failed on key " + publicKey
/*     */             
/* 337 */             .getAlgorithm() + " with size of " + 
/* 338 */             KeyUtil.getKeySize(publicKey) + "bits", null, null, -1, CertPathValidatorException.BasicReason.ALGORITHM_CONSTRAINED);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 345 */     if (this.prevPubKey == null) {
/* 346 */       this.prevPubKey = publicKey;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 351 */     if (!this.constraints.permits(SIGNATURE_PRIMITIVE_SET, str, this.prevPubKey, algorithmParameters))
/*     */     {
/*     */       
/* 354 */       throw new CertPathValidatorException("Algorithm constraints check failed on signature algorithm: " + str, null, null, -1, CertPathValidatorException.BasicReason.ALGORITHM_CONSTRAINED);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 361 */     if (PKIX.isDSAPublicKeyWithoutParams(publicKey)) {
/*     */       
/* 363 */       if (!(this.prevPubKey instanceof DSAPublicKey)) {
/* 364 */         throw new CertPathValidatorException("Input key is not of a appropriate type for inheriting parameters");
/*     */       }
/*     */ 
/*     */       
/* 368 */       DSAParams dSAParams = ((DSAPublicKey)this.prevPubKey).getParams();
/* 369 */       if (dSAParams == null) {
/* 370 */         throw new CertPathValidatorException("Key parameters missing from public key.");
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 375 */         BigInteger bigInteger = ((DSAPublicKey)publicKey).getY();
/* 376 */         KeyFactory keyFactory = KeyFactory.getInstance("DSA");
/*     */         
/* 378 */         DSAPublicKeySpec dSAPublicKeySpec = new DSAPublicKeySpec(bigInteger, dSAParams.getP(), dSAParams.getQ(), dSAParams.getG());
/* 379 */         publicKey = keyFactory.generatePublic(dSAPublicKeySpec);
/* 380 */       } catch (GeneralSecurityException generalSecurityException) {
/* 381 */         throw new CertPathValidatorException("Unable to generate key with inherited parameters: " + generalSecurityException
/* 382 */             .getMessage(), generalSecurityException);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 387 */     this.prevPubKey = publicKey;
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
/*     */   void trySetTrustAnchor(TrustAnchor paramTrustAnchor) {
/* 402 */     if (this.prevPubKey == null) {
/* 403 */       if (paramTrustAnchor == null) {
/* 404 */         throw new IllegalArgumentException("The trust anchor cannot be null");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 409 */       if (paramTrustAnchor.getTrustedCert() != null) {
/* 410 */         this.prevPubKey = paramTrustAnchor.getTrustedCert().getPublicKey();
/*     */         
/* 412 */         this.trustedMatch = checkFingerprint(paramTrustAnchor.getTrustedCert());
/* 413 */         if (this.trustedMatch && debug != null) {
/* 414 */           debug.println("trustedMatch = true");
/*     */         }
/*     */       } else {
/* 417 */         this.prevPubKey = paramTrustAnchor.getCAPublicKey();
/*     */       } 
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
/*     */   static void check(PublicKey paramPublicKey, X509CRL paramX509CRL, String paramString) throws CertPathValidatorException {
/* 433 */     X509CRLImpl x509CRLImpl = null;
/*     */     try {
/* 435 */       x509CRLImpl = X509CRLImpl.toImpl(paramX509CRL);
/* 436 */     } catch (CRLException cRLException) {
/* 437 */       throw new CertPathValidatorException(cRLException);
/*     */     } 
/*     */     
/* 440 */     AlgorithmId algorithmId = x509CRLImpl.getSigAlgId();
/* 441 */     check(paramPublicKey, algorithmId, paramString);
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
/*     */   static void check(PublicKey paramPublicKey, AlgorithmId paramAlgorithmId, String paramString) throws CertPathValidatorException {
/* 454 */     String str = paramAlgorithmId.getName();
/* 455 */     AlgorithmParameters algorithmParameters = paramAlgorithmId.getParameters();
/*     */     
/* 457 */     certPathDefaultConstraints.permits(new ConstraintsParameters(str, algorithmParameters, paramPublicKey, paramString));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/AlgorithmChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */