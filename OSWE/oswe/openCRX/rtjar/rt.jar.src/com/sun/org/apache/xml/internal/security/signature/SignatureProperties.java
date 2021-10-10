/*     */ package com.sun.org.apache.xml.internal.security.signature;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
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
/*     */ public class SignatureProperties
/*     */   extends SignatureElementProxy
/*     */ {
/*     */   public SignatureProperties(Document paramDocument) {
/*  49 */     super(paramDocument);
/*     */     
/*  51 */     XMLUtils.addReturnToElement(this.constructionElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureProperties(Element paramElement, String paramString) throws XMLSecurityException {
/*  61 */     super(paramElement, paramString);
/*     */     
/*  63 */     Attr attr = paramElement.getAttributeNodeNS((String)null, "Id");
/*  64 */     if (attr != null) {
/*  65 */       paramElement.setIdAttributeNode(attr, true);
/*     */     }
/*     */     
/*  68 */     int i = getLength();
/*  69 */     for (byte b = 0; b < i; b++) {
/*     */       
/*  71 */       Element element = XMLUtils.selectDsNode(this.constructionElement, "SignatureProperty", b);
/*  72 */       Attr attr1 = element.getAttributeNodeNS((String)null, "Id");
/*  73 */       if (attr1 != null) {
/*  74 */         element.setIdAttributeNode(attr1, true);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLength() {
/*  86 */     Element[] arrayOfElement = XMLUtils.selectDsNodes(this.constructionElement, "SignatureProperty");
/*     */     
/*  88 */     return arrayOfElement.length;
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
/*     */   public SignatureProperty item(int paramInt) throws XMLSignatureException {
/*     */     try {
/* 102 */       Element element = XMLUtils.selectDsNode(this.constructionElement, "SignatureProperty", paramInt);
/*     */       
/* 104 */       if (element == null) {
/* 105 */         return null;
/*     */       }
/* 107 */       return new SignatureProperty(element, this.baseURI);
/* 108 */     } catch (XMLSecurityException xMLSecurityException) {
/* 109 */       throw new XMLSignatureException("empty", xMLSecurityException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(String paramString) {
/* 119 */     if (paramString != null) {
/* 120 */       this.constructionElement.setAttributeNS((String)null, "Id", paramString);
/* 121 */       this.constructionElement.setIdAttributeNS((String)null, "Id", true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 131 */     return this.constructionElement.getAttributeNS((String)null, "Id");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSignatureProperty(SignatureProperty paramSignatureProperty) {
/* 140 */     this.constructionElement.appendChild(paramSignatureProperty.getElement());
/* 141 */     XMLUtils.addReturnToElement(this.constructionElement);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBaseLocalName() {
/* 146 */     return "SignatureProperties";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/signature/SignatureProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */