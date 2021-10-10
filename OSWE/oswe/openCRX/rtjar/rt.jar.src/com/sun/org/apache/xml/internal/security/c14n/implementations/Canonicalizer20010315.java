/*     */ package com.sun.org.apache.xml.internal.security.c14n.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
/*     */ import com.sun.org.apache.xml.internal.security.c14n.helper.C14nHelper;
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeSet;
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
/*     */ public abstract class Canonicalizer20010315
/*     */   extends CanonicalizerBase
/*     */ {
/*     */   private static final String XMLNS_URI = "http://www.w3.org/2000/xmlns/";
/*     */   private static final String XML_LANG_URI = "http://www.w3.org/XML/1998/namespace";
/*     */   private boolean firstCall = true;
/*  61 */   private final SortedSet<Attr> result = new TreeSet<>(COMPARE);
/*     */   
/*     */   private static class XmlAttrStack {
/*     */     static class XmlsStackElement {
/*     */       int level;
/*     */       boolean rendered = false;
/*  67 */       List<Attr> nodes = new ArrayList<>();
/*     */     }
/*     */     
/*  70 */     int currentLevel = 0;
/*  71 */     int lastlevel = 0;
/*     */     XmlsStackElement cur;
/*  73 */     List<XmlsStackElement> levels = new ArrayList<>();
/*     */     
/*     */     void push(int param1Int) {
/*  76 */       this.currentLevel = param1Int;
/*  77 */       if (this.currentLevel == -1) {
/*     */         return;
/*     */       }
/*  80 */       this.cur = null;
/*  81 */       while (this.lastlevel >= this.currentLevel) {
/*  82 */         this.levels.remove(this.levels.size() - 1);
/*  83 */         int i = this.levels.size();
/*  84 */         if (i == 0) {
/*  85 */           this.lastlevel = 0;
/*     */           return;
/*     */         } 
/*  88 */         this.lastlevel = ((XmlsStackElement)this.levels.get(i - 1)).level;
/*     */       } 
/*     */     }
/*     */     
/*     */     void addXmlnsAttr(Attr param1Attr) {
/*  93 */       if (this.cur == null) {
/*  94 */         this.cur = new XmlsStackElement();
/*  95 */         this.cur.level = this.currentLevel;
/*  96 */         this.levels.add(this.cur);
/*  97 */         this.lastlevel = this.currentLevel;
/*     */       } 
/*  99 */       this.cur.nodes.add(param1Attr);
/*     */     }
/*     */     
/*     */     void getXmlnsAttr(Collection<Attr> param1Collection) {
/* 103 */       int i = this.levels.size() - 1;
/* 104 */       if (this.cur == null) {
/* 105 */         this.cur = new XmlsStackElement();
/* 106 */         this.cur.level = this.currentLevel;
/* 107 */         this.lastlevel = this.currentLevel;
/* 108 */         this.levels.add(this.cur);
/*     */       } 
/* 110 */       boolean bool = false;
/* 111 */       XmlsStackElement xmlsStackElement = null;
/* 112 */       if (i == -1) {
/* 113 */         bool = true;
/*     */       } else {
/* 115 */         xmlsStackElement = this.levels.get(i);
/* 116 */         if (xmlsStackElement.rendered && xmlsStackElement.level + 1 == this.currentLevel) {
/* 117 */           bool = true;
/*     */         }
/*     */       } 
/* 120 */       if (bool) {
/* 121 */         param1Collection.addAll(this.cur.nodes);
/* 122 */         this.cur.rendered = true;
/*     */         
/*     */         return;
/*     */       } 
/* 126 */       HashMap<Object, Object> hashMap = new HashMap<>();
/* 127 */       for (; i >= 0; i--) {
/* 128 */         xmlsStackElement = this.levels.get(i);
/* 129 */         Iterator<Attr> iterator = xmlsStackElement.nodes.iterator();
/* 130 */         while (iterator.hasNext()) {
/* 131 */           Attr attr = iterator.next();
/* 132 */           if (!hashMap.containsKey(attr.getName())) {
/* 133 */             hashMap.put(attr.getName(), attr);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 138 */       this.cur.rendered = true;
/* 139 */       param1Collection.addAll(hashMap.values());
/*     */     }
/*     */     
/*     */     private XmlAttrStack() {} }
/*     */   
/* 144 */   private XmlAttrStack xmlattrStack = new XmlAttrStack();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Canonicalizer20010315(boolean paramBoolean) {
/* 152 */     super(paramBoolean);
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
/*     */   public byte[] engineCanonicalizeXPathNodeSet(Set<Node> paramSet, String paramString) throws CanonicalizationException {
/* 167 */     throw new CanonicalizationException("c14n.Canonicalizer.UnsupportedOperation");
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
/*     */   public byte[] engineCanonicalizeSubTree(Node paramNode, String paramString) throws CanonicalizationException {
/* 182 */     throw new CanonicalizationException("c14n.Canonicalizer.UnsupportedOperation");
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
/*     */   protected Iterator<Attr> handleAttributesSubtree(Element paramElement, NameSpaceSymbTable paramNameSpaceSymbTable) throws CanonicalizationException {
/* 202 */     if (!paramElement.hasAttributes() && !this.firstCall) {
/* 203 */       return null;
/*     */     }
/*     */     
/* 206 */     SortedSet<Attr> sortedSet = this.result;
/* 207 */     sortedSet.clear();
/*     */     
/* 209 */     if (paramElement.hasAttributes()) {
/* 210 */       NamedNodeMap namedNodeMap = paramElement.getAttributes();
/* 211 */       int i = namedNodeMap.getLength();
/*     */       
/* 213 */       for (byte b = 0; b < i; b++) {
/* 214 */         Attr attr = (Attr)namedNodeMap.item(b);
/* 215 */         String str1 = attr.getNamespaceURI();
/* 216 */         String str2 = attr.getLocalName();
/* 217 */         String str3 = attr.getValue();
/*     */         
/* 219 */         if (!"http://www.w3.org/2000/xmlns/".equals(str1)) {
/*     */           
/* 221 */           sortedSet.add(attr);
/* 222 */         } else if (!"xml".equals(str2) || !"http://www.w3.org/XML/1998/namespace".equals(str3)) {
/*     */           
/* 224 */           Node node = paramNameSpaceSymbTable.addMappingAndRender(str2, str3, attr);
/*     */           
/* 226 */           if (node != null) {
/*     */             
/* 228 */             sortedSet.add((Attr)node);
/* 229 */             if (C14nHelper.namespaceIsRelative(attr)) {
/* 230 */               Object[] arrayOfObject = { paramElement.getTagName(), str2, attr.getNodeValue() };
/* 231 */               throw new CanonicalizationException("c14n.Canonicalizer.RelativeNamespace", arrayOfObject);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 240 */     if (this.firstCall) {
/*     */ 
/*     */       
/* 243 */       paramNameSpaceSymbTable.getUnrenderedNodes(sortedSet);
/*     */       
/* 245 */       this.xmlattrStack.getXmlnsAttr(sortedSet);
/* 246 */       this.firstCall = false;
/*     */     } 
/*     */     
/* 249 */     return sortedSet.iterator();
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
/*     */   protected Iterator<Attr> handleAttributes(Element paramElement, NameSpaceSymbTable paramNameSpaceSymbTable) throws CanonicalizationException {
/* 268 */     this.xmlattrStack.push(paramNameSpaceSymbTable.getLevel());
/* 269 */     boolean bool = (isVisibleDO(paramElement, paramNameSpaceSymbTable.getLevel()) == 1) ? true : false;
/* 270 */     SortedSet<Attr> sortedSet = this.result;
/* 271 */     sortedSet.clear();
/*     */     
/* 273 */     if (paramElement.hasAttributes()) {
/* 274 */       NamedNodeMap namedNodeMap = paramElement.getAttributes();
/* 275 */       int i = namedNodeMap.getLength();
/*     */       
/* 277 */       for (byte b = 0; b < i; b++) {
/* 278 */         Attr attr = (Attr)namedNodeMap.item(b);
/* 279 */         String str1 = attr.getNamespaceURI();
/* 280 */         String str2 = attr.getLocalName();
/* 281 */         String str3 = attr.getValue();
/*     */         
/* 283 */         if (!"http://www.w3.org/2000/xmlns/".equals(str1)) {
/*     */           
/* 285 */           if ("http://www.w3.org/XML/1998/namespace".equals(str1)) {
/* 286 */             this.xmlattrStack.addXmlnsAttr(attr);
/* 287 */           } else if (bool) {
/*     */             
/* 289 */             sortedSet.add(attr);
/*     */           } 
/* 291 */         } else if (!"xml".equals(str2) || !"http://www.w3.org/XML/1998/namespace".equals(str3)) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 296 */           if (isVisible(attr)) {
/* 297 */             if (bool || !paramNameSpaceSymbTable.removeMappingIfRender(str2))
/*     */             {
/* 299 */               Node node = paramNameSpaceSymbTable.addMappingAndRender(str2, str3, attr);
/* 300 */               if (node != null) {
/* 301 */                 sortedSet.add((Attr)node);
/* 302 */                 if (C14nHelper.namespaceIsRelative(attr)) {
/* 303 */                   Object[] arrayOfObject = { paramElement.getTagName(), str2, attr.getNodeValue() };
/* 304 */                   throw new CanonicalizationException("c14n.Canonicalizer.RelativeNamespace", arrayOfObject);
/*     */                 }
/*     */               
/*     */               }
/*     */             
/*     */             }
/*     */           
/* 311 */           } else if (bool && !"xmlns".equals(str2)) {
/* 312 */             paramNameSpaceSymbTable.removeMapping(str2);
/*     */           } else {
/* 314 */             paramNameSpaceSymbTable.addMapping(str2, str3, attr);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 320 */     if (bool) {
/*     */       
/* 322 */       Attr attr = paramElement.getAttributeNodeNS("http://www.w3.org/2000/xmlns/", "xmlns");
/* 323 */       Node node = null;
/* 324 */       if (attr == null) {
/*     */         
/* 326 */         node = paramNameSpaceSymbTable.getMapping("xmlns");
/* 327 */       } else if (!isVisible(attr)) {
/*     */ 
/*     */         
/* 330 */         node = paramNameSpaceSymbTable.addMappingAndRender("xmlns", "", 
/* 331 */             getNullNode(attr.getOwnerDocument()));
/*     */       } 
/*     */       
/* 334 */       if (node != null) {
/* 335 */         sortedSet.add((Attr)node);
/*     */       }
/*     */       
/* 338 */       this.xmlattrStack.getXmlnsAttr(sortedSet);
/* 339 */       paramNameSpaceSymbTable.getUnrenderedNodes(sortedSet);
/*     */     } 
/*     */     
/* 342 */     return sortedSet.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void circumventBugIfNeeded(XMLSignatureInput paramXMLSignatureInput) throws CanonicalizationException, ParserConfigurationException, IOException, SAXException {
/* 347 */     if (!paramXMLSignatureInput.isNeedsToBeExpanded()) {
/*     */       return;
/*     */     }
/* 350 */     Document document = null;
/* 351 */     if (paramXMLSignatureInput.getSubNode() != null) {
/* 352 */       document = XMLUtils.getOwnerDocument(paramXMLSignatureInput.getSubNode());
/*     */     } else {
/* 354 */       document = XMLUtils.getOwnerDocument(paramXMLSignatureInput.getNodeSet());
/*     */     } 
/* 356 */     XMLUtils.circumventBug2650(document);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleParent(Element paramElement, NameSpaceSymbTable paramNameSpaceSymbTable) {
/* 361 */     if (!paramElement.hasAttributes() && paramElement.getNamespaceURI() == null) {
/*     */       return;
/*     */     }
/* 364 */     this.xmlattrStack.push(-1);
/* 365 */     NamedNodeMap namedNodeMap = paramElement.getAttributes();
/* 366 */     int i = namedNodeMap.getLength();
/* 367 */     for (byte b = 0; b < i; b++) {
/* 368 */       Attr attr = (Attr)namedNodeMap.item(b);
/* 369 */       String str1 = attr.getLocalName();
/* 370 */       String str2 = attr.getNodeValue();
/*     */       
/* 372 */       if ("http://www.w3.org/2000/xmlns/".equals(attr.getNamespaceURI())) {
/* 373 */         if (!"xml".equals(str1) || !"http://www.w3.org/XML/1998/namespace".equals(str2)) {
/* 374 */           paramNameSpaceSymbTable.addMapping(str1, str2, attr);
/*     */         }
/* 376 */       } else if ("http://www.w3.org/XML/1998/namespace".equals(attr.getNamespaceURI())) {
/* 377 */         this.xmlattrStack.addXmlnsAttr(attr);
/*     */       } 
/*     */     } 
/* 380 */     if (paramElement.getNamespaceURI() != null) {
/* 381 */       String str3, str1 = paramElement.getPrefix();
/* 382 */       String str2 = paramElement.getNamespaceURI();
/*     */       
/* 384 */       if (str1 == null || str1.equals("")) {
/* 385 */         str1 = "xmlns";
/* 386 */         str3 = "xmlns";
/*     */       } else {
/* 388 */         str3 = "xmlns:" + str1;
/*     */       } 
/* 390 */       Attr attr = paramElement.getOwnerDocument().createAttributeNS("http://www.w3.org/2000/xmlns/", str3);
/* 391 */       attr.setValue(str2);
/* 392 */       paramNameSpaceSymbTable.addMapping(str1, str2, attr);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/c14n/implementations/Canonicalizer20010315.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */