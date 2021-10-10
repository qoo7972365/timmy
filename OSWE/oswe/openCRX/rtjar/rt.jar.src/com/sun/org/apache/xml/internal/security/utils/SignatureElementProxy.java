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
/*    */ public abstract class SignatureElementProxy
/*    */   extends ElementProxy
/*    */ {
/*    */   protected SignatureElementProxy() {}
/*    */   
/*    */   public SignatureElementProxy(Document paramDocument) {
/* 45 */     if (paramDocument == null) {
/* 46 */       throw new RuntimeException("Document is null");
/*    */     }
/*    */     
/* 49 */     this.doc = paramDocument;
/* 50 */     this
/* 51 */       .constructionElement = XMLUtils.createElementInSignatureSpace(this.doc, getBaseLocalName());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SignatureElementProxy(Element paramElement, String paramString) throws XMLSecurityException {
/* 62 */     super(paramElement, paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getBaseNamespace() {
/* 68 */     return "http://www.w3.org/2000/09/xmldsig#";
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/utils/SignatureElementProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */