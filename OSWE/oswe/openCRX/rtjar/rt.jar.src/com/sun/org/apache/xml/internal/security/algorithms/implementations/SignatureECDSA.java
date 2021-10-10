/*     */ package com.sun.org.apache.xml.internal.security.algorithms.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.algorithms.JCEMapper;
/*     */ import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithmSpi;
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.Base64;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SignatureECDSA
/*     */   extends SignatureAlgorithmSpi
/*     */ {
/*  52 */   private static Logger log = Logger.getLogger(SignatureECDSA.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   private Signature signatureAlgorithm = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String engineGetURI();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] convertASN1toXMLDSIG(byte[] paramArrayOfbyte) throws IOException {
/*     */     byte b;
/*  75 */     if (paramArrayOfbyte.length < 8 || paramArrayOfbyte[0] != 48) {
/*  76 */       throw new IOException("Invalid ASN.1 format of ECDSA signature");
/*     */     }
/*     */     
/*  79 */     if (paramArrayOfbyte[1] > 0) {
/*  80 */       b = 2;
/*  81 */     } else if (paramArrayOfbyte[1] == -127) {
/*  82 */       b = 3;
/*     */     } else {
/*  84 */       throw new IOException("Invalid ASN.1 format of ECDSA signature");
/*     */     } 
/*     */     
/*  87 */     byte b1 = paramArrayOfbyte[b + 1];
/*     */     
/*     */     byte b2;
/*  90 */     for (b2 = b1; b2 > 0 && paramArrayOfbyte[b + 2 + b1 - b2] == 0; b2--);
/*     */     
/*  92 */     byte b3 = paramArrayOfbyte[b + 2 + b1 + 1];
/*     */ 
/*     */     
/*  95 */     byte b4 = b3;
/*  96 */     for (; b4 > 0 && paramArrayOfbyte[b + 2 + b1 + 2 + b3 - b4] == 0; b4--);
/*     */     
/*  98 */     int i = Math.max(b2, b4);
/*     */     
/* 100 */     if ((paramArrayOfbyte[b - 1] & 0xFF) != paramArrayOfbyte.length - b || (paramArrayOfbyte[b - 1] & 0xFF) != 2 + b1 + 2 + b3 || paramArrayOfbyte[b] != 2 || paramArrayOfbyte[b + 2 + b1] != 2)
/*     */     {
/*     */ 
/*     */       
/* 104 */       throw new IOException("Invalid ASN.1 format of ECDSA signature");
/*     */     }
/* 106 */     byte[] arrayOfByte = new byte[2 * i];
/*     */     
/* 108 */     System.arraycopy(paramArrayOfbyte, b + 2 + b1 - b2, arrayOfByte, i - b2, b2);
/* 109 */     System.arraycopy(paramArrayOfbyte, b + 2 + b1 + 2 + b3 - b4, arrayOfByte, 2 * i - b4, b4);
/*     */ 
/*     */     
/* 112 */     return arrayOfByte;
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
/*     */   public static byte[] convertXMLDSIGtoASN1(byte[] paramArrayOfbyte) throws IOException {
/*     */     int i2;
/*     */     byte[] arrayOfByte;
/* 130 */     int i = paramArrayOfbyte.length / 2;
/*     */     
/*     */     int j;
/*     */     
/* 134 */     for (j = i; j > 0 && paramArrayOfbyte[i - j] == 0; j--);
/*     */     
/* 136 */     int k = j;
/*     */     
/* 138 */     if (paramArrayOfbyte[i - j] < 0) {
/* 139 */       k++;
/*     */     }
/*     */     
/*     */     int m;
/*     */     
/* 144 */     for (m = i; m > 0 && paramArrayOfbyte[2 * i - m] == 0; m--);
/*     */     
/* 146 */     int n = m;
/*     */     
/* 148 */     if (paramArrayOfbyte[2 * i - m] < 0) {
/* 149 */       n++;
/*     */     }
/*     */     
/* 152 */     int i1 = 2 + k + 2 + n;
/* 153 */     if (i1 > 255) {
/* 154 */       throw new IOException("Invalid XMLDSIG format of ECDSA signature");
/*     */     }
/*     */ 
/*     */     
/* 158 */     if (i1 < 128) {
/* 159 */       arrayOfByte = new byte[4 + k + 2 + n];
/* 160 */       i2 = 1;
/*     */     } else {
/* 162 */       arrayOfByte = new byte[5 + k + 2 + n];
/* 163 */       arrayOfByte[1] = -127;
/* 164 */       i2 = 2;
/*     */     } 
/* 166 */     arrayOfByte[0] = 48;
/* 167 */     arrayOfByte[i2++] = (byte)i1;
/* 168 */     arrayOfByte[i2++] = 2;
/* 169 */     arrayOfByte[i2++] = (byte)k;
/*     */     
/* 171 */     System.arraycopy(paramArrayOfbyte, i - j, arrayOfByte, i2 + k - j, j);
/*     */     
/* 173 */     i2 += k;
/*     */     
/* 175 */     arrayOfByte[i2++] = 2;
/* 176 */     arrayOfByte[i2++] = (byte)n;
/*     */     
/* 178 */     System.arraycopy(paramArrayOfbyte, 2 * i - m, arrayOfByte, i2 + n - m, m);
/*     */     
/* 180 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureECDSA() throws XMLSignatureException {
/* 190 */     String str1 = JCEMapper.translateURItoJCEID(engineGetURI());
/*     */     
/* 192 */     if (log.isLoggable(Level.FINE)) {
/* 193 */       log.log(Level.FINE, "Created SignatureECDSA using " + str1);
/*     */     }
/* 195 */     String str2 = JCEMapper.getProviderId();
/*     */     try {
/* 197 */       if (str2 == null) {
/* 198 */         this.signatureAlgorithm = Signature.getInstance(str1);
/*     */       } else {
/* 200 */         this.signatureAlgorithm = Signature.getInstance(str1, str2);
/*     */       } 
/* 202 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 203 */       Object[] arrayOfObject = { str1, noSuchAlgorithmException.getLocalizedMessage() };
/*     */       
/* 205 */       throw new XMLSignatureException("algorithms.NoSuchAlgorithm", arrayOfObject);
/* 206 */     } catch (NoSuchProviderException noSuchProviderException) {
/* 207 */       Object[] arrayOfObject = { str1, noSuchProviderException.getLocalizedMessage() };
/*     */       
/* 209 */       throw new XMLSignatureException("algorithms.NoSuchAlgorithm", arrayOfObject);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineSetParameter(AlgorithmParameterSpec paramAlgorithmParameterSpec) throws XMLSignatureException {
/*     */     try {
/* 217 */       this.signatureAlgorithm.setParameter(paramAlgorithmParameterSpec);
/* 218 */     } catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
/* 219 */       throw new XMLSignatureException("empty", invalidAlgorithmParameterException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean engineVerify(byte[] paramArrayOfbyte) throws XMLSignatureException {
/*     */     try {
/* 226 */       byte[] arrayOfByte = convertXMLDSIGtoASN1(paramArrayOfbyte);
/*     */       
/* 228 */       if (log.isLoggable(Level.FINE)) {
/* 229 */         log.log(Level.FINE, "Called ECDSA.verify() on " + Base64.encode(paramArrayOfbyte));
/*     */       }
/*     */       
/* 232 */       return this.signatureAlgorithm.verify(arrayOfByte);
/* 233 */     } catch (SignatureException signatureException) {
/* 234 */       throw new XMLSignatureException("empty", signatureException);
/* 235 */     } catch (IOException iOException) {
/* 236 */       throw new XMLSignatureException("empty", iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineInitVerify(Key paramKey) throws XMLSignatureException {
/* 243 */     if (!(paramKey instanceof PublicKey)) {
/* 244 */       String str1 = paramKey.getClass().getName();
/* 245 */       String str2 = PublicKey.class.getName();
/* 246 */       Object[] arrayOfObject = { str1, str2 };
/*     */       
/* 248 */       throw new XMLSignatureException("algorithms.WrongKeyForThisOperation", arrayOfObject);
/*     */     } 
/*     */     
/*     */     try {
/* 252 */       this.signatureAlgorithm.initVerify((PublicKey)paramKey);
/* 253 */     } catch (InvalidKeyException invalidKeyException) {
/*     */ 
/*     */       
/* 256 */       Signature signature = this.signatureAlgorithm;
/*     */       try {
/* 258 */         this.signatureAlgorithm = Signature.getInstance(this.signatureAlgorithm.getAlgorithm());
/* 259 */       } catch (Exception exception) {
/*     */ 
/*     */         
/* 262 */         if (log.isLoggable(Level.FINE)) {
/* 263 */           log.log(Level.FINE, "Exception when reinstantiating Signature:" + exception);
/*     */         }
/* 265 */         this.signatureAlgorithm = signature;
/*     */       } 
/* 267 */       throw new XMLSignatureException("empty", invalidKeyException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte[] engineSign() throws XMLSignatureException {
/*     */     try {
/* 274 */       byte[] arrayOfByte = this.signatureAlgorithm.sign();
/*     */       
/* 276 */       return convertASN1toXMLDSIG(arrayOfByte);
/* 277 */     } catch (SignatureException signatureException) {
/* 278 */       throw new XMLSignatureException("empty", signatureException);
/* 279 */     } catch (IOException iOException) {
/* 280 */       throw new XMLSignatureException("empty", iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineInitSign(Key paramKey, SecureRandom paramSecureRandom) throws XMLSignatureException {
/* 287 */     if (!(paramKey instanceof PrivateKey)) {
/* 288 */       String str1 = paramKey.getClass().getName();
/* 289 */       String str2 = PrivateKey.class.getName();
/* 290 */       Object[] arrayOfObject = { str1, str2 };
/*     */       
/* 292 */       throw new XMLSignatureException("algorithms.WrongKeyForThisOperation", arrayOfObject);
/*     */     } 
/*     */     
/*     */     try {
/* 296 */       this.signatureAlgorithm.initSign((PrivateKey)paramKey, paramSecureRandom);
/* 297 */     } catch (InvalidKeyException invalidKeyException) {
/* 298 */       throw new XMLSignatureException("empty", invalidKeyException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void engineInitSign(Key paramKey) throws XMLSignatureException {
/* 304 */     if (!(paramKey instanceof PrivateKey)) {
/* 305 */       String str1 = paramKey.getClass().getName();
/* 306 */       String str2 = PrivateKey.class.getName();
/* 307 */       Object[] arrayOfObject = { str1, str2 };
/*     */       
/* 309 */       throw new XMLSignatureException("algorithms.WrongKeyForThisOperation", arrayOfObject);
/*     */     } 
/*     */     
/*     */     try {
/* 313 */       this.signatureAlgorithm.initSign((PrivateKey)paramKey);
/* 314 */     } catch (InvalidKeyException invalidKeyException) {
/* 315 */       throw new XMLSignatureException("empty", invalidKeyException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void engineUpdate(byte[] paramArrayOfbyte) throws XMLSignatureException {
/*     */     try {
/* 322 */       this.signatureAlgorithm.update(paramArrayOfbyte);
/* 323 */     } catch (SignatureException signatureException) {
/* 324 */       throw new XMLSignatureException("empty", signatureException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void engineUpdate(byte paramByte) throws XMLSignatureException {
/*     */     try {
/* 331 */       this.signatureAlgorithm.update(paramByte);
/* 332 */     } catch (SignatureException signatureException) {
/* 333 */       throw new XMLSignatureException("empty", signatureException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void engineUpdate(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws XMLSignatureException {
/*     */     try {
/* 340 */       this.signatureAlgorithm.update(paramArrayOfbyte, paramInt1, paramInt2);
/* 341 */     } catch (SignatureException signatureException) {
/* 342 */       throw new XMLSignatureException("empty", signatureException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected String engineGetJCEAlgorithmString() {
/* 348 */     return this.signatureAlgorithm.getAlgorithm();
/*     */   }
/*     */ 
/*     */   
/*     */   protected String engineGetJCEProviderName() {
/* 353 */     return this.signatureAlgorithm.getProvider().getName();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineSetHMACOutputLength(int paramInt) throws XMLSignatureException {
/* 359 */     throw new XMLSignatureException("algorithms.HMACOutputLengthOnlyForHMAC");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineInitSign(Key paramKey, AlgorithmParameterSpec paramAlgorithmParameterSpec) throws XMLSignatureException {
/* 366 */     throw new XMLSignatureException("algorithms.CannotUseAlgorithmParameterSpecOnRSA");
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
/*     */   public static class SignatureECDSASHA1
/*     */     extends SignatureECDSA
/*     */   {
/*     */     public String engineGetURI() {
/* 386 */       return "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1";
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
/*     */   public static class SignatureECDSASHA256
/*     */     extends SignatureECDSA
/*     */   {
/*     */     public String engineGetURI() {
/* 408 */       return "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256";
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
/*     */   public static class SignatureECDSASHA384
/*     */     extends SignatureECDSA
/*     */   {
/*     */     public String engineGetURI() {
/* 430 */       return "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha384";
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
/*     */   public static class SignatureECDSASHA512
/*     */     extends SignatureECDSA
/*     */   {
/*     */     public String engineGetURI() {
/* 452 */       return "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha512";
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/algorithms/implementations/SignatureECDSA.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */