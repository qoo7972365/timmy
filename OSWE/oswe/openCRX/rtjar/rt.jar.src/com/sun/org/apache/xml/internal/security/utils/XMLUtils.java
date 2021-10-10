/*      */ package com.sun.org.apache.xml.internal.security.utils;
/*      */ 
/*      */ import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
/*      */ import com.sun.org.apache.xml.internal.security.c14n.Canonicalizer;
/*      */ import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Set;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import org.w3c.dom.Attr;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.NamedNodeMap;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.NodeList;
/*      */ import org.w3c.dom.ProcessingInstruction;
/*      */ import org.w3c.dom.Text;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XMLUtils
/*      */ {
/*   55 */   private static boolean ignoreLineBreaks = ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>() {
/*      */         public Boolean run() {
/*   57 */           return Boolean.valueOf(
/*   58 */               Boolean.getBoolean("com.sun.org.apache.xml.internal.security.ignoreLineBreaks"));
/*      */         }
/*   60 */       })).booleanValue();
/*      */   
/*   62 */   private static volatile String dsPrefix = "ds";
/*   63 */   private static volatile String ds11Prefix = "dsig11";
/*   64 */   private static volatile String xencPrefix = "xenc";
/*   65 */   private static volatile String xenc11Prefix = "xenc11";
/*      */ 
/*      */ 
/*      */   
/*   69 */   private static final Logger log = Logger.getLogger(XMLUtils.class.getName());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setDsPrefix(String paramString) {
/*   87 */     JavaUtils.checkRegisterPermission();
/*   88 */     dsPrefix = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setDs11Prefix(String paramString) {
/*   98 */     JavaUtils.checkRegisterPermission();
/*   99 */     ds11Prefix = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setXencPrefix(String paramString) {
/*  109 */     JavaUtils.checkRegisterPermission();
/*  110 */     xencPrefix = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setXenc11Prefix(String paramString) {
/*  120 */     JavaUtils.checkRegisterPermission();
/*  121 */     xenc11Prefix = paramString;
/*      */   }
/*      */   
/*      */   public static Element getNextElement(Node paramNode) {
/*  125 */     Node node = paramNode;
/*  126 */     while (node != null && node.getNodeType() != 1) {
/*  127 */       node = node.getNextSibling();
/*      */     }
/*  129 */     return (Element)node;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void getSet(Node paramNode1, Set<Node> paramSet, Node paramNode2, boolean paramBoolean) {
/*  139 */     if (paramNode2 != null && isDescendantOrSelf(paramNode2, paramNode1)) {
/*      */       return;
/*      */     }
/*  142 */     getSetRec(paramNode1, paramSet, paramNode2, paramBoolean);
/*      */   }
/*      */   
/*      */   private static void getSetRec(Node paramNode1, Set<Node> paramSet, Node paramNode2, boolean paramBoolean) {
/*      */     Element element;
/*      */     Node node;
/*  148 */     if (paramNode1 == paramNode2) {
/*      */       return;
/*      */     }
/*  151 */     switch (paramNode1.getNodeType()) {
/*      */       case 1:
/*  153 */         paramSet.add(paramNode1);
/*  154 */         element = (Element)paramNode1;
/*  155 */         if (element.hasAttributes()) {
/*  156 */           NamedNodeMap namedNodeMap = element.getAttributes();
/*  157 */           for (byte b = 0; b < namedNodeMap.getLength(); b++) {
/*  158 */             paramSet.add(namedNodeMap.item(b));
/*      */           }
/*      */         } 
/*      */       
/*      */       case 9:
/*  163 */         for (node = paramNode1.getFirstChild(); node != null; node = node.getNextSibling()) {
/*  164 */           if (node.getNodeType() == 3) {
/*  165 */             paramSet.add(node);
/*  166 */             while (node != null && node.getNodeType() == 3) {
/*  167 */               node = node.getNextSibling();
/*      */             }
/*  169 */             if (node == null) {
/*      */               return;
/*      */             }
/*      */           } 
/*  173 */           getSetRec(node, paramSet, paramNode2, paramBoolean);
/*      */         } 
/*      */         return;
/*      */       case 8:
/*  177 */         if (paramBoolean) {
/*  178 */           paramSet.add(paramNode1);
/*      */         }
/*      */         return;
/*      */       case 10:
/*      */         return;
/*      */     } 
/*  184 */     paramSet.add(paramNode1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void outputDOM(Node paramNode, OutputStream paramOutputStream) {
/*  196 */     outputDOM(paramNode, paramOutputStream, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void outputDOM(Node paramNode, OutputStream paramOutputStream, boolean paramBoolean) {
/*      */     try {
/*  210 */       if (paramBoolean) {
/*  211 */         paramOutputStream.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".getBytes("UTF-8"));
/*      */       }
/*      */       
/*  214 */       paramOutputStream.write(Canonicalizer.getInstance("http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments")
/*  215 */           .canonicalizeSubtree(paramNode));
/*      */     }
/*  217 */     catch (IOException iOException) {
/*  218 */       if (log.isLoggable(Level.FINE)) {
/*  219 */         log.log(Level.FINE, iOException.getMessage(), iOException);
/*      */       }
/*      */     }
/*  222 */     catch (InvalidCanonicalizerException invalidCanonicalizerException) {
/*  223 */       if (log.isLoggable(Level.FINE)) {
/*  224 */         log.log(Level.FINE, invalidCanonicalizerException.getMessage(), invalidCanonicalizerException);
/*      */       }
/*  226 */     } catch (CanonicalizationException canonicalizationException) {
/*  227 */       if (log.isLoggable(Level.FINE)) {
/*  228 */         log.log(Level.FINE, canonicalizationException.getMessage(), canonicalizationException);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void outputDOMc14nWithComments(Node paramNode, OutputStream paramOutputStream) {
/*      */     try {
/*  248 */       paramOutputStream.write(Canonicalizer.getInstance("http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments")
/*  249 */           .canonicalizeSubtree(paramNode));
/*      */     }
/*  251 */     catch (IOException iOException) {
/*  252 */       if (log.isLoggable(Level.FINE)) {
/*  253 */         log.log(Level.FINE, iOException.getMessage(), iOException);
/*      */       }
/*      */     }
/*  256 */     catch (InvalidCanonicalizerException invalidCanonicalizerException) {
/*  257 */       if (log.isLoggable(Level.FINE)) {
/*  258 */         log.log(Level.FINE, invalidCanonicalizerException.getMessage(), invalidCanonicalizerException);
/*      */       }
/*      */     }
/*  261 */     catch (CanonicalizationException canonicalizationException) {
/*  262 */       if (log.isLoggable(Level.FINE)) {
/*  263 */         log.log(Level.FINE, canonicalizationException.getMessage(), canonicalizationException);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getFullTextChildrenFromElement(Element paramElement) {
/*  276 */     StringBuilder stringBuilder = new StringBuilder();
/*      */     
/*  278 */     Node node = paramElement.getFirstChild();
/*  279 */     while (node != null) {
/*  280 */       if (node.getNodeType() == 3) {
/*  281 */         stringBuilder.append(((Text)node).getData());
/*      */       }
/*  283 */       node = node.getNextSibling();
/*      */     } 
/*      */     
/*  286 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Element createElementInSignatureSpace(Document paramDocument, String paramString) {
/*  297 */     if (paramDocument == null) {
/*  298 */       throw new RuntimeException("Document is null");
/*      */     }
/*      */     
/*  301 */     if (dsPrefix == null || dsPrefix.length() == 0) {
/*  302 */       return paramDocument.createElementNS("http://www.w3.org/2000/09/xmldsig#", paramString);
/*      */     }
/*  304 */     return paramDocument.createElementNS("http://www.w3.org/2000/09/xmldsig#", dsPrefix + ":" + paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Element createElementInSignature11Space(Document paramDocument, String paramString) {
/*  315 */     if (paramDocument == null) {
/*  316 */       throw new RuntimeException("Document is null");
/*      */     }
/*      */     
/*  319 */     if (ds11Prefix == null || ds11Prefix.length() == 0) {
/*  320 */       return paramDocument.createElementNS("http://www.w3.org/2009/xmldsig11#", paramString);
/*      */     }
/*  322 */     return paramDocument.createElementNS("http://www.w3.org/2009/xmldsig11#", ds11Prefix + ":" + paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Element createElementInEncryptionSpace(Document paramDocument, String paramString) {
/*  333 */     if (paramDocument == null) {
/*  334 */       throw new RuntimeException("Document is null");
/*      */     }
/*      */     
/*  337 */     if (xencPrefix == null || xencPrefix.length() == 0) {
/*  338 */       return paramDocument.createElementNS("http://www.w3.org/2001/04/xmlenc#", paramString);
/*      */     }
/*  340 */     return paramDocument
/*  341 */       .createElementNS("http://www.w3.org/2001/04/xmlenc#", xencPrefix + ":" + paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Element createElementInEncryption11Space(Document paramDocument, String paramString) {
/*  354 */     if (paramDocument == null) {
/*  355 */       throw new RuntimeException("Document is null");
/*      */     }
/*      */     
/*  358 */     if (xenc11Prefix == null || xenc11Prefix.length() == 0) {
/*  359 */       return paramDocument.createElementNS("http://www.w3.org/2009/xmlenc11#", paramString);
/*      */     }
/*  361 */     return paramDocument
/*  362 */       .createElementNS("http://www.w3.org/2009/xmlenc11#", xenc11Prefix + ":" + paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean elementIsInSignatureSpace(Element paramElement, String paramString) {
/*  377 */     if (paramElement == null) {
/*  378 */       return false;
/*      */     }
/*      */     
/*  381 */     return ("http://www.w3.org/2000/09/xmldsig#".equals(paramElement.getNamespaceURI()) && paramElement
/*  382 */       .getLocalName().equals(paramString));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean elementIsInSignature11Space(Element paramElement, String paramString) {
/*  395 */     if (paramElement == null) {
/*  396 */       return false;
/*      */     }
/*      */     
/*  399 */     return ("http://www.w3.org/2009/xmldsig11#".equals(paramElement.getNamespaceURI()) && paramElement
/*  400 */       .getLocalName().equals(paramString));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean elementIsInEncryptionSpace(Element paramElement, String paramString) {
/*  413 */     if (paramElement == null) {
/*  414 */       return false;
/*      */     }
/*  416 */     return ("http://www.w3.org/2001/04/xmlenc#".equals(paramElement.getNamespaceURI()) && paramElement
/*  417 */       .getLocalName().equals(paramString));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean elementIsInEncryption11Space(Element paramElement, String paramString) {
/*  430 */     if (paramElement == null) {
/*  431 */       return false;
/*      */     }
/*  433 */     return ("http://www.w3.org/2009/xmlenc11#".equals(paramElement.getNamespaceURI()) && paramElement
/*  434 */       .getLocalName().equals(paramString));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Document getOwnerDocument(Node paramNode) {
/*  447 */     if (paramNode.getNodeType() == 9) {
/*  448 */       return (Document)paramNode;
/*      */     }
/*      */     try {
/*  451 */       return paramNode.getOwnerDocument();
/*  452 */     } catch (NullPointerException nullPointerException) {
/*  453 */       throw new NullPointerException(I18n.translate("endorsed.jdk1.4.0") + " Original message was \"" + nullPointerException
/*      */           
/*  455 */           .getMessage() + "\"");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Document getOwnerDocument(Set<Node> paramSet) {
/*  469 */     NullPointerException nullPointerException = null;
/*  470 */     for (Node node : paramSet) {
/*  471 */       short s = node.getNodeType();
/*  472 */       if (s == 9) {
/*  473 */         return (Document)node;
/*      */       }
/*      */       try {
/*  476 */         if (s == 2) {
/*  477 */           return ((Attr)node).getOwnerElement().getOwnerDocument();
/*      */         }
/*  479 */         return node.getOwnerDocument();
/*  480 */       } catch (NullPointerException nullPointerException1) {
/*  481 */         nullPointerException = nullPointerException1;
/*      */       } 
/*      */     } 
/*      */     
/*  485 */     throw new NullPointerException(I18n.translate("endorsed.jdk1.4.0") + " Original message was \"" + ((nullPointerException == null) ? "" : nullPointerException
/*      */         
/*  487 */         .getMessage()) + "\"");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Element createDSctx(Document paramDocument, String paramString1, String paramString2) {
/*  499 */     if (paramString1 == null || paramString1.trim().length() == 0) {
/*  500 */       throw new IllegalArgumentException("You must supply a prefix");
/*      */     }
/*      */     
/*  503 */     Element element = paramDocument.createElementNS(null, "namespaceContext");
/*      */     
/*  505 */     element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + paramString1.trim(), paramString2);
/*      */     
/*  507 */     return element;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void addReturnToElement(Element paramElement) {
/*  516 */     if (!ignoreLineBreaks) {
/*  517 */       Document document = paramElement.getOwnerDocument();
/*  518 */       paramElement.appendChild(document.createTextNode("\n"));
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void addReturnToElement(Document paramDocument, HelperNodeList paramHelperNodeList) {
/*  523 */     if (!ignoreLineBreaks) {
/*  524 */       paramHelperNodeList.appendChild(paramDocument.createTextNode("\n"));
/*      */     }
/*      */   }
/*      */   
/*      */   public static void addReturnBeforeChild(Element paramElement, Node paramNode) {
/*  529 */     if (!ignoreLineBreaks) {
/*  530 */       Document document = paramElement.getOwnerDocument();
/*  531 */       paramElement.insertBefore(document.createTextNode("\n"), paramNode);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Set<Node> convertNodelistToSet(NodeList paramNodeList) {
/*  542 */     if (paramNodeList == null) {
/*  543 */       return new HashSet<>();
/*      */     }
/*      */     
/*  546 */     int i = paramNodeList.getLength();
/*  547 */     HashSet<Node> hashSet = new HashSet(i);
/*      */     
/*  549 */     for (byte b = 0; b < i; b++) {
/*  550 */       hashSet.add(paramNodeList.item(b));
/*      */     }
/*      */     
/*  553 */     return hashSet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void circumventBug2650(Document paramDocument) {
/*  570 */     Element element = paramDocument.getDocumentElement();
/*      */ 
/*      */ 
/*      */     
/*  574 */     Attr attr = element.getAttributeNodeNS("http://www.w3.org/2000/xmlns/", "xmlns");
/*      */     
/*  576 */     if (attr == null) {
/*  577 */       element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "");
/*      */     }
/*      */     
/*  580 */     circumventBug2650internal(paramDocument);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void circumventBug2650internal(Node paramNode) {
/*  592 */     Node node1 = null;
/*  593 */     Node node2 = null;
/*      */     while (true) {
/*      */       Element element;
/*  596 */       switch (paramNode.getNodeType()) {
/*      */         case 1:
/*  598 */           element = (Element)paramNode;
/*  599 */           if (!element.hasChildNodes()) {
/*      */             break;
/*      */           }
/*  602 */           if (element.hasAttributes()) {
/*  603 */             NamedNodeMap namedNodeMap = element.getAttributes();
/*  604 */             int i = namedNodeMap.getLength();
/*      */             
/*  606 */             for (Node node = element.getFirstChild(); node != null; 
/*  607 */               node = node.getNextSibling()) {
/*      */               
/*  609 */               if (node.getNodeType() == 1) {
/*      */ 
/*      */                 
/*  612 */                 Element element1 = (Element)node;
/*      */                 
/*  614 */                 for (byte b = 0; b < i; b++)
/*  615 */                 { Attr attr = (Attr)namedNodeMap.item(b);
/*  616 */                   if ("http://www.w3.org/2000/xmlns/".equals(attr.getNamespaceURI()))
/*      */                   {
/*      */                     
/*  619 */                     if (!element1.hasAttributeNS("http://www.w3.org/2000/xmlns/", attr
/*  620 */                         .getLocalName()))
/*      */                     {
/*      */                       
/*  623 */                       element1.setAttributeNS("http://www.w3.org/2000/xmlns/", attr
/*  624 */                           .getName(), attr
/*  625 */                           .getNodeValue()); }  }  } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         case 5:
/*      */         case 9:
/*  631 */           node1 = paramNode;
/*  632 */           node2 = paramNode.getFirstChild();
/*      */           break;
/*      */       } 
/*  635 */       while (node2 == null && node1 != null) {
/*  636 */         node2 = node1.getNextSibling();
/*  637 */         node1 = node1.getParentNode();
/*      */       } 
/*  639 */       if (node2 == null) {
/*      */         return;
/*      */       }
/*      */       
/*  643 */       paramNode = node2;
/*  644 */       node2 = paramNode.getNextSibling();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Element selectDsNode(Node paramNode, String paramString, int paramInt) {
/*  655 */     while (paramNode != null) {
/*  656 */       if ("http://www.w3.org/2000/09/xmldsig#".equals(paramNode.getNamespaceURI()) && paramNode
/*  657 */         .getLocalName().equals(paramString)) {
/*  658 */         if (paramInt == 0) {
/*  659 */           return (Element)paramNode;
/*      */         }
/*  661 */         paramInt--;
/*      */       } 
/*  663 */       paramNode = paramNode.getNextSibling();
/*      */     } 
/*  665 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Element selectDs11Node(Node paramNode, String paramString, int paramInt) {
/*  675 */     while (paramNode != null) {
/*  676 */       if ("http://www.w3.org/2009/xmldsig11#".equals(paramNode.getNamespaceURI()) && paramNode
/*  677 */         .getLocalName().equals(paramString)) {
/*  678 */         if (paramInt == 0) {
/*  679 */           return (Element)paramNode;
/*      */         }
/*  681 */         paramInt--;
/*      */       } 
/*  683 */       paramNode = paramNode.getNextSibling();
/*      */     } 
/*  685 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Element selectXencNode(Node paramNode, String paramString, int paramInt) {
/*  695 */     while (paramNode != null) {
/*  696 */       if ("http://www.w3.org/2001/04/xmlenc#".equals(paramNode.getNamespaceURI()) && paramNode
/*  697 */         .getLocalName().equals(paramString)) {
/*  698 */         if (paramInt == 0) {
/*  699 */           return (Element)paramNode;
/*      */         }
/*  701 */         paramInt--;
/*      */       } 
/*  703 */       paramNode = paramNode.getNextSibling();
/*      */     } 
/*  705 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Text selectDsNodeText(Node paramNode, String paramString, int paramInt) {
/*  716 */     Element element = selectDsNode(paramNode, paramString, paramInt);
/*  717 */     if (element == null) {
/*  718 */       return null;
/*      */     }
/*  720 */     Node node = element.getFirstChild();
/*  721 */     while (node != null && node.getNodeType() != 3) {
/*  722 */       node = node.getNextSibling();
/*      */     }
/*  724 */     return (Text)node;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Text selectDs11NodeText(Node paramNode, String paramString, int paramInt) {
/*  734 */     Element element = selectDs11Node(paramNode, paramString, paramInt);
/*  735 */     if (element == null) {
/*  736 */       return null;
/*      */     }
/*  738 */     Node node = element.getFirstChild();
/*  739 */     while (node != null && node.getNodeType() != 3) {
/*  740 */       node = node.getNextSibling();
/*      */     }
/*  742 */     return (Text)node;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Text selectNodeText(Node paramNode, String paramString1, String paramString2, int paramInt) {
/*  753 */     Element element = selectNode(paramNode, paramString1, paramString2, paramInt);
/*  754 */     if (element == null) {
/*  755 */       return null;
/*      */     }
/*  757 */     Node node = element.getFirstChild();
/*  758 */     while (node != null && node.getNodeType() != 3) {
/*  759 */       node = node.getNextSibling();
/*      */     }
/*  761 */     return (Text)node;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Element selectNode(Node paramNode, String paramString1, String paramString2, int paramInt) {
/*  772 */     while (paramNode != null) {
/*  773 */       if (paramNode.getNamespaceURI() != null && paramNode.getNamespaceURI().equals(paramString1) && paramNode
/*  774 */         .getLocalName().equals(paramString2)) {
/*  775 */         if (paramInt == 0) {
/*  776 */           return (Element)paramNode;
/*      */         }
/*  778 */         paramInt--;
/*      */       } 
/*  780 */       paramNode = paramNode.getNextSibling();
/*      */     } 
/*  782 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Element[] selectDsNodes(Node paramNode, String paramString) {
/*  791 */     return selectNodes(paramNode, "http://www.w3.org/2000/09/xmldsig#", paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Element[] selectDs11Nodes(Node paramNode, String paramString) {
/*  800 */     return selectNodes(paramNode, "http://www.w3.org/2009/xmldsig11#", paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Element[] selectNodes(Node paramNode, String paramString1, String paramString2) {
/*  810 */     ArrayList<Element> arrayList = new ArrayList();
/*  811 */     while (paramNode != null) {
/*  812 */       if (paramNode.getNamespaceURI() != null && paramNode.getNamespaceURI().equals(paramString1) && paramNode
/*  813 */         .getLocalName().equals(paramString2)) {
/*  814 */         arrayList.add((Element)paramNode);
/*      */       }
/*  816 */       paramNode = paramNode.getNextSibling();
/*      */     } 
/*  818 */     return arrayList.<Element>toArray(new Element[arrayList.size()]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Set<Node> excludeNodeFromSet(Node paramNode, Set<Node> paramSet) {
/*  827 */     HashSet<Node> hashSet = new HashSet();
/*  828 */     Iterator<Node> iterator = paramSet.iterator();
/*      */     
/*  830 */     while (iterator.hasNext()) {
/*  831 */       Node node = iterator.next();
/*      */       
/*  833 */       if (!isDescendantOrSelf(paramNode, node)) {
/*  834 */         hashSet.add(node);
/*      */       }
/*      */     } 
/*  837 */     return hashSet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getStrFromNode(Node paramNode) {
/*  847 */     if (paramNode.getNodeType() == 3) {
/*      */ 
/*      */       
/*  850 */       StringBuilder stringBuilder = new StringBuilder();
/*      */       
/*  852 */       Node node = paramNode.getParentNode().getFirstChild();
/*  853 */       for (; node != null; 
/*  854 */         node = node.getNextSibling()) {
/*  855 */         if (node.getNodeType() == 3) {
/*  856 */           stringBuilder.append(((Text)node).getData());
/*      */         }
/*      */       } 
/*      */       
/*  860 */       return stringBuilder.toString();
/*  861 */     }  if (paramNode.getNodeType() == 2)
/*  862 */       return ((Attr)paramNode).getNodeValue(); 
/*  863 */     if (paramNode.getNodeType() == 7) {
/*  864 */       return ((ProcessingInstruction)paramNode).getNodeValue();
/*      */     }
/*      */     
/*  867 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isDescendantOrSelf(Node paramNode1, Node paramNode2) {
/*  879 */     if (paramNode1 == paramNode2) {
/*  880 */       return true;
/*      */     }
/*      */     
/*  883 */     Node node = paramNode2;
/*      */     
/*      */     while (true) {
/*  886 */       if (node == null) {
/*  887 */         return false;
/*      */       }
/*      */       
/*  890 */       if (node == paramNode1) {
/*  891 */         return true;
/*      */       }
/*      */       
/*  894 */       if (node.getNodeType() == 2) {
/*  895 */         node = ((Attr)node).getOwnerElement(); continue;
/*      */       } 
/*  897 */       node = node.getParentNode();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean ignoreLineBreaks() {
/*  903 */     return ignoreLineBreaks;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getAttributeValue(Element paramElement, String paramString) {
/*  921 */     Attr attr = paramElement.getAttributeNodeNS((String)null, paramString);
/*  922 */     return (attr == null) ? null : attr.getValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean protectAgainstWrappingAttack(Node paramNode, String paramString) {
/*  932 */     Node node1 = paramNode.getParentNode();
/*  933 */     Node node2 = null;
/*  934 */     Element element = null;
/*      */     
/*  936 */     String str = paramString.trim();
/*  937 */     if (!str.isEmpty() && str.charAt(0) == '#') {
/*  938 */       str = str.substring(1);
/*      */     }
/*      */     
/*  941 */     while (paramNode != null) {
/*  942 */       if (paramNode.getNodeType() == 1) {
/*  943 */         Element element1 = (Element)paramNode;
/*      */         
/*  945 */         NamedNodeMap namedNodeMap = element1.getAttributes();
/*  946 */         if (namedNodeMap != null) {
/*  947 */           for (byte b = 0; b < namedNodeMap.getLength(); b++) {
/*  948 */             Attr attr = (Attr)namedNodeMap.item(b);
/*  949 */             if (attr.isId() && str.equals(attr.getValue())) {
/*  950 */               if (element == null) {
/*      */                 
/*  952 */                 element = attr.getOwnerElement();
/*      */               } else {
/*  954 */                 log.log(Level.FINE, "Multiple elements with the same 'Id' attribute value!");
/*  955 */                 return false;
/*      */               } 
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*      */       
/*  962 */       node2 = paramNode;
/*  963 */       paramNode = paramNode.getFirstChild();
/*      */ 
/*      */       
/*  966 */       if (paramNode == null)
/*      */       {
/*  968 */         paramNode = node2.getNextSibling();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  973 */       while (paramNode == null) {
/*  974 */         node2 = node2.getParentNode();
/*  975 */         if (node2 == node1) {
/*  976 */           return true;
/*      */         }
/*      */         
/*  979 */         paramNode = node2.getNextSibling();
/*      */       } 
/*      */     } 
/*  982 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean protectAgainstWrappingAttack(Node paramNode, Element paramElement, String paramString) {
/*  993 */     Node node1 = paramNode.getParentNode();
/*  994 */     Node node2 = null;
/*      */     
/*  996 */     String str = paramString.trim();
/*  997 */     if (!str.isEmpty() && str.charAt(0) == '#') {
/*  998 */       str = str.substring(1);
/*      */     }
/*      */     
/* 1001 */     while (paramNode != null) {
/* 1002 */       if (paramNode.getNodeType() == 1) {
/* 1003 */         Element element = (Element)paramNode;
/*      */         
/* 1005 */         NamedNodeMap namedNodeMap = element.getAttributes();
/* 1006 */         if (namedNodeMap != null) {
/* 1007 */           for (byte b = 0; b < namedNodeMap.getLength(); b++) {
/* 1008 */             Attr attr = (Attr)namedNodeMap.item(b);
/* 1009 */             if (attr.isId() && str.equals(attr.getValue()) && element != paramElement) {
/* 1010 */               log.log(Level.FINE, "Multiple elements with the same 'Id' attribute value!");
/* 1011 */               return false;
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */       
/* 1017 */       node2 = paramNode;
/* 1018 */       paramNode = paramNode.getFirstChild();
/*      */ 
/*      */       
/* 1021 */       if (paramNode == null)
/*      */       {
/* 1023 */         paramNode = node2.getNextSibling();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1028 */       while (paramNode == null) {
/* 1029 */         node2 = node2.getParentNode();
/* 1030 */         if (node2 == node1) {
/* 1031 */           return true;
/*      */         }
/*      */         
/* 1034 */         paramNode = node2.getNextSibling();
/*      */       } 
/*      */     } 
/* 1037 */     return true;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/utils/XMLUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */