/*     */ package sun.security.pkcs;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.math.BigInteger;
/*     */ import java.net.URI;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.Principal;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.SignatureException;
/*     */ import java.security.cert.CRLException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.X509CRL;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import sun.security.timestamp.HttpTimestamper;
/*     */ import sun.security.timestamp.TSRequest;
/*     */ import sun.security.timestamp.TSResponse;
/*     */ import sun.security.timestamp.TimestampToken;
/*     */ import sun.security.timestamp.Timestamper;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.util.DerEncoder;
/*     */ import sun.security.util.DerInputStream;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
/*     */ import sun.security.util.ObjectIdentifier;
/*     */ import sun.security.x509.AlgorithmId;
/*     */ import sun.security.x509.X500Name;
/*     */ import sun.security.x509.X509CRLImpl;
/*     */ import sun.security.x509.X509CertImpl;
/*     */ import sun.security.x509.X509CertInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PKCS7
/*     */ {
/*     */   private ObjectIdentifier contentType;
/*  61 */   private BigInteger version = null;
/*  62 */   private AlgorithmId[] digestAlgorithmIds = null;
/*  63 */   private ContentInfo contentInfo = null;
/*  64 */   private X509Certificate[] certificates = null;
/*  65 */   private X509CRL[] crls = null;
/*  66 */   private SignerInfo[] signerInfos = null;
/*     */   
/*     */   private boolean oldStyle = false;
/*     */   
/*     */   private Principal[] certIssuerNames;
/*     */   private static final String KP_TIMESTAMPING_OID = "1.3.6.1.5.5.7.3.8";
/*     */   private static final String EXTENDED_KEY_USAGE_OID = "2.5.29.37";
/*     */   
/*     */   private static class SecureRandomHolder
/*     */   {
/*     */     static final SecureRandom RANDOM;
/*     */     
/*     */     static {
/*  79 */       SecureRandom secureRandom = null;
/*     */       try {
/*  81 */         secureRandom = SecureRandom.getInstance("SHA1PRNG");
/*  82 */       } catch (NoSuchAlgorithmException noSuchAlgorithmException) {}
/*     */ 
/*     */       
/*  85 */       RANDOM = secureRandom;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PKCS7(InputStream paramInputStream) throws ParsingException, IOException {
/* 108 */     DataInputStream dataInputStream = new DataInputStream(paramInputStream);
/* 109 */     byte[] arrayOfByte = new byte[dataInputStream.available()];
/* 110 */     dataInputStream.readFully(arrayOfByte);
/*     */     
/* 112 */     parse(new DerInputStream(arrayOfByte));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PKCS7(DerInputStream paramDerInputStream) throws ParsingException {
/* 123 */     parse(paramDerInputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PKCS7(byte[] paramArrayOfbyte) throws ParsingException {
/*     */     try {
/* 135 */       DerInputStream derInputStream = new DerInputStream(paramArrayOfbyte);
/* 136 */       parse(derInputStream);
/* 137 */     } catch (IOException iOException) {
/* 138 */       ParsingException parsingException = new ParsingException("Unable to parse the encoded bytes");
/*     */       
/* 140 */       parsingException.initCause(iOException);
/* 141 */       throw parsingException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parse(DerInputStream paramDerInputStream) throws ParsingException {
/*     */     try {
/* 152 */       paramDerInputStream.mark(paramDerInputStream.available());
/*     */       
/* 154 */       parse(paramDerInputStream, false);
/* 155 */     } catch (IOException iOException) {
/*     */       try {
/* 157 */         paramDerInputStream.reset();
/*     */         
/* 159 */         parse(paramDerInputStream, true);
/* 160 */         this.oldStyle = true;
/* 161 */       } catch (IOException iOException1) {
/*     */         
/* 163 */         ParsingException parsingException = new ParsingException(iOException1.getMessage());
/* 164 */         parsingException.initCause(iOException);
/* 165 */         parsingException.addSuppressed(iOException1);
/* 166 */         throw parsingException;
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
/*     */   private void parse(DerInputStream paramDerInputStream, boolean paramBoolean) throws IOException {
/* 181 */     this.contentInfo = new ContentInfo(paramDerInputStream, paramBoolean);
/* 182 */     this.contentType = this.contentInfo.contentType;
/* 183 */     DerValue derValue = this.contentInfo.getContent();
/*     */     
/* 185 */     if (this.contentType.equals(ContentInfo.SIGNED_DATA_OID)) {
/* 186 */       parseSignedData(derValue);
/* 187 */     } else if (this.contentType.equals(ContentInfo.OLD_SIGNED_DATA_OID)) {
/*     */       
/* 189 */       parseOldSignedData(derValue);
/* 190 */     } else if (this.contentType.equals(ContentInfo.NETSCAPE_CERT_SEQUENCE_OID)) {
/*     */       
/* 192 */       parseNetscapeCertChain(derValue);
/*     */     } else {
/* 194 */       throw new ParsingException("content type " + this.contentType + " not supported.");
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PKCS7(AlgorithmId[] paramArrayOfAlgorithmId, ContentInfo paramContentInfo, X509Certificate[] paramArrayOfX509Certificate, X509CRL[] paramArrayOfX509CRL, SignerInfo[] paramArrayOfSignerInfo) {
/* 214 */     this.version = BigInteger.ONE;
/* 215 */     this.digestAlgorithmIds = paramArrayOfAlgorithmId;
/* 216 */     this.contentInfo = paramContentInfo;
/* 217 */     this.certificates = paramArrayOfX509Certificate;
/* 218 */     this.crls = paramArrayOfX509CRL;
/* 219 */     this.signerInfos = paramArrayOfSignerInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PKCS7(AlgorithmId[] paramArrayOfAlgorithmId, ContentInfo paramContentInfo, X509Certificate[] paramArrayOfX509Certificate, SignerInfo[] paramArrayOfSignerInfo) {
/* 226 */     this(paramArrayOfAlgorithmId, paramContentInfo, paramArrayOfX509Certificate, null, paramArrayOfSignerInfo);
/*     */   }
/*     */ 
/*     */   
/*     */   private void parseNetscapeCertChain(DerValue paramDerValue) throws ParsingException, IOException {
/* 231 */     DerInputStream derInputStream = new DerInputStream(paramDerValue.toByteArray());
/* 232 */     DerValue[] arrayOfDerValue = derInputStream.getSequence(2);
/* 233 */     this.certificates = new X509Certificate[arrayOfDerValue.length];
/*     */     
/* 235 */     CertificateFactory certificateFactory = null;
/*     */     try {
/* 237 */       certificateFactory = CertificateFactory.getInstance("X.509");
/* 238 */     } catch (CertificateException certificateException) {}
/*     */ 
/*     */ 
/*     */     
/* 242 */     for (byte b = 0; b < arrayOfDerValue.length; b++) {
/* 243 */       ByteArrayInputStream byteArrayInputStream = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseSignedData(DerValue paramDerValue) throws ParsingException, IOException {
/* 273 */     DerInputStream derInputStream = paramDerValue.toDerInputStream();
/*     */ 
/*     */     
/* 276 */     this.version = derInputStream.getBigInteger();
/*     */ 
/*     */     
/* 279 */     DerValue[] arrayOfDerValue1 = derInputStream.getSet(1);
/* 280 */     int i = arrayOfDerValue1.length;
/* 281 */     this.digestAlgorithmIds = new AlgorithmId[i];
/*     */     try {
/* 283 */       for (byte b1 = 0; b1 < i; b1++) {
/* 284 */         DerValue derValue = arrayOfDerValue1[b1];
/* 285 */         this.digestAlgorithmIds[b1] = AlgorithmId.parse(derValue);
/*     */       }
/*     */     
/* 288 */     } catch (IOException iOException) {
/*     */ 
/*     */       
/* 291 */       ParsingException parsingException = new ParsingException("Error parsing digest AlgorithmId IDs: " + iOException.getMessage());
/* 292 */       parsingException.initCause(iOException);
/* 293 */       throw parsingException;
/*     */     } 
/*     */     
/* 296 */     this.contentInfo = new ContentInfo(derInputStream);
/*     */     
/* 298 */     CertificateFactory certificateFactory = null;
/*     */     try {
/* 300 */       certificateFactory = CertificateFactory.getInstance("X.509");
/* 301 */     } catch (CertificateException certificateException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 309 */     if ((byte)derInputStream.peekByte() == -96) {
/* 310 */       DerValue[] arrayOfDerValue = derInputStream.getSet(2, true);
/*     */       
/* 312 */       i = arrayOfDerValue.length;
/* 313 */       this.certificates = new X509Certificate[i];
/* 314 */       byte b1 = 0;
/*     */       
/* 316 */       for (byte b2 = 0; b2 < i; b2++) {
/* 317 */         ByteArrayInputStream byteArrayInputStream = null;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 348 */       if (b1 != i) {
/* 349 */         this.certificates = Arrays.<X509Certificate>copyOf(this.certificates, b1);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 354 */     if ((byte)derInputStream.peekByte() == -95) {
/* 355 */       DerValue[] arrayOfDerValue = derInputStream.getSet(1, true);
/*     */       
/* 357 */       i = arrayOfDerValue.length;
/* 358 */       this.crls = new X509CRL[i];
/*     */       
/* 360 */       for (byte b1 = 0; b1 < i; b1++) {
/* 361 */         ByteArrayInputStream byteArrayInputStream = null;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 385 */     DerValue[] arrayOfDerValue2 = derInputStream.getSet(1);
/*     */     
/* 387 */     i = arrayOfDerValue2.length;
/* 388 */     this.signerInfos = new SignerInfo[i];
/*     */     
/* 390 */     for (byte b = 0; b < i; b++) {
/* 391 */       DerInputStream derInputStream1 = arrayOfDerValue2[b].toDerInputStream();
/* 392 */       this.signerInfos[b] = new SignerInfo(derInputStream1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseOldSignedData(DerValue paramDerValue) throws ParsingException, IOException {
/* 403 */     DerInputStream derInputStream = paramDerValue.toDerInputStream();
/*     */ 
/*     */     
/* 406 */     this.version = derInputStream.getBigInteger();
/*     */ 
/*     */     
/* 409 */     DerValue[] arrayOfDerValue1 = derInputStream.getSet(1);
/* 410 */     int i = arrayOfDerValue1.length;
/*     */     
/* 412 */     this.digestAlgorithmIds = new AlgorithmId[i];
/*     */     try {
/* 414 */       for (byte b = 0; b < i; b++) {
/* 415 */         DerValue derValue = arrayOfDerValue1[b];
/* 416 */         this.digestAlgorithmIds[b] = AlgorithmId.parse(derValue);
/*     */       } 
/* 418 */     } catch (IOException iOException) {
/* 419 */       throw new ParsingException("Error parsing digest AlgorithmId IDs");
/*     */     } 
/*     */ 
/*     */     
/* 423 */     this.contentInfo = new ContentInfo(derInputStream, true);
/*     */ 
/*     */     
/* 426 */     CertificateFactory certificateFactory = null;
/*     */     try {
/* 428 */       certificateFactory = CertificateFactory.getInstance("X.509");
/* 429 */     } catch (CertificateException certificateException) {}
/*     */ 
/*     */     
/* 432 */     DerValue[] arrayOfDerValue2 = derInputStream.getSet(2);
/* 433 */     i = arrayOfDerValue2.length;
/* 434 */     this.certificates = new X509Certificate[i];
/*     */     
/* 436 */     for (byte b1 = 0; b1 < i; b1++) {
/* 437 */       ByteArrayInputStream byteArrayInputStream = null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 464 */     derInputStream.getSet(0);
/*     */ 
/*     */     
/* 467 */     DerValue[] arrayOfDerValue3 = derInputStream.getSet(1);
/* 468 */     i = arrayOfDerValue3.length;
/* 469 */     this.signerInfos = new SignerInfo[i];
/* 470 */     for (byte b2 = 0; b2 < i; b2++) {
/* 471 */       DerInputStream derInputStream1 = arrayOfDerValue3[b2].toDerInputStream();
/* 472 */       this.signerInfos[b2] = new SignerInfo(derInputStream1, true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encodeSignedData(OutputStream paramOutputStream) throws IOException {
/* 483 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 484 */     encodeSignedData(derOutputStream);
/* 485 */     paramOutputStream.write(derOutputStream.toByteArray());
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
/*     */   public void encodeSignedData(DerOutputStream paramDerOutputStream) throws IOException {
/* 497 */     DerOutputStream derOutputStream = new DerOutputStream();
/*     */ 
/*     */     
/* 500 */     derOutputStream.putInteger(this.version);
/*     */ 
/*     */     
/* 503 */     derOutputStream.putOrderedSetOf((byte)49, (DerEncoder[])this.digestAlgorithmIds);
/*     */ 
/*     */     
/* 506 */     this.contentInfo.encode(derOutputStream);
/*     */ 
/*     */     
/* 509 */     if (this.certificates != null && this.certificates.length != 0) {
/*     */       
/* 511 */       X509CertImpl[] arrayOfX509CertImpl = new X509CertImpl[this.certificates.length];
/* 512 */       for (byte b = 0; b < this.certificates.length; b++) {
/* 513 */         if (this.certificates[b] instanceof X509CertImpl) {
/* 514 */           arrayOfX509CertImpl[b] = (X509CertImpl)this.certificates[b];
/*     */         } else {
/*     */           try {
/* 517 */             byte[] arrayOfByte = this.certificates[b].getEncoded();
/* 518 */             arrayOfX509CertImpl[b] = new X509CertImpl(arrayOfByte);
/* 519 */           } catch (CertificateException certificateException) {
/* 520 */             throw new IOException(certificateException);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 527 */       derOutputStream.putOrderedSetOf((byte)-96, (DerEncoder[])arrayOfX509CertImpl);
/*     */     } 
/*     */ 
/*     */     
/* 531 */     if (this.crls != null && this.crls.length != 0) {
/*     */       
/* 533 */       HashSet<X509CRLImpl> hashSet = new HashSet(this.crls.length);
/* 534 */       for (X509CRL x509CRL : this.crls) {
/* 535 */         if (x509CRL instanceof X509CRLImpl) {
/* 536 */           hashSet.add((X509CRLImpl)x509CRL);
/*     */         } else {
/*     */           try {
/* 539 */             byte[] arrayOfByte = x509CRL.getEncoded();
/* 540 */             hashSet.add(new X509CRLImpl(arrayOfByte));
/* 541 */           } catch (CRLException cRLException) {
/* 542 */             throw new IOException(cRLException);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 549 */       derOutputStream.putOrderedSetOf((byte)-95, hashSet
/* 550 */           .<DerEncoder>toArray((DerEncoder[])new X509CRLImpl[hashSet.size()]));
/*     */     } 
/*     */ 
/*     */     
/* 554 */     derOutputStream.putOrderedSetOf((byte)49, (DerEncoder[])this.signerInfos);
/*     */ 
/*     */ 
/*     */     
/* 558 */     DerValue derValue = new DerValue((byte)48, derOutputStream.toByteArray());
/*     */ 
/*     */     
/* 561 */     ContentInfo contentInfo = new ContentInfo(ContentInfo.SIGNED_DATA_OID, derValue);
/*     */ 
/*     */ 
/*     */     
/* 565 */     contentInfo.encode(paramDerOutputStream);
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
/*     */   public SignerInfo verify(SignerInfo paramSignerInfo, byte[] paramArrayOfbyte) throws NoSuchAlgorithmException, SignatureException {
/* 579 */     return paramSignerInfo.verify(this, paramArrayOfbyte);
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
/*     */   public SignerInfo[] verify(byte[] paramArrayOfbyte) throws NoSuchAlgorithmException, SignatureException {
/* 593 */     Vector<SignerInfo> vector = new Vector();
/* 594 */     for (byte b = 0; b < this.signerInfos.length; b++) {
/*     */       
/* 596 */       SignerInfo signerInfo = verify(this.signerInfos[b], paramArrayOfbyte);
/* 597 */       if (signerInfo != null) {
/* 598 */         vector.addElement(signerInfo);
/*     */       }
/*     */     } 
/* 601 */     if (!vector.isEmpty()) {
/*     */       
/* 603 */       SignerInfo[] arrayOfSignerInfo = new SignerInfo[vector.size()];
/* 604 */       vector.copyInto((Object[])arrayOfSignerInfo);
/* 605 */       return arrayOfSignerInfo;
/*     */     } 
/* 607 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignerInfo[] verify() throws NoSuchAlgorithmException, SignatureException {
/* 618 */     return verify(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigInteger getVersion() {
/* 627 */     return this.version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AlgorithmId[] getDigestAlgorithmIds() {
/* 636 */     return this.digestAlgorithmIds;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContentInfo getContentInfo() {
/* 643 */     return this.contentInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509Certificate[] getCertificates() {
/* 652 */     if (this.certificates != null) {
/* 653 */       return (X509Certificate[])this.certificates.clone();
/*     */     }
/* 655 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509CRL[] getCRLs() {
/* 664 */     if (this.crls != null) {
/* 665 */       return (X509CRL[])this.crls.clone();
/*     */     }
/* 667 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignerInfo[] getSignerInfos() {
/* 676 */     return this.signerInfos;
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
/*     */   public X509Certificate getCertificate(BigInteger paramBigInteger, X500Name paramX500Name) {
/* 688 */     if (this.certificates != null) {
/* 689 */       if (this.certIssuerNames == null)
/* 690 */         populateCertIssuerNames(); 
/* 691 */       for (byte b = 0; b < this.certificates.length; b++) {
/* 692 */         X509Certificate x509Certificate = this.certificates[b];
/* 693 */         BigInteger bigInteger = x509Certificate.getSerialNumber();
/* 694 */         if (paramBigInteger.equals(bigInteger) && paramX500Name
/* 695 */           .equals(this.certIssuerNames[b]))
/*     */         {
/* 697 */           return x509Certificate;
/*     */         }
/*     */       } 
/*     */     } 
/* 701 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void populateCertIssuerNames() {
/* 709 */     if (this.certificates == null) {
/*     */       return;
/*     */     }
/* 712 */     this.certIssuerNames = new Principal[this.certificates.length];
/* 713 */     for (byte b = 0; b < this.certificates.length; b++) {
/* 714 */       X509Certificate x509Certificate = this.certificates[b];
/* 715 */       Principal principal = x509Certificate.getIssuerDN();
/* 716 */       if (!(principal instanceof X500Name)) {
/*     */         
/*     */         try {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 723 */           X509CertInfo x509CertInfo = new X509CertInfo(x509Certificate.getTBSCertificate());
/*     */           
/* 725 */           principal = (Principal)x509CertInfo.get("issuer.dname");
/*     */         }
/* 727 */         catch (Exception exception) {}
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 732 */       this.certIssuerNames[b] = principal;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 740 */     String str = "";
/*     */     
/* 742 */     str = str + this.contentInfo + "\n";
/* 743 */     if (this.version != null)
/* 744 */       str = str + "PKCS7 :: version: " + Debug.toHexString(this.version) + "\n"; 
/* 745 */     if (this.digestAlgorithmIds != null) {
/* 746 */       str = str + "PKCS7 :: digest AlgorithmIds: \n";
/* 747 */       for (byte b = 0; b < this.digestAlgorithmIds.length; b++)
/* 748 */         str = str + "\t" + this.digestAlgorithmIds[b] + "\n"; 
/*     */     } 
/* 750 */     if (this.certificates != null) {
/* 751 */       str = str + "PKCS7 :: certificates: \n";
/* 752 */       for (byte b = 0; b < this.certificates.length; b++)
/* 753 */         str = str + "\t" + b + ".   " + this.certificates[b] + "\n"; 
/*     */     } 
/* 755 */     if (this.crls != null) {
/* 756 */       str = str + "PKCS7 :: crls: \n";
/* 757 */       for (byte b = 0; b < this.crls.length; b++)
/* 758 */         str = str + "\t" + b + ".   " + this.crls[b] + "\n"; 
/*     */     } 
/* 760 */     if (this.signerInfos != null) {
/* 761 */       str = str + "PKCS7 :: signer infos: \n";
/* 762 */       for (byte b = 0; b < this.signerInfos.length; b++)
/* 763 */         str = str + "\t" + b + ".  " + this.signerInfos[b] + "\n"; 
/*     */     } 
/* 765 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOldStyle() {
/* 773 */     return this.oldStyle;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] generateSignedData(byte[] paramArrayOfbyte1, X509Certificate[] paramArrayOfX509Certificate, byte[] paramArrayOfbyte2, String paramString1, URI paramURI, String paramString2, String paramString3) throws CertificateException, IOException, NoSuchAlgorithmException {
/* 811 */     PKCS9Attributes pKCS9Attributes = null;
/* 812 */     if (paramURI != null) {
/*     */       
/* 814 */       HttpTimestamper httpTimestamper = new HttpTimestamper(paramURI);
/* 815 */       byte[] arrayOfByte = generateTimestampToken(httpTimestamper, paramString2, paramString3, paramArrayOfbyte1);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 820 */       pKCS9Attributes = new PKCS9Attributes(new PKCS9Attribute[] { new PKCS9Attribute("SignatureTimestampToken", arrayOfByte) });
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 829 */     X500Name x500Name = X500Name.asX500Name(paramArrayOfX509Certificate[0].getIssuerX500Principal());
/* 830 */     BigInteger bigInteger = paramArrayOfX509Certificate[0].getSerialNumber();
/* 831 */     String str1 = AlgorithmId.getEncAlgFromSigAlg(paramString1);
/* 832 */     String str2 = AlgorithmId.getDigAlgFromSigAlg(paramString1);
/*     */ 
/*     */     
/* 835 */     SignerInfo signerInfo = new SignerInfo(x500Name, bigInteger, AlgorithmId.get(str2), null, AlgorithmId.get(str1), paramArrayOfbyte1, pKCS9Attributes);
/*     */ 
/*     */ 
/*     */     
/* 839 */     SignerInfo[] arrayOfSignerInfo = { signerInfo };
/* 840 */     AlgorithmId[] arrayOfAlgorithmId = { signerInfo.getDigestAlgorithmId() };
/*     */     
/* 842 */     ContentInfo contentInfo = (paramArrayOfbyte2 == null) ? new ContentInfo(ContentInfo.DATA_OID, null) : new ContentInfo(paramArrayOfbyte2);
/*     */ 
/*     */     
/* 845 */     PKCS7 pKCS7 = new PKCS7(arrayOfAlgorithmId, contentInfo, paramArrayOfX509Certificate, arrayOfSignerInfo);
/*     */     
/* 847 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 848 */     pKCS7.encodeSignedData(byteArrayOutputStream);
/*     */     
/* 850 */     return byteArrayOutputStream.toByteArray();
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
/*     */ 
/*     */   
/*     */   private static byte[] generateTimestampToken(Timestamper paramTimestamper, String paramString1, String paramString2, byte[] paramArrayOfbyte) throws IOException, CertificateException {
/* 879 */     MessageDigest messageDigest = null;
/* 880 */     TSRequest tSRequest = null;
/*     */     try {
/* 882 */       messageDigest = MessageDigest.getInstance(paramString2);
/* 883 */       tSRequest = new TSRequest(paramString1, paramArrayOfbyte, messageDigest);
/* 884 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 885 */       throw new IllegalArgumentException(noSuchAlgorithmException);
/*     */     } 
/*     */ 
/*     */     
/* 889 */     BigInteger bigInteger1 = null;
/* 890 */     if (SecureRandomHolder.RANDOM != null) {
/* 891 */       bigInteger1 = new BigInteger(64, SecureRandomHolder.RANDOM);
/* 892 */       tSRequest.setNonce(bigInteger1);
/*     */     } 
/* 894 */     tSRequest.requestCertificate(true);
/*     */     
/* 896 */     TSResponse tSResponse = paramTimestamper.generateTimestamp(tSRequest);
/* 897 */     int i = tSResponse.getStatusCode();
/*     */     
/* 899 */     if (i != 0 && i != 1) {
/* 900 */       throw new IOException("Error generating timestamp: " + tSResponse
/* 901 */           .getStatusCodeAsText() + " " + tSResponse
/* 902 */           .getFailureCodeAsText());
/*     */     }
/*     */     
/* 905 */     if (paramString1 != null && 
/* 906 */       !paramString1.equals(tSResponse.getTimestampToken().getPolicyID())) {
/* 907 */       throw new IOException("TSAPolicyID changed in timestamp token");
/*     */     }
/*     */     
/* 910 */     PKCS7 pKCS7 = tSResponse.getToken();
/*     */     
/* 912 */     TimestampToken timestampToken = tSResponse.getTimestampToken();
/*     */     try {
/* 914 */       if (!timestampToken.getHashAlgorithm().equals(AlgorithmId.get(paramString2))) {
/* 915 */         throw new IOException("Digest algorithm not " + paramString2 + " in timestamp token");
/*     */       }
/*     */     }
/* 918 */     catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 919 */       throw new IllegalArgumentException();
/*     */     } 
/* 921 */     if (!MessageDigest.isEqual(timestampToken.getHashedMessage(), tSRequest
/* 922 */         .getHashedMessage())) {
/* 923 */       throw new IOException("Digest octets changed in timestamp token");
/*     */     }
/*     */     
/* 926 */     BigInteger bigInteger2 = timestampToken.getNonce();
/* 927 */     if (bigInteger2 == null && bigInteger1 != null) {
/* 928 */       throw new IOException("Nonce missing in timestamp token");
/*     */     }
/* 930 */     if (bigInteger2 != null && !bigInteger2.equals(bigInteger1)) {
/* 931 */       throw new IOException("Nonce changed in timestamp token");
/*     */     }
/*     */ 
/*     */     
/* 935 */     for (SignerInfo signerInfo : pKCS7.getSignerInfos()) {
/* 936 */       X509Certificate x509Certificate = signerInfo.getCertificate(pKCS7);
/* 937 */       if (x509Certificate == null)
/*     */       {
/* 939 */         throw new CertificateException("Certificate not included in timestamp token");
/*     */       }
/*     */       
/* 942 */       if (!x509Certificate.getCriticalExtensionOIDs().contains("2.5.29.37"))
/*     */       {
/* 944 */         throw new CertificateException("Certificate is not valid for timestamping");
/*     */       }
/*     */       
/* 947 */       List<String> list = x509Certificate.getExtendedKeyUsage();
/* 948 */       if (list == null || 
/* 949 */         !list.contains("1.3.6.1.5.5.7.3.8")) {
/* 950 */         throw new CertificateException("Certificate is not valid for timestamping");
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 955 */     return tSResponse.getEncodedToken();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/pkcs/PKCS7.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */