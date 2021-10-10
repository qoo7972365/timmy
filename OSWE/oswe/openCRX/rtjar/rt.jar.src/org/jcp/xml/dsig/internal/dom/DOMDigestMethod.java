/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.dom.DOMCryptoContext;
/*     */ import javax.xml.crypto.dsig.DigestMethod;
/*     */ import javax.xml.crypto.dsig.spec.DigestMethodParameterSpec;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DOMDigestMethod
/*     */   extends DOMStructure
/*     */   implements DigestMethod
/*     */ {
/*     */   static final String SHA384 = "http://www.w3.org/2001/04/xmldsig-more#sha384";
/*     */   private DigestMethodParameterSpec params;
/*     */   
/*     */   DOMDigestMethod(AlgorithmParameterSpec paramAlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
/*  64 */     if (paramAlgorithmParameterSpec != null && !(paramAlgorithmParameterSpec instanceof DigestMethodParameterSpec)) {
/*  65 */       throw new InvalidAlgorithmParameterException("params must be of type DigestMethodParameterSpec");
/*     */     }
/*     */     
/*  68 */     checkParams((DigestMethodParameterSpec)paramAlgorithmParameterSpec);
/*  69 */     this.params = (DigestMethodParameterSpec)paramAlgorithmParameterSpec;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DOMDigestMethod(Element paramElement) throws MarshalException {
/*  80 */     Element element = DOMUtils.getFirstChildElement(paramElement);
/*  81 */     if (element != null) {
/*  82 */       this.params = unmarshalParams(element);
/*     */     }
/*     */     try {
/*  85 */       checkParams(this.params);
/*  86 */     } catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
/*  87 */       throw new MarshalException(invalidAlgorithmParameterException);
/*     */     } 
/*     */   }
/*     */   
/*     */   static DigestMethod unmarshal(Element paramElement) throws MarshalException {
/*  92 */     String str = DOMUtils.getAttributeValue(paramElement, "Algorithm");
/*  93 */     if (str.equals("http://www.w3.org/2000/09/xmldsig#sha1"))
/*  94 */       return new SHA1(paramElement); 
/*  95 */     if (str.equals("http://www.w3.org/2001/04/xmlenc#sha256"))
/*  96 */       return new SHA256(paramElement); 
/*  97 */     if (str.equals("http://www.w3.org/2001/04/xmldsig-more#sha384"))
/*  98 */       return new SHA384(paramElement); 
/*  99 */     if (str.equals("http://www.w3.org/2001/04/xmlenc#sha512")) {
/* 100 */       return new SHA512(paramElement);
/*     */     }
/* 102 */     throw new MarshalException("unsupported DigestMethod algorithm: " + str);
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
/*     */   void checkParams(DigestMethodParameterSpec paramDigestMethodParameterSpec) throws InvalidAlgorithmParameterException {
/* 120 */     if (paramDigestMethodParameterSpec != null) {
/* 121 */       throw new InvalidAlgorithmParameterException("no parameters should be specified for the " + 
/* 122 */           getMessageDigestAlgorithm() + " DigestMethod algorithm");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public final AlgorithmParameterSpec getParameterSpec() {
/* 128 */     return this.params;
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
/*     */   DigestMethodParameterSpec unmarshalParams(Element paramElement) throws MarshalException {
/* 144 */     throw new MarshalException("no parameters should be specified for the " + 
/*     */         
/* 146 */         getMessageDigestAlgorithm() + " DigestMethod algorithm");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(Node paramNode, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
/* 157 */     Document document = DOMUtils.getOwnerDocument(paramNode);
/*     */     
/* 159 */     Element element = DOMUtils.createElement(document, "DigestMethod", "http://www.w3.org/2000/09/xmldsig#", paramString);
/*     */     
/* 161 */     DOMUtils.setAttribute(element, "Algorithm", getAlgorithm());
/*     */     
/* 163 */     if (this.params != null) {
/* 164 */       marshalParams(element, paramString);
/*     */     }
/*     */     
/* 167 */     paramNode.appendChild(element);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 172 */     if (this == paramObject) {
/* 173 */       return true;
/*     */     }
/*     */     
/* 176 */     if (!(paramObject instanceof DigestMethod)) {
/* 177 */       return false;
/*     */     }
/* 179 */     DigestMethod digestMethod = (DigestMethod)paramObject;
/*     */ 
/*     */     
/* 182 */     boolean bool = (this.params == null) ? ((digestMethod.getParameterSpec() == null) ? true : false) : this.params.equals(digestMethod.getParameterSpec());
/*     */     
/* 184 */     return (getAlgorithm().equals(digestMethod.getAlgorithm()) && bool);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 189 */     int i = 17;
/* 190 */     if (this.params != null) {
/* 191 */       i = 31 * i + this.params.hashCode();
/*     */     }
/* 193 */     i = 31 * i + getAlgorithm().hashCode();
/*     */     
/* 195 */     return i;
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
/*     */   void marshalParams(Element paramElement, String paramString) throws MarshalException {
/* 211 */     throw new MarshalException("no parameters should be specified for the " + 
/*     */         
/* 213 */         getMessageDigestAlgorithm() + " DigestMethod algorithm");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   abstract String getMessageDigestAlgorithm();
/*     */ 
/*     */   
/*     */   static final class SHA1
/*     */     extends DOMDigestMethod
/*     */   {
/*     */     SHA1(AlgorithmParameterSpec param1AlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
/* 225 */       super(param1AlgorithmParameterSpec);
/*     */     }
/*     */     SHA1(Element param1Element) throws MarshalException {
/* 228 */       super(param1Element);
/*     */     }
/*     */     public String getAlgorithm() {
/* 231 */       return "http://www.w3.org/2000/09/xmldsig#sha1";
/*     */     }
/*     */     String getMessageDigestAlgorithm() {
/* 234 */       return "SHA-1";
/*     */     }
/*     */   }
/*     */   
/*     */   static final class SHA256
/*     */     extends DOMDigestMethod {
/*     */     SHA256(AlgorithmParameterSpec param1AlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
/* 241 */       super(param1AlgorithmParameterSpec);
/*     */     }
/*     */     SHA256(Element param1Element) throws MarshalException {
/* 244 */       super(param1Element);
/*     */     }
/*     */     public String getAlgorithm() {
/* 247 */       return "http://www.w3.org/2001/04/xmlenc#sha256";
/*     */     }
/*     */     String getMessageDigestAlgorithm() {
/* 250 */       return "SHA-256";
/*     */     }
/*     */   }
/*     */   
/*     */   static final class SHA384
/*     */     extends DOMDigestMethod {
/*     */     SHA384(AlgorithmParameterSpec param1AlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
/* 257 */       super(param1AlgorithmParameterSpec);
/*     */     }
/*     */     SHA384(Element param1Element) throws MarshalException {
/* 260 */       super(param1Element);
/*     */     }
/*     */     public String getAlgorithm() {
/* 263 */       return "http://www.w3.org/2001/04/xmldsig-more#sha384";
/*     */     }
/*     */     String getMessageDigestAlgorithm() {
/* 266 */       return "SHA-384";
/*     */     }
/*     */   }
/*     */   
/*     */   static final class SHA512
/*     */     extends DOMDigestMethod {
/*     */     SHA512(AlgorithmParameterSpec param1AlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
/* 273 */       super(param1AlgorithmParameterSpec);
/*     */     }
/*     */     SHA512(Element param1Element) throws MarshalException {
/* 276 */       super(param1Element);
/*     */     }
/*     */     public String getAlgorithm() {
/* 279 */       return "http://www.w3.org/2001/04/xmlenc#sha512";
/*     */     }
/*     */     String getMessageDigestAlgorithm() {
/* 282 */       return "SHA-512";
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMDigestMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */