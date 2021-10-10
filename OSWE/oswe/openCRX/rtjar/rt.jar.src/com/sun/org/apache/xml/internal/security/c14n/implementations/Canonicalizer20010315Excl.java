/*     */ package com.sun.org.apache.xml.internal.security.c14n.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
/*     */ import com.sun.org.apache.xml.internal.security.c14n.helper.C14nHelper;
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.params.InclusiveNamespaces;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Canonicalizer20010315Excl
/*     */   extends CanonicalizerBase
/*     */ {
/*     */   private static final String XML_LANG_URI = "http://www.w3.org/XML/1998/namespace";
/*     */   private static final String XMLNS_URI = "http://www.w3.org/2000/xmlns/";
/*     */   private SortedSet<String> inclusiveNSSet;
/*  72 */   private final SortedSet<Attr> result = new TreeSet<>(COMPARE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Canonicalizer20010315Excl(boolean paramBoolean) {
/*  80 */     super(paramBoolean);
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
/*     */   public byte[] engineCanonicalizeSubTree(Node paramNode) throws CanonicalizationException {
/*  92 */     return engineCanonicalizeSubTree(paramNode, "", (Node)null);
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
/* 106 */     return engineCanonicalizeSubTree(paramNode, paramString, (Node)null);
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
/*     */   public byte[] engineCanonicalizeSubTree(Node paramNode1, String paramString, Node paramNode2) throws CanonicalizationException {
/* 120 */     this.inclusiveNSSet = InclusiveNamespaces.prefixStr2Set(paramString);
/* 121 */     return engineCanonicalizeSubTree(paramNode1, paramNode2);
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
/*     */   public byte[] engineCanonicalize(XMLSignatureInput paramXMLSignatureInput, String paramString) throws CanonicalizationException {
/* 134 */     this.inclusiveNSSet = InclusiveNamespaces.prefixStr2Set(paramString);
/* 135 */     return engineCanonicalize(paramXMLSignatureInput);
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
/*     */   public byte[] engineCanonicalizeXPathNodeSet(Set<Node> paramSet, String paramString) throws CanonicalizationException {
/* 148 */     this.inclusiveNSSet = InclusiveNamespaces.prefixStr2Set(paramString);
/* 149 */     return engineCanonicalizeXPathNodeSet(paramSet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Iterator<Attr> handleAttributesSubtree(Element paramElement, NameSpaceSymbTable paramNameSpaceSymbTable) throws CanonicalizationException {
/* 156 */     SortedSet<Attr> sortedSet = this.result;
/* 157 */     sortedSet.clear();
/*     */ 
/*     */ 
/*     */     
/* 161 */     TreeSet<String> treeSet = new TreeSet();
/* 162 */     if (this.inclusiveNSSet != null && !this.inclusiveNSSet.isEmpty()) {
/* 163 */       treeSet.addAll(this.inclusiveNSSet);
/*     */     }
/*     */     
/* 166 */     if (paramElement.hasAttributes()) {
/* 167 */       NamedNodeMap namedNodeMap = paramElement.getAttributes();
/* 168 */       int i = namedNodeMap.getLength();
/* 169 */       for (byte b = 0; b < i; b++) {
/* 170 */         Attr attr = (Attr)namedNodeMap.item(b);
/* 171 */         String str1 = attr.getLocalName();
/* 172 */         String str2 = attr.getNodeValue();
/*     */         
/* 174 */         if (!"http://www.w3.org/2000/xmlns/".equals(attr.getNamespaceURI())) {
/*     */ 
/*     */ 
/*     */           
/* 178 */           String str3 = attr.getPrefix();
/* 179 */           if (str3 != null && !str3.equals("xml") && !str3.equals("xmlns")) {
/* 180 */             treeSet.add(str3);
/*     */           }
/*     */           
/* 183 */           sortedSet.add(attr);
/* 184 */         } else if ((!"xml".equals(str1) || !"http://www.w3.org/XML/1998/namespace".equals(str2)) && paramNameSpaceSymbTable
/* 185 */           .addMapping(str1, str2, attr) && 
/* 186 */           C14nHelper.namespaceIsRelative(str2)) {
/*     */ 
/*     */           
/* 189 */           Object[] arrayOfObject = { paramElement.getTagName(), str1, attr.getNodeValue() };
/* 190 */           throw new CanonicalizationException("c14n.Canonicalizer.RelativeNamespace", arrayOfObject);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 196 */     String str = null;
/* 197 */     if (paramElement.getNamespaceURI() != null && paramElement
/* 198 */       .getPrefix() != null && paramElement.getPrefix().length() != 0) {
/* 199 */       str = paramElement.getPrefix();
/*     */     } else {
/* 201 */       str = "xmlns";
/*     */     } 
/* 203 */     treeSet.add(str);
/*     */     
/* 205 */     for (String str1 : treeSet) {
/* 206 */       Attr attr = paramNameSpaceSymbTable.getMapping(str1);
/* 207 */       if (attr != null) {
/* 208 */         sortedSet.add(attr);
/*     */       }
/*     */     } 
/*     */     
/* 212 */     return sortedSet.iterator();
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
/*     */   protected final Iterator<Attr> handleAttributes(Element paramElement, NameSpaceSymbTable paramNameSpaceSymbTable) throws CanonicalizationException {
/* 224 */     SortedSet<Attr> sortedSet = this.result;
/* 225 */     sortedSet.clear();
/*     */ 
/*     */ 
/*     */     
/* 229 */     TreeSet<String> treeSet = null;
/*     */     
/* 231 */     boolean bool = (isVisibleDO(paramElement, paramNameSpaceSymbTable.getLevel()) == 1) ? true : false;
/* 232 */     if (bool) {
/* 233 */       treeSet = new TreeSet();
/* 234 */       if (this.inclusiveNSSet != null && !this.inclusiveNSSet.isEmpty()) {
/* 235 */         treeSet.addAll(this.inclusiveNSSet);
/*     */       }
/*     */     } 
/*     */     
/* 239 */     if (paramElement.hasAttributes()) {
/* 240 */       NamedNodeMap namedNodeMap = paramElement.getAttributes();
/* 241 */       int i = namedNodeMap.getLength();
/* 242 */       for (byte b = 0; b < i; b++) {
/* 243 */         Attr attr = (Attr)namedNodeMap.item(b);
/*     */         
/* 245 */         String str1 = attr.getLocalName();
/* 246 */         String str2 = attr.getNodeValue();
/*     */         
/* 248 */         if (!"http://www.w3.org/2000/xmlns/".equals(attr.getNamespaceURI())) {
/* 249 */           if (isVisible(attr) && bool) {
/*     */ 
/*     */             
/* 252 */             String str = attr.getPrefix();
/* 253 */             if (str != null && !str.equals("xml") && !str.equals("xmlns")) {
/* 254 */               treeSet.add(str);
/*     */             }
/*     */             
/* 257 */             sortedSet.add(attr);
/*     */           } 
/* 259 */         } else if (bool && !isVisible(attr) && !"xmlns".equals(str1)) {
/* 260 */           paramNameSpaceSymbTable.removeMappingIfNotRender(str1);
/*     */         } else {
/* 262 */           if (!bool && isVisible(attr) && this.inclusiveNSSet
/* 263 */             .contains(str1) && 
/* 264 */             !paramNameSpaceSymbTable.removeMappingIfRender(str1)) {
/* 265 */             Node node = paramNameSpaceSymbTable.addMappingAndRender(str1, str2, attr);
/* 266 */             if (node != null) {
/* 267 */               sortedSet.add((Attr)node);
/* 268 */               if (C14nHelper.namespaceIsRelative(attr)) {
/* 269 */                 Object[] arrayOfObject = { paramElement.getTagName(), str1, attr.getNodeValue() };
/* 270 */                 throw new CanonicalizationException("c14n.Canonicalizer.RelativeNamespace", arrayOfObject);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 277 */           if (paramNameSpaceSymbTable.addMapping(str1, str2, attr) && 
/* 278 */             C14nHelper.namespaceIsRelative(str2)) {
/*     */             
/* 280 */             Object[] arrayOfObject = { paramElement.getTagName(), str1, attr.getNodeValue() };
/* 281 */             throw new CanonicalizationException("c14n.Canonicalizer.RelativeNamespace", arrayOfObject);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 289 */     if (bool) {
/*     */       
/* 291 */       Attr attr = paramElement.getAttributeNodeNS("http://www.w3.org/2000/xmlns/", "xmlns");
/* 292 */       if (attr != null && !isVisible(attr))
/*     */       {
/*     */         
/* 295 */         paramNameSpaceSymbTable.addMapping("xmlns", "", getNullNode(attr.getOwnerDocument()));
/*     */       }
/*     */       
/* 298 */       String str = null;
/* 299 */       if (paramElement.getNamespaceURI() != null && paramElement
/* 300 */         .getPrefix() != null && paramElement.getPrefix().length() != 0) {
/* 301 */         str = paramElement.getPrefix();
/*     */       } else {
/* 303 */         str = "xmlns";
/*     */       } 
/* 305 */       treeSet.add(str);
/*     */       
/* 307 */       for (String str1 : treeSet) {
/* 308 */         Attr attr1 = paramNameSpaceSymbTable.getMapping(str1);
/* 309 */         if (attr1 != null) {
/* 310 */           sortedSet.add(attr1);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 315 */     return sortedSet.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void circumventBugIfNeeded(XMLSignatureInput paramXMLSignatureInput) throws CanonicalizationException, ParserConfigurationException, IOException, SAXException {
/* 321 */     if (!paramXMLSignatureInput.isNeedsToBeExpanded() || this.inclusiveNSSet.isEmpty() || this.inclusiveNSSet.isEmpty()) {
/*     */       return;
/*     */     }
/* 324 */     Document document = null;
/* 325 */     if (paramXMLSignatureInput.getSubNode() != null) {
/* 326 */       document = XMLUtils.getOwnerDocument(paramXMLSignatureInput.getSubNode());
/*     */     } else {
/* 328 */       document = XMLUtils.getOwnerDocument(paramXMLSignatureInput.getNodeSet());
/*     */     } 
/* 330 */     XMLUtils.circumventBug2650(document);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/c14n/implementations/Canonicalizer20010315Excl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */