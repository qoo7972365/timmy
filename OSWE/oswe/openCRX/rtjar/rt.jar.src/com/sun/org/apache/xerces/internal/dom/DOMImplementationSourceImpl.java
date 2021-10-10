/*     */ package com.sun.org.apache.xerces.internal.dom;
/*     */ 
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import org.w3c.dom.DOMImplementation;
/*     */ import org.w3c.dom.DOMImplementationList;
/*     */ import org.w3c.dom.DOMImplementationSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DOMImplementationSourceImpl
/*     */   implements DOMImplementationSource
/*     */ {
/*     */   public DOMImplementation getDOMImplementation(String features) {
/*  56 */     DOMImplementation impl = CoreDOMImplementationImpl.getDOMImplementation();
/*  57 */     if (testImpl(impl, features)) {
/*  58 */       return impl;
/*     */     }
/*     */     
/*  61 */     impl = DOMImplementationImpl.getDOMImplementation();
/*  62 */     if (testImpl(impl, features)) {
/*  63 */       return impl;
/*     */     }
/*     */     
/*  66 */     return null;
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
/*     */   public DOMImplementationList getDOMImplementationList(String features) {
/*  82 */     DOMImplementation impl = CoreDOMImplementationImpl.getDOMImplementation();
/*  83 */     Vector<DOMImplementation> implementations = new Vector();
/*  84 */     if (testImpl(impl, features)) {
/*  85 */       implementations.addElement(impl);
/*     */     }
/*  87 */     impl = DOMImplementationImpl.getDOMImplementation();
/*  88 */     if (testImpl(impl, features)) {
/*  89 */       implementations.addElement(impl);
/*     */     }
/*     */     
/*  92 */     return new DOMImplementationListImpl(implementations);
/*     */   }
/*     */ 
/*     */   
/*     */   boolean testImpl(DOMImplementation impl, String features) {
/*  97 */     StringTokenizer st = new StringTokenizer(features);
/*  98 */     String feature = null;
/*  99 */     String version = null;
/*     */     
/* 101 */     if (st.hasMoreTokens()) {
/* 102 */       feature = st.nextToken();
/*     */     }
/* 104 */     while (feature != null) {
/* 105 */       boolean isVersion = false;
/* 106 */       if (st.hasMoreTokens()) {
/*     */         
/* 108 */         version = st.nextToken();
/* 109 */         char c = version.charAt(0);
/* 110 */         switch (c) { case '0': case '1': case '2': case '3': case '4': case '5': case '6': case '7':
/*     */           case '8':
/*     */           case '9':
/* 113 */             isVersion = true; break; }
/*     */       
/*     */       } else {
/* 116 */         version = null;
/*     */       } 
/* 118 */       if (isVersion) {
/* 119 */         if (!impl.hasFeature(feature, version)) {
/* 120 */           return false;
/*     */         }
/* 122 */         if (st.hasMoreTokens()) {
/* 123 */           feature = st.nextToken(); continue;
/*     */         } 
/* 125 */         feature = null;
/*     */         continue;
/*     */       } 
/* 128 */       if (!impl.hasFeature(feature, null)) {
/* 129 */         return false;
/*     */       }
/* 131 */       feature = version;
/*     */     } 
/*     */     
/* 134 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/dom/DOMImplementationSourceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */