/*     */ package sun.security.provider.certpath;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.PublicKey;
/*     */ import java.security.SignatureException;
/*     */ import java.security.cert.CertPathValidatorException;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateExpiredException;
/*     */ import java.security.cert.CertificateNotYetValidException;
/*     */ import java.security.cert.PKIXCertPathChecker;
/*     */ import java.security.cert.PKIXReason;
/*     */ import java.security.cert.TrustAnchor;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.security.interfaces.DSAParams;
/*     */ import java.security.interfaces.DSAPublicKey;
/*     */ import java.security.spec.DSAPublicKeySpec;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.Set;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.x509.X500Name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class BasicChecker
/*     */   extends PKIXCertPathChecker
/*     */ {
/*  62 */   private static final Debug debug = Debug.getInstance("certpath");
/*     */ 
/*     */   
/*     */   private final PublicKey trustedPubKey;
/*     */ 
/*     */   
/*     */   private final X500Principal caName;
/*     */ 
/*     */   
/*     */   private final Date date;
/*     */   
/*     */   private final String sigProvider;
/*     */   
/*     */   private final boolean sigOnly;
/*     */   
/*     */   private X500Principal prevSubject;
/*     */   
/*     */   private PublicKey prevPubKey;
/*     */ 
/*     */   
/*     */   BasicChecker(TrustAnchor paramTrustAnchor, Date paramDate, String paramString, boolean paramBoolean) {
/*  83 */     if (paramTrustAnchor.getTrustedCert() != null) {
/*  84 */       this.trustedPubKey = paramTrustAnchor.getTrustedCert().getPublicKey();
/*  85 */       this.caName = paramTrustAnchor.getTrustedCert().getSubjectX500Principal();
/*     */     } else {
/*  87 */       this.trustedPubKey = paramTrustAnchor.getCAPublicKey();
/*  88 */       this.caName = paramTrustAnchor.getCA();
/*     */     } 
/*  90 */     this.date = paramDate;
/*  91 */     this.sigProvider = paramString;
/*  92 */     this.sigOnly = paramBoolean;
/*  93 */     this.prevPubKey = this.trustedPubKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(boolean paramBoolean) throws CertPathValidatorException {
/* 102 */     if (!paramBoolean) {
/* 103 */       this.prevPubKey = this.trustedPubKey;
/* 104 */       if (PKIX.isDSAPublicKeyWithoutParams(this.prevPubKey))
/*     */       {
/*     */ 
/*     */         
/* 108 */         throw new CertPathValidatorException("Key parameters missing");
/*     */       }
/* 110 */       this.prevSubject = this.caName;
/*     */     } else {
/* 112 */       throw new CertPathValidatorException("forward checking not supported");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isForwardCheckingSupported() {
/* 119 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getSupportedExtensions() {
/* 124 */     return null;
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
/*     */   public void check(Certificate paramCertificate, Collection<String> paramCollection) throws CertPathValidatorException {
/* 141 */     X509Certificate x509Certificate = (X509Certificate)paramCertificate;
/*     */     
/* 143 */     if (!this.sigOnly) {
/* 144 */       verifyValidity(x509Certificate);
/* 145 */       verifyNameChaining(x509Certificate);
/*     */     } 
/* 147 */     verifySignature(x509Certificate);
/*     */     
/* 149 */     updateState(x509Certificate);
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
/*     */   private void verifySignature(X509Certificate paramX509Certificate) throws CertPathValidatorException {
/* 161 */     String str = "signature";
/* 162 */     if (debug != null) {
/* 163 */       debug.println("---checking " + str + "...");
/*     */     }
/*     */     try {
/* 166 */       paramX509Certificate.verify(this.prevPubKey, this.sigProvider);
/* 167 */     } catch (SignatureException signatureException) {
/* 168 */       throw new CertPathValidatorException(str + " check failed", signatureException, null, -1, CertPathValidatorException.BasicReason.INVALID_SIGNATURE);
/*     */     
/*     */     }
/* 171 */     catch (GeneralSecurityException generalSecurityException) {
/* 172 */       throw new CertPathValidatorException(str + " check failed", generalSecurityException);
/*     */     } 
/*     */     
/* 175 */     if (debug != null) {
/* 176 */       debug.println(str + " verified.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void verifyValidity(X509Certificate paramX509Certificate) throws CertPathValidatorException {
/* 185 */     String str = "validity";
/* 186 */     if (debug != null) {
/* 187 */       debug.println("---checking " + str + ":" + this.date.toString() + "...");
/*     */     }
/*     */     try {
/* 190 */       paramX509Certificate.checkValidity(this.date);
/* 191 */     } catch (CertificateExpiredException certificateExpiredException) {
/* 192 */       throw new CertPathValidatorException(str + " check failed", certificateExpiredException, null, -1, CertPathValidatorException.BasicReason.EXPIRED);
/*     */     }
/* 194 */     catch (CertificateNotYetValidException certificateNotYetValidException) {
/* 195 */       throw new CertPathValidatorException(str + " check failed", certificateNotYetValidException, null, -1, CertPathValidatorException.BasicReason.NOT_YET_VALID);
/*     */     } 
/*     */ 
/*     */     
/* 199 */     if (debug != null) {
/* 200 */       debug.println(str + " verified.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void verifyNameChaining(X509Certificate paramX509Certificate) throws CertPathValidatorException {
/* 209 */     if (this.prevSubject != null) {
/*     */       
/* 211 */       String str = "subject/issuer name chaining";
/* 212 */       if (debug != null) {
/* 213 */         debug.println("---checking " + str + "...");
/*     */       }
/* 215 */       X500Principal x500Principal = paramX509Certificate.getIssuerX500Principal();
/*     */ 
/*     */       
/* 218 */       if (X500Name.asX500Name(x500Principal).isEmpty()) {
/* 219 */         throw new CertPathValidatorException(str + " check failed: empty/null issuer DN in certificate is invalid", null, null, -1, PKIXReason.NAME_CHAINING);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 225 */       if (!x500Principal.equals(this.prevSubject)) {
/* 226 */         throw new CertPathValidatorException(str + " check failed", null, null, -1, PKIXReason.NAME_CHAINING);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 231 */       if (debug != null) {
/* 232 */         debug.println(str + " verified.");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateState(X509Certificate paramX509Certificate) throws CertPathValidatorException {
/* 242 */     PublicKey publicKey = paramX509Certificate.getPublicKey();
/* 243 */     if (debug != null) {
/* 244 */       debug.println("BasicChecker.updateState issuer: " + paramX509Certificate
/* 245 */           .getIssuerX500Principal().toString() + "; subject: " + paramX509Certificate
/* 246 */           .getSubjectX500Principal() + "; serial#: " + paramX509Certificate
/* 247 */           .getSerialNumber().toString());
/*     */     }
/* 249 */     if (PKIX.isDSAPublicKeyWithoutParams(publicKey)) {
/*     */       
/* 251 */       publicKey = makeInheritedParamsKey(publicKey, this.prevPubKey);
/* 252 */       if (debug != null) debug.println("BasicChecker.updateState Made key with inherited params");
/*     */     
/*     */     } 
/* 255 */     this.prevPubKey = publicKey;
/* 256 */     this.prevSubject = paramX509Certificate.getSubjectX500Principal();
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
/*     */   static PublicKey makeInheritedParamsKey(PublicKey paramPublicKey1, PublicKey paramPublicKey2) throws CertPathValidatorException {
/* 271 */     if (!(paramPublicKey1 instanceof DSAPublicKey) || !(paramPublicKey2 instanceof DSAPublicKey))
/*     */     {
/* 273 */       throw new CertPathValidatorException("Input key is not appropriate type for inheriting parameters");
/*     */     }
/*     */     
/* 276 */     DSAParams dSAParams = ((DSAPublicKey)paramPublicKey2).getParams();
/* 277 */     if (dSAParams == null)
/* 278 */       throw new CertPathValidatorException("Key parameters missing"); 
/*     */     try {
/* 280 */       BigInteger bigInteger = ((DSAPublicKey)paramPublicKey1).getY();
/* 281 */       KeyFactory keyFactory = KeyFactory.getInstance("DSA");
/*     */ 
/*     */ 
/*     */       
/* 285 */       DSAPublicKeySpec dSAPublicKeySpec = new DSAPublicKeySpec(bigInteger, dSAParams.getP(), dSAParams.getQ(), dSAParams.getG());
/* 286 */       return keyFactory.generatePublic(dSAPublicKeySpec);
/* 287 */     } catch (GeneralSecurityException generalSecurityException) {
/* 288 */       throw new CertPathValidatorException("Unable to generate key with inherited parameters: " + generalSecurityException
/*     */           
/* 290 */           .getMessage(), generalSecurityException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PublicKey getPublicKey() {
/* 300 */     return this.prevPubKey;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/BasicChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */