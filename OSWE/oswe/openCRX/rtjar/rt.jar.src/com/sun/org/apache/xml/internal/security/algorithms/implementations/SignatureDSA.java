/*     */ package com.sun.org.apache.xml.internal.security.algorithms.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.algorithms.JCEMapper;
/*     */ import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithmSpi;
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.Base64;
/*     */ import com.sun.org.apache.xml.internal.security.utils.JavaUtils;
/*     */ import java.io.IOException;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.Key;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.PublicKey;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.Signature;
/*     */ import java.security.SignatureException;
/*     */ import java.security.interfaces.DSAKey;
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SignatureDSA
/*     */   extends SignatureAlgorithmSpi
/*     */ {
/*  48 */   private static Logger log = Logger.getLogger(SignatureDSA.class.getName());
/*     */ 
/*     */   
/*  51 */   private Signature signatureAlgorithm = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int size;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String engineGetURI() {
/*  62 */     return "http://www.w3.org/2000/09/xmldsig#dsa-sha1";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureDSA() throws XMLSignatureException {
/*  71 */     String str1 = JCEMapper.translateURItoJCEID(engineGetURI());
/*  72 */     if (log.isLoggable(Level.FINE)) {
/*  73 */       log.log(Level.FINE, "Created SignatureDSA using " + str1);
/*     */     }
/*     */     
/*  76 */     String str2 = JCEMapper.getProviderId();
/*     */     try {
/*  78 */       if (str2 == null) {
/*  79 */         this.signatureAlgorithm = Signature.getInstance(str1);
/*     */       } else {
/*  81 */         this
/*  82 */           .signatureAlgorithm = Signature.getInstance(str1, str2);
/*     */       } 
/*  84 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*  85 */       Object[] arrayOfObject = { str1, noSuchAlgorithmException.getLocalizedMessage() };
/*  86 */       throw new XMLSignatureException("algorithms.NoSuchAlgorithm", arrayOfObject);
/*  87 */     } catch (NoSuchProviderException noSuchProviderException) {
/*  88 */       Object[] arrayOfObject = { str1, noSuchProviderException.getLocalizedMessage() };
/*  89 */       throw new XMLSignatureException("algorithms.NoSuchAlgorithm", arrayOfObject);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineSetParameter(AlgorithmParameterSpec paramAlgorithmParameterSpec) throws XMLSignatureException {
/*     */     try {
/*  99 */       this.signatureAlgorithm.setParameter(paramAlgorithmParameterSpec);
/* 100 */     } catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
/* 101 */       throw new XMLSignatureException("empty", invalidAlgorithmParameterException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean engineVerify(byte[] paramArrayOfbyte) throws XMLSignatureException {
/*     */     try {
/* 111 */       if (log.isLoggable(Level.FINE)) {
/* 112 */         log.log(Level.FINE, "Called DSA.verify() on " + Base64.encode(paramArrayOfbyte));
/*     */       }
/*     */       
/* 115 */       byte[] arrayOfByte = JavaUtils.convertDsaXMLDSIGtoASN1(paramArrayOfbyte, this.size / 8);
/*     */ 
/*     */       
/* 118 */       return this.signatureAlgorithm.verify(arrayOfByte);
/* 119 */     } catch (SignatureException signatureException) {
/* 120 */       throw new XMLSignatureException("empty", signatureException);
/* 121 */     } catch (IOException iOException) {
/* 122 */       throw new XMLSignatureException("empty", iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineInitVerify(Key paramKey) throws XMLSignatureException {
/* 130 */     if (!(paramKey instanceof PublicKey)) {
/* 131 */       String str1 = paramKey.getClass().getName();
/* 132 */       String str2 = PublicKey.class.getName();
/* 133 */       Object[] arrayOfObject = { str1, str2 };
/*     */       
/* 135 */       throw new XMLSignatureException("algorithms.WrongKeyForThisOperation", arrayOfObject);
/*     */     } 
/*     */     
/*     */     try {
/* 139 */       this.signatureAlgorithm.initVerify((PublicKey)paramKey);
/* 140 */     } catch (InvalidKeyException invalidKeyException) {
/*     */ 
/*     */       
/* 143 */       Signature signature = this.signatureAlgorithm;
/*     */       try {
/* 145 */         this.signatureAlgorithm = Signature.getInstance(this.signatureAlgorithm.getAlgorithm());
/* 146 */       } catch (Exception exception) {
/*     */ 
/*     */         
/* 149 */         if (log.isLoggable(Level.FINE)) {
/* 150 */           log.log(Level.FINE, "Exception when reinstantiating Signature:" + exception);
/*     */         }
/* 152 */         this.signatureAlgorithm = signature;
/*     */       } 
/* 154 */       throw new XMLSignatureException("empty", invalidKeyException);
/*     */     } 
/* 156 */     this.size = ((DSAKey)paramKey).getParams().getQ().bitLength();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] engineSign() throws XMLSignatureException {
/*     */     try {
/* 164 */       byte[] arrayOfByte = this.signatureAlgorithm.sign();
/*     */       
/* 166 */       return JavaUtils.convertDsaASN1toXMLDSIG(arrayOfByte, this.size / 8);
/* 167 */     } catch (IOException iOException) {
/* 168 */       throw new XMLSignatureException("empty", iOException);
/* 169 */     } catch (SignatureException signatureException) {
/* 170 */       throw new XMLSignatureException("empty", signatureException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineInitSign(Key paramKey, SecureRandom paramSecureRandom) throws XMLSignatureException {
/* 179 */     if (!(paramKey instanceof PrivateKey)) {
/* 180 */       String str1 = paramKey.getClass().getName();
/* 181 */       String str2 = PrivateKey.class.getName();
/* 182 */       Object[] arrayOfObject = { str1, str2 };
/*     */       
/* 184 */       throw new XMLSignatureException("algorithms.WrongKeyForThisOperation", arrayOfObject);
/*     */     } 
/*     */     
/*     */     try {
/* 188 */       this.signatureAlgorithm.initSign((PrivateKey)paramKey, paramSecureRandom);
/* 189 */     } catch (InvalidKeyException invalidKeyException) {
/* 190 */       throw new XMLSignatureException("empty", invalidKeyException);
/*     */     } 
/* 192 */     this.size = ((DSAKey)paramKey).getParams().getQ().bitLength();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineInitSign(Key paramKey) throws XMLSignatureException {
/* 199 */     if (!(paramKey instanceof PrivateKey)) {
/* 200 */       String str1 = paramKey.getClass().getName();
/* 201 */       String str2 = PrivateKey.class.getName();
/* 202 */       Object[] arrayOfObject = { str1, str2 };
/*     */       
/* 204 */       throw new XMLSignatureException("algorithms.WrongKeyForThisOperation", arrayOfObject);
/*     */     } 
/*     */     
/*     */     try {
/* 208 */       this.signatureAlgorithm.initSign((PrivateKey)paramKey);
/* 209 */     } catch (InvalidKeyException invalidKeyException) {
/* 210 */       throw new XMLSignatureException("empty", invalidKeyException);
/*     */     } 
/* 212 */     this.size = ((DSAKey)paramKey).getParams().getQ().bitLength();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineUpdate(byte[] paramArrayOfbyte) throws XMLSignatureException {
/*     */     try {
/* 220 */       this.signatureAlgorithm.update(paramArrayOfbyte);
/* 221 */     } catch (SignatureException signatureException) {
/* 222 */       throw new XMLSignatureException("empty", signatureException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineUpdate(byte paramByte) throws XMLSignatureException {
/*     */     try {
/* 231 */       this.signatureAlgorithm.update(paramByte);
/* 232 */     } catch (SignatureException signatureException) {
/* 233 */       throw new XMLSignatureException("empty", signatureException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineUpdate(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws XMLSignatureException {
/*     */     try {
/* 242 */       this.signatureAlgorithm.update(paramArrayOfbyte, paramInt1, paramInt2);
/* 243 */     } catch (SignatureException signatureException) {
/* 244 */       throw new XMLSignatureException("empty", signatureException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String engineGetJCEAlgorithmString() {
/* 254 */     return this.signatureAlgorithm.getAlgorithm();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String engineGetJCEProviderName() {
/* 263 */     return this.signatureAlgorithm.getProvider().getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineSetHMACOutputLength(int paramInt) throws XMLSignatureException {
/* 273 */     throw new XMLSignatureException("algorithms.HMACOutputLengthOnlyForHMAC");
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
/*     */   protected void engineInitSign(Key paramKey, AlgorithmParameterSpec paramAlgorithmParameterSpec) throws XMLSignatureException {
/* 286 */     throw new XMLSignatureException("algorithms.CannotUseAlgorithmParameterSpecOnDSA");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SHA256
/*     */     extends SignatureDSA
/*     */   {
/*     */     public String engineGetURI() {
/* 296 */       return "http://www.w3.org/2009/xmldsig11#dsa-sha256";
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/algorithms/implementations/SignatureDSA.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */