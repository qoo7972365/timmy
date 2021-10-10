/*      */ package sun.security.x509;
/*      */ 
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.OutputStream;
/*      */ import java.math.BigInteger;
/*      */ import java.security.InvalidKeyException;
/*      */ import java.security.MessageDigest;
/*      */ import java.security.NoSuchAlgorithmException;
/*      */ import java.security.NoSuchProviderException;
/*      */ import java.security.Principal;
/*      */ import java.security.PrivateKey;
/*      */ import java.security.Provider;
/*      */ import java.security.PublicKey;
/*      */ import java.security.Signature;
/*      */ import java.security.SignatureException;
/*      */ import java.security.cert.Certificate;
/*      */ import java.security.cert.CertificateEncodingException;
/*      */ import java.security.cert.CertificateException;
/*      */ import java.security.cert.CertificateExpiredException;
/*      */ import java.security.cert.CertificateNotYetValidException;
/*      */ import java.security.cert.CertificateParsingException;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.TreeSet;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import javax.security.auth.x500.X500Principal;
/*      */ import sun.misc.HexDumpEncoder;
/*      */ import sun.security.provider.X509Factory;
/*      */ import sun.security.util.DerEncoder;
/*      */ import sun.security.util.DerInputStream;
/*      */ import sun.security.util.DerOutputStream;
/*      */ import sun.security.util.DerValue;
/*      */ import sun.security.util.ObjectIdentifier;
/*      */ import sun.security.util.Pem;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class X509CertImpl
/*      */   extends X509Certificate
/*      */   implements DerEncoder
/*      */ {
/*      */   private static final long serialVersionUID = -3457612960190864406L;
/*      */   private static final String DOT = ".";
/*      */   public static final String NAME = "x509";
/*      */   public static final String INFO = "info";
/*      */   public static final String ALG_ID = "algorithm";
/*      */   public static final String SIGNATURE = "signature";
/*      */   public static final String SIGNED_CERT = "signed_cert";
/*      */   public static final String SUBJECT_DN = "x509.info.subject.dname";
/*      */   public static final String ISSUER_DN = "x509.info.issuer.dname";
/*      */   public static final String SERIAL_ID = "x509.info.serialNumber.number";
/*      */   public static final String PUBLIC_KEY = "x509.info.key.value";
/*      */   public static final String VERSION = "x509.info.version.number";
/*      */   public static final String SIG_ALG = "x509.algorithm";
/*      */   public static final String SIG = "x509.signature";
/*      */   private boolean readOnly = false;
/*  129 */   private byte[] signedCert = null;
/*  130 */   protected X509CertInfo info = null;
/*  131 */   protected AlgorithmId algId = null;
/*  132 */   protected byte[] signature = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_USAGE_OID = "2.5.29.15";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String EXTENDED_KEY_USAGE_OID = "2.5.29.37";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String BASIC_CONSTRAINT_OID = "2.5.29.19";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String SUBJECT_ALT_NAME_OID = "2.5.29.17";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String ISSUER_ALT_NAME_OID = "2.5.29.18";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String AUTH_INFO_ACCESS_OID = "1.3.6.1.5.5.7.1.1";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int NUM_STANDARD_KEY_USAGE = 9;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Collection<List<?>> subjectAlternativeNames;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Collection<List<?>> issuerAlternativeNames;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private List<String> extKeyUsage;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Set<AccessDescription> authInfoAccess;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PublicKey verifiedPublicKey;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String verifiedProvider;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean verificationResult;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ConcurrentHashMap<String, String> fingerprints;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DerValue readRFC1421Cert(InputStream paramInputStream) throws IOException {
/*  255 */     DerValue derValue = null;
/*  256 */     String str = null;
/*  257 */     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(paramInputStream, "ASCII"));
/*      */     
/*      */     try {
/*  260 */       str = bufferedReader.readLine();
/*  261 */     } catch (IOException iOException) {
/*  262 */       throw new IOException("Unable to read InputStream: " + iOException
/*  263 */           .getMessage());
/*      */     } 
/*  265 */     if (str.equals("-----BEGIN CERTIFICATE-----")) {
/*      */       
/*  267 */       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/*      */       try {
/*  269 */         while ((str = bufferedReader.readLine()) != null) {
/*  270 */           if (str.equals("-----END CERTIFICATE-----")) {
/*  271 */             derValue = new DerValue(byteArrayOutputStream.toByteArray());
/*      */             break;
/*      */           } 
/*  274 */           byteArrayOutputStream.write(Pem.decode(str));
/*      */         }
/*      */       
/*  277 */       } catch (IOException iOException) {
/*  278 */         throw new IOException("Unable to read InputStream: " + iOException
/*  279 */             .getMessage());
/*      */       } 
/*      */     } else {
/*  282 */       throw new IOException("InputStream is not RFC1421 hex-encoded DER bytes");
/*      */     } 
/*      */     
/*  285 */     return derValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void encode(OutputStream paramOutputStream) throws CertificateEncodingException {
/*  324 */     if (this.signedCert == null) {
/*  325 */       throw new CertificateEncodingException("Null certificate to encode");
/*      */     }
/*      */     try {
/*  328 */       paramOutputStream.write((byte[])this.signedCert.clone());
/*  329 */     } catch (IOException iOException) {
/*  330 */       throw new CertificateEncodingException(iOException.toString());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void derEncode(OutputStream paramOutputStream) throws IOException {
/*  343 */     if (this.signedCert == null)
/*  344 */       throw new IOException("Null certificate to encode"); 
/*  345 */     paramOutputStream.write((byte[])this.signedCert.clone());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getEncoded() throws CertificateEncodingException {
/*  357 */     return (byte[])getEncodedInternal().clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getEncodedInternal() throws CertificateEncodingException {
/*  366 */     if (this.signedCert == null) {
/*  367 */       throw new CertificateEncodingException("Null certificate to encode");
/*      */     }
/*      */     
/*  370 */     return this.signedCert;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void verify(PublicKey paramPublicKey) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
/*  392 */     verify(paramPublicKey, "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void verify(PublicKey paramPublicKey, String paramString) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
/*  414 */     if (paramString == null) {
/*  415 */       paramString = "";
/*      */     }
/*  417 */     if (this.verifiedPublicKey != null && this.verifiedPublicKey.equals(paramPublicKey))
/*      */     {
/*      */       
/*  420 */       if (paramString.equals(this.verifiedProvider)) {
/*  421 */         if (this.verificationResult) {
/*      */           return;
/*      */         }
/*  424 */         throw new SignatureException("Signature does not match.");
/*      */       } 
/*      */     }
/*      */     
/*  428 */     if (this.signedCert == null) {
/*  429 */       throw new CertificateEncodingException("Uninitialized certificate");
/*      */     }
/*      */     
/*  432 */     Signature signature = null;
/*  433 */     if (paramString.length() == 0) {
/*  434 */       signature = Signature.getInstance(this.algId.getName());
/*      */     } else {
/*  436 */       signature = Signature.getInstance(this.algId.getName(), paramString);
/*      */     } 
/*  438 */     signature.initVerify(paramPublicKey);
/*      */     
/*  440 */     byte[] arrayOfByte = this.info.getEncodedInfo();
/*  441 */     signature.update(arrayOfByte, 0, arrayOfByte.length);
/*      */ 
/*      */     
/*  444 */     this.verificationResult = signature.verify(this.signature);
/*  445 */     this.verifiedPublicKey = paramPublicKey;
/*  446 */     this.verifiedProvider = paramString;
/*      */     
/*  448 */     if (!this.verificationResult) {
/*  449 */       throw new SignatureException("Signature does not match.");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void verify(PublicKey paramPublicKey, Provider paramProvider) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
/*  473 */     if (this.signedCert == null) {
/*  474 */       throw new CertificateEncodingException("Uninitialized certificate");
/*      */     }
/*      */     
/*  477 */     Signature signature = null;
/*  478 */     if (paramProvider == null) {
/*  479 */       signature = Signature.getInstance(this.algId.getName());
/*      */     } else {
/*  481 */       signature = Signature.getInstance(this.algId.getName(), paramProvider);
/*      */     } 
/*  483 */     signature.initVerify(paramPublicKey);
/*      */     
/*  485 */     byte[] arrayOfByte = this.info.getEncodedInfo();
/*  486 */     signature.update(arrayOfByte, 0, arrayOfByte.length);
/*      */ 
/*      */     
/*  489 */     this.verificationResult = signature.verify(this.signature);
/*  490 */     this.verifiedPublicKey = paramPublicKey;
/*      */     
/*  492 */     if (!this.verificationResult) {
/*  493 */       throw new SignatureException("Signature does not match.");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void verify(X509Certificate paramX509Certificate, PublicKey paramPublicKey, Provider paramProvider) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
/*  506 */     paramX509Certificate.verify(paramPublicKey, paramProvider);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sign(PrivateKey paramPrivateKey, String paramString) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
/*  528 */     sign(paramPrivateKey, paramString, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sign(PrivateKey paramPrivateKey, String paramString1, String paramString2) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
/*      */     try {
/*  552 */       if (this.readOnly) {
/*  553 */         throw new CertificateEncodingException("cannot over-write existing certificate");
/*      */       }
/*  555 */       Signature signature = null;
/*  556 */       if (paramString2 == null || paramString2.length() == 0) {
/*  557 */         signature = Signature.getInstance(paramString1);
/*      */       } else {
/*  559 */         signature = Signature.getInstance(paramString1, paramString2);
/*      */       } 
/*  561 */       signature.initSign(paramPrivateKey);
/*      */ 
/*      */       
/*  564 */       this.algId = AlgorithmId.get(signature.getAlgorithm());
/*      */       
/*  566 */       DerOutputStream derOutputStream1 = new DerOutputStream();
/*  567 */       DerOutputStream derOutputStream2 = new DerOutputStream();
/*      */ 
/*      */       
/*  570 */       this.info.encode(derOutputStream2);
/*  571 */       byte[] arrayOfByte = derOutputStream2.toByteArray();
/*      */ 
/*      */       
/*  574 */       this.algId.encode(derOutputStream2);
/*      */ 
/*      */       
/*  577 */       signature.update(arrayOfByte, 0, arrayOfByte.length);
/*  578 */       this.signature = signature.sign();
/*  579 */       derOutputStream2.putBitString(this.signature);
/*      */ 
/*      */       
/*  582 */       derOutputStream1.write((byte)48, derOutputStream2);
/*  583 */       this.signedCert = derOutputStream1.toByteArray();
/*  584 */       this.readOnly = true;
/*      */     }
/*  586 */     catch (IOException iOException) {
/*  587 */       throw new CertificateEncodingException(iOException.toString());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
/*  601 */     Date date = new Date();
/*  602 */     checkValidity(date);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkValidity(Date paramDate) throws CertificateExpiredException, CertificateNotYetValidException {
/*  621 */     CertificateValidity certificateValidity = null;
/*      */     try {
/*  623 */       certificateValidity = (CertificateValidity)this.info.get("validity");
/*  624 */     } catch (Exception exception) {
/*  625 */       throw new CertificateNotYetValidException("Incorrect validity period");
/*      */     } 
/*  627 */     if (certificateValidity == null)
/*  628 */       throw new CertificateNotYetValidException("Null validity period"); 
/*  629 */     certificateValidity.valid(paramDate);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object get(String paramString) throws CertificateParsingException {
/*  644 */     X509AttributeName x509AttributeName = new X509AttributeName(paramString);
/*  645 */     String str = x509AttributeName.getPrefix();
/*  646 */     if (!str.equalsIgnoreCase("x509")) {
/*  647 */       throw new CertificateParsingException("Invalid root of attribute name, expected [x509], received [" + str + "]");
/*      */     }
/*      */ 
/*      */     
/*  651 */     x509AttributeName = new X509AttributeName(x509AttributeName.getSuffix());
/*  652 */     str = x509AttributeName.getPrefix();
/*      */     
/*  654 */     if (str.equalsIgnoreCase("info")) {
/*  655 */       if (this.info == null) {
/*  656 */         return null;
/*      */       }
/*  658 */       if (x509AttributeName.getSuffix() != null) {
/*      */         try {
/*  660 */           return this.info.get(x509AttributeName.getSuffix());
/*  661 */         } catch (IOException iOException) {
/*  662 */           throw new CertificateParsingException(iOException.toString());
/*  663 */         } catch (CertificateException certificateException) {
/*  664 */           throw new CertificateParsingException(certificateException.toString());
/*      */         } 
/*      */       }
/*  667 */       return this.info;
/*      */     } 
/*  669 */     if (str.equalsIgnoreCase("algorithm"))
/*  670 */       return this.algId; 
/*  671 */     if (str.equalsIgnoreCase("signature")) {
/*  672 */       if (this.signature != null) {
/*  673 */         return this.signature.clone();
/*      */       }
/*  675 */       return null;
/*  676 */     }  if (str.equalsIgnoreCase("signed_cert")) {
/*  677 */       if (this.signedCert != null) {
/*  678 */         return this.signedCert.clone();
/*      */       }
/*  680 */       return null;
/*      */     } 
/*  682 */     throw new CertificateParsingException("Attribute name not recognized or get() not allowed for the same: " + str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void set(String paramString, Object paramObject) throws CertificateException, IOException {
/*  698 */     if (this.readOnly) {
/*  699 */       throw new CertificateException("cannot over-write existing certificate");
/*      */     }
/*      */     
/*  702 */     X509AttributeName x509AttributeName = new X509AttributeName(paramString);
/*  703 */     String str = x509AttributeName.getPrefix();
/*  704 */     if (!str.equalsIgnoreCase("x509")) {
/*  705 */       throw new CertificateException("Invalid root of attribute name, expected [x509], received " + str);
/*      */     }
/*      */     
/*  708 */     x509AttributeName = new X509AttributeName(x509AttributeName.getSuffix());
/*  709 */     str = x509AttributeName.getPrefix();
/*      */     
/*  711 */     if (str.equalsIgnoreCase("info")) {
/*  712 */       if (x509AttributeName.getSuffix() == null) {
/*  713 */         if (!(paramObject instanceof X509CertInfo)) {
/*  714 */           throw new CertificateException("Attribute value should be of type X509CertInfo.");
/*      */         }
/*      */         
/*  717 */         this.info = (X509CertInfo)paramObject;
/*  718 */         this.signedCert = null;
/*      */       } else {
/*  720 */         this.info.set(x509AttributeName.getSuffix(), paramObject);
/*  721 */         this.signedCert = null;
/*      */       } 
/*      */     } else {
/*  724 */       throw new CertificateException("Attribute name not recognized or set() not allowed for the same: " + str);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void delete(String paramString) throws CertificateException, IOException {
/*  739 */     if (this.readOnly) {
/*  740 */       throw new CertificateException("cannot over-write existing certificate");
/*      */     }
/*      */     
/*  743 */     X509AttributeName x509AttributeName = new X509AttributeName(paramString);
/*  744 */     String str = x509AttributeName.getPrefix();
/*  745 */     if (!str.equalsIgnoreCase("x509")) {
/*  746 */       throw new CertificateException("Invalid root of attribute name, expected [x509], received " + str);
/*      */     }
/*      */ 
/*      */     
/*  750 */     x509AttributeName = new X509AttributeName(x509AttributeName.getSuffix());
/*  751 */     str = x509AttributeName.getPrefix();
/*      */     
/*  753 */     if (str.equalsIgnoreCase("info")) {
/*  754 */       if (x509AttributeName.getSuffix() != null) {
/*  755 */         this.info = null;
/*      */       } else {
/*  757 */         this.info.delete(x509AttributeName.getSuffix());
/*      */       } 
/*  759 */     } else if (str.equalsIgnoreCase("algorithm")) {
/*  760 */       this.algId = null;
/*  761 */     } else if (str.equalsIgnoreCase("signature")) {
/*  762 */       this.signature = null;
/*  763 */     } else if (str.equalsIgnoreCase("signed_cert")) {
/*  764 */       this.signedCert = null;
/*      */     } else {
/*  766 */       throw new CertificateException("Attribute name not recognized or delete() not allowed for the same: " + str);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Enumeration<String> getElements() {
/*  776 */     AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
/*  777 */     attributeNameEnumeration.addElement("x509.info");
/*  778 */     attributeNameEnumeration.addElement("x509.algorithm");
/*  779 */     attributeNameEnumeration.addElement("x509.signature");
/*  780 */     attributeNameEnumeration.addElement("x509.signed_cert");
/*      */     
/*  782 */     return attributeNameEnumeration.elements();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  789 */     return "x509";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  799 */     if (this.info == null || this.algId == null || this.signature == null) {
/*  800 */       return "";
/*      */     }
/*  802 */     StringBuilder stringBuilder = new StringBuilder();
/*      */     
/*  804 */     stringBuilder.append("[\n");
/*  805 */     stringBuilder.append(this.info.toString() + "\n");
/*  806 */     stringBuilder.append("  Algorithm: [" + this.algId.toString() + "]\n");
/*      */     
/*  808 */     HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
/*  809 */     stringBuilder.append("  Signature:\n" + hexDumpEncoder.encodeBuffer(this.signature));
/*  810 */     stringBuilder.append("\n]");
/*      */     
/*  812 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PublicKey getPublicKey() {
/*  823 */     if (this.info == null)
/*  824 */       return null; 
/*      */     try {
/*  826 */       return (PublicKey)this.info.get("key.value");
/*      */     
/*      */     }
/*  829 */     catch (Exception exception) {
/*  830 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getVersion() {
/*  840 */     if (this.info == null) {
/*  841 */       return -1;
/*      */     }
/*      */     try {
/*  844 */       int i = ((Integer)this.info.get("version.number")).intValue();
/*  845 */       return i + 1;
/*  846 */     } catch (Exception exception) {
/*  847 */       return -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigInteger getSerialNumber() {
/*  857 */     SerialNumber serialNumber = getSerialNumberObject();
/*      */     
/*  859 */     return (serialNumber != null) ? serialNumber.getNumber() : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SerialNumber getSerialNumberObject() {
/*  869 */     if (this.info == null)
/*  870 */       return null; 
/*      */     try {
/*  872 */       return (SerialNumber)this.info.get("serialNumber.number");
/*      */ 
/*      */     
/*      */     }
/*  876 */     catch (Exception exception) {
/*  877 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Principal getSubjectDN() {
/*  888 */     if (this.info == null)
/*  889 */       return null; 
/*      */     try {
/*  891 */       return (Principal)this.info.get("subject.dname");
/*      */     
/*      */     }
/*  894 */     catch (Exception exception) {
/*  895 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X500Principal getSubjectX500Principal() {
/*  905 */     if (this.info == null) {
/*  906 */       return null;
/*      */     }
/*      */     try {
/*  909 */       return (X500Principal)this.info.get("subject.x500principal");
/*      */ 
/*      */     
/*      */     }
/*  913 */     catch (Exception exception) {
/*  914 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Principal getIssuerDN() {
/*  924 */     if (this.info == null)
/*  925 */       return null; 
/*      */     try {
/*  927 */       return (Principal)this.info.get("issuer.dname");
/*      */     
/*      */     }
/*  930 */     catch (Exception exception) {
/*  931 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X500Principal getIssuerX500Principal() {
/*  941 */     if (this.info == null) {
/*  942 */       return null;
/*      */     }
/*      */     try {
/*  945 */       return (X500Principal)this.info.get("issuer.x500principal");
/*      */ 
/*      */     
/*      */     }
/*  949 */     catch (Exception exception) {
/*  950 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Date getNotBefore() {
/*  960 */     if (this.info == null)
/*  961 */       return null; 
/*      */     try {
/*  963 */       return (Date)this.info.get("validity.notBefore");
/*      */     
/*      */     }
/*  966 */     catch (Exception exception) {
/*  967 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Date getNotAfter() {
/*  977 */     if (this.info == null)
/*  978 */       return null; 
/*      */     try {
/*  980 */       return (Date)this.info.get("validity.notAfter");
/*      */     
/*      */     }
/*  983 */     catch (Exception exception) {
/*  984 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getTBSCertificate() throws CertificateEncodingException {
/*  997 */     if (this.info != null) {
/*  998 */       return this.info.getEncodedInfo();
/*      */     }
/* 1000 */     throw new CertificateEncodingException("Uninitialized certificate");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getSignature() {
/* 1009 */     if (this.signature == null)
/* 1010 */       return null; 
/* 1011 */     return (byte[])this.signature.clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSigAlgName() {
/* 1022 */     if (this.algId == null)
/* 1023 */       return null; 
/* 1024 */     return this.algId.getName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSigAlgOID() {
/* 1034 */     if (this.algId == null)
/* 1035 */       return null; 
/* 1036 */     ObjectIdentifier objectIdentifier = this.algId.getOID();
/* 1037 */     return objectIdentifier.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getSigAlgParams() {
/* 1048 */     if (this.algId == null)
/* 1049 */       return null; 
/*      */     try {
/* 1051 */       return this.algId.getEncodedParams();
/* 1052 */     } catch (IOException iOException) {
/* 1053 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean[] getIssuerUniqueID() {
/* 1063 */     if (this.info == null)
/* 1064 */       return null; 
/*      */     try {
/* 1066 */       UniqueIdentity uniqueIdentity = (UniqueIdentity)this.info.get("issuerID");
/*      */       
/* 1068 */       if (uniqueIdentity == null) {
/* 1069 */         return null;
/*      */       }
/* 1071 */       return uniqueIdentity.getId();
/* 1072 */     } catch (Exception exception) {
/* 1073 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean[] getSubjectUniqueID() {
/* 1083 */     if (this.info == null)
/* 1084 */       return null; 
/*      */     try {
/* 1086 */       UniqueIdentity uniqueIdentity = (UniqueIdentity)this.info.get("subjectID");
/*      */       
/* 1088 */       if (uniqueIdentity == null) {
/* 1089 */         return null;
/*      */       }
/* 1091 */       return uniqueIdentity.getId();
/* 1092 */     } catch (Exception exception) {
/* 1093 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public KeyIdentifier getAuthKeyId() {
/* 1099 */     AuthorityKeyIdentifierExtension authorityKeyIdentifierExtension = getAuthorityKeyIdentifierExtension();
/* 1100 */     if (authorityKeyIdentifierExtension != null) {
/*      */       try {
/* 1102 */         return (KeyIdentifier)authorityKeyIdentifierExtension.get("key_id");
/*      */       }
/* 1104 */       catch (IOException iOException) {}
/*      */     }
/* 1106 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyIdentifier getSubjectKeyId() {
/* 1113 */     SubjectKeyIdentifierExtension subjectKeyIdentifierExtension = getSubjectKeyIdentifierExtension();
/* 1114 */     if (subjectKeyIdentifierExtension != null) {
/*      */       try {
/* 1116 */         return subjectKeyIdentifierExtension.get("key_id");
/*      */       }
/* 1118 */       catch (IOException iOException) {}
/*      */     }
/* 1120 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AuthorityKeyIdentifierExtension getAuthorityKeyIdentifierExtension() {
/* 1130 */     return (AuthorityKeyIdentifierExtension)
/* 1131 */       getExtension(PKIXExtensions.AuthorityKey_Id);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BasicConstraintsExtension getBasicConstraintsExtension() {
/* 1140 */     return (BasicConstraintsExtension)
/* 1141 */       getExtension(PKIXExtensions.BasicConstraints_Id);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CertificatePoliciesExtension getCertificatePoliciesExtension() {
/* 1150 */     return (CertificatePoliciesExtension)
/* 1151 */       getExtension(PKIXExtensions.CertificatePolicies_Id);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ExtendedKeyUsageExtension getExtendedKeyUsageExtension() {
/* 1160 */     return (ExtendedKeyUsageExtension)
/* 1161 */       getExtension(PKIXExtensions.ExtendedKeyUsage_Id);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IssuerAlternativeNameExtension getIssuerAlternativeNameExtension() {
/* 1170 */     return (IssuerAlternativeNameExtension)
/* 1171 */       getExtension(PKIXExtensions.IssuerAlternativeName_Id);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NameConstraintsExtension getNameConstraintsExtension() {
/* 1179 */     return (NameConstraintsExtension)
/* 1180 */       getExtension(PKIXExtensions.NameConstraints_Id);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PolicyConstraintsExtension getPolicyConstraintsExtension() {
/* 1189 */     return (PolicyConstraintsExtension)
/* 1190 */       getExtension(PKIXExtensions.PolicyConstraints_Id);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PolicyMappingsExtension getPolicyMappingsExtension() {
/* 1199 */     return (PolicyMappingsExtension)
/* 1200 */       getExtension(PKIXExtensions.PolicyMappings_Id);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrivateKeyUsageExtension getPrivateKeyUsageExtension() {
/* 1208 */     return (PrivateKeyUsageExtension)
/* 1209 */       getExtension(PKIXExtensions.PrivateKeyUsage_Id);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SubjectAlternativeNameExtension getSubjectAlternativeNameExtension() {
/* 1219 */     return (SubjectAlternativeNameExtension)
/* 1220 */       getExtension(PKIXExtensions.SubjectAlternativeName_Id);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SubjectKeyIdentifierExtension getSubjectKeyIdentifierExtension() {
/* 1229 */     return (SubjectKeyIdentifierExtension)
/* 1230 */       getExtension(PKIXExtensions.SubjectKey_Id);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CRLDistributionPointsExtension getCRLDistributionPointsExtension() {
/* 1239 */     return (CRLDistributionPointsExtension)
/* 1240 */       getExtension(PKIXExtensions.CRLDistributionPoints_Id);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasUnsupportedCriticalExtension() {
/* 1248 */     if (this.info == null)
/* 1249 */       return false; 
/*      */     try {
/* 1251 */       CertificateExtensions certificateExtensions = (CertificateExtensions)this.info.get("extensions");
/*      */       
/* 1253 */       if (certificateExtensions == null)
/* 1254 */         return false; 
/* 1255 */       return certificateExtensions.hasUnsupportedCriticalExtension();
/* 1256 */     } catch (Exception exception) {
/* 1257 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<String> getCriticalExtensionOIDs() {
/* 1270 */     if (this.info == null) {
/* 1271 */       return null;
/*      */     }
/*      */     try {
/* 1274 */       CertificateExtensions certificateExtensions = (CertificateExtensions)this.info.get("extensions");
/*      */       
/* 1276 */       if (certificateExtensions == null) {
/* 1277 */         return null;
/*      */       }
/* 1279 */       TreeSet<String> treeSet = new TreeSet();
/* 1280 */       for (Extension extension : certificateExtensions.getAllExtensions()) {
/* 1281 */         if (extension.isCritical()) {
/* 1282 */           treeSet.add(extension.getExtensionId().toString());
/*      */         }
/*      */       } 
/* 1285 */       return treeSet;
/* 1286 */     } catch (Exception exception) {
/* 1287 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<String> getNonCriticalExtensionOIDs() {
/* 1300 */     if (this.info == null) {
/* 1301 */       return null;
/*      */     }
/*      */     try {
/* 1304 */       CertificateExtensions certificateExtensions = (CertificateExtensions)this.info.get("extensions");
/*      */       
/* 1306 */       if (certificateExtensions == null) {
/* 1307 */         return null;
/*      */       }
/* 1309 */       TreeSet<String> treeSet = new TreeSet();
/* 1310 */       for (Extension extension : certificateExtensions.getAllExtensions()) {
/* 1311 */         if (!extension.isCritical()) {
/* 1312 */           treeSet.add(extension.getExtensionId().toString());
/*      */         }
/*      */       } 
/* 1315 */       treeSet.addAll(certificateExtensions.getUnparseableExtensions().keySet());
/* 1316 */       return treeSet;
/* 1317 */     } catch (Exception exception) {
/* 1318 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Extension getExtension(ObjectIdentifier paramObjectIdentifier) {
/* 1330 */     if (this.info == null) {
/* 1331 */       return null;
/*      */     }
/*      */     try {
/*      */       CertificateExtensions certificateExtensions;
/*      */       try {
/* 1336 */         certificateExtensions = (CertificateExtensions)this.info.get("extensions");
/* 1337 */       } catch (CertificateException certificateException) {
/* 1338 */         return null;
/*      */       } 
/* 1340 */       if (certificateExtensions == null) {
/* 1341 */         return null;
/*      */       }
/* 1343 */       Extension extension = certificateExtensions.getExtension(paramObjectIdentifier.toString());
/* 1344 */       if (extension != null) {
/* 1345 */         return extension;
/*      */       }
/* 1347 */       for (Extension extension1 : certificateExtensions.getAllExtensions()) {
/* 1348 */         if (extension1.getExtensionId().equals(paramObjectIdentifier))
/*      */         {
/* 1350 */           return extension1;
/*      */         }
/*      */       } 
/*      */       
/* 1354 */       return null;
/*      */     }
/* 1356 */     catch (IOException iOException) {
/* 1357 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public Extension getUnparseableExtension(ObjectIdentifier paramObjectIdentifier) {
/* 1362 */     if (this.info == null) {
/* 1363 */       return null;
/*      */     }
/*      */     try {
/*      */       CertificateExtensions certificateExtensions;
/*      */       try {
/* 1368 */         certificateExtensions = (CertificateExtensions)this.info.get("extensions");
/* 1369 */       } catch (CertificateException certificateException) {
/* 1370 */         return null;
/*      */       } 
/* 1372 */       if (certificateExtensions == null) {
/* 1373 */         return null;
/*      */       }
/* 1375 */       return certificateExtensions.getUnparseableExtensions().get(paramObjectIdentifier.toString());
/*      */     }
/* 1377 */     catch (IOException iOException) {
/* 1378 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getExtensionValue(String paramString) {
/*      */     try {
/* 1390 */       ObjectIdentifier objectIdentifier = new ObjectIdentifier(paramString);
/* 1391 */       String str = OIDMap.getName(objectIdentifier);
/* 1392 */       Extension extension = null;
/* 1393 */       CertificateExtensions certificateExtensions = (CertificateExtensions)this.info.get("extensions");
/*      */ 
/*      */       
/* 1396 */       if (str == null) {
/*      */         
/* 1398 */         if (certificateExtensions == null) {
/* 1399 */           return null;
/*      */         }
/*      */         
/* 1402 */         for (Extension extension1 : certificateExtensions.getAllExtensions()) {
/* 1403 */           ObjectIdentifier objectIdentifier1 = extension1.getExtensionId();
/* 1404 */           if (objectIdentifier1.equals(objectIdentifier)) {
/* 1405 */             extension = extension1;
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } else {
/*      */         try {
/* 1411 */           extension = (Extension)get(str);
/* 1412 */         } catch (CertificateException certificateException) {}
/*      */       } 
/*      */ 
/*      */       
/* 1416 */       if (extension == null) {
/* 1417 */         if (certificateExtensions != null) {
/* 1418 */           extension = certificateExtensions.getUnparseableExtensions().get(paramString);
/*      */         }
/* 1420 */         if (extension == null) {
/* 1421 */           return null;
/*      */         }
/*      */       } 
/* 1424 */       byte[] arrayOfByte = extension.getExtensionValue();
/* 1425 */       if (arrayOfByte == null) {
/* 1426 */         return null;
/*      */       }
/* 1428 */       DerOutputStream derOutputStream = new DerOutputStream();
/* 1429 */       derOutputStream.putOctetString(arrayOfByte);
/* 1430 */       return derOutputStream.toByteArray();
/* 1431 */     } catch (Exception exception) {
/* 1432 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean[] getKeyUsage() {
/*      */     try {
/* 1443 */       String str = OIDMap.getName(PKIXExtensions.KeyUsage_Id);
/* 1444 */       if (str == null) {
/* 1445 */         return null;
/*      */       }
/* 1447 */       KeyUsageExtension keyUsageExtension = (KeyUsageExtension)get(str);
/* 1448 */       if (keyUsageExtension == null) {
/* 1449 */         return null;
/*      */       }
/* 1451 */       boolean[] arrayOfBoolean = keyUsageExtension.getBits();
/* 1452 */       if (arrayOfBoolean.length < 9) {
/* 1453 */         boolean[] arrayOfBoolean1 = new boolean[9];
/* 1454 */         System.arraycopy(arrayOfBoolean, 0, arrayOfBoolean1, 0, arrayOfBoolean.length);
/* 1455 */         arrayOfBoolean = arrayOfBoolean1;
/*      */       } 
/* 1457 */       return arrayOfBoolean;
/* 1458 */     } catch (Exception exception) {
/* 1459 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized List<String> getExtendedKeyUsage() throws CertificateParsingException {
/* 1471 */     if (this.readOnly && this.extKeyUsage != null) {
/* 1472 */       return this.extKeyUsage;
/*      */     }
/* 1474 */     ExtendedKeyUsageExtension extendedKeyUsageExtension = getExtendedKeyUsageExtension();
/* 1475 */     if (extendedKeyUsageExtension == null) {
/* 1476 */       return null;
/*      */     }
/* 1478 */     this
/* 1479 */       .extKeyUsage = Collections.unmodifiableList(extendedKeyUsageExtension.getExtendedKeyUsage());
/* 1480 */     return this.extKeyUsage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static List<String> getExtendedKeyUsage(X509Certificate paramX509Certificate) throws CertificateParsingException {
/*      */     try {
/* 1493 */       byte[] arrayOfByte1 = paramX509Certificate.getExtensionValue("2.5.29.37");
/* 1494 */       if (arrayOfByte1 == null)
/* 1495 */         return null; 
/* 1496 */       DerValue derValue = new DerValue(arrayOfByte1);
/* 1497 */       byte[] arrayOfByte2 = derValue.getOctetString();
/*      */       
/* 1499 */       ExtendedKeyUsageExtension extendedKeyUsageExtension = new ExtendedKeyUsageExtension(Boolean.FALSE, arrayOfByte2);
/*      */       
/* 1501 */       return Collections.unmodifiableList(extendedKeyUsageExtension.getExtendedKeyUsage());
/* 1502 */     } catch (IOException iOException) {
/* 1503 */       throw new CertificateParsingException(iOException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getBasicConstraints() {
/*      */     try {
/* 1514 */       String str = OIDMap.getName(PKIXExtensions.BasicConstraints_Id);
/* 1515 */       if (str == null) {
/* 1516 */         return -1;
/*      */       }
/* 1518 */       BasicConstraintsExtension basicConstraintsExtension = (BasicConstraintsExtension)get(str);
/* 1519 */       if (basicConstraintsExtension == null) {
/* 1520 */         return -1;
/*      */       }
/* 1522 */       if (((Boolean)basicConstraintsExtension.get("is_ca"))
/* 1523 */         .booleanValue() == true) {
/* 1524 */         return ((Integer)basicConstraintsExtension.get("path_len"))
/* 1525 */           .intValue();
/*      */       }
/* 1527 */       return -1;
/* 1528 */     } catch (Exception exception) {
/* 1529 */       return -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Collection<List<?>> makeAltNames(GeneralNames paramGeneralNames) {
/* 1543 */     if (paramGeneralNames.isEmpty()) {
/* 1544 */       return Collections.emptySet();
/*      */     }
/* 1546 */     ArrayList<? extends List<?>> arrayList = new ArrayList();
/* 1547 */     for (GeneralName generalName : paramGeneralNames.names()) {
/* 1548 */       DerOutputStream derOutputStream; GeneralNameInterface generalNameInterface = generalName.getName();
/* 1549 */       ArrayList<Integer> arrayList1 = new ArrayList(2);
/* 1550 */       arrayList1.add(Integer.valueOf(generalNameInterface.getType()));
/* 1551 */       switch (generalNameInterface.getType()) {
/*      */         case 1:
/* 1553 */           arrayList1.add(((RFC822Name)generalNameInterface).getName());
/*      */           break;
/*      */         case 2:
/* 1556 */           arrayList1.add(((DNSName)generalNameInterface).getName());
/*      */           break;
/*      */         case 4:
/* 1559 */           arrayList1.add(((X500Name)generalNameInterface).getRFC2253Name());
/*      */           break;
/*      */         case 6:
/* 1562 */           arrayList1.add(((URIName)generalNameInterface).getName());
/*      */           break;
/*      */         case 7:
/*      */           try {
/* 1566 */             arrayList1.add(((IPAddressName)generalNameInterface).getName());
/* 1567 */           } catch (IOException iOException) {
/*      */             
/* 1569 */             throw new RuntimeException("IPAddress cannot be parsed", iOException);
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 8:
/* 1574 */           arrayList1.add(((OIDName)generalNameInterface).getOID().toString());
/*      */           break;
/*      */         
/*      */         default:
/* 1578 */           derOutputStream = new DerOutputStream();
/*      */           try {
/* 1580 */             generalNameInterface.encode(derOutputStream);
/* 1581 */           } catch (IOException iOException) {
/*      */ 
/*      */             
/* 1584 */             throw new RuntimeException("name cannot be encoded", iOException);
/*      */           } 
/* 1586 */           arrayList1.add(derOutputStream.toByteArray());
/*      */           break;
/*      */       } 
/* 1589 */       arrayList.add(Collections.unmodifiableList(arrayList1));
/*      */     } 
/* 1591 */     return Collections.unmodifiableCollection(arrayList);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Collection<List<?>> cloneAltNames(Collection<List<?>> paramCollection) {
/* 1599 */     boolean bool = false;
/* 1600 */     for (List<?> list : paramCollection) {
/* 1601 */       if (list.get(1) instanceof byte[])
/*      */       {
/* 1603 */         bool = true;
/*      */       }
/*      */     } 
/* 1606 */     if (bool) {
/* 1607 */       ArrayList<List<Object>> arrayList = new ArrayList();
/* 1608 */       for (List<Object> list : paramCollection) {
/* 1609 */         byte[] arrayOfByte = (byte[])list.get(1);
/* 1610 */         if (arrayOfByte instanceof byte[]) {
/* 1611 */           ArrayList<Object> arrayList1 = new ArrayList<>((Collection)list);
/*      */           
/* 1613 */           arrayList1.set(1, ((byte[])arrayOfByte).clone());
/* 1614 */           arrayList.add(Collections.unmodifiableList((List)arrayList1)); continue;
/*      */         } 
/* 1616 */         arrayList.add(list);
/*      */       } 
/*      */       
/* 1619 */       return Collections.unmodifiableCollection(arrayList);
/*      */     } 
/* 1621 */     return paramCollection;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Collection<List<?>> getSubjectAlternativeNames() throws CertificateParsingException {
/*      */     GeneralNames generalNames;
/* 1634 */     if (this.readOnly && this.subjectAlternativeNames != null) {
/* 1635 */       return cloneAltNames(this.subjectAlternativeNames);
/*      */     }
/*      */     
/* 1638 */     SubjectAlternativeNameExtension subjectAlternativeNameExtension = getSubjectAlternativeNameExtension();
/* 1639 */     if (subjectAlternativeNameExtension == null) {
/* 1640 */       return null;
/*      */     }
/*      */     
/*      */     try {
/* 1644 */       generalNames = subjectAlternativeNameExtension.get("subject_name");
/*      */     }
/* 1646 */     catch (IOException iOException) {
/*      */       
/* 1648 */       return Collections.emptySet();
/*      */     } 
/* 1650 */     this.subjectAlternativeNames = makeAltNames(generalNames);
/* 1651 */     return this.subjectAlternativeNames;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Collection<List<?>> getSubjectAlternativeNames(X509Certificate paramX509Certificate) throws CertificateParsingException {
/*      */     try {
/*      */       GeneralNames generalNames;
/* 1663 */       byte[] arrayOfByte1 = paramX509Certificate.getExtensionValue("2.5.29.17");
/* 1664 */       if (arrayOfByte1 == null) {
/* 1665 */         return null;
/*      */       }
/* 1667 */       DerValue derValue = new DerValue(arrayOfByte1);
/* 1668 */       byte[] arrayOfByte2 = derValue.getOctetString();
/*      */       
/* 1670 */       SubjectAlternativeNameExtension subjectAlternativeNameExtension = new SubjectAlternativeNameExtension(Boolean.FALSE, arrayOfByte2);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1676 */         generalNames = subjectAlternativeNameExtension.get("subject_name");
/*      */       }
/* 1678 */       catch (IOException iOException) {
/*      */         
/* 1680 */         return Collections.emptySet();
/*      */       } 
/* 1682 */       return makeAltNames(generalNames);
/* 1683 */     } catch (IOException iOException) {
/* 1684 */       throw new CertificateParsingException(iOException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Collection<List<?>> getIssuerAlternativeNames() throws CertificateParsingException {
/*      */     GeneralNames generalNames;
/* 1697 */     if (this.readOnly && this.issuerAlternativeNames != null) {
/* 1698 */       return cloneAltNames(this.issuerAlternativeNames);
/*      */     }
/*      */     
/* 1701 */     IssuerAlternativeNameExtension issuerAlternativeNameExtension = getIssuerAlternativeNameExtension();
/* 1702 */     if (issuerAlternativeNameExtension == null) {
/* 1703 */       return null;
/*      */     }
/*      */     
/*      */     try {
/* 1707 */       generalNames = issuerAlternativeNameExtension.get("issuer_name");
/*      */     }
/* 1709 */     catch (IOException iOException) {
/*      */       
/* 1711 */       return Collections.emptySet();
/*      */     } 
/* 1713 */     this.issuerAlternativeNames = makeAltNames(generalNames);
/* 1714 */     return this.issuerAlternativeNames;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Collection<List<?>> getIssuerAlternativeNames(X509Certificate paramX509Certificate) throws CertificateParsingException {
/*      */     try {
/*      */       GeneralNames generalNames;
/* 1726 */       byte[] arrayOfByte1 = paramX509Certificate.getExtensionValue("2.5.29.18");
/* 1727 */       if (arrayOfByte1 == null) {
/* 1728 */         return null;
/*      */       }
/*      */       
/* 1731 */       DerValue derValue = new DerValue(arrayOfByte1);
/* 1732 */       byte[] arrayOfByte2 = derValue.getOctetString();
/*      */       
/* 1734 */       IssuerAlternativeNameExtension issuerAlternativeNameExtension = new IssuerAlternativeNameExtension(Boolean.FALSE, arrayOfByte2);
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1739 */         generalNames = issuerAlternativeNameExtension.get("issuer_name");
/*      */       }
/* 1741 */       catch (IOException iOException) {
/*      */         
/* 1743 */         return Collections.emptySet();
/*      */       } 
/* 1745 */       return makeAltNames(generalNames);
/* 1746 */     } catch (IOException iOException) {
/* 1747 */       throw new CertificateParsingException(iOException);
/*      */     } 
/*      */   }
/*      */   
/*      */   public AuthorityInfoAccessExtension getAuthorityInfoAccessExtension() {
/* 1752 */     return (AuthorityInfoAccessExtension)
/* 1753 */       getExtension(PKIXExtensions.AuthInfoAccess_Id);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void parse(DerValue paramDerValue) throws CertificateException, IOException {
/* 1771 */     if (this.readOnly) {
/* 1772 */       throw new CertificateParsingException("cannot over-write existing certificate");
/*      */     }
/*      */     
/* 1775 */     if (paramDerValue.data == null || paramDerValue.tag != 48) {
/* 1776 */       throw new CertificateParsingException("invalid DER-encoded certificate data");
/*      */     }
/*      */     
/* 1779 */     this.signedCert = paramDerValue.toByteArray();
/* 1780 */     DerValue[] arrayOfDerValue = new DerValue[3];
/*      */     
/* 1782 */     arrayOfDerValue[0] = paramDerValue.data.getDerValue();
/* 1783 */     arrayOfDerValue[1] = paramDerValue.data.getDerValue();
/* 1784 */     arrayOfDerValue[2] = paramDerValue.data.getDerValue();
/*      */     
/* 1786 */     if (paramDerValue.data.available() != 0) {
/* 1787 */       throw new CertificateParsingException("signed overrun, bytes = " + paramDerValue.data
/* 1788 */           .available());
/*      */     }
/* 1790 */     if ((arrayOfDerValue[0]).tag != 48) {
/* 1791 */       throw new CertificateParsingException("signed fields invalid");
/*      */     }
/*      */     
/* 1794 */     this.algId = AlgorithmId.parse(arrayOfDerValue[1]);
/* 1795 */     this.signature = arrayOfDerValue[2].getBitString();
/*      */     
/* 1797 */     if ((arrayOfDerValue[1]).data.available() != 0) {
/* 1798 */       throw new CertificateParsingException("algid field overrun");
/*      */     }
/* 1800 */     if ((arrayOfDerValue[2]).data.available() != 0) {
/* 1801 */       throw new CertificateParsingException("signed fields overrun");
/*      */     }
/*      */     
/* 1804 */     this.info = new X509CertInfo(arrayOfDerValue[0]);
/*      */ 
/*      */     
/* 1807 */     AlgorithmId algorithmId = (AlgorithmId)this.info.get("algorithmID.algorithm");
/*      */ 
/*      */ 
/*      */     
/* 1811 */     if (!this.algId.equals(algorithmId))
/* 1812 */       throw new CertificateException("Signature algorithm mismatch"); 
/* 1813 */     this.readOnly = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static X500Principal getX500Principal(X509Certificate paramX509Certificate, boolean paramBoolean) throws Exception {
/* 1823 */     byte[] arrayOfByte1 = paramX509Certificate.getEncoded();
/* 1824 */     DerInputStream derInputStream1 = new DerInputStream(arrayOfByte1);
/* 1825 */     DerValue derValue1 = derInputStream1.getSequence(3)[0];
/* 1826 */     DerInputStream derInputStream2 = derValue1.data;
/*      */     
/* 1828 */     DerValue derValue2 = derInputStream2.getDerValue();
/*      */     
/* 1830 */     if (derValue2.isContextSpecific((byte)0)) {
/* 1831 */       derValue2 = derInputStream2.getDerValue();
/*      */     }
/*      */     
/* 1834 */     derValue2 = derInputStream2.getDerValue();
/* 1835 */     derValue2 = derInputStream2.getDerValue();
/* 1836 */     if (!paramBoolean) {
/* 1837 */       derValue2 = derInputStream2.getDerValue();
/* 1838 */       derValue2 = derInputStream2.getDerValue();
/*      */     } 
/* 1840 */     byte[] arrayOfByte2 = derValue2.toByteArray();
/* 1841 */     return new X500Principal(arrayOfByte2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static X500Principal getSubjectX500Principal(X509Certificate paramX509Certificate) {
/*      */     try {
/* 1850 */       return getX500Principal(paramX509Certificate, false);
/* 1851 */     } catch (Exception exception) {
/* 1852 */       throw new RuntimeException("Could not parse subject", exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static X500Principal getIssuerX500Principal(X509Certificate paramX509Certificate) {
/*      */     try {
/* 1862 */       return getX500Principal(paramX509Certificate, true);
/* 1863 */     } catch (Exception exception) {
/* 1864 */       throw new RuntimeException("Could not parse issuer", exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] getEncodedInternal(Certificate paramCertificate) throws CertificateEncodingException {
/* 1876 */     if (paramCertificate instanceof X509CertImpl) {
/* 1877 */       return ((X509CertImpl)paramCertificate).getEncodedInternal();
/*      */     }
/* 1879 */     return paramCertificate.getEncoded();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static X509CertImpl toImpl(X509Certificate paramX509Certificate) throws CertificateException {
/* 1890 */     if (paramX509Certificate instanceof X509CertImpl) {
/* 1891 */       return (X509CertImpl)paramX509Certificate;
/*      */     }
/* 1893 */     return X509Factory.intern(paramX509Certificate);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSelfIssued(X509Certificate paramX509Certificate) {
/* 1902 */     X500Principal x500Principal1 = paramX509Certificate.getSubjectX500Principal();
/* 1903 */     X500Principal x500Principal2 = paramX509Certificate.getIssuerX500Principal();
/* 1904 */     return x500Principal1.equals(x500Principal2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSelfSigned(X509Certificate paramX509Certificate, String paramString) {
/* 1915 */     if (isSelfIssued(paramX509Certificate)) {
/*      */       try {
/* 1917 */         if (paramString == null) {
/* 1918 */           paramX509Certificate.verify(paramX509Certificate.getPublicKey());
/*      */         } else {
/* 1920 */           paramX509Certificate.verify(paramX509Certificate.getPublicKey(), paramString);
/*      */         } 
/* 1922 */         return true;
/* 1923 */       } catch (Exception exception) {}
/*      */     }
/*      */ 
/*      */     
/* 1927 */     return false;
/*      */   }
/*      */   
/* 1930 */   public X509CertImpl() { this.fingerprints = new ConcurrentHashMap<>(2); } public X509CertImpl(byte[] paramArrayOfbyte) throws CertificateException { this.fingerprints = new ConcurrentHashMap<>(2); try { parse(new DerValue(paramArrayOfbyte)); } catch (IOException iOException) { this.signedCert = null; throw new CertificateException("Unable to initialize, " + iOException, iOException); }  } public X509CertImpl(InputStream paramInputStream) throws CertificateException { this.fingerprints = new ConcurrentHashMap<>(2); DerValue derValue = null; BufferedInputStream bufferedInputStream = new BufferedInputStream(paramInputStream); try { bufferedInputStream.mark(2147483647); derValue = readRFC1421Cert(bufferedInputStream); } catch (IOException iOException) { try { bufferedInputStream.reset(); derValue = new DerValue(bufferedInputStream); } catch (IOException iOException1) { throw new CertificateException("Input stream must be either DER-encoded bytes or RFC1421 hex-encoded DER-encoded bytes: " + iOException1.getMessage(), iOException1); }  }  try { parse(derValue); } catch (IOException iOException) { this.signedCert = null; throw new CertificateException("Unable to parse DER value of certificate, " + iOException, iOException); }  } public X509CertImpl(X509CertInfo paramX509CertInfo) { this.fingerprints = new ConcurrentHashMap<>(2); this.info = paramX509CertInfo; } public X509CertImpl(DerValue paramDerValue) throws CertificateException { this.fingerprints = new ConcurrentHashMap<>(2); try {
/*      */       parse(paramDerValue);
/*      */     } catch (IOException iOException) {
/*      */       this.signedCert = null; throw new CertificateException("Unable to initialize, " + iOException, iOException);
/* 1934 */     }  } public String getFingerprint(String paramString) { return this.fingerprints.computeIfAbsent(paramString, paramString -> getFingerprint(paramString, this)); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getFingerprint(String paramString, X509Certificate paramX509Certificate) {
/* 1944 */     String str = "";
/*      */     try {
/* 1946 */       byte[] arrayOfByte1 = paramX509Certificate.getEncoded();
/* 1947 */       MessageDigest messageDigest = MessageDigest.getInstance(paramString);
/* 1948 */       byte[] arrayOfByte2 = messageDigest.digest(arrayOfByte1);
/* 1949 */       StringBuffer stringBuffer = new StringBuffer();
/* 1950 */       for (byte b = 0; b < arrayOfByte2.length; b++) {
/* 1951 */         byte2hex(arrayOfByte2[b], stringBuffer);
/*      */       }
/* 1953 */       str = stringBuffer.toString();
/* 1954 */     } catch (NoSuchAlgorithmException|CertificateEncodingException noSuchAlgorithmException) {}
/*      */ 
/*      */     
/* 1957 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void byte2hex(byte paramByte, StringBuffer paramStringBuffer) {
/* 1964 */     char[] arrayOfChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/*      */     
/* 1966 */     int i = (paramByte & 0xF0) >> 4;
/* 1967 */     int j = paramByte & 0xF;
/* 1968 */     paramStringBuffer.append(arrayOfChar[i]);
/* 1969 */     paramStringBuffer.append(arrayOfChar[j]);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/X509CertImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */