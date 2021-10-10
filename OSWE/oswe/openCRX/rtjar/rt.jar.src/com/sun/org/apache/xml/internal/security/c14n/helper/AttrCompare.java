/*     */ package com.sun.org.apache.xml.internal.security.c14n.helper;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
/*     */ import org.w3c.dom.Attr;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AttrCompare
/*     */   implements Comparator<Attr>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7113259629930576230L;
/*     */   private static final int ATTR0_BEFORE_ATTR1 = -1;
/*     */   private static final int ATTR1_BEFORE_ATTR0 = 1;
/*     */   private static final String XMLNS = "http://www.w3.org/2000/xmlns/";
/*     */   
/*     */   public int compare(Attr paramAttr1, Attr paramAttr2) {
/*  74 */     String str1 = paramAttr1.getNamespaceURI();
/*  75 */     String str2 = paramAttr2.getNamespaceURI();
/*     */     
/*  77 */     boolean bool1 = "http://www.w3.org/2000/xmlns/".equals(str1);
/*  78 */     boolean bool2 = "http://www.w3.org/2000/xmlns/".equals(str2);
/*     */     
/*  80 */     if (bool1) {
/*  81 */       if (bool2) {
/*     */         
/*  83 */         String str3 = paramAttr1.getLocalName();
/*  84 */         String str4 = paramAttr2.getLocalName();
/*     */         
/*  86 */         if ("xmlns".equals(str3)) {
/*  87 */           str3 = "";
/*     */         }
/*     */         
/*  90 */         if ("xmlns".equals(str4)) {
/*  91 */           str4 = "";
/*     */         }
/*     */         
/*  94 */         return str3.compareTo(str4);
/*     */       } 
/*     */       
/*  97 */       return -1;
/*  98 */     }  if (bool2)
/*     */     {
/* 100 */       return 1;
/*     */     }
/*     */ 
/*     */     
/* 104 */     if (str1 == null) {
/* 105 */       if (str2 == null) {
/* 106 */         String str3 = paramAttr1.getName();
/* 107 */         String str4 = paramAttr2.getName();
/* 108 */         return str3.compareTo(str4);
/*     */       } 
/* 110 */       return -1;
/* 111 */     }  if (str2 == null) {
/* 112 */       return 1;
/*     */     }
/*     */     
/* 115 */     int i = str1.compareTo(str2);
/* 116 */     if (i != 0) {
/* 117 */       return i;
/*     */     }
/*     */     
/* 120 */     return paramAttr1.getLocalName().compareTo(paramAttr2.getLocalName());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/c14n/helper/AttrCompare.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */