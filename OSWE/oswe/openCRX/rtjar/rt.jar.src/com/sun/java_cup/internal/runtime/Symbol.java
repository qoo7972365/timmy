/*     */ package com.sun.java_cup.internal.runtime;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Symbol
/*     */ {
/*     */   public int sym;
/*     */   public int parse_state;
/*     */   boolean used_by_parser;
/*     */   public int left;
/*     */   public int right;
/*     */   public Object value;
/*     */   
/*     */   public Symbol(int id, int l, int r, Object o) {
/*  54 */     this(id);
/*  55 */     this.left = l;
/*  56 */     this.right = r;
/*  57 */     this.value = o;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Symbol(int id, Object o) {
/*  65 */     this(id);
/*  66 */     this.left = -1;
/*  67 */     this.right = -1;
/*  68 */     this.value = o;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Symbol(int sym_num, int l, int r)
/*     */   {
/* 116 */     this.used_by_parser = false; this.sym = sym_num; this.left = l; this.right = r; this.value = null; } public Symbol(int sym_num, int state) { this.used_by_parser = false;
/*     */     this.sym = sym_num;
/*     */     this.parse_state = state; }
/*     */   
/*     */   public Symbol(int sym_num) {
/*     */     this(sym_num, -1);
/*     */     this.left = -1;
/*     */     this.right = -1;
/*     */     this.value = null;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 128 */     return "#" + this.sym;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java_cup/internal/runtime/Symbol.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */