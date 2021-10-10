/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.c14n.Canonicalizer;
/*     */ import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.crypto.Data;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.XMLStructure;
/*     */ import javax.xml.crypto.dsig.TransformException;
/*     */ import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
/*     */ import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
/*     */ import javax.xml.crypto.dsig.spec.TransformParameterSpec;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DOMExcC14NMethod
/*     */   extends ApacheCanonicalizer
/*     */ {
/*     */   public void init(TransformParameterSpec paramTransformParameterSpec) throws InvalidAlgorithmParameterException {
/*  57 */     if (paramTransformParameterSpec != null) {
/*  58 */       if (!(paramTransformParameterSpec instanceof ExcC14NParameterSpec)) {
/*  59 */         throw new InvalidAlgorithmParameterException("params must be of type ExcC14NParameterSpec");
/*     */       }
/*     */       
/*  62 */       this.params = (C14NMethodParameterSpec)paramTransformParameterSpec;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(XMLStructure paramXMLStructure, XMLCryptoContext paramXMLCryptoContext) throws InvalidAlgorithmParameterException {
/*  69 */     super.init(paramXMLStructure, paramXMLCryptoContext);
/*  70 */     Element element = DOMUtils.getFirstChildElement(this.transformElem);
/*  71 */     if (element == null) {
/*  72 */       this.params = null;
/*  73 */       this.inclusiveNamespaces = null;
/*     */       return;
/*     */     } 
/*  76 */     unmarshalParams(element);
/*     */   }
/*     */   
/*     */   private void unmarshalParams(Element paramElement) {
/*  80 */     String str = paramElement.getAttributeNS((String)null, "PrefixList");
/*  81 */     this.inclusiveNamespaces = str;
/*  82 */     int i = 0;
/*  83 */     int j = str.indexOf(' ');
/*  84 */     ArrayList<String> arrayList = new ArrayList();
/*  85 */     while (j != -1) {
/*  86 */       arrayList.add(str.substring(i, j));
/*  87 */       i = j + 1;
/*  88 */       j = str.indexOf(' ', i);
/*     */     } 
/*  90 */     if (i <= str.length()) {
/*  91 */       arrayList.add(str.substring(i));
/*     */     }
/*  93 */     this.params = new ExcC14NParameterSpec(arrayList);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshalParams(XMLStructure paramXMLStructure, XMLCryptoContext paramXMLCryptoContext) throws MarshalException {
/*  99 */     super.marshalParams(paramXMLStructure, paramXMLCryptoContext);
/* 100 */     AlgorithmParameterSpec algorithmParameterSpec = getParameterSpec();
/* 101 */     if (algorithmParameterSpec == null) {
/*     */       return;
/*     */     }
/*     */     
/* 105 */     String str = DOMUtils.getNSPrefix(paramXMLCryptoContext, "http://www.w3.org/2001/10/xml-exc-c14n#");
/*     */     
/* 107 */     Element element = DOMUtils.createElement(this.ownerDoc, "InclusiveNamespaces", "http://www.w3.org/2001/10/xml-exc-c14n#", str);
/*     */ 
/*     */ 
/*     */     
/* 111 */     if (str == null || str.length() == 0) {
/* 112 */       element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "http://www.w3.org/2001/10/xml-exc-c14n#");
/*     */     } else {
/*     */       
/* 115 */       element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + str, "http://www.w3.org/2001/10/xml-exc-c14n#");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 120 */     ExcC14NParameterSpec excC14NParameterSpec = (ExcC14NParameterSpec)algorithmParameterSpec;
/* 121 */     StringBuffer stringBuffer = new StringBuffer("");
/*     */     
/* 123 */     List<String> list = excC14NParameterSpec.getPrefixList(); byte b; int i;
/* 124 */     for (b = 0, i = list.size(); b < i; b++) {
/* 125 */       stringBuffer.append(list.get(b));
/* 126 */       if (b < i - 1) {
/* 127 */         stringBuffer.append(" ");
/*     */       }
/*     */     } 
/* 130 */     DOMUtils.setAttribute(element, "PrefixList", stringBuffer.toString());
/* 131 */     this.inclusiveNamespaces = stringBuffer.toString();
/* 132 */     this.transformElem.appendChild(element);
/*     */   }
/*     */   
/*     */   public String getParamsNSURI() {
/* 136 */     return "http://www.w3.org/2001/10/xml-exc-c14n#";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Data transform(Data paramData, XMLCryptoContext paramXMLCryptoContext) throws TransformException {
/* 145 */     if (paramData instanceof DOMSubTreeData) {
/* 146 */       DOMSubTreeData dOMSubTreeData = (DOMSubTreeData)paramData;
/* 147 */       if (dOMSubTreeData.excludeComments()) {
/*     */         try {
/* 149 */           this
/* 150 */             .apacheCanonicalizer = Canonicalizer.getInstance("http://www.w3.org/2001/10/xml-exc-c14n#");
/* 151 */         } catch (InvalidCanonicalizerException invalidCanonicalizerException) {
/* 152 */           throw new TransformException("Couldn't find Canonicalizer for: http://www.w3.org/2001/10/xml-exc-c14n#: " + invalidCanonicalizerException
/*     */ 
/*     */               
/* 155 */               .getMessage(), invalidCanonicalizerException);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 160 */     return canonicalize(paramData, paramXMLCryptoContext);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMExcC14NMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */