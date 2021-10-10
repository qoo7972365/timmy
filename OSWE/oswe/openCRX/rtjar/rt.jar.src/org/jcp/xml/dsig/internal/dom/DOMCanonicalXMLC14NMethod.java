/*    */ package org.jcp.xml.dsig.internal.dom;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.security.c14n.Canonicalizer;
/*    */ import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;
/*    */ import java.security.InvalidAlgorithmParameterException;
/*    */ import javax.xml.crypto.Data;
/*    */ import javax.xml.crypto.XMLCryptoContext;
/*    */ import javax.xml.crypto.dsig.TransformException;
/*    */ import javax.xml.crypto.dsig.spec.TransformParameterSpec;
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
/*    */ 
/*    */ public final class DOMCanonicalXMLC14NMethod
/*    */   extends ApacheCanonicalizer
/*    */ {
/*    */   public void init(TransformParameterSpec paramTransformParameterSpec) throws InvalidAlgorithmParameterException {
/* 50 */     if (paramTransformParameterSpec != null) {
/* 51 */       throw new InvalidAlgorithmParameterException("no parameters should be specified for Canonical XML C14N algorithm");
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Data transform(Data paramData, XMLCryptoContext paramXMLCryptoContext) throws TransformException {
/* 62 */     if (paramData instanceof DOMSubTreeData) {
/* 63 */       DOMSubTreeData dOMSubTreeData = (DOMSubTreeData)paramData;
/* 64 */       if (dOMSubTreeData.excludeComments()) {
/*    */         try {
/* 66 */           this
/* 67 */             .apacheCanonicalizer = Canonicalizer.getInstance("http://www.w3.org/TR/2001/REC-xml-c14n-20010315");
/* 68 */         } catch (InvalidCanonicalizerException invalidCanonicalizerException) {
/* 69 */           throw new TransformException("Couldn't find Canonicalizer for: http://www.w3.org/TR/2001/REC-xml-c14n-20010315: " + invalidCanonicalizerException
/*    */ 
/*    */               
/* 72 */               .getMessage(), invalidCanonicalizerException);
/*    */         } 
/*    */       }
/*    */     } 
/*    */     
/* 77 */     return canonicalize(paramData, paramXMLCryptoContext);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMCanonicalXMLC14NMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */