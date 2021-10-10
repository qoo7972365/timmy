/*     */ package com.sun.org.apache.xml.internal.security.signature;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
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
/*     */ public class SignatureProperty
/*     */   extends SignatureElementProxy
/*     */ {
/*     */   public SignatureProperty(Document paramDocument, String paramString) {
/*  49 */     this(paramDocument, paramString, null);
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
/*     */   public SignatureProperty(Document paramDocument, String paramString1, String paramString2) {
/*  62 */     super(paramDocument);
/*     */     
/*  64 */     setTarget(paramString1);
/*  65 */     setId(paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureProperty(Element paramElement, String paramString) throws XMLSecurityException {
/*  75 */     super(paramElement, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(String paramString) {
/*  84 */     if (paramString != null) {
/*  85 */       this.constructionElement.setAttributeNS((String)null, "Id", paramString);
/*  86 */       this.constructionElement.setIdAttributeNS((String)null, "Id", true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/*  96 */     return this.constructionElement.getAttributeNS((String)null, "Id");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTarget(String paramString) {
/* 105 */     if (paramString != null) {
/* 106 */       this.constructionElement.setAttributeNS((String)null, "Target", paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTarget() {
/* 116 */     return this.constructionElement.getAttributeNS((String)null, "Target");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node appendChild(Node paramNode) {
/* 126 */     return this.constructionElement.appendChild(paramNode);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBaseLocalName() {
/* 131 */     return "SignatureProperty";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/signature/SignatureProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */