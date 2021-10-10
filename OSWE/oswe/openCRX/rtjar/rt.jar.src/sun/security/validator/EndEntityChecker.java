/*     */ package sun.security.validator;
/*     */ 
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class EndEntityChecker
/*     */ {
/*     */   private static final String OID_EXTENDED_KEY_USAGE = "2.5.29.37";
/*     */   private static final String OID_EKU_TLS_SERVER = "1.3.6.1.5.5.7.3.1";
/*     */   private static final String OID_EKU_TLS_CLIENT = "1.3.6.1.5.5.7.3.2";
/*     */   private static final String OID_EKU_CODE_SIGNING = "1.3.6.1.5.5.7.3.3";
/*     */   private static final String OID_EKU_TIME_STAMPING = "1.3.6.1.5.5.7.3.8";
/*     */   private static final String OID_EKU_ANY_USAGE = "2.5.29.37.0";
/*     */   private static final String OID_EKU_NS_SGC = "2.16.840.1.113730.4.1";
/*     */   private static final String OID_EKU_MS_SGC = "1.3.6.1.4.1.311.10.3.3";
/*     */   private static final String OID_SUBJECT_ALT_NAME = "2.5.29.17";
/*     */   private static final String NSCT_SSL_CLIENT = "ssl_client";
/*     */   private static final String NSCT_SSL_SERVER = "ssl_server";
/*     */   private static final String NSCT_CODE_SIGNING = "object_signing";
/*     */   private static final int KU_SIGNATURE = 0;
/*     */   private static final int KU_KEY_ENCIPHERMENT = 2;
/*     */   private static final int KU_KEY_AGREEMENT = 4;
/* 109 */   private static final Collection<String> KU_SERVER_SIGNATURE = Arrays.asList(new String[] { "DHE_DSS", "DHE_RSA", "ECDHE_ECDSA", "ECDHE_RSA", "RSA_EXPORT", "UNKNOWN" });
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   private static final Collection<String> KU_SERVER_ENCRYPTION = Arrays.asList(new String[] { "RSA" });
/*     */ 
/*     */ 
/*     */   
/* 118 */   private static final Collection<String> KU_SERVER_KEY_AGREEMENT = Arrays.asList(new String[] { "DH_DSS", "DH_RSA", "ECDH_ECDSA", "ECDH_RSA" });
/*     */ 
/*     */   
/*     */   private final String variant;
/*     */   
/*     */   private final String type;
/*     */ 
/*     */   
/*     */   private EndEntityChecker(String paramString1, String paramString2) {
/* 127 */     this.type = paramString1;
/* 128 */     this.variant = paramString2;
/*     */   }
/*     */   
/*     */   static EndEntityChecker getInstance(String paramString1, String paramString2) {
/* 132 */     return new EndEntityChecker(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void check(X509Certificate[] paramArrayOfX509Certificate, Object paramObject, boolean paramBoolean) throws CertificateException {
/* 138 */     if (this.variant.equals("generic")) {
/*     */       return;
/*     */     }
/*     */     
/* 142 */     Set<String> set = getCriticalExtensions(paramArrayOfX509Certificate[0]);
/* 143 */     if (this.variant.equals("tls server")) {
/* 144 */       checkTLSServer(paramArrayOfX509Certificate[0], (String)paramObject, set);
/* 145 */     } else if (this.variant.equals("tls client")) {
/* 146 */       checkTLSClient(paramArrayOfX509Certificate[0], set);
/* 147 */     } else if (this.variant.equals("code signing")) {
/* 148 */       checkCodeSigning(paramArrayOfX509Certificate[0], set);
/* 149 */     } else if (this.variant.equals("jce signing")) {
/* 150 */       checkCodeSigning(paramArrayOfX509Certificate[0], set);
/* 151 */     } else if (this.variant.equals("plugin code signing")) {
/* 152 */       checkCodeSigning(paramArrayOfX509Certificate[0], set);
/* 153 */     } else if (this.variant.equals("tsa server")) {
/* 154 */       checkTSAServer(paramArrayOfX509Certificate[0], set);
/*     */     } else {
/* 156 */       throw new CertificateException("Unknown variant: " + this.variant);
/*     */     } 
/*     */ 
/*     */     
/* 160 */     if (paramBoolean) {
/* 161 */       checkRemainingExtensions(set);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 166 */     for (CADistrustPolicy cADistrustPolicy : CADistrustPolicy.POLICIES) {
/* 167 */       cADistrustPolicy.checkDistrust(this.variant, paramArrayOfX509Certificate);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Set<String> getCriticalExtensions(X509Certificate paramX509Certificate) {
/* 176 */     Set<String> set = paramX509Certificate.getCriticalExtensionOIDs();
/* 177 */     if (set == null) {
/* 178 */       set = Collections.emptySet();
/*     */     }
/* 180 */     return set;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkRemainingExtensions(Set<String> paramSet) throws CertificateException {
/* 190 */     paramSet.remove("2.5.29.19");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 196 */     paramSet.remove("2.5.29.17");
/*     */     
/* 198 */     if (!paramSet.isEmpty()) {
/* 199 */       throw new CertificateException("Certificate contains unsupported critical extensions: " + paramSet);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkEKU(X509Certificate paramX509Certificate, Set<String> paramSet, String paramString) throws CertificateException {
/* 210 */     List<String> list = paramX509Certificate.getExtendedKeyUsage();
/* 211 */     if (list == null) {
/* 212 */       return true;
/*     */     }
/* 214 */     return (list.contains(paramString) || list.contains("2.5.29.37.0"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkKeyUsage(X509Certificate paramX509Certificate, int paramInt) throws CertificateException {
/* 224 */     boolean[] arrayOfBoolean = paramX509Certificate.getKeyUsage();
/* 225 */     if (arrayOfBoolean == null) {
/* 226 */       return true;
/*     */     }
/* 228 */     return (arrayOfBoolean.length > paramInt && arrayOfBoolean[paramInt]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkTLSClient(X509Certificate paramX509Certificate, Set<String> paramSet) throws CertificateException {
/* 238 */     if (!checkKeyUsage(paramX509Certificate, 0)) {
/* 239 */       throw new ValidatorException("KeyUsage does not allow digital signatures", ValidatorException.T_EE_EXTENSIONS, paramX509Certificate);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 244 */     if (!checkEKU(paramX509Certificate, paramSet, "1.3.6.1.5.5.7.3.2")) {
/* 245 */       throw new ValidatorException("Extended key usage does not permit use for TLS client authentication", ValidatorException.T_EE_EXTENSIONS, paramX509Certificate);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 250 */     if (!SimpleValidator.getNetscapeCertTypeBit(paramX509Certificate, "ssl_client")) {
/* 251 */       throw new ValidatorException("Netscape cert type does not permit use for SSL client", ValidatorException.T_EE_EXTENSIONS, paramX509Certificate);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 257 */     paramSet.remove("2.5.29.15");
/* 258 */     paramSet.remove("2.5.29.37");
/* 259 */     paramSet.remove("2.16.840.1.113730.1.1");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkTLSServer(X509Certificate paramX509Certificate, String paramString, Set<String> paramSet) throws CertificateException {
/* 270 */     if (KU_SERVER_ENCRYPTION.contains(paramString)) {
/* 271 */       if (!checkKeyUsage(paramX509Certificate, 2)) {
/* 272 */         throw new ValidatorException("KeyUsage does not allow key encipherment", ValidatorException.T_EE_EXTENSIONS, paramX509Certificate);
/*     */       
/*     */       }
/*     */     }
/* 276 */     else if (KU_SERVER_SIGNATURE.contains(paramString)) {
/* 277 */       if (!checkKeyUsage(paramX509Certificate, 0)) {
/* 278 */         throw new ValidatorException("KeyUsage does not allow digital signatures", ValidatorException.T_EE_EXTENSIONS, paramX509Certificate);
/*     */       
/*     */       }
/*     */     }
/* 282 */     else if (KU_SERVER_KEY_AGREEMENT.contains(paramString)) {
/* 283 */       if (!checkKeyUsage(paramX509Certificate, 4)) {
/* 284 */         throw new ValidatorException("KeyUsage does not allow key agreement", ValidatorException.T_EE_EXTENSIONS, paramX509Certificate);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 289 */       throw new CertificateException("Unknown authType: " + paramString);
/*     */     } 
/*     */     
/* 292 */     if (!checkEKU(paramX509Certificate, paramSet, "1.3.6.1.5.5.7.3.1"))
/*     */     {
/*     */       
/* 295 */       if (!checkEKU(paramX509Certificate, paramSet, "1.3.6.1.4.1.311.10.3.3") && 
/* 296 */         !checkEKU(paramX509Certificate, paramSet, "2.16.840.1.113730.4.1")) {
/* 297 */         throw new ValidatorException("Extended key usage does not permit use for TLS server authentication", ValidatorException.T_EE_EXTENSIONS, paramX509Certificate);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 304 */     if (!SimpleValidator.getNetscapeCertTypeBit(paramX509Certificate, "ssl_server")) {
/* 305 */       throw new ValidatorException("Netscape cert type does not permit use for SSL server", ValidatorException.T_EE_EXTENSIONS, paramX509Certificate);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 311 */     paramSet.remove("2.5.29.15");
/* 312 */     paramSet.remove("2.5.29.37");
/* 313 */     paramSet.remove("2.16.840.1.113730.1.1");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkCodeSigning(X509Certificate paramX509Certificate, Set<String> paramSet) throws CertificateException {
/* 322 */     if (!checkKeyUsage(paramX509Certificate, 0)) {
/* 323 */       throw new ValidatorException("KeyUsage does not allow digital signatures", ValidatorException.T_EE_EXTENSIONS, paramX509Certificate);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 328 */     if (!checkEKU(paramX509Certificate, paramSet, "1.3.6.1.5.5.7.3.3")) {
/* 329 */       throw new ValidatorException("Extended key usage does not permit use for code signing", ValidatorException.T_EE_EXTENSIONS, paramX509Certificate);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 336 */     if (!this.variant.equals("jce signing")) {
/* 337 */       if (!SimpleValidator.getNetscapeCertTypeBit(paramX509Certificate, "object_signing")) {
/* 338 */         throw new ValidatorException("Netscape cert type does not permit use for code signing", ValidatorException.T_EE_EXTENSIONS, paramX509Certificate);
/*     */       }
/*     */ 
/*     */       
/* 342 */       paramSet.remove("2.16.840.1.113730.1.1");
/*     */     } 
/*     */ 
/*     */     
/* 346 */     paramSet.remove("2.5.29.15");
/* 347 */     paramSet.remove("2.5.29.37");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkTSAServer(X509Certificate paramX509Certificate, Set<String> paramSet) throws CertificateException {
/* 357 */     if (!checkKeyUsage(paramX509Certificate, 0)) {
/* 358 */       throw new ValidatorException("KeyUsage does not allow digital signatures", ValidatorException.T_EE_EXTENSIONS, paramX509Certificate);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 363 */     if (paramX509Certificate.getExtendedKeyUsage() == null) {
/* 364 */       throw new ValidatorException("Certificate does not contain an extended key usage extension required for a TSA server", ValidatorException.T_EE_EXTENSIONS, paramX509Certificate);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 370 */     if (!checkEKU(paramX509Certificate, paramSet, "1.3.6.1.5.5.7.3.8")) {
/* 371 */       throw new ValidatorException("Extended key usage does not permit use for TSA server", ValidatorException.T_EE_EXTENSIONS, paramX509Certificate);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 377 */     paramSet.remove("2.5.29.15");
/* 378 */     paramSet.remove("2.5.29.37");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/validator/EndEntityChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */