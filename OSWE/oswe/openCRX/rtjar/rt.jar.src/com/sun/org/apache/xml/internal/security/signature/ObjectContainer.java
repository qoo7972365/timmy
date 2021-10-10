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
/*     */ public class ObjectContainer
/*     */   extends SignatureElementProxy
/*     */ {
/*     */   public ObjectContainer(Document paramDocument) {
/*  48 */     super(paramDocument);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectContainer(Element paramElement, String paramString) throws XMLSecurityException {
/*  59 */     super(paramElement, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(String paramString) {
/*  68 */     if (paramString != null) {
/*  69 */       this.constructionElement.setAttributeNS((String)null, "Id", paramString);
/*  70 */       this.constructionElement.setIdAttributeNS((String)null, "Id", true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/*  80 */     return this.constructionElement.getAttributeNS((String)null, "Id");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMimeType(String paramString) {
/*  89 */     if (paramString != null) {
/*  90 */       this.constructionElement.setAttributeNS((String)null, "MimeType", paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMimeType() {
/* 100 */     return this.constructionElement.getAttributeNS((String)null, "MimeType");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEncoding(String paramString) {
/* 109 */     if (paramString != null) {
/* 110 */       this.constructionElement.setAttributeNS((String)null, "Encoding", paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEncoding() {
/* 120 */     return this.constructionElement.getAttributeNS((String)null, "Encoding");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node appendChild(Node paramNode) {
/* 130 */     return this.constructionElement.appendChild(paramNode);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBaseLocalName() {
/* 135 */     return "Object";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/signature/ObjectContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */