/*    */ package com.sun.org.apache.xml.internal.security.transforms.params;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.security.transforms.TransformParam;
/*    */ import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
/*    */ import org.w3c.dom.Document;
/*    */ import org.w3c.dom.NodeList;
/*    */ import org.w3c.dom.Text;
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
/*    */ 
/*    */ 
/*    */ public class XPathContainer
/*    */   extends SignatureElementProxy
/*    */   implements TransformParam
/*    */ {
/*    */   public XPathContainer(Document paramDocument) {
/* 48 */     super(paramDocument);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setXPath(String paramString) {
/* 57 */     if (this.constructionElement.getChildNodes() != null) {
/* 58 */       NodeList nodeList = this.constructionElement.getChildNodes();
/*    */       
/* 60 */       for (byte b = 0; b < nodeList.getLength(); b++) {
/* 61 */         this.constructionElement.removeChild(nodeList.item(b));
/*    */       }
/*    */     } 
/*    */     
/* 65 */     Text text = this.doc.createTextNode(paramString);
/* 66 */     this.constructionElement.appendChild(text);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getXPath() {
/* 75 */     return getTextFromTextChild();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getBaseLocalName() {
/* 80 */     return "XPath";
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/transforms/params/XPathContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */