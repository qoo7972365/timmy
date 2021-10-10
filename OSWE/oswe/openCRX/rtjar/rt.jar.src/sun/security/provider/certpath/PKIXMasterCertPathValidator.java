/*     */ package sun.security.provider.certpath;
/*     */ 
/*     */ import java.security.cert.CertPath;
/*     */ import java.security.cert.CertPathValidatorException;
/*     */ import java.security.cert.PKIXCertPathChecker;
/*     */ import java.security.cert.PKIXReason;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.StringJoiner;
/*     */ import sun.security.util.Debug;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PKIXMasterCertPathValidator
/*     */ {
/*  50 */   private static final Debug debug = Debug.getInstance("certpath");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void validate(CertPath paramCertPath, List<X509Certificate> paramList, List<PKIXCertPathChecker> paramList1) throws CertPathValidatorException {
/*  74 */     int i = paramList.size();
/*     */     
/*  76 */     if (debug != null) {
/*  77 */       debug.println("--------------------------------------------------------------");
/*     */       
/*  79 */       debug.println("Executing PKIX certification path validation algorithm.");
/*     */     } 
/*     */ 
/*     */     
/*  83 */     for (byte b = 0; b < i; b++) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  92 */       X509Certificate x509Certificate = paramList.get(b);
/*     */       
/*  94 */       if (debug != null) {
/*  95 */         debug.println("Checking cert" + (b + 1) + " - Subject: " + x509Certificate
/*  96 */             .getSubjectX500Principal());
/*     */       }
/*     */       
/*  99 */       Set<String> set = x509Certificate.getCriticalExtensionOIDs();
/* 100 */       if (set == null) {
/* 101 */         set = Collections.emptySet();
/*     */       }
/*     */       
/* 104 */       if (debug != null && !set.isEmpty()) {
/* 105 */         StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
/* 106 */         for (String str : set) {
/* 107 */           stringJoiner.add(str);
/*     */         }
/* 109 */         debug.println("Set of critical extensions: " + stringJoiner
/* 110 */             .toString());
/*     */       } 
/*     */       
/* 113 */       for (byte b1 = 0; b1 < paramList1.size(); b1++) {
/*     */         
/* 115 */         PKIXCertPathChecker pKIXCertPathChecker = paramList1.get(b1);
/* 116 */         if (debug != null) {
/* 117 */           debug.println("-Using checker" + (b1 + 1) + " ... [" + pKIXCertPathChecker
/* 118 */               .getClass().getName() + "]");
/*     */         }
/*     */         
/* 121 */         if (b == 0) {
/* 122 */           pKIXCertPathChecker.init(false);
/*     */         }
/*     */         try {
/* 125 */           pKIXCertPathChecker.check(x509Certificate, set);
/*     */           
/* 127 */           if (debug != null) {
/* 128 */             debug.println("-checker" + (b1 + 1) + " validation succeeded");
/*     */           
/*     */           }
/*     */         }
/* 132 */         catch (CertPathValidatorException certPathValidatorException) {
/* 133 */           throw new CertPathValidatorException(certPathValidatorException.getMessage(), 
/* 134 */               (certPathValidatorException.getCause() != null) ? certPathValidatorException.getCause() : certPathValidatorException, paramCertPath, i - b + 1, certPathValidatorException
/* 135 */               .getReason());
/*     */         } 
/*     */       } 
/*     */       
/* 139 */       if (!set.isEmpty()) {
/* 140 */         throw new CertPathValidatorException("unrecognized critical extension(s)", null, paramCertPath, i - b + 1, PKIXReason.UNRECOGNIZED_CRIT_EXT);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 145 */       if (debug != null) {
/* 146 */         debug.println("\ncert" + (b + 1) + " validation succeeded.\n");
/*     */       }
/*     */     } 
/* 149 */     if (debug != null) {
/* 150 */       debug.println("Cert path validation succeeded. (PKIX validation algorithm)");
/*     */       
/* 152 */       debug.println("--------------------------------------------------------------");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/PKIXMasterCertPathValidator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */