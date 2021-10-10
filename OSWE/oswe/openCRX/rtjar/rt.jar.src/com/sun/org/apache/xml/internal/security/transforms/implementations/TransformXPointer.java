/*    */ package com.sun.org.apache.xml.internal.security.transforms.implementations;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*    */ import com.sun.org.apache.xml.internal.security.transforms.Transform;
/*    */ import com.sun.org.apache.xml.internal.security.transforms.TransformSpi;
/*    */ import com.sun.org.apache.xml.internal.security.transforms.TransformationException;
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
/*    */ public class TransformXPointer
/*    */   extends TransformSpi
/*    */ {
/*    */   public static final String implementedTransformURI = "http://www.w3.org/TR/2001/WD-xptr-20010108";
/*    */   
/*    */   protected String engineGetURI() {
/* 47 */     return "http://www.w3.org/TR/2001/WD-xptr-20010108";
/*    */   }
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
/*    */   protected XMLSignatureInput enginePerformTransform(XMLSignatureInput paramXMLSignatureInput, OutputStream paramOutputStream, Transform paramTransform) throws TransformationException {
/* 61 */     Object[] arrayOfObject = { "http://www.w3.org/TR/2001/WD-xptr-20010108" };
/*    */     
/* 63 */     throw new TransformationException("signature.Transform.NotYetImplemented", arrayOfObject);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/transforms/implementations/TransformXPointer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */