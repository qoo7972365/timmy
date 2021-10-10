/*     */ package sun.security.util;
/*     */ 
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MemoryCache<K, V>
/*     */   extends Cache<K, V>
/*     */ {
/*     */   private static final float LOAD_FACTOR = 0.75F;
/*     */   private static final boolean DEBUG = false;
/*     */   private final Map<K, CacheEntry<K, V>> cacheMap;
/*     */   private int maxSize;
/*     */   private long lifetime;
/*     */   private final ReferenceQueue<V> queue;
/*     */   
/*     */   public MemoryCache(boolean paramBoolean, int paramInt) {
/* 261 */     this(paramBoolean, paramInt, 0);
/*     */   }
/*     */   
/*     */   public MemoryCache(boolean paramBoolean, int paramInt1, int paramInt2) {
/* 265 */     this.maxSize = paramInt1;
/* 266 */     this.lifetime = (paramInt2 * 1000);
/* 267 */     if (paramBoolean) {
/* 268 */       this.queue = new ReferenceQueue<>();
/*     */     } else {
/* 270 */       this.queue = null;
/*     */     } 
/* 272 */     int i = (int)(paramInt1 / 0.75F) + 1;
/* 273 */     this.cacheMap = new LinkedHashMap<>(i, 0.75F, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void emptyQueue() {
/* 284 */     if (this.queue == null) {
/*     */       return;
/*     */     }
/* 287 */     int i = this.cacheMap.size();
/*     */     
/*     */     while (true) {
/* 290 */       CacheEntry cacheEntry = (CacheEntry)this.queue.poll();
/* 291 */       if (cacheEntry == null) {
/*     */         break;
/*     */       }
/* 294 */       Object object = cacheEntry.getKey();
/* 295 */       if (object == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 299 */       CacheEntry<K, V> cacheEntry1 = this.cacheMap.remove(object);
/*     */ 
/*     */       
/* 302 */       if (cacheEntry1 != null && cacheEntry != cacheEntry1) {
/* 303 */         this.cacheMap.put((K)object, cacheEntry1);
/*     */       }
/*     */     } 
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
/*     */   private void expungeExpiredEntries() {
/* 319 */     emptyQueue();
/* 320 */     if (this.lifetime == 0L) {
/*     */       return;
/*     */     }
/* 323 */     byte b = 0;
/* 324 */     long l = System.currentTimeMillis();
/* 325 */     Iterator<CacheEntry> iterator = this.cacheMap.values().iterator();
/* 326 */     while (iterator.hasNext()) {
/* 327 */       CacheEntry cacheEntry = iterator.next();
/* 328 */       if (!cacheEntry.isValid(l)) {
/* 329 */         iterator.remove();
/* 330 */         b++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int size() {
/* 342 */     expungeExpiredEntries();
/* 343 */     return this.cacheMap.size();
/*     */   }
/*     */   
/*     */   public synchronized void clear() {
/* 347 */     if (this.queue != null) {
/*     */ 
/*     */       
/* 350 */       for (CacheEntry<K, V> cacheEntry : this.cacheMap.values()) {
/* 351 */         cacheEntry.invalidate();
/*     */       }
/* 353 */       while (this.queue.poll() != null);
/*     */     } 
/*     */ 
/*     */     
/* 357 */     this.cacheMap.clear();
/*     */   }
/*     */   
/*     */   public synchronized void put(K paramK, V paramV) {
/* 361 */     emptyQueue();
/*     */     
/* 363 */     long l = (this.lifetime == 0L) ? 0L : (System.currentTimeMillis() + this.lifetime);
/* 364 */     CacheEntry<K, V> cacheEntry = newEntry(paramK, paramV, l, this.queue);
/* 365 */     CacheEntry cacheEntry1 = this.cacheMap.put(paramK, cacheEntry);
/* 366 */     if (cacheEntry1 != null) {
/* 367 */       cacheEntry1.invalidate();
/*     */       return;
/*     */     } 
/* 370 */     if (this.maxSize > 0 && this.cacheMap.size() > this.maxSize) {
/* 371 */       expungeExpiredEntries();
/* 372 */       if (this.cacheMap.size() > this.maxSize) {
/* 373 */         Iterator<CacheEntry> iterator = this.cacheMap.values().iterator();
/* 374 */         CacheEntry cacheEntry2 = iterator.next();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 379 */         iterator.remove();
/* 380 */         cacheEntry2.invalidate();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized V get(Object paramObject) {
/* 386 */     emptyQueue();
/* 387 */     CacheEntry cacheEntry = this.cacheMap.get(paramObject);
/* 388 */     if (cacheEntry == null) {
/* 389 */       return null;
/*     */     }
/* 391 */     long l = (this.lifetime == 0L) ? 0L : System.currentTimeMillis();
/* 392 */     if (!cacheEntry.isValid(l)) {
/*     */ 
/*     */ 
/*     */       
/* 396 */       this.cacheMap.remove(paramObject);
/* 397 */       return null;
/*     */     } 
/* 399 */     return (V)cacheEntry.getValue();
/*     */   }
/*     */   
/*     */   public synchronized void remove(Object paramObject) {
/* 403 */     emptyQueue();
/* 404 */     CacheEntry cacheEntry = this.cacheMap.remove(paramObject);
/* 405 */     if (cacheEntry != null) {
/* 406 */       cacheEntry.invalidate();
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized void setCapacity(int paramInt) {
/* 411 */     expungeExpiredEntries();
/* 412 */     if (paramInt > 0 && this.cacheMap.size() > paramInt) {
/* 413 */       Iterator<CacheEntry> iterator = this.cacheMap.values().iterator();
/* 414 */       for (int i = this.cacheMap.size() - paramInt; i > 0; i--) {
/* 415 */         CacheEntry cacheEntry = iterator.next();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 420 */         iterator.remove();
/* 421 */         cacheEntry.invalidate();
/*     */       } 
/*     */     } 
/*     */     
/* 425 */     this.maxSize = (paramInt > 0) ? paramInt : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setTimeout(int paramInt) {
/* 433 */     emptyQueue();
/* 434 */     this.lifetime = (paramInt > 0) ? (paramInt * 1000L) : 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void accept(Cache.CacheVisitor<K, V> paramCacheVisitor) {
/* 443 */     expungeExpiredEntries();
/* 444 */     Map<K, V> map = getCachedEntries();
/*     */     
/* 446 */     paramCacheVisitor.visit(map);
/*     */   }
/*     */   
/*     */   private Map<K, V> getCachedEntries() {
/* 450 */     HashMap<Object, Object> hashMap = new HashMap<>(this.cacheMap.size());
/*     */     
/* 452 */     for (CacheEntry<K, V> cacheEntry : this.cacheMap.values()) {
/* 453 */       hashMap.put(cacheEntry.getKey(), cacheEntry.getValue());
/*     */     }
/*     */     
/* 456 */     return (Map)hashMap;
/*     */   }
/*     */ 
/*     */   
/*     */   protected CacheEntry<K, V> newEntry(K paramK, V paramV, long paramLong, ReferenceQueue<V> paramReferenceQueue) {
/* 461 */     if (paramReferenceQueue != null) {
/* 462 */       return new SoftCacheEntry<>(paramK, paramV, paramLong, paramReferenceQueue);
/*     */     }
/* 464 */     return new HardCacheEntry<>(paramK, paramV, paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   private static interface CacheEntry<K, V>
/*     */   {
/*     */     boolean isValid(long param1Long);
/*     */     
/*     */     void invalidate();
/*     */     
/*     */     K getKey();
/*     */     
/*     */     V getValue();
/*     */   }
/*     */   
/*     */   private static class HardCacheEntry<K, V>
/*     */     implements CacheEntry<K, V>
/*     */   {
/*     */     private K key;
/*     */     private V value;
/*     */     private long expirationTime;
/*     */     
/*     */     HardCacheEntry(K param1K, V param1V, long param1Long) {
/* 487 */       this.key = param1K;
/* 488 */       this.value = param1V;
/* 489 */       this.expirationTime = param1Long;
/*     */     }
/*     */     
/*     */     public K getKey() {
/* 493 */       return this.key;
/*     */     }
/*     */     
/*     */     public V getValue() {
/* 497 */       return this.value;
/*     */     }
/*     */     
/*     */     public boolean isValid(long param1Long) {
/* 501 */       boolean bool = (param1Long <= this.expirationTime) ? true : false;
/* 502 */       if (!bool) {
/* 503 */         invalidate();
/*     */       }
/* 505 */       return bool;
/*     */     }
/*     */     
/*     */     public void invalidate() {
/* 509 */       this.key = null;
/* 510 */       this.value = null;
/* 511 */       this.expirationTime = -1L;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class SoftCacheEntry<K, V>
/*     */     extends SoftReference<V>
/*     */     implements CacheEntry<K, V>
/*     */   {
/*     */     private K key;
/*     */     private long expirationTime;
/*     */     
/*     */     SoftCacheEntry(K param1K, V param1V, long param1Long, ReferenceQueue<V> param1ReferenceQueue) {
/* 524 */       super(param1V, param1ReferenceQueue);
/* 525 */       this.key = param1K;
/* 526 */       this.expirationTime = param1Long;
/*     */     }
/*     */     
/*     */     public K getKey() {
/* 530 */       return this.key;
/*     */     }
/*     */     
/*     */     public V getValue() {
/* 534 */       return get();
/*     */     }
/*     */     
/*     */     public boolean isValid(long param1Long) {
/* 538 */       boolean bool = (param1Long <= this.expirationTime && get() != null) ? true : false;
/* 539 */       if (!bool) {
/* 540 */         invalidate();
/*     */       }
/* 542 */       return bool;
/*     */     }
/*     */     
/*     */     public void invalidate() {
/* 546 */       clear();
/* 547 */       this.key = null;
/* 548 */       this.expirationTime = -1L;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/MemoryCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */