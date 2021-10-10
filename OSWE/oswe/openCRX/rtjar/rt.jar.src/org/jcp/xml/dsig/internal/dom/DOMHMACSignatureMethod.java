/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.Key;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.SignatureException;
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.crypto.Mac;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.dom.DOMCryptoContext;
/*     */ import javax.xml.crypto.dsig.SignedInfo;
/*     */ import javax.xml.crypto.dsig.XMLSignContext;
/*     */ import javax.xml.crypto.dsig.XMLSignatureException;
/*     */ import javax.xml.crypto.dsig.XMLValidateContext;
/*     */ import javax.xml.crypto.dsig.spec.HMACParameterSpec;
/*     */ import javax.xml.crypto.dsig.spec.SignatureMethodParameterSpec;
/*     */ import org.jcp.xml.dsig.internal.MacOutputStream;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DOMHMACSignatureMethod
/*     */   extends AbstractDOMSignatureMethod
/*     */ {
/*  58 */   private static Logger log = Logger.getLogger("org.jcp.xml.dsig.internal.dom");
/*     */ 
/*     */   
/*     */   static final String HMAC_SHA256 = "http://www.w3.org/2001/04/xmldsig-more#hmac-sha256";
/*     */ 
/*     */   
/*     */   static final String HMAC_SHA384 = "http://www.w3.org/2001/04/xmldsig-more#hmac-sha384";
/*     */ 
/*     */   
/*     */   static final String HMAC_SHA512 = "http://www.w3.org/2001/04/xmldsig-more#hmac-sha512";
/*     */ 
/*     */   
/*     */   private Mac hmac;
/*     */ 
/*     */   
/*     */   private int outputLength;
/*     */ 
/*     */   
/*     */   private boolean outputLengthSet;
/*     */   
/*     */   private SignatureMethodParameterSpec params;
/*     */ 
/*     */   
/*     */   DOMHMACSignatureMethod(AlgorithmParameterSpec paramAlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
/*  82 */     checkParams((SignatureMethodParameterSpec)paramAlgorithmParameterSpec);
/*  83 */     this.params = (SignatureMethodParameterSpec)paramAlgorithmParameterSpec;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DOMHMACSignatureMethod(Element paramElement) throws MarshalException {
/*  92 */     Element element = DOMUtils.getFirstChildElement(paramElement);
/*  93 */     if (element != null) {
/*  94 */       this.params = unmarshalParams(element);
/*     */     }
/*     */     try {
/*  97 */       checkParams(this.params);
/*  98 */     } catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
/*  99 */       throw new MarshalException(invalidAlgorithmParameterException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void checkParams(SignatureMethodParameterSpec paramSignatureMethodParameterSpec) throws InvalidAlgorithmParameterException {
/* 106 */     if (paramSignatureMethodParameterSpec != null) {
/* 107 */       if (!(paramSignatureMethodParameterSpec instanceof HMACParameterSpec)) {
/* 108 */         throw new InvalidAlgorithmParameterException("params must be of type HMACParameterSpec");
/*     */       }
/*     */       
/* 111 */       this.outputLength = ((HMACParameterSpec)paramSignatureMethodParameterSpec).getOutputLength();
/* 112 */       this.outputLengthSet = true;
/* 113 */       if (log.isLoggable(Level.FINE)) {
/* 114 */         log.log(Level.FINE, "Setting outputLength from HMACParameterSpec to: " + this.outputLength);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public final AlgorithmParameterSpec getParameterSpec() {
/* 120 */     return this.params;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   SignatureMethodParameterSpec unmarshalParams(Element paramElement) throws MarshalException {
/* 126 */     this.outputLength = Integer.valueOf(paramElement.getFirstChild().getNodeValue()).intValue();
/* 127 */     this.outputLengthSet = true;
/* 128 */     if (log.isLoggable(Level.FINE)) {
/* 129 */       log.log(Level.FINE, "unmarshalled outputLength: " + this.outputLength);
/*     */     }
/* 131 */     return new HMACParameterSpec(this.outputLength);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void marshalParams(Element paramElement, String paramString) throws MarshalException {
/* 137 */     Document document = DOMUtils.getOwnerDocument(paramElement);
/* 138 */     Element element = DOMUtils.createElement(document, "HMACOutputLength", "http://www.w3.org/2000/09/xmldsig#", paramString);
/*     */     
/* 140 */     element.appendChild(document
/* 141 */         .createTextNode(String.valueOf(this.outputLength)));
/*     */     
/* 143 */     paramElement.appendChild(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean verify(Key paramKey, SignedInfo paramSignedInfo, byte[] paramArrayOfbyte, XMLValidateContext paramXMLValidateContext) throws InvalidKeyException, SignatureException, XMLSignatureException {
/* 150 */     if (paramKey == null || paramSignedInfo == null || paramArrayOfbyte == null) {
/* 151 */       throw new NullPointerException();
/*     */     }
/* 153 */     if (!(paramKey instanceof javax.crypto.SecretKey)) {
/* 154 */       throw new InvalidKeyException("key must be SecretKey");
/*     */     }
/* 156 */     if (this.hmac == null) {
/*     */       try {
/* 158 */         this.hmac = Mac.getInstance(getJCAAlgorithm());
/* 159 */       } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 160 */         throw new XMLSignatureException(noSuchAlgorithmException);
/*     */       } 
/*     */     }
/* 163 */     if (this.outputLengthSet && this.outputLength < getDigestLength()) {
/* 164 */       throw new XMLSignatureException("HMACOutputLength must not be less than " + 
/* 165 */           getDigestLength());
/*     */     }
/* 167 */     this.hmac.init(paramKey);
/* 168 */     ((DOMSignedInfo)paramSignedInfo).canonicalize(paramXMLValidateContext, new MacOutputStream(this.hmac));
/* 169 */     byte[] arrayOfByte = this.hmac.doFinal();
/*     */     
/* 171 */     return MessageDigest.isEqual(paramArrayOfbyte, arrayOfByte);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   byte[] sign(Key paramKey, SignedInfo paramSignedInfo, XMLSignContext paramXMLSignContext) throws InvalidKeyException, XMLSignatureException {
/* 177 */     if (paramKey == null || paramSignedInfo == null) {
/* 178 */       throw new NullPointerException();
/*     */     }
/* 180 */     if (!(paramKey instanceof javax.crypto.SecretKey)) {
/* 181 */       throw new InvalidKeyException("key must be SecretKey");
/*     */     }
/* 183 */     if (this.hmac == null) {
/*     */       try {
/* 185 */         this.hmac = Mac.getInstance(getJCAAlgorithm());
/* 186 */       } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 187 */         throw new XMLSignatureException(noSuchAlgorithmException);
/*     */       } 
/*     */     }
/* 190 */     if (this.outputLengthSet && this.outputLength < getDigestLength()) {
/* 191 */       throw new XMLSignatureException("HMACOutputLength must not be less than " + 
/* 192 */           getDigestLength());
/*     */     }
/* 194 */     this.hmac.init(paramKey);
/* 195 */     ((DOMSignedInfo)paramSignedInfo).canonicalize(paramXMLSignContext, new MacOutputStream(this.hmac));
/* 196 */     return this.hmac.doFinal();
/*     */   }
/*     */   
/*     */   boolean paramsEqual(AlgorithmParameterSpec paramAlgorithmParameterSpec) {
/* 200 */     if (getParameterSpec() == paramAlgorithmParameterSpec) {
/* 201 */       return true;
/*     */     }
/* 203 */     if (!(paramAlgorithmParameterSpec instanceof HMACParameterSpec)) {
/* 204 */       return false;
/*     */     }
/* 206 */     HMACParameterSpec hMACParameterSpec = (HMACParameterSpec)paramAlgorithmParameterSpec;
/*     */     
/* 208 */     return (this.outputLength == hMACParameterSpec.getOutputLength());
/*     */   }
/*     */   
/*     */   AbstractDOMSignatureMethod.Type getAlgorithmType() {
/* 212 */     return AbstractDOMSignatureMethod.Type.HMAC;
/*     */   }
/*     */ 
/*     */   
/*     */   abstract int getDigestLength();
/*     */ 
/*     */   
/*     */   static final class SHA1
/*     */     extends DOMHMACSignatureMethod
/*     */   {
/*     */     SHA1(AlgorithmParameterSpec param1AlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
/* 223 */       super(param1AlgorithmParameterSpec);
/*     */     }
/*     */     SHA1(Element param1Element) throws MarshalException {
/* 226 */       super(param1Element);
/*     */     }
/*     */     public String getAlgorithm() {
/* 229 */       return "http://www.w3.org/2000/09/xmldsig#hmac-sha1";
/*     */     }
/*     */     String getJCAAlgorithm() {
/* 232 */       return "HmacSHA1";
/*     */     }
/*     */     int getDigestLength() {
/* 235 */       return 160;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class SHA256
/*     */     extends DOMHMACSignatureMethod {
/*     */     SHA256(AlgorithmParameterSpec param1AlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
/* 242 */       super(param1AlgorithmParameterSpec);
/*     */     }
/*     */     SHA256(Element param1Element) throws MarshalException {
/* 245 */       super(param1Element);
/*     */     }
/*     */     public String getAlgorithm() {
/* 248 */       return "http://www.w3.org/2001/04/xmldsig-more#hmac-sha256";
/*     */     }
/*     */     String getJCAAlgorithm() {
/* 251 */       return "HmacSHA256";
/*     */     }
/*     */     int getDigestLength() {
/* 254 */       return 256;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class SHA384
/*     */     extends DOMHMACSignatureMethod {
/*     */     SHA384(AlgorithmParameterSpec param1AlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
/* 261 */       super(param1AlgorithmParameterSpec);
/*     */     }
/*     */     SHA384(Element param1Element) throws MarshalException {
/* 264 */       super(param1Element);
/*     */     }
/*     */     public String getAlgorithm() {
/* 267 */       return "http://www.w3.org/2001/04/xmldsig-more#hmac-sha384";
/*     */     }
/*     */     String getJCAAlgorithm() {
/* 270 */       return "HmacSHA384";
/*     */     }
/*     */     int getDigestLength() {
/* 273 */       return 384;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class SHA512
/*     */     extends DOMHMACSignatureMethod {
/*     */     SHA512(AlgorithmParameterSpec param1AlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
/* 280 */       super(param1AlgorithmParameterSpec);
/*     */     }
/*     */     SHA512(Element param1Element) throws MarshalException {
/* 283 */       super(param1Element);
/*     */     }
/*     */     public String getAlgorithm() {
/* 286 */       return "http://www.w3.org/2001/04/xmldsig-more#hmac-sha512";
/*     */     }
/*     */     String getJCAAlgorithm() {
/* 289 */       return "HmacSHA512";
/*     */     }
/*     */     int getDigestLength() {
/* 292 */       return 512;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMHMACSignatureMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */