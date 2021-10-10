/*     */ package sun.security.provider.certpath;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.cert.CertPathValidatorException;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.PKIXCertPathChecker;
/*     */ import java.security.cert.PKIXReason;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.x509.NameConstraintsExtension;
/*     */ import sun.security.x509.PKIXExtensions;
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
/*     */ class ConstraintsChecker
/*     */   extends PKIXCertPathChecker
/*     */ {
/*  55 */   private static final Debug debug = Debug.getInstance("certpath");
/*     */ 
/*     */   
/*     */   private final int certPathLength;
/*     */ 
/*     */   
/*     */   private int maxPathLength;
/*     */ 
/*     */   
/*     */   private int i;
/*     */   
/*     */   private NameConstraintsExtension prevNC;
/*     */   
/*     */   private Set<String> supportedExts;
/*     */ 
/*     */   
/*     */   ConstraintsChecker(int paramInt) {
/*  72 */     this.certPathLength = paramInt;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(boolean paramBoolean) throws CertPathValidatorException {
/*  77 */     if (!paramBoolean) {
/*  78 */       this.i = 0;
/*  79 */       this.maxPathLength = this.certPathLength;
/*  80 */       this.prevNC = null;
/*     */     } else {
/*  82 */       throw new CertPathValidatorException("forward checking not supported");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isForwardCheckingSupported() {
/*  89 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getSupportedExtensions() {
/*  94 */     if (this.supportedExts == null) {
/*  95 */       this.supportedExts = new HashSet<>(2);
/*  96 */       this.supportedExts.add(PKIXExtensions.BasicConstraints_Id.toString());
/*  97 */       this.supportedExts.add(PKIXExtensions.NameConstraints_Id.toString());
/*  98 */       this.supportedExts = Collections.unmodifiableSet(this.supportedExts);
/*     */     } 
/* 100 */     return this.supportedExts;
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
/* 117 */     X509Certificate x509Certificate = (X509Certificate)paramCertificate;
/*     */     
/* 119 */     this.i++;
/*     */ 
/*     */     
/* 122 */     checkBasicConstraints(x509Certificate);
/* 123 */     verifyNameConstraints(x509Certificate);
/*     */     
/* 125 */     if (paramCollection != null && !paramCollection.isEmpty()) {
/* 126 */       paramCollection.remove(PKIXExtensions.BasicConstraints_Id.toString());
/* 127 */       paramCollection.remove(PKIXExtensions.NameConstraints_Id.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void verifyNameConstraints(X509Certificate paramX509Certificate) throws CertPathValidatorException {
/* 137 */     String str = "name constraints";
/* 138 */     if (debug != null) {
/* 139 */       debug.println("---checking " + str + "...");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 145 */     if (this.prevNC != null && (this.i == this.certPathLength || 
/* 146 */       !X509CertImpl.isSelfIssued(paramX509Certificate))) {
/* 147 */       if (debug != null) {
/* 148 */         debug.println("prevNC = " + this.prevNC + ", currDN = " + paramX509Certificate
/* 149 */             .getSubjectX500Principal());
/*     */       }
/*     */       
/*     */       try {
/* 153 */         if (!this.prevNC.verify(paramX509Certificate)) {
/* 154 */           throw new CertPathValidatorException(str + " check failed", null, null, -1, PKIXReason.INVALID_NAME);
/*     */         }
/*     */       }
/* 157 */       catch (IOException iOException) {
/* 158 */         throw new CertPathValidatorException(iOException);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 163 */     this.prevNC = mergeNameConstraints(paramX509Certificate, this.prevNC);
/*     */     
/* 165 */     if (debug != null) {
/* 166 */       debug.println(str + " verified.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static NameConstraintsExtension mergeNameConstraints(X509Certificate paramX509Certificate, NameConstraintsExtension paramNameConstraintsExtension) throws CertPathValidatorException {
/*     */     X509CertImpl x509CertImpl;
/*     */     try {
/* 178 */       x509CertImpl = X509CertImpl.toImpl(paramX509Certificate);
/* 179 */     } catch (CertificateException certificateException) {
/* 180 */       throw new CertPathValidatorException(certificateException);
/*     */     } 
/*     */ 
/*     */     
/* 184 */     NameConstraintsExtension nameConstraintsExtension = x509CertImpl.getNameConstraintsExtension();
/*     */     
/* 186 */     if (debug != null) {
/* 187 */       debug.println("prevNC = " + paramNameConstraintsExtension + ", newNC = " + 
/* 188 */           String.valueOf(nameConstraintsExtension));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 193 */     if (paramNameConstraintsExtension == null) {
/* 194 */       if (debug != null) {
/* 195 */         debug.println("mergedNC = " + String.valueOf(nameConstraintsExtension));
/*     */       }
/* 197 */       if (nameConstraintsExtension == null) {
/* 198 */         return nameConstraintsExtension;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 203 */       return (NameConstraintsExtension)nameConstraintsExtension.clone();
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 208 */       paramNameConstraintsExtension.merge(nameConstraintsExtension);
/* 209 */     } catch (IOException iOException) {
/* 210 */       throw new CertPathValidatorException(iOException);
/*     */     } 
/* 212 */     if (debug != null) {
/* 213 */       debug.println("mergedNC = " + paramNameConstraintsExtension);
/*     */     }
/* 215 */     return paramNameConstraintsExtension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkBasicConstraints(X509Certificate paramX509Certificate) throws CertPathValidatorException {
/* 225 */     String str = "basic constraints";
/* 226 */     if (debug != null) {
/* 227 */       debug.println("---checking " + str + "...");
/* 228 */       debug.println("i = " + this.i + ", maxPathLength = " + this.maxPathLength);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 233 */     if (this.i < this.certPathLength) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 247 */       int i = -1;
/* 248 */       if (paramX509Certificate.getVersion() < 3) {
/* 249 */         if (this.i == 1 && 
/* 250 */           X509CertImpl.isSelfIssued(paramX509Certificate)) {
/* 251 */           i = Integer.MAX_VALUE;
/*     */         }
/*     */       } else {
/*     */         
/* 255 */         i = paramX509Certificate.getBasicConstraints();
/*     */       } 
/*     */       
/* 258 */       if (i == -1) {
/* 259 */         throw new CertPathValidatorException(str + " check failed: this is not a CA certificate", null, null, -1, PKIXReason.NOT_CA_CERT);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 264 */       if (!X509CertImpl.isSelfIssued(paramX509Certificate)) {
/* 265 */         if (this.maxPathLength <= 0) {
/* 266 */           throw new CertPathValidatorException(str + " check failed: pathLenConstraint violated - this cert must be the last cert in the certification path", null, null, -1, PKIXReason.PATH_TOO_LONG);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 272 */         this.maxPathLength--;
/*     */       } 
/* 274 */       if (i < this.maxPathLength) {
/* 275 */         this.maxPathLength = i;
/*     */       }
/*     */     } 
/* 278 */     if (debug != null) {
/* 279 */       debug.println("after processing, maxPathLength = " + this.maxPathLength);
/* 280 */       debug.println(str + " verified.");
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
/*     */   static int mergeBasicConstraints(X509Certificate paramX509Certificate, int paramInt) {
/* 296 */     int i = paramX509Certificate.getBasicConstraints();
/*     */     
/* 298 */     if (!X509CertImpl.isSelfIssued(paramX509Certificate)) {
/* 299 */       paramInt--;
/*     */     }
/*     */     
/* 302 */     if (i < paramInt) {
/* 303 */       paramInt = i;
/*     */     }
/*     */     
/* 306 */     return paramInt;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/ConstraintsChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */