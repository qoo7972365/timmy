/*     */ package sun.security.pkcs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.math.BigInteger;
/*     */ import java.security.AlgorithmParameters;
/*     */ import java.security.CryptoPrimitive;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.Principal;
/*     */ import java.security.PublicKey;
/*     */ import java.security.Signature;
/*     */ import java.security.SignatureException;
/*     */ import java.security.Timestamp;
/*     */ import java.security.cert.CertPath;
/*     */ import java.security.cert.CertPathValidatorException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import sun.misc.HexDumpEncoder;
/*     */ import sun.security.timestamp.TimestampToken;
/*     */ import sun.security.util.ConstraintsParameters;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.util.DerEncoder;
/*     */ import sun.security.util.DerInputStream;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
/*     */ import sun.security.util.DisabledAlgorithmConstraints;
/*     */ import sun.security.util.KeyUtil;
/*     */ import sun.security.util.ObjectIdentifier;
/*     */ import sun.security.x509.AlgorithmId;
/*     */ import sun.security.x509.KeyUsageExtension;
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
/*     */ public class SignerInfo
/*     */   implements DerEncoder
/*     */ {
/*  75 */   private static final Set<CryptoPrimitive> DIGEST_PRIMITIVE_SET = Collections.unmodifiableSet(EnumSet.of(CryptoPrimitive.MESSAGE_DIGEST));
/*     */ 
/*     */   
/*  78 */   private static final Set<CryptoPrimitive> SIG_PRIMITIVE_SET = Collections.unmodifiableSet(EnumSet.of(CryptoPrimitive.SIGNATURE));
/*     */   
/*  80 */   private static final DisabledAlgorithmConstraints JAR_DISABLED_CHECK = new DisabledAlgorithmConstraints("jdk.jar.disabledAlgorithms");
/*     */   
/*     */   BigInteger version;
/*     */   
/*     */   X500Name issuerName;
/*     */   
/*     */   BigInteger certificateSerialNumber;
/*     */   AlgorithmId digestAlgorithmId;
/*     */   AlgorithmId digestEncryptionAlgorithmId;
/*     */   byte[] encryptedDigest;
/*     */   Timestamp timestamp;
/*     */   private boolean hasTimestamp = true;
/*  92 */   private static final Debug debug = Debug.getInstance("jar");
/*     */ 
/*     */   
/*     */   PKCS9Attributes authenticatedAttributes;
/*     */ 
/*     */   
/*     */   PKCS9Attributes unauthenticatedAttributes;
/*     */ 
/*     */   
/*     */   public SignerInfo(X500Name paramX500Name, BigInteger paramBigInteger, AlgorithmId paramAlgorithmId1, AlgorithmId paramAlgorithmId2, byte[] paramArrayOfbyte) {
/* 102 */     this.version = BigInteger.ONE;
/* 103 */     this.issuerName = paramX500Name;
/* 104 */     this.certificateSerialNumber = paramBigInteger;
/* 105 */     this.digestAlgorithmId = paramAlgorithmId1;
/* 106 */     this.digestEncryptionAlgorithmId = paramAlgorithmId2;
/* 107 */     this.encryptedDigest = paramArrayOfbyte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignerInfo(X500Name paramX500Name, BigInteger paramBigInteger, AlgorithmId paramAlgorithmId1, PKCS9Attributes paramPKCS9Attributes1, AlgorithmId paramAlgorithmId2, byte[] paramArrayOfbyte, PKCS9Attributes paramPKCS9Attributes2) {
/* 117 */     this.version = BigInteger.ONE;
/* 118 */     this.issuerName = paramX500Name;
/* 119 */     this.certificateSerialNumber = paramBigInteger;
/* 120 */     this.digestAlgorithmId = paramAlgorithmId1;
/* 121 */     this.authenticatedAttributes = paramPKCS9Attributes1;
/* 122 */     this.digestEncryptionAlgorithmId = paramAlgorithmId2;
/* 123 */     this.encryptedDigest = paramArrayOfbyte;
/* 124 */     this.unauthenticatedAttributes = paramPKCS9Attributes2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignerInfo(DerInputStream paramDerInputStream) throws IOException, ParsingException {
/* 133 */     this(paramDerInputStream, false);
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
/*     */   public SignerInfo(DerInputStream paramDerInputStream, boolean paramBoolean) throws IOException, ParsingException {
/* 150 */     this.version = paramDerInputStream.getBigInteger();
/*     */ 
/*     */     
/* 153 */     DerValue[] arrayOfDerValue = paramDerInputStream.getSequence(2);
/* 154 */     byte[] arrayOfByte = arrayOfDerValue[0].toByteArray();
/* 155 */     this.issuerName = new X500Name(new DerValue((byte)48, arrayOfByte));
/*     */     
/* 157 */     this.certificateSerialNumber = arrayOfDerValue[1].getBigInteger();
/*     */ 
/*     */     
/* 160 */     DerValue derValue = paramDerInputStream.getDerValue();
/*     */     
/* 162 */     this.digestAlgorithmId = AlgorithmId.parse(derValue);
/*     */ 
/*     */     
/* 165 */     if (paramBoolean) {
/*     */ 
/*     */       
/* 168 */       paramDerInputStream.getSet(0);
/*     */ 
/*     */     
/*     */     }
/* 172 */     else if ((byte)paramDerInputStream.peekByte() == -96) {
/* 173 */       this.authenticatedAttributes = new PKCS9Attributes(paramDerInputStream);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 179 */     derValue = paramDerInputStream.getDerValue();
/*     */     
/* 181 */     this.digestEncryptionAlgorithmId = AlgorithmId.parse(derValue);
/*     */ 
/*     */     
/* 184 */     this.encryptedDigest = paramDerInputStream.getOctetString();
/*     */ 
/*     */     
/* 187 */     if (paramBoolean) {
/*     */ 
/*     */       
/* 190 */       paramDerInputStream.getSet(0);
/*     */ 
/*     */     
/*     */     }
/* 194 */     else if (paramDerInputStream.available() != 0 && 
/* 195 */       (byte)paramDerInputStream.peekByte() == -95) {
/* 196 */       this.unauthenticatedAttributes = new PKCS9Attributes(paramDerInputStream, true);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 202 */     if (paramDerInputStream.available() != 0) {
/* 203 */       throw new ParsingException("extra data at the end");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void encode(DerOutputStream paramDerOutputStream) throws IOException {
/* 209 */     derEncode(paramDerOutputStream);
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
/*     */   public void derEncode(OutputStream paramOutputStream) throws IOException {
/* 222 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 223 */     derOutputStream1.putInteger(this.version);
/* 224 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 225 */     this.issuerName.encode(derOutputStream2);
/* 226 */     derOutputStream2.putInteger(this.certificateSerialNumber);
/* 227 */     derOutputStream1.write((byte)48, derOutputStream2);
/*     */     
/* 229 */     this.digestAlgorithmId.encode(derOutputStream1);
/*     */ 
/*     */     
/* 232 */     if (this.authenticatedAttributes != null) {
/* 233 */       this.authenticatedAttributes.encode((byte)-96, derOutputStream1);
/*     */     }
/* 235 */     this.digestEncryptionAlgorithmId.encode(derOutputStream1);
/*     */     
/* 237 */     derOutputStream1.putOctetString(this.encryptedDigest);
/*     */ 
/*     */     
/* 240 */     if (this.unauthenticatedAttributes != null) {
/* 241 */       this.unauthenticatedAttributes.encode((byte)-95, derOutputStream1);
/*     */     }
/* 243 */     DerOutputStream derOutputStream3 = new DerOutputStream();
/* 244 */     derOutputStream3.write((byte)48, derOutputStream1);
/*     */     
/* 246 */     paramOutputStream.write(derOutputStream3.toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509Certificate getCertificate(PKCS7 paramPKCS7) throws IOException {
/* 257 */     return paramPKCS7.getCertificate(this.certificateSerialNumber, this.issuerName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<X509Certificate> getCertificateChain(PKCS7 paramPKCS7) throws IOException {
/*     */     boolean bool;
/* 267 */     X509Certificate x509Certificate = paramPKCS7.getCertificate(this.certificateSerialNumber, this.issuerName);
/* 268 */     if (x509Certificate == null) {
/* 269 */       return null;
/*     */     }
/* 271 */     ArrayList<X509Certificate> arrayList = new ArrayList();
/* 272 */     arrayList.add(x509Certificate);
/*     */     
/* 274 */     X509Certificate[] arrayOfX509Certificate = paramPKCS7.getCertificates();
/* 275 */     if (arrayOfX509Certificate == null || x509Certificate
/* 276 */       .getSubjectDN().equals(x509Certificate.getIssuerDN())) {
/* 277 */       return arrayList;
/*     */     }
/*     */     
/* 280 */     Principal principal = x509Certificate.getIssuerDN();
/* 281 */     int i = 0;
/*     */     do {
/* 283 */       bool = false;
/* 284 */       int j = i;
/* 285 */       while (j < arrayOfX509Certificate.length) {
/* 286 */         if (principal.equals(arrayOfX509Certificate[j].getSubjectDN())) {
/*     */           
/* 288 */           arrayList.add(arrayOfX509Certificate[j]);
/*     */ 
/*     */           
/* 291 */           if (arrayOfX509Certificate[j].getSubjectDN().equals(arrayOfX509Certificate[j]
/* 292 */               .getIssuerDN())) {
/* 293 */             i = arrayOfX509Certificate.length;
/*     */           } else {
/* 295 */             principal = arrayOfX509Certificate[j].getIssuerDN();
/* 296 */             X509Certificate x509Certificate1 = arrayOfX509Certificate[i];
/* 297 */             arrayOfX509Certificate[i] = arrayOfX509Certificate[j];
/* 298 */             arrayOfX509Certificate[j] = x509Certificate1;
/* 299 */             i++;
/*     */           } 
/* 301 */           bool = true;
/*     */           break;
/*     */         } 
/* 304 */         j++;
/*     */       }
/*     */     
/* 307 */     } while (bool);
/*     */ 
/*     */ 
/*     */     
/* 311 */     return arrayList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SignerInfo verify(PKCS7 paramPKCS7, byte[] paramArrayOfbyte) throws NoSuchAlgorithmException, SignatureException {
/*     */     try {
/*     */       byte[] arrayOfByte;
/* 321 */       ContentInfo contentInfo = paramPKCS7.getContentInfo();
/* 322 */       if (paramArrayOfbyte == null) {
/* 323 */         paramArrayOfbyte = contentInfo.getContentBytes();
/*     */       }
/*     */       
/* 326 */       Timestamp timestamp = null;
/*     */       try {
/* 328 */         timestamp = getTimestamp();
/* 329 */       } catch (Exception exception) {}
/*     */ 
/*     */       
/* 332 */       ConstraintsParameters constraintsParameters = new ConstraintsParameters(timestamp);
/*     */       
/* 334 */       String str1 = getDigestAlgorithmId().getName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 340 */       if (this.authenticatedAttributes == null) {
/* 341 */         arrayOfByte = paramArrayOfbyte;
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 346 */         ObjectIdentifier objectIdentifier = (ObjectIdentifier)this.authenticatedAttributes.getAttributeValue(PKCS9Attribute.CONTENT_TYPE_OID);
/*     */         
/* 348 */         if (objectIdentifier == null || 
/* 349 */           !objectIdentifier.equals(contentInfo.contentType)) {
/* 350 */           return null;
/*     */         }
/*     */ 
/*     */         
/* 354 */         byte[] arrayOfByte1 = (byte[])this.authenticatedAttributes.getAttributeValue(PKCS9Attribute.MESSAGE_DIGEST_OID);
/*     */ 
/*     */         
/* 357 */         if (arrayOfByte1 == null) {
/* 358 */           return null;
/*     */         }
/*     */         
/*     */         try {
/* 362 */           JAR_DISABLED_CHECK.permits(str1, constraintsParameters);
/* 363 */         } catch (CertPathValidatorException certPathValidatorException) {
/* 364 */           throw new SignatureException(certPathValidatorException.getMessage(), certPathValidatorException);
/*     */         } 
/*     */         
/* 367 */         MessageDigest messageDigest = MessageDigest.getInstance(str1);
/* 368 */         byte[] arrayOfByte2 = messageDigest.digest(paramArrayOfbyte);
/*     */         
/* 370 */         if (arrayOfByte1.length != arrayOfByte2.length)
/* 371 */           return null; 
/* 372 */         for (byte b = 0; b < arrayOfByte1.length; b++) {
/* 373 */           if (arrayOfByte1[b] != arrayOfByte2[b]) {
/* 374 */             return null;
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 383 */         arrayOfByte = this.authenticatedAttributes.getDerEncoding();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 389 */       String str2 = getDigestEncryptionAlgorithmId().getName();
/*     */ 
/*     */ 
/*     */       
/* 393 */       String str3 = AlgorithmId.getEncAlgFromSigAlg(str2);
/* 394 */       if (str3 != null) str2 = str3; 
/* 395 */       String str4 = AlgorithmId.makeSigAlg(str1, str2);
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 400 */         JAR_DISABLED_CHECK.permits(str4, constraintsParameters);
/* 401 */       } catch (CertPathValidatorException certPathValidatorException) {
/* 402 */         throw new SignatureException(certPathValidatorException.getMessage(), certPathValidatorException);
/*     */       } 
/*     */       
/* 405 */       X509Certificate x509Certificate = getCertificate(paramPKCS7);
/* 406 */       if (x509Certificate == null) {
/* 407 */         return null;
/*     */       }
/* 409 */       PublicKey publicKey = x509Certificate.getPublicKey();
/*     */ 
/*     */       
/* 412 */       if (!JAR_DISABLED_CHECK.permits(SIG_PRIMITIVE_SET, publicKey)) {
/* 413 */         throw new SignatureException("Public key check failed. Disabled key used: " + 
/*     */             
/* 415 */             KeyUtil.getKeySize(publicKey) + " bit " + publicKey
/* 416 */             .getAlgorithm());
/*     */       }
/*     */       
/* 419 */       if (x509Certificate.hasUnsupportedCriticalExtension()) {
/* 420 */         throw new SignatureException("Certificate has unsupported critical extension(s)");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 428 */       boolean[] arrayOfBoolean = x509Certificate.getKeyUsage();
/* 429 */       if (arrayOfBoolean != null) {
/*     */         KeyUsageExtension keyUsageExtension;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 436 */           keyUsageExtension = new KeyUsageExtension(arrayOfBoolean);
/* 437 */         } catch (IOException iOException) {
/* 438 */           throw new SignatureException("Failed to parse keyUsage extension");
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 443 */         boolean bool1 = keyUsageExtension.get("digital_signature").booleanValue();
/*     */ 
/*     */         
/* 446 */         boolean bool2 = keyUsageExtension.get("non_repudiation").booleanValue();
/*     */         
/* 448 */         if (!bool1 && !bool2) {
/* 449 */           throw new SignatureException("Key usage restricted: cannot be used for digital signatures");
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 455 */       Signature signature = Signature.getInstance(str4);
/* 456 */       signature.initVerify(publicKey);
/* 457 */       signature.update(arrayOfByte);
/* 458 */       if (signature.verify(this.encryptedDigest)) {
/* 459 */         return this;
/*     */       }
/*     */     }
/* 462 */     catch (IOException iOException) {
/* 463 */       throw new SignatureException("IO error verifying signature:\n" + iOException
/* 464 */           .getMessage());
/*     */     }
/* 466 */     catch (InvalidKeyException invalidKeyException) {
/* 467 */       throw new SignatureException("InvalidKey: " + invalidKeyException.getMessage());
/*     */     } 
/*     */     
/* 470 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   SignerInfo verify(PKCS7 paramPKCS7) throws NoSuchAlgorithmException, SignatureException {
/* 476 */     return verify(paramPKCS7, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public BigInteger getVersion() {
/* 481 */     return this.version;
/*     */   }
/*     */   
/*     */   public X500Name getIssuerName() {
/* 485 */     return this.issuerName;
/*     */   }
/*     */   
/*     */   public BigInteger getCertificateSerialNumber() {
/* 489 */     return this.certificateSerialNumber;
/*     */   }
/*     */   
/*     */   public AlgorithmId getDigestAlgorithmId() {
/* 493 */     return this.digestAlgorithmId;
/*     */   }
/*     */   
/*     */   public PKCS9Attributes getAuthenticatedAttributes() {
/* 497 */     return this.authenticatedAttributes;
/*     */   }
/*     */   
/*     */   public AlgorithmId getDigestEncryptionAlgorithmId() {
/* 501 */     return this.digestEncryptionAlgorithmId;
/*     */   }
/*     */   
/*     */   public byte[] getEncryptedDigest() {
/* 505 */     return this.encryptedDigest;
/*     */   }
/*     */   
/*     */   public PKCS9Attributes getUnauthenticatedAttributes() {
/* 509 */     return this.unauthenticatedAttributes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PKCS7 getTsToken() throws IOException {
/* 517 */     if (this.unauthenticatedAttributes == null) {
/* 518 */       return null;
/*     */     }
/*     */     
/* 521 */     PKCS9Attribute pKCS9Attribute = this.unauthenticatedAttributes.getAttribute(PKCS9Attribute.SIGNATURE_TIMESTAMP_TOKEN_OID);
/*     */     
/* 523 */     if (pKCS9Attribute == null) {
/* 524 */       return null;
/*     */     }
/* 526 */     return new PKCS7((byte[])pKCS9Attribute.getValue());
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
/*     */ 
/*     */ 
/*     */   
/*     */   public Timestamp getTimestamp() throws IOException, NoSuchAlgorithmException, SignatureException, CertificateException {
/* 553 */     if (this.timestamp != null || !this.hasTimestamp) {
/* 554 */       return this.timestamp;
/*     */     }
/* 556 */     PKCS7 pKCS7 = getTsToken();
/* 557 */     if (pKCS7 == null) {
/* 558 */       this.hasTimestamp = false;
/* 559 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 563 */     byte[] arrayOfByte = pKCS7.getContentInfo().getData();
/*     */ 
/*     */     
/* 566 */     SignerInfo[] arrayOfSignerInfo = pKCS7.verify(arrayOfByte);
/*     */     
/* 568 */     ArrayList<X509Certificate> arrayList = arrayOfSignerInfo[0].getCertificateChain(pKCS7);
/* 569 */     CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
/* 570 */     CertPath certPath = certificateFactory.generateCertPath((List)arrayList);
/*     */     
/* 572 */     TimestampToken timestampToken = new TimestampToken(arrayOfByte);
/*     */     
/* 574 */     verifyTimestamp(timestampToken);
/*     */     
/* 576 */     this.timestamp = new Timestamp(timestampToken.getDate(), certPath);
/* 577 */     return this.timestamp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void verifyTimestamp(TimestampToken paramTimestampToken) throws NoSuchAlgorithmException, SignatureException {
/* 587 */     String str = paramTimestampToken.getHashAlgorithm().getName();
/*     */     
/* 589 */     if (!JAR_DISABLED_CHECK.permits(DIGEST_PRIMITIVE_SET, str, (AlgorithmParameters)null))
/*     */     {
/* 591 */       throw new SignatureException("Timestamp token digest check failed. Disabled algorithm used: " + str);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 596 */     MessageDigest messageDigest = MessageDigest.getInstance(str);
/*     */     
/* 598 */     if (!Arrays.equals(paramTimestampToken.getHashedMessage(), messageDigest
/* 599 */         .digest(this.encryptedDigest)))
/*     */     {
/* 601 */       throw new SignatureException("Signature timestamp (#" + paramTimestampToken
/* 602 */           .getSerialNumber() + ") generated on " + paramTimestampToken.getDate() + " is inapplicable");
/*     */     }
/*     */ 
/*     */     
/* 606 */     if (debug != null) {
/* 607 */       debug.println();
/* 608 */       debug.println("Detected signature timestamp (#" + paramTimestampToken
/* 609 */           .getSerialNumber() + ") generated on " + paramTimestampToken.getDate());
/* 610 */       debug.println();
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 615 */     HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
/*     */     
/* 617 */     String str = "";
/*     */     
/* 619 */     str = str + "Signer Info for (issuer): " + this.issuerName + "\n";
/* 620 */     str = str + "\tversion: " + Debug.toHexString(this.version) + "\n";
/*     */     
/* 622 */     str = str + "\tcertificateSerialNumber: " + Debug.toHexString(this.certificateSerialNumber) + "\n";
/* 623 */     str = str + "\tdigestAlgorithmId: " + this.digestAlgorithmId + "\n";
/* 624 */     if (this.authenticatedAttributes != null) {
/* 625 */       str = str + "\tauthenticatedAttributes: " + this.authenticatedAttributes + "\n";
/*     */     }
/*     */     
/* 628 */     str = str + "\tdigestEncryptionAlgorithmId: " + this.digestEncryptionAlgorithmId + "\n";
/*     */ 
/*     */ 
/*     */     
/* 632 */     str = str + "\tencryptedDigest: \n" + hexDumpEncoder.encodeBuffer(this.encryptedDigest) + "\n";
/* 633 */     if (this.unauthenticatedAttributes != null) {
/* 634 */       str = str + "\tunauthenticatedAttributes: " + this.unauthenticatedAttributes + "\n";
/*     */     }
/*     */     
/* 637 */     return str;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/pkcs/SignerInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */