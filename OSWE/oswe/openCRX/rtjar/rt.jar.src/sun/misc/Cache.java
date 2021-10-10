/*     */ package sun.misc;
/*     */ 
/*     */ import java.util.Dictionary;
/*     */ import java.util.Enumeration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Cache
/*     */   extends Dictionary
/*     */ {
/*     */   private CacheEntry[] table;
/*     */   private int count;
/*     */   private int threshold;
/*     */   private float loadFactor;
/*     */   
/*     */   private void init(int paramInt, float paramFloat) {
/* 100 */     if (paramInt <= 0 || paramFloat <= 0.0D) {
/* 101 */       throw new IllegalArgumentException();
/*     */     }
/* 103 */     this.loadFactor = paramFloat;
/* 104 */     this.table = new CacheEntry[paramInt];
/* 105 */     this.threshold = (int)(paramInt * paramFloat);
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
/*     */   public Cache(int paramInt, float paramFloat) {
/* 121 */     init(paramInt, paramFloat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cache(int paramInt) {
/* 130 */     init(paramInt, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cache() {
/*     */     try {
/* 140 */       init(101, 0.75F);
/* 141 */     } catch (IllegalArgumentException illegalArgumentException) {
/*     */       
/* 143 */       throw new Error("panic");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 151 */     return this.count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 158 */     return (this.count == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Enumeration keys() {
/* 167 */     return new CacheEnumerator(this.table, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Enumeration elements() {
/* 177 */     return new CacheEnumerator(this.table, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Object get(Object paramObject) {
/* 188 */     CacheEntry[] arrayOfCacheEntry = this.table;
/* 189 */     int i = paramObject.hashCode();
/* 190 */     int j = (i & Integer.MAX_VALUE) % arrayOfCacheEntry.length;
/* 191 */     for (CacheEntry cacheEntry = arrayOfCacheEntry[j]; cacheEntry != null; cacheEntry = cacheEntry.next) {
/* 192 */       if (cacheEntry.hash == i && cacheEntry.key.equals(paramObject)) {
/* 193 */         return cacheEntry.check();
/*     */       }
/*     */     } 
/* 196 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void rehash() {
/* 205 */     int i = this.table.length;
/* 206 */     CacheEntry[] arrayOfCacheEntry1 = this.table;
/*     */     
/* 208 */     int j = i * 2 + 1;
/* 209 */     CacheEntry[] arrayOfCacheEntry2 = new CacheEntry[j];
/*     */     
/* 211 */     this.threshold = (int)(j * this.loadFactor);
/* 212 */     this.table = arrayOfCacheEntry2;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 217 */     for (int k = i; k-- > 0;) {
/* 218 */       for (CacheEntry cacheEntry = arrayOfCacheEntry1[k]; cacheEntry != null; ) {
/* 219 */         CacheEntry cacheEntry1 = cacheEntry;
/* 220 */         cacheEntry = cacheEntry.next;
/* 221 */         if (cacheEntry1.check() != null) {
/* 222 */           int m = (cacheEntry1.hash & Integer.MAX_VALUE) % j;
/* 223 */           cacheEntry1.next = arrayOfCacheEntry2[m];
/* 224 */           arrayOfCacheEntry2[m] = cacheEntry1; continue;
/*     */         } 
/* 226 */         this.count--;
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
/*     */ 
/*     */   
/*     */   public synchronized Object put(Object paramObject1, Object paramObject2) {
/* 244 */     if (paramObject2 == null) {
/* 245 */       throw new NullPointerException();
/*     */     }
/*     */     
/* 248 */     CacheEntry[] arrayOfCacheEntry = this.table;
/* 249 */     int i = paramObject1.hashCode();
/* 250 */     int j = (i & Integer.MAX_VALUE) % arrayOfCacheEntry.length;
/* 251 */     CacheEntry cacheEntry1 = null;
/* 252 */     for (CacheEntry cacheEntry2 = arrayOfCacheEntry[j]; cacheEntry2 != null; cacheEntry2 = cacheEntry2.next) {
/* 253 */       if (cacheEntry2.hash == i && cacheEntry2.key.equals(paramObject1)) {
/* 254 */         Object object = cacheEntry2.check();
/* 255 */         cacheEntry2.setThing(paramObject2);
/* 256 */         return object;
/* 257 */       }  if (cacheEntry2.check() == null) {
/* 258 */         cacheEntry1 = cacheEntry2;
/*     */       }
/*     */     } 
/* 261 */     if (this.count >= this.threshold) {
/*     */       
/* 263 */       rehash();
/* 264 */       return put(paramObject1, paramObject2);
/*     */     } 
/*     */     
/* 267 */     if (cacheEntry1 == null) {
/* 268 */       cacheEntry1 = new CacheEntry();
/* 269 */       cacheEntry1.next = arrayOfCacheEntry[j];
/* 270 */       arrayOfCacheEntry[j] = cacheEntry1;
/* 271 */       this.count++;
/*     */     } 
/* 273 */     cacheEntry1.hash = i;
/* 274 */     cacheEntry1.key = paramObject1;
/* 275 */     cacheEntry1.setThing(paramObject2);
/* 276 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Object remove(Object paramObject) {
/* 286 */     CacheEntry[] arrayOfCacheEntry = this.table;
/* 287 */     int i = paramObject.hashCode();
/* 288 */     int j = (i & Integer.MAX_VALUE) % arrayOfCacheEntry.length;
/* 289 */     for (CacheEntry cacheEntry1 = arrayOfCacheEntry[j], cacheEntry2 = null; cacheEntry1 != null; cacheEntry2 = cacheEntry1, cacheEntry1 = cacheEntry1.next) {
/* 290 */       if (cacheEntry1.hash == i && cacheEntry1.key.equals(paramObject)) {
/* 291 */         if (cacheEntry2 != null) {
/* 292 */           cacheEntry2.next = cacheEntry1.next;
/*     */         } else {
/* 294 */           arrayOfCacheEntry[j] = cacheEntry1.next;
/*     */         } 
/* 296 */         this.count--;
/* 297 */         return cacheEntry1.check();
/*     */       } 
/*     */     } 
/* 300 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/Cache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */