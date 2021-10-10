/*     */ package com.sun.org.apache.xml.internal.security.keys.content;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureException;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.Transforms;
/*     */ import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Document;
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
/*     */ public class RetrievalMethod
/*     */   extends SignatureElementProxy
/*     */   implements KeyInfoContent
/*     */ {
/*     */   public static final String TYPE_DSA = "http://www.w3.org/2000/09/xmldsig#DSAKeyValue";
/*     */   public static final String TYPE_RSA = "http://www.w3.org/2000/09/xmldsig#RSAKeyValue";
/*     */   public static final String TYPE_PGP = "http://www.w3.org/2000/09/xmldsig#PGPData";
/*     */   public static final String TYPE_SPKI = "http://www.w3.org/2000/09/xmldsig#SPKIData";
/*     */   public static final String TYPE_MGMT = "http://www.w3.org/2000/09/xmldsig#MgmtData";
/*     */   public static final String TYPE_X509 = "http://www.w3.org/2000/09/xmldsig#X509Data";
/*     */   public static final String TYPE_RAWX509 = "http://www.w3.org/2000/09/xmldsig#rawX509Certificate";
/*     */   
/*     */   public RetrievalMethod(Element paramElement, String paramString) throws XMLSecurityException {
/*  60 */     super(paramElement, paramString);
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
/*     */   public RetrievalMethod(Document paramDocument, String paramString1, Transforms paramTransforms, String paramString2) {
/*  72 */     super(paramDocument);
/*     */     
/*  74 */     this.constructionElement.setAttributeNS((String)null, "URI", paramString1);
/*     */     
/*  76 */     if (paramString2 != null) {
/*  77 */       this.constructionElement.setAttributeNS((String)null, "Type", paramString2);
/*     */     }
/*     */     
/*  80 */     if (paramTransforms != null) {
/*  81 */       this.constructionElement.appendChild(paramTransforms.getElement());
/*  82 */       XMLUtils.addReturnToElement(this.constructionElement);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Attr getURIAttr() {
/*  92 */     return this.constructionElement.getAttributeNodeNS((String)null, "URI");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getURI() {
/* 101 */     return getURIAttr().getNodeValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getType() {
/* 106 */     return this.constructionElement.getAttributeNS((String)null, "Type");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transforms getTransforms() throws XMLSecurityException {
/*     */     try {
/* 118 */       Element element = XMLUtils.selectDsNode(this.constructionElement
/* 119 */           .getFirstChild(), "Transforms", 0);
/*     */       
/* 121 */       if (element != null) {
/* 122 */         return new Transforms(element, this.baseURI);
/*     */       }
/*     */       
/* 125 */       return null;
/* 126 */     } catch (XMLSignatureException xMLSignatureException) {
/* 127 */       throw new XMLSecurityException("empty", xMLSignatureException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBaseLocalName() {
/* 133 */     return "RetrievalMethod";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/content/RetrievalMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */