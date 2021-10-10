/*     */ package sun.security.provider.certpath;
/*     */ 
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.cert.CertPath;
/*     */ import java.security.cert.CertPathChecker;
/*     */ import java.security.cert.CertPathParameters;
/*     */ import java.security.cert.CertPathValidatorException;
/*     */ import java.security.cert.CertPathValidatorResult;
/*     */ import java.security.cert.CertPathValidatorSpi;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.PKIXCertPathChecker;
/*     */ import java.security.cert.PKIXCertPathValidatorResult;
/*     */ import java.security.cert.PKIXReason;
/*     */ import java.security.cert.TrustAnchor;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import sun.security.util.Debug;
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
/*     */ public final class PKIXCertPathValidator
/*     */   extends CertPathValidatorSpi
/*     */ {
/*  49 */   private static final Debug debug = Debug.getInstance("certpath");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CertPathChecker engineGetRevocationChecker() {
/*  58 */     return new RevocationChecker();
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
/*     */   public CertPathValidatorResult engineValidate(CertPath paramCertPath, CertPathParameters paramCertPathParameters) throws CertPathValidatorException, InvalidAlgorithmParameterException {
/*  79 */     PKIX.ValidatorParams validatorParams = PKIX.checkParams(paramCertPath, paramCertPathParameters);
/*  80 */     return validate(validatorParams);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static PKIXCertPathValidatorResult validate(PKIX.ValidatorParams paramValidatorParams) throws CertPathValidatorException {
/*  86 */     if (debug != null) {
/*  87 */       debug.println("PKIXCertPathValidator.engineValidate()...");
/*     */     }
/*     */ 
/*     */     
/*  91 */     AdaptableX509CertSelector adaptableX509CertSelector = null;
/*  92 */     List<X509Certificate> list = paramValidatorParams.certificates();
/*  93 */     if (!list.isEmpty()) {
/*  94 */       adaptableX509CertSelector = new AdaptableX509CertSelector();
/*  95 */       X509Certificate x509Certificate = list.get(0);
/*     */       
/*  97 */       adaptableX509CertSelector.setSubject(x509Certificate.getIssuerX500Principal());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 103 */         X509CertImpl x509CertImpl = X509CertImpl.toImpl(x509Certificate);
/* 104 */         adaptableX509CertSelector.setSkiAndSerialNumber(x509CertImpl
/* 105 */             .getAuthorityKeyIdentifierExtension());
/* 106 */       } catch (CertificateException|java.io.IOException certificateException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 111 */     CertPathValidatorException certPathValidatorException = null;
/*     */ 
/*     */ 
/*     */     
/* 115 */     for (TrustAnchor trustAnchor : paramValidatorParams.trustAnchors()) {
/* 116 */       X509Certificate x509Certificate = trustAnchor.getTrustedCert();
/* 117 */       if (x509Certificate != null) {
/*     */ 
/*     */         
/* 120 */         if (adaptableX509CertSelector != null && !adaptableX509CertSelector.match(x509Certificate)) {
/* 121 */           if (debug != null) {
/* 122 */             debug.println("NO - don't try this trustedCert");
/*     */           }
/*     */           
/*     */           continue;
/*     */         } 
/* 127 */         if (debug != null) {
/* 128 */           debug.println("YES - try this trustedCert");
/* 129 */           debug.println("anchor.getTrustedCert().getSubjectX500Principal() = " + x509Certificate
/*     */               
/* 131 */               .getSubjectX500Principal());
/*     */         }
/*     */       
/* 134 */       } else if (debug != null) {
/* 135 */         debug.println("PKIXCertPathValidator.engineValidate(): anchor.getTrustedCert() == null");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 141 */         return validate(trustAnchor, paramValidatorParams);
/* 142 */       } catch (CertPathValidatorException certPathValidatorException1) {
/*     */         
/* 144 */         certPathValidatorException = certPathValidatorException1;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 150 */     if (certPathValidatorException != null) {
/* 151 */       throw certPathValidatorException;
/*     */     }
/*     */     
/* 154 */     throw new CertPathValidatorException("Path does not chain with any of the trust anchors", null, null, -1, PKIXReason.NO_TRUST_ANCHOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static PKIXCertPathValidatorResult validate(TrustAnchor paramTrustAnchor, PKIX.ValidatorParams paramValidatorParams) throws CertPathValidatorException {
/* 164 */     UntrustedChecker untrustedChecker = new UntrustedChecker();
/* 165 */     X509Certificate x509Certificate = paramTrustAnchor.getTrustedCert();
/* 166 */     if (x509Certificate != null) {
/* 167 */       untrustedChecker.check(x509Certificate);
/*     */     }
/*     */     
/* 170 */     int i = paramValidatorParams.certificates().size();
/*     */ 
/*     */     
/* 173 */     ArrayList<UntrustedChecker> arrayList = new ArrayList();
/*     */     
/* 175 */     arrayList.add(untrustedChecker);
/* 176 */     arrayList.add(new AlgorithmChecker(paramTrustAnchor, null, paramValidatorParams.date(), paramValidatorParams
/* 177 */           .timestamp(), paramValidatorParams.variant()));
/* 178 */     arrayList.add(new KeyChecker(i, paramValidatorParams
/* 179 */           .targetCertConstraints()));
/* 180 */     arrayList.add(new ConstraintsChecker(i));
/*     */ 
/*     */     
/* 183 */     PolicyNodeImpl policyNodeImpl = new PolicyNodeImpl(null, "2.5.29.32.0", null, false, Collections.singleton("2.5.29.32.0"), false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 190 */     PolicyChecker policyChecker = new PolicyChecker(paramValidatorParams.initialPolicies(), i, paramValidatorParams.explicitPolicyRequired(), paramValidatorParams.policyMappingInhibited(), paramValidatorParams.anyPolicyInhibited(), paramValidatorParams.policyQualifiersRejected(), policyNodeImpl);
/*     */     
/* 192 */     arrayList.add(policyChecker);
/*     */ 
/*     */ 
/*     */     
/* 196 */     Date date = null;
/*     */ 
/*     */     
/* 199 */     if ((paramValidatorParams.variant() == "code signing" || paramValidatorParams
/* 200 */       .variant() == "plugin code signing") && paramValidatorParams
/* 201 */       .timestamp() != null) {
/* 202 */       date = paramValidatorParams.timestamp().getTimestamp();
/*     */     } else {
/* 204 */       date = paramValidatorParams.date();
/*     */     } 
/*     */     
/* 207 */     BasicChecker basicChecker = new BasicChecker(paramTrustAnchor, date, paramValidatorParams.sigProvider(), false);
/* 208 */     arrayList.add(basicChecker);
/*     */     
/* 210 */     boolean bool = false;
/* 211 */     List<PKIXCertPathChecker> list = paramValidatorParams.certPathCheckers();
/* 212 */     for (PKIXCertPathChecker pKIXCertPathChecker : list) {
/* 213 */       if (pKIXCertPathChecker instanceof java.security.cert.PKIXRevocationChecker) {
/* 214 */         if (bool) {
/* 215 */           throw new CertPathValidatorException("Only one PKIXRevocationChecker can be specified");
/*     */         }
/*     */         
/* 218 */         bool = true;
/*     */         
/* 220 */         if (pKIXCertPathChecker instanceof RevocationChecker) {
/* 221 */           ((RevocationChecker)pKIXCertPathChecker).init(paramTrustAnchor, paramValidatorParams);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 227 */     if (paramValidatorParams.revocationEnabled() && !bool) {
/* 228 */       arrayList.add(new RevocationChecker(paramTrustAnchor, paramValidatorParams));
/*     */     }
/*     */     
/* 231 */     arrayList.addAll(list);
/*     */     
/* 233 */     PKIXMasterCertPathValidator.validate(paramValidatorParams.certPath(), paramValidatorParams
/* 234 */         .certificates(), (List)arrayList);
/*     */ 
/*     */     
/* 237 */     return new PKIXCertPathValidatorResult(paramTrustAnchor, policyChecker.getPolicyTree(), basicChecker
/* 238 */         .getPublicKey());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/PKIXCertPathValidator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */