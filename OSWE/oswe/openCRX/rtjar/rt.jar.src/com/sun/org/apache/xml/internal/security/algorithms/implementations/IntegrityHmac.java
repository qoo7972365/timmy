/*     */ package com.sun.org.apache.xml.internal.security.algorithms.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.algorithms.JCEMapper;
/*     */ import com.sun.org.apache.xml.internal.security.algorithms.MessageDigestAlgorithm;
/*     */ import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithmSpi;
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.Key;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.crypto.Mac;
/*     */ import javax.crypto.SecretKey;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class IntegrityHmac
/*     */   extends SignatureAlgorithmSpi
/*     */ {
/*  49 */   private static Logger log = Logger.getLogger(IntegrityHmac.class.getName());
/*     */ 
/*     */   
/*  52 */   private Mac macAlgorithm = null;
/*     */ 
/*     */   
/*  55 */   private int HMACOutputLength = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean HMACOutputLengthSet = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String engineGetURI();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract int getDigestLength();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntegrityHmac() throws XMLSignatureException {
/*  76 */     String str = JCEMapper.translateURItoJCEID(engineGetURI());
/*  77 */     if (log.isLoggable(Level.FINE)) {
/*  78 */       log.log(Level.FINE, "Created IntegrityHmacSHA1 using " + str);
/*     */     }
/*     */     
/*     */     try {
/*  82 */       this.macAlgorithm = Mac.getInstance(str);
/*  83 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*  84 */       Object[] arrayOfObject = { str, noSuchAlgorithmException.getLocalizedMessage() };
/*     */       
/*  86 */       throw new XMLSignatureException("algorithms.NoSuchAlgorithm", arrayOfObject);
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
/*     */   protected void engineSetParameter(AlgorithmParameterSpec paramAlgorithmParameterSpec) throws XMLSignatureException {
/*  99 */     throw new XMLSignatureException("empty");
/*     */   }
/*     */   
/*     */   public void reset() {
/* 103 */     this.HMACOutputLength = 0;
/* 104 */     this.HMACOutputLengthSet = false;
/* 105 */     this.macAlgorithm.reset();
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
/*     */   protected boolean engineVerify(byte[] paramArrayOfbyte) throws XMLSignatureException {
/*     */     try {
/* 118 */       if (this.HMACOutputLengthSet && this.HMACOutputLength < getDigestLength()) {
/* 119 */         if (log.isLoggable(Level.FINE)) {
/* 120 */           log.log(Level.FINE, "HMACOutputLength must not be less than " + getDigestLength());
/*     */         }
/* 122 */         Object[] arrayOfObject = { String.valueOf(getDigestLength()) };
/* 123 */         throw new XMLSignatureException("algorithms.HMACOutputLengthMin", arrayOfObject);
/*     */       } 
/* 125 */       byte[] arrayOfByte = this.macAlgorithm.doFinal();
/* 126 */       return MessageDigestAlgorithm.isEqual(arrayOfByte, paramArrayOfbyte);
/*     */     }
/* 128 */     catch (IllegalStateException illegalStateException) {
/* 129 */       throw new XMLSignatureException("empty", illegalStateException);
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
/*     */   protected void engineInitVerify(Key paramKey) throws XMLSignatureException {
/* 141 */     if (!(paramKey instanceof SecretKey)) {
/* 142 */       String str1 = paramKey.getClass().getName();
/* 143 */       String str2 = SecretKey.class.getName();
/* 144 */       Object[] arrayOfObject = { str1, str2 };
/*     */       
/* 146 */       throw new XMLSignatureException("algorithms.WrongKeyForThisOperation", arrayOfObject);
/*     */     } 
/*     */     
/*     */     try {
/* 150 */       this.macAlgorithm.init(paramKey);
/* 151 */     } catch (InvalidKeyException invalidKeyException) {
/*     */ 
/*     */       
/* 154 */       Mac mac = this.macAlgorithm;
/*     */       try {
/* 156 */         this.macAlgorithm = Mac.getInstance(this.macAlgorithm.getAlgorithm());
/* 157 */       } catch (Exception exception) {
/*     */         
/* 159 */         if (log.isLoggable(Level.FINE)) {
/* 160 */           log.log(Level.FINE, "Exception when reinstantiating Mac:" + exception);
/*     */         }
/* 162 */         this.macAlgorithm = mac;
/*     */       } 
/* 164 */       throw new XMLSignatureException("empty", invalidKeyException);
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
/*     */   protected byte[] engineSign() throws XMLSignatureException {
/*     */     try {
/* 177 */       if (this.HMACOutputLengthSet && this.HMACOutputLength < getDigestLength()) {
/* 178 */         if (log.isLoggable(Level.FINE)) {
/* 179 */           log.log(Level.FINE, "HMACOutputLength must not be less than " + getDigestLength());
/*     */         }
/* 181 */         Object[] arrayOfObject = { String.valueOf(getDigestLength()) };
/* 182 */         throw new XMLSignatureException("algorithms.HMACOutputLengthMin", arrayOfObject);
/*     */       } 
/* 184 */       return this.macAlgorithm.doFinal();
/*     */     }
/* 186 */     catch (IllegalStateException illegalStateException) {
/* 187 */       throw new XMLSignatureException("empty", illegalStateException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineInitSign(Key paramKey) throws XMLSignatureException {
/* 198 */     if (!(paramKey instanceof SecretKey)) {
/* 199 */       String str1 = paramKey.getClass().getName();
/* 200 */       String str2 = SecretKey.class.getName();
/* 201 */       Object[] arrayOfObject = { str1, str2 };
/*     */       
/* 203 */       throw new XMLSignatureException("algorithms.WrongKeyForThisOperation", arrayOfObject);
/*     */     } 
/*     */     
/*     */     try {
/* 207 */       this.macAlgorithm.init(paramKey);
/* 208 */     } catch (InvalidKeyException invalidKeyException) {
/* 209 */       throw new XMLSignatureException("empty", invalidKeyException);
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
/*     */   protected void engineInitSign(Key paramKey, AlgorithmParameterSpec paramAlgorithmParameterSpec) throws XMLSignatureException {
/* 223 */     if (!(paramKey instanceof SecretKey)) {
/* 224 */       String str1 = paramKey.getClass().getName();
/* 225 */       String str2 = SecretKey.class.getName();
/* 226 */       Object[] arrayOfObject = { str1, str2 };
/*     */       
/* 228 */       throw new XMLSignatureException("algorithms.WrongKeyForThisOperation", arrayOfObject);
/*     */     } 
/*     */     
/*     */     try {
/* 232 */       this.macAlgorithm.init(paramKey, paramAlgorithmParameterSpec);
/* 233 */     } catch (InvalidKeyException invalidKeyException) {
/* 234 */       throw new XMLSignatureException("empty", invalidKeyException);
/* 235 */     } catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
/* 236 */       throw new XMLSignatureException("empty", invalidAlgorithmParameterException);
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
/*     */   protected void engineInitSign(Key paramKey, SecureRandom paramSecureRandom) throws XMLSignatureException {
/* 249 */     throw new XMLSignatureException("algorithms.CannotUseSecureRandomOnMAC");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineUpdate(byte[] paramArrayOfbyte) throws XMLSignatureException {
/*     */     try {
/* 261 */       this.macAlgorithm.update(paramArrayOfbyte);
/* 262 */     } catch (IllegalStateException illegalStateException) {
/* 263 */       throw new XMLSignatureException("empty", illegalStateException);
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
/*     */   protected void engineUpdate(byte paramByte) throws XMLSignatureException {
/*     */     try {
/* 276 */       this.macAlgorithm.update(paramByte);
/* 277 */     } catch (IllegalStateException illegalStateException) {
/* 278 */       throw new XMLSignatureException("empty", illegalStateException);
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
/*     */   protected void engineUpdate(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws XMLSignatureException {
/*     */     try {
/* 293 */       this.macAlgorithm.update(paramArrayOfbyte, paramInt1, paramInt2);
/* 294 */     } catch (IllegalStateException illegalStateException) {
/* 295 */       throw new XMLSignatureException("empty", illegalStateException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String engineGetJCEAlgorithmString() {
/* 305 */     return this.macAlgorithm.getAlgorithm();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String engineGetJCEProviderName() {
/* 314 */     return this.macAlgorithm.getProvider().getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineSetHMACOutputLength(int paramInt) {
/* 323 */     this.HMACOutputLength = paramInt;
/* 324 */     this.HMACOutputLengthSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineGetContextFromElement(Element paramElement) {
/* 333 */     super.engineGetContextFromElement(paramElement);
/*     */     
/* 335 */     if (paramElement == null) {
/* 336 */       throw new IllegalArgumentException("element null");
/*     */     }
/*     */ 
/*     */     
/* 340 */     Text text = XMLUtils.selectDsNodeText(paramElement.getFirstChild(), "HMACOutputLength", 0);
/*     */     
/* 342 */     if (text != null) {
/* 343 */       this.HMACOutputLength = Integer.parseInt(text.getData());
/* 344 */       this.HMACOutputLengthSet = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void engineAddContextToElement(Element paramElement) {
/* 354 */     if (paramElement == null) {
/* 355 */       throw new IllegalArgumentException("null element");
/*     */     }
/*     */     
/* 358 */     if (this.HMACOutputLengthSet) {
/* 359 */       Document document = paramElement.getOwnerDocument();
/*     */       
/* 361 */       Element element = XMLUtils.createElementInSignatureSpace(document, "HMACOutputLength");
/*     */       
/* 363 */       Text text = document.createTextNode(Integer.valueOf(this.HMACOutputLength).toString());
/*     */       
/* 365 */       element.appendChild(text);
/* 366 */       XMLUtils.addReturnToElement(paramElement);
/* 367 */       paramElement.appendChild(element);
/* 368 */       XMLUtils.addReturnToElement(paramElement);
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
/*     */ 
/*     */   
/*     */   public static class IntegrityHmacSHA1
/*     */     extends IntegrityHmac
/*     */   {
/*     */     public String engineGetURI() {
/* 392 */       return "http://www.w3.org/2000/09/xmldsig#hmac-sha1";
/*     */     }
/*     */     
/*     */     int getDigestLength() {
/* 396 */       return 160;
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
/*     */ 
/*     */   
/*     */   public static class IntegrityHmacSHA256
/*     */     extends IntegrityHmac
/*     */   {
/*     */     public String engineGetURI() {
/* 420 */       return "http://www.w3.org/2001/04/xmldsig-more#hmac-sha256";
/*     */     }
/*     */     
/*     */     int getDigestLength() {
/* 424 */       return 256;
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
/*     */ 
/*     */   
/*     */   public static class IntegrityHmacSHA384
/*     */     extends IntegrityHmac
/*     */   {
/*     */     public String engineGetURI() {
/* 448 */       return "http://www.w3.org/2001/04/xmldsig-more#hmac-sha384";
/*     */     }
/*     */     
/*     */     int getDigestLength() {
/* 452 */       return 384;
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
/*     */ 
/*     */   
/*     */   public static class IntegrityHmacSHA512
/*     */     extends IntegrityHmac
/*     */   {
/*     */     public String engineGetURI() {
/* 476 */       return "http://www.w3.org/2001/04/xmldsig-more#hmac-sha512";
/*     */     }
/*     */     
/*     */     int getDigestLength() {
/* 480 */       return 512;
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
/*     */ 
/*     */   
/*     */   public static class IntegrityHmacRIPEMD160
/*     */     extends IntegrityHmac
/*     */   {
/*     */     public String engineGetURI() {
/* 504 */       return "http://www.w3.org/2001/04/xmldsig-more#hmac-ripemd160";
/*     */     }
/*     */     
/*     */     int getDigestLength() {
/* 508 */       return 160;
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
/*     */ 
/*     */   
/*     */   public static class IntegrityHmacMD5
/*     */     extends IntegrityHmac
/*     */   {
/*     */     public String engineGetURI() {
/* 532 */       return "http://www.w3.org/2001/04/xmldsig-more#hmac-md5";
/*     */     }
/*     */     
/*     */     int getDigestLength() {
/* 536 */       return 128;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/algorithms/implementations/IntegrityHmac.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */