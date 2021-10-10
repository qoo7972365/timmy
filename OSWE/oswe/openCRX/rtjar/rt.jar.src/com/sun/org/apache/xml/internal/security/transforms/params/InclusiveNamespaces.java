/*     */ package com.sun.org.apache.xml.internal.security.transforms.params;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.TransformParam;
/*     */ import com.sun.org.apache.xml.internal.security.utils.ElementProxy;
/*     */ import java.util.Set;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeSet;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InclusiveNamespaces
/*     */   extends ElementProxy
/*     */   implements TransformParam
/*     */ {
/*     */   public static final String _TAG_EC_INCLUSIVENAMESPACES = "InclusiveNamespaces";
/*     */   public static final String _ATT_EC_PREFIXLIST = "PrefixList";
/*     */   public static final String ExclusiveCanonicalizationNamespace = "http://www.w3.org/2001/10/xml-exc-c14n#";
/*     */   
/*     */   public InclusiveNamespaces(Document paramDocument, String paramString) {
/*  64 */     this(paramDocument, prefixStr2Set(paramString));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InclusiveNamespaces(Document paramDocument, Set<String> paramSet) {
/*  74 */     super(paramDocument);
/*     */     
/*  76 */     SortedSet<String> sortedSet = null;
/*  77 */     if (paramSet instanceof SortedSet) {
/*  78 */       sortedSet = (SortedSet)paramSet;
/*     */     } else {
/*  80 */       sortedSet = new TreeSet<>(paramSet);
/*     */     } 
/*     */     
/*  83 */     StringBuilder stringBuilder = new StringBuilder();
/*  84 */     for (String str : sortedSet) {
/*  85 */       if (str.equals("xmlns")) {
/*  86 */         stringBuilder.append("#default "); continue;
/*     */       } 
/*  88 */       stringBuilder.append(str + " ");
/*     */     } 
/*     */ 
/*     */     
/*  92 */     this.constructionElement.setAttributeNS(null, "PrefixList", stringBuilder
/*  93 */         .toString().trim());
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
/*     */   public InclusiveNamespaces(Element paramElement, String paramString) throws XMLSecurityException {
/* 105 */     super(paramElement, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInclusiveNamespaces() {
/* 114 */     return this.constructionElement.getAttributeNS(null, "PrefixList");
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
/*     */   public static SortedSet<String> prefixStr2Set(String paramString) {
/* 135 */     TreeSet<String> treeSet = new TreeSet();
/*     */     
/* 137 */     if (paramString == null || paramString.length() == 0) {
/* 138 */       return treeSet;
/*     */     }
/*     */     
/* 141 */     String[] arrayOfString = paramString.split("\\s");
/* 142 */     for (String str : arrayOfString) {
/* 143 */       if (str.equals("#default")) {
/* 144 */         treeSet.add("xmlns");
/*     */       } else {
/* 146 */         treeSet.add(str);
/*     */       } 
/*     */     } 
/*     */     
/* 150 */     return treeSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBaseNamespace() {
/* 159 */     return "http://www.w3.org/2001/10/xml-exc-c14n#";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBaseLocalName() {
/* 168 */     return "InclusiveNamespaces";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/transforms/params/InclusiveNamespaces.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */