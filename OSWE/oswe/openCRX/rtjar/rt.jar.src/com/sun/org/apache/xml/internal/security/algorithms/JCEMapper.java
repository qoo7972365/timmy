/*     */ package com.sun.org.apache.xml.internal.security.algorithms;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.utils.JavaUtils;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class JCEMapper
/*     */ {
/*  41 */   private static Logger log = Logger.getLogger(JCEMapper.class.getName());
/*     */   
/*  43 */   private static Map<String, Algorithm> algorithmsMap = new ConcurrentHashMap<>();
/*     */ 
/*     */   
/*  46 */   private static String providerName = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register(String paramString, Algorithm paramAlgorithm) {
/*  57 */     JavaUtils.checkRegisterPermission();
/*  58 */     algorithmsMap.put(paramString, paramAlgorithm);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerDefaultAlgorithms() {
/*  65 */     algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#md5", new Algorithm("", "MD5", "MessageDigest"));
/*     */ 
/*     */ 
/*     */     
/*  69 */     algorithmsMap.put("http://www.w3.org/2001/04/xmlenc#ripemd160", new Algorithm("", "RIPEMD160", "MessageDigest"));
/*     */ 
/*     */ 
/*     */     
/*  73 */     algorithmsMap.put("http://www.w3.org/2000/09/xmldsig#sha1", new Algorithm("", "SHA-1", "MessageDigest"));
/*     */ 
/*     */ 
/*     */     
/*  77 */     algorithmsMap.put("http://www.w3.org/2001/04/xmlenc#sha256", new Algorithm("", "SHA-256", "MessageDigest"));
/*     */ 
/*     */ 
/*     */     
/*  81 */     algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#sha384", new Algorithm("", "SHA-384", "MessageDigest"));
/*     */ 
/*     */ 
/*     */     
/*  85 */     algorithmsMap.put("http://www.w3.org/2001/04/xmlenc#sha512", new Algorithm("", "SHA-512", "MessageDigest"));
/*     */ 
/*     */ 
/*     */     
/*  89 */     algorithmsMap.put("http://www.w3.org/2000/09/xmldsig#dsa-sha1", new Algorithm("", "SHA1withDSA", "Signature"));
/*     */ 
/*     */ 
/*     */     
/*  93 */     algorithmsMap.put("http://www.w3.org/2009/xmldsig11#dsa-sha256", new Algorithm("", "SHA256withDSA", "Signature"));
/*     */ 
/*     */ 
/*     */     
/*  97 */     algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#rsa-md5", new Algorithm("", "MD5withRSA", "Signature"));
/*     */ 
/*     */ 
/*     */     
/* 101 */     algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#rsa-ripemd160", new Algorithm("", "RIPEMD160withRSA", "Signature"));
/*     */ 
/*     */ 
/*     */     
/* 105 */     algorithmsMap.put("http://www.w3.org/2000/09/xmldsig#rsa-sha1", new Algorithm("", "SHA1withRSA", "Signature"));
/*     */ 
/*     */ 
/*     */     
/* 109 */     algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256", new Algorithm("", "SHA256withRSA", "Signature"));
/*     */ 
/*     */ 
/*     */     
/* 113 */     algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#rsa-sha384", new Algorithm("", "SHA384withRSA", "Signature"));
/*     */ 
/*     */ 
/*     */     
/* 117 */     algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#rsa-sha512", new Algorithm("", "SHA512withRSA", "Signature"));
/*     */ 
/*     */ 
/*     */     
/* 121 */     algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1", new Algorithm("", "SHA1withECDSA", "Signature"));
/*     */ 
/*     */ 
/*     */     
/* 125 */     algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256", new Algorithm("", "SHA256withECDSA", "Signature"));
/*     */ 
/*     */ 
/*     */     
/* 129 */     algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha384", new Algorithm("", "SHA384withECDSA", "Signature"));
/*     */ 
/*     */ 
/*     */     
/* 133 */     algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha512", new Algorithm("", "SHA512withECDSA", "Signature"));
/*     */ 
/*     */ 
/*     */     
/* 137 */     algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#hmac-md5", new Algorithm("", "HmacMD5", "Mac"));
/*     */ 
/*     */ 
/*     */     
/* 141 */     algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#hmac-ripemd160", new Algorithm("", "HMACRIPEMD160", "Mac"));
/*     */ 
/*     */ 
/*     */     
/* 145 */     algorithmsMap.put("http://www.w3.org/2000/09/xmldsig#hmac-sha1", new Algorithm("", "HmacSHA1", "Mac"));
/*     */ 
/*     */ 
/*     */     
/* 149 */     algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#hmac-sha256", new Algorithm("", "HmacSHA256", "Mac"));
/*     */ 
/*     */ 
/*     */     
/* 153 */     algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#hmac-sha384", new Algorithm("", "HmacSHA384", "Mac"));
/*     */ 
/*     */ 
/*     */     
/* 157 */     algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#hmac-sha512", new Algorithm("", "HmacSHA512", "Mac"));
/*     */ 
/*     */ 
/*     */     
/* 161 */     algorithmsMap.put("http://www.w3.org/2001/04/xmlenc#tripledes-cbc", new Algorithm("DESede", "DESede/CBC/ISO10126Padding", "BlockEncryption", 192));
/*     */ 
/*     */ 
/*     */     
/* 165 */     algorithmsMap.put("http://www.w3.org/2001/04/xmlenc#aes128-cbc", new Algorithm("AES", "AES/CBC/ISO10126Padding", "BlockEncryption", 128));
/*     */ 
/*     */ 
/*     */     
/* 169 */     algorithmsMap.put("http://www.w3.org/2001/04/xmlenc#aes192-cbc", new Algorithm("AES", "AES/CBC/ISO10126Padding", "BlockEncryption", 192));
/*     */ 
/*     */ 
/*     */     
/* 173 */     algorithmsMap.put("http://www.w3.org/2001/04/xmlenc#aes256-cbc", new Algorithm("AES", "AES/CBC/ISO10126Padding", "BlockEncryption", 256));
/*     */ 
/*     */ 
/*     */     
/* 177 */     algorithmsMap.put("http://www.w3.org/2009/xmlenc11#aes128-gcm", new Algorithm("AES", "AES/GCM/NoPadding", "BlockEncryption", 128));
/*     */ 
/*     */ 
/*     */     
/* 181 */     algorithmsMap.put("http://www.w3.org/2009/xmlenc11#aes192-gcm", new Algorithm("AES", "AES/GCM/NoPadding", "BlockEncryption", 192));
/*     */ 
/*     */ 
/*     */     
/* 185 */     algorithmsMap.put("http://www.w3.org/2009/xmlenc11#aes256-gcm", new Algorithm("AES", "AES/GCM/NoPadding", "BlockEncryption", 256));
/*     */ 
/*     */ 
/*     */     
/* 189 */     algorithmsMap.put("http://www.w3.org/2001/04/xmlenc#rsa-1_5", new Algorithm("RSA", "RSA/ECB/PKCS1Padding", "KeyTransport"));
/*     */ 
/*     */ 
/*     */     
/* 193 */     algorithmsMap.put("http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p", new Algorithm("RSA", "RSA/ECB/OAEPPadding", "KeyTransport"));
/*     */ 
/*     */ 
/*     */     
/* 197 */     algorithmsMap.put("http://www.w3.org/2009/xmlenc11#rsa-oaep", new Algorithm("RSA", "RSA/ECB/OAEPPadding", "KeyTransport"));
/*     */ 
/*     */ 
/*     */     
/* 201 */     algorithmsMap.put("http://www.w3.org/2001/04/xmlenc#dh", new Algorithm("", "", "KeyAgreement"));
/*     */ 
/*     */ 
/*     */     
/* 205 */     algorithmsMap.put("http://www.w3.org/2001/04/xmlenc#kw-tripledes", new Algorithm("DESede", "DESedeWrap", "SymmetricKeyWrap", 192));
/*     */ 
/*     */ 
/*     */     
/* 209 */     algorithmsMap.put("http://www.w3.org/2001/04/xmlenc#kw-aes128", new Algorithm("AES", "AESWrap", "SymmetricKeyWrap", 128));
/*     */ 
/*     */ 
/*     */     
/* 213 */     algorithmsMap.put("http://www.w3.org/2001/04/xmlenc#kw-aes192", new Algorithm("AES", "AESWrap", "SymmetricKeyWrap", 192));
/*     */ 
/*     */ 
/*     */     
/* 217 */     algorithmsMap.put("http://www.w3.org/2001/04/xmlenc#kw-aes256", new Algorithm("AES", "AESWrap", "SymmetricKeyWrap", 256));
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
/*     */   public static String translateURItoJCEID(String paramString) {
/* 230 */     if (log.isLoggable(Level.FINE)) {
/* 231 */       log.log(Level.FINE, "Request for URI " + paramString);
/*     */     }
/*     */     
/* 234 */     Algorithm algorithm = algorithmsMap.get(paramString);
/* 235 */     if (algorithm != null) {
/* 236 */       return algorithm.jceName;
/*     */     }
/* 238 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getAlgorithmClassFromURI(String paramString) {
/* 247 */     if (log.isLoggable(Level.FINE)) {
/* 248 */       log.log(Level.FINE, "Request for URI " + paramString);
/*     */     }
/*     */     
/* 251 */     Algorithm algorithm = algorithmsMap.get(paramString);
/* 252 */     if (algorithm != null) {
/* 253 */       return algorithm.algorithmClass;
/*     */     }
/* 255 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getKeyLengthFromURI(String paramString) {
/* 265 */     if (log.isLoggable(Level.FINE)) {
/* 266 */       log.log(Level.FINE, "Request for URI " + paramString);
/*     */     }
/* 268 */     Algorithm algorithm = algorithmsMap.get(paramString);
/* 269 */     if (algorithm != null) {
/* 270 */       return algorithm.keyLength;
/*     */     }
/* 272 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getJCEKeyAlgorithmFromURI(String paramString) {
/* 282 */     if (log.isLoggable(Level.FINE)) {
/* 283 */       log.log(Level.FINE, "Request for URI " + paramString);
/*     */     }
/* 285 */     Algorithm algorithm = algorithmsMap.get(paramString);
/* 286 */     if (algorithm != null) {
/* 287 */       return algorithm.requiredKey;
/*     */     }
/* 289 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getProviderId() {
/* 297 */     return providerName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setProviderId(String paramString) {
/* 307 */     JavaUtils.checkRegisterPermission();
/* 308 */     providerName = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Algorithm
/*     */   {
/*     */     final String requiredKey;
/*     */ 
/*     */     
/*     */     final String jceName;
/*     */     
/*     */     final String algorithmClass;
/*     */     
/*     */     final int keyLength;
/*     */ 
/*     */     
/*     */     public Algorithm(Element param1Element) {
/* 326 */       this.requiredKey = param1Element.getAttribute("RequiredKey");
/* 327 */       this.jceName = param1Element.getAttribute("JCEName");
/* 328 */       this.algorithmClass = param1Element.getAttribute("AlgorithmClass");
/* 329 */       if (param1Element.hasAttribute("KeyLength")) {
/* 330 */         this.keyLength = Integer.parseInt(param1Element.getAttribute("KeyLength"));
/*     */       } else {
/* 332 */         this.keyLength = 0;
/*     */       } 
/*     */     }
/*     */     
/*     */     public Algorithm(String param1String1, String param1String2) {
/* 337 */       this(param1String1, param1String2, null, 0);
/*     */     }
/*     */     
/*     */     public Algorithm(String param1String1, String param1String2, String param1String3) {
/* 341 */       this(param1String1, param1String2, param1String3, 0);
/*     */     }
/*     */     
/*     */     public Algorithm(String param1String1, String param1String2, int param1Int) {
/* 345 */       this(param1String1, param1String2, null, param1Int);
/*     */     }
/*     */     
/*     */     public Algorithm(String param1String1, String param1String2, String param1String3, int param1Int) {
/* 349 */       this.requiredKey = param1String1;
/* 350 */       this.jceName = param1String2;
/* 351 */       this.algorithmClass = param1String3;
/* 352 */       this.keyLength = param1Int;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/algorithms/JCEMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */