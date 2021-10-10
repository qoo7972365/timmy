/*     */ package com.sun.org.apache.xml.internal.security.algorithms;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureException;
/*     */ import java.security.DigestException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.Provider;
/*     */ import org.w3c.dom.Document;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MessageDigestAlgorithm
/*     */   extends Algorithm
/*     */ {
/*     */   public static final String ALGO_ID_DIGEST_NOT_RECOMMENDED_MD5 = "http://www.w3.org/2001/04/xmldsig-more#md5";
/*     */   public static final String ALGO_ID_DIGEST_SHA1 = "http://www.w3.org/2000/09/xmldsig#sha1";
/*     */   public static final String ALGO_ID_DIGEST_SHA256 = "http://www.w3.org/2001/04/xmlenc#sha256";
/*     */   public static final String ALGO_ID_DIGEST_SHA384 = "http://www.w3.org/2001/04/xmldsig-more#sha384";
/*     */   public static final String ALGO_ID_DIGEST_SHA512 = "http://www.w3.org/2001/04/xmlenc#sha512";
/*     */   public static final String ALGO_ID_DIGEST_RIPEMD160 = "http://www.w3.org/2001/04/xmlenc#ripemd160";
/*     */   private final MessageDigest algorithm;
/*     */   
/*     */   private MessageDigestAlgorithm(Document paramDocument, String paramString) throws XMLSignatureException {
/*  71 */     super(paramDocument, paramString);
/*     */     
/*  73 */     this.algorithm = getDigestInstance(paramString);
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
/*     */   public static MessageDigestAlgorithm getInstance(Document paramDocument, String paramString) throws XMLSignatureException {
/*  87 */     return new MessageDigestAlgorithm(paramDocument, paramString);
/*     */   }
/*     */   private static MessageDigest getDigestInstance(String paramString) throws XMLSignatureException {
/*     */     MessageDigest messageDigest;
/*  91 */     String str1 = JCEMapper.translateURItoJCEID(paramString);
/*     */     
/*  93 */     if (str1 == null) {
/*  94 */       Object[] arrayOfObject = { paramString };
/*  95 */       throw new XMLSignatureException("algorithms.NoSuchMap", arrayOfObject);
/*     */     } 
/*     */ 
/*     */     
/*  99 */     String str2 = JCEMapper.getProviderId();
/*     */     try {
/* 101 */       if (str2 == null) {
/* 102 */         messageDigest = MessageDigest.getInstance(str1);
/*     */       } else {
/* 104 */         messageDigest = MessageDigest.getInstance(str1, str2);
/*     */       } 
/* 106 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 107 */       Object[] arrayOfObject = { str1, noSuchAlgorithmException.getLocalizedMessage() };
/*     */       
/* 109 */       throw new XMLSignatureException("algorithms.NoSuchAlgorithm", arrayOfObject);
/* 110 */     } catch (NoSuchProviderException noSuchProviderException) {
/* 111 */       Object[] arrayOfObject = { str1, noSuchProviderException.getLocalizedMessage() };
/*     */       
/* 113 */       throw new XMLSignatureException("algorithms.NoSuchAlgorithm", arrayOfObject);
/*     */     } 
/*     */     
/* 116 */     return messageDigest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageDigest getAlgorithm() {
/* 125 */     return this.algorithm;
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
/*     */   public static boolean isEqual(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
/* 137 */     return MessageDigest.isEqual(paramArrayOfbyte1, paramArrayOfbyte2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] digest() {
/* 147 */     return this.algorithm.digest();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] digest(byte[] paramArrayOfbyte) {
/* 158 */     return this.algorithm.digest(paramArrayOfbyte);
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
/*     */   public int digest(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws DigestException {
/* 172 */     return this.algorithm.digest(paramArrayOfbyte, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJCEAlgorithmString() {
/* 182 */     return this.algorithm.getAlgorithm();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Provider getJCEProvider() {
/* 192 */     return this.algorithm.getProvider();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDigestLength() {
/* 202 */     return this.algorithm.getDigestLength();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 211 */     this.algorithm.reset();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(byte[] paramArrayOfbyte) {
/* 221 */     this.algorithm.update(paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(byte paramByte) {
/* 231 */     this.algorithm.update(paramByte);
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
/*     */   public void update(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 243 */     this.algorithm.update(paramArrayOfbyte, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBaseNamespace() {
/* 248 */     return "http://www.w3.org/2000/09/xmldsig#";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBaseLocalName() {
/* 253 */     return "DigestMethod";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/algorithms/MessageDigestAlgorithm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */