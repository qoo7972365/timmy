/*     */ package sun.security.provider.certpath;
/*     */ 
/*     */ import java.security.cert.CertPathValidatorException;
/*     */ import java.security.cert.CertSelector;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.PKIXCertPathChecker;
/*     */ import java.security.cert.PKIXReason;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.x509.PKIXExtensions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class KeyChecker
/*     */   extends PKIXCertPathChecker
/*     */ {
/*  47 */   private static final Debug debug = Debug.getInstance("certpath");
/*     */ 
/*     */   
/*     */   private final int certPathLen;
/*     */   
/*     */   private final CertSelector targetConstraints;
/*     */   
/*     */   private int remainingCerts;
/*     */   
/*     */   private Set<String> supportedExts;
/*     */   
/*     */   private static final int KEY_CERT_SIGN = 5;
/*     */ 
/*     */   
/*     */   KeyChecker(int paramInt, CertSelector paramCertSelector) {
/*  62 */     this.certPathLen = paramInt;
/*  63 */     this.targetConstraints = paramCertSelector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(boolean paramBoolean) throws CertPathValidatorException {
/*  72 */     if (!paramBoolean) {
/*  73 */       this.remainingCerts = this.certPathLen;
/*     */     } else {
/*  75 */       throw new CertPathValidatorException("forward checking not supported");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isForwardCheckingSupported() {
/*  82 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getSupportedExtensions() {
/*  87 */     if (this.supportedExts == null) {
/*  88 */       this.supportedExts = new HashSet<>(3);
/*  89 */       this.supportedExts.add(PKIXExtensions.KeyUsage_Id.toString());
/*  90 */       this.supportedExts.add(PKIXExtensions.ExtendedKeyUsage_Id.toString());
/*  91 */       this.supportedExts.add(PKIXExtensions.SubjectAlternativeName_Id.toString());
/*  92 */       this.supportedExts = Collections.unmodifiableSet(this.supportedExts);
/*     */     } 
/*  94 */     return this.supportedExts;
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
/*     */   public void check(Certificate paramCertificate, Collection<String> paramCollection) throws CertPathValidatorException {
/* 109 */     X509Certificate x509Certificate = (X509Certificate)paramCertificate;
/*     */     
/* 111 */     this.remainingCerts--;
/*     */ 
/*     */     
/* 114 */     if (this.remainingCerts == 0) {
/* 115 */       if (this.targetConstraints != null && 
/* 116 */         !this.targetConstraints.match(x509Certificate)) {
/* 117 */         throw new CertPathValidatorException("target certificate constraints check failed");
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 122 */       verifyCAKeyUsage(x509Certificate);
/*     */     } 
/*     */ 
/*     */     
/* 126 */     if (paramCollection != null && !paramCollection.isEmpty()) {
/* 127 */       paramCollection.remove(PKIXExtensions.KeyUsage_Id.toString());
/* 128 */       paramCollection.remove(PKIXExtensions.ExtendedKeyUsage_Id.toString());
/* 129 */       paramCollection.remove(PKIXExtensions.SubjectAlternativeName_Id.toString());
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
/*     */   static void verifyCAKeyUsage(X509Certificate paramX509Certificate) throws CertPathValidatorException {
/* 143 */     String str = "CA key usage";
/* 144 */     if (debug != null) {
/* 145 */       debug.println("KeyChecker.verifyCAKeyUsage() ---checking " + str + "...");
/*     */     }
/*     */ 
/*     */     
/* 149 */     boolean[] arrayOfBoolean = paramX509Certificate.getKeyUsage();
/*     */ 
/*     */ 
/*     */     
/* 153 */     if (arrayOfBoolean == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 158 */     if (!arrayOfBoolean[5]) {
/* 159 */       throw new CertPathValidatorException(str + " check failed: keyCertSign bit is not set", null, null, -1, PKIXReason.INVALID_KEY_USAGE);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 164 */     if (debug != null)
/* 165 */       debug.println("KeyChecker.verifyCAKeyUsage() " + str + " verified."); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/KeyChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */