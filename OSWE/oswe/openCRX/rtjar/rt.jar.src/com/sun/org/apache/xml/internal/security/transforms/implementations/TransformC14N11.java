/*    */ package com.sun.org.apache.xml.internal.security.transforms.implementations;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
/*    */ import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer11_OmitComments;
/*    */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*    */ import com.sun.org.apache.xml.internal.security.transforms.Transform;
/*    */ import com.sun.org.apache.xml.internal.security.transforms.TransformSpi;
/*    */ import java.io.OutputStream;
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
/*    */ public class TransformC14N11
/*    */   extends TransformSpi
/*    */ {
/*    */   protected String engineGetURI() {
/* 43 */     return "http://www.w3.org/2006/12/xml-c14n11";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected XMLSignatureInput enginePerformTransform(XMLSignatureInput paramXMLSignatureInput, OutputStream paramOutputStream, Transform paramTransform) throws CanonicalizationException {
/* 49 */     Canonicalizer11_OmitComments canonicalizer11_OmitComments = new Canonicalizer11_OmitComments();
/* 50 */     if (paramOutputStream != null) {
/* 51 */       canonicalizer11_OmitComments.setWriter(paramOutputStream);
/*    */     }
/* 53 */     byte[] arrayOfByte = null;
/* 54 */     arrayOfByte = canonicalizer11_OmitComments.engineCanonicalize(paramXMLSignatureInput);
/* 55 */     XMLSignatureInput xMLSignatureInput = new XMLSignatureInput(arrayOfByte);
/* 56 */     if (paramOutputStream != null) {
/* 57 */       xMLSignatureInput.setOutputStream(paramOutputStream);
/*    */     }
/* 59 */     return xMLSignatureInput;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/transforms/implementations/TransformC14N11.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */