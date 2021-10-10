/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.Key;
/*     */ import java.security.SignatureException;
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.dom.DOMCryptoContext;
/*     */ import javax.xml.crypto.dsig.SignatureMethod;
/*     */ import javax.xml.crypto.dsig.SignedInfo;
/*     */ import javax.xml.crypto.dsig.XMLSignContext;
/*     */ import javax.xml.crypto.dsig.XMLSignatureException;
/*     */ import javax.xml.crypto.dsig.XMLValidateContext;
/*     */ import javax.xml.crypto.dsig.spec.SignatureMethodParameterSpec;
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
/*     */ abstract class AbstractDOMSignatureMethod
/*     */   extends DOMStructure
/*     */   implements SignatureMethod
/*     */ {
/*     */   abstract boolean verify(Key paramKey, SignedInfo paramSignedInfo, byte[] paramArrayOfbyte, XMLValidateContext paramXMLValidateContext) throws InvalidKeyException, SignatureException, XMLSignatureException;
/*     */   
/*     */   abstract byte[] sign(Key paramKey, SignedInfo paramSignedInfo, XMLSignContext paramXMLSignContext) throws InvalidKeyException, XMLSignatureException;
/*     */   
/*     */   abstract String getJCAAlgorithm();
/*     */   
/*     */   abstract Type getAlgorithmType();
/*     */   
/*     */   enum Type
/*     */   {
/*  52 */     DSA, RSA, ECDSA, HMAC;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(Node paramNode, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
/* 111 */     Document document = DOMUtils.getOwnerDocument(paramNode);
/*     */     
/* 113 */     Element element = DOMUtils.createElement(document, "SignatureMethod", "http://www.w3.org/2000/09/xmldsig#", paramString);
/*     */     
/* 115 */     DOMUtils.setAttribute(element, "Algorithm", getAlgorithm());
/*     */     
/* 117 */     if (getParameterSpec() != null) {
/* 118 */       marshalParams(element, paramString);
/*     */     }
/*     */     
/* 121 */     paramNode.appendChild(element);
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
/* 137 */     throw new MarshalException("no parameters should be specified for the " + 
/* 138 */         getAlgorithm() + " SignatureMethod algorithm");
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
/*     */   SignatureMethodParameterSpec unmarshalParams(Element paramElement) throws MarshalException {
/* 155 */     throw new MarshalException("no parameters should be specified for the " + 
/* 156 */         getAlgorithm() + " SignatureMethod algorithm");
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
/*     */   void checkParams(SignatureMethodParameterSpec paramSignatureMethodParameterSpec) throws InvalidAlgorithmParameterException {
/* 173 */     if (paramSignatureMethodParameterSpec != null) {
/* 174 */       throw new InvalidAlgorithmParameterException("no parameters should be specified for the " + 
/* 175 */           getAlgorithm() + " SignatureMethod algorithm");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 183 */     if (this == paramObject) {
/* 184 */       return true;
/*     */     }
/*     */     
/* 187 */     if (!(paramObject instanceof SignatureMethod)) {
/* 188 */       return false;
/*     */     }
/* 190 */     SignatureMethod signatureMethod = (SignatureMethod)paramObject;
/*     */     
/* 192 */     return (getAlgorithm().equals(signatureMethod.getAlgorithm()) && 
/* 193 */       paramsEqual(signatureMethod.getParameterSpec()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 198 */     int i = 17;
/* 199 */     i = 31 * i + getAlgorithm().hashCode();
/* 200 */     AlgorithmParameterSpec algorithmParameterSpec = getParameterSpec();
/* 201 */     if (algorithmParameterSpec != null) {
/* 202 */       i = 31 * i + algorithmParameterSpec.hashCode();
/*     */     }
/*     */     
/* 205 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean paramsEqual(AlgorithmParameterSpec paramAlgorithmParameterSpec) {
/* 216 */     return (getParameterSpec() == paramAlgorithmParameterSpec);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/AbstractDOMSignatureMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */