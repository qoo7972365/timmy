/*     */ package com.sun.org.apache.xml.internal.security.transforms.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityRuntimeException;
/*     */ import com.sun.org.apache.xml.internal.security.signature.NodeFilter;
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.Transform;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.TransformSpi;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.TransformationException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XPathAPI;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XPathFactory;
/*     */ import java.io.OutputStream;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import org.w3c.dom.DOMException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TransformXPath
/*     */   extends TransformSpi
/*     */ {
/*     */   public static final String implementedTransformURI = "http://www.w3.org/TR/1999/REC-xpath-19991116";
/*     */   
/*     */   protected String engineGetURI() {
/*  65 */     return "http://www.w3.org/TR/1999/REC-xpath-19991116";
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected XMLSignatureInput enginePerformTransform(XMLSignatureInput paramXMLSignatureInput, OutputStream paramOutputStream, Transform paramTransform) throws TransformationException {
/*     */     try {
/*  91 */       Element element = XMLUtils.selectDsNode(paramTransform
/*  92 */           .getElement().getFirstChild(), "XPath", 0);
/*     */       
/*  94 */       if (element == null) {
/*  95 */         Object[] arrayOfObject = { "ds:XPath", "Transform" };
/*     */         
/*  97 */         throw new TransformationException("xml.WrongContent", arrayOfObject);
/*     */       } 
/*  99 */       Node node = element.getChildNodes().item(0);
/* 100 */       String str = XMLUtils.getStrFromNode(node);
/* 101 */       paramXMLSignatureInput.setNeedsToBeExpanded(needsCircumvent(str));
/* 102 */       if (node == null) {
/* 103 */         throw new DOMException((short)3, "Text must be in ds:Xpath");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 108 */       XPathFactory xPathFactory = XPathFactory.newInstance();
/* 109 */       XPathAPI xPathAPI = xPathFactory.newXPathAPI();
/* 110 */       paramXMLSignatureInput.addNodeFilter(new XPathNodeFilter(element, node, str, xPathAPI));
/* 111 */       paramXMLSignatureInput.setNodeSet(true);
/* 112 */       return paramXMLSignatureInput;
/* 113 */     } catch (DOMException dOMException) {
/* 114 */       throw new TransformationException("empty", dOMException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean needsCircumvent(String paramString) {
/* 123 */     return (paramString.indexOf("namespace") != -1 || paramString.indexOf("name()") != -1);
/*     */   }
/*     */   
/*     */   static class XPathNodeFilter
/*     */     implements NodeFilter {
/*     */     XPathAPI xPathAPI;
/*     */     Node xpathnode;
/*     */     Element xpathElement;
/*     */     String str;
/*     */     
/*     */     XPathNodeFilter(Element param1Element, Node param1Node, String param1String, XPathAPI param1XPathAPI) {
/* 134 */       this.xpathnode = param1Node;
/* 135 */       this.str = param1String;
/* 136 */       this.xpathElement = param1Element;
/* 137 */       this.xPathAPI = param1XPathAPI;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int isNodeInclude(Node param1Node) {
/*     */       try {
/* 145 */         boolean bool = this.xPathAPI.evaluate(param1Node, this.xpathnode, this.str, this.xpathElement);
/* 146 */         if (bool) {
/* 147 */           return 1;
/*     */         }
/* 149 */         return 0;
/* 150 */       } catch (TransformerException transformerException) {
/* 151 */         Object[] arrayOfObject = { param1Node };
/* 152 */         throw new XMLSecurityRuntimeException("signature.Transform.node", arrayOfObject, transformerException);
/* 153 */       } catch (Exception exception) {
/* 154 */         Object[] arrayOfObject = { param1Node, Short.valueOf(param1Node.getNodeType()) };
/* 155 */         throw new XMLSecurityRuntimeException("signature.Transform.nodeAndType", arrayOfObject, exception);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int isNodeIncludeDO(Node param1Node, int param1Int) {
/* 160 */       return isNodeInclude(param1Node);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/transforms/implementations/TransformXPath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */