/*     */ package com.sun.org.apache.xml.internal.security.transforms.params;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.TransformParam;
/*     */ import com.sun.org.apache.xml.internal.security.utils.ElementProxy;
/*     */ import com.sun.org.apache.xml.internal.security.utils.HelperNodeList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XPath2FilterContainer
/*     */   extends ElementProxy
/*     */   implements TransformParam
/*     */ {
/*     */   private static final String _ATT_FILTER = "Filter";
/*     */   private static final String _ATT_FILTER_VALUE_INTERSECT = "intersect";
/*     */   private static final String _ATT_FILTER_VALUE_SUBTRACT = "subtract";
/*     */   private static final String _ATT_FILTER_VALUE_UNION = "union";
/*     */   public static final String INTERSECT = "intersect";
/*     */   public static final String SUBTRACT = "subtract";
/*     */   public static final String UNION = "union";
/*     */   public static final String _TAG_XPATH2 = "XPath";
/*     */   public static final String XPathFilter2NS = "http://www.w3.org/2002/06/xmldsig-filter2";
/*     */   
/*     */   private XPath2FilterContainer() {}
/*     */   
/*     */   private XPath2FilterContainer(Document paramDocument, String paramString1, String paramString2) {
/*  91 */     super(paramDocument);
/*     */     
/*  93 */     this.constructionElement.setAttributeNS((String)null, "Filter", paramString2);
/*     */     
/*  95 */     this.constructionElement.appendChild(paramDocument.createTextNode(paramString1));
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
/*     */   private XPath2FilterContainer(Element paramElement, String paramString) throws XMLSecurityException {
/* 107 */     super(paramElement, paramString);
/*     */ 
/*     */     
/* 110 */     String str = this.constructionElement.getAttributeNS((String)null, "Filter");
/*     */     
/* 112 */     if (!str.equals("intersect") && 
/* 113 */       !str.equals("subtract") && 
/* 114 */       !str.equals("union")) {
/* 115 */       Object[] arrayOfObject = { "Filter", str, "intersect, subtract or union" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 122 */       throw new XMLSecurityException("attributeValueIllegal", arrayOfObject);
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
/*     */   public static XPath2FilterContainer newInstanceIntersect(Document paramDocument, String paramString) {
/* 136 */     return new XPath2FilterContainer(paramDocument, paramString, "intersect");
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
/*     */   public static XPath2FilterContainer newInstanceSubtract(Document paramDocument, String paramString) {
/* 148 */     return new XPath2FilterContainer(paramDocument, paramString, "subtract");
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
/*     */   public static XPath2FilterContainer newInstanceUnion(Document paramDocument, String paramString) {
/* 160 */     return new XPath2FilterContainer(paramDocument, paramString, "union");
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
/*     */   public static NodeList newInstances(Document paramDocument, String[][] paramArrayOfString) {
/* 172 */     HelperNodeList helperNodeList = new HelperNodeList();
/*     */     
/* 174 */     XMLUtils.addReturnToElement(paramDocument, helperNodeList);
/*     */     
/* 176 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 177 */       String str1 = paramArrayOfString[b][0];
/* 178 */       String str2 = paramArrayOfString[b][1];
/*     */       
/* 180 */       if (!str1.equals("intersect") && 
/* 181 */         !str1.equals("subtract") && 
/* 182 */         !str1.equals("union")) {
/* 183 */         throw new IllegalArgumentException("The type(" + b + ")=\"" + str1 + "\" is illegal");
/*     */       }
/*     */ 
/*     */       
/* 187 */       XPath2FilterContainer xPath2FilterContainer = new XPath2FilterContainer(paramDocument, str2, str1);
/*     */       
/* 189 */       helperNodeList.appendChild(xPath2FilterContainer.getElement());
/* 190 */       XMLUtils.addReturnToElement(paramDocument, helperNodeList);
/*     */     } 
/*     */     
/* 193 */     return helperNodeList;
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
/*     */   public static XPath2FilterContainer newInstance(Element paramElement, String paramString) throws XMLSecurityException {
/* 208 */     return new XPath2FilterContainer(paramElement, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIntersect() {
/* 217 */     return this.constructionElement.getAttributeNS((String)null, "Filter")
/*     */       
/* 219 */       .equals("intersect");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSubtract() {
/* 228 */     return this.constructionElement.getAttributeNS((String)null, "Filter")
/*     */       
/* 230 */       .equals("subtract");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUnion() {
/* 239 */     return this.constructionElement.getAttributeNS((String)null, "Filter")
/*     */       
/* 241 */       .equals("union");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getXPathFilterStr() {
/* 250 */     return getTextFromTextChild();
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
/*     */   public Node getXPathFilterTextNode() {
/* 263 */     NodeList nodeList = this.constructionElement.getChildNodes();
/* 264 */     int i = nodeList.getLength();
/*     */     
/* 266 */     for (byte b = 0; b < i; b++) {
/* 267 */       if (nodeList.item(b).getNodeType() == 3) {
/* 268 */         return nodeList.item(b);
/*     */       }
/*     */     } 
/*     */     
/* 272 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getBaseLocalName() {
/* 281 */     return "XPath";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getBaseNamespace() {
/* 290 */     return "http://www.w3.org/2002/06/xmldsig-filter2";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/transforms/params/XPath2FilterContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */