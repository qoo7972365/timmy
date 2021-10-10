/*     */ package com.sun.org.apache.xerces.internal.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class IntStack
/*     */ {
/*     */   private int fDepth;
/*     */   private int[] fData;
/*     */   
/*     */   public int size() {
/*  49 */     return this.fDepth;
/*     */   }
/*     */ 
/*     */   
/*     */   public void push(int value) {
/*  54 */     ensureCapacity(this.fDepth + 1);
/*  55 */     this.fData[this.fDepth++] = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public int peek() {
/*  60 */     return this.fData[this.fDepth - 1];
/*     */   }
/*     */ 
/*     */   
/*     */   public int elementAt(int depth) {
/*  65 */     return this.fData[depth];
/*     */   }
/*     */ 
/*     */   
/*     */   public int pop() {
/*  70 */     return this.fData[--this.fDepth];
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/*  75 */     this.fDepth = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void print() {
/*  82 */     System.out.print('(');
/*  83 */     System.out.print(this.fDepth);
/*  84 */     System.out.print(") {");
/*  85 */     for (int i = 0; i < this.fDepth; i++) {
/*  86 */       if (i == 3) {
/*  87 */         System.out.print(" ...");
/*     */         break;
/*     */       } 
/*  90 */       System.out.print(' ');
/*  91 */       System.out.print(this.fData[i]);
/*  92 */       if (i < this.fDepth - 1) {
/*  93 */         System.out.print(',');
/*     */       }
/*     */     } 
/*  96 */     System.out.print(" }");
/*  97 */     System.out.println();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void ensureCapacity(int size) {
/* 106 */     if (this.fData == null) {
/* 107 */       this.fData = new int[32];
/*     */     }
/* 109 */     else if (this.fData.length <= size) {
/* 110 */       int[] newdata = new int[this.fData.length * 2];
/* 111 */       System.arraycopy(this.fData, 0, newdata, 0, this.fData.length);
/* 112 */       this.fData = newdata;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/util/IntStack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */