/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import java.io.OutputStream;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.Provider;
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import javax.xml.crypto.Data;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.dom.DOMCryptoContext;
/*     */ import javax.xml.crypto.dom.DOMStructure;
/*     */ import javax.xml.crypto.dsig.Transform;
/*     */ import javax.xml.crypto.dsig.TransformException;
/*     */ import javax.xml.crypto.dsig.TransformService;
/*     */ import javax.xml.crypto.dsig.dom.DOMSignContext;
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
/*     */ public class DOMTransform
/*     */   extends DOMStructure
/*     */   implements Transform
/*     */ {
/*     */   protected TransformService spi;
/*     */   
/*     */   public DOMTransform(TransformService paramTransformService) {
/*  61 */     this.spi = paramTransformService;
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
/*     */   public DOMTransform(Element paramElement, XMLCryptoContext paramXMLCryptoContext, Provider paramProvider) throws MarshalException {
/*  75 */     String str = DOMUtils.getAttributeValue(paramElement, "Algorithm");
/*     */     
/*  77 */     if (paramProvider == null) {
/*     */       try {
/*  79 */         this.spi = TransformService.getInstance(str, "DOM");
/*  80 */       } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*  81 */         throw new MarshalException(noSuchAlgorithmException);
/*     */       } 
/*     */     } else {
/*     */       try {
/*  85 */         this.spi = TransformService.getInstance(str, "DOM", paramProvider);
/*  86 */       } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*     */         try {
/*  88 */           this.spi = TransformService.getInstance(str, "DOM");
/*  89 */         } catch (NoSuchAlgorithmException noSuchAlgorithmException1) {
/*  90 */           throw new MarshalException(noSuchAlgorithmException1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     try {
/*  95 */       this.spi.init(new DOMStructure(paramElement), paramXMLCryptoContext);
/*  96 */     } catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
/*  97 */       throw new MarshalException(invalidAlgorithmParameterException);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final AlgorithmParameterSpec getParameterSpec() {
/* 102 */     return this.spi.getParameterSpec();
/*     */   }
/*     */   
/*     */   public final String getAlgorithm() {
/* 106 */     return this.spi.getAlgorithm();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(Node paramNode, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
/* 116 */     Document document = DOMUtils.getOwnerDocument(paramNode);
/*     */     
/* 118 */     Element element = null;
/* 119 */     if (paramNode.getLocalName().equals("Transforms")) {
/* 120 */       element = DOMUtils.createElement(document, "Transform", "http://www.w3.org/2000/09/xmldsig#", paramString);
/*     */     }
/*     */     else {
/*     */       
/* 124 */       element = DOMUtils.createElement(document, "CanonicalizationMethod", "http://www.w3.org/2000/09/xmldsig#", paramString);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 129 */     DOMUtils.setAttribute(element, "Algorithm", getAlgorithm());
/*     */     
/* 131 */     this.spi.marshalParams(new DOMStructure(element), paramDOMCryptoContext);
/*     */ 
/*     */     
/* 134 */     paramNode.appendChild(element);
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
/*     */   public Data transform(Data paramData, XMLCryptoContext paramXMLCryptoContext) throws TransformException {
/* 151 */     return this.spi.transform(paramData, paramXMLCryptoContext);
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
/*     */   public Data transform(Data paramData, XMLCryptoContext paramXMLCryptoContext, OutputStream paramOutputStream) throws TransformException {
/* 170 */     return this.spi.transform(paramData, paramXMLCryptoContext, paramOutputStream);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 175 */     if (this == paramObject) {
/* 176 */       return true;
/*     */     }
/*     */     
/* 179 */     if (!(paramObject instanceof Transform)) {
/* 180 */       return false;
/*     */     }
/* 182 */     Transform transform = (Transform)paramObject;
/*     */     
/* 184 */     return (getAlgorithm().equals(transform.getAlgorithm()) && 
/* 185 */       DOMUtils.paramsEqual(getParameterSpec(), transform
/* 186 */         .getParameterSpec()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 191 */     int i = 17;
/* 192 */     i = 31 * i + getAlgorithm().hashCode();
/* 193 */     AlgorithmParameterSpec algorithmParameterSpec = getParameterSpec();
/* 194 */     if (algorithmParameterSpec != null) {
/* 195 */       i = 31 * i + algorithmParameterSpec.hashCode();
/*     */     }
/*     */     
/* 198 */     return i;
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
/*     */   Data transform(Data paramData, XMLCryptoContext paramXMLCryptoContext, DOMSignContext paramDOMSignContext) throws MarshalException, TransformException {
/* 220 */     marshal(paramDOMSignContext.getParent(), 
/* 221 */         DOMUtils.getSignaturePrefix(paramDOMSignContext), paramDOMSignContext);
/* 222 */     return transform(paramData, paramXMLCryptoContext);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMTransform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */