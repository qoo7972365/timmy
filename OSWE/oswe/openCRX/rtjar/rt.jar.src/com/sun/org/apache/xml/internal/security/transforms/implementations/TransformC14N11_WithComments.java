/*    */ package com.sun.org.apache.xml.internal.security.transforms.implementations;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
/*    */ import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer11_WithComments;
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
/*    */ public class TransformC14N11_WithComments
/*    */   extends TransformSpi
/*    */ {
/*    */   protected String engineGetURI() {
/* 43 */     return "http://www.w3.org/2006/12/xml-c14n11#WithComments";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected XMLSignatureInput enginePerformTransform(XMLSignatureInput paramXMLSignatureInput, OutputStream paramOutputStream, Transform paramTransform) throws CanonicalizationException {
/* 50 */     Canonicalizer11_WithComments canonicalizer11_WithComments = new Canonicalizer11_WithComments();
/* 51 */     if (paramOutputStream != null) {
/* 52 */       canonicalizer11_WithComments.setWriter(paramOutputStream);
/*    */     }
/*    */     
/* 55 */     byte[] arrayOfByte = null;
/* 56 */     arrayOfByte = canonicalizer11_WithComments.engineCanonicalize(paramXMLSignatureInput);
/* 57 */     XMLSignatureInput xMLSignatureInput = new XMLSignatureInput(arrayOfByte);
/* 58 */     if (paramOutputStream != null) {
/* 59 */       xMLSignatureInput.setOutputStream(paramOutputStream);
/*    */     }
/* 61 */     return xMLSignatureInput;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/transforms/implementations/TransformC14N11_WithComments.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */