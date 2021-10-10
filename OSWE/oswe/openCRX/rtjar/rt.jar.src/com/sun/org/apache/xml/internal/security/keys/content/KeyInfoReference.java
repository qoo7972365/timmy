/*     */ package com.sun.org.apache.xml.internal.security.keys.content;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.Signature11ElementProxy;
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
/*     */ public class KeyInfoReference
/*     */   extends Signature11ElementProxy
/*     */   implements KeyInfoContent
/*     */ {
/*     */   public KeyInfoReference(Element paramElement, String paramString) throws XMLSecurityException {
/*  47 */     super(paramElement, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyInfoReference(Document paramDocument, String paramString) {
/*  57 */     super(paramDocument);
/*     */     
/*  59 */     this.constructionElement.setAttributeNS(null, "URI", paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Attr getURIAttr() {
/*  68 */     return this.constructionElement.getAttributeNodeNS(null, "URI");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getURI() {
/*  77 */     return getURIAttr().getNodeValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(String paramString) {
/*  86 */     if (paramString != null) {
/*  87 */       this.constructionElement.setAttributeNS(null, "Id", paramString);
/*  88 */       this.constructionElement.setIdAttributeNS(null, "Id", true);
/*     */     } else {
/*  90 */       this.constructionElement.removeAttributeNS(null, "Id");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 100 */     return this.constructionElement.getAttributeNS(null, "Id");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBaseLocalName() {
/* 105 */     return "KeyInfoReference";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/content/KeyInfoReference.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */