/*     */ package sun.security.validator;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.AlgorithmConstraints;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.PublicKey;
/*     */ import java.security.cert.CertPathValidatorException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.TrustAnchor;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import sun.security.provider.certpath.AlgorithmChecker;
/*     */ import sun.security.provider.certpath.UntrustedChecker;
/*     */ import sun.security.util.DerInputStream;
/*     */ import sun.security.util.DerValue;
/*     */ import sun.security.util.ObjectIdentifier;
/*     */ import sun.security.x509.NetscapeCertTypeExtension;
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
/*     */ public final class SimpleValidator
/*     */   extends Validator
/*     */ {
/*     */   static final String OID_BASIC_CONSTRAINTS = "2.5.29.19";
/*     */   static final String OID_NETSCAPE_CERT_TYPE = "2.16.840.1.113730.1.1";
/*     */   static final String OID_KEY_USAGE = "2.5.29.15";
/*     */   static final String OID_EXTENDED_KEY_USAGE = "2.5.29.37";
/*     */   static final String OID_EKU_ANY_USAGE = "2.5.29.37.0";
/*  73 */   static final ObjectIdentifier OBJID_NETSCAPE_CERT_TYPE = NetscapeCertTypeExtension.NetscapeCertType_Id;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String NSCT_SSL_CA = "ssl_ca";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String NSCT_CODE_SIGNING_CA = "object_signing_ca";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Map<X500Principal, List<X509Certificate>> trustedX500Principals;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Collection<X509Certificate> trustedCerts;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SimpleValidator(String paramString, Collection<X509Certificate> paramCollection) {
/*  98 */     super("Simple", paramString);
/*  99 */     this.trustedCerts = paramCollection;
/* 100 */     this.trustedX500Principals = new HashMap<>();
/*     */     
/* 102 */     for (X509Certificate x509Certificate : paramCollection) {
/* 103 */       X500Principal x500Principal = x509Certificate.getSubjectX500Principal();
/* 104 */       List<X509Certificate> list = this.trustedX500Principals.get(x500Principal);
/* 105 */       if (list == null) {
/*     */ 
/*     */         
/* 108 */         list = new ArrayList(2);
/* 109 */         this.trustedX500Principals.put(x500Principal, list);
/*     */       } 
/* 111 */       list.add(x509Certificate);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Collection<X509Certificate> getTrustedCertificates() {
/* 116 */     return this.trustedCerts;
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
/*     */   X509Certificate[] engineValidate(X509Certificate[] paramArrayOfX509Certificate, Collection<X509Certificate> paramCollection, AlgorithmConstraints paramAlgorithmConstraints, Object paramObject) throws CertificateException {
/* 128 */     if (paramArrayOfX509Certificate == null || paramArrayOfX509Certificate.length == 0) {
/* 129 */       throw new CertificateException("null or zero-length certificate chain");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 134 */     paramArrayOfX509Certificate = buildTrustedChain(paramArrayOfX509Certificate);
/*     */ 
/*     */     
/* 137 */     Date date = this.validationDate;
/* 138 */     if (date == null) {
/* 139 */       date = new Date();
/*     */     }
/*     */ 
/*     */     
/* 143 */     UntrustedChecker untrustedChecker = new UntrustedChecker();
/*     */ 
/*     */     
/* 146 */     X509Certificate x509Certificate = paramArrayOfX509Certificate[paramArrayOfX509Certificate.length - 1];
/*     */     try {
/* 148 */       untrustedChecker.check(x509Certificate);
/* 149 */     } catch (CertPathValidatorException certPathValidatorException) {
/* 150 */       throw new ValidatorException("Untrusted certificate: " + x509Certificate
/* 151 */           .getSubjectX500Principal(), ValidatorException.T_UNTRUSTED_CERT, x509Certificate, certPathValidatorException);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 156 */     TrustAnchor trustAnchor = new TrustAnchor(x509Certificate, null);
/* 157 */     AlgorithmChecker algorithmChecker1 = new AlgorithmChecker(trustAnchor, this.variant);
/*     */ 
/*     */ 
/*     */     
/* 161 */     AlgorithmChecker algorithmChecker2 = null;
/* 162 */     if (paramAlgorithmConstraints != null) {
/* 163 */       algorithmChecker2 = new AlgorithmChecker(trustAnchor, paramAlgorithmConstraints, null, null, this.variant);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 169 */     int i = paramArrayOfX509Certificate.length - 1;
/* 170 */     for (int j = paramArrayOfX509Certificate.length - 2; j >= 0; j--) {
/* 171 */       X509Certificate x509Certificate1 = paramArrayOfX509Certificate[j + 1];
/* 172 */       X509Certificate x509Certificate2 = paramArrayOfX509Certificate[j];
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 178 */         untrustedChecker.check(x509Certificate2, Collections.emptySet());
/* 179 */       } catch (CertPathValidatorException certPathValidatorException) {
/* 180 */         throw new ValidatorException("Untrusted certificate: " + x509Certificate2
/* 181 */             .getSubjectX500Principal(), ValidatorException.T_UNTRUSTED_CERT, x509Certificate2, certPathValidatorException);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 189 */         algorithmChecker1.check(x509Certificate2, Collections.emptySet());
/* 190 */         if (algorithmChecker2 != null) {
/* 191 */           algorithmChecker2.check(x509Certificate2, Collections.emptySet());
/*     */         }
/* 193 */       } catch (CertPathValidatorException certPathValidatorException) {
/* 194 */         throw new ValidatorException(ValidatorException.T_ALGORITHM_DISABLED, x509Certificate2, certPathValidatorException);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 199 */       if (!this.variant.equals("code signing") && 
/* 200 */         !this.variant.equals("jce signing")) {
/* 201 */         x509Certificate2.checkValidity(date);
/*     */       }
/*     */ 
/*     */       
/* 205 */       if (!x509Certificate2.getIssuerX500Principal().equals(x509Certificate1
/* 206 */           .getSubjectX500Principal())) {
/* 207 */         throw new ValidatorException(ValidatorException.T_NAME_CHAINING, x509Certificate2);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 213 */         x509Certificate2.verify(x509Certificate1.getPublicKey());
/* 214 */       } catch (GeneralSecurityException generalSecurityException) {
/* 215 */         throw new ValidatorException(ValidatorException.T_SIGNATURE_ERROR, x509Certificate2, generalSecurityException);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 220 */       if (j != 0) {
/* 221 */         i = checkExtensions(x509Certificate2, i);
/*     */       }
/*     */     } 
/*     */     
/* 225 */     return paramArrayOfX509Certificate;
/*     */   }
/*     */ 
/*     */   
/*     */   private int checkExtensions(X509Certificate paramX509Certificate, int paramInt) throws CertificateException {
/* 230 */     Set<String> set = paramX509Certificate.getCriticalExtensionOIDs();
/* 231 */     if (set == null) {
/* 232 */       set = Collections.emptySet();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 237 */     int i = checkBasicConstraints(paramX509Certificate, set, paramInt);
/*     */ 
/*     */     
/* 240 */     checkKeyUsage(paramX509Certificate, set);
/*     */ 
/*     */     
/* 243 */     checkNetscapeCertType(paramX509Certificate, set);
/*     */     
/* 245 */     if (!set.isEmpty()) {
/* 246 */       throw new ValidatorException("Certificate contains unknown critical extensions: " + set, ValidatorException.T_CA_EXTENSIONS, paramX509Certificate);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 251 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkNetscapeCertType(X509Certificate paramX509Certificate, Set<String> paramSet) throws CertificateException {
/* 256 */     if (!this.variant.equals("generic"))
/*     */     {
/* 258 */       if (this.variant.equals("tls client") || this.variant
/* 259 */         .equals("tls server")) {
/* 260 */         if (!getNetscapeCertTypeBit(paramX509Certificate, "ssl_ca")) {
/* 261 */           throw new ValidatorException("Invalid Netscape CertType extension for SSL CA certificate", ValidatorException.T_CA_EXTENSIONS, paramX509Certificate);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 266 */         paramSet.remove("2.16.840.1.113730.1.1");
/* 267 */       } else if (this.variant.equals("code signing") || this.variant
/* 268 */         .equals("jce signing")) {
/* 269 */         if (!getNetscapeCertTypeBit(paramX509Certificate, "object_signing_ca")) {
/* 270 */           throw new ValidatorException("Invalid Netscape CertType extension for code signing CA certificate", ValidatorException.T_CA_EXTENSIONS, paramX509Certificate);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 275 */         paramSet.remove("2.16.840.1.113730.1.1");
/*     */       } else {
/* 277 */         throw new CertificateException("Unknown variant " + this.variant);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean getNetscapeCertTypeBit(X509Certificate paramX509Certificate, String paramString) {
/*     */     try {
/*     */       NetscapeCertTypeExtension netscapeCertTypeExtension;
/* 288 */       if (paramX509Certificate instanceof X509CertImpl) {
/* 289 */         X509CertImpl x509CertImpl = (X509CertImpl)paramX509Certificate;
/* 290 */         ObjectIdentifier objectIdentifier = OBJID_NETSCAPE_CERT_TYPE;
/* 291 */         netscapeCertTypeExtension = (NetscapeCertTypeExtension)x509CertImpl.getExtension(objectIdentifier);
/* 292 */         if (netscapeCertTypeExtension == null) {
/* 293 */           return true;
/*     */         }
/*     */       } else {
/* 296 */         byte[] arrayOfByte1 = paramX509Certificate.getExtensionValue("2.16.840.1.113730.1.1");
/* 297 */         if (arrayOfByte1 == null) {
/* 298 */           return true;
/*     */         }
/* 300 */         DerInputStream derInputStream = new DerInputStream(arrayOfByte1);
/* 301 */         byte[] arrayOfByte2 = derInputStream.getOctetString();
/*     */         
/* 303 */         arrayOfByte2 = (new DerValue(arrayOfByte2)).getUnalignedBitString().toByteArray();
/* 304 */         netscapeCertTypeExtension = new NetscapeCertTypeExtension(arrayOfByte2);
/*     */       } 
/* 306 */       Boolean bool = netscapeCertTypeExtension.get(paramString);
/* 307 */       return bool.booleanValue();
/* 308 */     } catch (IOException iOException) {
/* 309 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int checkBasicConstraints(X509Certificate paramX509Certificate, Set<String> paramSet, int paramInt) throws CertificateException {
/* 316 */     paramSet.remove("2.5.29.19");
/* 317 */     int i = paramX509Certificate.getBasicConstraints();
/*     */     
/* 319 */     if (i < 0) {
/* 320 */       throw new ValidatorException("End user tried to act as a CA", ValidatorException.T_CA_EXTENSIONS, paramX509Certificate);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 326 */     if (!X509CertImpl.isSelfIssued(paramX509Certificate)) {
/* 327 */       if (paramInt <= 0) {
/* 328 */         throw new ValidatorException("Violated path length constraints", ValidatorException.T_CA_EXTENSIONS, paramX509Certificate);
/*     */       }
/*     */ 
/*     */       
/* 332 */       paramInt--;
/*     */     } 
/*     */     
/* 335 */     if (paramInt > i) {
/* 336 */       paramInt = i;
/*     */     }
/*     */     
/* 339 */     return paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkKeyUsage(X509Certificate paramX509Certificate, Set<String> paramSet) throws CertificateException {
/* 349 */     paramSet.remove("2.5.29.15");
/*     */     
/* 351 */     paramSet.remove("2.5.29.37");
/*     */ 
/*     */     
/* 354 */     boolean[] arrayOfBoolean = paramX509Certificate.getKeyUsage();
/* 355 */     if (arrayOfBoolean != null)
/*     */     {
/* 357 */       if (arrayOfBoolean.length < 6 || !arrayOfBoolean[5]) {
/* 358 */         throw new ValidatorException("Wrong key usage: expected keyCertSign", ValidatorException.T_CA_EXTENSIONS, paramX509Certificate);
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
/*     */   private X509Certificate[] buildTrustedChain(X509Certificate[] paramArrayOfX509Certificate) throws CertificateException {
/* 372 */     ArrayList<X509Certificate> arrayList = new ArrayList(paramArrayOfX509Certificate.length);
/*     */ 
/*     */     
/* 375 */     for (byte b = 0; b < paramArrayOfX509Certificate.length; b++) {
/* 376 */       X509Certificate x509Certificate1 = paramArrayOfX509Certificate[b];
/* 377 */       X509Certificate x509Certificate2 = getTrustedCertificate(x509Certificate1);
/* 378 */       if (x509Certificate2 != null) {
/* 379 */         arrayList.add(x509Certificate2);
/* 380 */         return arrayList.<X509Certificate>toArray(CHAIN0);
/*     */       } 
/* 382 */       arrayList.add(x509Certificate1);
/*     */     } 
/*     */ 
/*     */     
/* 386 */     X509Certificate x509Certificate = paramArrayOfX509Certificate[paramArrayOfX509Certificate.length - 1];
/* 387 */     X500Principal x500Principal1 = x509Certificate.getSubjectX500Principal();
/* 388 */     X500Principal x500Principal2 = x509Certificate.getIssuerX500Principal();
/* 389 */     List<X509Certificate> list = this.trustedX500Principals.get(x500Principal2);
/* 390 */     if (list != null) {
/* 391 */       X509Certificate x509Certificate1 = list.iterator().next();
/* 392 */       arrayList.add(x509Certificate1);
/* 393 */       return arrayList.<X509Certificate>toArray(CHAIN0);
/*     */     } 
/*     */ 
/*     */     
/* 397 */     throw new ValidatorException(ValidatorException.T_NO_TRUST_ANCHOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private X509Certificate getTrustedCertificate(X509Certificate paramX509Certificate) {
/* 407 */     X500Principal x500Principal1 = paramX509Certificate.getSubjectX500Principal();
/* 408 */     List list = this.trustedX500Principals.get(x500Principal1);
/* 409 */     if (list == null) {
/* 410 */       return null;
/*     */     }
/*     */     
/* 413 */     X500Principal x500Principal2 = paramX509Certificate.getIssuerX500Principal();
/* 414 */     PublicKey publicKey = paramX509Certificate.getPublicKey();
/*     */     
/* 416 */     for (X509Certificate x509Certificate : list) {
/* 417 */       if (x509Certificate.equals(paramX509Certificate)) {
/* 418 */         return paramX509Certificate;
/*     */       }
/* 420 */       if (!x509Certificate.getIssuerX500Principal().equals(x500Principal2)) {
/*     */         continue;
/*     */       }
/* 423 */       if (!x509Certificate.getPublicKey().equals(publicKey)) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 428 */       return x509Certificate;
/*     */     } 
/* 430 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/validator/SimpleValidator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */