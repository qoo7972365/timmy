/*    */ package com.sun.org.apache.xml.internal.security.keys.content.x509;
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
/*    */ public class XMLX509CRL
/*    */   extends SignatureElementProxy
/*    */   implements XMLX509DataContent
/*    */ {
/*    */   public XMLX509CRL(Element paramElement, String paramString) throws XMLSecurityException {
/* 41 */     super(paramElement, paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XMLX509CRL(Document paramDocument, byte[] paramArrayOfbyte) {
/* 51 */     super(paramDocument);
/*    */     
/* 53 */     addBase64Text(paramArrayOfbyte);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] getCRLBytes() throws XMLSecurityException {
/* 63 */     return getBytesFromTextChild();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getBaseLocalName() {
/* 68 */     return "X509CRL";
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/content/x509/XMLX509CRL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */