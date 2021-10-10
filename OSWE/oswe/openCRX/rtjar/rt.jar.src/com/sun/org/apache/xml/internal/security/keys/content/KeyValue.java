/*     */ package com.sun.org.apache.xml.internal.security.keys.content;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.keyvalues.DSAKeyValue;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.keyvalues.RSAKeyValue;
/*     */ import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import java.security.PublicKey;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KeyValue
/*     */   extends SignatureElementProxy
/*     */   implements KeyInfoContent
/*     */ {
/*     */   public KeyValue(Document paramDocument, DSAKeyValue paramDSAKeyValue) {
/*  55 */     super(paramDocument);
/*     */     
/*  57 */     XMLUtils.addReturnToElement(this.constructionElement);
/*  58 */     this.constructionElement.appendChild(paramDSAKeyValue.getElement());
/*  59 */     XMLUtils.addReturnToElement(this.constructionElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyValue(Document paramDocument, RSAKeyValue paramRSAKeyValue) {
/*  69 */     super(paramDocument);
/*     */     
/*  71 */     XMLUtils.addReturnToElement(this.constructionElement);
/*  72 */     this.constructionElement.appendChild(paramRSAKeyValue.getElement());
/*  73 */     XMLUtils.addReturnToElement(this.constructionElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyValue(Document paramDocument, Element paramElement) {
/*  83 */     super(paramDocument);
/*     */     
/*  85 */     XMLUtils.addReturnToElement(this.constructionElement);
/*  86 */     this.constructionElement.appendChild(paramElement);
/*  87 */     XMLUtils.addReturnToElement(this.constructionElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyValue(Document paramDocument, PublicKey paramPublicKey) {
/*  97 */     super(paramDocument);
/*     */     
/*  99 */     XMLUtils.addReturnToElement(this.constructionElement);
/*     */     
/* 101 */     if (paramPublicKey instanceof java.security.interfaces.DSAPublicKey) {
/* 102 */       DSAKeyValue dSAKeyValue = new DSAKeyValue(this.doc, paramPublicKey);
/*     */       
/* 104 */       this.constructionElement.appendChild(dSAKeyValue.getElement());
/* 105 */       XMLUtils.addReturnToElement(this.constructionElement);
/* 106 */     } else if (paramPublicKey instanceof java.security.interfaces.RSAPublicKey) {
/* 107 */       RSAKeyValue rSAKeyValue = new RSAKeyValue(this.doc, paramPublicKey);
/*     */       
/* 109 */       this.constructionElement.appendChild(rSAKeyValue.getElement());
/* 110 */       XMLUtils.addReturnToElement(this.constructionElement);
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
/*     */   public KeyValue(Element paramElement, String paramString) throws XMLSecurityException {
/* 122 */     super(paramElement, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PublicKey getPublicKey() throws XMLSecurityException {
/* 133 */     Element element1 = XMLUtils.selectDsNode(this.constructionElement
/* 134 */         .getFirstChild(), "RSAKeyValue", 0);
/*     */     
/* 136 */     if (element1 != null) {
/* 137 */       RSAKeyValue rSAKeyValue = new RSAKeyValue(element1, this.baseURI);
/* 138 */       return rSAKeyValue.getPublicKey();
/*     */     } 
/*     */ 
/*     */     
/* 142 */     Element element2 = XMLUtils.selectDsNode(this.constructionElement
/* 143 */         .getFirstChild(), "DSAKeyValue", 0);
/*     */     
/* 145 */     if (element2 != null) {
/* 146 */       DSAKeyValue dSAKeyValue = new DSAKeyValue(element2, this.baseURI);
/* 147 */       return dSAKeyValue.getPublicKey();
/*     */     } 
/*     */     
/* 150 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBaseLocalName() {
/* 155 */     return "KeyValue";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/content/KeyValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */