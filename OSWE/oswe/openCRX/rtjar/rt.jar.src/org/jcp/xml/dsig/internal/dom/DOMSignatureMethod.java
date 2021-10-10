/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.algorithms.implementations.SignatureECDSA;
/*     */ import com.sun.org.apache.xml.internal.security.utils.JavaUtils;
/*     */ import java.io.IOException;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.Key;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.Provider;
/*     */ import java.security.PublicKey;
/*     */ import java.security.Signature;
/*     */ import java.security.SignatureException;
/*     */ import java.security.interfaces.DSAKey;
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.dom.DOMCryptoContext;
/*     */ import javax.xml.crypto.dsig.SignatureMethod;
/*     */ import javax.xml.crypto.dsig.SignedInfo;
/*     */ import javax.xml.crypto.dsig.XMLSignContext;
/*     */ import javax.xml.crypto.dsig.XMLSignatureException;
/*     */ import javax.xml.crypto.dsig.XMLValidateContext;
/*     */ import javax.xml.crypto.dsig.spec.SignatureMethodParameterSpec;
/*     */ import org.jcp.xml.dsig.internal.SignerOutputStream;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import sun.security.util.KeyUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DOMSignatureMethod
/*     */   extends AbstractDOMSignatureMethod
/*     */ {
/*  54 */   private static Logger log = Logger.getLogger("org.jcp.xml.dsig.internal.dom");
/*     */ 
/*     */   
/*     */   private SignatureMethodParameterSpec params;
/*     */ 
/*     */   
/*     */   private Signature signature;
/*     */ 
/*     */   
/*     */   static final String RSA_SHA256 = "http://www.w3.org/2001/04/xmldsig-more#rsa-sha256";
/*     */ 
/*     */   
/*     */   static final String RSA_SHA384 = "http://www.w3.org/2001/04/xmldsig-more#rsa-sha384";
/*     */ 
/*     */   
/*     */   static final String RSA_SHA512 = "http://www.w3.org/2001/04/xmldsig-more#rsa-sha512";
/*     */ 
/*     */   
/*     */   static final String ECDSA_SHA1 = "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1";
/*     */ 
/*     */   
/*     */   static final String ECDSA_SHA256 = "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256";
/*     */ 
/*     */   
/*     */   static final String ECDSA_SHA384 = "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha384";
/*     */ 
/*     */   
/*     */   static final String ECDSA_SHA512 = "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha512";
/*     */   
/*     */   static final String DSA_SHA256 = "http://www.w3.org/2009/xmldsig11#dsa-sha256";
/*     */ 
/*     */   
/*     */   DOMSignatureMethod(AlgorithmParameterSpec paramAlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
/*  87 */     if (paramAlgorithmParameterSpec != null && !(paramAlgorithmParameterSpec instanceof SignatureMethodParameterSpec))
/*     */     {
/*  89 */       throw new InvalidAlgorithmParameterException("params must be of type SignatureMethodParameterSpec");
/*     */     }
/*     */     
/*  92 */     checkParams((SignatureMethodParameterSpec)paramAlgorithmParameterSpec);
/*  93 */     this.params = (SignatureMethodParameterSpec)paramAlgorithmParameterSpec;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DOMSignatureMethod(Element paramElement) throws MarshalException {
/* 104 */     Element element = DOMUtils.getFirstChildElement(paramElement);
/* 105 */     if (element != null) {
/* 106 */       this.params = unmarshalParams(element);
/*     */     }
/*     */     try {
/* 109 */       checkParams(this.params);
/* 110 */     } catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
/* 111 */       throw new MarshalException(invalidAlgorithmParameterException);
/*     */     } 
/*     */   }
/*     */   
/*     */   static SignatureMethod unmarshal(Element paramElement) throws MarshalException {
/* 116 */     String str = DOMUtils.getAttributeValue(paramElement, "Algorithm");
/* 117 */     if (str.equals("http://www.w3.org/2000/09/xmldsig#rsa-sha1"))
/* 118 */       return new SHA1withRSA(paramElement); 
/* 119 */     if (str.equals("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256"))
/* 120 */       return new SHA256withRSA(paramElement); 
/* 121 */     if (str.equals("http://www.w3.org/2001/04/xmldsig-more#rsa-sha384"))
/* 122 */       return new SHA384withRSA(paramElement); 
/* 123 */     if (str.equals("http://www.w3.org/2001/04/xmldsig-more#rsa-sha512"))
/* 124 */       return new SHA512withRSA(paramElement); 
/* 125 */     if (str.equals("http://www.w3.org/2000/09/xmldsig#dsa-sha1"))
/* 126 */       return new SHA1withDSA(paramElement); 
/* 127 */     if (str.equals("http://www.w3.org/2009/xmldsig11#dsa-sha256"))
/* 128 */       return new SHA256withDSA(paramElement); 
/* 129 */     if (str.equals("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1"))
/* 130 */       return new SHA1withECDSA(paramElement); 
/* 131 */     if (str.equals("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256"))
/* 132 */       return new SHA256withECDSA(paramElement); 
/* 133 */     if (str.equals("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha384"))
/* 134 */       return new SHA384withECDSA(paramElement); 
/* 135 */     if (str.equals("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha512"))
/* 136 */       return new SHA512withECDSA(paramElement); 
/* 137 */     if (str.equals("http://www.w3.org/2000/09/xmldsig#hmac-sha1"))
/* 138 */       return new DOMHMACSignatureMethod.SHA1(paramElement); 
/* 139 */     if (str.equals("http://www.w3.org/2001/04/xmldsig-more#hmac-sha256"))
/* 140 */       return new DOMHMACSignatureMethod.SHA256(paramElement); 
/* 141 */     if (str.equals("http://www.w3.org/2001/04/xmldsig-more#hmac-sha384"))
/* 142 */       return new DOMHMACSignatureMethod.SHA384(paramElement); 
/* 143 */     if (str.equals("http://www.w3.org/2001/04/xmldsig-more#hmac-sha512")) {
/* 144 */       return new DOMHMACSignatureMethod.SHA512(paramElement);
/*     */     }
/* 146 */     throw new MarshalException("unsupported SignatureMethod algorithm: " + str);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final AlgorithmParameterSpec getParameterSpec() {
/* 152 */     return this.params;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean verify(Key paramKey, SignedInfo paramSignedInfo, byte[] paramArrayOfbyte, XMLValidateContext paramXMLValidateContext) throws InvalidKeyException, SignatureException, XMLSignatureException {
/* 159 */     if (paramKey == null || paramSignedInfo == null || paramArrayOfbyte == null) {
/* 160 */       throw new NullPointerException();
/*     */     }
/*     */     
/* 163 */     if (!(paramKey instanceof PublicKey)) {
/* 164 */       throw new InvalidKeyException("key must be PublicKey");
/*     */     }
/* 166 */     checkKeySize(paramXMLValidateContext, paramKey);
/* 167 */     if (this.signature == null) {
/*     */       
/*     */       try {
/* 170 */         Provider provider = (Provider)paramXMLValidateContext.getProperty("org.jcp.xml.dsig.internal.dom.SignatureProvider");
/* 171 */         this
/*     */           
/* 173 */           .signature = (provider == null) ? Signature.getInstance(getJCAAlgorithm()) : Signature.getInstance(getJCAAlgorithm(), provider);
/* 174 */       } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 175 */         throw new XMLSignatureException(noSuchAlgorithmException);
/*     */       } 
/*     */     }
/* 178 */     this.signature.initVerify((PublicKey)paramKey);
/* 179 */     if (log.isLoggable(Level.FINE)) {
/* 180 */       log.log(Level.FINE, "Signature provider:" + this.signature.getProvider());
/* 181 */       log.log(Level.FINE, "verifying with key: " + paramKey);
/*     */     } 
/* 183 */     ((DOMSignedInfo)paramSignedInfo).canonicalize(paramXMLValidateContext, new SignerOutputStream(this.signature));
/*     */ 
/*     */     
/*     */     try {
/* 187 */       AbstractDOMSignatureMethod.Type type = getAlgorithmType();
/* 188 */       if (type == AbstractDOMSignatureMethod.Type.DSA) {
/* 189 */         int i = ((DSAKey)paramKey).getParams().getQ().bitLength();
/* 190 */         return this.signature.verify(JavaUtils.convertDsaXMLDSIGtoASN1(paramArrayOfbyte, i / 8));
/*     */       } 
/* 192 */       if (type == AbstractDOMSignatureMethod.Type.ECDSA) {
/* 193 */         return this.signature.verify(SignatureECDSA.convertXMLDSIGtoASN1(paramArrayOfbyte));
/*     */       }
/* 195 */       return this.signature.verify(paramArrayOfbyte);
/*     */     }
/* 197 */     catch (IOException iOException) {
/* 198 */       throw new XMLSignatureException(iOException);
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
/*     */   private static void checkKeySize(XMLCryptoContext paramXMLCryptoContext, Key paramKey) throws XMLSignatureException {
/* 212 */     if (Utils.secureValidation(paramXMLCryptoContext)) {
/* 213 */       int i = KeyUtil.getKeySize(paramKey);
/* 214 */       if (i == -1) {
/*     */ 
/*     */ 
/*     */         
/* 218 */         if (log.isLoggable(Level.FINE)) {
/* 219 */           log.log(Level.FINE, "Size for " + paramKey
/* 220 */               .getAlgorithm() + " key cannot be determined");
/*     */         }
/*     */         return;
/*     */       } 
/* 224 */       if (Policy.restrictKey(paramKey.getAlgorithm(), i)) {
/* 225 */         throw new XMLSignatureException(paramKey.getAlgorithm() + " keys less than " + 
/*     */             
/* 227 */             Policy.minKeySize(paramKey.getAlgorithm()) + " bits are forbidden when secure validation is enabled");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   byte[] sign(Key paramKey, SignedInfo paramSignedInfo, XMLSignContext paramXMLSignContext) throws InvalidKeyException, XMLSignatureException {
/* 236 */     if (paramKey == null || paramSignedInfo == null) {
/* 237 */       throw new NullPointerException();
/*     */     }
/*     */     
/* 240 */     if (!(paramKey instanceof PrivateKey)) {
/* 241 */       throw new InvalidKeyException("key must be PrivateKey");
/*     */     }
/* 243 */     checkKeySize(paramXMLSignContext, paramKey);
/* 244 */     if (this.signature == null) {
/*     */       
/*     */       try {
/* 247 */         Provider provider = (Provider)paramXMLSignContext.getProperty("org.jcp.xml.dsig.internal.dom.SignatureProvider");
/* 248 */         this
/*     */           
/* 250 */           .signature = (provider == null) ? Signature.getInstance(getJCAAlgorithm()) : Signature.getInstance(getJCAAlgorithm(), provider);
/* 251 */       } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 252 */         throw new XMLSignatureException(noSuchAlgorithmException);
/*     */       } 
/*     */     }
/* 255 */     this.signature.initSign((PrivateKey)paramKey);
/* 256 */     if (log.isLoggable(Level.FINE)) {
/* 257 */       log.log(Level.FINE, "Signature provider:" + this.signature.getProvider());
/* 258 */       log.log(Level.FINE, "Signing with key: " + paramKey);
/*     */     } 
/*     */     
/* 261 */     ((DOMSignedInfo)paramSignedInfo).canonicalize(paramXMLSignContext, new SignerOutputStream(this.signature));
/*     */ 
/*     */     
/*     */     try {
/* 265 */       AbstractDOMSignatureMethod.Type type = getAlgorithmType();
/* 266 */       if (type == AbstractDOMSignatureMethod.Type.DSA) {
/* 267 */         int i = ((DSAKey)paramKey).getParams().getQ().bitLength();
/* 268 */         return JavaUtils.convertDsaASN1toXMLDSIG(this.signature.sign(), i / 8);
/*     */       } 
/* 270 */       if (type == AbstractDOMSignatureMethod.Type.ECDSA) {
/* 271 */         return SignatureECDSA.convertASN1toXMLDSIG(this.signature.sign());
/*     */       }
/* 273 */       return this.signature.sign();
/*     */     }
/* 275 */     catch (SignatureException signatureException) {
/* 276 */       throw new XMLSignatureException(signatureException);
/* 277 */     } catch (IOException iOException) {
/* 278 */       throw new XMLSignatureException(iOException);
/*     */     } 
/*     */   }
/*     */   
/*     */   static final class SHA1withRSA
/*     */     extends DOMSignatureMethod {
/*     */     SHA1withRSA(AlgorithmParameterSpec param1AlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
/* 285 */       super(param1AlgorithmParameterSpec);
/*     */     }
/*     */     SHA1withRSA(Element param1Element) throws MarshalException {
/* 288 */       super(param1Element);
/*     */     }
/*     */     public String getAlgorithm() {
/* 291 */       return "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
/*     */     }
/*     */     String getJCAAlgorithm() {
/* 294 */       return "SHA1withRSA";
/*     */     }
/*     */     AbstractDOMSignatureMethod.Type getAlgorithmType() {
/* 297 */       return AbstractDOMSignatureMethod.Type.RSA;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class SHA256withRSA
/*     */     extends DOMSignatureMethod {
/*     */     SHA256withRSA(AlgorithmParameterSpec param1AlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
/* 304 */       super(param1AlgorithmParameterSpec);
/*     */     }
/*     */     SHA256withRSA(Element param1Element) throws MarshalException {
/* 307 */       super(param1Element);
/*     */     }
/*     */     public String getAlgorithm() {
/* 310 */       return "http://www.w3.org/2001/04/xmldsig-more#rsa-sha256";
/*     */     }
/*     */     String getJCAAlgorithm() {
/* 313 */       return "SHA256withRSA";
/*     */     }
/*     */     AbstractDOMSignatureMethod.Type getAlgorithmType() {
/* 316 */       return AbstractDOMSignatureMethod.Type.RSA;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class SHA384withRSA
/*     */     extends DOMSignatureMethod {
/*     */     SHA384withRSA(AlgorithmParameterSpec param1AlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
/* 323 */       super(param1AlgorithmParameterSpec);
/*     */     }
/*     */     SHA384withRSA(Element param1Element) throws MarshalException {
/* 326 */       super(param1Element);
/*     */     }
/*     */     public String getAlgorithm() {
/* 329 */       return "http://www.w3.org/2001/04/xmldsig-more#rsa-sha384";
/*     */     }
/*     */     String getJCAAlgorithm() {
/* 332 */       return "SHA384withRSA";
/*     */     }
/*     */     AbstractDOMSignatureMethod.Type getAlgorithmType() {
/* 335 */       return AbstractDOMSignatureMethod.Type.RSA;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class SHA512withRSA
/*     */     extends DOMSignatureMethod {
/*     */     SHA512withRSA(AlgorithmParameterSpec param1AlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
/* 342 */       super(param1AlgorithmParameterSpec);
/*     */     }
/*     */     SHA512withRSA(Element param1Element) throws MarshalException {
/* 345 */       super(param1Element);
/*     */     }
/*     */     public String getAlgorithm() {
/* 348 */       return "http://www.w3.org/2001/04/xmldsig-more#rsa-sha512";
/*     */     }
/*     */     String getJCAAlgorithm() {
/* 351 */       return "SHA512withRSA";
/*     */     }
/*     */     AbstractDOMSignatureMethod.Type getAlgorithmType() {
/* 354 */       return AbstractDOMSignatureMethod.Type.RSA;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class SHA1withDSA
/*     */     extends DOMSignatureMethod {
/*     */     SHA1withDSA(AlgorithmParameterSpec param1AlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
/* 361 */       super(param1AlgorithmParameterSpec);
/*     */     }
/*     */     SHA1withDSA(Element param1Element) throws MarshalException {
/* 364 */       super(param1Element);
/*     */     }
/*     */     public String getAlgorithm() {
/* 367 */       return "http://www.w3.org/2000/09/xmldsig#dsa-sha1";
/*     */     }
/*     */     String getJCAAlgorithm() {
/* 370 */       return "SHA1withDSA";
/*     */     }
/*     */     AbstractDOMSignatureMethod.Type getAlgorithmType() {
/* 373 */       return AbstractDOMSignatureMethod.Type.DSA;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class SHA256withDSA
/*     */     extends DOMSignatureMethod {
/*     */     SHA256withDSA(AlgorithmParameterSpec param1AlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
/* 380 */       super(param1AlgorithmParameterSpec);
/*     */     }
/*     */     SHA256withDSA(Element param1Element) throws MarshalException {
/* 383 */       super(param1Element);
/*     */     }
/*     */     public String getAlgorithm() {
/* 386 */       return "http://www.w3.org/2009/xmldsig11#dsa-sha256";
/*     */     }
/*     */     String getJCAAlgorithm() {
/* 389 */       return "SHA256withDSA";
/*     */     }
/*     */     AbstractDOMSignatureMethod.Type getAlgorithmType() {
/* 392 */       return AbstractDOMSignatureMethod.Type.DSA;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class SHA1withECDSA
/*     */     extends DOMSignatureMethod {
/*     */     SHA1withECDSA(AlgorithmParameterSpec param1AlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
/* 399 */       super(param1AlgorithmParameterSpec);
/*     */     }
/*     */     SHA1withECDSA(Element param1Element) throws MarshalException {
/* 402 */       super(param1Element);
/*     */     }
/*     */     public String getAlgorithm() {
/* 405 */       return "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1";
/*     */     }
/*     */     String getJCAAlgorithm() {
/* 408 */       return "SHA1withECDSA";
/*     */     }
/*     */     AbstractDOMSignatureMethod.Type getAlgorithmType() {
/* 411 */       return AbstractDOMSignatureMethod.Type.ECDSA;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class SHA256withECDSA
/*     */     extends DOMSignatureMethod {
/*     */     SHA256withECDSA(AlgorithmParameterSpec param1AlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
/* 418 */       super(param1AlgorithmParameterSpec);
/*     */     }
/*     */     SHA256withECDSA(Element param1Element) throws MarshalException {
/* 421 */       super(param1Element);
/*     */     }
/*     */     public String getAlgorithm() {
/* 424 */       return "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256";
/*     */     }
/*     */     String getJCAAlgorithm() {
/* 427 */       return "SHA256withECDSA";
/*     */     }
/*     */     AbstractDOMSignatureMethod.Type getAlgorithmType() {
/* 430 */       return AbstractDOMSignatureMethod.Type.ECDSA;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class SHA384withECDSA
/*     */     extends DOMSignatureMethod {
/*     */     SHA384withECDSA(AlgorithmParameterSpec param1AlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
/* 437 */       super(param1AlgorithmParameterSpec);
/*     */     }
/*     */     SHA384withECDSA(Element param1Element) throws MarshalException {
/* 440 */       super(param1Element);
/*     */     }
/*     */     public String getAlgorithm() {
/* 443 */       return "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha384";
/*     */     }
/*     */     String getJCAAlgorithm() {
/* 446 */       return "SHA384withECDSA";
/*     */     }
/*     */     AbstractDOMSignatureMethod.Type getAlgorithmType() {
/* 449 */       return AbstractDOMSignatureMethod.Type.ECDSA;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class SHA512withECDSA
/*     */     extends DOMSignatureMethod {
/*     */     SHA512withECDSA(AlgorithmParameterSpec param1AlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
/* 456 */       super(param1AlgorithmParameterSpec);
/*     */     }
/*     */     SHA512withECDSA(Element param1Element) throws MarshalException {
/* 459 */       super(param1Element);
/*     */     }
/*     */     public String getAlgorithm() {
/* 462 */       return "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha512";
/*     */     }
/*     */     String getJCAAlgorithm() {
/* 465 */       return "SHA512withECDSA";
/*     */     }
/*     */     AbstractDOMSignatureMethod.Type getAlgorithmType() {
/* 468 */       return AbstractDOMSignatureMethod.Type.ECDSA;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMSignatureMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */