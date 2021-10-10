/*     */ package com.sun.org.apache.xml.internal.security.c14n.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
/*     */ import com.sun.org.apache.xml.internal.security.c14n.CanonicalizerSpi;
/*     */ import com.sun.org.apache.xml.internal.security.c14n.helper.AttrCompare;
/*     */ import com.sun.org.apache.xml.internal.security.signature.NodeFilter;
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*     */ import com.sun.org.apache.xml.internal.security.utils.UnsyncByteArrayOutputStream;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Comment;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.ProcessingInstruction;
/*     */ import org.xml.sax.SAXException;
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
/*     */ public abstract class CanonicalizerBase
/*     */   extends CanonicalizerSpi
/*     */ {
/*     */   public static final String XML = "xml";
/*     */   public static final String XMLNS = "xmlns";
/*  65 */   protected static final AttrCompare COMPARE = new AttrCompare();
/*     */ 
/*     */ 
/*     */   
/*  69 */   private static final byte[] END_PI = new byte[] { 63, 62 };
/*  70 */   private static final byte[] BEGIN_PI = new byte[] { 60, 63 };
/*  71 */   private static final byte[] END_COMM = new byte[] { 45, 45, 62 };
/*  72 */   private static final byte[] BEGIN_COMM = new byte[] { 60, 33, 45, 45 };
/*  73 */   private static final byte[] XA = new byte[] { 38, 35, 120, 65, 59 };
/*  74 */   private static final byte[] X9 = new byte[] { 38, 35, 120, 57, 59 };
/*  75 */   private static final byte[] QUOT = new byte[] { 38, 113, 117, 111, 116, 59 };
/*  76 */   private static final byte[] XD = new byte[] { 38, 35, 120, 68, 59 };
/*  77 */   private static final byte[] GT = new byte[] { 38, 103, 116, 59 };
/*  78 */   private static final byte[] LT = new byte[] { 38, 108, 116, 59 };
/*  79 */   private static final byte[] END_TAG = new byte[] { 60, 47 };
/*  80 */   private static final byte[] AMP = new byte[] { 38, 97, 109, 112, 59 };
/*  81 */   private static final byte[] EQUALS_STR = new byte[] { 61, 34 };
/*     */   
/*     */   protected static final int NODE_BEFORE_DOCUMENT_ELEMENT = -1;
/*     */   
/*     */   protected static final int NODE_NOT_BEFORE_OR_AFTER_DOCUMENT_ELEMENT = 0;
/*     */   
/*     */   protected static final int NODE_AFTER_DOCUMENT_ELEMENT = 1;
/*     */   
/*     */   private List<NodeFilter> nodeFilter;
/*     */   
/*     */   private boolean includeComments;
/*     */   
/*     */   private Set<Node> xpathNodeSet;
/*     */   
/*     */   private Node excludeNode;
/*     */   
/*  97 */   private OutputStream writer = new ByteArrayOutputStream();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Attr nullNode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CanonicalizerBase(boolean paramBoolean) {
/* 110 */     this.includeComments = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] engineCanonicalizeSubTree(Node paramNode) throws CanonicalizationException {
/* 121 */     return engineCanonicalizeSubTree(paramNode, (Node)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] engineCanonicalizeXPathNodeSet(Set<Node> paramSet) throws CanonicalizationException {
/* 132 */     this.xpathNodeSet = paramSet;
/* 133 */     return engineCanonicalizeXPathNodeSetInternal(XMLUtils.getOwnerDocument(this.xpathNodeSet));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] engineCanonicalize(XMLSignatureInput paramXMLSignatureInput) throws CanonicalizationException {
/*     */     try {
/* 144 */       if (paramXMLSignatureInput.isExcludeComments()) {
/* 145 */         this.includeComments = false;
/*     */       }
/* 147 */       if (paramXMLSignatureInput.isOctetStream()) {
/* 148 */         return engineCanonicalize(paramXMLSignatureInput.getBytes());
/*     */       }
/* 150 */       if (paramXMLSignatureInput.isElement())
/* 151 */         return engineCanonicalizeSubTree(paramXMLSignatureInput.getSubNode(), paramXMLSignatureInput.getExcludeNode()); 
/* 152 */       if (paramXMLSignatureInput.isNodeSet()) {
/* 153 */         this.nodeFilter = paramXMLSignatureInput.getNodeFilters();
/*     */         
/* 155 */         circumventBugIfNeeded(paramXMLSignatureInput);
/*     */         
/* 157 */         if (paramXMLSignatureInput.getSubNode() != null) {
/* 158 */           return engineCanonicalizeXPathNodeSetInternal(paramXMLSignatureInput.getSubNode());
/*     */         }
/* 160 */         return engineCanonicalizeXPathNodeSet(paramXMLSignatureInput.getNodeSet());
/*     */       } 
/*     */       
/* 163 */       return null;
/* 164 */     } catch (CanonicalizationException canonicalizationException) {
/* 165 */       throw new CanonicalizationException("empty", canonicalizationException);
/* 166 */     } catch (ParserConfigurationException parserConfigurationException) {
/* 167 */       throw new CanonicalizationException("empty", parserConfigurationException);
/* 168 */     } catch (IOException iOException) {
/* 169 */       throw new CanonicalizationException("empty", iOException);
/* 170 */     } catch (SAXException sAXException) {
/* 171 */       throw new CanonicalizationException("empty", sAXException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWriter(OutputStream paramOutputStream) {
/* 179 */     this.writer = paramOutputStream;
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
/*     */   protected byte[] engineCanonicalizeSubTree(Node paramNode1, Node paramNode2) throws CanonicalizationException {
/* 194 */     this.excludeNode = paramNode2;
/*     */     try {
/* 196 */       NameSpaceSymbTable nameSpaceSymbTable = new NameSpaceSymbTable();
/* 197 */       byte b = -1;
/* 198 */       if (paramNode1 != null && 1 == paramNode1.getNodeType()) {
/*     */         
/* 200 */         getParentNameSpaces((Element)paramNode1, nameSpaceSymbTable);
/* 201 */         b = 0;
/*     */       } 
/* 203 */       canonicalizeSubTree(paramNode1, nameSpaceSymbTable, paramNode1, b);
/* 204 */       this.writer.flush();
/* 205 */       if (this.writer instanceof ByteArrayOutputStream) {
/* 206 */         byte[] arrayOfByte = ((ByteArrayOutputStream)this.writer).toByteArray();
/* 207 */         if (this.reset) {
/* 208 */           ((ByteArrayOutputStream)this.writer).reset();
/*     */         } else {
/* 210 */           this.writer.close();
/*     */         } 
/* 212 */         return arrayOfByte;
/* 213 */       }  if (this.writer instanceof UnsyncByteArrayOutputStream) {
/* 214 */         byte[] arrayOfByte = ((UnsyncByteArrayOutputStream)this.writer).toByteArray();
/* 215 */         if (this.reset) {
/* 216 */           ((UnsyncByteArrayOutputStream)this.writer).reset();
/*     */         } else {
/* 218 */           this.writer.close();
/*     */         } 
/* 220 */         return arrayOfByte;
/*     */       } 
/* 222 */       this.writer.close();
/*     */       
/* 224 */       return null;
/*     */     }
/* 226 */     catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 227 */       throw new CanonicalizationException("empty", unsupportedEncodingException);
/* 228 */     } catch (IOException iOException) {
/* 229 */       throw new CanonicalizationException("empty", iOException);
/*     */     } 
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
/*     */   protected final void canonicalizeSubTree(Node paramNode1, NameSpaceSymbTable paramNameSpaceSymbTable, Node paramNode2, int paramInt) throws CanonicalizationException, IOException {
/* 246 */     if (isVisibleInt(paramNode1) == -1) {
/*     */       return;
/*     */     }
/* 249 */     Node node1 = null;
/* 250 */     Element element = null;
/* 251 */     OutputStream outputStream = this.writer;
/* 252 */     Node node2 = this.excludeNode;
/* 253 */     boolean bool = this.includeComments;
/* 254 */     HashMap<Object, Object> hashMap = new HashMap<>(); while (true) {
/*     */       Element element1; String str; Iterator<Attr> iterator;
/* 256 */       switch (paramNode1.getNodeType()) {
/*     */ 
/*     */         
/*     */         case 2:
/*     */         case 6:
/*     */         case 12:
/* 262 */           throw new CanonicalizationException("empty");
/*     */         
/*     */         case 9:
/*     */         case 11:
/* 266 */           paramNameSpaceSymbTable.outputNodePush();
/* 267 */           node1 = paramNode1.getFirstChild();
/*     */           break;
/*     */         
/*     */         case 8:
/* 271 */           if (bool) {
/* 272 */             outputCommentToWriter((Comment)paramNode1, outputStream, paramInt);
/*     */           }
/*     */           break;
/*     */         
/*     */         case 7:
/* 277 */           outputPItoWriter((ProcessingInstruction)paramNode1, outputStream, paramInt);
/*     */           break;
/*     */         
/*     */         case 3:
/*     */         case 4:
/* 282 */           outputTextToWriter(paramNode1.getNodeValue(), outputStream);
/*     */           break;
/*     */         
/*     */         case 1:
/* 286 */           paramInt = 0;
/* 287 */           if (paramNode1 == node2) {
/*     */             break;
/*     */           }
/* 290 */           element1 = (Element)paramNode1;
/*     */           
/* 292 */           paramNameSpaceSymbTable.outputNodePush();
/* 293 */           outputStream.write(60);
/* 294 */           str = element1.getTagName();
/* 295 */           UtfHelpper.writeByte(str, outputStream, (Map)hashMap);
/*     */           
/* 297 */           iterator = handleAttributesSubtree(element1, paramNameSpaceSymbTable);
/* 298 */           if (iterator != null)
/*     */           {
/* 300 */             while (iterator.hasNext()) {
/* 301 */               Attr attr = iterator.next();
/* 302 */               outputAttrToWriter(attr.getNodeName(), attr.getNodeValue(), outputStream, (Map)hashMap);
/*     */             } 
/*     */           }
/* 305 */           outputStream.write(62);
/* 306 */           node1 = paramNode1.getFirstChild();
/* 307 */           if (node1 == null) {
/* 308 */             outputStream.write((byte[])END_TAG.clone());
/* 309 */             UtfHelpper.writeStringToUtf8(str, outputStream);
/* 310 */             outputStream.write(62);
/*     */             
/* 312 */             paramNameSpaceSymbTable.outputNodePop();
/* 313 */             if (element != null)
/* 314 */               node1 = paramNode1.getNextSibling(); 
/*     */             break;
/*     */           } 
/* 317 */           element = element1;
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 325 */       while (node1 == null && element != null) {
/* 326 */         outputStream.write((byte[])END_TAG.clone());
/* 327 */         UtfHelpper.writeByte(element.getTagName(), outputStream, (Map)hashMap);
/* 328 */         outputStream.write(62);
/*     */         
/* 330 */         paramNameSpaceSymbTable.outputNodePop();
/* 331 */         if (element == paramNode2) {
/*     */           return;
/*     */         }
/* 334 */         node1 = element.getNextSibling();
/* 335 */         Node node = element.getParentNode();
/* 336 */         if (node == null || 1 != node.getNodeType()) {
/* 337 */           paramInt = 1;
/* 338 */           node = null;
/*     */         } 
/*     */       } 
/* 341 */       if (node1 == null) {
/*     */         return;
/*     */       }
/* 344 */       paramNode1 = node1;
/* 345 */       node1 = paramNode1.getNextSibling();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] engineCanonicalizeXPathNodeSetInternal(Node paramNode) throws CanonicalizationException {
/*     */     try {
/* 353 */       canonicalizeXPathNodeSet(paramNode, paramNode);
/* 354 */       this.writer.flush();
/* 355 */       if (this.writer instanceof ByteArrayOutputStream) {
/* 356 */         byte[] arrayOfByte = ((ByteArrayOutputStream)this.writer).toByteArray();
/* 357 */         if (this.reset) {
/* 358 */           ((ByteArrayOutputStream)this.writer).reset();
/*     */         } else {
/* 360 */           this.writer.close();
/*     */         } 
/* 362 */         return arrayOfByte;
/* 363 */       }  if (this.writer instanceof UnsyncByteArrayOutputStream) {
/* 364 */         byte[] arrayOfByte = ((UnsyncByteArrayOutputStream)this.writer).toByteArray();
/* 365 */         if (this.reset) {
/* 366 */           ((UnsyncByteArrayOutputStream)this.writer).reset();
/*     */         } else {
/* 368 */           this.writer.close();
/*     */         } 
/* 370 */         return arrayOfByte;
/*     */       } 
/* 372 */       this.writer.close();
/*     */       
/* 374 */       return null;
/* 375 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 376 */       throw new CanonicalizationException("empty", unsupportedEncodingException);
/* 377 */     } catch (IOException iOException) {
/* 378 */       throw new CanonicalizationException("empty", iOException);
/*     */     } 
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
/*     */   protected final void canonicalizeXPathNodeSet(Node paramNode1, Node paramNode2) throws CanonicalizationException, IOException {
/* 393 */     if (isVisibleInt(paramNode1) == -1) {
/*     */       return;
/*     */     }
/* 396 */     boolean bool = false;
/* 397 */     NameSpaceSymbTable nameSpaceSymbTable = new NameSpaceSymbTable();
/* 398 */     if (paramNode1 != null && 1 == paramNode1.getNodeType()) {
/* 399 */       getParentNameSpaces((Element)paramNode1, nameSpaceSymbTable);
/*     */     }
/* 401 */     if (paramNode1 == null) {
/*     */       return;
/*     */     }
/* 404 */     Node node = null;
/* 405 */     Element element = null;
/* 406 */     OutputStream outputStream = this.writer;
/* 407 */     byte b = -1;
/* 408 */     HashMap<Object, Object> hashMap = new HashMap<>(); while (true) {
/*     */       Element element1; String str; int i; Iterator<Attr> iterator;
/* 410 */       switch (paramNode1.getNodeType()) {
/*     */ 
/*     */         
/*     */         case 2:
/*     */         case 6:
/*     */         case 12:
/* 416 */           throw new CanonicalizationException("empty");
/*     */         
/*     */         case 9:
/*     */         case 11:
/* 420 */           nameSpaceSymbTable.outputNodePush();
/* 421 */           node = paramNode1.getFirstChild();
/*     */           break;
/*     */         
/*     */         case 8:
/* 425 */           if (this.includeComments && isVisibleDO(paramNode1, nameSpaceSymbTable.getLevel()) == 1) {
/* 426 */             outputCommentToWriter((Comment)paramNode1, outputStream, b);
/*     */           }
/*     */           break;
/*     */         
/*     */         case 7:
/* 431 */           if (isVisible(paramNode1)) {
/* 432 */             outputPItoWriter((ProcessingInstruction)paramNode1, outputStream, b);
/*     */           }
/*     */           break;
/*     */         
/*     */         case 3:
/*     */         case 4:
/* 438 */           if (isVisible(paramNode1)) {
/* 439 */             outputTextToWriter(paramNode1.getNodeValue(), outputStream);
/* 440 */             Node node1 = paramNode1.getNextSibling();
/* 441 */             for (; node1 != null && (node1.getNodeType() == 3 || node1
/* 442 */               .getNodeType() == 4); 
/* 443 */               node1 = node1.getNextSibling()) {
/* 444 */               outputTextToWriter(node1.getNodeValue(), outputStream);
/* 445 */               paramNode1 = node1;
/* 446 */               node = paramNode1.getNextSibling();
/*     */             } 
/*     */           } 
/*     */           break;
/*     */         
/*     */         case 1:
/* 452 */           b = 0;
/* 453 */           element1 = (Element)paramNode1;
/*     */           
/* 455 */           str = null;
/* 456 */           i = isVisibleDO(paramNode1, nameSpaceSymbTable.getLevel());
/* 457 */           if (i == -1) {
/* 458 */             node = paramNode1.getNextSibling();
/*     */             break;
/*     */           } 
/* 461 */           bool = (i == 1) ? true : false;
/* 462 */           if (bool) {
/* 463 */             nameSpaceSymbTable.outputNodePush();
/* 464 */             outputStream.write(60);
/* 465 */             str = element1.getTagName();
/* 466 */             UtfHelpper.writeByte(str, outputStream, (Map)hashMap);
/*     */           } else {
/* 468 */             nameSpaceSymbTable.push();
/*     */           } 
/*     */           
/* 471 */           iterator = handleAttributes(element1, nameSpaceSymbTable);
/* 472 */           if (iterator != null)
/*     */           {
/* 474 */             while (iterator.hasNext()) {
/* 475 */               Attr attr = iterator.next();
/* 476 */               outputAttrToWriter(attr.getNodeName(), attr.getNodeValue(), outputStream, (Map)hashMap);
/*     */             } 
/*     */           }
/* 479 */           if (bool) {
/* 480 */             outputStream.write(62);
/*     */           }
/* 482 */           node = paramNode1.getFirstChild();
/*     */           
/* 484 */           if (node == null) {
/* 485 */             if (bool) {
/* 486 */               outputStream.write((byte[])END_TAG.clone());
/* 487 */               UtfHelpper.writeByte(str, outputStream, (Map)hashMap);
/* 488 */               outputStream.write(62);
/*     */               
/* 490 */               nameSpaceSymbTable.outputNodePop();
/*     */             } else {
/* 492 */               nameSpaceSymbTable.pop();
/*     */             } 
/* 494 */             if (element != null)
/* 495 */               node = paramNode1.getNextSibling(); 
/*     */             break;
/*     */           } 
/* 498 */           element = element1;
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 506 */       while (node == null && element != null) {
/* 507 */         if (isVisible(element)) {
/* 508 */           outputStream.write((byte[])END_TAG.clone());
/* 509 */           UtfHelpper.writeByte(element.getTagName(), outputStream, (Map)hashMap);
/* 510 */           outputStream.write(62);
/*     */           
/* 512 */           nameSpaceSymbTable.outputNodePop();
/*     */         } else {
/* 514 */           nameSpaceSymbTable.pop();
/*     */         } 
/* 516 */         if (element == paramNode2) {
/*     */           return;
/*     */         }
/* 519 */         node = element.getNextSibling();
/* 520 */         Node node1 = element.getParentNode();
/* 521 */         if (node1 == null || 1 != node1.getNodeType()) {
/* 522 */           node1 = null;
/* 523 */           b = 1;
/*     */         } 
/*     */       } 
/* 526 */       if (node == null) {
/*     */         return;
/*     */       }
/* 529 */       paramNode1 = node;
/* 530 */       node = paramNode1.getNextSibling();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected int isVisibleDO(Node paramNode, int paramInt) {
/* 535 */     if (this.nodeFilter != null) {
/* 536 */       Iterator<NodeFilter> iterator = this.nodeFilter.iterator();
/* 537 */       while (iterator.hasNext()) {
/* 538 */         int i = ((NodeFilter)iterator.next()).isNodeIncludeDO(paramNode, paramInt);
/* 539 */         if (i != 1) {
/* 540 */           return i;
/*     */         }
/*     */       } 
/*     */     } 
/* 544 */     if (this.xpathNodeSet != null && !this.xpathNodeSet.contains(paramNode)) {
/* 545 */       return 0;
/*     */     }
/* 547 */     return 1;
/*     */   }
/*     */   
/*     */   protected int isVisibleInt(Node paramNode) {
/* 551 */     if (this.nodeFilter != null) {
/* 552 */       Iterator<NodeFilter> iterator = this.nodeFilter.iterator();
/* 553 */       while (iterator.hasNext()) {
/* 554 */         int i = ((NodeFilter)iterator.next()).isNodeInclude(paramNode);
/* 555 */         if (i != 1) {
/* 556 */           return i;
/*     */         }
/*     */       } 
/*     */     } 
/* 560 */     if (this.xpathNodeSet != null && !this.xpathNodeSet.contains(paramNode)) {
/* 561 */       return 0;
/*     */     }
/* 563 */     return 1;
/*     */   }
/*     */   
/*     */   protected boolean isVisible(Node paramNode) {
/* 567 */     if (this.nodeFilter != null) {
/* 568 */       Iterator<NodeFilter> iterator = this.nodeFilter.iterator();
/* 569 */       while (iterator.hasNext()) {
/* 570 */         if (((NodeFilter)iterator.next()).isNodeInclude(paramNode) != 1) {
/* 571 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 575 */     if (this.xpathNodeSet != null && !this.xpathNodeSet.contains(paramNode)) {
/* 576 */       return false;
/*     */     }
/* 578 */     return true;
/*     */   }
/*     */   
/*     */   protected void handleParent(Element paramElement, NameSpaceSymbTable paramNameSpaceSymbTable) {
/* 582 */     if (!paramElement.hasAttributes() && paramElement.getNamespaceURI() == null) {
/*     */       return;
/*     */     }
/* 585 */     NamedNodeMap namedNodeMap = paramElement.getAttributes();
/* 586 */     int i = namedNodeMap.getLength();
/* 587 */     for (byte b = 0; b < i; b++) {
/* 588 */       Attr attr = (Attr)namedNodeMap.item(b);
/* 589 */       String str1 = attr.getLocalName();
/* 590 */       String str2 = attr.getNodeValue();
/*     */       
/* 592 */       if ("http://www.w3.org/2000/xmlns/".equals(attr.getNamespaceURI()) && (
/* 593 */         !"xml".equals(str1) || !"http://www.w3.org/XML/1998/namespace".equals(str2))) {
/* 594 */         paramNameSpaceSymbTable.addMapping(str1, str2, attr);
/*     */       }
/*     */     } 
/* 597 */     if (paramElement.getNamespaceURI() != null) {
/* 598 */       String str3, str1 = paramElement.getPrefix();
/* 599 */       String str2 = paramElement.getNamespaceURI();
/*     */       
/* 601 */       if (str1 == null || str1.equals("")) {
/* 602 */         str1 = "xmlns";
/* 603 */         str3 = "xmlns";
/*     */       } else {
/* 605 */         str3 = "xmlns:" + str1;
/*     */       } 
/* 607 */       Attr attr = paramElement.getOwnerDocument().createAttributeNS("http://www.w3.org/2000/xmlns/", str3);
/* 608 */       attr.setValue(str2);
/* 609 */       paramNameSpaceSymbTable.addMapping(str1, str2, attr);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void getParentNameSpaces(Element paramElement, NameSpaceSymbTable paramNameSpaceSymbTable) {
/* 619 */     Node node1 = paramElement.getParentNode();
/* 620 */     if (node1 == null || 1 != node1.getNodeType()) {
/*     */       return;
/*     */     }
/*     */     
/* 624 */     ArrayList<Element> arrayList = new ArrayList();
/* 625 */     Node node2 = node1;
/* 626 */     while (node2 != null && 1 == node2.getNodeType()) {
/* 627 */       arrayList.add((Element)node2);
/* 628 */       node2 = node2.getParentNode();
/*     */     } 
/*     */     
/* 631 */     ListIterator<Element> listIterator = arrayList.listIterator(arrayList.size());
/* 632 */     while (listIterator.hasPrevious()) {
/* 633 */       Element element = listIterator.previous();
/* 634 */       handleParent(element, paramNameSpaceSymbTable);
/*     */     } 
/* 636 */     arrayList.clear();
/*     */     Attr attr;
/* 638 */     if ((attr = paramNameSpaceSymbTable.getMappingWithoutRendered("xmlns")) != null && ""
/* 639 */       .equals(attr.getValue())) {
/* 640 */       paramNameSpaceSymbTable.addMappingAndRender("xmlns", "", 
/* 641 */           getNullNode(attr.getOwnerDocument()));
/*     */     }
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
/*     */   abstract Iterator<Attr> handleAttributes(Element paramElement, NameSpaceSymbTable paramNameSpaceSymbTable) throws CanonicalizationException;
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
/*     */   abstract Iterator<Attr> handleAttributesSubtree(Element paramElement, NameSpaceSymbTable paramNameSpaceSymbTable) throws CanonicalizationException;
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
/*     */   abstract void circumventBugIfNeeded(XMLSignatureInput paramXMLSignatureInput) throws CanonicalizationException, ParserConfigurationException, IOException, SAXException;
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
/*     */   protected static final void outputAttrToWriter(String paramString1, String paramString2, OutputStream paramOutputStream, Map<String, byte[]> paramMap) throws IOException {
/* 693 */     paramOutputStream.write(32);
/* 694 */     UtfHelpper.writeByte(paramString1, paramOutputStream, paramMap);
/* 695 */     paramOutputStream.write((byte[])EQUALS_STR.clone());
/*     */     
/* 697 */     int i = paramString2.length();
/* 698 */     byte b = 0;
/* 699 */     while (b < i) {
/* 700 */       byte[] arrayOfByte; char c = paramString2.charAt(b++);
/*     */       
/* 702 */       switch (c) {
/*     */         
/*     */         case '&':
/* 705 */           arrayOfByte = (byte[])AMP.clone();
/*     */           break;
/*     */         
/*     */         case '<':
/* 709 */           arrayOfByte = (byte[])LT.clone();
/*     */           break;
/*     */         
/*     */         case '"':
/* 713 */           arrayOfByte = (byte[])QUOT.clone();
/*     */           break;
/*     */         
/*     */         case '\t':
/* 717 */           arrayOfByte = (byte[])X9.clone();
/*     */           break;
/*     */         
/*     */         case '\n':
/* 721 */           arrayOfByte = (byte[])XA.clone();
/*     */           break;
/*     */         
/*     */         case '\r':
/* 725 */           arrayOfByte = (byte[])XD.clone();
/*     */           break;
/*     */         
/*     */         default:
/* 729 */           if (c < '') {
/* 730 */             paramOutputStream.write(c); continue;
/*     */           } 
/* 732 */           UtfHelpper.writeCharToUtf8(c, paramOutputStream);
/*     */           continue;
/*     */       } 
/*     */       
/* 736 */       paramOutputStream.write(arrayOfByte);
/*     */     } 
/*     */     
/* 739 */     paramOutputStream.write(34);
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
/*     */   protected void outputPItoWriter(ProcessingInstruction paramProcessingInstruction, OutputStream paramOutputStream, int paramInt) throws IOException {
/* 752 */     if (paramInt == 1) {
/* 753 */       paramOutputStream.write(10);
/*     */     }
/* 755 */     paramOutputStream.write((byte[])BEGIN_PI.clone());
/*     */     
/* 757 */     String str1 = paramProcessingInstruction.getTarget();
/* 758 */     int i = str1.length();
/*     */     
/* 760 */     for (byte b = 0; b < i; b++) {
/* 761 */       char c = str1.charAt(b);
/* 762 */       if (c == '\r') {
/* 763 */         paramOutputStream.write((byte[])XD.clone());
/*     */       }
/* 765 */       else if (c < '') {
/* 766 */         paramOutputStream.write(c);
/*     */       } else {
/* 768 */         UtfHelpper.writeCharToUtf8(c, paramOutputStream);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 773 */     String str2 = paramProcessingInstruction.getData();
/*     */     
/* 775 */     i = str2.length();
/*     */     
/* 777 */     if (i > 0) {
/* 778 */       paramOutputStream.write(32);
/*     */       
/* 780 */       for (byte b1 = 0; b1 < i; b1++) {
/* 781 */         char c = str2.charAt(b1);
/* 782 */         if (c == '\r') {
/* 783 */           paramOutputStream.write((byte[])XD.clone());
/*     */         } else {
/* 785 */           UtfHelpper.writeCharToUtf8(c, paramOutputStream);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 790 */     paramOutputStream.write((byte[])END_PI.clone());
/* 791 */     if (paramInt == -1) {
/* 792 */       paramOutputStream.write(10);
/*     */     }
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
/*     */   protected void outputCommentToWriter(Comment paramComment, OutputStream paramOutputStream, int paramInt) throws IOException {
/* 806 */     if (paramInt == 1) {
/* 807 */       paramOutputStream.write(10);
/*     */     }
/* 809 */     paramOutputStream.write((byte[])BEGIN_COMM.clone());
/*     */     
/* 811 */     String str = paramComment.getData();
/* 812 */     int i = str.length();
/*     */     
/* 814 */     for (byte b = 0; b < i; b++) {
/* 815 */       char c = str.charAt(b);
/* 816 */       if (c == '\r') {
/* 817 */         paramOutputStream.write((byte[])XD.clone());
/*     */       }
/* 819 */       else if (c < '') {
/* 820 */         paramOutputStream.write(c);
/*     */       } else {
/* 822 */         UtfHelpper.writeCharToUtf8(c, paramOutputStream);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 827 */     paramOutputStream.write((byte[])END_COMM.clone());
/* 828 */     if (paramInt == -1) {
/* 829 */       paramOutputStream.write(10);
/*     */     }
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
/*     */   protected static final void outputTextToWriter(String paramString, OutputStream paramOutputStream) throws IOException {
/* 843 */     int i = paramString.length();
/*     */     
/* 845 */     byte b = 0; while (true) { if (b < i) {
/* 846 */         byte[] arrayOfByte; char c = paramString.charAt(b);
/*     */         
/* 848 */         switch (c) {
/*     */           
/*     */           case '&':
/* 851 */             arrayOfByte = (byte[])AMP.clone();
/*     */             break;
/*     */           
/*     */           case '<':
/* 855 */             arrayOfByte = (byte[])LT.clone();
/*     */             break;
/*     */           
/*     */           case '>':
/* 859 */             arrayOfByte = (byte[])GT.clone();
/*     */             break;
/*     */           
/*     */           case '\r':
/* 863 */             arrayOfByte = (byte[])XD.clone();
/*     */             break;
/*     */           
/*     */           default:
/* 867 */             if (c < '') {
/* 868 */               paramOutputStream.write(c);
/*     */             } else {
/* 870 */               UtfHelpper.writeCharToUtf8(c, paramOutputStream);
/*     */             }  b++;
/*     */             continue;
/*     */         } 
/* 874 */         paramOutputStream.write(arrayOfByte);
/*     */       } else {
/*     */         break;
/*     */       } 
/*     */       b++; }
/*     */      } protected Attr getNullNode(Document paramDocument) {
/* 880 */     if (this.nullNode == null) {
/*     */       try {
/* 882 */         this.nullNode = paramDocument.createAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns");
/*     */         
/* 884 */         this.nullNode.setValue("");
/* 885 */       } catch (Exception exception) {
/* 886 */         throw new RuntimeException("Unable to create nullNode: " + exception);
/*     */       } 
/*     */     }
/* 889 */     return this.nullNode;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/c14n/implementations/CanonicalizerBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */