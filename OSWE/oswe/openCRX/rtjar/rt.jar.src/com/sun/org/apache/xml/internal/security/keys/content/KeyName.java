/*    */ package com.sun.org.apache.xml.internal.security.keys.content;
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
/*    */ 
/*    */ public class KeyName
/*    */   extends SignatureElementProxy
/*    */   implements KeyInfoContent
/*    */ {
/*    */   public KeyName(Element paramElement, String paramString) throws XMLSecurityException {
/* 44 */     super(paramElement, paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public KeyName(Document paramDocument, String paramString) {
/* 54 */     super(paramDocument);
/*    */     
/* 56 */     addText(paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getKeyName() {
/* 65 */     return getTextFromTextChild();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getBaseLocalName() {
/* 70 */     return "KeyName";
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/content/KeyName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */