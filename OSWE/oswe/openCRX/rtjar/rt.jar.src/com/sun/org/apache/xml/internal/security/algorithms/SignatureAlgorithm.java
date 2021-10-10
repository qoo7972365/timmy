/*     */ package com.sun.org.apache.xml.internal.security.algorithms;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.algorithms.implementations.IntegrityHmac;
/*     */ import com.sun.org.apache.xml.internal.security.algorithms.implementations.SignatureBaseRSA;
/*     */ import com.sun.org.apache.xml.internal.security.algorithms.implementations.SignatureDSA;
/*     */ import com.sun.org.apache.xml.internal.security.algorithms.implementations.SignatureECDSA;
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.AlgorithmAlreadyRegisteredException;
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.JavaUtils;
/*     */ import java.security.Key;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SignatureAlgorithm
/*     */   extends Algorithm
/*     */ {
/*  55 */   private static Logger log = Logger.getLogger(SignatureAlgorithm.class.getName());
/*     */ 
/*     */   
/*  58 */   private static Map<String, Class<? extends SignatureAlgorithmSpi>> algorithmHash = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final SignatureAlgorithmSpi signatureAlgorithm;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String algorithmURI;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureAlgorithm(Document paramDocument, String paramString) throws XMLSecurityException {
/*  74 */     super(paramDocument, paramString);
/*  75 */     this.algorithmURI = paramString;
/*     */     
/*  77 */     this.signatureAlgorithm = getSignatureAlgorithmSpi(paramString);
/*  78 */     this.signatureAlgorithm.engineGetContextFromElement(this.constructionElement);
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
/*     */   public SignatureAlgorithm(Document paramDocument, String paramString, int paramInt) throws XMLSecurityException {
/*  92 */     super(paramDocument, paramString);
/*  93 */     this.algorithmURI = paramString;
/*     */     
/*  95 */     this.signatureAlgorithm = getSignatureAlgorithmSpi(paramString);
/*  96 */     this.signatureAlgorithm.engineGetContextFromElement(this.constructionElement);
/*     */     
/*  98 */     this.signatureAlgorithm.engineSetHMACOutputLength(paramInt);
/*  99 */     ((IntegrityHmac)this.signatureAlgorithm).engineAddContextToElement(this.constructionElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureAlgorithm(Element paramElement, String paramString) throws XMLSecurityException {
/* 110 */     this(paramElement, paramString, false);
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
/*     */   public SignatureAlgorithm(Element paramElement, String paramString, boolean paramBoolean) throws XMLSecurityException {
/* 124 */     super(paramElement, paramString);
/* 125 */     this.algorithmURI = getURI();
/*     */     
/* 127 */     Attr attr = paramElement.getAttributeNodeNS(null, "Id");
/* 128 */     if (attr != null) {
/* 129 */       paramElement.setIdAttributeNode(attr, true);
/*     */     }
/*     */     
/* 132 */     if (paramBoolean && ("http://www.w3.org/2001/04/xmldsig-more#hmac-md5".equals(this.algorithmURI) || "http://www.w3.org/2001/04/xmldsig-more#rsa-md5"
/* 133 */       .equals(this.algorithmURI))) {
/* 134 */       Object[] arrayOfObject = { this.algorithmURI };
/*     */       
/* 136 */       throw new XMLSecurityException("signature.signatureAlgorithm", arrayOfObject);
/*     */     } 
/*     */     
/* 139 */     this.signatureAlgorithm = getSignatureAlgorithmSpi(this.algorithmURI);
/* 140 */     this.signatureAlgorithm.engineGetContextFromElement(this.constructionElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static SignatureAlgorithmSpi getSignatureAlgorithmSpi(String paramString) throws XMLSignatureException {
/*     */     try {
/* 150 */       Class<SignatureAlgorithmSpi> clazz = (Class)algorithmHash.get(paramString);
/* 151 */       if (log.isLoggable(Level.FINE)) {
/* 152 */         log.log(Level.FINE, "Create URI \"" + paramString + "\" class \"" + clazz + "\"");
/*     */       }
/*     */       
/* 155 */       return clazz.newInstance();
/* 156 */     } catch (IllegalAccessException illegalAccessException) {
/* 157 */       Object[] arrayOfObject = { paramString, illegalAccessException.getMessage() };
/* 158 */       throw new XMLSignatureException("algorithms.NoSuchAlgorithm", arrayOfObject, illegalAccessException);
/* 159 */     } catch (InstantiationException instantiationException) {
/* 160 */       Object[] arrayOfObject = { paramString, instantiationException.getMessage() };
/* 161 */       throw new XMLSignatureException("algorithms.NoSuchAlgorithm", arrayOfObject, instantiationException);
/* 162 */     } catch (NullPointerException nullPointerException) {
/* 163 */       Object[] arrayOfObject = { paramString, nullPointerException.getMessage() };
/* 164 */       throw new XMLSignatureException("algorithms.NoSuchAlgorithm", arrayOfObject, nullPointerException);
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
/*     */   public byte[] sign() throws XMLSignatureException {
/* 177 */     return this.signatureAlgorithm.engineSign();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJCEAlgorithmString() {
/* 187 */     return this.signatureAlgorithm.engineGetJCEAlgorithmString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJCEProviderName() {
/* 196 */     return this.signatureAlgorithm.engineGetJCEProviderName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(byte[] paramArrayOfbyte) throws XMLSignatureException {
/* 207 */     this.signatureAlgorithm.engineUpdate(paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(byte paramByte) throws XMLSignatureException {
/* 218 */     this.signatureAlgorithm.engineUpdate(paramByte);
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
/*     */   public void update(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws XMLSignatureException {
/* 231 */     this.signatureAlgorithm.engineUpdate(paramArrayOfbyte, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initSign(Key paramKey) throws XMLSignatureException {
/* 242 */     this.signatureAlgorithm.engineInitSign(paramKey);
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
/*     */   public void initSign(Key paramKey, SecureRandom paramSecureRandom) throws XMLSignatureException {
/* 255 */     this.signatureAlgorithm.engineInitSign(paramKey, paramSecureRandom);
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
/*     */   public void initSign(Key paramKey, AlgorithmParameterSpec paramAlgorithmParameterSpec) throws XMLSignatureException {
/* 269 */     this.signatureAlgorithm.engineInitSign(paramKey, paramAlgorithmParameterSpec);
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
/*     */   public void setParameter(AlgorithmParameterSpec paramAlgorithmParameterSpec) throws XMLSignatureException {
/* 281 */     this.signatureAlgorithm.engineSetParameter(paramAlgorithmParameterSpec);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initVerify(Key paramKey) throws XMLSignatureException {
/* 292 */     this.signatureAlgorithm.engineInitVerify(paramKey);
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
/*     */   public boolean verify(byte[] paramArrayOfbyte) throws XMLSignatureException {
/* 305 */     return this.signatureAlgorithm.engineVerify(paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getURI() {
/* 314 */     return this.constructionElement.getAttributeNS(null, "Algorithm");
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
/*     */   public static void register(String paramString1, String paramString2) throws AlgorithmAlreadyRegisteredException, ClassNotFoundException, XMLSignatureException {
/* 332 */     JavaUtils.checkRegisterPermission();
/* 333 */     if (log.isLoggable(Level.FINE)) {
/* 334 */       log.log(Level.FINE, "Try to register " + paramString1 + " " + paramString2);
/*     */     }
/*     */ 
/*     */     
/* 338 */     Class clazz = algorithmHash.get(paramString1);
/* 339 */     if (clazz != null) {
/* 340 */       Object[] arrayOfObject = { paramString1, clazz };
/* 341 */       throw new AlgorithmAlreadyRegisteredException("algorithm.alreadyRegistered", arrayOfObject);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 348 */       Class<?> clazz1 = ClassLoaderUtils.loadClass(paramString2, SignatureAlgorithm.class);
/* 349 */       algorithmHash.put(paramString1, clazz1);
/* 350 */     } catch (NullPointerException nullPointerException) {
/* 351 */       Object[] arrayOfObject = { paramString1, nullPointerException.getMessage() };
/* 352 */       throw new XMLSignatureException("algorithms.NoSuchAlgorithm", arrayOfObject, nullPointerException);
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
/*     */   public static void register(String paramString, Class<? extends SignatureAlgorithmSpi> paramClass) throws AlgorithmAlreadyRegisteredException, ClassNotFoundException, XMLSignatureException {
/* 370 */     JavaUtils.checkRegisterPermission();
/* 371 */     if (log.isLoggable(Level.FINE)) {
/* 372 */       log.log(Level.FINE, "Try to register " + paramString + " " + paramClass);
/*     */     }
/*     */ 
/*     */     
/* 376 */     Class clazz = algorithmHash.get(paramString);
/* 377 */     if (clazz != null) {
/* 378 */       Object[] arrayOfObject = { paramString, clazz };
/* 379 */       throw new AlgorithmAlreadyRegisteredException("algorithm.alreadyRegistered", arrayOfObject);
/*     */     } 
/*     */ 
/*     */     
/* 383 */     algorithmHash.put(paramString, paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerDefaultAlgorithms() {
/* 390 */     algorithmHash.put("http://www.w3.org/2000/09/xmldsig#dsa-sha1", SignatureDSA.class);
/*     */ 
/*     */     
/* 393 */     algorithmHash.put("http://www.w3.org/2009/xmldsig11#dsa-sha256", SignatureDSA.SHA256.class);
/*     */ 
/*     */     
/* 396 */     algorithmHash.put("http://www.w3.org/2000/09/xmldsig#rsa-sha1", SignatureBaseRSA.SignatureRSASHA1.class);
/*     */ 
/*     */     
/* 399 */     algorithmHash.put("http://www.w3.org/2000/09/xmldsig#hmac-sha1", IntegrityHmac.IntegrityHmacSHA1.class);
/*     */ 
/*     */     
/* 402 */     algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#rsa-md5", SignatureBaseRSA.SignatureRSAMD5.class);
/*     */ 
/*     */ 
/*     */     
/* 406 */     algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#rsa-ripemd160", SignatureBaseRSA.SignatureRSARIPEMD160.class);
/*     */ 
/*     */ 
/*     */     
/* 410 */     algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256", SignatureBaseRSA.SignatureRSASHA256.class);
/*     */ 
/*     */     
/* 413 */     algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#rsa-sha384", SignatureBaseRSA.SignatureRSASHA384.class);
/*     */ 
/*     */     
/* 416 */     algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#rsa-sha512", SignatureBaseRSA.SignatureRSASHA512.class);
/*     */ 
/*     */     
/* 419 */     algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1", SignatureECDSA.SignatureECDSASHA1.class);
/*     */ 
/*     */     
/* 422 */     algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256", SignatureECDSA.SignatureECDSASHA256.class);
/*     */ 
/*     */     
/* 425 */     algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha384", SignatureECDSA.SignatureECDSASHA384.class);
/*     */ 
/*     */     
/* 428 */     algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha512", SignatureECDSA.SignatureECDSASHA512.class);
/*     */ 
/*     */     
/* 431 */     algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#hmac-md5", IntegrityHmac.IntegrityHmacMD5.class);
/*     */ 
/*     */     
/* 434 */     algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#hmac-ripemd160", IntegrityHmac.IntegrityHmacRIPEMD160.class);
/*     */ 
/*     */     
/* 437 */     algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#hmac-sha256", IntegrityHmac.IntegrityHmacSHA256.class);
/*     */ 
/*     */     
/* 440 */     algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#hmac-sha384", IntegrityHmac.IntegrityHmacSHA384.class);
/*     */ 
/*     */     
/* 443 */     algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#hmac-sha512", IntegrityHmac.IntegrityHmacSHA512.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBaseNamespace() {
/* 454 */     return "http://www.w3.org/2000/09/xmldsig#";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBaseLocalName() {
/* 463 */     return "SignatureMethod";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/algorithms/SignatureAlgorithm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */