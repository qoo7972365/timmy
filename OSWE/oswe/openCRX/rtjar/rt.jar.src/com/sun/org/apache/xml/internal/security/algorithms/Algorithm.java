/*    */ package com.sun.org.apache.xml.internal.security.algorithms;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*    */ import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
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
/*    */ public abstract class Algorithm
/*    */   extends SignatureElementProxy
/*    */ {
/*    */   public Algorithm(Document paramDocument, String paramString) {
/* 42 */     super(paramDocument);
/*    */     
/* 44 */     setAlgorithmURI(paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Algorithm(Element paramElement, String paramString) throws XMLSecurityException {
/* 55 */     super(paramElement, paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getAlgorithmURI() {
/* 64 */     return this.constructionElement.getAttributeNS(null, "Algorithm");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setAlgorithmURI(String paramString) {
/* 73 */     if (paramString != null)
/* 74 */       this.constructionElement.setAttributeNS(null, "Algorithm", paramString); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/algorithms/Algorithm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */