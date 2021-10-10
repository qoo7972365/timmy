/*     */ package jdk.internal.util.xml.impl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Pair
/*     */ {
/*     */   public String name;
/*     */   public String value;
/*     */   public int num;
/*     */   public char[] chars;
/*     */   public int id;
/*     */   public Pair list;
/*     */   public Pair next;
/*     */   
/*     */   public String qname() {
/*  58 */     return new String(this.chars, 1, this.chars.length - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String local() {
/*  67 */     if (this.chars[0] != '\000') {
/*  68 */       return new String(this.chars, this.chars[0] + 1, this.chars.length - this.chars[0] - 1);
/*     */     }
/*  70 */     return new String(this.chars, 1, this.chars.length - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String pref() {
/*  79 */     if (this.chars[0] != '\000') {
/*  80 */       return new String(this.chars, 1, this.chars[0] - 1);
/*     */     }
/*  82 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean eqpref(char[] paramArrayOfchar) {
/*  92 */     if (this.chars[0] == paramArrayOfchar[0]) {
/*  93 */       char c = this.chars[0];
/*  94 */       for (char c1 = '\001'; c1 < c; c1 = (char)(c1 + 1)) {
/*  95 */         if (this.chars[c1] != paramArrayOfchar[c1]) {
/*  96 */           return false;
/*     */         }
/*     */       } 
/*  99 */       return true;
/*     */     } 
/* 101 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean eqname(char[] paramArrayOfchar) {
/* 111 */     char c = (char)this.chars.length;
/* 112 */     if (c == paramArrayOfchar.length) {
/* 113 */       for (char c1 = Character.MIN_VALUE; c1 < c; c1 = (char)(c1 + 1)) {
/* 114 */         if (this.chars[c1] != paramArrayOfchar[c1]) {
/* 115 */           return false;
/*     */         }
/*     */       } 
/* 118 */       return true;
/*     */     } 
/* 120 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/util/xml/impl/Pair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */