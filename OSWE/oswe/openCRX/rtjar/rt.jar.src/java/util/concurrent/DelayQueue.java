/*     */ package java.util.concurrent;
/*     */ 
/*     */ import java.util.AbstractQueue;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.PriorityQueue;
/*     */ import java.util.concurrent.locks.Condition;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DelayQueue<E extends Delayed>
/*     */   extends AbstractQueue<E>
/*     */   implements BlockingQueue<E>
/*     */ {
/*  73 */   private final transient ReentrantLock lock = new ReentrantLock();
/*  74 */   private final PriorityQueue<E> q = new PriorityQueue<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   private Thread leader = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   private final Condition available = this.lock.newCondition();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DelayQueue(Collection<? extends E> paramCollection) {
/* 115 */     addAll(paramCollection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(E paramE) {
/* 126 */     return offer(paramE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean offer(E paramE) {
/* 137 */     ReentrantLock reentrantLock = this.lock;
/* 138 */     reentrantLock.lock();
/*     */     try {
/* 140 */       this.q.offer(paramE);
/* 141 */       if (this.q.peek() == paramE) {
/* 142 */         this.leader = null;
/* 143 */         this.available.signal();
/*     */       } 
/* 145 */       return true;
/*     */     } finally {
/* 147 */       reentrantLock.unlock();
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
/*     */   public void put(E paramE) {
/* 159 */     offer(paramE);
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
/*     */   public boolean offer(E paramE, long paramLong, TimeUnit paramTimeUnit) {
/* 173 */     return offer(paramE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E poll() {
/* 184 */     ReentrantLock reentrantLock = this.lock;
/* 185 */     reentrantLock.lock();
/*     */     try {
/* 187 */       Delayed delayed = (Delayed)this.q.peek();
/* 188 */       if (delayed == null || delayed.getDelay(TimeUnit.NANOSECONDS) > 0L) {
/* 189 */         return null;
/*     */       }
/* 191 */       return this.q.poll();
/*     */     } finally {
/* 193 */       reentrantLock.unlock();
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
/*     */   public E take() throws InterruptedException {
/* 205 */     ReentrantLock reentrantLock = this.lock;
/* 206 */     reentrantLock.lockInterruptibly();
/*     */     try {
/*     */       while (true) {
/* 209 */         Delayed delayed = (Delayed)this.q.peek();
/* 210 */         if (delayed == null) {
/* 211 */           this.available.await(); continue;
/*     */         } 
/* 213 */         long l = delayed.getDelay(TimeUnit.NANOSECONDS);
/* 214 */         if (l <= 0L)
/* 215 */           return this.q.poll(); 
/* 216 */         delayed = null;
/* 217 */         if (this.leader != null) {
/* 218 */           this.available.await(); continue;
/*     */         } 
/* 220 */         Thread thread = Thread.currentThread();
/* 221 */         this.leader = thread;
/*     */         try {
/* 223 */           this.available.awaitNanos(l);
/*     */         } finally {
/* 225 */           if (this.leader == thread) {
/* 226 */             this.leader = null;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } finally {
/*     */       
/* 232 */       if (this.leader == null && this.q.peek() != null)
/* 233 */         this.available.signal(); 
/* 234 */       reentrantLock.unlock();
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
/*     */   public E poll(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/* 249 */     long l = paramTimeUnit.toNanos(paramLong);
/* 250 */     ReentrantLock reentrantLock = this.lock;
/* 251 */     reentrantLock.lockInterruptibly();
/*     */     try {
/*     */       while (true) {
/* 254 */         Delayed delayed = (Delayed)this.q.peek();
/* 255 */         if (delayed == null) {
/* 256 */           if (l <= 0L) {
/* 257 */             return null;
/*     */           }
/* 259 */           l = this.available.awaitNanos(l); continue;
/*     */         } 
/* 261 */         long l1 = delayed.getDelay(TimeUnit.NANOSECONDS);
/* 262 */         if (l1 <= 0L)
/* 263 */           return this.q.poll(); 
/* 264 */         if (l <= 0L)
/* 265 */           return null; 
/* 266 */         delayed = null;
/* 267 */         if (l < l1 || this.leader != null) {
/* 268 */           l = this.available.awaitNanos(l); continue;
/*     */         } 
/* 270 */         Thread thread = Thread.currentThread();
/* 271 */         this.leader = thread;
/*     */         try {
/* 273 */           long l2 = this.available.awaitNanos(l1);
/* 274 */           l -= l1 - l2;
/*     */         } finally {
/* 276 */           if (this.leader == thread) {
/* 277 */             this.leader = null;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } finally {
/*     */       
/* 283 */       if (this.leader == null && this.q.peek() != null)
/* 284 */         this.available.signal(); 
/* 285 */       reentrantLock.unlock();
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
/*     */   public E peek() {
/* 300 */     ReentrantLock reentrantLock = this.lock;
/* 301 */     reentrantLock.lock();
/*     */     try {
/* 303 */       return this.q.peek();
/*     */     } finally {
/* 305 */       reentrantLock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public int size() {
/* 310 */     ReentrantLock reentrantLock = this.lock;
/* 311 */     reentrantLock.lock();
/*     */     try {
/* 313 */       return this.q.size();
/*     */     } finally {
/* 315 */       reentrantLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private E peekExpired() {
/* 325 */     Delayed delayed = (Delayed)this.q.peek();
/* 326 */     return (delayed == null || delayed.getDelay(TimeUnit.NANOSECONDS) > 0L) ? null : (E)delayed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int drainTo(Collection<? super E> paramCollection) {
/* 337 */     if (paramCollection == null)
/* 338 */       throw new NullPointerException(); 
/* 339 */     if (paramCollection == this)
/* 340 */       throw new IllegalArgumentException(); 
/* 341 */     ReentrantLock reentrantLock = this.lock;
/* 342 */     reentrantLock.lock();
/*     */     try {
/* 344 */       byte b = 0; E e;
/* 345 */       while ((e = peekExpired()) != null) {
/* 346 */         paramCollection.add(e);
/* 347 */         this.q.poll();
/* 348 */         b++;
/*     */       } 
/* 350 */       return b;
/*     */     } finally {
/* 352 */       reentrantLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int drainTo(Collection<? super E> paramCollection, int paramInt) {
/* 363 */     if (paramCollection == null)
/* 364 */       throw new NullPointerException(); 
/* 365 */     if (paramCollection == this)
/* 366 */       throw new IllegalArgumentException(); 
/* 367 */     if (paramInt <= 0)
/* 368 */       return 0; 
/* 369 */     ReentrantLock reentrantLock = this.lock;
/* 370 */     reentrantLock.lock();
/*     */     try {
/* 372 */       byte b = 0; E e;
/* 373 */       while (b < paramInt && (e = peekExpired()) != null) {
/* 374 */         paramCollection.add(e);
/* 375 */         this.q.poll();
/* 376 */         b++;
/*     */       } 
/* 378 */       return b;
/*     */     } finally {
/* 380 */       reentrantLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 391 */     ReentrantLock reentrantLock = this.lock;
/* 392 */     reentrantLock.lock();
/*     */     try {
/* 394 */       this.q.clear();
/*     */     } finally {
/* 396 */       reentrantLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int remainingCapacity() {
/* 407 */     return Integer.MAX_VALUE;
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
/*     */   public Object[] toArray() {
/* 424 */     ReentrantLock reentrantLock = this.lock;
/* 425 */     reentrantLock.lock();
/*     */     try {
/* 427 */       return this.q.toArray();
/*     */     } finally {
/* 429 */       reentrantLock.unlock();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T[] toArray(T[] paramArrayOfT) {
/* 469 */     ReentrantLock reentrantLock = this.lock;
/* 470 */     reentrantLock.lock();
/*     */     try {
/* 472 */       return (T[])this.q.toArray((Object[])paramArrayOfT);
/*     */     } finally {
/* 474 */       reentrantLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove(Object paramObject) {
/* 483 */     ReentrantLock reentrantLock = this.lock;
/* 484 */     reentrantLock.lock();
/*     */     try {
/* 486 */       return this.q.remove(paramObject);
/*     */     } finally {
/* 488 */       reentrantLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void removeEQ(Object paramObject) {
/* 496 */     ReentrantLock reentrantLock = this.lock;
/* 497 */     reentrantLock.lock();
/*     */     try {
/* 499 */       for (Iterator<E> iterator = this.q.iterator(); iterator.hasNext();) {
/* 500 */         if (paramObject == iterator.next()) {
/* 501 */           iterator.remove();
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 506 */       reentrantLock.unlock();
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
/*     */   public Iterator<E> iterator() {
/* 521 */     return new Itr(toArray());
/*     */   }
/*     */   
/*     */   public DelayQueue() {}
/*     */   
/*     */   private class Itr
/*     */     implements Iterator<E> {
/*     */     final Object[] array;
/*     */     int cursor;
/*     */     int lastRet;
/*     */     
/*     */     Itr(Object[] param1ArrayOfObject) {
/* 533 */       this.lastRet = -1;
/* 534 */       this.array = param1ArrayOfObject;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 538 */       return (this.cursor < this.array.length);
/*     */     }
/*     */ 
/*     */     
/*     */     public E next() {
/* 543 */       if (this.cursor >= this.array.length)
/* 544 */         throw new NoSuchElementException(); 
/* 545 */       this.lastRet = this.cursor;
/* 546 */       return (E)this.array[this.cursor++];
/*     */     }
/*     */     
/*     */     public void remove() {
/* 550 */       if (this.lastRet < 0)
/* 551 */         throw new IllegalStateException(); 
/* 552 */       DelayQueue.this.removeEQ(this.array[this.lastRet]);
/* 553 */       this.lastRet = -1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/DelayQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */