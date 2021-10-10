/*    */ package com.sun.org.apache.xml.internal.security.transforms.implementations;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
/*    */ import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer20010315WithComments;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TransformC14NWithComments
/*    */   extends TransformSpi
/*    */ {
/*    */   public static final String implementedTransformURI = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments";
/*    */   
/*    */   protected String engineGetURI() {
/* 48 */     return "http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected XMLSignatureInput enginePerformTransform(XMLSignatureInput paramXMLSignatureInput, OutputStream paramOutputStream, Transform paramTransform) throws CanonicalizationException {
/* 56 */     Canonicalizer20010315WithComments canonicalizer20010315WithComments = new Canonicalizer20010315WithComments();
/* 57 */     if (paramOutputStream != null) {
/* 58 */       canonicalizer20010315WithComments.setWriter(paramOutputStream);
/*    */     }
/*    */     
/* 61 */     byte[] arrayOfByte = null;
/* 62 */     arrayOfByte = canonicalizer20010315WithComments.engineCanonicalize(paramXMLSignatureInput);
/* 63 */     XMLSignatureInput xMLSignatureInput = new XMLSignatureInput(arrayOfByte);
/* 64 */     if (paramOutputStream != null) {
/* 65 */       xMLSignatureInput.setOutputStream(paramOutputStream);
/*    */     }
/* 67 */     return xMLSignatureInput;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/transforms/implementations/TransformC14NWithComments.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */