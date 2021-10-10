/*     */ package com.sun.org.apache.xerces.internal.util;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.dom.AttrImpl;
/*     */ import com.sun.org.apache.xerces.internal.dom.NodeImpl;
/*     */ import com.sun.org.apache.xerces.internal.impl.xs.opti.ElementImpl;
/*     */ import com.sun.org.apache.xerces.internal.impl.xs.opti.NodeImpl;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Map;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.ls.LSException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DOMUtil
/*     */ {
/*     */   public static void copyInto(Node src, Node dest) throws DOMException {
/*  69 */     Document factory = dest.getOwnerDocument();
/*  70 */     boolean domimpl = factory instanceof com.sun.org.apache.xerces.internal.dom.DocumentImpl;
/*     */ 
/*     */     
/*  73 */     Node start = src;
/*  74 */     Node parent = src;
/*  75 */     Node place = src;
/*     */ 
/*     */     
/*  78 */     while (place != null) {
/*     */       Element element; NamedNodeMap attrs;
/*     */       int attrCount, i;
/*  81 */       Node node = null;
/*  82 */       int type = place.getNodeType();
/*  83 */       switch (type) {
/*     */         case 4:
/*  85 */           node = factory.createCDATASection(place.getNodeValue());
/*     */           break;
/*     */         
/*     */         case 8:
/*  89 */           node = factory.createComment(place.getNodeValue());
/*     */           break;
/*     */         
/*     */         case 1:
/*  93 */           element = factory.createElement(place.getNodeName());
/*  94 */           node = element;
/*  95 */           attrs = place.getAttributes();
/*  96 */           attrCount = attrs.getLength();
/*  97 */           for (i = 0; i < attrCount; i++) {
/*  98 */             Attr attr = (Attr)attrs.item(i);
/*  99 */             String attrName = attr.getNodeName();
/* 100 */             String attrValue = attr.getNodeValue();
/* 101 */             element.setAttribute(attrName, attrValue);
/* 102 */             if (domimpl && !attr.getSpecified()) {
/* 103 */               ((AttrImpl)element.getAttributeNode(attrName)).setSpecified(false);
/*     */             }
/*     */           } 
/*     */           break;
/*     */         
/*     */         case 5:
/* 109 */           node = factory.createEntityReference(place.getNodeName());
/*     */           break;
/*     */         
/*     */         case 7:
/* 113 */           node = factory.createProcessingInstruction(place.getNodeName(), place
/* 114 */               .getNodeValue());
/*     */           break;
/*     */         
/*     */         case 3:
/* 118 */           node = factory.createTextNode(place.getNodeValue());
/*     */           break;
/*     */         
/*     */         default:
/* 122 */           throw new IllegalArgumentException("can't copy node type, " + type + " (" + place
/*     */               
/* 124 */               .getNodeName() + ')');
/*     */       } 
/*     */       
/* 127 */       dest.appendChild(node);
/*     */ 
/*     */       
/* 130 */       if (place.hasChildNodes()) {
/* 131 */         parent = place;
/* 132 */         place = place.getFirstChild();
/* 133 */         dest = node;
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 138 */       place = place.getNextSibling();
/* 139 */       while (place == null && parent != start) {
/* 140 */         place = parent.getNextSibling();
/* 141 */         parent = parent.getParentNode();
/* 142 */         dest = dest.getParentNode();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element getFirstChildElement(Node parent) {
/* 154 */     Node child = parent.getFirstChild();
/* 155 */     while (child != null) {
/* 156 */       if (child.getNodeType() == 1) {
/* 157 */         return (Element)child;
/*     */       }
/* 159 */       child = child.getNextSibling();
/*     */     } 
/*     */ 
/*     */     
/* 163 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element getFirstVisibleChildElement(Node parent) {
/* 171 */     Node child = parent.getFirstChild();
/* 172 */     while (child != null) {
/* 173 */       if (child.getNodeType() == 1 && 
/* 174 */         !isHidden(child)) {
/* 175 */         return (Element)child;
/*     */       }
/* 177 */       child = child.getNextSibling();
/*     */     } 
/*     */ 
/*     */     
/* 181 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element getFirstVisibleChildElement(Node parent, Map<Node, String> hiddenNodes) {
/* 189 */     Node child = parent.getFirstChild();
/* 190 */     while (child != null) {
/* 191 */       if (child.getNodeType() == 1 && 
/* 192 */         !isHidden(child, hiddenNodes)) {
/* 193 */         return (Element)child;
/*     */       }
/* 195 */       child = child.getNextSibling();
/*     */     } 
/*     */ 
/*     */     
/* 199 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element getLastChildElement(Node parent) {
/* 209 */     Node child = parent.getLastChild();
/* 210 */     while (child != null) {
/* 211 */       if (child.getNodeType() == 1) {
/* 212 */         return (Element)child;
/*     */       }
/* 214 */       child = child.getPreviousSibling();
/*     */     } 
/*     */ 
/*     */     
/* 218 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element getLastVisibleChildElement(Node parent) {
/* 226 */     Node child = parent.getLastChild();
/* 227 */     while (child != null) {
/* 228 */       if (child.getNodeType() == 1 && 
/* 229 */         !isHidden(child)) {
/* 230 */         return (Element)child;
/*     */       }
/* 232 */       child = child.getPreviousSibling();
/*     */     } 
/*     */ 
/*     */     
/* 236 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element getLastVisibleChildElement(Node parent, Map<Node, String> hiddenNodes) {
/* 246 */     Node child = parent.getLastChild();
/* 247 */     while (child != null) {
/* 248 */       if (child.getNodeType() == 1 && 
/* 249 */         !isHidden(child, hiddenNodes)) {
/* 250 */         return (Element)child;
/*     */       }
/* 252 */       child = child.getPreviousSibling();
/*     */     } 
/*     */ 
/*     */     
/* 256 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element getNextSiblingElement(Node node) {
/* 263 */     Node sibling = node.getNextSibling();
/* 264 */     while (sibling != null) {
/* 265 */       if (sibling.getNodeType() == 1) {
/* 266 */         return (Element)sibling;
/*     */       }
/* 268 */       sibling = sibling.getNextSibling();
/*     */     } 
/*     */ 
/*     */     
/* 272 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element getNextVisibleSiblingElement(Node node) {
/* 280 */     Node sibling = node.getNextSibling();
/* 281 */     while (sibling != null) {
/* 282 */       if (sibling.getNodeType() == 1 && 
/* 283 */         !isHidden(sibling)) {
/* 284 */         return (Element)sibling;
/*     */       }
/* 286 */       sibling = sibling.getNextSibling();
/*     */     } 
/*     */ 
/*     */     
/* 290 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element getNextVisibleSiblingElement(Node node, Map<Node, String> hiddenNodes) {
/* 298 */     Node sibling = node.getNextSibling();
/* 299 */     while (sibling != null) {
/* 300 */       if (sibling.getNodeType() == 1 && 
/* 301 */         !isHidden(sibling, hiddenNodes)) {
/* 302 */         return (Element)sibling;
/*     */       }
/* 304 */       sibling = sibling.getNextSibling();
/*     */     } 
/*     */ 
/*     */     
/* 308 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setHidden(Node node) {
/* 314 */     if (node instanceof NodeImpl) {
/* 315 */       ((NodeImpl)node).setReadOnly(true, false);
/* 316 */     } else if (node instanceof NodeImpl) {
/* 317 */       ((NodeImpl)node).setReadOnly(true, false);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void setHidden(Node node, Map<Node, String> hiddenNodes) {
/* 322 */     if (node instanceof NodeImpl) {
/* 323 */       ((NodeImpl)node).setReadOnly(true, false);
/*     */     } else {
/*     */       
/* 326 */       hiddenNodes.put(node, "");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setVisible(Node node) {
/* 332 */     if (node instanceof NodeImpl) {
/* 333 */       ((NodeImpl)node).setReadOnly(false, false);
/* 334 */     } else if (node instanceof NodeImpl) {
/* 335 */       ((NodeImpl)node).setReadOnly(false, false);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void setVisible(Node node, Map<Node, String> hiddenNodes) {
/* 340 */     if (node instanceof NodeImpl) {
/* 341 */       ((NodeImpl)node).setReadOnly(false, false);
/*     */     } else {
/*     */       
/* 344 */       hiddenNodes.remove(node);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isHidden(Node node) {
/* 350 */     if (node instanceof NodeImpl)
/* 351 */       return ((NodeImpl)node).getReadOnly(); 
/* 352 */     if (node instanceof NodeImpl)
/* 353 */       return ((NodeImpl)node).getReadOnly(); 
/* 354 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isHidden(Node node, Map<Node, String> hiddenNodes) {
/* 359 */     if (node instanceof NodeImpl) {
/* 360 */       return ((NodeImpl)node).getReadOnly();
/*     */     }
/*     */     
/* 363 */     return hiddenNodes.containsKey(node);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element getFirstChildElement(Node parent, String elemName) {
/* 371 */     Node child = parent.getFirstChild();
/* 372 */     while (child != null) {
/* 373 */       if (child.getNodeType() == 1 && 
/* 374 */         child.getNodeName().equals(elemName)) {
/* 375 */         return (Element)child;
/*     */       }
/*     */       
/* 378 */       child = child.getNextSibling();
/*     */     } 
/*     */ 
/*     */     
/* 382 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element getLastChildElement(Node parent, String elemName) {
/* 390 */     Node child = parent.getLastChild();
/* 391 */     while (child != null) {
/* 392 */       if (child.getNodeType() == 1 && 
/* 393 */         child.getNodeName().equals(elemName)) {
/* 394 */         return (Element)child;
/*     */       }
/*     */       
/* 397 */       child = child.getPreviousSibling();
/*     */     } 
/*     */ 
/*     */     
/* 401 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element getNextSiblingElement(Node node, String elemName) {
/* 409 */     Node sibling = node.getNextSibling();
/* 410 */     while (sibling != null) {
/* 411 */       if (sibling.getNodeType() == 1 && 
/* 412 */         sibling.getNodeName().equals(elemName)) {
/* 413 */         return (Element)sibling;
/*     */       }
/*     */       
/* 416 */       sibling = sibling.getNextSibling();
/*     */     } 
/*     */ 
/*     */     
/* 420 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element getFirstChildElementNS(Node parent, String uri, String localpart) {
/* 429 */     Node child = parent.getFirstChild();
/* 430 */     while (child != null) {
/* 431 */       if (child.getNodeType() == 1) {
/* 432 */         String childURI = child.getNamespaceURI();
/* 433 */         if (childURI != null && childURI.equals(uri) && child
/* 434 */           .getLocalName().equals(localpart)) {
/* 435 */           return (Element)child;
/*     */         }
/*     */       } 
/* 438 */       child = child.getNextSibling();
/*     */     } 
/*     */ 
/*     */     
/* 442 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element getLastChildElementNS(Node parent, String uri, String localpart) {
/* 451 */     Node child = parent.getLastChild();
/* 452 */     while (child != null) {
/* 453 */       if (child.getNodeType() == 1) {
/* 454 */         String childURI = child.getNamespaceURI();
/* 455 */         if (childURI != null && childURI.equals(uri) && child
/* 456 */           .getLocalName().equals(localpart)) {
/* 457 */           return (Element)child;
/*     */         }
/*     */       } 
/* 460 */       child = child.getPreviousSibling();
/*     */     } 
/*     */ 
/*     */     
/* 464 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element getNextSiblingElementNS(Node node, String uri, String localpart) {
/* 473 */     Node sibling = node.getNextSibling();
/* 474 */     while (sibling != null) {
/* 475 */       if (sibling.getNodeType() == 1) {
/* 476 */         String siblingURI = sibling.getNamespaceURI();
/* 477 */         if (siblingURI != null && siblingURI.equals(uri) && sibling
/* 478 */           .getLocalName().equals(localpart)) {
/* 479 */           return (Element)sibling;
/*     */         }
/*     */       } 
/* 482 */       sibling = sibling.getNextSibling();
/*     */     } 
/*     */ 
/*     */     
/* 486 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element getFirstChildElement(Node parent, String[] elemNames) {
/* 494 */     Node child = parent.getFirstChild();
/* 495 */     while (child != null) {
/* 496 */       if (child.getNodeType() == 1) {
/* 497 */         for (int i = 0; i < elemNames.length; i++) {
/* 498 */           if (child.getNodeName().equals(elemNames[i])) {
/* 499 */             return (Element)child;
/*     */           }
/*     */         } 
/*     */       }
/* 503 */       child = child.getNextSibling();
/*     */     } 
/*     */ 
/*     */     
/* 507 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element getLastChildElement(Node parent, String[] elemNames) {
/* 515 */     Node child = parent.getLastChild();
/* 516 */     while (child != null) {
/* 517 */       if (child.getNodeType() == 1) {
/* 518 */         for (int i = 0; i < elemNames.length; i++) {
/* 519 */           if (child.getNodeName().equals(elemNames[i])) {
/* 520 */             return (Element)child;
/*     */           }
/*     */         } 
/*     */       }
/* 524 */       child = child.getPreviousSibling();
/*     */     } 
/*     */ 
/*     */     
/* 528 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element getNextSiblingElement(Node node, String[] elemNames) {
/* 536 */     Node sibling = node.getNextSibling();
/* 537 */     while (sibling != null) {
/* 538 */       if (sibling.getNodeType() == 1) {
/* 539 */         for (int i = 0; i < elemNames.length; i++) {
/* 540 */           if (sibling.getNodeName().equals(elemNames[i])) {
/* 541 */             return (Element)sibling;
/*     */           }
/*     */         } 
/*     */       }
/* 545 */       sibling = sibling.getNextSibling();
/*     */     } 
/*     */ 
/*     */     
/* 549 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element getFirstChildElementNS(Node parent, String[][] elemNames) {
/* 558 */     Node child = parent.getFirstChild();
/* 559 */     while (child != null) {
/* 560 */       if (child.getNodeType() == 1) {
/* 561 */         for (int i = 0; i < elemNames.length; i++) {
/* 562 */           String uri = child.getNamespaceURI();
/* 563 */           if (uri != null && uri.equals(elemNames[i][0]) && child
/* 564 */             .getLocalName().equals(elemNames[i][1])) {
/* 565 */             return (Element)child;
/*     */           }
/*     */         } 
/*     */       }
/* 569 */       child = child.getNextSibling();
/*     */     } 
/*     */ 
/*     */     
/* 573 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element getLastChildElementNS(Node parent, String[][] elemNames) {
/* 582 */     Node child = parent.getLastChild();
/* 583 */     while (child != null) {
/* 584 */       if (child.getNodeType() == 1) {
/* 585 */         for (int i = 0; i < elemNames.length; i++) {
/* 586 */           String uri = child.getNamespaceURI();
/* 587 */           if (uri != null && uri.equals(elemNames[i][0]) && child
/* 588 */             .getLocalName().equals(elemNames[i][1])) {
/* 589 */             return (Element)child;
/*     */           }
/*     */         } 
/*     */       }
/* 593 */       child = child.getPreviousSibling();
/*     */     } 
/*     */ 
/*     */     
/* 597 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element getNextSiblingElementNS(Node node, String[][] elemNames) {
/* 606 */     Node sibling = node.getNextSibling();
/* 607 */     while (sibling != null) {
/* 608 */       if (sibling.getNodeType() == 1) {
/* 609 */         for (int i = 0; i < elemNames.length; i++) {
/* 610 */           String uri = sibling.getNamespaceURI();
/* 611 */           if (uri != null && uri.equals(elemNames[i][0]) && sibling
/* 612 */             .getLocalName().equals(elemNames[i][1])) {
/* 613 */             return (Element)sibling;
/*     */           }
/*     */         } 
/*     */       }
/* 617 */       sibling = sibling.getNextSibling();
/*     */     } 
/*     */ 
/*     */     
/* 621 */     return null;
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
/*     */   public static Element getFirstChildElement(Node parent, String elemName, String attrName, String attrValue) {
/* 635 */     Node child = parent.getFirstChild();
/* 636 */     while (child != null) {
/* 637 */       if (child.getNodeType() == 1) {
/* 638 */         Element element = (Element)child;
/* 639 */         if (element.getNodeName().equals(elemName) && element
/* 640 */           .getAttribute(attrName).equals(attrValue)) {
/* 641 */           return element;
/*     */         }
/*     */       } 
/* 644 */       child = child.getNextSibling();
/*     */     } 
/*     */ 
/*     */     
/* 648 */     return null;
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
/*     */   public static Element getLastChildElement(Node parent, String elemName, String attrName, String attrValue) {
/* 662 */     Node child = parent.getLastChild();
/* 663 */     while (child != null) {
/* 664 */       if (child.getNodeType() == 1) {
/* 665 */         Element element = (Element)child;
/* 666 */         if (element.getNodeName().equals(elemName) && element
/* 667 */           .getAttribute(attrName).equals(attrValue)) {
/* 668 */           return element;
/*     */         }
/*     */       } 
/* 671 */       child = child.getPreviousSibling();
/*     */     } 
/*     */ 
/*     */     
/* 675 */     return null;
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
/*     */   public static Element getNextSiblingElement(Node node, String elemName, String attrName, String attrValue) {
/* 690 */     Node sibling = node.getNextSibling();
/* 691 */     while (sibling != null) {
/* 692 */       if (sibling.getNodeType() == 1) {
/* 693 */         Element element = (Element)sibling;
/* 694 */         if (element.getNodeName().equals(elemName) && element
/* 695 */           .getAttribute(attrName).equals(attrValue)) {
/* 696 */           return element;
/*     */         }
/*     */       } 
/* 699 */       sibling = sibling.getNextSibling();
/*     */     } 
/*     */ 
/*     */     
/* 703 */     return null;
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
/*     */   public static String getChildText(Node node) {
/* 719 */     if (node == null) {
/* 720 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 724 */     StringBuffer str = new StringBuffer();
/* 725 */     Node child = node.getFirstChild();
/* 726 */     while (child != null) {
/* 727 */       short type = child.getNodeType();
/* 728 */       if (type == 3) {
/* 729 */         str.append(child.getNodeValue());
/*     */       }
/* 731 */       else if (type == 4) {
/* 732 */         str.append(getChildText(child));
/*     */       } 
/* 734 */       child = child.getNextSibling();
/*     */     } 
/*     */ 
/*     */     
/* 738 */     return str.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getName(Node node) {
/* 744 */     return node.getNodeName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getLocalName(Node node) {
/* 751 */     String name = node.getLocalName();
/* 752 */     return (name != null) ? name : node.getNodeName();
/*     */   }
/*     */   
/*     */   public static Element getParent(Element elem) {
/* 756 */     Node parent = elem.getParentNode();
/* 757 */     if (parent instanceof Element)
/* 758 */       return (Element)parent; 
/* 759 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Document getDocument(Node node) {
/* 764 */     return node.getOwnerDocument();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Element getRoot(Document doc) {
/* 769 */     return doc.getDocumentElement();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Attr getAttr(Element elem, String name) {
/* 776 */     return elem.getAttributeNode(name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Attr getAttrNS(Element elem, String nsUri, String localName) {
/* 782 */     return elem.getAttributeNodeNS(nsUri, localName);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Attr[] getAttrs(Element elem) {
/* 787 */     NamedNodeMap attrMap = elem.getAttributes();
/* 788 */     Attr[] attrArray = new Attr[attrMap.getLength()];
/* 789 */     for (int i = 0; i < attrMap.getLength(); i++)
/* 790 */       attrArray[i] = (Attr)attrMap.item(i); 
/* 791 */     return attrArray;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getValue(Attr attribute) {
/* 796 */     return attribute.getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getAttrValue(Element elem, String name) {
/* 807 */     return elem.getAttribute(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getAttrValueNS(Element elem, String nsUri, String localName) {
/* 814 */     return elem.getAttributeNS(nsUri, localName);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getPrefix(Node node) {
/* 819 */     return node.getPrefix();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getNamespaceURI(Node node) {
/* 824 */     return node.getNamespaceURI();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getAnnotation(Node node) {
/* 829 */     if (node instanceof ElementImpl) {
/* 830 */       return ((ElementImpl)node).getAnnotation();
/*     */     }
/* 832 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getSyntheticAnnotation(Node node) {
/* 837 */     if (node instanceof ElementImpl) {
/* 838 */       return ((ElementImpl)node).getSyntheticAnnotation();
/*     */     }
/* 840 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DOMException createDOMException(short code, Throwable cause) {
/* 847 */     DOMException de = new DOMException(code, (cause != null) ? cause.getMessage() : null);
/* 848 */     if (cause != null && ThrowableMethods.fgThrowableMethodsAvailable) {
/*     */       try {
/* 850 */         ThrowableMethods.fgThrowableInitCauseMethod.invoke(de, new Object[] { cause });
/*     */       
/*     */       }
/* 853 */       catch (Exception exception) {}
/*     */     }
/* 855 */     return de;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LSException createLSException(short code, Throwable cause) {
/* 862 */     LSException lse = new LSException(code, (cause != null) ? cause.getMessage() : null);
/* 863 */     if (cause != null && ThrowableMethods.fgThrowableMethodsAvailable) {
/*     */       try {
/* 865 */         ThrowableMethods.fgThrowableInitCauseMethod.invoke(lse, new Object[] { cause });
/*     */       
/*     */       }
/* 868 */       catch (Exception exception) {}
/*     */     }
/* 870 */     return lse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class ThrowableMethods
/*     */   {
/* 879 */     private static Method fgThrowableInitCauseMethod = null;
/*     */ 
/*     */ 
/*     */     
/*     */     private static boolean fgThrowableMethodsAvailable = false;
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*     */       try {
/* 889 */         fgThrowableInitCauseMethod = Throwable.class.getMethod("initCause", new Class[] { Throwable.class });
/* 890 */         fgThrowableMethodsAvailable = true;
/*     */ 
/*     */       
/*     */       }
/* 894 */       catch (Exception exc) {
/* 895 */         fgThrowableInitCauseMethod = null;
/* 896 */         fgThrowableMethodsAvailable = false;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/util/DOMUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */