/*     */ package sun.security.provider.certpath;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509CertSelector;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.util.DerInputStream;
/*     */ import sun.security.x509.AuthorityKeyIdentifierExtension;
/*     */ import sun.security.x509.SerialNumber;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class AdaptableX509CertSelector
/*     */   extends X509CertSelector
/*     */ {
/*  52 */   private static final Debug debug = Debug.getInstance("certpath");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Date startDate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Date endDate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] ski;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BigInteger serial;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setValidityPeriod(Date paramDate1, Date paramDate2) {
/*  86 */     this.startDate = paramDate1;
/*  87 */     this.endDate = paramDate2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSubjectKeyIdentifier(byte[] paramArrayOfbyte) {
/*  97 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSerialNumber(BigInteger paramBigInteger) {
/* 107 */     throw new IllegalArgumentException();
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
/*     */   void setSkiAndSerialNumber(AuthorityKeyIdentifierExtension paramAuthorityKeyIdentifierExtension) throws IOException {
/* 128 */     this.ski = null;
/* 129 */     this.serial = null;
/*     */     
/* 131 */     if (paramAuthorityKeyIdentifierExtension != null) {
/* 132 */       this.ski = paramAuthorityKeyIdentifierExtension.getEncodedKeyIdentifier();
/* 133 */       SerialNumber serialNumber = (SerialNumber)paramAuthorityKeyIdentifierExtension.get("serial_number");
/*     */       
/* 135 */       if (serialNumber != null) {
/* 136 */         this.serial = serialNumber.getNumber();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean match(Certificate paramCertificate) {
/* 156 */     X509Certificate x509Certificate = (X509Certificate)paramCertificate;
/*     */ 
/*     */     
/* 159 */     if (!matchSubjectKeyID(x509Certificate)) {
/* 160 */       return false;
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
/* 172 */     int i = x509Certificate.getVersion();
/* 173 */     if (this.serial != null && i > 2 && 
/* 174 */       !this.serial.equals(x509Certificate.getSerialNumber())) {
/* 175 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 180 */     if (i < 3) {
/* 181 */       if (this.startDate != null) {
/*     */         try {
/* 183 */           x509Certificate.checkValidity(this.startDate);
/* 184 */         } catch (CertificateException certificateException) {
/* 185 */           return false;
/*     */         } 
/*     */       }
/* 188 */       if (this.endDate != null) {
/*     */         try {
/* 190 */           x509Certificate.checkValidity(this.endDate);
/* 191 */         } catch (CertificateException certificateException) {
/* 192 */           return false;
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 198 */     if (!super.match(paramCertificate)) {
/* 199 */       return false;
/*     */     }
/*     */     
/* 202 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchSubjectKeyID(X509Certificate paramX509Certificate) {
/* 211 */     if (this.ski == null) {
/* 212 */       return true;
/*     */     }
/*     */     try {
/* 215 */       byte[] arrayOfByte1 = paramX509Certificate.getExtensionValue("2.5.29.14");
/* 216 */       if (arrayOfByte1 == null) {
/* 217 */         if (debug != null) {
/* 218 */           debug.println("AdaptableX509CertSelector.match: no subject key ID extension. Subject: " + paramX509Certificate
/*     */               
/* 220 */               .getSubjectX500Principal());
/*     */         }
/* 222 */         return true;
/*     */       } 
/* 224 */       DerInputStream derInputStream = new DerInputStream(arrayOfByte1);
/* 225 */       byte[] arrayOfByte2 = derInputStream.getOctetString();
/* 226 */       if (arrayOfByte2 == null || 
/* 227 */         !Arrays.equals(this.ski, arrayOfByte2)) {
/* 228 */         if (debug != null) {
/* 229 */           debug.println("AdaptableX509CertSelector.match: subject key IDs don't match. Expected: " + 
/*     */               
/* 231 */               Arrays.toString(this.ski) + " Cert's: " + 
/* 232 */               Arrays.toString(arrayOfByte2));
/*     */         }
/* 234 */         return false;
/*     */       } 
/* 236 */     } catch (IOException iOException) {
/* 237 */       if (debug != null) {
/* 238 */         debug.println("AdaptableX509CertSelector.match: exception in subject key ID check");
/*     */       }
/*     */       
/* 241 */       return false;
/*     */     } 
/* 243 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 249 */     AdaptableX509CertSelector adaptableX509CertSelector = (AdaptableX509CertSelector)super.clone();
/* 250 */     if (this.startDate != null) {
/* 251 */       adaptableX509CertSelector.startDate = (Date)this.startDate.clone();
/*     */     }
/*     */     
/* 254 */     if (this.endDate != null) {
/* 255 */       adaptableX509CertSelector.endDate = (Date)this.endDate.clone();
/*     */     }
/*     */     
/* 258 */     if (this.ski != null) {
/* 259 */       adaptableX509CertSelector.ski = (byte[])this.ski.clone();
/*     */     }
/* 261 */     return adaptableX509CertSelector;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/AdaptableX509CertSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */