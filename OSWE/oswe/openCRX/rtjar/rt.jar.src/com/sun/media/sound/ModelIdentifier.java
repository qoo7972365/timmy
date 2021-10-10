/*     */ package com.sun.media.sound;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ModelIdentifier
/*     */ {
/*  87 */   private String object = null;
/*  88 */   private String variable = null;
/*  89 */   private int instance = 0;
/*     */   
/*     */   public ModelIdentifier(String paramString) {
/*  92 */     this.object = paramString;
/*     */   }
/*     */   
/*     */   public ModelIdentifier(String paramString, int paramInt) {
/*  96 */     this.object = paramString;
/*  97 */     this.instance = paramInt;
/*     */   }
/*     */   
/*     */   public ModelIdentifier(String paramString1, String paramString2) {
/* 101 */     this.object = paramString1;
/* 102 */     this.variable = paramString2;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelIdentifier(String paramString1, String paramString2, int paramInt) {
/* 107 */     this.object = paramString1;
/* 108 */     this.variable = paramString2;
/* 109 */     this.instance = paramInt;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInstance() {
/* 114 */     return this.instance;
/*     */   }
/*     */   
/*     */   public void setInstance(int paramInt) {
/* 118 */     this.instance = paramInt;
/*     */   }
/*     */   
/*     */   public String getObject() {
/* 122 */     return this.object;
/*     */   }
/*     */   
/*     */   public void setObject(String paramString) {
/* 126 */     this.object = paramString;
/*     */   }
/*     */   
/*     */   public String getVariable() {
/* 130 */     return this.variable;
/*     */   }
/*     */   
/*     */   public void setVariable(String paramString) {
/* 134 */     this.variable = paramString;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 138 */     int i = this.instance;
/* 139 */     if (this.object != null) i |= this.object.hashCode(); 
/* 140 */     if (this.variable != null) i |= this.variable.hashCode(); 
/* 141 */     return i;
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 145 */     if (!(paramObject instanceof ModelIdentifier)) {
/* 146 */       return false;
/*     */     }
/* 148 */     ModelIdentifier modelIdentifier = (ModelIdentifier)paramObject;
/* 149 */     if (((this.object == null) ? true : false) != ((modelIdentifier.object == null) ? true : false))
/* 150 */       return false; 
/* 151 */     if (((this.variable == null) ? true : false) != ((modelIdentifier.variable == null) ? true : false))
/* 152 */       return false; 
/* 153 */     if (modelIdentifier.getInstance() != getInstance())
/* 154 */       return false; 
/* 155 */     if (this.object != null && !this.object.equals(modelIdentifier.object))
/* 156 */       return false; 
/* 157 */     if (this.variable != null && !this.variable.equals(modelIdentifier.variable))
/* 158 */       return false; 
/* 159 */     return true;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 163 */     if (this.variable == null) {
/* 164 */       return this.object + "[" + this.instance + "]";
/*     */     }
/* 166 */     return this.object + "[" + this.instance + "]." + this.variable;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/ModelIdentifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */