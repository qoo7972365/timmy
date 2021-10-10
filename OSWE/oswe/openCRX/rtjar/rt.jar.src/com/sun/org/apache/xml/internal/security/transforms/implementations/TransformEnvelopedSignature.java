/*     */ package com.sun.org.apache.xml.internal.security.transforms.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.signature.NodeFilter;
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.Transform;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.TransformSpi;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.TransformationException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import java.io.OutputStream;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TransformEnvelopedSignature
/*     */   extends TransformSpi
/*     */ {
/*     */   public static final String implementedTransformURI = "http://www.w3.org/2000/09/xmldsig#enveloped-signature";
/*     */   
/*     */   protected String engineGetURI() {
/*  56 */     return "http://www.w3.org/2000/09/xmldsig#enveloped-signature";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected XMLSignatureInput enginePerformTransform(XMLSignatureInput paramXMLSignatureInput, OutputStream paramOutputStream, Transform paramTransform) throws TransformationException {
/*  77 */     Element element = paramTransform.getElement();
/*     */     
/*  79 */     Node node = searchSignatureElement(element);
/*  80 */     paramXMLSignatureInput.setExcludeNode(node);
/*  81 */     paramXMLSignatureInput.addNodeFilter(new EnvelopedNodeFilter(node));
/*  82 */     return paramXMLSignatureInput;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Node searchSignatureElement(Node paramNode) throws TransformationException {
/*  92 */     boolean bool = false;
/*     */ 
/*     */     
/*  95 */     while (paramNode != null && paramNode
/*  96 */       .getNodeType() != 9) {
/*     */ 
/*     */       
/*  99 */       Element element = (Element)paramNode;
/* 100 */       if (element.getNamespaceURI().equals("http://www.w3.org/2000/09/xmldsig#") && element
/* 101 */         .getLocalName().equals("Signature")) {
/* 102 */         bool = true;
/*     */         
/*     */         break;
/*     */       } 
/* 106 */       paramNode = paramNode.getParentNode();
/*     */     } 
/*     */     
/* 109 */     if (!bool) {
/* 110 */       throw new TransformationException("transform.envelopedSignatureTransformNotInSignatureElement");
/*     */     }
/*     */     
/* 113 */     return paramNode;
/*     */   }
/*     */   
/*     */   static class EnvelopedNodeFilter
/*     */     implements NodeFilter {
/*     */     Node exclude;
/*     */     
/*     */     EnvelopedNodeFilter(Node param1Node) {
/* 121 */       this.exclude = param1Node;
/*     */     }
/*     */     
/*     */     public int isNodeIncludeDO(Node param1Node, int param1Int) {
/* 125 */       if (param1Node == this.exclude) {
/* 126 */         return -1;
/*     */       }
/* 128 */       return 1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int isNodeInclude(Node param1Node) {
/* 135 */       if (param1Node == this.exclude || XMLUtils.isDescendantOrSelf(this.exclude, param1Node)) {
/* 136 */         return -1;
/*     */       }
/* 138 */       return 1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/transforms/implementations/TransformEnvelopedSignature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */