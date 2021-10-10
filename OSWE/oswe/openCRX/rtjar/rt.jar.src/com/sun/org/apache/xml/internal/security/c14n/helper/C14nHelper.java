/*     */ package com.sun.org.apache.xml.internal.security.c14n.helper;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class C14nHelper
/*     */ {
/*     */   public static boolean namespaceIsRelative(Attr paramAttr) {
/*  53 */     return !namespaceIsAbsolute(paramAttr);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean namespaceIsRelative(String paramString) {
/*  63 */     return !namespaceIsAbsolute(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean namespaceIsAbsolute(Attr paramAttr) {
/*  73 */     return namespaceIsAbsolute(paramAttr.getValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean namespaceIsAbsolute(String paramString) {
/*  84 */     if (paramString.length() == 0) {
/*  85 */       return true;
/*     */     }
/*  87 */     return (paramString.indexOf(':') > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertNotRelativeNS(Attr paramAttr) throws CanonicalizationException {
/*  98 */     if (paramAttr == null) {
/*     */       return;
/*     */     }
/*     */     
/* 102 */     String str = paramAttr.getNodeName();
/* 103 */     boolean bool1 = str.equals("xmlns");
/* 104 */     boolean bool2 = str.startsWith("xmlns:");
/*     */     
/* 106 */     if ((bool1 || bool2) && namespaceIsRelative(paramAttr)) {
/* 107 */       String str1 = paramAttr.getOwnerElement().getTagName();
/* 108 */       String str2 = paramAttr.getValue();
/* 109 */       Object[] arrayOfObject = { str1, str, str2 };
/*     */       
/* 111 */       throw new CanonicalizationException("c14n.Canonicalizer.RelativeNamespace", arrayOfObject);
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
/*     */   public static void checkTraversability(Document paramDocument) throws CanonicalizationException {
/* 126 */     if (!paramDocument.isSupported("Traversal", "2.0")) {
/* 127 */       Object[] arrayOfObject = { paramDocument.getImplementation().getClass().getName() };
/*     */       
/* 129 */       throw new CanonicalizationException("c14n.Canonicalizer.TraversalNotSupported", arrayOfObject);
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
/*     */   public static void checkForRelativeNamespace(Element paramElement) throws CanonicalizationException {
/* 145 */     if (paramElement != null) {
/* 146 */       NamedNodeMap namedNodeMap = paramElement.getAttributes();
/*     */       
/* 148 */       for (byte b = 0; b < namedNodeMap.getLength(); b++) {
/* 149 */         assertNotRelativeNS((Attr)namedNodeMap.item(b));
/*     */       }
/*     */     } else {
/* 152 */       throw new CanonicalizationException("Called checkForRelativeNamespace() on null");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/c14n/helper/C14nHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */