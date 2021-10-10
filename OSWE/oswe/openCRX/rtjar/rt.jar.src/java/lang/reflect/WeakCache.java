/*     */ package java.lang.reflect;
/*     */ 
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.lang.reflect.WeakCache;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Supplier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class WeakCache<K, P, V>
/*     */ {
/*  59 */   private final ReferenceQueue<K> refQueue = new ReferenceQueue<>();
/*     */ 
/*     */   
/*  62 */   private final ConcurrentMap<Object, ConcurrentMap<Object, Supplier<V>>> map = new ConcurrentHashMap<>();
/*     */   
/*  64 */   private final ConcurrentMap<Supplier<V>, Boolean> reverseMap = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final BiFunction<K, P, ?> subKeyFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final BiFunction<K, P, V> valueFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WeakCache(BiFunction<K, P, ?> paramBiFunction, BiFunction<K, P, V> paramBiFunction1) {
/*  81 */     this.subKeyFactory = Objects.<BiFunction<K, P, ?>>requireNonNull(paramBiFunction);
/*  82 */     this.valueFactory = Objects.<BiFunction<K, P, V>>requireNonNull(paramBiFunction1);
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
/*     */   public V get(K paramK, P paramP) {
/* 101 */     Objects.requireNonNull(paramP);
/*     */     
/* 103 */     expungeStaleEntries();
/*     */     
/* 105 */     Object object = CacheKey.valueOf(paramK, this.refQueue);
/*     */ 
/*     */     
/* 108 */     ConcurrentMap<Object, Object> concurrentMap = (ConcurrentMap)this.map.get(object);
/* 109 */     if (concurrentMap == null) {
/*     */       
/* 111 */       ConcurrentMap<Object, Object> concurrentMap1 = (ConcurrentMap)this.map.putIfAbsent(object, concurrentMap = new ConcurrentHashMap<>());
/*     */       
/* 113 */       if (concurrentMap1 != null) {
/* 114 */         concurrentMap = concurrentMap1;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 120 */     Object object1 = Objects.requireNonNull(this.subKeyFactory.apply(paramK, paramP));
/* 121 */     Supplier<Object> supplier = (Supplier)concurrentMap.get(object1);
/* 122 */     Factory factory = null;
/*     */     
/*     */     while (true) {
/* 125 */       if (supplier != null) {
/*     */         
/* 127 */         V v = (V)supplier.get();
/* 128 */         if (v != null) {
/* 129 */           return v;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 137 */       if (factory == null) {
/* 138 */         factory = new Factory(paramK, paramP, object1, (ConcurrentMap)concurrentMap);
/*     */       }
/*     */       
/* 141 */       if (supplier == null) {
/* 142 */         supplier = (Supplier<Object>)concurrentMap.putIfAbsent(object1, factory);
/* 143 */         if (supplier == null)
/*     */         {
/* 145 */           supplier = factory;
/*     */         }
/*     */         continue;
/*     */       } 
/* 149 */       if (concurrentMap.replace(object1, supplier, factory)) {
/*     */ 
/*     */ 
/*     */         
/* 153 */         supplier = factory;
/*     */         continue;
/*     */       } 
/* 156 */       supplier = (Supplier<Object>)concurrentMap.get(object1);
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
/*     */   
/*     */   public boolean containsValue(V paramV) {
/* 172 */     Objects.requireNonNull(paramV);
/*     */     
/* 174 */     expungeStaleEntries();
/* 175 */     return this.reverseMap.containsKey(new LookupValue<>(paramV));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 183 */     expungeStaleEntries();
/* 184 */     return this.reverseMap.size();
/*     */   }
/*     */   
/*     */   private void expungeStaleEntries() {
/*     */     CacheKey cacheKey;
/* 189 */     while ((cacheKey = (CacheKey)this.refQueue.poll()) != null) {
/* 190 */       cacheKey.expungeFrom(this.map, this.reverseMap);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private final class Factory
/*     */     implements Supplier<V>
/*     */   {
/*     */     private final K key;
/*     */     
/*     */     private final P parameter;
/*     */     
/*     */     private final Object subKey;
/*     */     
/*     */     private final ConcurrentMap<Object, Supplier<V>> valuesMap;
/*     */     
/*     */     Factory(K param1K, P param1P, Object param1Object, ConcurrentMap<Object, Supplier<V>> param1ConcurrentMap) {
/* 207 */       this.key = param1K;
/* 208 */       this.parameter = param1P;
/* 209 */       this.subKey = param1Object;
/* 210 */       this.valuesMap = param1ConcurrentMap;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public synchronized V get() {
/* 216 */       Supplier supplier = this.valuesMap.get(this.subKey);
/* 217 */       if (supplier != this)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 223 */         return null;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 228 */       V v = null;
/*     */       try {
/* 230 */         v = Objects.requireNonNull(WeakCache.this.valueFactory.apply(this.key, this.parameter));
/*     */       } finally {
/* 232 */         if (v == null) {
/* 233 */           this.valuesMap.remove(this.subKey, this);
/*     */         }
/*     */       } 
/*     */       
/* 237 */       assert v != null;
/*     */ 
/*     */       
/* 240 */       WeakCache.CacheValue<V> cacheValue = new WeakCache.CacheValue(v);
/*     */ 
/*     */       
/* 243 */       WeakCache.this.reverseMap.put(cacheValue, Boolean.TRUE);
/*     */ 
/*     */       
/* 246 */       if (!this.valuesMap.replace(this.subKey, this, cacheValue)) {
/* 247 */         throw new AssertionError("Should not reach here");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 252 */       return v;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static interface Value<V>
/*     */     extends Supplier<V> {}
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class LookupValue<V>
/*     */     implements Value<V>
/*     */   {
/*     */     private final V value;
/*     */ 
/*     */ 
/*     */     
/*     */     LookupValue(V param1V) {
/* 272 */       this.value = param1V;
/*     */     }
/*     */ 
/*     */     
/*     */     public V get() {
/* 277 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 282 */       return System.identityHashCode(this.value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 287 */       return (param1Object == this || (param1Object instanceof WeakCache.Value && this.value == ((WeakCache.Value<V>)param1Object)
/*     */         
/* 289 */         .get()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class CacheValue<V>
/*     */     extends WeakReference<V>
/*     */     implements Value<V>
/*     */   {
/*     */     private final int hash;
/*     */ 
/*     */     
/*     */     CacheValue(V param1V) {
/* 302 */       super(param1V);
/* 303 */       this.hash = System.identityHashCode(param1V);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 308 */       return this.hash;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/*     */       V v;
/* 314 */       return (param1Object == this || (param1Object instanceof WeakCache.Value && (
/*     */ 
/*     */         
/* 317 */         v = get()) != null && v == ((WeakCache.Value<V>)param1Object)
/* 318 */         .get()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class CacheKey<K>
/*     */     extends WeakReference<K>
/*     */   {
/* 330 */     private static final Object NULL_KEY = new Object();
/*     */     
/*     */     static <K> Object valueOf(K param1K, ReferenceQueue<K> param1ReferenceQueue) {
/* 333 */       return (param1K == null) ? NULL_KEY : new CacheKey<>(param1K, param1ReferenceQueue);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int hash;
/*     */ 
/*     */ 
/*     */     
/*     */     private CacheKey(K param1K, ReferenceQueue<K> param1ReferenceQueue) {
/* 344 */       super(param1K, param1ReferenceQueue);
/* 345 */       this.hash = System.identityHashCode(param1K);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 350 */       return this.hash;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/*     */       K k;
/* 356 */       return (param1Object == this || (param1Object != null && param1Object
/*     */         
/* 358 */         .getClass() == getClass() && (
/*     */         
/* 360 */         k = get()) != null && k == ((CacheKey<K>)param1Object)
/*     */         
/* 362 */         .get()));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void expungeFrom(ConcurrentMap<?, ? extends ConcurrentMap<?, ?>> param1ConcurrentMap, ConcurrentMap<?, Boolean> param1ConcurrentMap1) {
/* 370 */       ConcurrentMap concurrentMap = param1ConcurrentMap.remove(this);
/*     */       
/* 372 */       if (concurrentMap != null)
/* 373 */         for (Object object : concurrentMap.values())
/* 374 */           param1ConcurrentMap1.remove(object);  
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/reflect/WeakCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */