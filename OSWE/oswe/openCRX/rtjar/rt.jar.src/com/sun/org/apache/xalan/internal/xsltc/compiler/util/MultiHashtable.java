/*    */ package com.sun.org.apache.xalan.internal.xsltc.compiler.util;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class MultiHashtable<K, V>
/*    */ {
/*    */   static final long serialVersionUID = -6151608290510033572L;
/* 40 */   private final Map<K, Set<V>> map = new HashMap<>();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean modifiable = true;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Set<V> put(K key, V value) {
/* 52 */     if (this.modifiable) {
/* 53 */       Set<V> set = this.map.get(key);
/* 54 */       if (set == null) {
/* 55 */         set = new HashSet<>();
/* 56 */         this.map.put(key, set);
/*    */       } 
/* 58 */       set.add(value);
/* 59 */       return set;
/*    */     } 
/* 61 */     throw new UnsupportedOperationException("The MultiHashtable instance is not modifiable.");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public V maps(K key, V value) {
/* 74 */     if (key == null) return null; 
/* 75 */     Set<V> set = this.map.get(key);
/* 76 */     if (set != null) {
/* 77 */       for (V v : set) {
/* 78 */         if (v.equals(value)) {
/* 79 */           return v;
/*    */         }
/*    */       } 
/*    */     }
/* 83 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void makeUnmodifiable() {
/* 92 */     this.modifiable = false;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/util/MultiHashtable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */