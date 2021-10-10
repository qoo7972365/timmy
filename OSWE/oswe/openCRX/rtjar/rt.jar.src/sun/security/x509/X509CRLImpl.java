/*      */ package sun.security.x509;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.math.BigInteger;
/*      */ import java.security.InvalidKeyException;
/*      */ import java.security.NoSuchAlgorithmException;
/*      */ import java.security.NoSuchProviderException;
/*      */ import java.security.Principal;
/*      */ import java.security.PrivateKey;
/*      */ import java.security.Provider;
/*      */ import java.security.PublicKey;
/*      */ import java.security.Signature;
/*      */ import java.security.SignatureException;
/*      */ import java.security.cert.CRLException;
/*      */ import java.security.cert.Certificate;
/*      */ import java.security.cert.X509CRL;
/*      */ import java.security.cert.X509CRLEntry;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.TreeMap;
/*      */ import java.util.TreeSet;
/*      */ import javax.security.auth.x500.X500Principal;
/*      */ import sun.misc.HexDumpEncoder;
/*      */ import sun.security.provider.X509Factory;
/*      */ import sun.security.util.DerEncoder;
/*      */ import sun.security.util.DerInputStream;
/*      */ import sun.security.util.DerOutputStream;
/*      */ import sun.security.util.DerValue;
/*      */ import sun.security.util.ObjectIdentifier;
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
/*      */ public class X509CRLImpl
/*      */   extends X509CRL
/*      */   implements DerEncoder
/*      */ {
/*   95 */   private byte[] signedCRL = null;
/*   96 */   private byte[] signature = null;
/*   97 */   private byte[] tbsCertList = null;
/*   98 */   private AlgorithmId sigAlgId = null;
/*      */   
/*      */   private int version;
/*      */   
/*      */   private AlgorithmId infoSigAlgId;
/*  103 */   private X500Name issuer = null;
/*  104 */   private X500Principal issuerPrincipal = null;
/*  105 */   private Date thisUpdate = null;
/*  106 */   private Date nextUpdate = null;
/*  107 */   private Map<X509IssuerSerial, X509CRLEntry> revokedMap = new TreeMap<>();
/*  108 */   private List<X509CRLEntry> revokedList = new LinkedList<>();
/*  109 */   private CRLExtensions extensions = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean isExplicit = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long YR_2050 = 2524636800000L;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean readOnly = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PublicKey verifiedPublicKey;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String verifiedProvider;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private X509CRLImpl() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X509CRLImpl(byte[] paramArrayOfbyte) throws CRLException {
/*      */     try {
/*  146 */       parse(new DerValue(paramArrayOfbyte));
/*  147 */     } catch (IOException iOException) {
/*  148 */       this.signedCRL = null;
/*  149 */       throw new CRLException("Parsing error: " + iOException.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X509CRLImpl(DerValue paramDerValue) throws CRLException {
/*      */     try {
/*  161 */       parse(paramDerValue);
/*  162 */     } catch (IOException iOException) {
/*  163 */       this.signedCRL = null;
/*  164 */       throw new CRLException("Parsing error: " + iOException.getMessage());
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
/*      */   public X509CRLImpl(InputStream paramInputStream) throws CRLException {
/*      */     try {
/*  177 */       parse(new DerValue(paramInputStream));
/*  178 */     } catch (IOException iOException) {
/*  179 */       this.signedCRL = null;
/*  180 */       throw new CRLException("Parsing error: " + iOException.getMessage());
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
/*      */   public X509CRLImpl(X500Name paramX500Name, Date paramDate1, Date paramDate2) {
/*  192 */     this.issuer = paramX500Name;
/*  193 */     this.thisUpdate = paramDate1;
/*  194 */     this.nextUpdate = paramDate2;
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
/*      */   public X509CRLImpl(X500Name paramX500Name, Date paramDate1, Date paramDate2, X509CRLEntry[] paramArrayOfX509CRLEntry) throws CRLException {
/*  211 */     this.issuer = paramX500Name;
/*  212 */     this.thisUpdate = paramDate1;
/*  213 */     this.nextUpdate = paramDate2;
/*  214 */     if (paramArrayOfX509CRLEntry != null) {
/*  215 */       X500Principal x500Principal1 = getIssuerX500Principal();
/*  216 */       X500Principal x500Principal2 = x500Principal1;
/*  217 */       for (byte b = 0; b < paramArrayOfX509CRLEntry.length; b++) {
/*  218 */         X509CRLEntryImpl x509CRLEntryImpl = (X509CRLEntryImpl)paramArrayOfX509CRLEntry[b];
/*      */         try {
/*  220 */           x500Principal2 = getCertIssuer(x509CRLEntryImpl, x500Principal2);
/*  221 */         } catch (IOException iOException) {
/*  222 */           throw new CRLException(iOException);
/*      */         } 
/*  224 */         x509CRLEntryImpl.setCertificateIssuer(x500Principal1, x500Principal2);
/*      */         
/*  226 */         X509IssuerSerial x509IssuerSerial = new X509IssuerSerial(x500Principal2, x509CRLEntryImpl.getSerialNumber());
/*  227 */         this.revokedMap.put(x509IssuerSerial, x509CRLEntryImpl);
/*  228 */         this.revokedList.add(x509CRLEntryImpl);
/*  229 */         if (x509CRLEntryImpl.hasExtensions()) {
/*  230 */           this.version = 1;
/*      */         }
/*      */       } 
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
/*      */   public X509CRLImpl(X500Name paramX500Name, Date paramDate1, Date paramDate2, X509CRLEntry[] paramArrayOfX509CRLEntry, CRLExtensions paramCRLExtensions) throws CRLException {
/*  251 */     this(paramX500Name, paramDate1, paramDate2, paramArrayOfX509CRLEntry);
/*  252 */     if (paramCRLExtensions != null) {
/*  253 */       this.extensions = paramCRLExtensions;
/*  254 */       this.version = 1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getEncodedInternal() throws CRLException {
/*  264 */     if (this.signedCRL == null) {
/*  265 */       throw new CRLException("Null CRL to encode");
/*      */     }
/*  267 */     return this.signedCRL;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getEncoded() throws CRLException {
/*  276 */     return (byte[])getEncodedInternal().clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void encodeInfo(OutputStream paramOutputStream) throws CRLException {
/*      */     try {
/*  287 */       DerOutputStream derOutputStream1 = new DerOutputStream();
/*  288 */       DerOutputStream derOutputStream2 = new DerOutputStream();
/*  289 */       DerOutputStream derOutputStream3 = new DerOutputStream();
/*      */       
/*  291 */       if (this.version != 0)
/*  292 */         derOutputStream1.putInteger(this.version); 
/*  293 */       this.infoSigAlgId.encode(derOutputStream1);
/*  294 */       if (this.version == 0 && this.issuer.toString() == null)
/*  295 */         throw new CRLException("Null Issuer DN not allowed in v1 CRL"); 
/*  296 */       this.issuer.encode(derOutputStream1);
/*      */       
/*  298 */       if (this.thisUpdate.getTime() < 2524636800000L) {
/*  299 */         derOutputStream1.putUTCTime(this.thisUpdate);
/*      */       } else {
/*  301 */         derOutputStream1.putGeneralizedTime(this.thisUpdate);
/*      */       } 
/*  303 */       if (this.nextUpdate != null) {
/*  304 */         if (this.nextUpdate.getTime() < 2524636800000L) {
/*  305 */           derOutputStream1.putUTCTime(this.nextUpdate);
/*      */         } else {
/*  307 */           derOutputStream1.putGeneralizedTime(this.nextUpdate);
/*      */         } 
/*      */       }
/*  310 */       if (!this.revokedList.isEmpty()) {
/*  311 */         for (X509CRLEntry x509CRLEntry : this.revokedList) {
/*  312 */           ((X509CRLEntryImpl)x509CRLEntry).encode(derOutputStream2);
/*      */         }
/*  314 */         derOutputStream1.write((byte)48, derOutputStream2);
/*      */       } 
/*      */       
/*  317 */       if (this.extensions != null) {
/*  318 */         this.extensions.encode(derOutputStream1, true);
/*      */       }
/*  320 */       derOutputStream3.write((byte)48, derOutputStream1);
/*      */       
/*  322 */       this.tbsCertList = derOutputStream3.toByteArray();
/*  323 */       paramOutputStream.write(this.tbsCertList);
/*  324 */     } catch (IOException iOException) {
/*  325 */       throw new CRLException("Encoding error: " + iOException.getMessage());
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
/*      */   public void verify(PublicKey paramPublicKey) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
/*  345 */     verify(paramPublicKey, "");
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
/*      */   public synchronized void verify(PublicKey paramPublicKey, String paramString) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
/*  368 */     if (paramString == null) {
/*  369 */       paramString = "";
/*      */     }
/*  371 */     if (this.verifiedPublicKey != null && this.verifiedPublicKey.equals(paramPublicKey))
/*      */     {
/*      */       
/*  374 */       if (paramString.equals(this.verifiedProvider)) {
/*      */         return;
/*      */       }
/*      */     }
/*  378 */     if (this.signedCRL == null) {
/*  379 */       throw new CRLException("Uninitialized CRL");
/*      */     }
/*  381 */     Signature signature = null;
/*  382 */     if (paramString.length() == 0) {
/*  383 */       signature = Signature.getInstance(this.sigAlgId.getName());
/*      */     } else {
/*  385 */       signature = Signature.getInstance(this.sigAlgId.getName(), paramString);
/*      */     } 
/*  387 */     signature.initVerify(paramPublicKey);
/*      */     
/*  389 */     if (this.tbsCertList == null) {
/*  390 */       throw new CRLException("Uninitialized CRL");
/*      */     }
/*      */     
/*  393 */     signature.update(this.tbsCertList, 0, this.tbsCertList.length);
/*      */     
/*  395 */     if (!signature.verify(this.signature)) {
/*  396 */       throw new SignatureException("Signature does not match.");
/*      */     }
/*  398 */     this.verifiedPublicKey = paramPublicKey;
/*  399 */     this.verifiedProvider = paramString;
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
/*      */   public synchronized void verify(PublicKey paramPublicKey, Provider paramProvider) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
/*  422 */     if (this.signedCRL == null) {
/*  423 */       throw new CRLException("Uninitialized CRL");
/*      */     }
/*  425 */     Signature signature = null;
/*  426 */     if (paramProvider == null) {
/*  427 */       signature = Signature.getInstance(this.sigAlgId.getName());
/*      */     } else {
/*  429 */       signature = Signature.getInstance(this.sigAlgId.getName(), paramProvider);
/*      */     } 
/*  431 */     signature.initVerify(paramPublicKey);
/*      */     
/*  433 */     if (this.tbsCertList == null) {
/*  434 */       throw new CRLException("Uninitialized CRL");
/*      */     }
/*      */     
/*  437 */     signature.update(this.tbsCertList, 0, this.tbsCertList.length);
/*      */     
/*  439 */     if (!signature.verify(this.signature)) {
/*  440 */       throw new SignatureException("Signature does not match.");
/*      */     }
/*  442 */     this.verifiedPublicKey = paramPublicKey;
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
/*      */   public static void verify(X509CRL paramX509CRL, PublicKey paramPublicKey, Provider paramProvider) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
/*  454 */     paramX509CRL.verify(paramPublicKey, paramProvider);
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
/*      */   public void sign(PrivateKey paramPrivateKey, String paramString) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
/*  473 */     sign(paramPrivateKey, paramString, null);
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
/*      */   public void sign(PrivateKey paramPrivateKey, String paramString1, String paramString2) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
/*      */     try {
/*  494 */       if (this.readOnly)
/*  495 */         throw new CRLException("cannot over-write existing CRL"); 
/*  496 */       Signature signature = null;
/*  497 */       if (paramString2 == null || paramString2.length() == 0) {
/*  498 */         signature = Signature.getInstance(paramString1);
/*      */       } else {
/*  500 */         signature = Signature.getInstance(paramString1, paramString2);
/*      */       } 
/*  502 */       signature.initSign(paramPrivateKey);
/*      */ 
/*      */       
/*  505 */       this.sigAlgId = AlgorithmId.get(signature.getAlgorithm());
/*  506 */       this.infoSigAlgId = this.sigAlgId;
/*      */       
/*  508 */       DerOutputStream derOutputStream1 = new DerOutputStream();
/*  509 */       DerOutputStream derOutputStream2 = new DerOutputStream();
/*      */ 
/*      */       
/*  512 */       encodeInfo(derOutputStream2);
/*      */ 
/*      */       
/*  515 */       this.sigAlgId.encode(derOutputStream2);
/*      */ 
/*      */       
/*  518 */       signature.update(this.tbsCertList, 0, this.tbsCertList.length);
/*  519 */       this.signature = signature.sign();
/*  520 */       derOutputStream2.putBitString(this.signature);
/*      */ 
/*      */       
/*  523 */       derOutputStream1.write((byte)48, derOutputStream2);
/*  524 */       this.signedCRL = derOutputStream1.toByteArray();
/*  525 */       this.readOnly = true;
/*      */     }
/*  527 */     catch (IOException iOException) {
/*  528 */       throw new CRLException("Error while encoding data: " + iOException
/*  529 */           .getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  539 */     return toStringWithAlgName("" + this.sigAlgId);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toStringWithAlgName(String paramString) {
/*  544 */     StringBuffer stringBuffer = new StringBuffer();
/*  545 */     stringBuffer.append("X.509 CRL v" + (this.version + 1) + "\n");
/*  546 */     if (this.sigAlgId != null)
/*  547 */       stringBuffer.append("Signature Algorithm: " + paramString.toString() + ", OID=" + this.sigAlgId
/*  548 */           .getOID().toString() + "\n"); 
/*  549 */     if (this.issuer != null)
/*  550 */       stringBuffer.append("Issuer: " + this.issuer.toString() + "\n"); 
/*  551 */     if (this.thisUpdate != null)
/*  552 */       stringBuffer.append("\nThis Update: " + this.thisUpdate.toString() + "\n"); 
/*  553 */     if (this.nextUpdate != null)
/*  554 */       stringBuffer.append("Next Update: " + this.nextUpdate.toString() + "\n"); 
/*  555 */     if (this.revokedList.isEmpty()) {
/*  556 */       stringBuffer.append("\nNO certificates have been revoked\n");
/*      */     } else {
/*  558 */       stringBuffer.append("\nRevoked Certificates: " + this.revokedList.size());
/*  559 */       byte b = 1;
/*  560 */       for (X509CRLEntry x509CRLEntry : this.revokedList) {
/*  561 */         stringBuffer.append("\n[" + b++ + "] " + x509CRLEntry.toString());
/*      */       }
/*      */     } 
/*  564 */     if (this.extensions != null) {
/*  565 */       Collection<Extension> collection = this.extensions.getAllExtensions();
/*  566 */       Object[] arrayOfObject = collection.toArray();
/*  567 */       stringBuffer.append("\nCRL Extensions: " + arrayOfObject.length);
/*  568 */       for (byte b = 0; b < arrayOfObject.length; b++) {
/*  569 */         stringBuffer.append("\n[" + (b + 1) + "]: ");
/*  570 */         Extension extension = (Extension)arrayOfObject[b];
/*      */         try {
/*  572 */           if (OIDMap.getClass(extension.getExtensionId()) == null)
/*  573 */           { stringBuffer.append(extension.toString());
/*  574 */             byte[] arrayOfByte = extension.getExtensionValue();
/*  575 */             if (arrayOfByte != null) {
/*  576 */               DerOutputStream derOutputStream = new DerOutputStream();
/*  577 */               derOutputStream.putOctetString(arrayOfByte);
/*  578 */               arrayOfByte = derOutputStream.toByteArray();
/*  579 */               HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
/*  580 */               stringBuffer.append("Extension unknown: DER encoded OCTET string =\n" + hexDumpEncoder
/*      */                   
/*  582 */                   .encodeBuffer(arrayOfByte) + "\n");
/*      */             }  }
/*      */           else
/*  585 */           { stringBuffer.append(extension.toString()); } 
/*  586 */         } catch (Exception exception) {
/*  587 */           stringBuffer.append(", Error parsing this extension");
/*      */         } 
/*      */       } 
/*      */     } 
/*  591 */     if (this.signature != null) {
/*  592 */       HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
/*  593 */       stringBuffer.append("\nSignature:\n" + hexDumpEncoder.encodeBuffer(this.signature) + "\n");
/*      */     } else {
/*      */       
/*  596 */       stringBuffer.append("NOT signed yet\n");
/*  597 */     }  return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRevoked(Certificate paramCertificate) {
/*  608 */     if (this.revokedMap.isEmpty() || !(paramCertificate instanceof X509Certificate)) {
/*  609 */       return false;
/*      */     }
/*  611 */     X509Certificate x509Certificate = (X509Certificate)paramCertificate;
/*  612 */     X509IssuerSerial x509IssuerSerial = new X509IssuerSerial(x509Certificate);
/*  613 */     return this.revokedMap.containsKey(x509IssuerSerial);
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
/*      */   public int getVersion() {
/*  627 */     return this.version + 1;
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
/*      */   public Principal getIssuerDN() {
/*  659 */     return this.issuer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X500Principal getIssuerX500Principal() {
/*  667 */     if (this.issuerPrincipal == null) {
/*  668 */       this.issuerPrincipal = this.issuer.asX500Principal();
/*      */     }
/*  670 */     return this.issuerPrincipal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Date getThisUpdate() {
/*  680 */     return new Date(this.thisUpdate.getTime());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Date getNextUpdate() {
/*  690 */     if (this.nextUpdate == null)
/*  691 */       return null; 
/*  692 */     return new Date(this.nextUpdate.getTime());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X509CRLEntry getRevokedCertificate(BigInteger paramBigInteger) {
/*  703 */     if (this.revokedMap.isEmpty()) {
/*  704 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  708 */     X509IssuerSerial x509IssuerSerial = new X509IssuerSerial(getIssuerX500Principal(), paramBigInteger);
/*  709 */     return this.revokedMap.get(x509IssuerSerial);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X509CRLEntry getRevokedCertificate(X509Certificate paramX509Certificate) {
/*  716 */     if (this.revokedMap.isEmpty()) {
/*  717 */       return null;
/*      */     }
/*  719 */     X509IssuerSerial x509IssuerSerial = new X509IssuerSerial(paramX509Certificate);
/*  720 */     return this.revokedMap.get(x509IssuerSerial);
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
/*      */   public Set<X509CRLEntry> getRevokedCertificates() {
/*  732 */     if (this.revokedList.isEmpty()) {
/*  733 */       return null;
/*      */     }
/*  735 */     return new TreeSet<>(this.revokedList);
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
/*      */   public byte[] getTBSCertList() throws CRLException {
/*  748 */     if (this.tbsCertList == null)
/*  749 */       throw new CRLException("Uninitialized CRL"); 
/*  750 */     return (byte[])this.tbsCertList.clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getSignature() {
/*  759 */     if (this.signature == null)
/*  760 */       return null; 
/*  761 */     return (byte[])this.signature.clone();
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
/*      */   public String getSigAlgName() {
/*  780 */     if (this.sigAlgId == null)
/*  781 */       return null; 
/*  782 */     return this.sigAlgId.getName();
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
/*      */   public String getSigAlgOID() {
/*  799 */     if (this.sigAlgId == null)
/*  800 */       return null; 
/*  801 */     ObjectIdentifier objectIdentifier = this.sigAlgId.getOID();
/*  802 */     return objectIdentifier.toString();
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
/*      */   public byte[] getSigAlgParams() {
/*  815 */     if (this.sigAlgId == null)
/*  816 */       return null; 
/*      */     try {
/*  818 */       return this.sigAlgId.getEncodedParams();
/*  819 */     } catch (IOException iOException) {
/*  820 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AlgorithmId getSigAlgId() {
/*  830 */     return this.sigAlgId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyIdentifier getAuthKeyId() throws IOException {
/*  841 */     AuthorityKeyIdentifierExtension authorityKeyIdentifierExtension = getAuthKeyIdExtension();
/*  842 */     if (authorityKeyIdentifierExtension != null) {
/*  843 */       return (KeyIdentifier)authorityKeyIdentifierExtension.get("key_id");
/*      */     }
/*      */ 
/*      */     
/*  847 */     return null;
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
/*      */   public AuthorityKeyIdentifierExtension getAuthKeyIdExtension() throws IOException {
/*  859 */     Object object = getExtension(PKIXExtensions.AuthorityKey_Id);
/*  860 */     return (AuthorityKeyIdentifierExtension)object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CRLNumberExtension getCRLNumberExtension() throws IOException {
/*  870 */     Object object = getExtension(PKIXExtensions.CRLNumber_Id);
/*  871 */     return (CRLNumberExtension)object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigInteger getCRLNumber() throws IOException {
/*  881 */     CRLNumberExtension cRLNumberExtension = getCRLNumberExtension();
/*  882 */     if (cRLNumberExtension != null) {
/*  883 */       return cRLNumberExtension.get("value");
/*      */     }
/*      */     
/*  886 */     return null;
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
/*      */   public DeltaCRLIndicatorExtension getDeltaCRLIndicatorExtension() throws IOException {
/*  899 */     Object object = getExtension(PKIXExtensions.DeltaCRLIndicator_Id);
/*  900 */     return (DeltaCRLIndicatorExtension)object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigInteger getBaseCRLNumber() throws IOException {
/*  910 */     DeltaCRLIndicatorExtension deltaCRLIndicatorExtension = getDeltaCRLIndicatorExtension();
/*  911 */     if (deltaCRLIndicatorExtension != null) {
/*  912 */       return deltaCRLIndicatorExtension.get("value");
/*      */     }
/*      */     
/*  915 */     return null;
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
/*      */   public IssuerAlternativeNameExtension getIssuerAltNameExtension() throws IOException {
/*  927 */     Object object = getExtension(PKIXExtensions.IssuerAlternativeName_Id);
/*  928 */     return (IssuerAlternativeNameExtension)object;
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
/*      */   public IssuingDistributionPointExtension getIssuingDistributionPointExtension() throws IOException {
/*  941 */     Object object = getExtension(PKIXExtensions.IssuingDistributionPoint_Id);
/*  942 */     return (IssuingDistributionPointExtension)object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasUnsupportedCriticalExtension() {
/*  950 */     if (this.extensions == null)
/*  951 */       return false; 
/*  952 */     return this.extensions.hasUnsupportedCriticalExtension();
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
/*  964 */     if (this.extensions == null) {
/*  965 */       return null;
/*      */     }
/*  967 */     TreeSet<String> treeSet = new TreeSet();
/*  968 */     for (Extension extension : this.extensions.getAllExtensions()) {
/*  969 */       if (extension.isCritical()) {
/*  970 */         treeSet.add(extension.getExtensionId().toString());
/*      */       }
/*      */     } 
/*  973 */     return treeSet;
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
/*  985 */     if (this.extensions == null) {
/*  986 */       return null;
/*      */     }
/*  988 */     TreeSet<String> treeSet = new TreeSet();
/*  989 */     for (Extension extension : this.extensions.getAllExtensions()) {
/*  990 */       if (!extension.isCritical()) {
/*  991 */         treeSet.add(extension.getExtensionId().toString());
/*      */       }
/*      */     } 
/*  994 */     return treeSet;
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
/*      */   public byte[] getExtensionValue(String paramString) {
/* 1009 */     if (this.extensions == null)
/* 1010 */       return null; 
/*      */     try {
/* 1012 */       String str = OIDMap.getName(new ObjectIdentifier(paramString));
/* 1013 */       Extension extension = null;
/*      */       
/* 1015 */       if (str == null) {
/* 1016 */         ObjectIdentifier objectIdentifier = new ObjectIdentifier(paramString);
/* 1017 */         Extension extension1 = null;
/*      */         
/* 1019 */         Enumeration<Extension> enumeration = this.extensions.getElements();
/* 1020 */         while (enumeration.hasMoreElements()) {
/* 1021 */           extension1 = enumeration.nextElement();
/* 1022 */           ObjectIdentifier objectIdentifier1 = extension1.getExtensionId();
/* 1023 */           if (objectIdentifier1.equals(objectIdentifier)) {
/* 1024 */             extension = extension1;
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } else {
/* 1029 */         extension = this.extensions.get(str);
/* 1030 */       }  if (extension == null)
/* 1031 */         return null; 
/* 1032 */       byte[] arrayOfByte = extension.getExtensionValue();
/* 1033 */       if (arrayOfByte == null)
/* 1034 */         return null; 
/* 1035 */       DerOutputStream derOutputStream = new DerOutputStream();
/* 1036 */       derOutputStream.putOctetString(arrayOfByte);
/* 1037 */       return derOutputStream.toByteArray();
/* 1038 */     } catch (Exception exception) {
/* 1039 */       return null;
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
/*      */   public Object getExtension(ObjectIdentifier paramObjectIdentifier) {
/* 1051 */     if (this.extensions == null) {
/* 1052 */       return null;
/*      */     }
/*      */     
/* 1055 */     return this.extensions.get(OIDMap.getName(paramObjectIdentifier));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void parse(DerValue paramDerValue) throws CRLException, IOException {
/* 1063 */     if (this.readOnly) {
/* 1064 */       throw new CRLException("cannot over-write existing CRL");
/*      */     }
/* 1066 */     if (paramDerValue.getData() == null || paramDerValue.tag != 48) {
/* 1067 */       throw new CRLException("Invalid DER-encoded CRL data");
/*      */     }
/* 1069 */     this.signedCRL = paramDerValue.toByteArray();
/* 1070 */     DerValue[] arrayOfDerValue = new DerValue[3];
/*      */     
/* 1072 */     arrayOfDerValue[0] = paramDerValue.data.getDerValue();
/* 1073 */     arrayOfDerValue[1] = paramDerValue.data.getDerValue();
/* 1074 */     arrayOfDerValue[2] = paramDerValue.data.getDerValue();
/*      */     
/* 1076 */     if (paramDerValue.data.available() != 0) {
/* 1077 */       throw new CRLException("signed overrun, bytes = " + paramDerValue.data
/* 1078 */           .available());
/*      */     }
/* 1080 */     if ((arrayOfDerValue[0]).tag != 48) {
/* 1081 */       throw new CRLException("signed CRL fields invalid");
/*      */     }
/* 1083 */     this.sigAlgId = AlgorithmId.parse(arrayOfDerValue[1]);
/* 1084 */     this.signature = arrayOfDerValue[2].getBitString();
/*      */     
/* 1086 */     if ((arrayOfDerValue[1]).data.available() != 0) {
/* 1087 */       throw new CRLException("AlgorithmId field overrun");
/*      */     }
/* 1089 */     if ((arrayOfDerValue[2]).data.available() != 0) {
/* 1090 */       throw new CRLException("Signature field overrun");
/*      */     }
/*      */     
/* 1093 */     this.tbsCertList = arrayOfDerValue[0].toByteArray();
/*      */ 
/*      */     
/* 1096 */     DerInputStream derInputStream = (arrayOfDerValue[0]).data;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1101 */     this.version = 0;
/* 1102 */     byte b = (byte)derInputStream.peekByte();
/* 1103 */     if (b == 2) {
/* 1104 */       this.version = derInputStream.getInteger();
/* 1105 */       if (this.version != 1)
/* 1106 */         throw new CRLException("Invalid version"); 
/*      */     } 
/* 1108 */     DerValue derValue = derInputStream.getDerValue();
/*      */ 
/*      */     
/* 1111 */     AlgorithmId algorithmId = AlgorithmId.parse(derValue);
/*      */ 
/*      */     
/* 1114 */     if (!algorithmId.equals(this.sigAlgId))
/* 1115 */       throw new CRLException("Signature algorithm mismatch"); 
/* 1116 */     this.infoSigAlgId = algorithmId;
/*      */ 
/*      */     
/* 1119 */     this.issuer = new X500Name(derInputStream);
/* 1120 */     if (this.issuer.isEmpty()) {
/* 1121 */       throw new CRLException("Empty issuer DN not allowed in X509CRLs");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1127 */     b = (byte)derInputStream.peekByte();
/* 1128 */     if (b == 23) {
/* 1129 */       this.thisUpdate = derInputStream.getUTCTime();
/* 1130 */     } else if (b == 24) {
/* 1131 */       this.thisUpdate = derInputStream.getGeneralizedTime();
/*      */     } else {
/* 1133 */       throw new CRLException("Invalid encoding for thisUpdate (tag=" + b + ")");
/*      */     } 
/*      */ 
/*      */     
/* 1137 */     if (derInputStream.available() == 0) {
/*      */       return;
/*      */     }
/*      */     
/* 1141 */     b = (byte)derInputStream.peekByte();
/* 1142 */     if (b == 23) {
/* 1143 */       this.nextUpdate = derInputStream.getUTCTime();
/* 1144 */     } else if (b == 24) {
/* 1145 */       this.nextUpdate = derInputStream.getGeneralizedTime();
/*      */     } 
/*      */     
/* 1148 */     if (derInputStream.available() == 0) {
/*      */       return;
/*      */     }
/*      */     
/* 1152 */     b = (byte)derInputStream.peekByte();
/* 1153 */     if (b == 48 && (b & 0xC0) != 128) {
/*      */       
/* 1155 */       DerValue[] arrayOfDerValue1 = derInputStream.getSequence(4);
/*      */       
/* 1157 */       X500Principal x500Principal1 = getIssuerX500Principal();
/* 1158 */       X500Principal x500Principal2 = x500Principal1;
/* 1159 */       for (byte b1 = 0; b1 < arrayOfDerValue1.length; b1++) {
/* 1160 */         X509CRLEntryImpl x509CRLEntryImpl = new X509CRLEntryImpl(arrayOfDerValue1[b1]);
/* 1161 */         x500Principal2 = getCertIssuer(x509CRLEntryImpl, x500Principal2);
/* 1162 */         x509CRLEntryImpl.setCertificateIssuer(x500Principal1, x500Principal2);
/*      */         
/* 1164 */         X509IssuerSerial x509IssuerSerial = new X509IssuerSerial(x500Principal2, x509CRLEntryImpl.getSerialNumber());
/* 1165 */         this.revokedMap.put(x509IssuerSerial, x509CRLEntryImpl);
/* 1166 */         this.revokedList.add(x509CRLEntryImpl);
/*      */       } 
/*      */     } 
/*      */     
/* 1170 */     if (derInputStream.available() == 0) {
/*      */       return;
/*      */     }
/*      */     
/* 1174 */     derValue = derInputStream.getDerValue();
/* 1175 */     if (derValue.isConstructed() && derValue.isContextSpecific((byte)0)) {
/* 1176 */       this.extensions = new CRLExtensions(derValue.data);
/*      */     }
/* 1178 */     this.readOnly = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static X500Principal getIssuerX500Principal(X509CRL paramX509CRL) {
/*      */     try {
/* 1189 */       byte[] arrayOfByte1 = paramX509CRL.getEncoded();
/* 1190 */       DerInputStream derInputStream1 = new DerInputStream(arrayOfByte1);
/* 1191 */       DerValue derValue1 = derInputStream1.getSequence(3)[0];
/* 1192 */       DerInputStream derInputStream2 = derValue1.data;
/*      */ 
/*      */ 
/*      */       
/* 1196 */       byte b = (byte)derInputStream2.peekByte();
/* 1197 */       if (b == 2) {
/* 1198 */         DerValue derValue = derInputStream2.getDerValue();
/*      */       }
/*      */       
/* 1201 */       DerValue derValue2 = derInputStream2.getDerValue();
/* 1202 */       derValue2 = derInputStream2.getDerValue();
/* 1203 */       byte[] arrayOfByte2 = derValue2.toByteArray();
/* 1204 */       return new X500Principal(arrayOfByte2);
/* 1205 */     } catch (Exception exception) {
/* 1206 */       throw new RuntimeException("Could not parse issuer", exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] getEncodedInternal(X509CRL paramX509CRL) throws CRLException {
/* 1217 */     if (paramX509CRL instanceof X509CRLImpl) {
/* 1218 */       return ((X509CRLImpl)paramX509CRL).getEncodedInternal();
/*      */     }
/* 1220 */     return paramX509CRL.getEncoded();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static X509CRLImpl toImpl(X509CRL paramX509CRL) throws CRLException {
/* 1231 */     if (paramX509CRL instanceof X509CRLImpl) {
/* 1232 */       return (X509CRLImpl)paramX509CRL;
/*      */     }
/* 1234 */     return X509Factory.intern(paramX509CRL);
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
/*      */   private X500Principal getCertIssuer(X509CRLEntryImpl paramX509CRLEntryImpl, X500Principal paramX500Principal) throws IOException {
/* 1250 */     CertificateIssuerExtension certificateIssuerExtension = paramX509CRLEntryImpl.getCertificateIssuerExtension();
/* 1251 */     if (certificateIssuerExtension != null) {
/* 1252 */       GeneralNames generalNames = certificateIssuerExtension.get("issuer");
/* 1253 */       X500Name x500Name = (X500Name)generalNames.get(0).getName();
/* 1254 */       return x500Name.asX500Principal();
/*      */     } 
/* 1256 */     return paramX500Principal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void derEncode(OutputStream paramOutputStream) throws IOException {
/* 1262 */     if (this.signedCRL == null)
/* 1263 */       throw new IOException("Null CRL to encode"); 
/* 1264 */     paramOutputStream.write((byte[])this.signedCRL.clone());
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class X509IssuerSerial
/*      */     implements Comparable<X509IssuerSerial>
/*      */   {
/*      */     final X500Principal issuer;
/*      */     
/*      */     final BigInteger serial;
/* 1274 */     volatile int hashcode = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     X509IssuerSerial(X500Principal param1X500Principal, BigInteger param1BigInteger) {
/* 1283 */       this.issuer = param1X500Principal;
/* 1284 */       this.serial = param1BigInteger;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     X509IssuerSerial(X509Certificate param1X509Certificate) {
/* 1291 */       this(param1X509Certificate.getIssuerX500Principal(), param1X509Certificate.getSerialNumber());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     X500Principal getIssuer() {
/* 1300 */       return this.issuer;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     BigInteger getSerial() {
/* 1309 */       return this.serial;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 1320 */       if (param1Object == this) {
/* 1321 */         return true;
/*      */       }
/*      */       
/* 1324 */       if (!(param1Object instanceof X509IssuerSerial)) {
/* 1325 */         return false;
/*      */       }
/*      */       
/* 1328 */       X509IssuerSerial x509IssuerSerial = (X509IssuerSerial)param1Object;
/* 1329 */       if (this.serial.equals(x509IssuerSerial.getSerial()) && this.issuer
/* 1330 */         .equals(x509IssuerSerial.getIssuer())) {
/* 1331 */         return true;
/*      */       }
/* 1333 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 1342 */       if (this.hashcode == 0) {
/* 1343 */         int i = 17;
/* 1344 */         i = 37 * i + this.issuer.hashCode();
/* 1345 */         i = 37 * i + this.serial.hashCode();
/* 1346 */         this.hashcode = i;
/*      */       } 
/* 1348 */       return this.hashcode;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int compareTo(X509IssuerSerial param1X509IssuerSerial) {
/* 1354 */       int i = this.issuer.toString().compareTo(param1X509IssuerSerial.issuer.toString());
/* 1355 */       if (i != 0) return i; 
/* 1356 */       return this.serial.compareTo(param1X509IssuerSerial.serial);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/X509CRLImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */