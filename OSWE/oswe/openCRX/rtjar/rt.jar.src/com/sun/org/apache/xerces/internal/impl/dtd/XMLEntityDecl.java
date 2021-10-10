/*     */ package com.sun.org.apache.xerces.internal.impl.dtd;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLEntityDecl
/*     */ {
/*     */   public String name;
/*     */   public String publicId;
/*     */   public String systemId;
/*     */   public String baseSystemId;
/*     */   public String notation;
/*     */   public boolean isPE;
/*     */   public boolean inExternal;
/*     */   public String value;
/*     */   
/*     */   public void setValues(String name, String publicId, String systemId, String baseSystemId, String notation, boolean isPE, boolean inExternal) {
/* 115 */     setValues(name, publicId, systemId, baseSystemId, notation, null, isPE, inExternal);
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
/*     */   public void setValues(String name, String publicId, String systemId, String baseSystemId, String notation, String value, boolean isPE, boolean inExternal) {
/* 133 */     this.name = name;
/* 134 */     this.publicId = publicId;
/* 135 */     this.systemId = systemId;
/* 136 */     this.baseSystemId = baseSystemId;
/* 137 */     this.notation = notation;
/* 138 */     this.value = value;
/* 139 */     this.isPE = isPE;
/* 140 */     this.inExternal = inExternal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 147 */     this.name = null;
/* 148 */     this.publicId = null;
/* 149 */     this.systemId = null;
/* 150 */     this.baseSystemId = null;
/* 151 */     this.notation = null;
/* 152 */     this.value = null;
/* 153 */     this.isPE = false;
/* 154 */     this.inExternal = false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/dtd/XMLEntityDecl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */