/*     */ package com.sun.xml.internal.bind.v2.util;
/*     */ 
/*     */ import java.util.AbstractMap;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.WeakHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EditDistance
/*     */ {
/*  49 */   private static final WeakHashMap<AbstractMap.SimpleEntry<String, String>, Integer> CACHE = new WeakHashMap<>();
/*     */   
/*     */   private int[] cost;
/*     */   
/*     */   private int[] back;
/*     */   
/*     */   private final String a;
/*     */   private final String b;
/*     */   
/*     */   public static int editDistance(String a, String b) {
/*  59 */     AbstractMap.SimpleEntry<String, String> entry = new AbstractMap.SimpleEntry<>(a, b);
/*  60 */     Integer result = null;
/*  61 */     if (CACHE.containsKey(entry)) {
/*  62 */       result = CACHE.get(entry);
/*     */     }
/*  64 */     if (result == null) {
/*  65 */       result = Integer.valueOf((new EditDistance(a, b)).calc());
/*  66 */       CACHE.put(entry, result);
/*     */     } 
/*  68 */     return result.intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String findNearest(String key, String[] group) {
/*  78 */     return findNearest(key, Arrays.asList(group));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String findNearest(String key, Collection<String> group) {
/*  88 */     int c = Integer.MAX_VALUE;
/*  89 */     String r = null;
/*     */     
/*  91 */     for (String s : group) {
/*  92 */       int ed = editDistance(key, s);
/*  93 */       if (c > ed) {
/*  94 */         c = ed;
/*  95 */         r = s;
/*     */       } 
/*     */     } 
/*  98 */     return r;
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
/*     */   private EditDistance(String a, String b) {
/* 110 */     this.a = a;
/* 111 */     this.b = b;
/* 112 */     this.cost = new int[a.length() + 1];
/* 113 */     this.back = new int[a.length() + 1];
/*     */     
/* 115 */     for (int i = 0; i <= a.length(); i++) {
/* 116 */       this.cost[i] = i;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void flip() {
/* 123 */     int[] t = this.cost;
/* 124 */     this.cost = this.back;
/* 125 */     this.back = t;
/*     */   }
/*     */   
/*     */   private int min(int a, int b, int c) {
/* 129 */     return Math.min(a, Math.min(b, c));
/*     */   }
/*     */   
/*     */   private int calc() {
/* 133 */     for (int j = 0; j < this.b.length(); j++) {
/* 134 */       flip();
/* 135 */       this.cost[0] = j + 1;
/* 136 */       for (int i = 0; i < this.a.length(); i++) {
/* 137 */         int match = (this.a.charAt(i) == this.b.charAt(j)) ? 0 : 1;
/* 138 */         this.cost[i + 1] = min(this.back[i] + match, this.cost[i] + 1, this.back[i + 1] + 1);
/*     */       } 
/*     */     } 
/* 141 */     return this.cost[this.a.length()];
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/util/EditDistance.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */