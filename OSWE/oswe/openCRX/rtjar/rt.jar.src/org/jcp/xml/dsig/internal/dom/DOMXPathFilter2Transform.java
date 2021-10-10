/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.XMLStructure;
/*     */ import javax.xml.crypto.dsig.spec.TransformParameterSpec;
/*     */ import javax.xml.crypto.dsig.spec.XPathFilter2ParameterSpec;
/*     */ import javax.xml.crypto.dsig.spec.XPathType;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DOMXPathFilter2Transform
/*     */   extends ApacheTransform
/*     */ {
/*     */   public void init(TransformParameterSpec paramTransformParameterSpec) throws InvalidAlgorithmParameterException {
/*  64 */     if (paramTransformParameterSpec == null)
/*  65 */       throw new InvalidAlgorithmParameterException("params are required"); 
/*  66 */     if (!(paramTransformParameterSpec instanceof XPathFilter2ParameterSpec)) {
/*  67 */       throw new InvalidAlgorithmParameterException("params must be of type XPathFilter2ParameterSpec");
/*     */     }
/*     */     
/*  70 */     this.params = paramTransformParameterSpec;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(XMLStructure paramXMLStructure, XMLCryptoContext paramXMLCryptoContext) throws InvalidAlgorithmParameterException {
/*  76 */     super.init(paramXMLStructure, paramXMLCryptoContext);
/*     */     try {
/*  78 */       unmarshalParams(DOMUtils.getFirstChildElement(this.transformElem));
/*  79 */     } catch (MarshalException marshalException) {
/*  80 */       throw new InvalidAlgorithmParameterException(marshalException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void unmarshalParams(Element paramElement) throws MarshalException {
/*  86 */     ArrayList<XPathType> arrayList = new ArrayList();
/*  87 */     while (paramElement != null) {
/*  88 */       String str1 = paramElement.getFirstChild().getNodeValue();
/*  89 */       String str2 = DOMUtils.getAttributeValue(paramElement, "Filter");
/*     */       
/*  91 */       if (str2 == null) {
/*  92 */         throw new MarshalException("filter cannot be null");
/*     */       }
/*  94 */       XPathType.Filter filter = null;
/*  95 */       if (str2.equals("intersect")) {
/*  96 */         filter = XPathType.Filter.INTERSECT;
/*  97 */       } else if (str2.equals("subtract")) {
/*  98 */         filter = XPathType.Filter.SUBTRACT;
/*  99 */       } else if (str2.equals("union")) {
/* 100 */         filter = XPathType.Filter.UNION;
/*     */       } else {
/* 102 */         throw new MarshalException("Unknown XPathType filter type" + str2);
/*     */       } 
/*     */       
/* 105 */       NamedNodeMap namedNodeMap = paramElement.getAttributes();
/* 106 */       if (namedNodeMap != null) {
/* 107 */         int i = namedNodeMap.getLength();
/* 108 */         HashMap<Object, Object> hashMap = new HashMap<>(i);
/*     */         
/* 110 */         for (byte b = 0; b < i; b++) {
/* 111 */           Attr attr = (Attr)namedNodeMap.item(b);
/* 112 */           String str = attr.getPrefix();
/* 113 */           if (str != null && str.equals("xmlns")) {
/* 114 */             hashMap.put(attr.getLocalName(), attr.getValue());
/*     */           }
/*     */         } 
/* 117 */         arrayList.add(new XPathType(str1, filter, (Map)hashMap));
/*     */       } else {
/* 119 */         arrayList.add(new XPathType(str1, filter));
/*     */       } 
/*     */       
/* 122 */       paramElement = DOMUtils.getNextSiblingElement(paramElement);
/*     */     } 
/* 124 */     this.params = new XPathFilter2ParameterSpec(arrayList);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshalParams(XMLStructure paramXMLStructure, XMLCryptoContext paramXMLCryptoContext) throws MarshalException {
/* 130 */     super.marshalParams(paramXMLStructure, paramXMLCryptoContext);
/*     */     
/* 132 */     XPathFilter2ParameterSpec xPathFilter2ParameterSpec = (XPathFilter2ParameterSpec)getParameterSpec();
/* 133 */     String str1 = DOMUtils.getNSPrefix(paramXMLCryptoContext, "http://www.w3.org/2002/06/xmldsig-filter2");
/* 134 */     String str2 = (str1 == null || str1.length() == 0) ? "xmlns" : ("xmlns:" + str1);
/*     */ 
/*     */     
/* 137 */     List<XPathType> list = xPathFilter2ParameterSpec.getXPathList();
/* 138 */     for (XPathType xPathType : list) {
/* 139 */       Element element = DOMUtils.createElement(this.ownerDoc, "XPath", "http://www.w3.org/2002/06/xmldsig-filter2", str1);
/*     */       
/* 141 */       element
/* 142 */         .appendChild(this.ownerDoc.createTextNode(xPathType.getExpression()));
/* 143 */       DOMUtils.setAttribute(element, "Filter", xPathType
/* 144 */           .getFilter().toString());
/* 145 */       element.setAttributeNS("http://www.w3.org/2000/xmlns/", str2, "http://www.w3.org/2002/06/xmldsig-filter2");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 151 */       Set<Map.Entry<String, String>> set = xPathType.getNamespaceMap().entrySet();
/* 152 */       for (Map.Entry<String, String> entry : set) {
/* 153 */         element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + (String)entry
/* 154 */             .getKey(), (String)entry
/* 155 */             .getValue());
/*     */       }
/*     */       
/* 158 */       this.transformElem.appendChild(element);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMXPathFilter2Transform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */