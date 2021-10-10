/*     */ package com.sun.org.apache.xerces.internal.impl.dtd;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.xni.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLAttributeDecl
/*     */ {
/*  76 */   public final QName name = new QName();
/*     */ 
/*     */   
/*  79 */   public final XMLSimpleType simpleType = new XMLSimpleType();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean optional;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValues(QName name, XMLSimpleType simpleType, boolean optional) {
/*  96 */     this.name.setValues(name);
/*  97 */     this.simpleType.setValues(simpleType);
/*  98 */     this.optional = optional;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 105 */     this.name.clear();
/* 106 */     this.simpleType.clear();
/* 107 */     this.optional = false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/dtd/XMLAttributeDecl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */