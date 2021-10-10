/*     */ package sun.util.locale;
/*     */ 
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class LocaleObjectCache<K, V>
/*     */ {
/*     */   private ConcurrentMap<K, CacheEntry<K, V>> map;
/*  41 */   private ReferenceQueue<V> queue = new ReferenceQueue<>();
/*     */   
/*     */   public LocaleObjectCache() {
/*  44 */     this(16, 0.75F, 16);
/*     */   }
/*     */   
/*     */   public LocaleObjectCache(int paramInt1, float paramFloat, int paramInt2) {
/*  48 */     this.map = new ConcurrentHashMap<>(paramInt1, paramFloat, paramInt2);
/*     */   }
/*     */   
/*     */   public V get(K paramK) {
/*  52 */     Object object = null;
/*     */     
/*  54 */     cleanStaleEntries();
/*  55 */     CacheEntry cacheEntry = this.map.get(paramK);
/*  56 */     if (cacheEntry != null) {
/*  57 */       object = cacheEntry.get();
/*     */     }
/*  59 */     if (object == null) {
/*  60 */       V v = createObject(paramK);
/*     */ 
/*     */       
/*  63 */       paramK = normalizeKey(paramK);
/*  64 */       if (paramK == null || v == null)
/*     */       {
/*  66 */         return null;
/*     */       }
/*     */       
/*  69 */       CacheEntry<K, V> cacheEntry1 = new CacheEntry<>(paramK, v, this.queue);
/*     */       
/*  71 */       cacheEntry = this.map.putIfAbsent(paramK, cacheEntry1);
/*  72 */       if (cacheEntry == null) {
/*  73 */         object = v;
/*     */       } else {
/*  75 */         object = cacheEntry.get();
/*  76 */         if (object == null) {
/*  77 */           this.map.put(paramK, cacheEntry1);
/*  78 */           object = v;
/*     */         } 
/*     */       } 
/*     */     } 
/*  82 */     return (V)object;
/*     */   }
/*     */   
/*     */   protected V put(K paramK, V paramV) {
/*  86 */     CacheEntry<K, V> cacheEntry = new CacheEntry<>(paramK, paramV, this.queue);
/*  87 */     CacheEntry cacheEntry1 = this.map.put(paramK, cacheEntry);
/*  88 */     return (cacheEntry1 == null) ? null : (V)cacheEntry1.get();
/*     */   }
/*     */ 
/*     */   
/*     */   private void cleanStaleEntries() {
/*     */     CacheEntry cacheEntry;
/*  94 */     while ((cacheEntry = (CacheEntry)this.queue.poll()) != null) {
/*  95 */       this.map.remove(cacheEntry.getKey());
/*     */     }
/*     */   }
/*     */   
/*     */   protected abstract V createObject(K paramK);
/*     */   
/*     */   protected K normalizeKey(K paramK) {
/* 102 */     return paramK;
/*     */   }
/*     */   
/*     */   private static class CacheEntry<K, V> extends SoftReference<V> {
/*     */     private K key;
/*     */     
/*     */     CacheEntry(K param1K, V param1V, ReferenceQueue<V> param1ReferenceQueue) {
/* 109 */       super(param1V, param1ReferenceQueue);
/* 110 */       this.key = param1K;
/*     */     }
/*     */     
/*     */     K getKey() {
/* 114 */       return this.key;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/LocaleObjectCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */