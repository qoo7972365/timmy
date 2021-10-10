/*     */ package com.sun.org.apache.xml.internal.security.c14n.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
/*     */ import com.sun.org.apache.xml.internal.security.c14n.helper.C14nHelper;
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeSet;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
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
/*     */ 
/*     */ 
/*     */ public abstract class Canonicalizer11
/*     */   extends CanonicalizerBase
/*     */ {
/*     */   private static final String XMLNS_URI = "http://www.w3.org/2000/xmlns/";
/*     */   private static final String XML_LANG_URI = "http://www.w3.org/XML/1998/namespace";
/*  64 */   private static Logger log = Logger.getLogger(Canonicalizer11.class.getName());
/*  65 */   private final SortedSet<Attr> result = new TreeSet<>(COMPARE);
/*     */   private boolean firstCall = true;
/*     */   
/*     */   private static class XmlAttrStack
/*     */   {
/*     */     static class XmlsStackElement {
/*     */       int level;
/*     */       boolean rendered = false;
/*  73 */       List<Attr> nodes = new ArrayList<>();
/*     */     }
/*     */     
/*  76 */     int currentLevel = 0;
/*  77 */     int lastlevel = 0;
/*     */     XmlsStackElement cur;
/*  79 */     List<XmlsStackElement> levels = new ArrayList<>();
/*     */     
/*     */     void push(int param1Int) {
/*  82 */       this.currentLevel = param1Int;
/*  83 */       if (this.currentLevel == -1) {
/*     */         return;
/*     */       }
/*  86 */       this.cur = null;
/*  87 */       while (this.lastlevel >= this.currentLevel) {
/*  88 */         this.levels.remove(this.levels.size() - 1);
/*  89 */         int i = this.levels.size();
/*  90 */         if (i == 0) {
/*  91 */           this.lastlevel = 0;
/*     */           return;
/*     */         } 
/*  94 */         this.lastlevel = ((XmlsStackElement)this.levels.get(i - 1)).level;
/*     */       } 
/*     */     }
/*     */     
/*     */     void addXmlnsAttr(Attr param1Attr) {
/*  99 */       if (this.cur == null) {
/* 100 */         this.cur = new XmlsStackElement();
/* 101 */         this.cur.level = this.currentLevel;
/* 102 */         this.levels.add(this.cur);
/* 103 */         this.lastlevel = this.currentLevel;
/*     */       } 
/* 105 */       this.cur.nodes.add(param1Attr);
/*     */     }
/*     */     
/*     */     void getXmlnsAttr(Collection<Attr> param1Collection) {
/* 109 */       int i = this.levels.size() - 1;
/* 110 */       if (this.cur == null) {
/* 111 */         this.cur = new XmlsStackElement();
/* 112 */         this.cur.level = this.currentLevel;
/* 113 */         this.lastlevel = this.currentLevel;
/* 114 */         this.levels.add(this.cur);
/*     */       } 
/* 116 */       boolean bool1 = false;
/* 117 */       XmlsStackElement xmlsStackElement = null;
/* 118 */       if (i == -1) {
/* 119 */         bool1 = true;
/*     */       } else {
/* 121 */         xmlsStackElement = this.levels.get(i);
/* 122 */         if (xmlsStackElement.rendered && xmlsStackElement.level + 1 == this.currentLevel) {
/* 123 */           bool1 = true;
/*     */         }
/*     */       } 
/* 126 */       if (bool1) {
/* 127 */         param1Collection.addAll(this.cur.nodes);
/* 128 */         this.cur.rendered = true;
/*     */         
/*     */         return;
/*     */       } 
/* 132 */       HashMap<Object, Object> hashMap = new HashMap<>();
/* 133 */       ArrayList<Attr> arrayList = new ArrayList();
/* 134 */       boolean bool2 = true;
/* 135 */       for (; i >= 0; i--) {
/* 136 */         xmlsStackElement = this.levels.get(i);
/* 137 */         if (xmlsStackElement.rendered) {
/* 138 */           bool2 = false;
/*     */         }
/* 140 */         Iterator<Attr> iterator = xmlsStackElement.nodes.iterator();
/* 141 */         while (iterator.hasNext() && bool2) {
/* 142 */           Attr attr = iterator.next();
/* 143 */           if (attr.getLocalName().equals("base") && !xmlsStackElement.rendered) {
/* 144 */             arrayList.add(attr); continue;
/* 145 */           }  if (!hashMap.containsKey(attr.getName())) {
/* 146 */             hashMap.put(attr.getName(), attr);
/*     */           }
/*     */         } 
/*     */       } 
/* 150 */       if (!arrayList.isEmpty()) {
/* 151 */         Iterator<Attr> iterator = param1Collection.iterator();
/* 152 */         String str = null;
/* 153 */         Attr attr = null;
/* 154 */         while (iterator.hasNext()) {
/* 155 */           Attr attr1 = iterator.next();
/* 156 */           if (attr1.getLocalName().equals("base")) {
/* 157 */             str = attr1.getValue();
/* 158 */             attr = attr1;
/*     */             break;
/*     */           } 
/*     */         } 
/* 162 */         iterator = arrayList.iterator();
/* 163 */         while (iterator.hasNext()) {
/* 164 */           Attr attr1 = iterator.next();
/* 165 */           if (str == null) {
/* 166 */             str = attr1.getValue();
/* 167 */             attr = attr1; continue;
/*     */           } 
/*     */           try {
/* 170 */             str = Canonicalizer11.joinURI(attr1.getValue(), str);
/* 171 */           } catch (URISyntaxException uRISyntaxException) {
/* 172 */             if (Canonicalizer11.log.isLoggable(Level.FINE)) {
/* 173 */               Canonicalizer11.log.log(Level.FINE, uRISyntaxException.getMessage(), uRISyntaxException);
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 178 */         if (str != null && str.length() != 0) {
/* 179 */           attr.setValue(str);
/* 180 */           param1Collection.add(attr);
/*     */         } 
/*     */       } 
/*     */       
/* 184 */       this.cur.rendered = true;
/* 185 */       param1Collection.addAll(hashMap.values());
/*     */     }
/*     */     private XmlAttrStack() {} }
/*     */   
/* 189 */   private XmlAttrStack xmlattrStack = new XmlAttrStack();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Canonicalizer11(boolean paramBoolean) {
/* 197 */     super(paramBoolean);
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
/*     */   public byte[] engineCanonicalizeXPathNodeSet(Set<Node> paramSet, String paramString) throws CanonicalizationException {
/* 211 */     throw new CanonicalizationException("c14n.Canonicalizer.UnsupportedOperation");
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
/*     */   public byte[] engineCanonicalizeSubTree(Node paramNode, String paramString) throws CanonicalizationException {
/* 225 */     throw new CanonicalizationException("c14n.Canonicalizer.UnsupportedOperation");
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
/*     */   protected Iterator<Attr> handleAttributesSubtree(Element paramElement, NameSpaceSymbTable paramNameSpaceSymbTable) throws CanonicalizationException {
/* 246 */     if (!paramElement.hasAttributes() && !this.firstCall) {
/* 247 */       return null;
/*     */     }
/*     */     
/* 250 */     SortedSet<Attr> sortedSet = this.result;
/* 251 */     sortedSet.clear();
/*     */     
/* 253 */     if (paramElement.hasAttributes()) {
/* 254 */       NamedNodeMap namedNodeMap = paramElement.getAttributes();
/* 255 */       int i = namedNodeMap.getLength();
/*     */       
/* 257 */       for (byte b = 0; b < i; b++) {
/* 258 */         Attr attr = (Attr)namedNodeMap.item(b);
/* 259 */         String str1 = attr.getNamespaceURI();
/* 260 */         String str2 = attr.getLocalName();
/* 261 */         String str3 = attr.getValue();
/*     */         
/* 263 */         if (!"http://www.w3.org/2000/xmlns/".equals(str1)) {
/*     */           
/* 265 */           sortedSet.add(attr);
/* 266 */         } else if (!"xml".equals(str2) || !"http://www.w3.org/XML/1998/namespace".equals(str3)) {
/*     */           
/* 268 */           Node node = paramNameSpaceSymbTable.addMappingAndRender(str2, str3, attr);
/*     */           
/* 270 */           if (node != null) {
/*     */             
/* 272 */             sortedSet.add((Attr)node);
/* 273 */             if (C14nHelper.namespaceIsRelative(attr)) {
/* 274 */               Object[] arrayOfObject = { paramElement.getTagName(), str2, attr.getNodeValue() };
/* 275 */               throw new CanonicalizationException("c14n.Canonicalizer.RelativeNamespace", arrayOfObject);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 284 */     if (this.firstCall) {
/*     */ 
/*     */       
/* 287 */       paramNameSpaceSymbTable.getUnrenderedNodes(sortedSet);
/*     */       
/* 289 */       this.xmlattrStack.getXmlnsAttr(sortedSet);
/* 290 */       this.firstCall = false;
/*     */     } 
/*     */     
/* 293 */     return sortedSet.iterator();
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
/*     */   protected Iterator<Attr> handleAttributes(Element paramElement, NameSpaceSymbTable paramNameSpaceSymbTable) throws CanonicalizationException {
/* 313 */     this.xmlattrStack.push(paramNameSpaceSymbTable.getLevel());
/* 314 */     boolean bool = (isVisibleDO(paramElement, paramNameSpaceSymbTable.getLevel()) == 1) ? true : false;
/* 315 */     SortedSet<Attr> sortedSet = this.result;
/* 316 */     sortedSet.clear();
/*     */     
/* 318 */     if (paramElement.hasAttributes()) {
/* 319 */       NamedNodeMap namedNodeMap = paramElement.getAttributes();
/* 320 */       int i = namedNodeMap.getLength();
/*     */       
/* 322 */       for (byte b = 0; b < i; b++) {
/* 323 */         Attr attr = (Attr)namedNodeMap.item(b);
/* 324 */         String str1 = attr.getNamespaceURI();
/* 325 */         String str2 = attr.getLocalName();
/* 326 */         String str3 = attr.getValue();
/*     */         
/* 328 */         if (!"http://www.w3.org/2000/xmlns/".equals(str1)) {
/*     */           
/* 330 */           if ("http://www.w3.org/XML/1998/namespace".equals(str1)) {
/* 331 */             if (str2.equals("id")) {
/* 332 */               if (bool)
/*     */               {
/*     */                 
/* 335 */                 sortedSet.add(attr);
/*     */               }
/*     */             } else {
/* 338 */               this.xmlattrStack.addXmlnsAttr(attr);
/*     */             } 
/* 340 */           } else if (bool) {
/*     */             
/* 342 */             sortedSet.add(attr);
/*     */           } 
/* 344 */         } else if (!"xml".equals(str2) || !"http://www.w3.org/XML/1998/namespace".equals(str3)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 350 */           if (isVisible(attr)) {
/* 351 */             if (bool || !paramNameSpaceSymbTable.removeMappingIfRender(str2))
/*     */             {
/* 353 */               Node node = paramNameSpaceSymbTable.addMappingAndRender(str2, str3, attr);
/* 354 */               if (node != null) {
/* 355 */                 sortedSet.add((Attr)node);
/* 356 */                 if (C14nHelper.namespaceIsRelative(attr)) {
/* 357 */                   Object[] arrayOfObject = { paramElement.getTagName(), str2, attr.getNodeValue() };
/* 358 */                   throw new CanonicalizationException("c14n.Canonicalizer.RelativeNamespace", arrayOfObject);
/*     */                 }
/*     */               
/*     */               }
/*     */             
/*     */             }
/*     */           
/* 365 */           } else if (bool && !"xmlns".equals(str2)) {
/* 366 */             paramNameSpaceSymbTable.removeMapping(str2);
/*     */           } else {
/* 368 */             paramNameSpaceSymbTable.addMapping(str2, str3, attr);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 375 */     if (bool) {
/*     */       
/* 377 */       Attr attr = paramElement.getAttributeNodeNS("http://www.w3.org/2000/xmlns/", "xmlns");
/* 378 */       Node node = null;
/* 379 */       if (attr == null) {
/*     */         
/* 381 */         node = paramNameSpaceSymbTable.getMapping("xmlns");
/* 382 */       } else if (!isVisible(attr)) {
/*     */ 
/*     */         
/* 385 */         node = paramNameSpaceSymbTable.addMappingAndRender("xmlns", "", 
/* 386 */             getNullNode(attr.getOwnerDocument()));
/*     */       } 
/*     */       
/* 389 */       if (node != null) {
/* 390 */         sortedSet.add((Attr)node);
/*     */       }
/*     */       
/* 393 */       this.xmlattrStack.getXmlnsAttr(sortedSet);
/* 394 */       paramNameSpaceSymbTable.getUnrenderedNodes(sortedSet);
/*     */     } 
/*     */     
/* 397 */     return sortedSet.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void circumventBugIfNeeded(XMLSignatureInput paramXMLSignatureInput) throws CanonicalizationException, ParserConfigurationException, IOException, SAXException {
/* 403 */     if (!paramXMLSignatureInput.isNeedsToBeExpanded()) {
/*     */       return;
/*     */     }
/* 406 */     Document document = null;
/* 407 */     if (paramXMLSignatureInput.getSubNode() != null) {
/* 408 */       document = XMLUtils.getOwnerDocument(paramXMLSignatureInput.getSubNode());
/*     */     } else {
/* 410 */       document = XMLUtils.getOwnerDocument(paramXMLSignatureInput.getNodeSet());
/*     */     } 
/* 412 */     XMLUtils.circumventBug2650(document);
/*     */   }
/*     */   
/*     */   protected void handleParent(Element paramElement, NameSpaceSymbTable paramNameSpaceSymbTable) {
/* 416 */     if (!paramElement.hasAttributes() && paramElement.getNamespaceURI() == null) {
/*     */       return;
/*     */     }
/* 419 */     this.xmlattrStack.push(-1);
/* 420 */     NamedNodeMap namedNodeMap = paramElement.getAttributes();
/* 421 */     int i = namedNodeMap.getLength();
/* 422 */     for (byte b = 0; b < i; b++) {
/* 423 */       Attr attr = (Attr)namedNodeMap.item(b);
/* 424 */       String str1 = attr.getLocalName();
/* 425 */       String str2 = attr.getNodeValue();
/*     */       
/* 427 */       if ("http://www.w3.org/2000/xmlns/".equals(attr.getNamespaceURI())) {
/* 428 */         if (!"xml".equals(str1) || !"http://www.w3.org/XML/1998/namespace".equals(str2)) {
/* 429 */           paramNameSpaceSymbTable.addMapping(str1, str2, attr);
/*     */         }
/* 431 */       } else if (!"id".equals(str1) && "http://www.w3.org/XML/1998/namespace".equals(attr.getNamespaceURI())) {
/* 432 */         this.xmlattrStack.addXmlnsAttr(attr);
/*     */       } 
/*     */     } 
/* 435 */     if (paramElement.getNamespaceURI() != null) {
/* 436 */       String str3, str1 = paramElement.getPrefix();
/* 437 */       String str2 = paramElement.getNamespaceURI();
/*     */       
/* 439 */       if (str1 == null || str1.equals("")) {
/* 440 */         str1 = "xmlns";
/* 441 */         str3 = "xmlns";
/*     */       } else {
/* 443 */         str3 = "xmlns:" + str1;
/*     */       } 
/* 445 */       Attr attr = paramElement.getOwnerDocument().createAttributeNS("http://www.w3.org/2000/xmlns/", str3);
/* 446 */       attr.setValue(str2);
/* 447 */       paramNameSpaceSymbTable.addMapping(str1, str2, attr);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String joinURI(String paramString1, String paramString2) throws URISyntaxException {
/* 452 */     String str9, str10, str11, str12, str1 = null;
/* 453 */     String str2 = null;
/* 454 */     String str3 = "";
/* 455 */     String str4 = null;
/*     */ 
/*     */     
/* 458 */     if (paramString1 != null) {
/* 459 */       if (paramString1.endsWith("..")) {
/* 460 */         paramString1 = paramString1 + "/";
/*     */       }
/* 462 */       URI uRI1 = new URI(paramString1);
/* 463 */       str1 = uRI1.getScheme();
/* 464 */       str2 = uRI1.getAuthority();
/* 465 */       str3 = uRI1.getPath();
/* 466 */       str4 = uRI1.getQuery();
/*     */     } 
/*     */     
/* 469 */     URI uRI = new URI(paramString2);
/* 470 */     String str5 = uRI.getScheme();
/* 471 */     String str6 = uRI.getAuthority();
/* 472 */     String str7 = uRI.getPath();
/* 473 */     String str8 = uRI.getQuery();
/*     */ 
/*     */     
/* 476 */     if (str5 != null && str5.equals(str1)) {
/* 477 */       str5 = null;
/*     */     }
/* 479 */     if (str5 != null) {
/* 480 */       str9 = str5;
/* 481 */       str10 = str6;
/* 482 */       str11 = removeDotSegments(str7);
/* 483 */       str12 = str8;
/*     */     } else {
/* 485 */       if (str6 != null) {
/* 486 */         str10 = str6;
/* 487 */         str11 = removeDotSegments(str7);
/* 488 */         str12 = str8;
/*     */       } else {
/* 490 */         if (str7.length() == 0) {
/* 491 */           str11 = str3;
/* 492 */           if (str8 != null) {
/* 493 */             str12 = str8;
/*     */           } else {
/* 495 */             str12 = str4;
/*     */           } 
/*     */         } else {
/* 498 */           if (str7.startsWith("/")) {
/* 499 */             str11 = removeDotSegments(str7);
/*     */           } else {
/* 501 */             if (str2 != null && str3.length() == 0) {
/* 502 */               str11 = "/" + str7;
/*     */             } else {
/* 504 */               int i = str3.lastIndexOf('/');
/* 505 */               if (i == -1) {
/* 506 */                 str11 = str7;
/*     */               } else {
/* 508 */                 str11 = str3.substring(0, i + 1) + str7;
/*     */               } 
/*     */             } 
/* 511 */             str11 = removeDotSegments(str11);
/*     */           } 
/* 513 */           str12 = str8;
/*     */         } 
/* 515 */         str10 = str2;
/*     */       } 
/* 517 */       str9 = str1;
/*     */     } 
/* 519 */     return (new URI(str9, str10, str11, str12, null)).toString();
/*     */   }
/*     */   
/*     */   private static String removeDotSegments(String paramString) {
/* 523 */     if (log.isLoggable(Level.FINE)) {
/* 524 */       log.log(Level.FINE, "STEP   OUTPUT BUFFER\t\tINPUT BUFFER");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 530 */     String str = paramString;
/* 531 */     while (str.indexOf("//") > -1) {
/* 532 */       str = str.replaceAll("//", "/");
/*     */     }
/*     */ 
/*     */     
/* 536 */     StringBuilder stringBuilder = new StringBuilder();
/*     */ 
/*     */ 
/*     */     
/* 540 */     if (str.charAt(0) == '/') {
/* 541 */       stringBuilder.append("/");
/* 542 */       str = str.substring(1);
/*     */     } 
/*     */     
/* 545 */     printStep("1 ", stringBuilder.toString(), str);
/*     */ 
/*     */     
/* 548 */     while (str.length() != 0) {
/*     */       String str1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 555 */       if (str.startsWith("./")) {
/* 556 */         str = str.substring(2);
/* 557 */         printStep("2A", stringBuilder.toString(), str); continue;
/* 558 */       }  if (str.startsWith("../")) {
/* 559 */         str = str.substring(3);
/* 560 */         if (!stringBuilder.toString().equals("/")) {
/* 561 */           stringBuilder.append("../");
/*     */         }
/* 563 */         printStep("2A", stringBuilder.toString(), str);
/*     */         
/*     */         continue;
/*     */       } 
/* 567 */       if (str.startsWith("/./")) {
/* 568 */         str = str.substring(2);
/* 569 */         printStep("2B", stringBuilder.toString(), str); continue;
/* 570 */       }  if (str.equals("/.")) {
/*     */         
/* 572 */         str = str.replaceFirst("/.", "/");
/* 573 */         printStep("2B", stringBuilder.toString(), str);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 584 */       if (str.startsWith("/../")) {
/* 585 */         str = str.substring(3);
/* 586 */         if (stringBuilder.length() == 0) {
/* 587 */           stringBuilder.append("/");
/* 588 */         } else if (stringBuilder.toString().endsWith("../")) {
/* 589 */           stringBuilder.append("..");
/* 590 */         } else if (stringBuilder.toString().endsWith("..")) {
/* 591 */           stringBuilder.append("/..");
/*     */         } else {
/* 593 */           int k = stringBuilder.lastIndexOf("/");
/* 594 */           if (k == -1) {
/* 595 */             stringBuilder = new StringBuilder();
/* 596 */             if (str.charAt(0) == '/') {
/* 597 */               str = str.substring(1);
/*     */             }
/*     */           } else {
/* 600 */             stringBuilder = stringBuilder.delete(k, stringBuilder.length());
/*     */           } 
/*     */         } 
/* 603 */         printStep("2C", stringBuilder.toString(), str); continue;
/* 604 */       }  if (str.equals("/..")) {
/*     */         
/* 606 */         str = str.replaceFirst("/..", "/");
/* 607 */         if (stringBuilder.length() == 0) {
/* 608 */           stringBuilder.append("/");
/* 609 */         } else if (stringBuilder.toString().endsWith("../")) {
/* 610 */           stringBuilder.append("..");
/* 611 */         } else if (stringBuilder.toString().endsWith("..")) {
/* 612 */           stringBuilder.append("/..");
/*     */         } else {
/* 614 */           int k = stringBuilder.lastIndexOf("/");
/* 615 */           if (k == -1) {
/* 616 */             stringBuilder = new StringBuilder();
/* 617 */             if (str.charAt(0) == '/') {
/* 618 */               str = str.substring(1);
/*     */             }
/*     */           } else {
/* 621 */             stringBuilder = stringBuilder.delete(k, stringBuilder.length());
/*     */           } 
/*     */         } 
/* 624 */         printStep("2C", stringBuilder.toString(), str);
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 630 */       if (str.equals(".")) {
/* 631 */         str = "";
/* 632 */         printStep("2D", stringBuilder.toString(), str); continue;
/* 633 */       }  if (str.equals("..")) {
/* 634 */         if (!stringBuilder.toString().equals("/")) {
/* 635 */           stringBuilder.append("..");
/*     */         }
/* 637 */         str = "";
/* 638 */         printStep("2D", stringBuilder.toString(), str);
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 644 */       int i = -1;
/* 645 */       int j = str.indexOf('/');
/* 646 */       if (j == 0) {
/* 647 */         i = str.indexOf('/', 1);
/*     */       } else {
/* 649 */         i = j;
/* 650 */         j = 0;
/*     */       } 
/*     */       
/* 653 */       if (i == -1) {
/* 654 */         str1 = str.substring(j);
/* 655 */         str = "";
/*     */       } else {
/* 657 */         str1 = str.substring(j, i);
/* 658 */         str = str.substring(i);
/*     */       } 
/* 660 */       stringBuilder.append(str1);
/* 661 */       printStep("2E", stringBuilder.toString(), str);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 669 */     if (stringBuilder.toString().endsWith("..")) {
/* 670 */       stringBuilder.append("/");
/* 671 */       printStep("3 ", stringBuilder.toString(), str);
/*     */     } 
/*     */     
/* 674 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   private static void printStep(String paramString1, String paramString2, String paramString3) {
/* 678 */     if (log.isLoggable(Level.FINE)) {
/* 679 */       log.log(Level.FINE, " " + paramString1 + ":   " + paramString2);
/* 680 */       if (paramString2.length() == 0) {
/* 681 */         log.log(Level.FINE, "\t\t\t\t" + paramString3);
/*     */       } else {
/* 683 */         log.log(Level.FINE, "\t\t\t" + paramString3);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/c14n/implementations/Canonicalizer11.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */