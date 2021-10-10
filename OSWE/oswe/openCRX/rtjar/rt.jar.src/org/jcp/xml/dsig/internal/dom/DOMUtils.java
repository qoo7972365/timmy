/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.XMLStructure;
/*     */ import javax.xml.crypto.dom.DOMStructure;
/*     */ import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
/*     */ import javax.xml.crypto.dsig.spec.XPathFilter2ParameterSpec;
/*     */ import javax.xml.crypto.dsig.spec.XPathFilterParameterSpec;
/*     */ import javax.xml.crypto.dsig.spec.XPathType;
/*     */ import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
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
/*     */ public class DOMUtils
/*     */ {
/*     */   public static Document getOwnerDocument(Node paramNode) {
/*  59 */     if (paramNode.getNodeType() == 9) {
/*  60 */       return (Document)paramNode;
/*     */     }
/*  62 */     return paramNode.getOwnerDocument();
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
/*     */   public static Element createElement(Document paramDocument, String paramString1, String paramString2, String paramString3) {
/*  79 */     String str = (paramString3 == null || paramString3.length() == 0) ? paramString1 : (paramString3 + ":" + paramString1);
/*     */     
/*  81 */     return paramDocument.createElementNS(paramString2, str);
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
/*     */   public static void setAttribute(Element paramElement, String paramString1, String paramString2) {
/*  93 */     if (paramString2 == null) {
/*     */       return;
/*     */     }
/*  96 */     paramElement.setAttributeNS((String)null, paramString1, paramString2);
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
/*     */   public static void setAttributeID(Element paramElement, String paramString1, String paramString2) {
/* 110 */     if (paramString2 == null) {
/*     */       return;
/*     */     }
/* 113 */     paramElement.setAttributeNS((String)null, paramString1, paramString2);
/* 114 */     paramElement.setIdAttributeNS((String)null, paramString1, true);
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
/*     */   public static Element getFirstChildElement(Node paramNode) {
/* 127 */     Node node = paramNode.getFirstChild();
/* 128 */     while (node != null && node.getNodeType() != 1) {
/* 129 */       node = node.getNextSibling();
/*     */     }
/* 131 */     return (Element)node;
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
/*     */   public static Element getFirstChildElement(Node paramNode, String paramString) throws MarshalException {
/* 147 */     return verifyElement(getFirstChildElement(paramNode), paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Element verifyElement(Element paramElement, String paramString) throws MarshalException {
/* 153 */     if (paramElement == null) {
/* 154 */       throw new MarshalException("Missing " + paramString + " element");
/*     */     }
/* 156 */     String str = paramElement.getLocalName();
/* 157 */     if (!str.equals(paramString)) {
/* 158 */       throw new MarshalException("Invalid element name: " + str + ", expected " + paramString);
/*     */     }
/*     */     
/* 161 */     return paramElement;
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
/*     */   public static Element getLastChildElement(Node paramNode) {
/* 174 */     Node node = paramNode.getLastChild();
/* 175 */     while (node != null && node.getNodeType() != 1) {
/* 176 */       node = node.getPreviousSibling();
/*     */     }
/* 178 */     return (Element)node;
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
/*     */   public static Element getNextSiblingElement(Node paramNode) {
/* 191 */     Node node = paramNode.getNextSibling();
/* 192 */     while (node != null && node.getNodeType() != 1) {
/* 193 */       node = node.getNextSibling();
/*     */     }
/* 195 */     return (Element)node;
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
/*     */   public static Element getNextSiblingElement(Node paramNode, String paramString) throws MarshalException {
/* 211 */     return verifyElement(getNextSiblingElement(paramNode), paramString);
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
/*     */   public static String getAttributeValue(Element paramElement, String paramString) {
/* 229 */     Attr attr = paramElement.getAttributeNodeNS((String)null, paramString);
/* 230 */     return (attr == null) ? null : attr.getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set<Node> nodeSet(NodeList paramNodeList) {
/* 241 */     return new NodeSet(paramNodeList);
/*     */   }
/*     */   
/*     */   static class NodeSet extends AbstractSet<Node> { private NodeList nl;
/*     */     
/*     */     public NodeSet(NodeList param1NodeList) {
/* 247 */       this.nl = param1NodeList;
/*     */     }
/*     */     public int size() {
/* 250 */       return this.nl.getLength();
/*     */     } public Iterator<Node> iterator() {
/* 252 */       return new Iterator<Node>() {
/* 253 */           int index = 0;
/*     */           
/*     */           public void remove() {
/* 256 */             throw new UnsupportedOperationException();
/*     */           }
/*     */           public Node next() {
/* 259 */             if (!hasNext()) {
/* 260 */               throw new NoSuchElementException();
/*     */             }
/* 262 */             return DOMUtils.NodeSet.this.nl.item(this.index++);
/*     */           }
/*     */           public boolean hasNext() {
/* 265 */             return (this.index < DOMUtils.NodeSet.this.nl.getLength());
/*     */           }
/*     */         };
/*     */     } }
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
/*     */   public static String getNSPrefix(XMLCryptoContext paramXMLCryptoContext, String paramString) {
/* 280 */     if (paramXMLCryptoContext != null) {
/* 281 */       return paramXMLCryptoContext
/* 282 */         .getNamespacePrefix(paramString, paramXMLCryptoContext.getDefaultNamespacePrefix());
/*     */     }
/* 284 */     return null;
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
/*     */   public static String getSignaturePrefix(XMLCryptoContext paramXMLCryptoContext) {
/* 296 */     return getNSPrefix(paramXMLCryptoContext, "http://www.w3.org/2000/09/xmldsig#");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeAllChildren(Node paramNode) {
/* 305 */     NodeList nodeList = paramNode.getChildNodes(); byte b; int i;
/* 306 */     for (b = 0, i = nodeList.getLength(); b < i; b++) {
/* 307 */       paramNode.removeChild(nodeList.item(b));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean nodesEqual(Node paramNode1, Node paramNode2) {
/* 315 */     if (paramNode1 == paramNode2) {
/* 316 */       return true;
/*     */     }
/* 318 */     if (paramNode1.getNodeType() != paramNode2.getNodeType()) {
/* 319 */       return false;
/*     */     }
/*     */     
/* 322 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void appendChild(Node paramNode1, Node paramNode2) {
/* 331 */     Document document = getOwnerDocument(paramNode1);
/* 332 */     if (paramNode2.getOwnerDocument() != document) {
/* 333 */       paramNode1.appendChild(document.importNode(paramNode2, true));
/*     */     } else {
/* 335 */       paramNode1.appendChild(paramNode2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean paramsEqual(AlgorithmParameterSpec paramAlgorithmParameterSpec1, AlgorithmParameterSpec paramAlgorithmParameterSpec2) {
/* 341 */     if (paramAlgorithmParameterSpec1 == paramAlgorithmParameterSpec2) {
/* 342 */       return true;
/*     */     }
/* 344 */     if (paramAlgorithmParameterSpec1 instanceof XPathFilter2ParameterSpec && paramAlgorithmParameterSpec2 instanceof XPathFilter2ParameterSpec)
/*     */     {
/* 346 */       return paramsEqual((XPathFilter2ParameterSpec)paramAlgorithmParameterSpec1, (XPathFilter2ParameterSpec)paramAlgorithmParameterSpec2);
/*     */     }
/*     */     
/* 349 */     if (paramAlgorithmParameterSpec1 instanceof ExcC14NParameterSpec && paramAlgorithmParameterSpec2 instanceof ExcC14NParameterSpec)
/*     */     {
/* 351 */       return paramsEqual((ExcC14NParameterSpec)paramAlgorithmParameterSpec1, (ExcC14NParameterSpec)paramAlgorithmParameterSpec2);
/*     */     }
/*     */     
/* 354 */     if (paramAlgorithmParameterSpec1 instanceof XPathFilterParameterSpec && paramAlgorithmParameterSpec2 instanceof XPathFilterParameterSpec)
/*     */     {
/* 356 */       return paramsEqual((XPathFilterParameterSpec)paramAlgorithmParameterSpec1, (XPathFilterParameterSpec)paramAlgorithmParameterSpec2);
/*     */     }
/*     */     
/* 359 */     if (paramAlgorithmParameterSpec1 instanceof XSLTTransformParameterSpec && paramAlgorithmParameterSpec2 instanceof XSLTTransformParameterSpec)
/*     */     {
/* 361 */       return paramsEqual((XSLTTransformParameterSpec)paramAlgorithmParameterSpec1, (XSLTTransformParameterSpec)paramAlgorithmParameterSpec2);
/*     */     }
/*     */     
/* 364 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean paramsEqual(XPathFilter2ParameterSpec paramXPathFilter2ParameterSpec1, XPathFilter2ParameterSpec paramXPathFilter2ParameterSpec2) {
/* 371 */     List<XPathType> list1 = paramXPathFilter2ParameterSpec1.getXPathList();
/*     */     
/* 373 */     List<XPathType> list2 = paramXPathFilter2ParameterSpec2.getXPathList();
/* 374 */     int i = list1.size();
/* 375 */     if (i != list2.size()) {
/* 376 */       return false;
/*     */     }
/* 378 */     for (byte b = 0; b < i; b++) {
/* 379 */       XPathType xPathType1 = list1.get(b);
/* 380 */       XPathType xPathType2 = list2.get(b);
/* 381 */       if (!xPathType1.getExpression().equals(xPathType2.getExpression()) || 
/* 382 */         !xPathType1.getNamespaceMap().equals(xPathType2.getNamespaceMap()) || xPathType1
/* 383 */         .getFilter() != xPathType2.getFilter()) {
/* 384 */         return false;
/*     */       }
/*     */     } 
/* 387 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean paramsEqual(ExcC14NParameterSpec paramExcC14NParameterSpec1, ExcC14NParameterSpec paramExcC14NParameterSpec2) {
/* 393 */     return paramExcC14NParameterSpec1.getPrefixList().equals(paramExcC14NParameterSpec2.getPrefixList());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean paramsEqual(XPathFilterParameterSpec paramXPathFilterParameterSpec1, XPathFilterParameterSpec paramXPathFilterParameterSpec2) {
/* 399 */     return (paramXPathFilterParameterSpec1.getXPath().equals(paramXPathFilterParameterSpec2.getXPath()) && paramXPathFilterParameterSpec1
/* 400 */       .getNamespaceMap().equals(paramXPathFilterParameterSpec2.getNamespaceMap()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean paramsEqual(XSLTTransformParameterSpec paramXSLTTransformParameterSpec1, XSLTTransformParameterSpec paramXSLTTransformParameterSpec2) {
/* 407 */     XMLStructure xMLStructure1 = paramXSLTTransformParameterSpec2.getStylesheet();
/* 408 */     if (!(xMLStructure1 instanceof DOMStructure)) {
/* 409 */       return false;
/*     */     }
/*     */     
/* 412 */     Node node1 = ((DOMStructure)xMLStructure1).getNode();
/* 413 */     XMLStructure xMLStructure2 = paramXSLTTransformParameterSpec1.getStylesheet();
/*     */     
/* 415 */     Node node2 = ((DOMStructure)xMLStructure2).getNode();
/* 416 */     return nodesEqual(node2, node1);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */