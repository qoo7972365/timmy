/*     */ package com.sun.beans.util;
/*     */ 
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Cache<K, V>
/*     */ {
/*     */   private static final int MAXIMUM_CAPACITY = 1073741824;
/*     */   private final boolean identity;
/*     */   private final Kind keyKind;
/*     */   private final Kind valueKind;
/*  48 */   private final ReferenceQueue<Object> queue = new ReferenceQueue();
/*     */   
/*  50 */   private volatile CacheEntry<K, V>[] table = newTable(8);
/*  51 */   private int threshold = 6;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int size;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cache(Kind paramKind1, Kind paramKind2) {
/*  73 */     this(paramKind1, paramKind2, false);
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
/*     */   public Cache(Kind paramKind1, Kind paramKind2, boolean paramBoolean) {
/*  90 */     Objects.requireNonNull(paramKind1, "keyKind");
/*  91 */     Objects.requireNonNull(paramKind2, "valueKind");
/*  92 */     this.keyKind = paramKind1;
/*  93 */     this.valueKind = paramKind2;
/*  94 */     this.identity = paramBoolean;
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
/*     */   public final V get(K paramK) {
/* 109 */     Objects.requireNonNull(paramK, "key");
/* 110 */     removeStaleEntries();
/* 111 */     int i = hash(paramK);
/*     */ 
/*     */     
/* 114 */     CacheEntry<K, V>[] arrayOfCacheEntry = this.table;
/* 115 */     V v = getEntryValue(paramK, i, arrayOfCacheEntry[index(i, (Object[])arrayOfCacheEntry)]);
/* 116 */     if (v != null) {
/* 117 */       return v;
/*     */     }
/* 119 */     synchronized (this.queue) {
/*     */ 
/*     */       
/* 122 */       v = getEntryValue(paramK, i, this.table[index(i, (Object[])this.table)]);
/* 123 */       if (v != null) {
/* 124 */         return v;
/*     */       }
/* 126 */       V v1 = create(paramK);
/* 127 */       Objects.requireNonNull(v1, "value");
/* 128 */       int j = index(i, (Object[])this.table);
/* 129 */       this.table[j] = new CacheEntry<>(i, paramK, v1, this.table[j]);
/* 130 */       if (++this.size >= this.threshold) {
/* 131 */         if (this.table.length == 1073741824) {
/* 132 */           this.threshold = Integer.MAX_VALUE;
/*     */         } else {
/* 134 */           removeStaleEntries();
/* 135 */           CacheEntry[] arrayOfCacheEntry1 = (CacheEntry[])newTable(this.table.length << 1);
/* 136 */           transfer(this.table, (CacheEntry<K, V>[])arrayOfCacheEntry1);
/*     */ 
/*     */ 
/*     */           
/* 140 */           if (this.size >= this.threshold / 2) {
/* 141 */             this.table = (CacheEntry<K, V>[])arrayOfCacheEntry1;
/* 142 */             this.threshold <<= 1;
/*     */           } else {
/* 144 */             transfer((CacheEntry<K, V>[])arrayOfCacheEntry1, this.table);
/*     */           } 
/* 146 */           removeStaleEntries();
/*     */         } 
/*     */       }
/* 149 */       return v1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void remove(K paramK) {
/* 159 */     if (paramK != null) {
/* 160 */       synchronized (this.queue) {
/* 161 */         removeStaleEntries();
/* 162 */         int i = hash(paramK);
/* 163 */         int j = index(i, (Object[])this.table);
/* 164 */         CacheEntry<K, V> cacheEntry1 = this.table[j];
/* 165 */         CacheEntry<K, V> cacheEntry2 = cacheEntry1;
/* 166 */         while (cacheEntry2 != null) {
/* 167 */           CacheEntry<K, V> cacheEntry = cacheEntry2.next;
/* 168 */           if (cacheEntry2.matches(i, paramK)) {
/* 169 */             if (cacheEntry2 == cacheEntry1) {
/* 170 */               this.table[j] = cacheEntry;
/*     */             } else {
/* 172 */               cacheEntry1.next = cacheEntry;
/*     */             } 
/* 174 */             cacheEntry2.unlink();
/*     */             break;
/*     */           } 
/* 177 */           cacheEntry1 = cacheEntry2;
/* 178 */           cacheEntry2 = cacheEntry;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clear() {
/* 189 */     synchronized (this.queue) {
/* 190 */       int i = this.table.length;
/* 191 */       while (0 < i--) {
/* 192 */         CacheEntry<K, V> cacheEntry = this.table[i];
/* 193 */         while (cacheEntry != null) {
/* 194 */           CacheEntry<K, V> cacheEntry1 = cacheEntry.next;
/* 195 */           cacheEntry.unlink();
/* 196 */           cacheEntry = cacheEntry1;
/*     */         } 
/* 198 */         this.table[i] = null;
/*     */       } 
/* 200 */       while (null != this.queue.poll());
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
/*     */   
/*     */   private int hash(Object paramObject) {
/* 217 */     if (this.identity) {
/* 218 */       int j = System.identityHashCode(paramObject);
/* 219 */       return (j << 1) - (j << 8);
/*     */     } 
/* 221 */     int i = paramObject.hashCode();
/*     */ 
/*     */ 
/*     */     
/* 225 */     i ^= i >>> 20 ^ i >>> 12;
/* 226 */     return i ^ i >>> 7 ^ i >>> 4;
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
/*     */   private static int index(int paramInt, Object[] paramArrayOfObject) {
/* 238 */     return paramInt & paramArrayOfObject.length - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CacheEntry<K, V>[] newTable(int paramInt) {
/* 249 */     return (CacheEntry<K, V>[])new CacheEntry[paramInt];
/*     */   }
/*     */   
/*     */   private V getEntryValue(K paramK, int paramInt, CacheEntry<K, V> paramCacheEntry) {
/* 253 */     while (paramCacheEntry != null) {
/* 254 */       if (paramCacheEntry.matches(paramInt, paramK)) {
/* 255 */         return (V)paramCacheEntry.value.getReferent();
/*     */       }
/* 257 */       paramCacheEntry = paramCacheEntry.next;
/*     */     } 
/* 259 */     return null;
/*     */   }
/*     */   
/*     */   private void removeStaleEntries() {
/* 263 */     Reference<?> reference = this.queue.poll();
/* 264 */     if (reference != null)
/* 265 */       synchronized (this.queue) {
/*     */         while (true) {
/* 267 */           if (reference instanceof Ref) {
/* 268 */             Ref ref = (Ref)reference;
/*     */             
/* 270 */             CacheEntry<K, V> cacheEntry = (CacheEntry)ref.getOwner();
/* 271 */             if (cacheEntry != null) {
/* 272 */               int i = index(cacheEntry.hash, (Object[])this.table);
/* 273 */               CacheEntry<K, V> cacheEntry1 = this.table[i];
/* 274 */               CacheEntry<K, V> cacheEntry2 = cacheEntry1;
/* 275 */               while (cacheEntry2 != null) {
/* 276 */                 CacheEntry<K, V> cacheEntry3 = cacheEntry2.next;
/* 277 */                 if (cacheEntry2 == cacheEntry) {
/* 278 */                   if (cacheEntry2 == cacheEntry1) {
/* 279 */                     this.table[i] = cacheEntry3;
/*     */                   } else {
/* 281 */                     cacheEntry1.next = cacheEntry3;
/*     */                   } 
/* 283 */                   cacheEntry2.unlink();
/*     */                   break;
/*     */                 } 
/* 286 */                 cacheEntry1 = cacheEntry2;
/* 287 */                 cacheEntry2 = cacheEntry3;
/*     */               } 
/*     */             } 
/*     */           } 
/* 291 */           reference = this.queue.poll();
/*     */           
/* 293 */           if (reference == null)
/*     */             break; 
/*     */         } 
/*     */       }  
/*     */   }
/*     */   private void transfer(CacheEntry<K, V>[] paramArrayOfCacheEntry1, CacheEntry<K, V>[] paramArrayOfCacheEntry2) {
/* 299 */     int i = paramArrayOfCacheEntry1.length;
/* 300 */     while (0 < i--) {
/* 301 */       CacheEntry<K, V> cacheEntry = paramArrayOfCacheEntry1[i];
/* 302 */       paramArrayOfCacheEntry1[i] = null;
/* 303 */       while (cacheEntry != null) {
/* 304 */         CacheEntry<K, V> cacheEntry1 = cacheEntry.next;
/* 305 */         if (cacheEntry.key.isStale() || cacheEntry.value.isStale()) {
/* 306 */           cacheEntry.unlink();
/*     */         } else {
/* 308 */           int j = index(cacheEntry.hash, (Object[])paramArrayOfCacheEntry2);
/* 309 */           cacheEntry.next = paramArrayOfCacheEntry2[j];
/* 310 */           paramArrayOfCacheEntry2[j] = cacheEntry;
/*     */         } 
/* 312 */         cacheEntry = cacheEntry1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract V create(K paramK);
/*     */ 
/*     */ 
/*     */   
/*     */   private final class CacheEntry<K, V>
/*     */   {
/*     */     private final int hash;
/*     */     
/*     */     private final Cache.Ref<K> key;
/*     */     
/*     */     private final Cache.Ref<V> value;
/*     */     
/*     */     private volatile CacheEntry<K, V> next;
/*     */ 
/*     */     
/*     */     private CacheEntry(int param1Int, K param1K, V param1V, CacheEntry<K, V> param1CacheEntry) {
/* 335 */       this.hash = param1Int;
/* 336 */       this.key = Cache.this.keyKind.create(this, param1K, Cache.this.queue);
/* 337 */       this.value = Cache.this.valueKind.create(this, param1V, Cache.this.queue);
/* 338 */       this.next = param1CacheEntry;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean matches(int param1Int, Object param1Object) {
/* 350 */       if (this.hash != param1Int) {
/* 351 */         return false;
/*     */       }
/* 353 */       K k = this.key.getReferent();
/* 354 */       return (k == param1Object || (!Cache.this.identity && k != null && k.equals(param1Object)));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void unlink() {
/* 361 */       this.next = null;
/* 362 */       this.key.removeOwner();
/* 363 */       this.value.removeOwner();
/* 364 */       Cache.this.size--;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static interface Ref<T>
/*     */   {
/*     */     Object getOwner();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     T getReferent();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean isStale();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void removeOwner();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Kind
/*     */   {
/* 406 */     STRONG {
/*     */       <T> Cache.Ref<T> create(Object param2Object, T param2T, ReferenceQueue<? super T> param2ReferenceQueue) {
/* 408 */         return new Strong<>(param2Object, param2T);
/*     */       }
/*     */     },
/* 411 */     SOFT {
/*     */       <T> Cache.Ref<T> create(Object param2Object, T param2T, ReferenceQueue<? super T> param2ReferenceQueue) {
/* 413 */         return (param2T == null) ? new Strong<>(param2Object, param2T) : new Soft<>(param2Object, param2T, param2ReferenceQueue);
/*     */       }
/*     */     },
/*     */ 
/*     */     
/* 418 */     WEAK {
/*     */       <T> Cache.Ref<T> create(Object param2Object, T param2T, ReferenceQueue<? super T> param2ReferenceQueue) {
/* 420 */         return (param2T == null) ? new Strong<>(param2Object, param2T) : new Weak<>(param2Object, param2T, param2ReferenceQueue);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     abstract <T> Cache.Ref<T> create(Object param1Object, T param1T, ReferenceQueue<? super T> param1ReferenceQueue);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static final class Strong<T>
/*     */       implements Cache.Ref<T>
/*     */     {
/*     */       private Object owner;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private final T referent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private Strong(Object param2Object, T param2T) {
/* 456 */         this.owner = param2Object;
/* 457 */         this.referent = param2T;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public Object getOwner() {
/* 466 */         return this.owner;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public T getReferent() {
/* 475 */         return this.referent;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean isStale() {
/* 484 */         return false;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void removeOwner() {
/* 491 */         this.owner = null;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static final class Soft<T>
/*     */       extends SoftReference<T>
/*     */       implements Cache.Ref<T>
/*     */     {
/*     */       private Object owner;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private Soft(Object param2Object, T param2T, ReferenceQueue<? super T> param2ReferenceQueue) {
/* 515 */         super(param2T, param2ReferenceQueue);
/* 516 */         this.owner = param2Object;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public Object getOwner() {
/* 525 */         return this.owner;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public T getReferent() {
/* 534 */         return get();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean isStale() {
/* 543 */         return (null == get());
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void removeOwner() {
/* 550 */         this.owner = null;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static final class Weak<T>
/*     */       extends WeakReference<T>
/*     */       implements Cache.Ref<T>
/*     */     {
/*     */       private Object owner;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private Weak(Object param2Object, T param2T, ReferenceQueue<? super T> param2ReferenceQueue) {
/* 574 */         super(param2T, param2ReferenceQueue);
/* 575 */         this.owner = param2Object;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public Object getOwner() {
/* 584 */         return this.owner;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public T getReferent() {
/* 593 */         return get();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean isStale() {
/* 602 */         return (null == get());
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void removeOwner() {
/* 609 */         this.owner = null;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/beans/util/Cache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */