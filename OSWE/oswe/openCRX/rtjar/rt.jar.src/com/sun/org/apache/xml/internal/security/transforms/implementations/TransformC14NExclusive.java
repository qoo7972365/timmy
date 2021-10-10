/*    */ package com.sun.org.apache.xml.internal.security.transforms.implementations;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
/*    */ import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer20010315ExclOmitComments;
/*    */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*    */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*    */ import com.sun.org.apache.xml.internal.security.transforms.Transform;
/*    */ import com.sun.org.apache.xml.internal.security.transforms.TransformSpi;
/*    */ import com.sun.org.apache.xml.internal.security.transforms.params.InclusiveNamespaces;
/*    */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*    */ import java.io.OutputStream;
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
/*    */ 
/*    */ 
/*    */ public class TransformC14NExclusive
/*    */   extends TransformSpi
/*    */ {
/*    */   public static final String implementedTransformURI = "http://www.w3.org/2001/10/xml-exc-c14n#";
/*    */   
/*    */   protected String engineGetURI() {
/* 54 */     return "http://www.w3.org/2001/10/xml-exc-c14n#";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected XMLSignatureInput enginePerformTransform(XMLSignatureInput paramXMLSignatureInput, OutputStream paramOutputStream, Transform paramTransform) throws CanonicalizationException {
/*    */     try {
/* 61 */       String str = null;
/*    */       
/* 63 */       if (paramTransform.length("http://www.w3.org/2001/10/xml-exc-c14n#", "InclusiveNamespaces") == 1) {
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 68 */         Element element = XMLUtils.selectNode(paramTransform
/* 69 */             .getElement().getFirstChild(), "http://www.w3.org/2001/10/xml-exc-c14n#", "InclusiveNamespaces", 0);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 77 */         str = (new InclusiveNamespaces(element, paramTransform.getBaseURI())).getInclusiveNamespaces();
/*    */       } 
/*    */       
/* 80 */       Canonicalizer20010315ExclOmitComments canonicalizer20010315ExclOmitComments = new Canonicalizer20010315ExclOmitComments();
/*    */       
/* 82 */       if (paramOutputStream != null) {
/* 83 */         canonicalizer20010315ExclOmitComments.setWriter(paramOutputStream);
/*    */       }
/* 85 */       byte[] arrayOfByte = canonicalizer20010315ExclOmitComments.engineCanonicalize(paramXMLSignatureInput, str);
/*    */       
/* 87 */       XMLSignatureInput xMLSignatureInput = new XMLSignatureInput(arrayOfByte);
/* 88 */       if (paramOutputStream != null) {
/* 89 */         xMLSignatureInput.setOutputStream(paramOutputStream);
/*    */       }
/* 91 */       return xMLSignatureInput;
/* 92 */     } catch (XMLSecurityException xMLSecurityException) {
/* 93 */       throw new CanonicalizationException("empty", xMLSecurityException);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/transforms/implementations/TransformC14NExclusive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */