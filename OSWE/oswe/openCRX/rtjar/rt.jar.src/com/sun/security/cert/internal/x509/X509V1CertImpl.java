/*     */ package com.sun.security.cert.internal.x509;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.math.BigInteger;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.Principal;
/*     */ import java.security.PublicKey;
/*     */ import java.security.SignatureException;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateExpiredException;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.CertificateNotYetValidException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Date;
/*     */ import javax.security.cert.CertificateEncodingException;
/*     */ import javax.security.cert.CertificateException;
/*     */ import javax.security.cert.CertificateExpiredException;
/*     */ import javax.security.cert.CertificateNotYetValidException;
/*     */ import javax.security.cert.X509Certificate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class X509V1CertImpl
/*     */   extends X509Certificate
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = -2048442350420423405L;
/*     */   private X509Certificate wrappedCert;
/*     */   
/*     */   private static synchronized CertificateFactory getFactory() throws CertificateException {
/*  58 */     return CertificateFactory.getInstance("X.509");
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
/*     */   public X509V1CertImpl() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509V1CertImpl(byte[] paramArrayOfbyte) throws CertificateException {
/*     */     try {
/*  83 */       ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(paramArrayOfbyte);
/*  84 */       this
/*  85 */         .wrappedCert = (X509Certificate)getFactory().generateCertificate(byteArrayInputStream);
/*  86 */     } catch (CertificateException certificateException) {
/*  87 */       throw new CertificateException(certificateException.getMessage());
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
/*     */   public X509V1CertImpl(InputStream paramInputStream) throws CertificateException {
/*     */     try {
/* 100 */       this
/* 101 */         .wrappedCert = (X509Certificate)getFactory().generateCertificate(paramInputStream);
/* 102 */     } catch (CertificateException certificateException) {
/* 103 */       throw new CertificateException(certificateException.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getEncoded() throws CertificateEncodingException {
/*     */     try {
/* 115 */       return this.wrappedCert.getEncoded();
/* 116 */     } catch (CertificateEncodingException certificateEncodingException) {
/* 117 */       throw new CertificateEncodingException(certificateEncodingException.getMessage());
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
/*     */   public void verify(PublicKey paramPublicKey) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
/*     */     try {
/* 135 */       this.wrappedCert.verify(paramPublicKey);
/* 136 */     } catch (CertificateException certificateException) {
/* 137 */       throw new CertificateException(certificateException.getMessage());
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
/*     */   public void verify(PublicKey paramPublicKey, String paramString) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
/*     */     try {
/* 156 */       this.wrappedCert.verify(paramPublicKey, paramString);
/* 157 */     } catch (CertificateException certificateException) {
/* 158 */       throw new CertificateException(certificateException.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
/* 168 */     checkValidity(new Date());
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
/*     */   public void checkValidity(Date paramDate) throws CertificateExpiredException, CertificateNotYetValidException {
/*     */     try {
/* 182 */       this.wrappedCert.checkValidity(paramDate);
/* 183 */     } catch (CertificateNotYetValidException certificateNotYetValidException) {
/* 184 */       throw new CertificateNotYetValidException(certificateNotYetValidException.getMessage());
/* 185 */     } catch (CertificateExpiredException certificateExpiredException) {
/* 186 */       throw new CertificateExpiredException(certificateExpiredException.getMessage());
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
/*     */   public String toString() {
/* 198 */     return this.wrappedCert.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PublicKey getPublicKey() {
/* 207 */     return this.wrappedCert.getPublicKey();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVersion() {
/* 217 */     return this.wrappedCert.getVersion() - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigInteger getSerialNumber() {
/* 226 */     return this.wrappedCert.getSerialNumber();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Principal getSubjectDN() {
/* 236 */     return this.wrappedCert.getSubjectDN();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Principal getIssuerDN() {
/* 246 */     return this.wrappedCert.getIssuerDN();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getNotBefore() {
/* 256 */     return this.wrappedCert.getNotBefore();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getNotAfter() {
/* 266 */     return this.wrappedCert.getNotAfter();
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
/*     */   public String getSigAlgName() {
/* 278 */     return this.wrappedCert.getSigAlgName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSigAlgOID() {
/* 289 */     return this.wrappedCert.getSigAlgOID();
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
/*     */   public byte[] getSigAlgParams() {
/* 301 */     return this.wrappedCert.getSigAlgParams();
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/*     */     try {
/* 307 */       paramObjectOutputStream.write(getEncoded());
/* 308 */     } catch (CertificateEncodingException certificateEncodingException) {
/* 309 */       throw new IOException("getEncoded failed: " + certificateEncodingException.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized void readObject(ObjectInputStream paramObjectInputStream) throws IOException {
/*     */     try {
/* 316 */       this
/* 317 */         .wrappedCert = (X509Certificate)getFactory().generateCertificate(paramObjectInputStream);
/* 318 */     } catch (CertificateException certificateException) {
/* 319 */       throw new IOException("generateCertificate failed: " + certificateException.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public X509Certificate getX509Certificate() {
/* 324 */     return this.wrappedCert;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/cert/internal/x509/X509V1CertImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */