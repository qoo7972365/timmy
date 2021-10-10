/*    */ package com.sun.org.apache.xml.internal.security.utils;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*    */ import org.w3c.dom.Document;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Signature11ElementProxy
/*    */   extends ElementProxy
/*    */ {
/*    */   protected Signature11ElementProxy() {}
/*    */   
/*    */   public Signature11ElementProxy(Document paramDocument) {
/* 45 */     if (paramDocument == null) {
/* 46 */       throw new RuntimeException("Document is null");
/*    */     }
/*    */     
/* 49 */     this.doc = paramDocument;
/* 50 */     this
/* 51 */       .constructionElement = XMLUtils.createElementInSignature11Space(this.doc, getBaseLocalName());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Signature11ElementProxy(Element paramElement, String paramString) throws XMLSecurityException {
/* 62 */     super(paramElement, paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getBaseNamespace() {
/* 68 */     return "http://www.w3.org/2009/xmldsig11#";
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/utils/Signature11ElementProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */