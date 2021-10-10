/*    */ package com.sun.org.apache.xml.internal.security.transforms.implementations;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
/*    */ import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer20010315OmitComments;
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
/*    */ 
/*    */ 
/*    */ public class TransformC14N
/*    */   extends TransformSpi
/*    */ {
/*    */   public static final String implementedTransformURI = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";
/*    */   
/*    */   protected String engineGetURI() {
/* 50 */     return "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected XMLSignatureInput enginePerformTransform(XMLSignatureInput paramXMLSignatureInput, OutputStream paramOutputStream, Transform paramTransform) throws CanonicalizationException {
/* 56 */     Canonicalizer20010315OmitComments canonicalizer20010315OmitComments = new Canonicalizer20010315OmitComments();
/* 57 */     if (paramOutputStream != null) {
/* 58 */       canonicalizer20010315OmitComments.setWriter(paramOutputStream);
/*    */     }
/* 60 */     byte[] arrayOfByte = null;
/* 61 */     arrayOfByte = canonicalizer20010315OmitComments.engineCanonicalize(paramXMLSignatureInput);
/* 62 */     XMLSignatureInput xMLSignatureInput = new XMLSignatureInput(arrayOfByte);
/* 63 */     if (paramOutputStream != null) {
/* 64 */       xMLSignatureInput.setOutputStream(paramOutputStream);
/*    */     }
/* 66 */     return xMLSignatureInput;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/transforms/implementations/TransformC14N.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */