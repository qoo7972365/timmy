/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import java.security.cert.CRLException;
/*     */ import java.security.cert.CRLReason;
/*     */ import java.security.cert.Extension;
/*     */ import java.security.cert.X509CRLEntry;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import java.util.TreeSet;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import sun.misc.HexDumpEncoder;
/*     */ import sun.security.util.DerInputStream;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
/*     */ import sun.security.util.ObjectIdentifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class X509CRLEntryImpl
/*     */   extends X509CRLEntry
/*     */   implements Comparable<X509CRLEntryImpl>
/*     */ {
/*  73 */   private SerialNumber serialNumber = null;
/*  74 */   private Date revocationDate = null;
/*  75 */   private CRLExtensions extensions = null;
/*  76 */   private byte[] revokedCert = null;
/*     */ 
/*     */   
/*     */   private X500Principal certIssuer;
/*     */ 
/*     */   
/*     */   private static final boolean isExplicit = false;
/*     */ 
/*     */   
/*     */   private static final long YR_2050 = 2524636800000L;
/*     */ 
/*     */ 
/*     */   
/*     */   public X509CRLEntryImpl(BigInteger paramBigInteger, Date paramDate) {
/*  90 */     this.serialNumber = new SerialNumber(paramBigInteger);
/*  91 */     this.revocationDate = paramDate;
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
/*     */   public X509CRLEntryImpl(BigInteger paramBigInteger, Date paramDate, CRLExtensions paramCRLExtensions) {
/* 105 */     this.serialNumber = new SerialNumber(paramBigInteger);
/* 106 */     this.revocationDate = paramDate;
/* 107 */     this.extensions = paramCRLExtensions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509CRLEntryImpl(byte[] paramArrayOfbyte) throws CRLException {
/*     */     try {
/* 118 */       parse(new DerValue(paramArrayOfbyte));
/* 119 */     } catch (IOException iOException) {
/* 120 */       this.revokedCert = null;
/* 121 */       throw new CRLException("Parsing error: " + iOException.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509CRLEntryImpl(DerValue paramDerValue) throws CRLException {
/*     */     try {
/* 133 */       parse(paramDerValue);
/* 134 */     } catch (IOException iOException) {
/* 135 */       this.revokedCert = null;
/* 136 */       throw new CRLException("Parsing error: " + iOException.toString());
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
/*     */   public boolean hasExtensions() {
/* 148 */     return (this.extensions != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(DerOutputStream paramDerOutputStream) throws CRLException {
/*     */     try {
/* 160 */       if (this.revokedCert == null) {
/* 161 */         DerOutputStream derOutputStream1 = new DerOutputStream();
/*     */         
/* 163 */         this.serialNumber.encode(derOutputStream1);
/*     */         
/* 165 */         if (this.revocationDate.getTime() < 2524636800000L) {
/* 166 */           derOutputStream1.putUTCTime(this.revocationDate);
/*     */         } else {
/* 168 */           derOutputStream1.putGeneralizedTime(this.revocationDate);
/*     */         } 
/*     */         
/* 171 */         if (this.extensions != null) {
/* 172 */           this.extensions.encode(derOutputStream1, false);
/*     */         }
/* 174 */         DerOutputStream derOutputStream2 = new DerOutputStream();
/* 175 */         derOutputStream2.write((byte)48, derOutputStream1);
/*     */         
/* 177 */         this.revokedCert = derOutputStream2.toByteArray();
/*     */       } 
/* 179 */       paramDerOutputStream.write(this.revokedCert);
/* 180 */     } catch (IOException iOException) {
/* 181 */       throw new CRLException("Encoding error: " + iOException.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getEncoded() throws CRLException {
/* 192 */     return (byte[])getEncoded0().clone();
/*     */   }
/*     */ 
/*     */   
/*     */   private byte[] getEncoded0() throws CRLException {
/* 197 */     if (this.revokedCert == null)
/* 198 */       encode(new DerOutputStream()); 
/* 199 */     return this.revokedCert;
/*     */   }
/*     */ 
/*     */   
/*     */   public X500Principal getCertificateIssuer() {
/* 204 */     return this.certIssuer;
/*     */   }
/*     */   
/*     */   void setCertificateIssuer(X500Principal paramX500Principal1, X500Principal paramX500Principal2) {
/* 208 */     if (paramX500Principal1.equals(paramX500Principal2)) {
/* 209 */       this.certIssuer = null;
/*     */     } else {
/* 211 */       this.certIssuer = paramX500Principal2;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigInteger getSerialNumber() {
/* 222 */     return this.serialNumber.getNumber();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getRevocationDate() {
/* 232 */     return new Date(this.revocationDate.getTime());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CRLReason getRevocationReason() {
/* 242 */     Extension extension = getExtension(PKIXExtensions.ReasonCode_Id);
/* 243 */     if (extension == null) {
/* 244 */       return null;
/*     */     }
/* 246 */     CRLReasonCodeExtension cRLReasonCodeExtension = (CRLReasonCodeExtension)extension;
/* 247 */     return cRLReasonCodeExtension.getReasonCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CRLReason getRevocationReason(X509CRLEntry paramX509CRLEntry) {
/*     */     try {
/* 256 */       byte[] arrayOfByte1 = paramX509CRLEntry.getExtensionValue("2.5.29.21");
/* 257 */       if (arrayOfByte1 == null) {
/* 258 */         return null;
/*     */       }
/* 260 */       DerValue derValue = new DerValue(arrayOfByte1);
/* 261 */       byte[] arrayOfByte2 = derValue.getOctetString();
/*     */       
/* 263 */       CRLReasonCodeExtension cRLReasonCodeExtension = new CRLReasonCodeExtension(Boolean.FALSE, arrayOfByte2);
/*     */       
/* 265 */       return cRLReasonCodeExtension.getReasonCode();
/* 266 */     } catch (IOException iOException) {
/* 267 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getReasonCode() throws IOException {
/* 278 */     Extension extension = getExtension(PKIXExtensions.ReasonCode_Id);
/* 279 */     if (extension == null)
/* 280 */       return null; 
/* 281 */     CRLReasonCodeExtension cRLReasonCodeExtension = (CRLReasonCodeExtension)extension;
/* 282 */     return cRLReasonCodeExtension.get("reason");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 292 */     StringBuilder stringBuilder = new StringBuilder();
/*     */     
/* 294 */     stringBuilder.append(this.serialNumber.toString());
/* 295 */     stringBuilder.append("  On: " + this.revocationDate.toString());
/* 296 */     if (this.certIssuer != null) {
/* 297 */       stringBuilder.append("\n    Certificate issuer: " + this.certIssuer);
/*     */     }
/* 299 */     if (this.extensions != null) {
/* 300 */       Collection<Extension> collection = this.extensions.getAllExtensions();
/* 301 */       Extension[] arrayOfExtension = collection.<Extension>toArray(new Extension[0]);
/*     */       
/* 303 */       stringBuilder.append("\n    CRL Entry Extensions: " + arrayOfExtension.length);
/* 304 */       for (byte b = 0; b < arrayOfExtension.length; b++) {
/* 305 */         stringBuilder.append("\n    [" + (b + 1) + "]: ");
/* 306 */         Extension extension = arrayOfExtension[b];
/*     */         try {
/* 308 */           if (OIDMap.getClass(extension.getExtensionId()) == null)
/* 309 */           { stringBuilder.append(extension.toString());
/* 310 */             byte[] arrayOfByte = extension.getExtensionValue();
/* 311 */             if (arrayOfByte != null) {
/* 312 */               DerOutputStream derOutputStream = new DerOutputStream();
/* 313 */               derOutputStream.putOctetString(arrayOfByte);
/* 314 */               arrayOfByte = derOutputStream.toByteArray();
/* 315 */               HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
/* 316 */               stringBuilder.append("Extension unknown: DER encoded OCTET string =\n" + hexDumpEncoder
/*     */                   
/* 318 */                   .encodeBuffer(arrayOfByte) + "\n");
/*     */             }  }
/*     */           else
/* 321 */           { stringBuilder.append(extension.toString()); } 
/* 322 */         } catch (Exception exception) {
/* 323 */           stringBuilder.append(", Error parsing this extension");
/*     */         } 
/*     */       } 
/*     */     } 
/* 327 */     stringBuilder.append("\n");
/* 328 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasUnsupportedCriticalExtension() {
/* 336 */     if (this.extensions == null)
/* 337 */       return false; 
/* 338 */     return this.extensions.hasUnsupportedCriticalExtension();
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
/*     */   public Set<String> getCriticalExtensionOIDs() {
/* 350 */     if (this.extensions == null) {
/* 351 */       return null;
/*     */     }
/* 353 */     TreeSet<String> treeSet = new TreeSet();
/* 354 */     for (Extension extension : this.extensions.getAllExtensions()) {
/* 355 */       if (extension.isCritical()) {
/* 356 */         treeSet.add(extension.getExtensionId().toString());
/*     */       }
/*     */     } 
/* 359 */     return treeSet;
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
/*     */   public Set<String> getNonCriticalExtensionOIDs() {
/* 371 */     if (this.extensions == null) {
/* 372 */       return null;
/*     */     }
/* 374 */     TreeSet<String> treeSet = new TreeSet();
/* 375 */     for (Extension extension : this.extensions.getAllExtensions()) {
/* 376 */       if (!extension.isCritical()) {
/* 377 */         treeSet.add(extension.getExtensionId().toString());
/*     */       }
/*     */     } 
/* 380 */     return treeSet;
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
/*     */   public byte[] getExtensionValue(String paramString) {
/* 396 */     if (this.extensions == null)
/* 397 */       return null; 
/*     */     try {
/* 399 */       String str = OIDMap.getName(new ObjectIdentifier(paramString));
/* 400 */       Extension extension = null;
/*     */       
/* 402 */       if (str == null) {
/* 403 */         ObjectIdentifier objectIdentifier = new ObjectIdentifier(paramString);
/* 404 */         Extension extension1 = null;
/*     */         
/* 406 */         Enumeration<Extension> enumeration = this.extensions.getElements();
/* 407 */         while (enumeration.hasMoreElements()) {
/* 408 */           extension1 = enumeration.nextElement();
/* 409 */           ObjectIdentifier objectIdentifier1 = extension1.getExtensionId();
/* 410 */           if (objectIdentifier1.equals(objectIdentifier)) {
/* 411 */             extension = extension1;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 416 */         extension = this.extensions.get(str);
/* 417 */       }  if (extension == null)
/* 418 */         return null; 
/* 419 */       byte[] arrayOfByte = extension.getExtensionValue();
/* 420 */       if (arrayOfByte == null) {
/* 421 */         return null;
/*     */       }
/* 423 */       DerOutputStream derOutputStream = new DerOutputStream();
/* 424 */       derOutputStream.putOctetString(arrayOfByte);
/* 425 */       return derOutputStream.toByteArray();
/* 426 */     } catch (Exception exception) {
/* 427 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Extension getExtension(ObjectIdentifier paramObjectIdentifier) {
/* 438 */     if (this.extensions == null) {
/* 439 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 443 */     return this.extensions.get(OIDMap.getName(paramObjectIdentifier));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void parse(DerValue paramDerValue) throws CRLException, IOException {
/* 449 */     if (paramDerValue.tag != 48) {
/* 450 */       throw new CRLException("Invalid encoded RevokedCertificate, starting sequence tag missing.");
/*     */     }
/*     */     
/* 453 */     if (paramDerValue.data.available() == 0) {
/* 454 */       throw new CRLException("No data encoded for RevokedCertificates");
/*     */     }
/* 456 */     this.revokedCert = paramDerValue.toByteArray();
/*     */     
/* 458 */     DerInputStream derInputStream = paramDerValue.toDerInputStream();
/* 459 */     DerValue derValue = derInputStream.getDerValue();
/* 460 */     this.serialNumber = new SerialNumber(derValue);
/*     */ 
/*     */     
/* 463 */     int i = paramDerValue.data.peekByte();
/* 464 */     if ((byte)i == 23) {
/* 465 */       this.revocationDate = paramDerValue.data.getUTCTime();
/* 466 */     } else if ((byte)i == 24) {
/* 467 */       this.revocationDate = paramDerValue.data.getGeneralizedTime();
/*     */     } else {
/* 469 */       throw new CRLException("Invalid encoding for revocation date");
/*     */     } 
/* 471 */     if (paramDerValue.data.available() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 475 */     this.extensions = new CRLExtensions(paramDerValue.toDerInputStream());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static X509CRLEntryImpl toImpl(X509CRLEntry paramX509CRLEntry) throws CRLException {
/* 485 */     if (paramX509CRLEntry instanceof X509CRLEntryImpl) {
/* 486 */       return (X509CRLEntryImpl)paramX509CRLEntry;
/*     */     }
/* 488 */     return new X509CRLEntryImpl(paramX509CRLEntry.getEncoded());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CertificateIssuerExtension getCertificateIssuerExtension() {
/* 498 */     return (CertificateIssuerExtension)
/* 499 */       getExtension(PKIXExtensions.CertificateIssuer_Id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Extension> getExtensions() {
/* 507 */     if (this.extensions == null) {
/* 508 */       return Collections.emptyMap();
/*     */     }
/* 510 */     Collection<Extension> collection = this.extensions.getAllExtensions();
/* 511 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/* 512 */     for (Extension extension : collection) {
/* 513 */       treeMap.put(extension.getId(), extension);
/*     */     }
/* 515 */     return (Map)treeMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(X509CRLEntryImpl paramX509CRLEntryImpl) {
/* 520 */     int i = getSerialNumber().compareTo(paramX509CRLEntryImpl.getSerialNumber());
/* 521 */     if (i != 0) {
/* 522 */       return i;
/*     */     }
/*     */     try {
/* 525 */       byte[] arrayOfByte1 = getEncoded0();
/* 526 */       byte[] arrayOfByte2 = paramX509CRLEntryImpl.getEncoded0();
/* 527 */       for (byte b = 0; b < arrayOfByte1.length && b < arrayOfByte2.length; b++) {
/* 528 */         int j = arrayOfByte1[b] & 0xFF;
/* 529 */         int k = arrayOfByte2[b] & 0xFF;
/* 530 */         if (j != k) return j - k; 
/*     */       } 
/* 532 */       return arrayOfByte1.length - arrayOfByte2.length;
/* 533 */     } catch (CRLException cRLException) {
/* 534 */       return -1;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/X509CRLEntryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */