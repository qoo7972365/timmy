/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import java.io.OutputStream;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.Provider;
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import javax.xml.crypto.Data;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.dsig.CanonicalizationMethod;
/*     */ import javax.xml.crypto.dsig.TransformException;
/*     */ import javax.xml.crypto.dsig.TransformService;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DOMCanonicalizationMethod
/*     */   extends DOMTransform
/*     */   implements CanonicalizationMethod
/*     */ {
/*     */   public DOMCanonicalizationMethod(TransformService paramTransformService) throws InvalidAlgorithmParameterException {
/*  57 */     super(paramTransformService);
/*  58 */     if (!(paramTransformService instanceof ApacheCanonicalizer) && 
/*  59 */       !isC14Nalg(paramTransformService.getAlgorithm())) {
/*  60 */       throw new InvalidAlgorithmParameterException("Illegal CanonicalizationMethod");
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
/*     */   public DOMCanonicalizationMethod(Element paramElement, XMLCryptoContext paramXMLCryptoContext, Provider paramProvider) throws MarshalException {
/*  76 */     super(paramElement, paramXMLCryptoContext, paramProvider);
/*  77 */     if (!(this.spi instanceof ApacheCanonicalizer) && 
/*  78 */       !isC14Nalg(this.spi.getAlgorithm())) {
/*  79 */       throw new MarshalException("Illegal CanonicalizationMethod");
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
/*     */   public Data canonicalize(Data paramData, XMLCryptoContext paramXMLCryptoContext) throws TransformException {
/*  99 */     return transform(paramData, paramXMLCryptoContext);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Data canonicalize(Data paramData, XMLCryptoContext paramXMLCryptoContext, OutputStream paramOutputStream) throws TransformException {
/* 105 */     return transform(paramData, paramXMLCryptoContext, paramOutputStream);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 110 */     if (this == paramObject) {
/* 111 */       return true;
/*     */     }
/*     */     
/* 114 */     if (!(paramObject instanceof CanonicalizationMethod)) {
/* 115 */       return false;
/*     */     }
/* 117 */     CanonicalizationMethod canonicalizationMethod = (CanonicalizationMethod)paramObject;
/*     */     
/* 119 */     return (getAlgorithm().equals(canonicalizationMethod.getAlgorithm()) && 
/* 120 */       DOMUtils.paramsEqual(getParameterSpec(), canonicalizationMethod.getParameterSpec()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 125 */     int i = 17;
/* 126 */     i = 31 * i + getAlgorithm().hashCode();
/* 127 */     AlgorithmParameterSpec algorithmParameterSpec = getParameterSpec();
/* 128 */     if (algorithmParameterSpec != null) {
/* 129 */       i = 31 * i + algorithmParameterSpec.hashCode();
/*     */     }
/*     */     
/* 132 */     return i;
/*     */   }
/*     */   
/*     */   private static boolean isC14Nalg(String paramString) {
/* 136 */     return (paramString.equals("http://www.w3.org/TR/2001/REC-xml-c14n-20010315") || paramString
/* 137 */       .equals("http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments") || paramString
/* 138 */       .equals("http://www.w3.org/2001/10/xml-exc-c14n#") || paramString
/* 139 */       .equals("http://www.w3.org/2001/10/xml-exc-c14n#WithComments") || paramString
/* 140 */       .equals("http://www.w3.org/2006/12/xml-c14n11") || paramString
/* 141 */       .equals("http://www.w3.org/2006/12/xml-c14n11#WithComments"));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMCanonicalizationMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */