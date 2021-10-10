/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.XMLStructure;
/*     */ import javax.xml.crypto.dsig.spec.TransformParameterSpec;
/*     */ import javax.xml.crypto.dsig.spec.XPathFilterParameterSpec;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DOMXPathTransform
/*     */   extends ApacheTransform
/*     */ {
/*     */   public void init(TransformParameterSpec paramTransformParameterSpec) throws InvalidAlgorithmParameterException {
/*  54 */     if (paramTransformParameterSpec == null)
/*  55 */       throw new InvalidAlgorithmParameterException("params are required"); 
/*  56 */     if (!(paramTransformParameterSpec instanceof XPathFilterParameterSpec)) {
/*  57 */       throw new InvalidAlgorithmParameterException("params must be of type XPathFilterParameterSpec");
/*     */     }
/*     */     
/*  60 */     this.params = paramTransformParameterSpec;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(XMLStructure paramXMLStructure, XMLCryptoContext paramXMLCryptoContext) throws InvalidAlgorithmParameterException {
/*  66 */     super.init(paramXMLStructure, paramXMLCryptoContext);
/*  67 */     unmarshalParams(DOMUtils.getFirstChildElement(this.transformElem));
/*     */   }
/*     */   
/*     */   private void unmarshalParams(Element paramElement) {
/*  71 */     String str = paramElement.getFirstChild().getNodeValue();
/*     */     
/*  73 */     NamedNodeMap namedNodeMap = paramElement.getAttributes();
/*  74 */     if (namedNodeMap != null) {
/*  75 */       int i = namedNodeMap.getLength();
/*  76 */       HashMap<Object, Object> hashMap = new HashMap<>(i);
/*     */       
/*  78 */       for (byte b = 0; b < i; b++) {
/*  79 */         Attr attr = (Attr)namedNodeMap.item(b);
/*  80 */         String str1 = attr.getPrefix();
/*  81 */         if (str1 != null && str1.equals("xmlns")) {
/*  82 */           hashMap.put(attr.getLocalName(), attr.getValue());
/*     */         }
/*     */       } 
/*  85 */       this.params = new XPathFilterParameterSpec(str, (Map)hashMap);
/*     */     } else {
/*  87 */       this.params = new XPathFilterParameterSpec(str);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshalParams(XMLStructure paramXMLStructure, XMLCryptoContext paramXMLCryptoContext) throws MarshalException {
/*  94 */     super.marshalParams(paramXMLStructure, paramXMLCryptoContext);
/*     */     
/*  96 */     XPathFilterParameterSpec xPathFilterParameterSpec = (XPathFilterParameterSpec)getParameterSpec();
/*  97 */     Element element = DOMUtils.createElement(this.ownerDoc, "XPath", "http://www.w3.org/2000/09/xmldsig#", 
/*  98 */         DOMUtils.getSignaturePrefix(paramXMLCryptoContext));
/*  99 */     element.appendChild(this.ownerDoc.createTextNode(xPathFilterParameterSpec.getXPath()));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 104 */     Set<Map.Entry<String, String>> set = xPathFilterParameterSpec.getNamespaceMap().entrySet();
/* 105 */     for (Map.Entry<String, String> entry : set) {
/* 106 */       element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + (String)entry
/* 107 */           .getKey(), (String)entry
/* 108 */           .getValue());
/*     */     }
/*     */     
/* 111 */     this.transformElem.appendChild(element);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMXPathTransform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */