/*     */ package com.sun.org.apache.xml.internal.security.transforms.params;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.TransformParam;
/*     */ import com.sun.org.apache.xml.internal.security.utils.ElementProxy;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XPath2FilterContainer04
/*     */   extends ElementProxy
/*     */   implements TransformParam
/*     */ {
/*     */   private static final String _ATT_FILTER = "Filter";
/*     */   private static final String _ATT_FILTER_VALUE_INTERSECT = "intersect";
/*     */   private static final String _ATT_FILTER_VALUE_SUBTRACT = "subtract";
/*     */   private static final String _ATT_FILTER_VALUE_UNION = "union";
/*     */   public static final String _TAG_XPATH2 = "XPath";
/*     */   public static final String XPathFilter2NS = "http://www.w3.org/2002/04/xmldsig-filter2";
/*     */   
/*     */   private XPath2FilterContainer04() {}
/*     */   
/*     */   private XPath2FilterContainer04(Document paramDocument, String paramString1, String paramString2) {
/*  79 */     super(paramDocument);
/*     */     
/*  81 */     this.constructionElement.setAttributeNS((String)null, "Filter", paramString2);
/*     */ 
/*     */     
/*  84 */     if (paramString1.length() > 2 && 
/*  85 */       !Character.isWhitespace(paramString1.charAt(0))) {
/*  86 */       XMLUtils.addReturnToElement(this.constructionElement);
/*  87 */       this.constructionElement.appendChild(paramDocument.createTextNode(paramString1));
/*  88 */       XMLUtils.addReturnToElement(this.constructionElement);
/*     */     } else {
/*  90 */       this.constructionElement.appendChild(paramDocument.createTextNode(paramString1));
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
/*     */   private XPath2FilterContainer04(Element paramElement, String paramString) throws XMLSecurityException {
/* 104 */     super(paramElement, paramString);
/*     */ 
/*     */     
/* 107 */     String str = this.constructionElement.getAttributeNS((String)null, "Filter");
/*     */     
/* 109 */     if (!str.equals("intersect") && 
/* 110 */       !str.equals("subtract") && 
/* 111 */       !str.equals("union")) {
/* 112 */       Object[] arrayOfObject = { "Filter", str, "intersect, subtract or union" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 119 */       throw new XMLSecurityException("attributeValueIllegal", arrayOfObject);
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
/*     */   public static XPath2FilterContainer04 newInstanceIntersect(Document paramDocument, String paramString) {
/* 133 */     return new XPath2FilterContainer04(paramDocument, paramString, "intersect");
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
/*     */   public static XPath2FilterContainer04 newInstanceSubtract(Document paramDocument, String paramString) {
/* 147 */     return new XPath2FilterContainer04(paramDocument, paramString, "subtract");
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
/*     */   public static XPath2FilterContainer04 newInstanceUnion(Document paramDocument, String paramString) {
/* 161 */     return new XPath2FilterContainer04(paramDocument, paramString, "union");
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
/*     */   public static XPath2FilterContainer04 newInstance(Element paramElement, String paramString) throws XMLSecurityException {
/* 177 */     return new XPath2FilterContainer04(paramElement, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIntersect() {
/* 186 */     return this.constructionElement.getAttributeNS((String)null, "Filter")
/*     */       
/* 188 */       .equals("intersect");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSubtract() {
/* 197 */     return this.constructionElement.getAttributeNS((String)null, "Filter")
/*     */       
/* 199 */       .equals("subtract");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUnion() {
/* 208 */     return this.constructionElement.getAttributeNS((String)null, "Filter")
/*     */       
/* 210 */       .equals("union");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getXPathFilterStr() {
/* 219 */     return getTextFromTextChild();
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
/*     */   public Node getXPathFilterTextNode() {
/* 231 */     NodeList nodeList = this.constructionElement.getChildNodes();
/* 232 */     int i = nodeList.getLength();
/*     */     
/* 234 */     for (byte b = 0; b < i; b++) {
/* 235 */       if (nodeList.item(b).getNodeType() == 3) {
/* 236 */         return nodeList.item(b);
/*     */       }
/*     */     } 
/*     */     
/* 240 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getBaseLocalName() {
/* 245 */     return "XPath";
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getBaseNamespace() {
/* 250 */     return "http://www.w3.org/2002/04/xmldsig-filter2";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/transforms/params/XPath2FilterContainer04.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */