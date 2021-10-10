/*    */ package org.jcp.xml.dsig.internal.dom;
/*    */ 
/*    */ import java.security.InvalidAlgorithmParameterException;
/*    */ import javax.xml.crypto.MarshalException;
/*    */ import javax.xml.crypto.XMLCryptoContext;
/*    */ import javax.xml.crypto.XMLStructure;
/*    */ import javax.xml.crypto.dom.DOMStructure;
/*    */ import javax.xml.crypto.dsig.spec.TransformParameterSpec;
/*    */ import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
/*    */ import org.w3c.dom.Element;
/*    */ import org.w3c.dom.Node;
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
/*    */ public final class DOMXSLTTransform
/*    */   extends ApacheTransform
/*    */ {
/*    */   public void init(TransformParameterSpec paramTransformParameterSpec) throws InvalidAlgorithmParameterException {
/* 49 */     if (paramTransformParameterSpec == null) {
/* 50 */       throw new InvalidAlgorithmParameterException("params are required");
/*    */     }
/* 52 */     if (!(paramTransformParameterSpec instanceof XSLTTransformParameterSpec)) {
/* 53 */       throw new InvalidAlgorithmParameterException("unrecognized params");
/*    */     }
/* 55 */     this.params = paramTransformParameterSpec;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(XMLStructure paramXMLStructure, XMLCryptoContext paramXMLCryptoContext) throws InvalidAlgorithmParameterException {
/* 61 */     super.init(paramXMLStructure, paramXMLCryptoContext);
/* 62 */     unmarshalParams(DOMUtils.getFirstChildElement(this.transformElem));
/*    */   }
/*    */   
/*    */   private void unmarshalParams(Element paramElement) {
/* 66 */     this.params = new XSLTTransformParameterSpec(new DOMStructure(paramElement));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void marshalParams(XMLStructure paramXMLStructure, XMLCryptoContext paramXMLCryptoContext) throws MarshalException {
/* 72 */     super.marshalParams(paramXMLStructure, paramXMLCryptoContext);
/*    */     
/* 74 */     XSLTTransformParameterSpec xSLTTransformParameterSpec = (XSLTTransformParameterSpec)getParameterSpec();
/*    */     
/* 76 */     Node node = ((DOMStructure)xSLTTransformParameterSpec.getStylesheet()).getNode();
/* 77 */     DOMUtils.appendChild(this.transformElem, node);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMXSLTTransform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */