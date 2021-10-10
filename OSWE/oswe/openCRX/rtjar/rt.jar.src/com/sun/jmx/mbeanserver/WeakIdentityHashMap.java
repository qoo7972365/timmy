/*     */ package com.sun.jmx.mbeanserver;
/*     */ 
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WeakIdentityHashMap<K, V>
/*     */ {
/*     */   static <K, V> WeakIdentityHashMap<K, V> make() {
/*  63 */     return new WeakIdentityHashMap<>();
/*     */   }
/*     */   
/*     */   V get(K paramK) {
/*  67 */     expunge();
/*  68 */     WeakReference<K> weakReference = makeReference(paramK);
/*  69 */     return this.map.get(weakReference);
/*     */   }
/*     */   
/*     */   public V put(K paramK, V paramV) {
/*  73 */     expunge();
/*  74 */     if (paramK == null)
/*  75 */       throw new IllegalArgumentException("Null key"); 
/*  76 */     WeakReference<K> weakReference = makeReference(paramK, this.refQueue);
/*  77 */     return this.map.put(weakReference, paramV);
/*     */   }
/*     */   
/*     */   public V remove(K paramK) {
/*  81 */     expunge();
/*  82 */     WeakReference<K> weakReference = makeReference(paramK);
/*  83 */     return this.map.remove(weakReference);
/*     */   }
/*     */   
/*     */   private void expunge() {
/*     */     Reference<? extends K> reference;
/*  88 */     while ((reference = this.refQueue.poll()) != null)
/*  89 */       this.map.remove(reference); 
/*     */   }
/*     */   
/*     */   private WeakReference<K> makeReference(K paramK) {
/*  93 */     return new IdentityWeakReference<>(paramK);
/*     */   }
/*     */   
/*     */   private WeakReference<K> makeReference(K paramK, ReferenceQueue<K> paramReferenceQueue) {
/*  97 */     return new IdentityWeakReference<>(paramK, paramReferenceQueue);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class IdentityWeakReference<T>
/*     */     extends WeakReference<T>
/*     */   {
/*     */     private final int hashCode;
/*     */ 
/*     */ 
/*     */     
/*     */     IdentityWeakReference(T param1T) {
/* 110 */       this(param1T, null);
/*     */     }
/*     */     
/*     */     IdentityWeakReference(T param1T, ReferenceQueue<T> param1ReferenceQueue) {
/* 114 */       super(param1T, param1ReferenceQueue);
/* 115 */       this.hashCode = (param1T == null) ? 0 : System.identityHashCode(param1T);
/*     */     }
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 119 */       if (this == param1Object)
/* 120 */         return true; 
/* 121 */       if (!(param1Object instanceof IdentityWeakReference))
/* 122 */         return false; 
/* 123 */       IdentityWeakReference<T> identityWeakReference = (IdentityWeakReference)param1Object;
/* 124 */       T t = get();
/* 125 */       return (t != null && t == identityWeakReference.get());
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 129 */       return this.hashCode;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 135 */   private Map<WeakReference<K>, V> map = Util.newMap();
/* 136 */   private ReferenceQueue<K> refQueue = new ReferenceQueue<>();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/mbeanserver/WeakIdentityHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */