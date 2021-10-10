/*     */ package com.sun.org.apache.xml.internal.security.algorithms.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.algorithms.JCEMapper;
/*     */ import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithmSpi;
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureException;
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
/*     */ 
/*     */ public abstract class SignatureBaseRSA
/*     */   extends SignatureAlgorithmSpi
/*     */ {
/*  45 */   private static Logger log = Logger.getLogger(SignatureBaseRSA.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   private Signature signatureAlgorithm = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String engineGetURI();
/*     */ 
/*     */   
/*     */   public SignatureBaseRSA() throws XMLSignatureException {
/*  59 */     String str1 = JCEMapper.translateURItoJCEID(engineGetURI());
/*     */     
/*  61 */     if (log.isLoggable(Level.FINE)) {
/*  62 */       log.log(Level.FINE, "Created SignatureRSA using " + str1);
/*     */     }
/*  64 */     String str2 = JCEMapper.getProviderId();
/*     */     try {
/*  66 */       if (str2 == null) {
/*  67 */         this.signatureAlgorithm = Signature.getInstance(str1);
/*     */       } else {
/*  69 */         this.signatureAlgorithm = Signature.getInstance(str1, str2);
/*     */       } 
/*  71 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*  72 */       Object[] arrayOfObject = { str1, noSuchAlgorithmException.getLocalizedMessage() };
/*     */       
/*  74 */       throw new XMLSignatureException("algorithms.NoSuchAlgorithm", arrayOfObject);
/*  75 */     } catch (NoSuchProviderException noSuchProviderException) {
/*  76 */       Object[] arrayOfObject = { str1, noSuchProviderException.getLocalizedMessage() };
/*     */       
/*  78 */       throw new XMLSignatureException("algorithms.NoSuchAlgorithm", arrayOfObject);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineSetParameter(AlgorithmParameterSpec paramAlgorithmParameterSpec) throws XMLSignatureException {
/*     */     try {
/*  86 */       this.signatureAlgorithm.setParameter(paramAlgorithmParameterSpec);
/*  87 */     } catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
/*  88 */       throw new XMLSignatureException("empty", invalidAlgorithmParameterException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean engineVerify(byte[] paramArrayOfbyte) throws XMLSignatureException {
/*     */     try {
/*  95 */       return this.signatureAlgorithm.verify(paramArrayOfbyte);
/*  96 */     } catch (SignatureException signatureException) {
/*  97 */       throw new XMLSignatureException("empty", signatureException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void engineInitVerify(Key paramKey) throws XMLSignatureException {
/* 103 */     if (!(paramKey instanceof PublicKey)) {
/* 104 */       String str1 = paramKey.getClass().getName();
/* 105 */       String str2 = PublicKey.class.getName();
/* 106 */       Object[] arrayOfObject = { str1, str2 };
/*     */       
/* 108 */       throw new XMLSignatureException("algorithms.WrongKeyForThisOperation", arrayOfObject);
/*     */     } 
/*     */     
/*     */     try {
/* 112 */       this.signatureAlgorithm.initVerify((PublicKey)paramKey);
/* 113 */     } catch (InvalidKeyException invalidKeyException) {
/*     */ 
/*     */       
/* 116 */       Signature signature = this.signatureAlgorithm;
/*     */       try {
/* 118 */         this.signatureAlgorithm = Signature.getInstance(this.signatureAlgorithm.getAlgorithm());
/* 119 */       } catch (Exception exception) {
/*     */ 
/*     */         
/* 122 */         if (log.isLoggable(Level.FINE)) {
/* 123 */           log.log(Level.FINE, "Exception when reinstantiating Signature:" + exception);
/*     */         }
/* 125 */         this.signatureAlgorithm = signature;
/*     */       } 
/* 127 */       throw new XMLSignatureException("empty", invalidKeyException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte[] engineSign() throws XMLSignatureException {
/*     */     try {
/* 134 */       return this.signatureAlgorithm.sign();
/* 135 */     } catch (SignatureException signatureException) {
/* 136 */       throw new XMLSignatureException("empty", signatureException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineInitSign(Key paramKey, SecureRandom paramSecureRandom) throws XMLSignatureException {
/* 143 */     if (!(paramKey instanceof PrivateKey)) {
/* 144 */       String str1 = paramKey.getClass().getName();
/* 145 */       String str2 = PrivateKey.class.getName();
/* 146 */       Object[] arrayOfObject = { str1, str2 };
/*     */       
/* 148 */       throw new XMLSignatureException("algorithms.WrongKeyForThisOperation", arrayOfObject);
/*     */     } 
/*     */     
/*     */     try {
/* 152 */       this.signatureAlgorithm.initSign((PrivateKey)paramKey, paramSecureRandom);
/* 153 */     } catch (InvalidKeyException invalidKeyException) {
/* 154 */       throw new XMLSignatureException("empty", invalidKeyException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void engineInitSign(Key paramKey) throws XMLSignatureException {
/* 160 */     if (!(paramKey instanceof PrivateKey)) {
/* 161 */       String str1 = paramKey.getClass().getName();
/* 162 */       String str2 = PrivateKey.class.getName();
/* 163 */       Object[] arrayOfObject = { str1, str2 };
/*     */       
/* 165 */       throw new XMLSignatureException("algorithms.WrongKeyForThisOperation", arrayOfObject);
/*     */     } 
/*     */     
/*     */     try {
/* 169 */       this.signatureAlgorithm.initSign((PrivateKey)paramKey);
/* 170 */     } catch (InvalidKeyException invalidKeyException) {
/* 171 */       throw new XMLSignatureException("empty", invalidKeyException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void engineUpdate(byte[] paramArrayOfbyte) throws XMLSignatureException {
/*     */     try {
/* 178 */       this.signatureAlgorithm.update(paramArrayOfbyte);
/* 179 */     } catch (SignatureException signatureException) {
/* 180 */       throw new XMLSignatureException("empty", signatureException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void engineUpdate(byte paramByte) throws XMLSignatureException {
/*     */     try {
/* 187 */       this.signatureAlgorithm.update(paramByte);
/* 188 */     } catch (SignatureException signatureException) {
/* 189 */       throw new XMLSignatureException("empty", signatureException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void engineUpdate(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws XMLSignatureException {
/*     */     try {
/* 196 */       this.signatureAlgorithm.update(paramArrayOfbyte, paramInt1, paramInt2);
/* 197 */     } catch (SignatureException signatureException) {
/* 198 */       throw new XMLSignatureException("empty", signatureException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected String engineGetJCEAlgorithmString() {
/* 204 */     return this.signatureAlgorithm.getAlgorithm();
/*     */   }
/*     */ 
/*     */   
/*     */   protected String engineGetJCEProviderName() {
/* 209 */     return this.signatureAlgorithm.getProvider().getName();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineSetHMACOutputLength(int paramInt) throws XMLSignatureException {
/* 215 */     throw new XMLSignatureException("algorithms.HMACOutputLengthOnlyForHMAC");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineInitSign(Key paramKey, AlgorithmParameterSpec paramAlgorithmParameterSpec) throws XMLSignatureException {
/* 222 */     throw new XMLSignatureException("algorithms.CannotUseAlgorithmParameterSpecOnRSA");
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
/*     */   public static class SignatureRSASHA1
/*     */     extends SignatureBaseRSA
/*     */   {
/*     */     public String engineGetURI() {
/* 241 */       return "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
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
/*     */   public static class SignatureRSASHA256
/*     */     extends SignatureBaseRSA
/*     */   {
/*     */     public String engineGetURI() {
/* 261 */       return "http://www.w3.org/2001/04/xmldsig-more#rsa-sha256";
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
/*     */   public static class SignatureRSASHA384
/*     */     extends SignatureBaseRSA
/*     */   {
/*     */     public String engineGetURI() {
/* 281 */       return "http://www.w3.org/2001/04/xmldsig-more#rsa-sha384";
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
/*     */   public static class SignatureRSASHA512
/*     */     extends SignatureBaseRSA
/*     */   {
/*     */     public String engineGetURI() {
/* 301 */       return "http://www.w3.org/2001/04/xmldsig-more#rsa-sha512";
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
/*     */   public static class SignatureRSARIPEMD160
/*     */     extends SignatureBaseRSA
/*     */   {
/*     */     public String engineGetURI() {
/* 321 */       return "http://www.w3.org/2001/04/xmldsig-more#rsa-ripemd160";
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
/*     */   public static class SignatureRSAMD5
/*     */     extends SignatureBaseRSA
/*     */   {
/*     */     public String engineGetURI() {
/* 341 */       return "http://www.w3.org/2001/04/xmldsig-more#rsa-md5";
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/algorithms/implementations/SignatureBaseRSA.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */