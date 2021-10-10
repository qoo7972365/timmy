/*     */ package com.sun.org.apache.xml.internal.security.transforms.params;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.TransformParam;
/*     */ import com.sun.org.apache.xml.internal.security.utils.ElementProxy;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import org.w3c.dom.Document;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XPathFilterCHGPContainer
/*     */   extends ElementProxy
/*     */   implements TransformParam
/*     */ {
/*     */   public static final String TRANSFORM_XPATHFILTERCHGP = "http://www.nue.et-inf.uni-siegen.de/~geuer-pollmann/#xpathFilter";
/*     */   private static final String _TAG_INCLUDE_BUT_SEARCH = "IncludeButSearch";
/*     */   private static final String _TAG_EXCLUDE_BUT_SEARCH = "ExcludeButSearch";
/*     */   private static final String _TAG_EXCLUDE = "Exclude";
/*     */   public static final String _TAG_XPATHCHGP = "XPathAlternative";
/*     */   public static final String _ATT_INCLUDESLASH = "IncludeSlashPolicy";
/*     */   public static final boolean IncludeSlash = true;
/*     */   public static final boolean ExcludeSlash = false;
/*     */   
/*     */   private XPathFilterCHGPContainer() {}
/*     */   
/*     */   private XPathFilterCHGPContainer(Document paramDocument, boolean paramBoolean, String paramString1, String paramString2, String paramString3) {
/*  87 */     super(paramDocument);
/*     */     
/*  89 */     if (paramBoolean) {
/*  90 */       this.constructionElement.setAttributeNS((String)null, "IncludeSlashPolicy", "true");
/*     */     }
/*     */     else {
/*     */       
/*  94 */       this.constructionElement.setAttributeNS((String)null, "IncludeSlashPolicy", "false");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  99 */     if (paramString1 != null && paramString1.trim().length() > 0) {
/*     */       
/* 101 */       Element element = ElementProxy.createElementForFamily(paramDocument, 
/* 102 */           getBaseNamespace(), "IncludeButSearch");
/*     */ 
/*     */       
/* 105 */       element.appendChild(this.doc
/* 106 */           .createTextNode(indentXPathText(paramString1)));
/*     */       
/* 108 */       XMLUtils.addReturnToElement(this.constructionElement);
/* 109 */       this.constructionElement.appendChild(element);
/*     */     } 
/*     */     
/* 112 */     if (paramString2 != null && paramString2.trim().length() > 0) {
/*     */       
/* 114 */       Element element = ElementProxy.createElementForFamily(paramDocument, 
/* 115 */           getBaseNamespace(), "ExcludeButSearch");
/*     */ 
/*     */       
/* 118 */       element.appendChild(this.doc
/* 119 */           .createTextNode(indentXPathText(paramString2)));
/*     */       
/* 121 */       XMLUtils.addReturnToElement(this.constructionElement);
/* 122 */       this.constructionElement.appendChild(element);
/*     */     } 
/*     */     
/* 125 */     if (paramString3 != null && paramString3.trim().length() > 0) {
/*     */       
/* 127 */       Element element = ElementProxy.createElementForFamily(paramDocument, 
/* 128 */           getBaseNamespace(), "Exclude");
/*     */       
/* 130 */       element.appendChild(this.doc.createTextNode(indentXPathText(paramString3)));
/* 131 */       XMLUtils.addReturnToElement(this.constructionElement);
/* 132 */       this.constructionElement.appendChild(element);
/*     */     } 
/*     */     
/* 135 */     XMLUtils.addReturnToElement(this.constructionElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String indentXPathText(String paramString) {
/* 145 */     if (paramString.length() > 2 && !Character.isWhitespace(paramString.charAt(0))) {
/* 146 */       return "\n" + paramString + "\n";
/*     */     }
/* 148 */     return paramString;
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
/*     */   private XPathFilterCHGPContainer(Element paramElement, String paramString) throws XMLSecurityException {
/* 160 */     super(paramElement, paramString);
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
/*     */   public static XPathFilterCHGPContainer getInstance(Document paramDocument, boolean paramBoolean, String paramString1, String paramString2, String paramString3) {
/* 177 */     return new XPathFilterCHGPContainer(paramDocument, paramBoolean, paramString1, paramString2, paramString3);
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
/*     */   public static XPathFilterCHGPContainer getInstance(Element paramElement, String paramString) throws XMLSecurityException {
/* 193 */     return new XPathFilterCHGPContainer(paramElement, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getXStr(String paramString) {
/* 203 */     if (length(getBaseNamespace(), paramString) != 1) {
/* 204 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 208 */     Element element = XMLUtils.selectNode(this.constructionElement
/* 209 */         .getFirstChild(), getBaseNamespace(), paramString, 0);
/*     */ 
/*     */     
/* 212 */     return XMLUtils.getFullTextChildrenFromElement(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIncludeButSearch() {
/* 221 */     return getXStr("IncludeButSearch");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExcludeButSearch() {
/* 230 */     return getXStr("ExcludeButSearch");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExclude() {
/* 239 */     return getXStr("Exclude");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIncludeSlashPolicy() {
/* 248 */     return this.constructionElement.getAttributeNS((String)null, "IncludeSlashPolicy")
/* 249 */       .equals("true");
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
/*     */   private Node getHereContextNode(String paramString) {
/* 263 */     if (length(getBaseNamespace(), paramString) != 1) {
/* 264 */       return null;
/*     */     }
/*     */     
/* 267 */     return XMLUtils.selectNodeText(this.constructionElement
/* 268 */         .getFirstChild(), getBaseNamespace(), paramString, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node getHereContextNodeIncludeButSearch() {
/* 278 */     return getHereContextNode("IncludeButSearch");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node getHereContextNodeExcludeButSearch() {
/* 287 */     return getHereContextNode("ExcludeButSearch");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node getHereContextNodeExclude() {
/* 296 */     return getHereContextNode("Exclude");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getBaseLocalName() {
/* 305 */     return "XPathAlternative";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getBaseNamespace() {
/* 314 */     return "http://www.nue.et-inf.uni-siegen.de/~geuer-pollmann/#xpathFilter";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/transforms/params/XPathFilterCHGPContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */