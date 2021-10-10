/*     */ package java.lang;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ClassValue<T>
/*     */ {
/*     */   protected abstract T computeValue(Class<?> paramClass);
/*     */   
/*     */   public T get(Class<?> paramClass) {
/*     */     Entry[] arrayOfEntry;
/* 102 */     Entry<?> entry = ClassValueMap.probeHomeLocation((Entry<?>[])(arrayOfEntry = (Entry[])getCacheCarefully(paramClass)), this);
/*     */ 
/*     */     
/* 105 */     if (match(entry))
/*     */     {
/*     */ 
/*     */       
/* 109 */       return (T)entry.value();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 115 */     return getFromBackup((Entry<?>[])arrayOfEntry, paramClass);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(Class<?> paramClass) {
/* 172 */     ClassValueMap classValueMap = getMap(paramClass);
/* 173 */     classValueMap.removeEntry(this);
/*     */   }
/*     */ 
/*     */   
/*     */   void put(Class<?> paramClass, T paramT) {
/* 178 */     ClassValueMap classValueMap = getMap(paramClass);
/* 179 */     classValueMap.changeEntry(this, paramT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Entry<?>[] getCacheCarefully(Class<?> paramClass) {
/* 189 */     ClassValueMap classValueMap = paramClass.classValueMap;
/* 190 */     if (classValueMap == null) return EMPTY_CACHE; 
/* 191 */     return classValueMap.getCache();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 197 */   private static final Entry<?>[] EMPTY_CACHE = new Entry[] { null };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private T getFromBackup(Entry<?>[] paramArrayOfEntry, Class<?> paramClass) {
/* 206 */     Entry<?> entry = ClassValueMap.probeBackupLocations(paramArrayOfEntry, this);
/* 207 */     if (entry != null)
/* 208 */       return (T)entry.value(); 
/* 209 */     return getFromHashMap(paramClass);
/*     */   }
/*     */ 
/*     */   
/*     */   Entry<T> castEntry(Entry<?> paramEntry) {
/* 214 */     return (Entry)paramEntry;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private T getFromHashMap(Class<?> paramClass) {
/* 220 */     ClassValueMap classValueMap = getMap(paramClass);
/*     */     while (true) {
/* 222 */       Entry<?> entry = classValueMap.startEntry(this);
/* 223 */       if (!entry.isPromise()) {
/* 224 */         return (T)entry.value();
/*     */       }
/*     */       try {
/* 227 */         entry = makeEntry(entry.version(), computeValue(paramClass));
/*     */       }
/*     */       finally {
/*     */         
/* 231 */         entry = classValueMap.finishEntry(this, entry);
/*     */       } 
/* 233 */       if (entry != null) {
/* 234 */         return (T)entry.value();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean match(Entry<?> paramEntry) {
/* 243 */     return (paramEntry != null && paramEntry.get() == this.version);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 249 */   final int hashCodeForCache = nextHashCode.getAndAdd(1640531527) & 0x3FFFFFFF;
/*     */ 
/*     */   
/* 252 */   private static final AtomicInteger nextHashCode = new AtomicInteger();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int HASH_INCREMENT = 1640531527;
/*     */ 
/*     */ 
/*     */   
/*     */   static final int HASH_MASK = 1073741823;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class Identity {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 271 */   final Identity identity = new Identity();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 300 */   private volatile Version<T> version = new Version<>(this);
/* 301 */   Version<T> version() { return this.version; } void bumpVersion() {
/* 302 */     this.version = new Version<>(this);
/*     */   }
/*     */   static class Version<T> { private final ClassValue<T> classValue;
/* 305 */     private final ClassValue.Entry<T> promise = new ClassValue.Entry<>(this);
/* 306 */     Version(ClassValue<T> param1ClassValue) { this.classValue = param1ClassValue; }
/* 307 */     ClassValue<T> classValue() { return this.classValue; }
/* 308 */     ClassValue.Entry<T> promise() { return this.promise; } boolean isLive() {
/* 309 */       return (this.classValue.version() == this);
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class Entry<T>
/*     */     extends WeakReference<Version<T>>
/*     */   {
/*     */     final Object value;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Entry(ClassValue.Version<T> param1Version, T param1T) {
/* 326 */       super(param1Version);
/* 327 */       this.value = param1T;
/*     */     } private void assertNotPromise() {
/* 329 */       assert !isPromise();
/*     */     }
/*     */     Entry(ClassValue.Version<T> param1Version) {
/* 332 */       super(param1Version);
/* 333 */       this.value = this;
/*     */     }
/*     */     
/*     */     T value() {
/* 337 */       assertNotPromise(); return (T)this.value;
/* 338 */     } boolean isPromise() { return (this.value == this); } ClassValue.Version<T> version() {
/* 339 */       return get();
/*     */     } ClassValue<T> classValueOrNull() {
/* 341 */       ClassValue.Version<T> version = version();
/* 342 */       return (version == null) ? null : version.classValue();
/*     */     }
/*     */     boolean isLive() {
/* 345 */       ClassValue.Version<T> version = version();
/* 346 */       if (version == null) return false; 
/* 347 */       if (version.isLive()) return true; 
/* 348 */       clear();
/* 349 */       return false;
/*     */     }
/*     */     Entry<T> refreshVersion(ClassValue.Version<T> param1Version) {
/* 352 */       assertNotPromise();
/*     */       
/* 354 */       Entry<T> entry = new Entry(param1Version, (T)this.value);
/* 355 */       clear();
/*     */       
/* 357 */       return entry;
/*     */     }
/* 359 */     static final Entry<?> DEAD_ENTRY = new Entry(null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ClassValueMap getMap(Class<?> paramClass) {
/* 367 */     ClassValueMap classValueMap = paramClass.classValueMap;
/* 368 */     if (classValueMap != null) return classValueMap; 
/* 369 */     return initializeMap(paramClass);
/*     */   }
/*     */   
/* 372 */   private static final Object CRITICAL_SECTION = new Object();
/*     */   private static ClassValueMap initializeMap(Class<?> paramClass) {
/*     */     ClassValueMap classValueMap;
/* 375 */     synchronized (CRITICAL_SECTION) {
/*     */       
/* 377 */       if ((classValueMap = paramClass.classValueMap) == null)
/* 378 */         paramClass.classValueMap = classValueMap = new ClassValueMap(paramClass); 
/*     */     } 
/* 380 */     return classValueMap;
/*     */   }
/*     */ 
/*     */   
/*     */   static <T> Entry<T> makeEntry(Version<T> paramVersion, T paramT) {
/* 385 */     return new Entry<>(paramVersion, paramT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class ClassValueMap
/*     */     extends WeakHashMap<Identity, Entry<?>>
/*     */   {
/*     */     private final Class<?> type;
/*     */ 
/*     */ 
/*     */     
/*     */     private ClassValue.Entry<?>[] cacheArray;
/*     */ 
/*     */ 
/*     */     
/*     */     private int cacheLoad;
/*     */ 
/*     */ 
/*     */     
/*     */     private int cacheLoadLimit;
/*     */ 
/*     */     
/*     */     private static final int INITIAL_ENTRIES = 32;
/*     */ 
/*     */     
/*     */     private static final int CACHE_LOAD_LIMIT = 67;
/*     */ 
/*     */     
/*     */     private static final int PROBE_LIMIT = 6;
/*     */ 
/*     */ 
/*     */     
/*     */     ClassValueMap(Class<?> param1Class) {
/* 420 */       this.type = param1Class;
/* 421 */       sizeCache(32);
/*     */     }
/*     */     ClassValue.Entry<?>[] getCache() {
/* 424 */       return this.cacheArray;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     synchronized <T> ClassValue.Entry<T> startEntry(ClassValue<T> param1ClassValue) {
/* 430 */       ClassValue.Entry<?> entry = get(param1ClassValue.identity);
/* 431 */       ClassValue.Version<T> version = param1ClassValue.version();
/* 432 */       if (entry == null) {
/* 433 */         entry = version.promise();
/*     */ 
/*     */         
/* 436 */         put(param1ClassValue.identity, entry);
/*     */         
/* 438 */         return (ClassValue.Entry)entry;
/* 439 */       }  if (entry.isPromise()) {
/*     */ 
/*     */         
/* 442 */         if (entry.version() != version) {
/* 443 */           entry = version.promise();
/* 444 */           put(param1ClassValue.identity, entry);
/*     */         } 
/* 446 */         return (ClassValue.Entry)entry;
/*     */       } 
/*     */       
/* 449 */       if (entry.version() != version) {
/*     */ 
/*     */         
/* 452 */         entry = entry.refreshVersion(version);
/* 453 */         put(param1ClassValue.identity, entry);
/*     */       } 
/*     */       
/* 456 */       checkCacheLoad();
/* 457 */       addToCache(param1ClassValue, (ClassValue.Entry)entry);
/* 458 */       return (ClassValue.Entry)entry;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     synchronized <T> ClassValue.Entry<T> finishEntry(ClassValue<T> param1ClassValue, ClassValue.Entry<T> param1Entry) {
/* 466 */       ClassValue.Entry<?> entry = get(param1ClassValue.identity);
/* 467 */       if (param1Entry == entry) {
/*     */         
/* 469 */         assert param1Entry.isPromise();
/* 470 */         remove(param1ClassValue.identity);
/* 471 */         return null;
/* 472 */       }  if (entry != null && entry.isPromise() && entry.version() == param1Entry.version()) {
/*     */ 
/*     */         
/* 475 */         ClassValue.Version<T> version = param1ClassValue.version();
/* 476 */         if (param1Entry.version() != version)
/* 477 */           param1Entry = param1Entry.refreshVersion(version); 
/* 478 */         put(param1ClassValue.identity, param1Entry);
/*     */         
/* 480 */         checkCacheLoad();
/* 481 */         addToCache(param1ClassValue, param1Entry);
/* 482 */         return param1Entry;
/*     */       } 
/*     */       
/* 485 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     synchronized void removeEntry(ClassValue<?> param1ClassValue) {
/* 492 */       ClassValue.Entry<?> entry = remove(param1ClassValue.identity);
/* 493 */       if (entry != null)
/*     */       {
/* 495 */         if (entry.isPromise()) {
/*     */ 
/*     */ 
/*     */           
/* 499 */           put(param1ClassValue.identity, entry);
/*     */         } else {
/*     */           
/* 502 */           param1ClassValue.bumpVersion();
/*     */           
/* 504 */           removeStaleEntries(param1ClassValue);
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     synchronized <T> void changeEntry(ClassValue<T> param1ClassValue, T param1T) {
/* 512 */       ClassValue.Entry<?> entry = get(param1ClassValue.identity);
/* 513 */       ClassValue.Version<T> version = param1ClassValue.version();
/* 514 */       if (entry != null) {
/* 515 */         if (entry.version() == version && entry.value() == param1T) {
/*     */           return;
/*     */         }
/* 518 */         param1ClassValue.bumpVersion();
/* 519 */         removeStaleEntries(param1ClassValue);
/*     */       } 
/* 521 */       ClassValue.Entry<T> entry1 = ClassValue.makeEntry(version, param1T);
/* 522 */       put(param1ClassValue.identity, entry1);
/*     */       
/* 524 */       checkCacheLoad();
/* 525 */       addToCache(param1ClassValue, entry1);
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
/*     */     
/*     */     static ClassValue.Entry<?> loadFromCache(ClassValue.Entry<?>[] param1ArrayOfEntry, int param1Int) {
/* 538 */       return param1ArrayOfEntry[param1Int & param1ArrayOfEntry.length - 1];
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     static <T> ClassValue.Entry<T> probeHomeLocation(ClassValue.Entry<?>[] param1ArrayOfEntry, ClassValue<T> param1ClassValue) {
/* 544 */       return param1ClassValue.castEntry(loadFromCache(param1ArrayOfEntry, param1ClassValue.hashCodeForCache));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static <T> ClassValue.Entry<T> probeBackupLocations(ClassValue.Entry<?>[] param1ArrayOfEntry, ClassValue<T> param1ClassValue) {
/* 551 */       int i = param1ArrayOfEntry.length - 1;
/* 552 */       int j = param1ClassValue.hashCodeForCache & i;
/* 553 */       ClassValue.Entry<?> entry = param1ArrayOfEntry[j];
/* 554 */       if (entry == null) {
/* 555 */         return null;
/*     */       }
/*     */       
/* 558 */       int k = -1;
/* 559 */       for (int m = j + 1; m < j + 6; m++) {
/* 560 */         ClassValue.Entry<?> entry1 = param1ArrayOfEntry[m & i];
/* 561 */         if (entry1 == null) {
/*     */           break;
/*     */         }
/* 564 */         if (param1ClassValue.match(entry1)) {
/*     */           
/* 566 */           param1ArrayOfEntry[j] = entry1;
/* 567 */           if (k >= 0) {
/* 568 */             param1ArrayOfEntry[m & i] = ClassValue.Entry.DEAD_ENTRY;
/*     */           } else {
/* 570 */             k = m;
/*     */           } 
/* 572 */           param1ArrayOfEntry[k & i] = (entryDislocation(param1ArrayOfEntry, k, entry) < 6) ? entry : ClassValue.Entry.DEAD_ENTRY;
/*     */ 
/*     */           
/* 575 */           return param1ClassValue.castEntry(entry1);
/*     */         } 
/*     */         
/* 578 */         if (!entry1.isLive() && k < 0) k = m; 
/*     */       } 
/* 580 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     private static int entryDislocation(ClassValue.Entry<?>[] param1ArrayOfEntry, int param1Int, ClassValue.Entry<?> param1Entry) {
/* 585 */       ClassValue<?> classValue = param1Entry.classValueOrNull();
/* 586 */       if (classValue == null) return 0; 
/* 587 */       int i = param1ArrayOfEntry.length - 1;
/* 588 */       return param1Int - classValue.hashCodeForCache & i;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void sizeCache(int param1Int) {
/* 596 */       assert (param1Int & param1Int - 1) == 0;
/* 597 */       this.cacheLoad = 0;
/* 598 */       this.cacheLoadLimit = (int)(param1Int * 67.0D / 100.0D);
/* 599 */       this.cacheArray = (ClassValue.Entry<?>[])new ClassValue.Entry[param1Int];
/*     */     }
/*     */ 
/*     */     
/*     */     private void checkCacheLoad() {
/* 604 */       if (this.cacheLoad >= this.cacheLoadLimit)
/* 605 */         reduceCacheLoad(); 
/*     */     }
/*     */     
/*     */     private void reduceCacheLoad() {
/* 609 */       removeStaleEntries();
/* 610 */       if (this.cacheLoad < this.cacheLoadLimit)
/*     */         return; 
/* 612 */       ClassValue.Entry[] arrayOfEntry = (ClassValue.Entry[])getCache();
/* 613 */       if (arrayOfEntry.length > 1073741823)
/*     */         return; 
/* 615 */       sizeCache(arrayOfEntry.length * 2);
/* 616 */       for (ClassValue.Entry<?> entry : arrayOfEntry) {
/* 617 */         if (entry != null && entry.isLive()) {
/* 618 */           addToCache(entry);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void removeStaleEntries(ClassValue.Entry<?>[] param1ArrayOfEntry, int param1Int1, int param1Int2) {
/* 628 */       int i = param1ArrayOfEntry.length - 1;
/* 629 */       byte b = 0;
/* 630 */       for (int j = param1Int1; j < param1Int1 + param1Int2; j++) {
/* 631 */         ClassValue.Entry<?> entry = param1ArrayOfEntry[j & i];
/* 632 */         if (entry != null && !entry.isLive()) {
/*     */           
/* 634 */           ClassValue.Entry<?> entry1 = null;
/*     */ 
/*     */           
/* 637 */           entry1 = findReplacement(param1ArrayOfEntry, j);
/*     */           
/* 639 */           param1ArrayOfEntry[j & i] = entry1;
/* 640 */           if (entry1 == null) b++; 
/*     */         } 
/* 642 */       }  this.cacheLoad = Math.max(0, this.cacheLoad - b);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private ClassValue.Entry<?> findReplacement(ClassValue.Entry<?>[] param1ArrayOfEntry, int param1Int) {
/* 651 */       ClassValue.Entry<?> entry = null;
/* 652 */       byte b = -1; int i = 0;
/* 653 */       int j = param1ArrayOfEntry.length - 1;
/* 654 */       for (int k = param1Int + 1; k < param1Int + 6; k++) {
/* 655 */         ClassValue.Entry<?> entry1 = param1ArrayOfEntry[k & j];
/* 656 */         if (entry1 == null)
/* 657 */           break;  if (entry1.isLive()) {
/* 658 */           int m = entryDislocation(param1ArrayOfEntry, k, entry1);
/* 659 */           if (m != 0) {
/* 660 */             int n = k - m;
/* 661 */             if (n <= param1Int)
/*     */             {
/* 663 */               if (n == param1Int) {
/*     */                 
/* 665 */                 b = 1;
/* 666 */                 i = k;
/* 667 */                 entry = entry1;
/* 668 */               } else if (b <= 0) {
/* 669 */                 b = 0;
/* 670 */                 i = k;
/* 671 */                 entry = entry1;
/*     */               }  } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 676 */       if (b >= 0) {
/* 677 */         if (param1ArrayOfEntry[i + 1 & j] != null) {
/*     */           
/* 679 */           param1ArrayOfEntry[i & j] = ClassValue.Entry.DEAD_ENTRY;
/*     */         } else {
/* 681 */           param1ArrayOfEntry[i & j] = null;
/* 682 */           this.cacheLoad--;
/*     */         } 
/*     */       }
/* 685 */       return entry;
/*     */     }
/*     */ 
/*     */     
/*     */     private void removeStaleEntries(ClassValue<?> param1ClassValue) {
/* 690 */       removeStaleEntries(getCache(), param1ClassValue.hashCodeForCache, 6);
/*     */     }
/*     */ 
/*     */     
/*     */     private void removeStaleEntries() {
/* 695 */       ClassValue.Entry[] arrayOfEntry = (ClassValue.Entry[])getCache();
/* 696 */       removeStaleEntries((ClassValue.Entry<?>[])arrayOfEntry, 0, arrayOfEntry.length + 6 - 1);
/*     */     }
/*     */ 
/*     */     
/*     */     private <T> void addToCache(ClassValue.Entry<T> param1Entry) {
/* 701 */       ClassValue<T> classValue = param1Entry.classValueOrNull();
/* 702 */       if (classValue != null) {
/* 703 */         addToCache(classValue, param1Entry);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private <T> void addToCache(ClassValue<T> param1ClassValue, ClassValue.Entry<T> param1Entry) {
/* 710 */       ClassValue.Entry[] arrayOfEntry = (ClassValue.Entry[])getCache();
/* 711 */       int i = arrayOfEntry.length - 1;
/* 712 */       int j = param1ClassValue.hashCodeForCache & i;
/* 713 */       ClassValue.Entry<?> entry = placeInCache((ClassValue.Entry<?>[])arrayOfEntry, j, param1Entry, false);
/* 714 */       if (entry == null) {
/*     */         return;
/*     */       }
/* 717 */       int k = entryDislocation((ClassValue.Entry<?>[])arrayOfEntry, j, entry);
/* 718 */       int m = j - k;
/* 719 */       for (int n = m; n < m + 6; n++) {
/* 720 */         if (placeInCache((ClassValue.Entry<?>[])arrayOfEntry, n & i, entry, true) == null) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private ClassValue.Entry<?> placeInCache(ClassValue.Entry<?>[] param1ArrayOfEntry, int param1Int, ClassValue.Entry<?> param1Entry, boolean param1Boolean) {
/* 732 */       ClassValue.Entry<?> entry = overwrittenEntry(param1ArrayOfEntry[param1Int]);
/* 733 */       if (param1Boolean && entry != null)
/*     */       {
/* 735 */         return param1Entry;
/*     */       }
/* 737 */       param1ArrayOfEntry[param1Int] = param1Entry;
/* 738 */       return entry;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private <T> ClassValue.Entry<T> overwrittenEntry(ClassValue.Entry<T> param1Entry) {
/* 749 */       if (param1Entry == null) { this.cacheLoad++; }
/* 750 */       else if (param1Entry.isLive()) { return param1Entry; }
/* 751 */        return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/ClassValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */