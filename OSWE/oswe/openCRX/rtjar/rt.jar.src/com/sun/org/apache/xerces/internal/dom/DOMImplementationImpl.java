/*     */ package com.sun.org.apache.xerces.internal.dom;
/*     */ 
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.DOMImplementation;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.DocumentType;
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
/*     */ public class DOMImplementationImpl
/*     */   extends CoreDOMImplementationImpl
/*     */   implements DOMImplementation
/*     */ {
/*  54 */   static DOMImplementationImpl singleton = new DOMImplementationImpl();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DOMImplementation getDOMImplementation() {
/*  63 */     return singleton;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasFeature(String feature, String version) {
/*  87 */     boolean result = super.hasFeature(feature, version);
/*  88 */     if (!result) {
/*  89 */       boolean anyVersion = (version == null || version.length() == 0);
/*  90 */       if (feature.startsWith("+")) {
/*  91 */         feature = feature.substring(1);
/*     */       }
/*  93 */       return ((feature
/*  94 */         .equalsIgnoreCase("Events") && (anyVersion || version
/*  95 */         .equals("2.0"))) || (feature
/*  96 */         .equalsIgnoreCase("MutationEvents") && (anyVersion || version
/*  97 */         .equals("2.0"))) || (feature
/*  98 */         .equalsIgnoreCase("Traversal") && (anyVersion || version
/*  99 */         .equals("2.0"))) || (feature
/* 100 */         .equalsIgnoreCase("Range") && (anyVersion || version
/* 101 */         .equals("2.0"))) || (feature
/* 102 */         .equalsIgnoreCase("MutationEvents") && (anyVersion || version
/* 103 */         .equals("2.0"))));
/*     */     } 
/* 105 */     return result;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Document createDocument(String namespaceURI, String qualifiedName, DocumentType doctype) throws DOMException {
/* 135 */     if (namespaceURI == null && qualifiedName == null && doctype == null)
/*     */     {
/*     */       
/* 138 */       return new DocumentImpl();
/*     */     }
/* 140 */     if (doctype != null && doctype.getOwnerDocument() != null) {
/* 141 */       String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "WRONG_DOCUMENT_ERR", null);
/* 142 */       throw new DOMException((short)4, msg);
/*     */     } 
/* 144 */     DocumentImpl doc = new DocumentImpl(doctype);
/* 145 */     Element e = doc.createElementNS(namespaceURI, qualifiedName);
/* 146 */     doc.appendChild(e);
/* 147 */     return doc;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/dom/DOMImplementationImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */