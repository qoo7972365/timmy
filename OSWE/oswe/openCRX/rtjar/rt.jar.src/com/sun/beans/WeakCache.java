/*    */ package com.sun.beans;
/*    */ 
/*    */ import java.lang.ref.Reference;
/*    */ import java.lang.ref.WeakReference;
/*    */ import java.util.Map;
/*    */ import java.util.WeakHashMap;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class WeakCache<K, V>
/*    */ {
/* 45 */   private final Map<K, Reference<V>> map = new WeakHashMap<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public V get(K paramK) {
/* 55 */     Reference<Object> reference = (Reference)this.map.get(paramK);
/* 56 */     if (reference == null) {
/* 57 */       return null;
/*    */     }
/* 59 */     V v = (V)reference.get();
/* 60 */     if (v == null) {
/* 61 */       this.map.remove(paramK);
/*    */     }
/* 63 */     return v;
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
/*    */   
/*    */   public void put(K paramK, V paramV) {
/* 77 */     if (paramV != null) {
/* 78 */       this.map.put(paramK, new WeakReference<>(paramV));
/*    */     } else {
/*    */       
/* 81 */       this.map.remove(paramK);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void clear() {
/* 89 */     this.map.clear();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/beans/WeakCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */