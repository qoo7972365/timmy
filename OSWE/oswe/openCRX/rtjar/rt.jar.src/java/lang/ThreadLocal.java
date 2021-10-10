/*     */ package java.lang;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ThreadLocal<T>
/*     */ {
/*  85 */   private final int threadLocalHashCode = nextHashCode();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   private static AtomicInteger nextHashCode = new AtomicInteger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int HASH_INCREMENT = 1640531527;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int nextHashCode() {
/* 105 */     return nextHashCode.getAndAdd(1640531527);
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
/*     */   protected T initialValue() {
/* 127 */     return null;
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
/*     */   public static <S> ThreadLocal<S> withInitial(Supplier<? extends S> paramSupplier) {
/* 141 */     return new SuppliedThreadLocal<>(paramSupplier);
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
/*     */   public T get() {
/* 160 */     Thread thread = Thread.currentThread();
/* 161 */     ThreadLocalMap threadLocalMap = getMap(thread);
/* 162 */     if (threadLocalMap != null) {
/* 163 */       ThreadLocalMap.Entry entry = threadLocalMap.getEntry(this);
/* 164 */       if (entry != null)
/*     */       {
/* 166 */         return (T)entry.value;
/*     */       }
/*     */     } 
/*     */     
/* 170 */     return setInitialValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private T setInitialValue() {
/* 180 */     T t = initialValue();
/* 181 */     Thread thread = Thread.currentThread();
/* 182 */     ThreadLocalMap threadLocalMap = getMap(thread);
/* 183 */     if (threadLocalMap != null) {
/* 184 */       threadLocalMap.set(this, t);
/*     */     } else {
/* 186 */       createMap(thread, t);
/* 187 */     }  return t;
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
/*     */   public void set(T paramT) {
/* 200 */     Thread thread = Thread.currentThread();
/* 201 */     ThreadLocalMap threadLocalMap = getMap(thread);
/* 202 */     if (threadLocalMap != null) {
/* 203 */       threadLocalMap.set(this, paramT);
/*     */     } else {
/* 205 */       createMap(thread, paramT);
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
/*     */   public void remove() {
/* 220 */     ThreadLocalMap threadLocalMap = getMap(Thread.currentThread());
/* 221 */     if (threadLocalMap != null) {
/* 222 */       threadLocalMap.remove(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ThreadLocalMap getMap(Thread paramThread) {
/* 233 */     return paramThread.threadLocals;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void createMap(Thread paramThread, T paramT) {
/* 244 */     paramThread.threadLocals = new ThreadLocalMap(this, paramT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ThreadLocalMap createInheritedMap(ThreadLocalMap paramThreadLocalMap) {
/* 255 */     return new ThreadLocalMap(paramThreadLocalMap);
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
/*     */   T childValue(T paramT) {
/* 267 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class SuppliedThreadLocal<T>
/*     */     extends ThreadLocal<T>
/*     */   {
/*     */     private final Supplier<? extends T> supplier;
/*     */ 
/*     */     
/*     */     SuppliedThreadLocal(Supplier<? extends T> param1Supplier) {
/* 279 */       this.supplier = Objects.<Supplier<? extends T>>requireNonNull(param1Supplier);
/*     */     }
/*     */ 
/*     */     
/*     */     protected T initialValue() {
/* 284 */       return this.supplier.get();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class ThreadLocalMap
/*     */   {
/*     */     private static final int INITIAL_CAPACITY = 16;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Entry[] table;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static class Entry
/*     */       extends WeakReference<ThreadLocal<?>>
/*     */     {
/*     */       Object value;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       Entry(ThreadLocal<?> param2ThreadLocal, Object param2Object) {
/* 313 */         super(param2ThreadLocal);
/* 314 */         this.value = param2Object;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 332 */     private int size = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int threshold;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void setThreshold(int param1Int) {
/* 343 */       this.threshold = param1Int * 2 / 3;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static int nextIndex(int param1Int1, int param1Int2) {
/* 350 */       return (param1Int1 + 1 < param1Int2) ? (param1Int1 + 1) : 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static int prevIndex(int param1Int1, int param1Int2) {
/* 357 */       return (param1Int1 - 1 >= 0) ? (param1Int1 - 1) : (param1Int2 - 1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ThreadLocalMap(ThreadLocal<?> param1ThreadLocal, Object param1Object) {
/* 366 */       this.table = new Entry[16];
/* 367 */       int i = param1ThreadLocal.threadLocalHashCode & 0xF;
/* 368 */       this.table[i] = new Entry(param1ThreadLocal, param1Object);
/* 369 */       this.size = 1;
/* 370 */       setThreshold(16);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private ThreadLocalMap(ThreadLocalMap param1ThreadLocalMap) {
/* 380 */       Entry[] arrayOfEntry = param1ThreadLocalMap.table;
/* 381 */       int i = arrayOfEntry.length;
/* 382 */       setThreshold(i);
/* 383 */       this.table = new Entry[i];
/*     */       
/* 385 */       for (byte b = 0; b < i; b++) {
/* 386 */         Entry entry = arrayOfEntry[b];
/* 387 */         if (entry != null) {
/*     */           
/* 389 */           ThreadLocal<?> threadLocal = entry.get();
/* 390 */           if (threadLocal != null) {
/* 391 */             Object object = threadLocal.childValue(entry.value);
/* 392 */             Entry entry1 = new Entry(threadLocal, object);
/* 393 */             int j = threadLocal.threadLocalHashCode & i - 1;
/* 394 */             while (this.table[j] != null)
/* 395 */               j = nextIndex(j, i); 
/* 396 */             this.table[j] = entry1;
/* 397 */             this.size++;
/*     */           } 
/*     */         } 
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
/*     */ 
/*     */ 
/*     */     
/*     */     private Entry getEntry(ThreadLocal<?> param1ThreadLocal) {
/* 414 */       int i = param1ThreadLocal.threadLocalHashCode & this.table.length - 1;
/* 415 */       Entry entry = this.table[i];
/* 416 */       if (entry != null && entry.get() == param1ThreadLocal) {
/* 417 */         return entry;
/*     */       }
/* 419 */       return getEntryAfterMiss(param1ThreadLocal, i, entry);
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
/*     */     private Entry getEntryAfterMiss(ThreadLocal<?> param1ThreadLocal, int param1Int, Entry param1Entry) {
/* 432 */       Entry[] arrayOfEntry = this.table;
/* 433 */       int i = arrayOfEntry.length;
/*     */       
/* 435 */       while (param1Entry != null) {
/* 436 */         ThreadLocal<?> threadLocal = param1Entry.get();
/* 437 */         if (threadLocal == param1ThreadLocal)
/* 438 */           return param1Entry; 
/* 439 */         if (threadLocal == null) {
/* 440 */           expungeStaleEntry(param1Int);
/*     */         } else {
/* 442 */           param1Int = nextIndex(param1Int, i);
/* 443 */         }  param1Entry = arrayOfEntry[param1Int];
/*     */       } 
/* 445 */       return null;
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
/*     */ 
/*     */ 
/*     */     
/*     */     private void set(ThreadLocal<?> param1ThreadLocal, Object param1Object) {
/* 461 */       Entry[] arrayOfEntry = this.table;
/* 462 */       int i = arrayOfEntry.length;
/* 463 */       int j = param1ThreadLocal.threadLocalHashCode & i - 1;
/*     */       
/* 465 */       Entry entry = arrayOfEntry[j];
/* 466 */       for (; entry != null; 
/* 467 */         entry = arrayOfEntry[j = nextIndex(j, i)]) {
/* 468 */         ThreadLocal<?> threadLocal = entry.get();
/*     */         
/* 470 */         if (threadLocal == param1ThreadLocal) {
/* 471 */           entry.value = param1Object;
/*     */           
/*     */           return;
/*     */         } 
/* 475 */         if (threadLocal == null) {
/* 476 */           replaceStaleEntry(param1ThreadLocal, param1Object, j);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 481 */       arrayOfEntry[j] = new Entry(param1ThreadLocal, param1Object);
/* 482 */       int k = ++this.size;
/* 483 */       if (!cleanSomeSlots(j, k) && k >= this.threshold) {
/* 484 */         rehash();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void remove(ThreadLocal<?> param1ThreadLocal) {
/* 491 */       Entry[] arrayOfEntry = this.table;
/* 492 */       int i = arrayOfEntry.length;
/* 493 */       int j = param1ThreadLocal.threadLocalHashCode & i - 1;
/* 494 */       Entry entry = arrayOfEntry[j];
/* 495 */       for (; entry != null; 
/* 496 */         entry = arrayOfEntry[j = nextIndex(j, i)]) {
/* 497 */         if (entry.get() == param1ThreadLocal) {
/* 498 */           entry.clear();
/* 499 */           expungeStaleEntry(j);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void replaceStaleEntry(ThreadLocal<?> param1ThreadLocal, Object param1Object, int param1Int) {
/* 522 */       Entry[] arrayOfEntry = this.table;
/* 523 */       int i = arrayOfEntry.length;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 530 */       int j = param1Int;
/* 531 */       int k = prevIndex(param1Int, i); Entry entry;
/* 532 */       for (; (entry = arrayOfEntry[k]) != null; 
/* 533 */         k = prevIndex(k, i)) {
/* 534 */         if (entry.get() == null) {
/* 535 */           j = k;
/*     */         }
/*     */       } 
/*     */       
/* 539 */       k = nextIndex(param1Int, i);
/* 540 */       for (; (entry = arrayOfEntry[k]) != null; 
/* 541 */         k = nextIndex(k, i)) {
/* 542 */         ThreadLocal<?> threadLocal = entry.get();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 549 */         if (threadLocal == param1ThreadLocal) {
/* 550 */           entry.value = param1Object;
/*     */           
/* 552 */           arrayOfEntry[k] = arrayOfEntry[param1Int];
/* 553 */           arrayOfEntry[param1Int] = entry;
/*     */ 
/*     */           
/* 556 */           if (j == param1Int)
/* 557 */             j = k; 
/* 558 */           cleanSomeSlots(expungeStaleEntry(j), i);
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */ 
/*     */         
/* 565 */         if (threadLocal == null && j == param1Int) {
/* 566 */           j = k;
/*     */         }
/*     */       } 
/*     */       
/* 570 */       (arrayOfEntry[param1Int]).value = null;
/* 571 */       arrayOfEntry[param1Int] = new Entry(param1ThreadLocal, param1Object);
/*     */ 
/*     */       
/* 574 */       if (j != param1Int) {
/* 575 */         cleanSomeSlots(expungeStaleEntry(j), i);
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
/*     */ 
/*     */ 
/*     */     
/*     */     private int expungeStaleEntry(int param1Int) {
/* 590 */       Entry[] arrayOfEntry = this.table;
/* 591 */       int i = arrayOfEntry.length;
/*     */ 
/*     */       
/* 594 */       (arrayOfEntry[param1Int]).value = null;
/* 595 */       arrayOfEntry[param1Int] = null;
/* 596 */       this.size--;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 601 */       int j = nextIndex(param1Int, i); Entry entry;
/* 602 */       for (; (entry = arrayOfEntry[j]) != null; 
/* 603 */         j = nextIndex(j, i)) {
/* 604 */         ThreadLocal<?> threadLocal = entry.get();
/* 605 */         if (threadLocal == null) {
/* 606 */           entry.value = null;
/* 607 */           arrayOfEntry[j] = null;
/* 608 */           this.size--;
/*     */         } else {
/* 610 */           int k = threadLocal.threadLocalHashCode & i - 1;
/* 611 */           if (k != j) {
/* 612 */             arrayOfEntry[j] = null;
/*     */ 
/*     */ 
/*     */             
/* 616 */             while (arrayOfEntry[k] != null)
/* 617 */               k = nextIndex(k, i); 
/* 618 */             arrayOfEntry[k] = entry;
/*     */           } 
/*     */         } 
/*     */       } 
/* 622 */       return j;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean cleanSomeSlots(int param1Int1, int param1Int2) {
/* 650 */       boolean bool = false;
/* 651 */       Entry[] arrayOfEntry = this.table;
/* 652 */       int i = arrayOfEntry.length;
/*     */       while (true) {
/* 654 */         param1Int1 = nextIndex(param1Int1, i);
/* 655 */         Entry entry = arrayOfEntry[param1Int1];
/* 656 */         if (entry != null && entry.get() == null) {
/* 657 */           param1Int2 = i;
/* 658 */           bool = true;
/* 659 */           param1Int1 = expungeStaleEntry(param1Int1);
/*     */         } 
/* 661 */         if ((param1Int2 >>>= 1) == 0) {
/* 662 */           return bool;
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void rehash() {
/* 671 */       expungeStaleEntries();
/*     */ 
/*     */       
/* 674 */       if (this.size >= this.threshold - this.threshold / 4) {
/* 675 */         resize();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void resize() {
/* 682 */       Entry[] arrayOfEntry1 = this.table;
/* 683 */       int i = arrayOfEntry1.length;
/* 684 */       int j = i * 2;
/* 685 */       Entry[] arrayOfEntry2 = new Entry[j];
/* 686 */       byte b1 = 0;
/*     */       
/* 688 */       for (byte b2 = 0; b2 < i; b2++) {
/* 689 */         Entry entry = arrayOfEntry1[b2];
/* 690 */         if (entry != null) {
/* 691 */           ThreadLocal<?> threadLocal = entry.get();
/* 692 */           if (threadLocal == null) {
/* 693 */             entry.value = null;
/*     */           } else {
/* 695 */             int k = threadLocal.threadLocalHashCode & j - 1;
/* 696 */             while (arrayOfEntry2[k] != null)
/* 697 */               k = nextIndex(k, j); 
/* 698 */             arrayOfEntry2[k] = entry;
/* 699 */             b1++;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 704 */       setThreshold(j);
/* 705 */       this.size = b1;
/* 706 */       this.table = arrayOfEntry2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void expungeStaleEntries() {
/* 713 */       Entry[] arrayOfEntry = this.table;
/* 714 */       int i = arrayOfEntry.length;
/* 715 */       for (byte b = 0; b < i; b++) {
/* 716 */         Entry entry = arrayOfEntry[b];
/* 717 */         if (entry != null && entry.get() == null)
/* 718 */           expungeStaleEntry(b); 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static class Entry extends WeakReference<ThreadLocal<?>> {
/*     */     Object value;
/*     */     
/*     */     Entry(ThreadLocal<?> param1ThreadLocal, Object param1Object) {
/*     */       super(param1ThreadLocal);
/*     */       this.value = param1Object;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/ThreadLocal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */