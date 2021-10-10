/*     */ package com.sun.org.apache.xml.internal.security.utils;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.Text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ElementProxy
/*     */ {
/*  45 */   protected static final Logger log = Logger.getLogger(ElementProxy.class.getName());
/*     */ 
/*     */   
/*  48 */   protected Element constructionElement = null;
/*     */ 
/*     */   
/*  51 */   protected String baseURI = null;
/*     */ 
/*     */   
/*  54 */   protected Document doc = null;
/*     */ 
/*     */   
/*  57 */   private static Map<String, String> prefixMappings = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ElementProxy() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ElementProxy(Document paramDocument) {
/*  72 */     if (paramDocument == null) {
/*  73 */       throw new RuntimeException("Document is null");
/*     */     }
/*     */     
/*  76 */     this.doc = paramDocument;
/*  77 */     this
/*  78 */       .constructionElement = createElementForFamilyLocal(this.doc, getBaseNamespace(), getBaseLocalName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ElementProxy(Element paramElement, String paramString) throws XMLSecurityException {
/*  89 */     if (paramElement == null) {
/*  90 */       throw new XMLSecurityException("ElementProxy.nullElement");
/*     */     }
/*     */     
/*  93 */     if (log.isLoggable(Level.FINE)) {
/*  94 */       log.log(Level.FINE, "setElement(\"" + paramElement.getTagName() + "\", \"" + paramString + "\")");
/*     */     }
/*     */     
/*  97 */     this.doc = paramElement.getOwnerDocument();
/*  98 */     this.constructionElement = paramElement;
/*  99 */     this.baseURI = paramString;
/*     */     
/* 101 */     guaranteeThatElementInCorrectSpace();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getBaseNamespace();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getBaseLocalName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Element createElementForFamilyLocal(Document paramDocument, String paramString1, String paramString2) {
/* 122 */     Element element = null;
/* 123 */     if (paramString1 == null) {
/* 124 */       element = paramDocument.createElementNS(null, paramString2);
/*     */     } else {
/* 126 */       String str1 = getBaseNamespace();
/* 127 */       String str2 = getDefaultPrefix(str1);
/* 128 */       if (str2 == null || str2.length() == 0) {
/* 129 */         element = paramDocument.createElementNS(paramString1, paramString2);
/* 130 */         element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", paramString1);
/*     */       } else {
/* 132 */         element = paramDocument.createElementNS(paramString1, str2 + ":" + paramString2);
/* 133 */         element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + str2, paramString1);
/*     */       } 
/*     */     } 
/* 136 */     return element;
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
/*     */   public static Element createElementForFamily(Document paramDocument, String paramString1, String paramString2) {
/* 153 */     Element element = null;
/* 154 */     String str = getDefaultPrefix(paramString1);
/*     */     
/* 156 */     if (paramString1 == null) {
/* 157 */       element = paramDocument.createElementNS(null, paramString2);
/*     */     }
/* 159 */     else if (str == null || str.length() == 0) {
/* 160 */       element = paramDocument.createElementNS(paramString1, paramString2);
/* 161 */       element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", paramString1);
/*     */     } else {
/* 163 */       element = paramDocument.createElementNS(paramString1, str + ":" + paramString2);
/* 164 */       element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + str, paramString1);
/*     */     } 
/*     */ 
/*     */     
/* 168 */     return element;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setElement(Element paramElement, String paramString) throws XMLSecurityException {
/* 179 */     if (paramElement == null) {
/* 180 */       throw new XMLSecurityException("ElementProxy.nullElement");
/*     */     }
/*     */     
/* 183 */     if (log.isLoggable(Level.FINE)) {
/* 184 */       log.log(Level.FINE, "setElement(" + paramElement.getTagName() + ", \"" + paramString + "\"");
/*     */     }
/*     */     
/* 187 */     this.doc = paramElement.getOwnerDocument();
/* 188 */     this.constructionElement = paramElement;
/* 189 */     this.baseURI = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Element getElement() {
/* 199 */     return this.constructionElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final NodeList getElementPlusReturns() {
/* 209 */     HelperNodeList helperNodeList = new HelperNodeList();
/*     */     
/* 211 */     helperNodeList.appendChild(this.doc.createTextNode("\n"));
/* 212 */     helperNodeList.appendChild(getElement());
/* 213 */     helperNodeList.appendChild(this.doc.createTextNode("\n"));
/*     */     
/* 215 */     return helperNodeList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Document getDocument() {
/* 224 */     return this.doc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBaseURI() {
/* 233 */     return this.baseURI;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void guaranteeThatElementInCorrectSpace() throws XMLSecurityException {
/* 243 */     String str1 = getBaseLocalName();
/* 244 */     String str2 = getBaseNamespace();
/*     */     
/* 246 */     String str3 = this.constructionElement.getLocalName();
/* 247 */     String str4 = this.constructionElement.getNamespaceURI();
/*     */     
/* 249 */     if (!str2.equals(str4) && 
/* 250 */       !str1.equals(str3)) {
/* 251 */       Object[] arrayOfObject = { str4 + ":" + str3, str2 + ":" + str1 };
/*     */       
/* 253 */       throw new XMLSecurityException("xml.WrongElement", arrayOfObject);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBigIntegerElement(BigInteger paramBigInteger, String paramString) {
/* 264 */     if (paramBigInteger != null) {
/* 265 */       Element element = XMLUtils.createElementInSignatureSpace(this.doc, paramString);
/*     */       
/* 267 */       Base64.fillElementWithBigInteger(element, paramBigInteger);
/* 268 */       this.constructionElement.appendChild(element);
/* 269 */       XMLUtils.addReturnToElement(this.constructionElement);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBase64Element(byte[] paramArrayOfbyte, String paramString) {
/* 280 */     if (paramArrayOfbyte != null) {
/* 281 */       Element element = Base64.encodeToElement(this.doc, paramString, paramArrayOfbyte);
/*     */       
/* 283 */       this.constructionElement.appendChild(element);
/* 284 */       if (!XMLUtils.ignoreLineBreaks()) {
/* 285 */         this.constructionElement.appendChild(this.doc.createTextNode("\n"));
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
/*     */   public void addTextElement(String paramString1, String paramString2) {
/* 297 */     Element element = XMLUtils.createElementInSignatureSpace(this.doc, paramString2);
/* 298 */     Text text = this.doc.createTextNode(paramString1);
/*     */     
/* 300 */     element.appendChild(text);
/* 301 */     this.constructionElement.appendChild(element);
/* 302 */     XMLUtils.addReturnToElement(this.constructionElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBase64Text(byte[] paramArrayOfbyte) {
/* 311 */     if (paramArrayOfbyte != null) {
/*     */ 
/*     */       
/* 314 */       Text text = XMLUtils.ignoreLineBreaks() ? this.doc.createTextNode(Base64.encode(paramArrayOfbyte)) : this.doc.createTextNode("\n" + Base64.encode(paramArrayOfbyte) + "\n");
/* 315 */       this.constructionElement.appendChild(text);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addText(String paramString) {
/* 325 */     if (paramString != null) {
/* 326 */       Text text = this.doc.createTextNode(paramString);
/*     */       
/* 328 */       this.constructionElement.appendChild(text);
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
/*     */   public BigInteger getBigIntegerFromChildElement(String paramString1, String paramString2) throws Base64DecodingException {
/* 343 */     return Base64.decodeBigIntegerFromText(
/* 344 */         XMLUtils.selectNodeText(this.constructionElement
/* 345 */           .getFirstChild(), paramString2, paramString1, 0));
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
/*     */   @Deprecated
/*     */   public byte[] getBytesFromChildElement(String paramString1, String paramString2) throws XMLSecurityException {
/* 362 */     Element element = XMLUtils.selectNode(this.constructionElement
/* 363 */         .getFirstChild(), paramString2, paramString1, 0);
/*     */ 
/*     */     
/* 366 */     return Base64.decode(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTextFromChildElement(String paramString1, String paramString2) {
/* 377 */     return XMLUtils.selectNode(this.constructionElement
/* 378 */         .getFirstChild(), paramString2, paramString1, 0)
/*     */ 
/*     */       
/* 381 */       .getTextContent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getBytesFromTextChild() throws XMLSecurityException {
/* 391 */     return Base64.decode(XMLUtils.getFullTextChildrenFromElement(this.constructionElement));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTextFromTextChild() {
/* 401 */     return XMLUtils.getFullTextChildrenFromElement(this.constructionElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int length(String paramString1, String paramString2) {
/* 412 */     byte b = 0;
/* 413 */     Node node = this.constructionElement.getFirstChild();
/* 414 */     while (node != null) {
/* 415 */       if (paramString2.equals(node.getLocalName()) && paramString1
/* 416 */         .equals(node.getNamespaceURI())) {
/* 417 */         b++;
/*     */       }
/* 419 */       node = node.getNextSibling();
/*     */     } 
/* 421 */     return b;
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
/*     */   public void setXPathNamespaceContext(String paramString1, String paramString2) throws XMLSecurityException {
/*     */     String str;
/* 441 */     if (paramString1 == null || paramString1.length() == 0)
/* 442 */       throw new XMLSecurityException("defaultNamespaceCannotBeSetHere"); 
/* 443 */     if (paramString1.equals("xmlns"))
/* 444 */       throw new XMLSecurityException("defaultNamespaceCannotBeSetHere"); 
/* 445 */     if (paramString1.startsWith("xmlns:")) {
/* 446 */       str = paramString1;
/*     */     } else {
/* 448 */       str = "xmlns:" + paramString1;
/*     */     } 
/*     */     
/* 451 */     Attr attr = this.constructionElement.getAttributeNodeNS("http://www.w3.org/2000/xmlns/", str);
/*     */     
/* 453 */     if (attr != null) {
/* 454 */       if (!attr.getNodeValue().equals(paramString2)) {
/* 455 */         Object[] arrayOfObject = { str, this.constructionElement.getAttributeNS((String)null, str) };
/*     */         
/* 457 */         throw new XMLSecurityException("namespacePrefixAlreadyUsedByOtherURI", arrayOfObject);
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 462 */     this.constructionElement.setAttributeNS("http://www.w3.org/2000/xmlns/", str, paramString2);
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
/*     */   public static void setDefaultPrefix(String paramString1, String paramString2) throws XMLSecurityException {
/* 476 */     JavaUtils.checkRegisterPermission();
/* 477 */     if (prefixMappings.containsValue(paramString2)) {
/* 478 */       String str = prefixMappings.get(paramString1);
/* 479 */       if (!str.equals(paramString2)) {
/* 480 */         Object[] arrayOfObject = { paramString2, paramString1, str };
/*     */         
/* 482 */         throw new XMLSecurityException("prefix.AlreadyAssigned", arrayOfObject);
/*     */       } 
/*     */     } 
/*     */     
/* 486 */     if ("http://www.w3.org/2000/09/xmldsig#".equals(paramString1)) {
/* 487 */       XMLUtils.setDsPrefix(paramString2);
/*     */     }
/* 489 */     if ("http://www.w3.org/2001/04/xmlenc#".equals(paramString1)) {
/* 490 */       XMLUtils.setXencPrefix(paramString2);
/*     */     }
/* 492 */     prefixMappings.put(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerDefaultPrefixes() throws XMLSecurityException {
/* 499 */     setDefaultPrefix("http://www.w3.org/2000/09/xmldsig#", "ds");
/* 500 */     setDefaultPrefix("http://www.w3.org/2001/04/xmlenc#", "xenc");
/* 501 */     setDefaultPrefix("http://www.w3.org/2009/xmlenc11#", "xenc11");
/* 502 */     setDefaultPrefix("http://www.xmlsecurity.org/experimental#", "experimental");
/* 503 */     setDefaultPrefix("http://www.w3.org/2002/04/xmldsig-filter2", "dsig-xpath-old");
/* 504 */     setDefaultPrefix("http://www.w3.org/2002/06/xmldsig-filter2", "dsig-xpath");
/* 505 */     setDefaultPrefix("http://www.w3.org/2001/10/xml-exc-c14n#", "ec");
/* 506 */     setDefaultPrefix("http://www.nue.et-inf.uni-siegen.de/~geuer-pollmann/#xpathFilter", "xx");
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
/*     */   public static String getDefaultPrefix(String paramString) {
/* 518 */     return prefixMappings.get(paramString);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/utils/ElementProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */